package org.github.mrconfig.framework.scaffold;

import org.github.mrconfig.framework.util.Pair;

import java.util.Arrays;

/**
 * Created by julian3 on 2014/07/19.
 */
public class FormField {

    private final Pair<String, String>[] options;
    String id;
    String label;
    String type;
    String lookup;
    String lookupFilter;
    boolean required;

    public FormField(String id, String label, String type, String lookup, String lookupFilter, boolean required, Pair<String, String> ... options) {
        this.id = id;
        this.label = label;
        this.type = type;
        this.lookup = lookup;
        this.options = options;
        this.lookupFilter = lookupFilter;
        this.required = required;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getType() {
        return type;
    }

    public String getLookup() {
        return lookup;
    }

    public String htmlType() {
        if (type.equals("Hidden")) {
            return "hidden";
        }
        if (type.equals("Enum")) {
            return "select";
        }
        if (type.equals("Boolean")) {
            return "checkbox";
        }
        return "text";
    }

    public String getLookupFilter() {
        return lookupFilter;
    }

    public Pair<String, String>[] getOptions() {
        return options;
    }

    public boolean isRequired() {
        return required;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FormField{");
        sb.append("options=").append(Arrays.toString(options));
        sb.append(", id='").append(id).append('\'');
        sb.append(", label='").append(label).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", lookup='").append(lookup).append('\'');
        sb.append(", lookupFilter='").append(lookupFilter).append('\'');
        sb.append(", required=").append(required);
        sb.append('}');
        return sb.toString();
    }
}
