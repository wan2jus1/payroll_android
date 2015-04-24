package com.google.appinventor.components.runtime;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.provider.Contacts.People;
import android.provider.Contacts.Phones;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.ContactPicker;
import com.google.appinventor.components.runtime.util.HoneycombUtil;
import java.util.ArrayList;

@DesignerComponent(
   category = ComponentCategory.SOCIAL,
   description = "A button that, when clicked on, displays a list of the contacts\' phone numbers to choose among. After the user has made a selection, the following properties will be set to information about the chosen contact: <ul>\n<li> <code>ContactName</code>: the contact\'s name </li>\n <li> <code>PhoneNumber</code>: the contact\'s phone number </li>\n <li> <code>EmailAddress</code>: the contact\'s email address </li> <li> <code>Picture</code>: the name of the file containing the contact\'s image, which can be used as a <code>Picture</code> property value for the <code>Image</code> or <code>ImageSprite</code> component.</li></ul>\n</p><p>Other properties affect the appearance of the button (<code>TextAlignment</code>, <code>BackgroundColor</code>, etc.) and whether it can be clicked on (<code>Enabled</code>).</p>\n<p>The PhoneNumberPicker component may not work on all Android devices. For example, on Android systems before system 3.0, the returned lists of phone numbers and email addresses will be empty.\n",
   version = 4
)
@SimpleObject
@UsesPermissions(
   permissionNames = "android.permission.READ_CONTACTS"
)
public class PhoneNumberPicker extends ContactPicker {

   private static String[] DATA_PROJECTION;
   private static final int EMAIL_INDEX = 3;
   private static final String LOG_TAG = "PhoneNumberPicker";
   private static final int NAME_INDEX = 0;
   private static String[] NAME_PROJECTION;
   private static final int NUMBER_INDEX = 1;
   private static final int PERSON_INDEX = 2;
   private static final String[] PROJECTION = new String[]{"name", "number", "person", "primary_email"};


   public PhoneNumberPicker(ComponentContainer var1) {
      super(var1, Phones.CONTENT_URI);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String PhoneNumber() {
      return this.ensureNotNull(this.phoneNumber);
   }

   public void postHoneycombGetContactEmailsAndPhones(Cursor var1) {
      ArrayList var2 = new ArrayList();
      ArrayList var3 = new ArrayList();
      if(var1.moveToFirst()) {
         int var7 = HoneycombUtil.getPhoneIndex(var1);
         int var8 = HoneycombUtil.getEmailIndex(var1);
         int var9 = HoneycombUtil.getMimeIndex(var1);
         String var4 = HoneycombUtil.getPhoneType();

         for(String var5 = HoneycombUtil.getEmailType(); !var1.isAfterLast(); var1.moveToNext()) {
            String var6 = this.guardCursorGetString(var1, var9);
            if(var6.contains(var4)) {
               var2.add(this.guardCursorGetString(var1, var7));
            } else if(var6.contains(var5)) {
               var3.add(this.guardCursorGetString(var1, var8));
            } else {
               Log.i("ContactPicker", "Type mismatch: " + var6 + " not " + var4 + " or " + var5);
            }
         }

         this.phoneNumberList = var2;
         this.emailAddressList = var3;
         if(this.emailAddressList.isEmpty()) {
            this.emailAddress = "";
            return;
         }

         this.emailAddress = (String)this.emailAddressList.get(0);
      }

   }

   public String postHoneycombGetContactNameAndPicture(Cursor var1) {
      String var2 = "";
      if(var1.moveToFirst()) {
         int var3 = HoneycombUtil.getContactIdIndex(var1);
         int var4 = HoneycombUtil.getNameIndex(var1);
         int var5 = HoneycombUtil.getThumbnailIndex(var1);
         this.phoneNumber = this.guardCursorGetString(var1, HoneycombUtil.getPhoneIndex(var1));
         var2 = this.guardCursorGetString(var1, var3);
         this.contactName = this.guardCursorGetString(var1, var4);
         this.contactPictureUri = this.guardCursorGetString(var1, var5);
      }

      return var2;
   }

   public void preHoneycombGetContactInfo(Cursor var1) {
      if(var1.moveToFirst()) {
         this.contactName = this.guardCursorGetString(var1, 0);
         this.phoneNumber = this.guardCursorGetString(var1, 1);
         int var2 = var1.getInt(2);
         this.contactPictureUri = ContentUris.withAppendedId(People.CONTENT_URI, (long)var2).toString();
         this.emailAddress = this.getEmailAddress(this.guardCursorGetString(var1, 3));
      }

   }

   public void resultReturned(int param1, int param2, Intent param3) {
      // $FF: Couldn't be decompiled
   }
}
