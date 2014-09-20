package org.github.levelthree.ux;

import org.github.levelthree.resources.CRUDResource;
import org.github.levelthree.service.CRUDService;
import org.github.mrconfig.framework.testdomain.MyEntity;

import javax.ws.rs.Path;

/**
 * Created by w1428134 on 2014/08/12.
 */
@Path("/test")
public class MyEntityController implements CRUDResource<MyEntity,Long> {
    @Override
    public CRUDService<MyEntity, Long> getService() {
        return null;
    }
}
