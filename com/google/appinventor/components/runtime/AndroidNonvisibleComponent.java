package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;

@SimpleObject
public abstract class AndroidNonvisibleComponent implements Component {

   protected final Form form;


   protected AndroidNonvisibleComponent(Form var1) {
      this.form = var1;
   }

   public HandlesEventDispatching getDispatchDelegate() {
      return this.form;
   }
}
