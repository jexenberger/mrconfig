package org.github.levelthree.ux.form;


import org.github.levelthree.ModuleRegistry;

import java.util.*;

import static java.util.Optional.ofNullable;

/**
 * Created by julian3 on 2014/07/19.
 */
public class Form {

    String id;
    String name;
    String resourceName;
    List<FormField> fields;
    Set<FormField> searchFields;
    Set<FormField> listFields;
    String group = ModuleRegistry.DEFAULT_MODULE;
    boolean sorted = false;
    boolean sortedSearch = false;
    Map<String, Form> collectionForms;
    Map<String, String> uxContext;
    


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
        if (!sorted) {
            Collections.sort(fields, (field1, field2) -> {
                sorted = true;
                return orderFields(field1, field2);
            });
        }
        return fields;
    }

    public void addSearchField(FormField searchField) {
        getSearchFields().add(searchField);
    }

    public Set<FormField> getSearchFields() {
        if (searchFields == null) {
            searchFields = new LinkedHashSet<>();
        }
        if (!sortedSearch) {
            Collections.sort(fields, (field1, field2) -> {
                sortedSearch = true;
                return orderFields(field1, field2);
            });
        }
        return searchFields;
    }

    public Set<FormField> getListFields() {
        if (listFields == null) {
            listFields = new LinkedHashSet<>();
        }
        return listFields;
    }

    public int orderFields(FormField field1, FormField field2) {
        if (!field1.getGroup().equals(field2.getGroup())) {
            return field1.getGroup().compareTo(field2.getGroup());
        }
        if (field1.getId().equals("id")) {
            return -1;
        }
        if (field1.getId().equals("key") && !field2.equals("id")) {
            return -1;
        }
        return field1.getLabel().compareTo(field2.getLabel());
    }


    public Optional<FormField> getField(String id) {
        return fields.stream().filter((field) -> field.getId().equals(id)).findFirst();
    }

    public Form addField(FormField field) {
        if (this.fields == null) {
            this.fields = new ArrayList<>();
        }
        this.fields.add(field);
        return this;
    }

    public void addCollectionForm(String modelProperty, Form collectionForm) {
        if (this.collectionForms == null) {
            this.collectionForms = new HashMap<>();
        }
        collectionForm.getFields().forEach((field)-> {
            field.setParent(modelProperty);
            field.setIndexed(true);
        });
        Collections.sort(collectionForm.getFields(), sortComparator());
        this.collectionForms.put(modelProperty, collectionForm);
    }

    public Map<String, Form> getCollectionForms() {
        return collectionForms;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Map<String, List<FormField>> getByGroups() {

        Map<String, List<FormField>> result = new LinkedHashMap<>();
        //add default first so that it always pops up first in the linked hash map
        result.put("default", new ArrayList<>());
        for (FormField formField : getFields()) {
            if (!result.containsKey(formField.getGroup())) {
                result.put(formField.getGroup(), new ArrayList<>());
            }
            result.get(formField.getGroup()).add(formField);
        }
        //sort fields
        for (List<FormField> formFields : result.values()) {
            Collections.sort(formFields, sortComparator());
        }
        return result;

    }

    public Comparator<FormField> sortComparator() {
        return (fieldA, fieldB)-> {
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
        };
    }

    public Map<String, String> getUxContext() {
        uxContext = ofNullable(uxContext).orElse(new HashMap<>());
        return uxContext;
    }

    public void setUxContext(Map<String, String> uxContext) {
        this.uxContext = uxContext;
    }
}
