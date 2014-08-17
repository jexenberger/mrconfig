package org.github.mrconfig.framework.service;

import java.io.Serializable;

/**
 * Created by julian3 on 2014/08/16.
 */
public interface Identified<T extends Serializable> {

    T getId();

}
