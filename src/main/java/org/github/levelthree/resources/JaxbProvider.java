package org.github.levelthree.resources;

import org.github.levelthree.Module;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * Created by julian3 on 2014/07/18.
 */
@Provider
public class JaxbProvider extends Module implements ContextResolver<JAXBContext>{
    private JAXBContext context = null;
    private static String packages;

    public JaxbProvider() {
        super();
    }

    public JaxbProvider(String packages) {
        this.packages = packages;
    }

    @Override
    public void init() {
        addResourceClass(JaxbProvider.class);
    }

    public JAXBContext getContext(Class<?> type) {

        if (context == null) {
            try {
                context = JAXBContext.newInstance("org.github.levelthree.service:org.github.levelthree.resources:org.github.levelthree.activerecord:org.github.levelthree.resources:" + packages);
                context = new FormattingJaxbContext(context);
            } catch (JAXBException e) {
               throw new RuntimeException(e);
            }
        }

        return context;
    }
}
