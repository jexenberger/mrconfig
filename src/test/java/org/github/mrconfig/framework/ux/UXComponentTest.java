package org.github.mrconfig.framework.ux;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static org.easymock.EasyMock.*;

/**
 * Created by julian3 on 2014/08/11.
 */
public class UXComponentTest {

    @Test
    public void testRender() throws Exception {

        View view = createMock(View.class);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Map<String, Object> model = new HashMap<>();

        view.render(model, output);
        expectLastCall().times(4);

        replay(view);

        UXComponent uxComponent = new UXComponent(view,view,view,view);
        uxComponent.render("get", model, output);
        uxComponent.render("put", model,output);
        uxComponent.render("post",model, output);
        uxComponent.render("list",model, output);

        verify(view);


    }
}
