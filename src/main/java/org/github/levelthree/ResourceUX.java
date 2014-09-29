package org.github.levelthree;

import org.github.levelthree.ux.View;
import org.github.levelthree.ux.form.Form;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by w1428134 on 2014/08/04.
 */
public abstract class ResourceUX {




    Form form;
    Function<Resource, Form> formSupplier;
    Resource resource;

    Map<String, View> viewRequestMap;

    public ResourceUX() {
        viewRequestMap = new HashMap<>();
    }



    public ResourceUX addView(String mapping, View view) {
        Objects.requireNonNull(mapping);
        viewRequestMap.put(mapping, view);
        return this;
    }

    public ResourceUX form(Form form) {
        Objects.requireNonNull(form);
        this.form = form;
        this.form.setGroup(getGroup());
        return this;
    }

    public ResourceUX formSupplier(Function<Resource,Form> supplier) {
        this.formSupplier = supplier;
        return this;
    }

    public Form getForm() {
        if (this.form != null) {
            return this.form;
        }
        if (this.formSupplier != null) {
            this.form = this.formSupplier.apply(this.resource);
        }
        return this.form;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public ResourceUX resource(Resource resource) {
        setResource(resource);
        return this;
    }

    public String getGroup() {
        return resource.getGroup();
    }

    public String getKey() {
        return resource.getName();
    }

    public String getName() {
        return resource.getDisplayName();
    }

    public Resource getResource() {
        return resource;
    }

    public void render(String method, OutputStream output) {
        //the hard way
        View targetView = viewRequestMap.get(method);
        if (targetView == null) {
            throw new IllegalStateException("no view could be resolved for "+method);
        }
        targetView.render(getForm(), output);
    }

    public abstract String getListLink();

    public abstract String getCreateLink();

    public abstract String getEditLink();

    public abstract String getViewLink();

    public void create() {
        Objects.requireNonNull(resource, "resource not set");
        init();
    }

    public abstract void init();

}
