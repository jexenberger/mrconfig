package org.github.mrconfig.framework.ux;

import org.github.mrconfig.framework.Resource;
import org.github.mrconfig.framework.testdomain.MyEntity;
import org.junit.Test;

import static org.github.mrconfig.framework.ux.FormBuilder.newInstance;

/**
 * Created by w1428134 on 2014/08/12.
 */
public class FormBuilderTest {


    @Test
    public void testBuild() throws Exception {
        FormBuilder builder = newInstance(new Resource("/test", "group", MyEntity.class, MyEntityController.class, null, null));
        builder.addField(new FormField("id","ID",Component.readOnly(),null, null, true,true));
    }
}
