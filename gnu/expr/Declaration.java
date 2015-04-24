package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Label;
import gnu.bytecode.Method;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.AccessExp;
import gnu.expr.ApplyExp;
import gnu.expr.BindingInitializer;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.FluidLetExp;
import gnu.expr.IgnoreTarget;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.Literal;
import gnu.expr.ModuleExp;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.Target;
import gnu.expr.ThisExp;
import gnu.expr.TypeValue;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.Location;
import gnu.mapping.Named;
import gnu.mapping.Namespace;
import gnu.mapping.OutPort;
import gnu.mapping.Symbol;
import gnu.mapping.WrappedException;
import gnu.math.IntNum;
import gnu.text.Char;
import gnu.text.SourceLocator;

public class Declaration implements SourceLocator {

   static final int CAN_CALL = 4;
   static final int CAN_READ = 2;
   static final int CAN_WRITE = 8;
   public static final long CLASS_ACCESS_FLAGS = 25820135424L;
   public static final int EARLY_INIT = 536870912;
   public static final long ENUM_ACCESS = 8589934592L;
   public static final int EXPORT_SPECIFIED = 1024;
   public static final int EXTERNAL_ACCESS = 524288;
   public static final long FIELD_ACCESS_FLAGS = 32463912960L;
   public static final int FIELD_OR_METHOD = 1048576;
   public static final long FINAL_ACCESS = 17179869184L;
   static final int INDIRECT_BINDING = 1;
   public static final int IS_ALIAS = 256;
   public static final int IS_CONSTANT = 16384;
   public static final int IS_DYNAMIC = 268435456;
   static final int IS_FLUID = 16;
   public static final int IS_IMPORTED = 131072;
   public static final int IS_NAMESPACE_PREFIX = 2097152;
   static final int IS_SIMPLE = 64;
   public static final int IS_SINGLE_VALUE = 262144;
   public static final int IS_SYNTAX = 32768;
   public static final int IS_UNKNOWN = 65536;
   public static final long METHOD_ACCESS_FLAGS = 17431527424L;
   public static final int MODULE_REFERENCE = 1073741824;
   public static final int NONSTATIC_SPECIFIED = 4096;
   public static final int NOT_DEFINING = 512;
   public static final int PACKAGE_ACCESS = 134217728;
   static final int PRIVATE = 32;
   public static final int PRIVATE_ACCESS = 16777216;
   public static final String PRIVATE_PREFIX = "$Prvt$";
   public static final int PRIVATE_SPECIFIED = 16777216;
   static final int PROCEDURE = 128;
   public static final int PROTECTED_ACCESS = 33554432;
   public static final int PUBLIC_ACCESS = 67108864;
   public static final int STATIC_SPECIFIED = 2048;
   public static final long TRANSIENT_ACCESS = 4294967296L;
   public static final int TYPE_SPECIFIED = 8192;
   static final String UNKNOWN_PREFIX = "loc$";
   public static final long VOLATILE_ACCESS = 2147483648L;
   static int counter;
   public Declaration base;
   public ScopeExp context;
   int evalIndex;
   public Field field;
   String filename;
   public ApplyExp firstCall;
   protected long flags;
   protected int id;
   Method makeLocationMethod;
   Declaration next;
   Declaration nextCapturedVar;
   int position;
   Object symbol;
   protected Type type;
   protected Expression typeExp;
   protected Expression value;
   Variable var;


   protected Declaration() {
      int var1 = counter + 1;
      counter = var1;
      this.id = var1;
      this.value = QuoteExp.undefined_exp;
      this.flags = 64L;
      this.makeLocationMethod = null;
   }

   public Declaration(Variable var1) {
      this(var1.getName(), (Type)var1.getType());
      this.var = var1;
   }

   public Declaration(Object var1) {
      int var2 = counter + 1;
      counter = var2;
      this.id = var2;
      this.value = QuoteExp.undefined_exp;
      this.flags = 64L;
      this.makeLocationMethod = null;
      this.setName(var1);
   }

   public Declaration(Object var1, Field var2) {
      this(var1, (Type)var2.getType());
      this.field = var2;
      this.setSimple(false);
   }

   public Declaration(Object var1, Type var2) {
      int var3 = counter + 1;
      counter = var3;
      this.id = var3;
      this.value = QuoteExp.undefined_exp;
      this.flags = 64L;
      this.makeLocationMethod = null;
      this.setName(var1);
      this.setType(var2);
   }

   public static Declaration followAliases(Declaration var0) {
      while(true) {
         if(var0 != null && var0.isAlias()) {
            Expression var1 = var0.getValue();
            if(var1 instanceof ReferenceExp) {
               Declaration var2 = ((ReferenceExp)var1).binding;
               if(var2 != null) {
                  var0 = var2;
                  continue;
               }
            }
         }

         return var0;
      }
   }

   public static Declaration getDeclaration(Named var0) {
      return getDeclaration(var0, var0.getName());
   }

   public static Declaration getDeclaration(Object var0, String var1) {
      Object var3 = null;
      Field var2 = (Field)var3;
      if(var1 != null) {
         Class var4 = PrimProcedure.getProcedureClass(var0);
         var2 = (Field)var3;
         if(var4 != null) {
            var2 = ((ClassType)Type.make(var4)).getDeclaredField(Compilation.mangleNameIfNeeded(var1));
         }
      }

      if(var2 != null) {
         int var5 = var2.getModifiers();
         if((var5 & 8) != 0) {
            Declaration var6 = new Declaration(var1, var2);
            var6.noteValue(new QuoteExp(var0));
            if((var5 & 16) != 0) {
               var6.setFlag(16384L);
            }

            return var6;
         }
      }

      return null;
   }

   public static Declaration getDeclarationFromStatic(String var0, String var1) {
      Declaration var2 = new Declaration(var1, ClassType.make(var0).getDeclaredField(var1));
      var2.setFlag(18432L);
      return var2;
   }

   public static Declaration getDeclarationValueFromStatic(String var0, String var1, String var2) {
      try {
         Object var3 = Class.forName(var0).getDeclaredField(var1).get((Object)null);
         Declaration var5 = new Declaration(var2, ClassType.make(var0).getDeclaredField(var1));
         var5.noteValue(new QuoteExp(var3));
         var5.setFlag(18432L);
         return var5;
      } catch (Exception var4) {
         throw new WrappedException(var4);
      }
   }

   public static final boolean isUnknown(Declaration var0) {
      return var0 == null || var0.getFlag(65536L);
   }

   public final Variable allocateVariable(CodeAttr var1) {
      if(!this.isSimple() || this.var == null) {
         String var2 = null;
         if(this.symbol != null) {
            var2 = Compilation.mangleNameIfNeeded(this.getName());
         }

         if(this.isAlias() && this.getValue() instanceof ReferenceExp) {
            Declaration var4 = followAliases(this);
            Variable var5;
            if(var4 == null) {
               var5 = null;
            } else {
               var5 = var4.var;
            }

            this.var = var5;
         } else {
            Object var3;
            if(this.isIndirectBinding()) {
               var3 = Compilation.typeLocation;
            } else {
               var3 = this.getType().getImplementationType();
            }

            this.var = this.context.getVarScope().addVariable(var1, (Type)var3, var2);
         }
      }

      return this.var;
   }

   public void compileStore(Compilation var1) {
      CodeAttr var2 = var1.getCode();
      if(this.isSimple()) {
         var2.emitStore(this.getVariable());
      } else if(!this.field.getStaticFlag()) {
         this.loadOwningObject((Declaration)null, var1);
         var2.emitSwap();
         var2.emitPutField(this.field);
      } else {
         var2.emitPutStatic(this.field);
      }
   }

   public short getAccessFlags(short var1) {
      short var2;
      if(this.getFlag(251658240L)) {
         var1 = 0;
         if(this.getFlag(16777216L)) {
            var1 = (short)2;
         }

         var2 = var1;
         if(this.getFlag(33554432L)) {
            var2 = (short)(var1 | 4);
         }

         var1 = var2;
         if(this.getFlag(67108864L)) {
            var1 = (short)(var2 | 1);
         }
      }

      var2 = var1;
      if(this.getFlag(2147483648L)) {
         var2 = (short)(var1 | 64);
      }

      var1 = var2;
      if(this.getFlag(4294967296L)) {
         var1 = (short)(var2 | 128);
      }

      var2 = var1;
      if(this.getFlag(8589934592L)) {
         var2 = (short)(var1 | 16384);
      }

      var1 = var2;
      if(this.getFlag(17179869184L)) {
         var1 = (short)(var2 | 16);
      }

      return var1;
   }

   public final boolean getCanCall() {
      return (this.flags & 4L) != 0L;
   }

   public final boolean getCanRead() {
      return (this.flags & 2L) != 0L;
   }

   public final boolean getCanWrite() {
      return (this.flags & 8L) != 0L;
   }

   public int getCode() {
      return this.id;
   }

   public final int getColumnNumber() {
      int var2 = this.position & 4095;
      int var1 = var2;
      if(var2 == 0) {
         var1 = -1;
      }

      return var1;
   }

   public final Object getConstantValue() {
      Expression var1 = this.getValue();
      return var1 instanceof QuoteExp && var1 != QuoteExp.undefined_exp?((QuoteExp)var1).getValue():null;
   }

   public final ScopeExp getContext() {
      return this.context;
   }

   public final String getFileName() {
      return this.filename;
   }

   public final boolean getFlag(long var1) {
      return (this.flags & var1) != 0L;
   }

   public final int getLineNumber() {
      int var2 = this.position >> 12;
      int var1 = var2;
      if(var2 == 0) {
         var1 = -1;
      }

      return var1;
   }

   public final String getName() {
      return this.symbol == null?null:(this.symbol instanceof Symbol?((Symbol)this.symbol).getName():this.symbol.toString());
   }

   public String getPublicId() {
      return null;
   }

   public final Object getSymbol() {
      return this.symbol;
   }

   public String getSystemId() {
      return this.filename;
   }

   public final Type getType() {
      if(this.type == null) {
         this.setType(Type.objectType);
      }

      return this.type;
   }

   public final Expression getTypeExp() {
      if(this.typeExp == null) {
         this.setType(Type.objectType);
      }

      return this.typeExp;
   }

   public final Expression getValue() {
      if(this.value == QuoteExp.undefined_exp) {
         if(this.field != null && (this.field.getModifiers() & 24) == 24 && !this.isIndirectBinding()) {
            try {
               this.value = new QuoteExp(this.field.getReflectField().get((Object)null));
            } catch (Throwable var4) {
               ;
            }
         }
      } else if(this.value instanceof QuoteExp && this.getFlag(8192L) && this.value.getType() != this.type) {
         try {
            Object var1 = ((QuoteExp)this.value).getValue();
            Type var2 = this.getType();
            this.value = new QuoteExp(var2.coerceFromObject(var1), var2);
         } catch (Throwable var3) {
            ;
         }
      }

      return this.value;
   }

   public Variable getVariable() {
      return this.var;
   }

   public final boolean hasConstantValue() {
      Expression var1 = this.getValue();
      return var1 instanceof QuoteExp && var1 != QuoteExp.undefined_exp;
   }

   public boolean ignorable() {
      if(!this.getCanRead() && !this.isPublic() && (!this.getCanWrite() || !this.getFlag(65536L))) {
         if(!this.getCanCall()) {
            return true;
         }

         Expression var1 = this.getValue();
         if(var1 != null && var1 instanceof LambdaExp) {
            LambdaExp var2 = (LambdaExp)var1;
            if(!var2.isHandlingTailCalls() || var2.getInlineOnly()) {
               return true;
            }
         }
      }

      return false;
   }

   public final boolean isAlias() {
      return (this.flags & 256L) != 0L;
   }

   public boolean isCompiletimeConstant() {
      return this.getFlag(16384L) && this.hasConstantValue();
   }

   public final boolean isFluid() {
      return (this.flags & 16L) != 0L;
   }

   public final boolean isIndirectBinding() {
      return (this.flags & 1L) != 0L;
   }

   public final boolean isLexical() {
      return (this.flags & 268501008L) == 0L;
   }

   public final boolean isNamespaceDecl() {
      return (this.flags & 2097152L) != 0L;
   }

   public final boolean isPrivate() {
      return (this.flags & 32L) != 0L;
   }

   public final boolean isProcedureDecl() {
      return (this.flags & 128L) != 0L;
   }

   public final boolean isPublic() {
      return this.context instanceof ModuleExp && (this.flags & 32L) == 0L;
   }

   public final boolean isSimple() {
      return (this.flags & 64L) != 0L;
   }

   public boolean isStableSourceLocation() {
      return true;
   }

   public boolean isStatic() {
      boolean var3 = true;
      boolean var2;
      if(this.field != null) {
         var2 = this.field.getStaticFlag();
      } else {
         var2 = var3;
         if(!this.getFlag(2048L)) {
            var2 = var3;
            if(!this.isCompiletimeConstant()) {
               if(this.getFlag(4096L)) {
                  return false;
               }

               LambdaExp var1 = this.context.currentLambda();
               if(var1 instanceof ModuleExp) {
                  var2 = var3;
                  if(((ModuleExp)var1).isStatic()) {
                     return var2;
                  }
               }

               return false;
            }
         }
      }

      return var2;
   }

   public final boolean isThisParameter() {
      return this.symbol == ThisExp.THIS_NAME;
   }

   public void load(AccessExp var1, int var2, Compilation var3, Target var4) {
      if(!(var4 instanceof IgnoreTarget)) {
         Declaration var5;
         if(var1 == null) {
            var5 = null;
         } else {
            var5 = var1.contextDecl();
         }

         if(this.isAlias() && this.value instanceof ReferenceExp) {
            ReferenceExp var6 = (ReferenceExp)this.value;
            Declaration var7 = var6.binding;
            if(var7 != null && ((var2 & 2) == 0 || var7.isIndirectBinding()) && (var5 == null || !var7.needsContext())) {
               var7.load(var6, var2, var3, var4);
               return;
            }
         }

         if(this.isFluid() && this.context instanceof FluidLetExp) {
            this.base.load(var1, var2, var3, var4);
         } else {
            CodeAttr var9 = var3.getCode();
            Type var22 = this.getType();
            boolean var13;
            Object var19;
            Method var24;
            if(!this.isIndirectBinding() && (var2 & 2) != 0) {
               if(this.field == null) {
                  throw new Error("internal error: cannot take location of " + this);
               }

               var13 = var3.immediate;
               ClassType var15;
               byte var16;
               Method var29;
               if(this.field.getStaticFlag()) {
                  var15 = ClassType.make("gnu.kawa.reflect.StaticFieldLocation");
                  if(var13) {
                     var16 = 1;
                  } else {
                     var16 = 2;
                  }

                  var29 = var15.getDeclaredMethod("make", var16);
               } else {
                  var15 = ClassType.make("gnu.kawa.reflect.FieldLocation");
                  if(var13) {
                     var16 = 2;
                  } else {
                     var16 = 3;
                  }

                  var24 = var15.getDeclaredMethod("make", var16);
                  this.loadOwningObject(var5, var3);
                  var29 = var24;
               }

               if(var13) {
                  var3.compileConstant(this);
               } else {
                  var3.compileConstant(this.field.getDeclaringClass().getName());
                  var3.compileConstant(this.field.getName());
               }

               var9.emitInvokeStatic(var29);
               var19 = var15;
            } else {
               Label var8;
               if(this.field != null) {
                  var3.usedClass(this.field.getDeclaringClass());
                  var3.usedClass(this.field.getType());
                  if(!this.field.getStaticFlag()) {
                     this.loadOwningObject(var5, var3);
                     var9.emitGetField(this.field);
                  } else {
                     var9.emitGetStatic(this.field);
                  }
               } else if(this.isIndirectBinding() && var3.immediate && this.getVariable() == null) {
                  Environment var10 = Environment.getCurrent();
                  Symbol var25;
                  if(this.symbol instanceof Symbol) {
                     var25 = (Symbol)this.symbol;
                  } else {
                     var25 = var10.getSymbol(this.symbol.toString());
                  }

                  var8 = null;
                  Object var21 = var8;
                  if(this.isProcedureDecl()) {
                     var21 = var8;
                     if(var3.getLanguage().hasSeparateFunctionNamespace()) {
                        var21 = EnvironmentKey.FUNCTION;
                     }
                  }

                  var3.compileConstant(var10.getLocation(var25, var21), Target.pushValue(Compilation.typeLocation));
               } else {
                  label174: {
                     if(var3.immediate) {
                        var19 = this.getConstantValue();
                        if(var19 != null) {
                           var3.compileConstant(var19, var4);
                           return;
                        }
                     }

                     if(this.value != QuoteExp.undefined_exp && this.ignorable() && (!(this.value instanceof LambdaExp) || !(((LambdaExp)this.value).outer instanceof ModuleExp))) {
                        this.value.compile(var3, (Target)var4);
                        return;
                     }

                     Variable var20 = this.getVariable();
                     if(this.context instanceof ClassExp && var20 == null && !this.getFlag(128L)) {
                        ClassExp var17 = (ClassExp)this.context;
                        if(var17.isMakingClassPair()) {
                           String var18 = ClassExp.slotToMethodName("get", this.getName());
                           var24 = var17.type.getDeclaredMethod(var18, 0);
                           var17.loadHeapFrame(var3);
                           var9.emitInvoke(var24);
                           break label174;
                        }
                     }

                     Variable var23 = var20;
                     if(var20 == null) {
                        var23 = this.allocateVariable(var9);
                     }

                     var9.emitLoad(var23);
                  }
               }

               var19 = var22;
               if(this.isIndirectBinding()) {
                  var19 = var22;
                  if((var2 & 2) == 0) {
                     label115: {
                        if(var1 != null) {
                           String var28 = var1.getFileName();
                           if(var28 != null) {
                              int var11 = var1.getLineNumber();
                              if(var11 > 0) {
                                 ClassType var27 = ClassType.make("gnu.mapping.UnboundLocationException");
                                 var13 = var9.isInTry();
                                 int var12 = var1.getColumnNumber();
                                 Label var14 = new Label(var9);
                                 var14.define(var9);
                                 var9.emitInvokeVirtual(Compilation.getLocationMethod);
                                 Label var26 = new Label(var9);
                                 var26.define(var9);
                                 var8 = new Label(var9);
                                 var8.setTypes((CodeAttr)var9);
                                 if(var13) {
                                    var9.emitGoto(var8);
                                 } else {
                                    var9.setUnreachable();
                                 }

                                 var2 = 0;
                                 if(!var13) {
                                    var2 = var9.beginFragment(var8);
                                 }

                                 var9.addHandler(var14, var26, var27);
                                 var9.emitDup(var27);
                                 var9.emitPushString(var28);
                                 var9.emitPushInt(var11);
                                 var9.emitPushInt(var12);
                                 var9.emitInvokeVirtual(var27.getDeclaredMethod("setLine", 3));
                                 var9.emitThrow();
                                 if(var13) {
                                    var8.define(var9);
                                 } else {
                                    var9.endFragment(var2);
                                 }
                                 break label115;
                              }
                           }
                        }

                        var9.emitInvokeVirtual(Compilation.getLocationMethod);
                     }

                     var19 = Type.pointer_type;
                  }
               }
            }

            var4.compileFromStack(var3, (Type)var19);
         }
      }
   }

   void loadOwningObject(Declaration var1, Compilation var2) {
      Declaration var3 = var1;
      if(var1 == null) {
         var3 = this.base;
      }

      if(var3 != null) {
         var3.load((AccessExp)null, 0, var2, Target.pushObject);
      } else {
         this.getContext().currentLambda().loadHeapFrame(var2);
      }
   }

   public void makeField(ClassType var1, Compilation var2, Expression var3) {
      boolean var10 = this.needsExternalAccess();
      int var8 = 0;
      boolean var11 = this.getFlag(16384L);
      boolean var12 = this.getFlag(8192L);
      if(var2.immediate && this.context instanceof ModuleExp && !var11 && !var12) {
         this.setIndirectBinding(true);
      }

      if(this.isPublic() || var10 || var2.immediate) {
         var8 = 0 | 1;
      }

      int var7;
      label126: {
         if(!this.isStatic() && (!this.getFlag(268501008L) || !this.isIndirectBinding() || this.isAlias())) {
            var7 = var8;
            if(!(var3 instanceof ClassExp)) {
               break label126;
            }

            var7 = var8;
            if(((LambdaExp)var3).getNeedsClosureEnv()) {
               break label126;
            }
         }

         var7 = var8 | 8;
      }

      label137: {
         if(!this.isIndirectBinding()) {
            var8 = var7;
            if(!var11) {
               break label137;
            }

            if(!this.shouldEarlyInit()) {
               var8 = var7;
               if(!(this.context instanceof ModuleExp)) {
                  break label137;
               }

               var8 = var7;
               if(!((ModuleExp)this.context).staticInitRun()) {
                  break label137;
               }
            }
         }

         if(!(this.context instanceof ClassExp)) {
            var8 = var7;
            if(!(this.context instanceof ModuleExp)) {
               break label137;
            }
         }

         var8 = var7 | 16;
      }

      Type var4 = this.getType().getImplementationType();
      Object var6 = var4;
      if(this.isIndirectBinding()) {
         var6 = var4;
         if(!var4.isSubtype(Compilation.typeLocation)) {
            var6 = Compilation.typeLocation;
         }
      }

      if(!this.ignorable()) {
         String var15 = this.getName();
         String var5;
         if(var15 == null) {
            var5 = "$unnamed$0";
            var7 = "$unnamed$0".length() - 2;
         } else {
            var5 = Compilation.mangleNameIfNeeded(var15);
            var15 = var5;
            if(this.getFlag(65536L)) {
               var15 = "loc$" + var5;
            }

            var5 = var15;
            if(var10) {
               var5 = var15;
               if(!this.getFlag(1073741824L)) {
                  var5 = "$Prvt$" + var15;
               }
            }

            var7 = var5.length();
         }

         StringBuilder var17;
         for(int var9 = 0; var1.getDeclaredField(var5) != null; var5 = var17.append(var9).toString()) {
            var17 = (new StringBuilder()).append(var5.substring(0, var7)).append('$');
            ++var9;
         }

         this.field = var1.addField(var5, (Type)var6, var8);
         if(var3 instanceof QuoteExp) {
            Object var16 = ((QuoteExp)var3).getValue();
            if(this.field.getStaticFlag() && var16.getClass().getName().equals(((Type)var6).getName())) {
               Literal var13 = var2.litTable.findLiteral(var16);
               if(var13.field == null) {
                  var13.assign((Field)this.field, var2.litTable);
               }
            } else if(var6 instanceof PrimType || "java.lang.String".equals(((Type)var6).getName())) {
               Object var14 = var16;
               if(var16 instanceof Char) {
                  var14 = IntNum.make(((Char)var16).intValue());
               }

               this.field.setConstantValue(var14, var1);
               return;
            }
         }
      }

      if(!this.shouldEarlyInit() && (this.isIndirectBinding() || var3 != null && !(var3 instanceof ClassExp))) {
         BindingInitializer.create(this, var3, var2);
      }

   }

   public void makeField(Compilation var1, Expression var2) {
      this.setSimple(false);
      this.makeField(var1.mainClass, var1, var2);
   }

   Location makeIndirectLocationFor() {
      Symbol var1;
      if(this.symbol instanceof Symbol) {
         var1 = (Symbol)this.symbol;
      } else {
         var1 = Namespace.EmptyNamespace.getSymbol(this.symbol.toString().intern());
      }

      return Location.make((Symbol)var1);
   }

   public void maybeIndirectBinding(Compilation var1) {
      if(this.isLexical() && !(this.context instanceof ModuleExp) || this.context == var1.mainLambda) {
         this.setIndirectBinding(true);
      }

   }

   public final boolean needsContext() {
      return this.base == null && this.field != null && !this.field.getStaticFlag();
   }

   public final boolean needsExternalAccess() {
      return (this.flags & 524320L) == 524320L || (this.flags & 2097184L) == 2097184L;
   }

   public boolean needsInit() {
      return !this.ignorable() && (this.value != QuoteExp.nullExp || this.base == null);
   }

   public final Declaration nextDecl() {
      return this.next;
   }

   public void noteValue(Expression var1) {
      if(this.value == QuoteExp.undefined_exp) {
         if(var1 instanceof LambdaExp) {
            ((LambdaExp)var1).nameDecl = this;
         }

         this.value = var1;
      } else if(this.value != var1) {
         if(this.value instanceof LambdaExp) {
            ((LambdaExp)this.value).nameDecl = null;
         }

         this.value = null;
         return;
      }

   }

   public void printInfo(OutPort var1) {
      StringBuffer var2 = new StringBuffer();
      this.printInfo((StringBuffer)var2);
      var1.print((String)var2.toString());
   }

   public void printInfo(StringBuffer var1) {
      var1.append(this.symbol);
      var1.append('/');
      var1.append(this.id);
      var1.append("/fl:");
      var1.append(Long.toHexString(this.flags));
      if(this.ignorable()) {
         var1.append("(ignorable)");
      }

      Expression var2 = this.typeExp;
      Type var3 = this.getType();
      if(var2 != null && !(var2 instanceof QuoteExp)) {
         var1.append("::");
         var1.append(var2);
      } else if(this.type != null && var3 != Type.pointer_type) {
         var1.append("::");
         var1.append(var3.getName());
      }

      if(this.base != null) {
         var1.append("(base:#");
         var1.append(this.base.id);
         var1.append(')');
      }

   }

   public void pushIndirectBinding(Compilation var1) {
      CodeAttr var6 = var1.getCode();
      var6.emitPushString(this.getName());
      if(this.makeLocationMethod == null) {
         ClassType var2 = Type.pointer_type;
         ClassType var3 = Type.string_type;
         ClassType var4 = Compilation.typeLocation;
         ClassType var5 = Compilation.typeLocation;
         this.makeLocationMethod = var4.addMethod("make", new Type[]{var2, var3}, var5, 9);
      }

      var6.emitInvokeStatic(this.makeLocationMethod);
   }

   public final void setAlias(boolean var1) {
      this.setFlag(var1, 256L);
   }

   public final void setCanCall() {
      this.setFlag(true, 4L);
      if(this.base != null) {
         this.base.setCanRead();
      }

   }

   public final void setCanCall(boolean var1) {
      this.setFlag(var1, 4L);
   }

   public final void setCanRead() {
      this.setFlag(true, 2L);
      if(this.base != null) {
         this.base.setCanRead();
      }

   }

   public final void setCanRead(boolean var1) {
      this.setFlag(var1, 2L);
   }

   public final void setCanWrite() {
      this.flags |= 8L;
      if(this.base != null) {
         this.base.setCanRead();
      }

   }

   public final void setCanWrite(boolean var1) {
      if(var1) {
         this.flags |= 8L;
      } else {
         this.flags &= -9L;
      }
   }

   public void setCode(int var1) {
      if(var1 >= 0) {
         throw new Error("code must be negative");
      } else {
         this.id = var1;
      }
   }

   public final void setFile(String var1) {
      this.filename = var1;
   }

   public final void setFlag(long var1) {
      this.flags |= var1;
   }

   public final void setFlag(boolean var1, long var2) {
      if(var1) {
         this.flags |= var2;
      } else {
         this.flags &= ~var2;
      }
   }

   public final void setFluid(boolean var1) {
      this.setFlag(var1, 16L);
   }

   public final void setIndirectBinding(boolean var1) {
      this.setFlag(var1, 1L);
   }

   public final void setLine(int var1) {
      this.setLine(var1, 0);
   }

   public final void setLine(int var1, int var2) {
      int var3 = var1;
      if(var1 < 0) {
         var3 = 0;
      }

      var1 = var2;
      if(var2 < 0) {
         var1 = 0;
      }

      this.position = (var3 << 12) + var1;
   }

   public final void setLocation(SourceLocator var1) {
      this.filename = var1.getFileName();
      this.setLine(var1.getLineNumber(), var1.getColumnNumber());
   }

   public final void setName(Object var1) {
      this.symbol = var1;
   }

   public final void setNext(Declaration var1) {
      this.next = var1;
   }

   public final void setPrivate(boolean var1) {
      this.setFlag(var1, 32L);
   }

   public final void setProcedureDecl(boolean var1) {
      this.setFlag(var1, 128L);
   }

   public final void setSimple(boolean var1) {
      this.setFlag(var1, 64L);
      if(this.var != null && !this.var.isParameter()) {
         this.var.setSimple(var1);
      }

   }

   public final void setSymbol(Object var1) {
      this.symbol = var1;
   }

   public final void setSyntax() {
      this.setSimple(false);
      this.setFlag(536920064L);
   }

   public final void setType(Type var1) {
      this.type = var1;
      if(this.var != null) {
         this.var.setType(var1);
      }

      this.typeExp = QuoteExp.getInstance(var1);
   }

   public final void setTypeExp(Expression var1) {
      this.typeExp = var1;
      Type var3;
      if(var1 instanceof TypeValue) {
         var3 = ((TypeValue)var1).getImplementationType();
      } else {
         var3 = Language.getDefaultLanguage().getTypeFor((Expression)var1, false);
      }

      Object var2 = var3;
      if(var3 == null) {
         var2 = Type.pointer_type;
      }

      this.type = (Type)var2;
      if(this.var != null) {
         this.var.setType((Type)var2);
      }

   }

   public final void setValue(Expression var1) {
      this.value = var1;
   }

   boolean shouldEarlyInit() {
      return this.getFlag(536870912L) || this.isCompiletimeConstant();
   }

   public String toString() {
      return "Declaration[" + this.symbol + '/' + this.id + ']';
   }
}
