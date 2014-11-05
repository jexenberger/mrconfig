package org.github.levelthree.resources;

import org.github.levelthree.Resource;
import org.github.levelthree.ResourceRegistry;
import org.github.levelthree.security.Security;
import org.github.levelthree.service.Link;
import org.github.levelthree.service.Listable;
import org.github.levelthree.service.UniqueLookup;
import org.github.levelthree.util.GenericsUtil;
import org.github.levelthree.util.Pair;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.github.levelthree.resources.Error.notFound;
import static org.github.levelthree.resources.Errors.errors;
import static org.github.levelthree.util.Pair.cons;

/**
 * Created by julian3 on 2014/07/18.
 */
public interface ReadableResource<T, K extends Serializable> extends BaseResource {


    @GET
    @Path("{id}")
    default Response get(@Context SecurityContext context, @PathParam("id") String id, @Context UriInfo ui, @Context HttpHeaders headers) {

        if (!isUserAllowedToLookup(context)) return Response.status(Response.Status.UNAUTHORIZED).build();

        Optional<MediaType> mediaType = resolveMediaType(headers.getMediaType());

        Optional<T> byId = getLookup().resolve(id, getResourceIdType());
        if (!byId.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).entity(errors(notFound())).build();
        }

        T entity = byId.get();
        if (!isAllowed(entity)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        populateGetLinks(entity);

        return Response.ok(entity).type(mediaType.get()).links(getLookup().toLink(entity, toType(mediaType.get()))).build();
    }

    default boolean isUserAllowedToLookup(SecurityContext context) {
        Resource resource = ResourceRegistry.get(getPath());
        if (Security.authorized(context, resource.isRequiresAuthentication(), resource.getLookupRole())) {
            return true;
        }
        return false;
    }

    default void populateGetLinks(T entity) {

    }


    default Class<K> getResourceIdType() {
        return (Class<K>) GenericsUtil.getClass(this.getClass(), 1);
    }

    Listable<T> getListableResource();

    UniqueLookup<T, K> getLookup();

    default boolean isAllowed(T t) {
        return true;
    }

    @GET
    default Response get(@Context SecurityContext context, @Context UriInfo ui, @Context HttpHeaders headers) {

        Optional<MediaType> mediaType = resolveMediaType(headers.getMediaType());

        if (!isUserAllowedToList(context)) return Response.status(Response.Status.UNAUTHORIZED).build();

        if (!mediaType.isPresent()) {
            return Response.status(Response.Status.UNSUPPORTED_MEDIA_TYPE).build();
        }

        MultivaluedMap<String, String> queryParameters = ui.getQueryParameters();
        Collection<Pair<String, Object>> parameters = new ArrayList<>();
        queryParameters.forEach((key, value) -> {
            if (!key.equals(getPageParameter()) && !key.equals(getDetailsParameter())) {
                parameters.add(cons(key, value.get(0)));
            }
        });
        addFilterParameters(parameters);
        Pair<String, Object>[] paramArray = parameters.toArray(new Pair[]{});
        Listable<T> listable = getListableResource();
        long totalResults = listable.count(paramArray);
        Integer page = (totalResults < 100) ? -1 : 1;

        if (queryParameters.containsKey(getPageParameter())) {
            page = Integer.valueOf(queryParameters.getFirst(getPageParameter()));

        }
        Collection<T> results;

        ListableResource<T> response = newResultsClass();
        response.setTotalResults(totalResults);
        if (totalResults == 0) {
            response.setTotalPages(0);
            response.setCurrentPage(0);
            return Response.ok(new GenericEntity<>(response, response.getClass())).type(mediaType.get()).build();
        }

        if (page > 0) {
            int maxPageSize = getPageSize();
            long pages = totalResults / maxPageSize;

            long maxPages = pages + (((totalResults % maxPageSize) > 0) ? 1 : 0);
            if (page > maxPages) {
                throw new BadRequestException("specified page exceeds total pages of " + pages);
            }
            int offset = ((page > 1) ? (page - 1) * maxPageSize : 0);
            results = listable.page(offset, maxPageSize, paramArray);
            response.setTotalPages(maxPages);
            response.setCurrentPage(page);
            if (page < maxPages) {
                response.getLink().add(new Link("next", null, buildPath(ui, page + 1), "Next Page"));
            }
            if (page > 1) {
                response.getLink().add(new Link("prev", null, buildPath(ui, page - 1), "Previous Page"));
            }
            response.setCurrentPage(page);
        } else {
            results = listable.list(paramArray);
            response.setTotalPages(1);
            response.setCurrentPage(1);
        }

        if (queryParameters.containsKey(getDetailsParameter())) {
            response.setResource(results);
        } else {
            response.setResult(results.stream().map((instance) -> {
                return listable.toLink(instance, toType(mediaType.get()));
            }).collect(toList()));
        }
        return Response.ok(new GenericEntity<>(response, response.getClass())).type(mediaType.get()).build();

    }

    default boolean isUserAllowedToList(SecurityContext context) {
        Resource resource = ResourceRegistry.get(getPath());
        return Security.authorized(context, resource.isRequiresAuthentication(), resource.getListRole());
    }

    default void addFilterParameters(Collection<Pair<String, Object>> parameters) {
        //do nothing, can be overriden
    }

    default ListableResource<T> newResultsClass() {
        return new Results<>();
    }

    default String getPageParameter() {
        return "page";
    }

    default String getDetailsParameter() {
        return "details";
    }

    default int getPageSize() {
        return 15;
    }

    default String buildPath(UriInfo ui, int pageNumber) {
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.putAll(ui.getQueryParameters());
        parameters.put(getPageParameter(), asList(Integer.toString(pageNumber)));

        return ui.getPath() + "?" + parameters.entrySet().stream().map((entry) -> entry.getKey() + "=" + entry.getValue().get(0)).collect(Collectors.joining("&"));
    }


}
