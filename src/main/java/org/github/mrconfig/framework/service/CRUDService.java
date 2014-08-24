package org.github.mrconfig.framework.service;

import java.io.Serializable;

/**
 * Created by w1428134 on 2014/08/04.
 */
public interface CRUDService<T,K extends Serializable> extends Creatable<T,K>, Updateable<T,K>, UniqueLookup<T,K>, Listable<T>, Deletable<T, K> {
}
