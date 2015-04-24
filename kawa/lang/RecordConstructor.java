package kawa.lang;

import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.bytecode.Type;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.ProcedureN;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongArguments;
import kawa.lang.GenericError;

public class RecordConstructor extends ProcedureN {

   Field[] fields;
   ClassType type;


   public RecordConstructor(ClassType var1) {
      this.init(var1);
   }

   public RecordConstructor(ClassType var1, Object var2) {
      this.type = var1;
      if(var2 == null) {
         this.init(var1);
      } else {
         int var7 = LList.listLength(var2, false);
         this.fields = new Field[var7];
         Field var3 = var1.getFields();
         int var6 = 0;

         label24:
         while(var6 < var7) {
            Pair var4 = (Pair)var2;
            String var5 = var4.getCar().toString();

            for(Field var8 = var3; var8 != null; var8 = var8.getNext()) {
               if(var8.getSourceName() == var5) {
                  this.fields[var6] = var8;
                  var2 = var4.getCdr();
                  ++var6;
                  continue label24;
               }
            }

            throw new RuntimeException("no such field " + var5 + " in " + var1.getName());
         }
      }

   }

   public RecordConstructor(ClassType var1, Field[] var2) {
      this.type = var1;
      this.fields = var2;
   }

   public RecordConstructor(Class var1) {
      this.init((ClassType)Type.make(var1));
   }

   public RecordConstructor(Class var1, Object var2) {
      this((ClassType)((ClassType)Type.make(var1)), (Object)var2);
   }

   public RecordConstructor(Class var1, Field[] var2) {
      this((ClassType)((ClassType)Type.make(var1)), (Field[])var2);
   }

   private void init(ClassType var1) {
      this.type = var1;
      Field var5 = var1.getFields();
      int var3 = 0;

      int var4;
      for(Field var2 = var5; var2 != null; var3 = var4) {
         var4 = var3;
         if((var2.getModifiers() & 9) == 1) {
            var4 = var3 + 1;
         }

         var2 = var2.getNext();
      }

      this.fields = new Field[var3];

      for(var3 = 0; var5 != null; var5 = var5.getNext()) {
         if((var5.getModifiers() & 9) == 1) {
            Field[] var6 = this.fields;
            var4 = var3 + 1;
            var6[var3] = var5;
            var3 = var4;
         }
      }

   }

   public Object applyN(Object[] var1) {
      Object var3;
      try {
         var3 = this.type.getReflectClass().newInstance();
      } catch (InstantiationException var6) {
         throw new GenericError(var6.toString());
      } catch (IllegalAccessException var7) {
         throw new GenericError(var7.toString());
      }

      if(var1.length != this.fields.length) {
         throw new WrongArguments(this, var1.length);
      } else {
         for(int var4 = 0; var4 < var1.length; ++var4) {
            Field var2 = this.fields[var4];

            try {
               var2.getReflectField().set(var3, var1[var4]);
            } catch (Exception var5) {
               throw new WrappedException("illegal access for field " + var2.getName(), var5);
            }
         }

         return var3;
      }
   }

   public String getName() {
      return this.type.getName() + " constructor";
   }

   public int numArgs() {
      int var1 = this.fields.length;
      return var1 << 12 | var1;
   }
}
