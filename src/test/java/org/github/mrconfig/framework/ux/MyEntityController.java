package org.github.mrconfig.framework.ux;

import org.github.mrconfig.framework.resources.CRUDResource;
import org.github.mrconfig.framework.service.CRUDService;
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
