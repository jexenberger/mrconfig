package org.github.mrconfig.framework.activerecord.jpa;

import org.github.mrconfig.framework.Resource;
import org.github.mrconfig.framework.ResourceRegistry;
import org.github.mrconfig.framework.service.CRUDService;
import org.github.mrconfig.framework.testdomain.MyEntity;
import org.github.mrconfig.framework.util.Pair;
import org.github.mrconfig.framework.util.ReflectionUtil;
import org.github.mrconfig.framework.ux.MyEntityController;
import org.github.mrconfig.framework.ux.form.FormField;
import org.github.mrconfig.framework.ux.form.UXConstraint;
import org.github.mrconfig.framework.ux.form.constraints.DecimalMaxConstraint;
import org.github.mrconfig.framework.ux.form.constraints.RequiredConstraint;
import org.github.mrconfig.framework.ux.form.constraints.SizeConstraint;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by julian3 on 2014/09/18.
 */

public class JPAFieldHelperTest {


    @Before
    public void setUp() throws Exception {
        ResourceRegistry.register(Resource.resource(MyEntityController.class,null,createMock(CRUDService.class)));

    }

    @Test
    public void testApply() throws Exception {

        Collection<Field> allFields = ReflectionUtil.getAllFields(MyEntity.class);
        for (Field field : allFields) {
            FormField formField = FormField.fromField(field, MyEntity.class, null);
            new JPAFieldHelper().consume(formField, field, MyEntity.class, formField.getType());
            assertTrue(JPAFieldHelper.INDEXED_FIELDS.containsKey(MyEntity.class));
            assertTrue(JPAFieldHelper.INDEXED_FIELDS.get(MyEntity.class).contains("value"));
            assertTrue(JPAFieldHelper.INDEXED_FIELDS.get(MyEntity.class).contains("enumType"));

            if (field.getName().equals("enumType")) {
                assertTrue(formField.isSearchable());
            }

            if (field.getName().equals("value")) {
                assertTrue(formField.isSearchable());
            }

            if (field.getName().equals("randomColumn")) {
                Collection<UXConstraint> constraints = formField.getConstraints();
                boolean foundRequired = false;
                boolean foundMaxDecimalConstraint = false;
                boolean foundSizeConstraint = false;
                for (UXConstraint constraint : constraints) {
                    if (constraint instanceof RequiredConstraint) {
                        foundRequired = true;
                    }
                    if (constraint instanceof DecimalMaxConstraint) {
                        foundMaxDecimalConstraint = true;
                        assertEquals("99999999.99", constraint.getAttributes().iterator().next().getRight());
                    }
                    if (constraint instanceof SizeConstraint) {
                        foundSizeConstraint = true;
                        Iterator<Pair<String, String>> iterator = constraint.getAttributes().iterator();
                        iterator.next();
                        assertEquals("10", iterator.next().getRight());
                    }
                }
                assertTrue("should have had a required constraint", foundRequired);
                assertTrue("should have had a DecimalMaxConstaint constraint", foundMaxDecimalConstraint);
                assertTrue("should have had a SizeConstraint constraint", foundSizeConstraint);

                assertTrue(formField.isSearchable());


            }

            if (field.getName().equals("id")) {
                assertTrue(formField.isKey());
                assertTrue(formField.isSearchable());
                assertTrue(formField.isReadOnly());
            }

            if (field.getName().equals("parent")) {
                assertTrue(formField.isSearchable());
            }

        }



    }
}
