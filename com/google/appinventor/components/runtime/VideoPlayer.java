package com.google.appinventor.components.runtime;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.MediaController;
import android.widget.VideoView;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Deleteable;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.OnDestroyListener;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.SdkLevel;
import java.io.IOException;

@DesignerComponent(
   category = ComponentCategory.MEDIA,
   description = "A multimedia component capable of playing videos. When the application is run, the VideoPlayer will be displayed as a rectangle on-screen.  If the user touches the rectangle, controls will appear to play/pause, skip ahead, and skip backward within the video.  The application can also control behavior by calling the <code>Start</code>, <code>Pause</code>, and <code>SeekTo</code> methods.  <p>Video files should be in 3GPP (.3gp) or MPEG-4 (.mp4) formats.  For more details about legal formats, see <a href=\"http://developer.android.com/guide/appendix/media-formats.html\" target=\"_blank\">Android Supported Media Formats</a>.</p><p>App Inventor for Android only permits video files under 1 MB and limits the total size of an application to 5 MB, not all of which is available for media (video, audio, and sound) files.  If your media files are too large, you may get errors when packaging or installing your application, in which case you should reduce the number of media files or their sizes.  Most video editing software, such as Windows Movie Maker and Apple iMovie, can help you decrease the size of videos by shortening them or re-encoding the video into a more compact format.</p><p>You can also set the media source to a URL that points to a streaming video, but the URL must point to the video file itself, not to a program that plays the video.",
   version = 5
)
@SimpleObject
@UsesPermissions(
   permissionNames = "android.permission.INTERNET"
)
public final class VideoPlayer extends AndroidViewComponent implements OnDestroyListener, Deleteable, OnCompletionListener, OnErrorListener, OnPreparedListener {

   private boolean delayedStart = false;
   private boolean inFullScreen = false;
   private MediaPlayer mPlayer;
   private boolean mediaReady = false;
   private String sourcePath;
   private final VideoPlayer.ResizableVideoView videoView;


   public VideoPlayer(ComponentContainer var1) {
      super(var1);
      var1.$form().registerForOnDestroy(this);
      this.videoView = new VideoPlayer.ResizableVideoView(var1.$context());
      this.videoView.setMediaController(new MediaController(var1.$context()));
      this.videoView.setOnCompletionListener(this);
      this.videoView.setOnErrorListener(this);
      this.videoView.setOnPreparedListener(this);
      var1.$add(this);
      var1.setChildWidth(this, 176);
      var1.setChildHeight(this, 144);
      var1.$form().setVolumeControlStream(3);
      this.sourcePath = "";
   }

   private void prepareToDie() {
      if(this.videoView.isPlaying()) {
         this.videoView.stopPlayback();
      }

      this.videoView.setVideoURI((Uri)null);
      this.videoView.clearAnimation();
      this.delayedStart = false;
      this.mediaReady = false;
      if(this.inFullScreen) {
         Bundle var1 = new Bundle();
         var1.putBoolean("FullScreenKey", false);
         this.container.$form().fullScreenVideoAction(195, this, var1);
      }

   }

   @SimpleEvent
   public void Completed() {
      EventDispatcher.dispatchEvent(this, "Completed", new Object[0]);
   }

   @SimpleProperty(
      userVisible = true
   )
   public void FullScreen(boolean var1) {
      if(var1 && SdkLevel.getLevel() <= 4) {
         this.container.$form().dispatchErrorOccurredEvent(this, "FullScreen(true)", 1303, new Object[0]);
      } else if(var1 != this.inFullScreen) {
         Bundle var2;
         if(var1) {
            var2 = new Bundle();
            var2.putInt("PositionKey", this.videoView.getCurrentPosition());
            var2.putBoolean("PlayingKey", this.videoView.isPlaying());
            this.videoView.pause();
            var2.putBoolean("FullScreenKey", true);
            var2.putString("SourceKey", this.sourcePath);
            if(this.container.$form().fullScreenVideoAction(195, this, var2).getBoolean("ActionSuccess")) {
               this.inFullScreen = true;
               return;
            }

            this.inFullScreen = false;
            this.container.$form().dispatchErrorOccurredEvent(this, "FullScreen", 1301, new Object[]{""});
            return;
         }

         var2 = new Bundle();
         var2.putBoolean("FullScreenKey", false);
         var2 = this.container.$form().fullScreenVideoAction(195, this, var2);
         if(var2.getBoolean("ActionSuccess")) {
            this.fullScreenKilled(var2);
            return;
         }

         this.inFullScreen = true;
         this.container.$form().dispatchErrorOccurredEvent(this, "FullScreen", 1302, new Object[]{""});
         return;
      }

   }

   @SimpleProperty
   public boolean FullScreen() {
      return this.inFullScreen;
   }

   @SimpleFunction(
      description = "Returns duration of the video in milliseconds."
   )
   public int GetDuration() {
      Log.i("VideoPlayer", "Calling GetDuration");
      if(this.inFullScreen) {
         Bundle var1 = this.container.$form().fullScreenVideoAction(196, this, (Object)null);
         return var1.getBoolean("ActionSuccess")?var1.getInt("ActionData"):0;
      } else {
         return this.videoView.getDuration();
      }
   }

   @SimpleProperty
   public int Height() {
      return super.Height();
   }

   @SimpleProperty(
      userVisible = true
   )
   public void Height(int var1) {
      super.Height(var1);
      this.videoView.changeVideoSize(this.videoView.forcedWidth, var1);
   }

   @SimpleFunction(
      description = "Pauses playback of the video.  Playback can be resumed at the same location by calling the <code>Start</code> method."
   )
   public void Pause() {
      Log.i("VideoPlayer", "Calling Pause");
      if(this.inFullScreen) {
         this.container.$form().fullScreenVideoAction(192, this, (Object)null);
         this.delayedStart = false;
      } else {
         this.delayedStart = false;
         this.videoView.pause();
      }
   }

   @SimpleFunction(
      description = "Seeks to the requested time (specified in milliseconds) in the video. Note that if the video is paused, the frame shown will not be updated by the seek. "
   )
   public void SeekTo(int var1) {
      Log.i("VideoPlayer", "Calling SeekTo");
      int var2 = var1;
      if(var1 < 0) {
         var2 = 0;
      }

      if(this.inFullScreen) {
         this.container.$form().fullScreenVideoAction(190, this, Integer.valueOf(var2));
      } else {
         this.videoView.seekTo(var2);
      }
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "asset"
   )
   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The \"path\" to the video.  Usually, this will be the name of the video file, which should be added in the Designer."
   )
   public void Source(String var1) {
      if(this.inFullScreen) {
         this.container.$form().fullScreenVideoAction(194, this, var1);
      } else {
         String var2 = var1;
         if(var1 == null) {
            var2 = "";
         }

         this.sourcePath = var2;
         this.videoView.invalidateMediaPlayer(true);
         if(this.videoView.isPlaying()) {
            this.videoView.stopPlayback();
         }

         this.videoView.setVideoURI((Uri)null);
         this.videoView.clearAnimation();
         if(this.sourcePath.length() > 0) {
            Log.i("VideoPlayer", "Source path is " + this.sourcePath);

            try {
               this.mediaReady = false;
               MediaUtil.loadVideoView(this.videoView, this.container.$form(), this.sourcePath);
            } catch (IOException var3) {
               this.container.$form().dispatchErrorOccurredEvent(this, "Source", 701, new Object[]{this.sourcePath});
               return;
            }

            Log.i("VideoPlayer", "loading video succeeded");
            return;
         }
      }

   }

   @SimpleFunction(
      description = "Starts playback of the video."
   )
   public void Start() {
      Log.i("VideoPlayer", "Calling Start");
      if(this.inFullScreen) {
         this.container.$form().fullScreenVideoAction(191, this, (Object)null);
      } else if(this.mediaReady) {
         this.videoView.start();
      } else {
         this.delayedStart = true;
      }
   }

   @SimpleEvent(
      description = "The VideoPlayerError event is no longer used. Please use the Screen.ErrorOccurred event instead.",
      userVisible = false
   )
   public void VideoPlayerError(String var1) {
   }

   @DesignerProperty(
      defaultValue = "50",
      editorType = "non_negative_float"
   )
   @SimpleProperty(
      description = "Sets the volume to a number between 0 and 100. Values less than 0 will be treated as 0, and values greater than 100 will be treated as 100."
   )
   public void Volume(int var1) {
      var1 = Math.min(Math.max(var1, 0), 100);
      if(this.mPlayer != null) {
         this.mPlayer.setVolume((float)var1 / 100.0F, (float)var1 / 100.0F);
      }

   }

   @SimpleProperty
   public int Width() {
      return super.Width();
   }

   @SimpleProperty(
      userVisible = true
   )
   public void Width(int var1) {
      super.Width(var1);
      this.videoView.changeVideoSize(var1, this.videoView.forcedHeight);
   }

   public void delayedStart() {
      this.delayedStart = true;
      this.Start();
   }

   public void fullScreenKilled(Bundle var1) {
      this.inFullScreen = false;
      String var2 = var1.getString("SourceKey");
      if(!var2.equals(this.sourcePath)) {
         this.Source(var2);
      }

      this.videoView.setVisibility(0);
      this.videoView.requestLayout();
      this.SeekTo(var1.getInt("PositionKey"));
      if(var1.getBoolean("PlayingKey")) {
         this.Start();
      }

   }

   public int getPassedHeight() {
      return this.videoView.forcedHeight;
   }

   public int getPassedWidth() {
      return this.videoView.forcedWidth;
   }

   public View getView() {
      return this.videoView;
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

   public boolean onError(MediaPlayer var1, int var2, int var3) {
      this.videoView.invalidateMediaPlayer(true);
      this.delayedStart = false;
      this.mediaReady = false;
      Log.e("VideoPlayer", "onError: what is " + var2 + " 0x" + Integer.toHexString(var2) + ", extra is " + var3 + " 0x" + Integer.toHexString(var3));
      this.container.$form().dispatchErrorOccurredEvent(this, "Source", 701, new Object[]{this.sourcePath});
      return true;
   }

   public void onPrepared(MediaPlayer var1) {
      this.mediaReady = true;
      this.delayedStart = false;
      this.mPlayer = var1;
      this.videoView.setMediaPlayer(this.mPlayer, true);
      if(this.delayedStart) {
         this.Start();
      }

   }

   class ResizableVideoView extends VideoView {

      public int forcedHeight = -1;
      public int forcedWidth = -1;
      private Boolean mFoundMediaPlayer = Boolean.valueOf(false);
      private MediaPlayer mVideoPlayer;


      public ResizableVideoView(Context var2) {
         super(var2);
      }

      public void changeVideoSize(int var1, int var2) {
         this.forcedWidth = var1;
         this.forcedHeight = var2;
         this.forceLayout();
         this.invalidate();
      }

      public void invalidateMediaPlayer(boolean var1) {
         this.mFoundMediaPlayer = Boolean.valueOf(false);
         this.mVideoPlayer = null;
         if(var1) {
            this.forceLayout();
            this.invalidate();
         }

      }

      public void onMeasure(int var1, int var2) {
         short var4;
         Log.i("VideoPlayer..onMeasure", "AI setting dimensions as:" + this.forcedWidth + ":" + this.forcedHeight);
         Log.i("VideoPlayer..onMeasure", "Dimenions from super>>" + MeasureSpec.getSize(var1) + ":" + MeasureSpec.getSize(var2));
         short var5 = 176;
         var4 = 144;
         label41:
         switch(this.forcedWidth) {
         case -2:
            switch(MeasureSpec.getMode(var1)) {
            case Integer.MIN_VALUE:
            case 1073741824:
               var1 = MeasureSpec.getSize(var1);
               break label41;
            case 0:
               try {
                  var1 = ((View)this.getParent()).getMeasuredWidth();
               } catch (ClassCastException var8) {
                  var1 = 176;
               } catch (NullPointerException var9) {
                  var1 = 176;
               }
               break label41;
            default:
               var1 = var5;
               break label41;
            }
         case -1:
            var1 = var5;
            if(this.mFoundMediaPlayer.booleanValue()) {
               try {
                  var1 = this.mVideoPlayer.getVideoWidth();
                  Log.i("VideoPlayer.onMeasure", "Got width from MediaPlayer>" + var1);
               } catch (NullPointerException var7) {
                  Log.e("VideoPlayer..onMeasure", "Failed to get MediaPlayer for width:\n" + var7.getMessage());
                  var1 = 176;
               }
            }
            break;
         default:
            var1 = this.forcedWidth;
         }

         label35:
         switch(this.forcedHeight) {
         case -2:
            switch(MeasureSpec.getMode(var2)) {
            case Integer.MIN_VALUE:
            case 1073741824:
               var2 = MeasureSpec.getSize(var2);
               break label35;
            default:
               var2 = var4;
               break label35;
            }
         case -1:
            var2 = var4;
            if(this.mFoundMediaPlayer.booleanValue()) {
               try {
                  var2 = this.mVideoPlayer.getVideoHeight();
                  Log.i("VideoPlayer.onMeasure", "Got height from MediaPlayer>" + var2);
               } catch (NullPointerException var6) {
                  Log.e("VideoPlayer..onMeasure", "Failed to get MediaPlayer for height:\n" + var6.getMessage());
                  var2 = 144;
               }
            }
            break;
         default:
            var2 = this.forcedHeight;
         }

         Log.i("VideoPlayer.onMeasure", "Setting dimensions to:" + var1 + "x" + var2);
         this.getHolder().setFixedSize(var1, var2);
         this.setMeasuredDimension(var1, var2);
      }

      public void setMediaPlayer(MediaPlayer var1, boolean var2) {
         this.mVideoPlayer = var1;
         this.mFoundMediaPlayer = Boolean.valueOf(true);
         if(var2) {
            this.forceLayout();
            this.invalidate();
         }

      }
   }
}
