package org.github.mrconfig.domain;

import org.github.mrconfig.framework.activerecord.Link;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import java.util.Optional;

/**
 * Created by julian3 on 2014/07/17.
 */
public abstract class LinkAdapter<T extends BaseEntity> extends XmlAdapter<Link, T> {


    Class<T> type;
    String rel;


    protected LinkAdapter(Class<T> type, String rel) {
        this.type = type;
        this.rel = rel;
    }

    @Override
    public T unmarshal(Link v) throws Exception {

        Optional<T> byId = Link.resolve(type, v);
        return byId.get();

    }

    @Override
    public Link marshal(T v) throws Exception {
        if (v == null) {
           return null;
        }
        Link link = v.toLink();
        link.setRel(rel);
        return link;
    }
}
