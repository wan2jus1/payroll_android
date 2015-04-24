package org.acra;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.acra.ACRA;
import org.acra.CrashReportFileNameParser;
import org.acra.CrashReportFinder;
import org.acra.CrashReportPersister;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

final class SendWorker extends Thread {

   private final boolean approvePendingReports;
   private final Context context;
   private final CrashReportFileNameParser fileNameParser = new CrashReportFileNameParser();
   private final List reportSenders;
   private final boolean sendOnlySilentReports;


   public SendWorker(Context var1, List var2, boolean var3, boolean var4) {
      this.context = var1;
      this.reportSenders = var2;
      this.sendOnlySilentReports = var3;
      this.approvePendingReports = var4;
   }

   private void approvePendingReports() {
      Log.d(ACRA.LOG_TAG, "Mark all pending reports as approved.");
      String[] var1 = (new CrashReportFinder(this.context)).getCrashReportFiles();
      int var5 = var1.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         String var3 = var1[var4];
         if(!this.fileNameParser.isApproved(var3)) {
            File var2 = new File(this.context.getFilesDir(), var3);
            var3 = var3.replace(".stacktrace", "-approved.stacktrace");
            File var6 = new File(this.context.getFilesDir(), var3);
            if(!var2.renameTo(var6)) {
               Log.e(ACRA.LOG_TAG, "Could not rename approved report from " + var2 + " to " + var6);
            }
         }
      }

   }

   private void checkAndSendReports(Context var1, boolean var2) {
      Log.d(ACRA.LOG_TAG, "#checkAndSendReports - start");
      String[] var4 = (new CrashReportFinder(var1)).getCrashReportFiles();
      Arrays.sort(var4);
      int var6 = 0;
      int var7 = var4.length;

      for(int var5 = 0; var5 < var7; ++var5) {
         String var3 = var4[var5];
         if(!var2 || this.fileNameParser.isSilent(var3)) {
            if(var6 >= 5) {
               break;
            }

            Log.i(ACRA.LOG_TAG, "Sending file " + var3);

            try {
               this.sendCrashReport((new CrashReportPersister(var1)).load((String)var3));
               this.deleteFile(var1, var3);
            } catch (RuntimeException var8) {
               Log.e(ACRA.LOG_TAG, "Failed to send crash reports for " + var3, var8);
               this.deleteFile(var1, var3);
               break;
            } catch (IOException var9) {
               Log.e(ACRA.LOG_TAG, "Failed to load crash report for " + var3, var9);
               this.deleteFile(var1, var3);
               break;
            } catch (ReportSenderException var10) {
               Log.e(ACRA.LOG_TAG, "Failed to send crash report for " + var3, var10);
               break;
            }

            ++var6;
         }
      }

      Log.d(ACRA.LOG_TAG, "#checkAndSendReports - finish");
   }

   private void deleteFile(Context var1, String var2) {
      if(!var1.deleteFile(var2)) {
         Log.w(ACRA.LOG_TAG, "Could not delete error report : " + var2);
      }

   }

   private void sendCrashReport(CrashReportData var1) throws ReportSenderException {
      if(!ACRA.isDebuggable() || ACRA.getConfig().sendReportsInDevMode()) {
         boolean var5 = false;
         Iterator var2 = this.reportSenders.iterator();

         while(var2.hasNext()) {
            ReportSender var3 = (ReportSender)var2.next();

            try {
               var3.send(var1);
            } catch (ReportSenderException var6) {
               if(!var5) {
                  throw var6;
               }

               Log.w(ACRA.LOG_TAG, "ReportSender of class " + var3.getClass().getName() + " failed but other senders completed their task. ACRA will not send this report again.");
               continue;
            }

            var5 = true;
         }
      }

   }

   public void run() {
      if(this.approvePendingReports) {
         this.approvePendingReports();
      }

      this.checkAndSendReports(this.context, this.sendOnlySilentReports);
   }
}
