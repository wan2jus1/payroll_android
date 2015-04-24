package kawa.lang;

import gnu.lists.Consumer;
import gnu.lists.Pair;
import gnu.text.Printable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import kawa.lang.Pattern;

public class PairPat extends Pattern implements Printable, Externalizable {

   Pattern car;
   private int car_count;
   Pattern cdr;
   private int cdr_count;


   public PairPat() {
   }

   public PairPat(Pattern var1, Pattern var2) {
      this.car = var1;
      this.cdr = var2;
      this.car_count = var1.varCount();
      this.cdr_count = var2.varCount();
   }

   public static PairPat make(Pattern var0, Pattern var1) {
      return new PairPat(var0, var1);
   }

   public boolean match(Object var1, Object[] var2, int var3) {
      if(var1 instanceof Pair) {
         Pair var4 = (Pair)var1;
         if(this.car.match(var4.getCar(), var2, var3) && this.cdr.match(var4.getCdr(), var2, this.car_count + var3)) {
            return true;
         }
      }

      return false;
   }

   public void print(Consumer var1) {
      var1.write("#<pair-pattern car: ");
      this.car.print(var1);
      var1.write(" cdr: ");
      this.cdr.print(var1);
      var1.write(62);
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.car = (Pattern)var1.readObject();
      this.cdr = (Pattern)var1.readObject();
   }

   public int varCount() {
      return this.car_count + this.cdr_count;
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.car);
      var1.writeObject(this.cdr);
   }
}
