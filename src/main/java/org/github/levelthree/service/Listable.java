package org.github.levelthree.service;

import org.github.levelthree.util.Pair;

import java.util.Collection;

/**
 * Created by w1428134 on 2014/08/04.
 */
public interface Listable<T> {


    public Collection<T> list(Pair<String, Object> ... parameters);
    public Collection<T> page(int offset, int size, Pair<String, Object> ... parameters);
    public Link toLink(T instance);
    public Link toLink(T instance, String mediaType);
    public long count(Pair<String, Object> ... parameters);



}
