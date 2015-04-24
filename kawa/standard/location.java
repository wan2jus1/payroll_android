package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.expr.ApplyExp;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ReferenceExp;
import gnu.kawa.functions.ApplyToArgs;
import gnu.kawa.reflect.Invoke;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Location;
import gnu.mapping.LocationProc;
import gnu.mapping.ProcLocation;
import gnu.mapping.Procedure;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class location extends Syntax {

   public static final location location = new location();
   private static ClassType thisType;


   static {
      location.setName("location");
      thisType = ClassType.make("kawa.standard.location");
   }

   public static Procedure makeLocationProc(Location var0) {
      return new LocationProc(var0);
   }

   public static Location makeProcLocation$V(Procedure var0, Object[] var1) {
      int var3 = var1.length;
      if(var0 instanceof ApplyToArgs && var3 > 0 && var1[0] instanceof Procedure) {
         var0 = (Procedure)var1[0];
         if(var0 instanceof LocationProc && var3 == 1) {
            return ((LocationProc)var0).getLocation();
         } else {
            Object[] var2 = new Object[var3 - 1];
            System.arraycopy(var1, 1, var2, 0, var2.length);
            return new ProcLocation(var0, var2);
         }
      } else {
         return (Location)(var0 instanceof LocationProc && var3 == 0?((LocationProc)var0).getLocation():new ProcLocation(var0, var1));
      }
   }

   public static Expression rewrite(Expression var0, Translator var1) {
      if(var0 instanceof ReferenceExp) {
         ReferenceExp var4 = (ReferenceExp)var0;
         var4.setDontDereference(true);
         Declaration var2 = var4.getBinding();
         if(var2 != null) {
            var2.maybeIndirectBinding(var1);
            Declaration var6 = Declaration.followAliases(var2);
            var6.setCanRead(true);
            var6.setCanWrite(true);
         }

         return var4;
      } else if(var0 instanceof ApplyExp) {
         ApplyExp var3 = (ApplyExp)var0;
         Expression[] var5 = new Expression[var3.getArgs().length + 1];
         var5[0] = var3.getFunction();
         System.arraycopy(var3.getArgs(), 0, var5, 1, var5.length - 1);
         return Invoke.makeInvokeStatic(thisType, "makeProcLocation", var5);
      } else {
         return var1.syntaxError("invalid argument to location");
      }
   }

   public Expression rewrite(Object var1, Translator var2) {
      if(!(var1 instanceof Pair)) {
         return var2.syntaxError("missing argument to location");
      } else {
         Pair var4 = (Pair)var1;
         if(var4.getCdr() != LList.Empty) {
            return var2.syntaxError("extra arguments to location");
         } else {
            location var3 = location;
            Expression var5 = rewrite((Expression)var2.rewrite(var4.getCar()), var2);
            return Invoke.makeInvokeStatic(thisType, "makeLocationProc", new Expression[]{var5});
         }
      }
   }
}
