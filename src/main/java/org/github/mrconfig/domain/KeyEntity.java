package org.github.mrconfig.domain;

import org.github.mrconfig.framework.activerecord.ActiveRecord;
import org.github.mrconfig.framework.activerecord.Keyed;
import org.github.mrconfig.framework.activerecord.Link;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import java.util.Collection;
import java.util.Optional;

import static org.github.mrconfig.framework.activerecord.Parameter.p;

/**
 * Created by w1428134 on 2014/07/07.
 */
@MappedSuperclass
public class KeyEntity<T extends ActiveRecord> extends BaseEntity<T> implements Keyed {


    public static <K extends KeyEntity> Optional<K> findByKey(Class<K> type, String key) {
        Collection<K> result = (Collection<K>) ActiveRecord.findWhere(type, p("key", key));
        if (result.size() > 0) {
            return Optional.ofNullable((K) result.iterator().next());
        }
        return Optional.empty();
    }

    @Basic(optional = false)
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    @Column(name="key", unique = true, nullable = false)
    String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+"{" +
                "id=" + getId() +
                ", version=" + getVersion() +
                ", key=" + key +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KeyEntity)) return false;
        if (!super.equals(o)) return false;

        KeyEntity keyEntity = (KeyEntity) o;

        if (key != null ? !key.equals(keyEntity.key) : keyEntity.key != null) return false;

        return true;
    }

    public   static <K> Optional<K> resolveByKeyOrId(String id,  Class type) {
        Optional<K> byId = Optional.empty();
        Object targetId = id;
        try {
            targetId = Long.parseLong(id);
            byId = ActiveRecord.findById(type, targetId);
        } catch (NumberFormatException e) {
            if (KeyEntity.class.isAssignableFrom(type)) {
                byId = findByKey(type, targetId.toString());
            }
        }
        return byId;
    }



    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (key != null ? key.hashCode() : 0);
        return result;
    }
}
