package org.github.mrconfig.resources;

import org.github.mrconfig.domain.PropertyValue;
import org.github.levelthree.resources.BaseCRUDResource;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by julian3 on 2014/07/19.
 */
@Path("/propertyvalues")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class PropertyValueResource extends BaseCRUDResource<PropertyValue,Long> {
}