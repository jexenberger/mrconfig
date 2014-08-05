package org.github.mrconfig.domain;

import org.github.mrconfig.service.BaseJPA;
import org.junit.Test;

import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;

import static org.github.mrconfig.domain.Property.importProperties;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by julian3 on 2014/07/19.
 */
public class EnvironmentTest extends BaseJPA {


    @Test
    public void testGetValues() throws Exception {

        Server server = new Server("test",null,"test","qweqwe","linux","test","test").save();
        InputStream resourceAsStream = getClass().getResourceAsStream("/test.properties");
        importProperties(server, resourceAsStream);

        Properties p = new Properties();
        p.load(getClass().getResourceAsStream("/test.properties"));

        Collection<PropertyValue> values = server.getValues();
        assertNotNull(values);
        assertEquals(p.size(),values.size());
    }

    @Test
    public void testGetValuesWithParentHierachy() throws Exception {
        EnvironmentGroup group = new EnvironmentGroup("parent",null).save();
        Server server = new Server("test",group,"test","qweqwe","linux","test","test").save();

        Properties groupP = new Properties();
        groupP.load(getClass().getResourceAsStream("/parent.properties"));

        Properties serverP = new Properties();
        serverP.load(getClass().getResourceAsStream("/test.properties"));

        importProperties(group, getClass().getResourceAsStream("/parent.properties"));
        importProperties(server, getClass().getResourceAsStream("/test.properties"));

        Collection<PropertyValue> values = server.getValues();
        assertNotNull(values);
        assertEquals(groupP.size()+serverP.size(), values.size());
        boolean foundAll = true;
        for (PropertyValue value : values) {
            foundAll &= (groupP.containsKey(value.getPropertyKey()) | serverP.containsKey(value.getPropertyKey()));
        }
        assertTrue(foundAll);

    }
}
