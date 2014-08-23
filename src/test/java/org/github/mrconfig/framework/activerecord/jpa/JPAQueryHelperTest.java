package org.github.mrconfig.framework.activerecord.jpa;

import org.github.mrconfig.framework.testdomain.MyEntity;
import org.github.mrconfig.framework.testdomain.TestEnum;
import org.github.mrconfig.framework.util.Pair;
import org.github.mrconfig.service.BaseJPA;
import org.junit.After;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import static org.github.mrconfig.framework.activerecord.Parameter.p;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by julian3 on 2014/08/17.
 */
public class JPAQueryHelperTest extends BaseJPA {

    private MyEntity parent;
    private Date aDate;

    @Override
    public void before() throws Exception {
        super.before();
        parent = new MyEntity();
        parent.setName("parent");
        parent.save();
        MyEntity myEntity = new MyEntity();
        myEntity.setName("test");
        myEntity.setParent(parent);
        myEntity.save();
        MyEntity myEntity2 = new MyEntity();
        myEntity2.setName("test2");
        myEntity2.setValue(3);
        myEntity2.setEnumType(TestEnum.one);
        aDate = new Date();
        myEntity2.setaDate(aDate);
        myEntity2.save();
    }

    @Test
    public void testFind() throws Exception {

        System.out.println(parent.getId());
        Collection<MyEntity> results = new JPAQueryHelper().findWhere(getEntityManager(), MyEntity.class, p("name", "test"),p("parent",parent));
        System.out.println(results);
        assertNotNull(results);
        assertEquals(1, results.size());
        System.out.println(results.iterator().next().getParent());

        //result test with a null parent
        results = new JPAQueryHelper().findWhere(getEntityManager(), MyEntity.class, p("name", "test"),p("parent",null));
        assertEquals(0, results.size());


        //result test2 with a null parent
        results = new JPAQueryHelper().findWhere(getEntityManager(), MyEntity.class, p("name", "test2"),p("parent",null));
        System.out.println(results);
        assertEquals(1, results.size());

        //result test2 with a null parent
        results = new JPAQueryHelper().findWhere(getEntityManager(), MyEntity.class, p("name", "test"),p("parent",parent.getId()));
        assertEquals(1, results.size());

        //result est with a wildcard
        results = new JPAQueryHelper().findWhere(getEntityManager(), MyEntity.class, p("name", "*est*"));
        assertEquals(2,results.size());

        //type conversion
        results = new JPAQueryHelper().findWhere(getEntityManager(), MyEntity.class, p("value", "3"));
        assertEquals(1,results.size());

        //no type conversion
        results = new JPAQueryHelper().findWhere(getEntityManager(), MyEntity.class, p("value", 3));
        assertEquals(1,results.size());

        //enum
        results = new JPAQueryHelper().findWhere(getEntityManager(), MyEntity.class, p("enumType", TestEnum.one));
        assertEquals(1,results.size());

        //enum with conversion
        results = new JPAQueryHelper().findWhere(getEntityManager(), MyEntity.class, p("enumType", "one"));
        assertEquals(1,results.size());


        //date
        results = new JPAQueryHelper().findWhere(getEntityManager(), MyEntity.class, p("aDate", aDate));
        assertEquals(1,results.size());


        //date wildcard
        results = new JPAQueryHelper().findWhere(getEntityManager(), MyEntity.class, p("aDate", "201*"));
        assertEquals(1,results.size());


    }

    @Test
    public void testPage() throws Exception {

        System.out.println(parent.getId());
        Collection<MyEntity> results = new JPAQueryHelper().pageWhere(getEntityManager(), MyEntity.class, 1L, 2L);
        assertNotNull(results);
        assertEquals(2, results.size());
        System.out.println(results.iterator().next().getParent());
    }

    @Test
    public void testCount() throws Exception {

        System.out.println(parent.getId());
        long results = new JPAQueryHelper().countWhere(getEntityManager(), MyEntity.class);
        assertNotNull(results);
        assertEquals(3, results);

        results = new JPAQueryHelper().countWhere(getEntityManager(), MyEntity.class, p("parent",parent));
        assertNotNull(results);
        assertEquals(1, results);

    }

    @Override
    @After
    public void after() throws Exception {
        super.after();
    }

    @Test
    public void testCalcWildCardDateRange() throws Exception {

        Pair<Date, Date> dateDatePair = JPAQueryHelper.calcWildCardDateRange("1*");
        System.out.println(dateDatePair);
        assertNotNull(dateDatePair);

        dateDatePair = JPAQueryHelper.calcWildCardDateRange("19*");
        System.out.println(dateDatePair);
        assertNotNull(dateDatePair);

        dateDatePair = JPAQueryHelper.calcWildCardDateRange("199*");
        System.out.println(dateDatePair);
        assertNotNull(dateDatePair);

        dateDatePair = JPAQueryHelper.calcWildCardDateRange("2014*");
        System.out.println(dateDatePair);
        assertNotNull(dateDatePair);

        dateDatePair = JPAQueryHelper.calcWildCardDateRange("20140*");
        System.out.println(dateDatePair);
        assertNotNull(dateDatePair);

        dateDatePair = JPAQueryHelper.calcWildCardDateRange("20141*");
        System.out.println(dateDatePair);
        assertNotNull(dateDatePair);

        dateDatePair = JPAQueryHelper.calcWildCardDateRange("201405*");
        System.out.println(dateDatePair);
        assertNotNull(dateDatePair);

    }
}
