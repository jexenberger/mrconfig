package org.github.levelthree;

import org.github.levelthree.angular.AngularResourceUX;
import org.github.levelthree.ux.View;

/**
 * Created by julian3 on 2014/09/21.
 */
public class UXProvider {


    public static ModuleUX getModuleUXInstance() {
        return new ModuleUX();
    }

    public static ResourceUX getResourceUXInstance(View listView, View getView, View editView, View createView) {
        return new AngularResourceUX();
    }

}
