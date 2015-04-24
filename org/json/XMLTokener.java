package org.json;

import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONTokener;
import org.json.XML;

public class XMLTokener extends JSONTokener {

   public static final HashMap entity = new HashMap(8);


   static {
      entity.put("amp", XML.AMP);
      entity.put("apos", XML.APOS);
      entity.put("gt", XML.GT);
      entity.put("lt", XML.LT);
      entity.put("quot", XML.QUOT);
   }

   public XMLTokener(String var1) {
      super((String)var1);
   }

   public String nextCDATA() throws JSONException {
      StringBuffer var2 = new StringBuffer();

      int var3;
      do {
         char var1 = this.next();
         if(this.end()) {
            throw this.syntaxError("Unclosed CDATA");
         }

         var2.append(var1);
         var3 = var2.length() - 3;
      } while(var3 < 0 || var2.charAt(var3) != 93 || var2.charAt(var3 + 1) != 93 || var2.charAt(var3 + 2) != 62);

      var2.setLength(var3);
      return var2.toString();
   }

   public Object nextContent() throws JSONException {
      char var1;
      do {
         var1 = this.next();
      } while(Character.isWhitespace(var1));

      if(var1 == 0) {
         return null;
      } else if(var1 == 60) {
         return XML.LT;
      } else {
         StringBuffer var2;
         for(var2 = new StringBuffer(); var1 != 60 && var1 != 0; var1 = this.next()) {
            if(var1 == 38) {
               var2.append(this.nextEntity(var1));
            } else {
               var2.append(var1);
            }
         }

         this.back();
         return var2.toString().trim();
      }
   }

   public Object nextEntity(char var1) throws JSONException {
      StringBuffer var3 = new StringBuffer();

      while(true) {
         char var2 = this.next();
         if(!Character.isLetterOrDigit(var2) && var2 != 35) {
            if(var2 == 59) {
               String var5 = var3.toString();
               Object var4 = entity.get(var5);
               if(var4 != null) {
                  return var4;
               }

               return var1 + var5 + ";";
            }

            throw this.syntaxError("Missing \';\' in XML entity: &" + var3);
         }

         var3.append(Character.toLowerCase(var2));
      }
   }

   public Object nextMeta() throws JSONException {
      char var1;
      do {
         var1 = this.next();
      } while(Character.isWhitespace(var1));

      switch(var1) {
      case 0:
         throw this.syntaxError("Misshaped meta tag");
      case 33:
         return XML.BANG;
      case 34:
      case 39:
         char var2;
         do {
            var2 = this.next();
            if(var2 == 0) {
               throw this.syntaxError("Unterminated string");
            }
         } while(var2 != var1);

         return Boolean.TRUE;
      case 47:
         return XML.SLASH;
      case 60:
         return XML.LT;
      case 61:
         return XML.EQ;
      case 62:
         return XML.GT;
      case 63:
         return XML.QUEST;
      default:
         while(true) {
            var1 = this.next();
            if(Character.isWhitespace(var1)) {
               return Boolean.TRUE;
            }

            switch(var1) {
            case 0:
            case 33:
            case 34:
            case 39:
            case 47:
            case 60:
            case 61:
            case 62:
            case 63:
               this.back();
               return Boolean.TRUE;
            }
         }
      }
   }

   public Object nextToken() throws JSONException {
      char var1;
      do {
         var1 = this.next();
      } while(Character.isWhitespace(var1));

      StringBuffer var3;
      switch(var1) {
      case 0:
         throw this.syntaxError("Misshaped element");
      case 33:
         return XML.BANG;
      case 34:
      case 39:
         var3 = new StringBuffer();

         while(true) {
            char var2 = this.next();
            if(var2 == 0) {
               throw this.syntaxError("Unterminated string");
            }

            if(var2 == var1) {
               return var3.toString();
            }

            if(var2 == 38) {
               var3.append(this.nextEntity(var2));
            } else {
               var3.append(var2);
            }
         }
      case 47:
         return XML.SLASH;
      case 60:
         throw this.syntaxError("Misplaced \'<\'");
      case 61:
         return XML.EQ;
      case 62:
         return XML.GT;
      case 63:
         return XML.QUEST;
      default:
         var3 = new StringBuffer();

         while(true) {
            var3.append(var1);
            var1 = this.next();
            if(Character.isWhitespace(var1)) {
               return var3.toString();
            }

            switch(var1) {
            case 0:
               return var3.toString();
            case 33:
            case 47:
            case 61:
            case 62:
            case 63:
            case 91:
            case 93:
               this.back();
               return var3.toString();
            case 34:
            case 39:
            case 60:
               throw this.syntaxError("Bad character in a name");
            }
         }
      }
   }

   public boolean skipPast(String var1) throws JSONException {
      byte var6 = 0;
      int var9 = var1.length();
      char[] var3 = new char[var9];
      int var5 = 0;

      while(true) {
         int var4 = var6;
         char var2;
         if(var5 >= var9) {
            while(true) {
               var5 = var4;
               boolean var8 = true;
               int var10 = 0;

               while(true) {
                  boolean var7 = var8;
                  if(var10 < var9) {
                     if(var3[var5] == var1.charAt(var10)) {
                        int var11 = var5 + 1;
                        var5 = var11;
                        if(var11 >= var9) {
                           var5 = var11 - var9;
                        }

                        ++var10;
                        continue;
                     }

                     var7 = false;
                  }

                  if(var7) {
                     return true;
                  }

                  var2 = this.next();
                  if(var2 == 0) {
                     return false;
                  }

                  var3[var4] = var2;
                  var5 = var4 + 1;
                  var4 = var5;
                  if(var5 >= var9) {
                     var4 = var5 - var9;
                  }
                  break;
               }
            }
         }

         var2 = this.next();
         if(var2 == 0) {
            return false;
         }

         var3[var5] = var2;
         ++var5;
      }
   }
}
