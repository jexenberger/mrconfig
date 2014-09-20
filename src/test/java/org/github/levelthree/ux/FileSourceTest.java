package org.github.levelthree.ux;

import org.junit.Test;

import java.io.File;

/**
 * Created by julian3 on 2014/08/11.
 */
public class FileSourceTest {

    @Test
    public void testNew() throws Exception {
        FileSource fileView = new FileSource(new File("pom.xml"));
    }


}
