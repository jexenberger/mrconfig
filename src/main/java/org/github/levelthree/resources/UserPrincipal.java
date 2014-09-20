package org.github.levelthree.resources;

import org.github.levelthree.security.Security;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Collection;

import static java.util.Arrays.asList;

/**
 * Created by julian3 on 2014/08/24.
 */
public class UserPrincipal implements Principal, SecurityContext {

    String userId;
    Collection<String> roles;
    String password;

    public UserPrincipal(String userId, String password, String... roles) {
        this.userId = userId;
        this.roles = asList(roles);
        if (password != null) {
            this.password = Security.hashAsHex(password, Security.getDefaultHashingAlgorithm());
        }
    }

    public static UserPrincipal user(String userId, String password, String ... roles) {
        return new UserPrincipal(userId, password, roles);
    }

    @Override
    public String getName() {
        return userId;
    }

    @Override
    public Principal getUserPrincipal() {
        return this;
    }

    @Override
    public boolean isUserInRole(String s) {
        return roles.contains(s);
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getAuthenticationScheme() {
        return null;
    }
}
