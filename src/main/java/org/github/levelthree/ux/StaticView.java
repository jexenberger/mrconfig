package org.github.levelthree.ux;

import java.io.OutputStream;

/**
 * Created by w1428134 on 2014/08/12.
 */
public class StaticView  implements View{

    Source source;

    public StaticView(Source source) {
        this.source = source;
    }

    @Override
    public void render(Object model, OutputStream target) {
        //can't render the model, just process the target
        source.render(target);
    }

    public Source getSource() {
        return source;
    }

    public static View staticView(Source source) {
        return new StaticView(source);
    }
}
