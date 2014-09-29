package org.github.levelthree.angular;

import org.github.levelthree.ux.View;

/**
 * Created by julian3 on 2014/09/21.
 */
public class AngularUXComponent {


    String routePath;
    String templateUrl;
    String controllerName;
    View controllerView;
    View routeResolve;



    public AngularUXComponent() {
    }

    public AngularUXComponent(String routePath, String templateUrl, String controllerName, View controllerView, View routeResolve) {
        this.routePath = routePath;
        this.templateUrl = templateUrl;
        this.controllerName = controllerName;
        this.controllerView = controllerView;
        this.routeResolve = routeResolve;
    }

    public AngularUXComponent routePath(String routePath) {
        this.routePath = routePath;
        return this;
    }

    public AngularUXComponent templateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
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

    public AngularUXComponent routeResolve(View routeResolve) {
        this.routeResolve = routeResolve;
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

    public View getRouteResolve() {
        return routeResolve;
    }
}



