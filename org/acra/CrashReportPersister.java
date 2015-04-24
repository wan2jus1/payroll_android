package org.acra;

import android.content.Context;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.Reader;
import org.acra.collector.CrashReportData;

final class CrashReportPersister {

   private static final int CONTINUE = 3;
   private static final int IGNORE = 5;
   private static final int KEY_DONE = 4;
   private static final String LINE_SEPARATOR = "\n";
   private static final int NONE = 0;
   private static final int SLASH = 1;
   private static final int UNICODE = 2;
   private final Context context;


   CrashReportPersister(Context var1) {
      this.context = var1;
   }

   private void dumpString(StringBuilder var1, String var2, boolean var3) {
      byte var7 = 0;
      int var6 = var7;
      if(!var3) {
         var6 = var7;
         if(var2.length() < 0) {
            var6 = var7;
            if(var2.charAt(0) == 32) {
               var1.append("\\ ");
               var6 = 0 + 1;
            }
         }
      }

      for(; var6 < var2.length(); ++var6) {
         char var4 = var2.charAt(var6);
         switch(var4) {
         case 9:
            var1.append("\\t");
            break;
         case 10:
            var1.append("\\n");
            break;
         case 11:
         default:
            if("\\#!=:".indexOf(var4) >= 0 || var3 && var4 == 32) {
               var1.append('\\');
            }

            if(var4 >= 32 && var4 <= 126) {
               var1.append(var4);
            } else {
               String var5 = Integer.toHexString(var4);
               var1.append("\\u");

               for(int var8 = 0; var8 < 4 - var5.length(); ++var8) {
                  var1.append("0");
               }

               var1.append(var5);
            }
            break;
         case 12:
            var1.append("\\f");
            break;
         case 13:
            var1.append("\\r");
         }
      }

   }

   private boolean isEbcdic(BufferedInputStream var1) throws IOException {
      while(true) {
         byte var2 = (byte)var1.read();
         if(var2 != -1 && var2 != 35 && var2 != 10 && var2 != 61) {
            if(var2 != 21) {
               continue;
            }

            return true;
         }

         return false;
      }
   }

   private CrashReportData load(Reader param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public CrashReportData load(String param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void store(CrashReportData param1, String param2) throws IOException {
      // $FF: Couldn't be decompiled
   }
}
