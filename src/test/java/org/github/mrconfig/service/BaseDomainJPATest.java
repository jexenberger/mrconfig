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
 * Created by julian3 on 2014/08/10.
 */
public abstract class BaseDomainJPATest{

    private static EntityManager entityManager;

    public static final String ENTITY_MANAGER = "entity.manager";
    public static final String UNIT_NAME = "org.github.mrconfig.domain";
    private static EntityManagerFactory entityManagerFactory;
    private static EntityTransaction transaction;

    @BeforeClass
    public static void setup() throws Exception {
        entityManager = (EntityManager) System.getProperties().get(ENTITY_MANAGER);
        if (entityManager == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory(UNIT_NAME);
            entityManager = entityManagerFactory.createEntityManager();
            System.getProperties().put(ENTITY_MANAGER, entityManager);
        }
        JPAProvider.setPERSISTENCE_UNIT(UNIT_NAME);
        ProviderFactory.setProvider(new JPAProvider());
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
            if (transaction != null) {
                transaction.setRollbackOnly();
            }
        }
    }



    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    protected <T extends KeyEntity> Optional<T> lookupByKey(Class<T> environmentClass, String key) {
        String environment = environmentClass.getSimpleName();
        Query query = getEntityManager().createQuery("select e from " + environment + " e where e.id = ?");
        query.setParameter(1, key);
        try {
            return Optional.ofNullable((T) query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

}
