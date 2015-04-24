package org.json.zip;

import java.util.HashMap;
import org.json.Kim;
import org.json.zip.JSONzip;
import org.json.zip.Keep;
import org.json.zip.PostMortem;

class MapKeep extends Keep {

   private Object[] list;
   private HashMap map;


   public MapKeep(int var1) {
      super(var1);
      this.list = new Object[this.capacity];
      this.map = new HashMap(this.capacity);
   }

   private void compact() {
      int var2 = 0;

      int var3;
      for(var3 = 0; var2 < this.capacity; ++var2) {
         Object var1 = this.list[var2];
         long var4 = age(this.uses[var2]);
         if(var4 > 0L) {
            this.uses[var3] = var4;
            this.list[var3] = var1;
            this.map.put(var1, new Integer(var3));
            ++var3;
         } else {
            this.map.remove(var1);
         }
      }

      if(var3 < this.capacity) {
         this.length = var3;
      } else {
         this.map.clear();
         this.length = 0;
      }

      this.power = 0;
   }

   public int find(Object var1) {
      var1 = this.map.get(var1);
      return var1 instanceof Integer?((Integer)var1).intValue():-1;
   }

   public boolean postMortem(PostMortem var1) {
      MapKeep var4 = (MapKeep)var1;
      if(this.length != var4.length) {
         JSONzip.log(this.length + " <> " + var4.length);
         return false;
      } else {
         for(int var5 = 0; var5 < this.length; ++var5) {
            boolean var6;
            if(this.list[var5] instanceof Kim) {
               var6 = ((Kim)this.list[var5]).equals(var4.list[var5]);
            } else {
               Object var2 = this.list[var5];
               Object var3 = var4.list[var5];
               Object var7 = var2;
               if(var2 instanceof Number) {
                  var7 = var2.toString();
               }

               var2 = var3;
               if(var3 instanceof Number) {
                  var2 = var3.toString();
               }

               var6 = var7.equals(var2);
            }

            if(!var6) {
               JSONzip.log("\n[" + var5 + "]\n " + this.list[var5] + "\n " + var4.list[var5] + "\n " + this.uses[var5] + "\n " + var4.uses[var5]);
               return false;
            }
         }

         return true;
      }
   }

   public void register(Object var1) {
      if(this.length >= this.capacity) {
         this.compact();
      }

      this.list[this.length] = var1;
      this.map.put(var1, new Integer(this.length));
      this.uses[this.length] = 1L;
      ++this.length;
   }

   public Object value(int var1) {
      return this.list[var1];
   }
}
