package org.github.mrconfig.domain;

import org.github.mrconfig.framework.service.LinkAdapter;

/**
 * Created by julian3 on 2014/07/18.
 */
public class AdminGroupAdapter extends LinkAdapter<AdminGroup> {
    protected AdminGroupAdapter() {
        super(AdminGroup.class, "group");
    }
}
