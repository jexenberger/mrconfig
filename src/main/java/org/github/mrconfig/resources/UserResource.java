package org.github.mrconfig.resources;

import org.github.mrconfig.domain.User;
import org.github.mrconfig.domain.UserState;
import org.github.levelthree.resources.BaseCRUDResource;
import org.github.levelthree.service.Link;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 * Created by julian3 on 2014/07/18.
 */
@Path("/users")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class UserResource extends BaseCRUDResource<User, String> {


    @Override
    public void populateGetLinks(User entity) {
        if (UserState.Frozen.equals(entity.getState()) || UserState.Pending.equals(entity.getState())) {
            entity.getLinks().add(new Link("self:PUT", null, getPath()+"?state="+UserState.Active.name(), "Activate User"));
        } else {
            entity.getLinks().add(new Link("self", null, getPath()+"?state="+UserState.Frozen.name(), "Freeze User"));
        }
    }

    @Override
    public void applySaveState(User group, UriInfo uri) {
        if (uri.getQueryParameters().containsKey("state")) {
            UserState state = UserState.valueOf(uri.getQueryParameters().getFirst("state"));
            group.setState(state);
        }
    }
}
