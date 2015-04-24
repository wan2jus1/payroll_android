package org.acra;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.io.IOException;
import org.acra.ACRA;
import org.acra.CrashReportPersister;
import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.util.ToastSender;

public final class CrashReportDialog extends Activity {

   private static final String STATE_COMMENT = "comment";
   private static final String STATE_EMAIL = "email";
   String mReportFileName;
   private SharedPreferences prefs;
   private EditText userComment;
   private EditText userEmail;


   protected void cancelNotification() {
      ((NotificationManager)this.getSystemService("notification")).cancel(666);
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.mReportFileName = this.getIntent().getStringExtra("REPORT_FILE_NAME");
      Log.d(ACRA.LOG_TAG, "Opening CrashReportDialog for " + this.mReportFileName);
      if(this.mReportFileName == null) {
         this.finish();
      }

      this.requestWindowFeature(3);
      LinearLayout var3 = new LinearLayout(this);
      var3.setOrientation(1);
      var3.setPadding(10, 10, 10, 10);
      var3.setLayoutParams(new LayoutParams(-1, -2));
      var3.setFocusable(true);
      var3.setFocusableInTouchMode(true);
      final ScrollView var4 = new ScrollView(this);
      var3.addView(var4, new android.widget.LinearLayout.LayoutParams(-1, -1, 1.0F));
      LinearLayout var5 = new LinearLayout(this);
      var5.setOrientation(1);
      var4.addView(var5);
      TextView var2 = new TextView(this);
      var2.setText(this.getText(ACRA.getConfig().resDialogText()));
      var5.addView(var2);
      int var6 = ACRA.getConfig().resDialogCommentPrompt();
      String var8;
      if(var6 != 0) {
         var2 = new TextView(this);
         var2.setText(this.getText(var6));
         var2.setPadding(var2.getPaddingLeft(), 10, var2.getPaddingRight(), var2.getPaddingBottom());
         var5.addView(var2, new android.widget.LinearLayout.LayoutParams(-1, -2));
         this.userComment = new EditText(this);
         this.userComment.setLines(2);
         if(var1 != null) {
            var8 = var1.getString("comment");
            if(var8 != null) {
               this.userComment.setText(var8);
            }
         }

         var5.addView(this.userComment);
      }

      var6 = ACRA.getConfig().resDialogEmailPrompt();
      if(var6 != 0) {
         var2 = new TextView(this);
         var2.setText(this.getText(var6));
         var2.setPadding(var2.getPaddingLeft(), 10, var2.getPaddingRight(), var2.getPaddingBottom());
         var5.addView(var2);
         this.userEmail = new EditText(this);
         this.userEmail.setSingleLine();
         this.userEmail.setInputType(33);
         this.prefs = this.getSharedPreferences(ACRA.getConfig().sharedPreferencesName(), ACRA.getConfig().sharedPreferencesMode());
         var8 = null;
         if(var1 != null) {
            var8 = var1.getString("email");
         }

         if(var8 != null) {
            this.userEmail.setText(var8);
         } else {
            this.userEmail.setText(this.prefs.getString("acra.user.email", ""));
         }

         var5.addView(this.userEmail);
      }

      LinearLayout var7 = new LinearLayout(this);
      var7.setLayoutParams(new android.widget.LinearLayout.LayoutParams(-1, -2));
      var7.setPadding(var7.getPaddingLeft(), 10, var7.getPaddingRight(), var7.getPaddingBottom());
      Button var9 = new Button(this);
      var9.setText(17039379);
      var9.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            String var7;
            if(CrashReportDialog.this.userComment != null) {
               var7 = CrashReportDialog.this.userComment.getText().toString();
            } else {
               var7 = "";
            }

            String var2;
            if(CrashReportDialog.this.prefs != null && CrashReportDialog.this.userEmail != null) {
               var2 = CrashReportDialog.this.userEmail.getText().toString();
               Editor var3 = CrashReportDialog.this.prefs.edit();
               var3.putString("acra.user.email", var2);
               var3.commit();
            } else {
               var2 = "";
            }

            CrashReportPersister var8 = new CrashReportPersister(CrashReportDialog.this.getApplicationContext());

            try {
               Log.d(ACRA.LOG_TAG, "Add user comment to " + CrashReportDialog.this.mReportFileName);
               CrashReportData var4 = var8.load((String)CrashReportDialog.this.mReportFileName);
               var4.put(ReportField.USER_COMMENT, var7);
               var4.put(ReportField.USER_EMAIL, var2);
               var8.store(var4, CrashReportDialog.this.mReportFileName);
            } catch (IOException var6) {
               Log.w(ACRA.LOG_TAG, "User comment not added: ", var6);
            }

            Log.v(ACRA.LOG_TAG, "About to start SenderWorker from CrashReportDialog");
            ACRA.getErrorReporter().startSendingReports(false, true);
            int var5 = ACRA.getConfig().resDialogOkToast();
            if(var5 != 0) {
               ToastSender.sendToast(CrashReportDialog.this.getApplicationContext(), var5, 1);
            }

            CrashReportDialog.this.finish();
         }
      });
      var7.addView(var9, new android.widget.LinearLayout.LayoutParams(-1, -2, 1.0F));
      var9 = new Button(this);
      var9.setText(17039369);
      var9.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            ACRA.getErrorReporter().deletePendingNonApprovedReports(false);
            CrashReportDialog.this.finish();
         }
      });
      var7.addView(var9, new android.widget.LinearLayout.LayoutParams(-1, -2, 1.0F));
      var3.addView(var7, new android.widget.LinearLayout.LayoutParams(-1, -2));
      this.setContentView(var3);
      var6 = ACRA.getConfig().resDialogTitle();
      if(var6 != 0) {
         this.setTitle(var6);
      }

      this.getWindow().setFeatureDrawableResource(3, ACRA.getConfig().resDialogIcon());
      this.cancelNotification();
      var4.post(new Runnable() {
         public void run() {
            var4.scrollTo(0, 0);
         }
      });
   }

   public boolean onKeyUp(int var1, KeyEvent var2) {
      if(var1 == 4) {
         ACRA.getErrorReporter().deletePendingNonApprovedReports(false);
      }

      return super.onKeyUp(var1, var2);
   }

   protected void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      if(this.userComment != null && this.userComment.getText() != null) {
         var1.putString("comment", this.userComment.getText().toString());
      }

      if(this.userEmail != null && this.userEmail.getText() != null) {
         var1.putString("email", this.userEmail.getText().toString());
      }

   }
}
