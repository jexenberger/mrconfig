package org.github.levelthree.angular;

import org.github.levelthree.Module;
import org.github.levelthree.ModuleRegistry;
import org.github.levelthree.Resource;
import org.github.levelthree.service.CRUDService;
import org.github.levelthree.ux.MyEntityController;
import org.github.levelthree.ux.Templating;
import org.github.levelthree.ux.View;
import org.github.levelthree.ux.form.BeanFormBuilder;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.easymock.EasyMock.createMock;
import static org.github.levelthree.util.IOUtil.pwd;
import static org.junit.Assert.assertNotNull;

/**
 * Created by julian3 on 2014/09/20.
 */
public class AngularApplicationResourceTest {

    @Before
    public void setUp() throws Exception {
        Templating.reset();
        AngularUXModule.reset();
        AngularUXModule.DEBUG_PATH = pwd() + "/src/main/resources/org/github/levelthree/angular";
        new AngularUXModule().init();
        ModuleRegistry.add(new Module("testModule1") {

            @Override
            public void init() {

                Resource resource = Resource.resource(MyEntityController.class, createMock(CRUDService.class))
                        .ux(new AngularResourceUX().formSupplier(BeanFormBuilder::form));
                register(resource);
            }
        });

        ModuleRegistry.add(new Module("testModule2") {

            @Override
            public void init() {
                Resource resource = Resource.resource(MyEntityController.class, createMock(CRUDService.class))
                        .ux(new AngularResourceUX());
                register(resource);

            }
        });

        ModuleRegistry.get("testModule1").ifPresent(Module::init);
        ModuleRegistry.get("testModule2").ifPresent(Module::init);
    }

    @Test
    public void testLoadModules() throws Exception {
        checkAndRunTemplate(new AngularApplicationResource().loadModules());
    }

    public void checkAndRunTemplate(Response response) throws IOException {
        assertNotNull(response);
        assertNotNull(response.getEntity());

        StreamingOutput output = (StreamingOutput) response.getEntity();
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        output.write(result);
        System.out.println(result.toString());
    }

    @Test
    public void testLoadModuleNavigation() throws Exception {
        checkAndRunTemplate(new AngularApplicationResource().getNavigation("testModule1"));
    }

    @Test
    public void testAllNavigation() throws Exception {
        checkAndRunTemplate(new AngularApplicationResource().getAllNavigation());
    }

    @Test
    public void testGetApplication() throws Exception {
        checkAndRunTemplate(new AngularApplicationResource().getApplication());
    }

    @Test
    public void testGetView() throws Exception {

        Response view = new AngularApplicationResource().getView("testModule1", "test", "edit");
        checkAndRunTemplate(view);

        view = new AngularApplicationResource().getView("testModule1", "test", "list");
        checkAndRunTemplate(view);

        view = new AngularApplicationResource().getView("testModule1", "test", "create");
        checkAndRunTemplate(view);

        view = new AngularApplicationResource().getView("testModule1", "test", "template");
        checkAndRunTemplate(view);

    }
}
