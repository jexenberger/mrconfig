package org.github.levelthree.angular;

import org.github.levelthree.Resource;

import java.io.OutputStream;

/**
 * Created by julian3 on 2014/10/30.
 */
public interface AngularElement {

    void render(Resource resource, OutputStream outputStream);

}
