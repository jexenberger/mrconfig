package org.github.mrconfig.framework.util;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by w1428134 on 2014/08/04.
 */
public class Box<T> {

    public enum State {
        success,
        nothing,
        error
    }

    private Collection<Pair<String, String>> errors = new ArrayList<>();
    private T result;
    private State state;

    protected Box(Collection<Pair<String, String>> errors, T result, State state) {
        this.errors = errors;
        this.result = result;
        this.state = state;
    }

    public static <T> Box<T> success(T result) {
        return new Box<T>(null, result, State.success);
    }

    public static <T> Box<T> nothing() {
        return new Box<T>(null, null, State.nothing);
    }

    public static <T> Box<T> error(Pair<String, String> ... errors) {
        return new Box<T>(Arrays.asList(errors), null, State.error);
    }



    public <K> Collection<K> mapError(BiFunction<String, String,K> function) {
        if (this.errors != null && this.errors.size() > 0) {
            return this.errors.stream().map((error) -> function.apply(error.getCar(), error.getCdr())).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }



    public <K> Optional<K> onSuccess(Function<T,K> consumer) {
        if (isPresent() && State.success.equals(state)) {
          return Optional.ofNullable(consumer.apply(this.result));
        }
        return Optional.empty();
    }

    public boolean isPresent() {
        return this.result != null;
    }

     public boolean isSuccess() {
        return isPresent() && State.success.equals(this.state);
    }

    public Collection<Pair<String, String>> getErrors() {
        if (errors == null) {
            this.errors= new ArrayList<>();
        }
        return Collections.unmodifiableCollection(errors);
    }

    public <K> Optional<K> onNothing(Supplier<K> consumer) {
        if (!isPresent() && State.nothing.equals(this.state)) {
            return Optional.ofNullable(consumer.get());
        }
        return Optional.empty();
    }

    public <K> Optional<K> onError(Function<Collection<Pair<String, String>>,K> consumer) {
        if (!isPresent() && State.error.equals(this.state)) {
            return Optional.ofNullable(consumer.apply(this.getErrors()));
        }
        return Optional.empty();
    }

    public State getState() {
        return state;
    }

    public T get() {
        return this.result;
    }
}
