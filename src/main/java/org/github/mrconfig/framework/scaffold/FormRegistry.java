package org.github.mrconfig.framework.scaffold;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.github.mrconfig.framework.scaffold.BeanFormBuilder.form;
import static org.github.mrconfig.framework.scaffold.ResourceResolver.getRelativeHref;

/**
 * Created by julian3 on 2014/07/20.
 */
public class FormRegistry {

    private static final FormRegistry REGISTRY = new FormRegistry();

    public static FormRegistry get() {
        return REGISTRY;
    }

    private Map<String, Form> forms;

    private FormRegistry() {
        forms = new LinkedHashMap<>();
    }

    public FormRegistry register(String entity, Form form) {
        synchronized (forms) {
            forms.put(entity, form);
        }
        return this;
    }

    public FormRegistry register(Class entity, Form form) {
        return register(getRelativeHref(entity),form);
    }

    public FormRegistry register(Class entity) {
        String formId = getRelativeHref(entity);
        return register(formId,form(entity,formId,true,null));
    }

    public Form getForm(String entity) {
        return forms.get(entity);
    }

    public Collection<String> getFormNames() {
        return forms.keySet();
    }

    public Collection<Form> getForms() {
        return forms.values();
    }




}
