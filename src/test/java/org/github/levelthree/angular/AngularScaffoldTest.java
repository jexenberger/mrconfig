package org.github.levelthree.angular;

import org.github.levelthree.Resource;
import org.github.levelthree.ux.MyEntityController;
import org.github.levelthree.ux.form.BeanFormBuilder;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by julian3 on 2014/11/01.
 */
public class AngularScaffoldTest {

    @Test
    public void testScaffold() throws Exception {

        Resource resource = Resource.scaffold(MyEntityController.class);
        Collection<AngularUXComponent> scaffold = AngularScaffold.scaffold(null, resource, ()-> BeanFormBuilder.form(resource));
        assertNotNull(scaffold);
        assertEquals(4, scaffold.size());

    }
}
