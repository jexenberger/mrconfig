package org.github.mrconfig.resources;

import org.github.levelthree.activerecord.Provider;
import org.github.levelthree.activerecord.ProviderFactory;
import org.github.mrconfig.domain.Environment;
import org.junit.Test;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.UriInfo;

import java.util.ArrayList;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by julian3 on 2014/07/18.
 */
public class EnvironmentResourceTest {

    @Test
    public void testGet() throws Exception {
        UriInfo mock = createMock(UriInfo.class);

        Provider provider = createMock(Provider.class);
        ProviderFactory.setProvider(provider);
        int totalResults = 15 * 3 + 6;
        int totalPages = (totalResults/15)+1;
        expect(provider.countWhere(anyObject())).andReturn((long) totalResults).anyTimes();
        expect(provider.pageWhere(eq(Environment.class), eq(3*15), eq(15))).andReturn(new ArrayList<>()).anyTimes();

        MultivaluedHashMap<String, String> queryMap = new MultivaluedHashMap<>();
        queryMap.putSingle("page","3");

        expect(mock.getQueryParameters()).andReturn(queryMap).anyTimes();
        expect(mock.getPath()).andReturn("/test/qwerty").anyTimes();

        replay(mock, provider);

        /*
        Results<Environment> environmentResults = new EnvironmentResource().get(mock);
        assertEquals(3, environmentResults.getCurrentPage());
        assertEquals(totalResults, environmentResults.getTotalResults());
        assertEquals(totalPages, environmentResults.getTotalPages());
        assertEquals(1,environmentResults.getEnvironment().stream().filter((link)->link.getRel().contains("next")).count());
        assertEquals(1,environmentResults.getEnvironment().stream().filter((link)->link.getRel().contains("prev")).count());
        */
    }
}
