package org.github.mrconfig.resources;

import org.github.mrconfig.domain.User;
import org.github.mrconfig.framework.resources.BaseCRUDResource;
import org.github.mrconfig.framework.resources.DeletableResource;
import org.github.mrconfig.framework.resources.ReadableResource;
import org.github.mrconfig.framework.resources.WritableResource;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by julian3 on 2014/07/18.
 */
@Path("/users")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class UserResource extends BaseCRUDResource<User,Long> {

}
