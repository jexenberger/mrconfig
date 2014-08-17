package org.github.mrconfig.framework.ux.form.constraints;

import org.github.mrconfig.framework.util.Pair;
import org.github.mrconfig.framework.ux.StringView;
import org.github.mrconfig.framework.ux.View;
import org.github.mrconfig.framework.ux.form.Name;
import org.github.mrconfig.framework.ux.form.UXConstraint;

import javax.validation.Constraint;
import java.util.Collection;

import static java.util.Arrays.asList;
import static org.github.mrconfig.framework.util.Pair.cons;

/**
 * Created by julian3 on 2014/08/15.
 */
public class DecimalMaxConstraint extends IDConstraint{

    float max;


    public DecimalMaxConstraint( @Name("max") float max) {
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
        return asList(cons("max",Float.toString(max)));
    }


}
