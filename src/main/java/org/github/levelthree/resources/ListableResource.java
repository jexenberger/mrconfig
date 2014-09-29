package org.github.levelthree.resources;

import org.github.levelthree.service.Link;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.Collection;

/**
 * Created by julian3 on 2014/09/20.
 */
public interface ListableResource<T> {

    long getTotalResults();

    void setTotalResults(long totalResults);

    Collection<Link> getLink();

    void setLink(Collection<Link> link);

    Collection<Link> getResult();

    void setResult(Collection<Link> result);

    Collection<T> getResource();

    void setResource(Collection<T> resource);

    long getTotalPages();

    void setTotalPages(long totalPages);

    long getCurrentPage();

    void setCurrentPage(long currentPage);
}
