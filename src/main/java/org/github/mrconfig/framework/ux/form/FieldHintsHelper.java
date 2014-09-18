package org.github.mrconfig.framework.ux.form;

import org.github.mrconfig.framework.util.FieldHints;
import org.github.mrconfig.framework.ux.Component;

import java.lang.reflect.Field;

/**
 * Created by julian3 on 2014/09/18.
 */
public class FieldHintsHelper implements FieldHelper {
    @Override
    public void consume(FormField formField, Field field, Class<?> parent, Component component) {

        FieldHints fieldHints = field.getAnnotation(FieldHints.class);
        if (fieldHints == null) {
            return;
        }

        if (!fieldHints.id().equals("")) {
            formField.setId(fieldHints.id());
        }

        //only override if the field is not already read only
        if (!formField.isReadOnly() && fieldHints.readOnly()) {
            formField.setReadOnly(fieldHints.readOnly());
        }
        //only override if the field is not already searchable
        if (!formField.isSearchable() && fieldHints.searchable()) {
            formField.setSearchable(fieldHints.searchable());
        }

        if (!fieldHints.group().equals("")) {
            formField.setGroup(fieldHints.group());
        }

        if (fieldHints.order() > -1) {
            formField.setOrder(fieldHints.order());
        }
        if (fieldHints.tabIndex() > -1) {
            formField.setTabIndex(fieldHints.tabIndex());
        }
        if (!fieldHints.defaultValue().equals("")) {
            formField.setDefaultValue(fieldHints.defaultValue());
        }





    }
}
