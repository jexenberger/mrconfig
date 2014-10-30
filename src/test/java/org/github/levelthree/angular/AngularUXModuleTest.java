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
public class AngularUXModuleTest {

    @Test
    public void testRenderModule() throws Exception {

        Resource resource = Resource.resource(MyEntityController.class);
        ResourceRegistry.register(resource);

        AngularUXComponent component = new AngularUXComponent()
                .setControllerView(templateView("edit_controller.ftl"))
                .setControllerName("myTestControllerName")
                .setService(service("test", "resource/stuff"))
                .setTemplate(templateView("edit_form.ftl"))
                .setForm(BeanFormBuilder.form(resource))
                .setPath("/mypath");


        AngularUXModule module = new AngularUXModule();
        module.addComponent(component);
        module.init();
        module.renderModule(ModuleRegistry.DEFAULT_MODULE, new ByteArrayOutputStream());
    }
}
