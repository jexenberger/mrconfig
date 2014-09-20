package org.github.levelthree.security;

import org.github.levelthree.resources.UserPrincipal;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.SecurityContext;

import java.util.Optional;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by julian3 on 2014/09/18.
 */
public class SecurityTest {


    private SecurityContext securityContext;

    @Before
    public void setUp() throws Exception {
        securityContext = createMock(SecurityContext.class);
        Security.reset();

    }

    @Test
    public void testAuthorize() throws Exception {
        expect(securityContext.getUserPrincipal()).andReturn(null).anyTimes();
        replay(securityContext);

        boolean authorized = Security.authorized(securityContext, true, null);
        assertFalse(authorized);

    }


    @Test
    public void testAuthorizeWithRole() throws Exception {
        expect(securityContext.getUserPrincipal()).andReturn(new UserPrincipal("test","password", "one")).anyTimes();
        expect(securityContext.isUserInRole("one")).andReturn(true).anyTimes();
        expect(securityContext.isUserInRole("two")).andReturn(false).anyTimes();
        replay(securityContext);

        boolean authorized = Security.authorized(securityContext, true, "one");
        assertTrue(authorized);

        authorized = Security.authorized(securityContext, true, "two");
        assertFalse(authorized);

        verify(securityContext);
    }

    @Test
    public void testGetUser() throws Exception {
        //user default registry
        Optional<UserPrincipal> test = Security.getUser("test");
        assertFalse(test.isPresent());

        Security.setUserRegistry((userId)-> {
            if (userId.equals("test")) {
                return Optional.of(new UserPrincipal("test","test"));
            }
            return Optional.empty();
        });
        test = Security.getUser("test");
        assertTrue(test.isPresent());
    }

    @Test
    public void testHash() throws Exception {

        String hello = Security.hashAsHex("hello world","SHA-256");
        assertEquals("b94d27b9934d3e08a52e52d7da7dabfac484efe37a5380ee9088f7ace2efcde9", hello);

    }
}
