package com.google.appinventor.components.runtime;

import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Handler;
import android.os.Vibrator;
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
import com.google.appinventor.components.runtime.Deleteable;
import com.google.appinventor.components.runtime.OnDestroyListener;
import com.google.appinventor.components.runtime.OnResumeListener;
import com.google.appinventor.components.runtime.OnStopListener;
import com.google.appinventor.components.runtime.util.SdkLevel;
import java.util.HashMap;
import java.util.Map;

@DesignerComponent(
   category = ComponentCategory.MEDIA,
   description = "<p>A multimedia component that plays sound files and optionally vibrates for the number of milliseconds (thousandths of a second) specified in the Blocks Editor.  The name of the sound file to play can be specified either in the Designer or in the Blocks Editor.</p> <p>For supported sound file formats, see <a href=\"http://developer.android.com/guide/appendix/media-formats.html\" target=\"_blank\">Android Supported Media Formats</a>.</p><p>This <code>Sound</code> component is best for short sound files, such as sound effects, while the <code>Player</code> component is more efficient for longer sounds, such as songs.</p><p>You might get an error if you attempt to play a sound immeditely after setting the source.</p>",
   iconName = "images/soundEffect.png",
   nonVisible = true,
   version = 3
)
@SimpleObject
@UsesPermissions(
   permissionNames = "android.permission.VIBRATE, android.permission.INTERNET"
)
public class Sound extends AndroidNonvisibleComponent implements Component, OnResumeListener, OnStopListener, OnDestroyListener, Deleteable {

   private static final int LOOP_MODE_NO_LOOP = 0;
   private static final int MAX_PLAY_DELAY_RETRIES = 10;
   private static final int MAX_STREAMS = 10;
   private static final float PLAYBACK_RATE_NORMAL = 1.0F;
   private static final int PLAY_DELAY_LENGTH = 50;
   private static final float VOLUME_FULL = 1.0F;
   private int delayRetries;
   private boolean loadComplete;
   private int minimumInterval;
   private final Handler playWaitHandler;
   private int soundId;
   private final Map soundMap;
   private SoundPool soundPool;
   private String sourcePath;
   private int streamId;
   private final Component thisComponent;
   private long timeLastPlayed;
   private final Vibrator vibe;
   private final boolean waitForLoadToComplete;


   public Sound(ComponentContainer var1) {
      super(var1.$form());
      boolean var2;
      if(SdkLevel.getLevel() >= 8) {
         var2 = true;
      } else {
         var2 = false;
      }

      this.waitForLoadToComplete = var2;
      this.playWaitHandler = new Handler();
      this.thisComponent = this;
      this.soundPool = new SoundPool(10, 3, 0);
      this.soundMap = new HashMap();
      this.vibe = (Vibrator)this.form.getSystemService("vibrator");
      this.sourcePath = "";
      this.loadComplete = true;
      this.form.registerForOnResume(this);
      this.form.registerForOnStop(this);
      this.form.registerForOnDestroy(this);
      this.form.setVolumeControlStream(3);
      this.MinimumInterval(500);
      if(this.waitForLoadToComplete) {
         (new Sound.OnLoadHelper(null)).setOnloadCompleteListener(this.soundPool);
      }

   }

   // $FF: synthetic method
   static int access$310(Sound var0) {
      int var1 = var0.delayRetries;
      var0.delayRetries = var1 - 1;
      return var1;
   }

   private void playAndCheckResult() {
      this.streamId = this.soundPool.play(this.soundId, 1.0F, 1.0F, 0, 0, 1.0F);
      Log.i("Sound", "SoundPool.play returned stream id " + this.streamId);
      if(this.streamId == 0) {
         this.form.dispatchErrorOccurredEvent(this, "Play", 703, new Object[]{this.sourcePath});
      }

   }

   private void playWhenLoadComplete() {
      if(!this.loadComplete && this.waitForLoadToComplete) {
         Log.i("Sound", "Sound not ready:  retrying.  Remaining retries = " + this.delayRetries);
         this.playWaitHandler.postDelayed(new Runnable() {
            public void run() {
               if(Sound.this.loadComplete) {
                  Sound.this.playAndCheckResult();
               } else if(Sound.this.delayRetries > 0) {
                  Sound.access$310(Sound.this);
                  Sound.this.playWhenLoadComplete();
               } else {
                  Sound.this.form.dispatchErrorOccurredEvent(Sound.this.thisComponent, "Play", 710, new Object[]{Sound.this.sourcePath});
               }
            }
         }, 50L);
      } else {
         this.playAndCheckResult();
      }
   }

   private void prepareToDie() {
      if(this.streamId != 0) {
         this.soundPool.stop(this.streamId);
         this.soundPool.unload(this.streamId);
      }

      this.soundPool.release();
      this.vibe.cancel();
      this.soundPool = null;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The minimum interval between sounds.  If you play a sound, all further Play() calls will be ignored until the interval has elapsed."
   )
   public int MinimumInterval() {
      return this.minimumInterval;
   }

   @DesignerProperty(
      defaultValue = "500",
      editorType = "non_negative_integer"
   )
   @SimpleProperty
   public void MinimumInterval(int var1) {
      this.minimumInterval = var1;
   }

   @SimpleFunction(
      description = "Pauses playing the sound if it is being played."
   )
   public void Pause() {
      if(this.streamId != 0) {
         this.soundPool.pause(this.streamId);
      } else {
         Log.i("Sound", "Unable to pause. Did you remember to call the Play function?");
      }
   }

   @SimpleFunction(
      description = "Plays the sound specified by the Source property."
   )
   public void Play() {
      if(this.soundId != 0) {
         long var1 = System.currentTimeMillis();
         if(this.timeLastPlayed != 0L && var1 < this.timeLastPlayed + (long)this.minimumInterval) {
            Log.i("Sound", "Unable to play because MinimumInterval has not elapsed since last play.");
         } else {
            this.timeLastPlayed = var1;
            this.delayRetries = 10;
            this.playWhenLoadComplete();
         }
      } else {
         Log.i("Sound", "Sound Id was 0. Did you remember to set the Source property?");
         this.form.dispatchErrorOccurredEvent(this, "Play", 703, new Object[]{this.sourcePath});
      }
   }

   @SimpleFunction(
      description = "Resumes playing the sound after a pause."
   )
   public void Resume() {
      if(this.streamId != 0) {
         this.soundPool.resume(this.streamId);
      } else {
         Log.i("Sound", "Unable to resume. Did you remember to call the Play function?");
      }
   }

   @SimpleEvent(
      description = "The SoundError event is no longer used. Please use the Screen.ErrorOccurred event instead.",
      userVisible = false
   )
   public void SoundError(String var1) {
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The name of the sound file.  Only certain formats are supported.  See http://developer.android.com/guide/appendix/media-formats.html."
   )
   public String Source() {
      return this.sourcePath;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "asset"
   )
   @SimpleProperty
   public void Source(String param1) {
      // $FF: Couldn't be decompiled
   }

   @SimpleFunction(
      description = "Stops playing the sound if it is being played."
   )
   public void Stop() {
      if(this.streamId != 0) {
         this.soundPool.stop(this.streamId);
         this.streamId = 0;
      } else {
         Log.i("Sound", "Unable to stop. Did you remember to call the Play function?");
      }
   }

   @SimpleFunction(
      description = "Vibrates for the specified number of milliseconds."
   )
   public void Vibrate(int var1) {
      this.vibe.vibrate((long)var1);
   }

   public void onDelete() {
      this.prepareToDie();
   }

   public void onDestroy() {
      this.prepareToDie();
   }

   public void onResume() {
      Log.i("Sound", "Got onResume");
      if(this.streamId != 0) {
         this.soundPool.resume(this.streamId);
      }

   }

   public void onStop() {
      Log.i("Sound", "Got onStop");
      if(this.streamId != 0) {
         this.soundPool.pause(this.streamId);
      }

   }

   private class OnLoadHelper {

      private OnLoadHelper() {
      }

      // $FF: synthetic method
      OnLoadHelper(Object var2) {
         this();
      }

      public void setOnloadCompleteListener(SoundPool var1) {
         var1.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            public void onLoadComplete(SoundPool var1, int var2, int var3) {
               Sound.this.loadComplete = true;
            }
         });
      }
   }
}
