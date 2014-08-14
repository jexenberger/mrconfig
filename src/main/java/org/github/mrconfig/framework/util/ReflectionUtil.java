package org.github.mrconfig.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;

/**
 * Created by julian3 on 2014/07/20.
 */
public class ReflectionUtil {

    public static Collection<Field> getAllFields(Class<?> type) {
        return getAllFields(type, null);
    }


    public static Collection<Field> getAllFields(Class<?> type, Predicate<Field> fieldFilter) {

        ArrayList<Field> fields = new ArrayList<Field>();
        Field[] baseFields = type.getDeclaredFields();
        for (Field baseField : baseFields) {
            if (fieldFilter != null) {
                if (fieldFilter.test(baseField)) {
                    fields.add(baseField);
                }
            } else {
                fields.add(baseField);
            }
        }
        if (!type.getSuperclass().getName().equals(Object.class.getName())) {
            fields.addAll(getAllFields(type.getSuperclass(), fieldFilter));
        }
        return fields;

    }


    public static String getMethod(Field property) {
        Objects.requireNonNull(property);
        Inflector inflector = Inflector.getInstance();
        if (Boolean.class.isAssignableFrom(property.getType()) || boolean.class.isAssignableFrom(property.getType())) {
            return "is"+ inflector.capitalise(property.getName());
        } else {
            return "get" + inflector.capitalise(property.getName());
        }

    }

    public static String setMethod(Field property) {
        Objects.requireNonNull(property);
        Inflector inflector = Inflector.getInstance();
        return "set" + inflector.capitalise(property.getName());
    }

    public static boolean hasSetterMethod(Field property, Class<?> parent) {
        Objects.requireNonNull(property);
        String method = setMethod(property);
        Method[] methods = parent.getMethods();
        Optional<Method> setMethod = Arrays.stream(methods)
                .filter((aMethod) -> aMethod.getName().equals(method) && aMethod.getParameterCount() == 1 && aMethod.getParameterTypes()[0].isAssignableFrom(property.getType()))
                .findFirst();
        return setMethod.isPresent();
    }
}
