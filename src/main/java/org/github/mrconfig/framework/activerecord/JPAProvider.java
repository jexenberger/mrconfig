package org.github.mrconfig.framework.activerecord;

import javax.persistence.*;
import java.lang.reflect.Field;
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
    private static EntityManagerFactory entityManagerFactory;
    private static String persistenceUnit = "default";
    private static ThreadLocal<EntityManager> PROVIDER = new ThreadLocal<>();

    public JPAProvider() {

    }

    public static void setPersistenceUnit(String unit) {
        reset();
        persistenceUnit = unit;
    }

    public static String getEmptyIndicator() {
        return NULL;
    }

    public static void setEmptyIndicator(String NULL) {
        JPAProvider.NULL = NULL;
    }

    public static String getPersistenceUnit() {
        return persistenceUnit;
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
    public <K> ActiveRecord<?,K> save(ActiveRecord<?,K> activeRecord, K existingId) {
        return transact(() -> {
            ActiveRecord<?,K> result = null;
            if (existingId != null) {
                if (getEntityManager().contains(activeRecord)) {
                    result = activeRecord;
                    //do nothing, state will save auto-magically
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
            return result;
        });

    }

    @Override
    public <T> Optional<T> single(Class<T> type, String name, Parameter... parameters) {
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
    public <T> Iterator<T> iterate(Class<T> type, String name, Parameter... parameters) {
        return transact(() -> {
            Query namedQuery = createNamedQuery(buildName(type, name), parameters);
            return namedQuery.getResultList().iterator();
        });
    }

    @Override
    public <T> Collection<T> all(Class<T> type) {
        return transact(() -> {
            Query namedQuery = getEntityManager().createQuery("from " + type.getSimpleName());
            return namedQuery.getResultList();
        });
    }

    @Override
    public <T> Stream<T> stream(Class<T> type, String name, Parameter... parameters) {
        return transact(() -> {
            Query namedQuery = createNamedQuery(buildName(type, name), parameters);
            return namedQuery.getResultList().stream();
        });

    }

    @Override
    public <T> Collection<T> page(Class<T> type, String name, int start, int pageSize, Parameter... parameters) {
        return transact(() -> {
            Query namedQuery = createNamedQuery(buildName(type, name), parameters);
            namedQuery.setFirstResult(start);
            namedQuery.setMaxResults(pageSize);
            return namedQuery.getResultList();
        });
    }

    @Override
    public <T> Collection<T> page(Class<T> type, int start, int pageSize) {
        return transact(() -> {
            Query namedQuery = getEntityManager().createQuery("from " + type.getSimpleName());
            namedQuery.setFirstResult(start);
            namedQuery.setMaxResults(pageSize);
            return namedQuery.getResultList();
        });
    }

    @Override
    public <T> Collection<T> findWhere(Class<T> type, Parameter... parameters) {
        return transact(() -> {
            Query namedQuery = createWhereQuery(type, parameters);
            return namedQuery.getResultList();
        });
    }

    <T> Query createWhereQuery(Class<T> type, Parameter... parameters) {
        return createWhereQuery(type, String.join(" ", "select x from", type.getSimpleName(), "x"), false, parameters);
    }

    <T> Query createWhereQuery(Class<T> type, String selectClause, boolean disableFetch, Parameter... parameters) {

        String qlString = generateQuery(type, selectClause, disableFetch, true, parameters);

        Query namedQuery = getEntityManager().createQuery(qlString);
        for (Parameter parameter : parameters) {

            Object value = (parameter.getValue() instanceof String && ((String) parameter.getValue()).contains("*")) ? parameter.getValue().toString().replace('*', '%') : parameter.getValue();
            if ("null".equals(parameter.getValue())) {
                continue;
            }
            if (value != null) {
                Class parmType = namedQuery.getParameter(parameter.getName()).getParameterType();
                if (!String.class.isAssignableFrom(parmType)) {
                    value = TransformerService.convert(value, parmType);
                }
            }
            namedQuery.setParameter(parameter.getName(), value);
        }
        return namedQuery;
    }

    <T> String generateQuery(Class<T> type, String selectClause, boolean disableFetch, boolean orderBy, Parameter... parameters) {
        String qlString = selectClause;
        String clause = "";
        if (parameters.length > 0) {
            String joins = "";
            for (Parameter parameter : parameters) {
                String idParam = "";
                Class<?> fieldType = resolveField(type, parameter);

                if (ActiveRecord.class.isAssignableFrom(fieldType)) {
                    if (!NULL.equals(parameter.getValue())) {
                        if (!disableFetch) {
                            joins += " JOIN FETCH x." + parameter.getName();
                        }
                        idParam = ".id";
                        if (Keyed.class.isAssignableFrom(fieldType)) {
                            try {
                                Long.parseLong(parameter.getValue().toString());
                            } catch (NumberFormatException e) {
                                idParam = ".key";
                            }
                        }
                    }
                }


                String operator = (parameter.getValue() instanceof String && parameter.getValue().toString().contains("*")) ? "like" : "=";
                if ("null".equals(parameter.getValue())) {
                    clause = String.join(" ", clause, "x." + parameter.getName() + idParam, "is null", "AND");
                } else {
                    clause = String.join(" ", clause, "x." + parameter.getName() + idParam, operator, ":" + parameter.getName(), "AND");
                }
            }
            clause = clause.trim();
            clause = clause.substring(0, clause.length() - 3);
            qlString += joins + " where " + clause;
        }
        /*
        if (orderBy && Named.class.isAssignableFrom(type)) {
            qlString += " order by x.name";
        }*/
        return qlString;
    }

    private Class<?> resolveField(Class<?> type, Parameter parameter) {
        Field field = null;
        try {
            field = type.getDeclaredField(parameter.getName().trim());
        } catch (NoSuchFieldException e) {
            if (type.getSuperclass() != null) {
                return resolveField(type.getSuperclass(), parameter);
            }
            throw new RuntimeException(e);
        }
        return field.getType();
    }

    @Override
    public <T> Collection<T> pageWhere(Class<T> type, int start, int pageSize, Parameter... parameters) {
        return transact(() -> {
            Query namedQuery = createWhereQuery(type, parameters);
            namedQuery.setFirstResult(start);
            namedQuery.setMaxResults(pageSize);
            return namedQuery.getResultList();
        });
    }

    @Override
    public void clear() {
        getEntityManager().clear();
    }

    @Override
    public long countWhere(Class<?> type, Parameter... parameters) {
        return transact(() -> {
            Query namedQuery = createWhereQuery(type, String.join(" ", "select count(x) from", type.getSimpleName(), "x"), true, parameters);
            return (Long) namedQuery.getSingleResult();
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


    private Query createNamedQuery(String name, Parameter... parameters) {
        Query namedQuery = getEntityManager().createNamedQuery(name);
        for (Parameter parameter : parameters) {
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
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
        }
        EntityManager entityManager = PROVIDER.get();
        if (entityManager != null) {
            return entityManager;
        }
        return entityManagerFactory.createEntityManager();
    }

    public static void reset() {
        entityManagerFactory = null;
    }


}
