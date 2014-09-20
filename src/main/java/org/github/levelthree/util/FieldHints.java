package org.github.levelthree.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by julian3 on 2014/08/15.
 */
@Target({  FIELD })
@Retention(RUNTIME)
@Documented
public @interface FieldHints {


    String group() default "";
    boolean readOnly() default false;
    String id() default "";
    String defaultValue() default "";
    int tabIndex() default -1;
    int order() default -1;
    boolean searchable() default false;

    Class<?> resource() default void.class;





}
