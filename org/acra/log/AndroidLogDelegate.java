package org.acra.log;

import android.util.Log;
import org.acra.log.ACRALog;

public final class AndroidLogDelegate implements ACRALog {

   public int d(String var1, String var2) {
      return Log.d(var1, var2);
   }

   public int d(String var1, String var2, Throwable var3) {
      return Log.d(var1, var2, var3);
   }

   public int e(String var1, String var2) {
      return Log.e(var1, var2);
   }

   public int e(String var1, String var2, Throwable var3) {
      return Log.e(var1, var2, var3);
   }

   public String getStackTraceString(Throwable var1) {
      return Log.getStackTraceString(var1);
   }

   public int i(String var1, String var2) {
      return Log.i(var1, var2);
   }

   public int i(String var1, String var2, Throwable var3) {
      return Log.i(var1, var2, var3);
   }

   public int v(String var1, String var2) {
      return Log.v(var1, var2);
   }

   public int v(String var1, String var2, Throwable var3) {
      return Log.v(var1, var2, var3);
   }

   public int w(String var1, String var2) {
      return Log.w(var1, var2);
   }

   public int w(String var1, String var2, Throwable var3) {
      return Log.w(var1, var2, var3);
   }

   public int w(String var1, Throwable var2) {
      return Log.w(var1, var2);
   }
}
