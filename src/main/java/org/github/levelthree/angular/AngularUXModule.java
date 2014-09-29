package org.github.levelthree.angular;

import org.github.levelthree.Module;
import org.github.levelthree.ux.Templating;

import java.util.Collection;

/**
 * Created by julian3 on 2014/09/20.
 */
public class AngularUXModule extends Module {

    public static String DEBUG_PATH = null;

    private Collection<AngularUXComponent> components;

    public static void reset() {
        DEBUG_PATH = null;
    }

    @Override
    public void init() {
        if (DEBUG_PATH == null) {
            Templating.registerClass(AngularUXModule.class);
        } else {
            Templating.registerPath(DEBUG_PATH);
        }
        addResourceClass(AngularApplicationResource.class);
    }


}
