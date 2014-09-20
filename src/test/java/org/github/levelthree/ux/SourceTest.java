package org.github.levelthree.ux;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by julian3 on 2014/08/11.
 */
public class SourceTest {


    @Test
    public void testRender() throws Exception {

        String helloWorld = "hello world";
        Source toTest = new Source() {
            @Override
            public InputStream getSource() {
                return new ByteArrayInputStream(helloWorld.getBytes());
            }

            @Override
            public String getPath() {
                return "";
            }
        };





        ByteArrayOutputStream output = new ByteArrayOutputStream();
        toTest.render(output);
        assertEquals(helloWorld, output.toString());

    }
}
