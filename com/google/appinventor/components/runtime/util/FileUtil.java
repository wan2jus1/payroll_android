package com.google.appinventor.components.runtime.util;

import android.os.Environment;
import com.google.appinventor.components.runtime.errors.RuntimeError;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class FileUtil {

   private static final String DIRECTORY_DOWNLOADS = "Downloads";
   private static final String DIRECTORY_PICTURES = "Pictures";
   private static final String DIRECTORY_RECORDINGS = "Recordings";
   private static final String DOCUMENT_DIRECTORY = "My Documents/";
   private static final String FILENAME_PREFIX = "app_inventor_";


   public static void checkExternalStorageWriteable() throws FileUtil.FileException {
      String var0 = Environment.getExternalStorageState();
      if(!"mounted".equals(var0)) {
         if("mounted_ro".equals(var0)) {
            throw new FileUtil.FileException(704);
         } else {
            throw new FileUtil.FileException(705);
         }
      }
   }

   private static void copy(InputStream var0, OutputStream var1) throws IOException {
      BufferedOutputStream var4 = new BufferedOutputStream(var1, 4096);
      BufferedInputStream var3 = new BufferedInputStream(var0, 4096);

      while(true) {
         int var2 = var3.read();
         if(var2 == -1) {
            var4.flush();
            return;
         }

         var4.write(var2);
      }
   }

   public static String copyFile(String var0, String var1) throws IOException {
      FileInputStream var4 = new FileInputStream(var0);

      try {
         var1 = writeStreamToFile(var4, var1);
      } finally {
         var4.close();
      }

      return var1;
   }

   public static String downloadUrlToFile(String var0, String var1) throws IOException {
      InputStream var4 = (new URL(var0)).openStream();

      try {
         var1 = writeStreamToFile(var4, var1);
      } finally {
         var4.close();
      }

      return var1;
   }

   public static File getDownloadFile(String var0) throws IOException, FileUtil.FileException {
      return getFile("Downloads", var0);
   }

   public static File getExternalFile(String var0) throws IOException, FileUtil.FileException {
      checkExternalStorageWriteable();
      File var2 = new File(Environment.getExternalStorageDirectory(), var0);
      File var1 = var2.getParentFile();
      if(!var1.exists() && !var1.mkdirs()) {
         throw new IOException("Unable to create directory " + var1.getAbsolutePath());
      } else if(var2.exists() && !var2.delete()) {
         throw new IOException("Cannot overwrite existing file " + var2.getAbsolutePath());
      } else {
         return var2;
      }
   }

   private static File getFile(String var0, String var1) throws IOException, FileUtil.FileException {
      return getExternalFile("My Documents/" + var0 + "/" + "app_inventor_" + System.currentTimeMillis() + "." + var1);
   }

   public static String getFileUrl(String var0) {
      return (new File(var0)).toURI().toString();
   }

   public static File getPictureFile(String var0) throws IOException, FileUtil.FileException {
      return getFile("Pictures", var0);
   }

   public static File getRecordingFile(String var0) throws IOException, FileUtil.FileException {
      return getFile("Recordings", var0);
   }

   public static byte[] readFile(String var0) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      FileInputStream var4 = new FileInputStream(var0);

      try {
         copy(var4, var1);
      } finally {
         var4.close();
      }

      return var1.toByteArray();
   }

   public static String writeFile(byte[] var0, String var1) throws IOException {
      ByteArrayInputStream var4 = new ByteArrayInputStream(var0);

      try {
         var1 = writeStreamToFile(var4, var1);
      } finally {
         var4.close();
      }

      return var1;
   }

   public static String writeStreamToFile(InputStream var0, String var1) throws IOException {
      File var2 = new File(var1);
      var2.getParentFile().mkdirs();
      FileOutputStream var6 = new FileOutputStream(var2);

      String var5;
      try {
         copy(var0, var6);
         var5 = var2.toURI().toString();
      } finally {
         var6.flush();
         var6.close();
      }

      return var5;
   }

   public static class FileException extends RuntimeError {

      private final int msgNumber;


      public FileException(int var1) {
         this.msgNumber = var1;
      }

      public int getErrorMessageNumber() {
         return this.msgNumber;
      }
   }
}
