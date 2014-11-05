package org.github.levelthree.resources;

import org.github.levelthree.activerecord.ProviderFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.UUID;

import static org.github.levelthree.resources.Errors.errors;

/**
 * Created by julian3 on 2014/08/18.
 */
@Provider
public class SessionInViewInterceptor implements ContainerRequestFilter, ContainerResponseFilter,ExceptionMapper<Exception>  {

    String uuid = null;

    public SessionInViewInterceptor() {
        uuid = UUID.randomUUID().toString();

    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        ProviderFactory.getProvider().startTransaction();
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        ProviderFactory.getProvider().commitOrRollback(true);

    }


    @Override
    public Response toResponse(Exception e) {
        e.printStackTrace();
        if (e instanceof WebApplicationException) {
            return ((WebApplicationException) e).getResponse();
        }

        ProviderFactory.getProvider().commitOrRollback(false);
        return Response.serverError().entity(errors(Error.generalFailure(e))).build();
    }
}
