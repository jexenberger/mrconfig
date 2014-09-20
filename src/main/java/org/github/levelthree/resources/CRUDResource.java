package org.github.levelthree.resources;

import org.github.levelthree.service.*;
import org.github.levelthree.util.GenericsUtil;

import java.io.Serializable;

/**
 * Created by w1428134 on 2014/08/04.
 */
public interface CRUDResource<T,K extends Serializable> extends ReadableResource<T,K>, WritableResource<T,K>, DeletableResource<T,K>{

    
    @Override
    default Class<K> getResourceIdType() {
        return (Class<K>) GenericsUtil.getClass(getClass(),1);
    }


    @Override
    default Listable<T> getListableResource() {
        return getService();
    }

    @Override
    default Creatable<T, K> getCreatable() {
        return getService();
    }

    @Override
    default Updateable<T,K> getUpdateable() {
        return getService();
    }

    @Override
    default UniqueLookup<T, K> getLookup() {
        return getService();
    }

    @Override
    default Deletable getDeletable() {
        return getService();
    }

    CRUDService<T,K> getService();
}
