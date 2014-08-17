package org.github.mrconfig.framework.resources;

import org.github.mrconfig.framework.ResourceRegistry;
import org.github.mrconfig.framework.UXModule;
import org.github.mrconfig.framework.ux.form.Form;
import org.github.mrconfig.framework.macro.FormRegistry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
    public Menu getMenu() {
        Collection<UXModule> forms = ResourceRegistry.list()
                .stream()
                .filter((resource)-> resource.getUxModule() != null)
                .map((resource)-> resource.getUxModule())
                .collect(toList());
        Menu response = new Menu();
        for (UXModule form : forms) {

            initGroup(response,form.getGroup());
            response.getMenuGroups().get(form.getGroup()).add(new MenuItem(form.getName(), form.getKey(), form.getLink()));
        }

        return response;
    }

    private void initGroup(Menu response, String group) {
        if (response.getMenuGroups().containsKey(group)) {
            return;
        }
        List<MenuItem> menuItems = new ArrayList<>();
        response.getMenuGroups().put(group,menuItems);
    }

}
