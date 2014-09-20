package org.github.mrconfig.resources;

import org.github.mrconfig.domain.DataCentre;
import org.github.levelthree.resources.BaseCRUDResource;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by w1428134 on 2014/08/29.
 */
@Path("/datacentres")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class DataCentreResource extends BaseCRUDResource<DataCentre,Long> {
}
