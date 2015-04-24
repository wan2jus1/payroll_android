package gnu.kawa.xml;

import gnu.kawa.xml.KNode;
import gnu.lists.AbstractSequence;
import gnu.lists.Consumer;
import gnu.lists.SeqPosition;
import gnu.lists.Sequence;
import gnu.lists.TreeList;
import gnu.mapping.Values;
import gnu.xml.NodeTree;
import gnu.xml.XMLFilter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Nodes extends Values implements NodeList {

   static final int POS_SIZE = 5;
   int count;
   XMLFilter curFragment;
   NodeTree curNode;
   boolean inAttribute;
   int nesting = 0;


   private void maybeEndNonTextNode() {
      int var1 = this.nesting - 1;
      this.nesting = var1;
      if(var1 == 0) {
         this.finishFragment();
      }

   }

   private void maybeStartNonTextNode() {
      if(this.curFragment != null && this.nesting == 0) {
         this.finishFragment();
      }

      if(this.curFragment == null) {
         this.startFragment();
      }

      ++this.nesting;
   }

   public static KNode root(NodeTree var0, int var1) {
      byte var2;
      if(var0.gapStart > 5 && var0.data[0] == '\uf112') {
         var2 = 10;
      } else {
         var2 = 0;
      }

      return KNode.make(var0, var2);
   }

   public Consumer append(CharSequence var1, int var2, int var3) {
      this.maybeStartTextNode();
      this.curFragment.write((CharSequence)var1, var2, var3);
      return this;
   }

   public void beginEntity(Object var1) {
      this.maybeStartNonTextNode();
      this.curFragment.beginEntity(var1);
   }

   public void endAttribute() {
      if(this.inAttribute) {
         this.inAttribute = false;
         this.curFragment.endAttribute();
         this.maybeEndNonTextNode();
      }
   }

   public void endDocument() {
      this.curFragment.endDocument();
      this.maybeEndNonTextNode();
   }

   public void endElement() {
      this.curFragment.endElement();
      this.maybeEndNonTextNode();
   }

   public void endEntity() {
      this.curFragment.endEntity();
      this.maybeEndNonTextNode();
   }

   public int find(Object var1) {
      int var2;
      if(this.gapStart > 0) {
         var2 = this.getIntN(this.gapStart - 5 + 1);
         if(this.objects[var2] == var1) {
            return var2;
         }
      }

      if(this.gapEnd < this.data.length) {
         int var3 = this.getIntN(this.gapEnd + 1);
         var2 = var3;
         if(this.objects[var3] == var1) {
            return var2;
         }
      }

      return super.find(var1);
   }

   void finishFragment() {
      this.curNode = null;
      this.curFragment = null;
   }

   public Object get(int var1) {
      int var2 = var1 * 5;
      var1 = var2;
      if(var2 >= this.gapStart) {
         var1 = var2 + (this.gapEnd - this.gapStart);
      }

      if(var1 >= 0 && var1 < this.data.length) {
         if(this.data[var1] != '\uf10f') {
            throw new RuntimeException("internal error - unexpected data");
         } else {
            return KNode.make((NodeTree)this.objects[this.getIntN(var1 + 1)], this.getIntN(var1 + 3));
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public int getLength() {
      return this.count;
   }

   public int getPos(int var1) {
      int var2 = var1 * 5;
      var1 = var2;
      if(var2 >= this.gapStart) {
         var1 = var2 + (this.gapEnd - this.gapStart);
      }

      if(this.data[var1] != '\uf10f') {
         throw new RuntimeException("internal error - unexpected data");
      } else {
         return this.getIntN(var1 + 3);
      }
   }

   public Object getPosNext(int var1) {
      var1 = this.posToDataIndex(var1);
      if(var1 == this.data.length) {
         return Sequence.eofValue;
      } else if(this.data[var1] != '\uf10f') {
         throw new RuntimeException("internal error - unexpected data");
      } else {
         return KNode.make((NodeTree)this.objects[this.getIntN(var1 + 1)], this.getIntN(var1 + 3));
      }
   }

   public AbstractSequence getSeq(int var1) {
      int var2 = var1 * 5;
      var1 = var2;
      if(var2 >= this.gapStart) {
         var1 = var2 + (this.gapEnd - this.gapStart);
      }

      if(var1 >= 0 && var1 < this.data.length) {
         if(this.data[var1] != '\uf10f') {
            throw new RuntimeException("internal error - unexpected data");
         } else {
            return (AbstractSequence)this.objects[this.getIntN(var1 + 1)];
         }
      } else {
         return null;
      }
   }

   void handleNonNode() {
      if(this.curFragment == null) {
         throw new ClassCastException("atomic value where node is required");
      }
   }

   public Node item(int var1) {
      return var1 >= this.count?null:(Node)this.get(var1);
   }

   void maybeStartTextNode() {
      if(this.curFragment == null) {
         throw new IllegalArgumentException("non-node where node required");
      }
   }

   public int size() {
      return this.count;
   }

   public void startAttribute(Object var1) {
      this.maybeStartNonTextNode();
      this.curFragment.startAttribute(var1);
      this.inAttribute = true;
   }

   public void startDocument() {
      this.maybeStartNonTextNode();
      this.curFragment.startDocument();
   }

   public void startElement(Object var1) {
      this.maybeStartNonTextNode();
      this.curFragment.startElement(var1);
   }

   void startFragment() {
      this.curNode = new NodeTree();
      this.curFragment = new XMLFilter(this.curNode);
      this.writePosition(this.curNode, 0);
   }

   public void write(int var1) {
      this.maybeStartTextNode();
      this.curFragment.write(var1);
   }

   public void write(CharSequence var1, int var2, int var3) {
      this.maybeStartTextNode();
      this.curFragment.write((CharSequence)var1, var2, var3);
   }

   public void write(String var1) {
      this.maybeStartTextNode();
      this.curFragment.write(var1);
   }

   public void write(char[] var1, int var2, int var3) {
      this.maybeStartTextNode();
      this.curFragment.write((char[])var1, var2, var3);
   }

   public void writeBoolean(boolean var1) {
      this.handleNonNode();
      this.curFragment.writeBoolean(var1);
   }

   public void writeCDATA(char[] var1, int var2, int var3) {
      this.maybeStartNonTextNode();
      this.curFragment.writeCDATA(var1, var2, var3);
   }

   public void writeComment(char[] var1, int var2, int var3) {
      this.maybeStartNonTextNode();
      this.curFragment.writeComment(var1, var2, var3);
      this.maybeEndNonTextNode();
   }

   public void writeDouble(double var1) {
      this.handleNonNode();
      this.curFragment.writeDouble(var1);
   }

   public void writeFloat(float var1) {
      this.handleNonNode();
      this.curFragment.writeFloat(var1);
   }

   public void writeInt(int var1) {
      this.handleNonNode();
      this.curFragment.writeInt(var1);
   }

   public void writeLong(long var1) {
      this.handleNonNode();
      this.curFragment.writeLong(var1);
   }

   public void writeObject(Object var1) {
      if(this.curFragment != null) {
         if(this.nesting != 0 || !(var1 instanceof SeqPosition) && !(var1 instanceof TreeList)) {
            this.curFragment.writeObject(var1);
            return;
         }

         this.finishFragment();
      }

      if(var1 instanceof SeqPosition) {
         SeqPosition var2 = (SeqPosition)var1;
         this.writePosition(var2.sequence, var2.ipos);
      } else if(var1 instanceof TreeList) {
         this.writePosition((TreeList)var1, 0);
      } else {
         this.handleNonNode();
         this.curFragment.writeObject(var1);
      }
   }

   public void writePosition(AbstractSequence var1, int var2) {
      ++this.count;
      super.writePosition(var1, var2);
   }

   public void writeProcessingInstruction(String var1, char[] var2, int var3, int var4) {
      this.maybeStartNonTextNode();
      this.curFragment.writeProcessingInstruction(var1, var2, var3, var4);
      this.maybeEndNonTextNode();
   }
}
