package org.github.mrconfig.framework.ux.form;

import org.github.mrconfig.framework.Resource;
import org.github.mrconfig.framework.ResourceRegistry;
import org.github.mrconfig.framework.service.CRUDService;
import org.github.mrconfig.framework.testdomain.MyEntity;
import org.github.mrconfig.framework.util.ReflectionUtil;
import org.github.mrconfig.framework.ux.MyEntityController;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Collection;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by julian3 on 2014/09/18.
 */
public class FieldHintsHelperTest {


    @Before
    public void setUp() throws Exception {
        ResourceRegistry.register(Resource.resource(MyEntityController.class, null, createMock(CRUDService.class)));

    }


    @Test
    public void testApply() throws Exception {

        Collection<Field> allFields = ReflectionUtil.getAllFields(MyEntity.class);
        boolean foundFieldHints = false;
        boolean foundSimpleHints = false;
        for (Field field : allFields) {
            FormField formField = FormField.fromField(field, MyEntity.class, null);
            new FieldHintsHelper().consume(formField,field,MyEntity.class, formField.getType());
            if (field.getName().equals("fieldHints")) {
                foundFieldHints = true;
                assertEquals("group", formField.getGroup());
                assertEquals(true, formField.isReadOnly());
                assertEquals("qwerty123", formField.getId());
                assertEquals("lalala", formField.getDefaultValue());
                assertEquals(10, formField.getTabIndex());
                assertEquals(100, formField.getOrder());
                assertEquals(true, formField.isSearchable());
            }
            if (field.getName().equals("simpleHints")) {
                foundSimpleHints = true;
                assertEquals("default", formField.getGroup());
                assertEquals(false, formField.isReadOnly());
                assertEquals("simpleHints", formField.getId());
                assertEquals(null, formField.getDefaultValue());
                assertEquals(-1, formField.getTabIndex());
                assertEquals(-1, formField.getOrder());
                assertEquals(false, formField.isSearchable());
            }

        }
        assertTrue("should have been a field called fieldHints", foundFieldHints);
        assertTrue("should have been a field called simpleHints", foundSimpleHints);

    }
}

