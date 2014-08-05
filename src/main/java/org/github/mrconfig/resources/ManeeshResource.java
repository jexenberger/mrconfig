package org.github.mrconfig.resources;

import org.github.mrconfig.domain.ManeeshDemo;
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
 * Created by w1428134 on 2014/08/01.
 */
@Path("/maneeshdemos")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ManeeshResource extends BaseCRUDResource<ManeeshDemo,Long> {

}
