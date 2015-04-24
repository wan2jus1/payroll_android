package gnu.expr;

import gnu.expr.ModuleBody;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Values;
import gnu.mapping.WrongArguments;

public class ModuleMethod extends MethodProc {

   public ModuleBody module;
   protected int numArgs;
   public int selector;


   public ModuleMethod(ModuleBody var1, int var2, Object var3, int var4) {
      this.init(var1, var2, var3, var4);
   }

   public ModuleMethod(ModuleBody var1, int var2, Object var3, int var4, Object var5) {
      this.init(var1, var2, var3, var4);
      this.argTypes = var5;
   }

   public static Object apply0Default(ModuleMethod var0) throws Throwable {
      return var0.module.applyN(var0, Values.noArgs);
   }

   public static Object apply1Default(ModuleMethod var0, Object var1) throws Throwable {
      return var0.module.applyN(var0, new Object[]{var1});
   }

   public static Object apply2Default(ModuleMethod var0, Object var1, Object var2) throws Throwable {
      return var0.module.applyN(var0, new Object[]{var1, var2});
   }

   public static Object apply3Default(ModuleMethod var0, Object var1, Object var2, Object var3) throws Throwable {
      return var0.module.applyN(var0, new Object[]{var1, var2, var3});
   }

   public static Object apply4Default(ModuleMethod var0, Object var1, Object var2, Object var3, Object var4) throws Throwable {
      return var0.module.applyN(var0, new Object[]{var1, var2, var3, var4});
   }

   public static void applyError() {
      throw new Error("internal error - bad selector");
   }

   public static Object applyNDefault(ModuleMethod var0, Object[] var1) throws Throwable {
      int var3 = var1.length;
      int var4 = var0.numArgs();
      ModuleBody var2 = var0.module;
      if(var3 >= (var4 & 4095) && (var4 < 0 || var3 <= var4 >> 12)) {
         switch(var3) {
         case 0:
            return var2.apply0(var0);
         case 1:
            return var2.apply1(var0, var1[0]);
         case 2:
            return var2.apply2(var0, var1[0], var1[1]);
         case 3:
            return var2.apply3(var0, var1[0], var1[1], var1[2]);
         case 4:
            return var2.apply4(var0, var1[0], var1[1], var1[2], var1[3]);
         }
      }

      throw new WrongArguments(var0, var3);
   }

   public void apply(CallContext var1) throws Throwable {
      Object var2;
      switch(var1.pc) {
      case 0:
         var2 = this.apply0();
         break;
      case 1:
         var2 = this.apply1(var1.value1);
         break;
      case 2:
         var2 = this.apply2(var1.value1, var1.value2);
         break;
      case 3:
         var2 = this.apply3(var1.value1, var1.value2, var1.value3);
         break;
      case 4:
         var2 = this.apply4(var1.value1, var1.value2, var1.value3, var1.value4);
         break;
      case 5:
         var2 = this.applyN(var1.values);
         break;
      default:
         throw new Error("internal error - apply " + this);
      }

      var1.writeValue(var2);
   }

   public Object apply0() throws Throwable {
      return this.module.apply0(this);
   }

   public Object apply1(Object var1) throws Throwable {
      return this.module.apply1(this, var1);
   }

   public Object apply2(Object var1, Object var2) throws Throwable {
      try {
         var1 = this.module.apply2(this, var1, var2);
         return var1;
      } catch (Exception var3) {
         throw var3;
      }
   }

   public Object apply3(Object var1, Object var2, Object var3) throws Throwable {
      return this.module.apply3(this, var1, var2, var3);
   }

   public Object apply4(Object var1, Object var2, Object var3, Object var4) throws Throwable {
      return this.module.apply4(this, var1, var2, var3, var4);
   }

   public Object applyN(Object[] var1) throws Throwable {
      return this.module.applyN(this, var1);
   }

   public ModuleMethod init(ModuleBody var1, int var2, Object var3, int var4) {
      this.module = var1;
      this.selector = var2;
      this.numArgs = var4;
      if(var3 != null) {
         this.setSymbol(var3);
      }

      return this;
   }

   public int match0(CallContext var1) {
      var1.count = 0;
      var1.where = 0;
      return this.module.match0(this, var1);
   }

   public int match1(Object var1, CallContext var2) {
      var2.count = 1;
      var2.where = 1;
      return this.module.match1(this, var1, var2);
   }

   public int match2(Object var1, Object var2, CallContext var3) {
      var3.count = 2;
      var3.where = 33;
      return this.module.match2(this, var1, var2, var3);
   }

   public int match3(Object var1, Object var2, Object var3, CallContext var4) {
      var4.count = 3;
      var4.where = 801;
      return this.module.match3(this, var1, var2, var3, var4);
   }

   public int match4(Object var1, Object var2, Object var3, Object var4, CallContext var5) {
      var5.count = 4;
      var5.where = 17185;
      return this.module.match4(this, var1, var2, var3, var4, var5);
   }

   public int matchN(Object[] var1, CallContext var2) {
      var2.count = var1.length;
      var2.where = 0;
      return this.module.matchN(this, var1, var2);
   }

   public int numArgs() {
      return this.numArgs;
   }

   protected void resolveParameterTypes() {
      // $FF: Couldn't be decompiled
   }
}
