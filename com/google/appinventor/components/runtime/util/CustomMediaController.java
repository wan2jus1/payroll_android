package com.google.appinventor.components.runtime.util;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.MediaController;

public class CustomMediaController extends MediaController implements OnTouchListener {

   private View mAnchorView;
   private int mShowTime = 3000;


   public CustomMediaController(Context var1) {
      super(var1);
   }

   public boolean addTo(ViewGroup var1, LayoutParams var2) {
      ViewParent var3 = this.getParent();
      if(var3 != null && var3 instanceof ViewGroup) {
         ((ViewGroup)var3).removeView(this);
         var1.addView(this, var2);
         return true;
      } else {
         Log.e("CustomMediaController.addTo", "MediaController not available in fullscreen.");
         return false;
      }
   }

   public void hide() {
      super.hide();
      this.setVisibility(4);
   }

   public boolean onTouch(View var1, MotionEvent var2) {
      if(var1 == this.mAnchorView) {
         this.show(this.mShowTime);
      }

      return false;
   }

   public void setAnchorView(View var1) {
      this.mAnchorView = var1;
      this.mAnchorView.setOnTouchListener(this);
      super.setAnchorView(var1);
   }

   public void show() {
      this.setVisibility(0);
      super.show();
   }

   public void show(int var1) {
      this.setVisibility(0);
      super.show(var1);
   }
}
