package org.github.levelthree.resources;

import org.github.levelthree.ResourceRegistry;
import org.github.levelthree.ResourceUX;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by w1428134 on 2014/08/01.
 */
@Path("/menus")
@Produces(MediaType.APPLICATION_JSON)
public class MenuResource {

    @GET
    public Menu getMenu(@Context SecurityContext context) {
        Collection<ResourceUX> forms = ResourceRegistry.list()
                .stream()
                .filter((resource)-> resource.getResourceUx() != null)
                .map((resource)-> resource.getResourceUx())
                .collect(toList());
        Menu response = new Menu();
        for (ResourceUX form : forms) {
            initGroup(response, form.getGroup());
            boolean allow = allowAccess(context, form.getResource().getListRole());
            if (allow) {
                response.getMenuGroups().get(form.getGroup()).add(new MenuItem(form.getName()+" Search", form.getKey()+".search", form.getListLink()));
            }
            allow = allowAccess(context, form.getResource().getCreateRole());
            if (allow) {
                response.getMenuGroups().get(form.getGroup()).add(new MenuItem(form.getName()+" New", form.getKey()+".new",  form.getCreateLink()));
            }

        }

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
