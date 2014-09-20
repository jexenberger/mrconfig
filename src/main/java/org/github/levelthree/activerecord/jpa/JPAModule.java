package org.github.levelthree.activerecord.jpa;

import org.github.levelthree.Module;
import org.github.levelthree.activerecord.ProviderFactory;
import org.github.levelthree.ux.form.FieldHelpers;

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
