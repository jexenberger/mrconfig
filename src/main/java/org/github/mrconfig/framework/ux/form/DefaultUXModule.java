package org.github.mrconfig.framework.ux.form;

import org.github.mrconfig.framework.Module;
import org.github.mrconfig.framework.resources.FormResource;
import org.github.mrconfig.framework.resources.StaticResource;
import org.github.mrconfig.framework.resources.MenuResource;
import org.github.mrconfig.framework.ux.Templating;

/**
 * Created by julian3 on 2014/08/17.
 */
public class DefaultUXModule extends Module{
    @Override
    public void init() {
        Templating.registerClass(Form.class);
        addResourceClass(StaticResource.class);
        addResourceClass(MenuResource.class);
        addResourceClass(FormResource.class);
    }
}
