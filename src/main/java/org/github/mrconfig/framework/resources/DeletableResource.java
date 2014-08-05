package org.github.mrconfig.framework.resources;

import org.github.mrconfig.framework.activerecord.ActiveRecord;
import org.github.mrconfig.domain.EnvironmentGroup;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.Optional;

import static org.github.mrconfig.domain.KeyEntity.resolveByKeyOrId;
import static org.github.mrconfig.framework.activerecord.ActiveRecord.doWork;

/**
 * Created by julian3 on 2014/07/18.
 */
public interface DeletableResource<T> {
    @DELETE
    @Path("{id}")
    default Response delete(@PathParam("id") String id) {
        return doWork(()-> {
            Optional<EnvironmentGroup> result =  resolveByKeyOrId(id, getType());
            result.ifPresent((value)-> value.delete());
            return Response.status(204).build();
        });
    }

    Class getType();
}
