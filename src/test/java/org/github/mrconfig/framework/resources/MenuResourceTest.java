package org.github.mrconfig.framework.resources;

import org.github.mrconfig.framework.Resource;
import org.github.mrconfig.framework.ResourceRegistry;
import org.github.mrconfig.framework.UXModule;
import org.github.mrconfig.framework.security.Security;
import org.github.mrconfig.framework.service.CRUDService;
import org.github.mrconfig.framework.ux.MyEntityController;
import org.github.mrconfig.framework.ux.form.DefaultUXModule;
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
        resource.ux(UXModule.defaultView(resource, null));
        ResourceRegistry.register(resource);

        SecurityContext securityContext = createMock(SecurityContext.class);
        expect(securityContext.isUserInRole(eq("list"))).andReturn(true).anyTimes();
        expect(securityContext.isUserInRole(eq("create"))).andReturn(true).anyTimes();
        replay(securityContext);

        Menu menu = new MenuResource().getMenu(securityContext);

        assertNotNull(menu);
        assertNotNull(menu.getMenuGroups());

    }
}
