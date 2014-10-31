package org.github.levelthree.angular;

import org.github.levelthree.ModuleRegistry;
import org.github.levelthree.Resource;
import org.github.levelthree.ResourceRegistry;
import org.github.levelthree.ux.MyEntityController;
import org.github.levelthree.ux.form.BeanFormBuilder;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.github.levelthree.angular.AngularService.service;
import static org.github.levelthree.ux.TemplateView.templateView;

/**
 * Created by w1428134 on 2014/10/30.
 */
public class AngularUXModuleTest extends BaseAngularTest{

    @Test
    public void testRenderModule() throws Exception {


        angularUXModule.addComponent(component);
        angularUXModule.init();
        angularUXModule.renderModule(ModuleRegistry.DEFAULT_MODULE, outputStream);
        checkResult();

    }
}
