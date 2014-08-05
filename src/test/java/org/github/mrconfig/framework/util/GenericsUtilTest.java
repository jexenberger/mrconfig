package org.github.mrconfig.framework.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by w1428134 on 2014/08/04.
 */
public class GenericsUtilTest {


    @Test
    public void testGetType() throws Exception {
        assertEquals(String.class, GenericsUtil.getClass(ConcreteObject.class, 0));

    }
}
