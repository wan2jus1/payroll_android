package gnu.kawa.xml;

import gnu.kawa.xml.XIntegerType;
import gnu.math.IntNum;

public class XInteger extends IntNum {

   private XIntegerType type;


   XInteger(IntNum var1, XIntegerType var2) {
      this.words = var1.words;
      this.ival = var1.ival;
      this.type = var2;
   }

   public XIntegerType getIntegerType() {
      return this.type;
   }
}
