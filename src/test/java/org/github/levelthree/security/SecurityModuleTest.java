package org.github.levelthree.security;

import org.github.levelthree.resources.UserPrincipal;
import org.junit.Test;

import java.util.Optional;

import static org.github.levelthree.resources.UserPrincipal.user;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by julian3 on 2014/09/18.
 */
public class SecurityModuleTest {


    @Test
    public void testCreate() throws Exception {

        SecurityModule securityModule = new SecurityModule().addUser(user("user","password"));

        Optional<UserPrincipal> user = Security.getUser("user");
        assertTrue(user.isPresent());
        assertNotNull(securityModule.authenticationFilter);


    }
}
