package org.github.levelthree.ux.form;

import org.github.levelthree.ux.form.constraints.MinConstraint;
import org.github.levelthree.ux.form.constraints.RequiredConstraint;
import org.github.levelthree.ux.form.constraints.SizeConstraint;
import org.junit.Test;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by julian3 on 2014/08/15.
 */
public class UXConstraintsTest {


    @Size(min=1,max=2)
    @NotNull
    @Min(100)
    private String val;


    @Test
    public void testGetConstraint() throws Exception {

        Field field = getClass().getDeclaredField("val");

        Optional<UXConstraint> constraint = UXConstraints.getConstraint(field.getAnnotation(Size.class));
        assertTrue(constraint.isPresent());
        assertEquals(SizeConstraint.class, constraint.get().getClass());

        constraint = UXConstraints.getConstraint(field.getAnnotation(NotNull.class));
        assertTrue(constraint.isPresent());
        assertEquals(RequiredConstraint.class, constraint.get().getClass());

        constraint = UXConstraints.getConstraint(field.getAnnotation(Min.class));
        assertTrue(constraint.isPresent());
        assertEquals(MinConstraint.class, constraint.get().getClass());

    }
}
