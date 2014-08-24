package org.github.mrconfig.framework.activerecord;

import org.github.mrconfig.framework.resources.Errors;
import org.github.mrconfig.framework.service.Active;
import org.github.mrconfig.framework.service.CRUDService;
import org.github.mrconfig.framework.service.Keyed;
import org.github.mrconfig.framework.service.Link;
import org.github.mrconfig.framework.util.Box;
import org.github.mrconfig.framework.util.Pair;
import org.github.mrconfig.framework.util.TransformerService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.github.mrconfig.framework.activerecord.ActiveRecord.*;
import static org.github.mrconfig.framework.activerecord.Parameter.p;
import static org.github.mrconfig.framework.util.Box.error;
import static org.github.mrconfig.framework.util.Box.success;
import static org.github.mrconfig.framework.util.Pair.cons;

/**
 * Created by w1428134 on 2014/08/04.
 */
public class ActiveRecordCRUDService<T extends ActiveRecord<T, K>, K extends Serializable> implements CRUDService<T, K> {

    Class<T> type;
    public static final ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();

    public ActiveRecordCRUDService(Class<T> type) {
        this.type = type;
    }

    @Override
    public Box<K> create(T instance) {
        Box<K> result = activeRecordSave(instance, false);
        return result;
    }

    @Override
    public Link toLink(T instance) {
        return instance.toLink();
    }

    private Box<K> activeRecordSave(T instance, boolean loadFirst) {

        return doWork(() -> {
            try {
                if (loadFirst) {
                    Optional<T> result = findById((Class<T>) instance.getClass(), instance.getId());
                    if (!result.isPresent()) {
                        return error(cons("not.found", instance.getId() + " not found"));
                    }
                }
                Collection<Pair<String, String>> converted = validate(instance);
                if (!converted.isEmpty()) {
                    return error(converted.toArray(new Pair[]{}));
                }
                T save = instance.save();
                return success((K) instance.getId());
            } catch (Exception e) {
                return error(cons(e.getClass().getSimpleName(), e.getMessage()));
            }
        });
    }

    private Collection<Pair<String, String>> validate(T instance) {
        Validator validator = VALIDATOR_FACTORY.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(instance);
        Collection<Pair<String,String>> converted = Collections.emptyList();
        if (!violations.isEmpty()) {
            converted =  violations.stream().map((violation)-> cons("value.invalid",violation.getPropertyPath().toString()+":"+violation.getMessage())).collect(toList());
        }
        return converted;
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
            return findWhere(getType(), p("key", id)).stream().findFirst();
        } else {
            K value = TransformerService.convert(id, idType);
            return findById(getType(), value);
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
                Optional<T> existing = findById((Class<T>) instance.getClass(), instance.getId());
                if (!existing.isPresent()) {
                    return error(cons("does.not.exist", "instance doesn't exist"));
                }
                Collection<Pair<String, String>> converted = validate(instance);
                if (!converted.isEmpty()) {
                    return error(converted.toArray(new Pair[]{}));
                }
                T result = instance.save();
                return success((T) result);
            } catch (Exception e) {
                return error(cons(e.getClass().getSimpleName(), e.getMessage()));
            }
        });
    }

    @Override
    public Box<T> delete(K id) {
        try {
            Optional<T> result = findById(getType(), id);
            if (result.isPresent()) {
                T instance = result.get();
                if (instance instanceof Active) {
                    ((Active) instance).deactivate();
                } else {
                    instance.delete();
                }
                return success(instance);
            } else {
                return error(cons("not.found",id.toString()));
            }
        } catch (Exception e) {
            return error(cons(e.getClass().getSimpleName(), e.getMessage()));
        }
    }
}
