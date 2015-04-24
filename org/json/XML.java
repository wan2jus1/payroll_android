package org.json;

import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XMLTokener;

public class XML {

   public static final Character AMP = new Character('&');
   public static final Character APOS = new Character('\'');
   public static final Character BANG = new Character('!');
   public static final Character EQ = new Character('=');
   public static final Character GT = new Character('>');
   public static final Character LT = new Character('<');
   public static final Character QUEST = new Character('?');
   public static final Character QUOT = new Character('\"');
   public static final Character SLASH = new Character('/');


   public static String escape(String var0) {
      StringBuffer var2 = new StringBuffer();
      int var3 = 0;

      for(int var4 = var0.length(); var3 < var4; ++var3) {
         char var1 = var0.charAt(var3);
         switch(var1) {
         case 34:
            var2.append("&quot;");
            break;
         case 38:
            var2.append("&amp;");
            break;
         case 39:
            var2.append("&apos;");
            break;
         case 60:
            var2.append("&lt;");
            break;
         case 62:
            var2.append("&gt;");
            break;
         default:
            var2.append(var1);
         }
      }

      return var2.toString();
   }

   public static void noSpace(String var0) throws JSONException {
      int var2 = var0.length();
      if(var2 == 0) {
         throw new JSONException("Empty string.");
      } else {
         for(int var1 = 0; var1 < var2; ++var1) {
            if(Character.isWhitespace(var0.charAt(var1))) {
               throw new JSONException("\'" + var0 + "\' contains a space character.");
            }
         }

      }
   }

   private static boolean parse(XMLTokener var0, JSONObject var1, String var2) throws JSONException {
      Object var3 = var0.nextToken();
      Object var9;
      if(var3 == BANG) {
         char var6 = var0.next();
         if(var6 == 45) {
            if(var0.next() == 45) {
               var0.skipPast("-->");
               return false;
            }

            var0.back();
         } else if(var6 == 91) {
            if(!"CDATA".equals(var0.nextToken()) || var0.next() != 91) {
               throw var0.syntaxError("Expected \'CDATA[\'");
            }

            String var8 = var0.nextCDATA();
            if(var8.length() > 0) {
               var1.accumulate("content", var8);
               return false;
            }

            return false;
         }

         int var7 = 1;

         int var12;
         do {
            var9 = var0.nextMeta();
            if(var9 == null) {
               throw var0.syntaxError("Missing \'>\' after \'<!\'.");
            }

            if(var9 == LT) {
               var12 = var7 + 1;
            } else {
               var12 = var7;
               if(var9 == GT) {
                  var12 = var7 - 1;
               }
            }

            var7 = var12;
         } while(var12 > 0);

         return false;
      } else if(var3 == QUEST) {
         var0.skipPast("?>");
         return false;
      } else if(var3 == SLASH) {
         var9 = var0.nextToken();
         if(var2 == null) {
            throw var0.syntaxError("Mismatched close tag " + var9);
         } else if(!var9.equals(var2)) {
            throw var0.syntaxError("Mismatched " + var2 + " and " + var9);
         } else if(var0.nextToken() != GT) {
            throw var0.syntaxError("Misshaped close tag");
         } else {
            return true;
         }
      } else if(var3 instanceof Character) {
         throw var0.syntaxError("Misshaped tag");
      } else {
         String var11 = (String)var3;
         Object var10 = null;
         JSONObject var4 = new JSONObject();

         while(true) {
            if(var10 == null) {
               var10 = var0.nextToken();
            }

            if(!(var10 instanceof String)) {
               if(var10 == SLASH) {
                  if(var0.nextToken() != GT) {
                     throw var0.syntaxError("Misshaped tag");
                  }

                  if(var4.length() > 0) {
                     var1.accumulate(var11, var4);
                     return false;
                  }

                  var1.accumulate(var11, "");
                  return false;
               }

               if(var10 != GT) {
                  throw var0.syntaxError("Misshaped tag");
               }

               while(true) {
                  var10 = var0.nextContent();
                  if(var10 == null) {
                     if(var11 != null) {
                        throw var0.syntaxError("Unclosed tag " + var11);
                     }

                     return false;
                  }

                  if(var10 instanceof String) {
                     var2 = (String)var10;
                     if(var2.length() > 0) {
                        var4.accumulate("content", stringToValue(var2));
                     }
                  } else if(var10 == LT && parse(var0, var4, var11)) {
                     if(var4.length() == 0) {
                        var1.accumulate(var11, "");
                        return false;
                     }

                     if(var4.length() == 1 && var4.opt("content") != null) {
                        var1.accumulate(var11, var4.opt("content"));
                        return false;
                     }

                     var1.accumulate(var11, var4);
                     return false;
                  }
               }
            }

            String var5 = (String)var10;
            var10 = var0.nextToken();
            if(var10 == EQ) {
               var10 = var0.nextToken();
               if(!(var10 instanceof String)) {
                  throw var0.syntaxError("Missing value");
               }

               var4.accumulate(var5, stringToValue((String)var10));
               var10 = null;
            } else {
               var4.accumulate(var5, "");
            }
         }
      }
   }

   public static Object stringToValue(String param0) {
      // $FF: Couldn't be decompiled
   }

   public static JSONObject toJSONObject(String var0) throws JSONException {
      JSONObject var1 = new JSONObject();
      XMLTokener var2 = new XMLTokener(var0);

      while(var2.more() && var2.skipPast("<")) {
         parse(var2, var1, (String)null);
      }

      return var1;
   }

   public static String toString(Object var0) throws JSONException {
      return toString(var0, (String)null);
   }

   public static String toString(Object var0, String var1) throws JSONException {
      StringBuffer var3 = new StringBuffer();
      Object var2;
      int var7;
      int var8;
      if(!(var0 instanceof JSONObject)) {
         var2 = var0;
         if(var0.getClass().isArray()) {
            var2 = new JSONArray(var0);
         }

         String var10;
         if(var2 instanceof JSONArray) {
            JSONArray var12 = (JSONArray)var2;
            var8 = var12.length();

            for(var7 = 0; var7 < var8; ++var7) {
               Object var13 = var12.opt(var7);
               if(var1 == null) {
                  var10 = "array";
               } else {
                  var10 = var1;
               }

               var3.append(toString(var13, var10));
            }

            return var3.toString();
         } else {
            if(var2 == null) {
               var10 = "null";
            } else {
               var10 = escape(var2.toString());
            }

            return var1 == null?"\"" + var10 + "\"":(var10.length() == 0?"<" + var1 + "/>":"<" + var1 + ">" + var10 + "</" + var1 + ">");
         }
      } else {
         if(var1 != null) {
            var3.append('<');
            var3.append(var1);
            var3.append('>');
         }

         JSONObject var4 = (JSONObject)var0;
         Iterator var5 = var4.keys();

         while(var5.hasNext()) {
            String var6 = var5.next().toString();
            var2 = var4.opt(var6);
            var0 = var2;
            if(var2 == null) {
               var0 = "";
            }

            if(var0 instanceof String) {
               String var11 = (String)var0;
            }

            JSONArray var9;
            if("content".equals(var6)) {
               if(var0 instanceof JSONArray) {
                  var9 = (JSONArray)var0;
                  var8 = var9.length();

                  for(var7 = 0; var7 < var8; ++var7) {
                     if(var7 > 0) {
                        var3.append('\n');
                     }

                     var3.append(escape(var9.get(var7).toString()));
                  }
               } else {
                  var3.append(escape(var0.toString()));
               }
            } else if(var0 instanceof JSONArray) {
               var9 = (JSONArray)var0;
               var8 = var9.length();

               for(var7 = 0; var7 < var8; ++var7) {
                  var2 = var9.get(var7);
                  if(var2 instanceof JSONArray) {
                     var3.append('<');
                     var3.append(var6);
                     var3.append('>');
                     var3.append(toString(var2));
                     var3.append("</");
                     var3.append(var6);
                     var3.append('>');
                  } else {
                     var3.append(toString(var2, var6));
                  }
               }
            } else if("".equals(var0)) {
               var3.append('<');
               var3.append(var6);
               var3.append("/>");
            } else {
               var3.append(toString(var0, var6));
            }
         }

         if(var1 != null) {
            var3.append("</");
            var3.append(var1);
            var3.append('>');
         }

         return var3.toString();
      }
   }
}
