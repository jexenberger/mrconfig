package org.github.mrconfig.resources;

import org.github.mrconfig.domain.AdminGroup;
import org.github.mrconfig.framework.resources.*;
import org.github.mrconfig.framework.service.Creatable;
import org.github.mrconfig.framework.service.Listable;
import org.github.mrconfig.framework.service.UniqueLookup;
import org.github.mrconfig.framework.service.Updateable;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by julian3 on 2014/07/18.
 */
@Path("/admingroups")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class AdminGroupResource extends BaseCRUDResource<AdminGroup,Long> {


}
