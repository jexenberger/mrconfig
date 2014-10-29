package org.github.levelthree.ux;

import java.io.OutputStream;

/**
 * Created by w1428134 on 2014/10/29.
 */
public interface RenderableComponent {

    void render(Object model, OutputStream target);

}
