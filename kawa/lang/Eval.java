package kawa.lang;

import gnu.lists.LList;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure1or2;
import gnu.text.SourceMessages;

public class Eval extends Procedure1or2 {

   public static final Eval eval = new Eval();
   static final String evalFunctionName = "atEvalLevel$";


   static {
      eval.setName("eval");
   }

   public static Object eval(Object var0, Environment var1) throws Throwable {
      CallContext var2 = CallContext.getInstance();
      int var3 = var2.startFromContext();

      try {
         eval(var0, var1, var2);
         var0 = var2.getFromContext(var3);
         return var0;
      } catch (Throwable var4) {
         var2.cleanupFromContext(var3);
         throw var4;
      }
   }

   public static void eval(Object var0, Environment var1, CallContext var2) throws Throwable {
      PairWithPosition var3;
      if(var0 instanceof PairWithPosition) {
         var3 = new PairWithPosition((PairWithPosition)var0, var0, LList.Empty);
      } else {
         var3 = new PairWithPosition(var0, LList.Empty);
         var3.setFile("<eval>");
      }

      evalBody(var3, var1, new SourceMessages(), var2);
   }

   public static Object evalBody(Object var0, Environment var1, SourceMessages var2) throws Throwable {
      CallContext var3 = CallContext.getInstance();
      int var4 = var3.startFromContext();

      try {
         evalBody(var0, var1, var2, var3);
         var0 = var3.getFromContext(var4);
         return var0;
      } catch (Throwable var5) {
         var3.cleanupFromContext(var4);
         throw var5;
      }
   }

   public static void evalBody(Object param0, Environment param1, SourceMessages param2, CallContext param3) throws Throwable {
      // $FF: Couldn't be decompiled
   }

   public void apply(CallContext var1) throws Throwable {
      Procedure.checkArgCount(this, var1.count);
      Object var4 = var1.getNextArg();
      Environment var3 = (Environment)var1.getNextArg((Object)null);
      Environment var2 = var3;
      if(var3 == null) {
         var2 = Environment.user();
      }

      var1.lastArg();
      eval(var4, var2, var1);
   }

   public Object apply1(Object var1) throws Throwable {
      return eval(var1, Environment.user());
   }

   public Object apply2(Object var1, Object var2) throws Throwable {
      return eval(var1, (Environment)var2);
   }
}
