package org.github.mrconfig.framework.ux.form;

import org.github.mrconfig.framework.ResourceRegistry;
import org.github.mrconfig.framework.Resource;
import org.github.mrconfig.framework.util.ResourceResolver;
import org.github.mrconfig.framework.util.Inflector;
import org.github.mrconfig.framework.util.Pair;
import org.github.mrconfig.framework.ux.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

import static java.util.Arrays.asList;
import static org.github.mrconfig.framework.util.ReflectionUtil.hasSetterMethod;
import static org.github.mrconfig.framework.ux.Component.text;

/**
 * Created by julian3 on 2014/07/19.
 */
public class FormField {



    String parent;
    String id;
    String label;
    Component type;
    String lookup;
    String lookupFilter;
    boolean readOnly;
    Set<UXConstraint> constraints;
    String group;
    Set<Pair<String,String>> defaultValueList;
    boolean key;
    boolean indexed;
    String uuid;
    String helpText = "&nbsp;";
    String defaultValue = null;
    int tabIndex = -1;
    int order = -1;
    Class<?> javaType;
    boolean searchable;



    public FormField(String id) {
        this.id = id;
    }

    void setId(String id) {
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
        this.constraints = new LinkedHashSet<>(asList(constraints));
    }




    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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

    public void setKey(boolean key) {
        this.key = key;
    }

    public boolean isKey() {
        return key;
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
        if (type != null) {
            type.mapFormField(this);
        }
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


    public Set<UXConstraint> getConstraints() {
        return constraints;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setConstraints(Set<UXConstraint> constraints) {
        this.constraints = constraints;
    }

    public Set<Pair<String, String>> getDefaultValueList() {
        if (defaultValueList == null) {
            defaultValueList = new LinkedHashSet<>();
        }
        return defaultValueList;
    }

    public void setDefaultValueList(Set<Pair<String, String>> defaultValueList) {
        this.defaultValueList = defaultValueList;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public boolean isIndexed() {
        return indexed;
    }

    public void setIndexed(boolean indexed) {
        this.indexed = indexed;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public void setJavaType(Class<?> javaType) {
        this.javaType = javaType;
    }

    public boolean isSearchable() {
        return searchable;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public static FormField fromField(Field field, Class<?> owner, Component component) {
        String name = field.getName();
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

        boolean readOnly =  !hasSetterMethod(field, owner);
        Collection<UXConstraint> constraints = new ArrayList<>();

        String groupName = "default";


        Annotation[] annotations = field.getAnnotations();
        for (Annotation annotation : annotations) {
            UXConstraints.getConstraint(annotation).ifPresent(constraints::add);
        }


        FormField formField = new FormField(name);

        formField.setLabel(label);
        formField.setGroup(groupName);
        formField.setLookup(lookupPath);
        formField.setLookupFilter(lookupFilter);
        formField.setReadOnly(readOnly);
        formField.setConstraints(new HashSet<>(constraints));
        formField.setHelpText(component.getDefaultHelp());
        formField.setJavaType(field.getType());
        formField.setSearchable(false);
        formField.setType(component);

        FieldHelpers.apply(formField, field, owner, component);

        return formField;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    public String getHelpText() {
        return helpText;
    }

    public String getUuid() {
        if (uuid == null) {

            uuid = ((parent != null) ? parent + "_" : "") +  (isIndexed() ? "_idx_" : "")  + id;
        }
        return uuid;
    }
}
