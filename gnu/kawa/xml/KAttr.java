package gnu.kawa.xml;

import gnu.kawa.xml.KNode;
import gnu.xml.NodeTree;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.TypeInfo;

public class KAttr extends KNode implements Attr {

   public KAttr(NodeTree var1, int var2) {
      super(var1, var2);
   }

   public static Object getObjectValue(NodeTree var0, int var1) {
      return var0.getPosNext(var1 + 10);
   }

   public String getName() {
      return this.sequence.getNextTypeName(this.ipos);
   }

   public short getNodeType() {
      return (short)2;
   }

   public Object getObjectValue() {
      return getObjectValue((NodeTree)this.sequence, this.ipos);
   }

   public Element getOwnerElement() {
      return (Element)super.getParentNode();
   }

   public Node getParentNode() {
      return null;
   }

   public TypeInfo getSchemaTypeInfo() {
      return null;
   }

   public boolean getSpecified() {
      return true;
   }

   public String getValue() {
      return this.getNodeValue();
   }

   public boolean isId() {
      return false;
   }

   public void setValue(String var1) throws DOMException {
      throw new DOMException((short)7, "setValue not supported");
   }
}
