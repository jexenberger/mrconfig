package org.github.mrconfig.framework;

import org.github.levelthree.Resource;
import org.github.levelthree.UX;
import org.github.levelthree.ux.View;
import org.github.levelthree.ux.form.Form;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.easymock.EasyMock.*;

/**
 * Created by julian3 on 2014/08/11.
 */
public class UXTest {

    @Test
    public void testRender() throws Exception {

        View view = createMock(View.class);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Form form = new Form();

        view.render(form, output);
        expectLastCall().times(4);

        replay(view);

        UX ux = new UX(view,view,view,view, createMock(Resource.class));
        ux.form(form);
        ux.render("get", output);
        ux.render("put",output);
        ux.render("post", output);
        ux.render("list", output);

        verify(view);


    }
}
