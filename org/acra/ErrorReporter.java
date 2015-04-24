package org.acra;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.os.Looper;
import android.os.Process;
import android.text.format.Time;
import android.util.Log;
import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.acra.ACRA;
import org.acra.ACRAConfiguration;
import org.acra.ACRAConstants;
import org.acra.CrashReportDialog;
import org.acra.CrashReportFileNameParser;
import org.acra.CrashReportFinder;
import org.acra.CrashReportPersister;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.SendWorker;
import org.acra.collector.ConfigurationCollector;
import org.acra.collector.CrashReportData;
import org.acra.collector.CrashReportDataFactory;
import org.acra.sender.EmailIntentSender;
import org.acra.sender.GoogleFormSender;
import org.acra.sender.HttpPostSender;
import org.acra.sender.ReportSender;
import org.acra.util.PackageManagerWrapper;
import org.acra.util.ToastSender;

public class ErrorReporter implements UncaughtExceptionHandler {

   private static boolean toastWaitEnded = true;
   private Thread brokenThread;
   private final CrashReportDataFactory crashReportDataFactory;
   private boolean enabled = false;
   private final CrashReportFileNameParser fileNameParser = new CrashReportFileNameParser();
   private final Context mContext;
   private final UncaughtExceptionHandler mDfltExceptionHandler;
   private final List mReportSenders = new ArrayList();
   private final SharedPreferences prefs;
   private Throwable unhandledThrowable;


   ErrorReporter(Context var1, SharedPreferences var2, boolean var3) {
      this.mContext = var1;
      this.prefs = var2;
      this.enabled = var3;
      String var5 = ConfigurationCollector.collectConfiguration(this.mContext);
      Time var4 = new Time();
      var4.setToNow();
      this.crashReportDataFactory = new CrashReportDataFactory(this.mContext, var2, var4, var5);
      this.mDfltExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
      Thread.setDefaultUncaughtExceptionHandler(this);
      this.checkReportsOnApplicationStart();
   }

   private boolean containsOnlySilentOrApprovedReports(String[] var1) {
      int var4 = var1.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         String var2 = var1[var3];
         if(!this.fileNameParser.isApproved(var2)) {
            return false;
         }
      }

      return true;
   }

   private void deletePendingReports(boolean var1, boolean var2, int var3) {
      String[] var4 = (new CrashReportFinder(this.mContext)).getCrashReportFiles();
      Arrays.sort(var4);
      if(var4 != null) {
         for(int var6 = 0; var6 < var4.length - var3; ++var6) {
            String var5 = var4[var6];
            boolean var7 = this.fileNameParser.isApproved(var5);
            if(var7 && var1 || !var7 && var2) {
               File var8 = new File(this.mContext.getFilesDir(), var5);
               if(!var8.delete()) {
                  Log.e(ACRA.LOG_TAG, "Could not delete report : " + var8);
               }
            }
         }
      }

   }

   private void endApplication() {
      if(ACRA.getConfig().mode() != ReportingInteractionMode.SILENT && (ACRA.getConfig().mode() != ReportingInteractionMode.TOAST || !ACRA.getConfig().forceCloseDialogAfterToast())) {
         Log.e(ACRA.LOG_TAG, this.mContext.getPackageName() + " fatal error : " + this.unhandledThrowable.getMessage(), this.unhandledThrowable);
         Process.killProcess(Process.myPid());
         System.exit(10);
      } else {
         this.mDfltExceptionHandler.uncaughtException(this.brokenThread, this.unhandledThrowable);
      }
   }

   public static ErrorReporter getInstance() {
      return ACRA.getErrorReporter();
   }

   private String getLatestNonSilentReport(String[] var1) {
      if(var1 != null && var1.length > 0) {
         for(int var2 = var1.length - 1; var2 >= 0; --var2) {
            if(!this.fileNameParser.isSilent(var1[var2])) {
               return var1[var2];
            }
         }

         return var1[var1.length - 1];
      } else {
         return null;
      }
   }

   private String getReportFileName(CrashReportData var1) {
      Time var2 = new Time();
      var2.setToNow();
      long var3 = var2.toMillis(false);
      String var5 = var1.getProperty(ReportField.IS_SILENT);
      StringBuilder var6 = (new StringBuilder()).append("").append(var3);
      if(var5 != null) {
         var5 = ACRAConstants.SILENT_SUFFIX;
      } else {
         var5 = "";
      }

      return var6.append(var5).append(".stacktrace").toString();
   }

   private void handleException(Throwable var1, ReportingInteractionMode var2, final boolean var3, final boolean var4) {
      boolean var9 = true;
      if(this.enabled) {
         boolean var10 = false;
         ReportingInteractionMode var5;
         boolean var8;
         if(var2 == null) {
            var5 = ACRA.getConfig().mode();
            var8 = var10;
         } else {
            var8 = var10;
            var5 = var2;
            if(var2 == ReportingInteractionMode.SILENT) {
               var8 = var10;
               var5 = var2;
               if(ACRA.getConfig().mode() != ReportingInteractionMode.SILENT) {
                  var8 = true;
                  var5 = var2;
               }
            }
         }

         Object var13 = var1;
         if(var1 == null) {
            var13 = new Exception("Report requested by developer");
         }

         boolean var7;
         if(var5 != ReportingInteractionMode.TOAST && (ACRA.getConfig().resToastText() == 0 || var5 != ReportingInteractionMode.NOTIFICATION && var5 != ReportingInteractionMode.DIALOG)) {
            var7 = false;
         } else {
            var7 = true;
         }

         if(var7) {
            (new Thread() {
               public void run() {
                  Looper.prepare();
                  ToastSender.sendToast(ErrorReporter.this.mContext, ACRA.getConfig().resToastText(), 1);
                  Looper.loop();
               }
            }).start();
         }

         CrashReportData var11 = this.crashReportDataFactory.createCrashData((Throwable)var13, var3, this.brokenThread);
         final String var6 = this.getReportFileName(var11);
         this.saveCrashReportFile(var6, var11);
         var2 = null;
         final SendWorker var12;
         if(var5 != ReportingInteractionMode.SILENT && var5 != ReportingInteractionMode.TOAST && !this.prefs.getBoolean("acra.alwaysaccept", false)) {
            var12 = var2;
            if(var5 == ReportingInteractionMode.NOTIFICATION) {
               Log.d(ACRA.LOG_TAG, "About to send status bar notification from #handleException");
               this.notifySendReport(var6);
               var12 = var2;
            }
         } else {
            Log.d(ACRA.LOG_TAG, "About to start ReportSenderWorker from #handleException");
            var12 = this.startSendingReports(var8, true);
         }

         if(var7) {
            toastWaitEnded = false;
            (new Thread() {
               public void run() {
                  Time var2 = new Time();
                  Time var1 = new Time();
                  var2.setToNow();
                  long var5 = var2.toMillis(false);

                  for(long var3 = 0L; var3 < 3000L; var3 = var1.toMillis(false) - var5) {
                     try {
                        Thread.sleep(3000L);
                     } catch (InterruptedException var7) {
                        Log.d(ACRA.LOG_TAG, "Interrupted while waiting for Toast to end.", var7);
                     }

                     var1.setToNow();
                  }

                  ErrorReporter.toastWaitEnded = true;
               }
            }).start();
         }

         if(var5 == ReportingInteractionMode.DIALOG && !this.prefs.getBoolean("acra.alwaysaccept", false)) {
            var3 = var9;
         } else {
            var3 = false;
         }

         (new Thread() {
            public void run() {
               Log.d(ACRA.LOG_TAG, "Waiting for Toast + worker...");

               while(!ErrorReporter.toastWaitEnded || var12 != null && var12.isAlive()) {
                  try {
                     Thread.sleep(100L);
                  } catch (InterruptedException var2) {
                     Log.e(ACRA.LOG_TAG, "Error : ", var2);
                  }
               }

               if(var3) {
                  Log.d(ACRA.LOG_TAG, "About to create DIALOG from #handleException");
                  ErrorReporter.this.notifyDialog(var6);
               }

               Log.d(ACRA.LOG_TAG, "Wait for Toast + worker ended. Kill Application ? " + var4);
               if(var4) {
                  ErrorReporter.this.endApplication();
               }

            }
         }).start();
      }
   }

   private void notifySendReport(String var1) {
      NotificationManager var2 = (NotificationManager)this.mContext.getSystemService("notification");
      ACRAConfiguration var5 = ACRA.getConfig();
      Notification var3 = new Notification(var5.resNotifIcon(), this.mContext.getText(var5.resNotifTickerText()), System.currentTimeMillis());
      CharSequence var4 = this.mContext.getText(var5.resNotifTitle());
      CharSequence var8 = this.mContext.getText(var5.resNotifText());
      Intent var6 = new Intent(this.mContext, CrashReportDialog.class);
      Log.d(ACRA.LOG_TAG, "Creating Notification for " + var1);
      var6.putExtra("REPORT_FILE_NAME", var1);
      PendingIntent var7 = PendingIntent.getActivity(this.mContext, 0, var6, 134217728);
      var3.setLatestEventInfo(this.mContext, var4, var8, var7);
      var2.cancelAll();
      var2.notify(666, var3);
   }

   private void saveCrashReportFile(String var1, CrashReportData var2) {
      try {
         Log.d(ACRA.LOG_TAG, "Writing crash report file " + var1 + ".");
         (new CrashReportPersister(this.mContext)).store(var2, var1);
      } catch (Exception var3) {
         Log.e(ACRA.LOG_TAG, "An error occurred while writing the report file...", var3);
      }
   }

   @Deprecated
   public void addCustomData(String var1, String var2) {
      this.crashReportDataFactory.putCustomData(var1, var2);
   }

   public void addReportSender(ReportSender var1) {
      this.mReportSenders.add(var1);
   }

   public void checkReportsOnApplicationStart() {
      long var4 = (long)this.prefs.getInt("acra.lastVersionNr", 0);
      PackageInfo var1 = (new PackageManagerWrapper(this.mContext)).getPackageInfo();
      boolean var3;
      if(var1 != null && (long)var1.versionCode > var4) {
         var3 = true;
      } else {
         var3 = false;
      }

      if(var3) {
         if(ACRA.getConfig().deleteOldUnsentReportsOnApplicationStart()) {
            this.deletePendingReports();
         }

         Editor var2 = this.prefs.edit();
         var2.putInt("acra.lastVersionNr", var1.versionCode);
         var2.commit();
      }

      if((ACRA.getConfig().mode() == ReportingInteractionMode.NOTIFICATION || ACRA.getConfig().mode() == ReportingInteractionMode.DIALOG) && ACRA.getConfig().deleteUnapprovedReportsOnApplicationStart()) {
         this.deletePendingNonApprovedReports(true);
      }

      CrashReportFinder var7 = new CrashReportFinder(this.mContext);
      String[] var9 = var7.getCrashReportFiles();
      if(var9 != null && var9.length > 0) {
         ReportingInteractionMode var10 = ACRA.getConfig().mode();
         String[] var8 = var7.getCrashReportFiles();
         boolean var6 = this.containsOnlySilentOrApprovedReports(var8);
         if(var10 != ReportingInteractionMode.SILENT && var10 != ReportingInteractionMode.TOAST && (!var6 || var10 != ReportingInteractionMode.NOTIFICATION && var10 != ReportingInteractionMode.DIALOG)) {
            if(ACRA.getConfig().mode() == ReportingInteractionMode.NOTIFICATION) {
               this.notifySendReport(this.getLatestNonSilentReport(var8));
               return;
            }

            if(ACRA.getConfig().mode() == ReportingInteractionMode.DIALOG) {
               this.notifyDialog(this.getLatestNonSilentReport(var8));
               return;
            }
         } else {
            if(var10 == ReportingInteractionMode.TOAST && !var6) {
               ToastSender.sendToast(this.mContext, ACRA.getConfig().resToastText(), 1);
            }

            Log.v(ACRA.LOG_TAG, "About to start ReportSenderWorker from #checkReportOnApplicationStart");
            this.startSendingReports(false, false);
         }
      }

   }

   void deletePendingNonApprovedReports(boolean var1) {
      byte var2;
      if(var1) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      this.deletePendingReports(false, true, var2);
   }

   void deletePendingReports() {
      this.deletePendingReports(true, true, 0);
   }

   public String getCustomData(String var1) {
      return this.crashReportDataFactory.getCustomData(var1);
   }

   public void handleException(Throwable var1) {
      this.handleException(var1, ACRA.getConfig().mode(), false, false);
   }

   public void handleException(Throwable var1, boolean var2) {
      this.handleException(var1, ACRA.getConfig().mode(), false, var2);
   }

   public void handleSilentException(Throwable var1) {
      if(this.enabled) {
         this.handleException(var1, ReportingInteractionMode.SILENT, true, false);
         Log.d(ACRA.LOG_TAG, "ACRA sent Silent report.");
      } else {
         Log.d(ACRA.LOG_TAG, "ACRA is disabled. Silent report not sent.");
      }
   }

   void notifyDialog(String var1) {
      Log.d(ACRA.LOG_TAG, "Creating Dialog for " + var1);
      Intent var2 = new Intent(this.mContext, CrashReportDialog.class);
      var2.putExtra("REPORT_FILE_NAME", var1);
      var2.setFlags(268435456);
      this.mContext.startActivity(var2);
   }

   public String putCustomData(String var1, String var2) {
      return this.crashReportDataFactory.putCustomData(var1, var2);
   }

   public void removeAllReportSenders() {
      this.mReportSenders.clear();
   }

   public String removeCustomData(String var1) {
      return this.crashReportDataFactory.removeCustomData(var1);
   }

   public void removeReportSender(ReportSender var1) {
      this.mReportSenders.remove(var1);
   }

   public void removeReportSenders(Class var1) {
      if(ReportSender.class.isAssignableFrom(var1)) {
         Iterator var2 = this.mReportSenders.iterator();

         while(var2.hasNext()) {
            ReportSender var3 = (ReportSender)var2.next();
            if(var1.isInstance(var3)) {
               this.mReportSenders.remove(var3);
            }
         }
      }

   }

   public void setDefaultReportSenders() {
      ACRAConfiguration var1 = ACRA.getConfig();
      Application var2 = ACRA.getApplication();
      this.removeAllReportSenders();
      if(!"".equals(var1.mailTo())) {
         Log.w(ACRA.LOG_TAG, var2.getPackageName() + " reports will be sent by email (if accepted by user).");
         this.setReportSender(new EmailIntentSender(var2));
      } else {
         if(!(new PackageManagerWrapper(var2)).hasPermission("android.permission.INTERNET")) {
            Log.e(ACRA.LOG_TAG, var2.getPackageName() + " should be granted permission " + "android.permission.INTERNET" + " if you want your crash reports to be sent. If you don\'t want to add this permission to your application you can also enable sending reports by email. If this is your will then provide your email address in @ReportsCrashes(mailTo=\"your.account@domain.com\"");
            return;
         }

         if(var1.formUri() != null && !"".equals(var1.formUri())) {
            this.setReportSender(new HttpPostSender((Map)null));
            return;
         }

         if(var1.formKey() != null && !"".equals(var1.formKey().trim())) {
            this.addReportSender(new GoogleFormSender());
            return;
         }
      }

   }

   public void setEnabled(boolean var1) {
      String var3 = ACRA.LOG_TAG;
      StringBuilder var4 = (new StringBuilder()).append("ACRA is ");
      String var2;
      if(var1) {
         var2 = "enabled";
      } else {
         var2 = "disabled";
      }

      Log.i(var3, var4.append(var2).append(" for ").append(this.mContext.getPackageName()).toString());
      this.enabled = var1;
   }

   public void setReportSender(ReportSender var1) {
      this.removeAllReportSenders();
      this.addReportSender(var1);
   }

   SendWorker startSendingReports(boolean var1, boolean var2) {
      SendWorker var3 = new SendWorker(this.mContext, this.mReportSenders, var1, var2);
      var3.start();
      return var3;
   }

   public void uncaughtException(Thread var1, Throwable var2) {
      try {
         if(!this.enabled) {
            if(this.mDfltExceptionHandler != null) {
               Log.e(ACRA.LOG_TAG, "ACRA is disabled for " + this.mContext.getPackageName() + " - forwarding uncaught Exception on to default ExceptionHandler");
               this.mDfltExceptionHandler.uncaughtException(var1, var2);
               return;
            }

            Log.e(ACRA.LOG_TAG, "ACRA is disabled for " + this.mContext.getPackageName() + " - no default ExceptionHandler");
            return;
         }

         this.brokenThread = var1;
         this.unhandledThrowable = var2;
         Log.e(ACRA.LOG_TAG, "ACRA caught a " + var2.getClass().getSimpleName() + " exception for " + this.mContext.getPackageName() + ". Building report.");
         this.handleException(var2, ACRA.getConfig().mode(), false, true);
      } catch (Throwable var4) {
         if(this.mDfltExceptionHandler != null) {
            this.mDfltExceptionHandler.uncaughtException(var1, var2);
            return;
         }
      }

   }
}
