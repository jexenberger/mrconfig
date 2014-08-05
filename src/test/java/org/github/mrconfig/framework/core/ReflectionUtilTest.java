package org.github.mrconfig.framework.core;

import org.github.mrconfig.domain.KeyEntity;
import org.github.mrconfig.framework.util.ReflectionUtil;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by julian3 on 2014/07/20.
 */
public class ReflectionUtilTest {


    @Test
    public void testGetAllFields() throws Exception {

        Collection<Field> allFields = ReflectionUtil.getAllFields(KeyEntity.class);
        boolean foundAll = true;
        assertEquals(3, allFields.size());
        for (Field allField : allFields) {
            //check for fields both on key entity and on base entity
            foundAll &= allField.getName().equals("key") | allField.getName().equals("id") | allField.getName().equals("version");
            System.out.println(allField.getName());
        }
        assertTrue(foundAll);



    }

    @Test
    public void testGetAllFieldsFiltered() throws Exception {

        Collection<Field> allFields = ReflectionUtil.getAllFields(KeyEntity.class, (field)-> !field.getName().equals("version"));
        boolean foundAll = true;
        assertEquals(2, allFields.size());
        for (Field allField : allFields) {
            //check for fields both on key entity and on base entity
            foundAll &= allField.getName().equals("key") | allField.getName().equals("id");
            System.out.println(allField.getName());
        }
        assertTrue(foundAll);



    }
}
