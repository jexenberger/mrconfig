package org.github.levelthree.resources;

import org.github.levelthree.activerecord.ActiveRecord;
import org.github.levelthree.activerecord.ActiveRecordCRUDService;
import org.github.levelthree.service.CRUDService;
import org.github.levelthree.util.GenericsUtil;

import java.io.Serializable;

/**
 * Created by w1428134 on 2014/08/04.
 */
public class BaseCRUDResource<T extends ActiveRecord<T,K>,K extends Serializable> implements CRUDResource<T,K> {

    private CRUDService<T,K> service;

    @Override
    public CRUDService<T, K> getService() {
        if (service == null) {
            Class<T> aClass = (Class<T>) GenericsUtil.getClass(getClass(), 0);
            service = new ActiveRecordCRUDService<>(aClass);
        }
        return service;
    }
}
