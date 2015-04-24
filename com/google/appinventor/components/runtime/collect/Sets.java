package com.google.appinventor.components.runtime.collect;

import java.util.Collections;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

public class Sets {

   public static HashSet newHashSet() {
      return new HashSet();
   }

   public static HashSet newHashSet(Object ... var0) {
      HashSet var1 = new HashSet(var0.length * 4 / 3 + 1);
      Collections.addAll(var1, var0);
      return var1;
   }

   public static SortedSet newSortedSet(Object ... var0) {
      TreeSet var1 = new TreeSet();
      Collections.addAll(var1, var0);
      return var1;
   }
}
