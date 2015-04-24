package gnu.kawa.slib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;

public class XStrings extends ModuleBody {

   public static final XStrings $instance = new XStrings();
   static final IntNum Lit0 = IntNum.make(Integer.MAX_VALUE);
   static final SimpleSymbol Lit1 = (SimpleSymbol)(new SimpleSymbol("substring")).readResolve();
   static final SimpleSymbol Lit2 = (SimpleSymbol)(new SimpleSymbol("string-length")).readResolve();
   public static final ModuleMethod string$Mnlength;
   public static final ModuleMethod substring;


   static {
      XStrings var0 = $instance;
      substring = new ModuleMethod(var0, 1, Lit1, 12290);
      string$Mnlength = new ModuleMethod(var0, 3, Lit2, 4097);
      $instance.run();
   }

   public XStrings() {
      ModuleInfo.register(this);
   }

   public static Object stringLength(Object var0) {
      return var0 == Values.empty?Values.empty:Integer.valueOf(((String)var0).length());
   }

   public static Object substring(Object var0, Object var1) {
      return substring(var0, var1, Lit0);
   }

   public static Object substring(Object var0, Object var1, Object var2) {
      boolean var4;
      if(var0 == Values.empty) {
         var4 = true;
      } else {
         var4 = false;
      }

      if(var4) {
         if(var4) {
            return Values.empty;
         }
      } else {
         if(var1 == Values.empty) {
            var4 = true;
         } else {
            var4 = false;
         }

         if(var4) {
            if(var4) {
               return Values.empty;
            }
         } else if(var2 == Values.empty) {
            return Values.empty;
         }
      }

      String var3;
      try {
         var3 = (String)var0;
      } catch (ClassCastException var9) {
         throw new WrongType(var9, "s", -2, var0);
      }

      int var5 = var3.length();

      int var10;
      try {
         var10 = ((Number)var1).intValue();
      } catch (ClassCastException var8) {
         throw new WrongType(var8, "sindex", -2, var1);
      }

      int var6 = var10 - 1;

      try {
         var10 = ((Number)var2).intValue();
      } catch (ClassCastException var7) {
         throw new WrongType(var7, "len", -2, var2);
      }

      var5 -= var6;
      if(var10 > var5) {
         var10 = var5;
      }

      return var3.substring(var6, var6 + var10);
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      return var1.selector == 3?stringLength(var2):super.apply1(var1, var2);
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      return var1.selector == 1?substring(var2, var3):super.apply2(var1, var2, var3);
   }

   public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
      return var1.selector == 1?substring(var2, var3, var4):super.apply3(var1, var2, var3, var4);
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      if(var1.selector == 3) {
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      } else {
         return super.match1(var1, var2, var3);
      }
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      if(var1.selector == 1) {
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      } else {
         return super.match2(var1, var2, var3, var4);
      }
   }

   public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
      if(var1.selector == 1) {
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      } else {
         return super.match3(var1, var2, var3, var4, var5);
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }
}
