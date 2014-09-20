package org.github.levelthree.ux.form.constraints;

/**
 * Created by julian3 on 2014/08/15.
 */
public class RequiredConstraint extends IDConstraint{

    @Override
    public boolean isElementDirective() {
        return true;
    }

    @Override
    public boolean isAttribute() {
        return false;
    }

    @Override
    public boolean isBoth() {
        return false;
    }

    @Override
    public String getId() {
        return "required";
    }


}
