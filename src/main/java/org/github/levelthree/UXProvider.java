package org.github.levelthree;

import org.github.levelthree.angular.AngularResourceUX;
import org.github.levelthree.ux.View;
import org.github.levelthree.ux.form.Form;

import java.util.function.Function;

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

    public static ResourceUX getResourceUXInstance(Function<Resource, Form> formSupplier) {
        AngularResourceUX angularResourceUX = new AngularResourceUX();
        return angularResourceUX.formSupplier(formSupplier);
    }

}
