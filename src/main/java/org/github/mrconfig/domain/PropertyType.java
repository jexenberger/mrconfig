package org.github.mrconfig.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

/**
 * Created by julian3 on 2014/07/19.
 */
public enum PropertyType {


    string(String.class,StringWrapper.class, (value) -> true),
    number(Double.class, StringWrapper.class, (value) -> (value.getValue() != null) ? value.getValue().matches("[\\d*]") : true),
    bool(Boolean.class, BooleanWrapper.class, (value) -> getBooleanCombinations().contains(value.getValue().trim().toLowerCase())),
    secure(String.class, StringWrapper.class, (value) -> true);

    Class<?> targetType;
    Function<PropertyValue, Boolean> validator = null;
    Class<? extends Wrapper> wrapperType;

    PropertyType(Class<?> targetType, Class<? extends Wrapper> wrapperType, Function<PropertyValue, Boolean> validator) {
        this.targetType = targetType;
        this.wrapperType = wrapperType;
        this.validator = validator;
    }

    public boolean isValidFormat(PropertyValue value) {
        return this.validator.apply(value);
    }


    public static Collection<String> getBooleanCombinations() {
        return Arrays.asList("true", "false", "t", "f", "y", "n", "yes", "no", "0", "1");

    }

    public Wrapper getWrapper(String value) {
        try {
            return this.wrapperType.getConstructor(String.class).newInstance(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
