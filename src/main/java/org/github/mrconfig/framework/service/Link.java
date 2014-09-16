package org.github.mrconfig.framework.service;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.github.mrconfig.framework.activerecord.ActiveRecord.findById;

/**
 * Created by julian3 on 2014/07/17.
 */
@XmlType(namespace = "http://www.w3.org/2005/Atom")
@XmlAccessorType(XmlAccessType.FIELD)
public class Link extends javax.ws.rs.core.Link{

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

    @Override
    public URI getUri() {
        try {
            return new URI(href);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UriBuilder getUriBuilder() {
        return UriBuilder.fromLink(this);
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

    @Override
    public List<String> getRels() {
        return asList(getRel());
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getType() {
        return type;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>(4,1.0f);
        params.put("type", type);
        params.put("rel", rel);
        params.put("title", title);
        return params;
    }

    @Override
    public String toString() {
        return href;
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
