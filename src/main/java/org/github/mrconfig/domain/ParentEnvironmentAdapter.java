package org.github.mrconfig.domain;

import org.github.levelthree.service.LinkAdapter;

/**
 * Created by julian3 on 2014/07/17.
 */
public class ParentEnvironmentAdapter extends LinkAdapter<Environment> {
    protected ParentEnvironmentAdapter() {
        super(Environment.class, "parent");
    }
}
