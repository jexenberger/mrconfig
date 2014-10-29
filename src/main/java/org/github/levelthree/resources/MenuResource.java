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
        throw new IllegalArgumentException("must fix");

        /*
        Collection<ResourceUX> forms = ResourceRegistry.list()
                .stream()
                .filter((setResource)-> setResource.getResourceUx() != null)
                .map((setResource)-> setResource.getResourceUx())
                .collect(toList());

        Menu response = new Menu();
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

        }

        return response;*/
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
