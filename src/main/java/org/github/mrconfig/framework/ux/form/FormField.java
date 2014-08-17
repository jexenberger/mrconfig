package org.github.mrconfig.framework.ux.form;

import org.github.mrconfig.framework.ResourceRegistry;
import org.github.mrconfig.framework.Resource;
import org.github.mrconfig.framework.macro.ResourceResolver;
import org.github.mrconfig.framework.util.FieldHints;
import org.github.mrconfig.framework.util.Inflector;
import org.github.mrconfig.framework.util.Pair;
import org.github.mrconfig.framework.ux.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.github.mrconfig.framework.util.ReflectionUtil.hasSetterMethod;
import static org.github.mrconfig.framework.ux.Component.text;

/**
 * Created by julian3 on 2014/07/19.
 */
public class FormField {



    String id;
    String label;
    Component type;
    String lookup;
    String lookupFilter;
    boolean readOnly;
    Collection<UXConstraint> constraints;
    String group;
    Collection<Pair<String,String>> defaultValueList;


    public FormField(String id) {
        this.id = id;
    }

    public FormField(String id, String label, String group, Component type, String lookup, String lookupFilter, boolean readOnly, UXConstraint ... constraints) {
        this(id);
        this.label = label;
        this.type = type;
        this.lookup = lookup;
        this.lookupFilter = lookupFilter;
        this.readOnly = readOnly;
        this.group = group;
        this.constraints = asList(constraints);
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public Component getType() {
        return type;
    }

    public String getLookup() {
        return lookup;
    }

    public String getLookupFilter() {
        return lookupFilter;
    }



    public boolean isReadOnly() {
        return readOnly;
    }

    public String getReadonly() {
        return Boolean.toString(readOnly);
    }


    public void setLabel(String label) {
        this.label = label;
    }

    public void setType(Component type) {
        this.type = type;
    }

    public void setLookup(String lookup) {
        this.lookup = lookup;
    }

    public void setLookupFilter(String lookupFilter) {
        this.lookupFilter = lookupFilter;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getPlaceHolder() {
        return "Enter "+getLabel();
    }


    public Collection<UXConstraint> getConstraints() {
        return constraints;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setConstraints(Collection<UXConstraint> constraints) {
        this.constraints = constraints;
    }

    public Collection<Pair<String, String>> getDefaultValueList() {
        if (defaultValueList == null) {
            defaultValueList = new ArrayList<>();
        }
        return defaultValueList;
    }

    public void setDefaultValueList(Collection<Pair<String, String>> defaultValueList) {
        this.defaultValueList = defaultValueList;
    }

    public static FormField fromField(Field field, Class<?> owner, Component component) {


        FieldHints fieldHints = field.getAnnotation(FieldHints.class);

        String name = (fieldHints != null) ? fieldHints.id() : field.getName();
        String label = Inflector.getInstance().phrase(name);
        Optional<Component> componentByType = Component.getComponentByType(field.getType(),true);

        component = (component == null) ? componentByType.orElse(text()) : component;
        String lookupPath = null;
        String lookupFilter = null;
        if (component.getId().equals("lookup")) {
            Resource resource = ResourceRegistry.getByResourceClass(field.getType());
            if (resource == null) {
                throw new IllegalStateException("referenced unregistered resource "+field.getType().getName());
            }
            lookupPath = resource.getPath();
            lookupFilter = ResourceResolver.getLookupField(field.getType());
        }

        boolean readOnly = (fieldHints != null) ? fieldHints.readOnly() : !hasSetterMethod(field, owner);
        Collection<UXConstraint> constraints = new ArrayList<>();

        String groupName = "default";
        groupName = (fieldHints != null) ? fieldHints.group() : groupName;

        Annotation[] annotations = field.getAnnotations();
        for (Annotation annotation : annotations) {
            UXConstraints.getConstraint(annotation).ifPresent(constraints::add);
        }

        return new FormField(name,label,groupName,component,lookupPath,lookupFilter,readOnly,constraints.toArray(new UXConstraint[] {}));


    }




}
