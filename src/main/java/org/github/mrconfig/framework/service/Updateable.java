package org.github.mrconfig.framework.service;

import org.github.mrconfig.framework.util.Box;

import java.io.Serializable;

/**
 * Created by w1428134 on 2014/08/04.
 */
public interface Updateable<T> {

    Box<T> save(T instance);

}
