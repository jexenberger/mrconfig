package org.github.levelthree.resources;

import org.github.levelthree.Module;
import org.github.levelthree.ModuleRegistry;
import org.github.levelthree.ResourceRegistry;
import org.github.levelthree.ResourceUX;
import org.github.levelthree.angular.AngularUXComponent;
import org.github.levelthree.angular.AngularUXModule;
import org.github.levelthree.ux.form.Form;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by w1428134 on 2014/08/01.
 */
@Path("/menus")
@Produces(MediaType.APPLICATION_JSON)
public class MenuResource {

    @GET
    public Menu getMenu(@Context SecurityContext context) {

        Menu response = new Menu();
        /*
        Collection<ResourceUX> forms = ResourceRegistry.list()
                .stream()
                .filter((setResource)-> setResource.getResourceUx() != null)
                .map((setResource)-> setResource.getResourceUx())
                .collect(toList());

        */
        Map<String, Module> modules = ModuleRegistry.getModules();
        Optional<Module> module = ModuleRegistry.get(AngularUXModule.class.getSimpleName());
        AngularUXModule uxModule = (AngularUXModule) module.get();
        Map<String, Set<AngularUXComponent>> allComponents = uxModule.allComponents();
        allComponents.forEach((key, value)-> {
            initGroup(response, key);
            value.forEach((component)-> {
                /*
                Form form = component.getForm();
                boolean allow = allowAccess(context, form.getResource().getListRole());
                if (allow) {
                    response.getMenuGroups().get(form.getGroup()).add(new MenuItem(form.getName()+" Search", form.getKey()+".search", form.getListLink()));
                }
                allow = allowAccess(context, form.getResource().getCreateRole());
                if (allow) {
                    response.getMenuGroups().get(form.getGroup()).add(new MenuItem(form.getName()+" New", form.getKey()+".new",  form.getCreateLink()));
                }*/
            });
        });
        /*
        for (String module : allComponents) {
            response.getMenuGroups().get(module);

        }
        /*
        for (ResourceUX setForm : forms) {
            initGroup(response, setForm.getGroup());
            boolean allow = allowAccess(context, setForm.getResource().getListRole());
            if (allow) {
                response.getMenuGroups().get(setForm.getGroup()).add(new MenuItem(setForm.getName()+" Search", setForm.getKey()+".search", setForm.getListLink()));
            }
            allow = allowAccess(context, setForm.getResource().getCreateRole());
            if (allow) {
                response.getMenuGroups().get(setForm.getGroup()).add(new MenuItem(setForm.getName()+" New", setForm.getKey()+".new",  setForm.getCreateLink()));
            }

        }*/

        return response;
    }

    public boolean allowAccess(SecurityContext context, String role) {
        boolean allow = role == null;
        if (role != null && context.isUserInRole(role)) {
            allow = true;
        }
        return allow;
    }

    private void initGroup(Menu response, String group) {
        if (response.getMenuGroups().containsKey(group)) {
            return;
        }
        List<MenuItem> menuItems = new ArrayList<>();
        response.getMenuGroups().put(group,menuItems);
    }

}
