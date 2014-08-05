package org.github.mrconfig.service;

import org.github.mrconfig.domain.KeyEntity;
import org.github.mrconfig.framework.activerecord.JPAProvider;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.persistence.*;
import java.util.Optional;

/**
 * Created by w1428134 on 2014/07/10.
 */
public class BaseJPA {
    public static final String UNIT_NAME = "org.github.mrconfig.domain";
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private EntityTransaction transaction;

    @BeforeClass
    public static void setup() throws Exception {
        entityManagerFactory =  Persistence.createEntityManagerFactory(UNIT_NAME);
        entityManager = entityManagerFactory.createEntityManager();
        JPAProvider.setPersistenceUnit(UNIT_NAME);
    }

    @Before
    public void before() throws Exception {
        transaction = entityManager.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }
    }

    @After
    public void after() throws Exception {
        try {
            entityManager.flush();
        } finally {
            if (transaction != null) transaction.setRollbackOnly();
        }
    }

    @AfterClass
    public static void teardown() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    public EntityTransaction getTransaction() {
        return transaction;
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
