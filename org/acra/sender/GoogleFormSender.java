package org.acra.sender;

import android.net.Uri;
import android.util.Log;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;
import org.acra.util.HttpRequest;

public class GoogleFormSender implements ReportSender {

   private final Uri mFormUri;


   public GoogleFormSender() {
      this.mFormUri = null;
   }

   public GoogleFormSender(String var1) {
      this.mFormUri = Uri.parse(String.format(ACRA.getConfig().googleFormUrlFormat(), new Object[]{var1}));
   }

   private Map remap(Map var1) {
      ReportField[] var3 = ACRA.getConfig().customReportContent();
      ReportField[] var2 = var3;
      if(var3.length == 0) {
         var2 = ACRA.DEFAULT_REPORT_FIELDS;
      }

      int var6 = 0;
      HashMap var8 = new HashMap();
      int var7 = var2.length;

      for(int var5 = 0; var5 < var7; ++var5) {
         ReportField var4 = var2[var5];
         switch(null.$SwitchMap$org$acra$ReportField[var4.ordinal()]) {
         case 1:
            var8.put("entry." + var6 + ".single", "\'" + (String)var1.get(var4));
            break;
         case 2:
            var8.put("entry." + var6 + ".single", "\'" + (String)var1.get(var4));
            break;
         default:
            var8.put("entry." + var6 + ".single", var1.get(var4));
         }

         ++var6;
      }

      return var8;
   }

   public void send(CrashReportData var1) throws ReportSenderException {
      Uri var2;
      if(this.mFormUri == null) {
         var2 = Uri.parse(String.format(ACRA.getConfig().googleFormUrlFormat(), new Object[]{ACRA.getConfig().formKey()}));
      } else {
         var2 = this.mFormUri;
      }

      Map var3 = this.remap(var1);
      var3.put("pageNumber", "0");
      var3.put("backupCache", "");
      var3.put("submit", "Envoyer");

      try {
         URL var6 = new URL(var2.toString());
         Log.d(ACRA.LOG_TAG, "Sending report " + (String)var1.get(ReportField.REPORT_ID));
         Log.d(ACRA.LOG_TAG, "Connect to " + var6);
         HttpRequest var5 = new HttpRequest();
         var5.setConnectionTimeOut(ACRA.getConfig().connectionTimeout());
         var5.setSocketTimeOut(ACRA.getConfig().socketTimeout());
         var5.setMaxNrRetries(ACRA.getConfig().maxNumberOfRequestRetries());
         var5.sendPost(var6, var3);
      } catch (IOException var4) {
         throw new ReportSenderException("Error while sending report to Google Form.", var4);
      }
   }
}
