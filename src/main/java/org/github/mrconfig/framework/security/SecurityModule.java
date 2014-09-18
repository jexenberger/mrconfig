package org.github.mrconfig.framework.security;

import org.github.mrconfig.framework.Module;
import org.github.mrconfig.framework.resources.UserPrincipal;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by julian3 on 2014/09/18.
 */
public class SecurityModule extends Module{

    Class<?> authenticationFilter;
    Map<String, UserPrincipal> users;


    public SecurityModule() {
        authenticationFilter = BasicAuthFilter.class;
        Security.setUserRegistry((userId) -> {
            if (users == null) {
                return Optional.empty();
            }
            return Optional.ofNullable(users.get(userId));
        });
    }



    public SecurityModule(Class<?> authenticationFilter) {
        this();
        this.authenticationFilter = authenticationFilter;
    }

    public SecurityModule(Class<?> authenticationFilter, Function<String, Optional<UserPrincipal>> userRegistry) {
        this(authenticationFilter);
        Security.setUserRegistry(userRegistry);
    }

    public SecurityModule(String name, Class<?> authenticationFilter) {
        super(name);
        this.authenticationFilter = authenticationFilter;
    }

    public SecurityModule(String name, Class<?> authenticationFilter, Function<String, Optional<UserPrincipal>> userRegistry) {
        this(name, authenticationFilter);
        Security.setUserRegistry(userRegistry);
    }

    public SecurityModule userRegistry(Function<String, Optional<UserPrincipal>> userRegistry) {
        Security.setUserRegistry(userRegistry);
        return this;
    }


    public SecurityModule addUser(UserPrincipal userPrincipal) {
        Objects.requireNonNull(userPrincipal);
        if (users == null) {
            users = new HashMap<>();
        }
        users.put(userPrincipal.getName(), userPrincipal);
        return this;
    }

    @Override
    public void init() {
        if (authenticationFilter != null) {
            addResourceClass(authenticationFilter);
        }
    }
}
