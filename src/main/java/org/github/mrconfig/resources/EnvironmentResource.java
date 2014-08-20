package org.github.mrconfig.resources;

import org.github.mrconfig.domain.Environment;
import org.github.mrconfig.framework.activerecord.ActiveRecordCRUDService;
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
public class EnvironmentResource implements ReadableResource<Environment,String> {


    public EnvironmentResource() {
    }

    @Override
    public Class getResourceIdType() {
        return String.class;
    }

    @Override
    public Listable<Environment> getListableResource() {
        return new ActiveRecordCRUDService(Environment.class);
    }

    @Override
    public UniqueLookup getLookup() {
        return new ActiveRecordCRUDService(Environment.class);
    }

}
