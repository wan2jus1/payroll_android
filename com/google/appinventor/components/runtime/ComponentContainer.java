package com.google.appinventor.components.runtime;

import android.app.Activity;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.Form;

public interface ComponentContainer {

   void $add(AndroidViewComponent var1);

   Activity $context();

   Form $form();

   void setChildHeight(AndroidViewComponent var1, int var2);

   void setChildWidth(AndroidViewComponent var1, int var2);
}
