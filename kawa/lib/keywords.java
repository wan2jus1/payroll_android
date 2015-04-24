package kawa.lib;

import gnu.expr.Keyword;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;

public class keywords extends ModuleBody {

   public static final keywords $instance = new keywords();
   static final SimpleSymbol Lit0 = (SimpleSymbol)(new SimpleSymbol("keyword?")).readResolve();
   static final SimpleSymbol Lit1 = (SimpleSymbol)(new SimpleSymbol("keyword->string")).readResolve();
   static final SimpleSymbol Lit2 = (SimpleSymbol)(new SimpleSymbol("string->keyword")).readResolve();
   public static final ModuleMethod keyword$Mn$Grstring;
   public static final ModuleMethod keyword$Qu;
   public static final ModuleMethod string$Mn$Grkeyword;


   static {
      keywords var0 = $instance;
      keyword$Qu = new ModuleMethod(var0, 1, Lit0, 4097);
      keyword$Mn$Grstring = new ModuleMethod(var0, 2, Lit1, 4097);
      string$Mn$Grkeyword = new ModuleMethod(var0, 3, Lit2, 4097);
      $instance.run();
   }

   public keywords() {
      ModuleInfo.register(this);
   }

   public static boolean isKeyword(Object var0) {
      return Keyword.isKeyword(var0);
   }

   public static CharSequence keyword$To$String(Keyword var0) {
      return var0.getName();
   }

   public static Keyword string$To$Keyword(String var0) {
      return Keyword.make(var0);
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      switch(var1.selector) {
      case 1:
         if(isKeyword(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 2:
         Keyword var5;
         try {
            var5 = (Keyword)var2;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "keyword->string", 1, var2);
         }

         return keyword$To$String(var5);
      case 3:
         String var4;
         if(var2 == null) {
            var4 = null;
         } else {
            var4 = var2.toString();
         }

         return string$To$Keyword(var4);
      default:
         return super.apply1(var1, var2);
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      switch(var1.selector) {
      case 1:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 2:
         if(!(var2 instanceof Keyword)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 3:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      default:
         return super.match1(var1, var2, var3);
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }
}
