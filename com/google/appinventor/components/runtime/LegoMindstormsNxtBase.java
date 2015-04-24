package com.google.appinventor.components.runtime;

import android.util.Log;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.BluetoothClient;
import com.google.appinventor.components.runtime.BluetoothConnectionBase;
import com.google.appinventor.components.runtime.BluetoothConnectionListener;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Deleteable;
import com.google.appinventor.components.runtime.Form;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SimpleObject
public class LegoMindstormsNxtBase extends AndroidNonvisibleComponent implements BluetoothConnectionListener, Component, Deleteable {

   private static final Map ERROR_MESSAGES = new HashMap();
   private static final int TOY_ROBOT = 2052;
   protected BluetoothClient bluetooth;
   protected final String logTag;


   static {
      ERROR_MESSAGES.put(Integer.valueOf(32), "Pending communication transaction in progress");
      ERROR_MESSAGES.put(Integer.valueOf(64), "Specified mailbox queue is empty");
      ERROR_MESSAGES.put(Integer.valueOf(129), "No more handles");
      ERROR_MESSAGES.put(Integer.valueOf(130), "No space");
      ERROR_MESSAGES.put(Integer.valueOf(131), "No more files");
      ERROR_MESSAGES.put(Integer.valueOf(132), "End of file expected");
      ERROR_MESSAGES.put(Integer.valueOf(133), "End of file");
      ERROR_MESSAGES.put(Integer.valueOf(134), "Not a linear file");
      ERROR_MESSAGES.put(Integer.valueOf(135), "File not found");
      ERROR_MESSAGES.put(Integer.valueOf(136), "Handle already closed");
      ERROR_MESSAGES.put(Integer.valueOf(137), "No linear space");
      ERROR_MESSAGES.put(Integer.valueOf(138), "Undefined error");
      ERROR_MESSAGES.put(Integer.valueOf(139), "File is busy");
      ERROR_MESSAGES.put(Integer.valueOf(140), "No write buffers");
      ERROR_MESSAGES.put(Integer.valueOf(141), "Append not possible");
      ERROR_MESSAGES.put(Integer.valueOf(142), "File is full");
      ERROR_MESSAGES.put(Integer.valueOf(143), "File exists");
      ERROR_MESSAGES.put(Integer.valueOf(144), "Module not found");
      ERROR_MESSAGES.put(Integer.valueOf(145), "Out of boundary");
      ERROR_MESSAGES.put(Integer.valueOf(146), "Illegal file name");
      ERROR_MESSAGES.put(Integer.valueOf(147), "Illegal handle");
      ERROR_MESSAGES.put(Integer.valueOf(189), "Request failed (i.e. specified file not found)");
      ERROR_MESSAGES.put(Integer.valueOf(190), "Unknown command opcode");
      ERROR_MESSAGES.put(Integer.valueOf(191), "Insane packet");
      ERROR_MESSAGES.put(Integer.valueOf(192), "Data contains out-of-range values");
      ERROR_MESSAGES.put(Integer.valueOf(221), "Communication bus error");
      ERROR_MESSAGES.put(Integer.valueOf(222), "No free memory in communication buffer");
      ERROR_MESSAGES.put(Integer.valueOf(223), "Specified channel/connection is not valid");
      ERROR_MESSAGES.put(Integer.valueOf(224), "Specified channel/connection not configured or busy");
      ERROR_MESSAGES.put(Integer.valueOf(236), "No active program");
      ERROR_MESSAGES.put(Integer.valueOf(237), "Illegal size specified");
      ERROR_MESSAGES.put(Integer.valueOf(238), "Illegal mailbox queue ID specified");
      ERROR_MESSAGES.put(Integer.valueOf(239), "Attempted to access invalid field of a structure");
      ERROR_MESSAGES.put(Integer.valueOf(240), "Bad input or output specified");
      ERROR_MESSAGES.put(Integer.valueOf(251), "Insufficient memory available");
      ERROR_MESSAGES.put(Integer.valueOf(255), "Bad arguments");
   }

   protected LegoMindstormsNxtBase() {
      super((Form)null);
      this.logTag = null;
   }

   protected LegoMindstormsNxtBase(ComponentContainer var1, String var2) {
      super(var1.$form());
      this.logTag = var2;
   }

   private void handleError(String var1, int var2) {
      if(var2 >= 0) {
         String var3 = (String)ERROR_MESSAGES.get(Integer.valueOf(var2));
         if(var3 != null) {
            this.form.dispatchErrorOccurredEvent(this, var1, 404, new Object[]{var3});
         } else {
            this.form.dispatchErrorOccurredEvent(this, var1, 404, new Object[]{"Error code 0x" + Integer.toHexString(var2 & 255)});
         }
      }
   }

   private byte[] receiveReturnPackage(String var1) {
      byte[] var2 = this.bluetooth.read(var1, 2);
      if(var2.length == 2) {
         int var3 = this.getUWORDValueFromBytes(var2, 0);
         var2 = this.bluetooth.read(var1, var3);
         if(var2.length >= 3) {
            return var2;
         }
      }

      this.form.dispatchErrorOccurredEvent(this, var1, 403, new Object[0]);
      return new byte[0];
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The BluetoothClient component that should be used for communication.",
      userVisible = false
   )
   public BluetoothClient BluetoothClient() {
      return this.bluetooth;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "BluetoothClient"
   )
   @SimpleProperty(
      userVisible = false
   )
   public void BluetoothClient(BluetoothClient var1) {
      if(this.bluetooth != null) {
         this.bluetooth.removeBluetoothConnectionListener(this);
         this.bluetooth.detachComponent(this);
         this.bluetooth = null;
      }

      if(var1 != null) {
         this.bluetooth = var1;
         this.bluetooth.attachComponent(this, Collections.singleton(Integer.valueOf(2052)));
         this.bluetooth.addBluetoothConnectionListener(this);
         if(this.bluetooth.IsConnected()) {
            this.afterConnect(this.bluetooth);
         }
      }

   }

   public final void Initialize() {
   }

   public void afterConnect(BluetoothConnectionBase var1) {
   }

   public void beforeDisconnect(BluetoothConnectionBase var1) {
   }

   protected final boolean checkBluetooth(String var1) {
      if(this.bluetooth == null) {
         this.form.dispatchErrorOccurredEvent(this, var1, 401, new Object[0]);
         return false;
      } else if(!this.bluetooth.IsConnected()) {
         this.form.dispatchErrorOccurredEvent(this, var1, 402, new Object[0]);
         return false;
      } else {
         return true;
      }
   }

   protected final int convertMotorPortLetterToNumber(char var1) {
      if(var1 != 65 && var1 != 97) {
         if(var1 != 66 && var1 != 98) {
            if(var1 != 67 && var1 != 99) {
               throw new IllegalArgumentException("Illegal motor port letter " + var1);
            } else {
               return 2;
            }
         } else {
            return 1;
         }
      } else {
         return 0;
      }
   }

   protected final int convertMotorPortLetterToNumber(String var1) {
      if(var1.length() == 1) {
         return this.convertMotorPortLetterToNumber(var1.charAt(0));
      } else {
         throw new IllegalArgumentException("Illegal motor port letter " + var1);
      }
   }

   protected final int convertSensorPortLetterToNumber(char var1) {
      if(var1 == 49) {
         return 0;
      } else if(var1 == 50) {
         return 1;
      } else if(var1 == 51) {
         return 2;
      } else if(var1 == 52) {
         return 3;
      } else {
         throw new IllegalArgumentException("Illegal sensor port letter " + var1);
      }
   }

   protected final int convertSensorPortLetterToNumber(String var1) {
      if(var1.length() == 1) {
         return this.convertSensorPortLetterToNumber(var1.charAt(0));
      } else {
         throw new IllegalArgumentException("Illegal sensor port letter " + var1);
      }
   }

   protected final void copyBooleanValueToBytes(boolean var1, byte[] var2, int var3) {
      byte var4;
      if(var1) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      var2[var3] = var4;
   }

   protected final void copySBYTEValueToBytes(int var1, byte[] var2, int var3) {
      var2[var3] = (byte)var1;
   }

   protected final void copySLONGValueToBytes(int var1, byte[] var2, int var3) {
      var2[var3] = (byte)(var1 & 255);
      var1 >>= 8;
      var2[var3 + 1] = (byte)(var1 & 255);
      var1 >>= 8;
      var2[var3 + 2] = (byte)(var1 & 255);
      var2[var3 + 3] = (byte)(var1 >> 8 & 255);
   }

   protected final void copySWORDValueToBytes(int var1, byte[] var2, int var3) {
      var2[var3] = (byte)(var1 & 255);
      var2[var3 + 1] = (byte)(var1 >> 8 & 255);
   }

   protected final void copyStringValueToBytes(String var1, byte[] var2, int var3, int var4) {
      String var5 = var1;
      if(var1.length() > var4) {
         var5 = var1.substring(0, var4);
      }

      byte[] var7;
      try {
         var7 = var5.getBytes("ISO-8859-1");
      } catch (UnsupportedEncodingException var6) {
         Log.w(this.logTag, "UnsupportedEncodingException: " + var6.getMessage());
         var7 = var5.getBytes();
      }

      System.arraycopy(var7, 0, var2, var3, Math.min(var4, var7.length));
   }

   protected final void copyUBYTEValueToBytes(int var1, byte[] var2, int var3) {
      var2[var3] = (byte)var1;
   }

   protected final void copyULONGValueToBytes(long var1, byte[] var3, int var4) {
      var3[var4] = (byte)((int)(var1 & 255L));
      var1 >>= 8;
      var3[var4 + 1] = (byte)((int)(var1 & 255L));
      var1 >>= 8;
      var3[var4 + 2] = (byte)((int)(var1 & 255L));
      var3[var4 + 3] = (byte)((int)(var1 >> 8 & 255L));
   }

   protected final void copyUWORDValueToBytes(int var1, byte[] var2, int var3) {
      var2[var3] = (byte)(var1 & 255);
      var2[var3 + 1] = (byte)(var1 >> 8 & 255);
   }

   protected final boolean evaluateStatus(String var1, byte[] var2, byte var3) {
      int var4 = this.getStatus(var1, var2, var3);
      if(var4 == 0) {
         return true;
      } else {
         this.handleError(var1, var4);
         return false;
      }
   }

   protected final boolean getBooleanValueFromBytes(byte[] var1, int var2) {
      return var1[var2] != 0;
   }

   protected final byte[] getInputValues(String var1, int var2) {
      byte[] var3 = new byte[]{(byte)0, (byte)7, (byte)0};
      this.copyUBYTEValueToBytes(var2, var3, 2);
      byte[] var4 = this.sendCommandAndReceiveReturnPackage(var1, var3);
      if(this.evaluateStatus(var1, var4, var3[1])) {
         if(var4.length == 16) {
            return var4;
         }

         Log.w(this.logTag, var1 + ": unexpected return package length " + var4.length + " (expected 16)");
      }

      return null;
   }

   protected final int getSBYTEValueFromBytes(byte[] var1, int var2) {
      return var1[var2];
   }

   protected final int getSLONGValueFromBytes(byte[] var1, int var2) {
      return var1[var2] & 255 | (var1[var2 + 1] & 255) << 8 | (var1[var2 + 2] & 255) << 16 | var1[var2 + 3] << 24;
   }

   protected final int getSWORDValueFromBytes(byte[] var1, int var2) {
      return var1[var2] & 255 | var1[var2 + 1] << 8;
   }

   protected final int getStatus(String var1, byte[] var2, byte var3) {
      if(var2.length >= 3) {
         if(var2[0] != 2) {
            Log.w(this.logTag, var1 + ": unexpected return package byte 0: 0x" + Integer.toHexString(var2[0] & 255) + " (expected 0x02)");
         }

         if(var2[1] != var3) {
            Log.w(this.logTag, var1 + ": unexpected return package byte 1: 0x" + Integer.toHexString(var2[1] & 255) + " (expected 0x" + Integer.toHexString(var3 & 255) + ")");
         }

         return this.getUBYTEValueFromBytes(var2, 2);
      } else {
         Log.w(this.logTag, var1 + ": unexpected return package length " + var2.length + " (expected >= 3)");
         return -1;
      }
   }

   protected final String getStringValueFromBytes(byte[] var1, int var2) {
      byte var5 = 0;
      int var3 = var2;

      int var4;
      while(true) {
         var4 = var5;
         if(var3 >= var1.length) {
            break;
         }

         if(var1[var3] == 0) {
            var4 = var3 - var2;
            break;
         }

         ++var3;
      }

      return this.getStringValueFromBytes(var1, var2, var4);
   }

   protected final String getStringValueFromBytes(byte[] var1, int var2, int var3) {
      try {
         String var4 = new String(var1, var2, var3, "ISO-8859-1");
         return var4;
      } catch (UnsupportedEncodingException var5) {
         Log.w(this.logTag, "UnsupportedEncodingException: " + var5.getMessage());
         return new String(var1, var2, var3);
      }
   }

   protected final int getUBYTEValueFromBytes(byte[] var1, int var2) {
      return var1[var2] & 255;
   }

   protected final long getULONGValueFromBytes(byte[] var1, int var2) {
      return (long)var1[var2] & 255L | ((long)var1[var2 + 1] & 255L) << 8 | ((long)var1[var2 + 2] & 255L) << 16 | ((long)var1[var2 + 3] & 255L) << 24;
   }

   protected final int getUWORDValueFromBytes(byte[] var1, int var2) {
      return var1[var2] & 255 | (var1[var2 + 1] & 255) << 8;
   }

   protected final int lsGetStatus(String var1, int var2) {
      byte var5 = 0;
      byte[] var3 = new byte[]{(byte)0, (byte)14, (byte)0};
      this.copyUBYTEValueToBytes(var2, var3, 2);
      byte[] var4 = this.sendCommandAndReceiveReturnPackage(var1, var3);
      var2 = var5;
      if(this.evaluateStatus(var1, var4, var3[1])) {
         if(var4.length != 4) {
            Log.w(this.logTag, var1 + ": unexpected return package length " + var4.length + " (expected 4)");
            return 0;
         }

         var2 = this.getUBYTEValueFromBytes(var4, 3);
      }

      return var2;
   }

   protected final byte[] lsRead(String var1, int var2) {
      byte[] var3 = new byte[]{(byte)0, (byte)16, (byte)0};
      this.copyUBYTEValueToBytes(var2, var3, 2);
      byte[] var4 = this.sendCommandAndReceiveReturnPackage(var1, var3);
      if(this.evaluateStatus(var1, var4, var3[1])) {
         if(var4.length == 20) {
            return var4;
         }

         Log.w(this.logTag, var1 + ": unexpected return package length " + var4.length + " (expected 20)");
      }

      return null;
   }

   protected final void lsWrite(String var1, int var2, byte[] var3, int var4) {
      if(var3.length > 16) {
         throw new IllegalArgumentException("length must be <= 16");
      } else {
         byte[] var5 = new byte[var3.length + 5];
         var5[0] = 0;
         var5[1] = 15;
         this.copyUBYTEValueToBytes(var2, var5, 2);
         this.copyUBYTEValueToBytes(var3.length, var5, 3);
         this.copyUBYTEValueToBytes(var4, var5, 4);
         System.arraycopy(var3, 0, var5, 5, var3.length);
         this.evaluateStatus(var1, this.sendCommandAndReceiveReturnPackage(var1, var5), var5[1]);
      }
   }

   public void onDelete() {
      if(this.bluetooth != null) {
         this.bluetooth.removeBluetoothConnectionListener(this);
         this.bluetooth.detachComponent(this);
         this.bluetooth = null;
      }

   }

   protected final void resetInputScaledValue(String var1, int var2) {
      byte[] var3 = new byte[]{(byte)-128, (byte)8, (byte)0};
      this.copyUBYTEValueToBytes(var2, var3, 2);
      this.sendCommand(var1, var3);
   }

   protected final int sanitizePower(int var1) {
      int var2 = var1;
      if(var1 < -100) {
         Log.w(this.logTag, "power " + var1 + " is invalid, using -100.");
         var2 = -100;
      }

      var1 = var2;
      if(var2 > 100) {
         Log.w(this.logTag, "power " + var2 + " is invalid, using 100.");
         var1 = 100;
      }

      return var1;
   }

   protected final int sanitizeTurnRatio(int var1) {
      int var2 = var1;
      if(var1 < -100) {
         Log.w(this.logTag, "turnRatio " + var1 + " is invalid, using -100.");
         var2 = -100;
      }

      var1 = var2;
      if(var2 > 100) {
         Log.w(this.logTag, "turnRatio " + var2 + " is invalid, using 100.");
         var1 = 100;
      }

      return var1;
   }

   protected final void sendCommand(String var1, byte[] var2) {
      byte[] var3 = new byte[2];
      this.copyUWORDValueToBytes(var2.length, var3, 0);
      this.bluetooth.write(var1, var3);
      this.bluetooth.write(var1, var2);
   }

   protected final byte[] sendCommandAndReceiveReturnPackage(String var1, byte[] var2) {
      this.sendCommand(var1, var2);
      return this.receiveReturnPackage(var1);
   }

   protected final void setInputMode(String var1, int var2, int var3, int var4) {
      byte[] var5 = new byte[]{(byte)-128, (byte)5, (byte)0, (byte)0, (byte)0};
      this.copyUBYTEValueToBytes(var2, var5, 2);
      this.copyUBYTEValueToBytes(var3, var5, 3);
      this.copyUBYTEValueToBytes(var4, var5, 4);
      this.sendCommand(var1, var5);
   }

   protected final void setOutputState(String var1, int var2, int var3, int var4, int var5, int var6, int var7, long var8) {
      var3 = this.sanitizePower(var3);
      byte[] var10 = new byte[12];
      var10[0] = -128;
      var10[1] = 4;
      this.copyUBYTEValueToBytes(var2, var10, 2);
      this.copySBYTEValueToBytes(var3, var10, 3);
      this.copyUBYTEValueToBytes(var4, var10, 4);
      this.copyUBYTEValueToBytes(var5, var10, 5);
      this.copySBYTEValueToBytes(var6, var10, 6);
      this.copyUBYTEValueToBytes(var7, var10, 7);
      this.copyULONGValueToBytes(var8, var10, 8);
      this.sendCommand(var1, var10);
   }
}
