package org.github.mrconfig.domain;

import org.github.mrconfig.framework.activerecord.ActiveRecord;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

/**
 * Created by w1428134 on 2014/07/07.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@XmlAccessorType(XmlAccessType.FIELD)
public class BaseEntity<T extends ActiveRecord> implements ActiveRecord<T, Long> {

    @Id
    @SequenceGenerator(name="mrConfigSequence",sequenceName="CONFIG_SEQ", allocationSize = 1, initialValue = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "mrConfigSequence" )
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    @Column()
    Long id;

    @Version
    @XmlAttribute(namespace = "http://www.github.org/mrconfig")
    Long version;

    public Long getId() {
        return id;
    }


    public Long getVersion() {
        return version;
    }

    final void setVersion(Long version) {
        this.version = version;
    }




    @Override
    public String toString() {
        return getClass().getSimpleName()+"{" +
                "id=" + id +
                ", version=" + version +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;

        BaseEntity that = (BaseEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }



    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
