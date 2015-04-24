package com.google.appinventor.components.runtime;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.ActivityResultListener;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import java.util.Date;

@DesignerComponent(
   category = ComponentCategory.MEDIA,
   description = "A component to take a picture using the device\'s camera. After the picture is taken, the name of the file on the phone containing the picture is available as an argument to the AfterPicture event. The file name can be used, for example, to set the Picture property of an Image component.",
   iconName = "images/camera.png",
   nonVisible = true,
   version = 2
)
@SimpleObject
public class Camera extends AndroidNonvisibleComponent implements ActivityResultListener, Component {

   private static final String CAMERA_INTENT = "android.media.action.IMAGE_CAPTURE";
   private static final String CAMERA_OUTPUT = "output";
   private final ComponentContainer container;
   private Uri imageFile;
   private int requestCode;
   private boolean useFront;


   public Camera(ComponentContainer var1) {
      super(var1.$form());
      this.container = var1;
      this.UseFront(false);
   }

   private void deleteFile(Uri var1) {
      java.io.File var2 = new java.io.File(var1.getPath());

      try {
         if(var2.delete()) {
            Log.i("CameraComponent", "Deleted file " + var1.toString());
         } else {
            Log.i("CameraComponent", "Could not delete file " + var1.toString());
         }
      } catch (SecurityException var3) {
         Log.i("CameraComponent", "Got security exception trying to delete file " + var1.toString());
      }
   }

   @SimpleEvent
   public void AfterPicture(String var1) {
      EventDispatcher.dispatchEvent(this, "AfterPicture", new Object[]{var1});
   }

   @SimpleFunction
   public void TakePicture() {
      Date var1 = new Date();
      String var2 = Environment.getExternalStorageState();
      if("mounted".equals(var2)) {
         Log.i("CameraComponent", "External storage is available and writable");
         this.imageFile = Uri.fromFile(new java.io.File(Environment.getExternalStorageDirectory(), "/Pictures/app_inventor_" + var1.getTime() + ".jpg"));
         ContentValues var3 = new ContentValues();
         var3.put("_data", this.imageFile.getPath());
         var3.put("mime_type", "image/jpeg");
         var3.put("title", this.imageFile.getLastPathSegment());
         if(this.requestCode == 0) {
            this.requestCode = this.form.registerForActivityResult(this);
         }

         Uri var4 = this.container.$context().getContentResolver().insert(Media.INTERNAL_CONTENT_URI, var3);
         Intent var5 = new Intent("android.media.action.IMAGE_CAPTURE");
         var5.putExtra("output", var4);
         if(this.useFront) {
            var5.putExtra("android.intent.extras.CAMERA_FACING", 1);
         }

         this.container.$context().startActivityForResult(var5, this.requestCode);
      } else if("mounted_ro".equals(var2)) {
         this.form.dispatchErrorOccurredEvent(this, "TakePicture", 704, new Object[0]);
      } else {
         this.form.dispatchErrorOccurredEvent(this, "TakePicture", 705, new Object[0]);
      }
   }

   @DesignerProperty(
      defaultValue = "False",
      editorType = "boolean"
   )
   @SimpleProperty(
      description = "Specifies whether the front-facing camera should be used (when available). If the device does not have a front-facing camera, this option will be ignored and the camera will open normally."
   )
   public void UseFront(boolean var1) {
      this.useFront = var1;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public boolean UseFront() {
      return this.useFront;
   }

   public void resultReturned(int var1, int var2, Intent var3) {
      Log.i("CameraComponent", "Returning result. Request code = " + var1 + ", result code = " + var2);
      if(var1 == this.requestCode && var2 == -1) {
         if((new java.io.File(this.imageFile.getPath())).length() != 0L) {
            this.AfterPicture(this.imageFile.toString());
         } else {
            this.deleteFile(this.imageFile);
            if(var3 != null && var3.getData() != null) {
               Uri var4 = var3.getData();
               Log.i("CameraComponent", "Calling Camera.AfterPicture with image path " + var4.toString());
               this.AfterPicture(var4.toString());
            } else {
               Log.i("CameraComponent", "Couldn\'t find an image file from the Camera result");
               this.form.dispatchErrorOccurredEvent(this, "TakePicture", 201, new Object[0]);
            }
         }
      } else {
         this.deleteFile(this.imageFile);
      }
   }
}
