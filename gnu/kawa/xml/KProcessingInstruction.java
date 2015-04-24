package gnu.kawa.xml;

import gnu.kawa.xml.KNode;
import gnu.xml.NodeTree;
import org.w3c.dom.DOMException;
import org.w3c.dom.ProcessingInstruction;

public class KProcessingInstruction extends KNode implements ProcessingInstruction {

   public KProcessingInstruction(NodeTree var1, int var2) {
      super(var1, var2);
   }

   public static KProcessingInstruction valueOf(String var0, String var1) {
      NodeTree var2 = new NodeTree();
      var2.writeProcessingInstruction(var0, var1, 0, var1.length());
      return new KProcessingInstruction(var2, 0);
   }

   public String getData() {
      return this.getNodeValue();
   }

   public String getNodeName() {
      return this.getTarget();
   }

   public short getNodeType() {
      return (short)7;
   }

   public String getTarget() {
      return ((NodeTree)this.sequence).posTarget(this.ipos);
   }

   public void setData(String var1) throws DOMException {
      throw new DOMException((short)7, "setData not supported");
   }
}
