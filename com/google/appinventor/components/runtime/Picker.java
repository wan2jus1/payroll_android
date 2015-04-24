package com.google.appinventor.components.runtime;

import android.content.Intent;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.runtime.ActivityResultListener;
import com.google.appinventor.components.runtime.ButtonBase;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.util.AnimationUtil;

@SimpleObject
public abstract class Picker extends ButtonBase implements ActivityResultListener {

   protected final ComponentContainer container;
   protected int requestCode;


   public Picker(ComponentContainer var1) {
      super(var1);
      this.container = var1;
   }

   @SimpleEvent
   public void AfterPicking() {
      EventDispatcher.dispatchEvent(this, "AfterPicking", new Object[0]);
   }

   @SimpleEvent
   public void BeforePicking() {
      EventDispatcher.dispatchEvent(this, "BeforePicking", new Object[0]);
   }

   @SimpleFunction(
      description = "Opens the picker, as though the user clicked on it."
   )
   public void Open() {
      this.click();
   }

   public void click() {
      this.BeforePicking();
      if(this.requestCode == 0) {
         this.requestCode = this.container.$form().registerForActivityResult(this);
      }

      this.container.$context().startActivityForResult(this.getIntent(), this.requestCode);
      String var1 = this.container.$form().getOpenAnimType();
      AnimationUtil.ApplyOpenScreenAnimation(this.container.$context(), var1);
   }

   protected abstract Intent getIntent();
}
