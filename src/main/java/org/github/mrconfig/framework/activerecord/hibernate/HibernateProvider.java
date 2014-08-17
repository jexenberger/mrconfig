package org.github.mrconfig.framework.activerecord.hibernate;

import org.github.mrconfig.framework.activerecord.Parameter;
import org.github.mrconfig.framework.activerecord.jpa.JPAProvider;

import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Created by julian3 on 2014/08/05.
 */
public class HibernateProvider extends JPAProvider {

    @Override
    public <T> Stream<T> stream(Class<T> type, String name, Parameter... parameters) {
        return super.stream(type, name, parameters);
    }

    @Override
    public <T> Iterator<T> iterate(Class<T> type, String name, Parameter... parameters) {
        return super.iterate(type, name, parameters);
    }
}
