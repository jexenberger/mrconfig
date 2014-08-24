package org.github.mrconfig.framework.activerecord.jpa;

import org.github.mrconfig.framework.activerecord.ProviderFactory;
import org.github.mrconfig.framework.activerecord.jpa.JPAProvider;
import org.github.mrconfig.framework.testdomain.MyEntity;
import org.github.mrconfig.service.BaseJPA;
import org.junit.After;
import org.junit.Before;
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
public class JPAProviderTest extends BaseJPA{

    private JPAProvider provider;

    @Before
    public  void before() throws Exception{
        super.before();
        provider = (JPAProvider) ProviderFactory.getProvider();
        //provider.deleteAll(MyEntity.class);

        for (int i = 0; i < 100; i++) {
            MyEntity environment = new MyEntity();
            environment.setName("Name ->" + i);
            //provider.save(environment, null);
            environment.save();
        }


    }


    @Test
    public void testTransaction() throws Exception {
        provider.transact(() -> {
            MyEntity environment = new MyEntity();
            environment.setName("Test");
            provider.getEntityManager().persist(environment);
            provider.getEntityManager().flush();
            provider.getEntityManager().clear();
            Optional<MyEntity> byId = provider.findById(MyEntity.class, environment.getId());
            assertTrue(byId.isPresent());
            return null;
        });

    }

    @Test
    public void testFindById() throws Exception {


        int i = 10000000;
        //for (int i=0;i<100;i++) {
        MyEntity environment = new MyEntity();
        environment.setName("Name ->" + i);
        provider.save(environment, null);
        //}
        provider.getEntityManager().clear();

        Optional<MyEntity> byId = provider.findById(MyEntity.class, environment.getId());
        assertNotNull(byId);
        assertTrue(byId.isPresent());
        assertEquals(environment.getId(), byId.get().getId());


    }


    @Test
    public void testStream() throws Exception {
        Stream<MyEntity> stream = provider.stream(MyEntity.class, "findNameLike", p("name", "Name%"));
        List<MyEntity> collect = stream.collect(Collectors.toList());
        assertNotNull(collect);
        assertTrue("there should have been some results",collect.size() > 1);


    }
    @Test
    public void testIterate() throws Exception {
        Iterator<MyEntity> stream = provider.iterate(MyEntity.class, "findNameLike", p("name", "Name%"));
        assertNotNull(stream);
        assertTrue(stream.hasNext());
        assertTrue(stream.hasNext());


    }

    @Test
    public void testPage() throws Exception {
        Collection<MyEntity> stream = provider.page(MyEntity.class, "findNameLike", 5, 5, p("name", "Name%"));
        assertNotNull(stream);
        assertEquals(5, stream.size());


    }
    @Test
    public void testSingle() throws Exception {

        Optional<MyEntity> stream = provider.single(MyEntity.class, "findNameLike", p("name", "Name%"));
        assertNotNull(stream);
        assertTrue(stream.isPresent());


    }

    @Test
    public void testCountWhere() throws Exception {
        long stream = provider.countWhere(MyEntity.class);
        assertTrue(stream > 0);

    }

    @Test
    public void testFindWhere() throws Exception {
        Collection<MyEntity> result = provider.findWhere(MyEntity.class, p("name", "Name ->0"));
        assertNotNull(result);
        assertTrue(result.size() > 0);

    }

    @Test
    public void testFindWhereWildcard() throws Exception {
        Collection<MyEntity> result = provider.findWhere(MyEntity.class, p("name","Name*"));
        assertNotNull(result);
        assertTrue(result.size() > 0);

    }

    @Test
    public void testPageWhere() throws Exception {
        Collection<MyEntity> result = provider.pageWhere(MyEntity.class,0,5, p("name","Name*"));
        assertNotNull(result);
        assertTrue(result.size() > 0);

    }

}
