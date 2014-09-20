package org.github.levelthree.ux.form.constraints;

import org.github.levelthree.util.Pair;
import org.github.levelthree.ux.form.Name;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.github.levelthree.util.Pair.cons;

/**
 * Created by julian3 on 2014/08/15.
 */
public class MinConstraint extends IDConstraint{

    long min;

    public MinConstraint(@Name("min") long min) {
        this.min = min;
    }



    @Override
    public boolean isAttribute() {
        return true;
    }

    @Override
    public Collection<Pair<String,String>> getAttributes() {
        return asList(cons("min",Long.toString(min)));
    }
}
