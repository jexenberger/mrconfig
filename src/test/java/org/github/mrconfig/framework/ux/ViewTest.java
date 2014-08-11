package org.github.mrconfig.framework.ux;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by julian3 on 2014/08/11.
 */
public class ViewTest {


    @Test
    public void testRender() throws Exception {

        String helloWorld = "hello world";
        View toTest = () -> new ByteArrayInputStream(helloWorld.getBytes());

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        toTest.render(output);
        assertEquals(helloWorld, output.toString());

    }
}
