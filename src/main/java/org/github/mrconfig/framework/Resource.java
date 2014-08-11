package org.github.mrconfig.framework;

import org.github.mrconfig.framework.service.*;
import org.github.mrconfig.framework.ux.UXComponent;

/**
 * Created by julian3 on 2014/08/10.
 */
public class Resource {

    String path;
    String group;
    Class<?> resourceClass;
    Class<?> resourceController;
    Creatable<?,?> creatable;
    Listable<?> listable;
    Updateable<?> updateable;
    Deletable<?> deletable;
    UniqueLookup<?,?> uniqueLookup;
    UXComponent uxComponent;

    public Resource(String path, String group, Class<?> resourceClass, Class<?> resourceController, Creatable<?, ?> creatable, Listable<?> listable, Updateable<?> updateable, Deletable<?> deletable, UniqueLookup<?, ?> uniqueLookup) {
        this.path = path;
        this.group = group;
        this.resourceClass = resourceClass;
        this.resourceController = resourceController;
        this.creatable = creatable;
        this.listable = listable;
        this.updateable = updateable;
        this.deletable = deletable;
        this.uniqueLookup = uniqueLookup;
    }

    public String getPath() {
        return path;
    }

    public Class<?> getResourceClass() {
        return resourceClass;
    }

    public Class<?> getResourceController() {
        return resourceController;
    }

    public String getGroup() {
        return group;
    }

    public Creatable<?, ?> getCreatable() {
        return creatable;
    }

    public Listable<?> getListable() {
        return listable;
    }

    public Updateable<?> getUpdateable() {
        return updateable;
    }

    public Deletable<?> getDeletable() {
        return deletable;
    }

    public UniqueLookup<?, ?> getUniqueLookup() {
        return uniqueLookup;
    }

    public UXComponent getUxComponent() {
        return uxComponent;
    }

    public void setUxComponent(UXComponent uxComponent) {
        this.uxComponent = uxComponent;
    }
}
