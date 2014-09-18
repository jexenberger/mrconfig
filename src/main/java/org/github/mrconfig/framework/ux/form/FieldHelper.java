package org.github.mrconfig.framework.ux.form;

import org.github.mrconfig.framework.ux.Component;

import java.lang.reflect.Field;

/**
 * Created by julian3 on 2014/09/18.
 */
@FunctionalInterface
public interface FieldHelper {


    void consume(FormField formField, Field field, Class<?> parent, Component component);

}
