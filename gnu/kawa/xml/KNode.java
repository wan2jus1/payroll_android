package gnu.kawa.xml;

import gnu.kawa.xml.KAttr;
import gnu.kawa.xml.KCDATASection;
import gnu.kawa.xml.KComment;
import gnu.kawa.xml.KDocument;
import gnu.kawa.xml.KElement;
import gnu.kawa.xml.KProcessingInstruction;
import gnu.kawa.xml.KText;
import gnu.kawa.xml.SortedNodes;
import gnu.lists.AbstractSequence;
import gnu.lists.Consumable;
import gnu.lists.Consumer;
import gnu.lists.PositionConsumer;
import gnu.lists.SeqPosition;
import gnu.lists.TreePosition;
import gnu.mapping.CharArrayOutPort;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import gnu.text.Path;
import gnu.xml.NodeTree;
import gnu.xml.XMLPrinter;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

public abstract class KNode extends SeqPosition implements Node, Consumable {

   public KNode(NodeTree var1, int var2) {
      super(var1, var2);
   }

   public static Object atomicValue(Object var0) {
      Object var1 = var0;
      if(var0 instanceof KNode) {
         KNode var2 = (KNode)var0;
         var1 = ((NodeTree)var2.sequence).typedValue(var2.ipos);
      }

      return var1;
   }

   public static KNode coerce(Object var0) {
      if(var0 instanceof KNode) {
         return (KNode)var0;
      } else if(var0 instanceof NodeTree) {
         NodeTree var2 = (NodeTree)var0;
         return make(var2, var2.startPos());
      } else {
         if(var0 instanceof SeqPosition && !(var0 instanceof TreePosition)) {
            SeqPosition var1 = (SeqPosition)var0;
            if(var1.sequence instanceof NodeTree) {
               return make((NodeTree)var1.sequence, var1.ipos);
            }
         }

         return null;
      }
   }

   public static String getNodeValue(NodeTree var0, int var1) {
      StringBuffer var2 = new StringBuffer();
      getNodeValue(var0, var1, var2);
      return var2.toString();
   }

   public static void getNodeValue(NodeTree var0, int var1, StringBuffer var2) {
      var0.stringValue(var0.posToDataIndex(var1), var2);
   }

   public static KNode make(NodeTree var0) {
      return make(var0, 0);
   }

   public static KNode make(NodeTree var0, int var1) {
      int var3 = var0.posToDataIndex(var1);
      int var2 = var1;

      for(var1 = var3; var1 < var0.data.length && var0.data[var1] == '\uf112'; var2 = var1 << 1) {
         var2 = var1 + 5;
         var1 = var2;
         if(var2 == var0.gapStart) {
            var1 = var0.gapEnd;
         }
      }

      switch(var0.getNextKindI(var0.posToDataIndex(var2))) {
      case 0:
         if(!var0.isEmpty()) {
            return null;
         }
      case 31:
         return new KCDATASection(var0, var2);
      case 33:
         return new KElement(var0, var2);
      case 34:
         return new KDocument(var0, var2);
      case 35:
         return new KAttr(var0, var2);
      case 36:
         return new KComment(var0, var2);
      case 37:
         return new KProcessingInstruction(var0, var2);
      default:
         return new KText(var0, var2);
      }
   }

   public Node appendChild(Node var1) throws DOMException {
      throw new DOMException((short)7, "appendChild not supported");
   }

   public Path baseURI() {
      return ((NodeTree)this.sequence).baseUriOfPos(this.ipos, true);
   }

   public Node cloneNode(boolean var1) {
      if(!var1) {
         throw new UnsupportedOperationException("shallow cloneNode not implemented");
      } else {
         NodeTree var2 = new NodeTree();
         ((NodeTree)this.sequence).consumeNext(this.ipos, var2);
         return make(var2);
      }
   }

   public short compareDocumentPosition(Node var1) throws DOMException {
      if(!(var1 instanceof KNode)) {
         throw new DOMException((short)9, "other Node is a " + var1.getClass().getName());
      } else {
         KNode var4 = (KNode)var1;
         AbstractSequence var2 = var4.sequence;
         int var3;
         if(this.sequence == var2) {
            var3 = var2.compare(this.ipos, var4.ipos);
         } else {
            var3 = this.sequence.stableCompare(var2);
         }

         return (short)var3;
      }
   }

   public void consume(Consumer var1) {
      if(var1 instanceof PositionConsumer) {
         ((PositionConsumer)var1).consume(this);
      } else {
         ((NodeTree)this.sequence).consumeNext(this.ipos, var1);
      }
   }

   public KNode copy() {
      return make((NodeTree)this.sequence, this.sequence.copyPos(this.getPos()));
   }

   public NamedNodeMap getAttributes() {
      throw new UnsupportedOperationException("getAttributes not implemented yet");
   }

   public String getBaseURI() {
      Path var1 = ((NodeTree)this.sequence).baseUriOfPos(this.ipos, true);
      return var1 == null?null:var1.toString();
   }

   public NodeList getChildNodes() {
      SortedNodes var1 = new SortedNodes();

      for(int var2 = this.sequence.firstChildPos(this.ipos); var2 != 0; var2 = this.sequence.nextPos(var2)) {
         var1.writePosition(this.sequence, var2);
      }

      return var1;
   }

   public NodeList getElementsByTagName(String var1) {
      throw new UnsupportedOperationException("getElementsByTagName not implemented yet");
   }

   public Object getFeature(String var1, String var2) {
      return null;
   }

   public Node getFirstChild() {
      int var1 = ((NodeTree)this.sequence).posFirstChild(this.ipos);
      return make((NodeTree)this.sequence, var1);
   }

   public Node getLastChild() {
      int var2 = 0;

      for(int var1 = this.sequence.firstChildPos(this.ipos); var1 != 0; var1 = this.sequence.nextPos(var1)) {
         var2 = var1;
      }

      return var2 == 0?null:make((NodeTree)this.sequence, var2);
   }

   public String getLocalName() {
      return ((NodeTree)this.sequence).posLocalName(this.ipos);
   }

   public String getNamespaceURI() {
      return ((NodeTree)this.sequence).posNamespaceURI(this.ipos);
   }

   public Node getNextSibling() {
      int var1 = ((NodeTree)this.sequence).nextPos(this.ipos);
      return var1 == 0?null:make((NodeTree)this.sequence, var1);
   }

   public String getNodeName() {
      return this.sequence.getNextTypeName(this.ipos);
   }

   public Object getNodeNameObject() {
      return ((NodeTree)this.sequence).getNextTypeObject(this.ipos);
   }

   public Symbol getNodeSymbol() {
      Object var1 = ((NodeTree)this.sequence).getNextTypeObject(this.ipos);
      return var1 == null?null:(var1 instanceof Symbol?(Symbol)var1:Namespace.EmptyNamespace.getSymbol(var1.toString().intern()));
   }

   public abstract short getNodeType();

   public String getNodeValue() {
      StringBuffer var1 = new StringBuffer();
      this.getNodeValue(var1);
      return var1.toString();
   }

   public void getNodeValue(StringBuffer var1) {
      getNodeValue((NodeTree)this.sequence, this.ipos, var1);
   }

   public org.w3c.dom.Document getOwnerDocument() {
      return this.sequence.getNextKind(this.ipos) == 34?new KDocument((NodeTree)this.sequence, 0):null;
   }

   public Node getParentNode() {
      int var1 = this.sequence.parentPos(this.ipos);
      return var1 == -1?null:make((NodeTree)this.sequence, var1);
   }

   public String getPrefix() {
      return ((NodeTree)this.sequence).posPrefix(this.ipos);
   }

   public Node getPreviousSibling() {
      int var2 = this.sequence.parentPos(this.ipos);
      int var1 = var2;
      if(var2 == -1) {
         var1 = 0;
      }

      int var4 = ((NodeTree)this.sequence).posToDataIndex(this.ipos);
      var1 = this.sequence.firstChildPos(var1);

      int var3;
      do {
         var3 = this.sequence.nextPos(var1);
         if(var3 == 0) {
            break;
         }

         var1 = var3;
      } while(((NodeTree)this.sequence).posToDataIndex(var3) != var4);

      return var1 == 0?null:make((NodeTree)this.sequence, var1);
   }

   public String getTextContent() {
      StringBuffer var1 = new StringBuffer();
      this.getTextContent(var1);
      return var1.toString();
   }

   protected void getTextContent(StringBuffer var1) {
      this.getNodeValue(var1);
   }

   public Object getUserData(String var1) {
      return null;
   }

   public boolean hasAttributes() {
      return false;
   }

   public boolean hasChildNodes() {
      return ((NodeTree)this.sequence).posFirstChild(this.ipos) >= 0;
   }

   public Node insertBefore(Node var1, Node var2) throws DOMException {
      throw new DOMException((short)7, "insertBefore not supported");
   }

   public boolean isDefaultNamespace(String var1) {
      return ((NodeTree)this.sequence).posIsDefaultNamespace(this.ipos, var1);
   }

   public boolean isEqualNode(Node var1) {
      throw new UnsupportedOperationException("getAttributesisEqualNode not implemented yet");
   }

   public boolean isSameNode(Node var1) {
      if(var1 instanceof KNode) {
         KNode var2 = (KNode)var1;
         if(this.sequence == var2.sequence) {
            return this.sequence.equals(this.ipos, var2.ipos);
         }
      }

      return false;
   }

   public boolean isSupported(String var1, String var2) {
      return false;
   }

   public String lookupNamespaceURI(String var1) {
      return ((NodeTree)this.sequence).posLookupNamespaceURI(this.ipos, var1);
   }

   public String lookupPrefix(String var1) {
      return ((NodeTree)this.sequence).posLookupPrefix(this.ipos, var1);
   }

   public void normalize() {
   }

   public Node removeChild(Node var1) throws DOMException {
      throw new DOMException((short)7, "removeChild not supported");
   }

   public Node replaceChild(Node var1, Node var2) throws DOMException {
      throw new DOMException((short)7, "replaceChild not supported");
   }

   public void setNodeValue(String var1) throws DOMException {
      throw new DOMException((short)7, "setNodeValue not supported");
   }

   public void setPrefix(String var1) throws DOMException {
      throw new DOMException((short)7, "setPrefix not supported");
   }

   public void setTextContent(String var1) throws DOMException {
      throw new DOMException((short)7, "setTextContent not supported");
   }

   public Object setUserData(String var1, Object var2, UserDataHandler var3) {
      throw new UnsupportedOperationException("setUserData not implemented yet");
   }

   public String toString() {
      CharArrayOutPort var1 = new CharArrayOutPort();
      XMLPrinter var2 = new XMLPrinter(var1);
      ((NodeTree)this.sequence).consumeNext(this.ipos, var2);
      var2.close();
      var1.close();
      return var1.toString();
   }
}
