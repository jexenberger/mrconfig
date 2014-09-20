package org.github.levelthree.ux.form.constraints;

import org.github.levelthree.ux.form.UXConstraint;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseConstraint)) return false;

        BaseConstraint that = (BaseConstraint) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
