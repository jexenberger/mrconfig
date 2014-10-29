package org.github.mrconfig.domain;

import org.github.levelthree.activerecord.ActiveRecord;
import org.github.levelthree.service.Named;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.github.levelthree.activerecord.Parameter.p;

/**
 * Created by w1428134 on 2014/07/07.
 */
@javax.persistence.Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@NamedQueries({
        @NamedQuery(name = "Environment.findNameLike", query = "SELECT e FROM Environment e where e.setName like :setName"),
        @NamedQuery(name = "Environment.findByKey", query = "SELECT e FROM Environment e where e.id = :key")
})
@XmlRootElement(namespace = "http://www.github.org/mrconfig")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Environment<T extends Environment> extends KeyEntity<T> implements  Named {



    @Column(name = "name")
    @NotNull
    String name;

    @ManyToOne(targetEntity = AdminGroup.class, fetch = FetchType.LAZY)
    @NotNull
    AdminGroup owner;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    Environment parent;
    @Enumerated
    EnvironmentType deployment;

    public Environment() {
    }

    public Environment(String id, String name, Environment parent) {
        this(name,parent);
        setId(id);
    }

    public Environment(String name, Environment parent) {
        this.name = name;
        this.parent = parent;
        getOwner();
    }

    public Environment(String name, Environment parent, AdminGroup owner) {
        this.name = name;
        this.parent = parent;
        this.owner = owner;
    }

    @XmlElement(namespace = "http://www.github.org/mrconfig")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(namespace = "http://www.github.org/mrconfig")
    @XmlJavaTypeAdapter(AdminGroupAdapter.class)
    public AdminGroup getOwner() {
        if (owner == null && parent != null) {
            setOwner(parent.getOwner());
        }
        return owner;
    }

    public void setOwner(AdminGroup owner) {
        if (owner != null) {
            owner.addEnvironment(this);
        }
        this.owner = owner;
    }

    @XmlJavaTypeAdapter(ParentEnvironmentAdapter.class)
    @XmlElement(namespace = "http://www.w3.org/2005/Atom")
    public Environment getParent() {
        return parent;
    }

    @XmlElement(namespace = "http://www.github.org/mrconfig")
    public String getType() {
        return getClass().getSimpleName();
    }

    public void setType(String type) {
        //don't do anything..
    }

    public void setParent(Environment parent) {
        this.parent = parent;
    }

    @XmlTransient
    public Collection<PropertyValue> getValues() {
        Collection<PropertyValue> values = new ArrayList<>();
        Collection<PropertyValue> envVals = ActiveRecord.findWhere(PropertyValue.class, p("environment", getId()));
        values.addAll(envVals);
        if (parent != null) {
            Collection<PropertyValue> parentValues = parent.getValues();
            values.addAll(
                    parentValues.stream().filter((parentValue) -> {
                        return !values.stream().anyMatch((currVal) -> currVal.isSameProperty(parentValue));
                    }).collect(toList())
            );
        }
        return values;
    }

    @XmlElement(namespace = "http://www.github.org/mrconfig")
    public EnvironmentType getDeployment() {
        return deployment;
    }


    public void setDeployment(EnvironmentType deployment) {
        this.deployment = deployment;
    }

    public Map<String, Object> getModel() {
        PropertyModel model = new PropertyModel();
        Collection<PropertyValue> values = getValues();
        for (PropertyValue value : values) {
            model.addValue(value);
        }
        return model;
    }

    public static Environment newRoot(String name) {
        EnvironmentGroup environmentGroup = new EnvironmentGroup();
        environmentGroup.setName(name);
        return environmentGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Environment)) return false;

        Environment that = (Environment) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
