package org.github.levelthree.ux.form;

import org.github.levelthree.ux.form.constraints.*;

import javax.validation.constraints.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

import static org.github.levelthree.util.ReflectionUtil.invoke;
import static org.github.levelthree.util.ReflectionUtil.resolveMethod;

/**
 * Created by julian3 on 2014/08/15.
 */
public class UXConstraints {


    private static Map<Class<? extends Annotation>, Class<? extends UXConstraint>> ANNOTATION_MAP;

    static {
        ANNOTATION_MAP = new HashMap<>();
        addMapping(DecimalMax.class, DecimalMaxConstraint.class);
        addMapping(DecimalMin.class, DecimalMinConstraint.class);
        addMapping(Max.class, MaxConstraint.class);
        addMapping(Min.class, MinConstraint.class);
        addMapping(NotNull.class, RequiredConstraint.class);
        addMapping(Size.class, SizeConstraint.class);
        addMapping(Pattern.class, PatternConstraint.class);
    }


    public static Optional<UXConstraint> getConstraint(Annotation annotation) {
        Class<? extends Annotation> annotationType = (Class<? extends Annotation>) annotation.getClass().getInterfaces()[0];
        if (!ANNOTATION_MAP.containsKey(annotationType)) {
            return Optional.empty();
        }
        Class<? extends UXConstraint> constraintType = ANNOTATION_MAP.get(annotationType);
        if (constraintType == null) {
            return Optional.empty();
        }
        Constructor<?>[] constructors = constraintType.getConstructors();
        Annotation[][] parameters = constructors[0].getParameterAnnotations();
        Collection<Object> list = new ArrayList<>();
        for (Annotation[] parameter : parameters) {
            String name = ((Name) parameter[0]).value();
            try {
                Method method = annotationType.getMethod(name);
                list.add(invoke(method, annotation));

            } catch (NoSuchMethodException e) {
                if (constructors[0].getParameterCount() == 1) {
                    Method method = null;
                    list.add(invoke(resolveMethod(annotationType, "value"), annotation));

                } else {
                    return Optional.empty();
                }
            }

        }
        try {
            return Optional.of((UXConstraint) constructors[0].newInstance(list.toArray()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void  addMapping(Class<? extends Annotation> annotation,  Class<? extends UXConstraint> constraint) {
        ANNOTATION_MAP.put(annotation,constraint);
    }

}
