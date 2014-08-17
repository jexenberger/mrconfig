package org.github.mrconfig.framework.util;

/**
 * Created by julian3 on 2014/08/16.
 */
public class URIUtil {


    public static String getResourceId(String uri) {
        String[] parts = uri.split("/");
        if (parts.length == 1) {
            return parts[0];
        }
        return parts[parts.length-1];
    }

}
