package gnu.kawa.functions;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.Language;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.kawa.functions.GetNamedExp;
import gnu.kawa.functions.GetNamedInstancePart;
import gnu.kawa.functions.NamedPart;
import gnu.kawa.functions.NamedPartSetter;
import gnu.kawa.functions.SetNamedInstancePart;
import gnu.kawa.reflect.ClassMethods;
import gnu.kawa.reflect.CompileReflect;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.kawa.reflect.SlotSet;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.HasNamedParts;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import kawa.lang.Translator;

public class CompileNamedPart {

   static final ClassType typeHasNamedParts = ClassType.make("gnu.mapping.HasNamedParts");


   public static String combineName(Expression var0, Expression var1) {
      Object var3 = var1.valueIfConstant();
      if(var3 instanceof SimpleSymbol) {
         String var4;
         if(var0 instanceof ReferenceExp) {
            String var2 = ((ReferenceExp)var0).getSimpleName();
            var4 = var2;
            if(var2 != null) {
               return (var4 + ':' + var3).intern();
            }
         }

         if(!(var0 instanceof GetNamedExp)) {
            return null;
         } else {
            var4 = ((GetNamedExp)var0).combinedName;
            if(var4 == null) {
               return null;
            } else {
               return (var4 + ':' + var3).intern();
            }
         }
      } else {
         return null;
      }
   }

   public static Expression makeExp(Type var0, String var1) {
      return makeExp((Expression)(new QuoteExp(var0)), (Expression)(new QuoteExp(var1)));
   }

   public static Expression makeExp(Expression var0, Expression var1) {
      String var4 = combineName(var0, var1);
      Environment var5 = Environment.getCurrent();
      Symbol var2;
      if(var4 != null) {
         Translator var3 = (Translator)Compilation.getCurrent();
         var2 = Namespace.EmptyNamespace.getSymbol(var4);
         Declaration var10 = var3.lexical.lookup(var2, false);
         if(!Declaration.isUnknown(var10)) {
            return new ReferenceExp(var10);
         }

         if(var2 != null && var5.isBound(var2, (Object)null)) {
            return new ReferenceExp(var4);
         }
      }

      Object var11 = var0;
      if(var0 instanceof ReferenceExp) {
         ReferenceExp var6 = (ReferenceExp)var0;
         var11 = var0;
         if(var6.isUnknown()) {
            Object var9 = var6.getSymbol();
            if(var9 instanceof Symbol) {
               var2 = (Symbol)var9;
            } else {
               var2 = var5.getSymbol(var9.toString());
            }

            var11 = var0;
            if(var5.get((EnvironmentKey)var2, (Object)null) == null) {
               String var12 = var6.getName();

               try {
                  var11 = QuoteExp.getInstance(Type.make(ClassType.getContextClass(var12)));
               } catch (Throwable var7) {
                  var11 = var0;
               }
            }
         }
      }

      GetNamedExp var8 = new GetNamedExp(new Expression[]{(Expression)var11, var1});
      var8.combinedName = var4;
      return var8;
   }

   public static Expression makeExp(Expression var0, String var1) {
      return makeExp((Expression)var0, (Expression)(new QuoteExp(var1)));
   }

   public static Expression makeGetNamedInstancePartExp(Expression var0) {
      if(var0 instanceof QuoteExp) {
         Object var1 = ((QuoteExp)var0).getValue();
         if(var1 instanceof SimpleSymbol) {
            return QuoteExp.getInstance(new GetNamedInstancePart(var1.toString()));
         }
      }

      QuoteExp var2 = new QuoteExp(ClassType.make("gnu.kawa.functions.GetNamedInstancePart"));
      return new ApplyExp(Invoke.make, new Expression[]{var2, var0});
   }

   public static Expression validateGetNamedInstancePart(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      var0.visitArgs(var1);
      Expression[] var6 = var0.getArgs();
      GetNamedInstancePart var4 = (GetNamedInstancePart)var3;
      Object var7;
      Expression[] var8;
      if(var4.isField) {
         var8 = new Expression[]{var6[0], new QuoteExp(var4.pname)};
         var7 = SlotGet.field;
      } else {
         int var5 = var6.length;
         var8 = new Expression[var5 + 1];
         var8[0] = var6[0];
         var8[1] = new QuoteExp(var4.pname);
         System.arraycopy(var6, 1, var8, 2, var5 - 1);
         var7 = Invoke.invoke;
      }

      return var1.visitApplyOnly(new ApplyExp((Procedure)var7, var8), var2);
   }

   public static Expression validateGetNamedPart(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      var0.visitArgs(var1);
      Expression[] var5 = var0.getArgs();
      if(var5.length == 2 && var5[1] instanceof QuoteExp && var0 instanceof GetNamedExp) {
         Expression var16 = var5[0];
         Declaration var4 = null;
         if(var16 instanceof ReferenceExp) {
            ReferenceExp var18 = (ReferenceExp)var16;
            if("*".equals(var18.getName())) {
               return makeGetNamedInstancePartExp(var5[1]);
            }

            var4 = var18.getBinding();
         }

         String var6 = ((QuoteExp)var5[1]).getValue().toString();
         Type var7 = var16.getType();
         if(var16 == QuoteExp.nullExp) {
            ;
         }

         Compilation var8 = var1.getCompilation();
         Language var9 = var8.getLanguage();
         Type var10 = var9.getTypeFor((Expression)var16, false);
         ClassType var17;
         if(var8 == null) {
            var17 = null;
         } else if(var8.curClass != null) {
            var17 = var8.curClass;
         } else {
            var17 = var8.mainClass;
         }

         GetNamedExp var11 = (GetNamedExp)var0;
         if(var10 != null) {
            if(var6.equals("<>")) {
               return new QuoteExp(var10);
            }

            if(var10 instanceof ObjectType) {
               if(var6.equals("new")) {
                  return var11.setProcedureKind('N');
               }

               if(var6.equals("instance?")) {
                  return var11.setProcedureKind('I');
               }

               if(var6.equals("@")) {
                  return var11.setProcedureKind('C');
               }
            }
         }

         ApplyExp var19;
         if(var10 instanceof ObjectType) {
            if(var6.length() > 1 && var6.charAt(0) == 46) {
               return new QuoteExp(new NamedPart(var10, var6, 'D'));
            }

            if(CompileReflect.checkKnownClass(var10, var8) >= 0) {
               PrimProcedure[] var20 = ClassMethods.getMethods((ObjectType)var10, Compilation.mangleName(var6), '\u0000', var17, var9);
               if(var20 != null && var20.length > 0) {
                  var11.methods = var20;
                  return var11.setProcedureKind('S');
               }

               var19 = new ApplyExp(SlotGet.staticField, var5);
               var19.setLine(var0);
               return var1.visitApplyOnly(var19, var2);
            }
         } else {
            if(var10 != null) {
               ;
            }

            if(!var7.isSubtype(Compilation.typeClassType) && !var7.isSubtype(Type.javalangClassType)) {
               if(var7 instanceof ObjectType) {
                  ObjectType var22 = (ObjectType)var7;
                  PrimProcedure[] var21 = ClassMethods.getMethods(var22, Compilation.mangleName(var6), 'V', var17, var9);
                  if(var21 != null && var21.length > 0) {
                     var11.methods = var21;
                     return var11.setProcedureKind('M');
                  }

                  if(var7.isSubtype(typeHasNamedParts)) {
                     if(var4 != null) {
                        Object var12 = Declaration.followAliases(var4).getConstantValue();
                        if(var12 != null) {
                           HasNamedParts var13 = (HasNamedParts)var12;
                           if(var13.isConstant(var6)) {
                              return QuoteExp.getInstance(var13.get(var6));
                           }
                        }
                     }

                     Expression var14 = var5[0];
                     QuoteExp var15 = QuoteExp.getInstance(var6);
                     return (new ApplyExp(typeHasNamedParts.getDeclaredMethod("get", 1), new Expression[]{var14, var15})).setLine(var0);
                  }

                  if(SlotGet.lookupMember(var22, var6, var17) != null || var6.equals("length") && var7 instanceof ArrayType) {
                     var19 = new ApplyExp(SlotGet.field, var5);
                     var19.setLine(var0);
                     return var1.visitApplyOnly(var19, var2);
                  }
               }

               if(var8.warnUnknownMember()) {
                  var8.error('w', "no known slot \'" + var6 + "\' in " + var7.getName());
                  return var0;
               }
            }
         }
      }

      return var0;
   }

   public static Expression validateNamedPart(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      var0.visitArgs(var1);
      Expression[] var5 = var0.getArgs();
      NamedPart var7 = (NamedPart)var3;
      switch(var7.kind) {
      case 68:
         String var6 = var7.member.toString().substring(1);
         Expression[] var4 = new Expression[]{null, QuoteExp.getInstance(var6)};
         SlotGet var8;
         if(var5.length > 0) {
            var4[0] = Compilation.makeCoercion(var5[0], (Expression)(new QuoteExp(var7.container)));
            var8 = SlotGet.field;
         } else {
            var4[0] = QuoteExp.getInstance(var7.container);
            var8 = SlotGet.staticField;
         }

         ApplyExp var9 = new ApplyExp(var8, var4);
         var9.setLine(var0);
         return var1.visitApplyOnly(var9, var2);
      default:
         return var0;
      }
   }

   public static Expression validateNamedPartSetter(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      var0.visitArgs(var1);
      NamedPart var5 = (NamedPart)((NamedPartSetter)var3).getGetter();
      Object var6 = var0;
      if(var5.kind == 68) {
         Expression[] var4 = new Expression[]{null, QuoteExp.getInstance(var5.member.toString().substring(1)), var0.getArgs()[0]};
         SlotSet var7;
         if(var0.getArgCount() == 1) {
            var4[0] = QuoteExp.getInstance(var5.container);
            var7 = SlotSet.set$Mnstatic$Mnfield$Ex;
         } else {
            var6 = var0;
            if(var0.getArgCount() != 2) {
               return (Expression)var6;
            }

            var4[0] = Compilation.makeCoercion(var0.getArgs()[0], (Expression)(new QuoteExp(var5.container)));
            var7 = SlotSet.set$Mnfield$Ex;
         }

         ApplyExp var8 = new ApplyExp(var7, var4);
         var8.setLine(var0);
         var6 = var1.visitApplyOnly(var8, var2);
      }

      return (Expression)var6;
   }

   public static Expression validateSetNamedInstancePart(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      var0.visitArgs(var1);
      Expression[] var5 = var0.getArgs();
      String var4 = ((SetNamedInstancePart)var3).pname;
      Expression var7 = var5[0];
      QuoteExp var8 = new QuoteExp(var4);
      Expression var6 = var5[1];
      return var1.visitApplyOnly(new ApplyExp(SlotSet.set$Mnfield$Ex, new Expression[]{var7, var8, var6}), var2);
   }

   public static Expression validateSetNamedPart(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      var0.visitArgs(var1);
      Expression[] var11 = var0.getArgs();
      if(var11.length == 3 && var11[1] instanceof QuoteExp) {
         Expression var9 = var11[0];
         String var4 = ((QuoteExp)var11[1]).getValue().toString();
         Type var5 = var9.getType();
         Compilation var6 = var1.getCompilation();
         Type var7 = var6.getLanguage().getTypeFor((Expression)var9);
         ClassType var10;
         if(var6 == null) {
            var10 = null;
         } else if(var6.curClass != null) {
            var10 = var6.curClass;
         } else {
            var10 = var6.mainClass;
         }

         ApplyExp var8;
         if(var7 instanceof ClassType) {
            var8 = new ApplyExp(SlotSet.set$Mnstatic$Mnfield$Ex, var11);
         } else {
            var8 = var0;
            if(var5 instanceof ClassType) {
               var8 = var0;
               if(SlotSet.lookupMember((ClassType)var5, var4, var10) != null) {
                  var8 = new ApplyExp(SlotSet.set$Mnfield$Ex, var11);
               }
            }
         }

         if(var8 != var0) {
            var8.setLine(var0);
         }

         var8.setType(Type.voidType);
         return var8;
      } else {
         return var0;
      }
   }
}
