package org.github.mrconfig.framework.service;


import org.github.mrconfig.framework.Resource;
import org.github.mrconfig.framework.ResourceRegistry;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.io.Serializable;
import java.util.Optional;

import static org.github.mrconfig.framework.util.URIUtil.getResourceId;

/**
 * Created by julian3 on 2014/07/17.
 */
public abstract class LinkAdapter<T> extends XmlAdapter<Link, T> {


    Class<T> type;
    String rel;


    protected LinkAdapter(Class<T> type, String rel) {
        this.type = type;
        this.rel = rel;
    }

    @Override
    public T unmarshal(Link v) throws Exception {

        Resource resource = ResourceRegistry.getByResourceClass(type);
        if (resource == null) {
            throw new IllegalArgumentException(type.getName() + " is not registered as a resource");
        }
        Serializable resourceId = getResourceId(v.getHref());
        //had to do this unchecked to get by the wildcard typing, Julian : 0, Java Generics: 99999999999
        UniqueLookup uniqueLookup = resource.getUniqueLookup();
        Optional<T> byId = uniqueLookup.get(resourceId);
        return byId.get();

    }

    @Override
    public Link marshal(T v) throws Exception {
        if (v == null) {
            return null;
        }
        Resource resource = ResourceRegistry.getByResourceClass(type);
        if (resource == null) {
            throw new IllegalArgumentException(type.getName() + " is not registered as a resource");
        }

        String id = resource.buildIDForResource(v);
        String path = resource.getPath() + "/" + id;
        return new Link(rel, "application/json", path, type.getSimpleName());
    }
}
