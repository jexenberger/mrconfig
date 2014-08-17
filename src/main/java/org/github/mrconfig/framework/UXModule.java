package org.github.mrconfig.framework;

import org.github.mrconfig.framework.ux.View;
import org.github.mrconfig.framework.ux.form.Form;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.github.mrconfig.framework.ux.TemplateView.templateView;

/**
 * Created by w1428134 on 2014/08/04.
 */
public class UXModule {


    View uniqueView;
    View getView;
    View putView;
    View postView;
    Form form;
    Function<Resource, Form> formSupplier;
    Resource resource;

    Map<String, View> viewRequestMap;

    UXModule() {
        viewRequestMap = new HashMap<>();
    }


    public UXModule(View uniqueView, View getView, View putView, View postView, Resource resource) {
        this();
        this.uniqueView = uniqueView;
        this.getView = getView;
        this.putView = putView;
        this.postView = postView;
        this.resource = resource;
        registerDefaultViews();
    }


    public UXModule(View listView, View editView, Resource resource) {
        this(listView,listView,editView,editView, resource);

    }



    public static UXModule defaultView(Resource resource, Function<Resource,Form> formSupplier) {
        return new UXModule(templateView("list_form.ftl"),templateView("edit_view.ftl"), resource).formSupplier(formSupplier);
    }

    public void registerDefaultViews() {
        addView("get",this.uniqueView);
        addView("list",this.getView);
        addView("put",this.putView);
        addView("post",this.postView);
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

    public void setUniqueView(View uniqueView) {
        this.uniqueView = uniqueView;
    }

    public UXModule getView(View getView) {
        this.getView = getView;
        registerDefaultViews();
        return this;
    }

    public UXModule putView(View putView) {
        this.putView = putView;
        registerDefaultViews();
        return this;
    }

    public UXModule postView(View postView) {
        this.postView = postView;
        registerDefaultViews();
        return this;
    }

    public UXModule addView(String mapping, View view) {
        Objects.requireNonNull(mapping);
        Objects.requireNonNull(view);
        viewRequestMap.put(mapping, view);
        return this;
    }

    public UXModule form(Form form) {
        Objects.requireNonNull(form);
        this.form = form;
        return this;
    }

    public UXModule formSupplier(Function<Resource,Form> supplier) {
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

    public String getGroup() {
        return resource.getGroup();
    }

    public String getKey() {
        return resource.getName();
    }

    public String getName() {
        return resource.getDisplayName();
    }

    public String getLink() {
        String defaultView = "list";
        return resource.getPath()+"/"+defaultView+".htm";
    }


    public void render(String method, OutputStream output) {
        //the hard way
        View targetView = viewRequestMap.get(method);
        if (targetView == null) {
            throw new IllegalStateException("no view could be resolved for "+method);
        }
        targetView.render(getForm(), output);
    }
}
