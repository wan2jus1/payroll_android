package gnu.kawa.xml;

import gnu.kawa.xml.KText;
import gnu.xml.NodeTree;
import org.w3c.dom.CDATASection;

public class KCDATASection extends KText implements CDATASection {

   public KCDATASection(NodeTree var1, int var2) {
      super(var1, var2);
   }

   public String getData() {
      return this.getNodeValue();
   }

   public int getLength() {
      StringBuffer var1 = new StringBuffer();
      NodeTree var2 = (NodeTree)this.sequence;
      var2.stringValue(var2.posToDataIndex(this.ipos), var1);
      return var1.length();
   }

   public String getNodeName() {
      return "#cdata-section";
   }

   public short getNodeType() {
      return (short)4;
   }
}
