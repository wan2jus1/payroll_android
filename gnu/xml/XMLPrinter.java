package gnu.xml;

import gnu.expr.Keyword;
import gnu.kawa.xml.XmlNamespace;
import gnu.lists.AbstractSequence;
import gnu.lists.Consumable;
import gnu.lists.PositionConsumer;
import gnu.lists.SeqPosition;
import gnu.lists.UnescapedData;
import gnu.lists.XConsumer;
import gnu.mapping.OutPort;
import gnu.mapping.Symbol;
import gnu.mapping.ThreadLocation;
import gnu.math.DFloNum;
import gnu.math.RealNum;
import gnu.text.Char;
import gnu.text.Path;
import gnu.text.PrettyWriter;
import gnu.xml.NamespaceBinding;
import gnu.xml.NodeTree;
import gnu.xml.XName;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;

public class XMLPrinter extends OutPort implements PositionConsumer, XConsumer {

   private static final int COMMENT = -5;
   private static final int ELEMENT_END = -4;
   private static final int ELEMENT_START = -3;
   static final String HtmlEmptyTags = "/area/base/basefont/br/col/frame/hr/img/input/isindex/link/meta/para/";
   private static final int KEYWORD = -6;
   private static final int PROC_INST = -7;
   private static final int WORD = -2;
   public static final ThreadLocation doctypePublic = new ThreadLocation("doctype-public");
   public static final ThreadLocation doctypeSystem = new ThreadLocation("doctype-system");
   public static final ThreadLocation indentLoc = new ThreadLocation("xml-indent");
   boolean canonicalize = true;
   public boolean canonicalizeCDATA;
   Object[] elementNameStack;
   int elementNesting;
   public boolean escapeNonAscii = true;
   public boolean escapeText = true;
   boolean inAttribute = false;
   int inComment;
   boolean inDocument;
   boolean inStartTag = false;
   public boolean indentAttributes;
   boolean isHtml = false;
   boolean isHtmlOrXhtml = false;
   NamespaceBinding namespaceBindings;
   NamespaceBinding[] namespaceSaveStack;
   boolean needXMLdecl = false;
   int prev;
   public int printIndent = -1;
   boolean printXMLdecl = false;
   char savedHighSurrogate;
   public boolean strict;
   Object style;
   boolean undeclareNamespaces = false;
   public int useEmptyElementTag = 2;


   public XMLPrinter(OutPort var1, boolean var2) {
      super((OutPort)var1, var2);
      this.namespaceBindings = NamespaceBinding.predefinedXML;
      this.namespaceSaveStack = new NamespaceBinding[20];
      this.elementNameStack = new Object[20];
      this.prev = 32;
   }

   public XMLPrinter(OutputStream var1) {
      super(new OutputStreamWriter(var1), false, false);
      this.namespaceBindings = NamespaceBinding.predefinedXML;
      this.namespaceSaveStack = new NamespaceBinding[20];
      this.elementNameStack = new Object[20];
      this.prev = 32;
   }

   public XMLPrinter(OutputStream var1, Path var2) {
      super(new OutputStreamWriter(var1), true, false, var2);
      this.namespaceBindings = NamespaceBinding.predefinedXML;
      this.namespaceSaveStack = new NamespaceBinding[20];
      this.elementNameStack = new Object[20];
      this.prev = 32;
   }

   public XMLPrinter(OutputStream var1, boolean var2) {
      super(new OutputStreamWriter(var1), true, var2);
      this.namespaceBindings = NamespaceBinding.predefinedXML;
      this.namespaceSaveStack = new NamespaceBinding[20];
      this.elementNameStack = new Object[20];
      this.prev = 32;
   }

   public XMLPrinter(Writer var1) {
      super((Writer)var1);
      this.namespaceBindings = NamespaceBinding.predefinedXML;
      this.namespaceSaveStack = new NamespaceBinding[20];
      this.elementNameStack = new Object[20];
      this.prev = 32;
   }

   public XMLPrinter(Writer var1, boolean var2) {
      super((Writer)var1, var2);
      this.namespaceBindings = NamespaceBinding.predefinedXML;
      this.namespaceSaveStack = new NamespaceBinding[20];
      this.elementNameStack = new Object[20];
      this.prev = 32;
   }

   static String formatDecimal(String var0) {
      if(var0.indexOf(46) >= 0) {
         int var3 = var0.length();
         int var2 = var3;

         int var1;
         char var4;
         do {
            var1 = var2 - 1;
            var4 = var0.charAt(var1);
            var2 = var1;
         } while(var4 == 48);

         var2 = var1;
         if(var4 != 46) {
            var2 = var1 + 1;
         }

         if(var2 != var3) {
            return var0.substring(0, var2);
         }
      }

      return var0;
   }

   public static String formatDecimal(BigDecimal var0) {
      return formatDecimal((String)var0.toPlainString());
   }

   public static String formatDouble(double var0) {
      if(Double.isNaN(var0)) {
         return "NaN";
      } else {
         boolean var5;
         if(var0 < 0.0D) {
            var5 = true;
         } else {
            var5 = false;
         }

         if(Double.isInfinite(var0)) {
            return var5?"-INF":"INF";
         } else {
            double var2;
            if(var5) {
               var2 = -var0;
            } else {
               var2 = var0;
            }

            String var4 = Double.toString(var0);
            return (var2 >= 1000000.0D || var2 < 1.0E-6D) && var2 != 0.0D?RealNum.toStringScientific(var4):formatDecimal((String)RealNum.toStringDecimal(var4));
         }
      }
   }

   public static String formatFloat(float var0) {
      if(Float.isNaN(var0)) {
         return "NaN";
      } else {
         boolean var3;
         if(var0 < 0.0F) {
            var3 = true;
         } else {
            var3 = false;
         }

         if(Float.isInfinite(var0)) {
            return var3?"-INF":"INF";
         } else {
            float var1;
            if(var3) {
               var1 = -var0;
            } else {
               var1 = var0;
            }

            String var2 = Float.toString(var0);
            return (var1 >= 1000000.0F || (double)var1 < 1.0E-6D) && (double)var1 != 0.0D?RealNum.toStringScientific(var2):formatDecimal((String)RealNum.toStringDecimal(var2));
         }
      }
   }

   public static boolean isHtmlEmptyElementTag(String var0) {
      int var1 = "/area/base/basefont/br/col/frame/hr/img/input/isindex/link/meta/para/".indexOf(var0);
      return var1 > 0 && "/area/base/basefont/br/col/frame/hr/img/input/isindex/link/meta/para/".charAt(var1 - 1) == 47 && "/area/base/basefont/br/col/frame/hr/img/input/isindex/link/meta/para/".charAt(var0.length() + var1) == 47;
   }

   public static XMLPrinter make(OutPort var0, Object var1) {
      XMLPrinter var2 = new XMLPrinter(var0, true);
      var2.setStyle(var1);
      return var2;
   }

   private void startWord() {
      this.closeTag();
      this.writeWordStart();
   }

   public static String toString(Object var0) {
      StringWriter var1 = new StringWriter();
      (new XMLPrinter(var1)).writeObject(var0);
      return var1.toString();
   }

   public void beginComment() {
      this.closeTag();
      if(this.printIndent >= 0 && (this.prev == -3 || this.prev == -4 || this.prev == -5)) {
         byte var1;
         if(this.printIndent > 0) {
            var1 = 82;
         } else {
            var1 = 78;
         }

         this.writeBreak(var1);
      }

      this.bout.write((String)"<!--");
      this.inComment = 1;
   }

   public void beginEntity(Object var1) {
   }

   public void closeTag() {
      if(this.inStartTag && !this.inAttribute) {
         if(this.printIndent >= 0 && this.indentAttributes) {
            this.endLogicalBlock("");
         }

         this.bout.write(62);
         this.inStartTag = false;
         this.prev = -3;
      } else if(this.needXMLdecl) {
         this.bout.write((String)"<?xml version=\"1.0\"?>\n");
         if(this.printIndent >= 0) {
            this.startLogicalBlock("", "", 2);
         }

         this.needXMLdecl = false;
         this.prev = 62;
         return;
      }

   }

   public void consume(SeqPosition var1) {
      var1.sequence.consumeNext(var1.ipos, this);
   }

   public void endAttribute() {
      if(this.inAttribute) {
         if(this.prev != -6) {
            this.bout.write(34);
            this.inAttribute = false;
         }

         this.prev = 32;
      }

   }

   public void endComment() {
      this.bout.write((String)"-->");
      this.prev = -5;
      this.inComment = 0;
   }

   public void endDocument() {
      this.inDocument = false;
      if(this.printIndent >= 0) {
         this.endLogicalBlock("");
      }

      this.freshLine();
   }

   public void endElement() {
      if(this.useEmptyElementTag == 0) {
         this.closeTag();
      }

      Object var4 = this.elementNameStack[this.elementNesting - 1];
      String var3 = this.getHtmlTag(var4);
      if(this.inStartTag) {
         if(this.printIndent >= 0 && this.indentAttributes) {
            this.endLogicalBlock("");
         }

         String var2 = null;
         boolean var6;
         if(var3 != null && isHtmlEmptyElementTag(var3)) {
            var6 = true;
         } else {
            var6 = false;
         }

         String var1;
         label81: {
            if(this.useEmptyElementTag != 0) {
               var1 = var2;
               if(var3 == null) {
                  break label81;
               }

               var1 = var2;
               if(var6) {
                  break label81;
               }
            }

            var1 = var2;
            if(var4 instanceof Symbol) {
               Symbol var5 = (Symbol)var4;
               var1 = var5.getPrefix();
               String var8 = var5.getNamespaceURI();
               String var9 = var5.getLocalName();
               if(var1 != "") {
                  var1 = "></" + var1 + ":" + var9 + ">";
               } else {
                  label73: {
                     if(var8 != "") {
                        var1 = var2;
                        if(var8 != null) {
                           break label73;
                        }
                     }

                     var1 = "></" + var9 + ">";
                  }
               }
            }
         }

         var2 = var1;
         if(var1 == null) {
            if(var6 && this.isHtml) {
               var2 = ">";
            } else if(this.useEmptyElementTag == 2) {
               var2 = " />";
            } else {
               var2 = "/>";
            }
         }

         this.bout.write((String)var2);
         this.inStartTag = false;
      } else {
         if(this.printIndent >= 0) {
            this.setIndentation(0, false);
            if(this.prev == -4) {
               byte var10;
               if(this.printIndent > 0) {
                  var10 = 82;
               } else {
                  var10 = 78;
               }

               this.writeBreak(var10);
            }
         }

         this.bout.write((String)"</");
         this.writeQName(var4);
         this.bout.write((String)">");
      }

      if(this.printIndent >= 0) {
         this.endLogicalBlock("");
      }

      this.prev = -4;
      if(var3 != null && !this.escapeText && ("script".equals(var3) || "style".equals(var3))) {
         this.escapeText = true;
      }

      NamespaceBinding[] var7 = this.namespaceSaveStack;
      int var11 = this.elementNesting - 1;
      this.elementNesting = var11;
      this.namespaceBindings = var7[var11];
      this.namespaceSaveStack[this.elementNesting] = null;
      this.elementNameStack[this.elementNesting] = null;
   }

   public void endEntity() {
   }

   protected void endNumber() {
      this.writeWordEnd();
   }

   public void error(String var1, String var2) {
      throw new RuntimeException("serialization error: " + var1 + " [" + var2 + ']');
   }

   protected String getHtmlTag(Object var1) {
      if(var1 instanceof Symbol) {
         Symbol var3 = (Symbol)var1;
         String var2 = var3.getNamespaceURI();
         if(var2 == "http://www.w3.org/1999/xhtml" || this.isHtmlOrXhtml && var2 == "") {
            return var3.getLocalPart();
         }
      } else if(this.isHtmlOrXhtml) {
         return var1.toString();
      }

      return null;
   }

   public boolean ignoring() {
      return false;
   }

   boolean mustHexEscape(int var1) {
      return var1 >= 127 && (var1 <= 159 || this.escapeNonAscii) || var1 == 8232 || var1 < 32 && (this.inAttribute || var1 != 9 && var1 != 10);
   }

   public void print(Object var1) {
      Object var2;
      if(var1 instanceof BigDecimal) {
         var2 = formatDecimal((BigDecimal)((BigDecimal)var1));
      } else if(!(var1 instanceof Double) && !(var1 instanceof DFloNum)) {
         var2 = var1;
         if(var1 instanceof Float) {
            var2 = formatFloat(((Float)var1).floatValue());
         }
      } else {
         var2 = formatDouble(((Number)var1).doubleValue());
      }

      String var3;
      if(var2 == null) {
         var3 = "(null)";
      } else {
         var3 = var2.toString();
      }

      this.write(var3);
   }

   void setIndentMode() {
      String var1 = null;
      Object var2 = indentLoc.get((Object)null);
      if(var2 != null) {
         var1 = var2.toString();
      }

      if(var1 == null) {
         this.printIndent = -1;
      } else if(var1.equals("pretty")) {
         this.printIndent = 0;
      } else if(!var1.equals("always") && !var1.equals("yes")) {
         this.printIndent = -1;
      } else {
         this.printIndent = 1;
      }
   }

   public void setPrintXMLdecl(boolean var1) {
      this.printXMLdecl = var1;
   }

   public void setStyle(Object var1) {
      this.style = var1;
      byte var2;
      if(this.canonicalize) {
         var2 = 0;
      } else {
         var2 = 1;
      }

      this.useEmptyElementTag = var2;
      if("html".equals(var1)) {
         this.isHtml = true;
         this.isHtmlOrXhtml = true;
         this.useEmptyElementTag = 2;
         if(this.namespaceBindings == NamespaceBinding.predefinedXML) {
            this.namespaceBindings = XmlNamespace.HTML_BINDINGS;
         }
      } else if(this.namespaceBindings == XmlNamespace.HTML_BINDINGS) {
         this.namespaceBindings = NamespaceBinding.predefinedXML;
      }

      if("xhtml".equals(var1)) {
         this.isHtmlOrXhtml = true;
         this.useEmptyElementTag = 2;
      }

      if("plain".equals(var1)) {
         this.escapeText = false;
      }

   }

   public void startAttribute(Object var1) {
      if(!this.inStartTag && this.strict) {
         this.error("attribute not in element", "SENR0001");
      }

      if(this.inAttribute) {
         this.bout.write(34);
      }

      this.inAttribute = true;
      this.bout.write(32);
      if(this.printIndent >= 0) {
         this.writeBreakFill();
      }

      this.bout.write((String)var1.toString());
      this.bout.write((String)"=\"");
      this.prev = 32;
   }

   public void startDocument() {
      if(this.printXMLdecl) {
         this.needXMLdecl = true;
      }

      this.setIndentMode();
      this.inDocument = true;
      if(this.printIndent >= 0 && !this.needXMLdecl) {
         this.startLogicalBlock("", "", 2);
      }

   }

   public void startElement(Object var1) {
      this.closeTag();
      String var14;
      if(this.elementNesting == 0) {
         if(!this.inDocument) {
            this.setIndentMode();
         }

         if(this.prev == -7) {
            this.write(10);
         }

         Object var2 = doctypeSystem.get((Object)null);
         if(var2 != null) {
            String var3 = var2.toString();
            if(var3.length() > 0) {
               var2 = doctypePublic.get((Object)null);
               this.bout.write((String)"<!DOCTYPE ");
               this.bout.write((String)var1.toString());
               if(var2 == null) {
                  var14 = null;
               } else {
                  var14 = var2.toString();
               }

               if(var14 != null && var14.length() > 0) {
                  this.bout.write((String)" PUBLIC \"");
                  this.bout.write((String)var14);
                  this.bout.write((String)"\" \"");
               } else {
                  this.bout.write((String)" SYSTEM \"");
               }

               this.bout.write((String)var3);
               this.bout.write((String)"\">");
               this.println();
            }
         }
      }

      if(this.printIndent >= 0) {
         if(this.prev == -3 || this.prev == -4 || this.prev == -5) {
            byte var9;
            if(this.printIndent > 0) {
               var9 = 82;
            } else {
               var9 = 78;
            }

            this.writeBreak(var9);
         }

         this.startLogicalBlock("", "", 2);
      }

      this.bout.write(60);
      this.writeQName(var1);
      if(this.printIndent >= 0 && this.indentAttributes) {
         this.startLogicalBlock("", "", 2);
      }

      this.elementNameStack[this.elementNesting] = var1;
      NamespaceBinding[] var16 = this.namespaceSaveStack;
      int var21 = this.elementNesting;
      this.elementNesting = var21 + 1;
      var16[var21] = this.namespaceBindings;
      if(var1 instanceof XName) {
         NamespaceBinding var15 = ((XName)var1).namespaceNodes;
         NamespaceBinding var4 = NamespaceBinding.commonAncestor(var15, this.namespaceBindings);
         if(var15 == null) {
            var21 = 0;
         } else {
            var21 = var15.count(var4);
         }

         NamespaceBinding[] var5 = new NamespaceBinding[var21];
         var21 = 0;
         boolean var12 = this.canonicalize;

         String var6;
         int var10;
         NamespaceBinding var17;
         for(var17 = var15; var17 != var4; var17 = var17.next) {
            var10 = var21;
            var17.getUri();
            var6 = var17.getPrefix();

            while(true) {
               int var11 = var10 - 1;
               if(var11 >= 0) {
                  NamespaceBinding var7 = var5[var11];
                  String var8 = var7.getPrefix();
                  if(var6 == var8) {
                     break;
                  }

                  var10 = var11;
                  if(!var12) {
                     continue;
                  }

                  if(var6 != null && (var8 == null || var6.compareTo(var8) > 0)) {
                     var5[var11 + 1] = var7;
                     var10 = var11;
                     continue;
                  }
               }

               if(var12) {
                  var10 = var11 + 1;
               } else {
                  var10 = var21;
               }

               var5[var10] = var17;
               ++var21;
               break;
            }
         }

         while(true) {
            var10 = var21 - 1;
            if(var10 < 0) {
               if(this.undeclareNamespaces) {
                  for(var17 = this.namespaceBindings; var17 != var4; var17 = var17.next) {
                     String var18 = var17.prefix;
                     if(var17.uri != null && var15.resolve(var18) == null) {
                        this.bout.write(32);
                        if(var18 == null) {
                           this.bout.write((String)"xmlns");
                        } else {
                           this.bout.write((String)"xmlns:");
                           this.bout.write((String)var18);
                        }

                        this.bout.write((String)"=\"\"");
                     }
                  }
               }

               this.namespaceBindings = var15;
               break;
            }

            NamespaceBinding var20 = var5[var10];
            var14 = var20.prefix;
            var6 = var20.uri;
            var21 = var10;
            if(var6 != this.namespaceBindings.resolve(var14)) {
               if(var6 == null && var14 != null) {
                  var21 = var10;
                  if(!this.undeclareNamespaces) {
                     continue;
                  }
               }

               this.bout.write(32);
               if(var14 == null) {
                  this.bout.write((String)"xmlns");
               } else {
                  this.bout.write((String)"xmlns:");
                  this.bout.write((String)var14);
               }

               this.bout.write((String)"=\"");
               this.inAttribute = true;
               if(var6 != null) {
                  this.write(var6);
               }

               this.inAttribute = false;
               this.bout.write(34);
               var21 = var10;
            }
         }
      }

      if(this.elementNesting >= this.namespaceSaveStack.length) {
         var16 = new NamespaceBinding[this.elementNesting * 2];
         System.arraycopy(this.namespaceSaveStack, 0, var16, 0, this.elementNesting);
         this.namespaceSaveStack = var16;
         Object[] var19 = new Object[this.elementNesting * 2];
         System.arraycopy(this.elementNameStack, 0, var19, 0, this.elementNesting);
         this.elementNameStack = var19;
      }

      this.inStartTag = true;
      String var13 = this.getHtmlTag(var1);
      if("script".equals(var13) || "style".equals(var13)) {
         this.escapeText = false;
      }

   }

   protected void startNumber() {
      this.startWord();
   }

   public void write(int var1) {
      this.closeTag();
      if(this.printIndent >= 0 && (var1 == 13 || var1 == 10)) {
         if(var1 != 10 || this.prev != 13) {
            this.writeBreak(82);
         }

         if(this.inComment > 0) {
            this.inComment = 1;
         }

      } else if(!this.escapeText) {
         this.bout.write(var1);
         this.prev = var1;
      } else if(this.inComment > 0) {
         if(var1 == 45) {
            if(this.inComment == 1) {
               this.inComment = 2;
            } else {
               this.bout.write(32);
            }
         } else {
            this.inComment = 1;
         }

         super.write(var1);
      } else {
         this.prev = 59;
         if(var1 == 60 && (!this.isHtml || !this.inAttribute)) {
            this.bout.write((String)"&lt;");
         } else if(var1 == 62) {
            this.bout.write((String)"&gt;");
         } else if(var1 == 38) {
            this.bout.write((String)"&amp;");
         } else if(var1 == 34 && this.inAttribute) {
            this.bout.write((String)"&quot;");
         } else if(this.mustHexEscape(var1)) {
            int var3 = var1;
            if(var1 >= '\ud800') {
               if(var1 < '\udc00') {
                  this.savedHighSurrogate = (char)var1;
                  return;
               }

               var3 = var1;
               if(var1 < '\ue000') {
                  var3 = (this.savedHighSurrogate - '\ud800') * 1024 + (var1 - '\udc00') + 65536;
                  this.savedHighSurrogate = 0;
               }
            }

            this.bout.write((String)("&#x" + Integer.toHexString(var3).toUpperCase() + ";"));
         } else {
            this.bout.write(var1);
            this.prev = var1;
         }
      }
   }

   public void write(String var1, int var2, int var3) {
      if(var3 > 0) {
         this.closeTag();
         int var5 = var2 + var3;
         byte var4 = 0;
         var3 = var2;

         int var7;
         for(var2 = var4; var3 < var5; var3 = var7) {
            var7 = var3 + 1;
            char var6 = var1.charAt(var3);
            if(!this.mustHexEscape(var6)) {
               label46: {
                  if(this.inComment > 0) {
                     if(var6 == 45 || this.inComment == 2) {
                        break label46;
                     }
                  } else if(var6 == 60 || var6 == 62 || var6 == 38 || this.inAttribute && (var6 == 34 || var6 < 32)) {
                     break label46;
                  }

                  ++var2;
                  continue;
               }
            }

            if(var2 > 0) {
               this.bout.write((String)var1, var7 - 1 - var2, var2);
            }

            this.write(var6);
            var2 = 0;
         }

         if(var2 > 0) {
            this.bout.write((String)var1, var5 - var2, var2);
         }
      }

      this.prev = 45;
   }

   public void write(char[] var1, int var2, int var3) {
      if(var3 > 0) {
         this.closeTag();
         int var5 = var2 + var3;
         byte var4 = 0;
         var3 = var2;

         int var7;
         for(var2 = var4; var3 < var5; var3 = var7) {
            var7 = var3 + 1;
            char var6 = var1[var3];
            if(!this.mustHexEscape(var6)) {
               label46: {
                  if(this.inComment > 0) {
                     if(var6 == 45 || this.inComment == 2) {
                        break label46;
                     }
                  } else if(var6 == 60 || var6 == 62 || var6 == 38 || this.inAttribute && (var6 == 34 || var6 < 32)) {
                     break label46;
                  }

                  ++var2;
                  continue;
               }
            }

            if(var2 > 0) {
               this.bout.write((char[])var1, var7 - 1 - var2, var2);
            }

            this.write(var6);
            var2 = 0;
         }

         if(var2 > 0) {
            this.bout.write((char[])var1, var5 - var2, var2);
         }
      }

      this.prev = 45;
   }

   public void writeBaseUri(Object var1) {
   }

   public void writeBoolean(boolean var1) {
      this.startWord();
      super.print(var1);
      this.writeWordEnd();
   }

   public void writeCDATA(char[] var1, int var2, int var3) {
      if(this.canonicalizeCDATA) {
         this.write((char[])var1, var2, var3);
      } else {
         this.closeTag();
         this.bout.write((String)"<![CDATA[");
         int var8 = var2 + var3;
         int var4 = var3;
         int var6 = var2;

         for(var2 = var2; var2 < var8 - 2; var4 = var3) {
            int var7 = var2;
            int var5 = var6;
            var3 = var4;
            if(var1[var2] == 93) {
               var7 = var2;
               var5 = var6;
               var3 = var4;
               if(var1[var2 + 1] == 93) {
                  var7 = var2;
                  var5 = var6;
                  var3 = var4;
                  if(var1[var2 + 2] == 62) {
                     if(var2 > var6) {
                        this.bout.write((char[])var1, var6, var2 - var6);
                     }

                     this.print("]]]><![CDATA[]>");
                     var5 = var2 + 3;
                     var3 = var8 - var5;
                     var7 = var2 + 2;
                  }
               }
            }

            var2 = var7 + 1;
            var6 = var5;
         }

         this.bout.write((char[])var1, var6, var4);
         this.bout.write((String)"]]>");
         this.prev = 62;
      }
   }

   public void writeComment(String var1) {
      this.beginComment();
      this.write(var1);
      this.endComment();
   }

   public void writeComment(char[] var1, int var2, int var3) {
      this.beginComment();
      this.write((char[])var1, var2, var3);
      this.endComment();
   }

   public void writeDouble(double var1) {
      this.startWord();
      this.bout.write((String)formatDouble(var1));
   }

   public void writeFloat(float var1) {
      this.startWord();
      this.bout.write((String)formatFloat(var1));
   }

   public void writeObject(Object var1) {
      if(var1 instanceof SeqPosition) {
         this.bout.clearWordEnd();
         SeqPosition var2 = (SeqPosition)var1;
         var2.sequence.consumeNext(var2.ipos, this);
         if(var2.sequence instanceof NodeTree) {
            this.prev = 45;
         }

      } else if(var1 instanceof Consumable && !(var1 instanceof UnescapedData)) {
         ((Consumable)var1).consume(this);
      } else if(var1 instanceof Keyword) {
         this.startAttribute(((Keyword)var1).getName());
         this.prev = -6;
      } else {
         this.closeTag();
         if(var1 instanceof UnescapedData) {
            this.bout.clearWordEnd();
            this.bout.write((String)((UnescapedData)var1).getData());
            this.prev = 45;
         } else if(var1 instanceof Char) {
            Char.print(((Char)var1).intValue(), this);
         } else {
            this.startWord();
            this.prev = 32;
            this.print(var1);
            this.writeWordEnd();
            this.prev = -2;
         }
      }
   }

   public void writePosition(AbstractSequence var1, int var2) {
      var1.consumeNext(var2, this);
   }

   public void writeProcessingInstruction(String var1, char[] var2, int var3, int var4) {
      if("xml".equals(var1)) {
         this.needXMLdecl = false;
      }

      this.closeTag();
      this.bout.write((String)"<?");
      this.print(var1);
      this.print(' ');
      this.bout.write((char[])var2, var3, var4);
      this.bout.write((String)"?>");
      this.prev = -7;
   }

   protected void writeQName(Object var1) {
      if(var1 instanceof Symbol) {
         Symbol var4 = (Symbol)var1;
         String var5 = var4.getPrefix();
         if(var5 != null && var5.length() > 0) {
            this.bout.write((String)var5);
            this.bout.write(58);
         }

         this.bout.write((String)var4.getLocalPart());
      } else {
         PrettyWriter var2 = this.bout;
         String var3;
         if(var1 == null) {
            var3 = "{null name}";
         } else {
            var3 = (String)var1;
         }

         var2.write((String)var3);
      }
   }
}
