package org.github.mrconfig.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Map;

/**
 * Created by w1428134 on 2014/07/07.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "PropertyValue.findByPropertyAndEnvironment", query = "select pv from PropertyValue pv JOIN FETCH pv.property JOIN FETCH pv.environment where pv.property.key = :key AND pv.environment.key = :key")
})
@XmlRootElement(namespace = "http://www.github.org/mrconfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class PropertyValue extends BaseEntity<PropertyValue> {

    @XmlElement(namespace = "http://www.github.org/mrconfig")
    String value;
    @ManyToOne(fetch = FetchType.EAGER)
    @XmlJavaTypeAdapter(PropertyAdapter.class)
    @XmlElement(namespace = "http://www.w3.org/2005/Atom")
    Property property;
    @ManyToOne(optional=false)
    @XmlJavaTypeAdapter(RelatedEnvironmentAdapter.class)
    @XmlElement(namespace = "http://www.w3.org/2005/Atom")
    Environment environment;

    public PropertyValue() {
    }

    public PropertyValue(String value, Property property, Environment environment) {
        this.value = value;
        this.property = property;
        this.environment = environment;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Environment getEnvironment() {

        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public boolean isSameProperty(PropertyValue value) {
        return getProperty().getKey().equals(value.getProperty().getKey());
    }

    public String getPropertyKey() {
        return getProperty().getKey();
    }

    public Wrapper getWrapper() {
        return getProperty().getType().getWrapper(getValue());
    }

}
