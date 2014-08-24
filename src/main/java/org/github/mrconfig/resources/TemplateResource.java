package org.github.mrconfig.resources;

import org.github.mrconfig.domain.Template;
import org.github.mrconfig.framework.resources.DeletableResource;
import org.github.mrconfig.framework.resources.ReadableResource;
import org.github.mrconfig.framework.resources.WritableResource;
import org.github.mrconfig.framework.service.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by julian3 on 2014/07/19.
 */
@Path("/templates")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class TemplateResource implements ReadableResource<Template,Long>, WritableResource<Template,Long>, DeletableResource<Template,Long> {
    @Override
    public Deletable<Template, Long> getDeletable() {
        return null;
    }

    @Override
    public Class<Long> getResourceIdType() {
        return null;
    }

    @Override
    public Listable<Template> getListableResource() {
        return null;
    }

    @Override
    public Creatable<Template, Long> getCreatable() {
        return null;
    }

    @Override
    public Updateable<Template,Long> getUpdateable() {
        return null;
    }

    @Override
    public UniqueLookup<Template, Long> getLookup() {
        return null;
    }

}
