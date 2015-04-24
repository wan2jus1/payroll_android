package org.json.zip;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.Kim;
import org.json.zip.BitWriter;
import org.json.zip.Huff;
import org.json.zip.JSONzip;
import org.json.zip.Keep;

public class Compressor extends JSONzip {

   final BitWriter bitwriter;


   public Compressor(BitWriter var1) {
      this.bitwriter = var1;
   }

   private static int bcd(char var0) {
      if(var0 >= 48 && var0 <= 57) {
         return var0 - 48;
      } else {
         switch(var0) {
         case 43:
            return 12;
         case 44:
         default:
            return 13;
         case 45:
            return 11;
         case 46:
            return 10;
         }
      }
   }

   private void one() throws JSONException {
      this.write(1, 1);
   }

   private void write(int var1, int var2) throws JSONException {
      try {
         this.bitwriter.write(var1, var2);
      } catch (Throwable var4) {
         throw new JSONException(var4);
      }
   }

   private void write(int var1, Huff var2) throws JSONException {
      var2.write(var1, this.bitwriter);
   }

   private void write(Kim var1, int var2, int var3, Huff var4) throws JSONException {
      while(var2 < var3) {
         this.write(var1.get(var2), var4);
         ++var2;
      }

   }

   private void write(Kim var1, Huff var2) throws JSONException {
      this.write(var1, 0, var1.length, var2);
   }

   private void writeAndTick(int var1, Keep var2) throws JSONException {
      int var3 = var2.bitsize();
      var2.tick(var1);
      this.write(var1, var3);
   }

   private void writeArray(JSONArray var1) throws JSONException {
      boolean var6 = false;
      int var5 = var1.length();
      if(var5 == 0) {
         this.write(1, 3);
      } else {
         Object var3 = var1.get(0);
         Object var2 = var3;
         if(var3 == null) {
            var2 = JSONObject.NULL;
         }

         if(var2 instanceof String) {
            var6 = true;
            this.write(6, 3);
            this.writeString((String)var2);
         } else {
            this.write(7, 3);
            this.writeValue(var2);
         }

         for(int var4 = 1; var4 < var5; ++var4) {
            var3 = var1.get(var4);
            var2 = var3;
            if(var3 == null) {
               var2 = JSONObject.NULL;
            }

            if(var2 instanceof String != var6) {
               this.zero();
            }

            this.one();
            if(var2 instanceof String) {
               this.writeString((String)var2);
            } else {
               this.writeValue(var2);
            }
         }

         this.zero();
         this.zero();
      }
   }

   private void writeJSON(Object var1) throws JSONException {
      if(JSONObject.NULL.equals(var1)) {
         this.write(4, 3);
      } else if(Boolean.FALSE.equals(var1)) {
         this.write(3, 3);
      } else if(Boolean.TRUE.equals(var1)) {
         this.write(2, 3);
      } else {
         Object var2;
         if(var1 instanceof Map) {
            var2 = new JSONObject((Map)var1);
         } else if(var1 instanceof Collection) {
            var2 = new JSONArray((Collection)var1);
         } else {
            var2 = var1;
            if(var1.getClass().isArray()) {
               var2 = new JSONArray(var1);
            }
         }

         if(var2 instanceof JSONObject) {
            this.writeObject((JSONObject)var2);
         } else if(var2 instanceof JSONArray) {
            this.writeArray((JSONArray)var2);
         } else {
            throw new JSONException("Unrecognized object");
         }
      }
   }

   private void writeName(String var1) throws JSONException {
      Kim var3 = new Kim(var1);
      int var2 = this.namekeep.find(var3);
      if(var2 != -1) {
         this.one();
         this.writeAndTick(var2, this.namekeep);
      } else {
         this.zero();
         this.write(var3, this.namehuff);
         this.write(256, this.namehuff);
         this.namekeep.register(var3);
      }
   }

   private void writeObject(JSONObject var1) throws JSONException {
      boolean var4 = true;
      Iterator var2 = var1.keys();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         if(var3 instanceof String) {
            if(var4) {
               var4 = false;
               this.write(5, 3);
            } else {
               this.one();
            }

            this.writeName((String)var3);
            var3 = var1.get((String)var3);
            if(var3 instanceof String) {
               this.zero();
               this.writeString((String)var3);
            } else {
               this.one();
               this.writeValue(var3);
            }
         }
      }

      if(var4) {
         this.write(0, 3);
      } else {
         this.zero();
      }
   }

   private void writeString(String var1) throws JSONException {
      if(var1.length() == 0) {
         this.zero();
         this.zero();
         this.write(256, this.substringhuff);
         this.zero();
      } else {
         Kim var3 = new Kim(var1);
         int var2 = this.stringkeep.find(var3);
         if(var2 != -1) {
            this.one();
            this.writeAndTick(var2, this.stringkeep);
         } else {
            this.writeSubstring(var3);
            this.stringkeep.register(var3);
         }
      }
   }

   private void writeSubstring(Kim var1) throws JSONException {
      this.substringkeep.reserve();
      this.zero();
      int var2 = 0;
      int var8 = var1.length;
      int var3 = -1;
      int var6 = 0;

      while(true) {
         int var5 = -1;
         int var4 = var2;

         while(true) {
            if(var4 <= var8 - 3) {
               var5 = this.substringkeep.match(var1, var4, var8);
               if(var5 == -1) {
                  ++var4;
                  continue;
               }
            }

            if(var5 == -1) {
               this.zero();
               if(var2 < var8) {
                  this.write(var1, var2, var8, this.substringhuff);
                  if(var3 != -1) {
                     this.substringkeep.registerOne(var1, var3, var6);
                  }
               }

               this.write(256, this.substringhuff);
               this.zero();
               this.substringkeep.registerMany(var1);
               return;
            }

            int var7 = var3;
            if(var2 != var4) {
               this.zero();
               this.write(var1, var2, var4, this.substringhuff);
               this.write(256, this.substringhuff);
               var7 = var3;
               if(var3 != -1) {
                  this.substringkeep.registerOne(var1, var3, var6);
                  var7 = -1;
               }
            }

            this.one();
            this.writeAndTick(var5, this.substringkeep);
            var2 = var4 + this.substringkeep.length(var5);
            if(var7 != -1) {
               this.substringkeep.registerOne(var1, var7, var6);
            }

            var3 = var4;
            var6 = var2 + 1;
            break;
         }
      }
   }

   private void writeValue(Object var1) throws JSONException {
      if(var1 instanceof Number) {
         String var2 = JSONObject.numberToString((Number)var1);
         int var3 = this.values.find(var2);
         if(var3 != -1) {
            this.write(2, 2);
            this.writeAndTick(var3, this.values);
         } else {
            if(var1 instanceof Integer || var1 instanceof Long) {
               long var4 = ((Number)var1).longValue();
               if(var4 >= 0L && var4 < 16384L) {
                  this.write(0, 2);
                  if(var4 < 16L) {
                     this.zero();
                     this.write((int)var4, 4);
                     return;
                  }

                  this.one();
                  if(var4 < 128L) {
                     this.zero();
                     this.write((int)var4, 7);
                     return;
                  }

                  this.one();
                  this.write((int)var4, 14);
                  return;
               }
            }

            this.write(1, 2);

            for(var3 = 0; var3 < var2.length(); ++var3) {
               this.write(bcd(var2.charAt(var3)), 4);
            }

            this.write(endOfNumber, 4);
            this.values.register(var2);
         }
      } else {
         this.write(3, 2);
         this.writeJSON(var1);
      }
   }

   private void zero() throws JSONException {
      this.write(0, 1);
   }

   public void flush() throws JSONException {
      this.pad(8);
   }

   public void pad(int var1) throws JSONException {
      try {
         this.bitwriter.pad(var1);
      } catch (Throwable var3) {
         throw new JSONException(var3);
      }
   }

   public void zip(JSONArray var1) throws JSONException {
      this.begin();
      this.writeJSON(var1);
   }

   public void zip(JSONObject var1) throws JSONException {
      this.begin();
      this.writeJSON(var1);
   }
}
