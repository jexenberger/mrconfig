package org.github.levelthree.ux.form.constraints;

import org.github.levelthree.util.Pair;
import org.github.levelthree.ux.StringView;
import org.github.levelthree.ux.View;
import org.github.levelthree.ux.form.Name;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.github.levelthree.util.Pair.cons;

/**
 * Created by julian3 on 2014/08/15.
 */
public class DecimalMaxConstraint extends IDConstraint{

    String max;


    public DecimalMaxConstraint( @Name("max") String max) {
        this.max = max;
    }

    @Override
    public View getView() {
        return new StringView(getId());
    }

    @Override
    public boolean isAttribute() {
        return true;
    }

    @Override
    public Collection<Pair<String,String>> getAttributes() {
        return asList(cons("max",max));
    }


}
