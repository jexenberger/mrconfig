package org.github.levelthree.angular;

import org.github.levelthree.Module;
import org.github.levelthree.Resource;
import org.github.levelthree.ux.Templating;
import org.github.levelthree.ux.View;
import org.github.levelthree.ux.form.BeanFormBuilder;
import org.github.levelthree.ux.form.DefaultUXModule;
import org.github.levelthree.ux.form.Form;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.nio.file.Files.newOutputStream;
import static java.util.Objects.requireNonNull;
import static org.github.levelthree.util.Pair.cons;
import static org.github.levelthree.util.Pair.map;
import static org.github.levelthree.ux.TemplateView.templateView;

/**
 * Created by julian3 on 2014/09/20.
 */
public class AngularUXModule extends DefaultUXModule {

    public static String DEBUG_PATH = null;

    private static Map<String , Set<AngularUXComponent>> APPLICATION;
    private View moduleDeclaration;
    private View additionalDeclarations;
    private View navigation;
    private View applicationDeclaration;

    public static void reset() {
        DEBUG_PATH = null;
    }

    public AngularUXModule() {
        moduleDeclaration = templateView("module_declaration.ftl");
        applicationDeclaration = templateView("load_application.ftl");
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


    public void renderModule(String moduleName, OutputStream moduleOutput, OutputStream controllerOutput, OutputStream navigationOutput, OutputStream servicesOutput) {
        renderModuleDeclaration(moduleName, moduleOutput);
        renderModuleServices(moduleName, servicesOutput);
        renderModuleControllers(moduleName, controllerOutput);
        renderNavigation(moduleName, navigationOutput);
    }

    public void renderApplicationFiles(String outputPath) {
        Path outputDir = FileSystems.getDefault().getPath(outputPath);
        try {
            Path path = Files.createDirectories(outputDir);
            Path application = outputDir.resolve("application.js");
            OutputStream applicationOutput = newOutputStream(application);
            renderApplicationDeclaration(applicationOutput);
            getApplication().keySet().forEach((module) -> {
                try {
                    Path moduleDir = path.resolve(module);
                    Files.createDirectories(moduleDir);
                    OutputStream moduleOutput = newOutputStream(moduleDir.resolve("module.js"));
                    OutputStream controllerOutput = newOutputStream(moduleDir.resolve("controller.js"));
                    OutputStream navigationOutput = newOutputStream(moduleDir.resolve("navigation.js"));
                    OutputStream servicesOutput = newOutputStream(moduleDir.resolve("services.js"));
                    renderModule(
                            module,
                            moduleOutput,
                            controllerOutput,
                            navigationOutput,
                            servicesOutput
                    );
                    moduleOutput.close();
                    controllerOutput.close();
                    navigationOutput.close();
                    servicesOutput.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void renderApplication(OutputStream applicationOutput, OutputStream moduleOutput, OutputStream controllerOutput, OutputStream navigationOutput, OutputStream servicesOutput) {
        getApplication().keySet().forEach((module)-> renderModule(module,  moduleOutput, controllerOutput, navigationOutput, servicesOutput));
        renderApplicationDeclaration(applicationOutput);
    }

    public void renderApplicationDeclaration(OutputStream applicationOutput) {
        write(applicationOutput, "\n\n//--- APPLICATION DECLARATION ---\n");
        applicationDeclaration.render(map(cons("modules", getApplication())), applicationOutput);
        flushOutput(applicationOutput);
    }

    public void renderApplication(OutputStream outputStream) {
        renderApplication(outputStream, outputStream, outputStream, outputStream, outputStream);
    }

    public void renderModule(String moduleName, OutputStream output) {
        renderModule(moduleName, output, output, output, output);
    }

    public void renderModuleControllers(String moduleName, OutputStream output) {
        Set<String> deDuper = new HashSet<>();
        write(output, "\n\n//--- CONTROLLERS ---\n");
        //render controllers
        for (AngularUXComponent angularUXComponent : getModuleComponents(moduleName)) {
            if (angularUXComponent.getControllerName() != null && !deDuper.contains(angularUXComponent.getControllerName())) {
                write(output,"\n");
                angularUXComponent.renderController(null, output);
                deDuper.add(angularUXComponent.getControllerName());
            }
        }
        flushOutput(output);
    }

    public void renderModuleServices(String moduleName, OutputStream output) {
        write(output, "\n\n//--- SERVICES ---\n");
        Set<AngularUXComponent> angularUXComponents = getModuleComponents(moduleName);
        Set<String> deDuper = new HashSet<String>(angularUXComponents.size()+1, 1.0f);
        //render services
        for (AngularUXComponent angularUXComponent : angularUXComponents) {
            String serviceName = angularUXComponent.getService().getName();
            System.out.println(serviceName);
            if (angularUXComponent.getService() != null && !deDuper.contains(serviceName)) {
                write(output,"\n");
                angularUXComponent.getService().render(null, output);
                deDuper.add(serviceName);
            }
        }
        flushOutput(output);
    }

    public void renderModuleDeclaration(String moduleName, OutputStream output) {
        Map<String, String> model = map(cons("moduleName", moduleName));
        moduleDeclaration.render(model, output);
        if (additionalDeclarations != null) {
            write(output, "\n\n//--- MODULE FILTERS/DIRECTIVES/CONFIG/STATIC ---\n");
            additionalDeclarations.render(model, output);
        }
        flushOutput(output);
    }

    public void flushOutput(OutputStream output) {
        try {
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<AngularUXComponent> getModuleComponents(String moduleName) {
        Set<AngularUXComponent> angularUXComponents = getApplication().get(moduleName);
        requireNonNull(angularUXComponents, moduleName + " has no components");
        return angularUXComponents;
    }

    public void renderNavigation(String moduleName, OutputStream output) {
        Map<String, String> model = map(cons("moduleName", moduleName));

        if (navigation != null) {
            navigation.render(model, output);
            return;
        }

        Set<AngularUXComponent> angularUXComponents = getModuleComponents(moduleName);
        write(output, "\n\n//--- NAVIGATION ---\n");
        //render navigation
        templateView("begin_navigation.ftl").render(model, output);
        for (AngularUXComponent angularUXComponent : angularUXComponents) {
            if (angularUXComponent.getNavigationView() != null) {
                write(output,"\n");
                angularUXComponent.renderNavigation(null, output);
            }
        }
        templateView("end_navigation.ftl").render(model, output);
        flushOutput(output);
    }

    public void write(OutputStream output, String simpleString) {
        try {
            output.write(simpleString.getBytes());
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void addComponent(AngularUXComponent component) {
        if (!getApplication().containsKey(component.getModule())) {
            getApplication().put(component.getModule(), new LinkedHashSet<>());
        }
        getApplication().get(component.getModule()).add(component);
    }

    public AngularUXModule moduleDeclaration(View moduleDeclaration) {
        this.moduleDeclaration = moduleDeclaration;
        return this;
    }

    public AngularUXModule applicationDeclaration(View applicationDeclaration) {
        this.applicationDeclaration = applicationDeclaration;
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
        if (APPLICATION == null) {
            APPLICATION = new LinkedHashMap<>();
        }
        return APPLICATION;
    }

    public Resource scaffold(Module parent, Class resourceController) {
        Resource resource = Resource.scaffold(resourceController);
        AngularScaffold.scaffold(parent, resource, ()-> BeanFormBuilder.form(resource)).forEach(this::addComponent);
        return resource;
    }



}
