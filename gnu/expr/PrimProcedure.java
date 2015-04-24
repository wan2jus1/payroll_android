package gnu.expr;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassFileInput;
import gnu.bytecode.ClassType;
import gnu.bytecode.ClassTypeWriter;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.CheckedTarget;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.GenericProc;
import gnu.expr.IgnoreTarget;
import gnu.expr.Inlineable;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.ModuleMethod;
import gnu.expr.ReferenceExp;
import gnu.expr.Target;
import gnu.kawa.functions.MakeList;
import gnu.kawa.lispexpr.LangObjType;
import gnu.lists.Consumer;
import gnu.lists.ConsumerWriter;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.WrongArguments;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Member;
import java.net.URL;

public class PrimProcedure extends MethodProc implements Inlineable {

   private static ClassLoader systemClassLoader = PrimProcedure.class.getClassLoader();
   Type[] argTypes;
   Member member;
   Method method;
   char mode;
   int op_code;
   Type retType;
   boolean sideEffectFree;
   LambdaExp source;


   public PrimProcedure(int var1, ClassType var2, String var3, Type var4, Type[] var5) {
      byte var6 = 0;
      super();
      this.op_code = var1;
      byte var7;
      if(var1 == 184) {
         var7 = 8;
      } else {
         var7 = 0;
      }

      this.method = var2.addMethod(var3, var7, var5, var4);
      this.retType = var4;
      this.argTypes = var5;
      if(var1 != 184) {
         var6 = 86;
      }

      this.mode = (char)var6;
   }

   public PrimProcedure(int var1, Type var2, Type[] var3) {
      this.op_code = var1;
      this.retType = var2;
      this.argTypes = var3;
   }

   public PrimProcedure(Method var1) {
      this.init(var1);
      Object var2;
      if(var1.getName().endsWith("$X")) {
         var2 = Type.objectType;
      } else {
         var2 = var1.getReturnType();
      }

      this.retType = (Type)var2;
   }

   public PrimProcedure(Method var1, char var2, Language var3) {
      this.mode = var2;
      this.init(var1);
      Type[] var4 = this.argTypes;
      int var8 = var4.length;
      this.argTypes = null;
      int var7 = var8;

      while(true) {
         int var9 = var7 - 1;
         if(var9 < 0) {
            if(this.argTypes == null) {
               this.argTypes = var4;
            }

            if(this.isConstructor()) {
               this.retType = var1.getDeclaringClass();
            } else {
               if(var1.getName().endsWith("$X")) {
                  this.retType = Type.objectType;
                  return;
               }

               this.retType = var3.getLangTypeFor(var1.getReturnType());
               if(this.retType == Type.toStringType) {
                  this.retType = Type.javalangStringType;
                  return;
               }
            }

            return;
         }

         Type var5 = var4[var9];
         Type var6 = var3.getLangTypeFor(var5);
         var7 = var9;
         if(var5 != var6) {
            if(this.argTypes == null) {
               this.argTypes = new Type[var8];
               System.arraycopy(var4, 0, this.argTypes, 0, var8);
            }

            this.argTypes[var9] = var6;
            var7 = var9;
         }
      }
   }

   public PrimProcedure(Method var1, LambdaExp var2) {
      this(var1);
      this.retType = var2.getReturnType();
      this.source = var2;
   }

   public PrimProcedure(Method var1, Language var2) {
      this(var1, '\u0000', var2);
   }

   public PrimProcedure(String var1, String var2, int var3) {
      this(ClassType.make(var1).getDeclaredMethod(var2, var3));
   }

   public PrimProcedure(java.lang.reflect.Method var1, Language var2) {
      this((Method)((ClassType)var2.getTypeFor((Class)var1.getDeclaringClass())).getMethod(var1), (Language)var2);
   }

   private void compileArgs(Expression[] var1, int var2, Type var3, Compilation var4) {
      boolean var19 = this.takesVarArgs();
      String var8 = this.getName();
      Declaration var7 = null;
      CodeAttr var9 = var4.getCode();
      byte var11;
      if(var3 == Type.voidType) {
         var11 = 1;
      } else {
         var11 = 0;
      }

      int var10 = this.argTypes.length - var11;
      int var12 = var10;
      if(this.takesContext()) {
         var12 = var10 - 1;
      }

      int var17 = var1.length - var2;
      boolean var13;
      if(var3 != null && var11 == 0) {
         var13 = false;
      } else {
         var13 = true;
      }

      boolean var16 = false;
      boolean var15 = false;
      boolean var24 = var16;
      boolean var18 = var19;
      Type var5;
      Type var6;
      byte var14;
      if(var19) {
         var24 = var16;
         var18 = var19;
         if((this.method.getModifiers() & 128) != 0) {
            var24 = var16;
            var18 = var19;
            if(var17 > 0) {
               var24 = var16;
               var18 = var19;
               if(this.argTypes.length > 0) {
                  if(var13) {
                     var14 = 0;
                  } else {
                     var14 = 1;
                  }

                  var24 = var16;
                  var18 = var19;
                  if(var17 == var14 + var12) {
                     var5 = var1[var1.length - 1].getType();
                     var6 = this.argTypes[this.argTypes.length - 1];
                     var24 = var16;
                     var18 = var19;
                     if(var5 instanceof ObjectType) {
                        var24 = var16;
                        var18 = var19;
                        if(var6 instanceof ArrayType) {
                           var24 = var16;
                           var18 = var19;
                           if(!(((ArrayType)var6).getComponentType() instanceof ArrayType)) {
                              var24 = var15;
                              if(!(var5 instanceof ArrayType)) {
                                 var24 = true;
                              }

                              var18 = false;
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      int var26;
      if(var18) {
         if(var13) {
            var14 = 1;
         } else {
            var14 = 0;
         }

         var26 = var12 - var14;
      } else {
         var26 = var1.length - var2;
      }

      Declaration var21;
      if(this.source == null) {
         var21 = null;
      } else {
         var21 = this.source.firstDecl();
      }

      Declaration var20 = var21;
      if(var21 != null) {
         var20 = var21;
         if(var21.isThisParameter()) {
            var20 = var21.nextDecl();
         }
      }

      int var25 = 0;
      var6 = var7;
      var7 = var20;

      while(true) {
         var5 = var6;
         if(var18) {
            var5 = var6;
            if(var25 == var26) {
               var5 = this.argTypes[var12 - 1 + var11];
               if(var5 == Compilation.scmListType || var5 == LangObjType.listType) {
                  MakeList.compile(var1, var2 + var25, var4);
                  break;
               }

               var9.emitPushInt(var1.length - var2 - var26);
               var5 = ((ArrayType)var5).getComponentType();
               var9.emitNewArray(var5);
            }
         }

         if(var25 >= var17) {
            break;
         }

         if(var24 && var25 + 1 == var17) {
            var16 = true;
         } else {
            var16 = false;
         }

         if(var25 >= var26) {
            var9.emitDup(1);
            var9.emitPushInt(var25 - var26);
         } else if(var7 != null && (var13 || var25 > 0)) {
            var5 = var7.getType();
         } else if(var13) {
            var5 = this.argTypes[var25 + var11];
         } else if(var25 == 0) {
            var5 = var3;
         } else {
            var5 = this.argTypes[var25 - 1];
         }

         var4.usedClass(var5);
         Object var22;
         if(var16) {
            var22 = Type.objectType;
         } else {
            var22 = var5;
         }

         Target var23;
         if(this.source == null) {
            var23 = CheckedTarget.getInstance((Type)var22, (String)var8, var25 + 1);
         } else {
            var23 = CheckedTarget.getInstance((Type)var22, (LambdaExp)this.source, var25);
         }

         var1[var2 + var25].compileNotePosition(var4, var23, var1[var2 + var25]);
         if(var16) {
            var6 = ((ArrayType)var5).getComponentType();
            var9.emitDup();
            var9.emitInstanceof(var5);
            var9.emitIfIntNotZero();
            var9.emitCheckcast(var5);
            var9.emitElse();
            var9.emitPushInt(1);
            var9.emitNewArray(var6);
            var9.emitDupX();
            var9.emitSwap();
            var9.emitPushInt(0);
            var9.emitSwap();
            var6.emitCoerceFromObject(var9);
            var9.emitArrayStore(var5);
            var9.emitFi();
         }

         if(var25 >= var26) {
            var9.emitArrayStore(var5);
         }

         var21 = var7;
         if(var7 != null) {
            label115: {
               if(!var13) {
                  var21 = var7;
                  if(var25 <= 0) {
                     break label115;
                  }
               }

               var21 = var7.nextDecl();
            }
         }

         ++var25;
         var7 = var21;
         var6 = var5;
      }

   }

   public static void compileInvoke(Compilation var0, Method var1, Target var2, boolean var3, int var4, Type var5) {
      CodeAttr var7 = var0.getCode();
      var0.usedClass(var1.getDeclaringClass());
      var0.usedClass(var1.getReturnType());
      if(!takesContext(var1)) {
         var7.emitInvokeMethod(var1, var4);
      } else {
         Variable var6;
         if(var2 instanceof IgnoreTarget || var2 instanceof ConsumerTarget && ((ConsumerTarget)var2).isContextTarget()) {
            Field var8 = null;
            var6 = null;
            var0.loadCallContext();
            if(var2 instanceof IgnoreTarget) {
               ClassType var9 = Compilation.typeCallContext;
               var8 = var9.getDeclaredField("consumer");
               var7.pushScope();
               var6 = var7.addLocal(var9);
               var7.emitDup();
               var7.emitGetField(var8);
               var7.emitStore(var6);
               var7.emitDup();
               var7.emitGetStatic(ClassType.make("gnu.lists.VoidConsumer").getDeclaredField("instance"));
               var7.emitPutField(var8);
            }

            var7.emitInvokeMethod(var1, var4);
            if(var3) {
               var0.loadCallContext();
               var7.emitInvoke(Compilation.typeCallContext.getDeclaredMethod("runUntilDone", 0));
            }

            if(var2 instanceof IgnoreTarget) {
               var0.loadCallContext();
               var7.emitLoad(var6);
               var7.emitPutField(var8);
               var7.popScope();
               return;
            }

            return;
         }

         var0.loadCallContext();
         var5 = Type.objectType;
         var7.pushScope();
         var6 = var7.addLocal(Type.intType);
         var0.loadCallContext();
         var7.emitInvokeVirtual(Compilation.typeCallContext.getDeclaredMethod("startFromContext", 0));
         var7.emitStore(var6);
         var7.emitWithCleanupStart();
         var7.emitInvokeMethod(var1, var4);
         var7.emitWithCleanupCatch((Variable)null);
         var0.loadCallContext();
         var7.emitLoad(var6);
         var7.emitInvokeVirtual(Compilation.typeCallContext.getDeclaredMethod("cleanupFromContext", 1));
         var7.emitWithCleanupDone();
         var0.loadCallContext();
         var7.emitLoad(var6);
         var7.emitInvokeVirtual(Compilation.typeCallContext.getDeclaredMethod("getFromContext", 1));
         var7.popScope();
      }

      var2.compileFromStack(var0, (Type)var5);
   }

   public static void disassemble(Procedure var0, ClassTypeWriter var1) throws Exception {
      String var10;
      if(var0 instanceof GenericProc) {
         GenericProc var2 = (GenericProc)var0;
         int var9 = var2.getMethodCount();
         var1.print("Generic procedure with ");
         var1.print(var9);
         if(var9 == 1) {
            var10 = " method.";
         } else {
            var10 = "methods.";
         }

         var1.println(var10);

         for(int var8 = 0; var8 < var9; ++var8) {
            MethodProc var11 = var2.getMethod(var8);
            if(var11 != null) {
               var1.println();
               disassemble(var11, (ClassTypeWriter)var1);
            }
         }

      } else {
         ClassType var4 = null;
         Class var5 = var0.getClass();
         Class var3;
         String var13;
         if(var0 instanceof ModuleMethod) {
            var3 = ((ModuleMethod)var0).module.getClass();
            var13 = var4;
         } else {
            var3 = var5;
            var13 = var4;
            if(var0 instanceof PrimProcedure) {
               Method var6 = ((PrimProcedure)var0).method;
               var3 = var5;
               var13 = var4;
               if(var6 != null) {
                  var3 = var6.getDeclaringClass().getReflectClass();
                  var13 = var6.getName();
               }
            }
         }

         ClassLoader var15 = var3.getClassLoader();
         String var14 = var3.getName();
         String var17 = var14.replace('.', '/') + ".class";
         var4 = new ClassType();
         InputStream var7 = var15.getResourceAsStream(var17);
         if(var7 == null) {
            throw new RuntimeException("missing resource " + var17);
         } else {
            new ClassFileInput(var4, var7);
            var1.setClass(var4);
            URL var16 = var15.getResource(var17);
            var1.print("In class ");
            var1.print(var14);
            if(var16 != null) {
               var1.print(" at ");
               var1.print(var16);
            }

            var1.println();
            var14 = var13;
            if(var13 == null) {
               var10 = var0.getName();
               if(var10 == null) {
                  var1.println("Anonymous function - unknown method.");
                  return;
               }

               var14 = Compilation.mangleName(var10);
            }

            for(Method var12 = var4.getMethods(); var12 != null; var12 = var12.getNext()) {
               if(var12.getName().equals(var14)) {
                  var1.printMethod(var12);
               }
            }

            var1.flush();
         }
      }
   }

   public static void disassemble(Procedure var0, Writer var1) throws Exception {
      disassemble(var0, (ClassTypeWriter)(new ClassTypeWriter((ClassType)null, var1, 0)));
   }

   public static void disassemble$X(Procedure var0, CallContext var1) throws Exception {
      Consumer var2 = var1.consumer;
      Object var3;
      if(var2 instanceof Writer) {
         var3 = (Writer)var2;
      } else {
         var3 = new ConsumerWriter(var2);
      }

      disassemble(var0, (Writer)var3);
   }

   public static PrimProcedure getMethodFor(ClassType param0, String param1, Declaration param2, Type[] param3, Language param4) {
      // $FF: Couldn't be decompiled
   }

   public static PrimProcedure getMethodFor(ClassType var0, String var1, Declaration var2, Expression[] var3, Language var4) {
      int var6 = var3.length;
      Type[] var5 = new Type[var6];

      while(true) {
         --var6;
         if(var6 < 0) {
            return getMethodFor((ClassType)var0, var1, var2, (Type[])var5, var4);
         }

         var5[var6] = var3[var6].getType();
      }
   }

   public static PrimProcedure getMethodFor(Procedure var0, Declaration var1, Type[] var2, Language var3) {
      Object var4 = var0;
      PrimProcedure var9;
      if(var0 instanceof GenericProc) {
         GenericProc var5 = (GenericProc)var0;
         MethodProc[] var11 = var5.methods;
         MethodProc var8 = null;
         int var6 = var5.count;

         while(true) {
            int var7 = var6 - 1;
            if(var7 < 0) {
               var4 = var8;
               if(var8 == null) {
                  return null;
               }
               break;
            }

            var6 = var7;
            if(var11[var7].isApplicable(var2) >= 0) {
               if(var8 != null) {
                  var9 = null;
                  return var9;
               }

               var8 = var11[var7];
               var6 = var7;
            }
         }
      }

      if(var4 instanceof PrimProcedure) {
         PrimProcedure var12 = (PrimProcedure)var4;
         var9 = var12;
         if(var12.isApplicable(var2) >= 0) {
            return var9;
         }
      }

      Class var10 = getProcedureClass(var4);
      if(var10 == null) {
         return null;
      } else {
         return getMethodFor((ClassType)((ClassType)Type.make(var10)), ((Procedure)var4).getName(), var1, (Type[])var2, var3);
      }
   }

   public static PrimProcedure getMethodFor(Procedure var0, Declaration var1, Expression[] var2, Language var3) {
      int var5 = var2.length;
      Type[] var4 = new Type[var5];

      while(true) {
         --var5;
         if(var5 < 0) {
            return getMethodFor(var0, var1, (Type[])var4, var3);
         }

         var4[var5] = var2[var5].getType();
      }
   }

   public static PrimProcedure getMethodFor(Procedure var0, Expression[] var1) {
      return getMethodFor(var0, (Declaration)null, (Expression[])var1, Language.getDefaultLanguage());
   }

   public static PrimProcedure getMethodFor(Class var0, String var1, Declaration var2, Expression[] var3, Language var4) {
      return getMethodFor((ClassType)((ClassType)Type.make(var0)), var1, var2, (Expression[])var3, var4);
   }

   public static Class getProcedureClass(Object var0) {
      Class var4;
      if(var0 instanceof ModuleMethod) {
         var4 = ((ModuleMethod)var0).module.getClass();
      } else {
         var4 = var0.getClass();
      }

      ClassLoader var1;
      ClassLoader var2;
      try {
         var1 = var4.getClassLoader();
         var2 = systemClassLoader;
      } catch (SecurityException var3) {
         return null;
      }

      if(var1 == var2) {
         return var4;
      } else {
         return null;
      }
   }

   private void init(Method var1) {
      this.method = var1;
      if((var1.getModifiers() & 8) != 0) {
         this.op_code = 184;
      } else {
         ClassType var2 = var1.getDeclaringClass();
         if(this.mode == 80) {
            this.op_code = 183;
         } else {
            this.mode = 86;
            if("<init>".equals(var1.getName())) {
               this.op_code = 183;
            } else if((var2.getModifiers() & 512) != 0) {
               this.op_code = 185;
            } else {
               this.op_code = 182;
            }
         }
      }

      Type[] var3 = var1.getParameterTypes();
      Type[] var5 = var3;
      if(this.isConstructor()) {
         var5 = var3;
         if(var1.getDeclaringClass().hasOuterLink()) {
            int var4 = var3.length - 1;
            var5 = new Type[var4];
            System.arraycopy(var3, 1, var5, 0, var4);
         }
      }

      this.argTypes = var5;
   }

   public static PrimProcedure makeBuiltinBinary(int var0, Type var1) {
      return new PrimProcedure(var0, var1, new Type[]{var1, var1});
   }

   public static PrimProcedure makeBuiltinUnary(int var0, Type var1) {
      return new PrimProcedure(var0, var1, new Type[]{var1});
   }

   public static boolean takesContext(Method var0) {
      return var0.getName().endsWith("$X");
   }

   public void apply(CallContext param1) throws Throwable {
      // $FF: Couldn't be decompiled
   }

   void compile(Type var1, ApplyExp var2, Compilation var3, Target var4) {
      ClassType var6 = null;
      Type var5 = null;
      Expression[] var7 = var2.getArgs();
      CodeAttr var8 = var3.getCode();
      Type var9 = this.retType;
      byte var11 = 0;
      byte var10;
      if(this.isConstructor()) {
         ClassType var12;
         if(this.method == null) {
            var12 = var5;
         } else {
            var12 = this.method.getDeclaringClass();
         }

         if(var12.hasOuterLink()) {
            ClassExp.loadSuperStaticLink(var7[0], var12, var3);
         }

         var5 = null;
         var10 = 1;
      } else if(this.opcode() == 183 && this.mode == 80 && "<init>".equals(this.method.getName())) {
         if(this.method != null) {
            var6 = this.method.getDeclaringClass();
         }

         var10 = var11;
         var5 = var1;
         if(var6.hasOuterLink()) {
            var8.emitPushThis();
            var8.emitLoad(var8.getCurrentScope().getVariable(1));
            var5 = null;
            var10 = 1;
         }
      } else {
         var10 = var11;
         var5 = var1;
         if(this.takesTarget()) {
            var10 = var11;
            var5 = var1;
            if(this.method.getStaticFlag()) {
               var10 = 1;
               var5 = var1;
            }
         }
      }

      this.compileArgs(var7, var10, var5, var3);
      if(this.method == null) {
         var8.emitPrimop(this.opcode(), var7.length, this.retType);
         var4.compileFromStack(var3, var9);
      } else {
         compileInvoke(var3, this.method, var4, var2.isTailCall(), this.op_code, var9);
      }
   }

   public void compile(ApplyExp var1, Compilation var2, Target var3) {
      CodeAttr var6 = var2.getCode();
      ClassType var4;
      if(this.method == null) {
         var4 = null;
      } else {
         var4 = this.method.getDeclaringClass();
      }

      Expression[] var5 = var1.getArgs();
      if(this.isConstructor()) {
         if(var1.getFlag(8)) {
            int var8 = var5.length;
            var2.letStart();
            Expression[] var10 = new Expression[var8];
            var10[0] = var5[0];

            for(int var7 = 1; var7 < var8; ++var7) {
               Expression var12 = var5[var7];
               Declaration var11 = var2.letVariable((Object)null, var12.getType(), var12);
               var11.setCanRead(true);
               var10[var7] = new ReferenceExp(var11);
            }

            var2.letEnter();
            var2.letDone(new ApplyExp(var1.func, var10)).compile(var2, var3);
            return;
         }

         var6.emitNew(var4);
         var6.emitDup(var4);
      }

      String var9 = WrongArguments.checkArgCount(this, var5.length);
      if(var9 != null) {
         var2.error('e', var9);
      }

      if(this.getStaticFlag()) {
         var4 = null;
      }

      this.compile(var4, var1, var2, var3);
   }

   public Method getMethod() {
      return this.method;
   }

   public String getName() {
      String var1 = super.getName();
      if(var1 != null) {
         return var1;
      } else {
         var1 = this.getVerboseName();
         this.setName(var1);
         return var1;
      }
   }

   public Type getParameterType(int var1) {
      int var3 = var1;
      if(this.takesTarget()) {
         if(var1 == 0) {
            if(this.isConstructor()) {
               return Type.objectType;
            }

            return this.method.getDeclaringClass();
         }

         var3 = var1 - 1;
      }

      var1 = this.argTypes.length;
      if(var3 < var1 - 1) {
         return this.argTypes[var3];
      } else {
         boolean var4 = this.takesVarArgs();
         if(var3 < var1 && !var4) {
            return this.argTypes[var3];
         } else {
            Type var2 = this.argTypes[var1 - 1];
            return (Type)(var2 instanceof ArrayType?((ArrayType)var2).getComponentType():Type.objectType);
         }
      }
   }

   public final Type[] getParameterTypes() {
      return this.argTypes;
   }

   public Type getReturnType() {
      return this.retType;
   }

   public Type getReturnType(Expression[] var1) {
      return this.retType;
   }

   public final boolean getStaticFlag() {
      return this.method == null || this.method.getStaticFlag() || this.isConstructor();
   }

   public String getVerboseName() {
      StringBuffer var1 = new StringBuffer(100);
      if(this.method == null) {
         var1.append("<op ");
         var1.append(this.op_code);
         var1.append('>');
      } else {
         var1.append(this.method.getDeclaringClass().getName());
         var1.append('.');
         var1.append(this.method.getName());
      }

      var1.append('(');

      for(int var2 = 0; var2 < this.argTypes.length; ++var2) {
         if(var2 > 0) {
            var1.append(',');
         }

         var1.append(this.argTypes[var2].getName());
      }

      var1.append(')');
      return var1.toString();
   }

   public int isApplicable(Type[] var1) {
      int var4 = super.isApplicable(var1);
      int var5 = var1.length;
      int var3 = var4;
      if(var4 == -1) {
         var3 = var4;
         if(this.method != null) {
            var3 = var4;
            if((this.method.getModifiers() & 128) != 0) {
               var3 = var4;
               if(var5 > 0) {
                  var3 = var4;
                  if(var1[var5 - 1] instanceof ArrayType) {
                     Type[] var2 = new Type[var5];
                     System.arraycopy(var1, 0, var2, 0, var5 - 1);
                     var2[var5 - 1] = ((ArrayType)var1[var5 - 1]).getComponentType();
                     var3 = super.isApplicable(var2);
                  }
               }
            }
         }
      }

      return var3;
   }

   public final boolean isConstructor() {
      return this.opcode() == 183 && this.mode != 80;
   }

   public boolean isSideEffectFree() {
      return this.sideEffectFree;
   }

   public boolean isSpecial() {
      return this.mode == 80;
   }

   public int match0(CallContext var1) {
      return this.matchN(ProcedureN.noArgs, var1);
   }

   public int match1(Object var1, CallContext var2) {
      return this.matchN(new Object[]{var1}, var2);
   }

   public int match2(Object var1, Object var2, CallContext var3) {
      return this.matchN(new Object[]{var1, var2}, var3);
   }

   public int match3(Object var1, Object var2, Object var3, CallContext var4) {
      return this.matchN(new Object[]{var1, var2, var3}, var4);
   }

   public int match4(Object var1, Object var2, Object var3, Object var4, CallContext var5) {
      return this.matchN(new Object[]{var1, var2, var3, var4}, var5);
   }

   public int matchN(Object[] var1, CallContext var2) {
      int var14 = var1.length;
      boolean var15 = this.takesVarArgs();
      int var13 = this.minArgs();
      if(var14 < var13) {
         return -983040 | var13;
      } else if(!var15 && var14 > var13) {
         return -917504 | var13;
      } else {
         int var12 = this.argTypes.length;
         Object var3 = null;
         Object var4 = null;
         byte var10;
         if(!this.takesTarget() && !this.isConstructor()) {
            var10 = 0;
         } else {
            var10 = 1;
         }

         boolean var16 = this.takesContext();
         Object[] var9 = new Object[var12];
         int var11 = var12;
         if(var16) {
            var11 = var12 - 1;
            var9[var11] = var2;
         }

         Object[] var5 = (Object[])var4;
         if(var15) {
            Type var19 = this.argTypes[var11 - 1];
            if(var19 != Compilation.scmListType && var19 != LangObjType.listType) {
               var3 = ((ArrayType)var19).getComponentType();
               var5 = (Object[])((Object[])Array.newInstance(((Type)var3).getReflectClass(), var14 - var13));
               var9[var11 - 1] = var5;
            } else {
               var9[var11 - 1] = LList.makeList(var1, var13);
               var3 = Type.objectType;
               var5 = (Object[])var4;
            }
         }

         if(this.isConstructor()) {
            var4 = var1[0];
         } else if(var10 != 0) {
            try {
               var4 = this.method.getDeclaringClass().coerceFromObject(var1[0]);
            } catch (ClassCastException var18) {
               return -786431;
            }
         } else {
            var4 = null;
         }

         for(var11 = var10; var11 < var1.length; ++var11) {
            Object var8 = var1[var11];
            Object var6;
            if(var11 < var13) {
               var6 = this.argTypes[var11 - var10];
            } else {
               var6 = var3;
            }

            Object var7 = var8;
            if(var6 != Type.objectType) {
               try {
                  var7 = ((Type)var6).coerceFromObject(var8);
               } catch (ClassCastException var17) {
                  return -786432 | var11 + 1;
               }
            }

            if(var11 < var13) {
               var9[var11 - var10] = var7;
            } else if(var5 != null) {
               var5[var11 - var13] = var7;
            }
         }

         var2.value1 = var4;
         var2.values = var9;
         var2.proc = this;
         return 0;
      }
   }

   public int numArgs() {
      int var2 = this.argTypes.length;
      int var1 = var2;
      if(this.takesTarget()) {
         var1 = var2 + 1;
      }

      var2 = var1;
      if(this.takesContext()) {
         var2 = var1 - 1;
      }

      return this.takesVarArgs()?var2 - 1 - 4096:(var2 << 12) + var2;
   }

   public final int opcode() {
      return this.op_code;
   }

   public void print(PrintWriter var1) {
      var1.print("#<primitive procedure ");
      var1.print(this.toString());
      var1.print('>');
   }

   public void setReturnType(Type var1) {
      this.retType = var1;
   }

   public void setSideEffectFree() {
      this.sideEffectFree = true;
   }

   public boolean takesContext() {
      return this.method != null && takesContext(this.method);
   }

   public boolean takesTarget() {
      return this.mode != 0;
   }

   public boolean takesVarArgs() {
      boolean var3 = false;
      boolean var2 = var3;
      if(this.method != null) {
         if((this.method.getModifiers() & 128) != 0) {
            var2 = true;
         } else {
            String var1 = this.method.getName();
            if(!var1.endsWith("$V")) {
               var2 = var3;
               if(!var1.endsWith("$V$X")) {
                  return var2;
               }
            }

            return true;
         }
      }

      return var2;
   }

   public String toString() {
      StringBuffer var2 = new StringBuffer(100);
      String var1;
      if(this.retType == null) {
         var1 = "<unknown>";
      } else {
         var1 = this.retType.getName();
      }

      var2.append(var1);
      var2.append(' ');
      var2.append(this.getVerboseName());
      return var2.toString();
   }
}
