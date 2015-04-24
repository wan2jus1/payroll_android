package com.google.appinventor.components.runtime.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

public class BluetoothReflection {

   private static final int BOND_BONDED = 12;


   public static Object accept(Object var0) throws IOException {
      return invokeMethodThrowsIOException(getMethod(var0.getClass(), "accept"), var0, new Object[0]);
   }

   public static boolean checkBluetoothAddress(Object var0, String var1) {
      return ((Boolean)invokeMethod(getMethod(var0.getClass(), "checkBluetoothAddress", new Class[]{String.class}), var0, new Object[]{var1})).booleanValue();
   }

   public static void closeBluetoothServerSocket(Object var0) throws IOException {
      invokeMethodThrowsIOException(getMethod(var0.getClass(), "close"), var0, new Object[0]);
   }

   public static void closeBluetoothSocket(Object var0) throws IOException {
      invokeMethodThrowsIOException(getMethod(var0.getClass(), "close"), var0, new Object[0]);
   }

   public static void connectToBluetoothSocket(Object var0) throws IOException {
      invokeMethodThrowsIOException(getMethod(var0.getClass(), "connect"), var0, new Object[0]);
   }

   public static Object createInsecureRfcommSocketToServiceRecord(Object var0, UUID var1) throws IOException {
      return invokeMethodThrowsIOException(getMethod(var0.getClass(), "createInsecureRfcommSocketToServiceRecord", new Class[]{UUID.class}), var0, new Object[]{var1});
   }

   public static Object createRfcommSocketToServiceRecord(Object var0, UUID var1) throws IOException {
      return invokeMethodThrowsIOException(getMethod(var0.getClass(), "createRfcommSocketToServiceRecord", new Class[]{UUID.class}), var0, new Object[]{var1});
   }

   public static Object getBluetoothAdapter() {
      Class var0;
      try {
         var0 = Class.forName("android.bluetooth.BluetoothAdapter");
      } catch (ClassNotFoundException var1) {
         return null;
      }

      return invokeStaticMethod(getMethod(var0, "getDefaultAdapter"));
   }

   public static Object getBluetoothClass(Object var0) {
      return invokeMethod(getMethod(var0.getClass(), "getBluetoothClass"), var0, new Object[0]);
   }

   public static String getBluetoothDeviceAddress(Object var0) {
      return (String)invokeMethod(getMethod(var0.getClass(), "getAddress"), var0, new Object[0]);
   }

   public static String getBluetoothDeviceName(Object var0) {
      return (String)invokeMethod(getMethod(var0.getClass(), "getName"), var0, new Object[0]);
   }

   public static Set getBondedDevices(Object var0) {
      return (Set)invokeMethod(getMethod(var0.getClass(), "getBondedDevices"), var0, new Object[0]);
   }

   public static int getDeviceClass(Object var0) {
      return ((Integer)invokeMethod(getMethod(var0.getClass(), "getDeviceClass"), var0, new Object[0])).intValue();
   }

   public static InputStream getInputStream(Object var0) throws IOException {
      return (InputStream)invokeMethodThrowsIOException(getMethod(var0.getClass(), "getInputStream"), var0, new Object[0]);
   }

   private static Method getMethod(Class var0, String var1) {
      try {
         Method var3 = var0.getMethod(var1, new Class[0]);
         return var3;
      } catch (NoSuchMethodException var2) {
         throw new RuntimeException(var2);
      }
   }

   private static Method getMethod(Class var0, String var1, Class ... var2) {
      try {
         Method var4 = var0.getMethod(var1, var2);
         return var4;
      } catch (NoSuchMethodException var3) {
         throw new RuntimeException(var3);
      }
   }

   public static OutputStream getOutputStream(Object var0) throws IOException {
      return (OutputStream)invokeMethodThrowsIOException(getMethod(var0.getClass(), "getOutputStream"), var0, new Object[0]);
   }

   public static Object getRemoteDevice(Object var0, String var1) throws IllegalArgumentException {
      return invokeMethodThrowsIllegalArgumentException(getMethod(var0.getClass(), "getRemoteDevice", new Class[]{String.class}), var0, new Object[]{var1});
   }

   private static Object invokeMethod(Method var0, Object var1, Object ... var2) {
      try {
         Object var6 = var0.invoke(var1, var2);
         return var6;
      } catch (IllegalAccessException var3) {
         throw new RuntimeException(var3);
      } catch (InvocationTargetException var4) {
         Throwable var5 = var4.getCause();
         var5.printStackTrace();
         if(var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         } else {
            throw new RuntimeException(var5);
         }
      }
   }

   private static Object invokeMethodThrowsIOException(Method var0, Object var1, Object ... var2) throws IOException {
      try {
         Object var5 = var0.invoke(var1, var2);
         return var5;
      } catch (IllegalAccessException var3) {
         throw new RuntimeException(var3);
      } catch (InvocationTargetException var4) {
         Throwable var6 = var4.getCause();
         var6.printStackTrace();
         if(var6 instanceof IOException) {
            throw (IOException)var6;
         } else if(var6 instanceof RuntimeException) {
            throw (RuntimeException)var6;
         } else {
            throw new RuntimeException(var4);
         }
      }
   }

   private static Object invokeMethodThrowsIllegalArgumentException(Method var0, Object var1, Object ... var2) throws IllegalArgumentException {
      try {
         Object var5 = var0.invoke(var1, var2);
         return var5;
      } catch (IllegalAccessException var3) {
         throw new RuntimeException(var3);
      } catch (InvocationTargetException var4) {
         Throwable var6 = var4.getCause();
         var6.printStackTrace();
         if(var6 instanceof IllegalArgumentException) {
            throw (IllegalArgumentException)var6;
         } else if(var6 instanceof RuntimeException) {
            throw (RuntimeException)var6;
         } else {
            throw new RuntimeException(var4);
         }
      }
   }

   private static Object invokeStaticMethod(Method var0) {
      try {
         Object var4 = var0.invoke((Object)null, new Object[0]);
         return var4;
      } catch (IllegalAccessException var1) {
         throw new RuntimeException(var1);
      } catch (InvocationTargetException var2) {
         Throwable var3 = var2.getCause();
         var3.printStackTrace();
         if(var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new RuntimeException(var3);
         }
      }
   }

   public static boolean isBluetoothEnabled(Object var0) {
      return ((Boolean)invokeMethod(getMethod(var0.getClass(), "isEnabled"), var0, new Object[0])).booleanValue();
   }

   public static boolean isBonded(Object var0) {
      return ((Integer)invokeMethod(getMethod(var0.getClass(), "getBondState"), var0, new Object[0])).intValue() == 12;
   }

   public static Object listenUsingInsecureRfcommWithServiceRecord(Object var0, String var1, UUID var2) throws IOException {
      return invokeMethodThrowsIOException(getMethod(var0.getClass(), "listenUsingInsecureRfcommWithServiceRecord", new Class[]{String.class, UUID.class}), var0, new Object[]{var1, var2});
   }

   public static Object listenUsingRfcommWithServiceRecord(Object var0, String var1, UUID var2) throws IOException {
      return invokeMethodThrowsIOException(getMethod(var0.getClass(), "listenUsingRfcommWithServiceRecord", new Class[]{String.class, UUID.class}), var0, new Object[]{var1, var2});
   }
}
