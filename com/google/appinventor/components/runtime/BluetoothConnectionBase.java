package com.google.appinventor.components.runtime;

import android.util.Log;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.BluetoothConnectionListener;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Deleteable;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.OnDestroyListener;
import com.google.appinventor.components.runtime.util.BluetoothReflection;
import com.google.appinventor.components.runtime.util.YailList;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SimpleObject
public abstract class BluetoothConnectionBase extends AndroidNonvisibleComponent implements Component, OnDestroyListener, Deleteable {

   private final List bluetoothConnectionListeners;
   private ByteOrder byteOrder;
   private Object connectedBluetoothSocket;
   private byte delimiter;
   private String encoding;
   private InputStream inputStream;
   protected final String logTag;
   private OutputStream outputStream;
   protected boolean secure;


   protected BluetoothConnectionBase(ComponentContainer var1, String var2) {
      this((Form)var1.$form(), (String)var2);
      this.form.registerForOnDestroy(this);
   }

   private BluetoothConnectionBase(Form var1, String var2) {
      super(var1);
      this.bluetoothConnectionListeners = new ArrayList();
      this.logTag = var2;
      this.HighByteFirst(false);
      this.CharacterEncoding("UTF-8");
      this.DelimiterByte(0);
      this.Secure(true);
   }

   protected BluetoothConnectionBase(OutputStream var1, InputStream var2) {
      this((Form)((Form)null), (String)((String)null));
      this.connectedBluetoothSocket = "Not Null";
      this.outputStream = var1;
      this.inputStream = var2;
   }

   private void fireAfterConnectEvent() {
      Iterator var1 = this.bluetoothConnectionListeners.iterator();

      while(var1.hasNext()) {
         ((BluetoothConnectionListener)var1.next()).afterConnect(this);
      }

   }

   private void fireBeforeDisconnectEvent() {
      Iterator var1 = this.bluetoothConnectionListeners.iterator();

      while(var1.hasNext()) {
         ((BluetoothConnectionListener)var1.next()).beforeDisconnect(this);
      }

   }

   private void prepareToDie() {
      if(this.connectedBluetoothSocket != null) {
         this.Disconnect();
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Whether Bluetooth is available on the device"
   )
   public boolean Available() {
      return BluetoothReflection.getBluetoothAdapter() != null;
   }

   @SimpleEvent(
      description = "The BluetoothError event is no longer used. Please use the Screen.ErrorOccurred event instead.",
      userVisible = false
   )
   public void BluetoothError(String var1, String var2) {
   }

   @SimpleFunction(
      description = "Returns an estimate of the number of bytes that can be received without blocking"
   )
   public int BytesAvailableToReceive() {
      if(!this.IsConnected()) {
         this.bluetoothError("BytesAvailableToReceive", 515, new Object[0]);
         return 0;
      } else {
         try {
            int var2 = this.inputStream.available();
            return var2;
         } catch (IOException var3) {
            this.bluetoothError("BytesAvailableToReceive", 517, new Object[]{var3.getMessage()});
            return 0;
         }
      }
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String CharacterEncoding() {
      return this.encoding;
   }

   @DesignerProperty(
      defaultValue = "UTF-8",
      editorType = "string"
   )
   @SimpleProperty
   public void CharacterEncoding(String var1) {
      try {
         "check".getBytes(var1);
         this.encoding = var1;
      } catch (UnsupportedEncodingException var3) {
         this.bluetoothError("CharacterEncoding", 519, new Object[]{var1});
      }
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public int DelimiterByte() {
      return this.delimiter;
   }

   @DesignerProperty(
      defaultValue = "0",
      editorType = "non_negative_integer"
   )
   @SimpleProperty
   public void DelimiterByte(int var1) {
      byte var2 = (byte)var1;
      int var3 = var1 >> 8;
      if(var3 != 0 && var3 != -1) {
         this.bluetoothError("DelimiterByte", 511, new Object[]{Integer.valueOf(var1)});
      } else {
         this.delimiter = var2;
      }
   }

   @SimpleFunction(
      description = "Disconnect from the connected Bluetooth device."
   )
   public final void Disconnect() {
      if(this.connectedBluetoothSocket != null) {
         this.fireBeforeDisconnectEvent();

         try {
            BluetoothReflection.closeBluetoothSocket(this.connectedBluetoothSocket);
            Log.i(this.logTag, "Disconnected from Bluetooth device.");
         } catch (IOException var2) {
            Log.w(this.logTag, "Error while disconnecting: " + var2.getMessage());
         }

         this.connectedBluetoothSocket = null;
      }

      this.inputStream = null;
      this.outputStream = null;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Whether Bluetooth is enabled"
   )
   public boolean Enabled() {
      Object var1 = BluetoothReflection.getBluetoothAdapter();
      return var1 != null && BluetoothReflection.isBluetoothEnabled(var1);
   }

   @DesignerProperty(
      defaultValue = "False",
      editorType = "boolean"
   )
   @SimpleProperty
   public void HighByteFirst(boolean var1) {
      ByteOrder var2;
      if(var1) {
         var2 = ByteOrder.BIG_ENDIAN;
      } else {
         var2 = ByteOrder.LITTLE_ENDIAN;
      }

      this.byteOrder = var2;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public boolean HighByteFirst() {
      return this.byteOrder == ByteOrder.BIG_ENDIAN;
   }

   public final void Initialize() {
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public final boolean IsConnected() {
      return this.connectedBluetoothSocket != null;
   }

   @SimpleFunction(
      description = "Receive a signed 1-byte number from the connected Bluetooth device."
   )
   public int ReceiveSigned1ByteNumber() {
      byte[] var1 = this.read("ReceiveSigned1ByteNumber", 1);
      return var1.length != 1?0:var1[0];
   }

   @SimpleFunction(
      description = "Receive a signed 2-byte number from the connected Bluetooth device."
   )
   public int ReceiveSigned2ByteNumber() {
      byte[] var1 = this.read("ReceiveSigned2ByteNumber", 2);
      if(var1.length != 2) {
         return 0;
      } else if(this.byteOrder == ByteOrder.BIG_ENDIAN) {
         byte var2 = var1[1];
         return var1[0] << 8 | var2 & 255;
      } else {
         return var1[0] & 255 | var1[1] << 8;
      }
   }

   @SimpleFunction(
      description = "Receive a signed 4-byte number from the connected Bluetooth device."
   )
   public long ReceiveSigned4ByteNumber() {
      byte[] var1 = this.read("ReceiveSigned4ByteNumber", 4);
      return var1.length != 4?0L:(this.byteOrder == ByteOrder.BIG_ENDIAN?(long)(var1[3] & 255 | (var1[2] & 255) << 8 | (var1[1] & 255) << 16 | var1[0] << 24):(long)(var1[0] & 255 | (var1[1] & 255) << 8 | (var1[2] & 255) << 16 | var1[3] << 24));
   }

   @SimpleFunction(
      description = "Receive multiple signed byte values from the connected Bluetooth device. If numberOfBytes is less than 0, read until a delimiter byte value is received."
   )
   public List ReceiveSignedBytes(int var1) {
      byte[] var2 = this.read("ReceiveSignedBytes", var1);
      ArrayList var3 = new ArrayList();

      for(var1 = 0; var1 < var2.length; ++var1) {
         var3.add(Integer.valueOf(var2[var1]));
      }

      return var3;
   }

   @SimpleFunction(
      description = "Receive text from the connected Bluetooth device. If numberOfBytes is less than 0, read until a delimiter byte value is received."
   )
   public String ReceiveText(int param1) {
      // $FF: Couldn't be decompiled
   }

   @SimpleFunction(
      description = "Receive an unsigned 1-byte number from the connected Bluetooth device."
   )
   public int ReceiveUnsigned1ByteNumber() {
      byte[] var1 = this.read("ReceiveUnsigned1ByteNumber", 1);
      return var1.length != 1?0:var1[0] & 255;
   }

   @SimpleFunction(
      description = "Receive a unsigned 2-byte number from the connected Bluetooth device."
   )
   public int ReceiveUnsigned2ByteNumber() {
      byte[] var1 = this.read("ReceiveUnsigned2ByteNumber", 2);
      if(var1.length != 2) {
         return 0;
      } else if(this.byteOrder == ByteOrder.BIG_ENDIAN) {
         byte var2 = var1[1];
         return (var1[0] & 255) << 8 | var2 & 255;
      } else {
         return var1[0] & 255 | (var1[1] & 255) << 8;
      }
   }

   @SimpleFunction(
      description = "Receive a unsigned 4-byte number from the connected Bluetooth device."
   )
   public long ReceiveUnsigned4ByteNumber() {
      byte[] var1 = this.read("ReceiveUnsigned4ByteNumber", 4);
      return var1.length != 4?0L:(this.byteOrder == ByteOrder.BIG_ENDIAN?(long)var1[3] & 255L | ((long)var1[2] & 255L) << 8 | ((long)var1[1] & 255L) << 16 | ((long)var1[0] & 255L) << 24:(long)var1[0] & 255L | ((long)var1[1] & 255L) << 8 | ((long)var1[2] & 255L) << 16 | ((long)var1[3] & 255L) << 24);
   }

   @SimpleFunction(
      description = "Receive multiple unsigned byte values from the connected Bluetooth device. If numberOfBytes is less than 0, read until a delimiter byte value is received."
   )
   public List ReceiveUnsignedBytes(int var1) {
      byte[] var2 = this.read("ReceiveUnsignedBytes", var1);
      ArrayList var3 = new ArrayList();

      for(var1 = 0; var1 < var2.length; ++var1) {
         var3.add(Integer.valueOf(var2[var1] & 255));
      }

      return var3;
   }

   @DesignerProperty(
      defaultValue = "True",
      editorType = "boolean"
   )
   @SimpleProperty
   public void Secure(boolean var1) {
      this.secure = var1;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Whether to invoke SSP (Simple Secure Pairing), which is supported on devices with Bluetooth v2.1 or higher. When working with embedded Bluetooth devices, this property may need to be set to False. For Android 2.0-2.2, this property setting will be ignored."
   )
   public boolean Secure() {
      return this.secure;
   }

   @SimpleFunction(
      description = "Send a 1-byte number to the connected Bluetooth device."
   )
   public void Send1ByteNumber(String var1) {
      int var4;
      try {
         var4 = Integer.decode(var1).intValue();
      } catch (NumberFormatException var5) {
         this.bluetoothError("Send1ByteNumber", 510, new Object[]{var1});
         return;
      }

      byte var2 = (byte)var4;
      var4 >>= 8;
      if(var4 != 0 && var4 != -1) {
         this.bluetoothError("Send1ByteNumber", 511, new Object[]{var1});
      } else {
         this.write("Send1ByteNumber", var2);
      }
   }

   @SimpleFunction(
      description = "Send a 2-byte number to the connected Bluetooth device."
   )
   public void Send2ByteNumber(String var1) {
      int var3;
      try {
         var3 = Integer.decode(var1).intValue();
      } catch (NumberFormatException var4) {
         this.bluetoothError("Send2ByteNumber", 510, new Object[]{var1});
         return;
      }

      byte[] var2 = new byte[2];
      if(this.byteOrder == ByteOrder.BIG_ENDIAN) {
         var2[1] = (byte)(var3 & 255);
         var3 >>= 8;
         var2[0] = (byte)(var3 & 255);
      } else {
         var2[0] = (byte)(var3 & 255);
         var3 >>= 8;
         var2[1] = (byte)(var3 & 255);
      }

      var3 >>= 8;
      if(var3 != 0 && var3 != -1) {
         this.bluetoothError("Send2ByteNumber", 512, new Object[]{var1, Integer.valueOf(2)});
      } else {
         this.write("Send2ByteNumber", var2);
      }
   }

   @SimpleFunction(
      description = "Send a 4-byte number to the connected Bluetooth device."
   )
   public void Send4ByteNumber(String var1) {
      long var3;
      try {
         var3 = Long.decode(var1).longValue();
      } catch (NumberFormatException var5) {
         this.bluetoothError("Send4ByteNumber", 510, new Object[]{var1});
         return;
      }

      byte[] var2 = new byte[4];
      if(this.byteOrder == ByteOrder.BIG_ENDIAN) {
         var2[3] = (byte)((int)(var3 & 255L));
         var3 >>= 8;
         var2[2] = (byte)((int)(var3 & 255L));
         var3 >>= 8;
         var2[1] = (byte)((int)(var3 & 255L));
         var3 >>= 8;
         var2[0] = (byte)((int)(var3 & 255L));
      } else {
         var2[0] = (byte)((int)(var3 & 255L));
         var3 >>= 8;
         var2[1] = (byte)((int)(var3 & 255L));
         var3 >>= 8;
         var2[2] = (byte)((int)(var3 & 255L));
         var3 >>= 8;
         var2[3] = (byte)((int)(var3 & 255L));
      }

      var3 >>= 8;
      if(var3 != 0L && var3 != -1L) {
         this.bluetoothError("Send4ByteNumber", 512, new Object[]{var1, Integer.valueOf(4)});
      } else {
         this.write("Send4ByteNumber", var2);
      }
   }

   @SimpleFunction(
      description = "Send a list of byte values to the connected Bluetooth device."
   )
   public void SendBytes(YailList var1) {
      Object[] var7 = var1.toArray();
      byte[] var2 = new byte[var7.length];

      for(int var4 = 0; var4 < var7.length; ++var4) {
         String var3 = var7[var4].toString();

         int var5;
         try {
            var5 = Integer.decode(var3).intValue();
         } catch (NumberFormatException var6) {
            this.bluetoothError("SendBytes", 513, new Object[]{Integer.valueOf(var4 + 1)});
            return;
         }

         var2[var4] = (byte)(var5 & 255);
         var5 >>= 8;
         if(var5 != 0 && var5 != -1) {
            this.bluetoothError("SendBytes", 514, new Object[]{Integer.valueOf(var4 + 1)});
            return;
         }
      }

      this.write("SendBytes", var2);
   }

   @SimpleFunction(
      description = "Send text to the connected Bluetooth device."
   )
   public void SendText(String var1) {
      byte[] var4;
      label12: {
         byte[] var2;
         try {
            var2 = var1.getBytes(this.encoding);
         } catch (UnsupportedEncodingException var3) {
            Log.w(this.logTag, "UnsupportedEncodingException: " + var3.getMessage());
            var4 = var1.getBytes();
            break label12;
         }

         var4 = var2;
      }

      this.write("SendText", var4);
   }

   void addBluetoothConnectionListener(BluetoothConnectionListener var1) {
      this.bluetoothConnectionListeners.add(var1);
   }

   protected void bluetoothError(String var1, int var2, Object ... var3) {
      this.form.dispatchErrorOccurredEvent(this, var1, var2, var3);
   }

   public void onDelete() {
      this.prepareToDie();
   }

   public void onDestroy() {
      this.prepareToDie();
   }

   protected final byte[] read(String param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   void removeBluetoothConnectionListener(BluetoothConnectionListener var1) {
      this.bluetoothConnectionListeners.remove(var1);
   }

   protected final void setConnection(Object var1) throws IOException {
      this.connectedBluetoothSocket = var1;
      this.inputStream = new BufferedInputStream(BluetoothReflection.getInputStream(this.connectedBluetoothSocket));
      this.outputStream = new BufferedOutputStream(BluetoothReflection.getOutputStream(this.connectedBluetoothSocket));
      this.fireAfterConnectEvent();
   }

   protected void write(String var1, byte var2) {
      if(!this.IsConnected()) {
         this.bluetoothError(var1, 515, new Object[0]);
      } else {
         try {
            this.outputStream.write(var2);
            this.outputStream.flush();
         } catch (IOException var4) {
            this.bluetoothError(var1, 516, new Object[]{var4.getMessage()});
         }
      }
   }

   protected void write(String var1, byte[] var2) {
      if(!this.IsConnected()) {
         this.bluetoothError(var1, 515, new Object[0]);
      } else {
         try {
            this.outputStream.write(var2);
            this.outputStream.flush();
         } catch (IOException var3) {
            this.bluetoothError(var1, 516, new Object[]{var3.getMessage()});
         }
      }
   }
}
