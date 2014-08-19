package org.github.mrconfig.resources;

import org.github.mrconfig.framework.util.Pair;
import org.github.mrconfig.domain.Environment;
import org.github.mrconfig.domain.Property;
import org.github.mrconfig.domain.PropertyValue;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;
import java.util.Properties;

import static org.github.mrconfig.framework.activerecord.ActiveRecord.doWork;
import static org.github.mrconfig.framework.activerecord.ActiveRecord.findById;
import static org.github.mrconfig.framework.activerecord.ActiveRecord.findWhere;
import static org.github.mrconfig.framework.activerecord.Parameter.p;
import static org.github.mrconfig.domain.Property.fromKeyValue;

/**
 * Created by julian3 on 2014/07/18.
 */
@Path("/properties-import")
public class PropertiesImportResource {


    @Path("{id}")
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public Response importProperties(@PathParam("id") String environmentKey,  @FormDataParam("fileUpload") InputStream file) {
        Properties props = new Properties();
        try {
            props.load(file);
            doWork(()-> {
                Optional<Environment> result = findById(Environment.class, environmentKey);
                if (!result.isPresent()) {
                    return Response.status(404).entity(environmentKey + " not found").build();
                }
                props.forEach((key, value) -> {
                    Optional<Property> byKey = findById(Property.class, key.toString().trim());
                    Pair<Property, PropertyValue> propAndVal = fromKeyValue(byKey.get(), (String) key, (String) value, result.get());
                    if (!byKey.isPresent()) {
                        propAndVal.getCar().save();
                    }
                    Collection<PropertyValue> values = findWhere(PropertyValue.class, p("property", propAndVal.getCar().getId()), p("environment", result.get().getId()));
                    if (values.size() == 0) {
                        propAndVal.getCdr().save();
                    }
                });
                return null;
            });

        } catch (IOException e) {
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

}
