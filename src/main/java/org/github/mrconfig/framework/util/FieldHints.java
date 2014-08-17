package org.github.mrconfig.framework.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by julian3 on 2014/08/15.
 */
@Target({  PARAMETER })
@Retention(RUNTIME)
@Documented
public @interface FieldHints {

    String group() default "default";
    boolean readOnly() default false;
    String id() default "";
    Class<?> resource();




}
