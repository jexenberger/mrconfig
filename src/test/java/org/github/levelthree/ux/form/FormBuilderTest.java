package org.github.levelthree.ux.form;

import org.github.levelthree.Resource;
import org.github.mrconfig.framework.testdomain.MyEntity;
import org.github.levelthree.ux.Component;
import org.github.levelthree.ux.MyEntityController;
import org.junit.Test;

import static org.github.levelthree.ux.form.FormBuilder.newInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by w1428134 on 2014/08/12.
 */
public class FormBuilderTest {


    @Test
    public void testBuild() throws Exception {
        FormBuilder builder = newInstance(new Resource("/test", "group", MyEntity.class, MyEntityController.class, null, null));
        FormField formField = new FormField("id");
        formField.setGroup("group");
        formField.setType(Component.readOnly());
        builder.addField(formField);

        Form form = builder.getForm();
        assertNotNull(form);
        assertEquals(1, form.getFields().size());
    }
}
