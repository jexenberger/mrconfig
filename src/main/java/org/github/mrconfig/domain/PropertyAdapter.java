package org.github.mrconfig.domain;

import org.github.levelthree.service.LinkAdapter;

/**
 * Created by julian3 on 2014/07/19.
 */
public class PropertyAdapter extends LinkAdapter<Property> {
    protected PropertyAdapter() {
        super(Property.class, "property");
    }
}
