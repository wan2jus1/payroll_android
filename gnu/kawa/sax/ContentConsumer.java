package gnu.kawa.sax;

import gnu.lists.Consumable;
import gnu.lists.Consumer;
import gnu.lists.SeqPosition;
import gnu.mapping.Symbol;
import gnu.text.Char;
import gnu.xml.XName;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class ContentConsumer implements Consumer {

   String attrLocalName;
   String attrQName;
   String attrURI;
   AttributesImpl attributes = new AttributesImpl();
   char[] chBuffer;
   int inStartTag;
   String[] names = new String[15];
   int nesting = 0;
   ContentHandler out;
   StringBuilder strBuffer = new StringBuilder(200);


   public ContentConsumer() {
   }

   public ContentConsumer(ContentHandler var1) {
      this.out = var1;
   }

   public ContentConsumer append(char var1) {
      this.write(var1);
      return this;
   }

   public ContentConsumer append(CharSequence var1) {
      Object var2 = var1;
      if(var1 == null) {
         var2 = "null";
      }

      this.write((CharSequence)var2, 0, ((CharSequence)var2).length());
      return this;
   }

   public ContentConsumer append(CharSequence var1, int var2, int var3) {
      Object var4 = var1;
      if(var1 == null) {
         var4 = "null";
      }

      this.write((CharSequence)var4, var2, var3);
      return this;
   }

   public void endAttribute() {
      this.attributes.addAttribute(this.attrURI, this.attrLocalName, this.attrQName, "CDATA", this.strBuffer.toString());
      this.strBuffer.setLength(0);
      this.inStartTag = 1;
   }

   public void endDocument() {
      try {
         this.out.endDocument();
      } catch (SAXException var2) {
         this.error("endDocument", var2);
      }
   }

   public void endElement() {
      this.endStartTag();
      this.flushStrBuffer();
      --this.nesting;
      int var2 = this.nesting * 3;

      try {
         this.out.endElement(this.names[var2], this.names[var2 + 1], this.names[var2 + 2]);
      } catch (SAXException var3) {
         this.error("endElement", var3);
      }

      this.names[var2] = null;
      this.names[var2 + 1] = null;
      this.names[var2 + 2] = null;
   }

   public void endStartTag() {
      if(this.inStartTag == 1) {
         int var2 = (this.nesting - 1) * 3;

         try {
            this.out.startElement(this.names[var2], this.names[var2 + 1], this.names[var2 + 2], this.attributes);
         } catch (SAXException var3) {
            this.error("startElement", var3);
         }

         this.attributes.clear();
         this.inStartTag = 0;
      }
   }

   public void error(String var1, SAXException var2) {
      throw new RuntimeException("caught " + var2 + " in " + var1);
   }

   public void finalize() {
      this.flushStrBuffer();
   }

   void flushStrBuffer() {
      // $FF: Couldn't be decompiled
   }

   public ContentHandler getContentHandler() {
      return this.out;
   }

   public boolean ignoring() {
      return false;
   }

   public void setContentHandler(ContentHandler var1) {
      this.out = var1;
   }

   public void startAttribute(Object var1) {
      this.attrURI = ((Symbol)var1).getNamespaceURI();
      this.attrLocalName = ((Symbol)var1).getLocalName();
      this.attrQName = var1.toString();
      this.inStartTag = 2;
   }

   public void startDocument() {
      try {
         this.out.startDocument();
      } catch (SAXException var2) {
         this.error("startDocument", var2);
      }
   }

   public void startElement(Object var1) {
      if(this.inStartTag == 1) {
         this.endStartTag();
      }

      this.flushStrBuffer();
      int var4 = this.nesting * 3;
      if(var4 >= this.names.length) {
         String[] var2 = new String[var4 * 2];
         System.arraycopy(this.names, 0, var2, 0, var4);
         this.names = var2;
      }

      String var3;
      String var6;
      if(var1 instanceof Symbol) {
         Symbol var5 = (Symbol)var1;
         var3 = var5.getNamespaceURI();
         var6 = var5.getLocalName();
      } else if(var1 instanceof XName) {
         XName var7 = (XName)var1;
         var3 = var7.getNamespaceURI();
         var6 = var7.getLocalName();
      } else {
         var3 = "";
         var6 = var1.toString();
      }

      this.names[var4] = var3;
      this.names[var4 + 1] = var6;
      this.names[var4 + 2] = var1.toString();
      this.inStartTag = 1;
      ++this.nesting;
   }

   public void write(int var1) {
      if(this.inStartTag == 1) {
         this.endStartTag();
      }

      int var2 = var1;
      if(var1 >= 65536) {
         this.strBuffer.append((char)((var1 - 65536 >> 10) + '\ud800'));
         var2 = (var1 & 1023) + '\udc00';
      }

      this.strBuffer.append((char)var2);
   }

   public void write(CharSequence var1, int var2, int var3) {
      if(this.inStartTag == 1) {
         this.endStartTag();
      }

      this.strBuffer.append(var1, var2, var3);
   }

   public void write(String var1) {
      if(this.inStartTag == 1) {
         this.endStartTag();
      }

      this.strBuffer.append(var1);
   }

   public void write(char[] var1, int var2, int var3) {
      if(this.inStartTag == 1) {
         this.endStartTag();
      }

      if(this.inStartTag == 2) {
         this.strBuffer.append(var1, var2, var3);
      } else {
         this.flushStrBuffer();

         try {
            this.out.characters(var1, var2, var3);
         } catch (SAXException var4) {
            this.error("characters", var4);
         }
      }
   }

   public void writeBoolean(boolean var1) {
      if(this.inStartTag == 1) {
         this.endStartTag();
      }

      this.strBuffer.append(var1);
   }

   public void writeDouble(double var1) {
      if(this.inStartTag == 1) {
         this.endStartTag();
      }

      this.strBuffer.append(var1);
   }

   public void writeFloat(float var1) {
      if(this.inStartTag == 1) {
         this.endStartTag();
      }

      this.strBuffer.append(var1);
   }

   public void writeInt(int var1) {
      if(this.inStartTag == 1) {
         this.endStartTag();
      }

      this.strBuffer.append(var1);
   }

   public void writeLong(long var1) {
      if(this.inStartTag == 1) {
         this.endStartTag();
      }

      this.strBuffer.append(var1);
   }

   public void writeObject(Object var1) {
      if(var1 instanceof Consumable) {
         ((Consumable)var1).consume(this);
      } else if(var1 instanceof SeqPosition) {
         SeqPosition var3 = (SeqPosition)var1;
         var3.sequence.consumeNext(var3.ipos, this);
      } else if(var1 instanceof Char) {
         ((Char)var1).print(this);
      } else {
         String var2;
         if(var1 == null) {
            var2 = "(null)";
         } else {
            var2 = var1.toString();
         }

         this.write(var2);
      }
   }
}
