package org.github.mrconfig.framework.resources;

import org.github.mrconfig.framework.activerecord.Link;
import org.github.mrconfig.domain.BaseEntity;
import org.github.mrconfig.framework.activerecord.TransformerService;
import org.github.mrconfig.framework.service.Creatable;
import org.github.mrconfig.framework.service.UniqueLookup;
import org.github.mrconfig.framework.service.Updateable;
import org.github.mrconfig.framework.util.Box;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.Serializable;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;

import static org.github.mrconfig.domain.KeyEntity.resolveByKeyOrId;
import static org.github.mrconfig.framework.activerecord.ActiveRecord.doWork;
import static org.github.mrconfig.framework.resources.ResourceUtil.getResourcePath;

/**
 * Created by julian3 on 2014/07/18.
 */
public interface WritableResource<T, K extends Serializable> {


    @POST
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    default Response create(T instance) {

        Creatable<T, K> service = getCreatable();
        Box<K> result = service.create(instance);
        if (result.isSuccess()) {
            String urn = ResourceUtil.getResourcePath(getClass()) + "/" + result.get();
            UriBuilder uriBuilder = UriBuilder.fromUri(urn);
            URI build = uriBuilder.build();
            return Response.created(build).entity(instance).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(new Errors(result.mapError(Error::new))).build();
        }
    }

    Creatable<T, K> getCreatable();

    Updateable<T> getUpdateable();



    @PUT
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    default Response save(@PathParam("id") String id, T group) {
        Box<T> save = getUpdateable().save(group);
        if (save.isSuccess()) {
            return Response.ok(group).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(new Errors(save.mapError(Error::new))).build();
        }

    }

}
