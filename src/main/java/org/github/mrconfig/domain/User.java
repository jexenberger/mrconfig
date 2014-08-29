package org.github.mrconfig.domain;

import org.github.mrconfig.framework.service.Link;

import javax.persistence.*;
import javax.security.auth.Subject;
import javax.xml.bind.annotation.*;
import java.security.Principal;
import java.util.*;

/**
 * Created by julian3 on 2014/07/17.
 */
@Entity
@XmlRootElement(namespace="http://www.github.org/mrconfig")
@XmlAccessorType(XmlAccessType.FIELD)
@Table(indexes = {@Index(unique = false, columnList = "state")})
public class User extends KeyEntity<User> implements Principal {


    @XmlElement(namespace="http://www.github.org/mrconfig")
    String password;
    @ElementCollection()
    @XmlElement(name="role",namespace="http://www.github.org/mrconfig")
    @XmlElementWrapper(name="roles",namespace="http://www.github.org/mrconfig")
    Set<RoleMapping> roles;

    @XmlElementWrapper(name="roles",namespace="http://www.github.org/mrconfig")
    @Enumerated
    UserState state = UserState.Pending;


    @Transient
    Collection<Link> links;

    public User() {
    }

    public User(String userId, String password) {
        setId(userId);
        this.password = password;
    }

    public String getUserId() {
        return getId();
    }

    public void setUserId(String userId) {
        setId(userId);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleMapping> getRoles() {
        if (roles == null) {
            roles = new LinkedHashSet<>();
        }
        return roles;
    }

    public void setRoles(Set<RoleMapping> roles) {
        this.roles = roles;
    }

    public Collection<Link> getLinks() {
        if (links == null) {
            links = new ArrayList<>();
        }
        return links;
    }

    public void setLinks(Collection<Link> links) {
        this.links = links;
    }

    @Override
    public String getName() {
        return getName();
    }

    @Override
    public boolean implies(Subject subject) {
        Optional<Principal> first = subject.getPrincipals().stream().filter((principal) -> principal.getName().equals(getUserId())).findFirst();
        return first.isPresent();
    }

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }
}
