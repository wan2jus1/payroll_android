package com.google.appinventor.components.runtime;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
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
import com.google.appinventor.components.runtime.Deleteable;
import com.google.appinventor.components.runtime.EventDispatcher;
import java.lang.reflect.Array;

@DesignerComponent(
   category = ComponentCategory.INTERNAL,
   description = "Component that can count steps.",
   iconName = "images/pedometer.png",
   nonVisible = true,
   version = 1
)
@SimpleObject
@UsesPermissions(
   permissionNames = "android.permission.ACCESS_FINE_LOCATION"
)
public class Pedometer extends AndroidNonvisibleComponent implements Component, LocationListener, SensorEventListener, Deleteable {

   private static final int DIMENSIONS = 3;
   private static final int INTERVAL_VARIATION = 250;
   private static final int MIN_SATELLITES = 4;
   private static final int NUM_INTERVALS = 2;
   private static final float PEAK_VALLEY_RANGE = 4.0F;
   private static final String PREFS_NAME = "PedometerPrefs";
   private static final float STRIDE_LENGTH = 0.73F;
   private static final String TAG = "Pedometer";
   private static final int WIN_SIZE = 20;
   private boolean calibrateSteps;
   private final Context context;
   private Location currentLocation;
   private float distWhenGPSLost;
   private long elapsedTimestamp;
   private boolean firstGpsReading;
   private boolean foundNonStep;
   private boolean[] foundValley;
   private boolean gpsAvailable;
   private float gpsDistance;
   private long gpsStepTime;
   private int intervalPos = 0;
   private int lastNumSteps = 0;
   private float[] lastValley = new float[3];
   private float[][] lastValues;
   private final LocationManager locationManager;
   private Location locationWhenGPSLost;
   private int numStepsRaw = 0;
   private int numStepsWithFilter = 0;
   private int[] peak = new int[3];
   private boolean pedometerPaused;
   private float[] prevDiff;
   private Location prevLocation;
   private long prevStopClockTime;
   private final SensorManager sensorManager;
   private boolean startPeaking;
   private long startTime;
   private boolean statusMoving;
   private long[] stepInterval;
   private long stepTimestamp;
   private int stopDetectionTimeout = 2000;
   private float strideLength;
   private float totalDistance;
   private boolean useGps;
   private int[] valley = new int[3];
   private int winPos = 0;


   public Pedometer(ComponentContainer var1) {
      super(var1.$form());
      this.lastValues = (float[][])Array.newInstance(Float.TYPE, new int[]{20, 3});
      this.prevDiff = new float[3];
      this.strideLength = 0.73F;
      this.totalDistance = 0.0F;
      this.distWhenGPSLost = 0.0F;
      this.gpsDistance = 0.0F;
      this.stepInterval = new long[2];
      this.stepTimestamp = 0L;
      this.elapsedTimestamp = 0L;
      this.startTime = 0L;
      this.prevStopClockTime = 0L;
      this.gpsStepTime = 0L;
      this.foundValley = new boolean[3];
      this.startPeaking = false;
      this.foundNonStep = true;
      this.gpsAvailable = false;
      this.calibrateSteps = true;
      this.pedometerPaused = true;
      this.useGps = true;
      this.statusMoving = false;
      this.firstGpsReading = true;
      this.context = var1.$context();
      this.winPos = 0;
      this.startPeaking = false;
      this.numStepsWithFilter = 0;
      this.numStepsRaw = 0;
      this.firstGpsReading = true;
      this.gpsDistance = 0.0F;

      for(int var2 = 0; var2 < 3; ++var2) {
         this.foundValley[var2] = true;
         this.lastValley[var2] = 0.0F;
      }

      this.sensorManager = (SensorManager)this.context.getSystemService("sensor");
      this.locationManager = (LocationManager)this.context.getSystemService("location");
      this.locationManager.requestLocationUpdates("gps", 0L, 0.0F, this);
      SharedPreferences var3 = this.context.getSharedPreferences("PedometerPrefs", 0);
      this.strideLength = var3.getFloat("Pedometer.stridelength", 0.73F);
      this.totalDistance = var3.getFloat("Pedometer.distance", 0.0F);
      this.numStepsRaw = var3.getInt("Pedometer.prevStepCount", 0);
      this.prevStopClockTime = var3.getLong("Pedometer.clockTime", 0L);
      this.numStepsWithFilter = this.numStepsRaw;
      this.startTime = System.currentTimeMillis();
      Log.d("Pedometer", "Pedometer Created");
   }

   private boolean areStepsEquallySpaced() {
      float var1 = 0.0F;
      int var5 = 0;
      long[] var3 = this.stepInterval;
      int var7 = var3.length;

      int var4;
      int var6;
      for(var4 = 0; var4 < var7; var5 = var6) {
         long var8 = var3[var4];
         float var2 = var1;
         var6 = var5;
         if(var8 > 0L) {
            var6 = var5 + 1;
            var2 = var1 + (float)var8;
         }

         ++var4;
         var1 = var2;
      }

      var1 /= (float)var5;
      var3 = this.stepInterval;
      var5 = var3.length;

      for(var4 = 0; var4 < var5; ++var4) {
         if(Math.abs((float)var3[var4] - var1) > 250.0F) {
            return false;
         }
      }

      return true;
   }

   private void getPeak() {
      int var3 = (this.winPos + 10) % 20;
      int var1 = 0;

      while(var1 < 3) {
         this.peak[var1] = var3;
         int var2 = 0;

         while(true) {
            if(var2 < 20) {
               if(var2 == var3 || this.lastValues[var2][var1] < this.lastValues[var3][var1]) {
                  ++var2;
                  continue;
               }

               this.peak[var1] = -1;
            }

            ++var1;
            break;
         }
      }

   }

   private void getValley() {
      int var3 = (this.winPos + 10) % 20;
      int var1 = 0;

      while(var1 < 3) {
         this.valley[var1] = var3;
         int var2 = 0;

         while(true) {
            if(var2 < 20) {
               if(var2 == var3 || this.lastValues[var2][var1] > this.lastValues[var3][var1]) {
                  ++var2;
                  continue;
               }

               this.valley[var1] = -1;
            }

            ++var1;
            break;
         }
      }

   }

   private void setGpsAvailable(boolean var1) {
      if(!this.gpsAvailable && var1) {
         this.gpsAvailable = true;
         this.GPSAvailable();
      } else if(this.gpsAvailable && !var1) {
         this.gpsAvailable = false;
         this.GPSLost();
         return;
      }

   }

   @DesignerProperty(
      defaultValue = "true",
      editorType = "boolean"
   )
   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public void CalibrateStrideLength(boolean var1) {
      if(!this.gpsAvailable && var1) {
         this.CalibrationFailed();
      } else {
         if(var1) {
            this.useGps = true;
         }

         this.calibrateSteps = var1;
      }
   }

   @SimpleProperty
   public boolean CalibrateStrideLength() {
      return this.calibrateSteps;
   }

   @SimpleEvent
   public void CalibrationFailed() {
      EventDispatcher.dispatchEvent(this, "CalibrationFailed", new Object[0]);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public float Distance() {
      return this.totalDistance;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public long ElapsedTime() {
      return this.pedometerPaused?this.prevStopClockTime:this.prevStopClockTime + (System.currentTimeMillis() - this.startTime);
   }

   @SimpleEvent
   public void GPSAvailable() {
      EventDispatcher.dispatchEvent(this, "GPSAvailable", new Object[0]);
   }

   @SimpleEvent
   public void GPSLost() {
      EventDispatcher.dispatchEvent(this, "GPSLost", new Object[0]);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public boolean Moving() {
      return this.statusMoving;
   }

   @SimpleFunction
   public void Pause() {
      if(!this.pedometerPaused) {
         this.pedometerPaused = true;
         this.statusMoving = false;
         this.sensorManager.unregisterListener(this);
         Log.d("Pedometer", "Unregistered listener on pause");
         this.prevStopClockTime += System.currentTimeMillis() - this.startTime;
      }

   }

   @SimpleFunction
   public void Reset() {
      this.numStepsWithFilter = 0;
      this.numStepsRaw = 0;
      this.totalDistance = 0.0F;
      this.calibrateSteps = false;
      this.prevStopClockTime = 0L;
      this.startTime = System.currentTimeMillis();
   }

   @SimpleFunction
   public void Resume() {
      this.Start();
   }

   @SimpleFunction(
      description = "Saves the pedometer state to the phone"
   )
   public void Save() {
      Editor var1 = this.context.getSharedPreferences("PedometerPrefs", 0).edit();
      var1.putFloat("Pedometer.stridelength", this.strideLength);
      var1.putFloat("Pedometer.distance", this.totalDistance);
      var1.putInt("Pedometer.prevStepCount", this.numStepsRaw);
      if(this.pedometerPaused) {
         var1.putLong("Pedometer.clockTime", this.prevStopClockTime);
      } else {
         var1.putLong("Pedometer.clockTime", this.prevStopClockTime + (System.currentTimeMillis() - this.startTime));
      }

      var1.putLong("Pedometer.closeTime", System.currentTimeMillis());
      var1.commit();
      Log.d("Pedometer", "Pedometer state saved.");
   }

   @SimpleEvent(
      description = "This event is run when a raw step is detected"
   )
   public void SimpleStep(int var1, float var2) {
      EventDispatcher.dispatchEvent(this, "SimpleStep", new Object[]{Integer.valueOf(var1), Float.valueOf(var2)});
   }

   @SimpleFunction
   public void Start() {
      if(this.pedometerPaused) {
         this.pedometerPaused = false;
         this.sensorManager.registerListener(this, (Sensor)this.sensorManager.getSensorList(1).get(0), 0);
         this.startTime = System.currentTimeMillis();
      }

   }

   @SimpleEvent
   public void StartedMoving() {
      EventDispatcher.dispatchEvent(this, "StartedMoving", new Object[0]);
   }

   @SimpleFunction
   public void Stop() {
      this.Pause();
      this.locationManager.removeUpdates(this);
      this.useGps = false;
      this.calibrateSteps = false;
      this.setGpsAvailable(false);
   }

   @SimpleProperty
   public int StopDetectionTimeout() {
      return this.stopDetectionTimeout;
   }

   @DesignerProperty(
      defaultValue = "2000",
      editorType = "non_negative_integer"
   )
   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public void StopDetectionTimeout(int var1) {
      this.stopDetectionTimeout = var1;
   }

   @SimpleEvent
   public void StoppedMoving() {
      EventDispatcher.dispatchEvent(this, "StoppedMoving", new Object[0]);
   }

   @SimpleProperty
   public float StrideLength() {
      return this.strideLength;
   }

   @DesignerProperty(
      defaultValue = "0.73",
      editorType = "non_negative_float"
   )
   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public void StrideLength(float var1) {
      this.CalibrateStrideLength(false);
      this.strideLength = var1;
   }

   @DesignerProperty(
      defaultValue = "true",
      editorType = "boolean"
   )
   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public void UseGPS(boolean var1) {
      if(!var1 && this.useGps) {
         this.locationManager.removeUpdates(this);
      } else if(var1 && !this.useGps) {
         this.locationManager.requestLocationUpdates("gps", 0L, 0.0F, this);
      }

      this.useGps = var1;
   }

   @SimpleProperty
   public boolean UseGPS() {
      return this.useGps;
   }

   @SimpleEvent(
      description = "This event is run when a walking step is detected"
   )
   public void WalkStep(int var1, float var2) {
      EventDispatcher.dispatchEvent(this, "WalkStep", new Object[]{Integer.valueOf(var1), Float.valueOf(var2)});
   }

   public void onAccuracyChanged(Sensor var1, int var2) {
      Log.d("Pedometer", "Accelerometer accuracy changed.");
   }

   public void onDelete() {
      this.sensorManager.unregisterListener(this);
      this.locationManager.removeUpdates(this);
   }

   public void onLocationChanged(Location var1) {
      if(this.statusMoving && !this.pedometerPaused && this.useGps) {
         float var2 = 0.0F;
         this.currentLocation = var1;
         if(this.currentLocation.getAccuracy() > 10.0F) {
            this.setGpsAvailable(false);
         } else {
            this.setGpsAvailable(true);
            float var3;
            if(this.prevLocation != null) {
               var3 = this.currentLocation.distanceTo(this.prevLocation);
               var2 = var3;
               if((double)var3 > 0.1D) {
                  var2 = var3;
                  if(var3 < 10.0F) {
                     this.totalDistance += var3;
                     this.prevLocation = this.currentLocation;
                     var2 = var3;
                  }
               }
            } else {
               if(this.locationWhenGPSLost != null) {
                  var3 = this.currentLocation.distanceTo(this.locationWhenGPSLost);
                  this.totalDistance = this.distWhenGPSLost + (this.totalDistance - this.distWhenGPSLost + var3) / 2.0F;
               }

               this.gpsStepTime = System.currentTimeMillis();
               this.prevLocation = this.currentLocation;
            }

            if(this.calibrateSteps) {
               if(!this.firstGpsReading) {
                  this.gpsDistance += var2;
                  int var4 = this.numStepsRaw;
                  int var5 = this.lastNumSteps;
                  this.strideLength = this.gpsDistance / (float)(var4 - var5);
               } else {
                  this.firstGpsReading = false;
                  this.lastNumSteps = this.numStepsRaw;
               }
            } else {
               this.firstGpsReading = true;
               this.gpsDistance = 0.0F;
            }
         }
      }
   }

   public void onProviderDisabled(String var1) {
      this.distWhenGPSLost = this.totalDistance;
      this.locationWhenGPSLost = this.currentLocation;
      this.firstGpsReading = true;
      this.prevLocation = null;
      this.setGpsAvailable(false);
   }

   public void onProviderEnabled(String var1) {
      this.setGpsAvailable(true);
   }

   public void onSensorChanged(SensorEvent var1) {
      if(var1.sensor.getType() == 1) {
         float[] var6 = var1.values;
         if(this.startPeaking) {
            this.getPeak();
            this.getValley();
         }

         byte var2;
         if(this.prevDiff[0] > this.prevDiff[1]) {
            var2 = 0;
         } else {
            var2 = 1;
         }

         byte var3 = var2;
         if(this.prevDiff[2] > this.prevDiff[var2]) {
            var3 = 2;
         }

         int var7;
         for(var7 = 0; var7 < 3; ++var7) {
            if(this.startPeaking && this.peak[var7] >= 0) {
               if(this.foundValley[var7] && this.lastValues[this.peak[var7]][var7] - this.lastValley[var7] > 4.0F) {
                  if(var3 == var7) {
                     long var4 = System.currentTimeMillis();
                     this.stepInterval[this.intervalPos] = var4 - this.stepTimestamp;
                     this.intervalPos = (this.intervalPos + 1) % 2;
                     this.stepTimestamp = var4;
                     if(this.areStepsEquallySpaced()) {
                        if(this.foundNonStep) {
                           this.numStepsWithFilter += 2;
                           if(!this.gpsAvailable) {
                              this.totalDistance += this.strideLength * 2.0F;
                           }

                           this.foundNonStep = false;
                        }

                        ++this.numStepsWithFilter;
                        this.WalkStep(this.numStepsWithFilter, this.totalDistance);
                        if(!this.gpsAvailable) {
                           this.totalDistance += this.strideLength;
                        }
                     } else {
                        this.foundNonStep = true;
                     }

                     ++this.numStepsRaw;
                     this.SimpleStep(this.numStepsRaw, this.totalDistance);
                     if(!this.statusMoving) {
                        this.statusMoving = true;
                        this.StartedMoving();
                     }
                  }

                  this.foundValley[var7] = false;
                  this.prevDiff[var7] = this.lastValues[this.peak[var7]][var7] - this.lastValley[var7];
               } else {
                  this.prevDiff[var7] = 0.0F;
               }
            }

            if(this.startPeaking && this.valley[var7] >= 0) {
               this.foundValley[var7] = true;
               this.lastValley[var7] = this.lastValues[this.valley[var7]][var7];
            }

            this.lastValues[this.winPos][var7] = var6[var7];
         }

         this.elapsedTimestamp = System.currentTimeMillis();
         if(this.elapsedTimestamp - this.stepTimestamp > (long)this.stopDetectionTimeout) {
            if(this.statusMoving) {
               this.statusMoving = false;
               this.StoppedMoving();
            }

            this.stepTimestamp = this.elapsedTimestamp;
         }

         if(this.winPos - 1 < 0) {
            var7 = 19;
         } else {
            var7 = this.winPos - 1;
         }

         for(int var8 = 0; var8 < 3; ++var8) {
            if(this.lastValues[var7][var8] == this.lastValues[this.winPos][var8]) {
               var6 = this.lastValues[this.winPos];
               var6[var8] = (float)((double)var6[var8] + 0.001D);
            }
         }

         if(this.winPos == 19 && !this.startPeaking) {
            this.startPeaking = true;
         }

         this.winPos = (this.winPos + 1) % 20;
      }
   }

   public void onStatusChanged(String var1, int var2, Bundle var3) {
   }
}
