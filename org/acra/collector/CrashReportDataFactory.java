package org.acra.collector;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.Time;
import android.util.Log;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.acra.ACRA;
import org.acra.ACRAConfiguration;
import org.acra.ReportField;
import org.acra.collector.CrashReportData;

public final class CrashReportDataFactory {

   private final Time appStartDate;
   private final Context context;
   private final List crashReportFields;
   private final Map customParameters = new HashMap();
   private final String initialConfiguration;
   private final SharedPreferences prefs;


   public CrashReportDataFactory(Context var1, SharedPreferences var2, Time var3, String var4) {
      this.context = var1;
      this.prefs = var2;
      this.appStartDate = var3;
      this.initialConfiguration = var4;
      ACRAConfiguration var6 = ACRA.getConfig();
      ReportField[] var5 = var6.customReportContent();
      if(var5.length != 0) {
         Log.d(ACRA.LOG_TAG, "Using custom Report Fields");
      } else if(var6.mailTo() != null && !"".equals(var6.mailTo())) {
         Log.d(ACRA.LOG_TAG, "Using default Mail Report Fields");
         var5 = ACRA.DEFAULT_MAIL_REPORT_FIELDS;
      } else {
         Log.d(ACRA.LOG_TAG, "Using default Report Fields");
         var5 = ACRA.DEFAULT_REPORT_FIELDS;
      }

      this.crashReportFields = Arrays.asList(var5);
   }

   private String createCustomInfoString() {
      StringBuilder var1 = new StringBuilder();
      Iterator var2 = this.customParameters.keySet().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         String var4 = (String)this.customParameters.get(var3);
         var1.append(var3);
         var1.append(" = ");
         var1.append(var4);
         var1.append("\n");
      }

      return var1.toString();
   }

   private String getStackTrace(Throwable var1) {
      StringWriter var3 = new StringWriter();

      PrintWriter var2;
      for(var2 = new PrintWriter(var3); var1 != null; var1 = var1.getCause()) {
         var1.printStackTrace(var2);
      }

      String var4 = var3.toString();
      var2.close();
      return var4;
   }

   public CrashReportData createCrashData(Throwable param1, boolean param2, Thread param3) {
      // $FF: Couldn't be decompiled
   }

   public String getCustomData(String var1) {
      return (String)this.customParameters.get(var1);
   }

   public String putCustomData(String var1, String var2) {
      return (String)this.customParameters.put(var1, var2);
   }

   public String removeCustomData(String var1) {
      return (String)this.customParameters.remove(var1);
   }
}
