package org.github.mrconfig.framework.activerecord;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by w1428134 on 2014/07/11.
 */
public interface Provider {


    <T> Optional<T> findById(Class<T> type, Object id);

    <T> T transact(Supplier<T> supplier);

    void commitOrRollback(boolean success);

    void getTransaction();

    <T> T doWork(Supplier<T> work);

    <K> void save(ActiveRecord<?,K> activeRecord, K existingId);

    <T> Optional<T> single(Class<T> type, String name, Parameter... parameters);


    <T> Iterator<T> iterate(Class<T> type, String name, Parameter... parameters);

    <T> Collection<T> all(Class<T> type);

    <T> Stream<T> stream(Class<T> type, String name, Parameter... parameters);

    <T> Collection<T> page(Class<T> type,  String name, int start, int pageSize, Parameter... parameters);

    <T> Collection<T> page(Class<T> type, int start, int pageSize);

    <T> Collection<T> findWhere(Class<T> type, Parameter... parameters);
    <T> Collection<T> pageWhere(Class<T> type, int start, int pageSize, Parameter... parameters);

    void clear();

    long countWhere(Class<?> type, Parameter ... parameters);

    void delete(ActiveRecord<?,?> record);

}
