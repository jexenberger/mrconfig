package org.github.mrconfig.framework.ux;

import freemarker.template.Template;
import org.github.mrconfig.framework.macro.angular.TemplateEngine;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * Created by julian3 on 2014/08/11.
 */
public class TemplateView implements View{

    View delegate;

    public TemplateView(View delegate) {
        Objects.requireNonNull(delegate);
        this.delegate = delegate;
    }

    @Override
    public InputStream getSource() {
        return delegate.getSource();
    }

    @Override
    public String getPath() {
        return this.delegate.getPath();
    }

    @Override
    public void render(OutputStream outputStream) {
        Template template = TemplateEngine.getConfiguration().getTemplate(getPath());
        template.process();
    }
}
