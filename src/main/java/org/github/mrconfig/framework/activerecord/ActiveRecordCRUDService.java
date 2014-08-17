package org.github.mrconfig.framework.activerecord;

import org.github.mrconfig.framework.Resource;
import org.github.mrconfig.framework.ResourceRegistry;
import org.github.mrconfig.framework.service.Active;
import org.github.mrconfig.framework.service.CRUDService;
import org.github.mrconfig.framework.service.Keyed;
import org.github.mrconfig.framework.service.Link;
import org.github.mrconfig.framework.util.Box;
import org.github.mrconfig.framework.util.GenericsUtil;
import org.github.mrconfig.framework.util.Pair;
import org.github.mrconfig.framework.util.TransformerService;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.github.mrconfig.framework.activerecord.ActiveRecord.*;
import static org.github.mrconfig.framework.activerecord.ActiveRecord.findWhere;
import static org.github.mrconfig.framework.activerecord.Parameter.p;
import static org.github.mrconfig.framework.util.Box.error;
import static org.github.mrconfig.framework.util.Box.success;
import static org.github.mrconfig.framework.util.Pair.cons;
import static org.github.mrconfig.framework.util.ReflectionUtil.createInstance;
import static org.github.mrconfig.framework.util.ReflectionUtil.resolveField;
import static org.github.mrconfig.framework.util.ReflectionUtil.setField;

/**
 * Created by w1428134 on 2014/08/04.
 */
public class ActiveRecordCRUDService<T extends ActiveRecord<T, K>, K extends Serializable> implements CRUDService<T, K> {

    Class<T> type;

    public ActiveRecordCRUDService(Class<T> type) {
        this.type = type;
    }

    @Override
    public Box<K> create(T instance) {
        return activeRecordSave(instance);
    }

    @Override
    public Link toLink(T instance) {
        return instance.toLink();
    }

    private Box<K> activeRecordSave(T instance) {
        try {
            T save = instance.save();
            return success(save.getId());
        } catch (Exception e) {
            return error(cons(e.getClass().getSimpleName(), e.getMessage()));
        }
    }

    @Override
    public Collection<T> list(Pair<String, Object>... parameters) {
        return findWhere(getType(), convert(parameters));
    }

    private Parameter[] convert(Pair<String, Object>[] parameters) {
        List<Parameter> parms = convertToParameters(parameters).collect(toList());
        return parms.toArray(new Parameter[]{});
    }

    private Stream<Parameter> convertToParameters(Pair<String, Object>[] parameters) {
        return Arrays.stream(parameters)
                .map((pair) -> p(pair.getCar(), pair.getCdr()));
    }


    private Class<T> getType() {
        return this.type;
    }

    @Override
    public Collection<T> page(int offset, int size, Pair<String, Object>... parameters) {
        return pageWhere(getType(), offset, size, convert(parameters));
    }

    @Override
    public long count(Pair<String, Object>... parameters) {
        return countWhere(getType(), convert(parameters));
    }

    @Override
    public Optional<T> resolve(Serializable id, Class<K> idType) {
        if (Keyed.class.isAssignableFrom(getType())) {
            return findWhere(getType(),p("key",id)).stream().findFirst();
        } else {
            K value = TransformerService.convert(id, idType);
            return findById(getType(),value);
        }
    }

    @Override
    public Optional<T> get(K id) {
        return findById(getType(), id);
    }

    @Override
    public Box<T> save(T instance) {
        return doWork(() -> {
            try {
                T result = instance.save();
                return success((T) result);
            } catch (Exception e) {
                return error(cons(e.getClass().getSimpleName(), e.getMessage()));
            }
        });
    }

    @Override
    public Box<T> delete(T instance) {
        try {
            if (instance instanceof Active) {
                ((Active) instance).deactivate();
            } else {
                instance.delete();
            }
            return success(instance);
        } catch (Exception e) {
            return error(cons(e.getClass().getSimpleName(), e.getMessage()));
        }
    }
}
