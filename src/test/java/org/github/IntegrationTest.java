package org.github;

import org.github.mrconfig.domain.AdminGroup;
import org.github.mrconfig.domain.Environment;
import org.github.mrconfig.domain.Server;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;

/**
 * Created by w1428134 on 2014/07/07.
 */
public class IntegrationTest {


    @Test
    public void testStartup() throws Exception {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.github.mrconfig.domain");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Environment environment = new Environment();
        environment.setId("test");
        environment.setName("Test");
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        AdminGroup adminGroup = new AdminGroup();
        adminGroup.setName("global");
        entityManager.persist(adminGroup);
        environment.setOwner(adminGroup);
        entityManager.persist(environment);

        Server server = new Server();
        server.setId("server");
        server.setName("Server");
        server.setOwner(adminGroup);
        server.setDnsName("dnsName");
        server.setIpAddress("ipAddress");
        entityManager.persist(server);

        entityManager.flush();

        Query query = entityManager.createQuery("from Server");
        List resultList = query.getResultList();
        for (Object o : resultList) {
            System.out.println(o);
        }
        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();

    }
}
