package org.github.mrconfig.framework.macro;

import org.github.mrconfig.framework.activerecord.ActiveRecord;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;

import static java.util.Arrays.asList;

/**
 * Created by julian3 on 2014/07/20.
 */
public class TypeResolver {

    public static Collection<String> HIDDEN_GLOBAL = asList("version");
    public static Collection<String> READONLY_GLOBAL = asList("id");


    public static String getType(Field field) {
        Class<?> type = field.getType();
        String selectedType = selectType(field, type);
        System.out.println(selectedType+"=>"+type.getName());
        return selectedType;
    }

    private static String selectType(Field field, Class<?> type) {
        if (field.isAnnotationPresent(Id.class)) {
            return "Id";
        } else if (HIDDEN_GLOBAL.contains(field.getName())) {
            return "Hidden";
        } else if (READONLY_GLOBAL.contains(field.getName())) {
            return "ReadOnly";
        } else if (Number.class.isAssignableFrom(type)) {
            return "Number";
        }else if (Date.class.isAssignableFrom(type)) {
            return "Date";
        } else if (type.getName().equals("boolean") || Boolean.class.isAssignableFrom(type)) {
            return "Boolean";
        } else if (Enum.class.isAssignableFrom(type)) {
            return "Enum";
        } else if (type.isArray()  || type.getPackage().getName().startsWith("java.")) {
            return "Text";
        } else if (ActiveRecord.class.isAssignableFrom(type)) {
            return "Lookup";
        } else {
            throw new RuntimeException("unknown type" + type);
        }
    }

}
