package org.acra;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;
import org.acra.ACRAConfiguration;
import org.acra.ACRAConfigurationException;
import org.acra.ErrorReporter;
import org.acra.ReportField;
import org.acra.annotation.ReportsCrashes;
import org.acra.log.ACRALog;
import org.acra.log.AndroidLogDelegate;

public class ACRA {

   public static final ReportField[] DEFAULT_MAIL_REPORT_FIELDS = new ReportField[]{ReportField.USER_COMMENT, ReportField.ANDROID_VERSION, ReportField.APP_VERSION_NAME, ReportField.BRAND, ReportField.PHONE_MODEL, ReportField.CUSTOM_DATA, ReportField.STACK_TRACE};
   public static final ReportField[] DEFAULT_REPORT_FIELDS = new ReportField[]{ReportField.REPORT_ID, ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME, ReportField.PACKAGE_NAME, ReportField.FILE_PATH, ReportField.PHONE_MODEL, ReportField.BRAND, ReportField.PRODUCT, ReportField.ANDROID_VERSION, ReportField.BUILD, ReportField.TOTAL_MEM_SIZE, ReportField.AVAILABLE_MEM_SIZE, ReportField.CUSTOM_DATA, ReportField.IS_SILENT, ReportField.STACK_TRACE, ReportField.INITIAL_CONFIGURATION, ReportField.CRASH_CONFIGURATION, ReportField.DISPLAY, ReportField.USER_COMMENT, ReportField.USER_EMAIL, ReportField.USER_APP_START_DATE, ReportField.USER_CRASH_DATE, ReportField.DUMPSYS_MEMINFO, ReportField.LOGCAT, ReportField.INSTALLATION_ID, ReportField.DEVICE_FEATURES, ReportField.ENVIRONMENT, ReportField.SHARED_PREFERENCES, ReportField.SETTINGS_SYSTEM, ReportField.SETTINGS_SECURE};
   public static final boolean DEV_LOGGING = false;
   public static final String LOG_TAG = ACRA.class.getSimpleName();
   public static final String PREF_ALWAYS_ACCEPT = "acra.alwaysaccept";
   public static final String PREF_DISABLE_ACRA = "acra.disable";
   public static final String PREF_ENABLE_ACRA = "acra.enable";
   public static final String PREF_ENABLE_DEVICE_ID = "acra.deviceid.enable";
   public static final String PREF_ENABLE_SYSTEM_LOGS = "acra.syslog.enable";
   public static final String PREF_LAST_VERSION_NR = "acra.lastVersionNr";
   public static final String PREF_USER_EMAIL_ADDRESS = "acra.user.email";
   private static ACRAConfiguration configProxy;
   private static ErrorReporter errorReporterSingleton;
   public static ACRALog log = new AndroidLogDelegate();
   private static Application mApplication;
   private static OnSharedPreferenceChangeListener mPrefListener;
   private static ReportsCrashes mReportsCrashes;


   static void checkCrashResources() throws ACRAConfigurationException {
      ACRAConfiguration var0 = getConfig();
      switch(null.$SwitchMap$org$acra$ReportingInteractionMode[var0.mode().ordinal()]) {
      case 1:
         if(var0.resToastText() == 0) {
            throw new ACRAConfigurationException("TOAST mode: you have to define the resToastText parameter in your application @ReportsCrashes() annotation.");
         }
         break;
      case 2:
         if(var0.resNotifTickerText() == 0 || var0.resNotifTitle() == 0 || var0.resNotifText() == 0 || var0.resDialogText() == 0) {
            throw new ACRAConfigurationException("NOTIFICATION mode: you have to define at least the resNotifTickerText, resNotifTitle, resNotifText, resDialogText parameters in your application @ReportsCrashes() annotation.");
         }
         break;
      case 3:
         if(var0.resDialogText() == 0) {
            throw new ACRAConfigurationException("DIALOG mode: you have to define at least the resDialogText parameters in your application @ReportsCrashes() annotation.");
         }
      }

   }

   public static SharedPreferences getACRASharedPreferences() {
      ACRAConfiguration var0 = getConfig();
      return !"".equals(var0.sharedPreferencesName())?mApplication.getSharedPreferences(var0.sharedPreferencesName(), var0.sharedPreferencesMode()):PreferenceManager.getDefaultSharedPreferences(mApplication);
   }

   static Application getApplication() {
      return mApplication;
   }

   public static ACRAConfiguration getConfig() {
      if(configProxy == null) {
         if(mApplication == null) {
            log.w(LOG_TAG, (String)"Calling ACRA.getConfig() before ACRA.init() gives you an empty configuration instance. You might prefer calling ACRA.getNewDefaultConfig(Application) to get an instance with default values taken from a @ReportsCrashes annotation.");
         }

         configProxy = getNewDefaultConfig(mApplication);
      }

      return configProxy;
   }

   public static ErrorReporter getErrorReporter() {
      if(errorReporterSingleton == null) {
         throw new IllegalStateException("Cannot access ErrorReporter before ACRA#init");
      } else {
         return errorReporterSingleton;
      }
   }

   public static ACRAConfiguration getNewDefaultConfig(Application var0) {
      return var0 != null?new ACRAConfiguration((ReportsCrashes)var0.getClass().getAnnotation(ReportsCrashes.class)):new ACRAConfiguration((ReportsCrashes)null);
   }

   public static void init(Application param0) {
      // $FF: Couldn't be decompiled
   }

   static boolean isDebuggable() {
      boolean var2 = false;
      PackageManager var0 = mApplication.getPackageManager();

      int var1;
      try {
         var1 = var0.getApplicationInfo(mApplication.getPackageName(), 0).flags;
      } catch (NameNotFoundException var3) {
         return false;
      }

      if((var1 & 2) > 0) {
         var2 = true;
      }

      return var2;
   }

   public static void setConfig(ACRAConfiguration var0) {
      configProxy = var0;
   }

   public static void setLog(ACRALog var0) {
      log = var0;
   }

   private static boolean shouldDisableACRA(SharedPreferences param0) {
      // $FF: Couldn't be decompiled
   }
}
