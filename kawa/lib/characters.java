package kawa.lib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;
import gnu.text.Char;

public class characters extends ModuleBody {

   public static final characters $instance = new characters();
   static final SimpleSymbol Lit0 = (SimpleSymbol)(new SimpleSymbol("char?")).readResolve();
   static final SimpleSymbol Lit1 = (SimpleSymbol)(new SimpleSymbol("char->integer")).readResolve();
   static final SimpleSymbol Lit2 = (SimpleSymbol)(new SimpleSymbol("integer->char")).readResolve();
   static final SimpleSymbol Lit3 = (SimpleSymbol)(new SimpleSymbol("char=?")).readResolve();
   static final SimpleSymbol Lit4 = (SimpleSymbol)(new SimpleSymbol("char<?")).readResolve();
   static final SimpleSymbol Lit5 = (SimpleSymbol)(new SimpleSymbol("char>?")).readResolve();
   static final SimpleSymbol Lit6 = (SimpleSymbol)(new SimpleSymbol("char<=?")).readResolve();
   static final SimpleSymbol Lit7 = (SimpleSymbol)(new SimpleSymbol("char>=?")).readResolve();
   public static final ModuleMethod char$Eq$Qu;
   public static final ModuleMethod char$Gr$Eq$Qu;
   public static final ModuleMethod char$Gr$Qu;
   public static final ModuleMethod char$Ls$Eq$Qu;
   public static final ModuleMethod char$Ls$Qu;
   public static final ModuleMethod char$Mn$Grinteger;
   public static final ModuleMethod char$Qu;
   public static final ModuleMethod integer$Mn$Grchar;


   static {
      characters var0 = $instance;
      char$Qu = new ModuleMethod(var0, 1, Lit0, 4097);
      char$Mn$Grinteger = new ModuleMethod(var0, 2, Lit1, 4097);
      integer$Mn$Grchar = new ModuleMethod(var0, 3, Lit2, 4097);
      char$Eq$Qu = new ModuleMethod(var0, 4, Lit3, 8194);
      char$Ls$Qu = new ModuleMethod(var0, 5, Lit4, 8194);
      char$Gr$Qu = new ModuleMethod(var0, 6, Lit5, 8194);
      char$Ls$Eq$Qu = new ModuleMethod(var0, 7, Lit6, 8194);
      char$Gr$Eq$Qu = new ModuleMethod(var0, 8, Lit7, 8194);
      $instance.run();
   }

   public characters() {
      ModuleInfo.register(this);
   }

   public static int char$To$Integer(Char var0) {
      return var0.intValue();
   }

   public static Char integer$To$Char(int var0) {
      return Char.make(var0);
   }

   public static boolean isChar(Object var0) {
      return var0 instanceof Char;
   }

   public static boolean isChar$Eq(Char var0, Char var1) {
      return var0.intValue() == var1.intValue();
   }

   public static boolean isChar$Gr(Char var0, Char var1) {
      return var0.intValue() > var1.intValue();
   }

   public static boolean isChar$Gr$Eq(Char var0, Char var1) {
      return var0.intValue() >= var1.intValue();
   }

   public static boolean isChar$Ls(Char var0, Char var1) {
      return var0.intValue() < var1.intValue();
   }

   public static boolean isChar$Ls$Eq(Char var0, Char var1) {
      return var0.intValue() <= var1.intValue();
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      switch(var1.selector) {
      case 1:
         if(isChar(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 2:
         Char var6;
         try {
            var6 = (Char)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "char->integer", 1, var2);
         }

         return Integer.valueOf(char$To$Integer(var6));
      case 3:
         int var3;
         try {
            var3 = ((Number)var2).intValue();
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "integer->char", 1, var2);
         }

         return integer$To$Char(var3);
      default:
         return super.apply1(var1, var2);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      Char var14;
      Char var15;
      switch(var1.selector) {
      case 4:
         try {
            var14 = (Char)var2;
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "char=?", 1, var2);
         }

         try {
            var15 = (Char)var3;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "char=?", 2, var3);
         }

         if(isChar$Eq(var14, var15)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 5:
         try {
            var14 = (Char)var2;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "char<?", 1, var2);
         }

         try {
            var15 = (Char)var3;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "char<?", 2, var3);
         }

         if(isChar$Ls(var14, var15)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 6:
         try {
            var14 = (Char)var2;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "char>?", 1, var2);
         }

         try {
            var15 = (Char)var3;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "char>?", 2, var3);
         }

         if(isChar$Gr(var14, var15)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 7:
         try {
            var14 = (Char)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "char<=?", 1, var2);
         }

         try {
            var15 = (Char)var3;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "char<=?", 2, var3);
         }

         if(isChar$Ls$Eq(var14, var15)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 8:
         try {
            var14 = (Char)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "char>=?", 1, var2);
         }

         try {
            var15 = (Char)var3;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "char>=?", 2, var3);
         }

         if(isChar$Gr$Eq(var14, var15)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      default:
         return super.apply2(var1, var2, var3);
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
         if(!(var2 instanceof Char)) {
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

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      int var5 = -786431;
      switch(var1.selector) {
      case 4:
         if(var2 instanceof Char) {
            var4.value1 = var2;
            if(!(var3 instanceof Char)) {
               return -786430;
            }

            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 5:
         if(var2 instanceof Char) {
            var4.value1 = var2;
            if(!(var3 instanceof Char)) {
               return -786430;
            }

            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 6:
         if(var2 instanceof Char) {
            var4.value1 = var2;
            if(!(var3 instanceof Char)) {
               return -786430;
            }

            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 7:
         if(var2 instanceof Char) {
            var4.value1 = var2;
            if(!(var3 instanceof Char)) {
               return -786430;
            }

            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 8:
         if(var2 instanceof Char) {
            var4.value1 = var2;
            if(!(var3 instanceof Char)) {
               return -786430;
            }

            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      default:
         var5 = super.match2(var1, var2, var3, var4);
      }

      return var5;
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }
}
