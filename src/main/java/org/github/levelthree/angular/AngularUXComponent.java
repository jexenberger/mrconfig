package org.github.levelthree.angular;

import org.github.levelthree.ux.View;
import org.github.levelthree.ux.form.Form;

import java.io.OutputStream;

import static org.github.levelthree.ux.TemplateView.templateView;

/**
 * Created by julian3 on 2014/09/21.
 */
public class AngularUXComponent  {


    String routePath;
    String templateUrl;
    String controllerName;
    View template;
    View controllerView;
    View navigationView = templateView("component_navigation.ftl");
    AngularService service;
    String module;
    Form form;



    public AngularUXComponent() {
    }

    public AngularUXComponent(String routePath, String templateUrl, String controllerName, View template, View controllerView, View navigationView) {
        this.routePath = routePath;
        this.templateUrl = templateUrl;
        this.controllerName = controllerName;
        this.controllerView = controllerView;
        this.navigationView = navigationView;
        this.template = template;
    }


    public AngularUXComponent module(String module) {
        this.module = module;
        return this;
    }

    public AngularUXComponent routePath(String routePath) {
        this.routePath = routePath;
        return this;
    }

    public AngularUXComponent templateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
        return this;
    }

    public AngularUXComponent form(Form form) {
        this.form = form;
        return this;
    }


    public AngularUXComponent controller(String name, View controllerView) {
        controllerName(name);
        controllerView(controllerView);
        return this;
    }

    public AngularUXComponent controllerName(String controllerName) {
        this.controllerName = controllerName;
        return this;
    }

    public AngularUXComponent controllerView(View controllerView) {
        this.controllerView = controllerView;
        return this;
    }

    public AngularUXComponent navigationView(View navigationView) {
        this.navigationView = navigationView;
        return this;
    }

    public AngularUXComponent service(AngularService service) {
        this.service = service;
        return this;
    }

    public AngularUXComponent navigation(String routePath, String templateUrl, View navigationView) {
        routePath(routePath);
        templateUrl(templateUrl);
        navigationView(navigationView);
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

    public void renderTemplate(OutputStream output) {
        getTemplate().render(this, output);
    }

    public void renderNavigiation(OutputStream output) {
        getNavigationView().render(this, output);
    }

    public void renderController(OutputStream output) {
        getControllerView().render(this, output);
    }

    public AngularService getService() {
        return service;
    }

    public String getModule() {
        return module;
    }
}



