package gnu.kawa.reflect;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Target;
import gnu.expr.TypeValue;
import gnu.kawa.reflect.InstanceOf;
import gnu.kawa.reflect.SingletonType;
import gnu.lists.ItemPredicate;
import gnu.mapping.Procedure;
import gnu.mapping.Values;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class OccurrenceType extends ObjectType implements Externalizable, TypeValue {

   public static final Type emptySequenceType = getInstance(SingletonType.instance, 0, 0);
   static final Method isInstanceMethod = typeOccurrenceType.getDeclaredMethod("isInstance", 1);
   public static final ClassType typeOccurrenceType = ClassType.make("gnu.kawa.reflect.OccurrenceType");
   Type base;
   int maxOccurs;
   int minOccurs;


   public OccurrenceType(Type var1, int var2, int var3) {
      this.base = var1;
      this.minOccurs = var2;
      this.maxOccurs = var3;
   }

   public static Type getInstance(Type var0, int var1, int var2) {
      return (Type)(var1 == 1 && var2 == 1?var0:(var1 == 0 && var2 < 0 && (var0 == SingletonType.instance || var0 == Type.pointer_type)?Type.pointer_type:new OccurrenceType(var0, var1, var2)));
   }

   public static char itemCountCode(Type var0) {
      int var2 = itemCountRange(var0);
      int var1 = var2 & 4095;
      var2 >>= 12;
      return (char)(var2 == 0?'0':(var1 == 0?(var2 == 1?'?':'*'):(var1 == 1 && var2 == 1?'1':'+')));
   }

   public static boolean itemCountIsOne(Type var0) {
      return itemCountRange(var0) == 4097;
   }

   public static boolean itemCountIsZeroOrOne(Type var0) {
      return itemCountRange(var0) >> 13 == 0;
   }

   public static int itemCountRange(Type var0) {
      short var1 = 0;
      if(!(var0 instanceof SingletonType)) {
         if(var0 instanceof OccurrenceType) {
            OccurrenceType var7 = (OccurrenceType)var0;
            int var2 = var7.minOccurs();
            int var3 = var7.maxOccurs();
            int var6 = itemCountRange(var7.getBase());
            if((var2 != 1 || var3 != 1) && var6 != 0) {
               int var8 = var3;
               if(var3 > 1048575) {
                  var8 = -1;
               }

               if(var8 == 0) {
                  return 0;
               }

               int var5 = var6 >> 12;
               var3 = var8;
               int var4 = var2;
               if(var6 != 4097) {
                  var3 = var2;
                  if(var2 > 4095) {
                     var3 = 4095;
                  }

                  var3 *= var6 & 4095;
                  var2 = var3;
                  if(var3 > 4095) {
                     var2 = 4095;
                  }

                  if(var8 >= 0 && var5 >= 0) {
                     var8 *= var5;
                  } else {
                     var8 = -1;
                  }

                  var3 = var8;
                  var4 = var2;
                  if(var8 > 1048575) {
                     var3 = -1;
                     var4 = var2;
                  }
               }

               return var3 << 12 | var4;
            }

            return var6;
         }

         if(var0 instanceof PrimType) {
            if(!var0.isVoid()) {
               var1 = 4097;
            }

            return var1;
         }

         if(!(var0 instanceof ArrayType) && (!(var0 instanceof ObjectType) || var0.compare(Compilation.typeValues) != -3)) {
            return -4096;
         }
      }

      return 4097;
   }

   public static Type itemPrimeType(Type var0) {
      while(var0 instanceof OccurrenceType) {
         var0 = ((OccurrenceType)var0).getBase();
      }

      if(itemCountIsOne(var0)) {
         return var0;
      } else {
         return SingletonType.instance;
      }
   }

   public Object coerceFromObject(Object var1) {
      Object var2;
      if(!(var1 instanceof Values) && this.minOccurs <= 1 && this.maxOccurs != 0) {
         var2 = this.base.coerceFromObject(var1);
      } else {
         var2 = var1;
         if(!this.isInstance(var1)) {
            throw new ClassCastException();
         }
      }

      return var2;
   }

   public int compare(Type var1) {
      if(var1 instanceof OccurrenceType) {
         OccurrenceType var2 = (OccurrenceType)var1;
         if(this.minOccurs == var2.minOccurs && this.maxOccurs == var2.maxOccurs) {
            return this.base.compare(var2.getBase());
         }
      }

      return -2;
   }

   public Expression convertValue(Expression var1) {
      return null;
   }

   public void emitIsInstance(Variable var1, Compilation var2, Target var3) {
      InstanceOf.emitIsInstance(this, var1, var2, var3);
   }

   public void emitTestIf(Variable var1, Declaration var2, Compilation var3) {
      CodeAttr var4 = var3.getCode();
      if(var1 != null) {
         var4.emitLoad(var1);
      }

      if(var2 != null) {
         var4.emitDup();
         var2.compileStore(var3);
      }

      var3.compileConstant(this);
      var4.emitSwap();
      var4.emitInvokeVirtual(isInstanceMethod);
      var4.emitIfIntNotZero();
   }

   public Type getBase() {
      return this.base;
   }

   public Procedure getConstructor() {
      return null;
   }

   public Type getImplementationType() {
      return Type.pointer_type;
   }

   public boolean isInstance(Object var1) {
      boolean var10 = true;
      boolean var8 = true;
      boolean var9 = false;
      boolean var7;
      if(var1 instanceof Values) {
         Values var11 = (Values)var1;
         int var3 = var11.startPos();
         int var4 = 0;
         byte var6 = 0;
         int var5 = var3;
         if(!(this.base instanceof ItemPredicate)) {
            while(true) {
               var5 = var11.nextPos(var5);
               if(var5 == 0) {
                  if(var4 >= this.minOccurs) {
                     var7 = var10;
                     if(this.maxOccurs < 0) {
                        return var7;
                     }

                     if(var4 <= this.maxOccurs) {
                        var7 = var10;
                        return var7;
                     }
                  }

                  var7 = false;
                  return var7;
               }

               Object var12 = var11.getPosPrevious(var5);
               if(!this.base.isInstance(var12)) {
                  return false;
               }

               ++var4;
            }
         }

         ItemPredicate var2 = (ItemPredicate)this.base;
         var4 = var3;
         var3 = var6;

         while(true) {
            var10 = var2.isInstancePos(var11, var4);
            var4 = var11.nextPos(var4);
            if(var4 == 0) {
               if(var3 >= this.minOccurs) {
                  var7 = var8;
                  if(this.maxOccurs < 0) {
                     break;
                  }

                  if(var3 <= this.maxOccurs) {
                     var7 = var8;
                     break;
                  }
               }

               var7 = false;
               break;
            }

            var7 = var9;
            if(!var10) {
               break;
            }

            ++var3;
         }
      } else {
         var7 = var9;
         if(this.minOccurs <= 1) {
            var7 = var9;
            if(this.maxOccurs != 0) {
               return this.base.isInstance(var1);
            }
         }
      }

      return var7;
   }

   public int maxOccurs() {
      return this.maxOccurs;
   }

   public int minOccurs() {
      return this.minOccurs;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.base = (Type)var1.readObject();
      this.minOccurs = var1.readInt();
      this.maxOccurs = var1.readInt();
   }

   public String toString() {
      String var1 = this.base.toString();
      boolean var3;
      if(var1 != null && var1.indexOf(32) < 0) {
         var3 = false;
      } else {
         var3 = true;
      }

      StringBuffer var2 = new StringBuffer();
      if(var3) {
         var2.append('(');
      }

      var2.append(var1);
      if(var3) {
         var2.append(')');
      }

      if(this.minOccurs != 1 || this.maxOccurs != 1) {
         if(this.minOccurs == 0 && this.maxOccurs == 1) {
            var2.append('?');
         } else if(this.minOccurs == 1 && this.maxOccurs == -1) {
            var2.append('+');
         } else if(this.minOccurs == 0 && this.maxOccurs == -1) {
            var2.append('*');
         } else {
            var2.append('{');
            var2.append(this.minOccurs);
            var2.append(',');
            if(this.maxOccurs >= 0) {
               var2.append(this.maxOccurs);
            } else {
               var2.append('*');
            }

            var2.append('}');
         }
      }

      return var2.toString();
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.base);
      var1.writeInt(this.minOccurs);
      var1.writeInt(this.maxOccurs);
   }
}
