package gnu.kawa.sax;

import gnu.kawa.sax.ContentConsumer;
import gnu.text.LineBufferedReader;
import gnu.text.SourceMessages;
import gnu.xml.XMLFilter;
import gnu.xml.XMLParser;
import java.io.IOException;
import java.io.Reader;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

public class KawaXMLReader extends ContentConsumer implements XMLReader {

   ErrorHandler errorHandler;


   public DTDHandler getDTDHandler() {
      return null;
   }

   public EntityResolver getEntityResolver() {
      return null;
   }

   public ErrorHandler getErrorHandler() {
      return this.errorHandler;
   }

   public boolean getFeature(String var1) {
      return false;
   }

   public Object getProperty(String var1) {
      return null;
   }

   public void parse(String var1) {
   }

   public void parse(InputSource var1) throws IOException, SAXException {
      Reader var3 = var1.getCharacterStream();
      Object var2 = var3;
      if(var3 == null) {
         var2 = XMLParser.XMLStreamReader(var1.getByteStream());
      }

      SourceMessages var6 = new SourceMessages();
      XMLFilter var4 = new XMLFilter(this);
      LineBufferedReader var5 = new LineBufferedReader((Reader)var2);
      var4.setSourceLocator((LineBufferedReader)var5);
      this.getContentHandler().setDocumentLocator(var4);
      XMLParser.parse((LineBufferedReader)var5, var6, (XMLFilter)var4);
      String var7 = var6.toString(20);
      if(var7 != null) {
         throw new SAXParseException(var7, var4);
      }
   }

   public void setDTDHandler(DTDHandler var1) {
   }

   public void setEntityResolver(EntityResolver var1) {
   }

   public void setErrorHandler(ErrorHandler var1) {
      this.errorHandler = var1;
   }

   public void setFeature(String var1, boolean var2) {
   }

   public void setProperty(String var1, Object var2) {
   }
}
