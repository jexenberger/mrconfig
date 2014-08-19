package org.github.mrconfig.framework.resources;

import org.github.mrconfig.framework.activerecord.ProviderFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by julian3 on 2014/08/18.
 */
@Provider
public class SessionInViewInterceptor implements ContainerRequestFilter, ContainerResponseFilter {

    String uuid = null;

    public SessionInViewInterceptor() {
        uuid = UUID.randomUUID().toString();

    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        System.out.println(uuid);
        ProviderFactory.getProvider().startTransaction();
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        System.out.println(uuid);
        ProviderFactory.getProvider().commitOrRollback(true);

    }
}
