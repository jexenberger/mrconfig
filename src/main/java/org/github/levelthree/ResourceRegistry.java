package org.github.levelthree;

import java.util.*;

import static java.util.Objects.requireNonNull;

/**
 * Created by julian3 on 2014/08/10.
 */
public class ResourceRegistry {

    private static Map<String, Resource> RESOURCES = new HashMap<>();

    public static String DEFAULT_MODULE = "main";

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

    public static String register(Resource resource) {
        requireNonNull(resource, "resource cannot be null");
        RESOURCES.put(resource.getPath(), resource);
        return resource.getPath();
    }

    public static Collection<Resource> list() {
        return RESOURCES.values();
    }


    public static Collection<String> getAllRoles() {
        Set<String> roleSet = new LinkedHashSet<>();
        for (Resource resource : RESOURCES.values()) {
            Optional.ofNullable(resource.getCreateRole()).ifPresent(roleSet::add);
            Optional.ofNullable(resource.getUpdateRole()).ifPresent(roleSet::add);
            Optional.ofNullable(resource.getDeleteRole()).ifPresent(roleSet::add);
            Optional.ofNullable(resource.getListRole()).ifPresent(roleSet::add);
            Optional.ofNullable(resource.getLookupRole()).ifPresent(roleSet::add);
        }
        return roleSet;
    }


}
