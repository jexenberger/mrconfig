package org.github.mrconfig.domain;

import org.github.mrconfig.framework.service.LinkAdapter;

/**
 * Created by julian3 on 2014/07/17.
 */
public class EnvironmentAdapter extends LinkAdapter<Environment> {
    public EnvironmentAdapter() {
        super(Environment.class, "self");
    }
}
