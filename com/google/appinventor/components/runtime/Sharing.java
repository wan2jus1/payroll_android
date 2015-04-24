package com.google.appinventor.components.runtime;

import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;

@DesignerComponent(
   category = ComponentCategory.SOCIAL,
   description = "Sharing is a non-visible component that enables sharing files and/or messages between your app and other apps installed on a device. The component will display a list of the installed apps that can handle the information provided, and will allow the user to choose one to share the content with, for instance a mail app, a social network app, a texting app, and so on.<br>The file path can be taken directly from other components such as the Camera or the ImagePicker, but can also be specified directly to read from storage. Be aware that different devices treat storage differently, so a few things to try if, for instance, you have a file called arrow.gif in the folder <code>Appinventor/assets</code>, would be: <ul><li><code>\"file:///sdcard/Appinventor/assets/arrow.gif\"</code></li> or <li><code>\"/storage/Appinventor/assets/arrow.gif\"</code></li></ul>",
   iconName = "images/sharing.png",
   nonVisible = true,
   version = 1
)
@SimpleObject
@UsesPermissions(
   permissionNames = "android.permission.READ_EXTERNAL_STORAGE"
)
public class Sharing extends AndroidNonvisibleComponent {

   public Sharing(ComponentContainer var1) {
      super(var1.$form());
   }

   @SimpleFunction(
      description = "Shares a file through any capable application installed on the phone by displaying a list of the available apps and allowing the user to choose one from the list. The selected app will open with the file inserted on it."
   )
   public void ShareFile(String var1) {
      this.ShareFileWithMessage(var1, "");
   }

   @SimpleFunction(
      description = "Shares both a file and a message through any capable application installed on the phone by displaying a list of available apps and allowing the user to  choose one from the list. The selected app will open with the file and message inserted on it."
   )
   public void ShareFileWithMessage(String var1, String var2) {
      String var3 = var1;
      if(!var1.startsWith("file://")) {
         var3 = "file://" + var1;
      }

      Uri var5 = Uri.parse(var3);
      if((new java.io.File(var5.getPath())).isFile()) {
         var3 = var3.substring(var3.lastIndexOf(".") + 1).toLowerCase();
         var3 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3);
         Intent var4 = new Intent("android.intent.action.SEND");
         var4.putExtra("android.intent.extra.STREAM", var5);
         var4.setType(var3);
         if(var2.length() > 0) {
            var4.putExtra("android.intent.extra.TEXT", var2);
         }

         this.form.startActivity(var4);
      } else {
         var1 = "ShareFile";
         if(var2.equals("")) {
            var1 = "ShareFileWithMessage";
         }

         this.form.dispatchErrorOccurredEvent(this, var1, 2001, new Object[]{var3});
      }
   }

   @SimpleFunction(
      description = "Shares a message through any capable application installed on the phone by displaying a list of the available apps and allowing the user to choose one from the list. The selected app will open with the message inserted on it."
   )
   public void ShareMessage(String var1) {
      Intent var2 = new Intent("android.intent.action.SEND");
      var2.putExtra("android.intent.extra.TEXT", var1);
      var2.setType("text/plain");
      this.form.startActivity(var2);
   }
}
