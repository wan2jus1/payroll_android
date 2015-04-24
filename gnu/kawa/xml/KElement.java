package gnu.kawa.xml;

import gnu.kawa.xml.KAttr;
import gnu.kawa.xml.KNode;
import gnu.xml.NodeTree;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;

public class KElement extends KNode implements Element {

   public KElement(NodeTree var1, int var2) {
      super(var1, var2);
   }

   public String getAttribute(String var1) {
      String var2 = var1;
      if(var1 == null) {
         var2 = "";
      }

      NodeTree var4 = (NodeTree)this.sequence;
      int var3 = var4.getAttribute(this.ipos, (String)null, var2);
      return var3 == 0?"":KNode.getNodeValue(var4, var3);
   }

   public String getAttributeNS(String var1, String var2) {
      String var3 = var1;
      if(var1 == null) {
         var3 = "";
      }

      var1 = var2;
      if(var2 == null) {
         var1 = "";
      }

      NodeTree var5 = (NodeTree)this.sequence;
      int var4 = var5.getAttribute(this.ipos, var3, var1);
      return var4 == 0?"":getNodeValue(var5, var4);
   }

   public KAttr getAttributeNode(String var1) {
      String var2 = var1;
      if(var1 == null) {
         var2 = "";
      }

      NodeTree var4 = (NodeTree)this.sequence;
      int var3 = var4.getAttribute(this.ipos, (String)null, var2);
      return var3 == 0?null:new KAttr(var4, var3);
   }

   public KAttr getAttributeNodeNS(String var1, String var2) {
      String var3 = var1;
      if(var1 == null) {
         var3 = "";
      }

      var1 = var2;
      if(var2 == null) {
         var1 = "";
      }

      NodeTree var5 = (NodeTree)this.sequence;
      int var4 = var5.getAttribute(this.ipos, var3, var1);
      return var4 == 0?null:new KAttr(var5, var4);
   }

   public NodeList getElementsByTagNameNS(String var1, String var2) {
      throw new UnsupportedOperationException("getElementsByTagNameNS not implemented yet");
   }

   public short getNodeType() {
      return (short)1;
   }

   public String getNodeValue() {
      return null;
   }

   public TypeInfo getSchemaTypeInfo() {
      return null;
   }

   public String getTagName() {
      return this.sequence.getNextTypeName(this.ipos);
   }

   public boolean hasAttribute(String var1) {
      NodeTree var3 = (NodeTree)this.sequence;
      int var4 = this.ipos;
      String var2 = var1;
      if(var1 == null) {
         var2 = "";
      }

      return var3.getAttribute(var4, (String)null, var2) != 0;
   }

   public boolean hasAttributeNS(String var1, String var2) {
      String var3 = var1;
      if(var1 == null) {
         var3 = "";
      }

      var1 = var2;
      if(var2 == null) {
         var1 = "";
      }

      return ((NodeTree)this.sequence).getAttribute(this.ipos, var3, var1) != 0;
   }

   public boolean hasAttributes() {
      return ((NodeTree)this.sequence).posHasAttributes(this.ipos);
   }

   public void removeAttribute(String var1) throws DOMException {
      throw new DOMException((short)7, "removeAttribute not supported");
   }

   public void removeAttributeNS(String var1, String var2) throws DOMException {
      throw new DOMException((short)7, "removeAttributeNS not supported");
   }

   public Attr removeAttributeNode(Attr var1) throws DOMException {
      throw new DOMException((short)7, "removeAttributeNode not supported");
   }

   public void setAttribute(String var1, String var2) throws DOMException {
      throw new DOMException((short)7, "setAttribute not supported");
   }

   public void setAttributeNS(String var1, String var2, String var3) throws DOMException {
      throw new DOMException((short)7, "setAttributeNS not supported");
   }

   public Attr setAttributeNode(Attr var1) throws DOMException {
      throw new DOMException((short)7, "setAttributeNode not supported");
   }

   public Attr setAttributeNodeNS(Attr var1) throws DOMException {
      throw new DOMException((short)7, "setAttributeNodeNS not supported");
   }

   public void setIdAttribute(String var1, boolean var2) throws DOMException {
      throw new DOMException((short)7, "setIdAttribute not supported");
   }

   public void setIdAttributeNS(String var1, String var2, boolean var3) throws DOMException {
      throw new DOMException((short)7, "setIdAttributeNS not supported");
   }

   public void setIdAttributeNode(Attr var1, boolean var2) throws DOMException {
      throw new DOMException((short)7, "setIdAttributeNode not supported");
   }
}
