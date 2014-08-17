package org.github.mrconfig.framework;

import org.github.mrconfig.framework.UXModule;
import org.github.mrconfig.framework.ux.View;
import org.github.mrconfig.framework.ux.form.Form;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static org.easymock.EasyMock.*;

/**
 * Created by julian3 on 2014/08/11.
 */
public class UXModuleTest {

    @Test
    public void testRender() throws Exception {

        View view = createMock(View.class);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Form form = new Form();

        view.render(form, output);
        expectLastCall().times(4);

        replay(view);

        UXModule uxModule = new UXModule(view,view,view,view, createMock(Resource.class));
        uxModule.form(form);
        uxModule.render("get", output);
        uxModule.render("put",output);
        uxModule.render("post", output);
        uxModule.render("list", output);

        verify(view);


    }
}
