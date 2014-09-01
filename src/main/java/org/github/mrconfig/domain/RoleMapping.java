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
    Date getEffectiveFrom;
    @Temporal(TemporalType.DATE)
    Date effectiveTo;

    public RoleMapping() {
    }

    public RoleMapping(Date getEffectiveFrom, Date effectiveTo) {
        this.getEffectiveFrom = getEffectiveFrom;
        this.effectiveTo = effectiveTo;
    }


    public Date getGetEffectiveFrom() {
        return getEffectiveFrom;
    }

    public void setGetEffectiveFrom(Date getEffectiveFrom) {
        this.getEffectiveFrom = getEffectiveFrom;
    }

    public Date getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(Date effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleMapping)) return false;
        if (!super.equals(o)) return false;

        RoleMapping that = (RoleMapping) o;

        if (!effectiveTo.equals(that.effectiveTo)) return false;
        if (!getEffectiveFrom.equals(that.getEffectiveFrom)) return false;
        if (role != that.role) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + role.hashCode();
        result = 31 * result + getEffectiveFrom.hashCode();
        result = 31 * result + effectiveTo.hashCode();
        return result;
    }
}
