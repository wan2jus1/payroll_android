package org.json;

import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONArray {

   private final ArrayList myArrayList;


   public JSONArray() {
      this.myArrayList = new ArrayList();
   }

   public JSONArray(Object var1) throws JSONException {
      this();
      if(!var1.getClass().isArray()) {
         throw new JSONException("JSONArray initial value should be a string or collection or array.");
      } else {
         int var3 = Array.getLength(var1);

         for(int var2 = 0; var2 < var3; ++var2) {
            this.put((Object)JSONObject.wrap(Array.get(var1, var2)));
         }

      }
   }

   public JSONArray(String var1) throws JSONException {
      this((JSONTokener)(new JSONTokener(var1)));
   }

   public JSONArray(Collection var1) {
      this.myArrayList = new ArrayList();
      if(var1 != null) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            this.myArrayList.add(JSONObject.wrap(var2.next()));
         }
      }

   }

   public JSONArray(JSONTokener var1) throws JSONException {
      this();
      if(var1.nextClean() != 91) {
         throw var1.syntaxError("A JSONArray text must start with \'[\'");
      } else if(var1.nextClean() != 93) {
         var1.back();

         while(true) {
            if(var1.nextClean() == 44) {
               var1.back();
               this.myArrayList.add(JSONObject.NULL);
            } else {
               var1.back();
               this.myArrayList.add(var1.nextValue());
            }

            switch(var1.nextClean()) {
            case 44:
               if(var1.nextClean() != 93) {
                  var1.back();
                  break;
               }

               return;
            case 93:
               return;
            default:
               throw var1.syntaxError("Expected a \',\' or \']\'");
            }
         }
      }
   }

   public Object get(int var1) throws JSONException {
      Object var2 = this.opt(var1);
      if(var2 == null) {
         throw new JSONException("JSONArray[" + var1 + "] not found.");
      } else {
         return var2;
      }
   }

   public boolean getBoolean(int var1) throws JSONException {
      Object var2 = this.get(var1);
      if(!var2.equals(Boolean.FALSE) && (!(var2 instanceof String) || !((String)var2).equalsIgnoreCase("false"))) {
         if(!var2.equals(Boolean.TRUE) && (!(var2 instanceof String) || !((String)var2).equalsIgnoreCase("true"))) {
            throw new JSONException("JSONArray[" + var1 + "] is not a boolean.");
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public double getDouble(int var1) throws JSONException {
      Object var4 = this.get(var1);

      try {
         if(var4 instanceof Number) {
            return ((Number)var4).doubleValue();
         } else {
            double var2 = Double.parseDouble((String)var4);
            return var2;
         }
      } catch (Exception var5) {
         throw new JSONException("JSONArray[" + var1 + "] is not a number.");
      }
   }

   public int getInt(int var1) throws JSONException {
      Object var2 = this.get(var1);

      try {
         if(var2 instanceof Number) {
            return ((Number)var2).intValue();
         } else {
            int var3 = Integer.parseInt((String)var2);
            return var3;
         }
      } catch (Exception var4) {
         throw new JSONException("JSONArray[" + var1 + "] is not a number.");
      }
   }

   public JSONArray getJSONArray(int var1) throws JSONException {
      Object var2 = this.get(var1);
      if(var2 instanceof JSONArray) {
         return (JSONArray)var2;
      } else {
         throw new JSONException("JSONArray[" + var1 + "] is not a JSONArray.");
      }
   }

   public JSONObject getJSONObject(int var1) throws JSONException {
      Object var2 = this.get(var1);
      if(var2 instanceof JSONObject) {
         return (JSONObject)var2;
      } else {
         throw new JSONException("JSONArray[" + var1 + "] is not a JSONObject.");
      }
   }

   public long getLong(int var1) throws JSONException {
      Object var2 = this.get(var1);

      try {
         if(var2 instanceof Number) {
            return ((Number)var2).longValue();
         } else {
            long var3 = Long.parseLong((String)var2);
            return var3;
         }
      } catch (Exception var5) {
         throw new JSONException("JSONArray[" + var1 + "] is not a number.");
      }
   }

   public String getString(int var1) throws JSONException {
      Object var2 = this.get(var1);
      if(var2 instanceof String) {
         return (String)var2;
      } else {
         throw new JSONException("JSONArray[" + var1 + "] not a string.");
      }
   }

   public boolean isNull(int var1) {
      return JSONObject.NULL.equals(this.opt(var1));
   }

   public String join(String var1) throws JSONException {
      int var4 = this.length();
      StringBuffer var2 = new StringBuffer();

      for(int var3 = 0; var3 < var4; ++var3) {
         if(var3 > 0) {
            var2.append(var1);
         }

         var2.append(JSONObject.valueToString(this.myArrayList.get(var3)));
      }

      return var2.toString();
   }

   public int length() {
      return this.myArrayList.size();
   }

   public Object opt(int var1) {
      return var1 >= 0 && var1 < this.length()?this.myArrayList.get(var1):null;
   }

   public boolean optBoolean(int var1) {
      return this.optBoolean(var1, false);
   }

   public boolean optBoolean(int var1, boolean var2) {
      try {
         boolean var4 = this.getBoolean(var1);
         return var4;
      } catch (Exception var5) {
         return var2;
      }
   }

   public double optDouble(int var1) {
      return this.optDouble(var1, Double.NaN);
   }

   public double optDouble(int var1, double var2) {
      try {
         double var4 = this.getDouble(var1);
         return var4;
      } catch (Exception var7) {
         return var2;
      }
   }

   public int optInt(int var1) {
      return this.optInt(var1, 0);
   }

   public int optInt(int var1, int var2) {
      try {
         var1 = this.getInt(var1);
         return var1;
      } catch (Exception var4) {
         return var2;
      }
   }

   public JSONArray optJSONArray(int var1) {
      Object var2 = this.opt(var1);
      return var2 instanceof JSONArray?(JSONArray)var2:null;
   }

   public JSONObject optJSONObject(int var1) {
      Object var2 = this.opt(var1);
      return var2 instanceof JSONObject?(JSONObject)var2:null;
   }

   public long optLong(int var1) {
      return this.optLong(var1, 0L);
   }

   public long optLong(int var1, long var2) {
      try {
         long var5 = this.getLong(var1);
         return var5;
      } catch (Exception var7) {
         return var2;
      }
   }

   public String optString(int var1) {
      return this.optString(var1, "");
   }

   public String optString(int var1, String var2) {
      Object var3 = this.opt(var1);
      return JSONObject.NULL.equals(var3)?var2:var3.toString();
   }

   public JSONArray put(double var1) throws JSONException {
      Double var3 = new Double(var1);
      JSONObject.testValidity(var3);
      this.put((Object)var3);
      return this;
   }

   public JSONArray put(int var1) {
      this.put((Object)(new Integer(var1)));
      return this;
   }

   public JSONArray put(int var1, double var2) throws JSONException {
      this.put(var1, (Object)(new Double(var2)));
      return this;
   }

   public JSONArray put(int var1, int var2) throws JSONException {
      this.put(var1, (Object)(new Integer(var2)));
      return this;
   }

   public JSONArray put(int var1, long var2) throws JSONException {
      this.put(var1, (Object)(new Long(var2)));
      return this;
   }

   public JSONArray put(int var1, Object var2) throws JSONException {
      JSONObject.testValidity(var2);
      if(var1 < 0) {
         throw new JSONException("JSONArray[" + var1 + "] not found.");
      } else if(var1 < this.length()) {
         this.myArrayList.set(var1, var2);
         return this;
      } else {
         while(var1 != this.length()) {
            this.put((Object)JSONObject.NULL);
         }

         this.put((Object)var2);
         return this;
      }
   }

   public JSONArray put(int var1, Collection var2) throws JSONException {
      this.put(var1, (Object)(new JSONArray(var2)));
      return this;
   }

   public JSONArray put(int var1, Map var2) throws JSONException {
      this.put(var1, (Object)(new JSONObject(var2)));
      return this;
   }

   public JSONArray put(int var1, boolean var2) throws JSONException {
      Boolean var3;
      if(var2) {
         var3 = Boolean.TRUE;
      } else {
         var3 = Boolean.FALSE;
      }

      this.put(var1, (Object)var3);
      return this;
   }

   public JSONArray put(long var1) {
      this.put((Object)(new Long(var1)));
      return this;
   }

   public JSONArray put(Object var1) {
      this.myArrayList.add(var1);
      return this;
   }

   public JSONArray put(Collection var1) {
      this.put((Object)(new JSONArray(var1)));
      return this;
   }

   public JSONArray put(Map var1) {
      this.put((Object)(new JSONObject(var1)));
      return this;
   }

   public JSONArray put(boolean var1) {
      Boolean var2;
      if(var1) {
         var2 = Boolean.TRUE;
      } else {
         var2 = Boolean.FALSE;
      }

      this.put((Object)var2);
      return this;
   }

   public Object remove(int var1) {
      Object var2 = this.opt(var1);
      this.myArrayList.remove(var1);
      return var2;
   }

   public JSONObject toJSONObject(JSONArray var1) throws JSONException {
      JSONObject var2;
      if(var1 != null && var1.length() != 0 && this.length() != 0) {
         JSONObject var3 = new JSONObject();
         int var4 = 0;

         while(true) {
            var2 = var3;
            if(var4 >= var1.length()) {
               break;
            }

            var3.put(var1.getString(var4), (Object)this.opt(var4));
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
}
