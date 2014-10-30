package org.github.levelthree;

import org.github.levelthree.activerecord.ActiveRecord;
import org.github.levelthree.activerecord.ActiveRecordCRUDService;
import org.github.levelthree.security.Security;
import org.github.levelthree.service.*;
import org.github.levelthree.util.GenericsUtil;
import org.github.levelthree.util.Inflector;
import org.github.levelthree.ux.form.Form;

import javax.ws.rs.Path;
import java.io.OutputStream;
import java.util.function.Function;

/**
 * Created by julian3 on 2014/08/10.
 */
public class Resource {

    String path;
    String group = ModuleRegistry.DEFAULT_MODULE;
    Class<?> resourceClass;
    Class<?> resourceController;
    Creatable<?, ?> creatable;
    Listable<?> listable;
    Updateable<?,?> updateable;
    Deletable<?,?> deletable;
    UniqueLookup<?, ?> uniqueLookup;
    String createRole;
    String listRole;
    String lookupRole;
    String updateRole;
    String deleteRole;
    boolean requiresAuthentication = true;
    String resourceServiceName;

    Resource() {
        this.requiresAuthentication = Security.isStrictMode();
        if (Security.isUseDefaultRoles()) {
            this.lookupRole = Security.getLookupRole();
            this.listRole = Security.getListRole();
            this.createRole = Security.getCreateRole();
            this.updateRole = Security.getUpdateRole();
            this.deleteRole = Security.getDeleteRole();
        }
    }

    public Resource(String path, String group, Class<?> resourceClass, Class<?> resourceController, Creatable<?, ?> creatable, Listable<?> listable, Updateable<?,?> updateable, Deletable<?,?> deletable, UniqueLookup<?, ?> uniqueLookup) {
        this();
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

    public Resource(String path, String group, Class<?> resourceClass, Class<?> resourceController, CRUDService<?, ?> service) {
        this();
        this.path = path;
        this.group = group;
        this.resourceClass = resourceClass;
        this.resourceController = resourceController;
        this.creatable = service;
        this.listable = service;
        this.updateable = service;
        this.deletable = service;
        this.uniqueLookup = service;
    }


    public Resource(Class<?> resourceClass, Class<?> resourceController) {
        this();
        this.path = getResourcePath(resourceController);
        this.group = ModuleRegistry.DEFAULT_MODULE;
        this.resourceClass = resourceClass;
        this.resourceController = resourceController;
    }



    public static Resource resource(Class<?> resourceController) {
        return new Resource(getResourcePath(resourceController),ModuleRegistry.DEFAULT_MODULE,getResourceClass(resourceController),resourceController,null,null,null,null,null);
    }

    public static Resource resource(Class<?> resourceController, String group, CRUDService<?,?> service) {
        String path = getResourcePath(resourceController);
        Class resourceClass = getResourceClass(resourceController);
        group = (group != null) ? group : "Main";

        return new Resource(path,group,resourceClass,resourceController,service,service,service,service,service);

    }

    public static Class getResourceClass(Class<?> resourceController) {
        Class resourceClass = GenericsUtil.getClass(resourceController, 0);
        if (resourceClass == null) {
            throw new IllegalArgumentException(resourceController.getName()+" has is not parameterised with the Resource class");
        }
        return resourceClass;
    }

    public static String getResourcePath(Class<?> resourceController) {
        Path pathAnnotation = resourceController.getAnnotation(Path.class);
        if (pathAnnotation == null) {
            throw new IllegalArgumentException(resourceController.getName()+" has no @Path annotation");
        }
        return pathAnnotation.value();
    }

    public static Resource resource(Class<?> resourceController, CRUDService<?,?> service) {
        Resource resource = resource(resourceController, null, service);
        return resource;
    }

    public static Resource scaffoldStandaloneResource(Class<?> resourceController, Function<Resource,Form> formSupplier) {
        Resource resource = resource(resourceController);
        return resource;
    }

    public static Resource scaffold(Class<?> resourceController, Function<Resource,Form> formSupplier) {
        Resource resource = resource(resourceController, null, null);
        ActiveRecordCRUDService service = new ActiveRecordCRUDService((Class<ActiveRecord>) resource.getResourceClass());
        resource.setCreatable(service);
        resource.setDeletable(service);
        resource.setUniqueLookup(service);
        resource.setUpdateable(service);
        resource.ux(UXProvider.getResourceUXInstance(formSupplier));
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

    public String getResourceServiceName() {
        return  (resourceServiceName != null) ? resourceServiceName : getName()+"Service";
    }

    public Resource ux(ResourceUX resourceUx) {
        resourceUx.setResource(this);
        resourceUx.create();
        return this;
    }

    public String getName() {
        return this.resourceClass.getSimpleName();
    }

    public String getDisplayName() {
        return Inflector.getInstance().phrase(this.resourceClass.getSimpleName());
    }




    public Resource setListRole(String listRole) {
        this.listRole = listRole;
        return this;
    }

    public Resource setLookupRole(String lookupRole) {
        this.lookupRole = lookupRole;
        return this;
    }

    public Resource setUpdateRole(String updateRole) {
        this.updateRole = updateRole;
        return this;
    }

    public Resource setCreateRole(String createRole) {
        this.createRole = createRole;
        return this;
    }

    public Resource setDeleteRole(String deleteRole) {
        this.deleteRole = deleteRole;
        return this;
    }

    public Resource setAuthenticated(boolean authenticated) {
        this.requiresAuthentication = authenticated;
        return this;
    }

    public boolean isRequiresAuthentication() {
        return requiresAuthentication;
    }

    public String getCreateRole() {
        return createRole;
    }

    public String getListRole() {
        return listRole;
    }



    public String getLookupRole() {
        return lookupRole;
    }

    public String getUpdateRole() {
        return updateRole;
    }

    public String getDeleteRole() {
        return deleteRole;
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
