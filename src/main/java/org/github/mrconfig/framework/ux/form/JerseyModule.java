package org.github.mrconfig.framework.ux.form;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.github.mrconfig.framework.Module;
import org.github.mrconfig.framework.resources.GenerateExceptionMapper;
import org.github.mrconfig.framework.resources.JaxbProvider;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

/**
 * Created by julian3 on 2014/08/17.
 */
public class JerseyModule extends Module {
    @Override
    public void init() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // create JsonProvider to provide custom ObjectMapper
        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        provider.setMapper(mapper);

        addResourceClass(GenerateExceptionMapper.class);
        addResourceClass(MultiPartFeature.class);
        addResourceClass(JaxbProvider.class);
    }
}
