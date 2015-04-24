package com.google.appinventor.components.runtime.collect;

import java.util.ArrayList;
import java.util.Collections;

public class Lists {

   public static ArrayList newArrayList() {
      return new ArrayList();
   }

   public static ArrayList newArrayList(Object ... var0) {
      ArrayList var1 = new ArrayList(var0.length * 110 / 100 + 5);
      Collections.addAll(var1, var0);
      return var1;
   }
}
