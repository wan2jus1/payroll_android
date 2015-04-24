package com.google.appinventor.components.runtime.util;

import android.os.Build.VERSION;

public class SdkLevel {

   public static final int LEVEL_CUPCAKE = 3;
   public static final int LEVEL_DONUT = 4;
   public static final int LEVEL_ECLAIR = 5;
   public static final int LEVEL_ECLAIR_0_1 = 6;
   public static final int LEVEL_ECLAIR_MR1 = 7;
   public static final int LEVEL_FROYO = 8;
   public static final int LEVEL_GINGERBREAD = 9;
   public static final int LEVEL_GINGERBREAD_MR1 = 10;
   public static final int LEVEL_HONEYCOMB = 12;
   public static final int LEVEL_ICE_CREAM_SANDWICH = 14;
   public static final int LEVEL_JELLYBEAN = 16;
   public static final int LEVEL_JELLYBEAN_MR1 = 17;
   public static final int LEVEL_JELLYBEAN_MR2 = 18;


   public static int getLevel() {
      return Integer.parseInt(VERSION.SDK);
   }
}
