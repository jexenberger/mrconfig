package org.github.levelthree.resources;

import javax.ws.rs.Path;

/**
 * Created by julian3 on 2014/08/24.
 */
public interface BaseResource {


    default String getPath() {
        if (!getClass().isAnnotationPresent(Path.class))  {
            throw new IllegalStateException(getClass().getName()+" needs to be annotated with "+Path.class.getName());
        }
        Path path = getClass().getAnnotation(Path.class);
        return path.value();
    }

}
