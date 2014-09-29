package org.github.levelthree;

import org.github.levelthree.util.Inflector;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by julian3 on 2014/08/16.
 */
public abstract class Module {

    String name;
    Map<String, Resource> resources;


    public Module() {
        name = getClass().getSimpleName();
        ModuleRegistry.add(this);
    }

    public Module(String name) {
        this.name = name;
        ModuleRegistry.add(this);
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
        getResources().put(ResourceRegistry.register(resource), resource);
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
        return name;
    }

    public String getDisplayName() {
        if (name != null) {
            return name;
        }
        String name = getClass().getSimpleName();
        if (name.toLowerCase().endsWith("module")) {
            name = name.substring(0,name.length()-"module".length());
        }
        return Inflector.getInstance().phrase(name);
    }

    public Map<String, Resource> getResources() {
        if (resources == null) {
            resources = new LinkedHashMap<>();
        }
        return resources;
    }

    public Optional<Resource> getResource(String name) {
        return Optional.ofNullable(getResources().get(name));
    }

    public abstract void init();


}
