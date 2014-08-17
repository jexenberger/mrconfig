package org.github.mrconfig.framework.ux.form.constraints;

import org.github.mrconfig.framework.ux.View;
import org.github.mrconfig.framework.ux.form.UXConstraint;

import javax.validation.Constraint;

/**
 * Created by julian3 on 2014/08/15.
 */
public abstract class BaseConstraint implements UXConstraint{

    String id;


    protected BaseConstraint() {
        this.id = getClass().getSimpleName().toLowerCase();
    }

    protected BaseConstraint(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

}
