package org.github.mrconfig.resources;

import org.github.mrconfig.domain.Environment;
import org.github.mrconfig.framework.resources.ReadableResource;
import org.github.mrconfig.framework.service.Listable;
import org.github.mrconfig.framework.service.UniqueLookup;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by julian3 on 2014/07/17.
 */
@Path("/environments")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class EnvironmentResource implements ReadableResource<Environment,Long> {


    public EnvironmentResource() {
    }

    @Override
    public Class getResourceTypeId() {
        return null;
    }

    @Override
    public Listable<Environment> getListableResource() {
        return null;
    }

    @Override
    public UniqueLookup getLookup() {
        return null;
    }

}
