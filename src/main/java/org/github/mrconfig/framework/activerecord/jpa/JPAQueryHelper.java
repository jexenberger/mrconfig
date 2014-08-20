package org.github.mrconfig.framework.activerecord.jpa;

import org.github.mrconfig.framework.activerecord.Parameter;
import org.github.mrconfig.framework.util.GenericsUtil;
import org.github.mrconfig.framework.util.Pair;
import org.github.mrconfig.framework.util.ReflectionUtil;
import org.github.mrconfig.framework.util.TransformerService;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.util.stream.Collectors.toSet;
import static org.github.mrconfig.framework.util.Pair.cons;
import static org.github.mrconfig.framework.util.ReflectionUtil.resolveMethod;

/**
 * Created by julian3 on 2014/08/17.
 */
public class JPAQueryHelper {


    public <T> Collection<T> findWhere(EntityManager em, Class<T> type, Parameter... parameters) {
        TypedQuery<T> query = buildQuery(em, type, false, parameters);
        return query.getResultList();
    }

    public <T> long countWhere(EntityManager em, Class<T> type, Parameter... parameters) {
        TypedQuery<T> query = buildQuery(em, type, true, parameters);
        return (Long) query.getSingleResult();
    }

    public <T> Collection<T> pageWhere(EntityManager em, Class<T> type, long start, long size, Parameter... parameters) {
        TypedQuery<T> query = buildQuery(em, type, false, parameters);
        query.setFirstResult((int) start);
        query.setMaxResults((int) size);
        return query.getResultList();
    }

    public <T> TypedQuery<T> buildQuery(EntityManager em, Class<T> type, boolean count, Parameter... parameters) {
        EntityGraph<T> entityGraph = em.createEntityGraph(type);
        Collection<Field> typeFields = ReflectionUtil.getAllFields(type);
        final AtomicReference<Field> idField = new AtomicReference<>(null);
        Map<String, Field> fieldMap = new HashMap<>(typeFields.size() + 1, 1.0f);
        Collection<String> allFields = typeFields
                .stream()
                .map((field) -> {
                    fieldMap.put(field.getName(), field);
                    return field;
                })
                .filter((field) -> {
                    if (field.isAnnotationPresent(Id.class)) {
                        idField.set(field);
                    }
                    boolean result = false;
                    if (Collection.class.isAssignableFrom(field.getType())) {
                        result = true;
                    }
                    if (field.getType().isAnnotationPresent(Entity.class) || field.getType().isAnnotationPresent(MappedSuperclass.class)) {
                        result = true;
                    }
                    if (field.isAnnotationPresent(OneToOne.class) || field.isAnnotationPresent(OneToMany.class) || field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(ManyToMany.class)) {
                        result = true;
                    }
                    Method getMethod = resolveMethod(type, ReflectionUtil.getMethod(field));
                    if (idField.get() == null && getMethod.isAnnotationPresent(Id.class)) {
                        idField.set(field);
                    }
                    if (getMethod != null && (getMethod.getReturnType().isAnnotationPresent(Entity.class) || getMethod.getReturnType().isAnnotationPresent(MappedSuperclass.class))) {
                        result = true;
                    }

                    return result;
                })
                .map(Field::getName)
                .collect(toSet());

        if (!count) {
            //entityGraph.addAttributeNodes(allFields.toArray(new String[]{}));
        }

        CriteriaBuilder cb = em.getCriteriaBuilder();
        Root<T> root = null;
        CriteriaQuery cq = null;
        if (count) {
            cq = cb.createQuery(Long.class);
            root = cq.from(type);
            cq.select(cb.count(root));
        } else {
            cq = cb.createQuery(type);
            root = cq.from(type);
        }
        boolean isFirst = true;
        Collection<Predicate> predicates = new ArrayList<>(parameters.length);
        for (Parameter parameter : parameters) {
            Object parameterValue = parameter.getValue();
            boolean isNull = (parameterValue == null);
            boolean isLike = !isNull && (parameterValue instanceof String) && parameterValue.toString().indexOf('*') > -1;
            boolean isRelationship = allFields.contains(parameter.getName());
            From<T, ?> path = root;
            Predicate clause = null;
            String fieldName = parameter.getName();
            Field field = fieldMap.get(fieldName);
            Class<?> fieldType = field.getType();
            if (isRelationship) {
                if (!isNull && (parameterValue.getClass().getPackage().getName().startsWith("java") || parameterValue.getClass().isPrimitive())) {
                    path = root.join(parameter.getName());
                    fieldName = idField.get().getName();
                    field = idField.get();
                    fieldType = ReflectionUtil.getAllFields(fieldType).stream().filter((typeField)->typeField.isAnnotationPresent(Id.class)).findFirst().get().getType();
                }
            }
            if(isNull) {
                clause = cb.isNull(path.get(fieldName));
            } else {
                if (isLike) {
                    if (Date.class.isAssignableFrom(fieldType) && parameterValue instanceof String) {
                        Pair<Date, Date> datePair = calcWildCardDateRange(parameterValue.toString());
                        clause = cb.between(path.get(fieldName), datePair.getCar(), datePair.getCdr());
                    } else {
                        clause = cb.like(path.get(fieldName), parameterValue.toString().replace('*', '%'));
                    }
                } else {
                    if (!parameterValue.getClass().equals(fieldType)) {
                        try {
                            parameterValue = TransformerService.convert(parameterValue, fieldType);
                        } catch (Exception e) {
                            System.out.println(parameterValue+" - "+ fieldType.getName()+ " - "+field.getName()+" - "+type.getName());
                            throw e;
                        }
                    }
                    clause = cb.equal(path.get(fieldName), parameterValue);
                }
            }
            if (isFirst) {
                predicates.add(clause);
                isFirst = false;
            } else {
                predicates.add(cb.and(clause));
            }

        }
        cq.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<T> query = em.createQuery(cq);
        //query.setHint("javax.persistence.loadgraph", entityGraph);
        return query;
    }


    static Pair<Date, Date> calcWildCardDateRange(String date) {

        String parseDate = date.substring(0, date.lastIndexOf("*"));
        if (parseDate.indexOf("*") > -1) {
            throw new IllegalArgumentException("can query on date of " + date);
        }
        //do year
        if (parseDate.length() <= 4) {
            String startYear = parseDate;
            String endYear = parseDate;
            while (startYear.length() < 4) {
                startYear += "0";
                endYear += "9";
            }

            Date start = Date.from(LocalDateTime.of(Integer.parseInt(startYear), 01, 01, 0, 0, 0).atZone(ZoneId.systemDefault()).toInstant());
            Date end = Date.from(LocalDateTime.of(Integer.parseInt(endYear), 12, 31, 0, 0, 0).atZone(ZoneId.systemDefault()).toInstant());
            return cons(start, end);
        }

        int year = Integer.parseInt(parseDate.substring(0, 4));
        String month = parseDate.substring(4);
        int startMonth = Integer.parseInt(month);
        int endMonth = 0;
        if (month.length() == 2) {
            endMonth = startMonth;
        } else if (month.equals("0")) {
            startMonth = 1;
            endMonth = 9;
        } else {
            startMonth = 10;
            endMonth = 12;
        }

        LocalDateTime startLDT = LocalDateTime.of(year, startMonth, 01, 0, 0);
        LocalDateTime initialLDT = LocalDateTime.of(year, endMonth, 01, 0, 0);
        LocalDateTime endLDT = initialLDT.with(lastDayOfMonth());

        Date start = Date.from(startLDT.atZone(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(endLDT.atZone(ZoneId.systemDefault()).toInstant());
        return cons(start, end);


    }


}
