package org.github.mrconfig.framework.resources;

import org.github.mrconfig.framework.util.Inflector;

import javax.ws.rs.Path;

/**
 * Created by julian3 on 2014/07/18.
 */
public class ResourceUtil {

    public static String getResourcePath(Class<?> type) {
        Path annotation = (Path) type.getAnnotation(Path.class);
        if (annotation == null) {
            return Inflector.getInstance().pluralize(type.getSimpleName()).toLowerCase();
        } else {
            return annotation.value();
        }
    }
}
