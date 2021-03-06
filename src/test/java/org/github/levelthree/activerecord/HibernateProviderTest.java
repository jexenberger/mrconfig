package org.github.levelthree.activerecord;

import org.github.levelthree.activerecord.jpa.JPAProvider;
import org.github.mrconfig.framework.testdomain.MyEntity;
import org.github.mrconfig.service.BaseJPA;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by julian3 on 2014/08/10.
 */
public class HibernateProviderTest extends BaseJPA{

    private static JPAProvider provider;

    @Before
    public void before() throws Exception{
        super.before();
        JPAProvider.setPersistenceUnit(BaseJPA.UNIT_NAME);
        provider = new JPAProvider();

        for (int i = 0; i < 100; i++) {
            MyEntity environment = new MyEntity();
            environment.setName("Name ->" + i);
            environment.setRandomColumn("Na" + i);
            provider.save(environment, null);
        }


    }

    @Test
    public void testSave() throws Exception {

        MyEntity gotOne = provider.findWhere(MyEntity.class).iterator().next();
        provider.getEntityManager().clear();

        gotOne.save();


    }

    @After
    public void after() throws Exception{
        super.after();
        provider.getEntityManager().clear();
    }

}
