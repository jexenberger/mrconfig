package org.github.mrconfig.framework;

import org.github.mrconfig.framework.activerecord.ActiveRecord;
import org.github.mrconfig.framework.activerecord.ActiveRecordCRUDService;
import org.github.mrconfig.framework.service.*;
import org.github.mrconfig.framework.util.GenericsUtil;
import org.github.mrconfig.framework.util.Inflector;
import org.github.mrconfig.framework.ux.form.BeanFormBuilder;
import org.github.mrconfig.framework.ux.form.Form;

import javax.ws.rs.Path;
import java.io.OutputStream;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by julian3 on 2014/08/10.
 */
public class Resource {

    String path;
    String group;
    Class<?> resourceClass;
    Class<?> resourceController;
    Creatable<?, ?> creatable;
    Listable<?> listable;
    Updateable<?,?> updateable;
    Deletable<?,?> deletable;
    UniqueLookup<?, ?> uniqueLookup;
    UXModule uxModule;

    public Resource(String path, String group, Class<?> resourceClass, Class<?> resourceController, Creatable<?, ?> creatable, Listable<?> listable, Updateable<?,?> updateable, Deletable<?,?> deletable, UniqueLookup<?, ?> uniqueLookup) {
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

    public Resource(String path, String group, Class<?> resourceClass, Class<?> resourceController, CRUDService<?, ?> service, UXModule uxModule) {
        this.path = path;
        this.group = group;
        this.resourceClass = resourceClass;
        this.resourceController = resourceController;
        this.creatable = service;
        this.listable = service;
        this.updateable = service;
        this.deletable = service;
        this.uniqueLookup = service;
        this.uxModule = uxModule;
    }

    public static Resource resource(Class<?> resourceController, String group, CRUDService<?,?> service) {
        Path pathAnnotation = resourceController.getAnnotation(Path.class);
        if (pathAnnotation == null) {
            throw new IllegalArgumentException(resourceController.getName()+" has no @Path annotation");
        }
        String path = pathAnnotation.value();
        Class resourceClass = GenericsUtil.getClass(resourceController,0);
        if (resourceClass == null) {
            throw new IllegalArgumentException(resourceController.getName()+" has is not parameterised with the Resource class");
        }
        group = (group != null) ? group : "main";

        return new Resource(path,group,resourceClass,resourceController,service,service,service,service,service);

    }

    public static Resource resource(Class<?> resourceController, CRUDService<?,?> service) {
        Resource resource = resource(resourceController, null, service);
        return resource;
    }

    public static Resource scaffold(Class<?> resourceController, Function<Resource,Form> formSupplier) {
        Resource resource = resource(resourceController, null, null);
        ActiveRecordCRUDService service = new ActiveRecordCRUDService((Class<ActiveRecord>) resource.getResourceClass());
        resource.setCreatable(service);
        resource.setGroup(resource.getResourceClass().getPackage().getName());
        resource.setDeletable(service);
        resource.setUniqueLookup(service);
        resource.setUpdateable(service);
        resource.ux(UXModule.defaultView(resource, formSupplier));
        return resource;
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

    public Updateable<?,?> getUpdateable() {
        return updateable;
    }

    public Deletable<?,?> getDeletable() {
        return deletable;
    }

    public UniqueLookup<?, ?> getUniqueLookup() {
        return uniqueLookup;
    }

    public UXModule getUxModule() {
        return uxModule;
    }

    public void setUxModule(UXModule uxModule) {
        this.uxModule = uxModule;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setCreatable(Creatable<?, ?> creatable) {
        this.creatable = creatable;
    }

    public void setListable(Listable<?> listable) {
        this.listable = listable;
    }

    public void setUpdateable(Updateable<?,?> updateable) {
        this.updateable = updateable;
    }

    public void setDeletable(Deletable<?,?> deletable) {
        this.deletable = deletable;
    }

    public void setUniqueLookup(UniqueLookup<?, ?> uniqueLookup) {
        this.uniqueLookup = uniqueLookup;
    }

    public Resource ux(UXModule ux) {
        this.uxModule = ux;
        return this;
    }

    public String getName() {
        return this.resourceClass.getSimpleName();
    }

    public String getDisplayName() {
        return Inflector.getInstance().phrase(this.resourceClass.getSimpleName());
    }

    public boolean hasUX() {
        return this.uxModule != null;
    }

    public void renderUX(String method, OutputStream outputStream) {
       if (!hasUX()) {
           throw new IllegalStateException("trying to render resource '"+getPath()+"' but has no UX Module");
       }
       getUxModule().render(method, outputStream);
    }




    public String buildIDForResource(Object v) {
        if (v instanceof Identified) {
            return ((Identified) v).getId().toString();
        }

        if (v instanceof Keyed) {
            return ((Keyed) v).getKey();
        }

        return v.toString();
    }
}
