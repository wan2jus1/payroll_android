package gnu.kawa.functions;

import gnu.kawa.functions.IntegerFormat;
import gnu.kawa.functions.ObjectFormat;
import gnu.mapping.Procedure1;
import gnu.text.CompoundFormat;
import gnu.text.LineBufferedReader;
import gnu.text.LiteralFormat;
import gnu.text.PadFormat;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.text.ParseException;
import java.util.Vector;

public class ParseFormat extends Procedure1 {

   public static final int PARAM_FROM_LIST = -1610612736;
   public static final int PARAM_UNSPECIFIED = -1073741824;
   public static final int SEEN_HASH = 16;
   public static final int SEEN_MINUS = 1;
   public static final int SEEN_PLUS = 2;
   public static final int SEEN_SPACE = 4;
   public static final int SEEN_ZERO = 8;
   public static final ParseFormat parseFormat = new ParseFormat(false);
   boolean emacsStyle = true;


   public ParseFormat(boolean var1) {
      this.emacsStyle = var1;
   }

   public static ReportFormat asFormat(Object param0, char param1) {
      // $FF: Couldn't be decompiled
   }

   public static ReportFormat parseFormat(LineBufferedReader var0, char var1) throws ParseException, IOException {
      StringBuffer var5 = new StringBuffer(100);
      int var7 = 0;
      Vector var4 = new Vector();

      label167:
      while(true) {
         int var6;
         int var8;
         while(true) {
            var8 = var0.read();
            var6 = var8;
            if(var8 < 0) {
               break;
            }

            if(var8 != var1) {
               var5.append((char)var8);
            } else {
               var8 = var0.read();
               var6 = var8;
               if(var8 != var1) {
                  break;
               }

               var5.append((char)var8);
            }
         }

         var8 = var5.length();
         if(var8 > 0) {
            char[] var3 = new char[var8];
            var5.getChars(0, var8, var3, 0);
            var5.setLength(0);
            var4.addElement(new LiteralFormat(var3));
         }

         if(var6 < 0) {
            int var17 = var4.size();
            if(var17 == 1) {
               Object var15 = var4.elementAt(0);
               if(var15 instanceof ReportFormat) {
                  return (ReportFormat)var15;
               }
            }

            java.text.Format[] var16 = new java.text.Format[var17];
            var4.copyInto(var16);
            return new CompoundFormat(var16);
         }

         var8 = var6;
         int var12 = var7;
         if(var6 == 36) {
            var7 = Character.digit((char)var0.read(), 10);
            var6 = var7;
            if(var7 < 0) {
               throw new ParseException("missing number (position) after \'%$\'", -1);
            }

            while(true) {
               var8 = var0.read();
               var7 = Character.digit((char)var8, 10);
               if(var7 < 0) {
                  var12 = var6 - 1;
                  break;
               }

               var6 = var6 * 10 + var7;
            }
         }

         var6 = 0;

         while(true) {
            switch((char)var8) {
            case 32:
               var6 |= 4;
               break;
            case 35:
               var6 |= 16;
               break;
            case 43:
               var6 |= 2;
               break;
            case 45:
               var6 |= 1;
               break;
            case 48:
               var6 |= 8;
               break;
            default:
               var7 = -1073741824;
               int var9 = Character.digit((char)var8, 10);
               if(var9 >= 0) {
                  var7 = var9;

                  while(true) {
                     var9 = var0.read();
                     var8 = Character.digit((char)var9, 10);
                     if(var8 < 0) {
                        break;
                     }

                     var7 = var7 * 10 + var8;
                  }
               } else {
                  var9 = var8;
                  if(var8 == 42) {
                     var7 = -1610612736;
                     var9 = var8;
                  }
               }

               var8 = -1073741824;
               int var11 = var9;
               int var10;
               if(var9 == 46) {
                  if(var9 == 42) {
                     var8 = -1610612736;
                     var11 = var9;
                  } else {
                     var9 = 0;

                     while(true) {
                        var11 = var0.read();
                        var10 = Character.digit((char)var11, 10);
                        var8 = var9;
                        if(var10 < 0) {
                           break;
                        }

                        var9 = var9 * 10 + var10;
                     }
                  }
               }

               Object var18;
               switch(var11) {
               case 83:
               case 115:
                  boolean var14;
                  if(var11 == 83) {
                     var14 = true;
                  } else {
                     var14 = false;
                  }

                  var18 = new ObjectFormat(var14, var8);
                  break;
               case 88:
               case 100:
               case 105:
               case 111:
               case 120:
                  byte var20 = 0;
                  byte var13;
                  byte var19;
                  if(var11 != 100 && var11 != 105) {
                     if(var11 == 111) {
                        var19 = 8;
                     } else {
                        var13 = 16;
                        var19 = var13;
                        if(var11 == 88) {
                           var20 = 32;
                           var19 = var13;
                        }
                     }
                  } else {
                     var19 = 10;
                  }

                  if((var6 & 9) == 8) {
                     var13 = 48;
                  } else {
                     var13 = 32;
                  }

                  var11 = var20;
                  if((var6 & 16) != 0) {
                     var11 = var20 | 8;
                  }

                  var10 = var11;
                  if((var6 & 2) != 0) {
                     var10 = var11 | 2;
                  }

                  var11 = var10;
                  if((var6 & 1) != 0) {
                     var11 = var10 | 16;
                  }

                  var10 = var11;
                  if((var6 & 4) != 0) {
                     var10 = var11 | 4;
                  }

                  if(var8 != -1073741824) {
                     var6 &= -9;
                     var18 = IntegerFormat.getInstance(var19, var8, 48, -1073741824, -1073741824, var10 | 64);
                  } else {
                     var18 = IntegerFormat.getInstance(var19, var7, var13, -1073741824, -1073741824, var10);
                  }
                  break;
               case 101:
               case 102:
               case 103:
                  var18 = new ObjectFormat(false);
                  break;
               default:
                  throw new ParseException("unknown format character \'" + var11 + "\'", -1);
               }

               if(var7 > 0) {
                  char var2;
                  if((var6 & 8) != 0) {
                     var2 = 48;
                  } else {
                     var2 = 32;
                  }

                  byte var21;
                  if((var6 & 1) != 0) {
                     var21 = 100;
                  } else if(var2 == 48) {
                     var21 = -1;
                  } else {
                     var21 = 0;
                  }

                  var18 = new PadFormat((java.text.Format)var18, var7, var2, var21);
               }

               var4.addElement(var18);
               var7 = var12 + 1;
               continue label167;
            }

            var8 = var0.read();
         }
      }
   }

   public Object apply1(Object var1) {
      char var2;
      if(this.emacsStyle) {
         var2 = 63;
      } else {
         var2 = 126;
      }

      return asFormat(var1, var2);
   }

   public ReportFormat parseFormat(LineBufferedReader var1) throws ParseException, IOException {
      char var2;
      if(this.emacsStyle) {
         var2 = 63;
      } else {
         var2 = 126;
      }

      return parseFormat(var1, var2);
   }
}
