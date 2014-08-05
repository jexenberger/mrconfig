package org.github.mrconfig.framework.resources;

import javax.xml.bind.*;

/**
 * Created by julian3 on 2014/07/18.
 */
public class FormattingJaxbContext extends JAXBContext {

    JAXBContext context;

    public FormattingJaxbContext(JAXBContext context) {
        this.context = context;
    }

    @Override
    public Unmarshaller createUnmarshaller() throws JAXBException {
        return context.createUnmarshaller();
    }

    @Override
    public Marshaller createMarshaller() throws JAXBException {
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty("jaxb.formatted.output", true);
        return marshaller;
    }

    @Override
    public Validator createValidator() throws JAXBException {
        return context.createValidator();
    }
}
