package org.github.mrconfig.resources;

import org.github.mrconfig.domain.EnvironmentGroup;
import org.github.mrconfig.framework.resources.BaseCRUDResource;
import org.github.mrconfig.framework.resources.DeletableResource;
import org.github.mrconfig.framework.resources.WritableResource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by julian3 on 2014/07/17.
 */
@Path("/environmentgroups")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class EnvironmentGroupResource extends BaseCRUDResource<EnvironmentGroup,Long>{



}
