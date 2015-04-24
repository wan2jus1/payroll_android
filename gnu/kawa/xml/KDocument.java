package gnu.kawa.xml;

import gnu.kawa.xml.KElement;
import gnu.kawa.xml.KNode;
import gnu.xml.NodeTree;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

public class KDocument extends KNode implements org.w3c.dom.Document {

   public KDocument(NodeTree var1, int var2) {
      super(var1, var2);
   }

   public Node adoptNode(Node var1) throws DOMException {
      throw new DOMException((short)9, "adoptNode not implemented");
   }

   public Attr createAttribute(String var1) {
      throw new UnsupportedOperationException("createAttribute not implemented");
   }

   public Attr createAttributeNS(String var1, String var2) {
      throw new UnsupportedOperationException("createAttributeNS not implemented");
   }

   public CDATASection createCDATASection(String var1) {
      throw new UnsupportedOperationException("createCDATASection not implemented");
   }

   public Comment createComment(String var1) {
      throw new UnsupportedOperationException("createComment not implemented");
   }

   public DocumentFragment createDocumentFragment() {
      throw new UnsupportedOperationException("createDocumentFragment not implemented");
   }

   public Element createElement(String var1) {
      throw new UnsupportedOperationException("createElement not implemented");
   }

   public Element createElementNS(String var1, String var2) {
      throw new UnsupportedOperationException("createElementNS not implemented");
   }

   public EntityReference createEntityReference(String var1) {
      throw new UnsupportedOperationException("createEntityReference implemented");
   }

   public ProcessingInstruction createProcessingInstruction(String var1, String var2) {
      throw new UnsupportedOperationException("createProcessingInstruction not implemented");
   }

   public Text createTextNode(String var1) {
      throw new UnsupportedOperationException("createTextNode not implemented");
   }

   public DocumentType getDoctype() {
      return null;
   }

   public KElement getDocumentElement() {
      for(int var1 = ((NodeTree)this.sequence).posFirstChild(this.ipos); var1 != -1; var1 = this.sequence.nextPos(var1)) {
         if(this.sequence.getNextKind(var1) != 36) {
            return (KElement)make((NodeTree)this.sequence, var1);
         }
      }

      return null;
   }

   public String getDocumentURI() {
      return null;
   }

   public DOMConfiguration getDomConfig() {
      throw new DOMException((short)9, "getDomConfig not implemented");
   }

   public Element getElementById(String var1) {
      return null;
   }

   public NodeList getElementsByTagNameNS(String var1, String var2) {
      throw new UnsupportedOperationException("getElementsByTagNameNS not implemented yet");
   }

   public DOMImplementation getImplementation() {
      throw new UnsupportedOperationException("getImplementation not implemented");
   }

   public String getInputEncoding() {
      return null;
   }

   public String getNodeName() {
      return "#document";
   }

   public short getNodeType() {
      return (short)9;
   }

   public String getNodeValue() {
      return null;
   }

   public Node getParentNode() {
      return null;
   }

   public boolean getStrictErrorChecking() {
      return false;
   }

   public String getTextContent() {
      return null;
   }

   protected void getTextContent(StringBuffer var1) {
   }

   public String getXmlEncoding() {
      return null;
   }

   public boolean getXmlStandalone() {
      return false;
   }

   public String getXmlVersion() {
      return "1.1";
   }

   public boolean hasAttributes() {
      return false;
   }

   public Node importNode(Node var1, boolean var2) {
      throw new UnsupportedOperationException("importNode not implemented");
   }

   public void normalizeDocument() {
   }

   public Node renameNode(Node var1, String var2, String var3) throws DOMException {
      throw new DOMException((short)9, "renameNode not implemented");
   }

   public void setDocumentURI(String var1) {
   }

   public void setStrictErrorChecking(boolean var1) {
   }

   public void setXmlStandalone(boolean var1) {
   }

   public void setXmlVersion(String var1) {
   }
}
