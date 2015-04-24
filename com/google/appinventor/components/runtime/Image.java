package com.google.appinventor.components.runtime;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.util.AnimationUtil;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.ViewUtil;
import java.io.IOException;

@DesignerComponent(
   category = ComponentCategory.USERINTERFACE,
   description = "Component for displaying images.  The picture to display, and other aspects of the Image\'s appearance, can be specified in the Designer or in the Blocks Editor.",
   version = 1
)
@SimpleObject
@UsesPermissions(
   permissionNames = "android.permission.INTERNET"
)
public final class Image extends AndroidViewComponent {

   private String picturePath = "";
   private final ImageView view;


   public Image(ComponentContainer var1) {
      super(var1);
      this.view = new ImageView(var1.$context()) {
         public boolean verifyDrawable(Drawable var1) {
            super.verifyDrawable(var1);
            return true;
         }
      };
      var1.$add(this);
      this.view.setFocusable(true);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "This is a limited form of animation that can attach a small number of motion types to images.  The allowable motions are ScrollRightSlow, ScrollRight, ScrollRightFast, ScrollLeftSlow, ScrollLeft, ScrollLeftFast, and Stop"
   )
   public void Animation(String var1) {
      AnimationUtil.ApplyAnimation(this.view, var1);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE
   )
   public String Picture() {
      return this.picturePath;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "asset"
   )
   @SimpleProperty
   public void Picture(String var1) {
      String var2 = var1;
      if(var1 == null) {
         var2 = "";
      }

      this.picturePath = var2;

      BitmapDrawable var4;
      try {
         var4 = MediaUtil.getBitmapDrawable(this.container.$form(), this.picturePath);
      } catch (IOException var3) {
         Log.e("Image", "Unable to load " + this.picturePath);
         var4 = null;
      }

      ViewUtil.setImage(this.view, var4);
   }

   public View getView() {
      return this.view;
   }
}
