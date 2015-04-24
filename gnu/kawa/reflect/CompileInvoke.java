package gnu.kawa.reflect;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.bytecode.Member;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.Keyword;
import gnu.expr.LetExp;
import gnu.expr.PairClassType;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.TypeValue;
import gnu.kawa.reflect.ArrayNew;
import gnu.kawa.reflect.ArraySet;
import gnu.kawa.reflect.ClassMethods;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotSet;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;

public class CompileInvoke {

   private static void append(PrimProcedure[] var0, int var1, StringBuffer var2) {
      for(int var3 = 0; var3 < var1; ++var3) {
         var2.append("\n  candidate: ");
         var2.append(var0[var3]);
      }

   }

   static Object[] checkKeywords(ObjectType var0, Expression[] var1, int var2, ClassType var3) {
      int var9 = var1.length;

      int var8;
      for(var8 = 0; var8 * 2 + var2 + 1 < var9 && var1[var8 * 2 + var2].valueIfConstant() instanceof Keyword; ++var8) {
         ;
      }

      Object[] var7 = new Object[var8];

      for(var9 = 0; var9 < var8; ++var9) {
         String var5 = ((Keyword)var1[var9 * 2 + var2].valueIfConstant()).getName();
         Member var6 = SlotSet.lookupMember(var0, var5, var3);
         Object var4 = var6;
         if(var6 == null) {
            var4 = var0.getMethod(ClassExp.slotToMethodName("add", var5), SlotSet.type1Array);
         }

         if(var4 == null) {
            var4 = var5;
         }

         var7[var9] = var4;
      }

      return var7;
   }

   private static String getMethodName(Expression[] var0, char var1) {
      if(var1 == 78) {
         return "<init>";
      } else {
         byte var2;
         if(var1 == 80) {
            var2 = 2;
         } else {
            var2 = 1;
         }

         return var0.length >= var2 + 1?ClassMethods.checkName(var0[var2], false):null;
      }
   }

   protected static PrimProcedure[] getMethods(ObjectType var0, String var1, ClassType var2, Invoke var3) {
      char var4 = 80;
      char var5 = var3.kind;
      if(var5 != 80) {
         if(var5 != 42 && var5 != 86) {
            var4 = 0;
         } else {
            var4 = 86;
         }
      }

      return ClassMethods.getMethods(var0, var1, var4, var2, var3.language);
   }

   public static PrimProcedure getStaticMethod(ClassType param0, String param1, Expression[] param2) {
      // $FF: Couldn't be decompiled
   }

   static int hasKeywordArgument(int var0, Expression[] var1) {
      while(var0 < var1.length) {
         if(var1[var0].valueIfConstant() instanceof Keyword) {
            return var0;
         }

         ++var0;
      }

      return var1.length;
   }

   private static long selectApplicable(PrimProcedure[] var0, ObjectType var1, Expression[] var2, int var3, int var4, int var5) {
      Type[] var6 = new Type[var3];
      var3 = 0;
      if(var5 >= 0) {
         var6[0] = var1;
         var3 = 0 + 1;
      }

      while(var4 < var2.length && var3 < var6.length) {
         Expression var7 = var2[var4];
         Object var8 = null;
         if(InlineCalls.checkIntValue(var7) != null) {
            var8 = Type.intType;
         } else if(InlineCalls.checkLongValue(var7) != null) {
            var8 = Type.longType;
         } else if(true) {
            var8 = var7.getType();
         }

         var6[var3] = (Type)var8;
         ++var4;
         ++var3;
      }

      return ClassMethods.selectApplicable(var0, var6);
   }

   public static Expression validateApplyInvoke(ApplyExp var0, InlineCalls var1, Type var2, Procedure var3) {
      Invoke var12 = (Invoke)var3;
      char var4 = var12.kind;
      Compilation var11 = var1.getCompilation();
      Expression[] var10 = var0.getArgs();
      int var24 = var10.length;
      if(var11.mustCompile && var24 != 0 && (var4 != 86 && var4 != 42 || var24 != 1)) {
         Expression var14 = var1.visit(var10[0], (Type)null);
         var10[0] = var14;
         Type var34;
         if(var4 != 86 && var4 != 42) {
            var34 = var12.language.getTypeFor((Expression)var14);
         } else {
            var34 = var14.getType();
         }

         Object var6;
         if(var34 instanceof PairClassType && var4 == 78) {
            var6 = ((PairClassType)var34).instanceType;
         } else if(var34 instanceof ObjectType) {
            var6 = (ObjectType)var34;
         } else {
            var6 = null;
         }

         String var8 = getMethodName(var10, var4);
         byte var15;
         byte var17;
         int var16;
         if(var4 != 86 && var4 != 42) {
            if(var4 == 78) {
               var16 = var24;
               var15 = 0;
               var17 = -1;
            } else if(var4 != 83 && var4 != 115) {
               if(var4 != 80) {
                  var0.visitArgs(var1);
                  return var0;
               }

               var16 = var24 - 2;
               var15 = 3;
               var17 = 1;
            } else {
               var16 = var24 - 2;
               var15 = 2;
               var17 = -1;
            }
         } else {
            var16 = var24 - 1;
            var15 = 2;
            var17 = 0;
         }

         int var18;
         Declaration var43;
         Type var40;
         Expression var63;
         int var69;
         int var66;
         if(var4 == 78 && var6 instanceof ArrayType) {
            ArrayType var41 = (ArrayType)var6;
            var40 = var41.getComponentType();
            var3 = null;
            boolean var72 = false;
            boolean var68 = var72;
            Expression var33 = var3;
            if(var10.length >= 3) {
               var68 = var72;
               var33 = var3;
               if(var10[1] instanceof QuoteExp) {
                  Object var61 = ((QuoteExp)var10[1]).getValue();
                  var68 = var72;
                  var33 = var3;
                  if(var61 instanceof Keyword) {
                     label268: {
                        String var62 = ((Keyword)var61).getName();
                        if(!"length".equals(var62)) {
                           var68 = var72;
                           var33 = var3;
                           if(!"size".equals(var62)) {
                              break label268;
                           }
                        }

                        var33 = var10[2];
                        var68 = true;
                     }
                  }
               }
            }

            Object var64 = var33;
            if(var33 == null) {
               var64 = QuoteExp.getInstance(new Integer(var10.length - 1));
            }

            var33 = var1.visit((Expression)var64, (Type)Type.intType);
            ApplyExp var36 = new ApplyExp(new ArrayNew(var40), new Expression[]{var33});
            var36.setType(var41);
            if(var68 && var10.length == 3) {
               return var36;
            } else {
               LetExp var59 = new LetExp(new Expression[]{var36});
               var43 = var59.addDeclaration((String)null, var41);
               var43.noteValue(var36);
               BeginExp var52 = new BeginExp();
               var16 = 0;
               if(var68) {
                  var66 = 3;
               } else {
                  var66 = 1;
               }

               while(var66 < var10.length) {
                  var63 = var10[var66];
                  var33 = var63;
                  var18 = var66;
                  var69 = var16;
                  if(var68) {
                     var33 = var63;
                     var18 = var66;
                     var69 = var16;
                     if(var66 + 1 < var10.length) {
                        var33 = var63;
                        var18 = var66;
                        var69 = var16;
                        if(var63 instanceof QuoteExp) {
                           Object var55 = ((QuoteExp)var63).getValue();
                           var33 = var63;
                           var18 = var66;
                           var69 = var16;
                           if(var55 instanceof Keyword) {
                              String var39 = ((Keyword)var55).getName();

                              try {
                                 var69 = Integer.parseInt(var39);
                              } catch (Throwable var30) {
                                 var11.error('e', "non-integer keyword \'" + var39 + "\' in array constructor");
                                 return var0;
                              }

                              var18 = var66 + 1;
                              var33 = var10[var18];
                           }
                        }
                     }
                  }

                  var33 = var1.visit(var33, (Type)var40);
                  var52.add(new ApplyExp(new ArraySet(var40), new Expression[]{new ReferenceExp(var43), QuoteExp.getInstance(new Integer(var69)), var33}));
                  var16 = var69 + 1;
                  var66 = var18 + 1;
               }

               var52.add(new ReferenceExp(var43));
               var59.body = var52;
               return var59;
            }
         } else {
            if(var6 != null && var8 != null) {
               if(var6 instanceof TypeValue && var4 == 78) {
                  var3 = ((TypeValue)var6).getConstructor();
                  if(var3 != null) {
                     Expression[] var32 = new Expression[var24 - 1];
                     System.arraycopy(var10, 1, var32, 0, var24 - 1);
                     return var1.visit(new ApplyExp(var3, var32), (Type)var2);
                  }
               }

               ClassType var7;
               if(var11 == null) {
                  var7 = null;
               } else if(var11.curClass != null) {
                  var7 = var11.curClass;
               } else {
                  var7 = var11.mainClass;
               }

               PrimProcedure[] var9;
               int var26;
               try {
                  var9 = getMethods((ObjectType)var6, var8, var7, var12);
                  var26 = ClassMethods.selectApplicable(var9, var16);
               } catch (Exception var31) {
                  var11.error('w', "unknown class: " + ((ObjectType)var6).getName());
                  return var0;
               }

               byte var25 = -1;
               Object var5;
               StringBuffer var44;
               ApplyExp var50;
               if(var4 == 78) {
                  var18 = hasKeywordArgument(1, var10);
                  if(var18 < var10.length || var26 <= 0 && ClassMethods.selectApplicable(var9, new Type[]{Compilation.typeClassType}) >> 32 == 1L) {
                     Object[] var13 = checkKeywords((ObjectType)var6, var10, var18, var7);
                     if(var13.length * 2 == var10.length - var18 || ClassMethods.selectApplicable(ClassMethods.getMethods((ObjectType)var6, "add", 'V', (ClassType)null, var12.language), 2) > 0) {
                        StringBuffer var56 = null;

                        for(var66 = 0; var66 < var13.length; var56 = var44) {
                           var44 = var56;
                           if(var13[var66] instanceof String) {
                              if(var56 == null) {
                                 var56 = new StringBuffer();
                                 var56.append("no field or setter ");
                              } else {
                                 var56.append(", ");
                              }

                              var56.append('`');
                              var56.append(var13[var66]);
                              var56.append('\'');
                              var44 = var56;
                           }

                           ++var66;
                        }

                        if(var56 != null) {
                           var56.append(" in class ");
                           var56.append(((ObjectType)var6).getName());
                           var11.error('w', var56.toString());
                           return var0;
                        }

                        if(var18 < var10.length) {
                           Expression[] var58 = new Expression[var18];
                           System.arraycopy(var10, 0, var58, 0, var18);
                           var50 = (ApplyExp)var1.visit(new ApplyExp(var0.getFunction(), var58), (Type)var6);
                        } else {
                           var50 = new ApplyExp(var9[0], new Expression[]{var14});
                        }

                        var50.setType((Type)var6);
                        var5 = var50;
                        if(var10.length > 0) {
                           var66 = 0;

                           ApplyExp var60;
                           for(var60 = var50; var66 < var13.length; ++var66) {
                              Object var48 = var13[var66];
                              if(var48 instanceof Method) {
                                 var34 = ((Method)var48).getParameterTypes()[0];
                              } else if(var48 instanceof Field) {
                                 var34 = ((Field)var48).getType();
                              } else {
                                 var34 = null;
                              }

                              Type var45 = var34;
                              if(var34 != null) {
                                 var45 = var12.language.getLangTypeFor(var34);
                              }

                              var63 = var1.visit(var10[var66 * 2 + var18 + 1], (Type)var45);
                              QuoteExp var57 = new QuoteExp(var48);
                              var60 = new ApplyExp(SlotSet.setFieldReturnObject, new Expression[]{var60, var57, var63});
                              var60.setType((Type)var6);
                           }

                           if(var18 == var10.length) {
                              var66 = 1;
                           } else {
                              var66 = var13.length * 2 + var18;
                           }

                           var5 = var60;
                           if(var66 < var10.length) {
                              var5 = new LetExp(new Expression[]{var60});
                              var43 = ((LetExp)var5).addDeclaration((String)null, (Type)var6);
                              var43.noteValue(var60);

                              BeginExp var65;
                              for(var65 = new BeginExp(); var66 < var10.length; ++var66) {
                                 ReferenceExp var54 = new ReferenceExp(var43);
                                 QuoteExp var46 = QuoteExp.getInstance("add");
                                 Expression var51 = var10[var66];
                                 var65.add(var1.visit(new ApplyExp(Invoke.invoke, new Expression[]{var54, var46, var51}), (Type)null));
                              }

                              var65.add(new ReferenceExp(var43));
                              ((LetExp)var5).body = var65;
                           }
                        }

                        return var1.checkType(((Expression)var5).setLine((Expression)var0), var2);
                     }
                  }
               }

               int var21;
               long var28;
               int var70;
               int var71;
               if(var26 >= 0) {
                  for(var18 = 1; var18 < var24; ++var18) {
                     var34 = null;
                     var5 = null;
                     boolean var19;
                     if(var18 == var24 - 1) {
                        var19 = true;
                     } else {
                        var19 = false;
                     }

                     if((var4 != 80 || var18 != 2) && (var4 == 78 || var18 != 1)) {
                        if(var4 == 80 && var18 == 1) {
                           var5 = var6;
                        } else if(var26 > 0) {
                           byte var20;
                           if(var4 == 78) {
                              var20 = 1;
                           } else {
                              var20 = var15;
                           }

                           var21 = 0;

                           while(true) {
                              var5 = var34;
                              if(var21 >= var26) {
                                 break;
                              }

                              PrimProcedure var37 = var9[var21];
                              byte var22;
                              if(var4 != 83 && var37.takesTarget()) {
                                 var22 = 1;
                              } else {
                                 var22 = 0;
                              }

                              var70 = var18 - var20 + var22;
                              if(var19 && var37.takesVarArgs() && var70 == var37.minArgs()) {
                                 var34 = null;
                              } else {
                                 var40 = var37.getParameterType(var70);
                                 if(var21 == 0) {
                                    var34 = var40;
                                 } else if(var40 instanceof PrimType != (var34 instanceof PrimType)) {
                                    var34 = null;
                                 } else {
                                    var34 = Type.lowestCommonSuperType(var34, var40);
                                 }
                              }

                              var5 = var34;
                              if(var34 == null) {
                                 break;
                              }

                              ++var21;
                           }
                        }
                     } else {
                        var5 = null;
                     }

                     var10[var18] = var1.visit(var10[var18], (Type)var5);
                  }

                  var28 = selectApplicable(var9, (ObjectType)var6, var10, var16, var15, var17);
                  var71 = (int)(var28 >> 32);
                  var69 = (int)var28;
               } else {
                  var71 = 0;
                  var69 = 0;
               }

               int var27 = var9.length;
               PrimProcedure[] var38 = var9;
               var70 = var16;
               byte var67 = var15;
               int var23 = var69;
               var21 = var71;
               if(var71 + var69 == 0) {
                  var38 = var9;
                  var70 = var16;
                  var67 = var15;
                  var23 = var69;
                  var21 = var71;
                  if(var4 == 78) {
                     var38 = getMethods((ObjectType)var6, "valueOf", var7, Invoke.invokeStatic);
                     var67 = 1;
                     var70 = var24 - 1;
                     var28 = selectApplicable(var38, (ObjectType)var6, var10, var70, 1, -1);
                     var21 = (int)(var28 >> 32);
                     var23 = (int)var28;
                  }
               }

               char var35;
               if(var21 + var23 == 0) {
                  label455: {
                     if(var4 != 80) {
                        var16 = var25;
                        if(!var11.warnInvokeUnknownMethod()) {
                           break label455;
                        }
                     }

                     String var42 = var8;
                     if(var4 == 78) {
                        var42 = var8 + "/valueOf";
                     }

                     StringBuilder var47 = new StringBuilder();
                     if(var38.length + var27 == 0) {
                        var47.append("no accessible method \'");
                     } else if(var26 == -983040) {
                        var47.append("too few arguments for method \'");
                     } else if(var26 == -917504) {
                        var47.append("too many arguments for method \'");
                     } else {
                        var47.append("no possibly applicable method \'");
                     }

                     var47.append(var42);
                     var47.append("\' in ");
                     var47.append(((ObjectType)var6).getName());
                     if(var4 == 80) {
                        var35 = 101;
                     } else {
                        var35 = 119;
                     }

                     var11.error(var35, var47.toString());
                     var16 = var25;
                  }
               } else if(var21 != 1 && (var21 != 0 || var23 != 1)) {
                  if(var21 > 0) {
                     var69 = MethodProc.mostSpecific(var38, var21);
                     var66 = var69;
                     if(var69 < 0) {
                        var66 = var69;
                        if(var4 == 83) {
                           var16 = 0;

                           while(true) {
                              var66 = var69;
                              if(var16 >= var21) {
                                 break;
                              }

                              var66 = var69;
                              if(var38[var16].getStaticFlag()) {
                                 if(var69 >= 0) {
                                    var66 = -1;
                                    break;
                                 }

                                 var66 = var16;
                              }

                              ++var16;
                              var69 = var66;
                           }
                        }
                     }

                     var16 = var66;
                     if(var66 < 0) {
                        label457: {
                           if(var4 != 80) {
                              var16 = var66;
                              if(!var11.warnInvokeUnknownMethod()) {
                                 break label457;
                              }
                           }

                           var44 = new StringBuffer();
                           var44.append("more than one definitely applicable method `");
                           var44.append(var8);
                           var44.append("\' in ");
                           var44.append(((ObjectType)var6).getName());
                           append(var38, var21, var44);
                           if(var4 == 80) {
                              var35 = 101;
                           } else {
                              var35 = 119;
                           }

                           var11.error(var35, var44.toString());
                           var16 = var66;
                        }
                     }
                  } else {
                     label458: {
                        if(var4 != 80) {
                           var16 = var25;
                           if(!var11.warnInvokeUnknownMethod()) {
                              break label458;
                           }
                        }

                        var44 = new StringBuffer();
                        var44.append("more than one possibly applicable method \'");
                        var44.append(var8);
                        var44.append("\' in ");
                        var44.append(((ObjectType)var6).getName());
                        append(var38, var23, var44);
                        if(var4 == 80) {
                           var35 = 101;
                        } else {
                           var35 = 119;
                        }

                        var11.error(var35, var44.toString());
                        var16 = var25;
                     }
                  }
               } else {
                  var16 = 0;
               }

               if(var16 >= 0) {
                  Expression[] var49 = new Expression[var70];
                  PrimProcedure var53 = var38[var16];
                  var53.takesVarArgs();
                  var66 = 0;
                  if(var17 >= 0) {
                     var49[0] = var10[var17];
                     var66 = 0 + 1;
                  }

                  for(var16 = var67; var16 < var10.length && var66 < var49.length; ++var66) {
                     var49[var66] = var10[var16];
                     ++var16;
                  }

                  var50 = new ApplyExp(var53, var49);
                  var50.setLine(var0);
                  return var1.visitApplyOnly(var50, var2);
               }
            }

            var0.visitArgs(var1);
            return var0;
         }
      } else {
         var0.visitArgs(var1);
         return var0;
      }
   }
}
