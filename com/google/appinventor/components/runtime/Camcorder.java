package com.google.appinventor.components.runtime;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.ActivityResultListener;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;

@DesignerComponent(
   category = ComponentCategory.MEDIA,
   description = "A component to record a video using the device\'s camcorder.After the video is recorded, the name of the file on the phone containing the clip is available as an argument to the AfterRecording event. The file name can be used, for example, to set the source property of a VideoPlayer component.",
   iconName = "images/camcorder.png",
   nonVisible = true,
   version = 1
)
@SimpleObject
public class Camcorder extends AndroidNonvisibleComponent implements ActivityResultListener, Component {

   private static final String CAMCORDER_INTENT = "android.media.action.VIDEO_CAPTURE";
   private final ComponentContainer container;
   private int requestCode;


   public Camcorder(ComponentContainer var1) {
      super(var1.$form());
      this.container = var1;
   }

   private void deleteFile(Uri var1) {
      java.io.File var2 = new java.io.File(var1.getPath());

      try {
         if(var2.delete()) {
            Log.i("CamcorderComponent", "Deleted file " + var1.toString());
         } else {
            Log.i("CamcorderComponent", "Could not delete file " + var1.toString());
         }
      } catch (SecurityException var3) {
         Log.i("CamcorderComponent", "Got security exception trying to delete file " + var1.toString());
      }
   }

   @SimpleEvent
   public void AfterRecording(String var1) {
      EventDispatcher.dispatchEvent(this, "AfterRecording", new Object[]{var1});
   }

   @SimpleFunction
   public void RecordVideo() {
      String var1 = Environment.getExternalStorageState();
      if("mounted".equals(var1)) {
         Log.i("CamcorderComponent", "External storage is available and writable");
         if(this.requestCode == 0) {
            this.requestCode = this.form.registerForActivityResult(this);
         }

         Intent var2 = new Intent("android.media.action.VIDEO_CAPTURE");
         this.container.$context().startActivityForResult(var2, this.requestCode);
      } else if("mounted_ro".equals(var1)) {
         this.form.dispatchErrorOccurredEvent(this, "RecordVideo", 704, new Object[0]);
      } else {
         this.form.dispatchErrorOccurredEvent(this, "RecordVideo", 705, new Object[0]);
      }
   }

   public void resultReturned(int var1, int var2, Intent var3) {
      Log.i("CamcorderComponent", "Returning result. Request code = " + var1 + ", result code = " + var2);
      if(var1 == this.requestCode && var2 == -1) {
         if(var3 != null && var3.getData() != null) {
            Uri var4 = var3.getData();
            Log.i("CamcorderComponent", "Calling Camcorder.AfterPicture with clip path " + var4.toString());
            this.AfterRecording(var4.toString());
         } else {
            Log.i("CamcorderComponent", "Couldn\'t find a clip file from the Camcorder result");
            this.form.dispatchErrorOccurredEvent(this, "TakeVideo", 1201, new Object[0]);
         }
      } else {
         Log.i("CamcorderComponent", "No clip filed rerturn; request failed");
         this.form.dispatchErrorOccurredEvent(this, "TakeVideo", 1201, new Object[0]);
      }
   }
}
