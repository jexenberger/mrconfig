package org.github.mrconfig;

import org.github.mrconfig.framework.Resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Created by julian3 on 2014/08/10.
 */
public class ResourceRegistry {

    private static Map<String, Resource> RESOURCES;

    public static Resource get(String path) {
        if (RESOURCES == null || RESOURCES.size() > 0) {
            throw new IllegalStateException("no resources registered");
        }
        return RESOURCES.get(path);
    }

    public static void register(Resource resource) {
        requireNonNull(resource, "resource cannot be null");
        if (RESOURCES == null) {
            RESOURCES = new HashMap<>();
        }
        RESOURCES.put(resource.getPath(), resource);
    }


}
