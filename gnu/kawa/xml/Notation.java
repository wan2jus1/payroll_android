package gnu.kawa.xml;

import gnu.mapping.Symbol;

public class Notation {

   Symbol qname;


   public boolean equals(Notation var1, Notation var2) {
      return var1.qname.equals(var2.qname);
   }

   public boolean equals(Object var1) {
      return var1 instanceof Notation && this.equals(this, (Notation)var1);
   }

   public int hashCode() {
      return this.qname.hashCode();
   }
}
