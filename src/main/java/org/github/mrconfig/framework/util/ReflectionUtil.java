package org.github.mrconfig.framework.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
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
}
