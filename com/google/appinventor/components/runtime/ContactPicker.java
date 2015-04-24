package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts.ContactMethods;
import android.provider.Contacts.People;
import android.provider.Contacts.Phones;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.ActivityResultListener;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Picker;
import com.google.appinventor.components.runtime.util.HoneycombUtil;
import com.google.appinventor.components.runtime.util.SdkLevel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DesignerComponent(
   category = ComponentCategory.SOCIAL,
   description = "A button that, when clicked on, displays a list of the contacts to choose among. After the user has made a selection, the following properties will be set to information about the chosen contact: <ul>\n<li> <code>ContactName</code>: the contact\'s name </li>\n <li> <code>EmailAddress</code>: the contact\'s primary email address </li>\n <li> <code>EmailAddressList</code>: a list of the contact\'s email addresses </li>\n <li> <code>PhoneNumber</code>: the contact\'s primary phone number (on Later Android Verisons)</li>\n <li> <code>PhoneNumberList</code>: a list of the contact\'s phone numbers (on Later Android Versions)</li>\n <li> <code>Picture</code>: the name of the file containing the contact\'s image, which can be used as a <code>Picture</code> property value for the <code>Image</code> or <code>ImageSprite</code> component.</li></ul>\n</p><p>Other properties affect the appearance of the button (<code>TextAlignment</code>, <code>BackgroundColor</code>, etc.) and whether it can be clicked on (<code>Enabled</code>).\n</p><p>The ContactPicker component might not work on all phones. For example, on Android systems before system 3.0, it cannot pick phone numbers, and the list of email addresses will contain only one email.",
   version = 5
)
@SimpleObject
@UsesPermissions(
   permissionNames = "android.permission.READ_CONTACTS"
)
public class ContactPicker extends Picker implements ActivityResultListener {

   private static String[] CONTACT_PROJECTION;
   private static String[] DATA_PROJECTION;
   private static final int EMAIL_INDEX = 1;
   private static final int NAME_INDEX = 0;
   private static final int PHONE_INDEX = 2;
   private static final String[] PROJECTION = new String[]{"name", "primary_email"};
   protected final Activity activityContext;
   protected String contactName;
   protected String contactPictureUri;
   protected String emailAddress;
   protected List emailAddressList;
   private final Uri intentUri;
   protected String phoneNumber;
   protected List phoneNumberList;


   public ContactPicker(ComponentContainer var1) {
      this(var1, People.CONTENT_URI);
   }

   protected ContactPicker(ComponentContainer var1, Uri var2) {
      super(var1);
      this.activityContext = var1.$context();
      if(SdkLevel.getLevel() >= 12 && var2.equals(People.CONTENT_URI)) {
         this.intentUri = HoneycombUtil.getContentUri();
      } else if(SdkLevel.getLevel() >= 12 && var2.equals(Phones.CONTENT_URI)) {
         this.intentUri = HoneycombUtil.getPhoneContentUri();
      } else {
         this.intentUri = var2;
      }
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String ContactName() {
      return this.ensureNotNull((String)this.contactName);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String EmailAddress() {
      return this.ensureNotNull((String)this.emailAddress);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public List EmailAddressList() {
      return this.ensureNotNull((List)this.emailAddressList);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String PhoneNumber() {
      return this.ensureNotNull((String)this.phoneNumber);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public List PhoneNumberList() {
      return this.ensureNotNull((List)this.phoneNumberList);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String Picture() {
      return this.ensureNotNull((String)this.contactPictureUri);
   }

   protected boolean checkContactUri(Uri var1, String var2) {
      Log.i("ContactPicker", "contactUri is " + var1);
      if(var1 != null && "content".equals(var1.getScheme())) {
         if(!var1.getSchemeSpecificPart().startsWith(var2)) {
            Log.i("ContactPicker", "checkContactUri failed: C");
            Log.i("ContactPicker", var1.getPath());
            this.puntContactSelection(1107);
            return false;
         } else {
            return true;
         }
      } else {
         Log.i("ContactPicker", "checkContactUri failed: A");
         this.puntContactSelection(1107);
         return false;
      }
   }

   protected String ensureNotNull(String var1) {
      String var2 = var1;
      if(var1 == null) {
         var2 = "";
      }

      return var2;
   }

   protected List ensureNotNull(List var1) {
      Object var2 = var1;
      if(var1 == null) {
         var2 = new ArrayList();
      }

      return (List)var2;
   }

   protected String getEmailAddress(String var1) {
      int var3;
      try {
         var3 = Integer.parseInt(var1);
      } catch (NumberFormatException var6) {
         return "";
      }

      var1 = "";
      String var2 = "contact_methods._id = " + var3;
      Cursor var8 = this.activityContext.getContentResolver().query(ContactMethods.CONTENT_EMAIL_URI, new String[]{"data"}, var2, (String[])null, (String)null);

      try {
         if(var8.moveToFirst()) {
            var1 = this.guardCursorGetString(var8, 0);
         }
      } finally {
         var8.close();
      }

      return this.ensureNotNull((String)var1);
   }

   protected Intent getIntent() {
      return new Intent("android.intent.action.PICK", this.intentUri);
   }

   protected String guardCursorGetString(Cursor var1, int var2) {
      String var4;
      try {
         var4 = var1.getString(var2);
      } catch (Exception var3) {
         var4 = "";
      }

      return this.ensureNotNull((String)var4);
   }

   public void postHoneycombGetContactEmailAndPhone(Cursor var1) {
      this.phoneNumber = "";
      this.emailAddress = "";
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
      }

      if(!var2.isEmpty()) {
         this.phoneNumber = (String)var2.get(0);
      }

      if(!var3.isEmpty()) {
         this.emailAddress = (String)var3.get(0);
      }

      this.phoneNumberList = var2;
      this.emailAddressList = var3;
   }

   public String postHoneycombGetContactNameAndPicture(Cursor var1) {
      String var2 = "";
      if(var1.moveToFirst()) {
         int var3 = HoneycombUtil.getIdIndex(var1);
         int var4 = HoneycombUtil.getNameIndex(var1);
         int var5 = HoneycombUtil.getThumbnailIndex(var1);
         int var6 = HoneycombUtil.getPhotoIndex(var1);
         var2 = this.guardCursorGetString(var1, var3);
         this.contactName = this.guardCursorGetString(var1, var4);
         this.contactPictureUri = this.guardCursorGetString(var1, var5);
         Log.i("ContactPicker", "photo_uri=" + this.guardCursorGetString(var1, var6));
      }

      return var2;
   }

   public void preHoneycombGetContactInfo(Cursor var1, Uri var2) {
      if(var1.moveToFirst()) {
         this.contactName = this.guardCursorGetString(var1, 0);
         this.emailAddress = this.getEmailAddress(this.guardCursorGetString(var1, 1));
         this.contactPictureUri = var2.toString();
         Object var3;
         if(this.emailAddress.equals("")) {
            var3 = new ArrayList();
         } else {
            var3 = Arrays.asList(new String[]{this.emailAddress});
         }

         this.emailAddressList = (List)var3;
      }

   }

   protected void puntContactSelection(int var1) {
      this.contactName = "";
      this.emailAddress = "";
      this.contactPictureUri = "";
      this.container.$form().dispatchErrorOccurredEvent(this, "", var1, new Object[0]);
   }

   public void resultReturned(int param1, int param2, Intent param3) {
      // $FF: Couldn't be decompiled
   }
}
