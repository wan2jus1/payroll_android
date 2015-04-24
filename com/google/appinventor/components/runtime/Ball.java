package com.google.appinventor.components.runtime;

import android.graphics.Paint;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Sprite;
import com.google.appinventor.components.runtime.util.PaintUtil;

@DesignerComponent(
   category = ComponentCategory.ANIMATION,
   description = "<p>A round \'sprite\' that can be placed on a <code>Canvas</code>, where it can react to touches and drags, interact with other sprites (<code>ImageSprite</code>s and other <code>Ball</code>s) and the edge of the Canvas, and move according to its property values.</p><p>For example, to have a <code>Ball</code> move 4 pixels toward the top of a <code>Canvas</code> every 500 milliseconds (half second), you would set the <code>Speed</code> property to 4 [pixels], the <code>Interval</code> property to 500 [milliseconds], the <code>Heading</code> property to 90 [degrees], and the <code>Enabled</code> property to <code>True</code>.  These and its other properties can be changed at any time.</p><p>The difference between a Ball and an <code>ImageSprite</code> is that the latter can get its appearance from an image file, while a Ball\'s appearance can only be changed by varying its <code>PaintColor</code> and <code>Radius</code> properties.</p>",
   version = 5
)
@SimpleObject
public final class Ball extends Sprite {

   static final int DEFAULT_RADIUS = 5;
   private Paint paint = new Paint();
   private int paintColor;
   private int radius;


   public Ball(ComponentContainer var1) {
      super(var1);
      this.PaintColor(-16777216);
      this.Radius(5);
   }

   public int Height() {
      return this.radius * 2;
   }

   public void Height(int var1) {
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE
   )
   public int PaintColor() {
      return this.paintColor;
   }

   @DesignerProperty(
      defaultValue = "&HFF000000",
      editorType = "color"
   )
   @SimpleProperty
   public void PaintColor(int var1) {
      this.paintColor = var1;
      if(var1 != 0) {
         PaintUtil.changePaint(this.paint, var1);
      } else {
         PaintUtil.changePaint(this.paint, -16777216);
      }

      this.registerChange();
   }

   @SimpleProperty
   public int Radius() {
      return this.radius;
   }

   @DesignerProperty(
      defaultValue = "5",
      editorType = "non_negative_integer"
   )
   @SimpleProperty(
      category = PropertyCategory.APPEARANCE
   )
   public void Radius(int var1) {
      this.radius = var1;
      this.registerChange();
   }

   public int Width() {
      return this.radius * 2;
   }

   public void Width(int var1) {
   }

   public boolean containsPoint(double var1, double var3) {
      double var5 = this.xLeft + (double)this.radius;
      double var7 = this.yTop + (double)this.radius;
      return (var1 - var5) * (var1 - var5) + (var3 - var7) * (var3 - var7) <= (double)(this.radius * this.radius);
   }

   protected void onDraw(android.graphics.Canvas var1) {
      if(this.visible) {
         var1.drawCircle((float)this.xLeft + (float)this.radius, (float)this.yTop + (float)this.radius, (float)this.radius, this.paint);
      }

   }
}
