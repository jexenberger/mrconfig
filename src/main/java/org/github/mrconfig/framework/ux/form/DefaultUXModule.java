package org.github.mrconfig.framework.ux.form;

import org.github.mrconfig.framework.Module;
import org.github.mrconfig.framework.macro.StaticResource;
import org.github.mrconfig.framework.resources.MenuResource;

/**
 * Created by julian3 on 2014/08/17.
 */
public class DefaultUXModule extends Module{
    @Override
    public void init() {
        addResourceClass(StaticResource.class);
        addResourceClass(MenuResource.class);
    }
}
