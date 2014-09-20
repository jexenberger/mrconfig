package org.github.levelthree.security;

import org.github.levelthree.resources.UserPrincipal;
import org.glassfish.jersey.internal.util.Base64;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.UriInfo;

import java.util.Optional;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by julian3 on 2014/09/18.
 */
public class BasicAuthFilterTest {


    public static final String ALLADIN_OPEN_SESAME_BASIC_AUTH_STRING = "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==";
    public static final String USER_PASSWORD_AUTH_STRING = "Basic "+ Base64.encodeAsString("user:password");
    public static final String NO_USER_AUTH_STRING = "Basic "+ Base64.encodeAsString(":password");
    public static final String NO_PASSWORD_AUTH_STRING = "Basic "+ Base64.encodeAsString("passwordless:");
    public static final String BAD_AUTH_STRING = "Basic "+ Base64.encodeAsString(":");
    public static final String EMPTY_STRING = "Basic "+ Base64.encodeAsString("");

    private ContainerRequestContext containerRequest;
    private UriInfo uriInfo;

    @Before
    public void setUp() throws Exception {
        containerRequest = createMock(ContainerRequestContext.class);
        uriInfo = createMock(UriInfo.class);

        expect(containerRequest.getMethod()).andReturn("POST").anyTimes();
        expect(containerRequest.getUriInfo()).andReturn(uriInfo).anyTimes();
        expect(uriInfo.getPath()).andReturn("/test/1");


        Security.setUserRegistry((userId)-> {
            if (userId.equals("Aladdin")) {
                return Optional.of(new UserPrincipal(userId,"open sesame"));
            }
            if (userId.equals("passwordless")) {
                return Optional.of(new UserPrincipal(userId,null));
            }
            return Optional.empty();
        });


    }

    @Test
    public void testFilter() throws Exception {

        containerRequest.setSecurityContext(anyObject(UserPrincipal.class));
        expectLastCall().atLeastOnce();
        expect(containerRequest.getHeaderString("authorization")).andReturn(ALLADIN_OPEN_SESAME_BASIC_AUTH_STRING).anyTimes();

        replay(containerRequest, uriInfo);

        new BasicAuthFilter().filter(containerRequest);

        verify(containerRequest);


    }

    @Test
    public void testFilterBadUser() throws Exception {

        expect(containerRequest.getHeaderString("authorization")).andReturn(USER_PASSWORD_AUTH_STRING).anyTimes();
        replay(containerRequest, uriInfo);

        try {
            new BasicAuthFilter().filter(containerRequest);
            fail("should have been stopped");
        } catch (WebApplicationException e) {
            assertEquals(401, e.getResponse().getStatus());
        }

        verify(containerRequest);
    }

    @Test
    public void testFilterNoUserAuth() throws Exception {

        expect(containerRequest.getHeaderString("authorization")).andReturn(NO_USER_AUTH_STRING).anyTimes();
        replay(containerRequest, uriInfo);

        try {
            new BasicAuthFilter().filter(containerRequest);
            fail("should have been stopped");
        } catch (WebApplicationException e) {
            assertEquals(401, e.getResponse().getStatus());
        }

        verify(containerRequest);
    }
    @Test
    public void testFilterBadString() throws Exception {

        expect(containerRequest.getHeaderString("authorization")).andReturn(BAD_AUTH_STRING).anyTimes();
        replay(containerRequest, uriInfo);

        try {
            new BasicAuthFilter().filter(containerRequest);
            fail("should have been stopped");
        } catch (WebApplicationException e) {
            assertEquals(401, e.getResponse().getStatus());
        }

        verify(containerRequest);
    }

    @Test
    public void testFilterEmptyString() throws Exception {

        expect(containerRequest.getHeaderString("authorization")).andReturn(EMPTY_STRING).anyTimes();
        replay(containerRequest, uriInfo);

        try {
            new BasicAuthFilter().filter(containerRequest);
            fail("should have been stopped");
        } catch (WebApplicationException e) {
            assertEquals(401, e.getResponse().getStatus());
        }

        verify(containerRequest);
    }

    @Test
    public void testFilterNoPassword() throws Exception {

        containerRequest.setSecurityContext(anyObject(UserPrincipal.class));
        expectLastCall().atLeastOnce();
        expect(containerRequest.getHeaderString("authorization")).andReturn(NO_PASSWORD_AUTH_STRING).anyTimes();

        replay(containerRequest, uriInfo);

        new BasicAuthFilter().filter(containerRequest);

        verify(containerRequest);


    }


}
