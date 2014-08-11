package org.github.mrconfig.framework.ux;

import org.junit.Test;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by julian3 on 2014/08/11.
 */
public class FileViewTest {

    @Test
    public void testNew() throws Exception {
        FileView fileView = new FileView(new File("pom.xml"));
    }


}
