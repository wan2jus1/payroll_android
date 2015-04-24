package gnu.math;

import gnu.math.CComplex;
import gnu.math.Complex;
import gnu.math.Quantity;
import gnu.math.RealNum;
import gnu.math.Unit;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class CQuantity extends Quantity implements Externalizable {

   Complex num;
   Unit unt;


   public CQuantity(Complex var1, Unit var2) {
      this.num = var1;
      this.unt = var2;
   }

   public CQuantity(RealNum var1, RealNum var2, Unit var3) {
      this.num = new CComplex(var1, var2);
      this.unt = var3;
   }

   public boolean isExact() {
      return this.num.isExact();
   }

   public boolean isZero() {
      return this.num.isZero();
   }

   public Complex number() {
      return this.num;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.num = (Complex)var1.readObject();
      this.unt = (Unit)var1.readObject();
   }

   public Unit unit() {
      return this.unt;
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.num);
      var1.writeObject(this.unt);
   }
}
