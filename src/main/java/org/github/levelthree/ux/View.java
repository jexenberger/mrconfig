package org.github.levelthree.ux;

import java.io.*;

/**
 * Created by julian3 on 2014/08/10.
 */
public interface View {


    void render(Object model, OutputStream target);

}
