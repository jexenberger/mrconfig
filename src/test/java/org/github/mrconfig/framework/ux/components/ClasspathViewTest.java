package org.github.mrconfig.framework.ux.components;

import org.github.mrconfig.framework.ux.ClasspathView;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

/**
 * Created by julian3 on 2014/08/11.
 */
public class ClasspathViewTest {




    @Test
    public void testNew() throws Exception {

        ClasspathView classpathView = new ClasspathView("META-INF/persistence.xml");
        classpathView.render(new ByteArrayOutputStream());

    }
}
