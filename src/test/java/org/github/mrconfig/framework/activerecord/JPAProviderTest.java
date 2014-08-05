package org.github.mrconfig.framework.activerecord;

import org.github.mrconfig.domain.Environment;
import org.github.mrconfig.domain.EnvironmentGroup;
import org.github.mrconfig.service.BaseJPA;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.github.mrconfig.framework.activerecord.Parameter.p;
import static org.junit.Assert.*;

/**
 * Created by w1428134 on 2014/07/11.
 */
public class JPAProviderTest {

    private static JPAProvider provider;

    @BeforeClass
    public static void before() {
        JPAProvider.setPersistenceUnit(BaseJPA.UNIT_NAME);
        provider = new JPAProvider();

        for (int i = 0; i < 100; i++) {
            Environment environment = new Environment();
            environment.setName("Name ->" + i);
            environment.setKey(Integer.toString(i));
            provider.save(environment, null);
        }


    }

    @After
    public void after() {
        provider.getEntityManager().clear();
    }

    @Test
    public void testTransaction() throws Exception {
        provider.transact(() -> {
            Environment environment = new Environment();
            environment.setName("Test");
            environment.setKey("test");
            provider.getEntityManager().persist(environment);
            provider.getEntityManager().flush();
            provider.getEntityManager().clear();
            Optional<Environment> byId = provider.findById(Environment.class, environment.getId());
            assertTrue(byId.isPresent());
            return null;
        });

    }

    @Test
    public void testFindById() throws Exception {


        int i = 10000000;
        //for (int i=0;i<100;i++) {
        Environment environment = new Environment();
        environment.setName("Name ->" + i);
        environment.setKey(Integer.toString(i));
        provider.save(environment, null);
        //}
        provider.getEntityManager().clear();

        Optional<Environment> byId = provider.findById(Environment.class, environment.getId());
        assertNotNull(byId);
        assertTrue(byId.isPresent());
        assertEquals(environment.getId(), byId.get().getId());


    }


    @Test
    public void testStream() throws Exception {
        Stream<Environment> stream = provider.stream(Environment.class, "findNameLike", p("name", "Name%"));
        List<Environment> collect = stream.collect(Collectors.toList());
        assertNotNull(collect);
        assertTrue(collect.size() > 1);


    }
    @Test
    public void testIterate() throws Exception {
        Iterator<Environment> stream = provider.iterate(Environment.class, "findNameLike", p("name", "Name%"));
        assertNotNull(stream);
        assertTrue(stream.hasNext());
        assertTrue(stream.hasNext());


    }

    @Test
    public void testPage() throws Exception {
        Collection<Environment> stream = provider.page(Environment.class, "findNameLike", 5, 5, p("name", "Name%"));
        assertNotNull(stream);
        assertEquals(5, stream.size());


    }
    @Test
    public void testSingle() throws Exception {
        Optional<Environment> stream = provider.single(Environment.class, "findByKey", p("key", "0"));
        assertNotNull(stream);
        assertTrue(stream.isPresent());
        assertEquals("0", stream.get().getKey());


    }

    @Test
    public void testCountWhere() throws Exception {
        long stream = provider.countWhere(Environment.class);
        assertTrue(stream > 0);

    }

    @Test
    public void testFindWhere() throws Exception {
        Collection<Environment> result = provider.findWhere(Environment.class, p("key", "0"));
        assertNotNull(result);
        assertTrue(result.size() > 0);

    }

    @Test
    public void testFindWhereWildcard() throws Exception {
        Collection<Environment> result = provider.findWhere(Environment.class, p("name","Name*"));
        assertNotNull(result);
        assertTrue(result.size() > 0);

    }

    @Test
    public void testPageWhere() throws Exception {
        Collection<Environment> result = provider.pageWhere(Environment.class,0,5, p("key","0"));
        assertNotNull(result);
        assertTrue(result.size() > 0);

    }

    @Test
    public void testGenerateQuery() throws Exception {
        String s = provider.generateQuery(EnvironmentGroup.class, "select x from "+Environment.class.getSimpleName()+" x",false, true, p("name", "val"), p("parent", "1"));
        assertEquals("select x from "+Environment.class.getSimpleName()+" x JOIN FETCH x.parent where x.name = :name AND x.parent.id = :parent", s.trim());
        provider.getEntityManager().createQuery(s);

    }

    @Test
    public void testGenerateWildcardQuery() throws Exception {
        String s = provider.generateQuery(Environment.class,"select x from Test x",true, true, p("name", "val*"), p("key", "val"));
        assertEquals("select x from "+Environment.class.getSimpleName()+" x where x.name like :name AND x.key = :key", s.trim());
        provider.getEntityManager().createQuery(s);
    }
}
