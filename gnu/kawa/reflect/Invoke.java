package gnu.kawa.reflect;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Keyword;
import gnu.expr.Language;
import gnu.expr.PairClassType;
import gnu.expr.PrimProcedure;
import gnu.expr.TypeValue;
import gnu.kawa.lispexpr.ClassNamespace;
import gnu.kawa.reflect.ClassMethods;
import gnu.kawa.reflect.CompileInvoke;
import gnu.kawa.reflect.SlotSet;
import gnu.lists.FString;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.Symbol;
import gnu.mapping.WrongType;
import java.lang.reflect.Array;

public class Invoke extends ProcedureN {

   public static final Invoke invoke = new Invoke("invoke", '*');
   public static final Invoke invokeSpecial = new Invoke("invoke-special", 'P');
   public static final Invoke invokeStatic = new Invoke("invoke-static", 'S');
   public static final Invoke make = new Invoke("make", 'N');
   char kind;
   Language language;


   public Invoke(String var1, char var2) {
      this(var1, var2, Language.getDefaultLanguage());
   }

   public Invoke(String var1, char var2, Language var3) {
      super(var1);
      this.kind = var2;
      this.language = var3;
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileInvoke:validateApplyInvoke");
   }

   public static PrimProcedure getStaticMethod(ClassType var0, String var1, Expression[] var2) {
      synchronized(Invoke.class){}

      PrimProcedure var5;
      try {
         var5 = CompileInvoke.getStaticMethod(var0, var1, var2);
      } finally {
         ;
      }

      return var5;
   }

   public static Object invoke$V(Object[] var0) throws Throwable {
      return invoke.applyN(var0);
   }

   public static Object invokeStatic$V(Object[] var0) throws Throwable {
      return invokeStatic.applyN(var0);
   }

   public static Object make$V(Object[] var0) throws Throwable {
      return make.applyN(var0);
   }

   public static ApplyExp makeInvokeStatic(ClassType param0, String param1, Expression[] param2) {
      // $FF: Couldn't be decompiled
   }

   private static ObjectType typeFrom(Object var0, Invoke var1) {
      Object var2 = var0;
      if(var0 instanceof Class) {
         var2 = Type.make((Class)var0);
      }

      if(var2 instanceof ObjectType) {
         return (ObjectType)var2;
      } else if(!(var2 instanceof String) && !(var2 instanceof FString)) {
         if(var2 instanceof Symbol) {
            return ClassType.make(((Symbol)var2).getName());
         } else if(var2 instanceof ClassNamespace) {
            return ((ClassNamespace)var2).getClassType();
         } else {
            throw new WrongType(var1, 0, var2, "class-specifier");
         }
      } else {
         return ClassType.make(var2.toString());
      }
   }

   public void apply(CallContext var1) throws Throwable {
      Object[] var3 = var1.getArgs();
      if(this.kind != 83 && this.kind != 86 && this.kind != 115 && this.kind != 42) {
         var1.writeValue(this.applyN(var3));
      } else {
         int var6 = var3.length;
         Procedure.checkArgCount(this, var6);
         Object var2 = var3[0];
         if(this.kind != 83 && this.kind != 115) {
            var2 = Type.make(var2.getClass());
         } else {
            var2 = typeFrom(var2, this);
         }

         MethodProc var7 = this.lookupMethods((ObjectType)var2, var3[1]);
         byte var5;
         if(this.kind == 83) {
            var5 = 2;
         } else {
            var5 = 1;
         }

         Object[] var4 = new Object[var6 - var5];
         int var8 = 0;
         if(this.kind == 86 || this.kind == 42) {
            var4[0] = var3[0];
            var8 = 0 + 1;
         }

         System.arraycopy(var3, 2, var4, var8, var6 - 2);
         var7.checkN(var4, var1);
      }
   }

   public Object applyN(Object[] var1) throws Throwable {
      if(this.kind == 80) {
         throw new RuntimeException(this.getName() + ": invoke-special not allowed at run time");
      } else {
         int var10 = var1.length;
         Procedure.checkArgCount(this, var10);
         Object var2 = var1[0];
         if(this.kind != 86 && this.kind != 42) {
            var2 = typeFrom(var2, this);
         } else {
            var2 = (ObjectType)Type.make(var2.getClass());
         }

         Object var4;
         int var7;
         int var8;
         Object var14;
         Object[] var22;
         if(this.kind == 78) {
            var4 = null;
            if(var2 instanceof TypeValue) {
               Procedure var3 = ((TypeValue)var2).getConstructor();
               if(var3 != null) {
                  var7 = var10 - 1;
                  var22 = new Object[var7];
                  System.arraycopy(var1, 1, var22, 0, var7);
                  var2 = var3.applyN(var22);
                  return var2;
               }
            }

            var14 = var2;
            if(var2 instanceof PairClassType) {
               var14 = ((PairClassType)var2).instanceType;
            }

            var2 = var14;
            if(var14 instanceof ArrayType) {
               Type var5;
               boolean var9;
               int var12;
               String var15;
               label100: {
                  var5 = ((ArrayType)var14).getComponentType();
                  var12 = var1.length - 1;
                  if(var12 >= 2 && var1[1] instanceof Keyword) {
                     var15 = ((Keyword)var1[1]).getName();
                     if("length".equals(var15) || "size".equals(var15)) {
                        var8 = ((Number)var1[2]).intValue();
                        var7 = 3;
                        var9 = true;
                        break label100;
                     }
                  }

                  var8 = var12;
                  var7 = 1;
                  var9 = false;
               }

               var14 = Array.newInstance(var5.getReflectClass(), var8);
               var8 = 0;

               while(true) {
                  var2 = var14;
                  if(var7 > var12) {
                     return var2;
                  }

                  var4 = var1[var7];
                  var2 = var4;
                  var10 = var7;
                  int var11 = var8;
                  if(var9) {
                     var2 = var4;
                     var10 = var7;
                     var11 = var8;
                     if(var4 instanceof Keyword) {
                        var2 = var4;
                        var10 = var7;
                        var11 = var8;
                        if(var7 < var12) {
                           var15 = ((Keyword)var4).getName();

                           try {
                              var11 = Integer.parseInt(var15);
                           } catch (Throwable var13) {
                              throw new RuntimeException("non-integer keyword \'" + var15 + "\' in array constructor");
                           }

                           var10 = var7 + 1;
                           var2 = var1[var10];
                        }
                     }
                  }

                  Array.set(var14, var11, var5.coerceFromObject(var2));
                  var8 = var11 + 1;
                  var7 = var10 + 1;
               }
            }
         } else {
            var4 = var1[1];
         }

         MethodProc var19 = this.lookupMethods((ObjectType)var2, var4);
         if(this.kind != 78) {
            byte var25;
            if(this.kind != 83 && this.kind != 115) {
               var25 = 1;
            } else {
               var25 = 2;
            }

            var22 = new Object[var10 - var25];
            var7 = 0;
            if(this.kind == 86 || this.kind == 42) {
               var22[0] = var1[0];
               var7 = 0 + 1;
            }

            System.arraycopy(var1, 2, var22, var7, var10 - 2);
            return var19.applyN(var22);
         } else {
            CallContext var16 = CallContext.getInstance();

            for(var7 = 0; var7 < var1.length && !(var1[var7] instanceof Keyword); ++var7) {
               ;
            }

            int var26 = -1;
            if(var7 == var1.length) {
               var8 = var19.matchN(var1, var16);
               if(var8 == 0) {
                  return var16.runUntilValue();
               }

               MethodProc var17 = ClassMethods.apply((ClassType)var2, "valueOf", '\u0000', this.language);
               if(var17 != null) {
                  Object[] var6 = new Object[var10 - 1];
                  System.arraycopy(var1, 1, var6, 0, var10 - 1);
                  var26 = var17.matchN(var6, var16);
                  var8 = var26;
                  if(var26 == 0) {
                     return var16.runUntilValue();
                  }
               }

               var14 = var19.apply1(var1[0]);
               var26 = var8;
            } else {
               Object[] var18 = new Object[var7];
               System.arraycopy(var1, 0, var18, 0, var7);
               var14 = var19.applyN(var18);
            }

            for(var8 = var7; var8 + 1 < var1.length; var8 += 2) {
               Object var21 = var1[var8];
               if(!(var21 instanceof Keyword)) {
                  break;
               }

               Keyword var20 = (Keyword)var21;
               Object var23 = var1[var8 + 1];
               SlotSet.apply(false, var14, var20.getName(), var23);
            }

            if(var7 == var1.length) {
               var8 = 1;
            }

            if(var8 != var1.length) {
               MethodProc var24 = ClassMethods.apply((ClassType)var2, "add", '\u0000', this.language);
               if(var24 == null) {
                  throw MethodProc.matchFailAsException(var26, var19, var1);
               }

               while(var8 < var1.length) {
                  var24.apply2(var14, var1[var8]);
                  ++var8;
               }
            }

            return var14;
         }
      }
   }

   protected MethodProc lookupMethods(ObjectType var1, Object var2) {
      char var3 = 80;
      String var5;
      if(this.kind == 78) {
         var5 = "<init>";
      } else {
         if(!(var2 instanceof String) && !(var2 instanceof FString)) {
            if(!(var2 instanceof Symbol)) {
               throw new WrongType(this, 1, (ClassCastException)null);
            }

            var5 = ((Symbol)var2).getName();
         } else {
            var5 = var2.toString();
         }

         var5 = Compilation.mangleName(var5);
      }

      if(this.kind != 80) {
         if(this.kind != 42 && this.kind != 86) {
            var3 = 0;
         } else {
            var3 = 86;
         }
      }

      MethodProc var4 = ClassMethods.apply(var1, var5, var3, this.language);
      if(var4 == null) {
         throw new RuntimeException(this.getName() + ": no method named `" + var5 + "\' in class " + var1.getName());
      } else {
         return var4;
      }
   }

   public int numArgs() {
      byte var1;
      if(this.kind == 78) {
         var1 = 1;
      } else {
         var1 = 2;
      }

      return var1 | -4096;
   }
}
