package org.acra.log;


public interface ACRALog {

   int d(String var1, String var2);

   int d(String var1, String var2, Throwable var3);

   int e(String var1, String var2);

   int e(String var1, String var2, Throwable var3);

   String getStackTraceString(Throwable var1);

   int i(String var1, String var2);

   int i(String var1, String var2, Throwable var3);

   int v(String var1, String var2);

   int v(String var1, String var2, Throwable var3);

   int w(String var1, String var2);

   int w(String var1, String var2, Throwable var3);

   int w(String var1, Throwable var2);
}
