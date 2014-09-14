package org.github.mrconfig.domain;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.*;

/**
 * Created by julian3 on 2014/09/13.
 */
@Embeddable
@XmlType(namespace = "http://www.github.org/mrconfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class Address {

    @XmlElement(namespace = "http://www.github.org/mrconfig")
    String lineOne;
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    String lineTwo;
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    String suburb;
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    String city;
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    String region;
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    String country;
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    String postalCode;

    public String getLineOne() {
        return lineOne;
    }

    public void setLineOne(String lineOne) {
        this.lineOne = lineOne;
    }

    public String getLineTwo() {
        return lineTwo;
    }

    public void setLineTwo(String lineTwo) {
        this.lineTwo = lineTwo;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
