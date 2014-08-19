package org.github.mrconfig.domain;

import org.github.mrconfig.framework.activerecord.ActiveRecord;
import org.github.mrconfig.framework.service.Keyed;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.Collection;
import java.util.Optional;

import static org.github.mrconfig.framework.activerecord.Parameter.p;

/**
 * Created by w1428134 on 2014/07/07.
 */
@MappedSuperclass
public class KeyEntity<T extends ActiveRecord> implements ActiveRecord<T,String>  {



    @Id
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    @Column()
    String id;

    @Version
    @XmlAttribute(namespace = "http://www.github.org/mrconfig")
    Long version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Long getVersion() {
        return version;
    }

    final void setVersion(Long version) {
        this.version = version;
    }


}
