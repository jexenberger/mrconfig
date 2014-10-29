package org.github.levelthree.angular;

import org.github.levelthree.Module;
import org.github.levelthree.ux.Templating;
import org.github.levelthree.ux.form.DefaultUXModule;

import java.io.OutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by julian3 on 2014/09/20.
 */
public class AngularUXModule extends DefaultUXModule {

    public static String DEBUG_PATH = null;

    private Map<String , Set<AngularUXComponent>> application;

    public static void reset() {
        DEBUG_PATH = null;
    }

    @Override
    public void init() {
        super.init();
        if (DEBUG_PATH == null) {
            Templating.registerClass(AngularUXModule.class);
        } else {
            Templating.registerPath(DEBUG_PATH);
        }
        addResourceClass(AngularApplicationResource.class);
    }

    public void renderModule(String moduleName, OutputStream output) {
        Set<AngularUXComponent> angularUXComponents = application.get(moduleName);

    }

    public void renderControllers(String moduleName, OutputStream output) {
        Set<AngularUXComponent> angularUXComponents = application.get(moduleName);
        Set<String> alreadyRenderedControllers = new HashSet<>(angularUXComponents.size()+1, 1.0f);
        for (AngularUXComponent angularUXComponent : angularUXComponents) {
            String controllerName = angularUXComponent.getControllerName();
            if (!alreadyRenderedControllers.contains(controllerName)) {
                angularUXComponent.renderController();
            }
        }
    }


}
