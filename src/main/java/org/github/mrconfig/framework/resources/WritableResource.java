package org.github.mrconfig.framework.resources;

import org.github.mrconfig.framework.service.Creatable;
import org.github.mrconfig.framework.service.Updateable;
import org.github.mrconfig.framework.util.Box;
import org.github.mrconfig.framework.util.GenericsUtil;
import org.github.mrconfig.framework.util.TransformerService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.Serializable;
import java.net.URI;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static org.github.mrconfig.framework.resources.Error.invalidID;
import static org.github.mrconfig.framework.resources.Error.notFound;
import static org.github.mrconfig.framework.resources.Errors.errors;

/**
 * Created by julian3 on 2014/07/18.
 */
public interface WritableResource<T, K extends Serializable> {


    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    default Response create(@Context SecurityContext context, T instance) {

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

    Updateable<T,K> getUpdateable();

    default Class<K> getResourceIdType() {
        return (Class<K>) GenericsUtil.getClass(this.getClass(), 1);
    }



    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    default Response save(@Context SecurityContext context, T group) {


        Box<T> save = getUpdateable().save(group);
        if (save.isSuccess()) {
            return Response.ok(group).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(new Errors(save.mapError(Error::new))).build();
        }

    }

}
