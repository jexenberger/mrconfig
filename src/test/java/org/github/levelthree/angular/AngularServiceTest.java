package org.github.levelthree.angular;

import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.github.levelthree.angular.AngularService.service;

/**
 * Created by w1428134 on 2014/10/30.
 */
public class AngularServiceTest extends BaseAngularTest {

    @Test
    public void testRender() throws Exception {

        service("test", "resource/stuff").render(null, outputStream);
        checkResult();

    }
}
