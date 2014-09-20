package org.github.levelthree.resources;

import org.github.levelthree.Resource;
import org.github.levelthree.ResourceRegistry;
import org.github.levelthree.security.Security;
import org.github.levelthree.service.Creatable;
import org.github.levelthree.service.Updateable;
import org.github.levelthree.util.Box;
import org.github.levelthree.util.GenericsUtil;

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


        if (!isUserAllowedToCreate(context)) {
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
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    default Response save(@Context SecurityContext context, @PathParam("id") String id, T group, @Context UriInfo uri) {


        if (!isUserAllowedToSave(context)) return Response.status(Response.Status.UNAUTHORIZED).build();

        applySaveState(group, uri);

        Box<T> save = getUpdateable().save(group);
        if (save.isSuccess()) {
            return Response.ok(group).links(getUpdateable().toLink(group)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(new Errors(save.mapError(Error::new))).build();
        }

    }

    default boolean isUserAllowedToSave(SecurityContext context) {
        Resource resource = ResourceRegistry.get(getPath());
        return Security.authorized(context, resource.isRequiresAuthentication(), resource.getUpdateRole());
    }

    default boolean isUserAllowedToCreate(SecurityContext context) {
        Resource resource = ResourceRegistry.get(getPath());
        return Security.authorized(context, resource.isRequiresAuthentication(), resource.getCreateRole());
    }

    default boolean isAllowParameterOverride() {
        return true;
    }

    default void applySaveState(T group, UriInfo uri) {}


}
