package org.github.mrconfig.framework.service;

/**
 * Created by julian3 on 2014/07/18.
 */
public interface Active {

    void deactivate();

    void reactivate();

    boolean isActive();
}