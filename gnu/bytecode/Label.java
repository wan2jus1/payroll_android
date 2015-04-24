package gnu.bytecode;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import java.util.ArrayList;
import java.util.Iterator;

public class Label {

   int first_fixup;
   Type[] localTypes;
   boolean needsStackMapEntry;
   int position;
   Type[] stackTypes;
   private Object[] typeChangeListeners;


   public Label() {
      this(-1);
   }

   public Label(int var1) {
      this.position = var1;
   }

   public Label(CodeAttr var1) {
      this(-1);
   }

   private void mergeLocalType(int var1, Type var2) {
      Type var3 = this.localTypes[var1];
      var2 = this.mergeTypes(var3, var2);
      this.localTypes[var1] = var2;
      if(var2 != var3) {
         this.notifyTypeChangeListeners(var1, var2);
      }

   }

   private void notifyTypeChangeListeners(int var1, Type var2) {
      Object[] var3 = this.typeChangeListeners;
      if(var3 != null && var3.length > var1) {
         Object var4 = var3[var1];
         if(var4 != null) {
            if(var4 instanceof Label) {
               ((Label)var4).mergeLocalType(var1, var2);
            } else {
               Iterator var5 = ((ArrayList)var4).iterator();

               while(var5.hasNext()) {
                  ((Label)var5.next()).mergeLocalType(var1, var2);
               }
            }

            if(var2 == null) {
               var3[var1] = null;
               return;
            }
         }
      }

   }

   void addTypeChangeListener(int var1, Label var2) {
      Object[] var4 = this.typeChangeListeners;
      Object[] var3;
      if(var4 == null) {
         var3 = new Object[var1 + 10];
         this.typeChangeListeners = var3;
      } else {
         var3 = var4;
         if(var4.length <= var1) {
            var3 = new Object[var1 + 10];
            System.arraycopy(this.typeChangeListeners, 0, var3, 0, this.typeChangeListeners.length);
            this.typeChangeListeners = var3;
         }
      }

      Object var5 = var3[var1];
      if(var5 == null) {
         var3[var1] = var2;
      } else {
         ArrayList var6;
         if(var5 instanceof Label) {
            ArrayList var7 = new ArrayList();
            var7.add((Label)var5);
            var3[var1] = var7;
            var6 = var7;
         } else {
            var6 = (ArrayList)var5;
         }

         var6.add(var2);
      }
   }

   void addTypeChangeListeners(CodeAttr var1) {
      if(var1.local_types != null && var1.previousLabel != null) {
         int var3 = var1.local_types.length;

         for(int var2 = 0; var2 < var3; ++var2) {
            if(var1.local_types[var2] != null && (var1.varsSetInCurrentBlock == null || var1.varsSetInCurrentBlock.length <= var2 || !var1.varsSetInCurrentBlock[var2])) {
               var1.previousLabel.addTypeChangeListener(var2, this);
            }
         }
      }

   }

   public void define(CodeAttr var1) {
      if(var1.reachableHere()) {
         this.setTypes((CodeAttr)var1);
      } else if(this.localTypes != null) {
         int var2 = this.localTypes.length;

         while(true) {
            int var3 = var2 - 1;
            if(var3 < 0) {
               break;
            }

            var2 = var3;
            if(this.localTypes[var3] != null) {
               if(var1.locals.used != null) {
                  var2 = var3;
                  if(var1.locals.used[var3] != null) {
                     continue;
                  }
               }

               this.localTypes[var3] = null;
               var2 = var3;
            }
         }
      }

      var1.previousLabel = this;
      var1.varsSetInCurrentBlock = null;
      this.defineRaw(var1);
      if(this.localTypes != null) {
         var1.setTypes(this);
      }

      var1.setReachable(true);
   }

   public void defineRaw(CodeAttr var1) {
      if(this.position >= 0) {
         throw new Error("label definition more than once");
      } else {
         this.position = var1.PC;
         this.first_fixup = var1.fixup_count;
         if(this.first_fixup >= 0) {
            var1.fixupAdd(1, this);
         }

      }
   }

   public final boolean defined() {
      return this.position >= 0;
   }

   Type mergeTypes(Type var1, Type var2) {
      return var1 instanceof PrimType != (var2 instanceof PrimType)?null:Type.lowestCommonSuperType(var1, var2);
   }

   public void setTypes(CodeAttr var1) {
      this.addTypeChangeListeners(var1);
      if(this.stackTypes != null && var1.SP != this.stackTypes.length) {
         throw new InternalError();
      } else {
         Type[] var2 = var1.local_types;
         int var3;
         if(var1.local_types == null) {
            var3 = 0;
         } else {
            var3 = var1.local_types.length;
         }

         this.setTypes(var2, var3, var1.stack_types, var1.SP);
      }
   }

   public void setTypes(Label var1) {
      this.setTypes(var1.localTypes, var1.localTypes.length, var1.stackTypes, var1.stackTypes.length);
   }

   void setTypes(Type[] var1, int var2, Type[] var3, int var4) {
      while(var2 > 0 && var1[var2 - 1] == null) {
         --var2;
      }

      if(this.stackTypes == null) {
         if(var4 == 0) {
            this.stackTypes = Type.typeArray0;
         } else {
            this.stackTypes = new Type[var4];
            System.arraycopy(var3, 0, this.stackTypes, 0, var4);
         }

         if(var2 != 0) {
            this.localTypes = new Type[var2];
            System.arraycopy(var1, 0, this.localTypes, 0, var2);
            return;
         }

         this.localTypes = Type.typeArray0;
      } else {
         if(var4 != this.stackTypes.length) {
            throw new InternalError("inconsistent stack length");
         }

         int var5;
         for(var5 = 0; var5 < var4; ++var5) {
            this.stackTypes[var5] = this.mergeTypes(this.stackTypes[var5], var3[var5]);
         }

         if(var2 < this.localTypes.length) {
            var4 = var2;
         } else {
            var4 = this.localTypes.length;
         }

         for(var5 = 0; var5 < var4; ++var5) {
            this.mergeLocalType(var5, var1[var5]);
         }

         while(var2 < this.localTypes.length) {
            this.localTypes[var2] = null;
            ++var2;
         }
      }

   }
}
