package org.github.levelthree.activerecord;

import java.io.Serializable;

/**
 * Created by julian3 on 2014/07/18.
 */
public interface DeActivatableActiveRecord<T  extends ActiveRecord,K extends Serializable> extends ActiveRecord<T,K> {

   void deactivate();

   boolean isActive();

   default T delete() {
        deactivate();
        return (T) this;
   }
}
