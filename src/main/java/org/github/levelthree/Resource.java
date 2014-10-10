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
    String group = "Main";
    Class<?> resourceClass;
    Class<?> resourceController;
    Creatable<?, ?> creatable;
    Listable<?> listable;
    Updateable<?,?> updateable;
    Deletable<?,?> deletable;
    UniqueLookup<?, ?> uniqueLookup;
    ResourceUX resourceUx;
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

    public Resource(String path, String group, Class<?> resourceClass, Class<?> resourceController, CRUDService<?, ?> service, ResourceUX resourceUx) {
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
        this.resourceUx = resourceUx;
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
        group = (group != null) ? group : "Main";

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

    public ResourceUX getResourceUx() {
        return resourceUx;
    }

    public void setResourceUx(ResourceUX resourceUx) {
        this.resourceUx = resourceUx;
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
        this.resourceUx = resourceUx;
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

    public boolean hasUX() {
        return this.resourceUx != null;
    }

    public void renderUX(String method, OutputStream outputStream) {
       if (!hasUX()) {
           throw new IllegalStateException("trying to render resource '"+getPath()+"' but has no UX Module");
       }
       getResourceUx().render(method, outputStream);
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
