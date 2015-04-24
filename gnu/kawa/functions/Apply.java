package gnu.kawa.functions;

import gnu.kawa.functions.ApplyToArgs;
import gnu.lists.Pair;
import gnu.lists.Sequence;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.WrongArguments;
import gnu.mapping.WrongType;

public class Apply extends ProcedureN {

   ApplyToArgs applyToArgs;


   public Apply(String var1, ApplyToArgs var2) {
      super(var1);
      this.applyToArgs = var2;
   }

   public static Object[] getArguments(Object[] var0, int var1, Procedure var2) {
      int var7 = var0.length;
      if(var7 < var1 + 1) {
         throw new WrongArguments("apply", 2, "(apply proc [args] args) [count:" + var7 + " skip:" + var1 + "]");
      } else {
         Object var3 = var0[var7 - 1];
         int var5;
         if(var3 instanceof Object[]) {
            Object[] var4 = (Object[])((Object[])var3);
            if(var7 == 2) {
               return var4;
            }

            var5 = var4.length;
         } else if(var3 instanceof Sequence) {
            var5 = ((Sequence)var3).size();
         } else {
            var5 = -1;
         }

         if(var5 < 0) {
            throw new WrongType(var2, var7, var3, "sequence or array");
         } else {
            Object[] var11 = new Object[var5 + (var7 - var1 - 1)];

            int var6;
            for(var6 = 0; var6 < var7 - var1 - 1; ++var6) {
               var11[var6] = var0[var6 + var1];
            }

            var1 = var6;
            Object var8 = var3;
            var7 = var5;
            if(var3 instanceof Object[]) {
               System.arraycopy((Object[])((Object[])var3), 0, var11, var6, var5);
            } else {
               while(var8 instanceof Pair) {
                  Pair var9 = (Pair)var8;
                  var11[var1] = var9.getCar();
                  var8 = var9.getCdr();
                  --var7;
                  ++var1;
               }

               if(var7 > 0) {
                  Sequence var10 = (Sequence)var8;

                  for(var5 = 0; var5 < var7; ++var1) {
                     var11[var1] = var10.get(var5);
                     ++var5;
                  }
               }
            }

            return var11;
         }
      }
   }

   public void apply(CallContext var1) throws Throwable {
      Object[] var2 = var1.getArgs();
      this.applyToArgs.checkN(getArguments(var2, 0, this), var1);
   }

   public Object applyN(Object[] var1) throws Throwable {
      return this.applyToArgs.applyN(getArguments(var1, 0, this));
   }
}
