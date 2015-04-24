package org.acra.sender;

import android.content.Context;
import android.content.Intent;
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

public class EmailIntentSender implements ReportSender {

   private final Context mContext;


   public EmailIntentSender(Context var1) {
      this.mContext = var1;
   }

   private String buildBody(CrashReportData var1) {
      ReportField[] var3 = ACRA.getConfig().customReportContent();
      ReportField[] var2 = var3;
      if(var3.length == 0) {
         var2 = ACRA.DEFAULT_MAIL_REPORT_FIELDS;
      }

      StringBuilder var7 = new StringBuilder();
      int var6 = var2.length;

      for(int var5 = 0; var5 < var6; ++var5) {
         ReportField var4 = var2[var5];
         var7.append(var4.toString()).append("=");
         var7.append((String)var1.get(var4));
         var7.append('\n');
      }

      return var7.toString();
   }

   public void send(CrashReportData var1) throws ReportSenderException {
      String var2 = this.mContext.getPackageName() + " Crash Report";
      String var4 = this.buildBody(var1);
      Intent var3 = new Intent("android.intent.action.SEND");
      var3.addFlags(268435456);
      var3.setType("text/plain");
      var3.putExtra("android.intent.extra.SUBJECT", var2);
      var3.putExtra("android.intent.extra.TEXT", var4);
      var3.putExtra("android.intent.extra.EMAIL", new String[]{ACRA.getConfig().mailTo()});
      this.mContext.startActivity(var3);
   }
}
