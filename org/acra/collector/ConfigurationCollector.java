package org.acra.collector;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.util.SparseArray;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import org.acra.ACRA;

public final class ConfigurationCollector {

   private static final String FIELD_MCC = "mcc";
   private static final String FIELD_MNC = "mnc";
   private static final String FIELD_SCREENLAYOUT = "screenLayout";
   private static final String FIELD_UIMODE = "uiMode";
   private static final String PREFIX_HARDKEYBOARDHIDDEN = "HARDKEYBOARDHIDDEN_";
   private static final String PREFIX_KEYBOARD = "KEYBOARD_";
   private static final String PREFIX_KEYBOARDHIDDEN = "KEYBOARDHIDDEN_";
   private static final String PREFIX_NAVIGATION = "NAVIGATION_";
   private static final String PREFIX_NAVIGATIONHIDDEN = "NAVIGATIONHIDDEN_";
   private static final String PREFIX_ORIENTATION = "ORIENTATION_";
   private static final String PREFIX_SCREENLAYOUT = "SCREENLAYOUT_";
   private static final String PREFIX_TOUCHSCREEN = "TOUCHSCREEN_";
   private static final String PREFIX_UI_MODE = "UI_MODE_";
   private static final String SUFFIX_MASK = "_MASK";
   private static SparseArray mHardKeyboardHiddenValues = new SparseArray();
   private static SparseArray mKeyboardHiddenValues = new SparseArray();
   private static SparseArray mKeyboardValues = new SparseArray();
   private static SparseArray mNavigationHiddenValues = new SparseArray();
   private static SparseArray mNavigationValues = new SparseArray();
   private static SparseArray mOrientationValues = new SparseArray();
   private static SparseArray mScreenLayoutValues = new SparseArray();
   private static SparseArray mTouchScreenValues = new SparseArray();
   private static SparseArray mUiModeValues = new SparseArray();
   private static final HashMap mValueArrays = new HashMap();


   static {
      mValueArrays.put("HARDKEYBOARDHIDDEN_", mHardKeyboardHiddenValues);
      mValueArrays.put("KEYBOARD_", mKeyboardValues);
      mValueArrays.put("KEYBOARDHIDDEN_", mKeyboardHiddenValues);
      mValueArrays.put("NAVIGATION_", mNavigationValues);
      mValueArrays.put("NAVIGATIONHIDDEN_", mNavigationHiddenValues);
      mValueArrays.put("ORIENTATION_", mOrientationValues);
      mValueArrays.put("SCREENLAYOUT_", mScreenLayoutValues);
      mValueArrays.put("TOUCHSCREEN_", mTouchScreenValues);
      mValueArrays.put("UI_MODE_", mUiModeValues);
      Field[] var0 = Configuration.class.getFields();
      int var4 = var0.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Field var1 = var0[var3];
         if(Modifier.isStatic(var1.getModifiers()) && Modifier.isFinal(var1.getModifiers())) {
            String var2 = var1.getName();

            try {
               if(var2.startsWith("HARDKEYBOARDHIDDEN_")) {
                  mHardKeyboardHiddenValues.put(var1.getInt((Object)null), var2);
               } else if(var2.startsWith("KEYBOARD_")) {
                  mKeyboardValues.put(var1.getInt((Object)null), var2);
               } else if(var2.startsWith("KEYBOARDHIDDEN_")) {
                  mKeyboardHiddenValues.put(var1.getInt((Object)null), var2);
               } else if(var2.startsWith("NAVIGATION_")) {
                  mNavigationValues.put(var1.getInt((Object)null), var2);
               } else if(var2.startsWith("NAVIGATIONHIDDEN_")) {
                  mNavigationHiddenValues.put(var1.getInt((Object)null), var2);
               } else if(var2.startsWith("ORIENTATION_")) {
                  mOrientationValues.put(var1.getInt((Object)null), var2);
               } else if(var2.startsWith("SCREENLAYOUT_")) {
                  mScreenLayoutValues.put(var1.getInt((Object)null), var2);
               } else if(var2.startsWith("TOUCHSCREEN_")) {
                  mTouchScreenValues.put(var1.getInt((Object)null), var2);
               } else if(var2.startsWith("UI_MODE_")) {
                  mUiModeValues.put(var1.getInt((Object)null), var2);
               }
            } catch (IllegalArgumentException var5) {
               Log.w(ACRA.LOG_TAG, "Error while inspecting device configuration: ", var5);
            } catch (IllegalAccessException var6) {
               Log.w(ACRA.LOG_TAG, "Error while inspecting device configuration: ", var6);
            }
         }
      }

   }

   private static String activeFlags(SparseArray var0, int var1) {
      StringBuilder var2 = new StringBuilder();

      for(int var3 = 0; var3 < var0.size(); ++var3) {
         int var4 = var0.keyAt(var3);
         if(((String)var0.get(var4)).endsWith("_MASK")) {
            var4 &= var1;
            if(var4 > 0) {
               if(var2.length() > 0) {
                  var2.append('+');
               }

               var2.append((String)var0.get(var4));
            }
         }
      }

      return var2.toString();
   }

   public static String collectConfiguration(Context var0) {
      try {
         String var1 = toString(var0.getResources().getConfiguration());
         return var1;
      } catch (RuntimeException var2) {
         Log.w(ACRA.LOG_TAG, "Couldn\'t retrieve CrashConfiguration for : " + var0.getPackageName(), var2);
         return "Couldn\'t retrieve crash config";
      }
   }

   private static String getFieldValueName(Configuration var0, Field var1) throws IllegalAccessException {
      String var2 = var1.getName();
      if(!var2.equals("mcc") && !var2.equals("mnc")) {
         if(var2.equals("uiMode")) {
            return activeFlags((SparseArray)mValueArrays.get("UI_MODE_"), var1.getInt(var0));
         }

         if(var2.equals("screenLayout")) {
            return activeFlags((SparseArray)mValueArrays.get("SCREENLAYOUT_"), var1.getInt(var0));
         }

         SparseArray var4 = (SparseArray)mValueArrays.get(var2.toUpperCase() + '_');
         if(var4 == null) {
            return Integer.toString(var1.getInt(var0));
         }

         String var3 = (String)var4.get(var1.getInt(var0));
         var2 = var3;
         if(var3 == null) {
            return Integer.toString(var1.getInt(var0));
         }
      } else {
         var2 = Integer.toString(var1.getInt(var0));
      }

      return var2;
   }

   public static String toString(Configuration param0) {
      // $FF: Couldn't be decompiled
   }
}
