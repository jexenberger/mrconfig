package org.github.mrconfig.framework.ux;

import java.io.OutputStream;
import java.util.Map;

/**
 * Created by w1428134 on 2014/08/04.
 */
public class UXComponent {


    View uniqueView;
    View getView;
    View putView;
    View postView;


    public UXComponent(View uniqueView, View getView, View putView, View postView) {
        this.uniqueView = uniqueView;
        this.getView = getView;
        this.putView = putView;
        this.postView = postView;
    }

    public View getUniqueView() {
        return uniqueView;
    }

    public View getGetView() {
        return getView;
    }

    public View getPutView() {
        return putView;
    }

    public View getPostView() {
        return postView;
    }


    public void render(String method, Map<String, Object> model, OutputStream output) {
        //the hard way
        View targetView = null;
        if (method.equalsIgnoreCase("get")) {
            targetView = getUniqueView();
        }
        if (method.equalsIgnoreCase("list")) {
            targetView = getGetView();
        }
        if (method.equalsIgnoreCase("put")) {
            targetView = getPutView();
        }
        if (method.equalsIgnoreCase("post")) {
            targetView = getPostView();
        }
        if (targetView == null) {
            throw new IllegalStateException("no view could be resolved for "+method);
        }
        targetView.render(model, output);
    }
}
