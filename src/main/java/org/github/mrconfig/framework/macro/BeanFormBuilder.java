package org.github.mrconfig.framework.macro;

import org.github.mrconfig.framework.activerecord.Keyed;
import org.github.mrconfig.framework.activerecord.Named;
import org.github.mrconfig.framework.util.Pair;
import org.github.mrconfig.framework.util.ReflectionUtil;
import org.github.mrconfig.framework.util.Inflector;
import org.github.mrconfig.framework.ux.form.Form;
import org.github.mrconfig.framework.ux.form.FormField;

import javax.persistence.Column;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.github.mrconfig.framework.util.Pair.cons;
import static org.github.mrconfig.framework.macro.ResourceResolver.getAbsoluteHref;
import static org.github.mrconfig.framework.macro.ResourceResolver.getLookupField;

/**
 * Created by julian3 on 2014/07/20.
 */
public class BeanFormBuilder {




    public static Form form(Class<?> type, String id, boolean ignoreCollections, Function<Field, String> customTypeResolver) {

        Form form = new Form();
        Inflector inflector = Inflector.getInstance();
        form.setId(inflector.capitalise(id));
        form.setName(inflector.phrase(type.getSimpleName()));
        form.setResourceName(getAbsoluteHref(type));
        Collection<Field> declaredFields = ReflectionUtil.getAllFields(type);
        for (Field declaredField : declaredFields) {
            String fieldId = declaredField.getName();
            Class<?> declaredFieldType = declaredField.getType();
            Class<?> fieldClazz = declaredFieldType;
            if (ignoreCollections && Collection.class.isAssignableFrom(fieldClazz)) {
                continue;
            }
            String fieldType = null;
            if (customTypeResolver != null) {
                fieldType = customTypeResolver.apply(declaredField);
            }
            if (fieldType == null) {
                fieldType = TypeResolver.getType(declaredField);
            }
            if (fieldType == null) {
                fieldType = "Text";
            }
            String lookup = null;
            String lookupFilter = null;
            if (fieldType.equals("Lookup")) {
                lookup = getAbsoluteHref(declaredFieldType);
                lookupFilter = getLookupField(declaredFieldType);
            }
            Pair<String, String>[] options = null;
            if (fieldType.equals("Enum")) {
                try {
                    Object[] enumConstants = declaredFieldType.getEnumConstants();
                    options = new Pair[enumConstants.length];
                    int i = 0;
                    for (Object enumConstant : enumConstants) {
                        options[i++] = cons(enumConstant.toString(), inflector.phrase(enumConstant.toString()));
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            String label = inflector.phrase(declaredField.getName());

            boolean required = (fieldId.equals("id") || fieldId.equals("key") || declaredField.isAnnotationPresent(NotNull.class));

            FormField formField = new FormField(fieldId,label, null, lookup, lookupFilter,required, false, options);
            form.getFields().add(formField);

        }


        //get the indexes
        form.getField("id").ifPresent(form::addSearchField);
        if (Keyed.class.isAssignableFrom(type)) {
            form.getField("key").ifPresent(form::addSearchField);
        }
        if (Named.class.isAssignableFrom(type)) {
            form.getField("name").ifPresent(form::addSearchField);
        }
        Table table = type.getAnnotation(Table.class);
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
                String[]  colArray = columns.split(",");
                colArray = Arrays.stream(colArray).map(String::trim).collect(Collectors.toList()).toArray(new String[]{});
                for (String column : colArray) {
                    Field field = fields.get(column);
                    form.getField(field.getName()).ifPresent(form::addSearchField);
                }
            }

            declaredFields.forEach((field) -> {
                Column column = field.getAnnotation(Column.class);
                if (column != null && column.unique()) {
                    form.getField(field.getName()).ifPresent(form::addSearchField);
                }
                if (field.isAnnotationPresent(JoinColumn.class)) {
                    form.getField(field.getName()).ifPresent(form::addSearchField);
                }
            });
        }

        return form;

    }


}
