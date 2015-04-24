package gnu.mapping;

import gnu.mapping.Entry;
import gnu.mapping.Symbol;
import java.lang.ref.WeakReference;

public class Table2D {

   private static Table2D instance = new Table2D();
   int log2Size;
   int mask;
   int num_bindings;
   Entry[] table;


   public Table2D() {
      this(64);
   }

   public Table2D(int var1) {
      for(this.log2Size = 4; var1 > 1 << this.log2Size; ++this.log2Size) {
         ;
      }

      var1 = 1 << this.log2Size;
      this.table = new Entry[var1];
      this.mask = var1 - 1;
   }

   public static final Table2D getInstance() {
      return instance;
   }

   public Object get(Object var1, Object var2, Object var3) {
      Entry var4 = this.lookup(var1, var2, System.identityHashCode(var1), System.identityHashCode(var2), false);
      return var4 != null && var4.value != var4?var4.value:var3;
   }

   public boolean isBound(Object var1, Object var2) {
      boolean var4 = false;
      Entry var5 = this.lookup(var1, var2, System.identityHashCode(var1), System.identityHashCode(var2), false);
      boolean var3 = var4;
      if(var5 != null) {
         var3 = var4;
         if(var5.value != var5) {
            var3 = true;
         }
      }

      return var3;
   }

   protected Entry lookup(Object var1, Object var2, int var3, int var4, boolean var5) {
      var4 = (var3 ^ var4) & this.mask;
      Entry var7 = null;
      Entry var10 = this.table[var4];

      Entry var6;
      Entry var12;
      for(var6 = var10; var6 != null; var6 = var12) {
         Object var9 = var6.key1;
         Object var11 = var6.key2;
         boolean var13 = false;
         Object var8 = var9;
         if(var9 instanceof WeakReference) {
            var8 = ((WeakReference)var9).get();
            if(var8 == null) {
               var13 = true;
            } else {
               var13 = false;
            }
         }

         var9 = var11;
         if(var11 instanceof WeakReference) {
            var9 = ((WeakReference)var11).get();
            if(var9 == null) {
               ;
            }

            var13 = true;
         }

         var12 = var6.chain;
         if(var13) {
            if(var7 == null) {
               this.table[var4] = var12;
            } else {
               var7.chain = var12;
            }

            --this.num_bindings;
            var6.value = var6;
         } else {
            if(var8 == var1 && var9 == var2) {
               return var6;
            }

            var7 = var6;
         }
      }

      if(var5) {
         var6 = new Entry();
         var1 = this.wrapReference(var1);
         var2 = this.wrapReference(var2);
         var6.key1 = var1;
         var6.key2 = var2;
         ++this.num_bindings;
         var6.chain = var10;
         this.table[var4] = var6;
         var6.value = var6;
         return var6;
      } else {
         return null;
      }
   }

   public Object put(Object var1, Object var2, Object var3) {
      Entry var4 = this.lookup(var1, var2, System.identityHashCode(var1), System.identityHashCode(var2), true);
      var2 = var4.getValue();
      var4.value = var3;
      return var2;
   }

   void rehash() {
      Entry[] var5 = this.table;
      int var7 = var5.length;
      int var8 = var7 * 2;
      Entry[] var6 = new Entry[var8];
      int var9 = var8 - 1;
      this.num_bindings = 0;

      while(true) {
         var8 = var7 - 1;
         if(var8 < 0) {
            this.table = var6;
            ++this.log2Size;
            this.mask = var9;
            return;
         }

         Entry var1 = var5[var8];

         while(true) {
            var7 = var8;
            if(var1 == null) {
               break;
            }

            Object var3 = var1.key1;
            Object var4 = var1.key2;
            boolean var11 = false;
            Object var2 = var3;
            if(var3 instanceof WeakReference) {
               var2 = ((WeakReference)var3).get();
               if(var2 == null) {
                  var11 = true;
               } else {
                  var11 = false;
               }
            }

            var3 = var4;
            if(var4 instanceof WeakReference) {
               var3 = ((WeakReference)var4).get();
               if(var3 == null) {
                  var11 = true;
               } else {
                  var11 = false;
               }
            }

            Entry var10 = var1.chain;
            if(var11) {
               var1.value = var1;
            } else {
               var7 = (System.identityHashCode(var2) ^ System.identityHashCode(var3)) & var9;
               var1.chain = var6[var7];
               var6[var7] = var1;
               ++this.num_bindings;
            }

            var1 = var10;
         }
      }
   }

   public Object remove(Object var1, Object var2) {
      return this.remove(var1, var2, System.identityHashCode(var1) ^ System.identityHashCode(var2));
   }

   public Object remove(Object var1, Object var2, int var3) {
      return this.remove(var1, var2, var3);
   }

   public Object remove(Object var1, Object var2, int var3, int var4) {
      int var11 = (var3 ^ var4) & this.mask;
      Entry var6 = null;

      Entry var14;
      for(Entry var5 = this.table[var11]; var5 != null; var5 = var14) {
         Object var8 = var5.key1;
         Object var9 = var5.key2;
         boolean var12 = false;
         Object var7 = var8;
         if(var8 instanceof WeakReference) {
            var7 = ((WeakReference)var8).get();
            if(var7 == null) {
               var12 = true;
            } else {
               var12 = false;
            }
         }

         var8 = var9;
         if(var9 instanceof WeakReference) {
            var8 = ((WeakReference)var9).get();
            if(var8 == null) {
               var12 = true;
            } else {
               var12 = false;
            }
         }

         var14 = var5.chain;
         Object var10 = var5.value;
         boolean var13;
         if(var7 == var1 && var8 == var2) {
            var13 = true;
         } else {
            var13 = false;
         }

         if(!var12 && !var13) {
            if(var13) {
               return var10;
            }

            var6 = var5;
         } else {
            if(var6 == null) {
               this.table[var11] = var14;
            } else {
               var6.chain = var14;
            }

            --this.num_bindings;
            var5.value = var5;
         }
      }

      return null;
   }

   protected Object wrapReference(Object var1) {
      return var1 != null && !(var1 instanceof Symbol)?new WeakReference(var1):var1;
   }
}
