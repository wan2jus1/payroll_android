package gnu.xml;

import gnu.expr.Keyword;
import gnu.kawa.sax.ContentConsumer;
import gnu.lists.AbstractSequence;
import gnu.lists.CharSeq;
import gnu.lists.Consumer;
import gnu.lists.PositionConsumer;
import gnu.lists.SeqPosition;
import gnu.lists.TreeList;
import gnu.lists.UnescapedData;
import gnu.lists.XConsumer;
import gnu.mapping.Symbol;
import gnu.text.Char;
import gnu.text.LineBufferedReader;
import gnu.text.SourceLocator;
import gnu.text.SourceMessages;
import gnu.xml.MappingInfo;
import gnu.xml.NamespaceBinding;
import gnu.xml.NodeTree;
import gnu.xml.TextUtils;
import gnu.xml.XName;
import java.util.Iterator;
import java.util.List;
import org.xml.sax.AttributeList;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class XMLFilter implements DocumentHandler, ContentHandler, SourceLocator, XConsumer, PositionConsumer {

   public static final int COPY_NAMESPACES_INHERIT = 2;
   public static final int COPY_NAMESPACES_PRESERVE = 1;
   private static final int SAW_KEYWORD = 1;
   private static final int SAW_WORD = 2;
   int attrCount = -1;
   String attrLocalName;
   String attrPrefix;
   Consumer base;
   public transient int copyNamespacesMode = 1;
   String currentNamespacePrefix;
   protected int ignoringLevel;
   LineBufferedReader in;
   boolean inStartTag;
   SourceLocator locator;
   MappingInfo[] mappingTable = new MappingInfo[128];
   int mappingTableMask;
   private SourceMessages messages;
   boolean mismatchReported;
   NamespaceBinding namespaceBindings;
   public boolean namespacePrefixes = false;
   protected int nesting;
   public Consumer out;
   int previous = 0;
   int[] startIndexes = null;
   protected int stringizingElementNesting = -1;
   protected int stringizingLevel;
   TreeList tlist;
   Object[] workStack;


   public XMLFilter(Consumer var1) {
      this.mappingTableMask = this.mappingTable.length - 1;
      this.base = var1;
      this.out = var1;
      if(var1 instanceof NodeTree) {
         this.tlist = (NodeTree)var1;
      } else {
         this.tlist = new TreeList();
      }

      this.namespaceBindings = NamespaceBinding.predefinedXML;
   }

   public static String duplicateAttributeMessage(Symbol var0, Object var1) {
      StringBuffer var2 = new StringBuffer("duplicate attribute: ");
      String var3 = var0.getNamespaceURI();
      if(var3 != null && var3.length() > 0) {
         var2.append('{');
         var2.append('}');
         var2.append(var3);
      }

      var2.append(var0.getLocalPart());
      if(var1 != null) {
         var2.append(" in <");
         var2.append(var1);
         var2.append('>');
      }

      return var2.toString();
   }

   private void ensureSpaceInStartIndexes(int var1) {
      if(this.startIndexes == null) {
         this.startIndexes = new int[20];
      } else if(var1 >= this.startIndexes.length) {
         int[] var2 = new int[this.startIndexes.length * 2];
         System.arraycopy(this.startIndexes, 0, var2, 0, var1);
         this.startIndexes = var2;
         return;
      }

   }

   private void ensureSpaceInWorkStack(int var1) {
      if(this.workStack == null) {
         this.workStack = new Object[20];
      } else if(var1 >= this.workStack.length) {
         Object[] var2 = new Object[this.workStack.length * 2];
         System.arraycopy(this.workStack, 0, var2, 0, var1);
         this.workStack = var2;
         return;
      }

   }

   private NamespaceBinding mergeHelper(NamespaceBinding var1, NamespaceBinding var2) {
      if(var2 == NamespaceBinding.predefinedXML) {
         return var1;
      } else {
         NamespaceBinding var3 = this.mergeHelper(var1, var2.next);
         String var4 = var2.uri;
         var1 = var3;
         if(var3 == null) {
            if(var4 == null) {
               return var3;
            }

            var1 = NamespaceBinding.predefinedXML;
         }

         String var5 = var2.prefix;
         String var6 = var1.resolve(var5);
         if(var6 == null) {
            if(var4 == null) {
               return var1;
            }
         } else if(var6.equals(var4)) {
            return var1;
         }

         return this.findNamespaceBinding(var5, var4, var1);
      }
   }

   private String resolve(String var1, boolean var2) {
      String var3;
      if(var2 && var1 == null) {
         var3 = "";
      } else {
         String var4 = this.namespaceBindings.resolve(var1);
         var3 = var4;
         if(var4 == null) {
            if(var1 != null) {
               this.error('e', "unknown namespace prefix \'" + var1 + '\'');
            }

            return "";
         }
      }

      return var3;
   }

   private boolean startAttributeCommon() {
      if(this.stringizingElementNesting >= 0) {
         ++this.ignoringLevel;
      }

      int var1 = this.stringizingLevel;
      this.stringizingLevel = var1 + 1;
      if(var1 > 0) {
         return false;
      } else {
         if(this.attrCount < 0) {
            this.attrCount = 0;
         }

         this.ensureSpaceInWorkStack(this.nesting + this.attrCount);
         this.ensureSpaceInStartIndexes(this.attrCount);
         this.startIndexes[this.attrCount] = this.tlist.gapStart;
         ++this.attrCount;
         return true;
      }
   }

   public XMLFilter append(char var1) {
      this.write(var1);
      return this;
   }

   public XMLFilter append(CharSequence var1) {
      Object var2 = var1;
      if(var1 == null) {
         var2 = "null";
      }

      this.append((CharSequence)var2, 0, ((CharSequence)var2).length());
      return this;
   }

   public XMLFilter append(CharSequence var1, int var2, int var3) {
      Object var4 = var1;
      if(var1 == null) {
         var4 = "null";
      }

      this.write((CharSequence)var4, var2, var3 - var2);
      return this;
   }

   public void beginEntity(Object var1) {
      if(this.base instanceof XConsumer) {
         ((XConsumer)this.base).beginEntity(var1);
      }

   }

   public void characters(char[] var1, int var2, int var3) throws SAXException {
      this.write((char[])var1, var2, var3);
   }

   protected void checkValidComment(char[] var1, int var2, int var3) {
      boolean var4 = true;

      while(true) {
         int var5 = var3 - 1;
         if(var5 < 0) {
            break;
         }

         boolean var6;
         if(var1[var2 + var5] == 45) {
            var6 = true;
         } else {
            var6 = false;
         }

         if(var4 && var6) {
            this.error('e', "consecutive or final hyphen in XML comment");
            break;
         }

         var4 = var6;
         var3 = var5;
      }

   }

   protected boolean checkWriteAtomic() {
      this.previous = 0;
      if(this.ignoringLevel > 0) {
         return false;
      } else {
         this.closeStartTag();
         return true;
      }
   }

   void closeStartTag() {
      if(this.attrCount >= 0 && this.stringizingLevel <= 0) {
         this.inStartTag = false;
         this.previous = 0;
         if(this.attrLocalName != null) {
            this.endAttribute();
         }

         NamespaceBinding var4;
         if(this.nesting == 0) {
            var4 = NamespaceBinding.predefinedXML;
         } else {
            var4 = (NamespaceBinding)this.workStack[this.nesting - 2];
         }

         NamespaceBinding var1 = this.namespaceBindings;

         NamespaceBinding var2;
         int var9;
         String var15;
         String var16;
         int var32;
         for(var9 = 0; var9 <= this.attrCount; var1 = var2) {
            Object var3 = this.workStack[this.nesting + var9 - 1];
            var2 = var1;
            if(var3 instanceof Symbol) {
               Symbol var7 = (Symbol)var3;
               var15 = var7.getPrefix();
               String var5 = var15;
               if(var15 == "") {
                  var5 = null;
               }

               var15 = var7.getNamespaceURI();
               var16 = var15;
               if(var15 == "") {
                  var16 = null;
               }

               if(var9 > 0 && var5 == null && var16 == null) {
                  var2 = var1;
               } else {
                  boolean var10 = false;
                  NamespaceBinding var6 = var1;

                  while(true) {
                     if(var6 == var4) {
                        var10 = true;
                     }

                     if(var6 == null) {
                        if(var5 == null) {
                           var2 = var1;
                           if(var16 == null) {
                              break;
                           }
                        }

                        var2 = this.findNamespaceBinding(var5, var16, var1);
                        break;
                     }

                     if(var6.prefix == var5) {
                        var2 = var1;
                        if(var6.uri == var16) {
                           break;
                        }

                        if(var10) {
                           var2 = this.findNamespaceBinding(var5, var16, var1);
                           break;
                        }

                        NamespaceBinding var22 = var1;

                        String var21;
                        label265:
                        while(true) {
                           if(var22 == null) {
                              var32 = 1;

                              while(true) {
                                 var15 = ("_ns_" + var32).intern();
                                 if(var1.resolve(var15) == null) {
                                    break label265;
                                 }

                                 ++var32;
                              }
                           }

                           if(var22.uri == var16) {
                              var21 = var22.prefix;
                              var15 = var21;
                              if(var1.resolve(var21) == var16) {
                                 break;
                              }
                           }

                           var22 = var22.next;
                        }

                        var22 = this.findNamespaceBinding(var15, var16, var1);
                        var21 = var7.getLocalName();
                        String var14 = var16;
                        if(var16 == null) {
                           var14 = "";
                        }

                        this.workStack[this.nesting + var9 - 1] = Symbol.make(var14, var21, var15);
                        var2 = var22;
                        break;
                     }

                     var6 = var6.next;
                  }
               }
            }

            ++var9;
         }

         for(var32 = 0; var32 <= this.attrCount; ++var32) {
            Object var24 = this.workStack[this.nesting + var32 - 1];
            boolean var11 = false;
            boolean var31 = false;
            int var12;
            MappingInfo var18;
            int var33;
            if(!(var24 instanceof MappingInfo) && this.out != this.tlist) {
               Symbol var20 = (Symbol)var24;
               var15 = var20.getNamespaceURI();
               var16 = var20.getLocalName();
               var18 = null;
               var31 = var11;
            } else {
               String var17;
               MappingInfo var27;
               if(var24 instanceof MappingInfo) {
                  var27 = (MappingInfo)var24;
                  var17 = var27.prefix;
                  var16 = var27.local;
                  if(var32 > 0 && (var17 == null && var16 == "xmlns" || var17 == "xmlns")) {
                     var31 = true;
                     var15 = "(namespace-node)";
                  } else {
                     boolean var13;
                     if(var32 > 0) {
                        var13 = true;
                     } else {
                        var13 = false;
                     }

                     var15 = this.resolve(var17, var13);
                  }
               } else {
                  Symbol var25 = (Symbol)var24;
                  var27 = this.lookupTag(var25);
                  var17 = var27.prefix;
                  var16 = var27.local;
                  var15 = var25.getNamespaceURI();
               }

               var33 = var27.tagHash;
               var12 = var33 & this.mappingTableMask;
               var27 = this.mappingTable[var12];

               while(true) {
                  if(var27 == null) {
                     var27 = new MappingInfo();
                     var27.tagHash = var33;
                     var27.prefix = var17;
                     var27.local = var16;
                     var27.nextInBucket = this.mappingTable[var12];
                     this.mappingTable[var12] = var27;
                     var27.uri = var15;
                     var27.qname = Symbol.make(var15, var16, var17);
                     var18 = var27;
                     if(var32 == 0) {
                        var27.type = new XName(var27.qname, var1);
                        var27.namespaces = var1;
                        var18 = var27;
                     }
                     break;
                  }

                  if(var27.tagHash == var33 && var27.local == var16 && var27.prefix == var17) {
                     label289: {
                        if(var27.uri == null) {
                           var27.uri = var15;
                           var27.qname = Symbol.make(var15, var16, var17);
                        } else {
                           if(var27.uri != var15) {
                              break label289;
                           }

                           if(var27.qname == null) {
                              var27.qname = Symbol.make(var15, var16, var17);
                           }
                        }

                        if(var32 != 0) {
                           Symbol var19 = var27.qname;
                           var18 = var27;
                           break;
                        }

                        if(var27.namespaces == var1 || var27.namespaces == null) {
                           XName var23 = var27.type;
                           var27.namespaces = var1;
                           var18 = var27;
                           if(var23 == null) {
                              var27.type = new XName(var27.qname, var1);
                              var18 = var27;
                           }
                           break;
                        }
                     }
                  }

                  var27 = var27.nextInBucket;
               }

               this.workStack[this.nesting + var32 - 1] = var18;
            }

            for(var33 = 1; var33 < var32; ++var33) {
               Object var30 = this.workStack[this.nesting + var33 - 1];
               Symbol var29;
               if(var30 instanceof Symbol) {
                  var29 = (Symbol)var30;
               } else {
                  if(!(var30 instanceof MappingInfo)) {
                     continue;
                  }

                  var29 = ((MappingInfo)var30).qname;
               }

               if(var16 == var29.getLocalPart() && var15 == var29.getNamespaceURI()) {
                  Object var8 = this.workStack[this.nesting - 1];
                  Object var26 = var8;
                  if(var8 instanceof MappingInfo) {
                     var26 = ((MappingInfo)var8).qname;
                  }

                  this.error('e', duplicateAttributeMessage(var29, var26));
               }
            }

            Object var28;
            if(this.out == this.tlist) {
               if(var32 == 0) {
                  var28 = var18.type;
               } else {
                  var28 = var18.qname;
               }

               label177: {
                  var12 = var18.index;
                  if(var12 > 0) {
                     var33 = var12;
                     if(this.tlist.objects[var12] == var28) {
                        break label177;
                     }
                  }

                  var33 = this.tlist.find(var28);
                  var18.index = var33;
               }

               if(var32 == 0) {
                  this.tlist.setElementName(this.tlist.gapEnd, var33);
               } else if(!var31 || this.namespacePrefixes) {
                  this.tlist.setAttributeName(this.startIndexes[var32 - 1], var33);
               }
            } else {
               if(var18 == null) {
                  var28 = var24;
               } else if(var32 == 0) {
                  var28 = var18.type;
               } else {
                  var28 = var18.qname;
               }

               if(var32 == 0) {
                  this.out.startElement(var28);
               } else if(!var31 || this.namespacePrefixes) {
                  this.out.startAttribute(var28);
                  var33 = this.startIndexes[var32 - 1];
                  if(var32 < this.attrCount) {
                     var9 = this.startIndexes[var32];
                  } else {
                     var9 = this.tlist.gapStart;
                  }

                  this.tlist.consumeIRange(var33 + 5, var9 - 1, this.out);
                  this.out.endAttribute();
               }
            }
         }

         if(this.out instanceof ContentConsumer) {
            ((ContentConsumer)this.out).endStartTag();
         }

         for(var9 = 1; var9 <= this.attrCount; ++var9) {
            this.workStack[this.nesting + var9 - 1] = null;
         }

         if(this.out != this.tlist) {
            this.base = this.out;
            this.tlist.clear();
         }

         this.attrCount = -1;
      }
   }

   public void commentFromParser(char[] var1, int var2, int var3) {
      if(this.stringizingLevel == 0) {
         this.closeStartTag();
         if(this.base instanceof XConsumer) {
            ((XConsumer)this.base).writeComment(var1, var2, var3);
         }
      } else if(this.stringizingElementNesting < 0) {
         this.base.write((char[])var1, var2, var3);
         return;
      }

   }

   public void consume(SeqPosition var1) {
      this.writePosition(var1.sequence, var1.ipos);
   }

   public void emitCharacterReference(int var1, char[] var2, int var3, int var4) {
      if(var1 >= 65536) {
         Char.print(var1, this);
      } else {
         this.write(var1);
      }
   }

   public void emitDoctypeDecl(char[] var1, int var2, int var3, int var4, int var5) {
   }

   public void emitEndAttributes() {
      if(this.attrLocalName != null) {
         this.endAttribute();
      }

      this.closeStartTag();
   }

   public void emitEndElement(char[] var1, int var2, int var3) {
      if(this.attrLocalName != null) {
         this.error('e', "unclosed attribute");
         this.endAttribute();
      }

      if(!this.inElement()) {
         this.error('e', "unmatched end element");
      } else {
         if(var1 != null) {
            MappingInfo var5 = this.lookupTag(var1, var2, var3);
            Object var4 = this.workStack[this.nesting - 1];
            if(var4 instanceof MappingInfo && !this.mismatchReported) {
               MappingInfo var7 = (MappingInfo)var4;
               if(var5.local != var7.local || var5.prefix != var7.prefix) {
                  StringBuffer var8 = new StringBuffer("</");
                  var8.append(var1, var2, var3);
                  var8.append("> matching <");
                  String var6 = var7.prefix;
                  if(var6 != null) {
                     var8.append(var6);
                     var8.append(':');
                  }

                  var8.append(var7.local);
                  var8.append('>');
                  this.error('e', var8.toString());
                  this.mismatchReported = true;
               }
            }
         }

         this.closeStartTag();
         if(this.nesting > 0) {
            this.endElement();
            return;
         }
      }

   }

   public void emitEntityReference(char[] var1, int var2, int var3) {
      char var6 = var1[var2];
      byte var5 = 63;
      byte var4;
      if(var3 == 2 && var1[var2 + 1] == 116) {
         if(var6 == 108) {
            var4 = 60;
         } else {
            var4 = var5;
            if(var6 == 103) {
               var4 = 62;
            }
         }
      } else if(var3 == 3) {
         var4 = var5;
         if(var6 == 97) {
            var4 = var5;
            if(var1[var2 + 1] == 109) {
               var4 = var5;
               if(var1[var2 + 2] == 112) {
                  var4 = 38;
               }
            }
         }
      } else {
         var4 = var5;
         if(var3 == 4) {
            char var9 = var1[var2 + 1];
            char var7 = var1[var2 + 2];
            char var8 = var1[var2 + 3];
            if(var6 == 113 && var9 == 117 && var7 == 111 && var8 == 116) {
               var4 = 34;
            } else {
               var4 = var5;
               if(var6 == 97) {
                  var4 = var5;
                  if(var9 == 112) {
                     var4 = var5;
                     if(var7 == 111) {
                        var4 = var5;
                        if(var8 == 115) {
                           var4 = 39;
                        }
                     }
                  }
               }
            }
         }
      }

      this.write(var4);
   }

   public void emitStartAttribute(char[] var1, int var2, int var3) {
      if(this.attrLocalName != null) {
         this.endAttribute();
      }

      if(this.startAttributeCommon()) {
         MappingInfo var4 = this.lookupTag(var1, var2, var3);
         this.workStack[this.nesting + this.attrCount - 1] = var4;
         String var5 = var4.prefix;
         String var6 = var4.local;
         this.attrLocalName = var6;
         this.attrPrefix = var5;
         if(var5 != null) {
            if(var5 == "xmlns") {
               this.currentNamespacePrefix = var6;
            }
         } else if(var6 == "xmlns" && var5 == null) {
            this.currentNamespacePrefix = "";
         }

         if(this.currentNamespacePrefix == null || this.namespacePrefixes) {
            this.tlist.startAttribute(0);
            return;
         }
      }

   }

   public void emitStartElement(char[] var1, int var2, int var3) {
      this.closeStartTag();
      MappingInfo var4 = this.lookupTag(var1, var2, var3);
      this.startElementCommon();
      this.ensureSpaceInWorkStack(this.nesting - 1);
      this.workStack[this.nesting - 1] = var4;
   }

   public void endAttribute() {
      if(this.attrLocalName != null) {
         if(this.previous == 1) {
            this.previous = 0;
            return;
         }

         if(this.stringizingElementNesting >= 0) {
            --this.ignoringLevel;
         }

         int var3 = this.stringizingLevel - 1;
         this.stringizingLevel = var3;
         if(var3 == 0) {
            char[] var1;
            int var4;
            int var5;
            int var6;
            if(this.attrLocalName == "id" && this.attrPrefix == "xml") {
               var4 = this.startIndexes[this.attrCount - 1] + 5;
               var6 = this.tlist.gapStart;
               var1 = this.tlist.data;

               for(var3 = var4; var3 < var6; var3 = var5) {
                  var5 = var3 + 1;
                  char var15 = var1[var3];
                  if(('\uffff' & var15) > '\u9fff' || var15 == 9 || var15 == 13 || var15 == 10 || var15 == 32 && (var5 == var6 || var1[var5] == 32)) {
                     StringBuffer var12 = new StringBuffer();
                     this.tlist.stringValue(var4, var6, var12);
                     this.tlist.gapStart = var4;
                     this.tlist.write(TextUtils.replaceWhitespace(var12.toString(), true));
                     break;
                  }
               }
            }

            this.attrLocalName = null;
            this.attrPrefix = null;
            if(this.currentNamespacePrefix == null || this.namespacePrefixes) {
               this.tlist.endAttribute();
            }

            if(this.currentNamespacePrefix != null) {
               int var9 = this.startIndexes[this.attrCount - 1];
               var3 = var9;
               int var11 = this.tlist.gapStart;
               int var10 = var11 - var9;
               char[] var2 = this.tlist.data;
               var4 = 0;
               var5 = var9;

               int var7;
               int var8;
               while(true) {
                  var1 = var2;
                  var8 = var3;
                  var7 = var10;
                  var6 = var4;
                  if(var5 >= var11) {
                     break;
                  }

                  char var16 = var2[var5];
                  if(('\uffff' & var16) > '\u9fff') {
                     StringBuffer var13 = new StringBuffer();
                     this.tlist.stringValue(var3, var11, var13);
                     var6 = var13.hashCode();
                     var8 = 0;
                     var7 = var13.length();
                     var1 = new char[var13.length()];
                     var13.getChars(0, var7, var1, 0);
                     break;
                  }

                  var4 = var4 * 31 + var16;
                  ++var5;
               }

               this.tlist.gapStart = var9;
               String var14;
               if(this.currentNamespacePrefix == "") {
                  var14 = null;
               } else {
                  var14 = this.currentNamespacePrefix;
               }

               this.namespaceBindings = this.lookupNamespaceBinding(var14, var1, var8, var7, var6, this.namespaceBindings).namespaces;
               this.currentNamespacePrefix = null;
               return;
            }
         }
      }

   }

   public void endDocument() {
      if(this.stringizingLevel > 0) {
         this.writeJoiner();
      } else {
         this.nesting -= 2;
         this.namespaceBindings = (NamespaceBinding)this.workStack[this.nesting];
         this.workStack[this.nesting] = null;
         this.workStack[this.nesting + 1] = null;
         if(this.nesting == 0) {
            this.base.endDocument();
         } else {
            this.writeJoiner();
         }
      }
   }

   public void endElement() {
      this.closeStartTag();
      this.nesting -= 2;
      this.previous = 0;
      if(this.stringizingLevel == 0) {
         this.namespaceBindings = (NamespaceBinding)this.workStack[this.nesting];
         this.workStack[this.nesting] = null;
         this.workStack[this.nesting + 1] = null;
         this.base.endElement();
      } else if(this.stringizingElementNesting == this.nesting) {
         this.stringizingElementNesting = -1;
         this.previous = 2;
         return;
      }

   }

   public void endElement(String var1) throws SAXException {
      this.endElement();
   }

   public void endElement(String var1, String var2, String var3) {
      this.endElement();
   }

   public void endEntity() {
      if(this.base instanceof XConsumer) {
         ((XConsumer)this.base).endEntity();
      }

   }

   public void endPrefixMapping(String var1) {
      this.namespaceBindings = this.namespaceBindings.getNext();
   }

   public void error(char var1, String var2) {
      if(this.messages == null) {
         throw new RuntimeException(var2);
      } else if(this.locator != null) {
         this.messages.error(var1, (SourceLocator)this.locator, (String)var2);
      } else {
         this.messages.error(var1, var2);
      }
   }

   public NamespaceBinding findNamespaceBinding(String var1, String var2, NamespaceBinding var3) {
      int var6;
      if(var2 == null) {
         var6 = 0;
      } else {
         var6 = var2.hashCode();
      }

      int var7 = var6;
      if(var1 != null) {
         var7 = var6 ^ var1.hashCode();
      }

      var6 = var7 & this.mappingTableMask;

      for(MappingInfo var4 = this.mappingTable[var6]; var4 != null; var4 = var4.nextInBucket) {
         if(var4.tagHash == var7 && var4.prefix == var1) {
            NamespaceBinding var5 = var4.namespaces;
            if(var5 != null && var5.getNext() == this.namespaceBindings && var5.getPrefix() == var1 && var4.uri == var2) {
               return var4.namespaces;
            }
         }
      }

      MappingInfo var8 = new MappingInfo();
      var8.nextInBucket = this.mappingTable[var6];
      this.mappingTable[var6] = var8;
      var8.tagHash = var7;
      var8.prefix = var1;
      var8.local = var2;
      var8.uri = var2;
      String var9 = var2;
      if(var2 == "") {
         var9 = null;
      }

      var8.namespaces = new NamespaceBinding(var1, var9, var3);
      return var8.namespaces;
   }

   public int getColumnNumber() {
      if(this.in != null) {
         int var1 = this.in.getColumnNumber();
         if(var1 > 0) {
            return var1;
         }
      }

      return -1;
   }

   public String getFileName() {
      return this.in == null?null:this.in.getName();
   }

   public int getLineNumber() {
      if(this.in != null) {
         int var1 = this.in.getLineNumber();
         if(var1 >= 0) {
            return var1 + 1;
         }
      }

      return -1;
   }

   public String getPublicId() {
      return null;
   }

   public String getSystemId() {
      return this.in == null?null:this.in.getName();
   }

   public void ignorableWhitespace(char[] var1, int var2, int var3) throws SAXException {
      this.write((char[])var1, var2, var3);
   }

   public boolean ignoring() {
      return this.ignoringLevel > 0;
   }

   final boolean inElement() {
      int var1;
      for(var1 = this.nesting; var1 > 0 && this.workStack[var1 - 1] == null; var1 -= 2) {
         ;
      }

      return var1 != 0;
   }

   public boolean isStableSourceLocation() {
      return false;
   }

   public void linefeedFromParser() {
      if(this.inElement() && this.checkWriteAtomic()) {
         this.base.write(10);
      }

   }

   public MappingInfo lookupNamespaceBinding(String var1, char[] var2, int var3, int var4, int var5, NamespaceBinding var6) {
      if(var1 != null) {
         var5 ^= var1.hashCode();
      }

      int var9 = var5 & this.mappingTableMask;

      for(MappingInfo var7 = this.mappingTable[var9]; var7 != null; var7 = var7.nextInBucket) {
         if(var7.tagHash == var5 && var7.prefix == var1) {
            NamespaceBinding var8 = var7.namespaces;
            if(var8 != null && var8.getNext() == this.namespaceBindings && var8.getPrefix() == var1 && MappingInfo.equals(var7.uri, var2, var3, var4)) {
               return var7;
            }
         }
      }

      MappingInfo var12 = new MappingInfo();
      var12.nextInBucket = this.mappingTable[var9];
      this.mappingTable[var9] = var12;
      String var11 = (new String(var2, var3, var4)).intern();
      var12.tagHash = var5;
      var12.prefix = var1;
      var12.local = var11;
      var12.uri = var11;
      String var10 = var11;
      if(var11 == "") {
         var10 = null;
      }

      var12.namespaces = new NamespaceBinding(var1, var10, var6);
      return var12;
   }

   MappingInfo lookupTag(Symbol var1) {
      String var5 = var1.getLocalPart();
      String var3 = var1.getPrefix();
      String var2 = var3;
      if(var3 == "") {
         var2 = null;
      }

      String var6 = var1.getNamespaceURI();
      int var7 = MappingInfo.hash(var2, var5);
      int var8 = var7 & this.mappingTableMask;
      MappingInfo var4 = this.mappingTable[var8];

      MappingInfo var9;
      for(var9 = var4; var9 != null; var9 = var9.nextInBucket) {
         if(var1 == var9.qname) {
            return var9;
         }

         if(var5 == var9.local && var9.qname == null && (var6 == var9.uri || var9.uri == null) && var2 == var9.prefix) {
            var9.uri = var6;
            var9.qname = var1;
            return var9;
         }
      }

      var9 = new MappingInfo();
      var9.qname = var1;
      var9.prefix = var2;
      var9.uri = var6;
      var9.local = var5;
      var9.tagHash = var7;
      var9.nextInBucket = var4;
      this.mappingTable[var8] = var4;
      return var9;
   }

   MappingInfo lookupTag(char[] var1, int var2, int var3) {
      int var7 = 0;
      int var9 = 0;
      int var8 = -1;

      int var6;
      for(var6 = 0; var6 < var3; ++var6) {
         char var10 = var1[var2 + var6];
         if(var10 == 58 && var8 < 0) {
            var8 = var6;
            byte var11 = 0;
            var9 = var7;
            var7 = var11;
         } else {
            var7 = var7 * 31 + var10;
         }
      }

      var7 ^= var9;
      var6 = var7 & this.mappingTableMask;
      MappingInfo var5 = this.mappingTable[var6];

      MappingInfo var4;
      for(var4 = var5; var4 != null; var4 = var4.nextInBucket) {
         if(var7 == var4.tagHash && var4.match(var1, var2, var3)) {
            return var4;
         }
      }

      var4 = new MappingInfo();
      var4.tagHash = var7;
      if(var8 >= 0) {
         var4.prefix = (new String(var1, var2, var8)).intern();
         var7 = var8 + 1;
         var4.local = (new String(var1, var2 + var7, var3 - var7)).intern();
      } else {
         var4.prefix = null;
         var4.local = (new String(var1, var2, var3)).intern();
      }

      var4.nextInBucket = var5;
      this.mappingTable[var6] = var5;
      return var4;
   }

   public void processingInstruction(String var1, String var2) {
      char[] var3 = var2.toCharArray();
      this.processingInstructionCommon(var1, var3, 0, var3.length);
   }

   void processingInstructionCommon(String var1, char[] var2, int var3, int var4) {
      if(this.stringizingLevel == 0) {
         this.closeStartTag();
         if(this.base instanceof XConsumer) {
            ((XConsumer)this.base).writeProcessingInstruction(var1, var2, var3, var4);
         }
      } else if(this.stringizingElementNesting < 0) {
         this.base.write((char[])var2, var3, var4);
         return;
      }

   }

   public void processingInstructionFromParser(char[] var1, int var2, int var3, int var4, int var5) {
      if(var3 != 3 || this.inElement() || var1[var2] != 120 || var1[var2 + 1] != 109 || var1[var2 + 2] != 108) {
         this.processingInstructionCommon(new String(var1, var2, var3), var1, var4, var5);
      }
   }

   public void setDocumentLocator(Locator var1) {
      if(var1 instanceof SourceLocator) {
         this.locator = (SourceLocator)var1;
      }

   }

   public void setMessages(SourceMessages var1) {
      this.messages = var1;
   }

   public void setSourceLocator(LineBufferedReader var1) {
      this.in = var1;
      this.locator = this;
   }

   public void setSourceLocator(SourceLocator var1) {
      this.locator = var1;
   }

   public void skippedEntity(String var1) {
   }

   public void startAttribute(Object var1) {
      this.previous = 0;
      if(var1 instanceof Symbol) {
         Symbol var3 = (Symbol)var1;
         String var2 = var3.getLocalPart();
         this.attrLocalName = var2;
         this.attrPrefix = var3.getPrefix();
         String var4 = var3.getNamespaceURI();
         if(var4 == "http://www.w3.org/2000/xmlns/" || var4 == "" && var2 == "xmlns") {
            this.error('e', "arttribute name cannot be \'xmlns\' or in xmlns namespace");
         }
      }

      if(this.nesting == 2 && this.workStack[1] == null) {
         this.error('e', "attribute not allowed at document level");
      }

      if(this.attrCount < 0 && this.nesting > 0) {
         this.error('e', "attribute \'" + var1 + "\' follows non-attribute content");
      }

      if(this.startAttributeCommon()) {
         this.workStack[this.nesting + this.attrCount - 1] = var1;
         if(this.nesting == 0) {
            this.base.startAttribute(var1);
         } else {
            this.tlist.startAttribute(0);
         }
      }
   }

   public void startDocument() {
      this.closeStartTag();
      if(this.stringizingLevel > 0) {
         this.writeJoiner();
      } else {
         if(this.nesting == 0) {
            this.base.startDocument();
         } else {
            this.writeJoiner();
         }

         this.ensureSpaceInWorkStack(this.nesting);
         this.workStack[this.nesting] = this.namespaceBindings;
         this.workStack[this.nesting + 1] = null;
         this.nesting += 2;
      }
   }

   public void startElement(Object var1) {
      this.startElementCommon();
      if(this.stringizingLevel == 0) {
         this.ensureSpaceInWorkStack(this.nesting - 1);
         this.workStack[this.nesting - 1] = var1;
         if(this.copyNamespacesMode != 0) {
            NamespaceBinding var4;
            if(this.copyNamespacesMode != 1 && this.nesting != 2) {
               int var3 = 2;

               NamespaceBinding var2;
               while(true) {
                  if(var3 == this.nesting) {
                     var2 = null;
                     break;
                  }

                  if(this.workStack[var3 + 1] != null) {
                     var2 = (NamespaceBinding)this.workStack[var3];
                     break;
                  }

                  var3 += 2;
               }

               if(var2 == null) {
                  if(var1 instanceof XName) {
                     var4 = ((XName)var1).getNamespaceNodes();
                  } else {
                     var4 = NamespaceBinding.predefinedXML;
                  }

                  this.namespaceBindings = var4;
                  return;
               }

               if(this.copyNamespacesMode == 2) {
                  this.namespaceBindings = var2;
                  return;
               }

               if(var1 instanceof XName) {
                  var4 = ((XName)var1).getNamespaceNodes();
                  if(NamespaceBinding.commonAncestor(var2, var4) == var2) {
                     this.namespaceBindings = var4;
                     return;
                  }

                  this.namespaceBindings = this.mergeHelper(var2, var4);
                  return;
               }

               this.namespaceBindings = var2;
               return;
            }

            if(var1 instanceof XName) {
               var4 = ((XName)var1).getNamespaceNodes();
            } else {
               var4 = NamespaceBinding.predefinedXML;
            }

            this.namespaceBindings = var4;
            return;
         }

         this.namespaceBindings = NamespaceBinding.predefinedXML;
      }

   }

   public void startElement(String var1, String var2, String var3, Attributes var4) {
      this.startElement(Symbol.make(var1, var2));
      int var6 = var4.getLength();

      for(int var5 = 0; var5 < var6; ++var5) {
         this.startAttribute(Symbol.make(var4.getURI(var5), var4.getLocalName(var5)));
         this.write(var4.getValue(var5));
         this.endAttribute();
      }

   }

   public void startElement(String var1, AttributeList var2) {
      this.startElement(var1.intern());
      int var5 = var2.getLength();

      for(int var4 = 0; var4 < var5; ++var4) {
         var1 = var2.getName(var4).intern();
         var2.getType(var4);
         String var3 = var2.getValue(var4);
         this.startAttribute(var1);
         this.write(var3);
         this.endAttribute();
      }

   }

   protected void startElementCommon() {
      this.closeStartTag();
      if(this.stringizingLevel == 0) {
         this.ensureSpaceInWorkStack(this.nesting);
         this.workStack[this.nesting] = this.namespaceBindings;
         this.tlist.startElement(0);
         this.base = this.tlist;
         this.attrCount = 0;
      } else {
         if(this.previous == 2 && this.stringizingElementNesting < 0) {
            this.write(32);
         }

         this.previous = 0;
         if(this.stringizingElementNesting < 0) {
            this.stringizingElementNesting = this.nesting;
         }
      }

      this.nesting += 2;
   }

   public void startPrefixMapping(String var1, String var2) {
      this.namespaceBindings = this.findNamespaceBinding(var1.intern(), var2.intern(), this.namespaceBindings);
   }

   public void textFromParser(char[] var1, int var2, int var3) {
      if(!this.inElement()) {
         for(int var4 = 0; var4 != var3; ++var4) {
            if(!Character.isWhitespace(var1[var2 + var4])) {
               this.error('e', "text at document level");
               return;
            }
         }
      } else if(var3 > 0 && this.checkWriteAtomic()) {
         this.base.write((char[])var1, var2, var3);
         return;
      }

   }

   public void write(int var1) {
      if(this.checkWriteAtomic()) {
         this.base.write(var1);
      }

   }

   public void write(CharSequence var1, int var2, int var3) {
      if(var3 == 0) {
         this.writeJoiner();
      } else if(this.checkWriteAtomic()) {
         this.base.write((CharSequence)var1, var2, var3);
         return;
      }

   }

   public void write(String var1) {
      this.write((CharSequence)var1, 0, var1.length());
   }

   public void write(char[] var1, int var2, int var3) {
      if(var3 == 0) {
         this.writeJoiner();
      } else if(this.checkWriteAtomic()) {
         this.base.write((char[])var1, var2, var3);
         return;
      }

   }

   public void writeBoolean(boolean var1) {
      if(this.checkWriteAtomic()) {
         this.base.writeBoolean(var1);
      }

   }

   public void writeCDATA(char[] var1, int var2, int var3) {
      if(this.checkWriteAtomic()) {
         if(!(this.base instanceof XConsumer)) {
            this.write((char[])var1, var2, var3);
            return;
         }

         ((XConsumer)this.base).writeCDATA(var1, var2, var3);
      }

   }

   public void writeComment(char[] var1, int var2, int var3) {
      this.checkValidComment(var1, var2, var3);
      this.commentFromParser(var1, var2, var3);
   }

   public void writeDocumentUri(Object var1) {
      if(this.nesting == 2 && this.base instanceof TreeList) {
         ((TreeList)this.base).writeDocumentUri(var1);
      }

   }

   public void writeDouble(double var1) {
      if(this.checkWriteAtomic()) {
         this.base.writeDouble(var1);
      }

   }

   public void writeFloat(float var1) {
      if(this.checkWriteAtomic()) {
         this.base.writeFloat(var1);
      }

   }

   public void writeInt(int var1) {
      if(this.checkWriteAtomic()) {
         this.base.writeInt(var1);
      }

   }

   protected void writeJoiner() {
      this.previous = 0;
      if(this.ignoringLevel == 0) {
         ((TreeList)this.base).writeJoiner();
      }

   }

   public void writeLong(long var1) {
      if(this.checkWriteAtomic()) {
         this.base.writeLong(var1);
      }

   }

   public void writeObject(Object var1) {
      if(this.ignoringLevel <= 0) {
         if(var1 instanceof SeqPosition) {
            SeqPosition var4 = (SeqPosition)var1;
            this.writePosition(var4.sequence, var4.getPos());
            return;
         }

         if(var1 instanceof TreeList) {
            ((TreeList)var1).consume((Consumer)this);
            return;
         }

         if(!(var1 instanceof List) || var1 instanceof CharSeq) {
            if(var1 instanceof Keyword) {
               this.startAttribute(((Keyword)var1).asSymbol());
               this.previous = 1;
               return;
            }

            this.closeStartTag();
            if(var1 instanceof UnescapedData) {
               this.base.writeObject(var1);
               this.previous = 0;
               return;
            }

            if(this.previous == 2) {
               this.write(32);
            }

            TextUtils.textValue(var1, this);
            this.previous = 2;
            return;
         }

         Iterator var3 = ((List)var1).iterator();

         for(int var2 = 0; var3.hasNext(); ++var2) {
            this.writeObject(var3.next());
         }
      }

   }

   public void writePosition(AbstractSequence var1, int var2) {
      if(this.ignoringLevel <= 0) {
         if(this.stringizingLevel > 0 && this.previous == 2) {
            if(this.stringizingElementNesting < 0) {
               this.write(32);
            }

            this.previous = 0;
         }

         var1.consumeNext(var2, this);
         if(this.stringizingLevel > 0 && this.stringizingElementNesting < 0) {
            this.previous = 2;
            return;
         }
      }

   }

   public void writeProcessingInstruction(String var1, char[] var2, int var3, int var4) {
      var1 = TextUtils.replaceWhitespace(var1, true);
      int var6 = var3 + var4;

      label31:
      while(true) {
         int var5 = var6 - 1;
         if(var5 < var3) {
            if("xml".equalsIgnoreCase(var1)) {
               this.error('e', "processing-instruction target may not be \'xml\' (ignoring case)");
            }

            if(!XName.isNCName(var1)) {
               this.error('e', "processing-instruction target \'" + var1 + "\' is not a valid Name");
            }

            this.processingInstructionCommon(var1, var2, var3, var4);
            return;
         }

         char var7 = var2[var5];

         int var8;
         char var9;
         do {
            var6 = var5;
            if(var7 != 62) {
               continue label31;
            }

            var8 = var5 - 1;
            var6 = var8;
            if(var8 < var3) {
               continue label31;
            }

            var9 = var2[var8];
            var7 = var9;
            var5 = var8;
         } while(var9 != 63);

         this.error('e', "\'?>\' is not allowed in a processing-instruction");
         var6 = var8;
      }
   }
}
