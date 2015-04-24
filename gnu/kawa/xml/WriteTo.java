package gnu.kawa.xml;

import gnu.mapping.OutPort;
import gnu.mapping.Procedure2;
import gnu.mapping.Values;
import gnu.text.Path;
import gnu.xml.XMLPrinter;
import java.io.OutputStream;

public class WriteTo extends Procedure2 {

   public static final WriteTo writeTo = new WriteTo();
   public static final WriteTo writeToIfChanged = new WriteTo();
   boolean ifChanged;


   static {
      writeToIfChanged.ifChanged = true;
   }

   public static void writeTo(Object var0, Path var1, OutputStream var2) throws Throwable {
      OutPort var4 = new OutPort(var2, var1);
      XMLPrinter var3 = new XMLPrinter(var4, false);
      if("html".equals(var1.getExtension())) {
         var3.setStyle("html");
      }

      Values.writeValues(var0, var3);
      var4.close();
   }

   public static void writeTo(Object var0, Object var1) throws Throwable {
      Path var2 = Path.valueOf(var1);
      writeTo(var0, var2, var2.openOutputStream());
   }

   public static void writeToIfChanged(Object param0, Object param1) throws Throwable {
      // $FF: Couldn't be decompiled
   }

   public Object apply2(Object var1, Object var2) throws Throwable {
      if(this.ifChanged) {
         writeToIfChanged(var1, var2.toString());
      } else {
         writeTo(var1, var2.toString());
      }

      return Values.empty;
   }
}
