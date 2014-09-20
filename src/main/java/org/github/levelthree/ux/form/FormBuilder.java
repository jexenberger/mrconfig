package org.github.levelthree.ux.form;

import org.github.levelthree.Resource;
import org.github.levelthree.util.Inflector;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

/**
 * Created by julian3 on 2014/08/10.
 */
public class FormBuilder {

    Resource resource;
    Form form;
    Inflector inflector = Inflector.getInstance();

    public FormBuilder(Resource resource) {
        this(null,null);
        this.resource = resource;
        this.form.setName(inflector.phrase(resource.getResourceClass().getSimpleName()));
        this.form.setId(resource.getResourceClass().getSimpleName());
        this.form.setResourceName(resource.getPath());
        this.form.setGroup(resource.getGroup());

    }

    public FormBuilder(String id, String name) {
        this.form = new Form();
        this.form.setId(id);
        this.form.setName(name);
    }

    public static FormBuilder newInstance(Resource resource) {
        return new FormBuilder(resource);
    }

    public FormBuilder addField(FormField formField) {
        requireNonNull(formField, "formField cannot be null");
        form.addField(formField);
        if (formField.isSearchable()) {
            addSearchField(formField);
        }
        return this;
    }

    public FormBuilder withField(String id, Consumer<FormField> handler) {
        Optional<FormField> field = form.getField(id);
        field.ifPresent(handler);
        return this;
    }

    public FormBuilder setField(FormField field) {
        requireNonNull(field, "formField cannot be null");
        for (int i = 0; i < form.getFields().size(); i++) {
            FormField other = form.getFields().get(i);
            if (field.getId().equals(other.getId())) {
                form.getFields().set(i, field);
                break;
            }

        }
        addField(field);
        return this;
    }


    public FormBuilder addSearchField(FormField formField) {
        requireNonNull(formField, "formField cannot be null");
        form.addSearchField(formField);
        return this;
    }

    public FormBuilder withSearchField(String id, Consumer<FormField> handler) {
        Optional<FormField> result = form.getSearchFields().stream().filter((field) -> id.equals(field.getId())).findFirst();
        result.ifPresent(handler);
        return this;
    }

    public FormBuilder setSearchField(FormField field) {
        requireNonNull(field, "formField cannot be null");
        form.getSearchFields().removeIf((searchField)-> searchField.getId().equals(field.getId()));
        form.getSearchFields().add(field);
        return this;
    }

    public FormBuilder addSListField(FormField formField) {
        requireNonNull(formField, "formField cannot be null");
        form.getSearchFields().add(formField);
        return this;
    }

    public FormBuilder withListField(String id, Consumer<FormField> handler) {
        Optional<FormField> result = form.getListFields().stream().filter((field) -> id.equals(field.getId())).findFirst();
        result.ifPresent(handler);
        return this;
    }

    public FormBuilder sortAsc()  {
        Collections.sort(this.form.getFields(),(fieldA, fieldB)-> {
            if (fieldA.isKey() && fieldB.isKey()) {
                return 0;
            }
            if (fieldA.isKey()) {
                return -1;
            }
            if (fieldB.isKey()) {
                return 1;
            }
            if (fieldA.getOrder() != fieldB.getOrder()) {
                return Integer.compare(fieldA.getOrder(), fieldB.getOrder());
            }
            return fieldA.getId().compareTo(fieldB.getId());
        });
        return this;
    }


    public FormBuilder withForm(Consumer<Form> handler) {
        handler.accept(this.form);
        return this;
    }

    public FormBuilder addCollection(String modelProperty, Form collectionForm) {
        this.form.addCollectionForm(modelProperty, collectionForm);
        return this;
    }

    public Form getForm() {
        return form;
    }

    public void addFields(List<FormField> fields) {
        fields.forEach(form::addField);
    }
}
