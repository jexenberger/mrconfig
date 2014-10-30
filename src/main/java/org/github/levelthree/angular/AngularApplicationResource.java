package org.github.levelthree.angular;

import org.github.levelthree.Module;
import org.github.levelthree.ModuleRegistry;
import org.github.levelthree.Resource;
import org.github.levelthree.ResourceUX;
import org.github.levelthree.ux.Templating;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by julian3 on 2014/09/20.
 */
@Path("/ng-app")
@Produces({"application/javascript"})
public class AngularApplicationResource {


    @GET
    @Path("/modules.js")
    public Response loadModules() {

        final Map<String, Object> modules = new HashMap<>(1,1.0f);
        modules.put("modules", ModuleRegistry.getModules());

        return createTemplateResponse(modules, "load_modules.ftl");
    }

    private Response createTemplateResponse(Object model, String templateName) {
        StreamingOutput stream = (output)-> {
            Templating.getTemplating().write(templateName,model,output);
        };
        return Response.ok(stream).build();
    }

    @GET
    @Path("/application.js")
    public Response getApplication() {
        final Map<String, Object> modules = new HashMap<>(1,1.0f);
        modules.put("modules", ModuleRegistry.getModules());
        return createTemplateResponse(modules,"application_declaration.ftl");
    }


    @GET
    @Path("/{setModule}/views/{entity}/{type}.html")
    @Produces({MediaType.TEXT_HTML})
    public Response getView(@PathParam("setModule") String module, @PathParam("entity") String entity, @PathParam("type") String type) {
        if (!entity.startsWith("/")) {
            entity = "/"+entity;
        }

        Module applicationModule = ModuleRegistry.get(module).orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
        Resource resource = applicationModule.getResource(entity).orElseThrow(()->new WebApplicationException(Response.Status.NOT_FOUND));

        StreamingOutput stream = (output)-> {
            throw new IllegalArgumentException("need to implement in "+AngularApplicationResource.class.getName());
            /*
            AngularResourceUX resourceUx = (AngularResourceUX) ux;
            try {
                AngularUXComponent component = resourceUx.getComponentByType(type);
                resourceUx.getForm().getUxContext().put(type, component.getControllerName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            resourceUx.render(type, output);
            */
        };
        return Response.ok(stream).build();
    }

    @GET
    @Path("/{setModule}/controllers/{setController}.js")
    public Response getControllers(@PathParam("setModule") String module, @PathParam("setController") String controller) {
        return null;
    }


    @GET
    @Path("/controllers/{setController}.js")
    public Response getAllControllers() {
        return null;
    }

    @GET
    @Path("/{setModule}/controllers.js")
    public Response getControllers(@PathParam("setModule") String module) {
        return null;
    }

    @GET
    @Path("/{setModule}/navigation.js")
    public Response getNavigation(@PathParam("setModule") String module) {

        Module theModule = ModuleRegistry.get(module).orElseThrow(()-> new WebApplicationException(Response.Status.NOT_FOUND));
        final Map<String, Object> modules = new HashMap<>(1,1.0f);
        modules.put("setModule", theModule);
        return createTemplateResponse(modules, "module_navigation.ftl");
    }

    @GET
    @Path("/navigation.js")
    public Response getAllNavigation() {
        final Map<String, Object> modules = new HashMap<>(1,1.0f);
        modules.put("modules", ModuleRegistry.getModules());

        return createTemplateResponse(modules, "all_navigation.ftl");

    }


}
