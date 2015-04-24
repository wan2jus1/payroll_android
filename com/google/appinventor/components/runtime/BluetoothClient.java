package com.google.appinventor.components.runtime;

import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.BluetoothConnectionBase;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.util.BluetoothReflection;
import com.google.appinventor.components.runtime.util.SdkLevel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@DesignerComponent(
   category = ComponentCategory.CONNECTIVITY,
   description = "Bluetooth client component",
   iconName = "images/bluetooth.png",
   nonVisible = true,
   version = 5
)
@SimpleObject
@UsesPermissions(
   permissionNames = "android.permission.BLUETOOTH, android.permission.BLUETOOTH_ADMIN"
)
public final class BluetoothClient extends BluetoothConnectionBase {

   private static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
   private Set acceptableDeviceClasses;
   private final List attachedComponents = new ArrayList();


   public BluetoothClient(ComponentContainer var1) {
      super((ComponentContainer)var1, (String)"BluetoothClient");
   }

   private void connect(Object var1, UUID var2) throws IOException {
      Object var3;
      if(!this.secure && SdkLevel.getLevel() >= 10) {
         var3 = BluetoothReflection.createInsecureRfcommSocketToServiceRecord(var1, var2);
      } else {
         var3 = BluetoothReflection.createRfcommSocketToServiceRecord(var1, var2);
      }

      BluetoothReflection.connectToBluetoothSocket(var3);
      this.setConnection(var3);
      Log.i(this.logTag, "Connected to Bluetooth device " + BluetoothReflection.getBluetoothDeviceAddress(var1) + " " + BluetoothReflection.getBluetoothDeviceName(var1) + ".");
   }

   private boolean connect(String var1, String var2, String var3) {
      Object var5 = BluetoothReflection.getBluetoothAdapter();
      if(var5 == null) {
         this.form.dispatchErrorOccurredEvent(this, var1, 501, new Object[0]);
         return false;
      } else if(!BluetoothReflection.isBluetoothEnabled(var5)) {
         this.form.dispatchErrorOccurredEvent(this, var1, 502, new Object[0]);
         return false;
      } else {
         int var6 = var2.indexOf(" ");
         String var4 = var2;
         if(var6 != -1) {
            var4 = var2.substring(0, var6);
         }

         if(!BluetoothReflection.checkBluetoothAddress(var5, var4)) {
            this.form.dispatchErrorOccurredEvent(this, var1, 503, new Object[0]);
            return false;
         } else {
            Object var9 = BluetoothReflection.getRemoteDevice(var5, var4);
            if(!BluetoothReflection.isBonded(var9)) {
               this.form.dispatchErrorOccurredEvent(this, var1, 504, new Object[0]);
               return false;
            } else if(!this.isDeviceClassAcceptable(var9)) {
               this.form.dispatchErrorOccurredEvent(this, var1, 505, new Object[0]);
               return false;
            } else {
               UUID var10;
               try {
                  var10 = UUID.fromString(var3);
               } catch (IllegalArgumentException var8) {
                  this.form.dispatchErrorOccurredEvent(this, var1, 506, new Object[]{var3});
                  return false;
               }

               this.Disconnect();

               try {
                  this.connect(var9, var10);
                  return true;
               } catch (IOException var7) {
                  this.Disconnect();
                  this.form.dispatchErrorOccurredEvent(this, var1, 507, new Object[0]);
                  return false;
               }
            }
         }
      }
   }

   private boolean isDeviceClassAcceptable(Object var1) {
      if(this.acceptableDeviceClasses == null) {
         return true;
      } else {
         var1 = BluetoothReflection.getBluetoothClass(var1);
         if(var1 == null) {
            return false;
         } else {
            int var2 = BluetoothReflection.getDeviceClass(var1);
            return this.acceptableDeviceClasses.contains(Integer.valueOf(var2));
         }
      }
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The addresses and names of paired Bluetooth devices"
   )
   public List AddressesAndNames() {
      ArrayList var1 = new ArrayList();
      Object var2 = BluetoothReflection.getBluetoothAdapter();
      if(var2 != null && BluetoothReflection.isBluetoothEnabled(var2)) {
         Iterator var5 = BluetoothReflection.getBondedDevices(var2).iterator();

         while(var5.hasNext()) {
            Object var4 = var5.next();
            if(this.isDeviceClassAcceptable(var4)) {
               String var3 = BluetoothReflection.getBluetoothDeviceName(var4);
               String var6 = BluetoothReflection.getBluetoothDeviceAddress(var4);
               var1.add(var6 + " " + var3);
            }
         }
      }

      return var1;
   }

   @SimpleFunction(
      description = "Connect to the Bluetooth device with the specified address and the Serial Port Profile (SPP). Returns true if the connection was successful."
   )
   public boolean Connect(String var1) {
      return this.connect("Connect", var1, "00001101-0000-1000-8000-00805F9B34FB");
   }

   @SimpleFunction(
      description = "Connect to the Bluetooth device with the specified address and UUID. Returns true if the connection was successful."
   )
   public boolean ConnectWithUUID(String var1, String var2) {
      return this.connect("ConnectWithUUID", var1, var2);
   }

   @SimpleFunction(
      description = "Checks whether the Bluetooth device with the specified address is paired."
   )
   public boolean IsDevicePaired(String var1) {
      Object var3 = BluetoothReflection.getBluetoothAdapter();
      if(var3 == null) {
         this.form.dispatchErrorOccurredEvent(this, "IsDevicePaired", 501, new Object[0]);
         return false;
      } else if(!BluetoothReflection.isBluetoothEnabled(var3)) {
         this.form.dispatchErrorOccurredEvent(this, "IsDevicePaired", 502, new Object[0]);
         return false;
      } else {
         int var4 = var1.indexOf(" ");
         String var2 = var1;
         if(var4 != -1) {
            var2 = var1.substring(0, var4);
         }

         if(!BluetoothReflection.checkBluetoothAddress(var3, var2)) {
            this.form.dispatchErrorOccurredEvent(this, "IsDevicePaired", 503, new Object[0]);
            return false;
         } else {
            return BluetoothReflection.isBonded(BluetoothReflection.getRemoteDevice(var3, var2));
         }
      }
   }

   boolean attachComponent(Component var1, Set var2) {
      boolean var4 = false;
      boolean var3;
      if(this.attachedComponents.isEmpty()) {
         HashSet var5;
         if(var2 == null) {
            var5 = null;
         } else {
            var5 = new HashSet(var2);
         }

         this.acceptableDeviceClasses = var5;
      } else if(this.acceptableDeviceClasses == null) {
         if(var2 != null) {
            return false;
         }
      } else {
         var3 = var4;
         if(var2 == null) {
            return var3;
         }

         var3 = var4;
         if(!this.acceptableDeviceClasses.containsAll(var2)) {
            return var3;
         }

         if(!var2.containsAll(this.acceptableDeviceClasses)) {
            return false;
         }
      }

      this.attachedComponents.add(var1);
      var3 = true;
      return var3;
   }

   void detachComponent(Component var1) {
      this.attachedComponents.remove(var1);
      if(this.attachedComponents.isEmpty()) {
         this.acceptableDeviceClasses = null;
      }

   }
}
