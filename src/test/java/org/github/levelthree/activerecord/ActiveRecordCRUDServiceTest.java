package org.github.levelthree.activerecord;

import org.github.levelthree.activerecord.jpa.JPAProvider;
import org.github.mrconfig.framework.testdomain.MyEntity;
import org.github.levelthree.util.Box;
import org.github.mrconfig.service.BaseJPA;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Optional;

import static org.github.levelthree.util.Pair.cons;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by w1428134 on 2014/08/04.
 */
public class ActiveRecordCRUDServiceTest extends BaseJPA {

    private ActiveRecordCRUDService<MyEntity, Long> service;
    private MyEntity environment;
    private Long id;


    @Override
    @Before
    public void before() throws Exception {
        super.before();
        JPAProvider.setPersistenceUnit(BaseJPA.UNIT_NAME);

        service = new ActiveRecordCRUDService<MyEntity, Long>(MyEntity.class, "/myentities");
        environment = getMyEntity();
    }

    @Test
    public void testCreate() throws Exception {

        Box<Long> save = service.create(environment);
        id = save.get();
        assertNotNull(save);
        save.mapError((code, val)-> {
            System.out.println(code+ " "+val);
            return null;
        });
        assertTrue(save.isSuccess());
        assertTrue(save.get() > 0);
    }

    @Test
    public void testSave() throws Exception {
        testCreate();
        environment.setName("qwerty");
        Box<MyEntity> save = service.save(environment);
        assertNotNull(save);
        assertTrue(save.isSuccess());
        assertEquals("qwerty",service.get(environment.getId()).get().getName());

    }

    @Test
    public void testFindWhere() throws Exception {
        testCreate();
        Collection<MyEntity> save = service.list(cons("id", id));
        assertNotNull(save);
        assertEquals(1,save.size());

    }

    @Test
    public void testPage() throws Exception {
        testCreate();
        Collection<MyEntity> save = service.page(0, 10, cons("id", id));
        assertNotNull(save);
        assertEquals(1,save.size());

    }

    @Test
    public void testGet() throws Exception {
        testCreate();
        Optional<MyEntity> save = service.get(environment.getId());
        assertNotNull(save);
        assertEquals("Name", save.get().getName());

    }

    @Test
    public void testDelete() throws Exception {
        testCreate();
        Box<MyEntity> save = service.delete(environment.getId());
        assertNotNull(save);
        System.out.println(save.getErrors());
        assertTrue(save.isSuccess());

    }

    private MyEntity getMyEntity() {
        MyEntity environment = new MyEntity();
        environment.setName("Name");
        environment.setRandomColumn("random");
        return environment;
    }
}
