package kawa.standard;

import gnu.kawa.functions.ObjectFormat;
import gnu.mapping.Environment;
import gnu.mapping.Location;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.Symbol;
import gnu.math.IntNum;
import java.io.IOException;
import java.io.PrintWriter;

public class TracedProcedure extends ProcedureN {

   static Symbol curIndentSym = Symbol.makeUninterned("current-indentation");
   static int indentationStep = 2;
   boolean enabled;
   public Procedure proc;


   public TracedProcedure(Procedure var1, boolean var2) {
      this.proc = var1;
      this.enabled = var2;
      String var3 = var1.getName();
      if(var3 != null) {
         this.setName(var3);
      }

   }

   public static Procedure doTrace(Procedure var0, boolean var1) {
      if(var0 instanceof TracedProcedure) {
         ((TracedProcedure)var0).enabled = var1;
         return var0;
      } else {
         return new TracedProcedure(var0, var1);
      }
   }

   static void indent(int var0, PrintWriter var1) {
      while(true) {
         --var0;
         if(var0 < 0) {
            return;
         }

         var1.print(' ');
      }
   }

   static void put(Object var0, PrintWriter var1) {
      try {
         if(!ObjectFormat.format(var0, var1, 50, true)) {
            var1.print("...");
         }

      } catch (IOException var2) {
         var1.print("<caught ");
         var1.print(var2);
         var1.print('>');
      }
   }

   public Object applyN(Object[] var1) throws Throwable {
      if(this.enabled) {
         Location var4 = Environment.getCurrent().getLocation(curIndentSym);
         Object var2 = var4.get((Object)null);
         int var6;
         if(!(var2 instanceof IntNum)) {
            var6 = 0;
            var4.set(IntNum.zero());
         } else {
            var6 = ((IntNum)var2).intValue();
         }

         OutPort var5 = OutPort.errDefault();
         String var3 = this.getName();
         String var14 = var3;
         if(var3 == null) {
            var14 = "??";
         }

         indent(var6, var5);
         var5.print("call to ");
         var5.print(var14);
         int var8 = var1.length;
         var5.print(" (");

         for(int var7 = 0; var7 < var8; ++var7) {
            if(var7 > 0) {
               var5.print(' ');
            }

            put(var1[var7], var5);
         }

         var5.println(")");
         Object var15 = var4.setWithSave(IntNum.make(indentationStep + var6));

         Object var13;
         try {
            var13 = this.proc.applyN(var1);
         } catch (RuntimeException var11) {
            indent(var6, var5);
            var5.println("procedure " + var14 + " throws exception " + var11);
            throw var11;
         } finally {
            var4.setRestore(var15);
         }

         indent(var6, var5);
         var5.print("return from ");
         var5.print(var14);
         var5.print(" => ");
         put(var13, var5);
         var5.println();
         return var13;
      } else {
         return this.proc.applyN(var1);
      }
   }

   public void print(PrintWriter var1) {
      var1.print("#<procedure ");
      String var2 = this.getName();
      if(var2 == null) {
         var1.print("<unnamed>");
      } else {
         var1.print(var2);
      }

      if(this.enabled) {
         var2 = ", traced>";
      } else {
         var2 = ">";
      }

      var1.print(var2);
   }
}
