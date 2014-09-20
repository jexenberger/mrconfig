package org.github.levelthree.ux.form;

import org.github.levelthree.util.Pair;
import org.github.levelthree.ux.View;

import java.util.Collection;

/**
 * Created by julian3 on 2014/08/15.
 */
public interface UXConstraint {

    String getId();
    View getView();
    boolean isElementDirective();
    boolean isAttribute();
    boolean isBoth();

    public Collection<Pair<String,String>> getAttributes();



}
