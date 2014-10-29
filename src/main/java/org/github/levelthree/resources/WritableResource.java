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
public interface WritableResource<T, K extends Serializable> extends BaseResource, CreateableResource<T,K>{





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


    default boolean isAllowParameterOverride() {
        return true;
    }

    default void applySaveState(T group, UriInfo uri) {}


}
