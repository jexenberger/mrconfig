package org.github.mrconfig.framework.resources;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * Created by julian3 on 2014/07/18.
 */
@Provider
public class JaxbProvider implements ContextResolver<JAXBContext>{
    private JAXBContext context = null;

    public JAXBContext getContext(Class<?> type) {

        if (context == null) {
            try {
                context = JAXBContext.newInstance("org.github.mrconfig.resources:org.github.mrconfig.framework.activerecord:org.github.mrconfig.framework.resources:org.github.mrconfig.domain");
                context = new FormattingJaxbContext(context);
            } catch (JAXBException e) {
               throw new RuntimeException(e);
            }
        }

        return context;
    }
}
