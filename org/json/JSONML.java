package org.json;

import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.json.XMLTokener;

public class JSONML {

   private static Object parse(XMLTokener var0, boolean var1, JSONArray var2) throws JSONException {
      label175:
      while(var0.more()) {
         Object var4 = var0.nextContent();
         Object var3;
         if(var4 != XML.LT) {
            if(var2 != null) {
               var3 = var4;
               if(var4 instanceof String) {
                  var3 = XML.stringToValue((String)var4);
               }

               var2.put((Object)var3);
            }
         } else {
            var3 = var0.nextToken();
            Object var10;
            if(var3 instanceof Character) {
               if(var3 != XML.SLASH) {
                  if(var3 == XML.BANG) {
                     char var8 = var0.next();
                     if(var8 == 45) {
                        if(var0.next() == 45) {
                           var0.skipPast("-->");
                           continue;
                        }

                        var0.back();
                        continue;
                     }

                     if(var8 == 91) {
                        if(var0.nextToken().equals("CDATA") && var0.next() == 91) {
                           if(var2 != null) {
                              var2.put((Object)var0.nextCDATA());
                           }
                           continue;
                        }

                        throw var0.syntaxError("Expected \'CDATA[\'");
                     }

                     int var9 = 1;

                     while(true) {
                        var3 = var0.nextMeta();
                        if(var3 == null) {
                           throw var0.syntaxError("Missing \'>\' after \'<!\'.");
                        }

                        int var13;
                        if(var3 == XML.LT) {
                           var13 = var9 + 1;
                        } else {
                           var13 = var9;
                           if(var3 == XML.GT) {
                              var13 = var9 - 1;
                           }
                        }

                        var9 = var13;
                        if(var13 <= 0) {
                           continue label175;
                        }
                     }
                  }

                  if(var3 == XML.QUEST) {
                     var0.skipPast("?>");
                     continue;
                  }

                  throw var0.syntaxError("Misshaped tag");
               }

               var10 = var0.nextToken();
               if(!(var10 instanceof String)) {
                  throw new JSONException("Expected a closing name instead of \'" + var10 + "\'.");
               }

               if(var0.nextToken() != XML.GT) {
                  throw var0.syntaxError("Misshaped close tag");
               }
            } else {
               if(!(var3 instanceof String)) {
                  throw var0.syntaxError("Bad tagName \'" + var3 + "\'.");
               }

               String var5 = (String)var3;
               JSONArray var11 = new JSONArray();
               JSONObject var6 = new JSONObject();
               if(var1) {
                  var11.put((Object)var5);
                  if(var2 != null) {
                     var2.put((Object)var11);
                  }
               } else {
                  var6.put("tagName", (Object)var5);
                  if(var2 != null) {
                     var2.put((Object)var6);
                  }
               }

               var3 = null;

               while(true) {
                  if(var3 == null) {
                     var3 = var0.nextToken();
                  }

                  if(var3 == null) {
                     throw var0.syntaxError("Misshaped tag");
                  }

                  if(!(var3 instanceof String)) {
                     if(var1 && var6.length() > 0) {
                        var11.put((Object)var6);
                     }

                     if(var3 != XML.SLASH) {
                        if(var3 != XML.GT) {
                           throw var0.syntaxError("Misshaped tag");
                        }

                        String var12 = (String)parse(var0, var1, var11);
                        if(var12 == null) {
                           continue label175;
                        }

                        if(!var12.equals(var5)) {
                           throw var0.syntaxError("Mismatched \'" + var5 + "\' and \'" + var12 + "\'");
                        }

                        if(!var1 && var11.length() > 0) {
                           var6.put("childNodes", (Object)var11);
                        }

                        if(var2 != null) {
                           continue label175;
                        }

                        if(var1) {
                           return var11;
                        }

                        return var6;
                     }

                     if(var0.nextToken() != XML.GT) {
                        throw var0.syntaxError("Misshaped tag");
                     }

                     if(var2 != null) {
                        continue label175;
                     }

                     if(!var1) {
                        return var6;
                     }

                     var10 = var11;
                     break;
                  }

                  String var7 = (String)var3;
                  if(!var1 && ("tagName".equals(var7) || "childNode".equals(var7))) {
                     throw var0.syntaxError("Reserved attribute.");
                  }

                  var3 = var0.nextToken();
                  if(var3 == XML.EQ) {
                     var3 = var0.nextToken();
                     if(!(var3 instanceof String)) {
                        throw var0.syntaxError("Missing value");
                     }

                     var6.accumulate(var7, XML.stringToValue((String)var3));
                     var3 = null;
                  } else {
                     var6.accumulate(var7, "");
                  }
               }
            }

            return var10;
         }
      }

      throw var0.syntaxError("Bad XML");
   }

   public static JSONArray toJSONArray(String var0) throws JSONException {
      return toJSONArray((XMLTokener)(new XMLTokener(var0)));
   }

   public static JSONArray toJSONArray(XMLTokener var0) throws JSONException {
      return (JSONArray)parse(var0, true, (JSONArray)null);
   }

   public static JSONObject toJSONObject(String var0) throws JSONException {
      return toJSONObject((XMLTokener)(new XMLTokener(var0)));
   }

   public static JSONObject toJSONObject(XMLTokener var0) throws JSONException {
      return (JSONObject)parse(var0, false, (JSONArray)null);
   }

   public static String toString(JSONArray var0) throws JSONException {
      StringBuffer var1 = new StringBuffer();
      String var2 = var0.getString(0);
      XML.noSpace(var2);
      var2 = XML.escape(var2);
      var1.append('<');
      var1.append(var2);
      Object var3 = var0.opt(1);
      int var7;
      if(var3 instanceof JSONObject) {
         byte var8 = 2;
         JSONObject var10 = (JSONObject)var3;
         Iterator var4 = var10.keys();

         while(true) {
            var7 = var8;
            if(!var4.hasNext()) {
               break;
            }

            String var5 = var4.next().toString();
            XML.noSpace(var5);
            String var6 = var10.optString(var5);
            if(var6 != null) {
               var1.append(' ');
               var1.append(XML.escape(var5));
               var1.append('=');
               var1.append('\"');
               var1.append(XML.escape(var6));
               var1.append('\"');
            }
         }
      } else {
         var7 = 1;
      }

      int var9 = var0.length();
      if(var7 >= var9) {
         var1.append('/');
         var1.append('>');
      } else {
         var1.append('>');

         int var11;
         do {
            var3 = var0.get(var7);
            var11 = var7 + 1;
            if(var3 != null) {
               if(var3 instanceof String) {
                  var1.append(XML.escape(var3.toString()));
               } else if(var3 instanceof JSONObject) {
                  var1.append(toString((JSONObject)((JSONObject)var3)));
               } else if(var3 instanceof JSONArray) {
                  var1.append(toString((JSONArray)((JSONArray)var3)));
               }
            }

            var7 = var11;
         } while(var11 < var9);

         var1.append('<');
         var1.append('/');
         var1.append(var2);
         var1.append('>');
      }

      return var1.toString();
   }

   public static String toString(JSONObject var0) throws JSONException {
      StringBuffer var1 = new StringBuffer();
      String var2 = var0.optString("tagName");
      if(var2 == null) {
         return XML.escape(var0.toString());
      } else {
         XML.noSpace(var2);
         var2 = XML.escape(var2);
         var1.append('<');
         var1.append(var2);
         Iterator var3 = var0.keys();

         while(var3.hasNext()) {
            String var4 = var3.next().toString();
            if(!"tagName".equals(var4) && !"childNodes".equals(var4)) {
               XML.noSpace(var4);
               String var5 = var0.optString(var4);
               if(var5 != null) {
                  var1.append(' ');
                  var1.append(XML.escape(var4));
                  var1.append('=');
                  var1.append('\"');
                  var1.append(XML.escape(var5));
                  var1.append('\"');
               }
            }
         }

         JSONArray var8 = var0.optJSONArray("childNodes");
         if(var8 == null) {
            var1.append('/');
            var1.append('>');
         } else {
            var1.append('>');
            int var7 = var8.length();

            for(int var6 = 0; var6 < var7; ++var6) {
               Object var9 = var8.get(var6);
               if(var9 != null) {
                  if(var9 instanceof String) {
                     var1.append(XML.escape(var9.toString()));
                  } else if(var9 instanceof JSONObject) {
                     var1.append(toString((JSONObject)((JSONObject)var9)));
                  } else if(var9 instanceof JSONArray) {
                     var1.append(toString((JSONArray)((JSONArray)var9)));
                  } else {
                     var1.append(var9.toString());
                  }
               }
            }

            var1.append('<');
            var1.append('/');
            var1.append(var2);
            var1.append('>');
         }

         return var1.toString();
      }
   }
}
