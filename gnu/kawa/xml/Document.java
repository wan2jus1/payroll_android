package gnu.kawa.xml;

import gnu.kawa.xml.KDocument;
import gnu.lists.Consumer;
import gnu.lists.XConsumer;
import gnu.mapping.ThreadLocation;
import gnu.text.Path;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import gnu.xml.NodeTree;
import gnu.xml.XMLParser;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;

public class Document {

   private static HashMap cache = new HashMap();
   private static ThreadLocation docMapLocation = new ThreadLocation("document-map");
   public static final Document document = new Document();


   public static void clearLocalCache() {
      docMapLocation.getLocation().set((Object)null);
   }

   public static void clearSoftCache() {
      cache = new HashMap();
   }

   public static KDocument parse(Object var0) throws Throwable {
      NodeTree var1 = new NodeTree();
      parse(var0, var1);
      return new KDocument(var1, 10);
   }

   public static void parse(Object var0, Consumer var1) throws Throwable {
      SourceMessages var2 = new SourceMessages();
      if(var1 instanceof XConsumer) {
         ((XConsumer)var1).beginEntity(var0);
      }

      XMLParser.parse((Object)var0, var2, (Consumer)var1);
      if(var2.seenErrors()) {
         throw new SyntaxException("document function read invalid XML", var2);
      } else {
         if(var1 instanceof XConsumer) {
            ((XConsumer)var1).endEntity();
         }

      }
   }

   public static KDocument parseCached(Path param0) throws Throwable {
      // $FF: Couldn't be decompiled
   }

   public static KDocument parseCached(Object var0) throws Throwable {
      return parseCached((Path)Path.valueOf(var0));
   }

   private static class DocReference extends SoftReference {

      static ReferenceQueue queue = new ReferenceQueue();
      Path key;


      public DocReference(Path var1, KDocument var2) {
         super(var2, queue);
         this.key = var1;
      }
   }
}
