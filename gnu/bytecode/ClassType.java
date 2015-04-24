package gnu.bytecode;

import gnu.bytecode.AttrContainer;
import gnu.bytecode.Attribute;
import gnu.bytecode.ConstantPool;
import gnu.bytecode.CpoolClass;
import gnu.bytecode.CpoolEntry;
import gnu.bytecode.EnclosingMethodAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Filter;
import gnu.bytecode.InnerClassesAttr;
import gnu.bytecode.Member;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.SourceDebugExtAttr;
import gnu.bytecode.SourceFileAttr;
import gnu.bytecode.Type;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.Externalizable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Vector;

public class ClassType extends ObjectType implements AttrContainer, Externalizable, Member {

   public static final int JDK_1_1_VERSION = 2949123;
   public static final int JDK_1_2_VERSION = 3014656;
   public static final int JDK_1_3_VERSION = 3080192;
   public static final int JDK_1_4_VERSION = 3145728;
   public static final int JDK_1_5_VERSION = 3211264;
   public static final int JDK_1_6_VERSION = 3276800;
   public static final int JDK_1_7_VERSION = 3342336;
   public static final ClassType[] noClasses = new ClassType[0];
   int Code_name_index;
   int ConstantValue_name_index;
   int LineNumberTable_name_index;
   int LocalVariableTable_name_index;
   int access_flags;
   Attribute attributes;
   int classfileFormatVersion = 2949123;
   ConstantPool constants;
   public Method constructor;
   boolean emitDebugInfo = true;
   Member enclosingMember;
   Field fields;
   int fields_count;
   ClassType firstInnerClass;
   int[] interfaceIndexes;
   ClassType[] interfaces;
   Field last_field;
   Method last_method;
   Method methods;
   int methods_count;
   ClassType nextInnerClass;
   SourceDebugExtAttr sourceDbgExt;
   ClassType superClass;
   int superClassIndex = -1;
   int thisClassIndex;


   public ClassType() {
   }

   public ClassType(String var1) {
      this.setName(var1);
   }

   public static ClassType make(String var0) {
      return (ClassType)Type.getType(var0);
   }

   public static ClassType make(String var0, ClassType var1) {
      ClassType var2 = make(var0);
      if(var2.superClass == null) {
         var2.setSuper((ClassType)var1);
      }

      return var2;
   }

   public static byte[] to_utf8(String var0) {
      byte[] var1;
      if(var0 == null) {
         var1 = null;
      } else {
         int var6 = var0.length();
         int var3 = 0;

         int var4;
         for(var4 = 0; var4 < var6; ++var4) {
            char var5 = var0.charAt(var4);
            if(var5 > 0 && var5 <= 127) {
               ++var3;
            } else if(var5 <= 2047) {
               var3 += 2;
            } else {
               var3 += 3;
            }
         }

         byte[] var2 = new byte[var3];
         var4 = 0;
         var3 = 0;

         while(true) {
            var1 = var2;
            if(var4 >= var6) {
               break;
            }

            char var7 = var0.charAt(var4);
            int var9;
            if(var7 > 0 && var7 <= 127) {
               var9 = var3 + 1;
               var2[var3] = (byte)var7;
               var3 = var9;
            } else if(var7 <= 2047) {
               var9 = var3 + 1;
               var2[var3] = (byte)(var7 >> 6 & 31 | 192);
               var2[var9] = (byte)(var7 >> 0 & 63 | 128);
               var3 = var9 + 1;
            } else {
               var9 = var3 + 1;
               var2[var3] = (byte)(var7 >> 12 & 15 | 224);
               int var8 = var9 + 1;
               var2[var9] = (byte)(var7 >> 6 & 63 | 128);
               var3 = var8 + 1;
               var2[var8] = (byte)(var7 >> 0 & 63 | 128);
            }

            ++var4;
         }
      }

      return var1;
   }

   void addEnclosingMember() {
      // $FF: Couldn't be decompiled
   }

   public Field addField() {
      return new Field(this);
   }

   public Field addField(String var1) {
      Field var2 = new Field(this);
      var2.setName(var1);
      return var2;
   }

   public final Field addField(String var1, Type var2) {
      Field var3 = new Field(this);
      var3.setName(var1);
      var3.setType(var2);
      return var3;
   }

   public final Field addField(String var1, Type var2, int var3) {
      Field var4 = this.addField(var1, var2);
      var4.flags = var3;
      return var4;
   }

   public void addFields() {
      // $FF: Couldn't be decompiled
   }

   public void addInterface(ClassType var1) {
      int var3;
      if(this.interfaces != null && this.interfaces.length != 0) {
         var3 = this.interfaces.length;
         int var4 = var3;

         while(true) {
            int var5 = var4 - 1;
            if(var5 < 0) {
               ClassType[] var2 = new ClassType[var3 + 1];
               System.arraycopy(this.interfaces, 0, var2, 0, var3);
               this.interfaces = var2;
               break;
            }

            var4 = var5;
            if(this.interfaces[var5] == var1) {
               return;
            }
         }
      } else {
         var3 = 0;
         this.interfaces = new ClassType[1];
      }

      this.interfaces[var3] = var1;
   }

   public void addMemberClass(ClassType var1) {
      ClassType var3 = null;

      for(ClassType var2 = this.firstInnerClass; var2 != null; var2 = var2.nextInnerClass) {
         if(var2 == var1) {
            return;
         }

         var3 = var2;
      }

      if(var3 == null) {
         this.firstInnerClass = var1;
      } else {
         var3.nextInnerClass = var1;
      }
   }

   public void addMemberClasses() {
      // $FF: Couldn't be decompiled
   }

   Method addMethod() {
      return new Method(this, 0);
   }

   public Method addMethod(String var1) {
      return this.addMethod(var1, 0);
   }

   public Method addMethod(String var1, int var2) {
      Method var3 = new Method(this, var2);
      var3.setName(var1);
      return var3;
   }

   public Method addMethod(String param1, int param2, Type[] param3, Type param4) {
      // $FF: Couldn't be decompiled
   }

   public Method addMethod(String var1, String var2, int var3) {
      Method var4 = this.addMethod(var1, var3);
      var4.setSignature(var2);
      return var4;
   }

   public Method addMethod(String var1, Type[] var2, Type var3, int var4) {
      return this.addMethod(var1, var4, var2, var3);
   }

   public Method addMethod(Constructor var1) {
      Class[] var2 = var1.getParameterTypes();
      int var4 = var1.getModifiers();
      int var3 = var2.length;
      Type[] var5 = new Type[var3];

      while(true) {
         --var3;
         if(var3 < 0) {
            return this.addMethod("<init>", var4, var5, Type.voidType);
         }

         var5[var3] = Type.make(var2[var3]);
      }
   }

   public Method addMethod(java.lang.reflect.Method var1) {
      int var5 = var1.getModifiers();
      Class[] var3 = var1.getParameterTypes();
      int var4 = var3.length;
      Type[] var2 = new Type[var4];

      while(true) {
         --var4;
         if(var4 < 0) {
            Type var6 = Type.make(var1.getReturnType());
            return this.addMethod(var1.getName(), var5, var2, var6);
         }

         var2[var4] = Type.make(var3[var4]);
      }
   }

   public void addMethods(Class param1) {
      // $FF: Couldn't be decompiled
   }

   public final void addModifiers(int var1) {
      this.access_flags |= var1;
   }

   public Method checkSingleAbstractMethod() {
      Method[] var3 = this.getAbstractMethods();
      int var6 = var3.length;
      Method var1 = null;
      int var5 = 0;

      Method var2;
      while(true) {
         var2 = var1;
         if(var5 >= var6) {
            break;
         }

         var2 = var3[var5];
         Method var4 = this.getMethod(var2.getName(), var2.getParameterTypes());
         if(var4 == null || var4.isAbstract()) {
            if(var1 != null) {
               var2 = null;
               break;
            }

            var1 = var2;
         }

         ++var5;
      }

      return var2;
   }

   public void cleanupAfterCompilation() {
      for(Method var1 = this.methods; var1 != null; var1 = var1.getNext()) {
         var1.cleanupAfterCompilation();
      }

      this.constants = null;
      this.attributes = null;
      this.sourceDbgExt = null;
   }

   public int compare(Type var1) {
      byte var3 = -1;
      if(var1 != nullType) {
         if(!(var1 instanceof ClassType)) {
            return swappedCompareResult(var1.compare(this));
         }

         String var2 = this.getName();
         if(var2 != null && var2.equals(var1.getName())) {
            return 0;
         }

         ClassType var4 = (ClassType)var1;
         if(this.isSubclass((ClassType)var4)) {
            return -1;
         }

         if(!var4.isSubclass((ClassType)this)) {
            if(this == toStringType) {
               if(var4 != Type.javalangObjectType) {
                  var3 = 1;
               }

               return var3;
            }

            if(var4 == toStringType) {
               if(this != Type.javalangObjectType) {
                  return -1;
               }
            } else {
               if(this.isInterface()) {
                  if(var4 != Type.javalangObjectType) {
                     var3 = -2;
                  }

                  return var3;
               }

               if(!var4.isInterface()) {
                  return -3;
               }

               if(this != Type.javalangObjectType) {
                  return -2;
               }
            }
         }
      }

      return 1;
   }

   public final int countMethods(Filter var1, int var2) {
      Vector var3 = new Vector();
      this.getMethods(var1, var2, var3);
      return var3.size();
   }

   public void doFixups() {
      if(this.constants == null) {
         this.constants = new ConstantPool();
      }

      if(this.thisClassIndex == 0) {
         this.thisClassIndex = this.constants.addClass((ObjectType)this).index;
      }

      if(this.superClass == this) {
         this.setSuper((ClassType)((ClassType)null));
      }

      int var4;
      if(this.superClassIndex < 0) {
         if(this.superClass == null) {
            var4 = 0;
         } else {
            var4 = this.constants.addClass((ObjectType)this.superClass).index;
         }

         this.superClassIndex = var4;
      }

      if(this.interfaces != null && this.interfaceIndexes == null) {
         int var5 = this.interfaces.length;
         this.interfaceIndexes = new int[var5];

         for(var4 = 0; var4 < var5; ++var4) {
            this.interfaceIndexes[var4] = this.constants.addClass((ObjectType)this.interfaces[var4]).index;
         }
      }

      for(Field var1 = this.fields; var1 != null; var1 = var1.next) {
         var1.assign_constants(this);
      }

      for(Method var6 = this.methods; var6 != null; var6 = var6.next) {
         var6.assignConstants();
      }

      if(this.enclosingMember instanceof Method) {
         EnclosingMethodAttr var2 = EnclosingMethodAttr.getFirstEnclosingMethod(this.getAttributes());
         EnclosingMethodAttr var7 = var2;
         if(var2 == null) {
            var7 = new EnclosingMethodAttr(this);
         }

         var7.method = (Method)this.enclosingMember;
      } else if(this.enclosingMember instanceof ClassType) {
         this.constants.addClass((ObjectType)((ClassType)this.enclosingMember));
      }

      for(ClassType var10 = this.firstInnerClass; var10 != null; var10 = var10.nextInnerClass) {
         this.constants.addClass((ObjectType)var10);
      }

      InnerClassesAttr var11 = InnerClassesAttr.getFirstInnerClasses(this.getAttributes());
      if(var11 != null) {
         var11.setSkipped(true);
      }

      Attribute.assignConstants(this, this);

      InnerClassesAttr var9;
      for(var4 = 1; var4 <= this.constants.count; var11 = var9) {
         CpoolEntry var8 = this.constants.pool[var4];
         if(!(var8 instanceof CpoolClass)) {
            var9 = var11;
         } else {
            CpoolClass var3 = (CpoolClass)var8;
            var9 = var11;
            if(var3.clas instanceof ClassType) {
               var9 = var11;
               if(((ClassType)var3.clas).getEnclosingMember() != null) {
                  var9 = var11;
                  if(var11 == null) {
                     var9 = new InnerClassesAttr(this);
                  }

                  var9.addClass(var3, this);
               }
            }
         }

         ++var4;
      }

      if(var11 != null) {
         var11.setSkipped(false);
         var11.assignConstants(this);
      }

   }

   public Method[] getAbstractMethods() {
      return this.getMethods(ClassType.AbstractMethodFilter.instance, 2);
   }

   public final Attribute getAttributes() {
      return this.attributes;
   }

   public short getClassfileMajorVersion() {
      return (short)(this.classfileFormatVersion >> 16);
   }

   public short getClassfileMinorVersion() {
      return (short)(this.classfileFormatVersion & '\uffff');
   }

   public int getClassfileVersion() {
      return this.classfileFormatVersion;
   }

   public final CpoolEntry getConstant(int var1) {
      return this.constants != null && this.constants.pool != null && var1 <= this.constants.count?this.constants.pool[var1]:null;
   }

   public final ConstantPool getConstants() {
      return this.constants;
   }

   public ClassType getDeclaredClass(String var1) {
      this.addMemberClasses();

      for(ClassType var2 = this.firstInnerClass; var2 != null; var2 = var2.nextInnerClass) {
         if(var1.equals(var2.getSimpleName())) {
            return var2;
         }
      }

      return null;
   }

   public Field getDeclaredField(String var1) {
      for(Field var2 = this.getFields(); var2 != null; var2 = var2.next) {
         if(var1.equals(var2.name)) {
            return var2;
         }
      }

      return null;
   }

   public Method getDeclaredMethod(String param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public Method getDeclaredMethod(String var1, Type[] var2) {
      byte var7;
      if("<init>".equals(var1) && this.hasOuterLink()) {
         var7 = 1;
      } else {
         var7 = 0;
      }

      for(Method var3 = this.getDeclaredMethods(); var3 != null; var3 = var3.next) {
         if(var1.equals(var3.getName())) {
            Type[] var4 = var3.getParameterTypes();
            if(var2 == null || var2 == var4 && var7 == 0) {
               return var3;
            }

            int var8 = var2.length;
            if(var8 == var4.length - var7) {
               int var9;
               while(true) {
                  var9 = var8 - 1;
                  if(var9 < 0) {
                     break;
                  }

                  Type var5 = var4[var9 + var7];
                  Type var6 = var2[var9];
                  var8 = var9;
                  if(var5 != var6) {
                     var8 = var9;
                     if(var6 != null) {
                        var8 = var9;
                        if(!var5.getSignature().equals(var6.getSignature())) {
                           break;
                        }
                     }
                  }
               }

               if(var9 < 0) {
                  return var3;
               }
            }
         }
      }

      return null;
   }

   public final Method getDeclaredMethods() {
      synchronized(this){}

      Method var1;
      try {
         if((this.flags & 18) == 16) {
            this.addMethods(this.getReflectClass());
         }

         var1 = this.methods;
      } finally {
         ;
      }

      return var1;
   }

   public ClassType getDeclaringClass() {
      this.addEnclosingMember();
      return this.enclosingMember instanceof ClassType?(ClassType)this.enclosingMember:null;
   }

   public Member getEnclosingMember() {
      this.addEnclosingMember();
      return this.enclosingMember;
   }

   public Field getField(String var1) {
      return this.getField(var1, 1);
   }

   public Field getField(String param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public final int getFieldCount() {
      return this.fields_count;
   }

   public final Field getFields() {
      synchronized(this){}

      Field var1;
      try {
         if((this.flags & 17) == 16) {
            this.addFields();
         }

         var1 = this.fields;
      } finally {
         ;
      }

      return var1;
   }

   public ClassType[] getInterfaces() {
      // $FF: Couldn't be decompiled
   }

   public Method[] getMatchingMethods(String var1, Type[] var2, int var3) {
      int var6 = 0;
      Vector var5 = new Vector(10);

      int var7;
      for(Method var4 = this.methods; var4 != null; var6 = var7) {
         if(!var1.equals(var4.getName())) {
            var7 = var6;
         } else {
            var7 = var6;
            if((var3 & 8) == (var4.access_flags & 8)) {
               var7 = var6;
               if((var3 & 1) <= (var4.access_flags & 1)) {
                  var7 = var6;
                  if(var4.arg_types.length == var2.length) {
                     var7 = var6 + 1;
                     var5.addElement(var4);
                  }
               }
            }
         }

         var4 = var4.getNext();
      }

      Method[] var8 = new Method[var6];
      var5.copyInto(var8);
      return var8;
   }

   public Method getMethod(String param1, Type[] param2) {
      // $FF: Couldn't be decompiled
   }

   public Method getMethod(java.lang.reflect.Method var1) {
      String var2 = var1.getName();
      Class[] var3 = var1.getParameterTypes();
      Type[] var4 = new Type[var3.length];
      int var5 = var3.length;

      while(true) {
         --var5;
         if(var5 < 0) {
            return this.addMethod(var2, var1.getModifiers(), var4, Type.make(var1.getReturnType()));
         }

         var4[var5] = Type.make(var3[var5]);
      }
   }

   public final int getMethodCount() {
      return this.methods_count;
   }

   public int getMethods(Filter var1, int var2, List var3) {
      int var8 = 0;
      String var5 = null;
      ClassType var4 = this;

      int var9;
      while(true) {
         var9 = var8;
         if(var4 == null) {
            break;
         }

         String var7 = var4.getPackageName();

         for(Method var6 = var4.getDeclaredMethods(); var6 != null; var8 = var9) {
            label51: {
               if(var4 != this) {
                  var9 = var6.getModifiers();
                  if((var9 & 2) != 0) {
                     var9 = var8;
                     break label51;
                  }

                  if((var9 & 5) == 0) {
                     var9 = var8;
                     if(!var7.equals(var5)) {
                        break label51;
                     }
                  }
               }

               var9 = var8;
               if(var1.select(var6)) {
                  if(var3 != null) {
                     var3.add(var6);
                  }

                  var9 = var8 + 1;
               }
            }

            var6 = var6.getNext();
         }

         var5 = var7;
         if(var2 == 0) {
            var9 = var8;
            break;
         }

         var9 = var8;
         if(var2 > 1) {
            ClassType[] var11 = var4.getInterfaces();
            var9 = var8;
            if(var11 != null) {
               int var10 = 0;

               while(true) {
                  var9 = var8;
                  if(var10 >= var11.length) {
                     break;
                  }

                  var8 += var11[var10].getMethods(var1, var2, var3);
                  ++var10;
               }
            }
         }

         var4 = var4.getSuperclass();
         var8 = var9;
      }

      return var9;
   }

   public int getMethods(Filter var1, int var2, Method[] var3, int var4) {
      Vector var5 = new Vector();
      this.getMethods(var1, var2, var5);
      int var6 = var5.size();

      for(var2 = 0; var2 < var6; ++var2) {
         var3[var4 + var2] = (Method)var5.elementAt(var2);
      }

      return var6;
   }

   public final Method getMethods() {
      return this.methods;
   }

   public Method[] getMethods(Filter var1, int var2) {
      Vector var3 = new Vector();
      this.getMethods(var1, var2, var3);
      int var4 = var3.size();
      Method[] var5 = new Method[var4];

      for(var2 = 0; var2 < var4; ++var2) {
         var5[var2] = (Method)var3.elementAt(var2);
      }

      return var5;
   }

   public Method[] getMethods(Filter var1, boolean var2) {
      byte var3;
      if(var2) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      return this.getMethods(var1, var3);
   }

   public final int getModifiers() {
      synchronized(this){}

      int var2;
      try {
         if(this.access_flags == 0 && (this.flags & 16) != 0 && this.getReflectClass() != null) {
            this.access_flags = this.reflectClass.getModifiers();
         }

         var2 = this.access_flags;
      } finally {
         ;
      }

      return var2;
   }

   public ClassType getOuterLinkType() {
      return !this.hasOuterLink()?null:(ClassType)this.getDeclaredField("this$0").getType();
   }

   public String getPackageName() {
      String var1 = this.getName();
      int var2 = var1.lastIndexOf(46);
      return var2 < 0?"":var1.substring(0, var2);
   }

   public String getSimpleName() {
      // $FF: Couldn't be decompiled
   }

   public final boolean getStaticFlag() {
      return (this.getModifiers() & 8) != 0;
   }

   public ClassType getSuperclass() {
      synchronized(this){}

      ClassType var1;
      try {
         if(this.superClass == null && !this.isInterface() && !"java.lang.Object".equals(this.getName()) && (this.flags & 16) != 0 && this.getReflectClass() != null) {
            this.superClass = (ClassType)make(this.reflectClass.getSuperclass());
         }

         var1 = this.superClass;
      } finally {
         ;
      }

      return var1;
   }

   public final boolean hasOuterLink() {
      this.getFields();
      return (this.flags & 32) != 0;
   }

   public final boolean implementsInterface(ClassType var1) {
      if(this != var1) {
         ClassType var2 = this.getSuperclass();
         if(var2 == null || !var2.implementsInterface(var1)) {
            ClassType[] var5 = this.getInterfaces();
            if(var5 != null) {
               int var3 = var5.length;

               while(true) {
                  int var4 = var3 - 1;
                  if(var4 < 0) {
                     break;
                  }

                  var3 = var4;
                  if(var5[var4].implementsInterface(var1)) {
                     return true;
                  }
               }
            }

            return false;
         }
      }

      return true;
   }

   public boolean isAccessible(ClassType var1, ObjectType var2, int var3) {
      int var6 = var1.getModifiers();
      if((var3 & 1) == 0 || (var6 & 1) == 0) {
         String var4 = this.getName();
         String var5 = var1.getName();
         if(!var4.equals(var5)) {
            if((var3 & 2) != 0) {
               return false;
            }

            int var7 = var4.lastIndexOf(46);
            if(var7 >= 0) {
               var4 = var4.substring(0, var7);
            } else {
               var4 = "";
            }

            var7 = var5.lastIndexOf(46);
            if(var7 >= 0) {
               var5 = var5.substring(0, var7);
            } else {
               var5 = "";
            }

            if(!var4.equals(var5)) {
               if((var6 & 1) == 0) {
                  return false;
               }

               if((var3 & 4) == 0 || !this.isSubclass((ClassType)var1) || var2 instanceof ClassType && !((ClassType)var2).isSubclass((ClassType)this)) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   public boolean isAccessible(Member var1, ObjectType var2) {
      if(var1.getStaticFlag()) {
         var2 = null;
      }

      return this.isAccessible(var1.getDeclaringClass(), var2, var1.getModifiers());
   }

   public final boolean isInterface() {
      return (this.getModifiers() & 512) != 0;
   }

   public final boolean isSubclass(ClassType var1) {
      boolean var4 = true;
      boolean var3;
      if(var1.isInterface()) {
         var3 = this.implementsInterface(var1);
      } else {
         if(this == toStringType) {
            var3 = var4;
            if(var1 == javalangStringType) {
               return var3;
            }
         }

         if(this == javalangStringType) {
            var3 = var4;
            if(var1 == toStringType) {
               return var3;
            }
         }

         ClassType var2 = this;

         while(true) {
            if(var2 == null) {
               return false;
            }

            var3 = var4;
            if(var2 == var1) {
               break;
            }

            var2 = var2.getSuperclass();
         }
      }

      return var3;
   }

   public final boolean isSubclass(String var1) {
      ClassType var2 = this;

      while(!var1.equals(var2.getName())) {
         ClassType var3 = var2.getSuperclass();
         var2 = var3;
         if(var3 == null) {
            return false;
         }
      }

      return true;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.setName(var1.readUTF());
      this.flags |= 16;
   }

   public Object readResolve() throws ObjectStreamException {
      // $FF: Couldn't be decompiled
   }

   public final void setAttributes(Attribute var1) {
      this.attributes = var1;
   }

   public void setClassfileVersion(int var1) {
      this.classfileFormatVersion = var1;
   }

   public void setClassfileVersion(int var1, int var2) {
      this.classfileFormatVersion = (var1 & '\uffff') * 65536 + var2 * '\uffff';
   }

   public void setClassfileVersionJava5() {
      this.setClassfileVersion(3211264);
   }

   public void setEnclosingMember(Member var1) {
      this.enclosingMember = var1;
   }

   public final void setInterface(boolean var1) {
      if(var1) {
         this.access_flags |= 1536;
      } else {
         this.access_flags &= -513;
      }
   }

   public void setInterfaces(ClassType[] var1) {
      this.interfaces = var1;
   }

   public final void setModifiers(int var1) {
      this.access_flags = var1;
   }

   public void setName(String var1) {
      this.this_name = var1;
      this.setSignature("L" + var1.replace('.', '/') + ";");
   }

   public final Field setOuterLink(ClassType var1) {
      if((this.flags & 16) != 0) {
         throw new Error("setOuterLink called for existing class " + this.getName());
      } else {
         Field var2 = this.getDeclaredField("this$0");
         Field var3;
         if(var2 == null) {
            Field var4 = this.addField("this$0", var1);
            this.flags |= 32;
            Method var6 = this.methods;

            while(true) {
               var3 = var4;
               if(var6 == null) {
                  break;
               }

               if("<init>".equals(var6.getName())) {
                  if(var6.code != null) {
                     throw new Error("setOuterLink called when " + var6 + " has code");
                  }

                  Type[] var7 = var6.arg_types;
                  Type[] var5 = new Type[var7.length + 1];
                  System.arraycopy(var7, 0, var5, 1, var7.length);
                  var5[0] = var1;
                  var6.arg_types = var5;
                  var6.signature = null;
               }

               var6 = var6.getNext();
            }
         } else {
            var3 = var2;
            if(!var1.equals(var2.getType())) {
               throw new Error("inconsistent setOuterLink call for " + this.getName());
            }
         }

         return var3;
      }
   }

   public void setSourceFile(String var1) {
      if(this.sourceDbgExt != null) {
         this.sourceDbgExt.addFile(var1);
         if(this.sourceDbgExt.fileCount > 1) {
            return;
         }
      }

      String var2 = SourceFileAttr.fixSourceFile(var1);
      int var3 = var2.lastIndexOf(47);
      var1 = var2;
      if(var3 >= 0) {
         var1 = var2.substring(var3 + 1);
      }

      SourceFileAttr.setSourceFile(this, var1);
   }

   public void setStratum(String var1) {
      if(this.sourceDbgExt == null) {
         this.sourceDbgExt = new SourceDebugExtAttr(this);
      }

      this.sourceDbgExt.addStratum(var1);
   }

   public void setSuper(ClassType var1) {
      this.superClass = var1;
   }

   public void setSuper(String var1) {
      ClassType var2;
      if(var1 == null) {
         var2 = Type.pointer_type;
      } else {
         var2 = make(var1);
      }

      this.setSuper((ClassType)var2);
   }

   public String toString() {
      return "ClassType " + this.getName();
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeUTF(this.getName());
   }

   public byte[] writeToArray() {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream(500);

      try {
         this.writeToStream(var1);
      } catch (IOException var2) {
         throw new InternalError(var2.toString());
      }

      return var1.toByteArray();
   }

   public void writeToFile() throws IOException {
      this.writeToFile(this.this_name.replace('.', File.separatorChar) + ".class");
   }

   public void writeToFile(String var1) throws IOException {
      BufferedOutputStream var2 = new BufferedOutputStream(new FileOutputStream(var1));
      this.writeToStream(var2);
      var2.close();
   }

   public void writeToStream(OutputStream var1) throws IOException {
      DataOutputStream var2 = new DataOutputStream(var1);
      this.doFixups();
      var2.writeInt(-889275714);
      var2.writeShort(this.getClassfileMinorVersion());
      var2.writeShort(this.getClassfileMajorVersion());
      if(this.constants == null) {
         var2.writeShort(1);
      } else {
         this.constants.write(var2);
      }

      var2.writeShort(this.access_flags);
      var2.writeShort(this.thisClassIndex);
      var2.writeShort(this.superClassIndex);
      if(this.interfaceIndexes == null) {
         var2.writeShort(0);
      } else {
         int var4 = this.interfaceIndexes.length;
         var2.writeShort(var4);

         for(int var3 = 0; var3 < var4; ++var3) {
            var2.writeShort(this.interfaceIndexes[var3]);
         }
      }

      var2.writeShort(this.fields_count);

      for(Field var5 = this.fields; var5 != null; var5 = var5.next) {
         var5.write(var2, this);
      }

      var2.writeShort(this.methods_count);

      for(Method var6 = this.methods; var6 != null; var6 = var6.next) {
         var6.write(var2, this);
      }

      Attribute.writeAll(this, var2);
      this.flags |= 3;
   }

   static class AbstractMethodFilter implements Filter {

      public static final ClassType.AbstractMethodFilter instance = new ClassType.AbstractMethodFilter();


      public boolean select(Object var1) {
         return ((Method)var1).isAbstract();
      }
   }
}
