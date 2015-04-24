package gnu.bytecode;

import gnu.bytecode.CpoolClass;
import gnu.bytecode.CpoolEntry;
import gnu.bytecode.CpoolNameAndType;
import gnu.bytecode.CpoolRef;
import gnu.bytecode.CpoolString;
import gnu.bytecode.CpoolUtf8;
import gnu.bytecode.CpoolValue1;
import gnu.bytecode.CpoolValue2;
import gnu.bytecode.Field;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ConstantPool {

   public static final byte CLASS = 7;
   public static final byte DOUBLE = 6;
   public static final byte FIELDREF = 9;
   public static final byte FLOAT = 4;
   public static final byte INTEGER = 3;
   public static final byte INTERFACE_METHODREF = 11;
   public static final byte LONG = 5;
   public static final byte METHODREF = 10;
   public static final byte NAME_AND_TYPE = 12;
   public static final byte STRING = 8;
   public static final byte UTF8 = 1;
   int count;
   CpoolEntry[] hashTab;
   boolean locked;
   CpoolEntry[] pool;


   public ConstantPool() {
   }

   public ConstantPool(DataInputStream var1) throws IOException {
      this.count = var1.readUnsignedShort() - 1;
      this.pool = new CpoolEntry[this.count + 1];

      int var4;
      for(int var3 = 1; var3 <= this.count; var3 = var4 + 1) {
         byte var5 = var1.readByte();
         CpoolEntry var2 = this.getForced(var3, var5);
         var4 = var3;
         switch(var5) {
         case 1:
            ((CpoolUtf8)var2).string = var1.readUTF();
            var4 = var3;
         case 2:
            break;
         case 3:
         case 4:
            ((CpoolValue1)var2).value = var1.readInt();
            var4 = var3;
            break;
         case 5:
         case 6:
            ((CpoolValue2)var2).value = var1.readLong();
            var4 = var3 + 1;
            break;
         case 7:
            ((CpoolClass)var2).name = (CpoolUtf8)this.getForced(var1.readUnsignedShort(), 1);
            var4 = var3;
            break;
         case 8:
            ((CpoolString)var2).str = (CpoolUtf8)this.getForced(var1.readUnsignedShort(), 1);
            var4 = var3;
            break;
         case 9:
         case 10:
         case 11:
            CpoolRef var7 = (CpoolRef)var2;
            var7.clas = this.getForcedClass(var1.readUnsignedShort());
            var7.nameAndType = (CpoolNameAndType)this.getForced(var1.readUnsignedShort(), 12);
            var4 = var3;
            break;
         case 12:
            CpoolNameAndType var6 = (CpoolNameAndType)var2;
            var6.name = (CpoolUtf8)this.getForced(var1.readUnsignedShort(), 1);
            var6.type = (CpoolUtf8)this.getForced(var1.readUnsignedShort(), 1);
            var4 = var3;
            break;
         default:
            var4 = var3;
         }
      }

   }

   public CpoolClass addClass(CpoolUtf8 var1) {
      int var4 = CpoolClass.hashCode(var1);
      if(this.hashTab == null) {
         this.rehash();
      }

      int var5 = this.hashTab.length;

      for(CpoolEntry var2 = this.hashTab[(Integer.MAX_VALUE & var4) % var5]; var2 != null; var2 = var2.next) {
         if(var4 == var2.hash && var2 instanceof CpoolClass) {
            CpoolClass var3 = (CpoolClass)var2;
            if(var3.name == var1) {
               return var3;
            }
         }
      }

      return new CpoolClass(this, var4, var1);
   }

   public CpoolClass addClass(ObjectType var1) {
      CpoolClass var2 = this.addClass((CpoolUtf8)this.addUtf8(var1.getInternalName()));
      var2.clas = var1;
      return var2;
   }

   public CpoolValue2 addDouble(double var1) {
      return this.addValue2(6, Double.doubleToLongBits(var1));
   }

   public CpoolRef addFieldRef(Field var1) {
      return this.addRef(9, this.addClass((ObjectType)var1.owner), this.addNameAndType((Field)var1));
   }

   public CpoolValue1 addFloat(float var1) {
      return this.addValue1(4, Float.floatToIntBits(var1));
   }

   public CpoolValue1 addInt(int var1) {
      return this.addValue1(3, var1);
   }

   public CpoolValue2 addLong(long var1) {
      return this.addValue2(5, var1);
   }

   public CpoolRef addMethodRef(Method var1) {
      CpoolClass var2 = this.addClass((ObjectType)var1.classfile);
      byte var3;
      if((var1.getDeclaringClass().getModifiers() & 512) == 0) {
         var3 = 10;
      } else {
         var3 = 11;
      }

      return this.addRef(var3, var2, this.addNameAndType((Method)var1));
   }

   public CpoolNameAndType addNameAndType(CpoolUtf8 var1, CpoolUtf8 var2) {
      int var4 = CpoolNameAndType.hashCode(var1, var2);
      if(this.hashTab == null) {
         this.rehash();
      }

      int var5 = this.hashTab.length;

      for(CpoolEntry var3 = this.hashTab[(Integer.MAX_VALUE & var4) % var5]; var3 != null; var3 = var3.next) {
         if(var4 == var3.hash && var3 instanceof CpoolNameAndType && ((CpoolNameAndType)var3).name == var1 && ((CpoolNameAndType)var3).type == var2) {
            return (CpoolNameAndType)var3;
         }
      }

      return new CpoolNameAndType(this, var4, var1, var2);
   }

   public CpoolNameAndType addNameAndType(Field var1) {
      return this.addNameAndType(this.addUtf8(var1.getName()), this.addUtf8(var1.getSignature()));
   }

   public CpoolNameAndType addNameAndType(Method var1) {
      return this.addNameAndType(this.addUtf8(var1.getName()), this.addUtf8(var1.getSignature()));
   }

   public CpoolRef addRef(int var1, CpoolClass var2, CpoolNameAndType var3) {
      int var6 = CpoolRef.hashCode(var2, var3);
      if(this.hashTab == null) {
         this.rehash();
      }

      int var7 = this.hashTab.length;

      for(CpoolEntry var4 = this.hashTab[(Integer.MAX_VALUE & var6) % var7]; var4 != null; var4 = var4.next) {
         if(var6 == var4.hash && var4 instanceof CpoolRef) {
            CpoolRef var5 = (CpoolRef)var4;
            if(var5.tag == var1 && var5.clas == var2 && var5.nameAndType == var3) {
               return var5;
            }
         }
      }

      return new CpoolRef(this, var6, var1, var2, var3);
   }

   public CpoolString addString(CpoolUtf8 var1) {
      int var4 = CpoolString.hashCode(var1);
      if(this.hashTab == null) {
         this.rehash();
      }

      int var5 = this.hashTab.length;

      for(CpoolEntry var2 = this.hashTab[(Integer.MAX_VALUE & var4) % var5]; var2 != null; var2 = var2.next) {
         if(var4 == var2.hash && var2 instanceof CpoolString) {
            CpoolString var3 = (CpoolString)var2;
            if(var3.str == var1) {
               return var3;
            }
         }
      }

      return new CpoolString(this, var4, var1);
   }

   public final CpoolString addString(String var1) {
      return this.addString((CpoolUtf8)this.addUtf8(var1));
   }

   public CpoolUtf8 addUtf8(String var1) {
      String var2 = var1.intern();
      int var4 = var2.hashCode();
      if(this.hashTab == null) {
         this.rehash();
      }

      int var5 = this.hashTab.length;

      for(CpoolEntry var6 = this.hashTab[(Integer.MAX_VALUE & var4) % var5]; var6 != null; var6 = var6.next) {
         if(var4 == var6.hash && var6 instanceof CpoolUtf8) {
            CpoolUtf8 var3 = (CpoolUtf8)var6;
            if(var3.string == var2) {
               return var3;
            }
         }
      }

      if(this.locked) {
         throw new Error("adding new Utf8 entry to locked contant pool: " + var2);
      } else {
         return new CpoolUtf8(this, var4, var2);
      }
   }

   CpoolValue1 addValue1(int var1, int var2) {
      int var5 = CpoolValue1.hashCode(var2);
      if(this.hashTab == null) {
         this.rehash();
      }

      int var6 = this.hashTab.length;

      for(CpoolEntry var3 = this.hashTab[(Integer.MAX_VALUE & var5) % var6]; var3 != null; var3 = var3.next) {
         if(var5 == var3.hash && var3 instanceof CpoolValue1) {
            CpoolValue1 var4 = (CpoolValue1)var3;
            if(var4.tag == var1 && var4.value == var2) {
               return var4;
            }
         }
      }

      return new CpoolValue1(this, var1, var5, var2);
   }

   CpoolValue2 addValue2(int var1, long var2) {
      int var6 = CpoolValue2.hashCode(var2);
      if(this.hashTab == null) {
         this.rehash();
      }

      int var7 = this.hashTab.length;

      for(CpoolEntry var4 = this.hashTab[(Integer.MAX_VALUE & var6) % var7]; var4 != null; var4 = var4.next) {
         if(var6 == var4.hash && var4 instanceof CpoolValue2) {
            CpoolValue2 var5 = (CpoolValue2)var4;
            if(var5.tag == var1 && var5.value == var2) {
               return var5;
            }
         }
      }

      return new CpoolValue2(this, var1, var6, var2);
   }

   public final int getCount() {
      return this.count;
   }

   CpoolEntry getForced(int var1, int var2) {
      var1 &= '\uffff';
      CpoolEntry var4 = this.pool[var1];
      Object var3;
      if(var4 == null) {
         if(this.locked) {
            throw new Error("adding new entry to locked contant pool");
         }

         var3 = var4;
         switch(var2) {
         case 1:
            var3 = new CpoolUtf8();
         case 2:
            break;
         case 3:
         case 4:
            var3 = new CpoolValue1(var2);
            break;
         case 5:
         case 6:
            var3 = new CpoolValue2(var2);
            break;
         case 7:
            var3 = new CpoolClass();
            break;
         case 8:
            var3 = new CpoolString();
            break;
         case 9:
         case 10:
         case 11:
            var3 = new CpoolRef(var2);
            break;
         case 12:
            var3 = new CpoolNameAndType();
            break;
         default:
            var3 = var4;
         }

         this.pool[var1] = (CpoolEntry)var3;
         ((CpoolEntry)var3).index = var1;
      } else {
         var3 = var4;
         if(var4.getTag() != var2) {
            throw new ClassFormatError("conflicting constant pool tags at " + var1);
         }
      }

      return (CpoolEntry)var3;
   }

   CpoolClass getForcedClass(int var1) {
      return (CpoolClass)this.getForced(var1, 7);
   }

   public final CpoolEntry getPoolEntry(int var1) {
      return this.pool[var1];
   }

   void rehash() {
      CpoolEntry var1;
      int var2;
      int var3;
      if(this.hashTab == null && this.count > 0) {
         var2 = this.pool.length;

         while(true) {
            var3 = var2 - 1;
            if(var3 < 0) {
               break;
            }

            var1 = this.pool[var3];
            var2 = var3;
            if(var1 != null) {
               var1.hashCode();
               var2 = var3;
            }
         }
      }

      if(this.count < 5) {
         var2 = 101;
      } else {
         var2 = this.count * 2;
      }

      this.hashTab = new CpoolEntry[var2];
      if(this.pool != null) {
         var2 = this.pool.length;

         while(true) {
            var3 = var2 - 1;
            if(var3 < 0) {
               break;
            }

            var1 = this.pool[var3];
            var2 = var3;
            if(var1 != null) {
               var1.add_hashed(this);
               var2 = var3;
            }
         }
      }

   }

   void write(DataOutputStream var1) throws IOException {
      var1.writeShort(this.count + 1);

      for(int var3 = 1; var3 <= this.count; ++var3) {
         CpoolEntry var2 = this.pool[var3];
         if(var2 != null) {
            var2.write(var1);
         }
      }

      this.locked = true;
   }
}
