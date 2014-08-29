package org.github.mrconfig.domain;

import javax.persistence.Entity;
import java.util.Date;

/**
 * Created by w1428134 on 2014/08/29.
 */
@Entity
public class JerryRecord extends BaseEntity<JerryRecord> {


    String name;
    String surname;
    Date dob;

    public JerryRecord() {
    }

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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
}
