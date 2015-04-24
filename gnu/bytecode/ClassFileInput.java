package gnu.bytecode;

import gnu.bytecode.AttrContainer;
import gnu.bytecode.Attribute;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.ConstantPool;
import gnu.bytecode.ConstantValueAttr;
import gnu.bytecode.CpoolClass;
import gnu.bytecode.CpoolUtf8;
import gnu.bytecode.EnclosingMethodAttr;
import gnu.bytecode.Field;
import gnu.bytecode.InnerClassesAttr;
import gnu.bytecode.Label;
import gnu.bytecode.LineNumbersAttr;
import gnu.bytecode.LocalVarsAttr;
import gnu.bytecode.Member;
import gnu.bytecode.Method;
import gnu.bytecode.MiscAttr;
import gnu.bytecode.RuntimeAnnotationsAttr;
import gnu.bytecode.Scope;
import gnu.bytecode.SignatureAttr;
import gnu.bytecode.SourceDebugExtAttr;
import gnu.bytecode.SourceFileAttr;
import gnu.bytecode.StackMapTableAttr;
import gnu.bytecode.Variable;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class ClassFileInput extends DataInputStream {

   ClassType ctype;
   InputStream str;


   public ClassFileInput(ClassType var1, InputStream var2) throws IOException, ClassFormatError {
      super(var2);
      this.ctype = var1;
      if(!this.readHeader()) {
         throw new ClassFormatError("invalid magic number");
      } else {
         var1.constants = this.readConstants();
         this.readClassInfo();
         this.readFields();
         this.readMethods();
         this.readAttributes(var1);
      }
   }

   public ClassFileInput(InputStream var1) throws IOException {
      super(var1);
   }

   public static ClassType readClassType(InputStream var0) throws IOException, ClassFormatError {
      ClassType var1 = new ClassType();
      new ClassFileInput(var1, var0);
      return var1;
   }

   CpoolClass getClassConstant(int var1) {
      return (CpoolClass)this.ctype.constants.getForced(var1, 7);
   }

   public Attribute readAttribute(String var1, int var2, AttrContainer var3) throws IOException {
      Object var19;
      if(var1 == "SourceFile" && var3 instanceof ClassType) {
         var19 = new SourceFileAttr(this.readUnsignedShort(), (ClassType)var3);
      } else {
         int var7;
         byte[] var24;
         if(var1 == "Code" && var3 instanceof Method) {
            CodeAttr var21 = new CodeAttr((Method)var3);
            var21.fixup_count = -1;
            var21.setMaxStack(this.readUnsignedShort());
            var21.setMaxLocals(this.readUnsignedShort());
            var24 = new byte[this.readInt()];
            this.readFully(var24);
            var21.setCode(var24);
            var7 = this.readUnsignedShort();

            for(var2 = 0; var2 < var7; ++var2) {
               var21.addHandler(this.readUnsignedShort(), this.readUnsignedShort(), this.readUnsignedShort(), this.readUnsignedShort());
            }

            this.readAttributes(var21);
            return var21;
         }

         short[] var16;
         if(var1 == "LineNumberTable" && var3 instanceof CodeAttr) {
            var7 = this.readUnsignedShort() * 2;
            var16 = new short[var7];

            for(var2 = 0; var2 < var7; ++var2) {
               var16[var2] = this.readShort();
            }

            return new LineNumbersAttr(var16, (CodeAttr)var3);
         }

         if(var1 != "LocalVariableTable" || !(var3 instanceof CodeAttr)) {
            if(var1 == "Signature" && var3 instanceof Member) {
               return new SignatureAttr(this.readUnsignedShort(), (Member)var3);
            }

            if(var1 == "StackMapTable" && var3 instanceof CodeAttr) {
               byte[] var17 = new byte[var2];
               this.readFully(var17, 0, var2);
               return new StackMapTableAttr(var17, (CodeAttr)var3);
            }

            if((var1 == "RuntimeVisibleAnnotations" || var1 == "RuntimeInvisibleAnnotations") && (var3 instanceof Field || var3 instanceof Method || var3 instanceof ClassType)) {
               byte[] var22 = new byte[var2];
               this.readFully(var22, 0, var2);
               return new RuntimeAnnotationsAttr(var1, var22, var3);
            }

            if(var1 == "ConstantValue" && var3 instanceof Field) {
               return new ConstantValueAttr(this.readUnsignedShort());
            }

            if(var1 == "InnerClasses" && var3 instanceof ClassType) {
               var7 = this.readUnsignedShort() * 4;
               var16 = new short[var7];

               for(var2 = 0; var2 < var7; ++var2) {
                  var16[var2] = this.readShort();
               }

               return new InnerClassesAttr(var16, (ClassType)var3);
            }

            if(var1 == "EnclosingMethod" && var3 instanceof ClassType) {
               return new EnclosingMethodAttr(this.readUnsignedShort(), this.readUnsignedShort(), (ClassType)var3);
            }

            if(var1 == "Exceptions" && var3 instanceof Method) {
               Method var15 = (Method)var3;
               var7 = this.readUnsignedShort();
               short[] var25 = new short[var7];

               for(var2 = 0; var2 < var7; ++var2) {
                  var25[var2] = this.readShort();
               }

               var15.setExceptions((short[])var25);
               return var15.getExceptionAttr();
            }

            if(var1 == "SourceDebugExtension" && var3 instanceof ClassType) {
               SourceDebugExtAttr var14 = new SourceDebugExtAttr((ClassType)var3);
               var24 = new byte[var2];
               this.readFully(var24, 0, var2);
               var14.data = var24;
               var14.dlength = var2;
               return var14;
            }

            var24 = new byte[var2];
            this.readFully(var24, 0, var2);
            return new MiscAttr(var1, var24);
         }

         CodeAttr var18 = (CodeAttr)var3;
         LocalVarsAttr var4 = new LocalVarsAttr(var18);
         Method var5 = var4.getMethod();
         if(var4.parameter_scope == null) {
            var4.parameter_scope = var5.pushScope();
         }

         Scope var13 = var4.parameter_scope;
         if(var13.end == null) {
            var13.end = new Label(var18.PC);
         }

         ConstantPool var23 = var5.getConstants();
         int var12 = this.readUnsignedShort();
         int var8 = var13.start.position;
         var7 = var13.end.position;
         var2 = 0;

         while(true) {
            var19 = var4;
            if(var2 >= var12) {
               break;
            }

            Variable var6;
            int var9;
            Scope var20;
            label170: {
               var6 = new Variable();
               int var11 = this.readUnsignedShort();
               int var10 = var11 + this.readUnsignedShort();
               var20 = var13;
               if(var11 == var8) {
                  var9 = var7;
                  var20 = var13;
                  if(var10 == var7) {
                     break label170;
                  }

                  var20 = var13;
               }

               while(var20.parent != null && (var11 < var20.start.position || var10 > var20.end.position)) {
                  var20 = var20.parent;
               }

               var13 = new Scope(new Label(var11), new Label(var10));
               var13.linkChild(var20);
               var8 = var11;
               var9 = var10;
               var20 = var13;
            }

            var20.addVariable(var6);
            var6.setName(this.readUnsignedShort(), var23);
            var6.setSignature(this.readUnsignedShort(), var23);
            var6.offset = this.readUnsignedShort();
            ++var2;
            var7 = var9;
            var13 = var20;
         }
      }

      return (Attribute)var19;
   }

   public int readAttributes(AttrContainer var1) throws IOException {
      int var6 = this.readUnsignedShort();
      Attribute var2 = var1.getAttributes();

      for(int var5 = 0; var5 < var6; ++var5) {
         Attribute var3 = var2;
         if(var2 != null) {
            while(true) {
               var3 = var2.getNext();
               if(var3 == null) {
                  var3 = var2;
                  break;
               }

               var2 = var3;
            }
         }

         int var7 = this.readUnsignedShort();
         CpoolUtf8 var9 = (CpoolUtf8)this.ctype.constants.getForced(var7, 1);
         int var8 = this.readInt();
         var9.intern();
         Attribute var4 = this.readAttribute(var9.string, var8, var1);
         var2 = var3;
         if(var4 != null) {
            if(var4.getNameIndex() == 0) {
               var4.setNameIndex(var7);
            }

            if(var3 == null) {
               var1.setAttributes(var4);
            } else {
               if(var1.getAttributes() == var4) {
                  var1.setAttributes(var4.getNext());
                  var4.setNext((Attribute)null);
               }

               var3.setNext(var4);
            }

            var2 = var4;
         }
      }

      return var6;
   }

   public void readClassInfo() throws IOException {
      this.ctype.access_flags = this.readUnsignedShort();
      this.ctype.thisClassIndex = this.readUnsignedShort();
      String var1 = this.getClassConstant(this.ctype.thisClassIndex).name.string;
      this.ctype.this_name = var1.replace('/', '.');
      this.ctype.setSignature("L" + var1 + ";");
      this.ctype.superClassIndex = this.readUnsignedShort();
      if(this.ctype.superClassIndex == 0) {
         this.ctype.setSuper((ClassType)((ClassType)null));
      } else {
         var1 = this.getClassConstant(this.ctype.superClassIndex).name.string;
         this.ctype.setSuper((String)var1.replace('/', '.'));
      }

      int var3 = this.readUnsignedShort();
      if(var3 > 0) {
         this.ctype.interfaces = new ClassType[var3];
         this.ctype.interfaceIndexes = new int[var3];

         for(int var2 = 0; var2 < var3; ++var2) {
            int var4 = this.readUnsignedShort();
            this.ctype.interfaceIndexes[var2] = var4;
            var1 = ((CpoolClass)this.ctype.constants.getForced(var4, 7)).name.string.replace('/', '.');
            this.ctype.interfaces[var2] = ClassType.make(var1);
         }
      }

   }

   public ConstantPool readConstants() throws IOException {
      return new ConstantPool(this);
   }

   public void readFields() throws IOException {
      int var4 = this.readUnsignedShort();
      ConstantPool var1 = this.ctype.constants;

      for(int var3 = 0; var3 < var4; ++var3) {
         int var5 = this.readUnsignedShort();
         int var6 = this.readUnsignedShort();
         int var7 = this.readUnsignedShort();
         Field var2 = this.ctype.addField();
         var2.setName(var6, var1);
         var2.setSignature(var7, var1);
         var2.flags = var5;
         this.readAttributes(var2);
      }

   }

   public void readFormatVersion() throws IOException {
      int var1 = this.readUnsignedShort();
      int var2 = this.readUnsignedShort();
      this.ctype.classfileFormatVersion = 65536 * var2 + var1;
   }

   public boolean readHeader() throws IOException {
      if(this.readInt() != -889275714) {
         return false;
      } else {
         this.readFormatVersion();
         return true;
      }
   }

   public void readMethods() throws IOException {
      int var3 = this.readUnsignedShort();

      for(int var2 = 0; var2 < var3; ++var2) {
         int var4 = this.readUnsignedShort();
         int var5 = this.readUnsignedShort();
         int var6 = this.readUnsignedShort();
         Method var1 = this.ctype.addMethod((String)null, var4);
         var1.setName(var5);
         var1.setSignature(var6);
         this.readAttributes(var1);
      }

   }

   public final void skipAttribute(int var1) throws IOException {
      int var3;
      for(int var2 = 0; var2 < var1; var2 += var3) {
         int var4 = (int)this.skip((long)(var1 - var2));
         var3 = var4;
         if(var4 == 0) {
            if(this.read() < 0) {
               throw new EOFException("EOF while reading class files attributes");
            }

            var3 = 1;
         }
      }

   }
}
