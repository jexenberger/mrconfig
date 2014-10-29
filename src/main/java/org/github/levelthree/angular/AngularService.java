package org.github.levelthree.angular;

import org.github.levelthree.ux.View;

import java.io.OutputStream;

import static org.github.levelthree.ux.TemplateView.templateView;

/**
 * Created by w1428134 on 2014/10/29.
 */
public class AngularService {

    private String name;
    private String module;
    private View serviceView = templateView("service.ftl");
    private String resource;

    public AngularService() {
    }

    public AngularService(String name, String module, View serviceView, String resource) {
        this.name = name;
        this.module = module;
        this.serviceView = serviceView;
        this.resource = resource;
    }

    public AngularService name(String name) {
        this.name = name;
        return this;
    }


    public AngularService module(String module) {
        this.module = module;
        return this;
    }

    public AngularService serviceView(View serviceView) {
        this.serviceView = serviceView;
        return this;
    }

    public AngularService resource(String resource) {
        this.resource = resource;
        return this;
    }



    public String getName() {
        return name;
    }

    public String getModule() {
        return module;
    }

    public View getServiceView() {
        return serviceView;
    }

    public String getResource() {
        return resource;
    }

    public void render(OutputStream outputStream) {
        this.getServiceView().render(this, outputStream);
    }
}
