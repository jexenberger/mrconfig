package org.github.levelthree.angular;

import org.github.levelthree.Resource;
import org.github.levelthree.ResourceRegistry;
import org.github.levelthree.ux.MyEntityController;
import org.github.levelthree.ux.form.BeanFormBuilder;
import org.junit.Test;

import java.io.StringWriter;
import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by julian3 on 2014/11/01.
 */
public class AngularScaffoldTest {

    @Test
    public void testScaffold() throws Exception {

        Resource resource = Resource.scaffold(MyEntityController.class);
        ResourceRegistry.register(resource);
        Map<String, AngularUXComponent> scaffold = AngularScaffold.scaffold(null, resource, () -> BeanFormBuilder.form(resource));
        assertNotNull(scaffold);
        assertEquals(4, scaffold.size());

        StringWriter out = new StringWriter();
        TemplateEngine.getConfiguration().getTemplate("edit_form.ftl").process(scaffold.get("edit"), out);
        System.out.println(out.toString());

        out = new StringWriter();
        TemplateEngine.getConfiguration().getTemplate("list_form.ftl").process(scaffold.get("list"), out);
        System.out.println(out.toString());

    }
}
