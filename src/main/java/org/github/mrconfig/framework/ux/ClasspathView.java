package org.github.mrconfig.framework.ux;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * Created by julian3 on 2014/08/11.
 */
public class ClasspathView implements View{


    ClassLoader classLoader;
    String path;

    public ClasspathView(ClassLoader classLoader, String path) {
        this.classLoader = classLoader;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public ClasspathView(String path) {
        this(Thread.currentThread().getContextClassLoader(),path);
    }

    @Override
    public InputStream getSource() {
        InputStream resourceAsStream = classLoader.getResourceAsStream(path);
        Objects.requireNonNull(resourceAsStream,path+" returned no resource");
        return resourceAsStream;
    }

    public static View classpath(String path) {
        return new ClasspathView(path);
    }

    public static View classpath(ClassLoader classLoader, String path) {
        return new ClasspathView(classLoader, path);
    }
}
