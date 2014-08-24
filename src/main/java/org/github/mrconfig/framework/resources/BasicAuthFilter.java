package org.github.mrconfig.framework.resources;

import org.glassfish.jersey.internal.util.Base64;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;

/**
 * Created by julian3 on 2014/08/24.
 */
@Provider
public class BasicAuthFilter implements ContainerRequestFilter{




    @Override
    public void filter(ContainerRequestContext containerRequest) throws IOException {
        String method = containerRequest.getMethod();
        // myresource/get/56bCA for example
        String path = containerRequest.getUriInfo().getPath();

        //We do allow wadl to be retrieve
        if(method.equals("GET") && path.startsWith("static") || path.startsWith("views") || path.endsWith("wadl") || path.endsWith("xsd") || path.endsWith("html")){
            return;
        }

        String auth = containerRequest.getHeaderString("authorization");
        if(auth == null){
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
        //get rid of  'Basic' prefix
        auth = auth.split(" ")[1].trim();
        byte[] decode = Base64.decode(auth.getBytes());



        String authString = new String(decode);
        String[] authParts = authString.split(":");
        String userName = authParts[0];
        String password = authParts[1];

        final UserPrincipal principal = authenticate(userName, password);
        containerRequest.setSecurityContext(principal);

    }

    private UserPrincipal authenticate(String userName, String password) {
        return new UserPrincipal(userName, "admin");
    }
}
