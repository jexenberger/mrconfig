package org.github.levelthree.resources;

import org.github.levelthree.util.JaxbUtil;
import org.junit.Test;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertNotNull;

/**
 * Created by julian3 on 2014/09/11.
 */
public class RolesResourceTest {


    @Test
    public void testGet() throws Exception {


        SecurityContext securityContext = createMock(SecurityContext.class);

        replay(securityContext);

        RolesResources resources = new RolesResources() {
            @Override
            public Roles getRoles(SecurityContext context) {
                return new Roles("test","test2");
            }
        };
        Response response = resources.get(securityContext);
        assertNotNull(response);
        assertNotNull(response.getEntity());
        Roles roles = (Roles) response.getEntity();
        System.out.println(JaxbUtil.toXml(roles));



    }
}
