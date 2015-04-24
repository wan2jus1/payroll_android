package gnu.xml;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public final class NamespaceBinding implements Externalizable {

   public static final String XML_NAMESPACE = "http://www.w3.org/XML/1998/namespace";
   public static final NamespaceBinding predefinedXML = new NamespaceBinding("xml", "http://www.w3.org/XML/1998/namespace", (NamespaceBinding)null);
   int depth;
   NamespaceBinding next;
   String prefix;
   String uri;


   public NamespaceBinding(String var1, String var2, NamespaceBinding var3) {
      this.prefix = var1;
      this.uri = var2;
      this.setNext(var3);
   }

   public static NamespaceBinding commonAncestor(NamespaceBinding var0, NamespaceBinding var1) {
      NamespaceBinding var3 = var0;
      NamespaceBinding var2 = var1;
      if(var0.depth > var1.depth) {
         var2 = var0;
         var3 = var1;
      }

      while(true) {
         var0 = var3;
         var1 = var2;
         if(var2.depth <= var3.depth) {
            while(var0 != var1) {
               var0 = var0.next;
               var1 = var1.next;
            }

            return var0;
         }

         var2 = var2.next;
      }
   }

   public static NamespaceBinding maybeAdd(String var0, String var1, NamespaceBinding var2) {
      NamespaceBinding var3 = var2;
      if(var2 == null) {
         if(var1 == null) {
            return var2;
         }

         var3 = predefinedXML;
      }

      String var4 = var3.resolve(var0);
      if(var4 == null) {
         if(var1 == null) {
            return var3;
         }
      } else if(var4.equals(var1)) {
         return var3;
      }

      return new NamespaceBinding(var0, var1, var3);
   }

   public static final NamespaceBinding nconc(NamespaceBinding var0, NamespaceBinding var1) {
      if(var0 == null) {
         return var1;
      } else {
         var0.setNext(nconc(var0.next, var1));
         return var0;
      }
   }

   public int count(NamespaceBinding var1) {
      int var3 = 0;

      for(NamespaceBinding var2 = this; var2 != var1; var2 = var2.next) {
         ++var3;
      }

      return var3;
   }

   public final NamespaceBinding getNext() {
      return this.next;
   }

   public final String getPrefix() {
      return this.prefix;
   }

   public final String getUri() {
      return this.uri;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.prefix = var1.readUTF();
      this.uri = var1.readUTF();
      this.next = (NamespaceBinding)var1.readObject();
   }

   public String resolve(String var1) {
      for(NamespaceBinding var2 = this; var2 != null; var2 = var2.next) {
         if(var2.prefix == var1) {
            return var2.uri;
         }
      }

      return null;
   }

   public String resolve(String var1, NamespaceBinding var2) {
      for(NamespaceBinding var3 = this; var3 != var2; var3 = var3.next) {
         if(var3.prefix == var1) {
            return var3.uri;
         }
      }

      return null;
   }

   public NamespaceBinding reversePrefix(NamespaceBinding var1) {
      NamespaceBinding var3 = var1;
      NamespaceBinding var2 = this;
      int var5;
      if(var1 == null) {
         var5 = -1;
      } else {
         var5 = var1.depth;
      }

      while(var2 != var1) {
         NamespaceBinding var4 = var2.next;
         var2.next = var3;
         var3 = var2;
         ++var5;
         var2.depth = var5;
         var2 = var4;
      }

      return var3;
   }

   public final void setNext(NamespaceBinding var1) {
      this.next = var1;
      int var2;
      if(var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.depth + 1;
      }

      this.depth = var2;
   }

   public final void setPrefix(String var1) {
      this.prefix = var1;
   }

   public final void setUri(String var1) {
      this.uri = var1;
   }

   public String toString() {
      return "Namespace{" + this.prefix + "=" + this.uri + ", depth:" + this.depth + "}";
   }

   public String toStringAll() {
      StringBuffer var3 = new StringBuffer("Namespaces{");

      for(NamespaceBinding var1 = this; var1 != null; var1 = var1.next) {
         var3.append(var1.prefix);
         var3.append("=\"");
         var3.append(var1.uri);
         String var2;
         if(var1 == null) {
            var2 = "\"";
         } else {
            var2 = "\", ";
         }

         var3.append(var2);
      }

      var3.append('}');
      return var3.toString();
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeUTF(this.prefix);
      var1.writeUTF(this.uri);
      var1.writeObject(this.next);
   }
}
