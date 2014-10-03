package org.github.levelthree;

import org.github.levelthree.angular.AngularResourceUX;
import org.github.levelthree.service.CRUDService;
import org.github.levelthree.ux.MyEntityController;
import org.github.levelthree.ux.View;
import org.github.levelthree.ux.form.Form;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.easymock.EasyMock.*;
import static org.github.levelthree.ux.StringView.stringView;

/**
 * Created by julian3 on 2014/08/11.
 */
public class ResourceUXTest {

    @Test
    public void testRender() throws Exception {

        View view = createMock(View.class);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Form form = new Form();

        view.render(form, output);
        expectLastCall().times(2);

        replay(view);

        ResourceUX resourceUx = new ResourceUX() {
            @Override
            public String getListLink() {
                return "list";
            }

            @Override
            public String getCreateLink() {
                return "create";
            }

            @Override
            public String getEditLink() {
                return "edit";
            }

            @Override
            public String getViewLink() {
                return "view";
            }

            @Override
            public View getViewByType(String type) {
                return view;
            }

            @Override
            public void init() {
                addView("edit.html",view);
                addView("list.html",view);
            }
        }.resource(Resource.resource(MyEntityController.class, createMock(CRUDService.class)));
        resourceUx.create();



        resourceUx.form(form);
        resourceUx.render("edit.html", output);
        resourceUx.render("list.html",output);

        verify(view);


    }
}
