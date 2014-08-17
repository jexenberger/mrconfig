package org.github.mrconfig.domain;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.security.auth.Subject;
import javax.xml.bind.annotation.*;
import java.security.Principal;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by julian3 on 2014/07/17.
 */
@Entity
@XmlRootElement(namespace="http://www.github.org/mrconfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class User extends KeyEntity<User> implements Principal {


    @XmlElement(namespace="http://www.github.org/mrconfig")
    String password;
    @ElementCollection()
    @XmlElement(name="role",namespace="http://www.github.org/mrconfig")
    @XmlElementWrapper(name="roles",namespace="http://www.github.org/mrconfig")
    Set<String> roles;

    public User() {
    }

    public User(String userId, String password) {
        setKey(userId);
        this.password = password;
    }

    public String getUserId() {
        return getKey();
    }

    public void setUserId(String userId) {
        setKey(userId);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        if (roles == null) {
            roles = new LinkedHashSet<>();
        }
        return roles;
    }


    @Override
    public String getName() {
        return getKey();
    }

    @Override
    public boolean implies(Subject subject) {
        Optional<Principal> first = subject.getPrincipals().stream().filter((principal) -> principal.getName().equals(getUserId())).findFirst();
        return first.isPresent();
    }

}
