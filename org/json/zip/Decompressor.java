package org.json.zip;

import java.io.UnsupportedEncodingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.Kim;
import org.json.zip.BitReader;
import org.json.zip.JSONzip;
import org.json.zip.Keep;

public class Decompressor extends JSONzip {

   BitReader bitreader;


   public Decompressor(BitReader var1) {
      this.bitreader = var1;
   }

   private boolean bit() throws JSONException {
      try {
         boolean var2 = this.bitreader.bit();
         return var2;
      } catch (Throwable var3) {
         throw new JSONException(var3);
      }
   }

   private Object getAndTick(Keep var1, BitReader var2) throws JSONException {
      try {
         int var3 = var2.read(var1.bitsize());
         Object var5 = var1.value(var3);
         if(var3 >= var1.length) {
            throw new JSONException("Deep error.");
         } else {
            var1.tick(var3);
            return var5;
         }
      } catch (Throwable var4) {
         throw new JSONException(var4);
      }
   }

   private int read(int var1) throws JSONException {
      try {
         var1 = this.bitreader.read(var1);
         return var1;
      } catch (Throwable var3) {
         throw new JSONException(var3);
      }
   }

   private JSONArray readArray(boolean var1) throws JSONException {
      JSONArray var3 = new JSONArray();
      Object var2;
      if(var1) {
         var2 = this.readString();
      } else {
         var2 = this.readValue();
      }

      var3.put((Object)var2);

      while(true) {
         for(; this.bit(); var3.put((Object)var2)) {
            if(var1) {
               var2 = this.readString();
            } else {
               var2 = this.readValue();
            }
         }

         if(!this.bit()) {
            return var3;
         }

         if(var1) {
            var2 = this.readValue();
         } else {
            var2 = this.readString();
         }

         var3.put((Object)var2);
      }
   }

   private Object readJSON() throws JSONException {
      switch(this.read(3)) {
      case 0:
         return new JSONObject();
      case 1:
         return new JSONArray();
      case 2:
         return Boolean.TRUE;
      case 3:
         return Boolean.FALSE;
      case 4:
      default:
         return JSONObject.NULL;
      case 5:
         return this.readObject();
      case 6:
         return this.readArray(true);
      case 7:
         return this.readArray(false);
      }
   }

   private String readName() throws JSONException {
      byte[] var1 = new byte[65536];
      int var2 = 0;
      if(this.bit()) {
         return this.getAndTick(this.namekeep, this.bitreader).toString();
      } else {
         while(true) {
            int var3 = this.namehuff.read(this.bitreader);
            if(var3 == 256) {
               if(var2 == 0) {
                  return "";
               } else {
                  Kim var4 = new Kim(var1, var2);
                  this.namekeep.register(var4);
                  return var4.toString();
               }
            }

            var1[var2] = (byte)var3;
            ++var2;
         }
      }
   }

   private JSONObject readObject() throws JSONException {
      JSONObject var2 = new JSONObject();

      do {
         String var3 = this.readName();
         Object var1;
         if(!this.bit()) {
            var1 = this.readString();
         } else {
            var1 = this.readValue();
         }

         var2.put(var3, (Object)var1);
      } while(this.bit());

      return var2;
   }

   private String readString() throws JSONException {
      int var2 = 0;
      int var3 = -1;
      int var4 = 0;
      if(this.bit()) {
         return this.getAndTick(this.stringkeep, this.bitreader).toString();
      } else {
         byte[] var1 = new byte[65536];
         boolean var6 = this.bit();
         this.substringkeep.reserve();

         while(true) {
            int var5;
            while(!var6) {
               while(true) {
                  var5 = this.substringhuff.read(this.bitreader);
                  if(var5 == 256) {
                     if(!this.bit()) {
                        if(var2 == 0) {
                           return "";
                        }

                        Kim var7 = new Kim(var1, var2);
                        this.stringkeep.register(var7);
                        this.substringkeep.registerMany(var7);
                        return var7.toString();
                     }

                     var6 = true;
                  } else {
                     var1[var2] = (byte)var5;
                     var5 = var2 + 1;
                     var2 = var5;
                     if(var3 != -1) {
                        this.substringkeep.registerOne(new Kim(var1, var3, var4 + 1));
                        var3 = -1;
                        var2 = var5;
                     }
                  }
               }
            }

            var5 = ((Kim)this.getAndTick(this.substringkeep, this.bitreader)).copy(var1, var2);
            if(var3 != -1) {
               this.substringkeep.registerOne(new Kim(var1, var3, var4 + 1));
            }

            var4 = var5;
            var6 = this.bit();
            var3 = var2;
            var2 = var5;
         }
      }
   }

   private Object readValue() throws JSONException {
      byte var2 = 4;
      switch(this.read(2)) {
      case 0:
         if(this.bit()) {
            if(!this.bit()) {
               var2 = 7;
            } else {
               var2 = 14;
            }
         }

         return new Integer(this.read(var2));
      case 1:
         byte[] var1 = new byte[256];
         int var6 = 0;

         while(true) {
            int var3 = this.read(4);
            if(var3 == endOfNumber) {
               Object var5;
               try {
                  var5 = JSONObject.stringToValue(new String(var1, 0, var6, "US-ASCII"));
               } catch (UnsupportedEncodingException var4) {
                  throw new JSONException(var4);
               }

               this.values.register(var5);
               return var5;
            }

            var1[var6] = bcd[var3];
            ++var6;
         }
      case 2:
         return this.getAndTick(this.values, this.bitreader);
      case 3:
         return this.readJSON();
      default:
         throw new JSONException("Impossible.");
      }
   }

   public boolean pad(int var1) throws JSONException {
      try {
         boolean var3 = this.bitreader.pad(var1);
         return var3;
      } catch (Throwable var4) {
         throw new JSONException(var4);
      }
   }

   public Object unzip() throws JSONException {
      this.begin();
      return this.readJSON();
   }
}
