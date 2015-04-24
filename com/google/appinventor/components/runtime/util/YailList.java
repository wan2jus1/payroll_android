package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.YailConstants;
import com.google.appinventor.components.runtime.util.YailNumberToString;
import gnu.lists.LList;
import gnu.lists.Pair;
import java.util.Collection;
import java.util.List;

public class YailList extends Pair {

   private static final String LOG_TAG = "YailList";


   public YailList() {
      super(YailConstants.YAIL_HEADER, LList.Empty);
   }

   private YailList(Object var1) {
      super(YailConstants.YAIL_HEADER, var1);
   }

   public static String YailListElementToString(Object var0) {
      return Number.class.isInstance(var0)?YailNumberToString.format(((Number)var0).doubleValue()):String.valueOf(var0);
   }

   public static YailList makeEmptyList() {
      return new YailList();
   }

   public static YailList makeList(Collection var0) {
      return new YailList(Pair.makeList(var0.toArray(), 0));
   }

   public static YailList makeList(List var0) {
      return new YailList(Pair.makeList(var0));
   }

   public static YailList makeList(Object[] var0) {
      return new YailList(Pair.makeList(var0, 0));
   }

   public Object getObject(int var1) {
      return this.get(var1 + 1);
   }

   public String getString(int var1) {
      return this.get(var1 + 1).toString();
   }

   public int size() {
      return super.size() - 1;
   }

   public Object[] toArray() {
      if(this.cdr instanceof Pair) {
         return ((Pair)this.cdr).toArray();
      } else if(this.cdr instanceof LList) {
         return ((LList)this.cdr).toArray();
      } else {
         throw new YailRuntimeError("YailList cannot be represented as an array", "YailList Error.");
      }
   }

   public String toJSONString() {
      // $FF: Couldn't be decompiled
   }

   public String toString() {
      if(this.cdr instanceof Pair) {
         return ((Pair)this.cdr).toString();
      } else if(this.cdr instanceof LList) {
         return ((LList)this.cdr).toString();
      } else {
         throw new RuntimeException("YailList cannot be represented as a String");
      }
   }

   public String[] toStringArray() {
      int var3 = this.size();
      String[] var1 = new String[var3];

      for(int var2 = 1; var2 <= var3; ++var2) {
         var1[var2 - 1] = YailListElementToString(this.get(var2));
      }

      return var1;
   }
}
