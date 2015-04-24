package gnu.kawa.xml;

import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;

public class MakeResponseHeader extends MethodProc {

   public static MakeResponseHeader makeResponseHeader = new MakeResponseHeader();


   public void apply(CallContext var1) {
      String var2 = var1.getNextArg().toString();
      Object var3 = var1.getNextArg();
      var1.lastArg();
      Consumer var4 = var1.consumer;
      var4.startAttribute(var2);
      var4.write(var3.toString());
      var4.endAttribute();
   }
}
