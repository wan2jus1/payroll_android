package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.expr.Keyword;
import gnu.mapping.ProcedureN;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongArguments;
import gnu.mapping.WrongType;
import kawa.lang.Record;

public class make extends ProcedureN {

   public Object applyN(Object[] var1) {
      int var5 = var1.length;
      if(var5 == 0) {
         throw new WrongArguments(this, var5);
      } else {
         Object var3 = var1[0];
         Class var2;
         if(var3 instanceof Class) {
            var2 = (Class)var3;
         } else if(var3 instanceof ClassType) {
            var2 = ((ClassType)var3).getReflectClass();
         } else {
            var2 = null;
         }

         if(var2 == null) {
            throw new WrongType(this, 1, var3, "class");
         } else {
            Object var8;
            try {
               var8 = var2.newInstance();
            } catch (Exception var7) {
               throw new WrappedException(var7);
            }

            int var4 = 1;

            while(var4 < var5) {
               int var6 = var4 + 1;
               Keyword var9 = (Keyword)var1[var4];
               var4 = var6 + 1;
               Record.set1(var1[var6], var9.getName(), var8);
            }

            return var8;
         }
      }
   }

   public int numArgs() {
      return -4095;
   }
}
