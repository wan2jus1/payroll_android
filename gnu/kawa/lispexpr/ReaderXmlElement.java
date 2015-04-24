package gnu.kawa.lispexpr;

import gnu.expr.Special;
import gnu.kawa.lispexpr.LispReader;
import gnu.kawa.lispexpr.MakeXmlElement;
import gnu.kawa.lispexpr.ReadTableEntry;
import gnu.kawa.lispexpr.ResolveNamespace;
import gnu.kawa.xml.CommentConstructor;
import gnu.kawa.xml.MakeAttribute;
import gnu.kawa.xml.MakeCDATA;
import gnu.kawa.xml.MakeProcInst;
import gnu.kawa.xml.MakeText;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import gnu.text.Lexer;
import gnu.text.SyntaxException;
import gnu.xml.XName;
import java.io.IOException;

public class ReaderXmlElement extends ReadTableEntry {

   static final String DEFAULT_ELEMENT_NAMESPACE = "[default-element-namespace]";


   public static void namedEntity(LispReader var0, String var1) {
      var1 = var1.intern();
      byte var2 = 63;
      if(var1 == "lt") {
         var2 = 60;
      } else if(var1 == "gt") {
         var2 = 62;
      } else if(var1 == "amp") {
         var2 = 38;
      } else if(var1 == "quot") {
         var2 = 34;
      } else if(var1 == "apos") {
         var2 = 39;
      } else {
         var0.error("unknown enity reference: \'" + var1 + "\'");
      }

      var0.tokenBufferAppend(var2);
   }

   public static Pair quote(Object var0) {
      return LList.list2(Namespace.EmptyNamespace.getSymbol("quote"), var0);
   }

   static void readCharRef(LispReader var0) throws IOException, SyntaxException {
      int var1 = var0.read();
      byte var2;
      if(var1 == 120) {
         var2 = 16;
         var1 = var0.read();
      } else {
         var2 = 10;
      }

      int var3;
      for(var3 = 0; var1 >= 0; var1 = var0.read()) {
         int var4 = Character.digit((char)var1, var2);
         if(var4 < 0 || var3 >= 134217728) {
            break;
         }

         var3 = var3 * var2 + var4;
      }

      if(var1 != 59) {
         var0.unread(var1);
         var0.error("invalid character reference");
      } else if((var3 <= 0 || var3 > '\ud7ff') && (var3 < '\ue000' || var3 > '�') && (var3 < 65536 || var3 > 1114111)) {
         var0.error("invalid character value " + var3);
      } else {
         var0.tokenBufferAppend(var3);
      }
   }

   public static Pair readContent(LispReader var0, char var1, Pair var2) throws IOException, SyntaxException {
      var0.tokenBufferLength = 0;
      boolean var12 = false;
      Object var4 = var2;

      while(true) {
         Object var8 = null;
         Object var16 = null;
         Object var7 = null;
         Object var9 = null;
         Object var6 = null;
         String var5 = null;
         Object var10 = null;
         String var3 = null;
         int var14 = var0.getLineNumber();
         int var15 = var0.getColumnNumber();
         int var13 = var0.read();
         boolean var18;
         if(var13 < 0) {
            var0.error("unexpected end-of-file");
            var16 = Special.eof;
            var18 = var12;
         } else {
            int var11;
            if(var13 == var1) {
               if(var1 == 60) {
                  var3 = (String)var9;
                  if(var0.tokenBufferLength > 0) {
                     var3 = var0.tokenBufferString();
                     var0.tokenBufferLength = 0;
                  }

                  var11 = var0.read();
                  if(var11 == 47) {
                     var16 = Special.eof;
                  } else {
                     var16 = readXMLConstructor(var0, var11, true);
                  }
               } else if(var0.checkNext(var1)) {
                  var0.tokenBufferAppend(var1);
                  var16 = var8;
                  var3 = (String)var6;
               } else {
                  var16 = Special.eof;
                  var3 = (String)var6;
               }

               var18 = false;
            } else if(var13 == 38) {
               var11 = var0.read();
               if(var11 == 35) {
                  readCharRef(var0);
                  var3 = var5;
               } else if(var11 != 40 && var11 != 123) {
                  var6 = readEntity(var0, var11);
                  var16 = var6;
                  var3 = var5;
                  if(var12) {
                     var16 = var6;
                     var3 = var5;
                     if(var0.tokenBufferLength == 0) {
                        var3 = "";
                        var16 = var6;
                     }
                  }
               } else {
                  label71: {
                     if(var0.tokenBufferLength <= 0) {
                        var3 = (String)var10;
                        if(!var12) {
                           break label71;
                        }
                     }

                     var3 = var0.tokenBufferString();
                  }

                  var0.tokenBufferLength = 0;
                  var16 = readEscapedExpression(var0, var11);
               }

               var18 = true;
            } else {
               var11 = var13;
               if(var1 != 60) {
                  label85: {
                     if(var13 != 9 && var13 != 10) {
                        var11 = var13;
                        if(var13 != 13) {
                           break label85;
                        }
                     }

                     var11 = 32;
                  }
               }

               if(var11 == 60) {
                  var0.error('e', "\'<\' must be quoted in a direct attribute value");
               }

               var0.tokenBufferAppend((char)var11);
               var16 = var7;
               var18 = var12;
            }
         }

         var5 = var3;
         if(var16 != null) {
            var5 = var3;
            if(var0.tokenBufferLength > 0) {
               var5 = var0.tokenBufferString();
               var0.tokenBufferLength = 0;
            }
         }

         Object var17 = var4;
         if(var5 != null) {
            var17 = PairWithPosition.make(Pair.list2(MakeText.makeText, var5), var0.makeNil(), (String)null, -1, -1);
            ((Pair)var4).setCdrBackdoor(var17);
         }

         if(var16 == Special.eof) {
            return (Pair)var17;
         }

         var12 = var18;
         var4 = var17;
         if(var16 != null) {
            var4 = PairWithPosition.make(var16, var0.makeNil(), (String)null, var14 + 1, var15);
            ((Pair)var17).setCdrBackdoor(var4);
            var12 = var18;
         }
      }
   }

   public static Object readElementConstructor(LispReader var0, int var1) throws IOException, SyntaxException {
      int var12 = var0.getLineNumber() + 1;
      int var13 = var0.getColumnNumber() - 2;
      Object var2 = readQNameExpression(var0, var1, true);
      String var3;
      if(var0.tokenBufferLength == 0) {
         var3 = null;
      } else {
         var3 = var0.tokenBufferString();
      }

      PairWithPosition var6 = PairWithPosition.make(var2, LList.Empty, var0.getName(), var12, var13);
      PairWithPosition var5 = var6;
      Object var4 = LList.Empty;

      while(true) {
         boolean var9 = false;

         for(var1 = var0.readUnicodeChar(); var1 >= 0 && Character.isWhitespace(var1); var9 = true) {
            var1 = var0.read();
         }

         String var14;
         if(var1 < 0 || var1 == 62 || var1 == 47) {
            boolean var11 = false;
            var9 = var11;
            int var10 = var1;
            if(var1 == 47) {
               var10 = var0.read();
               if(var10 == 62) {
                  var9 = true;
               } else {
                  var0.unread(var10);
                  var9 = var11;
               }
            }

            if(!var9) {
               if(var10 != 62) {
                  var0.error("missing \'>\' after start element");
                  return Boolean.FALSE;
               }

               readContent(var0, '<', var5);
               var1 = var0.readUnicodeChar();
               int var19 = var1;
               if(XName.isNameStart(var1)) {
                  var0.tokenBufferLength = 0;
                  var19 = var1;

                  do {
                     do {
                        var0.tokenBufferAppend(var19);
                        var1 = var0.readUnicodeChar();
                        var19 = var1;
                     } while(XName.isNamePart(var1));

                     var19 = var1;
                  } while(var1 == 58);

                  var14 = var0.tokenBufferString();
                  if(var3 == null || !var14.equals(var3)) {
                     String var16 = var0.getName();
                     var19 = var0.getLineNumber();
                     var10 = var0.getColumnNumber();
                     int var20 = var0.tokenBufferLength;
                     if(var3 == null) {
                        var14 = "computed start tag closed by \'</" + var14 + ">\'";
                     } else {
                        var14 = "\'<" + var3 + ">\' closed by \'</" + var14 + ">\'";
                     }

                     var0.error('e', var16, var19 + 1, var10 - var20, var14);
                  }

                  var0.tokenBufferLength = 0;
                  var19 = var1;
               }

               if(skipSpace(var0, var19) != 62) {
                  var0.error("missing \'>\' after end element");
               }
            }

            LList var17 = LList.reverseInPlace(var4);
            return PairWithPosition.make(MakeXmlElement.makeXml, Pair.make(var17, var6), var0.getName(), var12, var13);
         }

         if(!var9) {
            var0.error("missing space before attribute");
         }

         Object var8 = readQNameExpression(var0, var1, false);
         var0.getLineNumber();
         var0.getColumnNumber();
         var1 = var0.tokenBufferLength;
         PairWithPosition var7 = null;
         var14 = var7;
         if(var0.tokenBufferLength >= 5) {
            var14 = var7;
            if(var0.tokenBuffer[0] == 120) {
               var14 = var7;
               if(var0.tokenBuffer[1] == 109) {
                  var14 = var7;
                  if(var0.tokenBuffer[2] == 108) {
                     var14 = var7;
                     if(var0.tokenBuffer[3] == 110) {
                        var14 = var7;
                        if(var0.tokenBuffer[4] == 115) {
                           if(var0.tokenBufferLength == 5) {
                              var14 = "";
                           } else {
                              var14 = var7;
                              if(var0.tokenBuffer[5] == 58) {
                                 var14 = new String(var0.tokenBuffer, 6, var0.tokenBufferLength - 6);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         if(skipSpace(var0, 32) != 61) {
            var0.error("missing \'=\' after attribute");
         }

         var1 = skipSpace(var0, 32);
         var7 = PairWithPosition.make(MakeAttribute.makeAttribute, LList.Empty, var0.getName(), var12, var13);
         PairWithPosition var18 = PairWithPosition.make(var8, LList.Empty, var0.getName(), var12, var13);
         var0.setCdr(var7, var18);
         readContent(var0, (char)var1, var18);
         if(var14 != null) {
            var4 = new PairWithPosition(var18, Pair.make(var14, var18.getCdr()), var4);
         } else {
            PairWithPosition var15 = PairWithPosition.make(var7, var0.makeNil(), (String)null, -1, -1);
            var5.setCdrBackdoor(var15);
            var5 = var15;
         }
      }
   }

   static Object readEntity(LispReader var0, int var1) throws IOException, SyntaxException {
      int var3;
      for(var3 = var0.tokenBufferLength; var1 >= 0; var1 = var0.read()) {
         char var4 = (char)var1;
         if(!XName.isNamePart(var4)) {
            break;
         }

         var0.tokenBufferAppend(var4);
      }

      if(var1 != 59) {
         var0.unread(var1);
         var0.error("invalid entity reference");
         return "?";
      } else {
         String var2 = new String(var0.tokenBuffer, var3, var0.tokenBufferLength - var3);
         var0.tokenBufferLength = var3;
         namedEntity(var0, var2);
         return null;
      }
   }

   static Object readEscapedExpression(LispReader param0, int param1) throws IOException, SyntaxException {
      // $FF: Couldn't be decompiled
   }

   public static Object readQNameExpression(LispReader var0, int var1, boolean var2) throws IOException, SyntaxException {
      var0.getName();
      int var7 = var0.getLineNumber();
      int var8 = var0.getColumnNumber();
      var0.tokenBufferLength = 0;
      if(!XName.isNameStart(var1)) {
         if(var1 != 123 && var1 != 40) {
            var0.error("missing element name");
            return null;
         } else {
            return readEscapedExpression(var0, var1);
         }
      } else {
         byte var6 = -1;
         int var5 = var1;
         var1 = var6;

         while(true) {
            while(true) {
               var0.tokenBufferAppend(var5);
               int var10 = var0.readUnicodeChar();
               if(var10 == 58 && var1 < 0) {
                  var1 = var0.tokenBufferLength;
                  var5 = var10;
               } else {
                  var5 = var10;
                  if(!XName.isNamePart(var10)) {
                     var0.unread(var10);
                     if(var1 < 0 && !var2) {
                        return quote(Namespace.getDefaultSymbol(var0.tokenBufferString().intern()));
                     }

                     var5 = var0.tokenBufferLength;
                     String var4 = (new String(var0.tokenBuffer, var1 + 1, var5 - var1 - 1)).intern();
                     String var3;
                     if(var1 < 0) {
                        var3 = "[default-element-namespace]";
                     } else {
                        var3 = (new String(var0.tokenBuffer, 0, var1)).intern();
                     }

                     Symbol var9 = Namespace.EmptyNamespace.getSymbol(var3);
                     return new Pair(ResolveNamespace.resolveQName, PairWithPosition.make(var9, new Pair(var4, LList.Empty), var0.getName(), var7 + 1, var8));
                  }
               }
            }
         }
      }
   }

   static Object readXMLConstructor(LispReader var0, int var1, boolean var2) throws IOException, SyntaxException {
      int var6 = var0.getLineNumber() + 1;
      int var7 = var0.getColumnNumber() - 2;
      int var5;
      String var10;
      if(var1 == 33) {
         var1 = var0.read();
         var5 = var1;
         if(var1 == 45) {
            var1 = var0.peek();
            var5 = var1;
            if(var1 == 45) {
               var0.skip();
               if(!var0.readDelimited("-->")) {
                  var0.error('f', var0.getName(), var6, var7, "unexpected end-of-file in XML comment starting here - expected \"-->\"");
               }

               var10 = var0.tokenBufferString();
               return LList.list2(CommentConstructor.commentConstructor, var10);
            }
         }

         var1 = var5;
         if(var5 == 91) {
            var5 = var0.read();
            var1 = var5;
            if(var5 == 67) {
               var5 = var0.read();
               var1 = var5;
               if(var5 == 68) {
                  var5 = var0.read();
                  var1 = var5;
                  if(var5 == 65) {
                     var5 = var0.read();
                     var1 = var5;
                     if(var5 == 84) {
                        var5 = var0.read();
                        var1 = var5;
                        if(var5 == 65) {
                           var5 = var0.read();
                           var1 = var5;
                           if(var5 == 91) {
                              if(!var0.readDelimited("]]>")) {
                                 var0.error('f', var0.getName(), var6, var7, "unexpected end-of-file in CDATA starting here - expected \"]]>\"");
                              }

                              var10 = var0.tokenBufferString();
                              return LList.list2(MakeCDATA.makeCDATA, var10);
                           }
                        }
                     }
                  }
               }
            }
         }

         var0.error('f', var0.getName(), var6, var7, "\'<!\' must be followed by \'--\' or \'[CDATA[\'");

         while(var1 >= 0 && var1 != 62 && var1 != 10 && var1 != 13) {
            var1 = var0.read();
         }

         return null;
      } else if(var1 != 63) {
         return readElementConstructor(var0, var1);
      } else {
         label204: {
            var5 = var0.readUnicodeChar();
            if(var5 >= 0) {
               var1 = var5;
               if(XName.isNameStart(var5)) {
                  break label204;
               }
            }

            var0.error("missing target after \'<?\'");
            var1 = var5;
         }

         do {
            var0.tokenBufferAppend(var1);
            var5 = var0.readUnicodeChar();
            var1 = var5;
         } while(XName.isNamePart(var5));

         String var4 = var0.tokenBufferString();

         for(var1 = 0; var5 >= 0 && Character.isWhitespace(var5); var5 = var0.read()) {
            ++var1;
         }

         var0.unread(var5);
         char var3 = var0.pushNesting('?');

         try {
            if(!var0.readDelimited("?>")) {
               var0.error('f', var0.getName(), var6, var7, "unexpected end-of-file looking for \"?>\"");
            }
         } finally {
            var0.popNesting(var3);
         }

         if(var1 == 0 && var0.tokenBufferLength > 0) {
            var0.error("target must be followed by space or \'?>\'");
         }

         var10 = var0.tokenBufferString();
         return LList.list3(MakeProcInst.makeProcInst, var4, var10);
      }
   }

   static int skipSpace(LispReader var0, int var1) throws IOException, SyntaxException {
      while(var1 >= 0 && Character.isWhitespace(var1)) {
         var1 = var0.readUnicodeChar();
      }

      return var1;
   }

   public Object read(Lexer var1, int var2, int var3) throws IOException, SyntaxException {
      LispReader var4 = (LispReader)var1;
      return readXMLConstructor(var4, var4.readUnicodeChar(), false);
   }
}
