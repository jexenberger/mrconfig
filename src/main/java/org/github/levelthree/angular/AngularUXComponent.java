package org.github.levelthree.angular;

import org.github.levelthree.ModuleRegistry;
import org.github.levelthree.Resource;
import org.github.levelthree.ResourceRegistry;
import org.github.levelthree.ux.View;
import org.github.levelthree.ux.form.Form;

import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.github.levelthree.util.Pair.cons;
import static org.github.levelthree.util.Pair.map;
import static org.github.levelthree.ux.TemplateView.templateView;

/**
 * Created by julian3 on 2014/09/21.
 */
public class AngularUXComponent  {


    private String routePath;
    private String templateUrl;
    private String controllerName;
    private View template;
    private View controllerView;
    private View navigationView;
    private AngularService service;
    private String module;
    private Supplier<Form> form;
    private Map<String, AngularUXComponent> relations;



    public AngularUXComponent() {
        navigationView = templateView("component_navigation.ftl");
        module = ModuleRegistry.DEFAULT_MODULE;
    }

    public AngularUXComponent(String routePath, String templateUrl, String controllerName, View template, View controllerView, View navigationView) {
        this.routePath = routePath;
        this.templateUrl = templateUrl;
        this.controllerName = controllerName;
        this.controllerView = controllerView;
        this.navigationView = navigationView;
        this.template = template;
    }

    public AngularUXComponent setModule(String module) {
        this.module = module;
        return this;
    }

    public AngularUXComponent setTemplate(View template) {
        this.template = template;
        return this;
    }

    public AngularUXComponent setPath(String routePath) {
        this.routePath = routePath;
        return this;
    }

    public AngularUXComponent setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
        return this;
    }

    public AngularUXComponent setForm(Supplier<Form> form) {
        this.form = form;
        return this;
    }


    public AngularUXComponent setController(String name, View controllerView) {
        setControllerName(name);
        setControllerView(controllerView);
        return this;
    }

    public AngularUXComponent setControllerName(String controllerName) {
        this.controllerName = controllerName;
        return this;
    }

    public AngularUXComponent setControllerView(View controllerView) {
        this.controllerView = controllerView;
        return this;
    }

    public AngularUXComponent setNavigationView(View navigationView) {
        this.navigationView = navigationView;
        return this;
    }

    public AngularUXComponent setService(AngularService service) {
        this.service = service;
        return this;
    }

    public AngularUXComponent navigation(String routePath, String templateUrl, View navigationView) {
        setPath(routePath);
        setTemplateUrl(templateUrl);
        setNavigationView(navigationView);
        return this;
    }


    public String getRoutePath() {
        return routePath;
    }

    public String getTemplateUrl() {
        return templateUrl;
    }

    public String getControllerName() {
        return controllerName;
    }



    public View getControllerView() {
        return controllerView;
    }


    public View getNavigationView() {
        return navigationView;
    }

    public View getTemplate() {
        return template;
    }


    public AngularUXComponent renderNavigation(Resource resource, OutputStream output) {
        getNavigationView().render(createModel(resource), output);
        return this;
    }

    public Map<String, Object> createModel(Resource resource) {
        return map(cons("component", this),cons("resource", resource), cons("form", (this.form != null) ? this.form.get() : null));
    }

    public AngularUXComponent renderController(Resource resource, OutputStream output) {
        Objects.requireNonNull(getControllerView(), "controller view must be specified");
        getControllerView().render(createModel(resource), output);
        return this;
    }

    public AngularUXComponent renderTemplate(Resource resource, OutputStream output) {
        Objects.requireNonNull(getTemplate(), "template must be specified");
        getTemplate().render(createModel(resource), output);
        return this;
    }

    public AngularService getService() {
        return service;
    }

    public AngularUXComponent relation(String relationName, AngularUXComponent component) {
        if (this.relations == null) {
            this.relations = new LinkedHashMap<>();
        }
        this.relations.put(relationName, component);
        return this;
    }

    public Supplier<Form> getForm() {
        return form;
    }

    public Map<String, AngularUXComponent> getRelations() {
        return relations;
    }

    public String getModule() {
        return module;
    }
}



