package org.github.levelthree.angular;

import org.github.levelthree.Module;
import org.github.levelthree.ModuleRegistry;
import org.github.levelthree.ux.Templating;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by julian3 on 2014/09/20.
 */
@Path("/ng-app")
@Produces({"application/javascript"})
public class AngularApplicationResource {


    static Map<String, String> templateMaps = new LinkedHashMap<>();
    static {

        templateMaps.put("edit","edit_form.ftl");
        templateMaps.put("create","edit_form.ftl");
        templateMaps.put("list","list_form.ftl");
    }




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
        return Response.ok().type(MediaType.TEXT_HTML_TYPE).entity(stream).build();
    }

    @GET
    @Path("/application.js")
    public Response getApplication() {

        AngularUXModule module = getModule();

        StreamingOutput stream = (output)-> {
            module.renderApplication(output);
        };
        return Response.ok(stream).build();
    }

    public AngularUXModule getModule() {
        return (AngularUXModule) ModuleRegistry.get(AngularUXModule.class.getSimpleName()).orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }


    @GET
    @Path("/{module}/{entity}/{type}")
    @Produces({MediaType.TEXT_HTML})
    public Response getView(@PathParam("module") String module, @PathParam("entity") String entity, @PathParam("type") String type) {
        return getView(module, entity, type, false);
    }


    @GET
    @Path("/{module}/{entity}/{type}/{p_id}")
    @Produces({MediaType.TEXT_HTML})
    public Response getView(@PathParam("module") String module, @PathParam("entity") String entity, @PathParam("type") String type, String id) {
        return getView(module, entity, type, true);
    }

    private Response getView(String module, String entity, String type, boolean isIdParameter) {
        if (!entity.startsWith("/")) {
            entity = "/" + entity;
        }

        AngularUXModule applicationModule = getModule();
        AngularUXComponent uxComponent = applicationModule.resolveComponent(module, entity, type, isIdParameter);
        return createTemplateResponse(uxComponent, templateMaps.get(type));
    }

    @GET
    @Path("/{module}/controllers/{setController}.js")
    public Response getControllers(@PathParam("module") String module, @PathParam("setController") String controller) {
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
