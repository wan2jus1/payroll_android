package gnu.kawa.xml;

import gnu.kawa.xml.KCharacterData;
import gnu.xml.NodeTree;
import org.w3c.dom.DOMException;
import org.w3c.dom.Text;

public class KText extends KCharacterData implements Text {

   public KText(NodeTree var1, int var2) {
      super(var1, var2);
   }

   public static KText make(String var0) {
      NodeTree var1 = new NodeTree();
      var1.append(var0);
      return new KText(var1, 0);
   }

   public String getNodeName() {
      return "#text";
   }

   public short getNodeType() {
      return (short)3;
   }

   public String getWholeText() {
      throw new UnsupportedOperationException("getWholeText not implemented yet");
   }

   public boolean hasAttributes() {
      return false;
   }

   public boolean isElementContentWhitespace() {
      return false;
   }

   public Text replaceWholeText(String var1) throws DOMException {
      throw new DOMException((short)7, "splitText not supported");
   }

   public Text splitText(int var1) throws DOMException {
      throw new DOMException((short)7, "splitText not supported");
   }
}
