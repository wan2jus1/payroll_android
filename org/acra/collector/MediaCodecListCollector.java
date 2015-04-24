package org.acra.collector;

import android.util.SparseArray;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MediaCodecListCollector {

   private static final String[] AAC_TYPES;
   private static final String[] AVC_TYPES;
   private static final String COLOR_FORMAT_PREFIX = "COLOR_";
   private static final String[] H263_TYPES;
   private static final String[] MPEG4_TYPES;
   private static Class codecCapabilitiesClass;
   private static Field colorFormatsField;
   private static Method getCapabilitiesForTypeMethod;
   private static Method getCodecInfoAtMethod;
   private static Method getNameMethod;
   private static Method getSupportedTypesMethod;
   private static Method isEncoderMethod;
   private static Field levelField;
   private static SparseArray mAACProfileValues;
   private static SparseArray mAVCLevelValues;
   private static SparseArray mAVCProfileValues;
   private static SparseArray mColorFormatValues;
   private static SparseArray mH263LevelValues;
   private static SparseArray mH263ProfileValues;
   private static SparseArray mMPEG4LevelValues;
   private static SparseArray mMPEG4ProfileValues;
   private static Class mediaCodecInfoClass;
   private static Class mediaCodecListClass;
   private static Field profileField;
   private static Field profileLevelsField;


   static {
      // $FF: Couldn't be decompiled
   }

   public static String collecMediaCodecList() {
      // $FF: Couldn't be decompiled
   }

   private static String collectCapabilitiesForType(Object var0, String var1) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
      StringBuilder var2 = new StringBuilder();
      Object var3 = getCapabilitiesForTypeMethod.invoke(var0, new Object[]{var1});
      int[] var4 = (int[])((int[])colorFormatsField.get(var3));
      int var5;
      if(var4.length > 0) {
         var2.append(var1).append(" color formats:");

         for(var5 = 0; var5 < var4.length; ++var5) {
            var2.append((String)mColorFormatValues.get(var4[var5]));
            if(var5 < var4.length - 1) {
               var2.append(',');
            }
         }

         var2.append("\n");
      }

      Object[] var9 = (Object[])((Object[])profileLevelsField.get(var3));
      if(var9.length > 0) {
         var2.append(var1).append(" profile levels:");

         for(var5 = 0; var5 < var9.length; ++var5) {
            MediaCodecListCollector.CodecType var8 = identifyCodecType(var0);
            int var6 = profileField.getInt(var9[var5]);
            int var7 = levelField.getInt(var9[var5]);
            if(var8 == null) {
               var2.append(var6).append('-').append(var7);
            }

            switch(null.$SwitchMap$org$acra$collector$MediaCodecListCollector$CodecType[var8.ordinal()]) {
            case 1:
               var2.append(var6).append((String)mAVCProfileValues.get(var6)).append('-').append((String)mAVCLevelValues.get(var7));
               break;
            case 2:
               var2.append((String)mH263ProfileValues.get(var6)).append('-').append((String)mH263LevelValues.get(var7));
               break;
            case 3:
               var2.append((String)mMPEG4ProfileValues.get(var6)).append('-').append((String)mMPEG4LevelValues.get(var7));
               break;
            case 4:
               var2.append((String)mAACProfileValues.get(var6));
            }

            if(var5 < var9.length - 1) {
               var2.append(',');
            }
         }

         var2.append("\n");
      }

      return var2.append("\n").toString();
   }

   private static MediaCodecListCollector.CodecType identifyCodecType(Object var0) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
      String var4 = (String)getNameMethod.invoke(var0, new Object[0]);
      String[] var1 = AVC_TYPES;
      int var3 = var1.length;

      int var2;
      for(var2 = 0; var2 < var3; ++var2) {
         if(var4.contains(var1[var2])) {
            return MediaCodecListCollector.CodecType.AVC;
         }
      }

      var1 = H263_TYPES;
      var3 = var1.length;

      for(var2 = 0; var2 < var3; ++var2) {
         if(var4.contains(var1[var2])) {
            return MediaCodecListCollector.CodecType.H263;
         }
      }

      var1 = MPEG4_TYPES;
      var3 = var1.length;

      for(var2 = 0; var2 < var3; ++var2) {
         if(var4.contains(var1[var2])) {
            return MediaCodecListCollector.CodecType.MPEG4;
         }
      }

      var1 = AAC_TYPES;
      var3 = var1.length;

      for(var2 = 0; var2 < var3; ++var2) {
         if(var4.contains(var1[var2])) {
            return MediaCodecListCollector.CodecType.AAC;
         }
      }

      return null;
   }

   private static enum CodecType {

      // $FF: synthetic field
      private static final MediaCodecListCollector.CodecType[] $VALUES = new MediaCodecListCollector.CodecType[]{AVC, H263, MPEG4, AAC};
      AAC,
      AVC,
      H263,
      MPEG4;


   }
}
