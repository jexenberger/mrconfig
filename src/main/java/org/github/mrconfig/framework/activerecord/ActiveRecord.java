package org.github.mrconfig.framework.activerecord;

import org.github.mrconfig.framework.util.Inflector;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * Created by w1428134 on 2014/07/11.
 */
public interface ActiveRecord<T extends ActiveRecord, K> {


    public static  <T> Optional<T> findById(Class<T> type, Object id) {
        requireNonNull(type, "type required");
        requireNonNull(id, "id cannot be null");
        return getProvider().findById(type, id);
    }

    public static  <T> Collection<T> all(Class<T> type) {
        requireNonNull(type, "type required");
        return getProvider().all(type);
    }


    public static  <T> Collection<T> findWhere(Class<T> type, Parameter ... parameters) {
        requireNonNull(type, "type required");
        return getProvider().findWhere(type, parameters);
    }


    public static  <T> Collection<T> pageWhere(Class<T> type, int start, int size, Parameter ... parameters) {
        requireNonNull(type, "type required");
        return getProvider().pageWhere(type, start, size, parameters);
    }

    public static long countWhere(Class<?> type, Parameter ... parameters) {
        requireNonNull(type, "type required");
        return getProvider().countWhere(type, parameters);
    }




    static  Provider getProvider() {
        return ProviderFactory.getProvider();
    }

    static <T> Optional<T> single(Class<T> type, String name, Parameter... parameters) {
        requireNonNull(type, "type required");
        requireNonNull(name, "name required");
        return getProvider().single(type, name, parameters);
    }


    static <T> Iterator<T> iterate(Class<T> type, String name, Parameter... parameters) {
        requireNonNull(type, "type required");
        requireNonNull(name, "name required");
        return getProvider().iterate(type, name, parameters);
    }

    static <T> Stream<T> stream(Class<T> type, String name, Parameter... parameters) {
        requireNonNull(type, "type required");
        requireNonNull(name, "name required");
        return getProvider().stream(type, name, parameters);
    }

    static <T> Collection<T> page(Class<T> type,  String name, int start, int pageSize, Parameter... parameters) {
        requireNonNull(type, "type required");
        requireNonNull(name, "name required");
        return getProvider().page(type, name, start, pageSize, parameters);
    }

    static <T> Collection<T> page(Class<T> type,  int start, int pageSize) {
        requireNonNull(type, "type required");
        return getProvider().page(type,start, pageSize);
    }



    public K getId();

    public default T save() {
        getProvider().save(this, getId());
        return (T) this;
    }

    public default T delete() {
        if (this instanceof Active) {
            ((Active) this).deactivate();
        } else {
            getProvider().delete(this);
        }
        return (T) this;
    }

    public default Link toLink() {
        return toLink(Inflector.getInstance().pluralize(getClass().getSimpleName()).toLowerCase());
    }

    public default Link toLink(String entityName) {
        Object idToUse = getReference();

        return new Link(getId().toString(),null, String.join("/", entityName,idToUse.toString()), resolveName());
    }

    @XmlTransient
    default Object getReference() {
        return (this instanceof Keyed) ? ((Keyed) this).getKey() : getId();
    }

    default String resolveName() {
        if (this instanceof Named) {
            return ((Named) this).getName();
        }
        if (this instanceof Keyed) {
            return ((Keyed) this).getKey();
        }
        return getId().toString();
    }

    public  static <T> T doWork(Supplier<T> supplier) {
       return getProvider().doWork(supplier);
    }

}
