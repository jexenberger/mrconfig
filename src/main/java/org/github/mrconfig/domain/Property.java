package org.github.mrconfig.domain;

import org.github.levelthree.activerecord.ActiveRecord;
import org.github.levelthree.util.Pair;

import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;
import java.util.Properties;

import static org.github.levelthree.util.Pair.cons;
import static org.github.levelthree.activerecord.Parameter.p;

/**
 * Created by w1428134 on 2014/07/07.
 */
@javax.persistence.Entity
@Table(indexes = {@Index(unique = true, columnList = "name,propertyGroup,category")})
@XmlRootElement(namespace = "http://www.github.org/mrconfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class Property extends KeyEntity<Property> {


    @XmlElement(namespace = "http://www.github.org/mrconfig")
    String name;
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    String propertyGroup;
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    String category;
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    boolean required;
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    @Enumerated
    PropertyType type = PropertyType.string;

    public Property() {
    }

    public static Pair<Property, PropertyValue> fromKeyValue(Property p, String key, String value, Environment owner) {
        String[] split = key.split("\\.");
        p = (p == null) ? new Property() : p;
        p.setId(key);
        if (split.length >= 1) {
            p.setName(split[split.length - 1]);
        }
        if (split.length >= 2) {
            p.setPropertyGroup(split[split.length - 2]);
        }
        if (split.length >= 3) {
            p.setCategory(split[split.length - 3]);
        }
        p.setType(PropertyType.string);
        if (value == null || "".equals(value.trim())) {
            p.setRequired(false);
        } else {
            p.setRequired(true);
        }
        PropertyValue propVal = new PropertyValue(value, p, owner);
        return cons(p, propVal);
    }

    public Property(String name, String propertyGroup, String category, boolean required, PropertyType type) {
        this.name = name;
        this.propertyGroup = propertyGroup;
        this.category = category;
        this.required = required;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPropertyGroup() {
        return propertyGroup;
    }

    public void setPropertyGroup(String propertyGroup) {
        this.propertyGroup = propertyGroup;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isValidFormat(PropertyValue value) {
        return this.type.isValidFormat(value);
    }

    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }


    public static boolean importProperties(Environment linkedEnvironment, InputStream source) {
        Properties props = new Properties();
        try {
            props.load(source);
            return ActiveRecord.doWork(() -> {
                Optional<Environment> result = ActiveRecord.findById(Environment.class, linkedEnvironment.getId());
                if (!result.isPresent()) {
                    return false;
                }
                props.forEach((key, value) -> {
                    Optional<Property> byKey = ActiveRecord.findById(Property.class, (Serializable) key);
                    Pair<Property, PropertyValue> propAndVal = fromKeyValue(byKey.orElseGet(()->null), (String) key, (String) value, result.get());
                    if (!byKey.isPresent()) {
                        System.out.println("saving!! ***** -> "+propAndVal.getCar());
                        Property car = propAndVal.getCar();
                        car.save();
                    }
                    Property property = new Property();
                    Collection<PropertyValue> values = ActiveRecord.findWhere(PropertyValue.class, p("property", propAndVal.getCar()), p("environment", result.get()));
                    if (values.size() == 0) {
                        propAndVal.getCdr().save();
                    }
                });
                return true;
            });

        } catch (IOException e) {
            return false;
        }


    }

}
