package org.github.levelthree.util;

import org.junit.Test;

import java.util.Collection;
import java.util.Optional;

import static org.github.levelthree.util.Box.error;
import static org.github.levelthree.util.Box.nothing;
import static org.github.levelthree.util.Box.success;
import static org.github.levelthree.util.Pair.cons;
import static org.junit.Assert.*;

/**
 * Created by w1428134 on 2014/08/04.
 */
public class BoxTest {


    @Test
    public void testSuccess() throws Exception {

        Box<String> result = success("hello");
        assertNotNull(result);
        boolean called;
        assertEquals(Box.State.success, result.getState());
        called = result.onSuccess((value)-> true).get();
        assertTrue(called);
        //explicitly set to false
        called = false;
        Object nothing = result.onNothing(()->false);
        assertNotNull(nothing);

    }


    @Test
    public void testError() throws Exception {

        Box<String> result = error(cons("1", "error 1"), cons("2","error 2"));
        assertNotNull(result);
        boolean called;
        assertEquals(Box.State.error, result.getState());
        Optional<Boolean> wasCalled = result.onNothing(() -> true);
        assertFalse(wasCalled.isPresent());
        //explicitly set to false
        called = false;
        Optional<Boolean> nothing = result.onSuccess((value) -> false);
        assertNotNull(nothing);
        assertTrue(!nothing.isPresent());
        Collection<String> display = result.mapError((code,label)->String.join(" ",code, label));
        assertTrue(display.contains("1 error 1"));
        assertTrue(display.contains("2 error 2"));
        assertNotNull(result.getErrors());
        assertEquals(2, result.getErrors().size());

    }

    @Test
    public void testNothing() throws Exception {
        Box<String> result = nothing();
        Optional<Boolean> wasCalled = result.onNothing(() -> true);
        assertTrue(wasCalled.get());
        wasCalled = result.onError((errors) -> true);
        assertFalse(wasCalled.isPresent());
        wasCalled = result.onSuccess((val) -> true);
        assertFalse(wasCalled.isPresent());
    }



}
