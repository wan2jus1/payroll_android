package com.google.appinventor.components.runtime;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.webkit.MimeTypeMap;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.ActivityResultListener;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Picker;
import com.google.appinventor.components.runtime.util.MediaUtil;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

@DesignerComponent(
   category = ComponentCategory.MEDIA,
   description = "A special-purpose button. When the user taps an image picker, the device\'s image gallery appears, and the user can choose an image. After an image is picked, it is saved on the SD card and the <code>ImageFile</code> property will be the name of the file where the image is stored. In order to not fill up storage, a maximum of 10 images will be stored.  Picking more images will delete previous images, in order from oldest to newest.",
   version = 5
)
@SimpleObject
@UsesPermissions(
   permissionNames = "android.permission.WRITE_EXTERNAL_STORAGE"
)
public class ImagePicker extends Picker implements ActivityResultListener {

   private static final String FILE_PREFIX = "picked_image";
   private static final String LOG_TAG = "ImagePicker";
   private static final String imagePickerDirectoryName = "/Pictures/_app_inventor_image_picker";
   private static int maxSavedFiles = 10;
   private String selectionSavedImage = "";
   private String selectionURI;


   public ImagePicker(ComponentContainer var1) {
      super(var1);
   }

   private void copyToExternalStorageAndDeleteSource(java.io.File param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   private void saveSelectedImageToExternalStorage(String var1) {
      this.selectionSavedImage = "";

      java.io.File var2;
      try {
         var2 = MediaUtil.copyMediaToTempFile(this.container.$form(), this.selectionURI);
      } catch (IOException var3) {
         Log.i("ImagePicker", "copyMediaToTempFile failed: " + var3.getMessage());
         this.container.$form().dispatchErrorOccurredEvent(this, "ImagePicker", 1602, new Object[]{var3.getMessage()});
         return;
      }

      Log.i("ImagePicker", "temp file path is: " + var2.getPath());
      this.copyToExternalStorageAndDeleteSource(var2, var1);
   }

   private void trimDirectory(int var1, java.io.File var2) {
      java.io.File[] var5 = var2.listFiles();
      Arrays.sort(var5, new Comparator() {
         public int compare(java.io.File var1, java.io.File var2) {
            return Long.valueOf(var1.lastModified()).compareTo(Long.valueOf(var2.lastModified()));
         }
      });
      int var4 = var5.length;

      for(int var3 = 0; var3 < var4 - var1; ++var3) {
         var5[var3].delete();
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Path to the file containing the image that was selected."
   )
   public String Selection() {
      return this.selectionSavedImage;
   }

   protected Intent getIntent() {
      return new Intent("android.intent.action.PICK", Media.INTERNAL_CONTENT_URI);
   }

   public void resultReturned(int var1, int var2, Intent var3) {
      if(var1 == this.requestCode && var2 == -1) {
         Uri var6 = var3.getData();
         this.selectionURI = var6.toString();
         Log.i("ImagePicker", "selectionURI = " + this.selectionURI);
         ContentResolver var4 = this.container.$context().getContentResolver();
         MimeTypeMap var5 = MimeTypeMap.getSingleton();
         String var7 = "." + var5.getExtensionFromMimeType(var4.getType(var6));
         Log.i("ImagePicker", "extension = " + var7);
         this.saveSelectedImageToExternalStorage(var7);
         this.AfterPicking();
      }

   }
}
