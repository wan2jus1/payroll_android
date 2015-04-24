package gnu.math;

import gnu.math.DQuantity;
import gnu.math.Quantity;
import gnu.math.Unit;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

public class NamedUnit extends Unit implements Externalizable {

   Unit base;
   NamedUnit chain;
   String name;
   double scale;


   public NamedUnit() {
   }

   public NamedUnit(String var1, double var2, Unit var4) {
      this.name = var1;
      this.base = var4;
      this.scale = var2;
      this.init();
   }

   public NamedUnit(String var1, DQuantity var2) {
      this.name = var1.intern();
      this.scale = var2.factor;
      this.base = var2.unt;
      this.init();
   }

   public static NamedUnit lookup(String var0) {
      String var1 = var0.intern();
      int var2 = var1.hashCode();
      int var3 = table.length;

      for(NamedUnit var4 = table[(Integer.MAX_VALUE & var2) % var3]; var4 != null; var4 = var4.chain) {
         if(var4.name == var1) {
            return var4;
         }
      }

      return null;
   }

   public static NamedUnit lookup(String var0, double var1, Unit var3) {
      String var4 = var0.intern();
      int var5 = var4.hashCode();
      int var6 = table.length;

      for(NamedUnit var7 = table[(Integer.MAX_VALUE & var5) % var6]; var7 != null; var7 = var7.chain) {
         if(var7.name == var4 && var7.scale == var1 && var7.base == var3) {
            return var7;
         }
      }

      return null;
   }

   public static NamedUnit make(String var0, double var1, Unit var3) {
      NamedUnit var5 = lookup(var0, var1, var3);
      NamedUnit var4 = var5;
      if(var5 == null) {
         var4 = new NamedUnit(var0, var1, var3);
      }

      return var4;
   }

   public static NamedUnit make(String var0, Quantity var1) {
      double var2;
      if(var1 instanceof DQuantity) {
         var2 = ((DQuantity)var1).factor;
      } else {
         if(var1.imValue() != 0.0D) {
            throw new ArithmeticException("defining " + var0 + " using complex value");
         }

         var2 = var1.re().doubleValue();
      }

      Unit var5 = var1.unit();
      NamedUnit var4 = lookup(var0, var2, var5);
      NamedUnit var6 = var4;
      if(var4 == null) {
         var6 = new NamedUnit(var0, var2, var5);
      }

      return var6;
   }

   public String getName() {
      return this.name;
   }

   protected void init() {
      this.factor = this.scale * this.base.factor;
      this.dims = this.base.dims;
      this.name = this.name.intern();
      int var1 = (Integer.MAX_VALUE & this.name.hashCode()) % table.length;
      this.chain = table[var1];
      table[var1] = this;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.name = var1.readUTF();
      this.scale = var1.readDouble();
      this.base = (Unit)var1.readObject();
   }

   public Object readResolve() throws ObjectStreamException {
      NamedUnit var1 = lookup(this.name, this.scale, this.base);
      if(var1 != null) {
         return var1;
      } else {
         this.init();
         return this;
      }
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeUTF(this.name);
      var1.writeDouble(this.scale);
      var1.writeObject(this.base);
   }
}
