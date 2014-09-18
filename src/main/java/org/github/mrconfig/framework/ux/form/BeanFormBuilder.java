package org.github.mrconfig.framework.ux.form;

import org.github.mrconfig.framework.Resource;
import org.github.mrconfig.framework.activerecord.ActiveRecord;
import org.github.mrconfig.framework.util.FieldHints;
import org.github.mrconfig.framework.util.Inflector;
import org.github.mrconfig.framework.util.ReflectionUtil;

import javax.persistence.Column;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.github.mrconfig.framework.util.Pair.cons;
import static org.github.mrconfig.framework.util.ReflectionUtil.resolveMethod;

/**
 * Created by julian3 on 2014/07/20.
 */
public class BeanFormBuilder {

    private Collection<Consumer<FormBuilder>> helpers;
    private Resource resource;

    public BeanFormBuilder(Resource resource, Consumer<FormBuilder> ... formBuildHelpers) {
        this.helpers =  (formBuildHelpers != null) ? asList(formBuildHelpers) : Collections.emptyList();
        this.resource = resource;
    }

    public FormBuilder create() {

        FormBuilder builder = FormBuilder.newInstance(resource);


        Class<?> resourceClass = resource.getResourceClass();
        return fromClass(builder, resourceClass, this.helpers.toArray(new Consumer[] {}));


    }

    public static FormBuilder fromClass(FormBuilder builder, Class<?> resourceClass, Consumer<FormBuilder> ... formHelpers) {
        Collection<Field> declaredFields = ReflectionUtil.getAllFields(resourceClass);
        for (Field declaredField : declaredFields) {
            if (Modifier.isStatic(declaredField.getModifiers())) {
                continue;
            }
            Class<?> declaredFieldType = declaredField.getType();
            Class<?> fieldClazz = declaredFieldType;
            if (Collection.class.isAssignableFrom(fieldClazz) || fieldClazz.isArray()) {
                continue;
            }
            if (
                !fieldClazz.isPrimitive() &&
                !fieldClazz.getPackage().getName().startsWith("java") &&
                !ActiveRecord.class.isAssignableFrom(fieldClazz) &&
                !Enum.class.isAssignableFrom(fieldClazz)
            ) {
                String group = getGroup(declaredField);
                List<FormField> fields = formBuilder(new Resource("/dummy", group, fieldClazz, null, null, null)).create().getForm().getFields();
                fields.forEach((field) -> field.setGroup(group));
                fields.forEach((field) -> field.setParent(declaredField.getName()));
                builder.addFields(fields);
            }  else {
                FormField field = FormField.fromField(declaredField, resourceClass, null);
                if (field.getId().equals("id")) {
                    field.setKey(true);
                }
                builder.addField(field);
            }
        }


        //get the indexes
        builder.withField("id", builder::addSearchField)
                .withField("key", builder::addSearchField)
                .withField("name",builder::addSearchField);


        Arrays.stream(formHelpers).forEach((helper) -> {
            helper.accept(builder);
        });



        return builder;
    }

    public static String getGroup(Field field) {
        Class<?> fieldClazz = field.getType();
        if (!field.isAnnotationPresent(FieldHints.class)) {
            return fieldClazz.getSimpleName();
        }
        FieldHints hints = field.getAnnotation(FieldHints.class);
        if (hints.group().equals("")) {
            return fieldClazz.getSimpleName();
        }
        return hints.group();
    }

    public BeanFormBuilder addHelper(Consumer<FormBuilder> helper) {
        this.helpers.add(helper);
        return this;
    }


    public static BeanFormBuilder formBuilder(Resource resource, Consumer<FormBuilder>... helpers) {

            return new BeanFormBuilder(resource,helpers);

    }

    public static Form form(Resource resource) {

            return formBuilder(resource,null).create().getForm();

    }


}
