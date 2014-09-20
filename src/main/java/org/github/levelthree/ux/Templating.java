package org.github.levelthree.ux;


import freemarker.cache.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

/**
 * Created by w1428134 on 2014/08/12.
 */
public class Templating {


    private static Templating TEMPLATING;
    private static ServletContext CONTEXT;
    private Configuration config;
    private static Collection<String> fileDirs;
    private static Collection<Class> packagePaths;

    public static Templating getTemplating() {
        if (TEMPLATING == null) {
            TEMPLATING = new Templating();
        }
        return TEMPLATING;
    }

    public static void setTemplating(Templating templating) {
        TEMPLATING = templating;
    }

    public static void registerPath(String path) {
        if (fileDirs == null) {
            fileDirs = new ArrayList<>();
        }
        fileDirs.add(path);
    }

    public static void registerClass(Class<?> clazz) {
        if (packagePaths == null) {
            packagePaths = new ArrayList<>();
        }
        packagePaths.add(clazz);
    }

    protected Templating() {
        config = new Configuration();
        config.setLocale(Locale.getDefault());
        Collection<TemplateLoader> loaders = new ArrayList<>();
        if (fileDirs != null && fileDirs.size() > 0) {
            fileDirs.forEach((dir) -> {
                try {
                    loaders.add(new FileTemplateLoader(new File(dir), true));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        if (packagePaths != null && packagePaths.size() > 0) {
            packagePaths.forEach((clazz) -> loaders.add(new ClassTemplateLoader(clazz, "")));
        }
        if (CONTEXT != null) {
            loaders.add(new WebappTemplateLoader(CONTEXT));
        }

        TemplateLoader[] loaderArray = loaders.toArray(new TemplateLoader[]{});
        config.setTemplateLoader(new MultiTemplateLoader(loaderArray));
    }

    public void write(String name, Object model, OutputStream target) {
        try {
            Template template = config.getTemplate(name);
            template.process(model, new OutputStreamWriter(target));
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}
