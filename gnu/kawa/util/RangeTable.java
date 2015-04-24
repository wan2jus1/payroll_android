package gnu.kawa.util;

import java.util.Hashtable;

public class RangeTable implements Cloneable {

   Hashtable hash = new Hashtable(200);
   Object[] index = new Object[128];


   public Object clone() {
      return this.copy();
   }

   public RangeTable copy() {
      RangeTable var1 = new RangeTable();
      var1.index = (Object[])((Object[])this.index.clone());
      var1.hash = (Hashtable)this.hash.clone();
      return var1;
   }

   public Object lookup(int var1, Object var2) {
      return (var1 & 127) == var1?this.index[var1]:this.hash.get(new Integer(var1));
   }

   public void remove(int var1) {
      this.remove(var1, var1);
   }

   public void remove(int var1, int var2) {
      if(var1 <= var2) {
         while(true) {
            if((var1 & 127) == var1) {
               this.index[var1] = null;
            } else {
               this.hash.remove(new Integer(var1));
            }

            if(var1 == var2) {
               break;
            }

            ++var1;
         }
      }

   }

   public void set(int var1, int var2, Object var3) {
      if(var1 <= var2) {
         while(true) {
            if((var1 & 127) == var1) {
               this.index[var1] = var3;
            } else {
               this.hash.put(new Integer(var1), var3);
            }

            if(var1 == var2) {
               break;
            }

            ++var1;
         }
      }

   }

   public void set(int var1, Object var2) {
      this.set(var1, var1, var2);
   }
}
