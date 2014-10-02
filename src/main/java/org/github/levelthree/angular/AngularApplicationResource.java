package org.github.levelthree.angular;

import org.github.levelthree.Module;
import org.github.levelthree.ModuleRegistry;
import org.github.levelthree.Resource;
import org.github.levelthree.ux.Templating;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by julian3 on 2014/09/20.
 */
@Path("application")
public class AngularApplicationResource {


    @GET
    @Path("modules.js")
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
    @Path("application.js")
    public Response getApplication() {
        final Map<String, Object> modules = new HashMap<>(1,1.0f);
        modules.put("modules", ModuleRegistry.getModules());
        return createTemplateResponse(modules,"application.ftl");
    }


    @GET
    @Path("/{module}/views/{entity}/{type}.html")
    public Response getView(@PathParam("module") String module, @PathParam("entity") String entity, @PathParam("entity") String type) {
        if (!entity.startsWith("/")) {
            entity = "/"+entity;
        }

        Module applicationModule = ModuleRegistry.get(module).orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
        Resource resource = applicationModule.getResource(entity).orElseThrow(()->new WebApplicationException(Response.Status.NOT_FOUND));

        StreamingOutput stream = (output)-> {
            resource.getResourceUx().render(type,output);
        };
        return Response.ok(stream).build();
    }

    @GET
    @Path("/{module}/controllers/{controllerView}.js")
    public Response getControllers(String module, String controller) {
        return null;
    }


    @GET
    @Path("/controllers/{controller}.js")
    public Response getAllControllers() {
        return null;
    }

    @GET
    @Path("/{module}/controllers.js")
    public Response getControllers(String module) {
        return null;
    }

    @GET
    @Path("/{module}/navigation.js")
    public Response getNavigation(@PathParam("module") String module) {

        Module theModule = ModuleRegistry.get(module).orElseThrow(()-> new WebApplicationException(Response.Status.NOT_FOUND));
        final Map<String, Object> modules = new HashMap<>(1,1.0f);
        modules.put("module", theModule);
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
