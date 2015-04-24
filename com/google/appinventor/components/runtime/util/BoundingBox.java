package com.google.appinventor.components.runtime.util;


public final class BoundingBox {

   private double bottom;
   private double left;
   private double right;
   private double top;


   public BoundingBox(double var1, double var3, double var5, double var7) {
      this.left = var1;
      this.top = var3;
      this.right = var5;
      this.bottom = var7;
   }

   public double getBottom() {
      return this.bottom;
   }

   public double getLeft() {
      return this.left;
   }

   public double getRight() {
      return this.right;
   }

   public double getTop() {
      return this.top;
   }

   public boolean intersectDestructively(BoundingBox var1) {
      double var2 = Math.max(this.left, var1.left);
      double var4 = Math.min(this.right, var1.right);
      double var6 = Math.max(this.top, var1.top);
      double var8 = Math.min(this.bottom, var1.bottom);
      if(var2 <= var4 && var6 <= var8) {
         this.left = var2;
         this.right = var4;
         this.top = var6;
         this.bottom = var8;
         return true;
      } else {
         return false;
      }
   }

   public String toString() {
      return "<BoundingBox (left = " + this.left + ", top = " + this.top + ", right = " + this.right + ", bottom = " + this.bottom + ">";
   }
}
