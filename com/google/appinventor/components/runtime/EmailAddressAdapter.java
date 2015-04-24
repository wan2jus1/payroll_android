package com.google.appinventor.components.runtime;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.provider.Contacts.ContactMethods;
import android.text.TextUtils;
import android.text.util.Rfc822Token;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import com.google.appinventor.components.runtime.util.HoneycombUtil;
import com.google.appinventor.components.runtime.util.SdkLevel;

public class EmailAddressAdapter extends ResourceCursorAdapter {

   private static final boolean DEBUG = false;
   private static final String[] POST_HONEYCOMB_PROJECTION = HoneycombUtil.getEmailAdapterProjection();
   public static final int PRE_HONEYCOMB_DATA_INDEX = 2;
   public static final int PRE_HONEYCOMB_NAME_INDEX = 1;
   private static final String[] PRE_HONEYCOMB_PROJECTION = new String[]{"_id", "name", "data"};
   private static String SORT_ORDER;
   private static final String TAG = "EmailAddressAdapter";
   private ContentResolver contentResolver;
   private Context context;


   public EmailAddressAdapter(Context var1) {
      super(var1, 17367050, (Cursor)null);
      this.contentResolver = var1.getContentResolver();
      this.context = var1;
      if(SdkLevel.getLevel() >= 12) {
         SORT_ORDER = HoneycombUtil.getTimesContacted() + " DESC, " + HoneycombUtil.getDisplayName();
      } else {
         SORT_ORDER = "times_contacted DESC, name";
      }
   }

   private final String makeDisplayString(Cursor var1) {
      int var5 = var1.getColumnIndex(HoneycombUtil.getDisplayName());
      int var6 = var1.getColumnIndex(HoneycombUtil.getEmailAddress());
      StringBuilder var3 = new StringBuilder();
      boolean var4 = false;
      String var2;
      String var7;
      if(SdkLevel.getLevel() >= 12) {
         var2 = var1.getString(var5);
         var7 = var1.getString(var6);
      } else {
         var2 = var1.getString(1);
         var7 = var1.getString(2);
      }

      if(!TextUtils.isEmpty(var2)) {
         var3.append(var2);
         var4 = true;
      }

      if(var4) {
         var3.append(" <");
      }

      var3.append(var7);
      if(var4) {
         var3.append(">");
      }

      return var3.toString();
   }

   public final void bindView(View var1, Context var2, Cursor var3) {
      ((TextView)var1).setText(this.makeDisplayString(var3));
   }

   public final String convertToString(Cursor var1) {
      int var3 = var1.getColumnIndex(HoneycombUtil.getDisplayName());
      int var4 = var1.getColumnIndex(HoneycombUtil.getEmailAddress());
      String var2;
      String var5;
      if(SdkLevel.getLevel() >= 12) {
         var2 = var1.getString(var3);
         var5 = var1.getString(var4);
      } else {
         var2 = var1.getString(1);
         var5 = var1.getString(2);
      }

      return (new Rfc822Token(var2, var5, (String)null)).toString();
   }

   public Cursor runQueryOnBackgroundThread(CharSequence var1) {
      Uri var2 = null;
      StringBuilder var3 = new StringBuilder();
      String var4;
      if(var1 != null) {
         var4 = DatabaseUtils.sqlEscapeString(var1.toString() + '%');
         if(SdkLevel.getLevel() >= 12) {
            var2 = HoneycombUtil.getDataContentUri();
            var3.append("(" + HoneycombUtil.getDataMimeType() + "=\'" + HoneycombUtil.getEmailType() + "\')");
            var3.append(" AND ");
            var3.append("(display_name LIKE ");
            var3.append(var4);
            var3.append(")");
         } else {
            var2 = ContactMethods.CONTENT_EMAIL_URI;
            var3.append("(name LIKE ");
            var3.append(var4);
            var3.append(") OR (display_name LIKE ");
            var3.append(var4);
            var3.append(")");
         }
      }

      var4 = var3.toString();
      return SdkLevel.getLevel() >= 12?this.contentResolver.query(var2, POST_HONEYCOMB_PROJECTION, var4, (String[])null, SORT_ORDER):this.contentResolver.query(var2, PRE_HONEYCOMB_PROJECTION, var4, (String[])null, SORT_ORDER);
   }
}
