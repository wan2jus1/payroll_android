package gnu.xml;

import gnu.lists.Consumable;
import gnu.lists.Consumer;
import gnu.lists.FilterConsumer;
import gnu.lists.SeqPosition;
import gnu.lists.TreeList;
import gnu.mapping.Symbol;

public class NamedChildrenFilter extends FilterConsumer {

   int level;
   String localName;
   int matchLevel;
   String namespaceURI;


   public NamedChildrenFilter(String var1, String var2, Consumer var3) {
      super(var3);
      this.namespaceURI = var1;
      this.localName = var2;
      this.skipping = true;
   }

   public static NamedChildrenFilter make(String var0, String var1, Consumer var2) {
      return new NamedChildrenFilter(var0, var1, var2);
   }

   public void endDocument() {
      --this.level;
      super.endDocument();
   }

   public void endElement() {
      --this.level;
      super.endElement();
      if(!this.skipping && this.matchLevel == this.level) {
         this.skipping = true;
      }

   }

   public void startDocument() {
      ++this.level;
      super.startDocument();
   }

   public void startElement(Object var1) {
      if(this.skipping && this.level == 1) {
         String var3;
         String var4;
         if(var1 instanceof Symbol) {
            Symbol var2 = (Symbol)var1;
            var3 = var2.getNamespaceURI();
            var4 = var2.getLocalName();
         } else {
            var3 = "";
            var4 = var1.toString().intern();
         }

         if((this.localName == var4 || this.localName == null) && (this.namespaceURI == var3 || this.namespaceURI == null)) {
            this.skipping = false;
            this.matchLevel = this.level;
         }
      }

      super.startElement(var1);
      ++this.level;
   }

   public void writeObject(Object var1) {
      if(var1 instanceof SeqPosition) {
         SeqPosition var2 = (SeqPosition)var1;
         if(var2.sequence instanceof TreeList) {
            ((TreeList)var2.sequence).consumeNext(var2.ipos, this);
            return;
         }
      }

      if(var1 instanceof Consumable) {
         ((Consumable)var1).consume(this);
      } else {
         super.writeObject(var1);
      }
   }
}
