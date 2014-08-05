package org.github.mrconfig.domain;

import java.util.*;

/**
 * Created by julian3 on 2014/07/19.
 */
public class PropertyModel extends AbstractMap<String, Object> {

    Map<String, Object> store;

    public PropertyModel() {
        this.store = new LinkedHashMap<>();
    }

    public void addValue(PropertyValue value) {

        String key = value.getPropertyKey();
        String[] parts = key.split("\\.");
        Map<String, Object> lastMap = store;
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            Object o = lastMap.get(part);
            if (o == null) {
                //last one
                if (i == (parts.length - 1)) {
                    o = value.getWrapper();
                } else {
                    o = new HashMap<>();
                }
            }
            lastMap.put(part, o);
            if (i == (parts.length - 1)) {
                continue;
            } else {
                lastMap = (Map<String, Object>) o;
            }

        }

    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return store.entrySet();
    }


}
