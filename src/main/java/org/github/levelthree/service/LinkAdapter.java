package org.github.levelthree.service;


import org.github.levelthree.Resource;
import org.github.levelthree.ResourceRegistry;
import org.github.levelthree.util.GenericsUtil;
import org.github.levelthree.util.TransformerService;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.io.Serializable;
import java.util.Optional;

import static org.github.levelthree.util.URIUtil.getResourceId;

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
        Class<?> keyType = GenericsUtil.getClass(resource.getResourceController(), 1);
        T result = (T) TransformerService.convert(resourceId, keyType);
        Serializable targetId = (Serializable) result;
        Optional<T> byId = uniqueLookup.get(targetId);
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


        String title = type.getSimpleName();
        if (v instanceof Named) {
            title = ((Named) v).getName();
        }

        String path = resource.getPath() + "/" + id;
        return new Link(rel, "application/json", path, title);
    }
}
