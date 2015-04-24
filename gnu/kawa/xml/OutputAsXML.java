package gnu.kawa.xml;

import gnu.lists.FString;
import gnu.mapping.CharArrayOutPort;
import gnu.mapping.Procedure1;
import gnu.xml.XMLPrinter;

public class OutputAsXML extends Procedure1 {

   public Object apply1(Object var1) {
      CharArrayOutPort var2 = new CharArrayOutPort();
      XMLPrinter var3 = new XMLPrinter(var2);
      var3.writeObject(var1);
      var3.flush();
      return new FString(var2.toCharArray());
   }

   public int numArgs() {
      return 4097;
   }
}
