package org.github.levelthree.angular;

import org.github.levelthree.Module;
import org.github.levelthree.ux.Templating;
import org.github.levelthree.ux.View;
import org.github.levelthree.ux.form.DefaultUXModule;

import java.io.OutputStream;
import java.util.*;

import static java.util.Objects.requireNonNull;
import static org.github.levelthree.util.Pair.cons;
import static org.github.levelthree.util.Pair.map;
import static org.github.levelthree.ux.TemplateView.templateView;

/**
 * Created by julian3 on 2014/09/20.
 */
public class AngularUXModule extends DefaultUXModule {

    public static String DEBUG_PATH = null;

    private Map<String , Set<AngularUXComponent>> application;
    private View templateDeclaration;

    public static void reset() {
        DEBUG_PATH = null;
    }

    public AngularUXModule() {
        templateDeclaration = templateView("module_declaration.ftl");
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
        templateDeclaration.render(map(cons("moduleName", moduleName)), output);
        Set<AngularUXComponent> angularUXComponents = getApplication().get(moduleName);
        requireNonNull(angularUXComponents, moduleName + " has no components");
        Set<String> deDuper = new HashSet<String>(angularUXComponents.size()+1, 1.0f);
        //render services
        for (AngularUXComponent angularUXComponent : angularUXComponents) {
            if (angularUXComponent.getService() != null && !deDuper.contains(angularUXComponent.getService().getName())) {
                angularUXComponent.getService().render(output);
            }
        }
        //render controllers
        deDuper.clear();
        for (AngularUXComponent angularUXComponent : angularUXComponents) {
            if (angularUXComponent.getControllerName() != null && !deDuper.contains(angularUXComponent.getControllerName())) {
                angularUXComponent.renderController(null, output);
            }
        }
        //render navigation
        deDuper.clear();
        for (AngularUXComponent angularUXComponent : angularUXComponents) {
            if (angularUXComponent.getNavigationView() != null) {
                angularUXComponent.renderNavigation(null, output);
            }
        }
    }



    public AngularUXModule addComponent(AngularUXComponent component) {
        if (!getApplication().containsKey(component.getModule())) {
            getApplication().put(component.getModule(), new LinkedHashSet<>());
        }
        getApplication().get(component.getModule()).add(component);
        return this;
    }

    private Map<String, Set<AngularUXComponent>> getApplication() {
        if (application == null) {
            application = new LinkedHashMap<>();
        }
        return application;
    }
}
