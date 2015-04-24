package gnu.kawa.xml;

import gnu.kawa.xml.ElementType;
import gnu.mapping.Namespace;
import gnu.xml.NamespaceBinding;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

public class XmlNamespace extends Namespace implements Externalizable {

   public static final XmlNamespace HTML = valueOf("http://www.w3.org/1999/xhtml", "");
   public static final NamespaceBinding HTML_BINDINGS = new NamespaceBinding((String)null, "http://www.w3.org/1999/xhtml", NamespaceBinding.predefinedXML);
   public static final String XHTML_NAMESPACE = "http://www.w3.org/1999/xhtml";


   public static XmlNamespace getInstance(String param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   public static XmlNamespace valueOf(String var0, String var1) {
      return getInstance(var1, var0);
   }

   public Object get(String var1) {
      ElementType var2 = ElementType.make(this.getSymbol(var1));
      if(this == HTML) {
         var2.setNamespaceNodes(HTML_BINDINGS);
      }

      return var2;
   }

   public boolean isConstant(String var1) {
      return true;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.setName((String)var1.readObject());
      this.prefix = (String)var1.readObject();
   }

   public Object readResolve() throws ObjectStreamException {
      String var1 = this.prefix + " -> " + this.getName();
      Namespace var2 = (Namespace)nsTable.get(var1);
      if(var2 instanceof XmlNamespace) {
         return var2;
      } else {
         nsTable.put(var1, this);
         return this;
      }
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.getName());
      var1.writeObject(this.prefix);
   }
}
