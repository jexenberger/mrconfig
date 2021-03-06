package org.github.levelthree.angular;

import org.github.levelthree.ResourceUX;
import org.github.levelthree.ux.View;

import java.io.ByteArrayOutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Optional.ofNullable;
import static org.github.levelthree.util.StringUtil.capitalize;
import static org.github.levelthree.ux.TemplateView.templateView;

/**
 * Created by julian3 on 2014/09/21.
 */
public class AngularResourceUX extends ResourceUX {


    public static final String VIEW_VIEW_MAPPING = "template";
    public static final String LIST_VIEW_MAPPING = "list";
    public static final String EDIT_VIEW_MAPPING = "edit";
    public static final String CREATE_VIEW_MAPPING = "create";


    boolean defaultComponents = false;


    String serviceName;
    AngularUXComponent createComponent;
    AngularUXComponent editComponent;
    AngularUXComponent listComponent;
    AngularUXComponent viewComponent;
    Map<String, AngularUXComponent> componentMap = new LinkedHashMap<>();




    boolean init = false;
    private View serviceView;

    public AngularResourceUX() {
        super();
        defaultComponents = true;
    }



    public AngularResourceUX(String serviceName, AngularUXComponent createComponent, AngularUXComponent editComponent, AngularUXComponent listComponent, AngularUXComponent viewComponent, View serviceView) {
        super();
        this.serviceName = serviceName;
        setCreateComponent(createComponent);
        setEditComponent(editComponent);
        setListComponent(listComponent);
        setViewComponent(viewComponent);
        this.serviceView = serviceView;
    }

    public AngularResourceUX(View serviceView) {
        super();
        this.serviceView = serviceView;
    }

    public String getServiceName() {
        return ofNullable(serviceName).orElse(getGroup() + "_services_" + getResource().getName() + "Service");
    }

    public String getResourcePath() {
        return getResource().getPath();
    }



    public String getTemplatePath(String defaultView) {
        return "/ng-app/" + getGroup() + "/views" + getResource().getPath() + "/" + defaultView;
    }

    public String getNavigationLink(String defaultView, String navParameter) {
        if (navParameter != null) {
            return  "/views" + getResource().getPath() + "/" +  defaultView + "/" + navParameter;
        } else {
            return  "/views" + getResource().getPath() + "/" + defaultView;
        }
    }

    public String getControllerName(String defaultView) {
        return getGroup() + "_controllers_" + getResource().getName()+ capitalize(defaultView) + "Controller";
    }

    @Override
    public void init() {
        if (defaultComponents) {
            setCreateComponent(ofNullable(createComponent).orElse(createAngularComponent(CREATE_VIEW_MAPPING, templateView("edit_form.ftl"), templateView("edit_controller.ftl"), templateView("simple_resolve.ftl"), null, "edit.html", EDIT_VIEW_MAPPING)));
            setEditComponent(ofNullable(editComponent).orElse(createAngularComponent(EDIT_VIEW_MAPPING, templateView("edit_form.ftl"),templateView("edit_controller.ftl"), templateView("edit_resolve.ftl"), ":p_id", "edit.html", EDIT_VIEW_MAPPING)));
            setListComponent(ofNullable(listComponent).orElse(createAngularComponent(LIST_VIEW_MAPPING, templateView("list_form.ftl"), templateView("list_controller.ftl"), templateView("simple_resolve.ftl"), null, "list.html", LIST_VIEW_MAPPING)));
            setViewComponent(ofNullable(viewComponent).orElse(createAngularComponent(VIEW_VIEW_MAPPING, templateView("edit_form.ftl"), templateView("edit_controller.ftl"), templateView("edit_resolve.ftl"), ":p_id", "edit.html", EDIT_VIEW_MAPPING)));
            serviceView = templateView("setService.ftl");
        }
        init = true;
    }

    public AngularUXComponent createAngularComponent(String viewType, View view, View controllerView, View routeResolveView, String navParameter, String fileName, String controllerType) {
        addView(fileName, templateView(fileName));
        return new AngularUXComponent(getNavigationLink(viewType, navParameter), getTemplatePath(fileName), getControllerName(controllerType), view, controllerView, routeResolveView);
    }


    void checkInitialisation() {
        if (!init) {
            throw new IllegalStateException("init() not called yet");
        }
    }

    public AngularUXComponent getCreateComponent() {
        return createComponent;
    }

    public AngularUXComponent getEditComponent() {
        return editComponent;
    }

    public AngularUXComponent getListComponent() {
        return listComponent;
    }

    public AngularUXComponent getViewComponent() {
        return viewComponent;
    }

    public String getListLink() {
        checkInitialisation();
        return listComponent.getRoutePath();
    }

    public String getViewLink() {
        checkInitialisation();
        return viewComponent.getRoutePath();
    }

    @Override
    public View getViewByType(String type) {
        return getComponentByType(type).getTemplate();
    }

    public String getEditLink() {
        checkInitialisation();
        return editComponent.getRoutePath();
    }

    public String getCreateLink() {
        checkInitialisation();
        return createComponent.getRoutePath();
    }

    public String getListTemplate() {
        checkInitialisation();
        return listComponent.getTemplateUrl();
    }

    public String getCreateTemplate() {
        checkInitialisation();
        return createComponent.getTemplateUrl();
    }

    public String getEditTemplate() {
        checkInitialisation();
        return editComponent.getTemplateUrl();
    }

    public String getModuleName() {
        return getResource().getGroup();
    }

    public String getViewTemplate() {
        checkInitialisation();
        return viewComponent.getTemplateUrl();
    }


    public String getListController() {
        checkInitialisation();
        return listComponent.getControllerName();
    }

    public String getCreateController() {
        checkInitialisation();
        return createComponent.getControllerName();
    }

    public String getEditController() {
        checkInitialisation();
        return editComponent.getControllerName();
    }

    public String getViewController() {
        checkInitialisation();
        return viewComponent.getControllerName();
    }




    public String getListControllerView() {
        return renderTemplate(listComponent.getControllerView(), this);
    }

    public String getViewControllerView() {
        return renderTemplate(viewComponent.getControllerView(), this);
    }

    public String getCreateControllerView() {
        return renderTemplate(createComponent.getControllerView(), this);
    }

    public String getEditControllerView() {
        return renderTemplate(editComponent.getControllerView(), this);
    }

    public String getControllerViews() {
        StringBuilder builder = new StringBuilder();
        if (listComponent != null) {
            builder.append(getListControllerView()).append("\n");
        }
        //by default we use the same setController for all template/edit/create operations ergo we check if they are the same
        if (viewComponent != null) {
            builder.append(getViewControllerView()).append("\n");
        }
        if (!Objects.equals(getViewControllerView(), getCreateControllerView())) {
            builder.append(getCreateControllerView()).append("\n");
        }
        if (!Objects.equals(getViewControllerView(), getEditControllerView())) {
            builder.append(getEditControllerView()).append("\n");
        }
        return builder.toString();
    }

    public String getService() {
        serviceView = templateView("setService.ftl");
        return renderTemplate(serviceView,this);
    }

    public String renderTemplate(View template, Object model) {
        checkInitialisation();
        ByteArrayOutputStream target = new ByteArrayOutputStream();
        template.render(model, target);
        return target.toString();
    }

    public void setCreateComponent(AngularUXComponent createComponent) {
        this.componentMap.put(CREATE_VIEW_MAPPING, createComponent);
        this.createComponent = createComponent;
    }

    public void setEditComponent(AngularUXComponent editComponent) {
        this.componentMap.put(EDIT_VIEW_MAPPING, editComponent);
        this.editComponent = editComponent;
    }

    public void setListComponent(AngularUXComponent listComponent) {
        this.componentMap.put(LIST_VIEW_MAPPING, listComponent);
        this.listComponent = listComponent;
    }

    public void setViewComponent(AngularUXComponent viewComponent) {
        this.componentMap.put(VIEW_VIEW_MAPPING, viewComponent);
        this.viewComponent = viewComponent;
    }

    public AngularUXComponent getComponentByType(String type) {
        return this.componentMap.get(type);
    }
}
