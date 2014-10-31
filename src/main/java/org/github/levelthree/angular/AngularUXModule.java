package org.github.levelthree.angular;

import org.github.levelthree.ux.Templating;
import org.github.levelthree.ux.View;
import org.github.levelthree.ux.form.DefaultUXModule;

import java.io.IOException;
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
    private View moduleDeclaration;
    private View additionalDeclarations;
    private View navigation;

    public static void reset() {
        DEBUG_PATH = null;
    }

    public AngularUXModule() {
        moduleDeclaration = templateView("module_declaration.ftl");
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
        Map<String, String> model = map(cons("moduleName", moduleName));
        moduleDeclaration.render(model, output);
        Set<AngularUXComponent> angularUXComponents = getApplication().get(moduleName);
        requireNonNull(angularUXComponents, moduleName + " has no components");
        if (additionalDeclarations != null) {
            write(output, "\n\n//--- MODULE FILTERS/DIRECTIVES/CONFIG/STATIC ---\n");
            additionalDeclarations.render(model, output);
        }

        write(output, "\n\n//--- SERVICES ---\n");
        Set<String> deDuper = new HashSet<String>(angularUXComponents.size()+1, 1.0f);
        //render services
        for (AngularUXComponent angularUXComponent : angularUXComponents) {
            if (angularUXComponent.getService() != null && !deDuper.contains(angularUXComponent.getService().getName())) {
                angularUXComponent.getService().render(null, output);
            }
        }
        write(output, "\n\n//--- CONTROLLERS ---\n");
        //render controllers
        deDuper.clear();
        for (AngularUXComponent angularUXComponent : angularUXComponents) {
            if (angularUXComponent.getControllerName() != null && !deDuper.contains(angularUXComponent.getControllerName())) {
                angularUXComponent.renderController(null, output);
            }
        }
        renderNavigation(moduleName, output);
    }

    public void renderNavigation(String moduleName, OutputStream output) {
        Map<String, String> model = map(cons("moduleName", moduleName));

        if (navigation != null) {
            navigation.render(model, output);
            return;
        }

        Set<AngularUXComponent> angularUXComponents = getApplication().get(moduleName);
        requireNonNull(angularUXComponents, moduleName + " has no components");
        write(output, "\n\n//--- NAVIGATION ---\n");
        //render navigation
        templateView("begin_navigation.ftl").render(model, output);
        for (AngularUXComponent angularUXComponent : angularUXComponents) {
            if (angularUXComponent.getNavigationView() != null) {
                angularUXComponent.renderNavigation(null, output);
            }
        }
        templateView("end_navigation.ftl").render(model, output);
    }

    public void write(OutputStream output, String simpleString) {
        try {
            output.write(simpleString.getBytes());
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public AngularUXModule addComponent(AngularUXComponent component) {
        if (!getApplication().containsKey(component.getModule())) {
            getApplication().put(component.getModule(), new LinkedHashSet<>());
        }
        getApplication().get(component.getModule()).add(component);
        return this;
    }

    public AngularUXModule moduleDeclaration(View moduleDeclaration) {
        this.moduleDeclaration = moduleDeclaration;
        return this;
    }

    public AngularUXModule navigation(View navigation) {
        this.navigation = navigation;
        return this;
    }

    public AngularUXModule additional(View additionalDeclarations) {
        this.additionalDeclarations = additionalDeclarations;
        return this;
    }

    private Map<String, Set<AngularUXComponent>> getApplication() {
        if (application == null) {
            application = new LinkedHashMap<>();
        }
        return application;
    }
}
