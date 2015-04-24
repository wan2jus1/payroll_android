package gnu.kawa.functions;

import gnu.bytecode.Type;
import gnu.expr.Language;
import gnu.kawa.reflect.Invoke;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.WrongArguments;
import gnu.mapping.WrongType;
import java.lang.reflect.Array;
import java.util.List;

public class ApplyToArgs extends ProcedureN {

   Language language;


   public ApplyToArgs(String var1, Language var2) {
      super(var1);
      this.language = var2;
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompilationHelpers:validateApplyToArgs");
   }

   public Object applyN(Object[] var1) throws Throwable {
      Object var2 = var1[0];
      if(var2 instanceof Procedure) {
         Object[] var3 = new Object[var1.length - 1];
         System.arraycopy(var1, 1, var3, 0, var3.length);
         return ((Procedure)var2).applyN(var3);
      } else if(!(var2 instanceof Type) && !(var2 instanceof Class)) {
         if(var2 instanceof List) {
            if(var1.length != 2) {
               throw new WrongArguments(this, var1.length);
            } else {
               int var4 = ((Number)var1[1]).intValue();
               return ((List)var2).get(var4);
            }
         } else if(var2.getClass().isArray()) {
            if(var1.length != 2) {
               throw new WrongArguments(this, var1.length);
            } else {
               return Array.get(var2, ((Number)var1[1]).intValue());
            }
         } else {
            throw new WrongType(this, 0, var2, "procedure");
         }
      } else {
         return Invoke.make.applyN(var1);
      }
   }

   public void check1(Object var1, CallContext var2) {
      if(var1 instanceof Procedure) {
         ((Procedure)var1).check0(var2);
      } else {
         super.check1(var1, var2);
      }
   }

   public void check2(Object var1, Object var2, CallContext var3) {
      if(var1 instanceof Procedure) {
         ((Procedure)var1).check1(var2, var3);
      } else {
         super.check2(var1, var2, var3);
      }
   }

   public void check3(Object var1, Object var2, Object var3, CallContext var4) {
      if(var1 instanceof Procedure) {
         ((Procedure)var1).check2(var2, var3, var4);
      } else {
         super.check3(var1, var2, var3, var4);
      }
   }

   public void check4(Object var1, Object var2, Object var3, Object var4, CallContext var5) {
      if(var1 instanceof Procedure) {
         ((Procedure)var1).check3(var2, var3, var4, var5);
      } else {
         super.check4(var1, var2, var3, var4, var5);
      }
   }

   public void checkN(Object[] var1, CallContext var2) {
      int var5 = this.matchN(var1, var2);
      if(var5 != 0) {
         Object var4 = this;
         Object[] var6 = var1;
         if(var1.length > 0) {
            var4 = this;
            var6 = var1;
            if(var1[0] instanceof Procedure) {
               var4 = (Procedure)var1[0];
               var6 = new Object[var1.length - 1];
               System.arraycopy(var1, 1, var6, 0, var6.length);
            }
         }

         throw MethodProc.matchFailAsException(var5, (Procedure)var4, var6);
      }
   }

   public int match1(Object var1, CallContext var2) {
      return var1 instanceof Procedure?((Procedure)var1).match0(var2):super.match1(var1, var2);
   }

   public int match2(Object var1, Object var2, CallContext var3) {
      return var1 instanceof Procedure?((Procedure)var1).match1(var2, var3):super.match2(var1, var2, var3);
   }

   public int match3(Object var1, Object var2, Object var3, CallContext var4) {
      return var1 instanceof Procedure?((Procedure)var1).match2(var2, var3, var4):super.match3(var1, var2, var3, var4);
   }

   public int match4(Object var1, Object var2, Object var3, Object var4, CallContext var5) {
      return var1 instanceof Procedure?((Procedure)var1).match3(var2, var3, var4, var5):super.match4(var1, var2, var3, var4, var5);
   }

   public int matchN(Object[] var1, CallContext var2) {
      int var5 = var1.length;
      if(var5 > 0 && var1[0] instanceof Procedure) {
         Procedure var3 = (Procedure)var1[0];
         switch(var5) {
         case 1:
            return var3.match0(var2);
         case 2:
            return var3.match1(var1[1], var2);
         case 3:
            return var3.match2(var1[1], var1[2], var2);
         case 4:
            return var3.match3(var1[1], var1[2], var1[3], var2);
         case 5:
            return var3.match4(var1[1], var1[2], var1[3], var1[4], var2);
         default:
            Object[] var4 = new Object[var5 - 1];
            System.arraycopy(var1, 1, var4, 0, var5 - 1);
            return var3.matchN(var4, var2);
         }
      } else {
         return super.matchN(var1, var2);
      }
   }
}
