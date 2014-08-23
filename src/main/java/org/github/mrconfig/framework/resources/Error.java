package org.github.mrconfig.framework.resources;

import javax.xml.bind.annotation.*;

/**
 * Created by julian3 on 2014/07/19.
 */
@XmlType(namespace = "http://www.github.org/mrconfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class Error {

    private static final String NOT_FOUND = "not.found";
    private static final String SAVE_ERROR = "save.error";
    private static final String INVALID_ID = "invalid.id";
    private static final String INVALID_VALUE = "invalid.value";


    @XmlAttribute(namespace = "http://www.github.org/mrconfig",required=true)
    String code;
    @XmlAttribute(namespace = "http://www.github.org/mrconfig")
    String ref;
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    String description;

    public Error() {
    }

    public Error(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public Error(String code, String ref, String description) {
        this.code = code;
        this.ref = ref;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Error error(String code, String description) {
        return new Error(code, description);
    }

    public static Error error(String code, String ref, String description) {
        return new Error(code, ref,description);
    }

    public static Error notFound() {
        return error(NOT_FOUND,"Requsted resource was not found");
    }
    public static Error invalidID(Object id) {
        return error(INVALID_ID,"'"+id+"' is not a valid id");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Error)) return false;

        Error error = (Error) o;

        if (!code.equals(error.code)) return false;
        if (description != null ? !description.equals(error.description) : error.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public String toString() {
        return "Error{" +
                "code='" + code + '\'' +
                ", ref='" + ref + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getRef() {
        return ref;
    }

    public static Error invalidValue(String message) {
        return error(INVALID_VALUE,message);
    }

    public static Error invalidValue(String code, String message) {
        return error(INVALID_VALUE,message);
    }
}


