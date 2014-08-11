package org.github.mrconfig.framework.ux.components;

import org.github.mrconfig.framework.ux.Component;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by julian3 on 2014/08/11.
 */
public class ComponentTest {

    @Test
    public void testRegister() throws Exception {

        Component.register(new Component("test", "test.ftl"));
        Component component = Component.get("test");

        assertNotNull(component);


    }


    @Test
    public void testGetComponentRegistry() throws Exception {

        Map<String, Component> registry = Component.getRegistry();
        assertNotNull(registry);
        assertTrue(registry.size() > 0);
    }
}
