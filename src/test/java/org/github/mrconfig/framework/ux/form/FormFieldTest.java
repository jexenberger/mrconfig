package org.github.mrconfig.framework.ux.form;

import org.junit.Test;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * Created by julian3 on 2014/08/15.
 */
public class FormFieldTest {


    @NotNull
    @Size(min=1,max=100)
    String value;

    @NotNull
    String readOnly;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getReadOnly() {
        return readOnly;
    }

    @Test
    public void testFromField() throws Exception {

        Field fValue = getClass().getDeclaredField("value");
        FormField formField = FormField.fromField(fValue, FormFieldTest.class, null);
        assertNotNull(formField);
        assertEquals("value", formField.getId());
        assertEquals("Value",formField.getLabel());
        assertNull(formField.getLookup());
        assertNull(formField.getLookupFilter());
        assertFalse(formField.isReadOnly());
        assertNotNull(formField.getConstraints());
        assertEquals(2,formField.getConstraints().size());
    }

    @Test
    public void testFromReadOnly() throws Exception {

        Field fValue = getClass().getDeclaredField("readOnly");
        FormField formField = FormField.fromField(fValue, FormFieldTest.class, null);
        assertNotNull(formField);
        assertEquals("readOnly", formField.getId());
        assertEquals("Read Only",formField.getLabel());
        assertNull(formField.getLookup());
        assertNull(formField.getLookupFilter());
        assertTrue(formField.isReadOnly());
        assertNotNull(formField.getConstraints());
        assertEquals(1, formField.getConstraints().size());
    }
}
