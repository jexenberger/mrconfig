package org.github.mrconfig.framework.service;

import org.github.mrconfig.framework.activerecord.Link;
import org.github.mrconfig.framework.util.Pair;

import java.util.Collection;

/**
 * Created by w1428134 on 2014/08/04.
 */
public interface Listable<T> {


    public Collection<T> list(Pair<String, Object> ... parameters);
    public Collection<T> page(int offset, int size, Pair<String, Object> ... parameters);
    public Link toLink(T instance);
    public long count(Pair<String, Object> ... parameters);



}
