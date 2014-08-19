package org.github.mrconfig.framework.service;

import javax.ws.rs.NotFoundException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Optional;

import static org.github.mrconfig.framework.activerecord.ActiveRecord.findById;

/**
 * Created by julian3 on 2014/07/17.
 */
@XmlType(namespace = "http://www.w3.org/2005/Atom")
@XmlAccessorType(XmlAccessType.FIELD)
public class Link {

    @XmlAttribute(required = true, namespace = "http://www.w3.org/2005/Atom")
    String rel;
    @XmlAttribute(namespace = "http://www.w3.org/2005/Atom")
    String type;
    @XmlAttribute(required = true, namespace = "http://www.w3.org/2005/Atom")
    String href;
    @XmlAttribute(namespace = "http://www.w3.org/2005/Atom")
    String title;

    public Link() {
    }

    public Link(String rel, String type, String href, String title) {
        this.rel = rel;
        this.type = type;
        this.href = href;
        this.title = title;
    }

    public String getId() {
        String[] split = href.split("/");
        return split[split.length - 1];
    }


    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
