package org.github.levelthree.ux.form;

import org.github.levelthree.Module;
import org.github.levelthree.resources.FormResource;
import org.github.levelthree.resources.StaticResource;
import org.github.levelthree.resources.MenuResource;
import org.github.levelthree.ux.Templating;

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
