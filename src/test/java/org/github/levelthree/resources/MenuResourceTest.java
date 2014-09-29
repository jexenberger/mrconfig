package org.github.levelthree.resources;

import org.github.levelthree.Resource;
import org.github.levelthree.ResourceRegistry;
import org.github.levelthree.ResourceUX;
import org.github.levelthree.security.Security;
import org.github.levelthree.service.CRUDService;
import org.github.levelthree.ux.MyEntityController;
import org.junit.Test;

import javax.ws.rs.core.SecurityContext;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by julian3 on 2014/09/18.
 */
public class MenuResourceTest {


    @Test
    public void testAllowAccess() throws Exception {

        SecurityContext securityContext = createMock(SecurityContext.class);
        expect(securityContext.isUserInRole(eq("role"))).andReturn(true).anyTimes();
        replay(securityContext);

        boolean access = new MenuResource().allowAccess(securityContext, "role");
        assertEquals(true, access);

        access = new MenuResource().allowAccess(securityContext, null);
        assertEquals(true, access);

    }


    @Test
    public void testNotAllowAccess() throws Exception {

        SecurityContext securityContext = createMock(SecurityContext.class);
        expect(securityContext.isUserInRole(eq("role"))).andReturn(false).anyTimes();
        replay(securityContext);

        boolean access = new MenuResource().allowAccess(securityContext, "role");
        assertEquals(false, access);

        access = new MenuResource().allowAccess(securityContext, null);
        assertEquals(true, access);

    }

    @Test
    public void testGetMenu() throws Exception {

        Security.setUseDefaultRoles(true);
        Resource resource = Resource.resource(MyEntityController.class, createMock(CRUDService.class));
        ResourceUX resourceUX = createMock(ResourceUX.class);
        resource.ux(resourceUX);

        expect(resourceUX.getGroup()).andReturn("group").anyTimes();
        expect(resourceUX.getResource()).andReturn(resource).anyTimes();
        expect(resourceUX.getName()).andReturn("name").anyTimes();
        expect(resourceUX.getKey()).andReturn("key").anyTimes();
        expect(resourceUX.getListLink()).andReturn("listLink").anyTimes();
        expect(resourceUX.getCreateLink()).andReturn("listLink").anyTimes();
        ResourceRegistry.register(resource);

        SecurityContext securityContext = createMock(SecurityContext.class);
        expect(securityContext.isUserInRole(eq("list"))).andReturn(true).anyTimes();
        expect(securityContext.isUserInRole(eq("create"))).andReturn(true).anyTimes();

        replay(resourceUX, securityContext);

        Menu menu = new MenuResource().getMenu(securityContext);

        assertNotNull(menu);
        assertNotNull(menu.getMenuGroups());

    }
}
