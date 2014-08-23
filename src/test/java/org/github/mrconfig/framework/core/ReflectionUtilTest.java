package org.github.mrconfig.framework.core;

import org.github.mrconfig.domain.KeyEntity;
import org.github.mrconfig.framework.util.ReflectionUtil;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by julian3 on 2014/07/20.
 */
public class ReflectionUtilTest {

    public boolean test1;
    public Boolean test2;
    public String test3;
    public Number test4;

    @Test
    public void testGetAllFields() throws Exception {

        Collection<Field> allFields = ReflectionUtil.getAllFields(KeyEntity.class);
        boolean foundAll = true;
        assertEquals(2, allFields.size());
        for (Field allField : allFields) {
            //check for fields both on key entity and on base entity
            foundAll &=  allField.getName().equals("id") | allField.getName().equals("version");
            System.out.println(allField.getName());
        }
        assertTrue(foundAll);



    }

    @Test
    public void testGetAllFieldsFiltered() throws Exception {

        Collection<Field> allFields = ReflectionUtil.getAllFields(KeyEntity.class, (field)-> !field.getName().equals("version"));
        boolean foundAll = true;
        assertEquals(1, allFields.size());
        for (Field allField : allFields) {
            //check for fields both on key entity and on base entity
            foundAll &=   allField.getName().equals("id");
            System.out.println(allField.getName());
        }
        assertTrue(foundAll);



    }

    @Test
    public void testGetMethod() throws Exception {


        Field primitiveBoolean = getClass().getField("test1");
        String method = ReflectionUtil.getMethod(primitiveBoolean);
        assertEquals("isTest1",method);

        Field wrapperBoolean = getClass().getField("test2");
        method = ReflectionUtil.getMethod(wrapperBoolean);
        assertEquals("isTest2",method);

        Field stringField = getClass().getField("test3");
        method = ReflectionUtil.getMethod(stringField);
        assertEquals("getTest3",method);

    }

    @Test
    public void testSetMethod() throws Exception {

        Field primitiveBoolean = getClass().getField("test1");
        String method = ReflectionUtil.setMethod(primitiveBoolean);
        assertEquals("setTest1",method);
    }

    @Test
    public void testHasSetter() throws Exception {

        Field setterField = getClass().getField("test1");
        assertTrue(ReflectionUtil.hasSetterMethod(setterField, getClass()));

        Field noSetterField = getClass().getField("test2");
        assertFalse(ReflectionUtil.hasSetterMethod(noSetterField, getClass()));

        Field widenedField = getClass().getField("test3");
        assertFalse(ReflectionUtil.hasSetterMethod(widenedField, getClass()));


    }

    public void setTest1(boolean test1) {
        this.test1 = test1;
    }

    public void setTest4(Long test4) {
        this.test4 = test4;
    }
}
