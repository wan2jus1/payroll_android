package gnu.kawa.xml;

import gnu.lists.Consumable;
import gnu.lists.Consumer;
import gnu.lists.FilterConsumer;
import gnu.lists.UnescapedData;
import gnu.mapping.OutPort;
import gnu.mapping.Symbol;
import gnu.xml.XMLPrinter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

public class HttpPrinter extends FilterConsumer {

   Object currentHeader;
   private int elementNesting;
   Vector headers = new Vector();
   protected OutputStream ostream;
   protected String sawContentType;
   StringBuilder sbuf = new StringBuilder(100);
   private int seenStartDocument;
   boolean seenXmlHeader;
   OutPort writer;


   public HttpPrinter(OutPort var1) {
      super((Consumer)null);
      this.writer = var1;
   }

   public HttpPrinter(OutputStream var1) {
      super((Consumer)null);
      this.ostream = var1;
   }

   public static HttpPrinter make(OutPort var0) {
      return new HttpPrinter(var0);
   }

   private void writeRaw(String var1) throws IOException {
      if(this.writer != null) {
         this.writer.write(var1);
      } else {
         int var3 = var1.length();

         for(int var2 = 0; var2 < var3; ++var2) {
            this.ostream.write((byte)var1.charAt(var2));
         }
      }

   }

   public void addHeader(String var1, String var2) {
      if(var1.equalsIgnoreCase("Content-type")) {
         this.sawContentType = var2;
      }

      this.headers.addElement(var1);
      this.headers.addElement(var2);
   }

   protected void beforeNode() {
      if(this.sawContentType == null) {
         this.addHeader("Content-type", "text/xml");
      }

      this.beginData();
   }

   public void beginData() {
      if(this.base == null) {
         if(this.sawContentType == null) {
            this.addHeader("Content-type", "text/plain");
         }

         if(this.writer == null) {
            this.writer = new OutPort(this.ostream);
         }

         String var1 = null;
         if("text/html".equalsIgnoreCase(this.sawContentType)) {
            var1 = "html";
         } else if("application/xhtml+xml".equalsIgnoreCase(this.sawContentType)) {
            var1 = "xhtml";
         } else if("text/plain".equalsIgnoreCase(this.sawContentType)) {
            var1 = "plain";
         }

         this.base = XMLPrinter.make(this.writer, var1);
         if(this.seenStartDocument == 0) {
            this.base.startDocument();
            this.seenStartDocument = 1;
         }

         try {
            this.printHeaders();
         } catch (Throwable var2) {
            throw new RuntimeException(var2.toString());
         }
      }

      this.append(this.sbuf);
      this.sbuf.setLength(0);
   }

   public void endAttribute() {
      if(this.currentHeader != null) {
         this.addHeader(this.currentHeader.toString(), this.sbuf.toString());
         this.sbuf.setLength(0);
         this.currentHeader = null;
      } else {
         this.base.endAttribute();
      }
   }

   public void endDocument() {
      // $FF: Couldn't be decompiled
   }

   public void endElement() {
      super.endElement();
      --this.elementNesting;
      if(this.elementNesting == 0 && this.seenStartDocument == 1) {
         this.endDocument();
      }

   }

   public void printHeader(String var1, String var2) throws IOException {
      this.writeRaw(var1);
      this.writeRaw(": ");
      this.writeRaw(var2);
      this.writeRaw("\n");
   }

   public void printHeaders() throws IOException {
      int var2 = this.headers.size();

      for(int var1 = 0; var1 < var2; var1 += 2) {
         this.printHeader(this.headers.elementAt(var1).toString(), this.headers.elementAt(var1 + 1).toString());
      }

      this.writeRaw("\n");
   }

   public boolean reset(boolean var1) {
      if(var1) {
         this.headers.clear();
         this.sawContentType = null;
         this.currentHeader = null;
         this.elementNesting = 0;
      }

      this.sbuf.setLength(0);
      this.base = null;
      var1 = true;
      if(this.ostream != null) {
         if(this.writer == null) {
            var1 = true;
         } else {
            var1 = false;
         }

         this.writer = null;
      }

      return var1;
   }

   public void startAttribute(Object var1) {
      if(this.base == null) {
         this.currentHeader = var1;
      } else {
         this.base.startAttribute(var1);
      }
   }

   public void startDocument() {
      if(this.base != null) {
         this.base.startDocument();
      }

      this.seenStartDocument = 2;
   }

   public void startElement(Object var1) {
      if(this.sawContentType == null) {
         String var2;
         if(!this.seenXmlHeader) {
            var2 = "text/html";
         } else if(var1 instanceof Symbol && "html".equals(((Symbol)var1).getLocalPart())) {
            var2 = "text/xhtml";
         } else {
            var2 = "text/xml";
         }

         this.addHeader("Content-type", var2);
      }

      this.beginData();
      this.base.startElement(var1);
      ++this.elementNesting;
   }

   public void write(CharSequence var1, int var2, int var3) {
      if(this.base == null) {
         this.sbuf.append(var1, var2, var2 + var3);
      } else {
         this.base.write((CharSequence)var1, var2, var3);
      }
   }

   public void write(char[] var1, int var2, int var3) {
      if(this.base == null) {
         this.sbuf.append(var1, var2, var3);
      } else {
         this.base.write((char[])var1, var2, var3);
      }
   }

   public void writeObject(Object var1) {
      if(var1 instanceof Consumable && !(var1 instanceof UnescapedData)) {
         ((Consumable)var1).consume(this);
      } else {
         this.beginData();
         super.writeObject(var1);
      }
   }
}
