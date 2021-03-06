package org.github.levelthree.resources;

import org.github.levelthree.service.Link;
import org.github.levelthree.service.CRUDService;
import org.github.levelthree.service.Listable;
import org.github.levelthree.service.UniqueLookup;
import org.github.mrconfig.framework.testdomain.MyEntity;
import org.github.levelthree.util.Pair;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.Path;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.easymock.EasyMock.*;
import static org.github.levelthree.util.Box.success;
import static org.github.levelthree.util.Pair.cons;
import static org.junit.Assert.*;

/**
 * Created by w1428134 on 2014/08/04.
 */
@Path("/test")
public class ReadableResourceTest implements ReadableResource<MyEntity,Long> {

    private CRUDService<MyEntity,Long> service;
    private MyEntity instance;

    @Before
    public void setup() {
        service = createMock(CRUDService.class);
        instance = new MyEntity(1L, "hello");

        expect(service.create(anyObject(MyEntity.class))).andReturn(success(1L)).anyTimes();
        expect(service.save(anyObject(MyEntity.class))).andReturn(success(instance)).anyTimes();
        expect(service.get(anyObject(Long.class))).andReturn((Optional.of(instance))).anyTimes();
        expect(service.count()).andReturn(1L).anyTimes();
        expect(service.page(0, 5)).andReturn(asList(instance)).anyTimes();
        expect(service.list()).andReturn(asList(instance)).anyTimes();
        expect(service.resolve(anyObject(),anyObject())).andReturn(Optional.ofNullable(instance)).anyTimes();
        expect(service.toLink(eq(instance),eq("application/json"))).andReturn(new Link("self","string/xml","/helloworld","Hello World")).anyTimes();

        replay(service);

    }

    @Test
    public void testGet() throws Exception {
        Response response = this.get(createMock(SecurityContext.class), "1", createMock(UriInfo.class), createMock(HttpHeaders.class));
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertNotNull(response.getEntity());
        assertNotNull(response.getHeaderString("Link"));
        assertSame(instance, response.getEntity());
    }

    @Test
    public void testSimpleGetQuery() throws Exception {
        UriInfo uriInfo = createMock(UriInfo.class);
        expect(uriInfo.getQueryParameters()).andReturn(new MultivaluedHashMap<>()).times(2);
        expect(uriInfo.getPath()).andReturn("/helloworld").times(1);
        replay(uriInfo);

        Response response = this.get(createMock(SecurityContext.class), uriInfo,  createMock(HttpHeaders.class));
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertNotNull(response.getEntity());

        Results<MyEntity> results = (Results<MyEntity>) response.getEntity();
        assertNotNull(results.getResult());
        assertEquals(1, results.getResult().size());
        assertEquals(1, results.getTotalPages());
        assertEquals(1, results.getTotalResults());


    }

    @Test
    public void testPageGetQuery() throws Exception {
        reset(service);

        long totalItems = getPageSize() * 3 + getPageSize() - 2;
        long totalPages = (totalItems / getPageSize()) + (totalItems % getPageSize() > 0 ? 1 : 0);
        List<MyEntity> testData = new ArrayList<MyEntity>((int)totalItems);
        for (long i = 0; i < totalItems; i++) {
            testData.add(new MyEntity(i,"Name "+i));
        }
        Pair<String, Object> params = cons("name", (Object) "Name*");
        expect(service.count(params)).andReturn(totalItems).anyTimes();
        expect(service.page(0, getPageSize(), params)).andReturn(testData.subList(0,getPageSize())).anyTimes();
        expect(service.toLink(anyObject(MyEntity.class),eq("application/json"))).andReturn(new Link("self","test/html","/helloworld","Hello world")).times(getPageSize());



        UriInfo uriInfo = createMock(UriInfo.class);
        MultivaluedHashMap<String, String> parameters = new MultivaluedHashMap<>();
        parameters.put("name",asList("Name*"));
        parameters.put("page",asList("1"));
        expect(uriInfo.getQueryParameters()).andReturn(parameters).times(2);
        expect(uriInfo.getPath()).andReturn("/helloworld").times(1);

        replay(service, uriInfo);

        Response response = this.get(createMock(SecurityContext.class), uriInfo,  createMock(HttpHeaders.class));
        assertNotNull(response);
        assertNotNull(response.getEntity());
        assertEquals(200, response.getStatus());

        Results<MyEntity> results = (Results<MyEntity>) response.getEntity();
        assertNotNull(results.getResult());
        assertEquals(this.getPageSize(), results.getResult().size());
        assertEquals(totalPages, results.getTotalPages());
        assertEquals(totalItems, results.getTotalResults());
        assertEquals(1, results.getCurrentPage());


    }

    @Override
    public Listable<MyEntity> getListableResource() {
        return service;
    }

    @Override
    public UniqueLookup<MyEntity, Long> getLookup() {
        return service;
    }

    @Override
    public boolean isUserAllowedToList(SecurityContext context) {
        return true;
    }

    @Override
    public boolean isUserAllowedToLookup(SecurityContext context) {
        return true;
    }
}
