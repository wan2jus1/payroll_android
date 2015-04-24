package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.ActivityResultListener;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Deleteable;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.LinearLayout;
import com.google.appinventor.components.runtime.Notifier;
import com.google.appinventor.components.runtime.OnDestroyListener;
import com.google.appinventor.components.runtime.OnNewIntentListener;
import com.google.appinventor.components.runtime.OnPauseListener;
import com.google.appinventor.components.runtime.OnResumeListener;
import com.google.appinventor.components.runtime.OnStopListener;
import com.google.appinventor.components.runtime.ReplForm;
import com.google.appinventor.components.runtime.VideoPlayer;
import com.google.appinventor.components.runtime.collect.Lists;
import com.google.appinventor.components.runtime.collect.Maps;
import com.google.appinventor.components.runtime.collect.Sets;
import com.google.appinventor.components.runtime.util.AlignmentUtil;
import com.google.appinventor.components.runtime.util.AnimationUtil;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.FullScreenVideoUtil;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.OnInitializeListener;
import com.google.appinventor.components.runtime.util.SdkLevel;
import com.google.appinventor.components.runtime.util.ViewUtil;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

@DesignerComponent(
   category = ComponentCategory.LAYOUT,
   description = "Top-level component containing all other components in the program",
   showOnPalette = false,
   version = 14
)
@SimpleObject
@UsesPermissions(
   permissionNames = "android.permission.INTERNET,android.permission.ACCESS_WIFI_STATE,android.permission.ACCESS_NETWORK_STATE"
)
public class Form extends Activity implements Component, ComponentContainer, HandlesEventDispatching {

   public static final String APPINVENTOR_URL_SCHEME = "appinventor";
   private static final String ARGUMENT_NAME = "APP_INVENTOR_START";
   private static final String LOG_TAG = "Form";
   private static final String RESULT_NAME = "APP_INVENTOR_RESULT";
   private static final int SWITCH_FORM_REQUEST_CODE = 1;
   protected static Form activeForm;
   private static boolean applicationIsBeingClosed;
   private static long minimumToastWait = 10000000000L;
   private static int nextRequestCode = 2;
   private String aboutScreen;
   private final HashMap activityResultMap = Maps.newHashMap();
   private AlignmentUtil alignmentSetter;
   private final Handler androidUIHandler = new Handler();
   private int backgroundColor;
   private Drawable backgroundDrawable;
   private String backgroundImagePath = "";
   private String closeAnimType;
   protected String formName;
   private FrameLayout frameLayout;
   private FullScreenVideoUtil fullScreenVideoUtil;
   private int horizontalAlignment;
   private long lastToastTime;
   private String nextFormName;
   private final Set onDestroyListeners = Sets.newHashSet();
   private final Set onInitializeListeners = Sets.newHashSet();
   private final Set onNewIntentListeners = Sets.newHashSet();
   private final Set onPauseListeners = Sets.newHashSet();
   private final Set onResumeListeners = Sets.newHashSet();
   private final Set onStopListeners = Sets.newHashSet();
   private String openAnimType;
   private boolean screenInitialized;
   private boolean scrollable;
   protected String startupValue = "";
   private int verticalAlignment;
   private LinearLayout viewLayout;
   private String yandexTranslateTagline;


   public Form() {
      this.lastToastTime = System.nanoTime() - minimumToastWait;
      this.yandexTranslateTagline = "";
   }

   private void closeApplication() {
      applicationIsBeingClosed = true;
      this.finish();
      if(this.formName.equals("Screen1")) {
         System.exit(0);
      }

   }

   private void closeApplicationFromMenu() {
      this.closeApplication();
   }

   private static Object decodeJSONStringForForm(String param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   private void defaultPropertyValues() {
      this.Scrollable(false);
      this.BackgroundImage("");
      this.AboutScreen("");
      this.BackgroundColor(-1);
      this.AlignHorizontal(1);
      this.AlignVertical(1);
      this.Title("");
   }

   public static void finishActivity() {
      if(activeForm != null) {
         activeForm.closeForm((Intent)null);
      } else {
         throw new IllegalStateException("activeForm is null");
      }
   }

   public static void finishActivityWithResult(Object var0) {
      if(activeForm != null) {
         if(activeForm instanceof ReplForm) {
            ((ReplForm)activeForm).setResult(var0);
            activeForm.closeForm((Intent)null);
         } else {
            String var2 = jsonEncodeForForm(var0, "close screen with value");
            Intent var1 = new Intent();
            var1.putExtra("APP_INVENTOR_RESULT", var2);
            activeForm.closeForm(var1);
         }
      } else {
         throw new IllegalStateException("activeForm is null");
      }
   }

   public static void finishActivityWithTextResult(String var0) {
      if(activeForm != null) {
         Intent var1 = new Intent();
         var1.putExtra("APP_INVENTOR_RESULT", var0);
         activeForm.closeForm(var1);
      } else {
         throw new IllegalStateException("activeForm is null");
      }
   }

   public static void finishApplication() {
      if(activeForm != null) {
         activeForm.closeApplicationFromBlocks();
      } else {
         throw new IllegalStateException("activeForm is null");
      }
   }

   private static int generateNewRequestCode() {
      int var0 = nextRequestCode;
      nextRequestCode = var0 + 1;
      return var0;
   }

   public static Form getActiveForm() {
      return activeForm;
   }

   public static String getStartText() {
      if(activeForm != null) {
         return activeForm.startupValue;
      } else {
         throw new IllegalStateException("activeForm is null");
      }
   }

   public static Object getStartValue() {
      if(activeForm != null) {
         return decodeJSONStringForForm(activeForm.startupValue, "get start value");
      } else {
         throw new IllegalStateException("activeForm is null");
      }
   }

   protected static String jsonEncodeForForm(Object param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   private void showAboutApplicationNotification() {
      Notifier.oneButtonAlert(this, (this.aboutScreen + "<p><small><em>Invented with MIT App Inventor<br>appinventor.mit.edu</em></small></p>" + this.yandexTranslateTagline).replaceAll("\\n", "<br>"), "About this app", "Got it");
   }

   private void showExitApplicationNotification() {
      Runnable var1 = new Runnable() {
         public void run() {
            Form.this.closeApplicationFromMenu();
         }
      };
      Runnable var2 = new Runnable() {
         public void run() {
         }
      };
      Notifier.twoButtonDialog(this, "Stop this application and exit? You\'ll need to relaunch the application to use it again.", "Stop application?", "Stop and exit", "Don\'t stop", false, var1, var2, var2);
   }

   public static void switchForm(String var0) {
      if(activeForm != null) {
         activeForm.startNewForm(var0, (Object)null);
      } else {
         throw new IllegalStateException("activeForm is null");
      }
   }

   public static void switchFormWithStartValue(String var0, Object var1) {
      Log.i("Form", "Open another screen with start value:" + var0);
      if(activeForm != null) {
         activeForm.startNewForm(var0, var1);
      } else {
         throw new IllegalStateException("activeForm is null");
      }
   }

   public void $add(AndroidViewComponent var1) {
      this.viewLayout.add(var1);
   }

   public Activity $context() {
      return this;
   }

   protected void $define() {
      throw new UnsupportedOperationException();
   }

   public Form $form() {
      return this;
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Information about the screen.  It appears when \"About this Application\" is selected from the system menu. Use it to inform people about your app.  In multiple screen apps, each screen has its own AboutScreen info."
   )
   public String AboutScreen() {
      return this.aboutScreen;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "textArea"
   )
   @SimpleProperty
   public void AboutScreen(String var1) {
      this.aboutScreen = var1;
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "A number that encodes how contents of the screen are aligned  horizontally. The choices are: 1 = left aligned, 2 = horizontally centered,  3 = right aligned."
   )
   public int AlignHorizontal() {
      return this.horizontalAlignment;
   }

   @DesignerProperty(
      defaultValue = "1",
      editorType = "horizontal_alignment"
   )
   @SimpleProperty
   public void AlignHorizontal(int var1) {
      try {
         this.alignmentSetter.setHorizontalAlignment(var1);
         this.horizontalAlignment = var1;
      } catch (IllegalArgumentException var3) {
         this.dispatchErrorOccurredEvent(this, "HorizontalAlignment", 1401, new Object[]{Integer.valueOf(var1)});
      }
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "A number that encodes how the contents of the arrangement are aligned vertically. The choices are: 1 = aligned at the top, 2 = vertically centered, 3 = aligned at the bottom. Vertical alignment has no effect if the screen is scrollable."
   )
   public int AlignVertical() {
      return this.verticalAlignment;
   }

   @DesignerProperty(
      defaultValue = "1",
      editorType = "vertical_alignment"
   )
   @SimpleProperty
   public void AlignVertical(int var1) {
      try {
         this.alignmentSetter.setVerticalAlignment(var1);
         this.verticalAlignment = var1;
      } catch (IllegalArgumentException var3) {
         this.dispatchErrorOccurredEvent(this, "VerticalAlignment", 1402, new Object[]{Integer.valueOf(var1)});
      }
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty(
      description = "This is the display name of the installed application in the phone.If the AppName is blank, it will be set to the name of the project when the project is built.",
      userVisible = false
   )
   public void AppName(String var1) {
   }

   @SimpleEvent(
      description = "Device back button pressed."
   )
   public boolean BackPressed() {
      return EventDispatcher.dispatchEvent(this, "BackPressed", new Object[0]);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE
   )
   public int BackgroundColor() {
      return this.backgroundColor;
   }

   @DesignerProperty(
      defaultValue = "&HFFFFFFFF",
      editorType = "color"
   )
   @SimpleProperty
   public void BackgroundColor(int var1) {
      this.backgroundColor = var1;
      if(var1 != 0) {
         this.viewLayout.getLayoutManager().setBackgroundColor(var1);
         this.frameLayout.setBackgroundColor(var1);
      } else {
         this.viewLayout.getLayoutManager().setBackgroundColor(-1);
         this.frameLayout.setBackgroundColor(-1);
      }
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "The screen background image."
   )
   public String BackgroundImage() {
      return this.backgroundImagePath;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "asset"
   )
   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "The screen background image."
   )
   public void BackgroundImage(String var1) {
      String var2 = var1;
      if(var1 == null) {
         var2 = "";
      }

      this.backgroundImagePath = var2;

      try {
         this.backgroundDrawable = MediaUtil.getBitmapDrawable(this, this.backgroundImagePath);
      } catch (IOException var3) {
         Log.e("Form", "Unable to load " + this.backgroundImagePath);
         this.backgroundDrawable = null;
      }

      ViewUtil.setBackgroundImage(this.frameLayout, this.backgroundDrawable);
      this.frameLayout.invalidate();
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "The animation for closing current screen and returning  to the previous screen. Valid options are default, fade, zoom, slidehorizontal, slidevertical, and none"
   )
   public String CloseScreenAnimation() {
      return this.closeAnimType;
   }

   @DesignerProperty(
      defaultValue = "default",
      editorType = "screen_animation"
   )
   @SimpleProperty
   public void CloseScreenAnimation(String var1) {
      if(var1 != "default" && var1 != "fade" && var1 != "zoom" && var1 != "slidehorizontal" && var1 != "slidevertical" && var1 != "none") {
         this.dispatchErrorOccurredEvent(this, "Screen", 905, new Object[]{var1});
      } else {
         this.closeAnimType = var1;
      }
   }

   @SimpleEvent(
      description = "Event raised when an error occurs. Only some errors will raise this condition.  For those errors, the system will show a notification by default.  You can use this event handler to prescribe an error behavior different than the default."
   )
   public void ErrorOccurred(Component var1, String var2, int var3, String var4) {
      String var5 = var1.getClass().getName();
      var5 = var5.substring(var5.lastIndexOf(".") + 1);
      Log.e("Form", "Form " + this.formName + " ErrorOccurred, errorNumber = " + var3 + ", componentType = " + var5 + ", functionName = " + var2 + ", messages = " + var4);
      if(!EventDispatcher.dispatchEvent(this, "ErrorOccurred", new Object[]{var1, var2, Integer.valueOf(var3), var4}) && this.screenInitialized) {
         (new Notifier(this)).ShowAlert("Error " + var3 + ": " + var4);
      }

   }

   public void ErrorOccurredDialog(Component var1, String var2, int var3, String var4, String var5, String var6) {
      String var7 = var1.getClass().getName();
      var7 = var7.substring(var7.lastIndexOf(".") + 1);
      Log.e("Form", "Form " + this.formName + " ErrorOccurred, errorNumber = " + var3 + ", componentType = " + var7 + ", functionName = " + var2 + ", messages = " + var4);
      if(!EventDispatcher.dispatchEvent(this, "ErrorOccurred", new Object[]{var1, var2, Integer.valueOf(var3), var4}) && this.screenInitialized) {
         (new Notifier(this)).ShowMessageDialog("Error " + var3 + ": " + var4, var5, var6);
      }

   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Screen height (y-size)."
   )
   public int Height() {
      return this.frameLayout.getHeight();
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "asset"
   )
   @SimpleProperty(
      userVisible = false
   )
   public void Icon(String var1) {
   }

   @SimpleEvent(
      description = "Screen starting"
   )
   public void Initialize() {
      this.androidUIHandler.post(new Runnable() {
         public void run() {
            if(Form.this.frameLayout != null && Form.this.frameLayout.getWidth() != 0 && Form.this.frameLayout.getHeight() != 0) {
               EventDispatcher.dispatchEvent(Form.this, "Initialize", new Object[0]);
               Form.this.screenInitialized = true;
               Iterator var1 = Form.this.onInitializeListeners.iterator();

               while(var1.hasNext()) {
                  ((OnInitializeListener)var1.next()).onInitialize();
               }

               if(Form.activeForm instanceof ReplForm) {
                  ((ReplForm)Form.activeForm).HandleReturnValues();
               }

            } else {
               Form.this.androidUIHandler.post(this);
            }
         }
      });
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "The animation for switching to another screen. Valid options are default, fade, zoom, slidehorizontal, slidevertical, and none"
   )
   public String OpenScreenAnimation() {
      return this.openAnimType;
   }

   @DesignerProperty(
      defaultValue = "default",
      editorType = "screen_animation"
   )
   @SimpleProperty
   public void OpenScreenAnimation(String var1) {
      if(var1 != "default" && var1 != "fade" && var1 != "zoom" && var1 != "slidehorizontal" && var1 != "slidevertical" && var1 != "none") {
         this.dispatchErrorOccurredEvent(this, "Screen", 905, new Object[]{var1});
      } else {
         this.openAnimType = var1;
      }
   }

   @SimpleEvent(
      description = "Event raised when another screen has closed and control has returned to this screen."
   )
   public void OtherScreenClosed(String var1, Object var2) {
      Log.i("Form", "Form " + this.formName + " OtherScreenClosed, otherScreenName = " + var1 + ", result = " + var2.toString());
      EventDispatcher.dispatchEvent(this, "OtherScreenClosed", new Object[]{var1, var2});
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "The requested screen orientation, specified as a text value.  Commonly used values are landscape, portrait, sensor, user and unspecified.  See the Android developer documentation for ActivityInfo.Screen_Orientation for the complete list of possible settings."
   )
   public String ScreenOrientation() {
      switch(this.getRequestedOrientation()) {
      case -1:
         return "unspecified";
      case 0:
         return "landscape";
      case 1:
         return "portrait";
      case 2:
         return "user";
      case 3:
         return "behind";
      case 4:
         return "sensor";
      case 5:
         return "nosensor";
      case 6:
         return "sensorLandscape";
      case 7:
         return "sensorPortrait";
      case 8:
         return "reverseLandscape";
      case 9:
         return "reversePortrait";
      case 10:
         return "fullSensor";
      default:
         return "unspecified";
      }
   }

   @DesignerProperty(
      defaultValue = "unspecified",
      editorType = "screen_orientation"
   )
   @SimpleProperty(
      category = PropertyCategory.APPEARANCE
   )
   public void ScreenOrientation(String var1) {
      if(var1.equalsIgnoreCase("behind")) {
         this.setRequestedOrientation(3);
      } else if(var1.equalsIgnoreCase("landscape")) {
         this.setRequestedOrientation(0);
      } else if(var1.equalsIgnoreCase("nosensor")) {
         this.setRequestedOrientation(5);
      } else if(var1.equalsIgnoreCase("portrait")) {
         this.setRequestedOrientation(1);
      } else if(var1.equalsIgnoreCase("sensor")) {
         this.setRequestedOrientation(4);
      } else if(var1.equalsIgnoreCase("unspecified")) {
         this.setRequestedOrientation(-1);
      } else if(var1.equalsIgnoreCase("user")) {
         this.setRequestedOrientation(2);
      } else if(SdkLevel.getLevel() >= 9) {
         if(var1.equalsIgnoreCase("fullSensor")) {
            this.setRequestedOrientation(10);
         } else if(var1.equalsIgnoreCase("reverseLandscape")) {
            this.setRequestedOrientation(8);
         } else if(var1.equalsIgnoreCase("reversePortrait")) {
            this.setRequestedOrientation(9);
         } else if(var1.equalsIgnoreCase("sensorLandscape")) {
            this.setRequestedOrientation(6);
         } else if(var1.equalsIgnoreCase("sensorPortrait")) {
            this.setRequestedOrientation(7);
         } else {
            this.dispatchErrorOccurredEvent(this, "ScreenOrientation", 901, new Object[]{var1});
         }
      } else {
         this.dispatchErrorOccurredEvent(this, "ScreenOrientation", 901, new Object[]{var1});
      }
   }

   @SimpleEvent(
      description = "Screen orientation changed"
   )
   public void ScreenOrientationChanged() {
      EventDispatcher.dispatchEvent(this, "ScreenOrientationChanged", new Object[0]);
   }

   @DesignerProperty(
      defaultValue = "False",
      editorType = "boolean"
   )
   @SimpleProperty
   public void Scrollable(boolean var1) {
      if(this.scrollable != var1 || this.frameLayout == null) {
         if(this.frameLayout != null) {
            this.frameLayout.removeAllViews();
         }

         this.scrollable = var1;
         Object var2;
         if(var1) {
            var2 = new ScrollView(this);
         } else {
            var2 = new FrameLayout(this);
         }

         this.frameLayout = (FrameLayout)var2;
         this.frameLayout.addView(this.viewLayout.getLayoutManager(), new LayoutParams(-1, -1));
         this.frameLayout.setBackgroundColor(this.backgroundColor);
         if(this.backgroundDrawable != null) {
            ViewUtil.setBackgroundImage(this.frameLayout, this.backgroundDrawable);
         }

         this.setContentView(this.frameLayout);
         this.frameLayout.requestLayout();
      }
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "When checked, there will be a vertical scrollbar on the screen, and the height of the application can exceed the physical height of the device. When unchecked, the application height is constrained to the height of the device."
   )
   public boolean Scrollable() {
      return this.scrollable;
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "The caption for the form, which apears in the title bar"
   )
   public String Title() {
      return this.getTitle().toString();
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty
   public void Title(String var1) {
      this.setTitle(var1);
   }

   @DesignerProperty(
      defaultValue = "1",
      editorType = "non_negative_integer"
   )
   @SimpleProperty(
      description = "An integer value which must be incremented each time a new Android Application Package File (APK) is created for the Google Play Store.",
      userVisible = false
   )
   public void VersionCode(int var1) {
   }

   @DesignerProperty(
      defaultValue = "1.0",
      editorType = "string"
   )
   @SimpleProperty(
      description = "A string which can be changed to allow Google Play Store users to distinguish between different versions of the App.",
      userVisible = false
   )
   public void VersionName(String var1) {
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Screen width (x-size)."
   )
   public int Width() {
      return this.frameLayout.getWidth();
   }

   public void addAboutInfoToMenu(Menu var1) {
      var1.add(0, 0, 2, "About this application").setOnMenuItemClickListener(new OnMenuItemClickListener() {
         public boolean onMenuItemClick(MenuItem var1) {
            Form.this.showAboutApplicationNotification();
            return true;
         }
      }).setIcon(17301651);
   }

   public void addExitButtonToMenu(Menu var1) {
      var1.add(0, 0, 1, "Stop this application").setOnMenuItemClickListener(new OnMenuItemClickListener() {
         public boolean onMenuItemClick(MenuItem var1) {
            Form.this.showExitApplicationNotification();
            return true;
         }
      }).setIcon(17301594);
   }

   public void callInitialize(Object var1) throws Throwable {
      Method var2;
      try {
         var2 = var1.getClass().getMethod("Initialize", (Class[])null);
      } catch (SecurityException var4) {
         Log.i("Form", "Security exception " + var4.getMessage());
         return;
      } catch (NoSuchMethodException var5) {
         return;
      }

      try {
         Log.i("Form", "calling Initialize method for Object " + var1.toString());
         var2.invoke(var1, (Object[])null);
      } catch (InvocationTargetException var3) {
         Log.i("Form", "invoke exception: " + var3.getMessage());
         throw var3.getTargetException();
      }
   }

   public boolean canDispatchEvent(Component var1, String var2) {
      boolean var3;
      if(!this.screenInitialized && (var1 != this || !var2.equals("Initialize"))) {
         var3 = false;
      } else {
         var3 = true;
      }

      if(var3) {
         activeForm = this;
      }

      return var3;
   }

   public void clear() {
      this.viewLayout.getLayoutManager().removeAllViews();
      this.defaultPropertyValues();
      this.screenInitialized = false;
   }

   protected void closeApplicationFromBlocks() {
      this.closeApplication();
   }

   protected void closeForm(Intent var1) {
      if(var1 != null) {
         this.setResult(-1, var1);
      }

      this.finish();
      AnimationUtil.ApplyCloseScreenAnimation(this, this.closeAnimType);
   }

   public void deleteComponent(Object var1) {
      if(var1 instanceof OnStopListener) {
         OnStopListener var2 = (OnStopListener)var1;
         if(this.onStopListeners.contains(var2)) {
            this.onStopListeners.remove(var2);
         }
      }

      if(var1 instanceof OnResumeListener) {
         OnResumeListener var3 = (OnResumeListener)var1;
         if(this.onResumeListeners.contains(var3)) {
            this.onResumeListeners.remove(var3);
         }
      }

      if(var1 instanceof OnPauseListener) {
         OnPauseListener var4 = (OnPauseListener)var1;
         if(this.onPauseListeners.contains(var4)) {
            this.onPauseListeners.remove(var4);
         }
      }

      if(var1 instanceof OnDestroyListener) {
         OnDestroyListener var5 = (OnDestroyListener)var1;
         if(this.onDestroyListeners.contains(var5)) {
            this.onDestroyListeners.remove(var5);
         }
      }

      if(var1 instanceof Deleteable) {
         ((Deleteable)var1).onDelete();
      }

   }

   public void dispatchErrorOccurredEvent(final Component var1, final String var2, final int var3, final Object ... var4) {
      this.runOnUiThread(new Runnable() {
         public void run() {
            String var1x = ErrorMessages.formatMessage(var3, var4);
            Form.this.ErrorOccurred(var1, var2, var3, var1x);
         }
      });
   }

   public void dispatchErrorOccurredEventDialog(final Component var1, final String var2, final int var3, final Object ... var4) {
      this.runOnUiThread(new Runnable() {
         public void run() {
            String var1x = ErrorMessages.formatMessage(var3, var4);
            Form.this.ErrorOccurredDialog(var1, var2, var3, var1x, "Error in " + var2, "Dismiss");
         }
      });
   }

   public boolean dispatchEvent(Component var1, String var2, String var3, Object[] var4) {
      throw new UnsupportedOperationException();
   }

   public void dontGrabTouchEventsForComponent() {
      this.frameLayout.requestDisallowInterceptTouchEvent(true);
   }

   public Bundle fullScreenVideoAction(int var1, VideoPlayer var2, Object var3) {
      synchronized(this){}

      Bundle var6;
      try {
         var6 = this.fullScreenVideoUtil.performAction(var1, var2, var3);
      } finally {
         ;
      }

      return var6;
   }

   public HandlesEventDispatching getDispatchDelegate() {
      return this;
   }

   public String getOpenAnimType() {
      return this.openAnimType;
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      Log.i("Form", "Form " + this.formName + " got onActivityResult, requestCode = " + var1 + ", resultCode = " + var2);
      if(var1 == 1) {
         String var5;
         if(var3 != null && var3.hasExtra("APP_INVENTOR_RESULT")) {
            var5 = var3.getStringExtra("APP_INVENTOR_RESULT");
         } else {
            var5 = "";
         }

         Object var6 = decodeJSONStringForForm(var5, "other screen closed");
         this.OtherScreenClosed(this.nextFormName, var6);
      } else {
         ActivityResultListener var4 = (ActivityResultListener)this.activityResultMap.get(Integer.valueOf(var1));
         if(var4 != null) {
            var4.resultReturned(var1, var2, var3);
            return;
         }
      }

   }

   public void onConfigurationChanged(Configuration var1) {
      super.onConfigurationChanged(var1);
      final int var2 = var1.orientation;
      if(var2 == 2 || var2 == 1) {
         this.androidUIHandler.post(new Runnable() {
            public void run() {
               boolean var2x = false;
               boolean var1 = var2x;
               if(Form.this.frameLayout != null) {
                  if(var2 == 2) {
                     var1 = var2x;
                     if(Form.this.frameLayout.getWidth() >= Form.this.frameLayout.getHeight()) {
                        var1 = true;
                     }
                  } else {
                     var1 = var2x;
                     if(Form.this.frameLayout.getHeight() >= Form.this.frameLayout.getWidth()) {
                        var1 = true;
                     }
                  }
               }

               if(var1) {
                  Form.this.ScreenOrientationChanged();
               } else {
                  Form.this.androidUIHandler.post(this);
               }
            }
         });
      }

   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      String var3 = this.getClass().getName();
      this.formName = var3.substring(var3.lastIndexOf(46) + 1);
      Log.d("Form", "Form " + this.formName + " got onCreate");
      activeForm = this;
      Log.i("Form", "activeForm is now " + activeForm.formName);
      this.viewLayout = new LinearLayout(this, 1);
      this.alignmentSetter = new AlignmentUtil(this.viewLayout);
      this.defaultPropertyValues();
      Intent var4 = this.getIntent();
      if(var4 != null && var4.hasExtra("APP_INVENTOR_START")) {
         this.startupValue = var4.getStringExtra("APP_INVENTOR_START");
      }

      this.fullScreenVideoUtil = new FullScreenVideoUtil(this, this.androidUIHandler);
      int var2 = this.getWindow().getAttributes().softInputMode;
      this.getWindow().setSoftInputMode(var2 | 16);
      this.$define();
      this.Initialize();
   }

   public Dialog onCreateDialog(int var1) {
      switch(var1) {
      case 189:
         return this.fullScreenVideoUtil.createFullScreenVideoDialog();
      default:
         return super.onCreateDialog(var1);
      }
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      super.onCreateOptionsMenu(var1);
      this.addExitButtonToMenu(var1);
      this.addAboutInfoToMenu(var1);
      return true;
   }

   protected void onDestroy() {
      super.onDestroy();
      Log.i("Form", "Form " + this.formName + " got onDestroy");
      EventDispatcher.removeDispatchDelegate(this);
      Iterator var1 = this.onDestroyListeners.iterator();

      while(var1.hasNext()) {
         ((OnDestroyListener)var1.next()).onDestroy();
      }

   }

   public boolean onKeyDown(int var1, KeyEvent var2) {
      if(var1 == 4) {
         if(!this.BackPressed()) {
            boolean var3 = super.onKeyDown(var1, var2);
            AnimationUtil.ApplyCloseScreenAnimation(this, this.closeAnimType);
            return var3;
         } else {
            return true;
         }
      } else {
         return super.onKeyDown(var1, var2);
      }
   }

   protected void onNewIntent(Intent var1) {
      super.onNewIntent(var1);
      Log.d("Form", "Form " + this.formName + " got onNewIntent " + var1);
      Iterator var2 = this.onNewIntentListeners.iterator();

      while(var2.hasNext()) {
         ((OnNewIntentListener)var2.next()).onNewIntent(var1);
      }

   }

   protected void onPause() {
      super.onPause();
      Log.i("Form", "Form " + this.formName + " got onPause");
      Iterator var1 = this.onPauseListeners.iterator();

      while(var1.hasNext()) {
         ((OnPauseListener)var1.next()).onPause();
      }

   }

   public void onPrepareDialog(int var1, Dialog var2) {
      switch(var1) {
      case 189:
         this.fullScreenVideoUtil.prepareFullScreenVideoDialog(var2);
         return;
      default:
         super.onPrepareDialog(var1, var2);
      }
   }

   protected void onResume() {
      super.onResume();
      Log.i("Form", "Form " + this.formName + " got onResume");
      activeForm = this;
      if(applicationIsBeingClosed) {
         this.closeApplication();
      } else {
         Iterator var1 = this.onResumeListeners.iterator();

         while(var1.hasNext()) {
            ((OnResumeListener)var1.next()).onResume();
         }
      }

   }

   protected void onStop() {
      super.onStop();
      Log.i("Form", "Form " + this.formName + " got onStop");
      Iterator var1 = this.onStopListeners.iterator();

      while(var1.hasNext()) {
         ((OnStopListener)var1.next()).onStop();
      }

   }

   public int registerForActivityResult(ActivityResultListener var1) {
      int var2 = generateNewRequestCode();
      this.activityResultMap.put(Integer.valueOf(var2), var1);
      return var2;
   }

   public void registerForOnDestroy(OnDestroyListener var1) {
      this.onDestroyListeners.add(var1);
   }

   public void registerForOnInitialize(OnInitializeListener var1) {
      this.onInitializeListeners.add(var1);
   }

   public void registerForOnNewIntent(OnNewIntentListener var1) {
      this.onNewIntentListeners.add(var1);
   }

   public void registerForOnPause(OnPauseListener var1) {
      this.onPauseListeners.add(var1);
   }

   public void registerForOnResume(OnResumeListener var1) {
      this.onResumeListeners.add(var1);
   }

   public void registerForOnStop(OnStopListener var1) {
      this.onStopListeners.add(var1);
   }

   public void setChildHeight(AndroidViewComponent var1, int var2) {
      ViewUtil.setChildHeightForVerticalLayout(var1.getView(), var2);
   }

   public void setChildWidth(AndroidViewComponent var1, int var2) {
      ViewUtil.setChildWidthForVerticalLayout(var1.getView(), var2);
   }

   void setYandexTranslateTagline() {
      this.yandexTranslateTagline = "<p><small>Language translation powered by Yandex.Translate</small></p>";
   }

   protected void startNewForm(String var1, Object var2) {
      Log.i("Form", "startNewForm:" + var1);
      Intent var4 = new Intent();
      var4.setClassName(this, this.getPackageName() + "." + var1);
      String var3;
      if(var2 == null) {
         var3 = "open another screen";
      } else {
         var3 = "open another screen with start value";
      }

      String var6;
      if(var2 != null) {
         Log.i("Form", "StartNewForm about to JSON encode:" + var2);
         var6 = jsonEncodeForForm(var2, var3);
         Log.i("Form", "StartNewForm got JSON encoding:" + var6);
      } else {
         var6 = "";
      }

      var4.putExtra("APP_INVENTOR_START", var6);
      this.nextFormName = var1;
      Log.i("Form", "about to start new form" + var1);

      try {
         Log.i("Form", "startNewForm starting activity:" + var4);
         this.startActivityForResult(var4, 1);
         AnimationUtil.ApplyOpenScreenAnimation(this, this.openAnimType);
      } catch (ActivityNotFoundException var5) {
         this.dispatchErrorOccurredEvent(this, var3, 902, new Object[]{var1});
      }
   }

   protected boolean toastAllowed() {
      long var1 = System.nanoTime();
      if(var1 > this.lastToastTime + minimumToastWait) {
         this.lastToastTime = var1;
         return true;
      } else {
         return false;
      }
   }

   public void unregisterForActivityResult(ActivityResultListener var1) {
      ArrayList var2 = Lists.newArrayList();
      Iterator var3 = this.activityResultMap.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         if(var1.equals(var4.getValue())) {
            var2.add(var4.getKey());
         }
      }

      Iterator var5 = var2.iterator();

      while(var5.hasNext()) {
         Integer var6 = (Integer)var5.next();
         this.activityResultMap.remove(var6);
      }

   }
}
