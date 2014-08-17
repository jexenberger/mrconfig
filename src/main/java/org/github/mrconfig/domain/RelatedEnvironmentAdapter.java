package org.github.mrconfig.domain;

import org.github.mrconfig.framework.service.LinkAdapter;

/**
 * Created by julian3 on 2014/07/19.
 */
public class RelatedEnvironmentAdapter extends LinkAdapter<Environment> {
    protected RelatedEnvironmentAdapter() {
        super(Environment.class, "environment");
    }
}
