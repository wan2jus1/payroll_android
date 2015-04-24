package kawa.lang;

import gnu.bytecode.ArrayClassLoader;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrappedException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Vector;
import kawa.lang.GenericError;

public class Record {

   static Field getField(Class var0, String var1) throws NoSuchFieldException {
      for(gnu.bytecode.Field var2 = ((ClassType)Type.make(var0)).getFields(); var2 != null; var2 = var2.getNext()) {
         if((var2.getModifiers() & 9) == 1 && var2.getSourceName().equals(var1)) {
            return var2.getReflectField();
         }
      }

      throw new NoSuchFieldException();
   }

   public static boolean isRecord(Object var0) {
      return var0 instanceof Record;
   }

   public static ClassType makeRecordType(String var0, LList var1) {
      ClassType var5 = ClassType.make("kawa.lang.Record");
      String var3 = Compilation.mangleNameIfNeeded(var0);
      ClassType var4 = new ClassType(var3);
      var4.setSuper((ClassType)var5);
      var4.setModifiers(33);
      Method var2 = var4.addMethod("<init>", Type.typeArray0, Type.voidType, 1);
      Method var13 = var5.addMethod("<init>", Type.typeArray0, Type.voidType, 1);
      CodeAttr var11 = var2.startCode();
      var11.emitPushThis();
      var11.emitInvokeSpecial(var13);
      var11.emitReturn();
      LList var12 = var1;
      if(!var0.equals(var3)) {
         var11 = var4.addMethod("getTypeName", Type.typeArray0, Compilation.typeString, 1).startCode();
         var11.emitPushString(var0);
         var11.emitReturn();
         var12 = var1;
      }

      while(var12 != LList.Empty) {
         Pair var7 = (Pair)var12;
         String var10 = var7.getCar().toString();
         var4.addField(Compilation.mangleNameIfNeeded(var10), Type.pointer_type, 1).setSourceName(var10.intern());
         var12 = (LList)var7.getCdr();
      }

      byte[] var8 = var4.writeToArray();
      ArrayClassLoader var9 = new ArrayClassLoader(new String[]{var3}, new byte[][]{var8});

      try {
         Type.registerTypeForClass(var9.loadClass(var3), var4);
         return var4;
      } catch (ClassNotFoundException var6) {
         throw new InternalError(var6.toString());
      }
   }

   public static Object set1(Object var0, String var1, Object var2) {
      Class var3 = var0.getClass();

      try {
         Field var4 = getField(var3, var1);
         Object var5 = var4.get(var0);
         var4.set(var0, var2);
         return var5;
      } catch (NoSuchFieldException var6) {
         throw new GenericError("no such field " + var1 + " in " + var3.getName());
      } catch (IllegalAccessException var7) {
         throw new GenericError("illegal access for field " + var1);
      }
   }

   public static LList typeFieldNames(ClassType var0) {
      return typeFieldNames((Class)var0.getReflectClass());
   }

   public static LList typeFieldNames(Class var0) {
      LList var1 = LList.Empty;
      gnu.bytecode.Field var4 = ((ClassType)Type.make(var0)).getFields();

      Vector var2;
      for(var2 = new Vector(100); var4 != null; var4 = var4.getNext()) {
         if((var4.getModifiers() & 9) == 1) {
            var2.addElement(SimpleSymbol.valueOf(var4.getSourceName()));
         }
      }

      int var3 = var2.size();
      Object var5 = var1;

      while(true) {
         --var3;
         if(var3 < 0) {
            return (LList)var5;
         }

         var5 = new Pair(var2.elementAt(var3), var5);
      }
   }

   public boolean equals(Object var1) {
      if(this != var1) {
         Class var2 = this.getClass();
         if(var1 == null || var1.getClass() != var2) {
            return false;
         }

         for(gnu.bytecode.Field var6 = ((ClassType)Type.make(var2)).getFields(); var6 != null; var6 = var6.getNext()) {
            if((var6.getModifiers() & 9) == 1) {
               Object var3;
               Object var7;
               try {
                  Field var4 = var6.getReflectField();
                  var3 = var4.get(this);
                  var7 = var4.get(var1);
               } catch (Exception var5) {
                  throw new WrappedException(var5);
               }

               if(!var3.equals(var7)) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   public Object get(String var1, Object var2) {
      Class var6 = this.getClass();

      try {
         Object var3 = getField(var6, var1).get(this);
         return var3;
      } catch (NoSuchFieldException var4) {
         throw new GenericError("no such field " + var1 + " in " + var6.getName());
      } catch (IllegalAccessException var5) {
         throw new GenericError("illegal access for field " + var1);
      }
   }

   public String getTypeName() {
      return this.getClass().getName();
   }

   public int hashCode() {
      Field[] var1 = this.getClass().getFields();
      int var4 = 12345;

      int var5;
      for(int var3 = 0; var3 < var1.length; var4 = var5) {
         Field var2 = var1[var3];

         label21: {
            Object var7;
            try {
               var7 = var2.get(this);
            } catch (IllegalAccessException var6) {
               var5 = var4;
               break label21;
            }

            var5 = var4;
            if(var7 != null) {
               var5 = var4 ^ var7.hashCode();
            }
         }

         ++var3;
      }

      return var4;
   }

   public void print(PrintWriter var1) {
      var1.print(this.toString());
   }

   public Object put(String var1, Object var2) {
      return set1(this, var1, var2);
   }

   public String toString() {
      StringBuffer var3 = new StringBuffer(200);
      var3.append("#<");
      var3.append(this.getTypeName());

      for(gnu.bytecode.Field var1 = ((ClassType)Type.make(this.getClass())).getFields(); var1 != null; var1 = var1.getNext()) {
         if((var1.getModifiers() & 9) == 1) {
            Object var2;
            try {
               var2 = var1.getReflectField().get(this);
            } catch (Exception var4) {
               var2 = "#<illegal-access>";
            }

            var3.append(' ');
            var3.append(var1.getSourceName());
            var3.append(": ");
            var3.append(var2);
         }
      }

      var3.append(">");
      return var3.toString();
   }
}
