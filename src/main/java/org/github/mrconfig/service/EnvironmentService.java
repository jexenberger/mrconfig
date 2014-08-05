package org.github.mrconfig.service;

import org.github.mrconfig.domain.Environment;
import org.github.mrconfig.domain.Environment_;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by w1428134 on 2014/07/10.
 */
public class EnvironmentService {

    EntityManager entityMananager;

    public EnvironmentService(EntityManager entityMananager) {
        this.entityMananager = entityMananager;
    }

    public void add(Environment environment) {
       entityMananager.persist(environment);
       entityMananager.flush();
    }

    public void save(Environment environment) {
        entityMananager.persist(environment);
        entityMananager.flush();
    }

    public Optional<Environment> byId(Long id) {
        Environment environment = entityMananager.find(Environment.class, id);
        return Optional.ofNullable(environment);
    }

    public Optional<Environment> byKey(String name) {
        Environment environment = entityMananager.find(Environment.class, name);
        return Optional.ofNullable(environment);
    }

    public Collection<Environment> findBy(String name, String parent) {
        CriteriaBuilder cb = entityMananager.getCriteriaBuilder();

        CriteriaQuery<Environment> c = cb.createQuery(Environment.class);
        Root<Environment> root = c.from(Environment.class);
        c.where(cb.equal(root.get
                (Environment_.key), name));
        return entityMananager.createQuery(c).getResultList();
    }


}
