package org.github.mrconfig.domain;

/**
 * Created by julian3 on 2014/07/18.
 */
public class UserAdapter extends LinkAdapter<User> {
    protected UserAdapter() {
        super(User.class, "self");
    }
}
