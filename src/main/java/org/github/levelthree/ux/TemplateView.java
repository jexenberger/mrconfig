package org.github.levelthree.ux;

import java.io.OutputStream;

/**
 * Created by julian3 on 2014/08/11.
 */
public class TemplateView implements View{


    private String source;

    public TemplateView(String source) {
        this.source = source;
    }

    @Override
    public void render(Object model, OutputStream target) {
        Templating.getTemplating().write(source, model, target);
    }

    public static View templateView(String name) {
        return new TemplateView(name);
    }

    public String getSource() {
        return source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TemplateView)) return false;

        TemplateView that = (TemplateView) o;

        if (!source.equals(that.source)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return source.hashCode();
    }
}
