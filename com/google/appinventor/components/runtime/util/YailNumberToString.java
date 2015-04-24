package com.google.appinventor.components.runtime.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public final class YailNumberToString {

   private static final double BIGBOUND = 1000000.0D;
   static final String LOG_TAG = "YailNumberToString";
   private static final double SMALLBOUND = 1.0E-6D;
   private static final String decPattern = "#####0.0####";
   static DecimalFormat decimalFormat = new DecimalFormat("#####0.0####", symbols);
   static Locale locale = Locale.US;
   static DecimalFormat sciFormat = new DecimalFormat("0.####E0", symbols);
   private static final String sciPattern = "0.####E0";
   static DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);


   public static String format(double var0) {
      if(var0 == Math.rint(var0)) {
         return String.valueOf((int)var0);
      } else {
         double var2 = Math.abs(var0);
         return var2 < 1000000.0D && var2 > 1.0E-6D?decimalFormat.format(var0):sciFormat.format(var0);
      }
   }
}
