package org.github.mrconfig.domain;

import org.github.levelthree.activerecord.ActiveRecord;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by w1428134 on 2014/07/07.
 */
@MappedSuperclass
public class KeyEntity<T extends ActiveRecord> implements ActiveRecord<T,String>  {



    @Id
    @NotNull
    String id;

    @Version
    Long version;


    public void setId(String id) {
        this.id = id;
    }


    public Long getVersion() {
        return version;
    }

    final void setVersion(Long version) {
        this.version = version;
    }

    @Override
    @XmlAttribute(namespace = "http://www.github.org/mrconfig")
    public String getId() {
        return id;
    }
}
