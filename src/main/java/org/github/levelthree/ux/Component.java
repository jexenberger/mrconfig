package org.github.levelthree.ux;

import org.github.levelthree.activerecord.ActiveRecord;
import org.github.levelthree.util.Inflector;
import org.github.levelthree.util.ReflectionUtil;
import org.github.levelthree.ux.form.FormField;

import javax.ws.rs.core.Link;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Consumer;

import static org.github.levelthree.util.Pair.cons;
import static org.github.levelthree.util.ReflectionUtil.resolveMethod;

/**
 * Created by julian3 on 2014/08/11.
 */
public class Component {

    private static Map<String, Component> COMPONENT_REGISTRY = new HashMap<>();
    private static Map<Class<?>, Component> TYPE_REGISTRY = new HashMap<>();


    static {
        register(checkBox());
        register(date());
        register(hidden());
        register(lookup());
        register(readOnly());
        register(select());
        register(text());
        register(number());
    }

    String id;
    String name;
    String description;
    String templatePath;
    String type;
    Class<?>[] defaultDataTypes;
    String defaultHelp;
    Consumer<FormField> fieldMapper;

    public Component(String id, String name, String description, String templatePath, String type, String defaultHelp, Consumer<FormField> fieldMapper,  Class<?>... types) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.templatePath = templatePath;
        this.type = type;
        this.defaultDataTypes = types;
        this.defaultHelp = defaultHelp;
        this.fieldMapper = fieldMapper;
    }

    public Component(String id, String templatePath) {
        this.id = id;
        this.templatePath = templatePath;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public Class<?>[] getDefaultDataTypes() {
        return defaultDataTypes;
    }

    public static Map<String, Component> getRegistry() {
        return Collections.unmodifiableMap(COMPONENT_REGISTRY);
    }

    public static Component get(String id) {
        return COMPONENT_REGISTRY.get(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Component)) return false;

        Component component = (Component) o;

        if (description != null ? !description.equals(component.description) : component.description != null)
            return false;
        if (id != null ? !id.equals(component.id) : component.id != null) return false;
        if (name != null ? !name.equals(component.name) : component.name != null) return false;
        if (templatePath != null ? !templatePath.equals(component.templatePath) : component.templatePath != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (templatePath != null ? templatePath.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Component{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", templatePath='" + templatePath + '\'' +
                '}';
    }

    public static void register(Component component) {
        Objects.requireNonNull(component);
        Objects.requireNonNull(component.getId());
        Objects.requireNonNull(component.getTemplatePath());
        COMPONENT_REGISTRY.put(component.getId(), component);
        if (component.getDefaultDataTypes() != null && component.getDefaultDataTypes().length > 0) {
            for (Class<?> aClass : component.getDefaultDataTypes()) {
                TYPE_REGISTRY.put(aClass, component);
            }
        }
    }


    public static Component checkBox() {
        return new Component("checkbox", "Checkbox", "Checkbox component", "checkbox","checkbox","&nbsp;", null, boolean.class, Boolean.class);
    }

    public static Component date() {
        return new Component("date", "Date", "Date component", "date","text", "Enter date or select from Calendar",null, Date.class, java.sql.Date.class);
    }

    public static Component hidden() {
        return new Component("hidden", "Hidden", "Hidden Field component", "hidden", "hidden","&nbsp;", null);
    }

    public static Component lookup() {
        return new Component("lookup", "Lookup", "Lookup Field component", "lookup","text", "Enter id or search",null, Link.class, ActiveRecord.class);
    }

    /*
    public static Component file() {
        return new Component("file", "File", "File component", "text","file", "Select File",null, ByteArray.);
    } */

    public static Component readOnly() {
        return new Component("readonly", "Read Only", "ReadOnly Field component", "readonly", "readonly","&nbsp;", null);
    }

    public static Component select() {
        return new Component("select", "Read Only", "ReadOnly Field component", "select", "select","&nbsp;",(formField)-> {
            Object values = ReflectionUtil.invoke(resolveMethod(formField.getJavaType(), "values"), null);
            int length = Array.getLength(values);
            for (int i = 0; i < length; i++) {
                Enum<?> enumValue = (Enum<?>) Array.get(values,i);
                String label = Inflector.getInstance().phrase(enumValue.name());
                formField.getDefaultValueList().add(cons(enumValue.name(),label));
            }
        }, Enum.class);
    }

    public static Component number() {
        return new Component("number", "Number", "Number Field component", "text", "number","&nbsp;", null,
                Integer.class, int.class,
                Double.class, double.class,
                Float.class, float.class,
                Short.class, short.class,
                Byte.class, byte.class,
                long.class, Long.class,
                BigDecimal.class,
                BigInteger.class);
    }

    public static Component text() {
        return new Component("text", "Read Only", "ReadOnly Field component", "text","text", "&nbsp;",null,
                String.class,
                Character.class, char.class);
    }

    public static Optional<Component> getComponentByType(Class<?> dataType, boolean allowSubtypes) {
        Component component = TYPE_REGISTRY.get(dataType);
        if (component == null && allowSubtypes) {
            return TYPE_REGISTRY
                    .entrySet()
                    .stream()
                    .filter((entry) -> entry.getKey().isAssignableFrom(dataType))
                    .map((entry) -> entry.getValue())
                    .findFirst();
        }
        return Optional.ofNullable(component);
    }

    public String getDefaultHelp() {
        return defaultHelp;
    }

    public void mapFormField(FormField formField) {
        if (fieldMapper != null) {
            fieldMapper.accept(formField);
        }
    }

    public String getType() {
        return type;
    }
}
