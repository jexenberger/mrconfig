package org.github.levelthree.ux.form.constraints;

import org.github.levelthree.util.Pair;
import org.github.levelthree.ux.StringView;
import org.github.levelthree.ux.View;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by julian3 on 2014/08/15.
 */
public class IDConstraint extends BaseConstraint {



    @Override
    public View getView() {
        return new StringView(getId());
    }

    @Override
    public boolean isElementDirective() {
        return false;
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
    public Collection<Pair<String,String>> getAttributes() {
        return Collections.emptyList();
    }


}
