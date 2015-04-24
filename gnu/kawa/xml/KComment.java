package gnu.kawa.xml;

import gnu.kawa.xml.KCharacterData;
import gnu.xml.NodeTree;
import org.w3c.dom.Comment;

public class KComment extends KCharacterData implements Comment {

   public KComment(NodeTree var1, int var2) {
      super(var1, var2);
   }

   public static KComment valueOf(String var0) {
      NodeTree var1 = new NodeTree();
      var1.writeComment(var0, 0, var0.length());
      return new KComment(var1, 0);
   }

   public String getNodeName() {
      return "#comment";
   }

   public short getNodeType() {
      return (short)8;
   }
}
