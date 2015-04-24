package gnu.kawa.xml;

import gnu.lists.UnescapedData;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure1;

public class MakeUnescapedData extends Procedure1 {

   public static final MakeUnescapedData unescapedData = new MakeUnescapedData();


   public MakeUnescapedData() {
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.xml.CompileXmlFunctions:validateApplyMakeUnescapedData");
   }

   public Object apply1(Object var1) {
      String var2;
      if(var1 == null) {
         var2 = "";
      } else {
         var2 = var1.toString();
      }

      return new UnescapedData(var2);
   }
}
