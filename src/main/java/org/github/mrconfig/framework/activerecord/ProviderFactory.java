package org.github.mrconfig.framework.activerecord;

import static java.util.Objects.requireNonNull;

/**
 * Created by w1428134 on 2014/07/11.
 */
public class ProviderFactory {

    private static Provider PROVIDER = null;

    public static Provider getProvider() {
        if (PROVIDER == null) {
            PROVIDER = new JPAProvider();
        }
        return PROVIDER;
    }

    public static synchronized void setProvider(Provider provider) {
        requireNonNull(provider, "provider must be passed");
        PROVIDER = provider;
    }



}
