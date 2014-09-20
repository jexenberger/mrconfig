package org.github.levelthree.ux;

import java.io.InputStream;
import java.util.Objects;

/**
 * Created by julian3 on 2014/08/11.
 */
public class ClasspathSource implements Source{


    ClassLoader classLoader;
    String path;

    public ClasspathSource(ClassLoader classLoader, String path) {
        this.classLoader = classLoader;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public ClasspathSource(String path) {
        this(Thread.currentThread().getContextClassLoader(),path);
    }

    @Override
    public InputStream getSource() {
        InputStream resourceAsStream = classLoader.getResourceAsStream(path);
        Objects.requireNonNull(resourceAsStream,path+" returned no resource");
        return resourceAsStream;
    }

    public static Source classpath(String path) {
        return new ClasspathSource(path);
    }

    public static Source classpath(ClassLoader classLoader, String path) {
        return new ClasspathSource(classLoader, path);
    }
}
