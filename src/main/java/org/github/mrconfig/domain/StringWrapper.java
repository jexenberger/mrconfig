package org.github.mrconfig.domain;

/**
 * Created by julian3 on 2014/07/19.
 */
public class StringWrapper extends Wrapper{

    public StringWrapper(String value) {
        super(value);
    }

    public String upper() {
        if (getValue() != null) {
            return getValue().toUpperCase();
        } else {
            return null;
        }
    }

    public String lower() {
        if (getValue() != null) {
            return getValue().toLowerCase();
        } else {
            return null;
        }
    }

}
