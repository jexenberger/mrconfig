package org.github.mrconfig.framework.security;

import org.github.mrconfig.framework.resources.UserPrincipal;

import javax.ws.rs.core.SecurityContext;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by julian3 on 2014/09/18.
 */
public class Security {


    private static boolean disableSecurity = false;
    private static boolean strictMode = true;
    private static boolean useDefaultRoles = false;
    private static String lookupRole = "lookup";
    private static String listRole = "list";
    private static String createRole = "create";
    private static String updateRole = "update";
    private static String deleteRole = "delete";
    private static String defaultHashingAlgorithm = "SHA-256";

    public static void reset() {
        disableSecurity = false;
        strictMode = true;
        useDefaultRoles = false;
        lookupRole = "lookup";
        listRole = "list";
        createRole = "create";
        updateRole = "update";
        deleteRole = "delete";
        defaultHashingAlgorithm = "SHA-256";
    }

    private static Function<String, Optional<UserPrincipal>> userRegistry = (userId)-> Optional.empty();


    public static Optional<UserPrincipal> getUser(String userId) {
        if (userRegistry == null) {
            return Optional.empty();
        }
        return userRegistry.apply(userId);
    }

    public static void setUserRegistry(Function<String, Optional<UserPrincipal>> userRegistry) {
        Objects.requireNonNull(userRegistry, "userRegistry cannot be null");
        Security.userRegistry = userRegistry;
    }

    public static boolean isDisableSecurity() {
        return disableSecurity;
    }

    public static void setDisableSecurity(boolean disableSecurity) {
        Security.disableSecurity = disableSecurity;
    }

    public static boolean isStrictMode() {
        return strictMode;
    }

    public static void setStrictMode(boolean strictMode) {
        Security.strictMode = strictMode;
    }

    public static boolean isUseDefaultRoles() {
        return useDefaultRoles;
    }

    public static void setUseDefaultRoles(boolean useDefaultRoles) {
        Security.useDefaultRoles = useDefaultRoles;
    }

    public static String getLookupRole() {
        return lookupRole;
    }

    public static void setLookupRole(String lookupRole) {
        Security.lookupRole = lookupRole;
    }

    public static String getListRole() {
        return listRole;
    }

    public static void setListRole(String listRole) {
        Security.listRole = listRole;
    }

    public static String getCreateRole() {
        return createRole;
    }

    public static void setCreateRole(String createRole) {
        Security.createRole = createRole;
    }

    public static String getUpdateRole() {
        return updateRole;
    }

    public static void setUpdateRole(String updateRole) {
        Security.updateRole = updateRole;
    }

    public static String getDeleteRole() {
        return deleteRole;
    }

    public static void setDeleteRole(String deleteRole) {
        Security.deleteRole = deleteRole;
    }

    public static String getDefaultHashingAlgorithm() {
        return defaultHashingAlgorithm;
    }

    public static void setDefaultHashingAlgorithm(String defaultHashingAlgorithm) {
        Objects.requireNonNull(defaultHashingAlgorithm, "defaultHashingAlgorithm cannot be null");
        Security.defaultHashingAlgorithm = defaultHashingAlgorithm;
    }

    public static boolean authorized(SecurityContext context,boolean requiresAuthentication,  String role) {
        if (isDisableSecurity()) {
            return true;
        }

        if (!requiresAuthentication) {
            return true;
        }

        if (context.getUserPrincipal() == null) {
            return false;
        }

        if (role != null) {
            if (context.isUserInRole(role)) {
                return true;
            }
        }
        return false;
    }

    public static boolean authorized(SecurityContext context, String role) {
        return authorized(context, false, role);
    }

    public static byte[] hash(String text, String algorithm) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            return digest.digest(text.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String hashAsHex(String text, String algorithm) {
        return toHex(hash(text, algorithm));
    }


    public static String toHex(byte[] arg) {
            return String.format("%x", new BigInteger(1, arg));
    }

}
