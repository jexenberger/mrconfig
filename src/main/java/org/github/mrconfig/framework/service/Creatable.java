package org.github.mrconfig.framework.service;

import org.github.mrconfig.framework.activerecord.Link;
import org.github.mrconfig.framework.util.Box;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by w1428134 on 2014/08/04.
 */
public interface Creatable<T, K extends Serializable> {

   Box<K> create(T instance);

   Link toLink(T instance);

}
