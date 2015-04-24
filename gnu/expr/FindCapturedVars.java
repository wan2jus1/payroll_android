package gnu.expr;

import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ExpExpVisitor;
import gnu.expr.Expression;
import gnu.expr.FluidLetExp;
import gnu.expr.LambdaExp;
import gnu.expr.LetExp;
import gnu.expr.ModuleExp;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.expr.ThisExp;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.KeyPair;
import gnu.mapping.Symbol;
import gnu.text.SourceLocator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class FindCapturedVars extends ExpExpVisitor {

   int backJumpPossible = 0;
   ModuleExp currentModule = null;
   Hashtable unknownDecls = null;


   static Expression checkInlineable(LambdaExp var0, Set var1) {
      if(var0.returnContinuation == LambdaExp.unknownContinuation) {
         return var0.returnContinuation;
      } else if(var1.contains(var0)) {
         return var0.returnContinuation;
      } else if(!var0.getCanRead() && !var0.isClassMethod() && var0.min_args == var0.max_args) {
         var1.add(var0);
         Expression var2 = var0.returnContinuation;
         Expression var3 = var2;
         if(var0.tailCallers != null) {
            Iterator var5 = var0.tailCallers.iterator();

            while(true) {
               var3 = var2;
               if(!var5.hasNext()) {
                  break;
               }

               LambdaExp var4 = (LambdaExp)var5.next();
               var3 = checkInlineable(var4, var1);
               if(var3 == LambdaExp.unknownContinuation) {
                  if(var2 != null && var2 != var4.body) {
                     var0.returnContinuation = LambdaExp.unknownContinuation;
                     return var3;
                  }

                  var2 = var4.body;
                  var0.inlineHome = var4;
               } else if(var2 == null) {
                  var2 = var3;
                  if(var0.inlineHome == null) {
                     LambdaExp var6;
                     if(var0.nestedIn(var4)) {
                        var6 = var4;
                     } else {
                        var6 = var4.inlineHome;
                     }

                     var0.inlineHome = var6;
                     var2 = var3;
                  }
               } else if(var3 != null && var2 != var3 || var0.getFlag(32)) {
                  var0.returnContinuation = LambdaExp.unknownContinuation;
                  return LambdaExp.unknownContinuation;
               }
            }
         }

         return var3;
      } else {
         var0.returnContinuation = LambdaExp.unknownContinuation;
         return LambdaExp.unknownContinuation;
      }
   }

   public static void findCapturedVars(Expression var0, Compilation var1) {
      FindCapturedVars var2 = new FindCapturedVars();
      var2.setContext(var1);
      var0.visit(var2, (Object)null);
   }

   Declaration allocUnboundDecl(Object var1, boolean var2) {
      Object var4 = var1;
      boolean var6 = var2;
      if(var2) {
         var4 = var1;
         var6 = var2;
         if(var1 instanceof Symbol) {
            if(!this.getCompilation().getLanguage().hasSeparateFunctionNamespace()) {
               var6 = false;
               var4 = var1;
            } else {
               var4 = new KeyPair((Symbol)var1, EnvironmentKey.FUNCTION);
               var6 = var2;
            }
         }
      }

      Declaration var3;
      if(this.unknownDecls == null) {
         this.unknownDecls = new Hashtable(100);
         var3 = null;
      } else {
         var3 = (Declaration)this.unknownDecls.get(var4);
      }

      Declaration var5 = var3;
      if(var3 == null) {
         var5 = this.currentModule.addDeclaration(var1);
         var5.setSimple(false);
         var5.setPrivate(true);
         if(var6) {
            var5.setProcedureDecl(true);
         }

         if(this.currentModule.isStatic()) {
            var5.setFlag(2048L);
         }

         var5.setCanRead(true);
         var5.setCanWrite(true);
         var5.setFlag(327680L);
         var5.setIndirectBinding(true);
         this.unknownDecls.put(var4, var5);
      }

      return var5;
   }

   public void capture(Declaration var1) {
      if((var1.getCanRead() || var1.getCanCall()) && (var1.field == null || !var1.field.getStaticFlag()) && (!this.comp.immediate || !var1.hasConstantValue())) {
         LambdaExp var2 = this.getCurrentLambda();
         ScopeExp var3 = var1.getContext();
         if(var3 == null) {
            throw new Error("null context for " + var1 + " curL:" + var2);
         }

         LambdaExp var7 = var3.currentLambda();
         LambdaExp var5 = null;
         LambdaExp var9 = null;

         while(true) {
            LambdaExp var4;
            if(var2 != var7 && var2.getInlineOnly()) {
               LambdaExp var6 = var2.outerLambda();
               var4 = var5;
               if(var6 != var5) {
                  var9 = var6.firstChild;
                  var4 = var6;
               }

               if(var9 != null && var2.inlineHome != null) {
                  var2 = var2.getCaller();
                  var9 = var9.nextSibling;
                  var5 = var4;
                  continue;
               }

               var2.setCanCall(false);
               return;
            }

            if(this.comp.usingCPStyle()) {
               if(var2 instanceof ModuleExp) {
                  break;
               }
            } else if(var2 == var7) {
               return;
            }

            Expression var10 = var1.getValue();
            if(var10 != null && var10 instanceof LambdaExp) {
               var4 = (LambdaExp)var10;
               if(var4.getInlineOnly()) {
                  break;
               }

               if(var4.isHandlingTailCalls()) {
                  var9 = null;
               } else {
                  var9 = var4;
                  if(var4 == var2) {
                     var9 = var4;
                     if(!var1.getCanRead()) {
                        return;
                     }
                  }
               }
            } else {
               var9 = null;
            }

            if(var1.getFlag(65536L)) {
               for(var4 = var2; var4 != var7; var4 = var4.outerLambda()) {
                  if(var4.nameDecl != null && var4.nameDecl.getFlag(2048L)) {
                     var1.setFlag(2048L);
                     break;
                  }
               }
            }

            if(var1.base != null) {
               var1.base.setCanRead(true);
               this.capture(var1.base);
               return;
            }

            if(!var1.getCanRead() && !var1.getCanCall() && var9 != null) {
               break;
            }

            if(!var1.isStatic()) {
               if(!var1.isFluid()) {
                  var2.setImportsLexVars();
               }

               for(var4 = var2.outerLambda(); var4 != var7 && var4 != null && (var1.getCanRead() || var9 != var4); var4 = var4.outerLambda()) {
                  Declaration var11 = var4.nameDecl;
                  if(var11 != null && var11.getFlag(2048L)) {
                     this.comp.error('e', "static " + var4.getName() + " references non-static " + var1.getName());
                  }

                  if(var4 instanceof ClassExp && var4.getName() != null && ((ClassExp)var4).isSimple()) {
                     this.comp.error('w', (Declaration)var4.nameDecl, "simple class ", (String)(" requiring lexical link (because of reference to " + var1.getName() + ") - use define-class instead"));
                  }

                  var4.setNeedsStaticLink();
               }
            }

            if(var7 == null) {
               System.err.println("null declLambda for " + var1 + " curL:" + var2);

               for(ScopeExp var8 = var1.context; var8 != null; var8 = var8.outer) {
                  System.err.println("- context:" + var8);
               }
            }

            var7.capture(var1);
            return;
         }
      }

   }

   void capture(Declaration var1, Declaration var2) {
      Declaration var3 = var2;
      if(var2.isAlias()) {
         var3 = var2;
         if(var2.value instanceof ReferenceExp) {
            ReferenceExp var4 = (ReferenceExp)var2.value;
            Declaration var5 = var4.binding;
            var3 = var2;
            if(var5 != null) {
               label30: {
                  if(var1 != null) {
                     var3 = var2;
                     if(var5.needsContext()) {
                        break label30;
                     }
                  }

                  this.capture(var4.contextDecl(), var5);
                  return;
               }
            }
         }
      }

      while(var3.isFluid() && var3.context instanceof FluidLetExp) {
         var3 = var3.base;
      }

      if(var1 != null && var3.needsContext()) {
         this.capture(var1);
      } else {
         this.capture(var3);
      }
   }

   void maybeWarnNoDeclarationSeen(Object var1, Compilation var2, SourceLocator var3) {
      if(var2.warnUndefinedVariable()) {
         var2.error('w', "no declaration seen for " + var1, var3);
      }

   }

   protected Expression visitApplyExp(ApplyExp var1, Void var2) {
      int var10 = this.backJumpPossible;
      boolean var9 = false;
      boolean var8 = false;
      Expression var3;
      boolean var6;
      boolean var7;
      Declaration var11;
      if(var1.func instanceof ReferenceExp && Compilation.defaultCallConvention <= 1) {
         var11 = Declaration.followAliases(((ReferenceExp)var1.func).binding);
         var7 = var8;
         var6 = var9;
         if(var11 != null) {
            var7 = var8;
            var6 = var9;
            if(var11.context instanceof ModuleExp) {
               var7 = var8;
               var6 = var9;
               if(!var11.isPublic()) {
                  var7 = var8;
                  var6 = var9;
                  if(!var11.getFlag(4096L)) {
                     var3 = var11.getValue();
                     var7 = var8;
                     var6 = var9;
                     if(var3 instanceof LambdaExp) {
                        var7 = var8;
                        var6 = var9;
                        if(!((LambdaExp)var3).getNeedsClosureEnv()) {
                           var6 = true;
                           var7 = var8;
                        }
                     }
                  }
               }
            }
         }
      } else {
         var7 = var8;
         var6 = var9;
         if(var1.func instanceof QuoteExp) {
            var7 = var8;
            var6 = var9;
            if(var1.getArgCount() > 0) {
               Object var4 = ((QuoteExp)var1.func).getValue();
               var3 = var1.getArg(0);
               var7 = var8;
               var6 = var9;
               if(var4 instanceof PrimProcedure) {
                  var7 = var8;
                  var6 = var9;
                  if(var3 instanceof ReferenceExp) {
                     PrimProcedure var12 = (PrimProcedure)var4;
                     var11 = Declaration.followAliases(((ReferenceExp)var3).binding);
                     var7 = var8;
                     var6 = var9;
                     if(var11 != null) {
                        var7 = var8;
                        var6 = var9;
                        if(var11.context instanceof ModuleExp) {
                           var7 = var8;
                           var6 = var9;
                           if(!var11.getFlag(4096L)) {
                              Expression var13 = var11.getValue();
                              var7 = var8;
                              var6 = var9;
                              if(var13 instanceof ClassExp) {
                                 Expression[] var5 = var1.getArgs();
                                 var7 = var8;
                                 var6 = var9;
                                 if(!((LambdaExp)var13).getNeedsClosureEnv()) {
                                    var1.nextCall = var11.firstCall;
                                    var11.firstCall = var1;

                                    for(int var14 = 1; var14 < var5.length; ++var14) {
                                       var5[var14].visit(this, var2);
                                    }

                                    var7 = true;
                                    var6 = true;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if(!var6) {
         var1.func = (Expression)var1.func.visit(this, var2);
      }

      if(this.exitValue == null && !var7) {
         var1.args = this.visitExps(var1.args, var2);
      }

      if(this.backJumpPossible > var10) {
         var1.setFlag(8);
      }

      return var1;
   }

   protected Expression visitClassExp(ClassExp var1, Void var2) {
      Expression var3 = (Expression)super.visitClassExp(var1, var2);
      if(!var1.explicitInit && !var1.instanceType.isInterface()) {
         Compilation.getConstructor(var1.instanceType, var1);
      } else if(var1.getNeedsClosureEnv()) {
         for(LambdaExp var4 = var1.firstChild; var4 != null; var4 = var4.nextSibling) {
            if("*init*".equals(var4.getName())) {
               var4.setNeedsStaticLink(true);
            }
         }
      }

      if(var1.isSimple() && var1.getNeedsClosureEnv() && var1.nameDecl != null && var1.nameDecl.getType() == Compilation.typeClass) {
         var1.nameDecl.setType(Compilation.typeClassType);
      }

      return var3;
   }

   public void visitDefaultArgs(LambdaExp var1, Void var2) {
      if(var1.defaultArgs != null) {
         super.visitDefaultArgs(var1, var2);

         for(Declaration var3 = var1.firstDecl(); var3 != null; var3 = var3.nextDecl()) {
            if(!var3.isSimple()) {
               var1.setFlag(true, 512);
               return;
            }
         }
      }

   }

   protected Expression visitFluidLetExp(FluidLetExp var1, Void var2) {
      for(Declaration var3 = var1.firstDecl(); var3 != null; var3 = var3.nextDecl()) {
         if(var3.base == null) {
            Object var4 = var3.getSymbol();
            Declaration var5 = this.allocUnboundDecl(var4, false);
            this.maybeWarnNoDeclarationSeen(var4, this.comp, var1);
            this.capture(var5);
            var3.base = var5;
         }
      }

      return (Expression)super.visitLetExp(var1, var2);
   }

   protected Expression visitLambdaExp(LambdaExp var1, Void var2) {
      if(checkInlineable(var1, new LinkedHashSet()) != LambdaExp.unknownContinuation && (!(var1.outer instanceof ModuleExp) || var1.nameDecl == null)) {
         var1.setInlineOnly(true);
         ++this.backJumpPossible;
      }

      return (Expression)super.visitLambdaExp(var1, var2);
   }

   protected Expression visitLetExp(LetExp var1, Void var2) {
      if(var1.body instanceof BeginExp) {
         Expression[] var5 = var1.inits;
         int var11 = var5.length;
         Expression[] var6 = ((BeginExp)var1.body).exps;
         int var9 = 0;
         Declaration var3 = var1.firstDecl();

         int var10;
         for(int var8 = 0; var8 < var6.length && var9 < var11; var9 = var10) {
            Expression var7 = var6[var8];
            Declaration var4 = var3;
            var10 = var9;
            if(var7 instanceof SetExp) {
               SetExp var13 = (SetExp)var7;
               var4 = var3;
               var10 = var9;
               if(var13.binding == var3) {
                  var4 = var3;
                  var10 = var9;
                  if(var5[var9] == QuoteExp.nullExp) {
                     var4 = var3;
                     var10 = var9;
                     if(var13.isDefining()) {
                        Expression var12 = var13.new_value;
                        if((var12 instanceof QuoteExp || var12 instanceof LambdaExp) && var3.getValue() == var12) {
                           var5[var9] = var12;
                           var6[var8] = QuoteExp.voidExp;
                        }

                        var10 = var9 + 1;
                        var4 = var3.nextDecl();
                     }
                  }
               }
            }

            ++var8;
            var3 = var4;
         }
      }

      return (Expression)super.visitLetExp(var1, var2);
   }

   protected Expression visitModuleExp(ModuleExp var1, Void var2) {
      ModuleExp var3 = this.currentModule;
      Hashtable var4 = this.unknownDecls;
      this.currentModule = var1;
      this.unknownDecls = null;

      Expression var7;
      try {
         var7 = this.visitLambdaExp(var1, (Void)var2);
      } finally {
         this.currentModule = var3;
         this.unknownDecls = var4;
      }

      return var7;
   }

   protected Expression visitReferenceExp(ReferenceExp var1, Void var2) {
      Declaration var3 = var1.getBinding();
      Declaration var4 = var3;
      if(var3 == null) {
         var4 = this.allocUnboundDecl(var1.getSymbol(), var1.isProcedureName());
         var1.setBinding(var4);
      }

      if(var4.getFlag(65536L) && this.comp.resolve(var1.getSymbol(), var1.isProcedureName()) == null) {
         this.maybeWarnNoDeclarationSeen(var1.getSymbol(), this.comp, var1);
      }

      this.capture(var1.contextDecl(), var4);
      return var1;
   }

   protected Expression visitSetExp(SetExp var1, Void var2) {
      Declaration var4 = var1.binding;
      Declaration var3 = var4;
      if(var4 == null) {
         var3 = this.allocUnboundDecl(var1.getSymbol(), var1.isFuncDef());
         var1.binding = var3;
      }

      if(!var3.ignorable()) {
         var4 = var3;
         if(!var1.isDefining()) {
            var4 = Declaration.followAliases(var3);
         }

         this.capture(var1.contextDecl(), var4);
      }

      return (Expression)super.visitSetExp(var1, var2);
   }

   protected Expression visitThisExp(ThisExp var1, Void var2) {
      if(var1.isForContext()) {
         this.getCurrentLambda().setImportsLexVars();
         return var1;
      } else {
         return this.visitReferenceExp(var1, (Void)var2);
      }
   }
}
