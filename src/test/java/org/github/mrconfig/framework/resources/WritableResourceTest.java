package org.github.mrconfig.framework.resources;

import org.github.mrconfig.framework.service.CRUDService;
import org.github.mrconfig.framework.service.Creatable;
import org.github.mrconfig.framework.service.Updateable;
import org.github.mrconfig.framework.testdomain.MyEntity;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.easymock.EasyMock.*;
import static org.github.mrconfig.framework.util.Box.success;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by w1428134 on 2014/08/04.
 */
public class WritableResourceTest implements WritableResource<MyEntity,Long>{

    private CRUDService service;
    private MyEntity instance;

    @Before
    public void setup() {
        service = createMock(CRUDService.class);
        instance = new MyEntity(1L, "hello");

        expect(service.create(anyObject(MyEntity.class))).andReturn(success(1L)).anyTimes();
        expect(service.save(anyObject(MyEntity.class))).andReturn(success(instance)).anyTimes();

        replay(service);

    }

    @Test
    public void testCreate() throws Exception {
        Response response = this.create(instance);
        assertNotNull(response.getHeaderString("Location"));
        assertNotNull(response.getEntity());
        verify(service);
    }

    @Test
    public void testSave() throws Exception {
        Response response = this.save("1", instance);
        assertNotNull(response.getEntity());
        verify(service);
    }

    @Override
    public Creatable<MyEntity, Long> getCreatable() {

        return service;
    }

    @Override
    public Updateable<MyEntity,Long> getUpdateable() {
        return service;
    }
}
