package org.github.mrconfig.framework.resources;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.github.mrconfig.framework.Resource;
import org.github.mrconfig.framework.ResourceRegistry;
import org.github.mrconfig.framework.macro.angular.TemplateEngine;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Created by julian3 on 2014/07/20.
 */
@Path("/views")
public class FormResource {


    @Path("{entity}/{type}.html")
    @Produces(MediaType.TEXT_HTML)
    @GET
    public Response getView(@PathParam("entity") String entity, @PathParam("type")  String type, @QueryParam("key") String key) {


        if (!entity.startsWith("/")) {
            entity = "/"+entity;
        }
        Resource resource = ResourceRegistry.get(entity);
        if (resource == null || resource.getUxModule() == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(entity+" not found").build();
        }

        StreamingOutput stream = (output)-> {
            resource.getUxModule().render(type,output);
        };
        return Response.ok(stream).build();
    }


    @Path("{type}.js")
    @Produces("application/javascript")
    @GET
    public Response getControllerCode(@PathParam("type") String type) {
        StreamingOutput stream = (output)-> {

            List<Resource> resources = ResourceRegistry.list().stream().filter((resource) -> resource.getUxModule() != null).collect(toList());

            Template template = TemplateEngine.getConfiguration().getTemplate(type+".ftl");
            Map<String, List<Resource>> modules = new HashMap<>();
            modules.put("resources",resources);
            try {
                template.process(modules, new OutputStreamWriter(output));
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        };
        return Response.ok(stream).build();
    }

}
