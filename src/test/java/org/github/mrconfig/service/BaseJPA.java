package org.github.mrconfig.service;

import org.github.mrconfig.domain.KeyEntity;
import org.github.mrconfig.framework.activerecord.ProviderFactory;
import org.github.mrconfig.framework.activerecord.jpa.JPAProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.persistence.*;
import java.util.Optional;

/**
 * Created by w1428134 on 2014/07/10.
 */
public class BaseJPA {
    public static final String ENTITY_MANAGER = "entity.manager";
    public static final String UNIT_NAME = "org.github.mrconfig.framework.testdomain";
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeClass
    public static void setup() throws Exception {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory(UNIT_NAME);
        }
        JPAProvider.setPersistenceUnit(UNIT_NAME);
        ProviderFactory.setProvider(new JPAProvider());
    }

    @Before
    public void before() throws Exception {
        if (entityManager == null) {
            entityManager = entityManagerFactory.createEntityManager();
        }
        JPAProvider.setThreadEntityManager(entityManager);
        entityManager.getTransaction().begin();
    }

    @After
    public void after() throws Exception {
        try {
            entityManager.flush();
            entityManager.clear();
        } finally {
            try {
                JPAProvider.unsetThreadEntityManager();
                getEntityManager().getTransaction().rollback();
                System.out.println("TRANSACTION ROLLED BACK!!!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
            getEntityManager().close();
        }
    }


    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public EntityTransaction getTransaction() {
        return getEntityManager().getTransaction();
    }

    protected <T extends KeyEntity> Optional<T> lookupByKey(Class<T> environmentClass, String key) {
        String environment = environmentClass.getSimpleName();
        Query query = getEntityManager().createQuery("select e from " + environment + " e where e.key = ?");
        query.setParameter(1, key);
        try {
            return Optional.ofNullable((T) query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
