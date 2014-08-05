package org.github.mrconfig.framework.resources;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by w1428134 on 2014/08/01.
 */
@XmlType(namespace = "http://www.github.org/mrconfig")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class MenuItem {

    String name;
    String key;
    String link;

    public MenuItem() {
    }

    public MenuItem(String name, String key, String link) {
        this.name = name;
        this.key = key;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
