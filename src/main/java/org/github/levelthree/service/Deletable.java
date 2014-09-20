package org.github.levelthree.service;

import org.github.levelthree.util.Box;

import java.io.Serializable;

/**
 * Created by w1428134 on 2014/08/04.
 */
public interface Deletable<T, K extends Serializable> extends UniqueLookup<T,K>{

     Box<T> delete(K id);

}
