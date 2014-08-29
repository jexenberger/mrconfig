package org.github.mrconfig.framework.resources;

import org.github.mrconfig.framework.Resource;
import org.github.mrconfig.framework.ResourceRegistry;
import org.github.mrconfig.framework.service.Creatable;
import org.github.mrconfig.framework.service.Updateable;
import org.github.mrconfig.framework.util.Box;
import org.github.mrconfig.framework.util.GenericsUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.Serializable;
import java.net.URI;

/**
 * Created by julian3 on 2014/07/18.
 */
public interface WritableResource<T, K extends Serializable> extends BaseResource{


    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    default Response create(@Context SecurityContext context, T instance, @Context UriInfo uri) {


        Resource resource = ResourceRegistry.get(getPath());
        if (notAuthorized(context, resource.getCreateRole())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Creatable<T, K> service = getCreatable();
        Box<K> result = service.create(instance);
        if (result.isSuccess()) {
            String urn = ResourceUtil.getResourcePath(getClass()) + "/" + result.get();
            UriBuilder uriBuilder = UriBuilder.fromUri(urn);
            URI build = uriBuilder.build();
            populatePostCreationLinks(instance);
            return Response.created(build).entity(instance).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(new Errors(result.mapError(Error::new))).build();
        }
    }

    default void populatePostCreationLinks(T instance) {

    }

    Creatable<T, K> getCreatable();

    Updateable<T,K> getUpdateable();

    default Class<K> getResourceIdType() {
        return (Class<K>) GenericsUtil.getClass(this.getClass(), 1);
    }



    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    default Response save(@Context SecurityContext context, T group, @Context UriInfo uri) {

        Resource resource = ResourceRegistry.get(getPath());
        if (notAuthorized(context, resource.getUpdateRole())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        applySaveState(group, uri);

        Box<T> save = getUpdateable().save(group);
        if (save.isSuccess()) {
            return Response.ok(group).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(new Errors(save.mapError(Error::new))).build();
        }

    }

    default void applySaveState(T group, UriInfo uri) {}


}
