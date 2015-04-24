package com.google.appinventor.components.runtime.util;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.VideoView;
import android.widget.FrameLayout.LayoutParams;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.VideoPlayer;
import com.google.appinventor.components.runtime.util.CustomMediaController;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.SdkLevel;
import java.io.IOException;

public class FullScreenVideoUtil implements OnCompletionListener, OnPreparedListener {

   public static final String ACTION_DATA = "ActionData";
   public static final String ACTION_SUCESS = "ActionSuccess";
   public static final int FULLSCREEN_VIDEO_ACTION_DURATION = 196;
   public static final int FULLSCREEN_VIDEO_ACTION_FULLSCREEN = 195;
   public static final int FULLSCREEN_VIDEO_ACTION_PAUSE = 192;
   public static final int FULLSCREEN_VIDEO_ACTION_PLAY = 191;
   public static final int FULLSCREEN_VIDEO_ACTION_SEEK = 190;
   public static final int FULLSCREEN_VIDEO_ACTION_SOURCE = 194;
   public static final int FULLSCREEN_VIDEO_ACTION_STOP = 193;
   public static final int FULLSCREEN_VIDEO_DIALOG_FLAG = 189;
   public static final String VIDEOPLAYER_FULLSCREEN = "FullScreenKey";
   public static final String VIDEOPLAYER_PLAYING = "PlayingKey";
   public static final String VIDEOPLAYER_POSITION = "PositionKey";
   public static final String VIDEOPLAYER_SOURCE = "SourceKey";
   private Form mForm;
   private VideoPlayer mFullScreenPlayer = null;
   private Bundle mFullScreenVideoBundle;
   private CustomMediaController mFullScreenVideoController;
   private Dialog mFullScreenVideoDialog;
   private FrameLayout mFullScreenVideoHolder;
   private VideoView mFullScreenVideoView;
   private Handler mHandler;
   private LayoutParams mMediaControllerParams = new LayoutParams(-1, -2, 80);


   public FullScreenVideoUtil(Form var1, Handler var2) {
      this.mForm = var1;
      this.mHandler = var2;
      if(SdkLevel.getLevel() > 4) {
         this.mFullScreenVideoDialog = new Dialog(this.mForm, 16973831) {
            public void onBackPressed() {
               Bundle var1 = new Bundle();
               var1.putInt("PositionKey", FullScreenVideoUtil.this.mFullScreenVideoView.getCurrentPosition());
               var1.putBoolean("PlayingKey", FullScreenVideoUtil.this.mFullScreenVideoView.isPlaying());
               var1.putString("SourceKey", FullScreenVideoUtil.this.mFullScreenVideoBundle.getString("SourceKey"));
               FullScreenVideoUtil.this.mFullScreenPlayer.fullScreenKilled(var1);
               super.onBackPressed();
            }
            public void onStart() {
               super.onStart();
               FullScreenVideoUtil.this.startDialog();
            }
         };
      } else {
         this.mFullScreenVideoDialog = new Dialog(this.mForm, 16973831) {
            public void onStart() {
               super.onStart();
               FullScreenVideoUtil.this.startDialog();
            }
            protected void onStop() {
               Bundle var1 = new Bundle();
               var1.putInt("PositionKey", FullScreenVideoUtil.this.mFullScreenVideoView.getCurrentPosition());
               var1.putBoolean("PlayingKey", FullScreenVideoUtil.this.mFullScreenVideoView.isPlaying());
               var1.putString("SourceKey", FullScreenVideoUtil.this.mFullScreenVideoBundle.getString("SourceKey"));
               FullScreenVideoUtil.this.mFullScreenPlayer.fullScreenKilled(var1);
               super.onStop();
            }
         };
      }
   }

   private Bundle doFullScreenVideoAction(VideoPlayer var1, Bundle var2) {
      Log.i("Form.doFullScreenVideoAction", "Source:" + var1 + " Data:" + var2);
      Bundle var3 = new Bundle();
      var3.putBoolean("ActionSuccess", true);
      if(var2.getBoolean("FullScreenKey")) {
         this.mFullScreenPlayer = var1;
         this.mFullScreenVideoBundle = var2;
         if(!this.mFullScreenVideoDialog.isShowing()) {
            this.mForm.showDialog(189);
            return var3;
         } else {
            this.mFullScreenVideoView.pause();
            var3.putBoolean("ActionSuccess", this.setSource(this.mFullScreenVideoBundle.getString("SourceKey"), false));
            return var3;
         }
      } else if(this.showing()) {
         var3.putBoolean("PlayingKey", this.mFullScreenVideoView.isPlaying());
         var3.putInt("PositionKey", this.mFullScreenVideoView.getCurrentPosition());
         var3.putString("SourceKey", this.mFullScreenVideoBundle.getString("SourceKey"));
         this.mFullScreenPlayer = null;
         this.mFullScreenVideoBundle = null;
         this.mForm.dismissDialog(189);
         return var3;
      } else {
         var3.putBoolean("ActionSuccess", false);
         return var3;
      }
   }

   public Dialog createFullScreenVideoDialog() {
      if(this.mFullScreenVideoBundle == null) {
         Log.i("Form.createFullScreenVideoDialog", "mFullScreenVideoBundle is null");
      }

      this.mFullScreenVideoView = new VideoView(this.mForm);
      this.mFullScreenVideoHolder = new FrameLayout(this.mForm);
      this.mFullScreenVideoController = new CustomMediaController(this.mForm);
      this.mFullScreenVideoView.setId(this.mFullScreenVideoView.hashCode());
      this.mFullScreenVideoHolder.setId(this.mFullScreenVideoHolder.hashCode());
      this.mFullScreenVideoView.setMediaController(this.mFullScreenVideoController);
      this.mFullScreenVideoView.setOnTouchListener(new OnTouchListener() {
         public boolean onTouch(View var1, MotionEvent var2) {
            Log.i("FullScreenVideoUtil..onTouch", "Video Touched!!");
            return false;
         }
      });
      this.mFullScreenVideoController.setAnchorView(this.mFullScreenVideoView);
      String var1 = this.mForm.ScreenOrientation();
      if(!var1.equals("landscape") && !var1.equals("sensorLandscape") && !var1.equals("reverseLandscape")) {
         this.mFullScreenVideoView.setLayoutParams(new LayoutParams(-1, -2, 17));
      } else {
         this.mFullScreenVideoView.setLayoutParams(new LayoutParams(-2, -1, 17));
      }

      this.mFullScreenVideoHolder.setLayoutParams(new android.view.ViewGroup.LayoutParams(-1, -1));
      this.mFullScreenVideoHolder.addView(this.mFullScreenVideoView);
      this.mFullScreenVideoController.addTo(this.mFullScreenVideoHolder, this.mMediaControllerParams);
      this.mFullScreenVideoDialog.setContentView(this.mFullScreenVideoHolder);
      return this.mFullScreenVideoDialog;
   }

   public boolean dialogInitialized() {
      return this.mFullScreenVideoDialog != null;
   }

   public void onCompletion(MediaPlayer var1) {
      if(this.mFullScreenPlayer != null) {
         this.mFullScreenPlayer.Completed();
      }

   }

   public void onPrepared(MediaPlayer var1) {
      Log.i("FullScreenVideoUtil..onPrepared", "Seeking to:" + this.mFullScreenVideoBundle.getInt("PositionKey"));
      this.mFullScreenVideoView.seekTo(this.mFullScreenVideoBundle.getInt("PositionKey"));
      if(this.mFullScreenVideoBundle.getBoolean("PlayingKey")) {
         this.mFullScreenVideoView.start();
      } else {
         this.mFullScreenVideoView.start();
         this.mHandler.postDelayed(new Runnable() {
            public void run() {
               FullScreenVideoUtil.this.mFullScreenVideoView.pause();
            }
         }, 100L);
      }
   }

   public Bundle performAction(int param1, VideoPlayer param2, Object param3) {
      // $FF: Couldn't be decompiled
   }

   public void prepareFullScreenVideoDialog(Dialog var1) {
      this.mFullScreenVideoView.setOnPreparedListener(this);
      this.mFullScreenVideoView.setOnCompletionListener(this);
   }

   public boolean setSource(String param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   public boolean showing() {
      return this.dialogInitialized() && this.mFullScreenVideoDialog.isShowing();
   }

   public void startDialog() {
      try {
         MediaUtil.loadVideoView(this.mFullScreenVideoView, this.mForm, this.mFullScreenVideoBundle.getString("SourceKey"));
      } catch (IOException var2) {
         this.mForm.dispatchErrorOccurredEvent(this.mFullScreenPlayer, "Source", 701, new Object[]{this.mFullScreenVideoBundle.getString("SourceKey")});
      }
   }
}
