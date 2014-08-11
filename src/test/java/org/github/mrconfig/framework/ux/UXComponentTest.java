package org.github.mrconfig.framework.ux;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.easymock.EasyMock.*;

/**
 * Created by julian3 on 2014/08/11.
 */
public class UXComponentTest {

    @Test
    public void testRender() throws Exception {

        View view = createMock(View.class);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        view.render(output);
        expectLastCall().times(4);

        replay(view);

        UXComponent uxComponent = new UXComponent(view,view,view,view);
        uxComponent.render("get", output);
        uxComponent.render("put", output);
        uxComponent.render("post", output);
        uxComponent.render("list", output);

        verify(view);


    }
}
