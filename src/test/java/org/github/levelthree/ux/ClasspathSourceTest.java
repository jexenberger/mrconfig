package org.github.levelthree.ux;

import org.junit.Test;

import java.io.ByteArrayOutputStream;

/**
 * Created by julian3 on 2014/08/11.
 */
public class ClasspathSourceTest {




    @Test
    public void testNew() throws Exception {

        ClasspathSource classpathSource = new ClasspathSource("META-INF/persistence.xml");
        classpathSource.render(new ByteArrayOutputStream());

    }
}
