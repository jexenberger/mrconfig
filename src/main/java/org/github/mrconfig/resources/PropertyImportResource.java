package org.github.mrconfig.resources;

import org.github.levelthree.resources.CreateableResource;
import org.github.levelthree.service.Creatable;
import org.github.levelthree.service.Link;
import org.github.levelthree.util.Box;

import javax.ws.rs.Path;

/**
 * Created by julian3 on 2014/10/24.
 */
@Path("/property-import")
public class PropertyImportResource  implements CreateableResource<PropertyImport,Long>, Creatable<PropertyImport, Long> {


    @Override
    public Box<Long> create(PropertyImport instance) {
        return null;
    }

    @Override
    public Link toLink(PropertyImport instance) {
        return null;
    }

    @Override
    public Creatable<PropertyImport, Long> getCreatable() {
        return this;
    }
}
