package org.github.mrconfig.framework.ux;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by julian3 on 2014/08/11.
 */
public class Component {

    private static Map<String, Component> COMPONENT_REGISTRY = new HashMap<>();


    static {
        register(checkBox());
        register(date());
        register(hidden());
        register(lookup());
        register(readOnly());
        register(select());
        register(text());
    }

    String id;
    String name;
    String description;
    String templatePath;

    public Component(String id, String name, String description, String templatePath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.templatePath = templatePath;
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

    public static Map<String, Component> getRegistry() {
        return Collections.unmodifiableMap(COMPONENT_REGISTRY);
    }

    public static Component get(String id) {
        return COMPONENT_REGISTRY.get(id);
    }

    public static void register(Component component) {
        Objects.requireNonNull(component);
        Objects.requireNonNull(component.getId());
        Objects.requireNonNull(component.getTemplatePath());
        COMPONENT_REGISTRY.put(component.getId(), component);
    }


    public static Component checkBox() {
        return new Component("checkbox","Checkbox","Checkbox component","checkbox.ftl");
    }

    public static Component date() {
        return new Component("date","Date","Date component","date.ftl");
    }

    public static Component hidden() {
        return new Component("hidden","Hidden","Hidden Field component","hidden.ftl");
    }

    public static Component lookup() {
        return new Component("lookup","Lookup","Lookup Field component","lookup.ftl");
    }

    public static Component readOnly() {
        return new Component("readonly","Read Only","ReadOnly Field component","readonly.ftl");
    }

    public static Component select() {
        return new Component("select","Read Only","ReadOnly Field component","select.ftl");
    }

    public static Component text() {
        return new Component("text","Read Only","ReadOnly Field component","text.ftl");
    }

}
