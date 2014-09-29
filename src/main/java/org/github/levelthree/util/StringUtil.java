package org.github.levelthree.util;

/**
 * Created by julian3 on 2014/09/21.
 */
public class StringUtil {


    public static String capitalize(String aString) {

        return new StringBuilder().append(Character.toUpperCase(aString.charAt(0))).append(aString.substring(1)).toString();
    }

}
