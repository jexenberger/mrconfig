package org.github.mrconfig.domain;

import javax.persistence.Entity;

/**
 * Created by julian3 on 2014/08/23.
 */
@Entity
public class TestObject extends BaseEntity<TestObject> {

    String name;
    String surname;


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
}
