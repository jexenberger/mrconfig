package org.github.mrconfig.framework.testdomain;

import org.github.mrconfig.framework.activerecord.ActiveRecord;

import javax.persistence.*;

/**
 * Created by w1428134 on 2014/08/04.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "MyEntity.findNameLike", query = "SELECT e FROM MyEntity e where e.name like :name"),
})
public class MyEntity implements ActiveRecord<MyEntity, Long>{

    @Id
    @SequenceGenerator(name="myEntitySeq",sequenceName="TEST_SEQ", allocationSize = 1, initialValue = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "myEntitySeq" )
    Long id;

    String name;

    @ManyToOne
    MyEntity parent;


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
