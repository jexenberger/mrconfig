package org.github.mrconfig.domain;

import org.github.levelthree.util.FieldHints;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by w1428134 on 2014/07/07.
 */
@Entity
@DiscriminatorValue("S")
@XmlRootElement(namespace = "http://www.github.org/mrconfig")
@XmlAccessorType(XmlAccessType.FIELD)
@Table(indexes = {@Index(unique = false, columnList = "dnsName")})
public class Server extends Environment<Server> {

    @XmlElement(namespace = "http://www.github.org/mrconfig")
    @Column(name = "dnsName")
    @NotNull
    String dnsName;
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    @NotNull
    String ipAddress;
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    @FieldHints(defaultValue = "Linux")
    String operatingSystem;
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    String userName;
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    String password;
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    boolean virtualMachine = false;

    @XmlElement(namespace = "http://www.github.org/mrconfig")
    Date dateCommissioned = null;




    public Server() {
        super();
    }

    public Server(String id,String name, Environment parent, String fullyQualifiedDomainName, String ipAddress, String os, String userName, String password, AdminGroup group) {
        super(id,name, parent);
        setOwner(group);
        this.dnsName = fullyQualifiedDomainName;
        this.ipAddress = ipAddress;
        this.operatingSystem = os;
        this.userName = userName;
        this.password = password;
    }

    public String getDnsName() {
        return dnsName;
    }

    public void setDnsName(String dnsName) {
        this.dnsName = dnsName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVirtualMachine() {
        return virtualMachine;
    }

    public void setVirtualMachine(boolean virtualMachine) {
        this.virtualMachine = virtualMachine;
    }

    public Date getDateCommissioned() {
        return dateCommissioned;
    }

    public void setDateCommissioned(Date dateCommissioned) {
        this.dateCommissioned = dateCommissioned;
    }


}
