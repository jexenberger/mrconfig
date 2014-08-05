package org.github.mrconfig.domain;

import org.github.mrconfig.framework.util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.github.mrconfig.framework.util.Pair.cons;

/**
 * Created by julian3 on 2014/07/19.
 */
public class BooleanWrapper extends Wrapper{

    Object test = Arrays.asList("true", "false", "t", "f", "y", "n", "yes", "no", "0", "1");
    static Map<String, Pair<String, String>> MAPPINGS;
    static {
        MAPPINGS = new HashMap<>();
        MAPPINGS.put("TRUE_OR_FALSE",cons("TRUE","FALSE"));
        MAPPINGS.put("True_OR_False",cons("True","False"));
        MAPPINGS.put("true_OR_false",cons("true","false"));
        MAPPINGS.put("T_OR_F",cons("T","F"));
        MAPPINGS.put("t_OR_f",cons("t","f"));
        MAPPINGS.put("y_OR_n",cons("y","n"));
        MAPPINGS.put("Y_OR_N",cons("Y","N"));
        MAPPINGS.put("yes_OR_no",cons("yes","no"));
        MAPPINGS.put("Yes_OR_No",cons("Yes","No"));
        MAPPINGS.put("YES_OR_NO",cons("YES","NO"));
        MAPPINGS.put("ONE_OR_ZERO",cons("1","0"));
        MAPPINGS.put("ZERO_OR_MINUS_ONE",cons("0","-1"));
    }

    public String TRUE_or_FALSE() {
        return convert("TRUE_OR_FALSE");
    }

    public String True_OR_False() {
        return convert("True_OR_False");
    }

    public String true_OR_false() {
        return convert("true_OR_false");
    }

    public String T_OR_F() {
        return convert("T_OR_F");
    }

    public String t_OR_f() {
        return convert("t_OR_f");
    }

    public String y_OR_n() {
        return convert("y_OR_n");
    }

    public String Y_OR_N() {
        return convert("Y_OR_N");
    }

    public String yes_OR_no() {
        return convert("yes_OR_no");
    }

    public String Yes_OR_No() {
        return convert("Yes_OR_No");
    }

    public String YES_OR_NO() {
        return convert("YES_OR_NO");
    }

    public String ONE_OR_ZERO() {
        return convert("ONE_OR_ZERO");
    }

    public String ZERO_OR_MINUS_ONE() {
        return convert("ZERO_OR_MINUS_ONE");
    }



    public BooleanWrapper(String value) {
        super(value);
    }

    private String convert(String type) {
        if (getValue() == null) {
            return "";
        }
        boolean b = Boolean.parseBoolean(getValue());
        Pair<String, String> mapping = MAPPINGS.get(type);
        return (b) ? mapping.getCar() : mapping.getCdr();
    }




}
