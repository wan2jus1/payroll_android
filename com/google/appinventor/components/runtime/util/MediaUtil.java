package com.google.appinventor.components.runtime.util;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Environment;
import android.provider.Contacts.People;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.VideoView;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.ReplForm;
import com.google.appinventor.components.runtime.util.HoneycombUtil;
import com.google.appinventor.components.runtime.util.SdkLevel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MediaUtil {

   private static final String LOG_TAG = "MediaUtil";
   private static final String REPL_ASSET_DIR = "/sdcard/AppInventor/assets/";
   private static ConcurrentHashMap pathCache = new ConcurrentHashMap(2);
   private static final Map tempFileMap = new HashMap();


   private static File cacheMediaTempFile(Form var0, String var1, MediaUtil.MediaSource var2) throws IOException {
      File var4 = (File)tempFileMap.get(var1);
      File var3;
      if(var4 != null) {
         var3 = var4;
         if(var4.exists()) {
            return var3;
         }
      }

      Log.i("MediaUtil", "Copying media " + var1 + " to temp file...");
      var3 = copyMediaToTempFile(var0, var1, var2);
      Log.i("MediaUtil", "Finished copying media " + var1 + " to temp file " + var3.getAbsolutePath());
      tempFileMap.put(var1, var3);
      return var3;
   }

   public static File copyMediaToTempFile(Form var0, String var1) throws IOException {
      return copyMediaToTempFile(var0, var1, determineMediaSource(var0, var1));
   }

   private static File copyMediaToTempFile(Form param0, String param1, MediaUtil.MediaSource param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private static Bitmap decodeStream(InputStream var0, Rect var1, Options var2) {
      return BitmapFactory.decodeStream(new MediaUtil.FlushedInputStream(var0), var1, var2);
   }

   private static MediaUtil.MediaSource determineMediaSource(Form var0, String var1) {
      if(!var1.startsWith("/sdcard/") && !var1.startsWith(Environment.getExternalStorageDirectory().getAbsolutePath())) {
         if(var1.startsWith("content://contacts/")) {
            return MediaUtil.MediaSource.CONTACT_URI;
         } else if(var1.startsWith("content://")) {
            return MediaUtil.MediaSource.CONTENT_URI;
         } else {
            try {
               new URL(var1);
               if(var1.startsWith("file:")) {
                  return MediaUtil.MediaSource.FILE_URL;
               } else {
                  MediaUtil.MediaSource var3 = MediaUtil.MediaSource.URL;
                  return var3;
               }
            } catch (MalformedURLException var2) {
               return var0 instanceof ReplForm?(((ReplForm)var0).isAssetsLoaded()?MediaUtil.MediaSource.REPL_ASSET:MediaUtil.MediaSource.ASSET):MediaUtil.MediaSource.ASSET;
            }
         }
      } else {
         return MediaUtil.MediaSource.SDCARD;
      }
   }

   static String fileUrlToFilePath(String var0) throws IOException {
      try {
         String var1 = (new File((new URL(var0)).toURI())).getAbsolutePath();
         return var1;
      } catch (IllegalArgumentException var2) {
         throw new IOException("Unable to determine file path of file url " + var0);
      } catch (Exception var3) {
         throw new IOException("Unable to determine file path of file url " + var0);
      }
   }

   private static String findCaseinsensitivePath(Form var0, String var1) throws IOException {
      if(!pathCache.containsKey(var1)) {
         String var2 = findCaseinsensitivePathWithoutCache(var0, var1);
         if(var2 == null) {
            return null;
         }

         pathCache.put(var1, var2);
      }

      return (String)pathCache.get(var1);
   }

   private static String findCaseinsensitivePathWithoutCache(Form var0, String var1) throws IOException {
      String[] var5 = var0.getAssets().list("");
      int var4 = Array.getLength(var5);

      for(int var3 = 0; var3 < var4; ++var3) {
         String var2 = var5[var3];
         if(var2.equalsIgnoreCase(var1)) {
            return var2;
         }
      }

      return null;
   }

   private static AssetFileDescriptor getAssetsIgnoreCaseAfd(Form var0, String var1) throws IOException {
      try {
         AssetFileDescriptor var2 = var0.getAssets().openFd(var1);
         return var2;
      } catch (IOException var3) {
         var1 = findCaseinsensitivePath(var0, var1);
         if(var1 == null) {
            throw var3;
         } else {
            return var0.getAssets().openFd(var1);
         }
      }
   }

   private static InputStream getAssetsIgnoreCaseInputStream(Form var0, String var1) throws IOException {
      try {
         InputStream var2 = var0.getAssets().open(var1);
         return var2;
      } catch (IOException var3) {
         var1 = findCaseinsensitivePath(var0, var1);
         if(var1 == null) {
            throw var3;
         } else {
            return var0.getAssets().open(var1);
         }
      }
   }

   public static BitmapDrawable getBitmapDrawable(Form var0, String var1) throws IOException {
      BitmapDrawable var15;
      if(var1 != null && var1.length() != 0) {
         MediaUtil.MediaSource var2 = determineMediaSource(var0, var1);

         InputStream var3;
         try {
            var3 = openMedia(var0, var1, var2);
         } catch (IOException var13) {
            if(var2 == MediaUtil.MediaSource.CONTACT_URI) {
               return new BitmapDrawable(BitmapFactory.decodeResource(var0.getResources(), 17301606, (Options)null));
            }

            throw var13;
         }

         Options var4;
         try {
            var4 = getBitmapOptions(var0, var3);
         } finally {
            var3.close();
         }

         InputStream var17 = openMedia(var0, var1, var2);
         boolean var8 = false;

         BitmapDrawable var16;
         try {
            var8 = true;
            var16 = new BitmapDrawable(decodeStream(var17, (Rect)null, var4));
            var8 = false;
         } finally {
            if(var8) {
               if(var17 != null) {
                  var17.close();
               }

            }
         }

         var15 = var16;
         if(var17 != null) {
            var17.close();
            return var16;
         }
      } else {
         var15 = null;
      }

      return var15;
   }

   private static Options getBitmapOptions(Form var0, InputStream var1) {
      Options var2 = new Options();
      var2.inJustDecodeBounds = true;
      decodeStream(var1, (Rect)null, var2);
      int var4 = var2.outWidth;
      int var5 = var2.outHeight;
      Display var8 = ((WindowManager)var0.getSystemService("window")).getDefaultDisplay();
      int var6 = var8.getWidth();
      int var7 = var8.getHeight();

      int var3;
      for(var3 = 1; var4 / var3 > var6 * 2 && var5 / var3 > var7 * 2; var3 *= 2) {
         ;
      }

      Options var9 = new Options();
      var9.inSampleSize = var3;
      return var9;
   }

   public static void loadMediaPlayer(MediaPlayer var0, Form var1, String var2) throws IOException {
      MediaUtil.MediaSource var3 = determineMediaSource(var1, var2);
      switch(null.$SwitchMap$com$google$appinventor$components$runtime$util$MediaUtil$MediaSource[var3.ordinal()]) {
      case 1:
         AssetFileDescriptor var6 = getAssetsIgnoreCaseAfd(var1, var2);

         try {
            var0.setDataSource(var6.getFileDescriptor(), var6.getStartOffset(), var6.getLength());
         } finally {
            var6.close();
         }

         return;
      case 2:
         var0.setDataSource(replAssetPath(var2));
         return;
      case 3:
         var0.setDataSource(var2);
         return;
      case 4:
         var0.setDataSource(fileUrlToFilePath(var2));
         return;
      case 5:
         var0.setDataSource(var2);
         return;
      case 6:
         var0.setDataSource(var1, Uri.parse(var2));
         return;
      case 7:
         throw new IOException("Unable to load audio or video for contact " + var2 + ".");
      default:
         throw new IOException("Unable to load audio or video " + var2 + ".");
      }
   }

   public static int loadSoundPool(SoundPool var0, Form var1, String var2) throws IOException {
      MediaUtil.MediaSource var3 = determineMediaSource(var1, var2);
      switch(null.$SwitchMap$com$google$appinventor$components$runtime$util$MediaUtil$MediaSource[var3.ordinal()]) {
      case 1:
         return var0.load(getAssetsIgnoreCaseAfd(var1, var2), 1);
      case 2:
         return var0.load(replAssetPath(var2), 1);
      case 3:
         return var0.load(var2, 1);
      case 4:
         return var0.load(fileUrlToFilePath(var2), 1);
      case 5:
      case 6:
         return var0.load(cacheMediaTempFile(var1, var2, var3).getAbsolutePath(), 1);
      case 7:
         throw new IOException("Unable to load audio for contact " + var2 + ".");
      default:
         throw new IOException("Unable to load audio " + var2 + ".");
      }
   }

   public static void loadVideoView(VideoView var0, Form var1, String var2) throws IOException {
      MediaUtil.MediaSource var3 = determineMediaSource(var1, var2);
      switch(null.$SwitchMap$com$google$appinventor$components$runtime$util$MediaUtil$MediaSource[var3.ordinal()]) {
      case 1:
      case 5:
         var0.setVideoPath(cacheMediaTempFile(var1, var2, var3).getAbsolutePath());
         return;
      case 2:
         var0.setVideoPath(replAssetPath(var2));
         return;
      case 3:
         var0.setVideoPath(var2);
         return;
      case 4:
         var0.setVideoPath(fileUrlToFilePath(var2));
         return;
      case 6:
         var0.setVideoURI(Uri.parse(var2));
         return;
      case 7:
         throw new IOException("Unable to load video for contact " + var2 + ".");
      default:
         throw new IOException("Unable to load video " + var2 + ".");
      }
   }

   public static InputStream openMedia(Form var0, String var1) throws IOException {
      return openMedia(var0, var1, determineMediaSource(var0, var1));
   }

   private static InputStream openMedia(Form var0, String var1, MediaUtil.MediaSource var2) throws IOException {
      InputStream var4;
      switch(null.$SwitchMap$com$google$appinventor$components$runtime$util$MediaUtil$MediaSource[var2.ordinal()]) {
      case 1:
         var4 = getAssetsIgnoreCaseInputStream(var0, var1);
         break;
      case 2:
         return new FileInputStream(replAssetPath(var1));
      case 3:
         return new FileInputStream(var1);
      case 4:
      case 5:
         return (new URL(var1)).openStream();
      case 6:
         return var0.getContentResolver().openInputStream(Uri.parse(var1));
      case 7:
         InputStream var3;
         if(SdkLevel.getLevel() >= 12) {
            var3 = HoneycombUtil.openContactPhotoInputStreamHelper(var0.getContentResolver(), Uri.parse(var1));
         } else {
            var3 = People.openContactPhotoInputStream(var0.getContentResolver(), Uri.parse(var1));
         }

         var4 = var3;
         if(var3 == null) {
            throw new IOException("Unable to open contact photo " + var1 + ".");
         }
         break;
      default:
         throw new IOException("Unable to open media " + var1 + ".");
      }

      return var4;
   }

   private static String replAssetPath(String var0) {
      return "/sdcard/AppInventor/assets/" + var0;
   }

   private static class FlushedInputStream extends FilterInputStream {

      public FlushedInputStream(InputStream var1) {
         super(var1);
      }

      public long skip(long var1) throws IOException {
         long var3;
         long var5;
         for(var3 = 0L; var3 < var1; var3 += var5) {
            long var7 = this.in.skip(var1 - var3);
            var5 = var7;
            if(var7 == 0L) {
               if(this.read() < 0) {
                  break;
               }

               var5 = 1L;
            }
         }

         return var3;
      }
   }

   private static enum MediaSource {

      // $FF: synthetic field
      private static final MediaUtil.MediaSource[] $VALUES = new MediaUtil.MediaSource[]{ASSET, REPL_ASSET, SDCARD, FILE_URL, URL, CONTENT_URI, CONTACT_URI};
      ASSET,
      CONTACT_URI,
      CONTENT_URI,
      FILE_URL,
      REPL_ASSET,
      SDCARD,
      URL;


   }
}
