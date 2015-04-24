package org.acra;

import org.acra.ACRA;
import org.acra.ACRAConfigurationException;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

public class ACRAConfiguration implements ReportsCrashes {

   private String[] mAdditionalDropboxTags = null;
   private String[] mAdditionalSharedPreferences = null;
   private String mApplicationLogFile = null;
   private Integer mApplicationLogFileLines = null;
   private Integer mConnectionTimeout = null;
   private ReportField[] mCustomReportContent = null;
   private Boolean mDeleteOldUnsentReportsOnApplicationStart = null;
   private Boolean mDeleteUnapprovedReportsOnApplicationStart = null;
   private Boolean mDisableSSLCertValidation = null;
   private Integer mDropboxCollectionMinutes = null;
   private String[] mExcludeMatchingSharedPreferencesKeys = null;
   private Boolean mForceCloseDialogAfterToast = null;
   private String mFormKey = null;
   private String mFormUri = null;
   private String mFormUriBasicAuthLogin = null;
   private String mFormUriBasicAuthPassword = null;
   private String mGoogleFormUrlFormat = null;
   private Boolean mIncludeDropboxSystemTags = null;
   private String[] mLogcatArguments = null;
   private Boolean mLogcatFilterByPid = null;
   private String mMailTo = null;
   private Integer mMaxNumberOfRequestRetries = null;
   private ReportingInteractionMode mMode = null;
   private ReportsCrashes mReportsCrashes = null;
   private Integer mResDialogCommentPrompt = null;
   private Integer mResDialogEmailPrompt = null;
   private Integer mResDialogIcon = null;
   private Integer mResDialogOkToast = null;
   private Integer mResDialogText = null;
   private Integer mResDialogTitle = null;
   private Integer mResNotifIcon = null;
   private Integer mResNotifText = null;
   private Integer mResNotifTickerText = null;
   private Integer mResNotifTitle = null;
   private Integer mResToastText = null;
   private Boolean mSendReportsInDevMode = null;
   private Integer mSharedPreferenceMode = null;
   private String mSharedPreferenceName = null;
   private Integer mSocketTimeout = null;


   public ACRAConfiguration(ReportsCrashes var1) {
      this.mReportsCrashes = var1;
   }

   public String[] additionalDropBoxTags() {
      return this.mAdditionalDropboxTags != null?this.mAdditionalDropboxTags:(this.mReportsCrashes != null?this.mReportsCrashes.additionalDropBoxTags():new String[0]);
   }

   public String[] additionalSharedPreferences() {
      return this.mAdditionalSharedPreferences != null?this.mAdditionalSharedPreferences:(this.mReportsCrashes != null?this.mReportsCrashes.additionalSharedPreferences():new String[0]);
   }

   public Class annotationType() {
      return this.mReportsCrashes.annotationType();
   }

   public String applicationLogFile() {
      return this.mApplicationLogFile != null?this.mApplicationLogFile:(this.mReportsCrashes != null?this.mReportsCrashes.applicationLogFile():"");
   }

   public int applicationLogFileLines() {
      return this.mApplicationLogFileLines != null?this.mApplicationLogFileLines.intValue():(this.mReportsCrashes != null?this.mReportsCrashes.applicationLogFileLines():100);
   }

   public int connectionTimeout() {
      return this.mConnectionTimeout != null?this.mConnectionTimeout.intValue():(this.mReportsCrashes != null?this.mReportsCrashes.connectionTimeout():3000);
   }

   public ReportField[] customReportContent() {
      return this.mCustomReportContent != null?this.mCustomReportContent:(this.mReportsCrashes != null?this.mReportsCrashes.customReportContent():new ReportField[0]);
   }

   public boolean deleteOldUnsentReportsOnApplicationStart() {
      return this.mDeleteOldUnsentReportsOnApplicationStart != null?this.mDeleteOldUnsentReportsOnApplicationStart.booleanValue():(this.mReportsCrashes != null?this.mReportsCrashes.deleteOldUnsentReportsOnApplicationStart():true);
   }

   public boolean deleteUnapprovedReportsOnApplicationStart() {
      return this.mDeleteUnapprovedReportsOnApplicationStart != null?this.mDeleteUnapprovedReportsOnApplicationStart.booleanValue():(this.mReportsCrashes != null?this.mReportsCrashes.deleteUnapprovedReportsOnApplicationStart():true);
   }

   public boolean disableSSLCertValidation() {
      return this.mDisableSSLCertValidation != null?this.mDisableSSLCertValidation.booleanValue():(this.mReportsCrashes != null?this.mReportsCrashes.disableSSLCertValidation():false);
   }

   public int dropboxCollectionMinutes() {
      return this.mDropboxCollectionMinutes != null?this.mDropboxCollectionMinutes.intValue():(this.mReportsCrashes != null?this.mReportsCrashes.dropboxCollectionMinutes():5);
   }

   public String[] excludeMatchingSharedPreferencesKeys() {
      return this.mExcludeMatchingSharedPreferencesKeys != null?this.mExcludeMatchingSharedPreferencesKeys:(this.mReportsCrashes != null?this.mReportsCrashes.excludeMatchingSharedPreferencesKeys():new String[0]);
   }

   public boolean forceCloseDialogAfterToast() {
      return this.mForceCloseDialogAfterToast != null?this.mForceCloseDialogAfterToast.booleanValue():(this.mReportsCrashes != null?this.mReportsCrashes.forceCloseDialogAfterToast():false);
   }

   public String formKey() {
      return this.mFormKey != null?this.mFormKey:(this.mReportsCrashes != null?this.mReportsCrashes.formKey():"");
   }

   public String formUri() {
      return this.mFormUri != null?this.mFormUri:(this.mReportsCrashes != null?this.mReportsCrashes.formUri():"");
   }

   public String formUriBasicAuthLogin() {
      return this.mFormUriBasicAuthLogin != null?this.mFormUriBasicAuthLogin:(this.mReportsCrashes != null?this.mReportsCrashes.formUriBasicAuthLogin():"ACRA-NULL-STRING");
   }

   public String formUriBasicAuthPassword() {
      return this.mFormUriBasicAuthPassword != null?this.mFormUriBasicAuthPassword:(this.mReportsCrashes != null?this.mReportsCrashes.formUriBasicAuthPassword():"ACRA-NULL-STRING");
   }

   public String googleFormUrlFormat() {
      return this.mGoogleFormUrlFormat != null?this.mGoogleFormUrlFormat:(this.mReportsCrashes != null?this.mReportsCrashes.googleFormUrlFormat():"https://docs.google.com/spreadsheet/formResponse?formkey=%s&ifq");
   }

   public boolean includeDropBoxSystemTags() {
      return this.mIncludeDropboxSystemTags != null?this.mIncludeDropboxSystemTags.booleanValue():(this.mReportsCrashes != null?this.mReportsCrashes.includeDropBoxSystemTags():false);
   }

   public String[] logcatArguments() {
      return this.mLogcatArguments != null?this.mLogcatArguments:(this.mReportsCrashes != null?this.mReportsCrashes.logcatArguments():new String[]{"-t", Integer.toString(100), "-v", "time"});
   }

   public boolean logcatFilterByPid() {
      return this.mLogcatFilterByPid != null?this.mLogcatFilterByPid.booleanValue():(this.mReportsCrashes != null?this.mReportsCrashes.logcatFilterByPid():false);
   }

   public String mailTo() {
      return this.mMailTo != null?this.mMailTo:(this.mReportsCrashes != null?this.mReportsCrashes.mailTo():"");
   }

   public int maxNumberOfRequestRetries() {
      return this.mMaxNumberOfRequestRetries != null?this.mMaxNumberOfRequestRetries.intValue():(this.mReportsCrashes != null?this.mReportsCrashes.maxNumberOfRequestRetries():3);
   }

   public ReportingInteractionMode mode() {
      return this.mMode != null?this.mMode:(this.mReportsCrashes != null?this.mReportsCrashes.mode():ReportingInteractionMode.SILENT);
   }

   public int resDialogCommentPrompt() {
      return this.mResDialogCommentPrompt != null?this.mResDialogCommentPrompt.intValue():(this.mReportsCrashes != null?this.mReportsCrashes.resDialogCommentPrompt():0);
   }

   public int resDialogEmailPrompt() {
      return this.mResDialogEmailPrompt != null?this.mResDialogEmailPrompt.intValue():(this.mReportsCrashes != null?this.mReportsCrashes.resDialogEmailPrompt():0);
   }

   public int resDialogIcon() {
      return this.mResDialogIcon != null?this.mResDialogIcon.intValue():(this.mReportsCrashes != null?this.mReportsCrashes.resDialogIcon():17301543);
   }

   public int resDialogOkToast() {
      return this.mResDialogOkToast != null?this.mResDialogOkToast.intValue():(this.mReportsCrashes != null?this.mReportsCrashes.resDialogOkToast():0);
   }

   public int resDialogText() {
      return this.mResDialogText != null?this.mResDialogText.intValue():(this.mReportsCrashes != null?this.mReportsCrashes.resDialogText():0);
   }

   public int resDialogTitle() {
      return this.mResDialogTitle != null?this.mResDialogTitle.intValue():(this.mReportsCrashes != null?this.mReportsCrashes.resDialogTitle():0);
   }

   public int resNotifIcon() {
      return this.mResNotifIcon != null?this.mResNotifIcon.intValue():(this.mReportsCrashes != null?this.mReportsCrashes.resNotifIcon():17301624);
   }

   public int resNotifText() {
      return this.mResNotifText != null?this.mResNotifText.intValue():(this.mReportsCrashes != null?this.mReportsCrashes.resNotifText():0);
   }

   public int resNotifTickerText() {
      return this.mResNotifTickerText != null?this.mResNotifTickerText.intValue():(this.mReportsCrashes != null?this.mReportsCrashes.resNotifTickerText():0);
   }

   public int resNotifTitle() {
      return this.mResNotifTitle != null?this.mResNotifTitle.intValue():(this.mReportsCrashes != null?this.mReportsCrashes.resNotifTitle():0);
   }

   public int resToastText() {
      return this.mResToastText != null?this.mResToastText.intValue():(this.mReportsCrashes != null?this.mReportsCrashes.resToastText():0);
   }

   public boolean sendReportsInDevMode() {
      return this.mSendReportsInDevMode != null?this.mSendReportsInDevMode.booleanValue():(this.mReportsCrashes != null?this.mReportsCrashes.sendReportsInDevMode():true);
   }

   public void setAdditionalDropboxTags(String[] var1) {
      this.mAdditionalDropboxTags = var1;
   }

   public void setAdditionalSharedPreferences(String[] var1) {
      this.mAdditionalSharedPreferences = var1;
   }

   public void setApplicationLogFile(String var1) {
      this.mApplicationLogFile = var1;
   }

   public void setApplicationLogFileLines(int var1) {
      this.mApplicationLogFileLines = Integer.valueOf(var1);
   }

   public void setConnectionTimeout(Integer var1) {
      this.mConnectionTimeout = var1;
   }

   public void setCustomReportContent(ReportField[] var1) {
      this.mCustomReportContent = var1;
   }

   public void setDeleteOldUnsentReportsOnApplicationStart(Boolean var1) {
      this.mDeleteOldUnsentReportsOnApplicationStart = var1;
   }

   public void setDeleteUnapprovedReportsOnApplicationStart(Boolean var1) {
      this.mDeleteUnapprovedReportsOnApplicationStart = var1;
   }

   public void setDisableSSLCertValidation(boolean var1) {
      this.mDisableSSLCertValidation = Boolean.valueOf(var1);
   }

   public void setDropboxCollectionMinutes(Integer var1) {
      this.mDropboxCollectionMinutes = var1;
   }

   public void setExcludeMatchingSharedPreferencesKeys(String[] var1) {
      this.mExcludeMatchingSharedPreferencesKeys = var1;
   }

   public void setForceCloseDialogAfterToast(Boolean var1) {
      this.mForceCloseDialogAfterToast = var1;
   }

   public void setFormKey(String var1) {
      this.mFormKey = var1;
   }

   public void setFormUri(String var1) {
      this.mFormUri = var1;
   }

   public void setFormUriBasicAuthLogin(String var1) {
      this.mFormUriBasicAuthLogin = var1;
   }

   public void setFormUriBasicAuthPassword(String var1) {
      this.mFormUriBasicAuthPassword = var1;
   }

   public void setIncludeDropboxSystemTags(Boolean var1) {
      this.mIncludeDropboxSystemTags = var1;
   }

   public void setLogcatArguments(String[] var1) {
      this.mLogcatArguments = var1;
   }

   public void setLogcatFilterByPid(Boolean var1) {
      this.mLogcatFilterByPid = var1;
   }

   public void setMailTo(String var1) {
      this.mMailTo = var1;
   }

   public void setMaxNumberOfRequestRetries(Integer var1) {
      this.mMaxNumberOfRequestRetries = var1;
   }

   public void setMode(ReportingInteractionMode var1) throws ACRAConfigurationException {
      this.mMode = var1;
      ACRA.checkCrashResources();
   }

   public void setResDialogCommentPrompt(int var1) {
      this.mResDialogCommentPrompt = Integer.valueOf(var1);
   }

   public void setResDialogEmailPrompt(int var1) {
      this.mResDialogEmailPrompt = Integer.valueOf(var1);
   }

   public void setResDialogIcon(int var1) {
      this.mResDialogIcon = Integer.valueOf(var1);
   }

   public void setResDialogOkToast(int var1) {
      this.mResDialogOkToast = Integer.valueOf(var1);
   }

   public void setResDialogText(int var1) {
      this.mResDialogText = Integer.valueOf(var1);
   }

   public void setResDialogTitle(int var1) {
      this.mResDialogTitle = Integer.valueOf(var1);
   }

   public void setResNotifIcon(int var1) {
      this.mResNotifIcon = Integer.valueOf(var1);
   }

   public void setResNotifText(int var1) {
      this.mResNotifText = Integer.valueOf(var1);
   }

   public void setResNotifTickerText(int var1) {
      this.mResNotifTickerText = Integer.valueOf(var1);
   }

   public void setResNotifTitle(int var1) {
      this.mResNotifTitle = Integer.valueOf(var1);
   }

   public void setResToastText(int var1) {
      this.mResToastText = Integer.valueOf(var1);
   }

   public void setSendReportsInDevMode(Boolean var1) {
      this.mSendReportsInDevMode = var1;
   }

   public void setSharedPreferenceMode(Integer var1) {
      this.mSharedPreferenceMode = var1;
   }

   public void setSharedPreferenceName(String var1) {
      this.mSharedPreferenceName = var1;
   }

   public void setSocketTimeout(Integer var1) {
      this.mSocketTimeout = var1;
   }

   public int sharedPreferencesMode() {
      return this.mSharedPreferenceMode != null?this.mSharedPreferenceMode.intValue():(this.mReportsCrashes != null?this.mReportsCrashes.sharedPreferencesMode():0);
   }

   public String sharedPreferencesName() {
      return this.mSharedPreferenceName != null?this.mSharedPreferenceName:(this.mReportsCrashes != null?this.mReportsCrashes.sharedPreferencesName():"");
   }

   public int socketTimeout() {
      return this.mSocketTimeout != null?this.mSocketTimeout.intValue():(this.mReportsCrashes != null?this.mReportsCrashes.socketTimeout():5000);
   }
}
