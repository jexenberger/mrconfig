package org.github.mrconfig.resources;

import org.github.mrconfig.domain.EnvironmentGroup;
import org.github.levelthree.resources.BaseCRUDResource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by julian3 on 2014/07/17.
 */
@Path("/environmentgroups")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class EnvironmentGroupResource extends BaseCRUDResource<EnvironmentGroup,String>{



}
