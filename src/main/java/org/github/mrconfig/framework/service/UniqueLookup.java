package org.github.mrconfig.framework.service;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by w1428134 on 2014/08/04.
 */
public interface UniqueLookup<T, K extends Serializable> {


    public Optional<T> resolve(Serializable id, Class<K> idType);
    public Optional<T> get(K id);
    public Link toLink(T instance);

}
