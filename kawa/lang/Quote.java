package kawa.lang;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.kawa.functions.CompileNamedPart;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import java.util.IdentityHashMap;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.SyntaxForms;
import kawa.lang.Translator;

public class Quote extends Syntax {

   private static final Object CYCLE = new String("(cycle)");
   protected static final int QUOTE_DEPTH = -1;
   private static final Object WORKING = new String("(working)");
   static final Method appendMethod = quoteType.getDeclaredMethod("append$V", 1);
   static final Method consXMethod = quoteType.getDeclaredMethod("consX$V", 1);
   static final Method makePairMethod = Compilation.typePair.getDeclaredMethod("make", 2);
   static final Method makeVectorMethod = ClassType.make("gnu.lists.FVector").getDeclaredMethod("make", 1);
   public static final Quote plainQuote = new Quote("quote", false);
   public static final Quote quasiQuote = new Quote("quasiquote", true);
   static final ClassType quoteType = ClassType.make("kawa.lang.Quote");
   static final Method vectorAppendMethod = ClassType.make("kawa.standard.vector_append").getDeclaredMethod("apply$V", 1);
   protected boolean isQuasi;


   public Quote(String var1, boolean var2) {
      super(var1);
      this.isQuasi = var2;
   }

   public static Object append$V(Object[] var0) {
      int var8 = var0.length;
      Object var2;
      if(var8 == 0) {
         var2 = LList.Empty;
      } else {
         Object var1 = var0[var8 - 1];
         --var8;

         label43:
         while(true) {
            --var8;
            var2 = var1;
            if(var8 < 0) {
               break;
            }

            Object var3 = var0[var8];
            Pair var5 = null;
            SyntaxForm var4 = null;
            var2 = null;

            while(true) {
               while(!(var3 instanceof SyntaxForm)) {
                  if(var3 == LList.Empty) {
                     if(var5 != null) {
                        var5.setCdr(var1);
                     } else {
                        var2 = var1;
                     }

                     var1 = var2;
                     continue label43;
                  }

                  Pair var7 = (Pair)var3;
                  Object var6 = var7.getCar();
                  var3 = var6;
                  if(var4 != null) {
                     var3 = var6;
                     if(!(var6 instanceof SyntaxForm)) {
                        var3 = SyntaxForms.makeForm(var6, var4.getScope());
                     }
                  }

                  Pair var9 = new Pair(var3, (Object)null);
                  if(var5 == null) {
                     var2 = var9;
                  } else {
                     var5.setCdr(var9);
                  }

                  var6 = var7.getCdr();
                  var5 = var9;
                  var3 = var6;
               }

               var4 = (SyntaxForm)var3;
               var3 = var4.getDatum();
            }
         }
      }

      return var2;
   }

   public static Object consX$V(Object[] var0) {
      return LList.consX(var0);
   }

   private static ApplyExp makeInvokeMakeVector(Expression[] var0) {
      return new ApplyExp(makeVectorMethod, var0);
   }

   public static Symbol makeSymbol(Namespace var0, Object var1) {
      String var2;
      if(var1 instanceof CharSequence) {
         var2 = ((CharSequence)var1).toString();
      } else {
         var2 = (String)var1;
      }

      return var0.getSymbol(var2.intern());
   }

   public static Object quote(Object var0) {
      return plainQuote.expand(var0, -1, (Translator)Compilation.getCurrent());
   }

   public static Object quote(Object var0, Translator var1) {
      return plainQuote.expand(var0, -1, var1);
   }

   protected Expression coerceExpression(Object var1, Translator var2) {
      return var1 instanceof Expression?(Expression)var1:this.leaf(var1, var2);
   }

   Object expand(Object var1, int var2, SyntaxForm var3, Object var4, Translator var5) {
      IdentityHashMap var7 = (IdentityHashMap)var4;
      Object var6 = var7.get(var1);
      if(var6 == WORKING) {
         var7.put(var1, CYCLE);
      } else if(var6 != CYCLE && var6 == null) {
         Object var17;
         if(var1 instanceof Pair) {
            var17 = this.expand_pair((Pair)var1, var2, var3, var4, var5);
         } else if(var1 instanceof SyntaxForm) {
            var3 = (SyntaxForm)var1;
            var17 = this.expand(var3.getDatum(), var2, var3, var4, var5);
         } else if(var1 instanceof FVector) {
            FVector var19 = (FVector)var1;
            int var16 = var19.size();
            Object[] var8 = new Object[var16];
            byte[] var9 = new byte[var16];
            byte var12 = 0;
            int var13 = 0;

            while(true) {
               if(var13 >= var16) {
                  if(var12 == 0) {
                     var17 = var19;
                  } else if(var12 == 1) {
                     var17 = new FVector(var8);
                  } else {
                     Expression[] var18 = new Expression[var16];

                     for(var2 = 0; var2 < var16; ++var2) {
                        if(var9[var2] == 3) {
                           var18[var2] = (Expression)var8[var2];
                        } else if(var12 < 3) {
                           var18[var2] = this.coerceExpression(var8[var2], var5);
                        } else if(var9[var2] < 2) {
                           var18[var2] = this.leaf(new FVector(new Object[]{var8[var2]}), var5);
                        } else {
                           var18[var2] = makeInvokeMakeVector(new Expression[]{(Expression)var8[var2]});
                        }
                     }

                     if(var12 < 3) {
                        var17 = makeInvokeMakeVector(var18);
                     } else {
                        var17 = new ApplyExp(vectorAppendMethod, var18);
                     }
                  }
                  break;
               }

               label88: {
                  Object var11 = var19.get(var13);
                  int var14 = var2;
                  if(var11 instanceof Pair) {
                     var14 = var2;
                     if(var2 > -1) {
                        Pair var10 = (Pair)var11;
                        var14 = var2;
                        if(var5.matches(var10.getCar(), var3, (String)"unquote-splicing")) {
                           int var15 = var2 - 1;
                           var14 = var15;
                           if(var15 == 0) {
                              if(var10.getCdr() instanceof Pair) {
                                 Pair var20 = (Pair)var10.getCdr();
                                 if(var20.getCdr() == LList.Empty) {
                                    var8[var13] = var5.rewrite_car(var20, var3);
                                    var9[var13] = 3;
                                    break label88;
                                 }
                              }

                              return var5.syntaxError("invalid used of " + var10.getCar() + " in quasiquote template");
                           }
                        }
                     }
                  }

                  var8[var13] = this.expand(var11, var14, var3, var4, var5);
                  if(var8[var13] == var11) {
                     var9[var13] = 0;
                  } else if(var8[var13] instanceof Expression) {
                     var9[var13] = 2;
                  } else {
                     var9[var13] = 1;
                  }
               }

               byte var21 = var12;
               if(var9[var13] > var12) {
                  var21 = var9[var13];
               }

               ++var13;
               var12 = var21;
            }
         } else {
            var17 = var1;
         }

         if(var1 != var17 && var7.get(var1) == CYCLE) {
            var5.error('e', "cycle in non-literal data");
         }

         var7.put(var1, var17);
         return var17;
      }

      return var6;
   }

   protected Object expand(Object var1, int var2, Translator var3) {
      return this.expand(var1, var2, (SyntaxForm)null, new IdentityHashMap(), var3);
   }

   protected boolean expandColonForms() {
      return true;
   }

   Object expand_pair(Pair var1, int var2, SyntaxForm var3, Object var4, Translator var5) {
      Pair var6 = var1;

      Pair var7;
      int var11;
      Object var13;
      label170:
      while(true) {
         Expression var15;
         if(this.expandColonForms() && var6 == var1 && var5.matches(var6.getCar(), var3, (Symbol)LispLanguage.lookup_sym) && var6.getCdr() instanceof Pair) {
            var7 = (Pair)var6.getCdr();
            if(var7 instanceof Pair) {
               Pair var8 = (Pair)var7.getCdr();
               if(var8 instanceof Pair && var8.getCdr() == LList.Empty) {
                  var15 = var5.rewrite_car(var7, false);
                  Expression var26 = var5.rewrite_car(var8, false);
                  Namespace var29 = var5.namespaceResolvePrefix(var15);
                  var13 = var5.namespaceResolve((Namespace)var29, var26);
                  if(var13 != null) {
                     var4 = var6;
                  } else if(var29 != null && var2 == 1) {
                     var13 = new ApplyExp(quoteType.getDeclaredMethod("makeSymbol", 2), new Expression[]{QuoteExp.getInstance(var29), var26});
                     var4 = var6;
                  } else if(var15 instanceof ReferenceExp && var26 instanceof QuoteExp) {
                     var13 = var5.getGlobalEnvironment().getSymbol(((ReferenceExp)var15).getName() + ':' + ((QuoteExp)var26).getValue().toString());
                     var4 = var6;
                  } else {
                     String var18 = CompileNamedPart.combineName(var15, var26);
                     if(var18 != null) {
                        var13 = var5.getGlobalEnvironment().getSymbol(var18);
                        var4 = var6;
                     } else {
                        var4 = var5.pushPositionOf(var6);
                        var5.error('e', "\'" + var7.getCar() + "\' is not a valid prefix");
                        var5.popPositionOf(var4);
                        var4 = var6;
                     }
                  }
                  break;
               }
            }
         }

         if(var2 >= 0) {
            if(var5.matches(var6.getCar(), var3, (String)"quasiquote")) {
               ++var2;
            } else if(var5.matches(var6.getCar(), var3, (String)"unquote")) {
               label186: {
                  var11 = var2 - 1;
                  if(var6.getCdr() instanceof Pair) {
                     var7 = (Pair)var6.getCdr();
                     if(var7.getCdr() == LList.Empty) {
                        var2 = var11;
                        if(var11 == 0) {
                           var13 = var5.rewrite_car(var7, var3);
                           var4 = var6;
                           break;
                        }
                        break label186;
                     }
                  }

                  return var5.syntaxError("invalid used of " + var6.getCar() + " in quasiquote template");
               }
            } else if(var5.matches(var6.getCar(), var3, (String)"unquote-splicing")) {
               return var5.syntaxError("invalid used of " + var6.getCar() + " in quasiquote template");
            }
         }

         Object var23;
         if(var2 == 1 && var6.getCar() instanceof Pair) {
            Object var22 = var6.getCar();

            SyntaxForm var21;
            for(var21 = var3; var22 instanceof SyntaxForm; var22 = var21.getDatum()) {
               var21 = (SyntaxForm)var22;
            }

            byte var12 = -1;
            byte var27 = var12;
            Object var9;
            if(var22 instanceof Pair) {
               var9 = ((Pair)var22).getCar();
               if(var5.matches(var9, var21, (String)"unquote")) {
                  var27 = 0;
               } else {
                  var27 = var12;
                  if(var5.matches(var9, var21, (String)"unquote-splicing")) {
                     var27 = 1;
                  }
               }
            }

            if(var27 >= 0) {
               var9 = ((Pair)var22).getCdr();
               Vector var10 = new Vector();
               SyntaxForm var25 = var21;
               var23 = var9;

               while(true) {
                  var9 = var23;
                  if(var23 instanceof SyntaxForm) {
                     var25 = (SyntaxForm)var23;
                     var9 = var25.getDatum();
                  }

                  if(var9 == LList.Empty) {
                     var2 = var10.size() + 1;
                     var4 = this.expand(var6.getCdr(), 1, var3, var4, var5);
                     var13 = var4;
                     if(var2 > 1) {
                        Expression[] var24 = new Expression[var2];
                        var10.copyInto(var24);
                        var24[var2 - 1] = this.coerceExpression(var4, var5);
                        Method var16;
                        if(var27 == 0) {
                           var16 = consXMethod;
                        } else {
                           var16 = appendMethod;
                        }

                        var13 = new ApplyExp(var16, var24);
                     }

                     var4 = var6;
                     break label170;
                  }

                  if(!(var9 instanceof Pair)) {
                     return var5.syntaxError("improper list argument to unquote");
                  }

                  var10.addElement(var5.rewrite_car((Pair)var9, var25));
                  var23 = ((Pair)var9).getCdr();
               }
            }
         }

         var23 = this.expand(var6.getCar(), var2, var3, var4, var5);
         if(var23 == var6.getCar()) {
            Object var17 = var6.getCdr();
            if(var17 instanceof Pair) {
               var6 = (Pair)var17;
            } else {
               var13 = this.expand(var17, var2, var3, var4, var5);
               var4 = var17;
               break;
            }
         } else {
            var13 = this.expand(var6.getCdr(), var2, var3, var4, var5);
            if(!(var23 instanceof Expression) && !(var13 instanceof Expression)) {
               var13 = Translator.makePair(var6, var23, var13);
               var4 = var6;
            } else {
               var15 = this.coerceExpression(var23, var5);
               Expression var14 = this.coerceExpression(var13, var5);
               var13 = new ApplyExp(makePairMethod, new Expression[]{var15, var14});
               var4 = var6;
            }
            break;
         }
      }

      if(var1 == var4) {
         return var13;
      } else {
         var7 = var1;
         Pair[] var28 = new Pair[20];
         var2 = 0;

         while(true) {
            Pair[] var19 = var28;
            if(var2 >= var28.length) {
               var19 = new Pair[var2 * 2];
               System.arraycopy(var28, 0, var19, 0, var2);
            }

            var11 = var2 + 1;
            var19[var2] = var7;
            if(var7.getCdr() == var4) {
               if(var13 instanceof Expression) {
                  var4 = LList.Empty;
               } else {
                  var4 = var13;
               }

               while(true) {
                  --var11;
                  if(var11 < 0) {
                     if(var13 instanceof Expression) {
                        Expression[] var20 = new Expression[]{null, (Expression)var13};
                        if(var11 == 1) {
                           var20[0] = this.leaf(var1.getCar(), var5);
                           return new ApplyExp(makePairMethod, var20);
                        }

                        var20[0] = this.leaf(var4, var5);
                        return new ApplyExp(appendMethod, var20);
                     }

                     return var4;
                  }

                  var7 = var19[var11];
                  var4 = Translator.makePair(var7, var7.getCar(), var4);
               }
            }

            var7 = (Pair)var7.getCdr();
            var2 = var11;
            var28 = var19;
         }
      }
   }

   protected Expression leaf(Object var1, Translator var2) {
      return new QuoteExp(var1);
   }

   public Expression rewrite(Object var1, Translator var2) {
      if(var1 instanceof Pair) {
         Pair var4 = (Pair)var1;
         if(var4.getCdr() == LList.Empty) {
            var1 = var4.getCar();
            byte var3;
            if(this.isQuasi) {
               var3 = 1;
            } else {
               var3 = -1;
            }

            return this.coerceExpression(this.expand(var1, var3, var2), var2);
         }
      }

      return var2.syntaxError("wrong number of arguments to quote");
   }
}
