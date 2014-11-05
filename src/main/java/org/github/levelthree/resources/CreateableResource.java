package org.github.levelthree.resources;

import org.github.levelthree.Resource;
import org.github.levelthree.ResourceRegistry;
import org.github.levelthree.security.Security;
import org.github.levelthree.service.Creatable;
import org.github.levelthree.util.Box;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.io.Serializable;
import java.net.URI;
import java.util.Optional;

/**
 * Created by julian3 on 2014/10/24.
 */
public interface CreateableResource <T, K extends Serializable> extends BaseResource<T>{

    @POST
    default Response create(@Context SecurityContext context, T instance, @Context UriInfo uri, @Context HttpHeaders headers) {


        if (!isUserAllowedToCreate(context)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Optional<MediaType> mediaType = resolveMediaType(headers.getMediaType());
        if (!mediaType.isPresent()) {
            return Response.status(Response.Status.UNSUPPORTED_MEDIA_TYPE).build();
        }



        Creatable<T, K> service = getCreatable();
        Box<K> result = service.create(instance);
        if (result.isSuccess()) {
            String urn = ResourceRegistry.getByResourceClass(getClass()) + "/" + result.get();
            UriBuilder uriBuilder = UriBuilder.fromUri(urn);
            URI build = uriBuilder.build();
            populatePostCreationLinks(instance);
            return Response.created(build).type(mediaType.get()).entity(instance).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).type(mediaType.get()).entity(new Errors(result.mapError(Error::new))).build();
        }
    }

    default void populatePostCreationLinks(T instance) {

    }

    Creatable<T, K> getCreatable();



    default boolean isUserAllowedToCreate(SecurityContext context) {
        Resource resource = ResourceRegistry.get(getPath());
        return Security.authorized(context, resource.isRequiresAuthentication(), resource.getCreateRole());
    }



}
