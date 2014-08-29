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
                !fieldClazz.isEnum() &&
                !fieldClazz.isPrimitive() &&
                !fieldClazz.getPackage().getName().startsWith("java") &&
                !ActiveRecord.class.isAssignableFrom(fieldClazz
            )) {
                String group = (fieldClazz.isAnnotationPresent(FieldHints.class)) ? fieldClazz.getAnnotation(FieldHints.class).group() : fieldClazz.getSimpleName();
                List<FormField> fields = formBuilder(new Resource("/dummy", group, fieldClazz, null, null, null)).create().getForm().getFields();
                fields.forEach((field) -> field.setGroup(group));
                builder.addFields(fields);
            }
            if (Enum.class.isAssignableFrom(fieldClazz)) {
                Object values = ReflectionUtil.invoke(resolveMethod(fieldClazz, "values"), null);
                int length = Array.getLength(values);
                FormField field = FormField.fromField(declaredField, resourceClass, null);
                for (int i = 0; i < length; i++) {
                    Enum<?> enumValue = (Enum<?>) Array.get(values,i);
                    String label = Inflector.getInstance().phrase(enumValue.name());
                    field.getDefaultValueList().add(cons(enumValue.name(),label));
                }
                builder.addField(field);
            } else {
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

        Table table = resourceClass.getAnnotation(Table.class);
        if (table != null) {
            Index[] indexes = table.indexes();
            Map<String, Field> fields = declaredFields.stream().collect(Collectors.toMap((field) -> {
                String name = field.getName();
                Column annotation = field.getAnnotation(Column.class);
                if (annotation != null && annotation.name() != null && !annotation.name().equals("")) {
                    name = annotation.name();
                }
                JoinColumn join = field.getAnnotation(JoinColumn.class);
                if (join != null && join.name() != null && !join.name().equals("")) {
                    name = join.name();
                }
                return name;
            }, (val) -> val));
            for (Index index : indexes) {
                String columns = index.columnList();
                String[] colArray = columns.split(",");
                colArray = Arrays.stream(colArray).map(String::trim).collect(Collectors.toList()).toArray(new String[]{});
                for (String column : colArray) {
                    Field field = fields.get(column);
                    builder.withField(field.getName(), builder::addSearchField);
                }
            }

            declaredFields.forEach((field) -> {
                Column column = field.getAnnotation(Column.class);
                if (column != null && column.unique()) {
                    builder.withField(field.getName(), builder::addSearchField);
                }
                if (field.isAnnotationPresent(JoinColumn.class)) {
                    builder.withField(field.getName(), builder::addSearchField);
                }
            });
        }

        builder.sortAsc();

        return builder;
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
