package org.github.levelthree.resources;

import org.github.levelthree.service.Link;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by julian3 on 2014/07/18.
 */
@XmlRootElement(namespace = "http://www.github.org/mrconfig")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Results<T> {

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

    @XmlAttribute(namespace = "http://www.github.org/mrconfig")
    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    @XmlElementWrapper(name = "links", nillable = true)
    @XmlElement(namespace = "http://www.w3.org/2005/Atom", nillable = true)
    public Collection<Link> getLink() {
        if (link == null) {
            link = new ArrayList<>();
        }
        return link;
    }

    public void setLink(Collection<Link> link) {
        this.link = link;
    }

    @XmlElementWrapper(name = "results", nillable = true)
    @XmlElement(namespace = "http://www.w3.org/2005/Atom", nillable = true)
    public Collection<Link> getResult() {
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }

    public void setResult(Collection<Link> result) {
        this.result = result;
    }

    @XmlElementWrapper( name = "resources" ,namespace = "http://www.github.org/mrconfig", nillable = true)
    @XmlElement(namespace = "http://www.github.org/mrconfig", nillable = true)
    public Collection<T> getResource() {
        if (resource == null) {
            resource = new ArrayList<>();
        }
        return resource;
    }

    public void setResource(Collection<T> resource) {
        this.resource = resource;
    }

    @XmlAttribute(namespace = "http://www.github.org/mrconfig")
    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    @XmlAttribute(namespace = "http://www.github.org/mrconfig")
    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }
}
