package org.github.levelthree.angular;

import org.junit.Before;

import java.io.ByteArrayOutputStream;

import static org.github.levelthree.angular.AngularService.service;
import static org.github.levelthree.ux.TemplateView.templateView;
import static org.junit.Assert.assertTrue;

/**
 * Created by w1428134 on 2014/10/30.
 */
public abstract class BaseAngularTest {
    protected ByteArrayOutputStream outputStream;
    protected AngularUXComponent component;
    private AngularUXModule angularUXModule;

    @Before
    public void setup() throws Exception {
        angularUXModule = new AngularUXModule();
        angularUXModule.init();
        component = new AngularUXComponent();
        component
                .setControllerView(templateView("edit_controller.ftl"))
                .setControllerName("myTestControllerName")
                .setService(service("test", "resource/stuff"))
                .renderController(null, outputStream);
        outputStream = new ByteArrayOutputStream();

    }

    public void checkResult() {
        String result = outputStream.toString();
        System.out.println(result);
        assertTrue(result.length() > 0);
    }
}
