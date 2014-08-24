package org.github.mrconfig.framework.resources;

import javax.ws.rs.Path;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by julian3 on 2014/08/24.
 */
public interface BaseResource {
    default boolean notAuthorized(SecurityContext context, String role) {
        if (role != null) {
            if (!context.isUserInRole(role)) {
                return true;
            }
        }
        return false;
    }

    default String getPath() {
        Path path = getClass().getAnnotation(Path.class);
        return path.value();
    }
}
