package gnu.mapping;

import gnu.mapping.Named;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;

public abstract class PropertySet implements Named {

   public static final Symbol nameKey = Namespace.EmptyNamespace.getSymbol("name");
   private Object[] properties;


   public static Object[] setProperty(Object[] var0, Object var1, Object var2) {
      Object[] var4 = var0;
      Object[] var3;
      int var6;
      if(var0 == null) {
         var3 = new Object[10];
         var0 = var3;
         var6 = 0;
      } else {
         int var5 = -1;
         var6 = var0.length;

         while(true) {
            int var7 = var6 - 2;
            if(var7 < 0) {
               var6 = var5;
               var3 = var4;
               if(var5 < 0) {
                  var6 = var4.length;
                  var0 = new Object[var6 * 2];
                  System.arraycopy(var4, 0, var0, 0, var6);
                  var3 = var0;
               }
               break;
            }

            Object var8 = var4[var7];
            if(var8 == var1) {
               Object var10000 = var4[var7 + 1];
               var4[var7 + 1] = var2;
               return var0;
            }

            var6 = var7;
            if(var8 == null) {
               var5 = var7;
               var6 = var7;
            }
         }
      }

      var3[var6] = var1;
      var3[var6 + 1] = var2;
      return var0;
   }

   public String getName() {
      Object var1 = this.getProperty(nameKey, (Object)null);
      return var1 == null?null:(var1 instanceof Symbol?((Symbol)var1).getName():var1.toString());
   }

   public Object getProperty(Object var1, Object var2) {
      Object var3 = var2;
      if(this.properties != null) {
         int var4 = this.properties.length;

         while(true) {
            int var5 = var4 - 2;
            var3 = var2;
            if(var5 < 0) {
               break;
            }

            var4 = var5;
            if(this.properties[var5] == var1) {
               var3 = this.properties[var5 + 1];
               break;
            }
         }
      }

      return var3;
   }

   public Object getSymbol() {
      return this.getProperty(nameKey, (Object)null);
   }

   public Object removeProperty(Object var1) {
      Object[] var2 = this.properties;
      if(var2 == null) {
         return null;
      } else {
         int var3 = var2.length;

         int var4;
         do {
            var4 = var3 - 2;
            if(var4 < 0) {
               return null;
            }

            var3 = var4;
         } while(var2[var4] != var1);

         var1 = var2[var4 + 1];
         var2[var4] = null;
         var2[var4 + 1] = null;
         return var1;
      }
   }

   public final void setName(String var1) {
      this.setProperty(nameKey, var1);
   }

   public void setProperty(Object var1, Object var2) {
      synchronized(this){}

      try {
         this.properties = setProperty(this.properties, var1, var2);
      } finally {
         ;
      }

   }

   public final void setSymbol(Object var1) {
      this.setProperty(nameKey, var1);
   }
}
