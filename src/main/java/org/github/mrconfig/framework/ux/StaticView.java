package org.github.mrconfig.framework.ux;

import java.io.OutputStream;
import java.util.Map;

/**
 * Created by w1428134 on 2014/08/12.
 */
public class StaticView  implements View{

    Source source;

    public StaticView(Source source) {
        this.source = source;
    }

    @Override
    public void render(Map<String, Object> model, OutputStream target) {
        //can't render the model, just process the target
        source.render(target);
    }


    public static View staticView(Source source) {
        return new StaticView(source);
    }
}
