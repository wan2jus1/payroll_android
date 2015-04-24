package com.google.appinventor.components.runtime;

import android.content.Context;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout.LayoutParams;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.Layout;

@SimpleObject
public final class LinearLayout implements Layout {

   private final android.widget.LinearLayout layoutManager;


   LinearLayout(Context var1, int var2) {
      this(var1, var2, (Integer)null, (Integer)null);
   }

   LinearLayout(final Context var1, int var2, final Integer var3, final Integer var4) {
      if((var3 != null || var4 == null) && (var3 == null || var4 != null)) {
         this.layoutManager = new android.widget.LinearLayout(var1) {
            private int getSize(int var1, int var2) {
               int var4x = MeasureSpec.getMode(var1);
               int var3x = MeasureSpec.getSize(var1);
               if(var4x == 1073741824) {
                  var1 = var3x;
               } else {
                  var1 = var2;
                  if(var4x == Integer.MIN_VALUE) {
                     return Math.min(var2, var3x);
                  }
               }

               return var1;
            }
            protected void onMeasure(int var1, int var2) {
               if(var3 != null && var4 != null) {
                  if(this.getChildCount() != 0) {
                     super.onMeasure(var1, var2);
                  } else {
                     this.setMeasuredDimension(this.getSize(var1, var3.intValue()), this.getSize(var2, var4.intValue()));
                  }
               } else {
                  super.onMeasure(var1, var2);
               }
            }
         };
         android.widget.LinearLayout var5 = this.layoutManager;
         byte var6;
         if(var2 == 0) {
            var6 = 0;
         } else {
            var6 = 1;
         }

         var5.setOrientation(var6);
      } else {
         throw new IllegalArgumentException("LinearLayout - preferredEmptyWidth and preferredEmptyHeight must be either both null or both not null");
      }
   }

   public void add(AndroidViewComponent var1) {
      this.layoutManager.addView(var1.getView(), new LayoutParams(-2, -2, 0.0F));
   }

   public ViewGroup getLayoutManager() {
      return this.layoutManager;
   }

   public void setHorizontalGravity(int var1) {
      this.layoutManager.setHorizontalGravity(var1);
   }

   public void setVerticalGravity(int var1) {
      this.layoutManager.setVerticalGravity(var1);
   }
}
