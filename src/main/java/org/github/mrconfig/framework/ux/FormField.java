package org.github.mrconfig.framework.ux;

import org.github.mrconfig.framework.macro.ResourceResolver;
import org.github.mrconfig.framework.util.Inflector;

import javax.validation.constraints.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.github.mrconfig.framework.util.ReflectionUtil.hasSetterMethod;
import static org.github.mrconfig.framework.ux.Component.text;

/**
 * Created by julian3 on 2014/07/19.
 */
public class FormField {


    static Collection<Class<? extends Number>> FLOATING_POINT_TYPES = asList(double.class, Double.class, float.class, Float.class, BigDecimal.class);
    static Collection<Class<? extends Number>> INTEGRALS = asList(Integer.class, int.class, Long.class, long.class, short.class, Short.class, BigInteger.class);

    String id;
    String label;
    Component type;
    String lookup;
    String lookupFilter;
    int length;
    int min;
    int max;
    String minDecimal;
    String maxDecimal;
    boolean required;
    boolean readonly;
    String pattern;
    boolean future;
    boolean past;

    public FormField(String id, String label, Component type, String lookup, String lookupFilter, int length, int min, int max, String minDecimal, String maxDecimal, boolean required, boolean readonly, String pattern, boolean future, boolean past) {
        this.id = id;
        this.label = label;
        this.type = type;
        this.lookup = lookup;
        this.lookupFilter = lookupFilter;
        this.length = length;
        this.min = min;
        this.max = max;
        this.minDecimal = minDecimal;
        this.maxDecimal = maxDecimal;
        this.required = required;
        this.readonly = readonly;
        this.pattern = pattern;
        this.future = future;
        this.past = past;
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


    public boolean isRequired() {
        return required;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public int getLength() {
        return length;
    }

    public String getMinDecimal() {
        return minDecimal;
    }

    public String getMaxDecimal() {
        return maxDecimal;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public String getPattern() {
        return pattern;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FormField{");
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
        boolean readOnly = hasSetterMethod(field, owner);
        String maxDecimal = null;
        String minDecimal = null;
        if (FLOATING_POINT_TYPES.contains(field.getType()) && field.isAnnotationPresent(DecimalMax.class)) {
            maxDecimal = field.getAnnotation(DecimalMax.class).value();
        }
        if (FLOATING_POINT_TYPES.contains(field.getType()) && field.isAnnotationPresent(DecimalMin.class)) {
            minDecimal = field.getAnnotation(DecimalMin.class).value();
        }
        long max;
        long min;
        if (INTEGRALS.contains(field.getType()) && field.isAnnotationPresent(Max.class)) {
            max = field.getAnnotation(Max.class).value();
        }
        if (FLOATING_POINT_TYPES.contains(field.getType()) && field.isAnnotationPresent(Min.class)) {
            min = field.getAnnotation(Min.class).value();
        }
        boolean future = false;
        boolean past = false;
        if (Date.class.isAssignableFrom(field.getType()) && field.isAnnotationPresent(Future.class))  {
             future = true;
        } else if (Date.class.isAssignableFrom(field.getType()) && field.isAnnotationPresent(Past.class)) {
            past = true;
        }
        if (field.isAnnotationPresent())
        return null;


    }




}
