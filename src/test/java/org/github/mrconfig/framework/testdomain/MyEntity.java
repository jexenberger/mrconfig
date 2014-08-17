package org.github.mrconfig.framework.testdomain;

import org.github.mrconfig.framework.activerecord.ActiveRecord;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

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
    @NotNull
    @Min(10000)
    Long id;


    @Size(min=2,max = 24)
    String name;

    Date aDate;

    boolean doStuff;

    String readOnly;

    int value;

    TestEnum enumType;

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

    public Date getaDate() {
        return aDate;
    }

    public void setaDate(Date aDate) {
        this.aDate = aDate;
    }

    public boolean isDoStuff() {
        return doStuff;
    }

    public void setDoStuff(boolean doStuff) {
        this.doStuff = doStuff;
    }

    public String getReadOnly() {
        return readOnly;
    }

    public TestEnum getEnumType() {
        return enumType;
    }

    public void setEnumType(TestEnum enumType) {
        this.enumType = enumType;
    }

    public MyEntity getParent() {
        return parent;
    }

    public void setParent(MyEntity parent) {
        this.parent = parent;
    }

    public void setReadOnly(String readOnly) {
        this.readOnly = readOnly;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
