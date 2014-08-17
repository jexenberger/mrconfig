package org.github.mrconfig.framework.ux.form.constraints;

import org.github.mrconfig.framework.util.Pair;
import org.github.mrconfig.framework.ux.form.Name;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.github.mrconfig.framework.util.Pair.cons;

/**
 * Created by julian3 on 2014/08/15.
 */
public class SizeConstraint extends IDConstraint {

    int min;
    int max;


    public SizeConstraint(@Name("min") int min, @Name("max") int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean isElementDirective() {
        return false;
    }

    @Override
    public boolean isAttribute() {
        return true;
    }

    @Override
    public boolean isBoth() {
        return false;
    }

    @Override
    public Collection<Pair<String,String>> getAttributes() {
        return asList(cons("minlength",Integer.toString(min)),cons("maxlength",Integer.toString(max)));
    }
}
