package com.google.appinventor.components.runtime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.TelephonyManager;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.OnDestroyListener;
import com.google.appinventor.components.runtime.util.PhoneCallUtil;

@DesignerComponent(
   category = ComponentCategory.SOCIAL,
   description = "<p>A non-visible component that makes a phone call to the number specified in the <code>PhoneNumber</code> property, which can be set either in the Designer or Blocks Editor. The component has a <code>MakePhoneCall</code> method, enabling the program to launch a phone call.</p><p>Often, this component is used with the <code>ContactPicker</code> component, which lets the user select a contact from the ones stored on the phone and sets the <code>PhoneNumber</code> property to the contact\'s phone number.</p><p>To directly specify the phone number (e.g., 650-555-1212), set the <code>PhoneNumber</code> property to a Text with the specified digits (e.g., \"6505551212\").  Dashes, dots, and parentheses may be included (e.g., \"(650)-555-1212\") but will be ignored; spaces may not be included.</p>",
   iconName = "images/phoneCall.png",
   nonVisible = true,
   version = 2
)
@SimpleObject
@UsesPermissions(
   permissionNames = "android.permission.CALL_PHONE, android.permission.READ_PHONE_STATE, android.permission.PROCESS_OUTGOING_CALLS"
)
public class PhoneCall extends AndroidNonvisibleComponent implements Component, OnDestroyListener {

   private final PhoneCall.CallStateReceiver callStateReceiver;
   private final Context context;
   private String phoneNumber;


   public PhoneCall(ComponentContainer var1) {
      super(var1.$form());
      this.context = var1.$context();
      this.form.registerForOnDestroy(this);
      this.PhoneNumber("");
      this.callStateReceiver = new PhoneCall.CallStateReceiver();
      this.registerCallStateMonitor();
   }

   private void registerCallStateMonitor() {
      IntentFilter var1 = new IntentFilter();
      var1.addAction("android.intent.action.NEW_OUTGOING_CALL");
      var1.addAction("android.intent.action.PHONE_STATE");
      this.context.registerReceiver(this.callStateReceiver, var1);
   }

   private void unregisterCallStateMonitor() {
      this.context.unregisterReceiver(this.callStateReceiver);
   }

   @SimpleEvent(
      description = "Event indicating that an incoming phone call is answered. phoneNumber is the incoming call phone number."
   )
   public void IncomingCallAnswered(String var1) {
      EventDispatcher.dispatchEvent(this, "IncomingCallAnswered", new Object[]{var1});
   }

   @SimpleFunction
   public void MakePhoneCall() {
      PhoneCallUtil.makePhoneCall(this.context, this.phoneNumber);
   }

   @SimpleEvent(
      description = "Event indicating that a phone call has ended. If status is 1, incoming call is missed or rejected; if status is 2, incoming call is answered before hanging up; if status is 3, outgoing call is hung up. phoneNumber is the ended call phone number."
   )
   public void PhoneCallEnded(int var1, String var2) {
      EventDispatcher.dispatchEvent(this, "PhoneCallEnded", new Object[]{Integer.valueOf(var1), var2});
   }

   @SimpleEvent(
      description = "Event indicating that a phonecall has started. If status is 1, incoming call is ringing; if status is 2, outgoing call is dialled. phoneNumber is the incoming/outgoing phone number."
   )
   public void PhoneCallStarted(int var1, String var2) {
      EventDispatcher.dispatchEvent(this, "PhoneCallStarted", new Object[]{Integer.valueOf(var1), var2});
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String PhoneNumber() {
      return this.phoneNumber;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty
   public void PhoneNumber(String var1) {
      this.phoneNumber = var1;
   }

   public void onDestroy() {
      this.unregisterCallStateMonitor();
   }

   private class CallStateReceiver extends BroadcastReceiver {

      private String number = "";
      private int status = 0;


      public void onReceive(Context var1, Intent var2) {
         String var3 = var2.getAction();
         if("android.intent.action.PHONE_STATE".equals(var3)) {
            var3 = var2.getStringExtra("state");
            if(TelephonyManager.EXTRA_STATE_RINGING.equals(var3)) {
               this.status = 1;
               this.number = var2.getStringExtra("incoming_number");
               PhoneCall.this.PhoneCallStarted(1, this.number);
            } else if(TelephonyManager.EXTRA_STATE_OFFHOOK.equals(var3)) {
               if(this.status == 1) {
                  this.status = 3;
                  PhoneCall.this.IncomingCallAnswered(this.number);
                  return;
               }
            } else if(TelephonyManager.EXTRA_STATE_IDLE.equals(var3)) {
               if(this.status == 1) {
                  PhoneCall.this.PhoneCallEnded(1, this.number);
               } else if(this.status == 3) {
                  PhoneCall.this.PhoneCallEnded(2, this.number);
               } else if(this.status == 2) {
                  PhoneCall.this.PhoneCallEnded(3, this.number);
               }

               this.status = 0;
               this.number = "";
               return;
            }
         } else if("android.intent.action.NEW_OUTGOING_CALL".equals(var3)) {
            this.status = 2;
            this.number = var2.getStringExtra("android.intent.extra.PHONE_NUMBER");
            PhoneCall.this.PhoneCallStarted(2, this.number);
            return;
         }

      }
   }
}
