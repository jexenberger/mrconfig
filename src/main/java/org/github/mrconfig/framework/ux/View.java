package org.github.mrconfig.framework.ux;

import java.io.*;
import java.util.Map;

/**
 * Created by julian3 on 2014/08/10.
 */
public interface View {


    void render(Map<String, Object> model, OutputStream target);

}
