package gnu.bytecode;

import gnu.bytecode.AttrContainer;
import gnu.bytecode.Attribute;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.ConstantPool;
import gnu.bytecode.CpoolUtf8;
import gnu.bytecode.ExceptionsAttr;
import gnu.bytecode.Member;
import gnu.bytecode.Scope;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Stack;

public class Method implements AttrContainer, Member {

   int access_flags;
   Type[] arg_types;
   Attribute attributes;
   ClassType classfile;
   CodeAttr code;
   ExceptionsAttr exceptions;
   private String name;
   int name_index;
   Method next;
   Type return_type;
   String signature;
   int signature_index;


   private Method() {
   }

   Method(ClassType var1, int var2) {
      if(var1.last_method == null) {
         var1.methods = this;
      } else {
         var1.last_method.next = this;
      }

      var1.last_method = this;
      ++var1.methods_count;
      this.access_flags = var2;
      this.classfile = var1;
   }

   public Method(Method var1, ClassType var2) {
      this.arg_types = var1.arg_types;
      this.return_type = var1.return_type;
      this.name = var1.name;
      this.access_flags = var1.access_flags;
      this.classfile = var2;
   }

   public static Method makeCloneMethod(Type var0) {
      Method var1 = new Method();
      var1.name = "clone";
      var1.access_flags = 1;
      var1.arg_types = Type.typeArray0;
      var1.return_type = var0;
      var1.classfile = Type.pointer_type;
      return var1;
   }

   public static String makeSignature(Type[] var0, Type var1) {
      StringBuilder var2 = new StringBuilder(100);
      int var4 = var0.length;
      var2.append('(');

      for(int var3 = 0; var3 < var4; ++var3) {
         var2.append(var0[var3].getSignature());
      }

      var2.append(')');
      var2.append(var1.getSignature());
      return var2.toString();
   }

   public void allocate_local(Variable var1) {
      var1.allocateLocal(this.code);
   }

   void assignConstants() {
      ConstantPool var1 = this.getConstants();
      if(this.name_index == 0 && this.name != null) {
         this.name_index = var1.addUtf8(this.name).index;
      }

      if(this.signature_index == 0) {
         this.signature_index = var1.addUtf8(this.getSignature()).index;
      }

      Attribute.assignConstants(this, this.classfile);
   }

   public void cleanupAfterCompilation() {
      this.attributes = null;
      this.exceptions = null;
      this.code = null;
   }

   public void compile_checkcast(Type var1) {
      this.code.emitCheckcast(var1);
   }

   public void compile_push_this() {
      this.code.emitPushThis();
   }

   public void compile_push_value(Variable var1) {
      this.code.emitLoad(var1);
   }

   public void compile_store_value(Variable var1) {
      this.code.emitStore(var1);
   }

   public final Attribute getAttributes() {
      return this.attributes;
   }

   public final CodeAttr getCode() {
      return this.code;
   }

   public final ConstantPool getConstants() {
      return this.classfile.constants;
   }

   public ClassType getDeclaringClass() {
      return this.classfile;
   }

   public final ExceptionsAttr getExceptionAttr() {
      return this.exceptions;
   }

   public final ClassType[] getExceptions() {
      return this.exceptions == null?null:this.exceptions.getExceptions();
   }

   public int getModifiers() {
      return this.access_flags;
   }

   public final String getName() {
      return this.name;
   }

   public final Method getNext() {
      return this.next;
   }

   public final Type[] getParameterTypes() {
      return this.arg_types;
   }

   public final Type getReturnType() {
      return this.return_type;
   }

   public String getSignature() {
      if(this.signature == null) {
         this.signature = makeSignature(this.arg_types, this.return_type);
      }

      return this.signature;
   }

   public final boolean getStaticFlag() {
      return (this.access_flags & 8) != 0;
   }

   public void initCode() {
      if(this.classfile.constants == null) {
         this.classfile.constants = new ConstantPool();
      }

      this.prepareCode(0);
      this.code.sourceDbgExt = this.classfile.sourceDbgExt;
      this.code.noteParamTypes();
      this.code.pushScope();
   }

   public void init_param_slots() {
      this.startCode();
   }

   void instruction_start_hook(int var1) {
      this.prepareCode(var1);
   }

   public final boolean isAbstract() {
      return (this.access_flags & 1024) != 0;
   }

   void kill_local(Variable var1) {
      var1.freeLocal(this.code);
   }

   public void listParameters(StringBuffer var1) {
      int var3 = this.arg_types.length;
      var1.append('(');

      for(int var2 = 0; var2 < var3; ++var2) {
         if(var2 > 0) {
            var1.append(',');
         }

         var1.append(this.arg_types[var2].getName());
      }

      var1.append(')');
   }

   public void maybe_compile_checkcast(Type var1) {
      if(var1 != this.code.topType()) {
         this.code.emitCheckcast(var1);
      }

   }

   public Scope popScope() {
      return this.code.popScope();
   }

   final Type pop_stack_type() {
      return this.code.popType();
   }

   void prepareCode(int var1) {
      if(this.code == null) {
         this.code = new CodeAttr(this);
      }

      this.code.reserve(var1);
   }

   public Scope pushScope() {
      this.prepareCode(0);
      return this.code.pushScope();
   }

   final void push_stack_type(Type var1) {
      this.code.pushType(var1);
   }

   public void push_var(Variable var1) {
      this.code.emitLoad(var1);
   }

   public final boolean reachableHere() {
      return this.code.reachableHere();
   }

   public final void setAttributes(Attribute var1) {
      this.attributes = var1;
   }

   public void setExceptions(ClassType[] var1) {
      if(this.exceptions == null) {
         this.exceptions = new ExceptionsAttr(this);
      }

      this.exceptions.setExceptions(var1);
   }

   public void setExceptions(short[] var1) {
      if(this.exceptions == null) {
         this.exceptions = new ExceptionsAttr(this);
      }

      this.exceptions.setExceptions(var1, this.classfile);
   }

   public void setModifiers(int var1) {
      this.access_flags = var1;
   }

   public final void setName(int var1) {
      if(var1 <= 0) {
         this.name = null;
      } else {
         this.name = ((CpoolUtf8)this.getConstants().getForced(var1, 1)).string;
      }

      this.name_index = var1;
   }

   public final void setName(String var1) {
      this.name = var1;
   }

   public void setSignature(int var1) {
      CpoolUtf8 var2 = (CpoolUtf8)this.getConstants().getForced(var1, 1);
      this.signature_index = var1;
      this.setSignature(var2.string);
   }

   public void setSignature(String var1) {
      int var5 = var1.length();
      if(var5 >= 3 && var1.charAt(0) == 40) {
         int var3 = 1;
         Stack var2 = new Stack();

         while(true) {
            int var4 = Type.signatureLength(var1, var3);
            if(var4 < 0) {
               if(var3 < var5 && var1.charAt(var3) == 41) {
                  this.arg_types = new Type[var2.size()];
                  var4 = var2.size();

                  while(true) {
                     --var4;
                     if(var4 < 0) {
                        this.return_type = Type.signatureToType(var1, var3 + 1, var5 - var3 - 1);
                        return;
                     }

                     this.arg_types[var4] = (Type)var2.pop();
                  }
               } else {
                  throw new ClassFormatError("bad method signature");
               }
            }

            var2.push(Type.signatureToType(var1, var3, var4));
            var3 += var4;
         }
      } else {
         throw new ClassFormatError("bad method signature");
      }
   }

   public final void setStaticFlag(boolean var1) {
      if(var1) {
         this.access_flags |= 8;
      } else {
         this.access_flags ^= -9;
      }
   }

   public CodeAttr startCode() {
      this.initCode();
      this.code.addParamLocals();
      return this.code;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer(100);
      var1.append(this.getDeclaringClass().getName());
      var1.append('.');
      var1.append(this.name);
      if(this.arg_types != null) {
         this.listParameters(var1);
         var1.append(this.return_type.getName());
      }

      return var1.toString();
   }

   void write(DataOutputStream var1, ClassType var2) throws IOException {
      var1.writeShort(this.access_flags);
      var1.writeShort(this.name_index);
      var1.writeShort(this.signature_index);
      Attribute.writeAll(this, var1);
   }
}
