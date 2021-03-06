package org.github.levelthree.resources;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by julian3 on 2014/07/19.
 */
@XmlRootElement(namespace = "http://www.github.org/mrconfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class Errors {

    @XmlElement(namespace = "http://www.github.org/mrconfig")
    private Collection<Error> errors = new ArrayList<>();

    public Errors() {
    }

    public Errors(Collection<Error> errors) {
        this.errors = errors;
    }

    public Errors(Error ... errors) {
        for (Error error : errors) {
            getErrors().add(error);
        }
    }

    public Collection<Error> getErrors() {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        return errors;
    }

    public void setErrors(Collection<Error> errors) {
        this.errors = errors;
    }

    public static Errors errors(Error ... errors) {
        return new Errors(errors);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Errors)) return false;

        Errors errors1 = (Errors) o;

        if (!errors.equals(errors1.errors)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return errors.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Errors");
        sb.append(errors);
        return sb.toString();
    }
}
