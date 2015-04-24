package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.BluetoothConnectionBase;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.LegoMindstormsNxtBase;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@DesignerComponent(
   category = ComponentCategory.LEGOMINDSTORMS,
   description = "A component that provides a high-level interface to a LEGO MINDSTORMS NXT robot, with functions that can move and turn the robot.",
   iconName = "images/legoMindstormsNxt.png",
   nonVisible = true,
   version = 1
)
@SimpleObject
public class NxtDrive extends LegoMindstormsNxtBase {

   private static final int MODE_BRAKE = 2;
   private static final int MODE_MOTORON = 1;
   private static final int MODE_REGULATED = 4;
   private static final int MOTOR_RUN_STATE_IDLE = 0;
   private static final int MOTOR_RUN_STATE_RAMPDOWN = 64;
   private static final int MOTOR_RUN_STATE_RAMPUP = 16;
   private static final int MOTOR_RUN_STATE_RUNNING = 32;
   private static final int REGULATION_MODE_IDLE = 0;
   private static final int REGULATION_MODE_MOTOR_SPEED = 1;
   private static final int REGULATION_MODE_MOTOR_SYNC = 2;
   private List driveMotorPorts;
   private String driveMotors;
   private boolean stopBeforeDisconnect;
   private double wheelDiameter;


   public NxtDrive(ComponentContainer var1) {
      super(var1, "NxtDrive");
      this.DriveMotors("CB");
      this.WheelDiameter(4.32F);
      this.StopBeforeDisconnect(true);
   }

   private void move(String var1, int var2, long var3) {
      if(this.checkBluetooth(var1)) {
         Iterator var5 = this.driveMotorPorts.iterator();

         while(var5.hasNext()) {
            this.setOutputState(var1, ((Integer)var5.next()).intValue(), var2, 1, 1, 0, 32, var3);
         }
      }

   }

   private void turnIndefinitely(String var1, int var2, int var3, int var4) {
      if(this.checkBluetooth(var1)) {
         this.setOutputState(var1, ((Integer)this.driveMotorPorts.get(var3)).intValue(), var2, 1, 1, 0, 32, 0L);
         this.setOutputState(var1, ((Integer)this.driveMotorPorts.get(var4)).intValue(), -var2, 1, 1, 0, 32, 0L);
      }
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The motor ports that are used for driving: the left wheel\'s motor port followed by the right wheel\'s motor port.",
      userVisible = false
   )
   public String DriveMotors() {
      return this.driveMotors;
   }

   @DesignerProperty(
      defaultValue = "CB",
      editorType = "string"
   )
   @SimpleProperty
   public void DriveMotors(String var1) {
      this.driveMotors = var1;
      this.driveMotorPorts = new ArrayList();

      for(int var4 = 0; var4 < var1.length(); ++var4) {
         char var2 = var1.charAt(var4);

         try {
            this.driveMotorPorts.add(Integer.valueOf(this.convertMotorPortLetterToNumber(var2)));
         } catch (IllegalArgumentException var5) {
            this.form.dispatchErrorOccurredEvent(this, "DriveMotors", 407, new Object[]{Character.valueOf(var2)});
         }
      }

   }

   @SimpleFunction(
      description = "Move the robot backward the given distance, with the specified percentage of maximum power, by powering both drive motors backward."
   )
   public void MoveBackward(int var1, double var2) {
      long var4 = (long)(360.0D * var2 / (this.wheelDiameter * 3.141592653589793D));
      this.move("MoveBackward", -var1, var4);
   }

   @SimpleFunction(
      description = "Move the robot backward indefinitely, with the specified percentage of maximum power, by powering both drive motors backward."
   )
   public void MoveBackwardIndefinitely(int var1) {
      this.move("MoveBackwardIndefinitely", -var1, 0L);
   }

   @SimpleFunction(
      description = "Move the robot forward the given distance, with the specified percentage of maximum power, by powering both drive motors forward."
   )
   public void MoveForward(int var1, double var2) {
      this.move("MoveForward", var1, (long)(360.0D * var2 / (this.wheelDiameter * 3.141592653589793D)));
   }

   @SimpleFunction(
      description = "Move the robot forward indefinitely, with the specified percentage of maximum power, by powering both drive motors forward."
   )
   public void MoveForwardIndefinitely(int var1) {
      this.move("MoveForwardIndefinitely", var1, 0L);
   }

   @SimpleFunction(
      description = "Stop the drive motors of the robot."
   )
   public void Stop() {
      if(this.checkBluetooth("Stop")) {
         Iterator var1 = this.driveMotorPorts.iterator();

         while(var1.hasNext()) {
            this.setOutputState("Stop", ((Integer)var1.next()).intValue(), 0, 2, 0, 0, 0, 0L);
         }
      }

   }

   @DesignerProperty(
      defaultValue = "True",
      editorType = "boolean"
   )
   @SimpleProperty
   public void StopBeforeDisconnect(boolean var1) {
      this.stopBeforeDisconnect = var1;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Whether to stop the drive motors before disconnecting."
   )
   public boolean StopBeforeDisconnect() {
      return this.stopBeforeDisconnect;
   }

   @SimpleFunction(
      description = "Turn the robot clockwise indefinitely, with the specified percentage of maximum power, by powering the left drive motor forward and the right drive motor backward."
   )
   public void TurnClockwiseIndefinitely(int var1) {
      int var2 = this.driveMotorPorts.size();
      if(var2 >= 2) {
         this.turnIndefinitely("TurnClockwiseIndefinitely", var1, 0, var2 - 1);
      }

   }

   @SimpleFunction(
      description = "Turn the robot counterclockwise indefinitely, with the specified percentage of maximum power, by powering the right drive motor forward and the left drive motor backward."
   )
   public void TurnCounterClockwiseIndefinitely(int var1) {
      int var2 = this.driveMotorPorts.size();
      if(var2 >= 2) {
         this.turnIndefinitely("TurnCounterClockwiseIndefinitely", var1, var2 - 1, 0);
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The diameter of the wheels used for driving.",
      userVisible = false
   )
   public float WheelDiameter() {
      return (float)this.wheelDiameter;
   }

   @DesignerProperty(
      defaultValue = "4.32",
      editorType = "float"
   )
   @SimpleProperty
   public void WheelDiameter(float var1) {
      this.wheelDiameter = (double)var1;
   }

   public void beforeDisconnect(BluetoothConnectionBase var1) {
      if(this.stopBeforeDisconnect) {
         Iterator var2 = this.driveMotorPorts.iterator();

         while(var2.hasNext()) {
            this.setOutputState("Disconnect", ((Integer)var2.next()).intValue(), 0, 2, 0, 0, 0, 0L);
         }
      }

   }
}
