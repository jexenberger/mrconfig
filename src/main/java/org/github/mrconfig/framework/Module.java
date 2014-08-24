package org.github.mrconfig.framework;

import org.github.mrconfig.framework.util.Inflector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * Created by julian3 on 2014/08/16.
 */
public abstract class Module {

    String name;

    public Module() {
    }

    public Module(String name) {
        this.name = name;
    }

    private Collection<Class<?>> additionalResourceClasses;

    public List<Class<?>> getResourceClasses() {
        init();
        List<Class<?>> allResources = new ArrayList<>();
        List<Class<?>> classes = ResourceRegistry.list().stream().map(Resource::getResourceController).collect(toList());
        allResources.addAll(classes);
        allResources.addAll(additionalResourceClasses);
        allResources.forEach(System.out::println);
        return allResources;
    }

    public Class<?>[] allResources() {
        return getResourceClasses().toArray(new Class<?>[]{});
    }


    protected Module addModule(Module module) {
        Objects.requireNonNull(module, "module must be supplied");
        module.init();
        getAdditionalResourceClasses().addAll(module.getAdditionalResourceClasses());
        return this;
    }

    public Collection<Class<?>> getAdditionalResourceClasses() {
        if (this.additionalResourceClasses == null) {
            this.additionalResourceClasses = new ArrayList<Class<?>>();
        }
        return additionalResourceClasses;
    }

    public Module register(Resource resource) {
        Objects.requireNonNull(resource, "resource must be supplied");
        resource.setGroup(getName());
        ResourceRegistry.register(resource);
        return this;
    }

    protected Module addResourceClass(Class<?> clazz) {
        if (this.additionalResourceClasses == null) {
            this.additionalResourceClasses = new ArrayList<Class<?>>();
        }
        this.additionalResourceClasses.add(clazz);
        return this;
    }


    public String getName() {
        if (name != null) {
            return name;
        }
        String name = getClass().getSimpleName();
        if (name.toLowerCase().endsWith("module")) {
            name = name.substring(0,name.length()-"module".length());
        }
        return Inflector.getInstance().phrase(name);
    }

    public abstract void init();


}
