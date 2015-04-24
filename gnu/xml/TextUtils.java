package gnu.xml;

import gnu.kawa.xml.KNode;
import gnu.lists.Consumer;
import gnu.lists.TreeList;
import gnu.mapping.Values;
import gnu.math.DFloNum;
import gnu.xml.NodeTree;
import gnu.xml.XMLPrinter;
import java.math.BigDecimal;

public class TextUtils {

   public static String asString(Object var0) {
      if(var0 != Values.empty && var0 != null) {
         if(var0 instanceof Values) {
            throw new ClassCastException();
         } else {
            StringBuffer var1 = new StringBuffer(100);
            stringValue(var0, var1);
            return var1.toString();
         }
      } else {
         return "";
      }
   }

   public static String replaceWhitespace(String var0, boolean var1) {
      StringBuilder var4 = null;
      int var11 = var0.length();
      byte var6;
      if(var1) {
         var6 = 1;
      } else {
         var6 = 0;
      }

      int var7 = 0;

      while(var7 < var11) {
         int var8 = var7 + 1;
         char var3 = var0.charAt(var7);
         byte var12;
         if(var3 == 32) {
            var12 = 1;
         } else if(var3 != 9 && var3 != 13 && var3 != 10) {
            var12 = 0;
         } else {
            var12 = 2;
         }

         char var2 = var3;
         StringBuilder var5 = var4;
         if(var4 == null) {
            label115: {
               if(var12 != 2 && (var12 != 1 || var6 <= 0 || !var1)) {
                  var2 = var3;
                  var5 = var4;
                  if(var12 != 1) {
                     break label115;
                  }

                  var2 = var3;
                  var5 = var4;
                  if(var8 != var11) {
                     break label115;
                  }

                  var2 = var3;
                  var5 = var4;
                  if(!var1) {
                     break label115;
                  }
               }

               var5 = new StringBuilder();
               int var9;
               if(var6 > 0) {
                  var9 = var8 - 2;
               } else {
                  var9 = var8 - 1;
               }

               for(int var10 = 0; var10 < var9; ++var10) {
                  var5.append(var0.charAt(var10));
               }

               var2 = 32;
            }
         }

         byte var13 = var6;
         if(var1) {
            if(var6 > 0 && var12 == 0) {
               if(var5 != null && var5.length() > 0) {
                  var5.append(' ');
               }

               var6 = 0;
            } else if(var12 != 2 && (var12 != 1 || var6 <= 0)) {
               if(var12 > 0) {
                  var6 = 1;
               } else {
                  var6 = 0;
               }
            } else {
               var6 = 2;
            }

            var13 = var6;
            if(var6 > 0) {
               var7 = var8;
               var4 = var5;
               continue;
            }
         }

         if(var5 != null) {
            var5.append(var2);
         }

         var7 = var8;
         var6 = var13;
         var4 = var5;
      }

      if(var4 != null) {
         var0 = var4.toString();
      }

      return var0;
   }

   public static String stringValue(Object var0) {
      StringBuffer var1 = new StringBuffer(100);
      if(var0 instanceof Values) {
         TreeList var4 = (TreeList)var0;
         int var2 = 0;

         while(true) {
            int var3 = var4.getNextKind(var2);
            if(var3 == 0) {
               break;
            }

            if(var3 == 32) {
               stringValue(var4.getPosNext(var2), var1);
            } else {
               var4.stringValue(var4.posToDataIndex(var2), var1);
            }

            var2 = var4.nextPos(var2);
         }
      } else {
         stringValue(var0, var1);
      }

      return var1.toString();
   }

   public static void stringValue(Object var0, StringBuffer var1) {
      if(var0 instanceof KNode) {
         KNode var3 = (KNode)var0;
         NodeTree var2 = (NodeTree)var3.sequence;
         var2.stringValue(var2.posToDataIndex(var3.ipos), var1);
      } else {
         Object var4;
         if(var0 instanceof BigDecimal) {
            var4 = XMLPrinter.formatDecimal((BigDecimal)((BigDecimal)var0));
         } else if(!(var0 instanceof Double) && !(var0 instanceof DFloNum)) {
            var4 = var0;
            if(var0 instanceof Float) {
               var4 = XMLPrinter.formatFloat(((Number)var0).floatValue());
            }
         } else {
            var4 = XMLPrinter.formatDouble(((Number)var0).doubleValue());
         }

         if(var4 != null && var4 != Values.empty) {
            var1.append(var4);
            return;
         }
      }

   }

   public static void textValue(Object var0, Consumer var1) {
      if(var0 != null && (!(var0 instanceof Values) || !((Values)var0).isEmpty())) {
         String var4;
         if(var0 instanceof String) {
            var4 = (String)var0;
         } else {
            StringBuffer var2 = new StringBuffer();
            if(var0 instanceof Values) {
               Object[] var5 = ((Values)var0).getValues();

               for(int var3 = 0; var3 < var5.length; ++var3) {
                  if(var3 > 0) {
                     var2.append(' ');
                  }

                  stringValue(var5[var3], var2);
               }
            } else {
               stringValue(var0, var2);
            }

            var4 = var2.toString();
         }

         var1.write(var4);
      }
   }
}
