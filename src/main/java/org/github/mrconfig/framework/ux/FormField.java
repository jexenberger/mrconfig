package org.github.mrconfig.framework.ux;

import org.github.mrconfig.ResourceRegistry;
import org.github.mrconfig.framework.activerecord.Keyed;
import org.github.mrconfig.framework.activerecord.Named;
import org.github.mrconfig.framework.macro.ResourceResolver;
import org.github.mrconfig.framework.util.Inflector;
import org.github.mrconfig.framework.util.Pair;

import javax.validation.constraints.NotNull;
import java.beans.Beans;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

import static org.github.mrconfig.framework.ux.Component.text;

/**
 * Created by julian3 on 2014/07/19.
 */
public class FormField {

    private final Pair<String, String>[] options;
    String id;
    String label;
    Component type;
    String lookup;
    String lookupFilter;
    int length;
    double min;
    double max;
    boolean required;
    boolean readonly;

    public FormField(String id, String label, Component type, String lookup, String lookupFilter, boolean required, boolean readonly, Pair<String, String> ... options) {
        this.id = id;
        this.label = label;
        this.type = type;
        this.lookup = lookup;
        this.options = options;
        this.lookupFilter = lookupFilter;
        this.required = required;
        this.readonly = readonly;
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

    public Pair<String, String>[] getOptions() {
        return options;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public int getLength() {
        return length;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FormField{");
        sb.append("options=").append(Arrays.toString(options));
        sb.append(", id='").append(id).append('\'');
        sb.append(", label='").append(label).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", lookup='").append(lookup).append('\'');
        sb.append(", lookupFilter='").append(lookupFilter).append('\'');
        sb.append(", required=").append(required);
        sb.append(", readonly=").append(readonly);
        sb.append('}');
        return sb.toString();
    }

    public static FormField fromField(Field field, Class<?> owner, Component component) {
        String name = field.getName();
        String label = Inflector.getInstance().phrase(name);
        Optional<Component> componentByType = Component.getComponentByType(field.getType(),true);

        component = (component == null) ? componentByType.orElse(text()) : component;
        String lookupPath = null;
        String lookupFilter = null;
        if (component.getId().equals("lookup")) {
            lookupPath = ResourceResolver.getAbsoluteHref(field.getType());
            lookupFilter = ResourceResolver.getLookupField(field.getType());
        }
        boolean required = field.isAnnotationPresent(NotNull.class);
        return new FormField(name,label, component,lookupPath,lookupFilter,required,false);


    }


}
