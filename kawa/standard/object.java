package kawa.standard;

import gnu.bytecode.Type;
import gnu.expr.BeginExp;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.ObjectExp;
import gnu.expr.QuoteExp;
import gnu.expr.SetExp;
import gnu.expr.ThisExp;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Namespace;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Lambda;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;
import kawa.standard.SchemeCompilation;

public class object extends Syntax {

   public static final Keyword accessKeyword;
   public static final Keyword allocationKeyword;
   public static final Keyword classNameKeyword;
   static final Symbol coloncolon;
   static final Keyword initKeyword;
   static final Keyword init_formKeyword;
   static final Keyword init_keywordKeyword;
   static final Keyword init_valueKeyword;
   static final Keyword initformKeyword;
   public static final Keyword interfaceKeyword;
   public static final object objectSyntax = new object(SchemeCompilation.lambda);
   public static final Keyword throwsKeyword;
   static final Keyword typeKeyword;
   Lambda lambda;


   static {
      objectSyntax.setName("object");
      accessKeyword = Keyword.make("access");
      classNameKeyword = Keyword.make("class-name");
      interfaceKeyword = Keyword.make("interface");
      throwsKeyword = Keyword.make("throws");
      typeKeyword = Keyword.make("type");
      allocationKeyword = Keyword.make("allocation");
      initKeyword = Keyword.make("init");
      initformKeyword = Keyword.make("initform");
      init_formKeyword = Keyword.make("init-form");
      init_valueKeyword = Keyword.make("init-value");
      init_keywordKeyword = Keyword.make("init-keyword");
      coloncolon = Namespace.EmptyNamespace.getSymbol("::");
   }

   public object(Lambda var1) {
      this.lambda = var1;
   }

   static long addAccessFlags(Object var0, long var1, long var3, String var5, Translator var6) {
      long var7 = matchAccess(var0, var6);
      if(var7 == 0L) {
         var6.error('e', "unknown access specifier " + var0);
      } else if((~var3 & var7) != 0L) {
         var6.error('e', "invalid " + var5 + " access specifier " + var0);
      } else if((var1 & var7) != 0L) {
         var6.error('w', "duplicate " + var5 + " access specifiers " + var0);
      }

      return var1 | var7;
   }

   static long matchAccess(Object var0, Translator var1) {
      while(var0 instanceof SyntaxForm) {
         var0 = ((SyntaxForm)var0).getDatum();
      }

      Object var2 = var0;
      if(var0 instanceof Pair) {
         Pair var3 = (Pair)var0;
         var0 = var1.matchQuoted((Pair)var0);
         var2 = var0;
         if(var0 instanceof Pair) {
            return matchAccess2((Pair)var0, var1);
         }
      }

      return matchAccess1(var2, var1);
   }

   private static long matchAccess1(Object var0, Translator var1) {
      Object var2;
      if(var0 instanceof Keyword) {
         var2 = ((Keyword)var0).getName();
      } else if(var0 instanceof FString) {
         var2 = ((FString)var0).toString();
      } else {
         var2 = var0;
         if(var0 instanceof SimpleSymbol) {
            var2 = var0.toString();
         }
      }

      return "private".equals(var2)?16777216L:("protected".equals(var2)?33554432L:("public".equals(var2)?67108864L:("package".equals(var2)?134217728L:("volatile".equals(var2)?2147483648L:("transient".equals(var2)?4294967296L:("enum".equals(var2)?8589934592L:("final".equals(var2)?17179869184L:0L)))))));
   }

   private static long matchAccess2(Pair var0, Translator var1) {
      long var2 = matchAccess1(var0.getCar(), var1);
      Object var6 = var0.getCdr();
      if(var6 != LList.Empty && var2 != 0L) {
         if(var6 instanceof Pair) {
            long var4 = matchAccess2((Pair)var6, var1);
            if(var4 != 0L) {
               return var2 | var4;
            }
         }

         return 0L;
      } else {
         return var2;
      }
   }

   static boolean matches(Object var0, String var1, Translator var2) {
      boolean var4 = false;
      boolean var3;
      String var5;
      if(var0 instanceof Keyword) {
         var5 = ((Keyword)var0).getName();
      } else if(var0 instanceof FString) {
         var5 = ((FString)var0).toString();
      } else {
         var3 = var4;
         if(!(var0 instanceof Pair)) {
            return var3;
         }

         var0 = var2.matchQuoted((Pair)var0);
         var3 = var4;
         if(!(var0 instanceof SimpleSymbol)) {
            return var3;
         }

         var5 = var0.toString();
      }

      if(var1 != null) {
         var3 = var4;
         if(!var1.equals(var5)) {
            return var3;
         }
      }

      var3 = true;
      return var3;
   }

   private static void rewriteInit(Object var0, ClassExp var1, Pair var2, Translator var3, SyntaxForm var4) {
      boolean var7;
      if(var0 instanceof Declaration) {
         var7 = ((Declaration)var0).getFlag(2048L);
      } else if(var0 == Boolean.TRUE) {
         var7 = true;
      } else {
         var7 = false;
      }

      LambdaExp var5;
      if(var7) {
         var5 = var1.clinitMethod;
      } else {
         var5 = var1.initMethod;
      }

      LambdaExp var6 = var5;
      if(var5 == null) {
         var6 = new LambdaExp(new BeginExp());
         var6.setClassMethod(true);
         var6.setReturnType(Type.voidType);
         if(var7) {
            var6.setName("$clinit$");
            var1.clinitMethod = var6;
         } else {
            var6.setName("$finit$");
            var1.initMethod = var6;
            var6.add((Declaration)null, new Declaration(ThisExp.THIS_NAME));
         }

         var6.nextSibling = var1.firstChild;
         var1.firstChild = var6;
      }

      var3.push(var6);
      LambdaExp var8 = var3.curMethodLambda;
      var3.curMethodLambda = var6;
      Expression var9 = var3.rewrite_car(var2, var4);
      if(var0 instanceof Declaration) {
         Declaration var10 = (Declaration)var0;
         var0 = new SetExp(var10, var9);
         ((SetExp)var0).setLocation(var10);
         var10.noteValue((Expression)null);
      } else {
         var0 = Compilation.makeCoercion(var9, (Expression)(new QuoteExp(Type.voidType)));
      }

      ((BeginExp)var6.body).add((Expression)var0);
      var3.curMethodLambda = var8;
      var3.pop(var6);
   }

   public void rewriteClassDef(Object[] param1, Translator param2) {
      // $FF: Couldn't be decompiled
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      Object var5;
      if(!(var1.getCdr() instanceof Pair)) {
         var5 = var2.syntaxError("missing superclass specification in object");
      } else {
         Pair var4 = (Pair)var1.getCdr();
         ObjectExp var3 = new ObjectExp();
         var1 = var4;
         if(var4.getCar() instanceof FString) {
            if(!(var4.getCdr() instanceof Pair)) {
               return var2.syntaxError("missing superclass specification after object class name");
            }

            var1 = (Pair)var4.getCdr();
         }

         Object[] var6 = this.scanClassDef(var1, var3, var2);
         var5 = var3;
         if(var6 != null) {
            this.rewriteClassDef(var6, var2);
            return var3;
         }
      }

      return (Expression)var5;
   }

   public Object[] scanClassDef(Pair var1, ClassExp var2, Translator var3) {
      var3.mustCompileHere();
      Object var18 = var1.getCar();
      Object var16 = var1.getCdr();
      Object var9 = null;
      LambdaExp var5 = null;
      LambdaExp var10 = null;
      long var29 = 0L;
      Vector var19 = new Vector(20);
      Object var35 = var16;

      while(var35 != LList.Empty) {
         while(var35 instanceof SyntaxForm) {
            var35 = ((SyntaxForm)var35).getDatum();
         }

         if(!(var35 instanceof Pair)) {
            var3.error('e', "object member not a list");
            return null;
         }

         Pair var6 = (Pair)var35;

         Object var4;
         for(var4 = var6.getCar(); var4 instanceof SyntaxForm; var4 = ((SyntaxForm)var4).getDatum()) {
            ;
         }

         var35 = var6.getCdr();
         Object var20 = var3.pushPositionOf(var6);
         Object var38 = var35;
         if(var4 instanceof Keyword) {
            while(var35 instanceof SyntaxForm) {
               var35 = ((SyntaxForm)var35).getDatum();
            }

            var38 = var35;
            if(var35 instanceof Pair) {
               if(var4 == interfaceKeyword) {
                  if(((Pair)var35).getCar() == Boolean.FALSE) {
                     var2.setFlag(65536);
                  } else {
                     var2.setFlag('耀');
                  }

                  var35 = ((Pair)var35).getCdr();
                  var3.popPositionOf(var20);
                  continue;
               }

               if(var4 == classNameKeyword) {
                  if(var9 != null) {
                     var3.error('e', "duplicate class-name specifiers");
                  }

                  var9 = var35;
                  var35 = ((Pair)var35).getCdr();
                  var3.popPositionOf(var20);
                  continue;
               }

               var38 = var35;
               if(var4 == accessKeyword) {
                  var4 = var3.pushPositionOf(var35);
                  var29 = addAccessFlags(((Pair)var35).getCar(), var29, 25820135424L, "class", var3);
                  if(var2.nameDecl == null) {
                     var3.error('e', "access specifier for anonymous class");
                  }

                  var3.popPositionOf(var4);
                  var35 = ((Pair)var35).getCdr();
                  var3.popPositionOf(var20);
                  continue;
               }
            }
         }

         if(!(var4 instanceof Pair)) {
            var3.error('e', "object member not a list");
            return null;
         }

         var35 = (Pair)var4;

         Object var11;
         for(var11 = ((Pair)var35).getCar(); var11 instanceof SyntaxForm; var11 = ((SyntaxForm)var11).getDatum()) {
            ;
         }

         Pair var37;
         if(!(var11 instanceof String) && !(var11 instanceof Symbol) && !(var11 instanceof Keyword)) {
            if(var11 instanceof Pair) {
               var37 = (Pair)var11;
               Object var40 = var37.getCar();
               if(!(var40 instanceof String) && !(var40 instanceof Symbol)) {
                  var3.error('e', "missing method name");
                  return null;
               }

               LambdaExp var41 = new LambdaExp();
               Translator.setLine((Declaration)var2.addMethod(var41, var40), var37);
               if(var10 == null) {
                  var5 = var41;
               } else {
                  var10.nextSibling = var41;
               }

               var10 = var41;
            } else {
               var3.error('e', "invalid field/method definition");
            }
         } else {
            Pair var12 = null;
            short var26 = 0;
            long var31 = 0L;
            Declaration var7;
            if(var11 instanceof Keyword) {
               var7 = null;
            } else {
               var7 = var2.addDeclaration(var11);
               var7.setSimple(false);
               var7.setFlag(1048576L);
               Translator.setLine((Declaration)var7, var35);
               var35 = ((Pair)var35).getCdr();
            }

            int var27 = 0;
            boolean var25 = false;
            Pair var13 = null;

            boolean var23;
            while(true) {
               var4 = var35;
               if(var35 == LList.Empty) {
                  break;
               }

               while(var35 instanceof SyntaxForm) {
                  var35 = ((SyntaxForm)var35).getDatum();
               }

               var1 = (Pair)var35;

               Object var14;
               for(var14 = var1.getCar(); var14 instanceof SyntaxForm; var14 = ((SyntaxForm)var14).getDatum()) {
                  ;
               }

               Object var21 = var3.pushPositionOf(var1);
               Object var8 = var1.getCdr();
               short var24;
               long var33;
               Pair var39;
               if((var14 == coloncolon || var14 instanceof Keyword) && var8 instanceof Pair) {
                  int var28 = var27 + 1;
                  Pair var15 = (Pair)var8;
                  Object var22 = var15.getCar();
                  Object var17 = var15.getCdr();
                  if(var14 != coloncolon && var14 != typeKeyword) {
                     if(var14 == allocationKeyword) {
                        if(var26 != 0) {
                           var3.error('e', "duplicate allocation: specification");
                        }

                        if(!matches(var22, "class", var3) && !matches(var22, "static", var3)) {
                           if(matches(var22, "instance", var3)) {
                              var24 = 4096;
                              var33 = var31;
                              var35 = var17;
                              var37 = var13;
                              var27 = var28;
                              var23 = var25;
                              var39 = var12;
                           } else {
                              var3.error('e', "unknown allocation kind \'" + var22 + "\'");
                              var33 = var31;
                              var24 = var26;
                              var35 = var17;
                              var37 = var13;
                              var27 = var28;
                              var23 = var25;
                              var39 = var12;
                           }
                        } else {
                           var24 = 2048;
                           var33 = var31;
                           var35 = var17;
                           var37 = var13;
                           var27 = var28;
                           var23 = var25;
                           var39 = var12;
                        }
                     } else if(var14 != initKeyword && var14 != initformKeyword && var14 != init_formKeyword && var14 != init_valueKeyword) {
                        if(var14 == init_keywordKeyword) {
                           if(!(var22 instanceof Keyword)) {
                              var3.error('e', "invalid \'init-keyword\' - not a keyword");
                              var33 = var31;
                              var24 = var26;
                              var35 = var17;
                              var37 = var13;
                              var27 = var28;
                              var23 = var25;
                              var39 = var12;
                           } else {
                              var33 = var31;
                              var24 = var26;
                              var35 = var17;
                              var37 = var13;
                              var27 = var28;
                              var23 = var25;
                              var39 = var12;
                              if(((Keyword)var22).getName() != var11.toString()) {
                                 var3.error('w', "init-keyword option ignored");
                                 var33 = var31;
                                 var24 = var26;
                                 var35 = var17;
                                 var37 = var13;
                                 var27 = var28;
                                 var23 = var25;
                                 var39 = var12;
                              }
                           }
                        } else if(var14 == accessKeyword) {
                           var35 = var3.pushPositionOf(var15);
                           var33 = addAccessFlags(var22, var31, 32463912960L, "field", var3);
                           var3.popPositionOf(var35);
                           var24 = var26;
                           var35 = var17;
                           var37 = var13;
                           var27 = var28;
                           var23 = var25;
                           var39 = var12;
                        } else {
                           var3.error('w', "unknown slot keyword \'" + var14 + "\'");
                           var33 = var31;
                           var24 = var26;
                           var35 = var17;
                           var37 = var13;
                           var27 = var28;
                           var23 = var25;
                           var39 = var12;
                        }
                     } else {
                        if(var25) {
                           var3.error('e', "duplicate initialization");
                        }

                        var25 = true;
                        var33 = var31;
                        var24 = var26;
                        var35 = var17;
                        var37 = var13;
                        var27 = var28;
                        var23 = var25;
                        var39 = var12;
                        if(var14 != initKeyword) {
                           var37 = var15;
                           var33 = var31;
                           var24 = var26;
                           var35 = var17;
                           var27 = var28;
                           var23 = var25;
                           var39 = var12;
                        }
                     }
                  } else {
                     var39 = var15;
                     var23 = var25;
                     var27 = var28;
                     var37 = var13;
                     var35 = var17;
                     var24 = var26;
                     var33 = var31;
                  }
               } else if(var8 == LList.Empty && !var25) {
                  var37 = var1;
                  var23 = true;
                  var33 = var31;
                  var24 = var26;
                  var35 = var8;
                  var39 = var12;
               } else {
                  label297: {
                     if(var8 instanceof Pair && var27 == 0 && !var25 && var12 == null) {
                        Pair var43 = (Pair)var8;
                        if(var43.getCdr() == LList.Empty) {
                           var39 = var1;
                           var37 = var43;
                           var35 = var43.getCdr();
                           var23 = true;
                           var33 = var31;
                           var24 = var26;
                           break label297;
                        }
                     }

                     var4 = null;
                     break;
                  }
               }

               var3.popPositionOf(var21);
               var31 = var33;
               var26 = var24;
               var13 = var37;
               var25 = var23;
               var12 = var39;
            }

            if(var4 != LList.Empty) {
               StringBuilder var36 = (new StringBuilder()).append("invalid argument list for slot \'").append(var11).append('\'').append(" args:");
               String var42;
               if(var4 == null) {
                  var42 = "null";
               } else {
                  var42 = var4.getClass().getName();
               }

               var3.error('e', var36.append(var42).toString());
               return null;
            }

            if(var25) {
               if(var26 == 2048) {
                  var23 = true;
               } else {
                  var23 = false;
               }

               if(var7 != null) {
                  var35 = var7;
               } else if(var23) {
                  var35 = Boolean.TRUE;
               } else {
                  var35 = Boolean.FALSE;
               }

               var19.addElement(var35);
               var19.addElement(var13);
            }

            if(var7 == null) {
               if(!var25) {
                  var3.error('e', "missing field name");
                  return null;
               }
            } else {
               if(var12 != null) {
                  var7.setType(var3.exp2Type(var12));
               }

               if(var26 != 0) {
                  var7.setFlag((long)var26);
               }

               if(var31 != 0L) {
                  var7.setFlag(var31);
               }

               var7.setCanRead(true);
               var7.setCanWrite(true);
            }
         }

         var3.popPositionOf(var20);
         var35 = var38;
      }

      if(var29 != 0L) {
         var2.nameDecl.setFlag(var29);
      }

      return new Object[]{var2, var16, var19, var5, var18, var9};
   }
}
