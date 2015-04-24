package gnu.kawa.xml;

import gnu.kawa.xml.KNode;
import gnu.xml.NodeTree;
import org.w3c.dom.CharacterData;
import org.w3c.dom.DOMException;

public abstract class KCharacterData extends KNode implements CharacterData {

   public KCharacterData(NodeTree var1, int var2) {
      super(var1, var2);
   }

   public void appendData(String var1) throws DOMException {
      throw new DOMException((short)7, "appendData not supported");
   }

   public void deleteData(int var1, int var2) throws DOMException {
      this.replaceData(var1, var2, "");
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

   public void insertData(int var1, String var2) throws DOMException {
      this.replaceData(var1, 0, var2);
   }

   public void replaceData(int var1, int var2, String var3) throws DOMException {
      throw new DOMException((short)7, "replaceData not supported");
   }

   public void setData(String var1) throws DOMException {
      throw new DOMException((short)7, "setData not supported");
   }

   public String substringData(int var1, int var2) throws DOMException {
      String var3 = this.getData();
      if(var1 >= 0 && var2 >= 0 && var1 + var2 < var3.length()) {
         return var3.substring(var1, var2);
      } else {
         throw new DOMException((short)1, "invalid index to substringData");
      }
   }
}
