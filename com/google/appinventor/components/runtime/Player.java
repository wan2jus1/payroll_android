package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Vibrator;
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
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.OnDestroyListener;
import com.google.appinventor.components.runtime.OnPauseListener;
import com.google.appinventor.components.runtime.OnResumeListener;
import com.google.appinventor.components.runtime.OnStopListener;
import com.google.appinventor.components.runtime.errors.IllegalArgumentError;
import com.google.appinventor.components.runtime.util.FroyoUtil;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.SdkLevel;
import java.io.IOException;

@DesignerComponent(
   category = ComponentCategory.MEDIA,
   description = "Multimedia component that plays audio and controls phone vibration.  The name of a multimedia field is specified in the <code>Source</code> property, which can be set in the Designer or in the Blocks Editor.  The length of time for a vibration is specified in the Blocks Editor in milliseconds (thousandths of a second).\n<p>For supported audio formats, see <a href=\"http://developer.android.com/guide/appendix/media-formats.html\" target=\"_blank\">Android Supported Media Formats</a>.</p>\n<p>This component is best for long sound files, such as songs, while the <code>Sound</code> component is more efficient for short files, such as sound effects.</p>",
   iconName = "images/player.png",
   nonVisible = true,
   version = 6
)
@SimpleObject
@UsesPermissions(
   permissionNames = "android.permission.VIBRATE, android.permission.INTERNET"
)
public final class Player extends AndroidNonvisibleComponent implements Component, OnCompletionListener, OnPauseListener, OnResumeListener, OnDestroyListener, OnStopListener, Deleteable {

   private static final boolean audioFocusSupported;
   private final Activity activity;
   private Object afChangeListener;
   private AudioManager am;
   private boolean focusOn;
   private boolean loop;
   private boolean playOnlyInForeground;
   private MediaPlayer player;
   public int playerState;
   private String sourcePath;
   private final Vibrator vibe;


   static {
      if(SdkLevel.getLevel() >= 8) {
         audioFocusSupported = true;
      } else {
         audioFocusSupported = false;
      }
   }

   public Player(ComponentContainer var1) {
      Object var2 = null;
      super(var1.$form());
      this.activity = var1.$context();
      this.sourcePath = "";
      this.vibe = (Vibrator)this.form.getSystemService("vibrator");
      this.form.registerForOnDestroy(this);
      this.form.registerForOnResume(this);
      this.form.registerForOnPause(this);
      this.form.registerForOnStop(this);
      this.form.setVolumeControlStream(3);
      this.loop = false;
      this.playOnlyInForeground = false;
      this.focusOn = false;
      AudioManager var3;
      if(audioFocusSupported) {
         var3 = FroyoUtil.setAudioManager(this.activity);
      } else {
         var3 = null;
      }

      this.am = var3;
      Object var4 = var2;
      if(audioFocusSupported) {
         var4 = FroyoUtil.setAudioFocusChangeListener(this);
      }

      this.afChangeListener = var4;
   }

   private void abandonFocus() {
      FroyoUtil.abandonFocus(this.am, this.afChangeListener);
      this.focusOn = false;
   }

   private void prepare() {
      try {
         this.player.prepare();
         this.playerState = 1;
      } catch (IOException var2) {
         this.player.release();
         this.player = null;
         this.playerState = 0;
         this.form.dispatchErrorOccurredEvent(this, "Source", 702, new Object[]{this.sourcePath});
      }
   }

   private void prepareToDie() {
      if(audioFocusSupported && this.focusOn) {
         this.abandonFocus();
      }

      if(this.playerState != 0) {
         this.player.stop();
      }

      this.playerState = 0;
      if(this.player != null) {
         this.player.release();
         this.player = null;
      }

      this.vibe.cancel();
   }

   private void requestPermanentFocus() {
      boolean var1;
      if(FroyoUtil.focusRequestGranted(this.am, this.afChangeListener)) {
         var1 = true;
      } else {
         var1 = false;
      }

      this.focusOn = var1;
      if(!this.focusOn) {
         this.form.dispatchErrorOccurredEvent(this, "Source", 709, new Object[]{this.sourcePath});
      }

   }

   @SimpleEvent
   public void Completed() {
      EventDispatcher.dispatchEvent(this, "Completed", new Object[0]);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Reports whether the media is playing"
   )
   public boolean IsPlaying() {
      return this.playerState != 1 && this.playerState != 2?false:this.player.isPlaying();
   }

   @DesignerProperty(
      defaultValue = "False",
      editorType = "boolean"
   )
   @SimpleProperty
   public void Loop(boolean var1) {
      if(this.playerState == 1 || this.playerState == 2 || this.playerState == 3) {
         this.player.setLooping(var1);
      }

      this.loop = var1;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "If true, the player will loop when it plays. Setting Loop while the player is playing will affect the current playing."
   )
   public boolean Loop() {
      return this.loop;
   }

   @SimpleEvent(
      description = "This event is signaled when another player has started (and the current player is playing or paused, but not stopped)."
   )
   public void OtherPlayerStarted() {
      EventDispatcher.dispatchEvent(this, "OtherPlayerStarted", new Object[0]);
   }

   @SimpleFunction
   public void Pause() {
      if(this.player != null) {
         boolean var1 = this.player.isPlaying();
         if(this.playerState == 2) {
            this.player.pause();
            if(var1) {
               this.playerState = 3;
               return;
            }
         }
      }

   }

   @DesignerProperty(
      defaultValue = "False",
      editorType = "boolean"
   )
   @SimpleProperty
   public void PlayOnlyInForeground(boolean var1) {
      this.playOnlyInForeground = var1;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "If true, the player will pause playing when leaving the current screen; if false (default option), the player continues playing whenever the current screen is displaying or not."
   )
   public boolean PlayOnlyInForeground() {
      return this.playOnlyInForeground;
   }

   @SimpleEvent(
      description = "The PlayerError event is no longer used. Please use the Screen.ErrorOccurred event instead.",
      userVisible = false
   )
   public void PlayerError(String var1) {
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String Source() {
      return this.sourcePath;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "asset"
   )
   @SimpleProperty
   public void Source(String var1) {
      String var2 = var1;
      if(var1 == null) {
         var2 = "";
      }

      this.sourcePath = var2;
      if(this.playerState == 1 || this.playerState == 2 || this.playerState == 3) {
         this.player.stop();
         this.playerState = 0;
      }

      if(this.player != null) {
         this.player.release();
         this.player = null;
      }

      if(this.sourcePath.length() > 0) {
         this.player = new MediaPlayer();
         this.player.setOnCompletionListener(this);

         try {
            MediaUtil.loadMediaPlayer(this.player, this.form, this.sourcePath);
         } catch (IOException var3) {
            this.player.release();
            this.player = null;
            this.form.dispatchErrorOccurredEvent(this, "Source", 701, new Object[]{this.sourcePath});
            return;
         }

         this.player.setAudioStreamType(3);
         if(audioFocusSupported) {
            this.requestPermanentFocus();
         }

         this.prepare();
      }

   }

   @SimpleFunction
   public void Start() {
      if(audioFocusSupported && !this.focusOn) {
         this.requestPermanentFocus();
      }

      if(this.playerState == 1 || this.playerState == 2 || this.playerState == 3 || this.playerState == 4) {
         this.player.setLooping(this.loop);
         this.player.start();
         this.playerState = 2;
      }

   }

   @SimpleFunction
   public void Stop() {
      if(audioFocusSupported && this.focusOn) {
         this.abandonFocus();
      }

      if(this.playerState == 2 || this.playerState == 3 || this.playerState == 4) {
         this.player.stop();
         this.prepare();
         this.player.seekTo(0);
      }

   }

   @SimpleFunction
   public void Vibrate(long var1) {
      this.vibe.vibrate(var1);
   }

   @DesignerProperty(
      defaultValue = "50",
      editorType = "non_negative_float"
   )
   @SimpleProperty(
      description = "Sets the volume to a number between 0 and 100"
   )
   public void Volume(int var1) {
      if(this.playerState == 1 || this.playerState == 2 || this.playerState == 3) {
         if(var1 > 100 || var1 < 0) {
            throw new IllegalArgumentError("Volume must be set to a number between 0 and 100");
         }

         this.player.setVolume((float)var1 / 100.0F, (float)var1 / 100.0F);
      }

   }

   public void onCompletion(MediaPlayer var1) {
      this.Completed();
   }

   public void onDelete() {
      this.prepareToDie();
   }

   public void onDestroy() {
      this.prepareToDie();
   }

   public void onPause() {
      if(this.player != null && this.playOnlyInForeground && this.player.isPlaying()) {
         this.pause();
      }
   }

   public void onResume() {
      if(this.playOnlyInForeground && this.playerState == 4) {
         this.Start();
      }

   }

   public void onStop() {
      if(this.player != null && this.playOnlyInForeground && this.player.isPlaying()) {
         this.pause();
      }
   }

   public void pause() {
      if(this.player != null && this.playerState == 2) {
         this.player.pause();
         this.playerState = 4;
      }
   }
}
