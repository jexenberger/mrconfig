package org.github.levelthree.resources;

import org.github.levelthree.service.Link;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by julian3 on 2014/07/18.
 */
@XmlRootElement(namespace = "http://www.github.org/levelthree")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Results<T> implements ListableResource<T> {

    long totalResults = 0;
    long totalPages = 0;
    long currentPage = 0;


    Collection<Link> link;

    Collection<Link> result;
    protected Collection<T> resource;

    public Results(int totalResults, Collection<Link> links, Collection<Link> results) {
        this.totalResults = totalResults;
        this.link = links;
        this.result = results;
    }

    public Results() {
    }

    @Override
    @XmlAttribute(namespace = "http://www.github.org/levelthree")
    public long getTotalResults() {
        return totalResults;
    }

    @Override
    @XmlElementWrapper(name = "links", nillable = true)
    @XmlElement(namespace = "http://www.w3.org/2005/Atom", nillable = true)
    public Collection<Link> getLink() {
        if (link == null) {
            link = new ArrayList<>();
        }
        return link;
    }

    @Override
    @XmlElementWrapper(name = "results", nillable = true)
    @XmlElement(namespace = "http://www.w3.org/2005/Atom", nillable = true)
    public Collection<Link> getResult() {
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }

    @Override
    @XmlElementWrapper( name = "resources" ,namespace = "http://www.github.org/levelthree", nillable = true)
    @XmlElement(namespace = "http://www.github.org/levelthree", nillable = true)
    public Collection<T> getResource() {
        if (resource == null) {
            resource = new ArrayList<>();
        }
        return resource;
    }

    @Override
    @XmlAttribute(namespace = "http://www.github.org/levelthree")
    public long getTotalPages() {
        return totalPages;
    }

    @Override
    @XmlAttribute(namespace = "http://www.github.org/levelthree")
    public long getCurrentPage() {
        return currentPage;
    }


    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public void setLink(Collection<Link> link) {
        this.link = link;
    }

    public void setResult(Collection<Link> result) {
        this.result = result;
    }

    public void setResource(Collection<T> resource) {
        this.resource = resource;
    }
}
