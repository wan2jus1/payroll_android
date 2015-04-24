package com.google.appinventor.components.runtime;

import android.os.Handler;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.BluetoothConnectionBase;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.util.BluetoothReflection;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@DesignerComponent(
   category = ComponentCategory.CONNECTIVITY,
   description = "Bluetooth server component",
   iconName = "images/bluetooth.png",
   nonVisible = true,
   version = 5
)
@SimpleObject
@UsesPermissions(
   permissionNames = "android.permission.BLUETOOTH, android.permission.BLUETOOTH_ADMIN"
)
public final class BluetoothServer extends BluetoothConnectionBase {

   private static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
   private final Handler androidUIHandler = new Handler();
   private final AtomicReference arBluetoothServerSocket = new AtomicReference();


   public BluetoothServer(ComponentContainer var1) {
      super((ComponentContainer)var1, (String)"BluetoothServer");
   }

   private void accept(String param1, String param2, String param3) {
      // $FF: Couldn't be decompiled
   }

   // $FF: synthetic method
   static AtomicReference access$000(BluetoothServer var0) {
      return var0.arBluetoothServerSocket;
   }

   // $FF: synthetic method
   static Handler access$100(BluetoothServer var0) {
      return var0.androidUIHandler;
   }

   @SimpleFunction(
      description = "Accept an incoming connection with the Serial Port Profile (SPP)."
   )
   public void AcceptConnection(String var1) {
      this.accept("AcceptConnection", var1, "00001101-0000-1000-8000-00805F9B34FB");
   }

   @SimpleFunction(
      description = "Accept an incoming connection with a specific UUID."
   )
   public void AcceptConnectionWithUUID(String var1, String var2) {
      this.accept("AcceptConnectionWithUUID", var1, var2);
   }

   @SimpleEvent(
      description = "Indicates that a bluetooth connection has been accepted."
   )
   public void ConnectionAccepted() {
      Log.i(this.logTag, "Successfullly accepted bluetooth connection.");
      EventDispatcher.dispatchEvent(this, "ConnectionAccepted", new Object[0]);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public final boolean IsAccepting() {
      return this.arBluetoothServerSocket.get() != null;
   }

   @SimpleFunction(
      description = "Stop accepting an incoming connection."
   )
   public void StopAccepting() {
      Object var1 = this.arBluetoothServerSocket.getAndSet((Object)null);
      if(var1 != null) {
         try {
            BluetoothReflection.closeBluetoothServerSocket(var1);
         } catch (IOException var2) {
            Log.w(this.logTag, "Error while closing bluetooth server socket: " + var2.getMessage());
            return;
         }
      }

   }
}
