package org.github.levelthree.service;

import org.github.levelthree.util.Box;

import java.io.Serializable;

/**
 * Created by w1428134 on 2014/08/04.
 */
public interface Creatable<T, K extends Serializable> {

   Box<K> create(T instance);

   Link toLink(T instance);

}
