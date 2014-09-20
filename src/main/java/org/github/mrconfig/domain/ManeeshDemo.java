package org.github.mrconfig.domain;

import org.github.levelthree.service.Named;

import javax.persistence.Index;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by w1428134 on 2014/08/01.
 */
@javax.persistence.Entity
@Table(indexes = {@Index(unique = true, columnList = "name")})
@XmlRootElement(namespace = "http://www.github.org/mrconfig")
@XmlAccessorType(XmlAccessType.FIELD)

public class ManeeshDemo extends BaseEntity<ManeeshDemo> implements Named{


    @XmlElement(namespace = "http://www.github.org/mrconfig")
    String name;
    String surname;
    Date dateOfBirth;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
