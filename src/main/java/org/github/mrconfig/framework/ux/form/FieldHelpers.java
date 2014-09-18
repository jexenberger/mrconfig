package org.github.mrconfig.framework.ux.form;

import org.github.mrconfig.framework.ux.Component;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Stack;
import java.util.function.BiConsumer;

/**
 * Created by julian3 on 2014/09/18.
 */
public class FieldHelpers {

    private static Stack<FieldHelper> HELPERS;

    static {
        HELPERS = new Stack<>();
        HELPERS.push(new FieldHintsHelper());
    }


    public static void add(FieldHelper helper) {
       Objects.requireNonNull(helper,"helper cannot be null");
       HELPERS.push(helper);
    }


    public static void apply(FormField formField, Field field, Class<?> parent, Component component) {
        //iterate in reverse
        for (int i = HELPERS.size(); i > 0;i--) {
            HELPERS.get(i-1).consume(formField,field,parent,component);
        }
    }


}
