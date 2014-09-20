package org.github.levelthree.activerecord;

import java.io.Serializable;

/**
 * Created by w1428134 on 2014/08/01.
 */
public interface Versioned<T extends Serializable> {


    void setId(T id);
    void setVersion(long version);

    T getId();
    long getVersion();

    default void markForUpdate(Versioned<T> persistentVersion) {
        setId(persistentVersion.getId());
        setVersion(persistentVersion.getVersion());
    }

}
