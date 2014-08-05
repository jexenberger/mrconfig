package org.github.mrconfig.framework.testdomain;

import org.github.mrconfig.framework.activerecord.ActiveRecord;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by w1428134 on 2014/08/04.
 */
@Entity
public class MyEntity implements ActiveRecord<MyEntity, Long>{

    @Id
    Long id;

    String name;


    public MyEntity() {
    }

    public MyEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
