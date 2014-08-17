package org.github.mrconfig.framework.activerecord.jpa;

import org.github.mrconfig.framework.activerecord.*;
import org.github.mrconfig.framework.activerecord.Parameter;
import org.github.mrconfig.framework.service.Keyed;
import org.github.mrconfig.framework.util.TransformerService;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.github.mrconfig.framework.util.ReflectionUtil.resolveField;

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

    public static void setPERSISTENCE_UNIT(String unit) {
        reset();
        PERSISTENCE_UNIT = unit;
    }

    public static String getEmptyIndicator() {
        return NULL;
    }

    public static void setEmptyIndicator(String NULL) {
        JPAProvider.NULL = NULL;
    }

    public static String getPERSISTENCE_UNIT() {
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
            PROVIDER.set(entityManager);
            transaction = entityManager.getTransaction();
            if (!transaction.isActive()) {
                transaction.begin();
            } else {
                nested = true;
            }
            T result = supplier.get();
            entityManager.flush();
            success = true;
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
                PROVIDER.remove();
            }
        }
    }


    @Override
    public void getTransaction() {
        EntityTransaction transaction = null;
        EntityManager entityManager = getEntityManager();
        if (!entityManager.isJoinedToTransaction()) {
            transaction.begin();
            transaction = entityManager.getTransaction();
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
    public <T> Optional<T> single(Class<T> type, String name, org.github.mrconfig.framework.activerecord.Parameter... parameters) {
        return transact(() -> {
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
    public <T> Iterator<T> iterate(Class<T> type, String name, org.github.mrconfig.framework.activerecord.Parameter... parameters) {
        return transact(() -> {
            Query namedQuery = createNamedQuery(buildName(type, name), parameters);
            return namedQuery.getResultList().iterator();
        });
    }

    @Override
    public <T> Collection<T> all(Class<T> type) {
        return transact(() -> {
            return helper.findWhere(getEntityManager(),type);
        });
    }

    @Override
    public <T> Stream<T> stream(Class<T> type, String name, org.github.mrconfig.framework.activerecord.Parameter... parameters) {
        return transact(() -> {
            Query namedQuery = createNamedQuery(buildName(type, name), parameters);
            return namedQuery.getResultList().stream();
        });

    }

    @Override
    public <T> Collection<T> page(Class<T> type, String name, int start, int pageSize, org.github.mrconfig.framework.activerecord.Parameter... parameters) {
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
    public <T> Collection<T> findWhere(Class<T> type, org.github.mrconfig.framework.activerecord.Parameter... parameters) {
        return transact(() -> {
            return helper.findWhere(getEntityManager(), type, parameters);
        });
    }


    @Override
    public void clear() {
        getEntityManager().clear();
    }

    @Override
    public long countWhere(Class<?> type, org.github.mrconfig.framework.activerecord.Parameter... parameters) {
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


    private Query createNamedQuery(String name, org.github.mrconfig.framework.activerecord.Parameter... parameters) {
        Query namedQuery = getEntityManager().createNamedQuery(name);
        for (org.github.mrconfig.framework.activerecord.Parameter parameter : parameters) {
            namedQuery.setParameter(parameter.getName(), parameter.getValue());
        }
        return namedQuery;
    }


    @Override
    public void commitOrRollback(boolean success) {
        EntityTransaction transaction = null;
        EntityManager entityManager = getEntityManager();
        transaction = getEntityManager().getTransaction();
        if (transaction != null && transaction.isActive()) {
            if (success) {
                transaction.commit();
            } else {
                transaction.setRollbackOnly();
            }
        }

    }


    public <T> EntityManager getEntityManager() {
        if (ENTITY_MANAGER_FACTORY == null) {
            ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        }
        EntityManager entityManager = PROVIDER.get();
        if (entityManager != null) {
            return entityManager;
        }
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public static void reset() {
        ENTITY_MANAGER_FACTORY = null;
    }


}
