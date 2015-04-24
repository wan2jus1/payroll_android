package gnu.math;

import gnu.math.Dimensions;
import gnu.math.Unit;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

class MulUnit extends Unit implements Externalizable {

   MulUnit next;
   int power1;
   int power2;
   Unit unit1;
   Unit unit2;


   MulUnit(Unit var1, int var2, Unit var3, int var4) {
      this.unit1 = var1;
      this.unit2 = var3;
      this.power1 = var2;
      this.power2 = var4;
      this.dims = Dimensions.product(var1.dims, var2, var3.dims, var4);
      if(var2 == 1) {
         this.factor = var1.factor;
      } else {
         this.factor = Math.pow(var1.factor, (double)var2);
      }

      if(var4 < 0) {
         var2 = -var4;

         while(true) {
            --var2;
            if(var2 < 0) {
               break;
            }

            this.factor /= var3.factor;
         }
      } else {
         while(true) {
            --var4;
            if(var4 < 0) {
               break;
            }

            this.factor *= var3.factor;
         }
      }

      this.next = var1.products;
      var1.products = this;
   }

   MulUnit(Unit var1, Unit var2, int var3) {
      this(var1, 1, var2, var3);
   }

   static MulUnit lookup(Unit var0, int var1, Unit var2, int var3) {
      for(MulUnit var4 = var0.products; var4 != null; var4 = var4.next) {
         if(var4.unit1 == var0 && var4.unit2 == var2 && var4.power1 == var1 && var4.power2 == var3) {
            return var4;
         }
      }

      return null;
   }

   public static MulUnit make(Unit var0, int var1, Unit var2, int var3) {
      MulUnit var4 = lookup(var0, var1, var2, var3);
      return var4 != null?var4:new MulUnit(var0, var1, var2, var3);
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.unit1 = (Unit)var1.readObject();
      this.power1 = var1.readInt();
      this.unit2 = (Unit)var1.readObject();
      this.power2 = var1.readInt();
   }

   public Object readResolve() throws ObjectStreamException {
      MulUnit var1 = lookup(this.unit1, this.power1, this.unit2, this.power2);
      return var1 != null?var1:this;
   }

   public Unit sqrt() {
      return (this.power1 & 1) == 0 && (this.power2 & 1) == 0?times(this.unit1, this.power1 >> 1, this.unit2, this.power2 >> 1):super.sqrt();
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer(60);
      var1.append(this.unit1);
      if(this.power1 != 1) {
         var1.append('^');
         var1.append(this.power1);
      }

      if(this.power2 != 0) {
         var1.append('*');
         var1.append(this.unit2);
         if(this.power2 != 1) {
            var1.append('^');
            var1.append(this.power2);
         }
      }

      return var1.toString();
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.unit1);
      var1.writeInt(this.power1);
      var1.writeObject(this.unit2);
      var1.writeInt(this.power2);
   }
}
