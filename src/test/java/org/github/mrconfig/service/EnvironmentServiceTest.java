package org.github.mrconfig.service;

import org.github.mrconfig.domain.Environment;
import org.github.mrconfig.domain.EnvironmentGroup;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.github.mrconfig.framework.activerecord.ActiveRecord.findById;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertTrue;

/**
 * Created by w1428134 on 2014/07/10.
 */
public class EnvironmentServiceTest extends BaseDomainJPATest{

    String key;

    @Test
    public void testAdd() throws Exception {
        EnvironmentService environmentService = new EnvironmentService(getEntityManager());
        Environment environment = new EnvironmentGroup();
        environment.setName("Test environment");
        key = "test_environment";
        environment.setId(key);
        environmentService.add(environment);
        getEntityManager().clear();
        System.out.println("ENV -> " +environment.getId());

        Optional<Environment> result = findById(Environment.class, key);
        assertThat(result.get(), equalTo(environment));

    }

    @Test
    public void testSave() throws Exception {
        testAdd();
        EnvironmentService environmentService = new EnvironmentService(getEntityManager());
        Optional<Environment> result = lookupByKey(Environment.class, key);
        assertTrue(result.isPresent());
        result.get().setId("habbanagero");
        environmentService.save(result.get());

        result = lookupByKey(Environment.class, "habbanagero");
        assertThat(result.get(), notNullValue());

    }
}
