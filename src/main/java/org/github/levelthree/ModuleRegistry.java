package org.github.levelthree;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by julian3 on 2014/09/20.
 */
public class ModuleRegistry {

    private static Map<String, Module> MODULES;
    static {
        MODULES = new LinkedHashMap<>();
    }


    public static Optional<Module> get(String name) {
        return Optional.ofNullable(MODULES.get(name));
    }


    public static void add(Module module) {
        MODULES.put(module.getDisplayName(), module);
    }

    public static Map<String, Module> getModules() {
        return MODULES;
    }
}