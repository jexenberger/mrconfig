package org.github.mrconfig.domain;

import org.github.mrconfig.framework.service.Named;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by w1428134 on 2014/07/07.
 */
@javax.persistence.Entity
@XmlRootElement(namespace = "http://www.github.org/mrconfig")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class AdminGroup extends BaseEntity<AdminGroup> implements Named {

    @XmlElement(namespace = "http://www.github.org/mrconfig")
    @NotNull
    @Pattern(regexp = "[a-zA-Z]+")
    @Size(min=3, max=25)
    String name;
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    String description;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @XmlElement(name="environment", namespace = "http://www.github.org/mrconfig")
    @XmlElementWrapper(name="environments")
    @XmlJavaTypeAdapter(EnvironmentAdapter.class)
    List<Environment> environments;

    @ManyToMany()
    @XmlElement(name="user", namespace = "http://www.github.org/mrconfig")
    @XmlElementWrapper(name="users")
    @XmlJavaTypeAdapter(UserAdapter.class)
    List<User> users;

    public AdminGroup() {
    }

    public AdminGroup(String name, String description, User... users) {
        this.name = name;
        this.description = description;
        this.users = asList(users);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addEnvironment(Environment environment) {
        getEnvironments().add(environment);
    }

    public List<Environment> getEnvironments() {
        if (environments == null) {
            this.environments = new ArrayList<>();
        }
        return environments;
    }

    public void setEnvironments(List<Environment> environments) {
        this.environments = environments;
    }

    public List<User> getUsers() {
        if (users == null) {
            users = new ArrayList<>();
        }
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
