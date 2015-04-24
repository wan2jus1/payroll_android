package gnu.xml;

import gnu.kawa.xml.ElementType;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.UntypedAtomic;
import gnu.lists.AbstractSequence;
import gnu.lists.SeqPosition;
import gnu.lists.TreeList;
import gnu.mapping.CharArrayOutPort;
import gnu.mapping.Symbol;
import gnu.text.Path;
import gnu.text.URIPath;
import gnu.xml.XMLPrinter;
import gnu.xml.XName;

public class NodeTree extends TreeList {

   static int counter;
   int id;
   int idCount;
   String[] idNames;
   int[] idOffsets;


   public static NodeTree make() {
      return new NodeTree();
   }

   public int ancestorAttribute(int var1, String var2, String var3) {
      while(true) {
         int var4;
         if(var1 == -1) {
            var4 = 0;
         } else {
            int var5 = this.getAttributeI(var1, var2, var3);
            var4 = var5;
            if(var5 == 0) {
               var1 = this.parentPos(var1);
               continue;
            }
         }

         return var4;
      }
   }

   public Path baseUriOfPos(int var1, boolean var2) {
      Object var4 = null;
      int var7 = this.posToDataIndex(var1);
      int var6 = var1;
      var1 = var7;

      Object var3;
      while(true) {
         if(var1 == this.data.length) {
            return null;
         }

         char var8 = this.data[var1];
         Object var5 = null;
         if(var8 == '\uf112') {
            var6 = this.getIntN(var1 + 1);
            var3 = var5;
            if(var6 >= 0) {
               var3 = URIPath.makeURI(this.objects[var6]);
            }
         } else {
            label55: {
               if(var8 < 'ꀀ' || var8 > '꿿') {
                  var3 = var5;
                  if(var8 != '\uf108') {
                     break label55;
                  }
               }

               var6 = this.getAttributeI(var6, "http://www.w3.org/XML/1998/namespace", "base");
               var3 = var5;
               if(var6 != 0) {
                  var3 = URIPath.valueOf((String)KNode.getNodeValue(this, var6));
               }
            }
         }

         var5 = var4;
         if(var3 != null) {
            if(var4 != null && var2) {
               var3 = ((Path)var3).resolve((Path)var4);
            }

            if(((Path)var3).isAbsolute()) {
               break;
            }

            var5 = var3;
            if(!var2) {
               break;
            }
         }

         var1 = this.parentOrEntityI(var1);
         if(var1 == -1) {
            return (Path)var5;
         }

         var6 = var1 << 1;
         var4 = var5;
      }

      return (Path)var3;
   }

   void enterID(String var1, int var2) {
      String[] var5 = this.idNames;
      int[] var6 = this.idOffsets;
      String[] var3;
      int[] var4;
      int var7;
      int var8;
      int var9;
      if(var5 == null) {
         var7 = 64;
         this.idNames = new String[64];
         this.idOffsets = new int[64];
         var4 = var6;
         var3 = var5;
      } else {
         var9 = this.idCount;
         var8 = this.idNames.length;
         var7 = var8;
         var3 = var5;
         var4 = var6;
         if(var9 * 4 >= var8 * 3) {
            this.idNames = new String[var8 * 2];
            this.idOffsets = new int[var8 * 2];
            this.idCount = 0;
            var7 = var8;

            while(true) {
               var9 = var7 - 1;
               if(var9 < 0) {
                  var3 = this.idNames;
                  var4 = this.idOffsets;
                  var7 = var8 * 2;
                  break;
               }

               String var10 = var5[var9];
               var7 = var9;
               if(var10 != null) {
                  this.enterID(var10, var6[var9]);
                  var7 = var9;
               }
            }
         }
      }

      var8 = var1.hashCode();
      var9 = var7 - 1;
      var7 = var8 & var9;

      while(true) {
         String var11 = var3[var7];
         if(var11 == null) {
            var3[var7] = var1;
            var4[var7] = var2;
            ++this.idCount;
            break;
         }

         if(var11.equals(var1)) {
            break;
         }

         var7 = var7 + (~var8 << 1 | 1) & var9;
      }

   }

   public int getAttribute(int var1, String var2, String var3) {
      Object var4 = null;
      if(var2 == null) {
         var2 = null;
      } else {
         var2 = var2.intern();
      }

      if(var3 == null) {
         var3 = (String)var4;
      } else {
         var3 = var3.intern();
      }

      return this.getAttributeI(var1, var2, var3);
   }

   public int getAttributeI(int var1, String var2, String var3) {
      var1 = this.firstAttributePos(var1);

      int var4;
      while(true) {
         if(var1 == 0 || this.getNextKind(var1) != 35) {
            var4 = 0;
            break;
         }

         if(var3 == null || this.posLocalName(var1) == var3) {
            var4 = var1;
            if(var2 == null) {
               break;
            }

            var4 = var1;
            if(this.posNamespaceURI(var1) == var2) {
               break;
            }
         }

         var1 = this.nextPos(var1);
      }

      return var4;
   }

   public int getId() {
      if(this.id == 0) {
         int var1 = counter + 1;
         counter = var1;
         this.id = var1;
      }

      return this.id;
   }

   public SeqPosition getIteratorAtPos(int var1) {
      return KNode.make(this, var1);
   }

   public int lookupID(String var1) {
      String[] var2 = this.idNames;
      int[] var3 = this.idOffsets;
      int var5 = this.idNames.length;
      int var6 = var1.hashCode();
      int var7 = var5 - 1;
      var5 = var6 & var7;

      while(true) {
         String var4 = var2[var5];
         if(var4 == null) {
            return -1;
         }

         if(var4.equals(var1)) {
            return var3[var5];
         }

         var5 = var5 + (~var6 << 1 | 1) & var7;
      }
   }

   public void makeIDtableIfNeeded() {
      if(this.idNames == null) {
         this.idNames = new String[64];
         this.idOffsets = new int[64];
         int var3 = this.endPos();
         int var1 = 0;

         while(true) {
            int var2 = this.nextMatching(var1, ElementType.anyElement, var3, true);
            if(var2 == 0) {
               break;
            }

            int var4 = this.getAttributeI(var2, "http://www.w3.org/XML/1998/namespace", "id");
            var1 = var2;
            if(var4 != 0) {
               this.enterID(KNode.getNodeValue(this, var4), var2);
               var1 = var2;
            }
         }
      }

   }

   public int nextPos(int var1) {
      byte var2 = 0;
      if((var1 & 1) != 0) {
         ;
      }

      int var3 = this.posToDataIndex(var1);
      var1 = this.nextNodeIndex(var3, Integer.MAX_VALUE);
      if(var1 != var3) {
         var1 <<= 1;
      } else {
         var1 = var2;
         if(var3 != this.data.length) {
            return (var3 << 1) + 3;
         }
      }

      return var1;
   }

   public int posFirstChild(int var1) {
      var1 = this.gotoChildrenStart(this.posToDataIndex(var1));
      if(var1 >= 0) {
         char var2 = this.data[var1];
         if(var2 != '\uf10b' && var2 != '\uf10c' && var2 != '\uf111') {
            return var1 << 1;
         }
      }

      return -1;
   }

   public boolean posHasAttributes(int var1) {
      var1 = this.gotoAttributesStart(this.posToDataIndex(var1));
      return var1 >= 0 && var1 >= 0 && this.data[var1] == '\uf109';
   }

   public boolean posIsDefaultNamespace(int var1, String var2) {
      throw new Error("posIsDefaultNamespace not implemented");
   }

   public String posLocalName(int var1) {
      Object var2 = this.getNextTypeObject(var1);
      return var2 instanceof XName?((XName)var2).getLocalPart():(var2 instanceof Symbol?((Symbol)var2).getLocalName():this.getNextTypeName(var1));
   }

   public String posLookupNamespaceURI(int var1, String var2) {
      if(this.getNextKind(var1) != 33) {
         throw new IllegalArgumentException("argument must be an element");
      } else {
         Object var3 = this.getNextTypeObject(var1);
         return var3 instanceof XName?((XName)var3).lookupNamespaceURI(var2):null;
      }
   }

   public String posLookupPrefix(int var1, String var2) {
      throw new Error("posLookupPrefix not implemented");
   }

   public String posNamespaceURI(int var1) {
      Object var2 = this.getNextTypeObject(var1);
      return var2 instanceof XName?((XName)var2).getNamespaceURI():(var2 instanceof Symbol?((Symbol)var2).getNamespaceURI():null);
   }

   public String posPrefix(int var1) {
      String var2 = this.getNextTypeName(var1);
      if(var2 != null) {
         var1 = var2.indexOf(58);
         if(var1 >= 0) {
            return var2.substring(0, var1);
         }
      }

      return null;
   }

   public String posTarget(int var1) {
      var1 = this.posToDataIndex(var1);
      if(this.data[var1] != '\uf114') {
         throw new ClassCastException("expected process-instruction");
      } else {
         return (String)this.objects[this.getIntN(var1 + 1)];
      }
   }

   public int stableCompare(AbstractSequence var1) {
      if(this == var1) {
         return 0;
      } else {
         int var3 = super.stableCompare(var1);
         int var2 = var3;
         if(var3 == 0) {
            var2 = var3;
            if(var1 instanceof NodeTree) {
               var2 = this.getId();
               var3 = ((NodeTree)var1).getId();
               if(var2 < var3) {
                  var2 = -1;
               } else if(var2 > var3) {
                  var2 = 1;
               } else {
                  var2 = 0;
               }
            }
         }

         return var2;
      }
   }

   public String toString() {
      CharArrayOutPort var1 = new CharArrayOutPort();
      this.consume(new XMLPrinter(var1));
      var1.close();
      return var1.toString();
   }

   public Object typedValue(int var1) {
      StringBuffer var2 = new StringBuffer();
      this.stringValue(this.posToDataIndex(var1), var2);
      String var3 = var2.toString();
      var1 = this.getNextKind(var1);
      return var1 != 37 && var1 != 36?new UntypedAtomic(var3):var3;
   }
}
