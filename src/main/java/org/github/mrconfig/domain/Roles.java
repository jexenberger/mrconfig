package org.github.mrconfig.domain;

import javax.xml.bind.annotation.XmlEnum;

/**
 * Created by julian3 on 2014/08/28.
 */
@XmlEnum
public enum Roles {

    ENVIRONMENT_ADMIN,
    ENVIRONMENT_EDITOR,
    ENVIRONMENT_DEPLOYER;

}
