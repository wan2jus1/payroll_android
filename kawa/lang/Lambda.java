package kawa.lang;

import gnu.bytecode.Type;
import gnu.expr.BeginExp;
import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.LangExp;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.ThisExp;
import gnu.kawa.lispexpr.LangObjType;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.SyntaxForms;
import kawa.lang.TemplateScope;
import kawa.lang.Translator;
import kawa.standard.object;

public class Lambda extends Syntax {

   public static final Keyword nameKeyword = Keyword.make("name");
   public Expression defaultDefault;
   public Object keyKeyword;
   public Object optionalKeyword;
   public Object restKeyword;


   public Lambda() {
      this.defaultDefault = QuoteExp.falseExp;
   }

   private static void addParam(Declaration var0, ScopeExp var1, LambdaExp var2, Translator var3) {
      Declaration var4 = var0;
      if(var1 != null) {
         var4 = var3.makeRenamedAlias(var0, var1);
      }

      var2.addDeclaration(var4);
      if(var1 != null) {
         var4.context = var1;
      }

   }

   public void print(Consumer var1) {
      var1.write("#<builtin lambda>");
   }

   public Expression rewrite(Object var1, Translator var2) {
      if(!(var1 instanceof Pair)) {
         var1 = var2.syntaxError("missing formals in lambda");
      } else {
         int var4 = var2.getMessages().getErrorCount();
         LambdaExp var3 = new LambdaExp();
         Pair var5 = (Pair)var1;
         Translator.setLine((Expression)var3, var5);
         this.rewrite(var3, var5.getCar(), var5.getCdr(), var2, (TemplateScope)null);
         var1 = var3;
         if(var2.getMessages().getErrorCount() > var4) {
            return new ErrorExp("bad lambda expression");
         }
      }

      return (Expression)var1;
   }

   public void rewrite(LambdaExp var1, Object var2, Object var3, Translator var4, TemplateScope var5) {
      this.rewriteFormals(var1, var2, var4, var5);
      if(var3 instanceof PairWithPosition) {
         var1.setFile(((PairWithPosition)var3).getFileName());
      }

      this.rewriteBody(var1, this.rewriteAttrs(var1, var3, var4), var4);
   }

   public Object rewriteAttrs(LambdaExp var1, Object var2, Translator var3) {
      String var7 = null;
      String var6 = null;
      int var12 = 0;
      short var11 = 0;
      SyntaxForm var4 = null;

      int var23;
      while(true) {
         while(var2 instanceof SyntaxForm) {
            var4 = (SyntaxForm)var2;
            var2 = var4.getDatum();
         }

         if(!(var2 instanceof Pair)) {
            break;
         }

         Pair var9 = (Pair)var2;
         Object var5 = Translator.stripSyntax(var9.getCar());
         Object var8;
         if(var3.matches(var5, "::")) {
            var8 = null;
         } else {
            var8 = var5;
            if(!(var5 instanceof Keyword)) {
               break;
            }
         }

         SyntaxForm var17 = var4;

         Object var20;
         for(var20 = var9.getCdr(); var20 instanceof SyntaxForm; var20 = var17.getDatum()) {
            var17 = (SyntaxForm)var20;
         }

         if(!(var20 instanceof Pair)) {
            break;
         }

         var9 = (Pair)var20;
         short var10;
         int var13;
         String var15;
         String var18;
         if(var8 == null) {
            if(var1.isClassMethod() && "*init*".equals(var1.getName())) {
               var3.error('e', "explicit return type for \'*init*\' method");
               var18 = var6;
               var10 = var11;
               var15 = var7;
               var13 = var12;
            } else {
               var1.body = new LangExp(new Object[]{var9, var17});
               var13 = var12;
               var15 = var7;
               var10 = var11;
               var18 = var6;
            }
         } else {
            Expression var16;
            if(var8 == object.accessKeyword) {
               label141: {
                  var16 = var3.rewrite_car(var9, var17);
                  if(var16 instanceof QuoteExp) {
                     var2 = ((QuoteExp)var16).getValue();
                     if(var2 instanceof SimpleSymbol || var2 instanceof CharSequence) {
                        if(var1.nameDecl == null) {
                           var3.error('e', "access: not allowed for anonymous function");
                           var13 = var12;
                           var15 = var7;
                           var10 = var11;
                           var18 = var6;
                        } else {
                           var15 = var2.toString();
                           if("private".equals(var15)) {
                              var23 = 16777216;
                           } else if("protected".equals(var15)) {
                              var23 = 33554432;
                           } else if("public".equals(var15)) {
                              var23 = 67108864;
                           } else if("package".equals(var15)) {
                              var23 = 134217728;
                           } else {
                              var3.error('e', "unknown access specifier");
                              var23 = var12;
                           }

                           if(var7 != null && var15 != null) {
                              var3.error('e', "duplicate access specifiers - " + var7 + " and " + var15);
                           }

                           var13 = var23;
                           var10 = var11;
                           var18 = var6;
                        }
                        break label141;
                     }
                  }

                  var3.error('e', "access: value not a constant symbol or string");
                  var13 = var12;
                  var15 = var7;
                  var10 = var11;
                  var18 = var6;
               }
            } else if(var8 == object.allocationKeyword) {
               label133: {
                  var16 = var3.rewrite_car(var9, var17);
                  if(var16 instanceof QuoteExp) {
                     var2 = ((QuoteExp)var16).getValue();
                     if(var2 instanceof SimpleSymbol || var2 instanceof CharSequence) {
                        if(var1.nameDecl == null) {
                           var3.error('e', "allocation: not allowed for anonymous function");
                           var13 = var12;
                           var15 = var7;
                           var10 = var11;
                           var18 = var6;
                        } else {
                           var18 = var2.toString();
                           if(!"class".equals(var18) && !"static".equals(var18)) {
                              if("instance".equals(var18)) {
                                 var10 = 4096;
                              } else {
                                 var3.error('e', "unknown allocation specifier");
                                 var10 = var11;
                              }
                           } else {
                              var10 = 2048;
                           }

                           if(var6 != null && var18 != null) {
                              var3.error('e', "duplicate allocation specifiers - " + var6 + " and " + var18);
                           }

                           var13 = var12;
                           var15 = var7;
                        }
                        break label133;
                     }
                  }

                  var3.error('e', "allocation: value not a constant symbol or string");
                  var13 = var12;
                  var15 = var7;
                  var10 = var11;
                  var18 = var6;
               }
            } else if(var8 == object.throwsKeyword) {
               var2 = var9.getCar();
               var13 = Translator.listLength(var2);
               if(var13 < 0) {
                  var3.error('e', "throws: not followed by a list");
                  var13 = var12;
                  var15 = var7;
                  var10 = var11;
                  var18 = var6;
               } else {
                  Expression[] var22 = new Expression[var13];

                  for(var23 = 0; var23 < var13; ++var23) {
                     while(var2 instanceof SyntaxForm) {
                        var17 = (SyntaxForm)var2;
                        var2 = var17.getDatum();
                     }

                     Pair var19 = (Pair)var2;
                     var22[var23] = var3.rewrite_car(var19, var17);
                     Translator.setLine((Expression)var22[var23], var19);
                     var2 = var19.getCdr();
                  }

                  var1.setExceptions(var22);
                  var13 = var12;
                  var15 = var7;
                  var10 = var11;
                  var18 = var6;
               }
            } else if(var8 == nameKeyword) {
               Expression var21 = var3.rewrite_car(var9, var17);
               var13 = var12;
               var15 = var7;
               var10 = var11;
               var18 = var6;
               if(var21 instanceof QuoteExp) {
                  var1.setName(((QuoteExp)var21).getValue().toString());
                  var13 = var12;
                  var15 = var7;
                  var10 = var11;
                  var18 = var6;
               }
            } else {
               var3.error('w', "unknown procedure property " + var8);
               var13 = var12;
               var15 = var7;
               var10 = var11;
               var18 = var6;
            }
         }

         var8 = var9.getCdr();
         var12 = var13;
         var7 = var15;
         var11 = var10;
         var6 = var18;
         var2 = var8;
      }

      var23 = var12 | var11;
      if(var23 != 0) {
         var1.nameDecl.setFlag((long)var23);
      }

      Object var14 = var2;
      if(var4 != null) {
         var14 = SyntaxForms.fromDatumIfNeeded(var2, var4);
      }

      return var14;
   }

   public void rewriteBody(LambdaExp var1, Object var2, Translator var3) {
      int var8 = 0;
      if(var3.curMethodLambda == null && var1.nameDecl != null && var3.getModule().getFlag(131072)) {
         var3.curMethodLambda = var1;
      }

      var3.currentScope();
      var3.pushScope(var1);
      Declaration var6 = null;
      int var7;
      if(var1.keywords == null) {
         var7 = 0;
      } else {
         var7 = var1.keywords.length;
      }

      if(var1.defaultArgs == null) {
         var7 = 0;
      } else {
         var7 = var1.defaultArgs.length - var7;
      }

      int var10 = 0;
      int var9 = 0;

      int var12;
      Expression var14;
      for(Declaration var4 = var1.firstDecl(); var4 != null; var9 = var12) {
         Declaration var5 = var4;
         int var11 = var8;
         if(var4.isAlias()) {
            var5 = Translator.getOriginalRef(var4).getBinding();
            var1.replaceFollowing(var6, var5);
            var5.context = var1;
            var3.pushRenamedAlias(var4);
            var11 = var8 + 1;
         }

         var14 = var5.getTypeExp();
         if(var14 instanceof LangExp) {
            var5.setType(var3.exp2Type((Pair)((LangExp)var14).getLangValue()));
         }

         var6 = var5;
         var12 = var9;
         if(var10 >= var1.min_args) {
            label78: {
               if(var10 >= var1.min_args + var7 && var1.max_args < 0) {
                  var12 = var9;
                  if(var10 == var1.min_args + var7) {
                     break label78;
                  }
               }

               var1.defaultArgs[var9] = var3.rewrite(var1.defaultArgs[var9]);
               var12 = var9 + 1;
            }
         }

         ++var10;
         var3.lexical.push((Declaration)var5);
         var4 = var5.nextDecl();
         var8 = var11;
      }

      if(var1.isClassMethod() && !var1.nameDecl.getFlag(2048L)) {
         var1.add((Declaration)null, new Declaration(ThisExp.THIS_NAME));
      }

      LambdaExp var17 = var3.curLambda;
      var3.curLambda = var1;
      Type var16 = var1.returnType;
      if(var1.body instanceof LangExp) {
         Object[] var15 = (Object[])((Object[])((LangExp)var1.body).getLangValue());
         var14 = var3.rewrite_car((Pair)var15[0], (SyntaxForm)var15[1]);
         var16 = var3.getLanguage().getTypeFor((Expression)var14);
      }

      label67: {
         var1.body = var3.rewrite_body(var2);
         var3.curLambda = var17;
         if(var1.body instanceof BeginExp) {
            Expression[] var13 = ((BeginExp)var1.body).getExpressions();
            var7 = var13.length;
            if(var7 > 1) {
               label90: {
                  if(!(var13[0] instanceof ReferenceExp)) {
                     Object var19 = var13[0].valueIfConstant();
                     if(!(var19 instanceof Type) && !(var19 instanceof Class)) {
                        break label90;
                     }
                  }

                  var14 = var13[0];
                  --var7;
                  if(var7 == 1) {
                     var1.body = var13[1];
                  } else {
                     Expression[] var18 = new Expression[var7];
                     System.arraycopy(var13, 1, var18, 0, var7);
                     var1.body = BeginExp.canonicalize((Expression[])var18);
                  }

                  var1.setCoercedReturnValue(var14, var3.getLanguage());
                  break label67;
               }
            }
         }

         var1.setCoercedReturnType(var16);
      }

      var3.pop(var1);
      var1.countDecls();
      var3.popRenamedAlias(var8);
      var1.countDecls();
      if(var3.curMethodLambda == var1) {
         var3.curMethodLambda = null;
      }

   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      Expression var3 = this.rewrite(var1.getCdr(), var2);
      Translator.setLine((Expression)var3, var1);
      return var3;
   }

   public void rewriteFormals(LambdaExp var1, Object var2, Translator var3, TemplateScope var4) {
      String var5;
      int var20;
      if(var1.getSymbol() == null) {
         var5 = var1.getFileName();
         var20 = var1.getLineNumber();
         if(var5 != null && var20 > 0) {
            var1.setSourceLocation(var5, var20);
         }
      }

      Object var27 = var2;
      int var21 = -1;
      int var22 = -1;
      var20 = -1;

      while(true) {
         Object var6 = var27;
         if(var27 instanceof SyntaxForm) {
            var6 = ((SyntaxForm)var27).getDatum();
         }

         Object var7;
         Pair var29;
         if(!(var6 instanceof Pair)) {
            if(var6 instanceof Symbol) {
               if(var21 >= 0 || var20 >= 0 || var22 >= 0) {
                  var3.syntaxError("dotted rest-arg after " + this.optionalKeyword + ", " + this.restKeyword + ", or " + this.keyKeyword);
                  return;
               }

               var22 = 1;
            } else if(var6 != LList.Empty) {
               var3.syntaxError("misformed formals in lambda");
               return;
            }

            if(var22 > 1) {
               var3.syntaxError("multiple " + this.restKeyword + " parameters");
               return;
            }

            int var23 = var21;
            if(var21 < 0) {
               var23 = 0;
            }

            var21 = var22;
            if(var22 < 0) {
               var21 = 0;
            }

            var22 = var20;
            if(var20 < 0) {
               var22 = 0;
            }

            if(var21 > 0) {
               var1.max_args = -1;
            } else {
               var1.max_args = var1.min_args + var23 + var22 * 2;
            }

            if(var23 + var22 > 0) {
               var1.defaultArgs = new Expression[var23 + var22];
            }

            if(var22 > 0) {
               var1.keywords = new Keyword[var22];
            }

            var22 = 0;
            var20 = 0;
            Object var12 = null;
            TemplateScope var24 = var4;
            Object var26 = var2;

            while(true) {
               var6 = var26;
               SyntaxForm var25;
               if(var26 instanceof SyntaxForm) {
                  var25 = (SyntaxForm)var26;
                  var6 = var25.getDatum();
                  var24 = var25.getScope();
               }

               TemplateScope var34 = var24;
               if(!(var6 instanceof Pair)) {
                  var26 = var6;
                  if(var6 instanceof SyntaxForm) {
                     var25 = (SyntaxForm)var6;
                     var26 = var25.getDatum();
                     var24 = var25.getScope();
                  }

                  if(var26 instanceof Symbol) {
                     Declaration var36 = new Declaration(var26);
                     var36.setType(LangObjType.listType);
                     var36.setFlag(262144L);
                     var36.noteValue((Expression)null);
                     addParam(var36, var24, var1, var3);
                     return;
                  }

                  return;
               }

               Pair var13 = (Pair)var6;
               var7 = var13.getCar();
               var26 = var7;
               SyntaxForm var35;
               if(var7 instanceof SyntaxForm) {
                  var35 = (SyntaxForm)var7;
                  var26 = var35.getDatum();
                  var34 = var35.getScope();
               }

               Pair var8;
               if(var26 != this.optionalKeyword && var26 != this.restKeyword && var26 != this.keyKeyword) {
                  Object var18 = var3.pushPositionOf(var13);
                  Object var16 = null;
                  Expression var14 = this.defaultDefault;
                  Pair var10 = null;
                  Object var11 = null;
                  if(var3.matches(var26, "::")) {
                     var3.syntaxError("\'::\' must follow parameter name");
                     return;
                  }

                  var7 = var3.namespaceResolve(var26);
                  Object var9;
                  TemplateScope var15;
                  Object var17;
                  Pair var28;
                  if(var7 instanceof Symbol) {
                     var17 = var14;
                     var9 = var7;
                     var8 = var13;
                     var15 = var34;
                     var28 = (Pair)var11;
                     if(var13.getCdr() instanceof Pair) {
                        var10 = (Pair)var13.getCdr();
                        var17 = var14;
                        var9 = var7;
                        var8 = var13;
                        var15 = var34;
                        var28 = (Pair)var11;
                        if(var3.matches(var10.getCar(), "::")) {
                           if(!(var13.getCdr() instanceof Pair)) {
                              var3.syntaxError("\'::\' not followed by a type specifier (for parameter \'" + var7 + "\')");
                              return;
                           }

                           var8 = (Pair)var10.getCdr();
                           var28 = var8;
                           var15 = var34;
                           var9 = var7;
                           var17 = var14;
                        }
                     }
                  } else {
                     var17 = var14;
                     var9 = var16;
                     var8 = var13;
                     var15 = var34;
                     var28 = (Pair)var11;
                     if(var7 instanceof Pair) {
                        Pair var19 = (Pair)var7;
                        Object var33 = var19.getCar();
                        var26 = var33;
                        TemplateScope var31 = var34;
                        if(var33 instanceof SyntaxForm) {
                           var35 = (SyntaxForm)var33;
                           var26 = var35.getDatum();
                           var31 = var35.getScope();
                        }

                        var27 = var3.namespaceResolve(var26);
                        var17 = var14;
                        var9 = var16;
                        var8 = var13;
                        var15 = var31;
                        var28 = (Pair)var11;
                        if(var27 instanceof Symbol) {
                           var17 = var14;
                           var9 = var16;
                           var8 = var13;
                           var15 = var31;
                           var28 = (Pair)var11;
                           if(var19.getCdr() instanceof Pair) {
                              var29 = (Pair)var19.getCdr();
                              var28 = var29;
                              if(var3.matches(var29.getCar(), "::")) {
                                 if(!(var29.getCdr() instanceof Pair)) {
                                    var3.syntaxError("\'::\' not followed by a type specifier (for parameter \'" + var27 + "\')");
                                    return;
                                 }

                                 var28 = (Pair)var29.getCdr();
                                 var10 = var28;
                                 if(var28.getCdr() instanceof Pair) {
                                    var28 = (Pair)var28.getCdr();
                                 } else {
                                    if(var28.getCdr() != LList.Empty) {
                                       var3.syntaxError("improper list in specifier for parameter \'" + var27 + "\')");
                                       return;
                                    }

                                    var28 = null;
                                 }
                              }

                              var11 = var14;
                              var29 = var28;
                              if(var28 != null) {
                                 var11 = var14;
                                 var29 = var28;
                                 if(var12 != null) {
                                    var11 = var28.getCar();
                                    if(var28.getCdr() instanceof Pair) {
                                       var29 = (Pair)var28.getCdr();
                                    } else {
                                       if(var28.getCdr() != LList.Empty) {
                                          var3.syntaxError("improper list in specifier for parameter \'" + var27 + "\')");
                                          return;
                                       }

                                       var29 = null;
                                    }
                                 }
                              }

                              var17 = var11;
                              var9 = var27;
                              var8 = var13;
                              var15 = var31;
                              var28 = var10;
                              if(var29 != null) {
                                 if(var10 != null) {
                                    var3.syntaxError("duplicate type specifier for parameter \'" + var27 + '\'');
                                    return;
                                 }

                                 var28 = var29;
                                 var17 = var11;
                                 var9 = var27;
                                 var8 = var13;
                                 var15 = var31;
                                 if(var29.getCdr() != LList.Empty) {
                                    var3.syntaxError("junk at end of specifier for parameter \'" + var27 + '\'' + " after type " + var29.getCar());
                                    return;
                                 }
                              }
                           }
                        }
                     }
                  }

                  if(var9 == null) {
                     var3.syntaxError("parameter is neither name nor (name :: type) nor (name default): " + var8);
                     return;
                  }

                  label210: {
                     if(var12 != this.optionalKeyword) {
                        var21 = var22;
                        if(var12 != this.keyKeyword) {
                           break label210;
                        }
                     }

                     var1.defaultArgs[var22] = new LangExp(var17);
                     var21 = var22 + 1;
                  }

                  var22 = var20;
                  if(var12 == this.keyKeyword) {
                     Keyword[] var32 = var1.keywords;
                     if(var9 instanceof Symbol) {
                        var5 = ((Symbol)var9).getName();
                     } else {
                        var5 = var9.toString();
                     }

                     var32[var20] = Keyword.make(var5);
                     var22 = var20 + 1;
                  }

                  Declaration var37 = new Declaration(var9);
                  Translator.setLine((Declaration)var37, var6);
                  if(var28 != null) {
                     var37.setTypeExp(new LangExp(var28));
                     var37.setFlag(8192L);
                  } else if(var12 == this.restKeyword) {
                     var37.setType(LangObjType.listType);
                  }

                  var37.setFlag(262144L);
                  var37.noteValue((Expression)null);
                  addParam(var37, var15, var1, var3);
                  var3.popPositionOf(var18);
                  var20 = var22;
               } else {
                  var8 = var13;
                  var21 = var22;
                  var12 = var26;
               }

               var26 = var8.getCdr();
               var22 = var21;
            }
         }

         Pair var30 = (Pair)var6;
         var7 = var30.getCar();
         var27 = var7;
         if(var7 instanceof SyntaxForm) {
            var27 = ((SyntaxForm)var7).getDatum();
         }

         if(var27 == this.optionalKeyword) {
            if(var21 >= 0) {
               var3.syntaxError("multiple " + this.optionalKeyword + " in parameter list");
               return;
            }

            if(var22 >= 0 || var20 >= 0) {
               var3.syntaxError(this.optionalKeyword.toString() + " after " + this.restKeyword + " or " + this.keyKeyword);
               return;
            }

            var21 = 0;
            var29 = var30;
         } else if(var27 == this.restKeyword) {
            if(var22 >= 0) {
               var3.syntaxError("multiple " + this.restKeyword + " in parameter list");
               return;
            }

            if(var20 >= 0) {
               var3.syntaxError(this.restKeyword.toString() + " after " + this.keyKeyword);
               return;
            }

            var22 = 0;
            var29 = var30;
         } else if(var27 == this.keyKeyword) {
            if(var20 >= 0) {
               var3.syntaxError("multiple " + this.keyKeyword + " in parameter list");
               return;
            }

            var20 = 0;
            var29 = var30;
         } else if(var3.matches(var30.getCar(), "::") && var30.getCdr() instanceof Pair) {
            var29 = (Pair)var30.getCdr();
         } else if(var20 >= 0) {
            ++var20;
            var29 = var30;
         } else if(var22 >= 0) {
            ++var22;
            var29 = var30;
         } else if(var21 >= 0) {
            ++var21;
            var29 = var30;
         } else {
            ++var1.min_args;
            var29 = var30;
         }

         var29.getCdr();
         var27 = var29.getCdr();
      }
   }

   public void setKeywords(Object var1, Object var2, Object var3) {
      this.optionalKeyword = var1;
      this.restKeyword = var2;
      this.keyKeyword = var3;
   }

   public Object skipAttrs(LambdaExp var1, Object var2, Translator var3) {
      while(true) {
         if(var2 instanceof Pair) {
            Pair var5 = (Pair)var2;
            if(var5.getCdr() instanceof Pair) {
               Object var4 = var5.getCar();
               if(!var3.matches(var4, "::") && !(var4 instanceof Keyword)) {
                  return var2;
               }

               var2 = ((Pair)var5.getCdr()).getCdr();
               continue;
            }
         }

         return var2;
      }
   }
}
