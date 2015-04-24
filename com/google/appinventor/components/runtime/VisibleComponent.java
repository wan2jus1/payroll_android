package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.runtime.Component;

@SimpleObject
public abstract class VisibleComponent implements Component {

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE
   )
   public abstract int Height();

   @SimpleProperty
   public abstract void Height(int var1);

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE
   )
   public abstract int Width();

   @SimpleProperty
   public abstract void Width(int var1);
}
