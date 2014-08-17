package org.github.mrconfig.framework.util;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.github.mrconfig.framework.util.ReflectionUtil.resolveField;
import static org.junit.Assert.assertEquals;

/**
 * Created by w1428134 on 2014/08/04.
 */
public class GenericsUtilTest {


    List<String> strings;

    @Test
    public void testGetType() throws Exception {
        assertEquals(String.class, GenericsUtil.getClass(ConcreteObject.class, 0));

    }

    @Test
    public void testGetGenericType() throws Exception {

        Field field = resolveField(getClass(), "strings");
        assertEquals(String.class, GenericsUtil.getGenericType(field));


    }
}
