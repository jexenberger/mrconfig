package org.github.mrconfig.framework.activerecord;

import org.github.mrconfig.domain.Environment;
import org.github.mrconfig.domain.EnvironmentGroup;
import org.github.mrconfig.framework.util.Box;
import org.github.mrconfig.service.BaseJPA;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Optional;

import static org.github.mrconfig.framework.util.Pair.cons;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by w1428134 on 2014/08/04.
 */
public class ActiveRecordCRUDServiceTest extends BaseJPA {

    private ActiveRecordCRUDService<EnvironmentGroup, Long> service;
    private EnvironmentGroup environment;


    @Override
    @Before
    public void before() throws Exception {
        super.before();

        service = new ActiveRecordCRUDService<EnvironmentGroup, Long>(EnvironmentGroup.class);
        environment = getEnvironmentGroup();
    }

    @Test
    public void testCreate() throws Exception {

        Box<Long> save = service.create(environment);
        assertNotNull(save);
        assertTrue(save.isSuccess());
        assertTrue(save.get() > 0);
    }

    @Test
    public void testSave() throws Exception {
        testCreate();
        environment.setName("qwerty");
        Box<EnvironmentGroup> save = service.save(environment);
        assertNotNull(save);
        assertTrue(save.isSuccess());
        assertEquals("qwerty",service.get(environment.getId()).get().getName());

    }

    @Test
    public void testFindWhere() throws Exception {
        testCreate();
        Collection<EnvironmentGroup> save = service.list(cons("name", "Name"));
        assertNotNull(save);
        assertEquals(1,save.size());

    }

    @Test
    public void testPage() throws Exception {
        testCreate();
        Collection<EnvironmentGroup> save = service.page(0, 10, cons("name", "Name"));
        assertNotNull(save);
        assertEquals(1,save.size());

    }

    @Test
    public void testGet() throws Exception {
        testCreate();
        Optional<EnvironmentGroup> save = service.get(environment.getId());
        assertNotNull(save);
        assertEquals("Name", save.get().getName());

    }

    @Test
    public void testDelete() throws Exception {
        testCreate();
        Box<EnvironmentGroup> save = service.delete(environment);
        assertNotNull(save);
        System.out.println(save.getErrors());
        assertTrue(save.isSuccess());

    }

    private EnvironmentGroup getEnvironmentGroup() {
        EnvironmentGroup environment = new EnvironmentGroup();
        environment.setName("Name");
        environment.setKey("key");
        return environment;
    }
}
