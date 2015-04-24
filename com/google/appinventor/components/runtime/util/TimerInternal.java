package com.google.appinventor.components.runtime.util;

import android.os.Handler;
import com.google.appinventor.components.runtime.AlarmHandler;

public final class TimerInternal implements Runnable {

   private AlarmHandler component;
   private boolean enabled;
   private Handler handler;
   private int interval;


   public TimerInternal(AlarmHandler var1, boolean var2, int var3) {
      this(var1, var2, var3, new Handler());
   }

   public TimerInternal(AlarmHandler var1, boolean var2, int var3, Handler var4) {
      this.handler = var4;
      this.component = var1;
      this.enabled = var2;
      this.interval = var3;
      if(var2) {
         var4.postDelayed(this, (long)var3);
      }

   }

   public void Enabled(boolean var1) {
      if(this.enabled) {
         this.handler.removeCallbacks(this);
      }

      this.enabled = var1;
      if(var1) {
         this.handler.postDelayed(this, (long)this.interval);
      }

   }

   public boolean Enabled() {
      return this.enabled;
   }

   public int Interval() {
      return this.interval;
   }

   public void Interval(int var1) {
      this.interval = var1;
      if(this.enabled) {
         this.handler.removeCallbacks(this);
         this.handler.postDelayed(this, (long)var1);
      }

   }

   public void run() {
      if(this.enabled) {
         this.component.alarm();
         if(this.enabled) {
            this.handler.postDelayed(this, (long)this.interval);
         }
      }

   }
}
