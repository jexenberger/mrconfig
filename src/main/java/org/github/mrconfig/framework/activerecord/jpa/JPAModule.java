package org.github.mrconfig.framework.activerecord.jpa;

import org.github.mrconfig.framework.Module;
import org.github.mrconfig.framework.activerecord.ProviderFactory;
import org.github.mrconfig.framework.ux.form.FieldHelpers;

/**
 * Created by julian3 on 2014/08/17.
 */
public class JPAModule extends Module {

    public JPAModule(String persistenceUnit) {
        JPAProvider.setPersistenceUnit(persistenceUnit);
    }

    @Override
    public void init() {

        ProviderFactory.setProvider(new JPAProvider());
        FieldHelpers.add(new JPAFieldHelper());
    }
}
