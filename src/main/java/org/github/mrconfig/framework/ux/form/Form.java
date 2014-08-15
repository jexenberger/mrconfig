package org.github.mrconfig.framework.ux.form;

import org.github.mrconfig.framework.macro.ListForm;

import java.util.*;

/**
 * Created by julian3 on 2014/07/19.
 */
public class Form {

    String id;
    String name;
    String resourceName;
    List<FormField> fields;
    ListForm list;
    Set<FormField> searchFields;
    Set<FormField> listFields;
    String group = "Main";


    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public void setFields(List<FormField> fields) {
        this.fields = fields;
        Collections.sort(fields, (field1, field2) -> {
            return orderFields(field1, field2);
        });
    }

    public void setList(ListForm list) {
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getResourceName() {
        return resourceName;
    }

    public List<FormField> getFields() {
        if (fields == null) {
            fields = new ArrayList<>();
        }
        Collections.sort(fields, (field1, field2) -> {
            return orderFields(field1, field2);
        });
        return fields;
    }

    public void addSearchField(FormField searchField) {
        getSearchFields().add(searchField);
    }

    public Set<FormField> getSearchFields() {
        if (searchFields == null) {
            searchFields = new LinkedHashSet<>();
        }
        Collections.sort(fields, (field1, field2) -> {
            return orderFields(field1, field2);
        });
        return searchFields;
    }

    public Set<FormField> getListFields() {
        if (listFields == null) {
            listFields = new LinkedHashSet<>();
        }
        return listFields;
    }

    public int orderFields(FormField field1, FormField field2) {
        if (field1.getId().equals("id")) {
            return -1;
        }
        if (field1.getId().equals("key") && !field2.equals("id")) {
            return -1;
        }
        return field1.getLabel().compareTo(field2.getLabel());
    }

    public void setSearchFields(Set<FormField> searchFields) {
        this.searchFields = searchFields;
    }

    public ListForm getList() {
        return list;
    }

    public Optional<FormField> getField(String id) {
        return fields.stream().filter((field) -> field.getId().equals(id)).findFirst();
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
