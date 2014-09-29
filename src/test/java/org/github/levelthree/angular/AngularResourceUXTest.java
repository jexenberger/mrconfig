package org.github.levelthree.angular;

import org.github.levelthree.Resource;
import org.github.levelthree.service.CRUDService;
import org.github.levelthree.ux.MyEntityController;
import org.github.levelthree.ux.View;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;

/**
 * Created by julian3 on 2014/09/21.
 */
public class AngularResourceUXTest {

    private View mock;
    private AngularResourceUX resource;

    @Before
    public void setUp() throws Exception {
        mock = createMock(View.class);
        resource = (AngularResourceUX) new AngularResourceUX()
                .resource(Resource.resource(MyEntityController.class, createMock(CRUDService.class)));
        resource.create();
    }

    @Test
    public void testGetLink() throws Exception {


        String test = resource.getTemplatePath("test.html");
        assertEquals("/application/main/views/test/test.html", test);

        test = resource.getTemplatePath("qwerty.html");
        assertEquals("/application/main/views/test/qwerty.html", test);

    }


    @Test
    public void testNavigationPath() throws Exception {


        String test = resource.getNavigationLink("nav", ":param");
        assertEquals("/main/views/test/nav/:param", test);

        test = resource.getNavigationLink("nav", null);
        assertEquals("/main/views/test/nav", test);

    }

    @Test
    public void testNavLink() throws Exception {

        String test = resource.getCreateLink();
        assertEquals("/main/views/test/create", test);

        test = resource.getEditLink();
        assertEquals("/main/views/test/edit/:p_id", test);

        test = resource.getViewLink();
        assertEquals("/main/views/test/view/:p_id", test);

        test = resource.getListLink();
        assertEquals("/main/views/test/list", test);


    }


    @Test
    public void testAllTemplates() throws Exception {

        String test = resource.getCreateTemplate();
        assertEquals("/application/main/views/test/edit.html", test);

        test = resource.getEditTemplate();
        assertEquals("/application/main/views/test/edit.html", test);

        test = resource.getViewTemplate();
        assertEquals("/application/main/views/test/edit.html", test);

        test = resource.getListTemplate();
        assertEquals("/application/main/views/test/list.html", test);


    }
}
