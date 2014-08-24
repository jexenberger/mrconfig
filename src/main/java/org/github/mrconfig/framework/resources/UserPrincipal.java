package org.github.mrconfig.framework.resources;

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

    public UserPrincipal(String userId, String... roles) {
        this.userId = userId;
        this.roles = asList(roles);
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

    @Override
    public String getAuthenticationScheme() {
        return null;
    }
}
