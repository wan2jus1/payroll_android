package gnu.mapping;

import gnu.mapping.ConstrainedLocation;
import gnu.mapping.HasSetter;
import gnu.mapping.Location;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure0or1;
import gnu.mapping.Setter0;
import gnu.mapping.Symbol;
import gnu.mapping.Values;

public class LocationProc extends Procedure0or1 implements HasSetter {

   Location loc;


   public LocationProc(Location var1) {
      this.loc = var1;
   }

   public LocationProc(Location var1, Procedure var2) {
      this.loc = var1;
      if(var2 != null) {
         this.pushConverter(var2);
      }

   }

   public static LocationProc makeNamed(Symbol var0, Location var1) {
      LocationProc var2 = new LocationProc(var1);
      var2.setSymbol(var0);
      return var2;
   }

   public Object apply0() throws Throwable {
      return this.loc.get();
   }

   public Object apply1(Object var1) throws Throwable {
      this.set0(var1);
      return Values.empty;
   }

   public final Location getLocation() {
      return this.loc;
   }

   public Procedure getSetter() {
      return new Setter0(this);
   }

   public void pushConverter(Procedure var1) {
      this.loc = ConstrainedLocation.make(this.loc, var1);
   }

   public void set0(Object var1) throws Throwable {
      this.loc.set(var1);
   }

   public String toString() {
      return this.getSymbol() != null?super.toString():"#<location-proc " + this.loc + ">";
   }
}
