package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import java.io.InputStream;

public class HoneycombUtil {

   public static int getContactIdIndex(Cursor var0) {
      return var0.getColumnIndex("contact_id");
   }

   public static String[] getContactProjection() {
      return new String[]{"_id", "display_name", "photo_thumb_uri", "photo_uri"};
   }

   public static Uri getContentUri() {
      return Contacts.CONTENT_URI;
   }

   public static Uri getDataContentUri() {
      return Data.CONTENT_URI;
   }

   public static Cursor getDataCursor(String var0, Activity var1, String[] var2) {
      return var1.getContentResolver().query(Data.CONTENT_URI, var2, "contact_id=? AND (mimetype=? OR mimetype=?)", new String[]{var0, "vnd.android.cursor.item/phone_v2", "vnd.android.cursor.item/email_v2"}, (String)null);
   }

   public static String getDataMimeType() {
      return "mimetype";
   }

   public static String[] getDataProjection() {
      return new String[]{"mimetype", "data1", "data2", "data1", "data2"};
   }

   public static String getDisplayName() {
      return "display_name";
   }

   public static String[] getEmailAdapterProjection() {
      return new String[]{"_id", "display_name", "data1", "mimetype"};
   }

   public static String getEmailAddress() {
      return "data1";
   }

   public static int getEmailIndex(Cursor var0) {
      return var0.getColumnIndex("data1");
   }

   public static String getEmailType() {
      return "vnd.android.cursor.item/email_v2";
   }

   public static int getIdIndex(Cursor var0) {
      return var0.getColumnIndex("_id");
   }

   public static int getMimeIndex(Cursor var0) {
      return var0.getColumnIndex("mimetype");
   }

   public static int getNameIndex(Cursor var0) {
      return var0.getColumnIndex("display_name");
   }

   public static String[] getNameProjection() {
      return new String[]{"contact_id", "display_name", "photo_thumb_uri", "data1"};
   }

   public static Uri getPhoneContentUri() {
      return Phone.CONTENT_URI;
   }

   public static int getPhoneIndex(Cursor var0) {
      return var0.getColumnIndex("data1");
   }

   public static String getPhoneType() {
      return "vnd.android.cursor.item/phone_v2";
   }

   public static int getPhotoIndex(Cursor var0) {
      return var0.getColumnIndex("photo_uri");
   }

   public static int getThumbnailIndex(Cursor var0) {
      return var0.getColumnIndex("photo_thumb_uri");
   }

   public static String getTimesContacted() {
      return "times_contacted";
   }

   public static InputStream openContactPhotoInputStreamHelper(ContentResolver var0, Uri var1) {
      return Contacts.openContactPhotoInputStream(var0, var1);
   }
}
