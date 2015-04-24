package gnu.expr;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleMethod;
import gnu.mapping.CallContext;
import gnu.mapping.ProcedureN;

public abstract class ModuleWithContext extends ModuleBody {

   public Object apply0(ModuleMethod var1) throws Throwable {
      CallContext var2 = CallContext.getInstance();
      var1.check0(var2);
      return var2.runUntilValue();
   }

   public Object apply1(ModuleMethod var1, Object var2) throws Throwable {
      CallContext var3 = CallContext.getInstance();
      var1.check1(var2, var3);
      return var3.runUntilValue();
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) throws Throwable {
      CallContext var4 = CallContext.getInstance();
      var1.check2(var2, var3, var4);
      return var4.runUntilValue();
   }

   public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) throws Throwable {
      CallContext var5 = CallContext.getInstance();
      var1.check3(var2, var3, var4, var5);
      return var5.runUntilValue();
   }

   public Object apply4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5) throws Throwable {
      CallContext var6 = CallContext.getInstance();
      var1.check4(var2, var3, var4, var5, var6);
      return var6.runUntilValue();
   }

   public Object applyN(ModuleMethod var1, Object[] var2) throws Throwable {
      CallContext var3 = CallContext.getInstance();
      var1.checkN(var2, var3);
      return var3.runUntilValue();
   }

   public int match0(ModuleMethod var1, CallContext var2) {
      int var3 = var1.numArgs();
      int var4 = var3 & 4095;
      if(var4 > 0) {
         return -983040 | var4;
      } else {
         var2.count = 0;
         var2.where = 0;
         if(var3 < 0) {
            return this.matchN(var1, ProcedureN.noArgs, var2);
         } else {
            var2.next = 0;
            var2.proc = this;
            var2.pc = var1.selector;
            return 0;
         }
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      int var4 = var1.numArgs();
      int var5 = var4 & 4095;
      if(var5 > 1) {
         return -983040 | var5;
      } else if(var4 >= 0) {
         var4 >>= 12;
         if(var4 < 1) {
            return -917504 | var4;
         } else {
            var3.value1 = var2;
            var3.count = 1;
            var3.where = 1;
            var3.next = 0;
            var3.proc = this;
            var3.pc = var1.selector;
            return 0;
         }
      } else {
         var3.where = 0;
         return this.matchN(var1, new Object[]{var2}, var3);
      }
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      int var5 = var1.numArgs();
      int var6 = var5 & 4095;
      if(var6 > 2) {
         return -983040 | var6;
      } else if(var5 >= 0) {
         var5 >>= 12;
         if(var5 < 2) {
            return -917504 | var5;
         } else {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.count = 2;
            var4.where = 33;
            var4.next = 0;
            var4.proc = this;
            var4.pc = var1.selector;
            return 0;
         }
      } else {
         var4.where = 0;
         return this.matchN(var1, new Object[]{var2, var3}, var4);
      }
   }

   public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
      int var6 = var1.numArgs();
      int var7 = var6 & 4095;
      if(var7 > 3) {
         return -983040 | var7;
      } else if(var6 >= 0) {
         var6 >>= 12;
         if(var6 < 3) {
            return -917504 | var6;
         } else {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.count = 3;
            var5.where = 801;
            var5.next = 0;
            var5.proc = this;
            var5.pc = var1.selector;
            return 0;
         }
      } else {
         var5.where = 0;
         return this.matchN(var1, new Object[]{var2, var3, var4}, var5);
      }
   }

   public int match4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5, CallContext var6) {
      int var7 = var1.numArgs();
      int var8 = var7 & 4095;
      if(var8 > 4) {
         return -983040 | var8;
      } else if(var7 >= 0) {
         var7 >>= 12;
         if(var7 < 4) {
            return -917504 | var7;
         } else {
            var6.value1 = var2;
            var6.value2 = var3;
            var6.value3 = var4;
            var6.value4 = var5;
            var6.count = 4;
            var6.where = 17185;
            var6.next = 0;
            var6.proc = this;
            var6.pc = var1.selector;
            return 0;
         }
      } else {
         var6.where = 0;
         return this.matchN(var1, new Object[]{var2, var3, var4, var5}, var6);
      }
   }

   public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
      int var4 = var1.numArgs();
      int var5 = var4 & 4095;
      if(var2.length < var5) {
         return -983040 | var5;
      } else {
         if(var4 >= 0) {
            switch(var2.length) {
            case 0:
               return this.match0(var1, var3);
            case 1:
               return this.match1(var1, var2[0], var3);
            case 2:
               return this.match2(var1, var2[0], var2[1], var3);
            case 3:
               return this.match3(var1, var2[0], var2[1], var2[2], var3);
            case 4:
               return this.match4(var1, var2[0], var2[1], var2[2], var2[3], var3);
            default:
               var4 >>= 12;
               if(var2.length > var4) {
                  return -917504 | var4;
               }
            }
         }

         var3.values = var2;
         var3.count = var2.length;
         var3.where = 0;
         var3.next = 0;
         var3.proc = this;
         var3.pc = var1.selector;
         return 0;
      }
   }
}
