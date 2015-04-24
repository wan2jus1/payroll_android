package com.google.appinventor.common.version;


public final class GitBuildId {

   public static final String ACRA_URI = "${acra.uri}";
   public static final String ANT_BUILD_DATE = "March 29 2015";
   public static final String GIT_BUILD_FINGERPRINT = "156ee60cc9dd32a97c7043244b26b11a6f743994";
   public static final String GIT_BUILD_VERSION = "nb142";


   public static String getAcraUri() {
      return "${acra.uri}".equals("${acra.uri}")?"":"${acra.uri}".trim();
   }

   public static String getDate() {
      return "March 29 2015";
   }

   public static String getFingerprint() {
      return "156ee60cc9dd32a97c7043244b26b11a6f743994";
   }

   public static String getVersion() {
      String var0 = "nb142";
      if("nb142" == "" || "nb142".contains(" ")) {
         var0 = "none";
      }

      return var0;
   }
}
