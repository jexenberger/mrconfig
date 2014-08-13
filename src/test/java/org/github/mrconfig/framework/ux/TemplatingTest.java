package org.github.mrconfig.framework.ux;

import org.github.IntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by w1428134 on 2014/08/12.
 */
public class TemplatingTest {


    @BeforeClass
    public static void beforeClass() {
        Templating.registerClass(IntegrationTest.class);
        Templating.registerPath("C:\\Users\\w1428134\\Personal\\mrconfig\\src\\test\\resources\\test");
    }


    @Test
    public void testWrite() throws Exception {

        ByteArrayOutputStream target = new ByteArrayOutputStream();
        Map<String, Object> model = new HashMap<>();
        model.put("nested_template","/nested/nested.ftl");

        Templating.getTemplating().write("test.ftl",model, target);
        assertEquals("test",target.toString());
        target = new ByteArrayOutputStream();
        Templating.getTemplating().write("other.ftl",model, target);
        assertEquals("othernested",target.toString());
    }

    @AfterClass
    public static void after() {
        Templating.setTemplating(null);
    }
}
