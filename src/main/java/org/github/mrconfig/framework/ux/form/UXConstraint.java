package org.github.mrconfig.framework.ux.form;

import org.github.mrconfig.framework.util.Pair;
import org.github.mrconfig.framework.ux.View;

import javax.validation.ConstraintValidator;
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
