package com.google.appinventor.components.runtime;

import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Environment;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.util.FileUtil;
import java.io.IOException;

@DesignerComponent(
   category = ComponentCategory.MEDIA,
   description = "<p>Multimedia component that records audio.</p>",
   iconName = "images/soundRecorder.png",
   nonVisible = true,
   version = 2
)
@SimpleObject
@UsesPermissions(
   permissionNames = "android.permission.RECORD_AUDIO"
)
public final class SoundRecorder extends AndroidNonvisibleComponent implements Component, OnErrorListener, OnInfoListener {

   private static final String TAG = "SoundRecorder";
   private SoundRecorder.RecordingController controller;
   private String savedRecording = "";


   public SoundRecorder(ComponentContainer var1) {
      super(var1.$form());
   }

   @SimpleEvent(
      description = "Provides the location of the newly created sound."
   )
   public void AfterSoundRecorded(String var1) {
      EventDispatcher.dispatchEvent(this, "AfterSoundRecorded", new Object[]{var1});
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Specifies the path to the file where the recording is stored. If this proprety is the empty string, then starting a recording will create a file in an appropriate location."
   )
   public String SavedRecording() {
      return this.savedRecording;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty
   public void SavedRecording(String var1) {
      this.savedRecording = var1;
   }

   @SimpleFunction
   public void Start() {
      if(this.controller != null) {
         Log.i("SoundRecorder", "Start() called, but already recording to " + this.controller.file);
      } else {
         Log.i("SoundRecorder", "Start() called");
         if(!Environment.getExternalStorageState().equals("mounted")) {
            this.form.dispatchErrorOccurredEvent(this, "Start", 705, new Object[0]);
         } else {
            try {
               this.controller = new SoundRecorder.RecordingController(this.savedRecording);
            } catch (Throwable var3) {
               this.form.dispatchErrorOccurredEvent(this, "Start", 802, new Object[]{var3.getMessage()});
               return;
            }

            try {
               this.controller.start();
            } catch (Throwable var2) {
               this.controller = null;
               this.form.dispatchErrorOccurredEvent(this, "Start", 802, new Object[]{var2.getMessage()});
               return;
            }

            this.StartedRecording();
         }
      }
   }

   @SimpleEvent(
      description = "Indicates that the recorder has started, and can be stopped."
   )
   public void StartedRecording() {
      EventDispatcher.dispatchEvent(this, "StartedRecording", new Object[0]);
   }

   @SimpleFunction
   public void Stop() {
      if(this.controller == null) {
         Log.i("SoundRecorder", "Stop() called, but already stopped.");
      } else {
         try {
            Log.i("SoundRecorder", "Stop() called");
            Log.i("SoundRecorder", "stopping");
            this.controller.stop();
            Log.i("SoundRecorder", "Firing AfterSoundRecorded with " + this.controller.file);
            this.AfterSoundRecorded(this.controller.file);
            return;
         } catch (Throwable var4) {
            this.form.dispatchErrorOccurredEvent(this, "Stop", 801, new Object[0]);
         } finally {
            this.controller = null;
            this.StoppedRecording();
         }

      }
   }

   @SimpleEvent(
      description = "Indicates that the recorder has stopped, and can be started again."
   )
   public void StoppedRecording() {
      EventDispatcher.dispatchEvent(this, "StoppedRecording", new Object[0]);
   }

   public void onError(MediaRecorder var1, int var2, int var3) {
      if(this.controller != null && var1 == this.controller.recorder) {
         this.form.dispatchErrorOccurredEvent(this, "onError", 801, new Object[0]);

         try {
            this.controller.stop();
            return;
         } catch (Throwable var6) {
            Log.w("SoundRecorder", var6.getMessage());
         } finally {
            this.controller = null;
            this.StoppedRecording();
         }

      } else {
         Log.w("SoundRecorder", "onError called with wrong recorder. Ignoring.");
      }
   }

   public void onInfo(MediaRecorder var1, int var2, int var3) {
      if(this.controller != null && var1 == this.controller.recorder) {
         Log.i("SoundRecorder", "Recoverable condition while recording. Will attempt to stop normally.");
         this.controller.recorder.stop();
      } else {
         Log.w("SoundRecorder", "onInfo called with wrong recorder. Ignoring.");
      }
   }

   private class RecordingController {

      final String file;
      final MediaRecorder recorder;


      RecordingController(String var2) throws IOException {
         String var3 = var2;
         if(var2.equals("")) {
            var3 = FileUtil.getRecordingFile("3gp").getAbsolutePath();
         }

         this.file = var3;
         this.recorder = new MediaRecorder();
         this.recorder.setAudioSource(1);
         this.recorder.setOutputFormat(1);
         this.recorder.setAudioEncoder(1);
         Log.i("SoundRecorder", "Setting output file to " + this.file);
         this.recorder.setOutputFile(this.file);
         Log.i("SoundRecorder", "preparing");
         this.recorder.prepare();
         this.recorder.setOnErrorListener(SoundRecorder.this);
         this.recorder.setOnInfoListener(SoundRecorder.this);
      }

      void start() throws IllegalStateException {
         Log.i("SoundRecorder", "starting");

         try {
            this.recorder.start();
         } catch (IllegalStateException var2) {
            Log.e("SoundRecorder", "got IllegalStateException. Are there two recorders running?", var2);
            throw new IllegalStateException("Is there another recording running?");
         }
      }

      void stop() {
         this.recorder.setOnErrorListener((OnErrorListener)null);
         this.recorder.setOnInfoListener((OnInfoListener)null);
         this.recorder.stop();
         this.recorder.reset();
         this.recorder.release();
      }
   }
}
