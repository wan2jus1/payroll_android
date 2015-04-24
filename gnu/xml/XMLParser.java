package gnu.xml;

import gnu.lists.Consumer;
import gnu.text.LineBufferedReader;
import gnu.text.LineInputStreamReader;
import gnu.text.Path;
import gnu.text.SourceMessages;
import gnu.xml.XMLFilter;
import java.io.IOException;
import java.io.InputStream;

public class XMLParser {

   private static final int ATTRIBUTE_SEEN_EQ_STATE = 11;
   private static final int ATTRIBUTE_SEEN_NAME_STATE = 8;
   static final String BAD_ENCODING_SYNTAX = "bad \'encoding\' declaration";
   static final String BAD_STANDALONE_SYNTAX = "bad \'standalone\' declaration";
   private static final int BEGIN_ELEMENT_STATE = 2;
   private static final int DOCTYPE_NAME_SEEN_STATE = 16;
   private static final int DOCTYPE_SEEN_STATE = 13;
   private static final int END_ELEMENT_STATE = 4;
   private static final int EXPECT_NAME_MODIFIER = 1;
   private static final int EXPECT_RIGHT_STATE = 27;
   private static final int INIT_LEFT_QUEST_STATE = 30;
   private static final int INIT_LEFT_STATE = 34;
   private static final int INIT_STATE = 0;
   private static final int INIT_TEXT_STATE = 31;
   private static final int INVALID_VERSION_DECL = 35;
   private static final int MAYBE_ATTRIBUTE_STATE = 10;
   private static final int PREV_WAS_CR_STATE = 28;
   private static final int SAW_AMP_SHARP_STATE = 26;
   private static final int SAW_AMP_STATE = 25;
   private static final int SAW_ENTITY_REF = 6;
   private static final int SAW_EOF_ERROR = 37;
   private static final int SAW_ERROR = 36;
   private static final int SAW_LEFT_EXCL_MINUS_STATE = 22;
   private static final int SAW_LEFT_EXCL_STATE = 20;
   private static final int SAW_LEFT_QUEST_STATE = 21;
   private static final int SAW_LEFT_SLASH_STATE = 19;
   private static final int SAW_LEFT_STATE = 14;
   private static final int SKIP_SPACES_MODIFIER = 2;
   private static final int TEXT_STATE = 1;


   public static LineInputStreamReader XMLStreamReader(InputStream var0) throws IOException {
      int var5 = -1;
      LineInputStreamReader var2 = new LineInputStreamReader(var0);
      int var6 = var2.getByte();
      int var3;
      if(var6 < 0) {
         var3 = -1;
      } else {
         var3 = var2.getByte();
      }

      int var4;
      if(var3 < 0) {
         var4 = -1;
      } else {
         var4 = var2.getByte();
      }

      if(var6 == 239 && var3 == 187 && var4 == 191) {
         var2.resetStart(3);
         var2.setCharset((String)"UTF-8");
      } else if(var6 == 255 && var3 == 254 && var4 != 0) {
         var2.resetStart(2);
         var2.setCharset((String)"UTF-16LE");
      } else if(var6 == 254 && var3 == 255 && var4 != 0) {
         var2.resetStart(2);
         var2.setCharset((String)"UTF-16BE");
      } else {
         if(var4 >= 0) {
            var5 = var2.getByte();
         }

         if(var6 == 76 && var3 == 111 && var4 == 167 && var5 == 148) {
            throw new RuntimeException("XMLParser: EBCDIC encodings not supported");
         }

         var2.resetStart(0);
         if((var6 != 60 || (var3 != 63 || var4 != 120 || var5 != 109) && (var3 != 0 || var4 != 63 || var5 != 0)) && (var6 != 0 || var3 != 60 || var4 != 0 || var5 != 63)) {
            var2.setCharset((String)"UTF-8");
         } else {
            char[] var1 = var2.buffer;
            char[] var8 = var1;
            if(var1 == null) {
               var8 = new char[8192];
               var2.buffer = var8;
            }

            var4 = 0;
            var6 = 0;

            while(true) {
               int var7;
               do {
                  var7 = var2.getByte();
               } while(var7 == 0);

               if(var7 < 0) {
                  break;
               }

               var5 = var4 + 1;
               var8[var4] = (char)(var7 & 255);
               if(var6 == 0) {
                  label127: {
                     if(var7 == 62) {
                        var4 = var5;
                        break;
                     }

                     if(var7 != 39) {
                        var3 = var6;
                        if(var7 != 34) {
                           break label127;
                        }
                     }

                     var3 = var7;
                  }
               } else {
                  var3 = var6;
                  if(var7 == var6) {
                     var3 = 0;
                  }
               }

               var4 = var5;
               var6 = var3;
            }

            var2.pos = 0;
            var2.limit = var4;
         }
      }

      var2.setKeepFullLines(false);
      return var2;
   }

   public static void parse(LineBufferedReader var0, SourceMessages var1, Consumer var2) throws IOException {
      XMLFilter var4 = new XMLFilter(var2);
      var4.setMessages(var1);
      var4.setSourceLocator((LineBufferedReader)var0);
      var4.startDocument();
      Path var3 = var0.getPath();
      if(var3 != null) {
         var4.writeDocumentUri(var3);
      }

      parse(var0, var4);
      var4.endDocument();
   }

   public static void parse(LineBufferedReader var0, SourceMessages var1, XMLFilter var2) throws IOException {
      var2.setMessages(var1);
      var2.setSourceLocator((LineBufferedReader)var0);
      var2.startDocument();
      Path var3 = var0.getPath();
      if(var3 != null) {
         var2.writeDocumentUri(var3);
      }

      parse(var0, var2);
      var2.endDocument();
      var0.close();
   }

   public static void parse(LineBufferedReader param0, XMLFilter param1) {
      // $FF: Couldn't be decompiled
   }

   public static void parse(InputStream var0, Object var1, SourceMessages var2, Consumer var3) throws IOException {
      LineInputStreamReader var4 = XMLStreamReader(var0);
      if(var1 != null) {
         var4.setName(var1);
      }

      parse((LineBufferedReader)var4, var2, (Consumer)var3);
      var4.close();
   }

   public static void parse(Object var0, SourceMessages var1, Consumer var2) throws IOException {
      parse(Path.openInputStream(var0), var0, var1, var2);
   }
}
