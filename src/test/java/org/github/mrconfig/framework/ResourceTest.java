package org.github.mrconfig.framework;

import org.github.mrconfig.framework.security.Security;
import org.github.mrconfig.framework.service.CRUDService;
import org.github.mrconfig.framework.service.Identified;
import org.github.mrconfig.framework.ux.MyEntityController;
import org.junit.Test;

import java.util.Optional;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by julian3 on 2014/08/16.
 */
public class ResourceTest {


    @Test
    public void testGetIdForResource() throws Exception {
        CRUDService service = createMock(CRUDService.class);
        Resource resource = Resource.resource(MyEntityController.class, service);

        Identified identified = createMock(Identified.class);
        expect(service.get("hello")).andReturn(Optional.of(identified));
        expect(identified.getId()).andReturn("hello");

        replay(service, identified);

        String result = resource.buildIDForResource(identified);
        assertEquals("hello",result);


    }


    @Test
    public void testCreateWithDefaultRoles() throws Exception {
        Security.setUseDefaultRoles(true);
        Security.setStrictMode(false);
        CRUDService service = createMock(CRUDService.class);
        Resource resource = Resource.resource(MyEntityController.class, service);

        assertEquals(Security.getListRole(), resource.getListRole());
        assertEquals(Security.getLookupRole(), resource.getLookupRole());
        assertEquals(Security.getCreateRole(), resource.getCreateRole());
        assertEquals(Security.getUpdateRole(), resource.getUpdateRole());
        assertEquals(Security.getDeleteRole(), resource.getDeleteRole());
        assertEquals(false, resource.isRequiresAuthentication());

        Security.setUseDefaultRoles(false);
        Security.setStrictMode(true);
        resource = Resource.resource(MyEntityController.class, service);

        assertEquals(null, resource.getListRole());
        assertEquals(null, resource.getLookupRole());
        assertEquals(null, resource.getCreateRole());
        assertEquals(null, resource.getUpdateRole());
        assertEquals(null, resource.getDeleteRole());
        assertEquals(true, resource.isRequiresAuthentication());


    }
}
