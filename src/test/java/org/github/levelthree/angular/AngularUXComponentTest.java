package org.github.levelthree.angular;

import org.github.levelthree.Resource;
import org.github.levelthree.ResourceRegistry;
import org.github.levelthree.ux.MyEntityController;
import org.github.levelthree.ux.form.BeanFormBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.github.levelthree.angular.AngularService.service;
import static org.github.levelthree.ux.TemplateView.templateView;
import static org.junit.Assert.assertTrue;

/**
 * Created by julian3 on 2014/10/29.
 */
public class AngularUXComponentTest extends BaseAngularTest{


    @Test
    public void testRenderNavigation() throws Exception {

        component.setPath("test").setTemplateUrl("test.html");
        component.renderNavigation(null, outputStream);
        checkResult();

    }

    @Test
    public void testRenderController() throws Exception {

        component
                .setControllerView(templateView("edit_controller.ftl"))
                .setControllerName("myTestControllerName")
                .setService(service("test", "resource/stuff"))
                .renderController(null, outputStream);
        checkResult();
        component
                .setControllerView(templateView("list_controller.ftl"))
                .setControllerName("myTestListControllerName")
                .setService(service("test","resource/stuff"))
                .renderController(null, outputStream);
        checkResult();
    }

    @Test
    public void testRenderTemplate() throws Exception {

        Resource resource = Resource.resource(MyEntityController.class);
        ResourceRegistry.register(resource);

        component
                .setControllerView(templateView("edit_controller.ftl"))
                .setControllerName("myTestControllerName")
                .setService(service("test", "resource/stuff"))
                .setTemplate(templateView("edit_form.ftl"))
                .setForm(()-> BeanFormBuilder.form(resource))
                .setPath("/mypath")
                .renderTemplate(null, outputStream);
        checkResult();
        new AngularUXComponent()
                .setControllerView(templateView("list_controller.ftl"))
                .setControllerName("myTestListControllerName")
                .setService(service("test", "resource/stuff"))
                .setTemplate(templateView("list_form.ftl"))
                .setForm(()-> BeanFormBuilder.form(resource))
                .relation("edit", component)
                .renderTemplate(null, outputStream);
        checkResult();
    }

}
