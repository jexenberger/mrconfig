package org.github.levelthree.ux.form;

import org.github.levelthree.ResourceRegistry;
import org.github.levelthree.Resource;
import org.github.levelthree.angular.TemplateEngine;
import org.github.mrconfig.framework.testdomain.MyEntity;
import org.github.levelthree.ux.MyEntityController;
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


        Resource group = new Resource("/test", "group", MyEntity.class, MyEntityController.class, null, null);
        ResourceRegistry.register(group);
        Form myEnvironment = BeanFormBuilder.formBuilder(group).create().getForm();
        assertNotNull(myEnvironment);
        assertNotNull(myEnvironment.getId());
        assertNotNull(myEnvironment.getName());
        assertTrue(myEnvironment.getFields().size() > 0);
        myEnvironment.getFields().forEach((field)-> {
            assertNotNull(field.getId());
            assertNotNull(field.getLabel());
            assertNotNull(field.getType());
            if (field.getType().getId().equals("lookup")) {
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
        });
        assertNotNull(myEnvironment.getSearchFields());
        assertTrue(myEnvironment.getSearchFields().size() > 0);

        StringWriter out = new StringWriter();
        TemplateEngine.getConfiguration().getTemplate("edit_form.ftl").process(myEnvironment, out);
        System.out.println(out.toString());

        out = new StringWriter();
        TemplateEngine.getConfiguration().getTemplate("list_form.ftl").process(myEnvironment, out);
        System.out.println(out.toString());


    }


}
