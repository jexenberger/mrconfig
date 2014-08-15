package org.github.mrconfig.framework.macro;

import org.github.mrconfig.domain.Server;
import org.github.mrconfig.framework.macro.angular.TemplateEngine;
import org.github.mrconfig.framework.ux.form.Form;
import org.junit.Test;

import java.io.StringWriter;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by julian3 on 2014/07/20.
 */
public class BeanFormBuilderTest {


    @Test
    public void testForm() throws Exception {

        Form myEnvironment = BeanFormBuilder.form(Server.class, "servers", true, null);
        assertNotNull(myEnvironment);
        assertNotNull(myEnvironment.getId());
        assertNotNull(myEnvironment.getName());
        assertTrue(myEnvironment.getFields().size() > 0);
        myEnvironment.getFields().forEach((field)-> {
            assertNotNull(field.getId());
            assertNotNull(field.getLabel());
            assertNotNull(field.getType());
            if (field.getType().equals("Lookup")) {
                assertNotNull(field.getLookup());
            }
            if (field.getType().equals("Collection")) {
                fail("was supposed to ignore Collections");
            }
            if (field.getType().equals("List")) {
                fail("was supposed to ignore Collections");
            }
            if (field.getType().equals("Set")) {
                fail("was supposed to ignore Collections");
            }
            System.out.println(field);
        });
        assertNotNull(myEnvironment.getSearchFields());
        assertTrue(myEnvironment.getSearchFields().size() > 0);

        StringWriter out = new StringWriter();
        TemplateEngine.getConfiguration().getTemplate("edit_form.ftl").process(myEnvironment, out);
        System.out.println(out.toString());


    }


}
