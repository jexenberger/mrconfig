package org.github.levelthree.security;

import org.github.levelthree.resources.UserPrincipal;
import org.glassfish.jersey.internal.util.Base64;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static org.github.levelthree.security.Security.hashAsHex;

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


        String auth = containerRequest.getHeaderString("authorization");
        if(auth == null){
            //we allow the call through to then be appropriately handled in the application
            return;
        }
        //reject an empty header
        if (auth.trim().equals("")) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
        //get rid of  'Basic' prefix
        String[] authPieces = auth.split(" ");
        if (authPieces.length != 2) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
        auth = authPieces[1].trim();
        byte[] decode = Base64.decode(auth.getBytes());



        String authString = new String(decode);
        String[] authParts = authString.split(":");
        //if the array is empty fail
        if (authParts.length == 0) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
        String userName = authParts[0];
        String password = (authParts.length > 1) ? authParts[1] : null;

        final UserPrincipal principal = authenticate(userName, password);
        containerRequest.setSecurityContext(principal);

    }

    private UserPrincipal authenticate(String userName, String password) {
        final String passwordHash = (password != null) ? hashAsHex(password, Security.getDefaultHashingAlgorithm()) : null;
        Optional<UserPrincipal> user = Security.getUser(userName);
        user.ifPresent((userPrincipal)-> {
            if (!Objects.equals(userPrincipal.getPassword(), passwordHash)) {
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            }
        });
        return user.orElseThrow(() -> new WebApplicationException(Response.Status.UNAUTHORIZED));
    }
}
