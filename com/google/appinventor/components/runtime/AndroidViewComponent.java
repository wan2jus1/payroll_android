package com.google.appinventor.components.runtime;

import android.view.View;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.SimplePropertyCopier;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.VisibleComponent;

@SimpleObject
public abstract class AndroidViewComponent extends VisibleComponent {

   private int column = -1;
   protected final ComponentContainer container;
   private int lastSetHeight = -3;
   private int lastSetWidth = -3;
   private int row = -1;


   protected AndroidViewComponent(ComponentContainer var1) {
      this.container = var1;
   }

   @SimpleProperty(
      userVisible = false
   )
   public int Column() {
      return this.column;
   }

   @SimpleProperty(
      userVisible = false
   )
   public void Column(int var1) {
      this.column = var1;
   }

   @SimplePropertyCopier
   public void CopyHeight(AndroidViewComponent var1) {
      this.Height(var1.lastSetHeight);
   }

   @SimplePropertyCopier
   public void CopyWidth(AndroidViewComponent var1) {
      this.Width(var1.lastSetWidth);
   }

   @SimpleProperty
   public int Height() {
      return this.getView().getHeight();
   }

   @SimpleProperty
   public void Height(int var1) {
      this.container.setChildHeight(this, var1);
      this.lastSetHeight = var1;
   }

   @SimpleProperty(
      userVisible = false
   )
   public int Row() {
      return this.row;
   }

   @SimpleProperty(
      userVisible = false
   )
   public void Row(int var1) {
      this.row = var1;
   }

   @DesignerProperty(
      defaultValue = "True",
      editorType = "visibility"
   )
   @SimpleProperty(
      description = "Specifies whether the component should be visible on the screen. Value is true if the component is showing and false if hidden."
   )
   public void Visible(Boolean var1) {
      View var2 = this.getView();
      byte var3;
      if(var1.booleanValue()) {
         var3 = 0;
      } else {
         var3 = 8;
      }

      var2.setVisibility(var3);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE
   )
   public boolean Visible() {
      return this.getView().getVisibility() == 0;
   }

   @SimpleProperty
   public int Width() {
      return this.getView().getWidth();
   }

   @SimpleProperty
   public void Width(int var1) {
      this.container.setChildWidth(this, var1);
      this.lastSetWidth = var1;
   }

   public HandlesEventDispatching getDispatchDelegate() {
      return this.container.$form();
   }

   public abstract View getView();
}
