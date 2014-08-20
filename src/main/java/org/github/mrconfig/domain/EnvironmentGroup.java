package org.github.mrconfig.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by w1428134 on 2014/07/10.
 */
@Entity
@DiscriminatorValue("E")
@XmlRootElement(namespace="http://www.github.org/mrconfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class EnvironmentGroup extends Environment<EnvironmentGroup> {

    public EnvironmentGroup() {
        super();
    }

    public EnvironmentGroup(String id, String name, Environment parent, AdminGroup owner) {
        super(id, name, parent);
        setOwner(owner);
    }

    public EnvironmentGroup(String name, Environment parent) {
        super(name, parent);
    }

    public EnvironmentGroup(String name, Environment parent, AdminGroup owner) {
        super(name, parent, owner);
    }
}
