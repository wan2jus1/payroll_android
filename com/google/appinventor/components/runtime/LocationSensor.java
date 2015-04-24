package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.appinventor.components.runtime.OnResumeListener;
import com.google.appinventor.components.runtime.OnStopListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@DesignerComponent(
   category = ComponentCategory.SENSORS,
   description = "Non-visible component providing location information, including longitude, latitude, altitude (if supported by the device), and address.  This can also perform \"geocoding\", converting a given address (not necessarily the current one) to a latitude (with the <code>LatitudeFromAddress</code> method) and a longitude (with the <code>LongitudeFromAddress</code> method).</p>\n<p>In order to function, the component must have its <code>Enabled</code> property set to True, and the device must have location sensing enabled through wireless networks or GPS satellites (if outdoors).</p>\nLocation information might not be immediately available when an app starts.  You\'ll have to wait a short time for a location provider to be found and used, or wait for the OnLocationChanged event",
   iconName = "images/locationSensor.png",
   nonVisible = true,
   version = 2
)
@SimpleObject
@UsesPermissions(
   permissionNames = "android.permission.ACCESS_FINE_LOCATION,android.permission.ACCESS_COARSE_LOCATION,android.permission.ACCESS_MOCK_LOCATION,android.permission.ACCESS_LOCATION_EXTRA_COMMANDS"
)
public class LocationSensor extends AndroidNonvisibleComponent implements Component, OnStopListener, OnResumeListener, Deleteable {

   public static final int UNKNOWN_VALUE = 0;
   private List allProviders;
   private double altitude = 0.0D;
   private int distanceInterval;
   private boolean enabled = true;
   private Geocoder geocoder;
   private final Handler handler = new Handler();
   private boolean hasAltitude = false;
   private boolean hasLocationData = false;
   private Location lastLocation;
   private double latitude = 0.0D;
   private boolean listening = false;
   private final Criteria locationCriteria;
   private final LocationManager locationManager;
   private LocationProvider locationProvider;
   private double longitude = 0.0D;
   private LocationSensor.MyLocationListener myLocationListener;
   private boolean providerLocked = false;
   private String providerName;
   private int timeInterval;


   public LocationSensor(ComponentContainer var1) {
      super(var1.$form());
      this.form.registerForOnResume(this);
      this.form.registerForOnStop(this);
      this.timeInterval = '\uea60';
      this.distanceInterval = 5;
      Activity var2 = var1.$context();
      this.geocoder = new Geocoder(var2);
      this.locationManager = (LocationManager)var2.getSystemService("location");
      this.locationCriteria = new Criteria();
      this.myLocationListener = new LocationSensor.MyLocationListener(null);
      this.allProviders = new ArrayList();
      this.Enabled(this.enabled);
   }

   private boolean empty(String var1) {
      return var1 == null || var1.length() == 0;
   }

   private boolean startProvider(String var1) {
      this.providerName = var1;
      LocationProvider var2 = this.locationManager.getProvider(var1);
      if(var2 == null) {
         Log.d("LocationSensor", "getProvider(" + var1 + ") returned null");
         return false;
      } else {
         this.stopListening();
         this.locationProvider = var2;
         this.locationManager.requestLocationUpdates(var1, (long)this.timeInterval, (float)this.distanceInterval, this.myLocationListener);
         this.listening = true;
         return true;
      }
   }

   private void stopListening() {
      if(this.listening) {
         this.locationManager.removeUpdates(this.myLocationListener);
         this.locationProvider = null;
         this.listening = false;
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public double Accuracy() {
      return this.lastLocation != null && this.lastLocation.hasAccuracy()?(double)this.lastLocation.getAccuracy():(this.locationProvider != null?(double)this.locationProvider.getAccuracy():0.0D);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public double Altitude() {
      return this.altitude;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public List AvailableProviders() {
      return this.allProviders;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String CurrentAddress() {
      // $FF: Couldn't be decompiled
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Determines the minimum distance interval, in meters, that the sensor will try to use for sending out location updates. For example, if this is set to 5, then the sensor will fire a LocationChanged event only after 5 meters have been traversed. However, the sensor does not guarantee that an update will be received at exactly the distance interval. It may take more than 5 meters to fire an event, for instance."
   )
   public int DistanceInterval() {
      return this.distanceInterval;
   }

   @DesignerProperty(
      defaultValue = "5",
      editorType = "sensor_dist_interval"
   )
   @SimpleProperty
   public void DistanceInterval(int var1) {
      if(var1 >= 0 && var1 <= 1000) {
         this.distanceInterval = var1;
         if(this.enabled) {
            this.RefreshProvider();
            return;
         }
      }

   }

   @DesignerProperty(
      defaultValue = "True",
      editorType = "boolean"
   )
   @SimpleProperty
   public void Enabled(boolean var1) {
      this.enabled = var1;
      if(!var1) {
         this.stopListening();
      } else {
         this.RefreshProvider();
      }
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public boolean Enabled() {
      return this.enabled;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public boolean HasAccuracy() {
      return this.Accuracy() != 0.0D && this.enabled;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public boolean HasAltitude() {
      return this.hasAltitude && this.enabled;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public boolean HasLongitudeLatitude() {
      return this.hasLocationData && this.enabled;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public double Latitude() {
      return this.latitude;
   }

   @SimpleFunction(
      description = "Derives latitude of given address"
   )
   public double LatitudeFromAddress(String param1) {
      // $FF: Couldn't be decompiled
   }

   @SimpleEvent
   public void LocationChanged(double var1, double var3, double var5) {
      if(this.enabled) {
         EventDispatcher.dispatchEvent(this, "LocationChanged", new Object[]{Double.valueOf(var1), Double.valueOf(var3), Double.valueOf(var5)});
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public double Longitude() {
      return this.longitude;
   }

   @SimpleFunction(
      description = "Derives longitude of given address"
   )
   public double LongitudeFromAddress(String param1) {
      // $FF: Couldn't be decompiled
   }

   @SimpleProperty
   public void ProviderLocked(boolean var1) {
      this.providerLocked = var1;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public boolean ProviderLocked() {
      return this.providerLocked;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String ProviderName() {
      return this.providerName == null?"NO PROVIDER":this.providerName;
   }

   @SimpleProperty
   public void ProviderName(String var1) {
      this.providerName = var1;
      if(this.empty(var1) || !this.startProvider(var1)) {
         this.RefreshProvider();
      }
   }

   public void RefreshProvider() {
      this.stopListening();
      if(this.providerLocked && !this.empty(this.providerName)) {
         this.listening = this.startProvider(this.providerName);
      } else {
         this.allProviders = this.locationManager.getProviders(true);
         String var1 = this.locationManager.getBestProvider(this.locationCriteria, true);
         if(var1 != null && !var1.equals(this.allProviders.get(0))) {
            this.allProviders.add(0, var1);
         }

         Iterator var3 = this.allProviders.iterator();

         while(var3.hasNext()) {
            String var2 = (String)var3.next();
            this.listening = this.startProvider(var2);
            if(this.listening) {
               if(!this.providerLocked) {
                  this.providerName = var2;
                  return;
               }
               break;
            }
         }
      }

   }

   @SimpleEvent
   public void StatusChanged(String var1, String var2) {
      if(this.enabled) {
         EventDispatcher.dispatchEvent(this, "StatusChanged", new Object[]{var1, var2});
      }

   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Determines the minimum time interval, in milliseconds, that the sensor will try to use for sending out location updates. However, location updates will only be received when the location of the phone actually changes, and use of the specified time interval is not guaranteed. For example, if 1000 is used as the time interval, location updates will never be fired sooner than 1000ms, but they may be fired anytime after."
   )
   public int TimeInterval() {
      return this.timeInterval;
   }

   @DesignerProperty(
      defaultValue = "60000",
      editorType = "sensor_time_interval"
   )
   @SimpleProperty
   public void TimeInterval(int var1) {
      if(var1 >= 0 && var1 <= 1000000) {
         this.timeInterval = var1;
         if(this.enabled) {
            this.RefreshProvider();
            return;
         }
      }

   }

   public void onDelete() {
      this.stopListening();
   }

   public void onResume() {
      if(this.enabled) {
         this.RefreshProvider();
      }

   }

   public void onStop() {
      this.stopListening();
   }

   private class MyLocationListener implements LocationListener {

      private MyLocationListener() {
      }

      // $FF: synthetic method
      MyLocationListener(Object var2) {
         this();
      }

      public void onLocationChanged(Location var1) {
         LocationSensor.this.lastLocation = var1;
         LocationSensor.this.longitude = var1.getLongitude();
         LocationSensor.this.latitude = var1.getLatitude();
         if(var1.hasAltitude()) {
            LocationSensor.this.hasAltitude = true;
            LocationSensor.this.altitude = var1.getAltitude();
         }

         LocationSensor.this.hasLocationData = true;
         LocationSensor.this.LocationChanged(LocationSensor.this.latitude, LocationSensor.this.longitude, LocationSensor.this.altitude);
      }

      public void onProviderDisabled(String var1) {
         LocationSensor.this.StatusChanged(var1, "Disabled");
         LocationSensor.this.stopListening();
         if(LocationSensor.this.enabled) {
            LocationSensor.this.RefreshProvider();
         }

      }

      public void onProviderEnabled(String var1) {
         LocationSensor.this.StatusChanged(var1, "Enabled");
         LocationSensor.this.RefreshProvider();
      }

      public void onStatusChanged(String var1, int var2, Bundle var3) {
         switch(var2) {
         case 0:
            LocationSensor.this.StatusChanged(var1, "OUT_OF_SERVICE");
            if(var1.equals(LocationSensor.this.providerName)) {
               LocationSensor.this.stopListening();
               LocationSensor.this.RefreshProvider();
               return;
            }
            break;
         case 1:
            LocationSensor.this.StatusChanged(var1, "TEMPORARILY_UNAVAILABLE");
            return;
         case 2:
            LocationSensor.this.StatusChanged(var1, "AVAILABLE");
            if(!var1.equals(LocationSensor.this.providerName) && !LocationSensor.this.allProviders.contains(var1)) {
               LocationSensor.this.RefreshProvider();
               return;
            }
         }

      }
   }
}
