package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.GenericProc;
import gnu.expr.Language;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.kawa.reflect.MethodFilter;
import gnu.lists.FString;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure2;
import gnu.mapping.Symbol;
import gnu.mapping.WrongType;
import java.util.Vector;

public class ClassMethods extends Procedure2 {

   public static final ClassMethods classMethods = new ClassMethods();


   static {
      classMethods.setName("class-methods");
   }

   public static MethodProc apply(ObjectType var0, String var1, char var2, Language var3) {
      PrimProcedure[] var7 = getMethods(var0, var1, var2, (ClassType)null, var3);
      GenericProc var4 = null;
      PrimProcedure var9 = null;

      GenericProc var5;
      for(int var8 = 0; var8 < var7.length; var4 = var5) {
         PrimProcedure var6 = var7[var8];
         var5 = var4;
         if(var9 != null) {
            var5 = var4;
            if(var4 == null) {
               var5 = new GenericProc();
               var5.add((MethodProc)var9);
            }
         }

         var9 = var6;
         if(var5 != null) {
            var5.add((MethodProc)var6);
         }

         ++var8;
      }

      if(var4 != null) {
         var4.setName(var0.getName() + "." + var1);
         return var4;
      } else {
         return var9;
      }
   }

   public static MethodProc apply(Procedure var0, Object var1, Object var2) {
      Object var3 = var1;
      if(var1 instanceof Class) {
         var3 = Type.make((Class)var1);
      }

      ClassType var5;
      if(var3 instanceof ClassType) {
         var5 = (ClassType)var3;
      } else {
         if(!(var3 instanceof String) && !(var3 instanceof FString) && !(var3 instanceof Symbol)) {
            throw new WrongType(var0, 0, (ClassCastException)null);
         }

         var5 = ClassType.make(var3.toString());
      }

      if(!(var2 instanceof String) && !(var2 instanceof FString) && !(var2 instanceof Symbol)) {
         throw new WrongType(var0, 1, (ClassCastException)null);
      } else {
         String var6 = var2.toString();
         String var4 = var6;
         if(!"<init>".equals(var6)) {
            var4 = Compilation.mangleName(var6);
         }

         MethodProc var7 = apply(var5, var4, '\u0000', Language.getDefaultLanguage());
         if(var7 == null) {
            throw new RuntimeException("no applicable method named `" + var4 + "\' in " + var5.getName());
         } else {
            return var7;
         }
      }
   }

   static String checkName(Expression var0) {
      Object var2 = null;
      String var1 = (String)var2;
      if(var0 instanceof QuoteExp) {
         Object var3 = ((QuoteExp)var0).getValue();
         if(!(var3 instanceof FString) && !(var3 instanceof String)) {
            var1 = (String)var2;
            if(var3 instanceof Symbol) {
               return ((Symbol)var3).getName();
            }
         } else {
            var1 = var3.toString();
         }
      }

      return var1;
   }

   static String checkName(Expression var0, boolean var1) {
      Object var3 = null;
      String var2 = (String)var3;
      if(var0 instanceof QuoteExp) {
         Object var4 = ((QuoteExp)var0).getValue();
         String var5;
         if(!(var4 instanceof FString) && !(var4 instanceof String)) {
            var2 = (String)var3;
            if(!(var4 instanceof Symbol)) {
               return var2;
            }

            var5 = ((Symbol)var4).getName();
         } else {
            var5 = var4.toString();
         }

         if(!Compilation.isValidJavaName(var5)) {
            return Compilation.mangleName(var5, var1);
         }

         var2 = var5;
      }

      return var2;
   }

   public static PrimProcedure[] getMethods(ObjectType var0, String var1, char var2, ClassType var3, Language var4) {
      Object var5 = var0;
      if(var0 == Type.tostring_type) {
         var5 = Type.string_type;
      }

      Object var12;
      if(var2 == 80) {
         var12 = null;
      } else {
         var12 = var5;
      }

      MethodFilter var13 = new MethodFilter(var1, 0, 0, var3, (ObjectType)var12);
      boolean var9;
      if(var2 != 80 && !"<init>".equals(var1)) {
         var9 = false;
      } else {
         var9 = true;
      }

      Vector var16 = new Vector();
      byte var8;
      if(var9) {
         var8 = 0;
      } else {
         var8 = 2;
      }

      ((ObjectType)var5).getMethods(var13, var8, var16);
      if(!var9 && (!(var5 instanceof ClassType) || ((ClassType)var5).isInterface())) {
         Type.pointer_type.getMethods(var13, 0, var16);
      }

      int var17;
      if(var9) {
         var17 = var16.size();
      } else {
         var17 = removeRedundantMethods(var16);
      }

      PrimProcedure[] var6 = new PrimProcedure[var17];
      byte var11 = 0;
      int var10 = var17;
      var17 = var11;

      while(true) {
         --var10;
         if(var10 < 0) {
            return var6;
         }

         Method var14 = (Method)var16.elementAt(var10);
         Method var15 = var14;
         if(!var9) {
            var15 = var14;
            if(var14.getDeclaringClass() != var5) {
               Type var7 = ((ObjectType)var5).getImplementationType();
               var15 = var14;
               if(var7 instanceof ClassType) {
                  var15 = new Method(var14, (ClassType)var7);
               }
            }
         }

         var6[var17] = new PrimProcedure(var15, var2, var4);
         ++var17;
      }
   }

   private static int removeRedundantMethods(Vector var0) {
      int var6 = var0.size();
      int var7 = 1;

      label38:
      while(var7 < var6) {
         Method var1 = (Method)var0.elementAt(var7);
         ClassType var2 = var1.getDeclaringClass();
         Type[] var3 = var1.getParameterTypes();
         int var10 = var3.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            Method var4 = (Method)var0.elementAt(var8);
            Type[] var5 = var4.getParameterTypes();
            if(var10 == var5.length) {
               int var9 = var10;

               int var11;
               do {
                  var11 = var9 - 1;
                  if(var11 < 0) {
                     break;
                  }

                  var9 = var11;
               } while(var3[var11] == var5[var11]);

               if(var11 < 0) {
                  if(var2.isSubtype(var4.getDeclaringClass())) {
                     var0.setElementAt(var1, var8);
                  }

                  var0.setElementAt(var0.elementAt(var6 - 1), var7);
                  --var6;
                  continue label38;
               }
            }
         }

         ++var7;
      }

      return var6;
   }

   public static int selectApplicable(PrimProcedure[] var0, int var1) {
      int var3 = var0.length;
      int var6 = 0;
      int var7 = 0;
      int var4 = 0;
      int var5 = 0;

      while(var5 < var3) {
         int var8 = var0[var5].numArgs();
         int var9 = Procedure.minArgs(var8);
         int var10 = Procedure.maxArgs(var8);
         boolean var11 = false;
         if(var1 < var9) {
            ++var7;
         } else if(var1 > var10 && var10 >= 0) {
            ++var6;
         } else {
            var11 = true;
         }

         if(var11) {
            ++var4;
            ++var5;
         } else {
            PrimProcedure var2 = var0[var3 - 1];
            var0[var3 - 1] = var0[var5];
            var0[var5] = var2;
            --var3;
         }
      }

      if(var4 > 0) {
         return var4;
      } else if(var7 > 0) {
         return -983040;
      } else if(var6 > 0) {
         return -917504;
      } else {
         return 0;
      }
   }

   public static long selectApplicable(PrimProcedure[] var0, Type[] var1) {
      int var5 = var0.length;
      int var6 = 0;
      int var4 = 0;
      int var3 = 0;

      while(var3 < var5) {
         int var7 = var0[var3].isApplicable(var1);
         PrimProcedure var2;
         if(var7 < 0) {
            var2 = var0[var5 - 1];
            var0[var5 - 1] = var0[var3];
            var0[var3] = var2;
            --var5;
         } else if(var7 > 0) {
            var2 = var0[var6];
            var0[var6] = var0[var3];
            var0[var3] = var2;
            ++var6;
            ++var3;
         } else {
            ++var4;
            ++var3;
         }
      }

      return ((long)var6 << 32) + (long)var4;
   }

   public Object apply2(Object var1, Object var2) {
      return apply(this, var1, var2);
   }
}
