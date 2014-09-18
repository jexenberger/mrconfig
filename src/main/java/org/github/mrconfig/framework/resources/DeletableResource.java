package org.github.mrconfig.framework.resources;

import org.github.mrconfig.framework.Resource;
import org.github.mrconfig.framework.ResourceRegistry;
import org.github.mrconfig.framework.security.Security;
import org.github.mrconfig.framework.service.Deletable;
import org.github.mrconfig.framework.util.Box;
import org.github.mrconfig.framework.util.GenericsUtil;
import org.github.mrconfig.framework.util.TransformerService;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.Serializable;

import static org.github.mrconfig.framework.resources.Errors.errors;

/**
 * Created by julian3 on 2014/07/18.
 */
public interface DeletableResource<T, K extends Serializable> extends BaseResource {

    Deletable<T,K> getDeletable();

    @DELETE
    @Path("{id}")
    default Response delete(@Context SecurityContext context, @PathParam("id") String id) {

        if (isUserAllowedToDelete(context)) return Response.status(Response.Status.UNAUTHORIZED).build();


        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errors(Error.invalidID(id))).build();
        }




        K convertedId = null;
        try {
            convertedId = TransformerService.convert(id, getResourceIdType());
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errors(Error.invalidID(id))).build();
        }
        Box<T> delete = getDeletable().delete(convertedId);
        if (delete.isSuccess()) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(new Errors(delete.mapError(Error::new))).build();
        }
    }

    default boolean isUserAllowedToDelete(SecurityContext context) {
        Resource resource = ResourceRegistry.get(getPath());
        if (Security.authorized(context, resource.isRequiresAuthentication(), resource.getDeleteRole())) {
            return true;
        }
        return false;
    }

    default Class<K> getResourceIdType() {
        return (Class<K>) GenericsUtil.getClass(this.getClass(), 1);
    }



}
