package gnu.expr;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.ExceptionsAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Filter;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.CheckedTarget;
import gnu.expr.ClassExp;
import gnu.expr.Closure;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Declaration;
import gnu.expr.ExpVisitor;
import gnu.expr.Expression;
import gnu.expr.IgnoreTarget;
import gnu.expr.Initializer;
import gnu.expr.InlineCalls;
import gnu.expr.Keyword;
import gnu.expr.Language;
import gnu.expr.ModuleExp;
import gnu.expr.PrimProcedure;
import gnu.expr.ProcInitializer;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.Special;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.expr.ThisExp;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.PropertySet;
import gnu.mapping.Values;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongArguments;
import java.util.Set;
import java.util.Vector;

public class LambdaExp extends ScopeExp {

   public static final int ATTEMPT_INLINE = 4096;
   static final int CANNOT_INLINE = 32;
   static final int CAN_CALL = 4;
   static final int CAN_READ = 2;
   static final int CLASS_METHOD = 64;
   static final int DEFAULT_CAPTURES_ARG = 512;
   static final int IMPORTS_LEX_VARS = 8;
   static final int INLINE_ONLY = 8192;
   static final int METHODS_COMPILED = 128;
   static final int NEEDS_STATIC_LINK = 16;
   protected static final int NEXT_AVAIL_FLAG = 16384;
   public static final int NO_FIELD = 256;
   public static final int OVERLOADABLE_FIELD = 2048;
   public static final int SEQUENCE_RESULT = 1024;
   static Method searchForKeywordMethod3;
   static Method searchForKeywordMethod4;
   static final ApplyExp unknownContinuation = new ApplyExp((Expression)null, (Expression[])null);
   Vector applyMethods;
   Variable argsArray;
   public Expression body;
   Declaration capturedVars;
   Variable closureEnv;
   public Field closureEnvField;
   public Expression[] defaultArgs;
   private Declaration firstArgsArrayArg;
   public LambdaExp firstChild;
   Variable heapFrame;
   Initializer initChain;
   public LambdaExp inlineHome;
   public Keyword[] keywords;
   public int max_args;
   public int min_args;
   public Declaration nameDecl;
   public LambdaExp nextSibling;
   Method[] primBodyMethods;
   Method[] primMethods;
   Object[] properties;
   public Expression returnContinuation;
   public Type returnType;
   int selectorValue;
   public Field staticLinkField;
   Set tailCallers;
   Procedure thisValue;
   Variable thisVariable;
   Expression[] throwsSpecification;
   ClassType type;


   public LambdaExp() {
      this.type = Compilation.typeProcedure;
   }

   public LambdaExp(int var1) {
      this.type = Compilation.typeProcedure;
      this.min_args = var1;
      this.max_args = var1;
   }

   public LambdaExp(Expression var1) {
      this.type = Compilation.typeProcedure;
      this.body = var1;
   }

   final void addApplyMethod(Compilation var1, Field var2) {
      Object var5;
      label29: {
         LambdaExp var3 = this;
         if(var2 != null) {
            var3 = this;
            if(var2.getStaticFlag()) {
               var5 = var1.getModule();
               break label29;
            }
         }

         LambdaExp var4;
         do {
            var4 = var3.outerLambda();
            if(var4 instanceof ModuleExp) {
               break;
            }

            var3 = var4;
         } while(var4.heapFrame == null);

         var5 = var4;
         if(!var4.getHeapFrameType().getSuperclass().isSubtype(Compilation.typeModuleBody)) {
            var5 = var1.getModule();
         }
      }

      if(((LambdaExp)var5).applyMethods == null) {
         ((LambdaExp)var5).applyMethods = new Vector();
      }

      ((LambdaExp)var5).applyMethods.addElement(this);
   }

   void addMethodFor(ClassType var1, Compilation var2, ObjectType var3) {
      String var5 = this.getName();
      LambdaExp var10 = this.outerLambda();
      int var21;
      if(this.keywords == null) {
         var21 = 0;
      } else {
         var21 = this.keywords.length;
      }

      int var19;
      if(this.defaultArgs == null) {
         var19 = 0;
      } else {
         var19 = this.defaultArgs.length - var21;
      }

      int var22;
      if((this.flags & 512) != 0) {
         var22 = 0;
      } else {
         var22 = var19;
      }

      boolean var23;
      if(this.max_args >= 0 && this.min_args + var22 >= this.max_args) {
         var23 = false;
      } else {
         var23 = true;
      }

      Method[] var11 = new Method[var22 + 1];
      this.primBodyMethods = var11;
      if(this.primMethods == null) {
         this.primMethods = var11;
      }

      byte var20 = 0;
      boolean var16;
      if(this.nameDecl != null && this.nameDecl.getFlag(4096L)) {
         var16 = false;
      } else if(this.nameDecl != null && this.nameDecl.getFlag(2048L)) {
         var16 = true;
      } else if(this.isClassMethod()) {
         if(var10 instanceof ClassExp) {
            ClassExp var6 = (ClassExp)var10;
            if(var6.isMakingClassPair() && var3 != null) {
               var16 = true;
            } else {
               var16 = false;
            }

            if(this == var6.initMethod) {
               var20 = 73;
            } else if(this == var6.clinitMethod) {
               var20 = 67;
               var16 = true;
            }
         } else {
            var16 = false;
         }
      } else if(this.thisVariable == null && var3 != var1) {
         if(this.nameDecl != null && this.nameDecl.context instanceof ModuleExp) {
            ModuleExp var32 = (ModuleExp)this.nameDecl.context;
            if(var32.getSuperType() == null && var32.getInterfaces() == null) {
               var16 = true;
            } else {
               var16 = false;
            }
         } else {
            var16 = true;
         }
      } else {
         var16 = false;
      }

      StringBuffer var12 = new StringBuffer(60);
      byte var18;
      if(var16) {
         var18 = 8;
      } else {
         var18 = 0;
      }

      int var17 = var18;
      if(this.nameDecl != null) {
         if(this.nameDecl.needsExternalAccess()) {
            var17 = var18 | 1;
         } else {
            byte var4;
            if(this.nameDecl.isPrivate()) {
               var4 = 0;
            } else {
               var4 = 1;
            }

            short var46 = var4;
            if(this.isClassMethod()) {
               var46 = this.nameDecl.getAccessFlags(var4);
            }

            var17 = var18 | var46;
         }
      }

      int var47;
      if(!var10.isModuleBody() && !(var10 instanceof ClassExp) || var5 == null) {
         var12.append("lambda");
         var47 = var2.method_counter + 1;
         var2.method_counter = var47;
         var12.append(var47);
      }

      if(var20 == 67) {
         var12.append("<clinit>");
      } else if(this.getSymbol() != null) {
         var12.append(Compilation.mangleName(var5));
      }

      if(this.getFlag(1024)) {
         var12.append("$C");
      }

      boolean var24;
      if(this.getCallConvention() >= 2 && var20 == 0) {
         var24 = true;
      } else {
         var24 = false;
      }

      var47 = var17;
      if(var20 != 0) {
         if(var16) {
            var47 = (var17 & -3) + 1;
         } else {
            var47 = (var17 & 2) + 2;
         }
      }

      int var48;
      label343: {
         if(!var1.isInterface()) {
            var48 = var47;
            if(!this.isAbstract()) {
               break label343;
            }
         }

         var48 = var47 | 1024;
      }

      if(this.isClassMethod() && var10 instanceof ClassExp && this.min_args == this.max_args) {
         Method[] var30 = null;
         var17 = 0;
         Declaration var7 = this.firstDecl();

         label334:
         while(true) {
            Method[] var29;
            label399: {
               if(var7 == null) {
                  if(this.returnType != null) {
                     break;
                  }
               } else {
                  if(var7.isThisParameter()) {
                     var47 = var17 - 1;
                     var29 = var30;
                     break label399;
                  }

                  var47 = var17;
                  var29 = var30;
                  if(var7.getFlag(8192L)) {
                     break label399;
                  }
               }

               var29 = var30;
               if(var30 == null) {
                  var29 = var1.getMethods(new Filter() {

                     // $FF: synthetic field
                     final String val$mangled;

                     {
                        this.val$mangled = var2;
                     }
                     public boolean select(Object var1) {
                        Method var2 = (Method)var1;
                        return var2.getName().equals(this.val$mangled) && var2.getParameterTypes().length == LambdaExp.this.min_args;
                     }
                  }, 2);
               }

               Type var8 = null;
               var47 = var29.length;

               while(true) {
                  --var47;
                  if(var47 >= 0) {
                     Method var35 = var29[var47];
                     Type var33;
                     if(var7 == null) {
                        var33 = var35.getReturnType();
                     } else {
                        var33 = var35.getParameterTypes()[var17];
                     }

                     if(var8 == null) {
                        var8 = var33;
                        continue;
                     }

                     if(var33 == var8) {
                        continue;
                     }

                     var47 = var17;
                     if(var7 == null) {
                        break label334;
                     }
                     break;
                  }

                  if(var8 != null) {
                     if(var7 != null) {
                        var7.setType(var8);
                     } else {
                        this.setCoercedReturnType(var8);
                     }
                  }

                  var47 = var17;
                  if(var7 == null) {
                     break label334;
                  }
                  break;
               }
            }

            var7 = var7.nextDecl();
            var17 = var47 + 1;
            var30 = var29;
         }
      }

      Object var39;
      if(!this.getFlag(1024) && this.getCallConvention() < 2) {
         var39 = this.getReturnType().getImplementationType();
      } else {
         var39 = Type.voidType;
      }

      byte var49;
      if(var3 != null && var3 != var1) {
         var49 = 1;
      } else {
         var49 = 0;
      }

      byte var25 = 0;
      var18 = var25;
      if(this.getCallConvention() >= 2) {
         var18 = var25;
         if(var20 == 0) {
            var18 = 1;
         }
      }

      int var28 = var12.length();

      int var51;
      for(int var50 = 0; var50 <= var22; var48 = var51) {
         var12.setLength(var28);
         int var27 = this.min_args + var50;
         int var26 = var27;
         if(var50 == var22) {
            var26 = var27;
            if(var23) {
               var26 = var27 + 1;
            }
         }

         Type[] var9 = new Type[var49 + var26 + var18];
         if(var49 > 0) {
            var9[0] = var3;
         }

         Declaration var40 = this.firstDecl();
         Declaration var31 = var40;
         if(var40 != null) {
            var31 = var40;
            if(var40.isThisParameter()) {
               var31 = var40.nextDecl();
            }
         }

         for(var51 = 0; var51 < var27; ++var51) {
            var9[var49 + var51] = var31.getType().getImplementationType();
            var31 = var31.nextDecl();
         }

         if(var18 != 0) {
            var9[var9.length - 1] = Compilation.typeCallContext;
         }

         var51 = var48;
         Object var38;
         if(var27 < var26) {
            Type var34 = var31.getType();
            String var13 = var34.getName();
            if(var1.getClassfileVersion() >= 3211264 && var34 instanceof ArrayType) {
               var48 |= 128;
            } else {
               var12.append("$V");
            }

            label286: {
               if(var21 <= 0 && var22 >= var19) {
                  var38 = var34;
                  if("gnu.lists.LList".equals(var13)) {
                     break label286;
                  }

                  var38 = var34;
                  if(var34 instanceof ArrayType) {
                     break label286;
                  }
               }

               var38 = Compilation.objArrayType;
               this.argsArray = new Variable("argsArray", Compilation.objArrayType);
               this.argsArray.setParameter(true);
            }

            this.firstArgsArrayArg = var31;
            var26 = var9.length;
            if(var24) {
               var25 = 2;
            } else {
               var25 = 1;
            }

            var9[var26 - var25] = (Type)var38;
            var51 = var48;
         }

         if(var24) {
            var12.append("$X");
         }

         if(!(var10 instanceof ClassExp) && (!(var10 instanceof ModuleExp) || !((ModuleExp)var10).getFlag(131072))) {
            var16 = false;
         } else {
            var16 = true;
         }

         var5 = var12.toString();
         var26 = 0;
         var27 = var12.length();

         ClassType var43;
         label268:
         while(true) {
            var43 = var1;

            while(true) {
               if(var43 == null) {
                  break label268;
               }

               if(var43.getDeclaredMethod(var5, var9) != null) {
                  var12.setLength(var27);
                  var12.append('$');
                  ++var26;
                  var12.append(var26);
                  var5 = var12.toString();
                  break;
               }

               if(var16) {
                  break label268;
               }

               var43 = var43.getSuperclass();
            }
         }

         Method var45 = var1.addMethod(var5, var9, (Type)var39, var51);
         var11[var50] = var45;
         if(this.throwsSpecification != null && this.throwsSpecification.length > 0) {
            var26 = this.throwsSpecification.length;
            ClassType[] var14 = new ClassType[var26];

            for(var48 = 0; var48 < var26; ++var48) {
               ClassType var37 = null;
               var43 = null;
               Expression var15 = this.throwsSpecification[var48];
               var9 = null;
               if(var15 instanceof ReferenceExp) {
                  ReferenceExp var36 = (ReferenceExp)var15;
                  var31 = var36.getBinding();
                  if(var31 != null) {
                     Expression var41 = var31.getValue();
                     if(var41 instanceof ClassExp) {
                        var43 = ((ClassExp)var41).getCompiledClassType(var2);
                        var5 = var9;
                     } else {
                        var5 = "throws specification " + var31.getName() + " has non-class lexical binding";
                     }
                  } else {
                     var5 = "unknown class " + var36.getName();
                  }
               } else {
                  var5 = var9;
                  if(var15 instanceof QuoteExp) {
                     var38 = ((QuoteExp)var15).getValue();
                     Object var44 = var38;
                     if(var38 instanceof Class) {
                        var44 = Type.make((Class)var38);
                     }

                     if(var44 instanceof ClassType) {
                        var37 = (ClassType)var44;
                     }

                     var43 = var37;
                     var5 = var9;
                     if(var37 != null) {
                        var43 = var37;
                        var5 = var9;
                        if(!var37.isSubtype(Type.javalangThrowableType)) {
                           var5 = var37.getName() + " does not extend Throwable";
                           var43 = var37;
                        }
                     }
                  }
               }

               String var42 = var5;
               if(var43 == null) {
                  var42 = var5;
                  if(var5 == null) {
                     var42 = "invalid throws specification";
                  }
               }

               if(var42 != null) {
                  var2.error('e', var42, var15);
                  var43 = Type.javalangThrowableType;
               }

               var14[var48] = var43;
            }

            (new ExceptionsAttr(var45)).setExceptions(var14);
         }

         ++var50;
      }

   }

   void addMethodFor(Compilation var1, ObjectType var2) {
      Object var3;
      for(var3 = this; var3 != null && !(var3 instanceof ClassExp); var3 = ((ScopeExp)var3).outer) {
         ;
      }

      ClassType var4;
      if(var3 != null) {
         var4 = ((ClassExp)var3).instanceType;
      } else {
         var4 = this.getOwningLambda().getHeapFrameType();
      }

      this.addMethodFor(var4, var1, var2);
   }

   public void allocChildClasses(Compilation var1) {
      Method var2 = this.getMainMethod();
      if(var2 != null && !var2.getStaticFlag()) {
         this.declareThis(var2.getDeclaringClass());
      }

      Declaration var5 = this.firstDecl();

      while(true) {
         if(var5 == this.firstArgsArrayArg && this.argsArray != null) {
            this.getVarScope().addVariable(this.argsArray);
         }

         if(!this.getInlineOnly() && this.getCallConvention() >= 2) {
            label43: {
               if(this.firstArgsArrayArg == null) {
                  if(var5 != null) {
                     break label43;
                  }
               } else if(this.argsArray != null) {
                  if(var5 != this.firstArgsArrayArg) {
                     break label43;
                  }
               } else if(var5 != this.firstArgsArrayArg.nextDecl()) {
                  break label43;
               }

               this.getVarScope().addVariable((CodeAttr)null, Compilation.typeCallContext, "$ctx").setParameter(true);
            }
         }

         if(var5 == null) {
            this.declareClosureEnv();
            this.allocFrame(var1);
            this.allocChildMethods(var1);
            return;
         }

         if(var5.var == null && (!this.getInlineOnly() || !var5.ignorable())) {
            if(var5.isSimple() && !var5.isIndirectBinding()) {
               var5.allocateVariable((CodeAttr)null);
            } else {
               String var3 = Compilation.mangleName(var5.getName()).intern();
               Type var4 = var5.getType().getImplementationType();
               Variable var6 = this.getVarScope().addVariable((CodeAttr)null, var4, var3);
               var5.var = var6;
               var6.setParameter(true);
            }
         }

         var5 = var5.nextDecl();
      }
   }

   void allocChildMethods(Compilation var1) {
      for(LambdaExp var2 = this.firstChild; var2 != null; var2 = var2.nextSibling) {
         if(!var2.isClassGenerated() && !var2.getInlineOnly() && var2.nameDecl != null) {
            var2.allocMethod(this, var1);
         }

         if(var2 instanceof ClassExp) {
            ClassExp var4 = (ClassExp)var2;
            if(var4.getNeedsClosureEnv()) {
               ClassType var3;
               if(!(this instanceof ModuleExp) && !(this instanceof ClassExp)) {
                  Variable var5;
                  if(this.heapFrame != null) {
                     var5 = this.heapFrame;
                  } else {
                     var5 = this.closureEnv;
                  }

                  var3 = (ClassType)var5.getType();
               } else {
                  var3 = (ClassType)this.getType();
               }

               Field var6 = var4.instanceType.setOuterLink(var3);
               var4.staticLinkField = var6;
               var4.closureEnvField = var6;
            }
         }
      }

   }

   Field allocFieldFor(Compilation var1) {
      Field var10;
      if(this.nameDecl != null && this.nameDecl.field != null) {
         var10 = this.nameDecl.field;
      } else {
         boolean var7 = this.getNeedsClosureEnv();
         ClassType var4;
         if(var7) {
            var4 = this.getOwningLambda().getHeapFrameType();
         } else {
            var4 = var1.mainClass;
         }

         String var2 = this.getName();
         String var3;
         if(var2 == null) {
            var3 = "lambda";
         } else {
            var3 = Compilation.mangleNameIfNeeded(var2);
         }

         int var5 = 16;
         int var6;
         String var8;
         if(this.nameDecl != null && this.nameDecl.context instanceof ModuleExp) {
            var7 = this.nameDecl.needsExternalAccess();
            var2 = var3;
            if(var7) {
               var2 = "$Prvt$" + var3;
            }

            if(this.nameDecl.getFlag(2048L)) {
               var6 = 16 | 8;
               var5 = var6;
               if(!((ModuleExp)this.nameDecl.context).isStatic()) {
                  var5 = var6 & -17;
               }
            }

            label55: {
               if(this.nameDecl.isPrivate() && !var7) {
                  var6 = var5;
                  if(!var1.immediate) {
                     break label55;
                  }
               }

               var6 = var5 | 1;
            }

            var5 = var6;
            var8 = var2;
            if((this.flags & 2048) != 0) {
               if(this.min_args == this.max_args) {
                  var5 = this.min_args;
               } else {
                  var5 = 1;
               }

               while(true) {
                  var8 = var2 + '$' + var5;
                  if(var4.getDeclaredField(var8) == null) {
                     var5 = var6;
                     break;
                  }

                  ++var5;
               }
            }
         } else {
            StringBuilder var9 = (new StringBuilder()).append(var3).append("$Fn");
            var6 = var1.localFieldIndex + 1;
            var1.localFieldIndex = var6;
            var2 = var9.append(var6).toString();
            var8 = var2;
            if(!var7) {
               var5 = 16 | 8;
               var8 = var2;
            }
         }

         Field var11 = var4.addField(var8, Compilation.typeModuleMethod, var5);
         var10 = var11;
         if(this.nameDecl != null) {
            this.nameDecl.field = var11;
            return var11;
         }
      }

      return var10;
   }

   public void allocFrame(Compilation var1) {
      if(this.heapFrame != null) {
         ClassType var3;
         if(!(this instanceof ModuleExp) && !(this instanceof ClassExp)) {
            ClassType var2 = new ClassType(var1.generateClassName("frame"));
            var2.setSuper((ClassType)var1.getModuleType());
            var1.addClass(var2);
            var3 = var2;
         } else {
            var3 = this.getCompiledClassType(var1);
         }

         this.heapFrame.setType(var3);
      }

   }

   void allocMethod(LambdaExp var1, Compilation var2) {
      ClassType var3;
      if(!this.getNeedsClosureEnv()) {
         var3 = null;
      } else if(!(var1 instanceof ClassExp) && !(var1 instanceof ModuleExp)) {
         while(var1.heapFrame == null) {
            var1 = var1.outerLambda();
         }

         var3 = (ClassType)var1.heapFrame.getType();
      } else {
         var3 = var1.getCompiledClassType(var2);
      }

      this.addMethodFor(var2, var3);
   }

   void allocParameters(Compilation var1) {
      CodeAttr var3 = var1.getCode();
      var3.locals.enterScope(this.getVarScope());
      int var2 = this.getLineNumber();
      if(var2 > 0) {
         var3.putLineNumber(this.getFileName(), var2);
      }

      if(this.heapFrame != null) {
         this.heapFrame.allocateLocal(var3);
      }

   }

   public void apply(CallContext var1) throws Throwable {
      this.setIndexes();
      var1.writeValue(new Closure(this, var1));
   }

   public void capture(Declaration var1) {
      if(var1.isSimple()) {
         if(this.capturedVars == null && !var1.isStatic() && !(this instanceof ModuleExp) && !(this instanceof ClassExp)) {
            this.heapFrame = new Variable("heapFrame");
         }

         var1.setSimple(false);
         if(!var1.isPublic()) {
            var1.nextCapturedVar = this.capturedVars;
            this.capturedVars = var1;
         }
      }

   }

   public void compile(Compilation var1, Target var2) {
      if(!(var2 instanceof IgnoreTarget)) {
         CodeAttr var4 = var1.getCode();
         LambdaExp var3 = this.outerLambda();
         ClassType var5 = Compilation.typeModuleMethod;
         if((this.flags & 256) == 0 && (!var1.immediate || !(var3 instanceof ModuleExp))) {
            Field var6 = this.compileSetField(var1);
            if(var6.getStaticFlag()) {
               var4.emitGetStatic(var6);
            } else {
               var3 = var1.curLambda;
               Variable var7;
               if(var3.heapFrame != null) {
                  var7 = var3.heapFrame;
               } else {
                  var7 = var3.closureEnv;
               }

               var4.emitLoad(var7);
               var4.emitGetField(var6);
            }
         } else {
            if(this.primMethods == null) {
               this.allocMethod(this.outerLambda(), var1);
            }

            this.compileAsMethod(var1);
            this.addApplyMethod(var1, (Field)null);
            ProcInitializer.emitLoadModuleMethod(this, var1);
         }

         var2.compileFromStack(var1, var5);
      }
   }

   void compileAsMethod(Compilation var1) {
      if((this.flags & 128) == 0 && !this.isAbstract()) {
         this.flags |= 128;
         if(this.primMethods != null) {
            Method var6 = var1.method;
            LambdaExp var7 = var1.curLambda;
            var1.curLambda = this;
            boolean var17 = this.primMethods[0].getStaticFlag();
            int var16 = this.primMethods.length - 1;
            Type var8 = this.restArgType();
            long[] var4 = null;
            Declaration var2;
            int var11;
            if(var16 > 0) {
               long[] var3 = new long[this.min_args + var16];
               var11 = 0;
               var2 = this.firstDecl();

               while(true) {
                  var4 = var3;
                  if(var11 >= this.min_args + var16) {
                     break;
                  }

                  var3[var11] = var2.flags;
                  var2 = var2.nextDecl();
                  ++var11;
               }
            }

            boolean var12;
            if(this.getCallConvention() >= 2) {
               var12 = true;
            } else {
               var12 = false;
            }

            for(var11 = 0; var11 <= var16; ++var11) {
               var1.method = this.primMethods[var11];
               int var13;
               if(var11 < var16) {
                  CodeAttr var9 = var1.method.startCode();

                  for(var13 = var11 + 1; var13 < var16 && this.defaultArgs[var13] instanceof QuoteExp; ++var13) {
                     ;
                  }

                  boolean var14;
                  if(var13 == var16 && var8 != null) {
                     var14 = true;
                  } else {
                     var14 = false;
                  }

                  Variable var10 = var1.callContextVar;
                  Variable var19 = var9.getArg(0);
                  Variable var18 = var19;
                  if(!var17) {
                     var9.emitPushThis();
                     if(this.getNeedsClosureEnv()) {
                        this.closureEnv = var19;
                     }

                     var18 = var9.getArg(1);
                  }

                  Declaration var20 = this.firstDecl();

                  int var15;
                  for(var15 = 0; var15 < this.min_args + var11; var20 = var20.nextDecl()) {
                     var20.flags |= 64L;
                     var20.var = var18;
                     var9.emitLoad(var18);
                     var18 = var18.nextVar();
                     ++var15;
                  }

                  Variable var5;
                  if(var12) {
                     var5 = var18;
                  } else {
                     var5 = null;
                  }

                  var1.callContextVar = var5;

                  for(var15 = var11; var15 < var13; var20 = var20.nextDecl()) {
                     Target var23 = StackTarget.getInstance(var20.getType());
                     this.defaultArgs[var15].compile(var1, (Target)var23);
                     ++var15;
                  }

                  if(var14) {
                     String var21 = var8.getName();
                     QuoteExp var22;
                     if("gnu.lists.LList".equals(var21)) {
                        var22 = new QuoteExp(LList.Empty);
                     } else {
                        if(!"java.lang.Object[]".equals(var21)) {
                           throw new Error("unimplemented #!rest type " + var21);
                        }

                        var22 = new QuoteExp(Values.noArgs);
                     }

                     var22.compile(var1, (Type)var8);
                  }

                  if(var12) {
                     var9.emitLoad(var18);
                  }

                  if(var17) {
                     var9.emitInvokeStatic(this.primMethods[var13]);
                  } else {
                     var9.emitInvokeVirtual(this.primMethods[var13]);
                  }

                  var9.emitReturn();
                  this.closureEnv = null;
                  var1.callContextVar = var10;
               } else {
                  if(var4 != null) {
                     var13 = 0;

                     for(var2 = this.firstDecl(); var13 < this.min_args + var16; ++var13) {
                        var2.flags = var4[var13];
                        var2.var = null;
                        var2 = var2.nextDecl();
                     }
                  }

                  var1.method.initCode();
                  this.allocChildClasses(var1);
                  this.allocParameters(var1);
                  this.enterFunction(var1);
                  this.compileBody(var1);
                  this.compileEnd(var1);
                  this.generateApplyMethods(var1);
               }
            }

            var1.method = var6;
            var1.curLambda = var7;
            return;
         }
      }

   }

   public void compileBody(Compilation var1) {
      Variable var4 = var1.callContextVar;
      var1.callContextVar = null;
      Target var6;
      if(this.getCallConvention() >= 2) {
         Variable var2 = this.getVarScope().lookup("$ctx");
         if(var2 != null && var2.getType() == Compilation.typeCallContext) {
            var1.callContextVar = var2;
         }

         var6 = ConsumerTarget.makeContextTarget(var1);
      } else {
         var6 = Target.pushValue(this.getReturnType());
      }

      Expression var5 = this.body;
      Object var3 = this;
      if(this.body.getLineNumber() > 0) {
         var3 = this.body;
      }

      var5.compileWithPosition(var1, var6, (Expression)var3);
      var1.callContextVar = var4;
   }

   public void compileEnd(Compilation var1) {
      CodeAttr var2 = var1.getCode();
      if(!this.getInlineOnly()) {
         if(var1.method.reachableHere() && (Compilation.defaultCallConvention < 3 || this.isModuleBody() || this.isClassMethod() || this.isHandlingTailCalls())) {
            var2.emitReturn();
         }

         this.popScope(var2);
         var2.popScope();
      }

      for(LambdaExp var3 = this.firstChild; var3 != null; var3 = var3.nextSibling) {
         if(!var3.getCanRead() && !var3.getInlineOnly()) {
            var3.compileAsMethod(var1);
         }
      }

      if(this.heapFrame != null) {
         var1.generateConstructor(this);
      }

   }

   public Field compileSetField(Compilation var1) {
      if(this.primMethods == null) {
         this.allocMethod(this.outerLambda(), var1);
      }

      Field var2 = this.allocFieldFor(var1);
      if(var1.usingCPStyle()) {
         this.compile(var1, Type.objectType);
      } else {
         this.compileAsMethod(var1);
         this.addApplyMethod(var1, var2);
      }

      return (new ProcInitializer(this, var1, var2)).field;
   }

   public Variable declareClosureEnv() {
      if(this.closureEnv == null && this.getNeedsClosureEnv()) {
         LambdaExp var2 = this.outerLambda();
         LambdaExp var1 = var2;
         if(var2 instanceof ClassExp) {
            var1 = var2.outerLambda();
         }

         Variable var5;
         if(var1.heapFrame != null) {
            var5 = var1.heapFrame;
         } else {
            var5 = var1.closureEnv;
         }

         if(this.isClassMethod() && !"*init*".equals(this.getName())) {
            this.closureEnv = this.declareThis(this.type);
         } else if(var1.heapFrame == null && !var1.getNeedsStaticLink() && !(var1 instanceof ModuleExp)) {
            this.closureEnv = null;
         } else if(!this.isClassGenerated() && !this.getInlineOnly()) {
            Method var4 = this.getMainMethod();
            boolean var3 = "*init*".equals(this.getName());
            if(!var4.getStaticFlag() && !var3) {
               this.closureEnv = this.declareThis(var4.getDeclaringClass());
            } else {
               this.closureEnv = new Variable("closureEnv", var4.getParameterTypes()[0]);
               Variable var6;
               if(var3) {
                  var6 = this.declareThis(var4.getDeclaringClass());
               } else {
                  var6 = null;
               }

               this.getVarScope().addVariableAfter(var6, this.closureEnv);
               this.closureEnv.setParameter(true);
            }
         } else if(this.inlinedIn(var1)) {
            this.closureEnv = var5;
         } else {
            this.closureEnv = new Variable("closureEnv", var5.getType());
            this.getVarScope().addVariable(this.closureEnv);
         }
      }

      return this.closureEnv;
   }

   public Variable declareThis(ClassType var1) {
      if(this.thisVariable == null) {
         this.thisVariable = new Variable("this");
         this.getVarScope().addVariableAfter((Variable)null, this.thisVariable);
         this.thisVariable.setParameter(true);
      }

      if(this.thisVariable.getType() == null) {
         this.thisVariable.setType(var1);
      }

      if(this.decls != null && this.decls.isThisParameter()) {
         this.decls.var = this.thisVariable;
      }

      return this.thisVariable;
   }

   void enterFunction(Compilation var1) {
      CodeAttr var6 = var1.getCode();
      this.getVarScope().noteStartFunction(var6);
      if(this.closureEnv != null && !this.closureEnv.isParameter() && !var1.usingCPStyle()) {
         if(!this.getInlineOnly()) {
            var6.emitPushThis();
            Field var3 = this.closureEnvField;
            Field var2 = var3;
            if(var3 == null) {
               var2 = this.outerLambda().closureEnvField;
            }

            var6.emitGetField(var2);
            var6.emitStore(this.closureEnv);
         } else if(!this.inlinedIn(this.outerLambda())) {
            this.outerLambda().loadHeapFrame(var1);
            var6.emitStore(this.closureEnv);
         }
      }

      ClassType var23;
      if(!var1.usingCPStyle()) {
         if(this.heapFrame == null) {
            var23 = this.currentModule().getCompiledClassType(var1);
         } else {
            var23 = (ClassType)this.heapFrame.getType();
         }

         for(Declaration var24 = this.capturedVars; var24 != null; var24 = var24.nextCapturedVar) {
            if(var24.field == null) {
               var24.makeField(var23, var1, (Expression)null);
            }
         }
      }

      if(this.heapFrame != null && !var1.usingCPStyle()) {
         var23 = (ClassType)this.heapFrame.getType();
         if(this.closureEnv != null && !(this instanceof ModuleExp)) {
            this.staticLinkField = var23.addField("staticLink", this.closureEnv.getType());
         }

         if(!(this instanceof ModuleExp) && !(this instanceof ClassExp)) {
            var23.setEnclosingMember(var1.method);
            var6.emitNew(var23);
            var6.emitDup(var23);
            var6.emitInvokeSpecial(Compilation.getConstructor(var23, this));
            if(this.staticLinkField != null) {
               var6.emitDup(var23);
               var6.emitLoad(this.closureEnv);
               var6.emitPutField(this.staticLinkField);
            }

            var6.emitStore(this.heapFrame);
         }
      }

      Variable var26 = this.argsArray;
      Variable var25 = var26;
      if(this.min_args == this.max_args) {
         var25 = var26;
         if(this.primMethods == null) {
            var25 = var26;
            if(this.getCallConvention() < 2) {
               var25 = null;
            }
         }
      }

      int var17 = 0;
      int var15;
      if(this.keywords == null) {
         var15 = 0;
      } else {
         var15 = this.keywords.length;
      }

      int var20;
      if(this.defaultArgs == null) {
         var20 = 0;
      } else {
         var20 = this.defaultArgs.length - var15;
      }

      if(!(this instanceof ModuleExp)) {
         int var21 = -1;
         int var22 = 0;
         this.getMainMethod();
         Variable var7 = var1.callContextVar;
         Declaration var4 = this.firstDecl();
         var15 = 0;

         int var19;
         for(int var16 = 0; var4 != null; var21 = var19) {
            if(this.getCallConvention() < 2) {
               var26 = null;
            } else {
               var26 = this.getVarScope().lookup("$ctx");
            }

            var1.callContextVar = var26;
            int var18 = var22;
            var19 = var21;
            if(var4 == this.firstArgsArrayArg) {
               var18 = var22;
               var19 = var21;
               if(var25 != null) {
                  if(this.primMethods != null) {
                     var19 = var17;
                     var18 = var17 - this.min_args;
                  } else {
                     var19 = 0;
                     var18 = 0;
                  }
               }
            }

            if(var19 >= 0 || !var4.isSimple() || var4.isIndirectBinding()) {
               Type var5 = var4.getType();
               Object var27;
               if(var19 >= 0) {
                  var27 = Type.objectType;
               } else {
                  var27 = var5;
               }

               if(!var4.isSimple()) {
                  var4.loadOwningObject((Declaration)null, var1);
               }

               if(var19 < 0) {
                  var6.emitLoad(var4.getVariable());
               } else if(var17 < this.min_args) {
                  var6.emitLoad(var25);
                  var6.emitPushInt(var17);
                  var6.emitArrayLoad(Type.objectType);
               } else {
                  Expression[] var8;
                  if(var17 < this.min_args + var20) {
                     var6.emitPushInt(var17 - var19);
                     var6.emitLoad(var25);
                     var6.emitArrayLength();
                     var6.emitIfIntLt();
                     var6.emitLoad(var25);
                     var6.emitPushInt(var17 - var19);
                     var6.emitArrayLoad();
                     var6.emitElse();
                     var8 = this.defaultArgs;
                     var21 = var16 + 1;
                     var8[var18 + var16].compile(var1, (Type)var5);
                     var6.emitFi();
                     var16 = var21;
                  } else if(this.max_args < 0 && var17 == this.min_args + var20) {
                     var6.emitLoad(var25);
                     var6.emitPushInt(var17 - var19);
                     var6.emitInvokeStatic(Compilation.makeListMethod);
                     var27 = Compilation.scmListType;
                  } else {
                     var6.emitLoad(var25);
                     var6.emitPushInt(this.min_args + var20 - var19);
                     Keyword[] var28 = this.keywords;
                     var21 = var15 + 1;
                     var1.compileConstant(var28[var15]);
                     var8 = this.defaultArgs;
                     var22 = var16 + 1;
                     Expression var29 = var8[var18 + var16];
                     ArrayType var9;
                     PrimType var10;
                     ClassType var11;
                     ClassType var12;
                     ClassType var13;
                     if(var29 instanceof QuoteExp) {
                        if(searchForKeywordMethod4 == null) {
                           var9 = Compilation.objArrayType;
                           var10 = Type.intType;
                           var11 = Type.objectType;
                           var12 = Type.objectType;
                           var13 = Compilation.scmKeywordType;
                           ClassType var14 = Type.objectType;
                           searchForKeywordMethod4 = var13.addMethod("searchForKeyword", new Type[]{var9, var10, var11, var12}, var14, 9);
                        }

                        var29.compile(var1, (Type)var5);
                        var6.emitInvokeStatic(searchForKeywordMethod4);
                        var15 = var21;
                        var16 = var22;
                     } else {
                        if(searchForKeywordMethod3 == null) {
                           var9 = Compilation.objArrayType;
                           var10 = Type.intType;
                           var11 = Type.objectType;
                           var12 = Compilation.scmKeywordType;
                           var13 = Type.objectType;
                           searchForKeywordMethod3 = var12.addMethod("searchForKeyword", new Type[]{var9, var10, var11}, var13, 9);
                        }

                        var6.emitInvokeStatic(searchForKeywordMethod3);
                        var6.emitDup(1);
                        var1.compileConstant(Special.dfault);
                        var6.emitIfEq();
                        var6.emitPop(1);
                        var29.compile(var1, (Type)var5);
                        var6.emitFi();
                        var15 = var21;
                        var16 = var22;
                     }
                  }
               }

               if(var5 != var27) {
                  CheckedTarget.emitCheckedCoerce(var1, (LambdaExp)this, var17 + 1, var5);
               }

               if(var4.isIndirectBinding()) {
                  var4.pushIndirectBinding(var1);
               }

               if(var4.isSimple()) {
                  var26 = var4.getVariable();
                  if(var4.isIndirectBinding()) {
                     var26.setType(Compilation.typeLocation);
                  }

                  var6.emitStore(var26);
               } else {
                  var6.emitPutField(var4.field);
               }
            }

            ++var17;
            var4 = var4.nextDecl();
            var22 = var18;
         }

         var1.callContextVar = var7;
      }
   }

   Object evalDefaultArg(int var1, CallContext var2) {
      try {
         Object var4 = this.defaultArgs[var1].eval((CallContext)var2);
         return var4;
      } catch (Throwable var3) {
         throw new WrappedException("error evaluating default argument", var3);
      }
   }

   public void generateApplyMethods(Compilation var1) {
      var1.generateMatchMethods(this);
      if(Compilation.defaultCallConvention >= 2) {
         var1.generateApplyMethodsWithContext(this);
      } else {
         var1.generateApplyMethodsWithoutContext(this);
      }
   }

   Declaration getArg(int var1) {
      for(Declaration var2 = this.firstDecl(); var2 != null; var2 = var2.nextDecl()) {
         if(var1 == 0) {
            return var2;
         }

         --var1;
      }

      throw new Error("internal error - getArg");
   }

   public int getCallConvention() {
      int var1 = 2;
      if(this.isModuleBody()) {
         if(Compilation.defaultCallConvention >= 2) {
            var1 = Compilation.defaultCallConvention;
         }

         return var1;
      } else {
         return this.isClassMethod()?1:(Compilation.defaultCallConvention != 0?Compilation.defaultCallConvention:1);
      }
   }

   public LambdaExp getCaller() {
      return this.inlineHome;
   }

   public final boolean getCanCall() {
      return (this.flags & 4) != 0;
   }

   public final boolean getCanRead() {
      return (this.flags & 2) != 0;
   }

   public ClassType getClassType() {
      return this.type;
   }

   protected ClassType getCompiledClassType(Compilation var1) {
      if(this.type == Compilation.typeProcedure) {
         throw new Error("internal error: getCompiledClassType");
      } else {
         return this.type;
      }
   }

   protected final String getExpClassName() {
      String var2 = this.getClass().getName();
      int var3 = var2.lastIndexOf(46);
      String var1 = var2;
      if(var3 >= 0) {
         var1 = var2.substring(var3 + 1);
      }

      return var1;
   }

   public ClassType getHeapFrameType() {
      return !(this instanceof ModuleExp) && !(this instanceof ClassExp)?(ClassType)this.heapFrame.getType():(ClassType)this.getType();
   }

   public final boolean getImportsLexVars() {
      return (this.flags & 8) != 0;
   }

   public final boolean getInlineOnly() {
      return (this.flags & 8192) != 0;
   }

   public final Method getMainMethod() {
      Method[] var1 = this.primBodyMethods;
      return var1 == null?null:var1[var1.length - 1];
   }

   public final Method getMethod(int var1) {
      if(this.primMethods != null && (this.max_args < 0 || var1 <= this.max_args)) {
         var1 -= this.min_args;
         if(var1 >= 0) {
            int var3 = this.primMethods.length;
            Method[] var2 = this.primMethods;
            if(var1 >= var3) {
               var1 = var3 - 1;
            }

            return var2[var1];
         }
      }

      return null;
   }

   public final boolean getNeedsClosureEnv() {
      return (this.flags & 24) != 0;
   }

   public final boolean getNeedsStaticLink() {
      return (this.flags & 16) != 0;
   }

   public LambdaExp getOwningLambda() {
      for(ScopeExp var1 = this.outer; var1 != null; var1 = var1.outer) {
         if(var1 instanceof ModuleExp || var1 instanceof ClassExp && this.getNeedsClosureEnv() || var1 instanceof LambdaExp && ((LambdaExp)var1).heapFrame != null) {
            return (LambdaExp)var1;
         }
      }

      return null;
   }

   public Object getProperty(Object var1, Object var2) {
      Object var3 = var2;
      if(this.properties != null) {
         int var4 = this.properties.length;

         while(true) {
            int var5 = var4 - 2;
            var3 = var2;
            if(var5 < 0) {
               break;
            }

            var4 = var5;
            if(this.properties[var5] == var1) {
               var3 = this.properties[var5 + 1];
               break;
            }
         }
      }

      return var3;
   }

   public final Type getReturnType() {
      if(this.returnType == null) {
         this.returnType = Type.objectType;
         if(this.body != null && !this.isAbstract()) {
            this.returnType = this.body.getType();
         }
      }

      return this.returnType;
   }

   int getSelectorValue(Compilation var1) {
      int var3 = this.selectorValue;
      int var2 = var3;
      if(var3 == 0) {
         var2 = var1.maxSelectorValue;
         var1.maxSelectorValue = this.primMethods.length + var2;
         ++var2;
         this.selectorValue = var2;
      }

      return var2;
   }

   public Type getType() {
      return this.type;
   }

   public int incomingArgs() {
      return this.min_args == this.max_args && this.max_args <= 4 && this.max_args > 0?this.max_args:1;
   }

   boolean inlinedIn(LambdaExp var1) {
      for(LambdaExp var2 = this; var2.getInlineOnly(); var2 = var2.getCaller()) {
         if(var2 == var1) {
            return true;
         }
      }

      return false;
   }

   public boolean isAbstract() {
      return this.body == QuoteExp.abstractExp;
   }

   public final boolean isClassGenerated() {
      return this.isModuleBody() || this instanceof ClassExp;
   }

   public final boolean isClassMethod() {
      return (this.flags & 64) != 0;
   }

   public final boolean isHandlingTailCalls() {
      return this.isModuleBody() || Compilation.defaultCallConvention >= 3 && !this.isClassMethod();
   }

   public final boolean isModuleBody() {
      return this instanceof ModuleExp;
   }

   public void loadHeapFrame(Compilation var1) {
      LambdaExp var2;
      for(var2 = var1.curLambda; var2 != this && var2.getInlineOnly(); var2 = var2.getCaller()) {
         ;
      }

      CodeAttr var4 = var1.getCode();
      if(var2.heapFrame != null && this == var2) {
         var4.emitLoad(var2.heapFrame);
      } else {
         ClassType var6;
         if(var2.closureEnv != null) {
            var4.emitLoad(var2.closureEnv);
            var6 = (ClassType)var2.closureEnv.getType();
         } else {
            var4.emitPushThis();
            var6 = var1.curClass;
         }

         while(var2 != this) {
            Field var5 = var2.staticLinkField;
            ClassType var3 = var6;
            if(var5 != null) {
               var3 = var6;
               if(var5.getDeclaringClass() == var6) {
                  var4.emitGetField(var5);
                  var3 = (ClassType)var5.getType();
               }
            }

            var2 = var2.outerLambda();
            var6 = var3;
         }
      }

   }

   protected boolean mustCompile() {
      if(this.keywords != null && this.keywords.length > 0) {
         return true;
      } else {
         if(this.defaultArgs != null) {
            int var2 = this.defaultArgs.length;

            while(true) {
               int var3 = var2 - 1;
               if(var3 < 0) {
                  break;
               }

               Expression var1 = this.defaultArgs[var3];
               var2 = var3;
               if(var1 != null) {
                  var2 = var3;
                  if(!(var1 instanceof QuoteExp)) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   public LambdaExp outerLambda() {
      return this.outer == null?null:this.outer.currentLambda();
   }

   public LambdaExp outerLambdaNotInline() {
      Object var1 = this;

      while(true) {
         ScopeExp var2 = ((ScopeExp)var1).outer;
         if(var2 == null) {
            return null;
         }

         var1 = var2;
         if(var2 instanceof LambdaExp) {
            LambdaExp var3 = (LambdaExp)var2;
            var1 = var2;
            if(!var3.getInlineOnly()) {
               return var3;
            }
         }
      }
   }

   public void print(OutPort var1) {
      var1.startLogicalBlock("(Lambda/", ")", 2);
      Object var2 = this.getSymbol();
      if(var2 != null) {
         var1.print((Object)var2);
         var1.print('/');
      }

      var1.print(this.id);
      var1.print('/');
      var1.print((String)"fl:");
      var1.print((String)Integer.toHexString(this.flags));
      var1.writeSpaceFill();
      this.printLineColumn(var1);
      var1.startLogicalBlock("(", false, ")");
      Special var4 = null;
      int var6 = 0;
      int var5;
      if(this.keywords == null) {
         var5 = 0;
      } else {
         var5 = this.keywords.length;
      }

      int var7;
      if(this.defaultArgs == null) {
         var7 = 0;
      } else {
         var7 = this.defaultArgs.length - var5;
      }

      Declaration var3 = this.firstDecl();
      if(var3 != null && var3.isThisParameter()) {
         var6 = -1;
         var5 = 0;
      } else {
         var5 = 0;
      }

      while(var3 != null) {
         Special var9;
         if(var6 < this.min_args) {
            var9 = null;
         } else if(var6 < this.min_args + var7) {
            var9 = Special.optional;
         } else if(this.max_args < 0 && var6 == this.min_args + var7) {
            var9 = Special.rest;
         } else {
            var9 = Special.key;
         }

         if(var3 != this.firstDecl()) {
            var1.writeSpaceFill();
         }

         if(var9 != var4) {
            var1.print((Object)var9);
            var1.writeSpaceFill();
         }

         Expression var10 = null;
         if(var9 == Special.optional || var9 == Special.key) {
            Expression[] var11 = this.defaultArgs;
            int var8 = var5 + 1;
            var10 = var11[var5];
            var5 = var8;
         }

         if(var10 != null) {
            var1.print('(');
         }

         var3.printInfo((OutPort)var1);
         if(var10 != null && var10 != QuoteExp.falseExp) {
            var1.print(' ');
            var10.print((OutPort)var1);
            var1.print(')');
         }

         ++var6;
         var3 = var3.nextDecl();
         var4 = var9;
      }

      var1.endLogicalBlock(")");
      var1.writeSpaceLinear();
      if(this.body == null) {
         var1.print((String)"<null body>");
      } else {
         this.body.print((OutPort)var1);
      }

      var1.endLogicalBlock(")");
   }

   public final Type restArgType() {
      if(this.min_args != this.max_args) {
         if(this.primMethods == null) {
            throw new Error("internal error - restArgType");
         }

         Method[] var1 = this.primMethods;
         if(this.max_args < 0 || var1.length <= this.max_args - this.min_args) {
            Method var5 = var1[var1.length - 1];
            Type[] var2 = var5.getParameterTypes();
            int var4 = var2.length - 1;
            int var3 = var4;
            if(var5.getName().endsWith("$X")) {
               var3 = var4 - 1;
            }

            return var2[var3];
         }
      }

      return null;
   }

   void setCallersNeedStaticLink() {
      LambdaExp var3 = this.outerLambda();

      for(ApplyExp var1 = this.nameDecl.firstCall; var1 != null; var1 = var1.nextCall) {
         for(LambdaExp var2 = var1.context; var2 != var3 && !(var2 instanceof ModuleExp); var2 = var2.outerLambda()) {
            var2.setNeedsStaticLink();
         }
      }

   }

   public final void setCanCall(boolean var1) {
      if(var1) {
         this.flags |= 4;
      } else {
         this.flags &= -5;
      }
   }

   public final void setCanRead(boolean var1) {
      if(var1) {
         this.flags |= 2;
      } else {
         this.flags &= -3;
      }
   }

   public final void setClassMethod(boolean var1) {
      if(var1) {
         this.flags |= 64;
      } else {
         this.flags &= -65;
      }
   }

   public final void setCoercedReturnType(Type var1) {
      this.returnType = var1;
      if(var1 != null && var1 != Type.objectType && var1 != Type.voidType && this.body != QuoteExp.abstractExp) {
         Expression var2 = this.body;
         this.body = Compilation.makeCoercion(var2, (Type)var1);
         this.body.setLine((Expression)var2);
      }

   }

   public final void setCoercedReturnValue(Expression var1, Language var2) {
      if(!this.isAbstract()) {
         Expression var3 = this.body;
         this.body = Compilation.makeCoercion(var3, (Expression)var1);
         this.body.setLine((Expression)var3);
      }

      Type var4 = var2.getTypeFor((Expression)var1);
      if(var4 != null) {
         this.setReturnType(var4);
      }

   }

   public void setExceptions(Expression[] var1) {
      this.throwsSpecification = var1;
   }

   public final void setImportsLexVars() {
      int var1 = this.flags;
      this.flags |= 8;
      if((var1 & 8) == 0 && this.nameDecl != null) {
         this.setCallersNeedStaticLink();
      }

   }

   public final void setImportsLexVars(boolean var1) {
      if(var1) {
         this.flags |= 8;
      } else {
         this.flags &= -9;
      }
   }

   public final void setInlineOnly(boolean var1) {
      this.setFlag(var1, 8192);
   }

   public final void setNeedsStaticLink() {
      int var1 = this.flags;
      this.flags |= 16;
      if((var1 & 16) == 0 && this.nameDecl != null) {
         this.setCallersNeedStaticLink();
      }

   }

   public final void setNeedsStaticLink(boolean var1) {
      if(var1) {
         this.flags |= 16;
      } else {
         this.flags &= -17;
      }
   }

   public void setProperty(Object var1, Object var2) {
      synchronized(this){}

      try {
         this.properties = PropertySet.setProperty(this.properties, var1, var2);
      } finally {
         ;
      }

   }

   public final void setReturnType(Type var1) {
      this.returnType = var1;
   }

   public void setType(ClassType var1) {
      this.type = var1;
   }

   public boolean side_effects() {
      return false;
   }

   public String toString() {
      String var2 = this.getExpClassName() + ':' + this.getSymbol() + '/' + this.id + '/';
      int var4 = this.getLineNumber();
      int var3 = var4;
      if(var4 <= 0) {
         var3 = var4;
         if(this.body != null) {
            var3 = this.body.getLineNumber();
         }
      }

      String var1 = var2;
      if(var3 > 0) {
         var1 = var2 + "l:" + var3;
      }

      return var1;
   }

   public Expression validateApply(ApplyExp var1, InlineCalls var2, Type var3, Declaration var4) {
      Expression[] var12 = var1.getArgs();
      Object var11;
      if((this.flags & 4096) != 0) {
         Expression var13 = InlineCalls.inlineCall(this, var12, true);
         if(var13 != null) {
            var11 = var2.visit(var13, (Type)var3);
            return (Expression)var11;
         }
      }

      var1.visitArgs(var2);
      int var6 = var1.args.length;
      String var10 = WrongArguments.checkArgCount(this.getName(), this.min_args, this.max_args, var6);
      if(var10 != null) {
         return var2.noteError(var10);
      } else {
         int var7 = this.getCallConvention();
         var11 = var1;
         if(var2.getCompilation().inlineOk((Expression)this)) {
            var11 = var1;
            if(this.isClassMethod()) {
               if(var7 > 2) {
                  var11 = var1;
                  if(var7 != 3) {
                     return (Expression)var11;
                  }
               }

               Method var15 = this.getMethod(var6);
               var11 = var1;
               if(var15 != null) {
                  boolean var8 = this.nameDecl.isStatic();
                  if(!var8 && this.outer instanceof ClassExp && ((ClassExp)this.outer).isMakingClassPair()) {
                     ;
                  }

                  PrimProcedure var14 = new PrimProcedure(var15, this);
                  Expression[] var9;
                  if(var8) {
                     var9 = var1.args;
                  } else {
                     LambdaExp var16 = var2.getCurrentLambda();

                     while(true) {
                        if(var16 == null) {
                           return var2.noteError("internal error: missing " + this);
                        }

                        if(var16.outer == this.outer) {
                           Declaration var5 = var16.firstDecl();
                           if(var5 == null || !var5.isThisParameter()) {
                              return var2.noteError("calling non-static method " + this.getName() + " from static method " + var16.getName());
                           }

                           var6 = var1.getArgCount();
                           var9 = new Expression[var6 + 1];
                           System.arraycopy(var1.getArgs(), 0, var9, 1, var6);
                           var9[0] = new ThisExp(var5);
                           break;
                        }

                        var16 = var16.outerLambda();
                     }
                  }

                  return (new ApplyExp(var14, var9)).setLine(var1);
               }
            }
         }

         return (Expression)var11;
      }
   }

   public final boolean variable_args() {
      return this.max_args < 0;
   }

   protected Object visit(ExpVisitor var1, Object var2) {
      Compilation var4 = var1.getCompilation();
      LambdaExp var3;
      if(var4 == null) {
         var3 = null;
      } else {
         var3 = var4.curLambda;
         var4.curLambda = this;
      }

      Object var7;
      try {
         var7 = var1.visitLambdaExp(this, var2);
      } finally {
         if(var4 != null) {
            var4.curLambda = var3;
         }

      }

      return var7;
   }

   protected void visitChildren(ExpVisitor var1, Object var2) {
      this.visitChildrenOnly(var1, var2);
      this.visitProperties(var1, var2);
   }

   protected final void visitChildrenOnly(ExpVisitor var1, Object var2) {
      LambdaExp var3 = var1.currentLambda;
      var1.currentLambda = this;

      try {
         this.throwsSpecification = var1.visitExps(this.throwsSpecification, var2);
         var1.visitDefaultArgs(this, var2);
         if(var1.exitValue == null && this.body != null) {
            this.body = var1.update(this.body, var1.visit(this.body, var2));
         }
      } finally {
         var1.currentLambda = var3;
      }

   }

   protected final void visitProperties(ExpVisitor var1, Object var2) {
      if(this.properties != null) {
         int var5 = this.properties.length;

         for(int var4 = 1; var4 < var5; var4 += 2) {
            Object var3 = this.properties[var4];
            if(var3 instanceof Expression) {
               this.properties[var4] = var1.visitAndUpdate((Expression)var3, var2);
            }
         }
      }

   }
}
