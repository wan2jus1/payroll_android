package gnu.math;

import gnu.math.Complex;
import gnu.math.RealNum;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class CComplex extends Complex implements Externalizable {

   RealNum imag;
   RealNum real;


   public CComplex() {
   }

   public CComplex(RealNum var1, RealNum var2) {
      this.real = var1;
      this.imag = var2;
   }

   public RealNum im() {
      return this.imag;
   }

   public RealNum re() {
      return this.real;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.real = (RealNum)var1.readObject();
      this.imag = (RealNum)var1.readObject();
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.real);
      var1.writeObject(this.imag);
   }
}
