package org.github.mrconfig.framework.resources;

import org.github.mrconfig.domain.TestObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by julian3 on 2014/08/23.
 */
@Path("/testobjects")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class TestObjectResource extends BaseCRUDResource<TestObject,Long> {
}
