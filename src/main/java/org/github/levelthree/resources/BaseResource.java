package org.github.levelthree.resources;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

/**
 * Created by julian3 on 2014/08/24.
 */
public interface BaseResource {


    default String getPath() {
        if (!getClass().isAnnotationPresent(Path.class)) {
            throw new IllegalStateException(getClass().getName() + " needs to be annotated with " + Path.class.getName());
        }
        Path path = getClass().getAnnotation(Path.class);
        return path.value();
    }


    default String toType(MediaType type) {
        return type.getType() + "/" + type.getSubtype();
    }

    default Optional<MediaType> resolveMediaType(MediaType mediaType) {
        if (mediaType == null || mediaType.isWildcardType() || mediaType.isWildcardSubtype()) {
            return Optional.of(MediaType.APPLICATION_JSON_TYPE);
        }

        String type = toType(mediaType);
        if (type.equals(MediaType.TEXT_PLAIN)) {
            return Optional.of(MediaType.APPLICATION_JSON_TYPE);
        }
        return Optional.of(mediaType);
    }
}
