package org.github.mrconfig.framework.resources;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

/**
 * Created by julian3 on 2014/09/11.
 */
@XmlRootElement(namespace = "http://www.github.org/mrconfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class Roles {

    @XmlElement(namespace = "http://www.github.org/mrconfig")
    Collection<String> role;

    public Roles() {
    }

    public Roles(Collection<String> role) {
        this.role = role;
    }

    public Roles(String... roles) {
        this.getRole().addAll(asList(roles));
    }

    public Collection<String> getRole() {
        if (role == null) {
            role = new ArrayList<>();
        }
        return role;
    }

    public void setRole(Collection<String> role) {
        this.role = role;
    }
}
