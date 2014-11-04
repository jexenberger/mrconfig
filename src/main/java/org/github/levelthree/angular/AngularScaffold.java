package org.github.levelthree.angular;

import org.github.levelthree.Module;
import org.github.levelthree.ModuleRegistry;
import org.github.levelthree.Resource;
import org.github.levelthree.ResourceRegistry;
import org.github.levelthree.util.StringUtil;
import org.github.levelthree.ux.form.Form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.github.levelthree.angular.AngularService.service;
import static org.github.levelthree.ux.TemplateView.templateView;

/**
 * Created by w1428134 on 2014/10/29.
 */
public class AngularScaffold {



    public static Collection<AngularUXComponent> scaffold(Module parent, Resource resource, Supplier<Form> formSupplier) {
        Collection<AngularUXComponent> components = new ArrayList<>();
        String moduleName = parent != null ? parent.getName() : ModuleRegistry.DEFAULT_MODULE;
        AngularService service = service(moduleName + "Service", resource.getPath());

        if (resource.getListable() != null) {
            components.add(createComponent(parent.getName(), resource, "list", service, formSupplier));
        }
        if (resource.getUniqueLookup() != null) {
            components.add(createComponent(parent.getName(), resource, "view", service, formSupplier));
        }
        if (resource.getCreatable() != null) {
            components.add(createComponent(parent.getName(), resource, "create", service, formSupplier));
        }
        if (resource.getUpdateable() != null) {
            components.add(createComponent(parent.getName(), resource, "edit", service, formSupplier));
        }
        return components;
    }

    public static AngularUXComponent createComponent(String moduleName, Resource resource, String componentType, AngularService service, Supplier<Form> form) {
        AngularUXComponent component = new AngularUXComponent();
        String path = getRoutePath(moduleName, resource, componentType,"");
        //a bit of a hack
        String templateType = "edit";
        if (componentType.equals("list")){
            templateType = "list";
        }
        component.setPath(path)
                .setTemplateUrl(path)
                .setControllerView(templateView(templateType + "_controller.ftl"))
                .setControllerName(resource.getName() + StringUtil.capitalize(templateType) + "Controller")
                .setForm(form)
                .setModule(moduleName)
                .setService(service);
        return component;
    }

    public static String getRoutePath(String moduleName, Resource resource, final String type, String param) {
        return resource.getPath()+ "/" + type + ((param != null) ? ":"+param : "" );
    }

    public static String getTemplatePath(String moduleName, Resource resource, final String type) {
        return resource.getPath()+ "/" + type + ".html";
    }


}
