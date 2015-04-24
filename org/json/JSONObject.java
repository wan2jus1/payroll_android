package org.json;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONString;
import org.json.JSONTokener;

public class JSONObject {

   public static final Object NULL = new JSONObject.Null(null);
   private final Map map;


   public JSONObject() {
      this.map = new HashMap();
   }

   public JSONObject(Object var1) {
      this();
      this.populateMap(var1);
   }

   public JSONObject(Object var1, String[] var2) {
      this();
      Class var3 = var1.getClass();

      for(int var5 = 0; var5 < var2.length; ++var5) {
         String var4 = var2[var5];

         try {
            this.putOpt(var4, var3.getField(var4).get(var1));
         } catch (Exception var6) {
            ;
         }
      }

   }

   public JSONObject(String var1) throws JSONException {
      this((JSONTokener)(new JSONTokener(var1)));
   }

   public JSONObject(String var1, Locale var2) throws JSONException {
      this();
      ResourceBundle var4 = ResourceBundle.getBundle(var1, var2, Thread.currentThread().getContextClassLoader());
      Enumeration var5 = var4.getKeys();

      while(var5.hasMoreElements()) {
         Object var6 = var5.nextElement();
         if(var6 instanceof String) {
            String[] var7 = ((String)var6).split("\\.");
            int var10 = var7.length - 1;
            JSONObject var12 = this;

            JSONObject var11;
            for(int var9 = 0; var9 < var10; var12 = var11) {
               String var8 = var7[var9];
               JSONObject var3 = var12.optJSONObject(var8);
               var11 = var3;
               if(var3 == null) {
                  var11 = new JSONObject();
                  var12.put(var8, (Object)var11);
               }

               ++var9;
            }

            var12.put(var7[var10], (Object)var4.getString((String)var6));
         }
      }

   }

   public JSONObject(Map var1) {
      this.map = new HashMap();
      if(var1 != null) {
         Iterator var4 = var1.entrySet().iterator();

         while(var4.hasNext()) {
            Entry var2 = (Entry)var4.next();
            Object var3 = var2.getValue();
            if(var3 != null) {
               this.map.put(var2.getKey(), wrap(var3));
            }
         }
      }

   }

   public JSONObject(JSONObject var1, String[] var2) {
      this();

      for(int var4 = 0; var4 < var2.length; ++var4) {
         try {
            this.putOnce(var2[var4], var1.opt(var2[var4]));
         } catch (Exception var5) {
            ;
         }
      }

   }

   public JSONObject(JSONTokener var1) throws JSONException {
      this();
      if(var1.nextClean() != 123) {
         throw var1.syntaxError("A JSONObject text must begin with \'{\'");
      } else {
         while(true) {
            switch(var1.nextClean()) {
            case 0:
               throw var1.syntaxError("A JSONObject text must end with \'}\'");
            default:
               var1.back();
               String var2 = var1.nextValue().toString();
               if(var1.nextClean() != 58) {
                  throw var1.syntaxError("Expected a \':\' after a key");
               }

               this.putOnce(var2, var1.nextValue());
               switch(var1.nextClean()) {
               case 44:
               case 59:
                  if(var1.nextClean() != 125) {
                     var1.back();
                     continue;
                  }
               case 125:
                  break;
               default:
                  throw var1.syntaxError("Expected a \',\' or \'}\'");
               }
            case 125:
               return;
            }
         }
      }
   }

   public static String doubleToString(double var0) {
      String var2;
      if(!Double.isInfinite(var0) && !Double.isNaN(var0)) {
         String var3 = Double.toString(var0);
         var2 = var3;
         if(var3.indexOf(46) > 0) {
            var2 = var3;
            if(var3.indexOf(101) < 0) {
               var2 = var3;
               if(var3.indexOf(69) < 0) {
                  while(var3.endsWith("0")) {
                     var3 = var3.substring(0, var3.length() - 1);
                  }

                  var2 = var3;
                  if(var3.endsWith(".")) {
                     return var3.substring(0, var3.length() - 1);
                  }
               }
            }
         }
      } else {
         var2 = "null";
      }

      return var2;
   }

   public static String[] getNames(Object var0) {
      String[] var1 = null;
      String[] var5;
      if(var0 == null) {
         var5 = var1;
      } else {
         Field[] var2 = var0.getClass().getFields();
         int var4 = var2.length;
         var5 = var1;
         if(var4 != 0) {
            var1 = new String[var4];
            int var3 = 0;

            while(true) {
               var5 = var1;
               if(var3 >= var4) {
                  break;
               }

               var1[var3] = var2[var3].getName();
               ++var3;
            }
         }
      }

      return var5;
   }

   public static String[] getNames(JSONObject var0) {
      int var3 = var0.length();
      String[] var4;
      if(var3 == 0) {
         var4 = null;
      } else {
         Iterator var2 = var0.keys();
         String[] var1 = new String[var3];
         var3 = 0;

         while(true) {
            var4 = var1;
            if(!var2.hasNext()) {
               break;
            }

            var1[var3] = (String)var2.next();
            ++var3;
         }
      }

      return var4;
   }

   static final void indent(Writer var0, int var1) throws IOException {
      for(int var2 = 0; var2 < var1; ++var2) {
         var0.write(32);
      }

   }

   public static String numberToString(Number var0) throws JSONException {
      if(var0 == null) {
         throw new JSONException("Null pointer");
      } else {
         testValidity(var0);
         String var1 = var0.toString();
         String var2 = var1;
         if(var1.indexOf(46) > 0) {
            var2 = var1;
            if(var1.indexOf(101) < 0) {
               var2 = var1;
               if(var1.indexOf(69) < 0) {
                  while(var1.endsWith("0")) {
                     var1 = var1.substring(0, var1.length() - 1);
                  }

                  var2 = var1;
                  if(var1.endsWith(".")) {
                     var2 = var1.substring(0, var1.length() - 1);
                  }
               }
            }
         }

         return var2;
      }
   }

   private void populateMap(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public static Writer quote(String var0, Writer var1) throws IOException {
      if(var0 != null && var0.length() != 0) {
         char var3 = 0;
         int var6 = var0.length();
         var1.write(34);
         int var4 = 0;

         while(true) {
            char var5 = var3;
            if(var4 >= var6) {
               var1.write(34);
               return var1;
            }

            var3 = var0.charAt(var4);
            switch(var3) {
            case 8:
               var1.write("\\b");
               break;
            case 9:
               var1.write("\\t");
               break;
            case 10:
               var1.write("\\n");
               break;
            case 12:
               var1.write("\\f");
               break;
            case 13:
               var1.write("\\r");
               break;
            case 34:
            case 92:
               var1.write(92);
               var1.write(var3);
               break;
            case 47:
               if(var5 == 60) {
                  var1.write(92);
               }

               var1.write(var3);
               break;
            default:
               if(var3 >= 32 && (var3 < 128 || var3 >= 160) && (var3 < 8192 || var3 >= 8448)) {
                  var1.write(var3);
               } else {
                  var1.write("\\u");
                  String var2 = Integer.toHexString(var3);
                  var1.write("0000", 0, 4 - var2.length());
                  var1.write(var2);
               }
            }

            ++var4;
         }
      } else {
         var1.write("\"\"");
         return var1;
      }
   }

   public static String quote(String param0) {
      // $FF: Couldn't be decompiled
   }

   public static Object stringToValue(String var0) {
      if(!var0.equals("")) {
         if(var0.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
         }

         if(var0.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
         }

         if(var0.equalsIgnoreCase("null")) {
            return NULL;
         }

         char var2 = var0.charAt(0);
         if(var2 >= 48 && var2 <= 57 || var2 == 45) {
            try {
               if(var0.indexOf(46) <= -1 && var0.indexOf(101) <= -1 && var0.indexOf(69) <= -1) {
                  Long var4 = new Long(var0);
                  if(var0.equals(var4.toString())) {
                     if(var4.longValue() == (long)var4.intValue()) {
                        Integer var5 = new Integer(var4.intValue());
                        return var5;
                     }

                     return var4;
                  }
               } else {
                  Double var1 = Double.valueOf(var0);
                  if(!var1.isInfinite() && !var1.isNaN()) {
                     return var1;
                  }
               }
            } catch (Exception var3) {
               return var0;
            }
         }
      }

      return var0;
   }

   public static void testValidity(Object var0) throws JSONException {
      if(var0 != null) {
         if(var0 instanceof Double) {
            if(((Double)var0).isInfinite() || ((Double)var0).isNaN()) {
               throw new JSONException("JSON does not allow non-finite numbers.");
            }
         } else if(var0 instanceof Float && (((Float)var0).isInfinite() || ((Float)var0).isNaN())) {
            throw new JSONException("JSON does not allow non-finite numbers.");
         }
      }

   }

   public static String valueToString(Object var0) throws JSONException {
      if(var0 != null && !var0.equals((Object)null)) {
         if(var0 instanceof JSONString) {
            String var2;
            try {
               var2 = ((JSONString)var0).toJSONString();
            } catch (Exception var1) {
               throw new JSONException(var1);
            }

            if(var2 instanceof String) {
               return (String)var2;
            } else {
               throw new JSONException("Bad value from toJSONString: " + var2);
            }
         } else {
            return var0 instanceof Number?numberToString((Number)var0):(!(var0 instanceof Boolean) && !(var0 instanceof JSONObject) && !(var0 instanceof JSONArray)?(var0 instanceof Map?(new JSONObject((Map)var0)).toString():(var0 instanceof Collection?(new JSONArray((Collection)var0)).toString():(var0.getClass().isArray()?(new JSONArray(var0)).toString():quote(var0.toString())))):var0.toString());
         }
      } else {
         return "null";
      }
   }

   public static Object wrap(Object param0) {
      // $FF: Couldn't be decompiled
   }

   static final Writer writeValue(Writer var0, Object var1, int var2, int var3) throws JSONException, IOException {
      if(var1 != null && !var1.equals((Object)null)) {
         if(var1 instanceof JSONObject) {
            ((JSONObject)var1).write(var0, var2, var3);
            return var0;
         } else if(var1 instanceof JSONArray) {
            ((JSONArray)var1).write(var0, var2, var3);
            return var0;
         } else if(var1 instanceof Map) {
            (new JSONObject((Map)var1)).write(var0, var2, var3);
            return var0;
         } else if(var1 instanceof Collection) {
            (new JSONArray((Collection)var1)).write(var0, var2, var3);
            return var0;
         } else if(var1.getClass().isArray()) {
            (new JSONArray(var1)).write(var0, var2, var3);
            return var0;
         } else if(var1 instanceof Number) {
            var0.write(numberToString((Number)var1));
            return var0;
         } else if(var1 instanceof Boolean) {
            var0.write(var1.toString());
            return var0;
         } else if(var1 instanceof JSONString) {
            String var4;
            try {
               var4 = ((JSONString)var1).toJSONString();
            } catch (Exception var5) {
               throw new JSONException(var5);
            }

            String var6;
            if(var4 != null) {
               var6 = var4.toString();
            } else {
               var6 = quote(var1.toString());
            }

            var0.write(var6);
            return var0;
         } else {
            quote(var1.toString(), var0);
            return var0;
         }
      } else {
         var0.write("null");
         return var0;
      }
   }

   public JSONObject accumulate(String var1, Object var2) throws JSONException {
      testValidity(var2);
      Object var3 = this.opt(var1);
      if(var3 == null) {
         var3 = var2;
         if(var2 instanceof JSONArray) {
            var3 = (new JSONArray()).put((Object)var2);
         }

         this.put(var1, (Object)var3);
         return this;
      } else if(var3 instanceof JSONArray) {
         ((JSONArray)var3).put((Object)var2);
         return this;
      } else {
         this.put(var1, (Object)(new JSONArray()).put((Object)var3).put((Object)var2));
         return this;
      }
   }

   public JSONObject append(String var1, Object var2) throws JSONException {
      testValidity(var2);
      Object var3 = this.opt(var1);
      if(var3 == null) {
         this.put(var1, (Object)(new JSONArray()).put((Object)var2));
         return this;
      } else if(var3 instanceof JSONArray) {
         this.put(var1, (Object)((JSONArray)var3).put((Object)var2));
         return this;
      } else {
         throw new JSONException("JSONObject[" + var1 + "] is not a JSONArray.");
      }
   }

   public Object get(String var1) throws JSONException {
      if(var1 == null) {
         throw new JSONException("Null key.");
      } else {
         Object var2 = this.opt(var1);
         if(var2 == null) {
            throw new JSONException("JSONObject[" + quote(var1) + "] not found.");
         } else {
            return var2;
         }
      }
   }

   public boolean getBoolean(String var1) throws JSONException {
      Object var2 = this.get(var1);
      if(!var2.equals(Boolean.FALSE) && (!(var2 instanceof String) || !((String)var2).equalsIgnoreCase("false"))) {
         if(!var2.equals(Boolean.TRUE) && (!(var2 instanceof String) || !((String)var2).equalsIgnoreCase("true"))) {
            throw new JSONException("JSONObject[" + quote(var1) + "] is not a Boolean.");
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public double getDouble(String var1) throws JSONException {
      Object var4 = this.get(var1);

      try {
         if(var4 instanceof Number) {
            return ((Number)var4).doubleValue();
         } else {
            double var2 = Double.parseDouble((String)var4);
            return var2;
         }
      } catch (Exception var5) {
         throw new JSONException("JSONObject[" + quote(var1) + "] is not a number.");
      }
   }

   public int getInt(String var1) throws JSONException {
      Object var2 = this.get(var1);

      try {
         if(var2 instanceof Number) {
            return ((Number)var2).intValue();
         } else {
            int var3 = Integer.parseInt((String)var2);
            return var3;
         }
      } catch (Exception var4) {
         throw new JSONException("JSONObject[" + quote(var1) + "] is not an int.");
      }
   }

   public JSONArray getJSONArray(String var1) throws JSONException {
      Object var2 = this.get(var1);
      if(var2 instanceof JSONArray) {
         return (JSONArray)var2;
      } else {
         throw new JSONException("JSONObject[" + quote(var1) + "] is not a JSONArray.");
      }
   }

   public JSONObject getJSONObject(String var1) throws JSONException {
      Object var2 = this.get(var1);
      if(var2 instanceof JSONObject) {
         return (JSONObject)var2;
      } else {
         throw new JSONException("JSONObject[" + quote(var1) + "] is not a JSONObject.");
      }
   }

   public long getLong(String var1) throws JSONException {
      Object var2 = this.get(var1);

      try {
         if(var2 instanceof Number) {
            return ((Number)var2).longValue();
         } else {
            long var3 = Long.parseLong((String)var2);
            return var3;
         }
      } catch (Exception var5) {
         throw new JSONException("JSONObject[" + quote(var1) + "] is not a long.");
      }
   }

   public String getString(String var1) throws JSONException {
      Object var2 = this.get(var1);
      if(var2 instanceof String) {
         return (String)var2;
      } else {
         throw new JSONException("JSONObject[" + quote(var1) + "] not a string.");
      }
   }

   public boolean has(String var1) {
      return this.map.containsKey(var1);
   }

   public JSONObject increment(String var1) throws JSONException {
      Object var2 = this.opt(var1);
      if(var2 == null) {
         this.put(var1, 1);
         return this;
      } else if(var2 instanceof Integer) {
         this.put(var1, ((Integer)var2).intValue() + 1);
         return this;
      } else if(var2 instanceof Long) {
         this.put(var1, ((Long)var2).longValue() + 1L);
         return this;
      } else if(var2 instanceof Double) {
         this.put(var1, ((Double)var2).doubleValue() + 1.0D);
         return this;
      } else if(var2 instanceof Float) {
         this.put(var1, (double)(((Float)var2).floatValue() + 1.0F));
         return this;
      } else {
         throw new JSONException("Unable to increment [" + quote(var1) + "].");
      }
   }

   public boolean isNull(String var1) {
      return NULL.equals(this.opt(var1));
   }

   public Set keySet() {
      return this.map.keySet();
   }

   public Iterator keys() {
      return this.keySet().iterator();
   }

   public int length() {
      return this.map.size();
   }

   public JSONArray names() {
      JSONArray var2 = new JSONArray();
      Iterator var1 = this.keys();

      while(var1.hasNext()) {
         var2.put((Object)var1.next());
      }

      JSONArray var3 = var2;
      if(var2.length() == 0) {
         var3 = null;
      }

      return var3;
   }

   public Object opt(String var1) {
      return var1 == null?null:this.map.get(var1);
   }

   public boolean optBoolean(String var1) {
      return this.optBoolean(var1, false);
   }

   public boolean optBoolean(String var1, boolean var2) {
      try {
         boolean var3 = this.getBoolean(var1);
         return var3;
      } catch (Exception var4) {
         return var2;
      }
   }

   public double optDouble(String var1) {
      return this.optDouble(var1, Double.NaN);
   }

   public double optDouble(String var1, double var2) {
      try {
         double var4 = this.getDouble(var1);
         return var4;
      } catch (Exception var6) {
         return var2;
      }
   }

   public int optInt(String var1) {
      return this.optInt(var1, 0);
   }

   public int optInt(String var1, int var2) {
      try {
         int var3 = this.getInt(var1);
         return var3;
      } catch (Exception var4) {
         return var2;
      }
   }

   public JSONArray optJSONArray(String var1) {
      Object var2 = this.opt(var1);
      return var2 instanceof JSONArray?(JSONArray)var2:null;
   }

   public JSONObject optJSONObject(String var1) {
      Object var2 = this.opt(var1);
      return var2 instanceof JSONObject?(JSONObject)var2:null;
   }

   public long optLong(String var1) {
      return this.optLong(var1, 0L);
   }

   public long optLong(String var1, long var2) {
      try {
         long var4 = this.getLong(var1);
         return var4;
      } catch (Exception var6) {
         return var2;
      }
   }

   public String optString(String var1) {
      return this.optString(var1, "");
   }

   public String optString(String var1, String var2) {
      Object var3 = this.opt(var1);
      return NULL.equals(var3)?var2:var3.toString();
   }

   public JSONObject put(String var1, double var2) throws JSONException {
      this.put(var1, (Object)(new Double(var2)));
      return this;
   }

   public JSONObject put(String var1, int var2) throws JSONException {
      this.put(var1, (Object)(new Integer(var2)));
      return this;
   }

   public JSONObject put(String var1, long var2) throws JSONException {
      this.put(var1, (Object)(new Long(var2)));
      return this;
   }

   public JSONObject put(String var1, Object var2) throws JSONException {
      if(var1 == null) {
         throw new NullPointerException("Null key.");
      } else if(var2 != null) {
         testValidity(var2);
         this.map.put(var1, var2);
         return this;
      } else {
         this.remove(var1);
         return this;
      }
   }

   public JSONObject put(String var1, Collection var2) throws JSONException {
      this.put(var1, (Object)(new JSONArray(var2)));
      return this;
   }

   public JSONObject put(String var1, Map var2) throws JSONException {
      this.put(var1, (Object)(new JSONObject(var2)));
      return this;
   }

   public JSONObject put(String var1, boolean var2) throws JSONException {
      Boolean var3;
      if(var2) {
         var3 = Boolean.TRUE;
      } else {
         var3 = Boolean.FALSE;
      }

      this.put(var1, (Object)var3);
      return this;
   }

   public JSONObject putOnce(String var1, Object var2) throws JSONException {
      if(var1 != null && var2 != null) {
         if(this.opt(var1) != null) {
            throw new JSONException("Duplicate key \"" + var1 + "\"");
         }

         this.put(var1, (Object)var2);
      }

      return this;
   }

   public JSONObject putOpt(String var1, Object var2) throws JSONException {
      if(var1 != null && var2 != null) {
         this.put(var1, (Object)var2);
      }

      return this;
   }

   public Object remove(String var1) {
      return this.map.remove(var1);
   }

   public JSONArray toJSONArray(JSONArray var1) throws JSONException {
      JSONArray var2;
      if(var1 != null && var1.length() != 0) {
         JSONArray var3 = new JSONArray();
         int var4 = 0;

         while(true) {
            var2 = var3;
            if(var4 >= var1.length()) {
               break;
            }

            var3.put((Object)this.opt(var1.getString(var4)));
            ++var4;
         }
      } else {
         var2 = null;
      }

      return var2;
   }

   public String toString() {
      try {
         String var1 = this.toString(0);
         return var1;
      } catch (Exception var2) {
         return null;
      }
   }

   public String toString(int param1) throws JSONException {
      // $FF: Couldn't be decompiled
   }

   public Writer write(Writer var1) throws JSONException {
      return this.write(var1, 0, 0);
   }

   Writer write(Writer param1, int param2, int param3) throws JSONException {
      // $FF: Couldn't be decompiled
   }

   private static final class Null {

      private Null() {
      }

      Null(Object var1) {
         this();
      }

      protected final Object clone() {
         return this;
      }

      public boolean equals(Object var1) {
         return var1 == null || var1 == this;
      }

      public String toString() {
         return "null";
      }
   }
}
