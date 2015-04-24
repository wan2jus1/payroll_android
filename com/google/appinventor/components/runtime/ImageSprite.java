package com.google.appinventor.components.runtime;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.Sprite;
import com.google.appinventor.components.runtime.util.MediaUtil;
import java.io.IOException;

@DesignerComponent(
   category = ComponentCategory.ANIMATION,
   description = "<p>A \'sprite\' that can be placed on a <code>Canvas</code>, where it can react to touches and drags, interact with other sprites (<code>Ball</code>s and other <code>ImageSprite</code>s) and the edge of the Canvas, and move according to its property values.  Its appearance is that of the image specified in its <code>Picture</code> property (unless its <code>Visible</code> property is <code>False</code>.</p> <p>To have an <code>ImageSprite</code> move 10 pixels to the left every 1000 milliseconds (one second), for example, you would set the <code>Speed</code> property to 10 [pixels], the <code>Interval</code> property to 1000 [milliseconds], the <code>Heading</code> property to 180 [degrees], and the <code>Enabled</code> property to <code>True</code>.  A sprite whose <code>Rotates</code> property is <code>True</code> will rotate its image as the sprite\'s <code>Heading</code> changes.  Checking for collisions with a rotated sprite currently checks the sprite\'s unrotated position so that collision checking will be inaccurate for tall narrow or short wide sprites that are rotated.  Any of the sprite properties can be changed at any time under program control.</p> ",
   version = 6
)
@SimpleObject
@UsesPermissions(
   permissionNames = "android.permission.INTERNET"
)
public class ImageSprite extends Sprite {

   private double cachedRotationHeading;
   private BitmapDrawable drawable;
   private final Form form;
   private int heightHint = -1;
   private Matrix mat;
   private String picturePath = "";
   private Bitmap rotatedBitmap;
   private BitmapDrawable rotatedDrawable;
   private boolean rotates;
   private boolean rotationCached;
   private Bitmap scaledBitmap;
   private Bitmap unrotatedBitmap;
   private int widthHint = -1;


   public ImageSprite(ComponentContainer var1) {
      super(var1);
      this.form = var1.$form();
      this.mat = new Matrix();
      this.rotates = true;
      this.rotationCached = false;
   }

   @SimpleProperty
   public int Height() {
      return this.heightHint != -1 && this.heightHint != -2?this.heightHint:(this.drawable == null?0:this.drawable.getBitmap().getHeight());
   }

   @SimpleProperty
   public void Height(int var1) {
      this.heightHint = var1;
      this.registerChange();
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "The picture that determines the sprite\'s appearence"
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

      try {
         this.drawable = MediaUtil.getBitmapDrawable(this.form, this.picturePath);
      } catch (IOException var3) {
         Log.e("ImageSprite", "Unable to load " + this.picturePath);
         this.drawable = null;
      }

      if(this.drawable != null) {
         this.unrotatedBitmap = this.drawable.getBitmap();
      } else {
         this.unrotatedBitmap = null;
      }

      this.registerChange();
   }

   @DesignerProperty(
      defaultValue = "True",
      editorType = "boolean"
   )
   @SimpleProperty
   public void Rotates(boolean var1) {
      this.rotates = var1;
      this.registerChange();
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "If true, the sprite image rotates to match the sprite\'s heading. If false, the sprite image does not rotate when the sprite changes heading. The sprite rotates around its centerpoint."
   )
   public boolean Rotates() {
      return this.rotates;
   }

   @SimpleProperty
   public int Width() {
      return this.widthHint != -1 && this.widthHint != -2?this.widthHint:(this.drawable == null?0:this.drawable.getBitmap().getWidth());
   }

   @SimpleProperty
   public void Width(int var1) {
      this.widthHint = var1;
      this.registerChange();
   }

   public void onDraw(android.graphics.Canvas var1) {
      if(this.unrotatedBitmap != null && this.visible) {
         int var2 = (int)Math.round(this.xLeft);
         int var3 = (int)Math.round(this.yTop);
         int var4 = this.Width();
         int var5 = this.Height();
         if(this.rotates) {
            if(!this.rotationCached || this.cachedRotationHeading != this.Heading()) {
               this.mat.setRotate((float)(-this.Heading()), (float)(var4 / 2), (float)(var5 / 2));
               if(var4 == this.unrotatedBitmap.getWidth() && var5 == this.unrotatedBitmap.getHeight()) {
                  this.scaledBitmap = this.unrotatedBitmap;
               } else {
                  this.scaledBitmap = Bitmap.createScaledBitmap(this.unrotatedBitmap, var4, var5, true);
               }

               this.rotatedBitmap = Bitmap.createBitmap(this.scaledBitmap, 0, 0, this.scaledBitmap.getWidth(), this.scaledBitmap.getHeight(), this.mat, true);
               this.rotatedDrawable = new BitmapDrawable(this.rotatedBitmap);
               this.cachedRotationHeading = this.Heading();
            }

            this.rotatedDrawable.setBounds(var4 / 2 + var2 - this.rotatedBitmap.getWidth() / 2, var5 / 2 + var3 - this.rotatedBitmap.getHeight() / 2, var4 / 2 + var2 + this.rotatedBitmap.getWidth() / 2, var5 / 2 + var3 + this.rotatedBitmap.getHeight() / 2);
            this.rotatedDrawable.draw(var1);
            return;
         }

         this.drawable.setBounds(var2, var3, var2 + var4, var3 + var5);
         this.drawable.draw(var1);
      }

   }
}
