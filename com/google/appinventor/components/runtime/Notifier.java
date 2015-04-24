package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.util.SdkLevel;

@DesignerComponent(
   category = ComponentCategory.USERINTERFACE,
   description = "The Notifier component displays alert dialogs, messages, and temporary alerts, and creates Android log entries through the following methods: <ul><li> ShowMessageDialog: displays a message which the user must dismiss by pressing a button.</li><li> ShowChooseDialog: displays a message two buttons to let the user choose one of two responses, for example, yes or no, after which the AfterChoosing event is raised.</li><li> ShowTextDialog: lets the user enter text in response to the message, after which the AfterTextInput event is raised. <li> ShowAlert: displays a temporary  alert that goes away by itself after a short time.</li><li> ShowProgressDialog: displays an alert with a loading spinner that cannot be dismissed by the user. It can only be dismissed by using the DismissProgressDialog block.</li><li> DismissProgressDialog: Dismisses the progress dialog displayed by ShowProgressDialog.</li><li> LogError: logs an error message to the Android log. </li><li> LogInfo: logs an info message to the Android log.</li><li> LogWarning: logs a warning message to the Android log.</li><li>The messages in the dialogs (but not the alert) can be formatted using the following HTML tags:&lt;b&gt;, &lt;big&gt;, &lt;blockquote&gt;, &lt;br&gt;, &lt;cite&gt;, &lt;dfn&gt;, &lt;div&gt;, &lt;em&gt;, &lt;small&gt;, &lt;strong&gt;, &lt;sub&gt;, &lt;sup&gt;, &lt;tt&gt;. &lt;u&gt;</li><li>You can also use the font tag to specify color, for example, &lt;font color=\"blue\"&gt;.  Some of the available color names are aqua, black, blue, fuchsia, green, grey, lime, maroon, navy, olive, purple, red, silver, teal, white, and yellow</li></ul>",
   iconName = "images/notifier.png",
   nonVisible = true,
   version = 4
)
@SimpleObject
public final class Notifier extends AndroidNonvisibleComponent implements Component {

   private static final String LOG_TAG = "Notifier";
   private final Activity activity;
   private int backgroundColor = -12303292;
   private final Handler handler;
   private int notifierLength = 1;
   private ProgressDialog progressDialog;
   private int textColor = -1;


   public Notifier(ComponentContainer var1) {
      super(var1.$form());
      this.activity = var1.$context();
      this.handler = new Handler();
      this.progressDialog = null;
   }

   public static void oneButtonAlert(Activity var0, String var1, String var2, String var3) {
      Log.i("Notifier", "One button alert " + var1);
      AlertDialog var4 = (new Builder(var0)).create();
      var4.setTitle(var2);
      var4.setCancelable(false);
      var4.setMessage(stringToHTML(var1));
      var4.setButton(-3, var3, new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
         }
      });
      var4.show();
   }

   private static SpannableString stringToHTML(String var0) {
      return new SpannableString(Html.fromHtml(var0));
   }

   private void textInputDialog(String var1, String var2, boolean var3) {
      AlertDialog var4 = (new Builder(this.activity)).create();
      var4.setTitle(var2);
      var4.setMessage(stringToHTML(var1));
      final EditText var5 = new EditText(this.activity);
      var4.setView(var5);
      var4.setCancelable(false);
      var4.setButton(-1, "OK", new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            Notifier.this.HideKeyboard(var5);
            Notifier.this.AfterTextInput(var5.getText().toString());
         }
      });
      if(var3) {
         var4.setButton(-2, "Cancel", new OnClickListener() {
            public void onClick(DialogInterface var1, int var2) {
               Notifier.this.HideKeyboard(var5);
               Notifier.this.AfterTextInput("Cancel");
            }
         });
      }

      var4.show();
   }

   private void toastNow(String var1) {
      byte var4;
      if(SdkLevel.getLevel() >= 14) {
         var4 = 22;
      } else {
         var4 = 15;
      }

      Toast var2 = Toast.makeText(this.activity, var1, this.notifierLength);
      var2.setGravity(17, var2.getXOffset() / 2, var2.getYOffset() / 2);
      TextView var3 = new TextView(this.activity);
      var3.setBackgroundColor(this.backgroundColor);
      var3.setTextColor(this.textColor);
      var3.setTextSize((float)var4);
      var3.setTypeface(Typeface.create(Typeface.SANS_SERIF, 0));
      var3.setPadding(10, 10, 10, 10);
      var3.setText(var1 + " ");
      var2.setView(var3);
      var2.show();
   }

   public static void twoButtonDialog(Activity var0, String var1, String var2, String var3, String var4, boolean var5, final Runnable var6, final Runnable var7, final Runnable var8) {
      Log.i("Notifier", "ShowChooseDialog: " + var1);
      AlertDialog var9 = (new Builder(var0)).create();
      var9.setTitle(var2);
      var9.setCancelable(false);
      var9.setMessage(stringToHTML(var1));
      var9.setButton(-1, var3, new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            var6.run();
         }
      });
      var9.setButton(-3, var4, new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            var7.run();
         }
      });
      if(var5) {
         var9.setButton(-2, "Cancel", new OnClickListener() {
            public void onClick(DialogInterface var1, int var2) {
               var8.run();
            }
         });
      }

      var9.show();
   }

   @SimpleEvent
   public void AfterChoosing(String var1) {
      EventDispatcher.dispatchEvent(this, "AfterChoosing", new Object[]{var1});
   }

   @SimpleEvent
   public void AfterTextInput(String var1) {
      EventDispatcher.dispatchEvent(this, "AfterTextInput", new Object[]{var1});
   }

   @DesignerProperty(
      defaultValue = "&HFF444444",
      editorType = "color"
   )
   @SimpleProperty(
      description = "Specifies the background color for alerts (not dialogs)."
   )
   public void BackgroundColor(int var1) {
      this.backgroundColor = var1;
   }

   @SimpleFunction(
      description = "Dismiss a previously displayed ProgressDialog box"
   )
   public void DismissProgressDialog() {
      if(this.progressDialog != null) {
         this.progressDialog.dismiss();
         this.progressDialog = null;
      }

   }

   public void HideKeyboard(View var1) {
      if(var1 != null) {
         ((InputMethodManager)this.activity.getSystemService("input_method")).hideSoftInputFromWindow(var1.getWindowToken(), 0);
      }

   }

   @SimpleFunction(
      description = "Writes an error message to the Android system log. See the Google Android documentation for how to access the log."
   )
   public void LogError(String var1) {
      Log.e("Notifier", var1);
   }

   @SimpleFunction(
      description = "Writes an information message to the Android log."
   )
   public void LogInfo(String var1) {
      Log.i("Notifier", var1);
   }

   @SimpleFunction(
      description = "Writes a warning message to the Android log. See the Google Android documentation for how to access the log."
   )
   public void LogWarning(String var1) {
      Log.w("Notifier", var1);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "specifies the length of time that the alert is shown -- either \"short\" or \"long\"."
   )
   public int NotifierLength() {
      return this.notifierLength;
   }

   @DesignerProperty(
      defaultValue = "1",
      editorType = "toast_length"
   )
   @SimpleProperty(
      userVisible = false
   )
   public void NotifierLength(int var1) {
      this.notifierLength = var1;
   }

   @SimpleFunction
   public void ShowAlert(final String var1) {
      this.handler.post(new Runnable() {
         public void run() {
            Notifier.this.toastNow(var1);
         }
      });
   }

   @SimpleFunction(
      description = "Shows a dialog box with two buttons, from which the user can choose.  If cancelable is true there will be an additional CANCEL button. Pressing a button will raise the AfterChoosing event.  The \"choice\" parameter to AfterChoosing will be the text on the button that was pressed, or \"Cancel\" if the  CANCEL button was pressed."
   )
   public void ShowChooseDialog(String var1, String var2, final String var3, final String var4, boolean var5) {
      twoButtonDialog(this.activity, var1, var2, var3, var4, var5, new Runnable() {
         public void run() {
            Notifier.this.AfterChoosing(var3);
         }
      }, new Runnable() {
         public void run() {
            Notifier.this.AfterChoosing(var4);
         }
      }, new Runnable() {
         public void run() {
            Notifier.this.AfterChoosing("Cancel");
         }
      });
   }

   @SimpleFunction
   public void ShowMessageDialog(String var1, String var2, String var3) {
      oneButtonAlert(this.activity, var1, var2, var3);
   }

   @SimpleFunction(
      description = "Shows a dialog box with an optional title and message (use empty strings if they are not wanted). This dialog box contains a spinning artifact to indicate that the program is working. It cannot be canceled by the user but must be dismissed by the App Inventor Program by using the DismissProgressDialog block."
   )
   public void ShowProgressDialog(String var1, String var2) {
      this.progressDialog(var1, var2);
   }

   @SimpleFunction(
      description = "Shows a dialog box where the user can enter text, after which the AfterTextInput event will be raised.  If cancelable is true there will be an additional CANCEL button. Entering text will raise the AfterTextInput event.  The \"response\" parameter to AfterTextInput will be the text that was entered, or \"Cancel\" if the CANCEL button was pressed."
   )
   public void ShowTextDialog(String var1, String var2, boolean var3) {
      this.textInputDialog(var1, var2, var3);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Specifies the text color for alerts (not dialogs)."
   )
   public int TextColor() {
      return this.textColor;
   }

   @DesignerProperty(
      defaultValue = "&HFFFFFFFF",
      editorType = "color"
   )
   @SimpleProperty
   public void TextColor(int var1) {
      this.textColor = var1;
   }

   public void progressDialog(String var1, String var2) {
      if(this.progressDialog != null) {
         this.DismissProgressDialog();
      }

      this.progressDialog = ProgressDialog.show(this.activity, var2, var1);
      this.progressDialog.setCancelable(false);
   }
}
