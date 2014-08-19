package org.github.mrconfig.resources;

import org.github.mrconfig.domain.Environment;
import org.github.mrconfig.domain.Template;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Optional;

import static org.github.mrconfig.framework.activerecord.ActiveRecord.findById;


/**
 * Created by julian3 on 2014/07/19.
 */
@Path("/files")
public class FileResource {


    @GET
    @Path("{template}/{environment}/{fileName}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response generateFile(@PathParam("template") String template, @PathParam("environment") String environment, @PathParam("fileName") String fileName) {

        final Optional<Template> targetTemplate = findById(Template.class, template);
        if (!targetTemplate.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).entity(template+" not found").build();
        }

        final Optional<Environment> targetEnvironment = findById(Environment.class, environment);
        if (!targetEnvironment.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).entity(environment+" not found").build();
        }

        StreamingOutput stream = output -> targetTemplate.get().generate(targetEnvironment.get(), new OutputStreamWriter(output));

        return Response.ok(stream).build();
    }

}
