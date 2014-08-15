package org.github.mrconfig.framework.ux.form.constraints;

import org.github.mrconfig.framework.ux.StringView;
import org.github.mrconfig.framework.ux.View;
import org.github.mrconfig.framework.ux.form.UXConstraint;

import javax.validation.Constraint;

/**
 * Created by julian3 on 2014/08/15.
 */
public class DecimalMax implements UXConstraint{
    @Override
    public String getId() {
        return "decimal-max";
    }

    @Override
    public View getView() {
        return new StringView(getId());
    }
}
