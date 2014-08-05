package org.github.mrconfig.domain;

/**
 * Created by julian3 on 2014/07/19.
 */
public class Wrapper {
    String value;

    public Wrapper(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
