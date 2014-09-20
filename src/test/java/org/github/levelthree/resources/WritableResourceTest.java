package org.github.levelthree.resources;

import org.github.levelthree.service.CRUDService;
import org.github.levelthree.service.Creatable;
import org.github.levelthree.service.Link;
import org.github.levelthree.service.Updateable;
import org.github.mrconfig.framework.testdomain.MyEntity;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import static org.easymock.EasyMock.*;
import static org.github.levelthree.util.Box.success;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by w1428134 on 2014/08/04.
 */
@Path("/test")
public class WritableResourceTest implements WritableResource<MyEntity,Long>{

    private CRUDService service;
    private MyEntity instance;

    @Before
    public void setup() {
        service = createMock(CRUDService.class);
        instance = new MyEntity(1L, "hello");

        expect(service.create(anyObject(MyEntity.class))).andReturn(success(1L)).anyTimes();
        expect(service.save(anyObject(MyEntity.class))).andReturn(success(instance)).anyTimes();
        expect(service.toLink(anyObject(MyEntity.class))).andReturn(new Link()).anyTimes();

        replay(service);

    }

    @Test
    public void testCreate() throws Exception {
        Response response = this.create(createMock(SecurityContext.class), instance, createMock(UriInfo.class));
        assertEquals(201, response.getStatus());
        assertNotNull(response.getHeaderString("Location"));
        assertNotNull(response.getEntity());
        verify(service);
    }

    @Test
    public void testSave() throws Exception {
        Response response = this.save(createMock(SecurityContext.class),null, instance, createMock(UriInfo.class));
        assertEquals(200, response.getStatus());
        assertNotNull("the response should have contained an entity", response.getEntity());
        verify(service);
    }

    @Override
    public Creatable<MyEntity, Long> getCreatable() {

        return service;
    }

    @Override
    public boolean isUserAllowedToSave(SecurityContext context) {
        return true;
    }

    @Override
    public boolean isUserAllowedToCreate(SecurityContext context) {
        return true;
    }

    @Override
    public Updateable<MyEntity,Long> getUpdateable() {
        return service;
    }
}
