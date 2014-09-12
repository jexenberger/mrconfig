package org.github.mrconfig.framework.resources;

import org.github.mrconfig.framework.ResourceRegistry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by julian3 on 2014/09/11.
 */
@Path("/roles")
public class RolesResources {

    @GET
    public Response get(@Context SecurityContext context) {

        return Response.ok().entity(getRoles(context)).build();

    }

    public Roles getRoles(SecurityContext context) {
        return new Roles(ResourceRegistry.getAllRoles());
    }

}
