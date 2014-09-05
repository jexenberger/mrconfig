package org.github.mrconfig.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by julian3 on 2014/08/28.
 */
@Entity
@XmlRootElement()
@XmlAccessorType(XmlAccessType.PROPERTY)
public class RoleMapping extends BaseEntity<RoleMapping>{

    @Enumerated
    Roles role;
    @Temporal(TemporalType.DATE)
    Date effectiveFrom;
    @Temporal(TemporalType.DATE)
    Date effectiveTo;

    public RoleMapping() {
    }

    public RoleMapping(Date effectiveFrom, Date effectiveTo) {
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
    }


    public Date getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Date effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public Date getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(Date effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleMapping)) return false;
        if (!super.equals(o)) return false;

        RoleMapping that = (RoleMapping) o;

        if (!effectiveTo.equals(that.effectiveTo)) return false;
        if (!effectiveFrom.equals(that.effectiveFrom)) return false;
        if (role != that.role) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + role.hashCode();
        result = 31 * result + effectiveFrom.hashCode();
        result = 31 * result + effectiveTo.hashCode();
        return result;
    }
}
