package gnu.mapping;

import gnu.bytecode.Type;
import gnu.expr.Expression;
import gnu.mapping.CallContext;
import gnu.mapping.HasSetter;
import gnu.mapping.LazyPropertyKey;
import gnu.mapping.MethodProc;
import gnu.mapping.Namespace;
import gnu.mapping.ProcedureN;
import gnu.mapping.PropertySet;
import gnu.mapping.Setter;
import gnu.mapping.Setter0;
import gnu.mapping.Setter1;
import gnu.mapping.Symbol;
import gnu.mapping.WrongArguments;

public abstract class Procedure extends PropertySet {

   public static final LazyPropertyKey compilerKey = new LazyPropertyKey("compiler");
   private static final Symbol setterKey = Namespace.EmptyNamespace.getSymbol("setter");
   private static final String sourceLocationKey = "source-location";
   public static final Symbol validateApplyKey = Namespace.EmptyNamespace.getSymbol("validate-apply");


   public Procedure() {
   }

   public Procedure(String var1) {
      this.setName(var1);
   }

   public static void apply(Procedure var0, CallContext var1) throws Throwable {
      int var2 = var1.count;
      Object var3;
      if(var1.where == 0 && var2 != 0) {
         var3 = var0.applyN(var1.values);
      } else {
         switch(var2) {
         case 0:
            var3 = var0.apply0();
            break;
         case 1:
            var3 = var0.apply1(var1.getNextArg());
            break;
         case 2:
            var3 = var0.apply2(var1.getNextArg(), var1.getNextArg());
            break;
         case 3:
            var3 = var0.apply3(var1.getNextArg(), var1.getNextArg(), var1.getNextArg());
            break;
         case 4:
            var3 = var0.apply4(var1.getNextArg(), var1.getNextArg(), var1.getNextArg(), var1.getNextArg());
            break;
         default:
            var3 = var0.applyN(var1.getArgs());
         }
      }

      var1.writeValue(var3);
   }

   public static void checkArgCount(Procedure var0, int var1) {
      int var2 = var0.numArgs();
      if(var1 < minArgs(var2) || var2 >= 0 && var1 > maxArgs(var2)) {
         throw new WrongArguments(var0, var1);
      }
   }

   public static int maxArgs(int var0) {
      return var0 >> 12;
   }

   public static int minArgs(int var0) {
      return var0 & 4095;
   }

   public void apply(CallContext var1) throws Throwable {
      apply(this, var1);
   }

   public abstract Object apply0() throws Throwable;

   public abstract Object apply1(Object var1) throws Throwable;

   public abstract Object apply2(Object var1, Object var2) throws Throwable;

   public abstract Object apply3(Object var1, Object var2, Object var3) throws Throwable;

   public abstract Object apply4(Object var1, Object var2, Object var3, Object var4) throws Throwable;

   public abstract Object applyN(Object[] var1) throws Throwable;

   public void check0(CallContext var1) {
      int var2 = this.match0(var1);
      if(var2 != 0) {
         throw MethodProc.matchFailAsException(var2, this, ProcedureN.noArgs);
      }
   }

   public void check1(Object var1, CallContext var2) {
      int var3 = this.match1(var1, var2);
      if(var3 != 0) {
         throw MethodProc.matchFailAsException(var3, this, new Object[]{var1});
      }
   }

   public void check2(Object var1, Object var2, CallContext var3) {
      int var4 = this.match2(var1, var2, var3);
      if(var4 != 0) {
         throw MethodProc.matchFailAsException(var4, this, new Object[]{var1, var2});
      }
   }

   public void check3(Object var1, Object var2, Object var3, CallContext var4) {
      int var5 = this.match3(var1, var2, var3, var4);
      if(var5 != 0) {
         throw MethodProc.matchFailAsException(var5, this, new Object[]{var1, var2, var3});
      }
   }

   public void check4(Object var1, Object var2, Object var3, Object var4, CallContext var5) {
      int var6 = this.match4(var1, var2, var3, var4, var5);
      if(var6 != 0) {
         throw MethodProc.matchFailAsException(var6, this, new Object[]{var1, var2, var3, var4});
      }
   }

   public void checkN(Object[] var1, CallContext var2) {
      int var3 = this.matchN(var1, var2);
      if(var3 != 0) {
         throw MethodProc.matchFailAsException(var3, this, var1);
      }
   }

   public Type getReturnType(Expression[] var1) {
      return Type.objectType;
   }

   public Procedure getSetter() {
      if(!(this instanceof HasSetter)) {
         Object var1 = this.getProperty(setterKey, (Object)null);
         if(var1 instanceof Procedure) {
            return (Procedure)var1;
         } else {
            throw new RuntimeException("procedure \'" + this.getName() + "\' has no setter");
         }
      } else {
         int var2 = this.numArgs();
         return (Procedure)(var2 == 0?new Setter0(this):(var2 == 4097?new Setter1(this):new Setter(this)));
      }
   }

   public String getSourceLocation() {
      Object var1 = this.getProperty("source-location", (Object)null);
      return var1 == null?null:var1.toString();
   }

   public boolean isSideEffectFree() {
      return false;
   }

   public int match0(CallContext var1) {
      int var2 = this.numArgs();
      int var3 = minArgs(var2);
      if(var3 > 0) {
         return -983040 | var3;
      } else if(var2 < 0) {
         return this.matchN(ProcedureN.noArgs, var1);
      } else {
         var1.count = 0;
         var1.where = 0;
         var1.next = 0;
         var1.proc = this;
         return 0;
      }
   }

   public int match1(Object var1, CallContext var2) {
      int var3 = this.numArgs();
      int var4 = minArgs(var3);
      if(var4 > 1) {
         return -983040 | var4;
      } else if(var3 >= 0) {
         var3 = maxArgs(var3);
         if(var3 < 1) {
            return -917504 | var3;
         } else {
            var2.value1 = var1;
            var2.count = 1;
            var2.where = 1;
            var2.next = 0;
            var2.proc = this;
            return 0;
         }
      } else {
         return this.matchN(new Object[]{var1}, var2);
      }
   }

   public int match2(Object var1, Object var2, CallContext var3) {
      int var4 = this.numArgs();
      int var5 = minArgs(var4);
      if(var5 > 2) {
         return -983040 | var5;
      } else if(var4 >= 0) {
         var4 = maxArgs(var4);
         if(var4 < 2) {
            return -917504 | var4;
         } else {
            var3.value1 = var1;
            var3.value2 = var2;
            var3.count = 2;
            var3.where = 33;
            var3.next = 0;
            var3.proc = this;
            return 0;
         }
      } else {
         return this.matchN(new Object[]{var1, var2}, var3);
      }
   }

   public int match3(Object var1, Object var2, Object var3, CallContext var4) {
      int var5 = this.numArgs();
      int var6 = minArgs(var5);
      if(var6 > 3) {
         return -983040 | var6;
      } else if(var5 >= 0) {
         var5 = maxArgs(var5);
         if(var5 < 3) {
            return -917504 | var5;
         } else {
            var4.value1 = var1;
            var4.value2 = var2;
            var4.value3 = var3;
            var4.count = 3;
            var4.where = 801;
            var4.next = 0;
            var4.proc = this;
            return 0;
         }
      } else {
         return this.matchN(new Object[]{var1, var2, var3}, var4);
      }
   }

   public int match4(Object var1, Object var2, Object var3, Object var4, CallContext var5) {
      int var6 = this.numArgs();
      int var7 = minArgs(var6);
      if(var7 > 4) {
         return -983040 | var7;
      } else if(var6 >= 0) {
         var6 = maxArgs(var6);
         if(var6 < 4) {
            return -917504 | var6;
         } else {
            var5.value1 = var1;
            var5.value2 = var2;
            var5.value3 = var3;
            var5.value4 = var4;
            var5.count = 4;
            var5.where = 17185;
            var5.next = 0;
            var5.proc = this;
            return 0;
         }
      } else {
         return this.matchN(new Object[]{var1, var2, var3, var4}, var5);
      }
   }

   public int matchN(Object[] var1, CallContext var2) {
      int var3 = this.numArgs();
      int var4 = minArgs(var3);
      if(var1.length < var4) {
         return -983040 | var4;
      } else {
         if(var3 >= 0) {
            switch(var1.length) {
            case 0:
               return this.match0(var2);
            case 1:
               return this.match1(var1[0], var2);
            case 2:
               return this.match2(var1[0], var1[1], var2);
            case 3:
               return this.match3(var1[0], var1[1], var1[2], var2);
            case 4:
               return this.match4(var1[0], var1[1], var1[2], var1[3], var2);
            default:
               var3 = maxArgs(var3);
               if(var1.length > var3) {
                  return -917504 | var3;
               }
            }
         }

         var2.values = var1;
         var2.count = var1.length;
         var2.where = 0;
         var2.next = 0;
         var2.proc = this;
         return 0;
      }
   }

   public final int maxArgs() {
      return maxArgs(this.numArgs());
   }

   public final int minArgs() {
      return minArgs(this.numArgs());
   }

   public int numArgs() {
      return -4096;
   }

   public void set0(Object var1) throws Throwable {
      this.getSetter().apply1(var1);
   }

   public void set1(Object var1, Object var2) throws Throwable {
      this.getSetter().apply2(var1, var2);
   }

   public void setN(Object[] var1) throws Throwable {
      this.getSetter().applyN(var1);
   }

   public void setSetter(Procedure var1) {
      if(this instanceof HasSetter) {
         throw new RuntimeException("procedure \'" + this.getName() + "\' has builtin setter - cannot be modified");
      } else {
         this.setProperty(setterKey, var1);
      }
   }

   public void setSourceLocation(String var1, int var2) {
      this.setProperty("source-location", var1 + ":" + var2);
   }

   public String toString() {
      StringBuffer var3 = new StringBuffer();
      var3.append("#<procedure ");
      String var2 = this.getName();
      String var1 = var2;
      if(var2 == null) {
         var1 = this.getSourceLocation();
      }

      var2 = var1;
      if(var1 == null) {
         var2 = this.getClass().getName();
      }

      var3.append(var2);
      var3.append('>');
      return var3.toString();
   }
}
