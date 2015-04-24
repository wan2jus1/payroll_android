package com.google.appinventor.components.runtime;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.ActivityResultListener;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Deleteable;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.util.AnimationUtil;

@DesignerComponent(
   category = ComponentCategory.CONNECTIVITY,
   description = "A component that can launch an activity using the <code>StartActivity</code> method. \n<p>Activities that can be launched include:<ul> <li> Starting another App Inventor for Android app. \n To do so, first      find out the <em>class</em> of the other application by      downloading the source code and using a file explorer or unzip      utility to find a file named      \"youngandroidproject/project.properties\".  \n The first line of      the file will start with \"main=\" and be followed by the class      name; for example,      <code>main=com.gmail.Bitdiddle.Ben.HelloPurr.Screen1</code>.       (The first components indicate that it was created by      Ben.Bitdiddle@gmail.com.)  \n To make your      <code>ActivityStarter</code> launch this application, set the      following properties: <ul>\n      <li> <code>ActivityPackage</code> to the class name, dropping the           last component (for example,           <code>com.gmail.Bitdiddle.Ben.HelloPurr</code>)</li>\n      <li> <code>ActivityClass</code> to the entire class name (for           example,           <code>com.gmail.Bitdiddle.Ben.HelloPurr.Screen1</code>)</li>      </ul></li> \n<li> Starting the camera application by setting the following      properties:<ul> \n     <li> <code>Action: android.intent.action.MAIN</code> </li> \n     <li> <code>ActivityPackage: com.android.camera</code> </li> \n     <li> <code>ActivityClass: com.android.camera.Camera</code></li>\n      </ul></li>\n<li> Performing web search.  Assuming the term you want to search      for is \"vampire\" (feel free to substitute your own choice), \n     set the properties to:\n<ul><code>     <li>Action: android.intent.action.WEB_SEARCH</li>      <li>ExtraKey: query</li>      <li>ExtraValue: vampire</li>      <li>ActivityPackage: com.google.android.providers.enhancedgooglesearch</li>     <li>ActivityClass: com.google.android.providers.enhancedgooglesearch.Launcher</li>      </code></ul></li> \n<li> Opening a browser to a specified web page.  Assuming the page you      want to go to is \"www.facebook.com\" (feel free to substitute      your own choice), set the properties to:\n<ul><code>      <li>Action: android.intent.action.VIEW</li>      <li>DataUri: http://www.facebook.com</li> </code> </ul> </li> </ul></p>",
   designerHelpDescription = "A component that can launch an activity using the <code>StartActivity</code> method.<p>Activities that can be launched include: <ul> \n<li> starting other App Inventor for Android apps </li> \n<li> starting the camera application </li> \n<li> performing web search </li> \n<li> opening a browser to a specified web page</li> \n<li> opening the map application to a specified location</li></ul> \nYou can also launch activities that return text data.  See the documentation on using the Activity Starter for examples.</p>",
   iconName = "images/activityStarter.png",
   nonVisible = true,
   version = 4
)
@SimpleObject
public class ActivityStarter extends AndroidNonvisibleComponent implements ActivityResultListener, Component, Deleteable {

   private String action;
   private String activityClass;
   private String activityPackage;
   private final ComponentContainer container;
   private String dataType;
   private String dataUri;
   private String extraKey;
   private String extraValue;
   private int requestCode;
   private String result;
   private Intent resultIntent;
   private String resultName;


   public ActivityStarter(ComponentContainer var1) {
      super(var1.$form());
      this.container = var1;
      this.result = "";
      this.Action("android.intent.action.MAIN");
      this.ActivityPackage("");
      this.ActivityClass("");
      this.DataUri("");
      this.DataType("");
      this.ExtraKey("");
      this.ExtraValue("");
      this.ResultName("");
   }

   private Intent buildActivityIntent() {
      Uri var1;
      if(this.dataUri.length() != 0) {
         var1 = Uri.parse(this.dataUri);
      } else {
         var1 = null;
      }

      Intent var2;
      if(var1 != null) {
         var2 = new Intent(this.action, var1);
      } else {
         var2 = new Intent(this.action);
      }

      if(this.dataType.length() != 0) {
         if(var1 != null) {
            var2.setDataAndType(var1, this.dataType);
         } else {
            var2.setType(this.dataType);
         }
      }

      if(this.activityPackage.length() != 0 || this.activityClass.length() != 0) {
         var2.setComponent(new ComponentName(this.activityPackage, this.activityClass));
      }

      if(this.extraKey.length() != 0 && this.extraValue.length() != 0) {
         var2.putExtra(this.extraKey, this.extraValue);
      }

      return var2;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String Action() {
      return this.action;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty
   public void Action(String var1) {
      this.action = var1.trim();
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String ActivityClass() {
      return this.activityClass;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty
   public void ActivityClass(String var1) {
      this.activityClass = var1.trim();
   }

   @SimpleEvent(
      description = "The ActivityError event is no longer used. Please use the Screen.ErrorOccurred event instead.",
      userVisible = false
   )
   public void ActivityError(String var1) {
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String ActivityPackage() {
      return this.activityPackage;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty
   public void ActivityPackage(String var1) {
      this.activityPackage = var1.trim();
   }

   @SimpleEvent(
      description = "Event raised after this ActivityStarter returns."
   )
   public void AfterActivity(String var1) {
      EventDispatcher.dispatchEvent(this, "AfterActivity", new Object[]{var1});
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String DataType() {
      return this.dataType;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty
   public void DataType(String var1) {
      this.dataType = var1.trim();
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String DataUri() {
      return this.dataUri;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty
   public void DataUri(String var1) {
      this.dataUri = var1.trim();
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String ExtraKey() {
      return this.extraKey;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty
   public void ExtraKey(String var1) {
      this.extraKey = var1.trim();
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String ExtraValue() {
      return this.extraValue;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty
   public void ExtraValue(String var1) {
      this.extraValue = var1.trim();
   }

   @SimpleFunction(
      description = "Returns the name of the activity that corresponds to this ActivityStarter, or an empty string if no corresponding activity can be found."
   )
   public String ResolveActivity() {
      Intent var1 = this.buildActivityIntent();
      ResolveInfo var2 = this.container.$context().getPackageManager().resolveActivity(var1, 0);
      return var2 != null && var2.activityInfo != null?var2.activityInfo.name:"";
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String Result() {
      return this.result;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String ResultName() {
      return this.resultName;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty
   public void ResultName(String var1) {
      this.resultName = var1.trim();
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String ResultType() {
      if(this.resultIntent != null) {
         String var1 = this.resultIntent.getType();
         if(var1 != null) {
            return var1;
         }
      }

      return "";
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String ResultUri() {
      if(this.resultIntent != null) {
         String var1 = this.resultIntent.getDataString();
         if(var1 != null) {
            return var1;
         }
      }

      return "";
   }

   @SimpleFunction(
      description = "Start the activity corresponding to this ActivityStarter."
   )
   public void StartActivity() {
      this.resultIntent = null;
      this.result = "";
      Intent var1 = this.buildActivityIntent();
      if(this.requestCode == 0) {
         this.requestCode = this.form.registerForActivityResult(this);
      }

      try {
         this.container.$context().startActivityForResult(var1, this.requestCode);
         String var3 = this.container.$form().getOpenAnimType();
         AnimationUtil.ApplyOpenScreenAnimation(this.container.$context(), var3);
      } catch (ActivityNotFoundException var2) {
         this.form.dispatchErrorOccurredEvent(this, "StartActivity", 601, new Object[0]);
      }
   }

   public void onDelete() {
      this.form.unregisterForActivityResult(this);
   }

   public void resultReturned(int var1, int var2, Intent var3) {
      if(var1 == this.requestCode) {
         Log.i("ActivityStarter", "resultReturned - resultCode = " + var2);
         if(var2 == -1) {
            this.resultIntent = var3;
            if(this.resultName.length() != 0 && this.resultIntent != null && this.resultIntent.hasExtra(this.resultName)) {
               this.result = this.resultIntent.getStringExtra(this.resultName);
            } else {
               this.result = "";
            }

            this.AfterActivity(this.result);
         }
      }

   }
}
