package org.github.mrconfig.framework.macro;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.github.mrconfig.framework.macro.angular.TemplateEngine;
import org.github.mrconfig.framework.ux.Form;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.OutputStreamWriter;

/**
 * Created by julian3 on 2014/07/20.
 */
@Path("/views")
public class FormResource {


    @Path("{entity}/{type}.html")
    @Produces(MediaType.TEXT_HTML)
    @GET
    public Response getView(@PathParam("entity") String entity, @PathParam("type")  String type, @QueryParam("key") String key) {

        final Form form = FormRegistry.get().getForm(entity);
        if (form == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(entity+" not found").build();
        }
        StreamingOutput stream = (output)-> {
            try {
                Template template = TemplateEngine.getConfiguration().getTemplate(type + "_form.ftl");
                template.process(form, new OutputStreamWriter(output));
            } catch (Exception e) {
                e.printStackTrace();

            }
        };
        return Response.ok(stream).build();
    }


    @Path("{type}.js")
    @Produces("application/javascript")
    @GET
    public Response getControllerCode(@PathParam("type") String type) {
        StreamingOutput stream = (output)-> {
            Template template = TemplateEngine.getConfiguration().getTemplate(type+".ftl");
            try {
                template.process(FormRegistry.get(), new OutputStreamWriter(output));
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        };
        return Response.ok(stream).build();
    }

}
