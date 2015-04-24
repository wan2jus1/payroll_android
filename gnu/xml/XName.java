package gnu.xml;

import gnu.mapping.Symbol;
import gnu.xml.NamespaceBinding;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class XName extends Symbol implements Externalizable {

   NamespaceBinding namespaceNodes;


   public XName() {
   }

   public XName(Symbol var1, NamespaceBinding var2) {
      super(var1.getNamespace(), var1.getName());
      this.namespaceNodes = var2;
   }

   public static int checkName(String var0) {
      int var7 = var0.length();
      byte var3;
      if(var7 == 0) {
         var3 = -1;
      } else {
         byte var1 = 2;
         int var2 = 0;

         while(true) {
            var3 = var1;
            if(var2 >= var7) {
               break;
            }

            boolean var4;
            if(var2 == 0) {
               var4 = true;
            } else {
               var4 = false;
            }

            int var8 = var2 + 1;
            char var6 = var0.charAt(var2);
            int var5 = var6;
            var2 = var8;
            if(var6 >= '\ud800') {
               var5 = var6;
               var2 = var8;
               if(var6 < '\udc00') {
                  var5 = var6;
                  var2 = var8;
                  if(var8 < var7) {
                     var5 = (var6 - '\ud800') * 1024 + (var0.charAt(var8) - '\udc00') + 65536;
                     var2 = var8 + 1;
                  }
               }
            }

            byte var9;
            if(var5 == 58) {
               var9 = var1;
               if(var1 == 2) {
                  var9 = 1;
               }
            } else {
               if(!isNamePart(var5)) {
                  return -1;
               }

               var9 = var1;
               if(var4) {
                  var9 = var1;
                  if(!isNameStart(var5)) {
                     var9 = 0;
                  }
               }
            }

            var1 = var9;
         }
      }

      return var3;
   }

   public static boolean isNCName(String var0) {
      return checkName(var0) > 1;
   }

   public static boolean isName(String var0) {
      return checkName(var0) > 0;
   }

   public static boolean isNamePart(int var0) {
      return Character.isUnicodeIdentifierPart(var0) || var0 == 45 || var0 == 46;
   }

   public static boolean isNameStart(int var0) {
      return Character.isUnicodeIdentifierStart(var0) || var0 == 95;
   }

   public static boolean isNmToken(String var0) {
      return checkName(var0) >= 0;
   }

   public final NamespaceBinding getNamespaceNodes() {
      return this.namespaceNodes;
   }

   String lookupNamespaceURI(String var1) {
      for(NamespaceBinding var2 = this.namespaceNodes; var2 != null; var2 = var2.next) {
         if(var1 == var2.prefix) {
            return var2.uri;
         }
      }

      return null;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      super.readExternal(var1);
      this.namespaceNodes = (NamespaceBinding)var1.readObject();
   }

   public final void setNamespaceNodes(NamespaceBinding var1) {
      this.namespaceNodes = var1;
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      super.writeExternal(var1);
      var1.writeObject(this.namespaceNodes);
   }
}
