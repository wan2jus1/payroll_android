package org.acra.sender;

import android.net.Uri;
import java.util.HashMap;
import java.util.Map;
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

public class HttpPostSender implements ReportSender {

   private final Uri mFormUri;
   private final Map mMapping;


   public HttpPostSender(String var1, Map var2) {
      this.mFormUri = Uri.parse(var1);
      this.mMapping = var2;
   }

   public HttpPostSender(Map var1) {
      this.mFormUri = null;
      this.mMapping = var1;
   }

   private static boolean isNull(String var0) {
      return var0 == null || "ACRA-NULL-STRING".equals(var0);
   }

   private Map remap(Map var1) {
      ReportField[] var3 = ACRA.getConfig().customReportContent();
      ReportField[] var2 = var3;
      if(var3.length == 0) {
         var2 = ACRA.DEFAULT_REPORT_FIELDS;
      }

      HashMap var7 = new HashMap(var1.size());
      int var6 = var2.length;

      for(int var5 = 0; var5 < var6; ++var5) {
         ReportField var4 = var2[var5];
         if(this.mMapping != null && this.mMapping.get(var4) != null) {
            var7.put(this.mMapping.get(var4), var1.get(var4));
         } else {
            var7.put(var4.toString(), var1.get(var4));
         }
      }

      return var7;
   }

   public void send(CrashReportData param1) throws ReportSenderException {
      // $FF: Couldn't be decompiled
   }
}
