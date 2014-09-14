package org.github.mrconfig.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by w1428134 on 2014/08/29.
 */
@Entity
@XmlRootElement(namespace = "http://www.github.org/mrconfig")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DataCentre extends BaseEntity<DataCentre> {


    @NotNull
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    String name;

    @XmlElement(namespace = "http://www.github.org/mrconfig")
    Address address;

    public DataCentre() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        if (address == null) {
            address = new Address();
        }
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
