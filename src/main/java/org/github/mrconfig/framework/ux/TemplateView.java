package org.github.mrconfig.framework.ux;

import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.github.mrconfig.framework.macro.angular.TemplateEngine;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Objects;

/**
 * Created by julian3 on 2014/08/11.
 */
public class TemplateView implements View{


    private String source;

    public TemplateView(String source) {
        this.source = source;
    }

    @Override
    public void render(Map<String, Object> model, OutputStream target) {
        Templating.getTemplating().write(source, model, target);
    }

    public static View templateView(String name) {
        return new TemplateView(name);
    }
}
