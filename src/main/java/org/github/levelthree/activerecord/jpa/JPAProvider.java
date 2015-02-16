package org.github.levelthree.activerecord.jpa;

import org.github.levelthree.activerecord.*;
import org.github.levelthree.activerecord.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by w1428134 on 2014/07/11.
 */
public class JPAProvider implements Provider {

    private static  String NULL = "null";
    private static EntityManagerFactory ENTITY_MANAGER_FACTORY;
    private static String PERSISTENCE_UNIT = "default";
    private static ThreadLocal<EntityManager> PROVIDER = new ThreadLocal<>();

    private JPAQueryHelper helper;

    public JPAProvider() {
        helper = new JPAQueryHelper();
    }

    public static void setThreadEntityManager(EntityManager entityManager) {
        PROVIDER.set(entityManager);
    }

    public static void unsetThreadEntityManager() {
        PROVIDER.remove();
    }

    public static void setPersistenceUnit(String unit) {
        reset();
        PERSISTENCE_UNIT = unit;
    }

    public static void setEntityManagerFactory(EntityManagerFactory factory) {
        reset();
        ENTITY_MANAGER_FACTORY = factory;
    }

    public static String getEmptyIndicator() {
        return NULL;
    }

    public static void setEmptyIndicator(String NULL) {
        JPAProvider.NULL = NULL;
    }

    public static String getPersistenceUnit() {
        return PERSISTENCE_UNIT;
    }

    @Override
    public <T> Optional<T> findById(Class<T> type, Object id) {
        return Optional.ofNullable(transact(() -> getEntityManager().find(type, id)));
    }


    @Override
    public <T> T transact(Supplier<T> supplier) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = null;
        boolean success = false;
        boolean nested = false;
        try {
            setThreadEntityManager(entityManager);
            transaction = getTransaction(entityManager);
            if (!transaction.isActive()) {
                transaction.begin();
            } else {
                nested = true;
            }
            T result = supplier.get();
            entityManager.flush();
            success = !transaction.getRollbackOnly();
            return result;
        } catch (RuntimeException e) {
            success = false;
            throw e;
        } finally {
            if (transaction != null && transaction.isActive() && !nested) {
                if (success) {
                    transaction.commit();
                } else {
                    transaction.setRollbackOnly();
                }
                unsetThreadEntityManager();
            }
        }
    }

    public EntityTransaction getTransaction(EntityManager entityManager) {
        EntityTransaction transaction = entityManager.getTransaction();
        System.out.println(transaction);
        return transaction;
    }


    @Override
    public void startTransaction() {
        EntityTransaction transaction = null;
        EntityManager entityManager = getEntityManager();
        if (!entityManager.isJoinedToTransaction()) {
            transaction = getTransaction(entityManager);
            transaction.begin();
        }

    }

    @Override
    public <T> T doWork(Supplier<T> work) {
        return transact(() -> {
            return work.get();
        });
    }

    @Override
    public <K extends Serializable> ActiveRecord<?,K> save(ActiveRecord<?,K> activeRecord, K existingId) {
        return transact(() -> {
            ActiveRecord<?,K> result = null;
            if (existingId != null) {
                if (getEntityManager().contains(activeRecord)) {
                    result = activeRecord;
                    //do nothing, state will save auto-magically, just flush to trigger the update
                } else if (existingId != null && !getEntityManager().contains(activeRecord) && getEntityManager().find(activeRecord.getClass(),existingId) != null) {
                    result = getEntityManager().merge(activeRecord);
                } else {
                    getEntityManager().persist(activeRecord);
                    result = activeRecord;
                }
            } else {
                getEntityManager().persist(activeRecord);
                result = activeRecord;
            }
            getEntityManager().flush();
            return result;
        });

    }

    @Override
    public <T> Optional<T> single(Class<T> type, String name, org.github.levelthree.activerecord.Parameter... parameters) {
        return (Optional<T>) transact(() -> {
            Query namedQuery = createNamedQuery(buildName(type, name), parameters);
            try {
                Object singleResult = namedQuery.getSingleResult();
                T singleResult1 = (T) singleResult;
                return Optional.ofNullable(singleResult1);

            } catch (NoResultException e) {
                return Optional.empty();
            } catch (NonUniqueResultException e) {
                return Optional.ofNullable((T) namedQuery.getResultList().get(0));
            }
        });

    }

    private <T> String buildName(Class<T> type, String name) {
        return String.join(".", type.getSimpleName(), name);
    }

    @Override
    public <T> Iterator<T> iterate(Class<T> type, String name, org.github.levelthree.activerecord.Parameter... parameters) {
        return transact(() -> {
            Query namedQuery = createNamedQuery(buildName(type, name), parameters);
            return namedQuery.getResultList().iterator();
        });
    }

    @Override
    public <T> Collection<T> all(Class<T> type) {
        return transact(() -> {
            return helper.findWhere(getEntityManager(), type);
        });
    }

    @Override
    public <T> Stream<T> stream(Class<T> type, String name, org.github.levelthree.activerecord.Parameter... parameters) {
        return transact(() -> {
            Query namedQuery = createNamedQuery(buildName(type, name), parameters);
            return namedQuery.getResultList().stream();
        });

    }

    @Override
    public <T> Collection<T> page(Class<T> type, String name, int start, int pageSize, org.github.levelthree.activerecord.Parameter... parameters) {
        return transact(() -> {
            Query namedQuery = createNamedQuery(buildName(type, name), parameters);
            namedQuery.setFirstResult(start);
            namedQuery.setMaxResults(pageSize);
            return namedQuery.getResultList();
        });
    }

    @Override
    public <T> Collection<T> pageWhere(Class<T> type, int start, int pageSize, Parameter ... parameters) {
        return transact(() -> {
            return helper.pageWhere(getEntityManager(), type, start, pageSize, parameters);
        });
    }

    @Override
    public <T> Collection<T> findWhere(Class<T> type, org.github.levelthree.activerecord.Parameter... parameters) {
        return transact(() -> {
            return helper.findWhere(getEntityManager(), type, parameters);
        });
    }


    @Override
    public void clear() {
        getEntityManager().clear();
    }

    @Override
    public long countWhere(Class<?> type, org.github.levelthree.activerecord.Parameter... parameters) {
        return transact(() -> {
            return helper.countWhere(getEntityManager(), type, parameters);
        });
    }

    @Override
    public void delete(ActiveRecord<?,?> record) {
        transact(() -> {
            if (!getEntityManager().contains(record)) {
                ActiveRecord result = getEntityManager().find(record.getClass(), record.getId());
                getEntityManager().remove(result);
            } else {
                getEntityManager().remove(record);
            }
            return null;
        });
    }


    private Query createNamedQuery(String name, org.github.levelthree.activerecord.Parameter... parameters) {
        Query namedQuery = getEntityManager().createNamedQuery(name);
        for (org.github.levelthree.activerecord.Parameter parameter : parameters) {
            namedQuery.setParameter(parameter.getName(), parameter.getValue());
        }
        return namedQuery;
    }


    @Override
    public void commitOrRollback(boolean success) {
        EntityTransaction transaction = null;
        EntityManager entityManager = getEntityManager();
        transaction = getTransaction(getEntityManager());
        if (transaction != null && transaction.isActive()) {
            if (success) {
                transaction.commit();
            } else {
                transaction.rollback();
            }
        }

    }


    public <T> EntityManager getEntityManager() {
        EntityManager entityManager = getThreadEntityManager();
        if (entityManager != null) {
            return entityManager;
        }
        if (ENTITY_MANAGER_FACTORY == null) {
            ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        }
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public <T> Object deleteAll(Class<T> entity) {
        return getEntityManager().createQuery("delete from "+entity.getSimpleName()).getSingleResult();
    }

    private EntityManager getThreadEntityManager() {
        return PROVIDER.get();
    }

    public static void reset() {
        ENTITY_MANAGER_FACTORY = null;
    }


}
