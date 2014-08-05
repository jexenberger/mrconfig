package org.github.mrconfig.framework.resources;

import org.github.mrconfig.framework.activerecord.*;
import org.github.mrconfig.framework.activerecord.Link;
import org.github.mrconfig.framework.service.Listable;
import org.github.mrconfig.framework.service.UniqueLookup;
import org.github.mrconfig.framework.util.GenericsUtil;
import org.github.mrconfig.framework.util.Pair;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.github.mrconfig.domain.KeyEntity.resolveByKeyOrId;
import static org.github.mrconfig.framework.activerecord.ActiveRecord.*;
import static org.github.mrconfig.framework.activerecord.Parameter.p;
import static org.github.mrconfig.framework.resources.ResourceUtil.getResourcePath;
import static org.github.mrconfig.framework.util.Pair.cons;

/**
 * Created by julian3 on 2014/07/18.
 */
public interface ReadableResource<T,K extends Serializable> {
    @GET
    @Path("{id}")
    default Response get(@Context SecurityContext context, @PathParam("id") String id) {

        K value = TransformerService.convert(id, getResourceTypeId());
        Optional<T> byId = getLookup().get(value);
        if (!byId.isPresent()) {
           return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (!isAllowed(byId.get())) {
           return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.ok(byId.get()).build();
    }

    default Class<K> getResourceTypeId() {
       return (Class<K>) GenericsUtil.getClass(this.getClass(),1);
    }

    Listable<T> getListableResource();
    UniqueLookup<T,K> getLookup();

    default boolean isAllowed(T t) {
        return true;
    }


    @GET
    default Response get(@Context UriInfo ui) {
        MultivaluedMap<String, String> queryParameters = ui.getQueryParameters();
        Collection<Pair<String, Object>> parameters = new ArrayList<>();
        queryParameters.forEach((key, value) -> {
            if (!key.equals(getPageParameter()) && !key.equals(getDetailsParameter())) {
                parameters.add(cons(key, value.get(0)));
            }
        });
        addFilterParameters(parameters);
        Pair<String, Object>[] paramArray = parameters.toArray(new Pair[]{});
        Listable<T> resource = getListableResource();
        long totalResults = resource.count(paramArray);
        Integer page = (totalResults <100) ? -1 : 1;

        if (queryParameters.containsKey(getPageParameter())) {
            page = Integer.valueOf(queryParameters.getFirst(getPageParameter()));

        }
        Collection<T> results;

        Results<T> response = newResultsClass();
        response.setTotalResults(totalResults);
        if (totalResults == 0) {
            response.setTotalPages(0);
            response.setCurrentPage(0);
            return Response.ok(new GenericEntity<>(response, response.getClass())).build();
        }

        if (page > 0) {
            int maxPageSize = getPageSize();
            long pages = totalResults / maxPageSize;

            long maxPages = pages + (((totalResults % maxPageSize) > 0) ? 1 : 0);
            if (page > maxPages) {
                throw new BadRequestException("specified page exceeds total pages of " + pages);
            }
            int offset= ((page > 1) ? (page-1)* maxPageSize :0);
            results = resource.page(offset, maxPageSize, paramArray);
            response.setTotalPages(maxPages);
            response.setCurrentPage(page);
            if (page < maxPages) {
                response.getLink().add(new Link("next", null, buildPath(ui, page+1), "Next Page"));
            }
            if (page > 1) {
                response.getLink().add(new Link("prev", null, buildPath(ui, page-1), "Previous Page"));
            }
            response.setCurrentPage(page);
        } else {
            results = resource.list(paramArray);
            response.setTotalPages(1);
            response.setCurrentPage(1);
        }

        if (queryParameters.containsKey(getDetailsParameter())) {
            response.setResource(results);
        } else {
            response.setResult(results.stream().map(resource::toLink).collect(toList()));
        }
        return Response.ok(new GenericEntity<>(response, response.getClass())).build();

    }

    default void addFilterParameters(Collection<Pair<String, Object>> parameters) {
        //do nothing, can be overriden
    }

    default Results<T> newResultsClass() {
        return new Results<>();
    }

    default String getPageParameter() {
        return "page";
    }

    default String getDetailsParameter() {
        return "details";
    }

    default int getPageSize() {
        return 5;
    }

    default String buildPath(UriInfo ui, int pageNumber) {
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.putAll(ui.getQueryParameters());
        parameters.put(getPageParameter(),asList(Integer.toString(pageNumber)));

        return  ui.getPath() +"?" + parameters.entrySet().stream().map((entry)-> entry.getKey()+"="+entry.getValue().get(0)).collect(Collectors.joining("&"));
    }


}
