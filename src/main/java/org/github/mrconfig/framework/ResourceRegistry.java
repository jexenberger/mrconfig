package org.github.mrconfig.framework;

import org.github.mrconfig.framework.Resource;

import java.util.*;

import static java.util.Objects.requireNonNull;

/**
 * Created by julian3 on 2014/08/10.
 */
public class ResourceRegistry {

    private static Map<String, Resource> RESOURCES = new HashMap<>();

    public static Resource get(String path) {
        if (RESOURCES == null || RESOURCES.size()  == 0) {
            throw new IllegalStateException("no resources registered");
        }
        return RESOURCES.get(path);
    }

    public static Resource getByResourceClass(Class<?> type) {
        if (RESOURCES == null || RESOURCES.size() == 0) {
            throw new IllegalStateException("no resources registered");
        }
        Optional<Resource> first = RESOURCES.values().stream().filter((resource) -> resource.getResourceClass().equals(type)).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        return null;
    }

    public static boolean isRegistered(Class<?> type) {
        return getByResourceClass(type) != null;
    }

    public static void register(Resource resource) {
        requireNonNull(resource, "resource cannot be null");
        RESOURCES.put(resource.getPath(), resource);
    }

    public static Collection<Resource> list() {
        return RESOURCES.values();
    }


}
