package org.github.mrconfig.framework.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;

/**
 * Created by julian3 on 2014/09/11.
 */
public class JaxbUtil {




    public static JAXBContext get(Class<?> type) {
        try {
            JAXBContext jc = JAXBContext.newInstance(type);
            return jc;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }


    public static <T> T toObject(String xml, Class<T> targetClass) {
        try {
            return (T) get(targetClass).createUnmarshaller().unmarshal(new ByteArrayInputStream(xml.getBytes()));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toXml(Object instance) {
        try {
            StringWriter writer = new StringWriter();
            get(instance.getClass()).createMarshaller().marshal(instance, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

}
