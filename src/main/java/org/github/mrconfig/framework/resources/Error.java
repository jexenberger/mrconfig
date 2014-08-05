package org.github.mrconfig.framework.resources;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by julian3 on 2014/07/19.
 */
@XmlType(namespace = "http://www.github.org/mrconfig")
public class Error {

    @XmlAttribute(namespace = "http://www.github.org/mrconfig",required=true)
    String code;
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    String description;

    public Error() {
    }

    public Error(String code, String description) {
        this.code = code;
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
        final StringBuilder sb = new StringBuilder("Error{");
        sb.append("code='").append(code).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}


