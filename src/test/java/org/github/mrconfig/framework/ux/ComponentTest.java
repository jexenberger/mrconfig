package org.github.mrconfig.framework.ux;

import org.github.mrconfig.framework.activerecord.ActiveRecord;
import org.github.mrconfig.framework.testdomain.MyEntity;
import org.junit.Test;

import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

/**
 * Created by w1428134 on 2014/08/12.
 */
public class ComponentTest {


    @Test
    public void testGetComponentByType() throws Exception {

        Optional<Component> componentByType = Component.getComponentByType(String.class, false);
        assertTrue(componentByType.isPresent());

        componentByType = Component.getComponentByType(Date.class, false);
        assertTrue(componentByType.isPresent());

        componentByType = Component.getComponentByType(Long.class, false);
        assertTrue(componentByType.isPresent());

        componentByType = Component.getComponentByType(long.class, false);
        assertTrue(componentByType.isPresent());


        componentByType = Component.getComponentByType(MyEntity.class, true);
        assertTrue(componentByType.isPresent());



    }
}
