package com.google.appinventor.components.runtime;

import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.LegoMindstormsNxtBase;
import com.google.appinventor.components.runtime.util.YailList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@DesignerComponent(
   category = ComponentCategory.LEGOMINDSTORMS,
   description = "A component that provides a low-level interface to a LEGO MINDSTORMS NXT robot, with functions to send NXT Direct Commands.",
   iconName = "images/legoMindstormsNxt.png",
   nonVisible = true,
   version = 1
)
@SimpleObject
@UsesPermissions(
   permissionNames = "android.permission.INTERNET"
)
public class NxtDirectCommands extends LegoMindstormsNxtBase {

   public NxtDirectCommands(ComponentContainer var1) {
      super(var1, "NxtDirectCommands");
   }

   private void closeHandle(String var1, int var2) {
      byte[] var3 = new byte[]{(byte)1, (byte)-124, (byte)0};
      this.copyUBYTEValueToBytes(var2, var3, 2);
      this.evaluateStatus(var1, this.sendCommandAndReceiveReturnPackage(var1, var3), var3[1]);
   }

   private byte[] getOutputState(String var1, int var2) {
      byte[] var3 = new byte[]{(byte)0, (byte)6, (byte)0};
      this.copyUBYTEValueToBytes(var2, var3, 2);
      byte[] var4 = this.sendCommandAndReceiveReturnPackage(var1, var3);
      if(this.evaluateStatus(var1, var4, var3[1])) {
         if(var4.length == 25) {
            return var4;
         }

         Log.w(this.logTag, var1 + ": unexpected return package length " + var4.length + " (expected 25)");
      }

      return null;
   }

   private Integer openWrite(String var1, String var2, long var3) {
      byte[] var5 = new byte[26];
      var5[0] = 1;
      var5[1] = -127;
      this.copyStringValueToBytes(var2, var5, 2, 19);
      this.copyULONGValueToBytes(var3, var5, 22);
      byte[] var6 = this.sendCommandAndReceiveReturnPackage(var1, var5);
      if(this.evaluateStatus(var1, var6, var5[1])) {
         if(var6.length == 4) {
            return Integer.valueOf(this.getUBYTEValueFromBytes(var6, 3));
         }

         Log.w(this.logTag, var1 + ": unexpected return package length " + var6.length + " (expected 4)");
      }

      return null;
   }

   private Integer openWriteLinear(String var1, String var2, long var3) {
      byte[] var5 = new byte[26];
      var5[0] = 1;
      var5[1] = -119;
      this.copyStringValueToBytes(var2, var5, 2, 19);
      this.copyULONGValueToBytes(var3, var5, 22);
      byte[] var6 = this.sendCommandAndReceiveReturnPackage(var1, var5);
      if(this.evaluateStatus(var1, var6, var5[1])) {
         if(var6.length == 4) {
            return Integer.valueOf(this.getUBYTEValueFromBytes(var6, 3));
         }

         Log.w(this.logTag, var1 + ": unexpected return package length " + var6.length + " (expected 4)");
      }

      return null;
   }

   private int writeChunk(String var1, int var2, byte[] var3, int var4) throws IOException {
      byte var6 = 0;
      if(var4 > 32) {
         throw new IllegalArgumentException("length must be <= 32");
      } else {
         byte[] var5 = new byte[var4 + 3];
         var5[0] = 1;
         var5[1] = -125;
         this.copyUBYTEValueToBytes(var2, var5, 2);
         System.arraycopy(var3, 0, var5, 3, var4);
         var3 = this.sendCommandAndReceiveReturnPackage(var1, var5);
         var2 = var6;
         if(this.evaluateStatus(var1, var3, var5[1])) {
            if(var3.length == 6) {
               int var7 = this.getUWORDValueFromBytes(var3, 4);
               var2 = var7;
               if(var7 != var4) {
                  Log.e(this.logTag, var1 + ": only " + var7 + " bytes were written " + "(expected " + var4 + ")");
                  throw new IOException("Unable to write file on robot");
               }
            } else {
               Log.w(this.logTag, var1 + ": unexpected return package length " + var3.length + " (expected 6)");
               var2 = var6;
            }
         }

         return var2;
      }
   }

   @SimpleFunction(
      description = "Delete a file on the robot."
   )
   public void DeleteFile(String var1) {
      if(this.checkBluetooth("DeleteFile")) {
         if(var1.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "DeleteFile", 406, new Object[0]);
         } else {
            byte[] var2 = new byte[22];
            var2[0] = 1;
            var2[1] = -123;
            this.copyStringValueToBytes(var1, var2, 2, 19);
            this.evaluateStatus("DeleteFile", this.sendCommandAndReceiveReturnPackage("DeleteFile", var2), var2[1]);
         }
      }
   }

   @SimpleFunction(
      description = "Download a file to the robot."
   )
   public void DownloadFile(String param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   @SimpleFunction(
      description = "Get the battery level for the robot. Returns the voltage in millivolts."
   )
   public int GetBatteryLevel() {
      if(this.checkBluetooth("GetBatteryLevel")) {
         byte[] var1 = new byte[]{(byte)0, (byte)11};
         byte[] var2 = this.sendCommandAndReceiveReturnPackage("GetBatteryLevel", var1);
         if(this.evaluateStatus("GetBatteryLevel", var2, var1[1])) {
            if(var2.length == 5) {
               return this.getUWORDValueFromBytes(var2, 3);
            }

            Log.w(this.logTag, "GetBatteryLevel: unexpected return package length " + var2.length + " (expected 5)");
            return 0;
         }
      }

      return 0;
   }

   @SimpleFunction(
      description = "Get the brick name of the robot."
   )
   public String GetBrickName() {
      if(!this.checkBluetooth("GetBrickName")) {
         return "";
      } else {
         byte[] var1 = new byte[]{(byte)1, (byte)-101};
         byte[] var2 = this.sendCommandAndReceiveReturnPackage("GetBrickName", var1);
         return this.evaluateStatus("GetBrickName", var2, var1[1])?this.getStringValueFromBytes(var2, 3):"";
      }
   }

   @SimpleFunction(
      description = "Get the name of currently running program on the robot."
   )
   public String GetCurrentProgramName() {
      if(!this.checkBluetooth("GetCurrentProgramName")) {
         return "";
      } else {
         byte[] var1 = new byte[]{(byte)0, (byte)17};
         byte[] var2 = this.sendCommandAndReceiveReturnPackage("GetCurrentProgramName", var1);
         int var3 = this.getStatus("GetCurrentProgramName", var2, var1[1]);
         if(var3 == 0) {
            return this.getStringValueFromBytes(var2, 3);
         } else if(var3 == 236) {
            return "";
         } else {
            this.evaluateStatus("GetCurrentProgramName", var2, var1[1]);
            return "";
         }
      }
   }

   @SimpleFunction(
      description = "Get the firmware and protocol version numbers for the robot as a list where the first element is the firmware version number and the second element is the protocol version number."
   )
   public List GetFirmwareVersion() {
      if(!this.checkBluetooth("GetFirmwareVersion")) {
         return new ArrayList();
      } else {
         byte[] var2 = new byte[]{(byte)1, (byte)-120};
         byte[] var1 = this.sendCommandAndReceiveReturnPackage("GetFirmwareVersion", var2);
         if(this.evaluateStatus("GetFirmwareVersion", var1, var2[1])) {
            ArrayList var3 = new ArrayList();
            var3.add(var1[6] + "." + var1[5]);
            var3.add(var1[4] + "." + var1[3]);
            return var3;
         } else {
            return new ArrayList();
         }
      }
   }

   @SimpleFunction(
      description = "Reads the values of an input sensor on the robot. Assumes sensor type has been configured via SetInputMode."
   )
   public List GetInputValues(String var1) {
      if(!this.checkBluetooth("GetInputValues")) {
         return new ArrayList();
      } else {
         int var3;
         try {
            var3 = this.convertSensorPortLetterToNumber(var1);
         } catch (IllegalArgumentException var4) {
            this.form.dispatchErrorOccurredEvent(this, "GetInputValues", 408, new Object[]{var1});
            return new ArrayList();
         }

         byte[] var5 = this.getInputValues("GetInputValues", var3);
         if(var5 != null) {
            ArrayList var2 = new ArrayList();
            var2.add(Boolean.valueOf(this.getBooleanValueFromBytes(var5, 4)));
            var2.add(Boolean.valueOf(this.getBooleanValueFromBytes(var5, 5)));
            var2.add(Integer.valueOf(this.getUBYTEValueFromBytes(var5, 6)));
            var2.add(Integer.valueOf(this.getUBYTEValueFromBytes(var5, 7)));
            var2.add(Integer.valueOf(this.getUWORDValueFromBytes(var5, 8)));
            var2.add(Integer.valueOf(this.getUWORDValueFromBytes(var5, 10)));
            var2.add(Integer.valueOf(this.getSWORDValueFromBytes(var5, 12)));
            var2.add(Integer.valueOf(this.getSWORDValueFromBytes(var5, 14)));
            return var2;
         } else {
            return new ArrayList();
         }
      }
   }

   @SimpleFunction(
      description = "Reads the output state of a motor on the robot."
   )
   public List GetOutputState(String var1) {
      if(!this.checkBluetooth("GetOutputState")) {
         return new ArrayList();
      } else {
         int var3;
         try {
            var3 = this.convertMotorPortLetterToNumber(var1);
         } catch (IllegalArgumentException var4) {
            this.form.dispatchErrorOccurredEvent(this, "GetOutputState", 407, new Object[]{var1});
            return new ArrayList();
         }

         byte[] var5 = this.getOutputState("GetOutputState", var3);
         if(var5 != null) {
            ArrayList var2 = new ArrayList();
            var2.add(Integer.valueOf(this.getSBYTEValueFromBytes(var5, 4)));
            var2.add(Integer.valueOf(this.getUBYTEValueFromBytes(var5, 5)));
            var2.add(Integer.valueOf(this.getUBYTEValueFromBytes(var5, 6)));
            var2.add(Integer.valueOf(this.getSBYTEValueFromBytes(var5, 7)));
            var2.add(Integer.valueOf(this.getUBYTEValueFromBytes(var5, 8)));
            var2.add(Long.valueOf(this.getULONGValueFromBytes(var5, 9)));
            var2.add(Integer.valueOf(this.getSLONGValueFromBytes(var5, 13)));
            var2.add(Integer.valueOf(this.getSLONGValueFromBytes(var5, 17)));
            var2.add(Integer.valueOf(this.getSLONGValueFromBytes(var5, 21)));
            return var2;
         } else {
            return new ArrayList();
         }
      }
   }

   @SimpleFunction(
      description = "Keep Alive. Returns the current sleep time limit in milliseconds."
   )
   public long KeepAlive() {
      if(this.checkBluetooth("KeepAlive")) {
         byte[] var1 = new byte[]{(byte)0, (byte)13};
         byte[] var2 = this.sendCommandAndReceiveReturnPackage("KeepAlive", var1);
         if(this.evaluateStatus("KeepAlive", var2, var1[1])) {
            if(var2.length == 7) {
               return this.getULONGValueFromBytes(var2, 3);
            }

            Log.w(this.logTag, "KeepAlive: unexpected return package length " + var2.length + " (expected 7)");
            return 0L;
         }
      }

      return 0L;
   }

   @SimpleFunction(
      description = "Returns a list containing the names of matching files found on the robot."
   )
   public List ListFiles(String var1) {
      ArrayList var5;
      if(!this.checkBluetooth("ListFiles")) {
         var5 = new ArrayList();
      } else {
         ArrayList var3 = new ArrayList();
         String var2 = var1;
         if(var1.length() == 0) {
            var2 = "*.*";
         }

         byte[] var6 = new byte[22];
         var6[0] = 1;
         var6[1] = -122;
         this.copyStringValueToBytes(var2, var6, 2, 19);
         byte[] var7 = this.sendCommandAndReceiveReturnPackage("ListFiles", var6);
         int var4 = this.getStatus("ListFiles", var7, var6[1]);

         while(true) {
            var5 = var3;
            if(var4 != 0) {
               break;
            }

            var4 = this.getUBYTEValueFromBytes(var7, 3);
            var3.add(this.getStringValueFromBytes(var7, 4));
            var6 = new byte[]{(byte)1, (byte)-121, (byte)0};
            this.copyUBYTEValueToBytes(var4, var6, 2);
            var7 = this.sendCommandAndReceiveReturnPackage("ListFiles", var6);
            var4 = this.getStatus("ListFiles", var7, var6[1]);
         }
      }

      return var5;
   }

   @SimpleFunction(
      description = "Returns the count of available bytes to read."
   )
   public int LsGetStatus(String var1) {
      if(!this.checkBluetooth("LsGetStatus")) {
         return 0;
      } else {
         int var3;
         try {
            var3 = this.convertSensorPortLetterToNumber(var1);
         } catch (IllegalArgumentException var4) {
            this.form.dispatchErrorOccurredEvent(this, "LsGetStatus", 408, new Object[]{var1});
            return 0;
         }

         return this.lsGetStatus("LsGetStatus", var3);
      }
   }

   @SimpleFunction(
      description = "Reads unsigned low speed data from an input sensor on the robot. Assumes sensor type has been configured via SetInputMode."
   )
   public List LsRead(String var1) {
      ArrayList var7;
      if(!this.checkBluetooth("LsRead")) {
         var7 = new ArrayList();
      } else {
         int var4;
         try {
            var4 = this.convertSensorPortLetterToNumber(var1);
         } catch (IllegalArgumentException var6) {
            this.form.dispatchErrorOccurredEvent(this, "LsRead", 408, new Object[]{var1});
            return new ArrayList();
         }

         byte[] var3 = this.lsRead("LsRead", var4);
         if(var3 == null) {
            return new ArrayList();
         }

         ArrayList var2 = new ArrayList();
         int var5 = this.getUBYTEValueFromBytes(var3, 3);
         var4 = 0;

         while(true) {
            var7 = var2;
            if(var4 >= var5) {
               break;
            }

            var2.add(Integer.valueOf(var3[var4 + 4] & 255));
            ++var4;
         }
      }

      return var7;
   }

   @SimpleFunction(
      description = "Writes low speed data to an input sensor on the robot. Assumes sensor type has been configured via SetInputMode."
   )
   public void LsWrite(String var1, YailList var2, int var3) {
      if(this.checkBluetooth("LsWrite")) {
         int var6;
         try {
            var6 = this.convertSensorPortLetterToNumber(var1);
         } catch (IllegalArgumentException var9) {
            this.form.dispatchErrorOccurredEvent(this, "LsWrite", 408, new Object[]{var1});
            return;
         }

         if(var2.size() > 16) {
            this.form.dispatchErrorOccurredEvent(this, "LsWrite", 411, new Object[0]);
         } else {
            Object[] var10 = var2.toArray();
            byte[] var11 = new byte[var10.length];

            for(int var5 = 0; var5 < var10.length; ++var5) {
               String var4 = var10[var5].toString();

               int var7;
               try {
                  var7 = Integer.decode(var4).intValue();
               } catch (NumberFormatException var8) {
                  this.form.dispatchErrorOccurredEvent(this, "LsWrite", 412, new Object[]{Integer.valueOf(var5 + 1)});
                  return;
               }

               var11[var5] = (byte)(var7 & 255);
               var7 >>= 8;
               if(var7 != 0 && var7 != -1) {
                  this.form.dispatchErrorOccurredEvent(this, "LsWrite", 413, new Object[]{Integer.valueOf(var5 + 1)});
                  return;
               }
            }

            this.lsWrite("LsWrite", var6, var11, var3);
         }
      }
   }

   @SimpleFunction(
      description = "Read a message from a mailbox (1-10) on the robot."
   )
   public String MessageRead(int var1) {
      if(!this.checkBluetooth("MessageRead")) {
         return "";
      } else if(var1 >= 1 && var1 <= 10) {
         --var1;
         byte[] var2 = new byte[]{(byte)0, (byte)19, (byte)0, (byte)0, (byte)0};
         this.copyUBYTEValueToBytes(0, var2, 2);
         this.copyUBYTEValueToBytes(var1, var2, 3);
         this.copyBooleanValueToBytes(true, var2, 4);
         byte[] var3 = this.sendCommandAndReceiveReturnPackage("MessageRead", var2);
         if(this.evaluateStatus("MessageRead", var3, var2[1])) {
            if(var3.length == 64) {
               int var4 = this.getUBYTEValueFromBytes(var3, 3);
               if(var4 != var1) {
                  Log.w(this.logTag, "MessageRead: unexpected return mailbox: " + var4 + " (expected " + var1 + ")");
               }

               return this.getStringValueFromBytes(var3, 5, this.getUBYTEValueFromBytes(var3, 4) - 1);
            }

            Log.w(this.logTag, "MessageRead: unexpected return package length " + var3.length + " (expected 64)");
         }

         return "";
      } else {
         this.form.dispatchErrorOccurredEvent(this, "MessageRead", 409, new Object[]{Integer.valueOf(var1)});
         return "";
      }
   }

   @SimpleFunction(
      description = "Write a message to a mailbox (1-10) on the robot."
   )
   public void MessageWrite(int var1, String var2) {
      if(this.checkBluetooth("MessageWrite")) {
         if(var1 >= 1 && var1 <= 10) {
            int var4 = var2.length();
            if(var4 > 58) {
               this.form.dispatchErrorOccurredEvent(this, "MessageWrite", 410, new Object[0]);
            } else {
               byte[] var3 = new byte[var4 + 4 + 1];
               var3[0] = -128;
               var3[1] = 9;
               this.copyUBYTEValueToBytes(var1 - 1, var3, 2);
               this.copyUBYTEValueToBytes(var4 + 1, var3, 3);
               this.copyStringValueToBytes(var2, var3, 4, var4);
               this.sendCommand("MessageWrite", var3);
            }
         } else {
            this.form.dispatchErrorOccurredEvent(this, "MessageWrite", 409, new Object[]{Integer.valueOf(var1)});
         }
      }
   }

   @SimpleFunction(
      description = "Play a sound file on the robot."
   )
   public void PlaySoundFile(String var1) {
      if(this.checkBluetooth("PlaySoundFile")) {
         if(var1.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "PlaySoundFile", 406, new Object[0]);
         } else {
            String var2 = var1;
            if(var1.indexOf(".") == -1) {
               var2 = var1 + ".rso";
            }

            byte[] var3 = new byte[23];
            var3[0] = -128;
            var3[1] = 2;
            this.copyBooleanValueToBytes(false, var3, 2);
            this.copyStringValueToBytes(var2, var3, 3, 19);
            this.sendCommand("PlaySoundFile", var3);
         }
      }
   }

   @SimpleFunction(
      description = "Make the robot play a tone."
   )
   public void PlayTone(int var1, int var2) {
      if(this.checkBluetooth("PlayTone")) {
         int var4 = var1;
         if(var1 < 200) {
            Log.w(this.logTag, "frequencyHz " + var1 + " is invalid, using 200.");
            var4 = 200;
         }

         var1 = var4;
         if(var4 > 14000) {
            Log.w(this.logTag, "frequencyHz " + var4 + " is invalid, using 14000.");
            var1 = 14000;
         }

         byte[] var3 = new byte[]{(byte)-128, (byte)3, (byte)0, (byte)0, (byte)0, (byte)0};
         this.copyUWORDValueToBytes(var1, var3, 2);
         this.copyUWORDValueToBytes(var2, var3, 4);
         this.sendCommand("PlayTone", var3);
      }
   }

   @SimpleFunction(
      description = "Reset the scaled value of an input sensor on the robot."
   )
   public void ResetInputScaledValue(String var1) {
      if(this.checkBluetooth("ResetInputScaledValue")) {
         int var3;
         try {
            var3 = this.convertSensorPortLetterToNumber(var1);
         } catch (IllegalArgumentException var4) {
            this.form.dispatchErrorOccurredEvent(this, "ResetInputScaledValue", 408, new Object[]{var1});
            return;
         }

         this.resetInputScaledValue("ResetInputScaledValue", var3);
         byte[] var5 = new byte[]{(byte)-128, (byte)8, (byte)0};
         this.copyUBYTEValueToBytes(var3, var5, 2);
         this.sendCommand("ResetInputScaledValue", var5);
      }
   }

   @SimpleFunction(
      description = "Reset motor position."
   )
   public void ResetMotorPosition(String var1, boolean var2) {
      if(this.checkBluetooth("ResetMotorPosition")) {
         int var4;
         try {
            var4 = this.convertMotorPortLetterToNumber(var1);
         } catch (IllegalArgumentException var5) {
            this.form.dispatchErrorOccurredEvent(this, "ResetMotorPosition", 407, new Object[]{var1});
            return;
         }

         byte[] var6 = new byte[]{(byte)-128, (byte)10, (byte)0, (byte)0};
         this.copyUBYTEValueToBytes(var4, var6, 2);
         this.copyBooleanValueToBytes(var2, var6, 3);
         this.sendCommand("ResetMotorPosition", var6);
      }
   }

   @SimpleFunction(
      description = "Set the brick name of the robot."
   )
   public void SetBrickName(String var1) {
      if(this.checkBluetooth("SetBrickName")) {
         byte[] var2 = new byte[18];
         var2[0] = 1;
         var2[1] = -104;
         this.copyStringValueToBytes(var1, var2, 2, 15);
         this.evaluateStatus("SetBrickName", this.sendCommandAndReceiveReturnPackage("SetBrickName", var2), var2[1]);
      }
   }

   @SimpleFunction(
      description = "Configure an input sensor on the robot."
   )
   public void SetInputMode(String var1, int var2, int var3) {
      if(this.checkBluetooth("SetInputMode")) {
         int var5;
         try {
            var5 = this.convertSensorPortLetterToNumber(var1);
         } catch (IllegalArgumentException var6) {
            this.form.dispatchErrorOccurredEvent(this, "SetInputMode", 408, new Object[]{var1});
            return;
         }

         this.setInputMode("SetInputMode", var5, var2, var3);
      }
   }

   @SimpleFunction(
      description = "Sets the output state of a motor on the robot."
   )
   public void SetOutputState(String var1, int var2, int var3, int var4, int var5, int var6, long var7) {
      if(this.checkBluetooth("SetOutputState")) {
         int var10;
         try {
            var10 = this.convertMotorPortLetterToNumber(var1);
         } catch (IllegalArgumentException var11) {
            this.form.dispatchErrorOccurredEvent(this, "SetOutputState", 407, new Object[]{var1});
            return;
         }

         this.setOutputState("SetOutputState", var10, var2, var3, var4, this.sanitizeTurnRatio(var5), var6, var7);
      }
   }

   @SimpleFunction(
      description = "Start execution of a previously downloaded program on the robot."
   )
   public void StartProgram(String var1) {
      if(this.checkBluetooth("StartProgram")) {
         if(var1.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "StartProgram", 405, new Object[0]);
         } else {
            String var2 = var1;
            if(var1.indexOf(".") == -1) {
               var2 = var1 + ".rxe";
            }

            byte[] var3 = new byte[22];
            var3[0] = -128;
            var3[1] = 0;
            this.copyStringValueToBytes(var2, var3, 2, 19);
            this.sendCommand("StartProgram", var3);
         }
      }
   }

   @SimpleFunction(
      description = "Stop execution of the currently running program on the robot."
   )
   public void StopProgram() {
      if(this.checkBluetooth("StopProgram")) {
         this.sendCommand("StopProgram", new byte[]{(byte)-128, (byte)1});
      }
   }

   @SimpleFunction(
      description = "Stop sound playback."
   )
   public void StopSoundPlayback() {
      if(this.checkBluetooth("StopSoundPlayback")) {
         this.sendCommand("StopSoundPlayback", new byte[]{(byte)-128, (byte)12});
      }
   }
}
