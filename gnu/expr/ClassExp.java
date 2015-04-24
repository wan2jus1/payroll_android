package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.expr.ClassInitializer;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ExpVisitor;
import gnu.expr.Expression;
import gnu.expr.IgnoreTarget;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.ObjectExp;
import gnu.expr.PairClassType;
import gnu.expr.Target;
import gnu.mapping.OutPort;
import java.util.Hashtable;
import java.util.Vector;

public class ClassExp extends LambdaExp {

   public static final int CLASS_SPECIFIED = 65536;
   public static final int HAS_SUBCLASS = 131072;
   public static final int INTERFACE_SPECIFIED = 32768;
   public static final int IS_ABSTRACT = 16384;
   public String classNameSpecifier;
   public LambdaExp clinitMethod;
   boolean explicitInit;
   public LambdaExp initMethod;
   ClassType instanceType;
   boolean partsDeclared;
   boolean simple;
   public int superClassIndex = -1;
   public Expression[] supers;


   public ClassExp() {
   }

   public ClassExp(boolean var1) {
      this.simple = var1;
      ClassType var2 = new ClassType();
      this.type = var2;
      this.instanceType = var2;
   }

   static void getImplMethods(ClassType param0, String param1, Type[] param2, Vector param3) {
      // $FF: Couldn't be decompiled
   }

   static void invokeDefaultSuperConstructor(ClassType var0, Compilation var1, LambdaExp var2) {
      CodeAttr var3 = var1.getCode();
      Method var4 = var0.getDeclaredMethod("<init>", 0);
      if(var4 == null) {
         var1.error('e', "super class does not have a default constructor");
      } else {
         var3.emitPushThis();
         if(var0.hasOuterLink() && var2 instanceof ClassExp) {
            ClassExp var5 = (ClassExp)var2;
            loadSuperStaticLink(var5.supers[var5.superClassIndex], var0, var1);
         }

         var3.emitInvokeSpecial(var4);
      }
   }

   static void loadSuperStaticLink(Expression var0, ClassType var1, Compilation var2) {
      CodeAttr var3 = var2.getCode();
      var0.compile(var2, (Target)Target.pushValue(Compilation.typeClassType));
      var3.emitInvokeStatic(ClassType.make("gnu.expr.PairClassType").getDeclaredMethod("extractStaticLink", 1));
      var3.emitCheckcast(var1.getOuterLinkType());
   }

   public static String slotToMethodName(String var0, String var1) {
      String var2 = var1;
      if(!Compilation.isValidJavaName(var1)) {
         var2 = Compilation.mangleName(var1, false);
      }

      int var3 = var2.length();
      StringBuffer var4 = new StringBuffer(var3 + 3);
      var4.append(var0);
      if(var3 > 0) {
         var4.append(Character.toTitleCase(var2.charAt(0)));
         var4.append(var2.substring(1));
      }

      return var4.toString();
   }

   private static void usedSuperClasses(ClassType var0, Compilation var1) {
      var1.usedClass(var0.getSuperclass());
      ClassType[] var3 = var0.getInterfaces();
      if(var3 != null) {
         int var2 = var3.length;

         while(true) {
            --var2;
            if(var2 < 0) {
               break;
            }

            var1.usedClass(var3[var2]);
         }
      }

   }

   public Declaration addMethod(LambdaExp var1, Object var2) {
      Declaration var3 = this.addDeclaration(var2, Compilation.typeProcedure);
      var1.outer = this;
      var1.setClassMethod(true);
      var3.noteValue(var1);
      var3.setFlag(1048576L);
      var3.setProcedureDecl(true);
      var1.setSymbol(var2);
      return var3;
   }

   public void compile(Compilation var1, Target var2) {
      if(!(var2 instanceof IgnoreTarget)) {
         this.compileMembers(var1);
         this.compilePushClass(var1, var2);
      }
   }

   public ClassType compileMembers(Compilation param1) {
      // $FF: Couldn't be decompiled
   }

   public void compilePushClass(Compilation var1, Target var2) {
      ClassType var3 = this.type;
      CodeAttr var4 = var1.getCode();
      var1.loadClassRef(var3);
      boolean var9 = this.getNeedsClosureEnv();
      if(!this.isSimple() || var9) {
         byte var7;
         if(!this.isMakingClassPair() && !var9) {
            var3 = ClassType.make("gnu.bytecode.Type");
            var7 = 1;
         } else {
            if(var3 == this.instanceType) {
               var4.emitDup(this.instanceType);
            } else {
               var1.loadClassRef(this.instanceType);
            }

            var3 = ClassType.make("gnu.expr.PairClassType");
            if(var9) {
               var7 = 3;
            } else {
               var7 = 2;
            }
         }

         Type[] var5 = new Type[var7];
         int var8 = var7;
         if(var9) {
            this.getOwningLambda().loadHeapFrame(var1);
            var8 = var7 - 1;
            var5[var8] = Type.pointer_type;
         }

         ClassType var6 = ClassType.make("java.lang.Class");

         while(true) {
            --var8;
            if(var8 < 0) {
               var4.emitInvokeStatic(var3.addMethod("make", var5, var3, 9));
               var2.compileFromStack(var1, var3);
               return;
            }

            var5[var8] = var6;
         }
      }
   }

   public Field compileSetField(Compilation var1) {
      return (new ClassInitializer(this, var1)).field;
   }

   public void declareParts(Compilation var1) {
      if(!this.partsDeclared) {
         this.partsDeclared = true;
         Hashtable var3 = new Hashtable();

         for(Declaration var2 = this.firstDecl(); var2 != null; var2 = var2.nextDecl()) {
            if(var2.getCanRead()) {
               short var9 = var2.getAccessFlags((short)1);
               int var8 = var9;
               if(var2.getFlag(2048L)) {
                  var8 = var9 | 8;
               }

               if(this.isMakingClassPair()) {
                  var8 |= 1024;
                  Type var4 = var2.getType().getImplementationType();
                  this.type.addMethod(slotToMethodName("get", var2.getName()), var8, Type.typeArray0, var4);
                  ClassType var5 = this.type;
                  String var6 = slotToMethodName("set", var2.getName());
                  PrimType var7 = Type.voidType;
                  var5.addMethod(var6, var8, new Type[]{var4}, var7);
               } else {
                  String var11 = Compilation.mangleNameIfNeeded(var2.getName());
                  var2.field = this.instanceType.addField(var11, var2.getType(), var8);
                  var2.setSimple(false);
                  Declaration var12 = (Declaration)var3.get(var11);
                  if(var12 != null) {
                     duplicateDeclarationError(var12, var2, var1);
                  }

                  var3.put(var11, var2);
               }
            }
         }

         for(LambdaExp var10 = this.firstChild; var10 != null; var10 = var10.nextSibling) {
            if(var10.isAbstract()) {
               this.setFlag(16384);
            }

            if("*init*".equals(var10.getName())) {
               this.explicitInit = true;
               if(var10.isAbstract()) {
                  var1.error('e', "*init* method cannot be abstract", var10);
               }

               if(this.type instanceof PairClassType) {
                  var1.error('e', "\'*init*\' methods only supported for simple classes");
               }
            }

            var10.outer = this;
            if(var10 != this.initMethod && var10 != this.clinitMethod && var10.nameDecl != null && !var10.nameDecl.getFlag(2048L) || !this.isMakingClassPair()) {
               var10.addMethodFor(this.type, var1, (ObjectType)null);
            }

            if(this.isMakingClassPair()) {
               var10.addMethodFor(this.instanceType, var1, this.type);
            }
         }

         if(!this.explicitInit && !this.instanceType.isInterface()) {
            Compilation.getConstructor(this.instanceType, this);
         }

         if(this.isAbstract()) {
            this.instanceType.setModifiers(this.instanceType.getModifiers() | 1024);
         }

         if(this.nameDecl != null) {
            this.instanceType.setModifiers(this.instanceType.getModifiers() & -2 | this.nameDecl.getAccessFlags((short)1));
            return;
         }
      }

   }

   public ClassType getClassType() {
      return this.type;
   }

   protected ClassType getCompiledClassType(Compilation var1) {
      return this.type;
   }

   public Type getType() {
      return this.simple?Compilation.typeClass:Compilation.typeClassType;
   }

   public final boolean isAbstract() {
      return this.getFlag(16384);
   }

   public boolean isMakingClassPair() {
      return this.type != this.instanceType;
   }

   public boolean isSimple() {
      return this.simple;
   }

   protected boolean mustCompile() {
      return true;
   }

   public void print(OutPort var1) {
      var1.startLogicalBlock("(" + this.getExpClassName() + "/", ")", 2);
      Object var2 = this.getSymbol();
      if(var2 != null) {
         var1.print((Object)var2);
         var1.print('/');
      }

      var1.print(this.id);
      var1.print((String)"/fl:");
      var1.print((String)Integer.toHexString(this.flags));
      int var3;
      if(this.supers.length > 0) {
         var1.writeSpaceFill();
         var1.startLogicalBlock("supers:", "", 2);

         for(var3 = 0; var3 < this.supers.length; ++var3) {
            this.supers[var3].print((OutPort)var1);
            var1.writeSpaceFill();
         }

         var1.endLogicalBlock("");
      }

      var1.print('(');
      var3 = 0;
      if(this.keywords != null) {
         int var4 = this.keywords.length;
      }

      for(Declaration var5 = this.firstDecl(); var5 != null; var5 = var5.nextDecl()) {
         if(var3 > 0) {
            var1.print(' ');
         }

         var5.printInfo((OutPort)var1);
         ++var3;
      }

      var1.print((String)") ");

      for(LambdaExp var6 = this.firstChild; var6 != null; var6 = var6.nextSibling) {
         var1.writeBreakLinear();
         var6.print(var1);
      }

      if(this.body != null) {
         var1.writeBreakLinear();
         this.body.print((OutPort)var1);
      }

      var1.endLogicalBlock(")");
   }

   public void setSimple(boolean var1) {
      this.simple = var1;
   }

   public void setTypes(Compilation var1) {
      int var7;
      if(this.supers == null) {
         var7 = 0;
      } else {
         var7 = this.supers.length;
      }

      ClassType[] var4 = new ClassType[var7];
      ClassType var2 = null;
      int var8 = 0;

      int var6;
      ClassType var13;
      for(var6 = 0; var8 < var7; ++var8) {
         Type var3 = Language.getDefaultLanguage().getTypeFor((Expression)this.supers[var8]);
         if(!(var3 instanceof ClassType)) {
            var1.setLine((Expression)this.supers[var8]);
            var1.error('e', "invalid super type");
         } else {
            var13 = (ClassType)var3;

            int var9;
            try {
               var9 = var13.getModifiers();
            } catch (RuntimeException var11) {
               byte var10 = 0;
               var9 = var10;
               if(var1 != null) {
                  var1.error('e', "unknown super-type " + var13.getName());
                  var9 = var10;
               }
            }

            if((var9 & 512) == 0) {
               if(var6 < var8) {
                  var1.error('e', "duplicate superclass for " + this);
               }

               var2 = var13;
               this.superClassIndex = var8;
            } else {
               var9 = var6 + 1;
               var4[var6] = var13;
               var6 = var9;
            }
         }
      }

      if(var2 != null && (this.flags & '耀') != 0) {
         var1.error('e', "cannot be interface since has superclass");
      }

      if(!this.simple && var2 == null && (this.flags & 65536) == 0 && (this.getFlag(131072) || this.nameDecl != null && this.nameDecl.isPublic())) {
         PairClassType var15 = new PairClassType();
         this.type = var15;
         var15.setInterface(true);
         var15.instanceType = this.instanceType;
         var13 = this.type;
         this.instanceType.setSuper((ClassType)Type.pointer_type);
         this.instanceType.setInterfaces(new ClassType[]{var13});
      } else if(this.getFlag('耀')) {
         this.instanceType.setInterface(true);
      }

      ClassType var5 = this.type;
      var13 = var2;
      if(var2 == null) {
         var13 = Type.pointer_type;
      }

      var5.setSuper((ClassType)var13);
      ClassType[] var12;
      if(var6 == var7) {
         var12 = var4;
      } else {
         var12 = new ClassType[var6];
         System.arraycopy(var4, 0, var12, 0, var6);
      }

      this.type.setInterfaces(var12);
      if(this.type.getName() == null) {
         String var14;
         String var17;
         if(this.classNameSpecifier != null) {
            var14 = this.classNameSpecifier;
         } else {
            var17 = this.getName();
            var14 = var17;
            if(var17 != null) {
               var6 = var17.length();
               var14 = var17;
               if(var6 > 2) {
                  var14 = var17;
                  if(var17.charAt(0) == 60) {
                     var14 = var17;
                     if(var17.charAt(var6 - 1) == 62) {
                        var14 = var17.substring(1, var6 - 1);
                     }
                  }
               }
            }
         }

         if(var14 == null) {
            StringBuffer var18 = new StringBuffer(100);
            var1.getModule().classFor(var1);
            var18.append(var1.mainClass.getName());
            var18.append('$');
            var7 = var18.length();
            var6 = 0;

            while(true) {
               var18.append(var6);
               var14 = var18.toString();
               if(var1.findNamedClass(var14) == null) {
                  break;
               }

               var18.setLength(var7);
               ++var6;
            }
         } else if(this.isSimple() && !(this instanceof ObjectExp)) {
            var6 = 0;
            StringBuffer var16 = new StringBuffer(100);

            while(true) {
               var7 = var14.indexOf(46, var6);
               if(var7 < 0) {
                  if(var6 == 0) {
                     if(var1.mainClass == null) {
                        var17 = null;
                     } else {
                        var17 = var1.mainClass.getName();
                     }

                     if(var17 == null) {
                        var7 = -1;
                     } else {
                        var7 = var17.lastIndexOf(46);
                     }

                     if(var7 > 0) {
                        var16.append(var17.substring(0, var7 + 1));
                     } else if(var1.classPrefix != null) {
                        var16.append(var1.classPrefix);
                     }
                  } else if(var6 == 1 && var6 < var14.length()) {
                     var16.setLength(0);
                     var16.append(var1.mainClass.getName());
                     var16.append('$');
                  }

                  if(var6 < var14.length()) {
                     var16.append(Compilation.mangleNameIfNeeded(var14.substring(var6)));
                  }

                  var14 = var16.toString();
                  break;
               }

               var16.append(Compilation.mangleNameIfNeeded(var14.substring(var6, var7)));
               ++var7;
               var6 = var7;
               if(var7 < var14.length()) {
                  var16.append('.');
                  var6 = var7;
               }
            }
         } else {
            var14 = var1.generateClassName(var14);
         }

         this.type.setName(var14);
         var1.addClass(this.type);
         if(this.isMakingClassPair()) {
            this.instanceType.setName(this.type.getName() + "$class");
            var1.addClass(this.instanceType);
         }
      }

   }

   protected Object visit(ExpVisitor var1, Object var2) {
      Compilation var3 = var1.getCompilation();
      if(var3 == null) {
         return var1.visitClassExp(this, var2);
      } else {
         ClassType var4 = var3.curClass;

         Object var7;
         try {
            var3.curClass = this.type;
            var7 = var1.visitClassExp(this, var2);
         } finally {
            var3.curClass = var4;
         }

         return var7;
      }
   }

   protected void visitChildren(ExpVisitor param1, Object param2) {
      // $FF: Couldn't be decompiled
   }
}
