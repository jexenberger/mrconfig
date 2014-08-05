package org.github.mrconfig.framework.resources;

import org.github.mrconfig.framework.service.*;
import org.github.mrconfig.framework.util.GenericsUtil;

import java.io.Serializable;

/**
 * Created by w1428134 on 2014/08/04.
 */
public interface CRUDResource<T,K extends Serializable> extends ReadableResource<T,K>, WritableResource<T,K>, DeletableResource<T>{

    
    @Override
    default Class<K> getResourceTypeId() {
        return (Class<K>) GenericsUtil.getClass(getClass(),1);
    }

    @Override
    default Class<T> getType() {
        return (Class<T>) GenericsUtil.getClass(getClass(),0);
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
    default Updateable<T> getUpdateable() {
        return getService();
    }

    @Override
    default UniqueLookup<T, K> getLookup() {
        return getService();
    }
    
    CRUDService<T,K> getService(); 
}
