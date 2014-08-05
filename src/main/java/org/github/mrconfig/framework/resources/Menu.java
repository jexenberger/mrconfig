package org.github.mrconfig.framework.resources;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by w1428134 on 2014/08/01.
 */
@XmlRootElement(namespace = "http://www.github.org/mrconfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class Menu {

    @XmlElementWrapper(name="groups")
    Map<String, List<MenuItem>> menuGroups;

    public Map<String, List<MenuItem>> getMenuGroups() {
        if (menuGroups == null) {
            menuGroups = new HashMap<>();
        }
        return menuGroups;
    }

    public void setMenuGroups(Map<String, List<MenuItem>> menuGroups) {
        this.menuGroups = menuGroups;
    }
}
