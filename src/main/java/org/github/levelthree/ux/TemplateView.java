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
}
