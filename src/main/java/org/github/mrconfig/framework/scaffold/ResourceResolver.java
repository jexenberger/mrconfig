package org.github.mrconfig.framework.scaffold;

import org.github.mrconfig.framework.activerecord.Keyed;
import org.github.mrconfig.framework.activerecord.Named;
import org.github.mrconfig.framework.util.Inflector;

/**
 * Created by julian3 on 2014/07/20.
 */
public class ResourceResolver {

    public static String getRelativeHref(Class<?> type) {
        return Inflector.getInstance().pluralize(type.getSimpleName().toLowerCase());
    }

    public static String getAbsoluteHref(Class<?> type) {
        return "/" + getRelativeHref(type);
    }

    public static String getLookupField(Class<?> type) {
        if (Named.class.isAssignableFrom(type)) {
            return "name";
        }
        if (Keyed.class.isAssignableFrom(type)) {
            return "key";
        }
        return "id";
    }

}
