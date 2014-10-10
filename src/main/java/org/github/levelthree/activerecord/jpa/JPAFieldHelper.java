package org.github.levelthree.activerecord.jpa;

import org.github.levelthree.ux.Component;
import org.github.levelthree.ux.form.FieldHelper;
import org.github.levelthree.ux.form.FormField;
import org.github.levelthree.ux.form.constraints.DecimalMaxConstraint;
import org.github.levelthree.ux.form.constraints.RequiredConstraint;
import org.github.levelthree.ux.form.constraints.SizeConstraint;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by julian3 on 2014/09/18.
 */
public class JPAFieldHelper implements FieldHelper {


    static Map<Class<?>, Set<String>> INDEXED_FIELDS;

    static {
        INDEXED_FIELDS = new HashMap<>();
    }

    @Override
    public void consume(FormField formField, Field field, Class<?> parent, Component component) {
        if (field.isAnnotationPresent(Id.class)) {
            formField.setKey(true);
            formField.setSearchable(true);
        }
        if (field.isAnnotationPresent(GeneratedValue.class) || field.isAnnotationPresent(Version.class)) {
            formField.setType(Component.readOnly());
            formField.setReadOnly(true);
        }

        if (field.getType().isAnnotationPresent(Entity.class)) {
            formField.setSearchable(true);
        }

        Table table = parent.getAnnotation(Table.class);
        Set<String> indexColumns = INDEXED_FIELDS.get(parent);
        if (indexColumns == null) {
            indexColumns = new HashSet<>();
            INDEXED_FIELDS.put(parent, indexColumns);
        }
        if (table != null) {

            Index[] indexes = table.indexes();
            for (Index index : indexes) {
                String columnList = index.columnList();
                String[] columns = columnList.split(",");
                if (columns[0].trim().equals("")) {
                    continue;
                }
                for (String column : columns) {
                    INDEXED_FIELDS.get(parent).add(column.trim());
                }
            }

        }

        if (field.isAnnotationPresent(Column.class)) {
            addColumnConstraints(field.getAnnotation(Column.class), formField, field, parent);
        } else if (INDEXED_FIELDS.get(parent).contains(field.getName())){
            formField.setSearchable(true);
        }

    }

    public void addColumnConstraints(Column column, FormField formField, Field field, Class parent) {
        if (!column.name().equals("") && INDEXED_FIELDS.get(parent).contains(column.name())) {
            formField.setSearchable(true);
        }

        if (column.unique()) {
            formField.setSearchable(true);
        }

        if (!column.nullable()) {
            formField.getConstraints().add(new RequiredConstraint());
        }

        if (column.length() > 0) {
            formField.getConstraints().add(new SizeConstraint(0, column.length()));
        }
        String maxDecimalConstraint = "";
        if (column.precision() > 0) {
            int numSignificant = column.precision()-column.scale();
            if (numSignificant < 1) {
                throw new IllegalStateException(field.getName()+" on "+parent.getName()+" has invalid scale/precision settings on it's "+Column.class.getName()+" annotation");
            }
            for (int i = 0; i < numSignificant; i++) {
                maxDecimalConstraint += "9";
            }
        }
        if (column.scale() > 0) {
            maxDecimalConstraint += ".";
            for (int i = 0; i < column.scale(); i++) {
                maxDecimalConstraint += "9";
            }
        }
        if (!maxDecimalConstraint.equals("")) {
            formField.getConstraints().add(new DecimalMaxConstraint(maxDecimalConstraint));
        }
    }


}
