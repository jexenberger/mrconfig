package org.github.levelthree.resources;

import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;

/**
 * Created by julian3 on 2014/11/04.
 */
public class BaseResourceTest {

    @Test
    public void testToType() throws Exception {

        assertEquals("application/xml", new BaseResource() {}.toType(MediaType.APPLICATION_XML_TYPE));

    }


    @Test
    public void testGetMediaType() throws Exception {

        assertEquals(MediaType.APPLICATION_JSON_TYPE, new BaseResource() {}.resolveMediaType(null).get());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, new BaseResource() {}.resolveMediaType(MediaType.WILDCARD_TYPE).get());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, new BaseResource() {}.resolveMediaType(MediaType.TEXT_PLAIN_TYPE).get());
    }
}
