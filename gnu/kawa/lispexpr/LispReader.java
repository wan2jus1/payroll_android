package gnu.kawa.lispexpr;

import gnu.expr.Keyword;
import gnu.expr.QuoteExp;
import gnu.expr.Special;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.lispexpr.ReadTable;
import gnu.kawa.lispexpr.ReadTableEntry;
import gnu.kawa.lispexpr.ReaderIgnoreRestOfLine;
import gnu.kawa.lispexpr.ReaderParens;
import gnu.kawa.util.GeneralHashTable;
import gnu.lists.Convert;
import gnu.lists.F32Vector;
import gnu.lists.F64Vector;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.lists.S16Vector;
import gnu.lists.S32Vector;
import gnu.lists.S64Vector;
import gnu.lists.S8Vector;
import gnu.lists.Sequence;
import gnu.lists.SimpleVector;
import gnu.lists.U16Vector;
import gnu.lists.U32Vector;
import gnu.lists.U64Vector;
import gnu.lists.U8Vector;
import gnu.mapping.Environment;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.math.CComplex;
import gnu.math.Complex;
import gnu.math.DComplex;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.RatNum;
import gnu.math.RealNum;
import gnu.text.Char;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.IOException;
import java.math.BigDecimal;

public class LispReader extends Lexer {

   static final int SCM_COMPLEX = 1;
   public static final int SCM_NUMBERS = 1;
   public static final char TOKEN_ESCAPE_CHAR = '\uffff';
   protected boolean seenEscapes;
   GeneralHashTable sharedStructureTable;


   public LispReader(LineBufferedReader var1) {
      super(var1);
   }

   public LispReader(LineBufferedReader var1, SourceMessages var2) {
      super(var1, var2);
   }

   static char getReadCase() {
      char var0;
      try {
         var0 = Environment.getCurrent().get((String)"symbol-read-case", "P").toString().charAt(0);
      } catch (Exception var2) {
         return 'P';
      }

      if(var0 != 80) {
         if(var0 == 117) {
            return 'U';
         }

         if(var0 == 100 || var0 == 108 || var0 == 76) {
            return 'D';
         }

         if(var0 == 105) {
            return 'I';
         }
      }

      return var0;
   }

   private boolean isPotentialNumber(char[] var1, int var2, int var3) {
      boolean var7 = true;
      int var6 = 0;

      for(int var5 = var2; var5 < var3; ++var5) {
         char var4 = var1[var5];
         if(Character.isDigit(var4)) {
            ++var6;
         } else if(var4 != 45 && var4 != 43) {
            if(var4 == 35) {
               return true;
            }

            if(!Character.isLetter(var4) && var4 != 47 && var4 != 95 && var4 != 94) {
               if(var4 != 46) {
                  return false;
               }
            } else if(var5 == var2) {
               return false;
            }
         } else if(var5 + 1 == var3) {
            return false;
         }
      }

      if(var6 <= 0) {
         var7 = false;
      }

      return var7;
   }

   public static Object parseNumber(CharSequence var0, int var1) {
      char[] var2;
      if(var0 instanceof FString) {
         var2 = ((FString)var0).data;
      } else {
         var2 = var0.toString().toCharArray();
      }

      return parseNumber(var2, 0, var0.length(), '\u0000', var1, 1);
   }

   public static Object parseNumber(char[] var0, int var1, int var2, char var3, int var4, int var5) {
      int var21 = var1 + var2;
      Object var33;
      if(var1 >= var21) {
         var33 = "no digits";
      } else {
         int var15 = var1 + 1;
         char var10 = var0[var1];
         int var14 = var4;

         while(true) {
            int var16;
            if(var10 != 35) {
               char var11 = var3;
               if(var3 == 0) {
                  var11 = 32;
               }

               var4 = var14;
               if(var14 == 0) {
                  while(true) {
                     var4 = var2 - 1;
                     if(var4 < 0) {
                        var4 = 10;
                        break;
                     }

                     var2 = var4;
                     if(var0[var1 + var4] == 46) {
                        var4 = 10;
                        break;
                     }
                  }
               }

               boolean var27;
               if(var10 == 45) {
                  var27 = true;
               } else {
                  var27 = false;
               }

               boolean var17;
               if(var10 != 45 && var10 != 43) {
                  var17 = false;
               } else {
                  var17 = true;
               }

               if(var17) {
                  if(var15 >= var21) {
                     return "no digits following sign";
                  }

                  var2 = var15 + 1;
                  var10 = var0[var15];
               } else {
                  var2 = var15;
               }

               double var6;
               char var38;
               if((var10 == 105 || var10 == 73) && var2 == var21 && var1 == var2 - 2 && (var5 & 1) != 0) {
                  var38 = var0[var1];
                  if(var38 != 43 && var38 != 45) {
                     return "no digits";
                  }

                  if(var11 != 105 && var11 != 73) {
                     CComplex var36;
                     if(var27) {
                        var36 = Complex.imMinusOne();
                     } else {
                        var36 = Complex.imOne();
                     }

                     return var36;
                  }

                  if(var27) {
                     var6 = -1.0D;
                  } else {
                     var6 = 1.0D;
                  }

                  return new DComplex(0.0D, var6);
               }

               boolean var29 = false;
               int var18 = -1;
               byte var44 = -1;
               var16 = -1;
               boolean var28 = false;
               IntNum var12 = null;
               long var22 = 0L;
               var1 = var2;
               boolean var26 = var27;
               var2 = var44;

               IntNum var13;
               long var24;
               boolean var31;
               boolean var30;
               boolean var32;
               label472:
               while(true) {
                  var15 = Character.digit(var10, var4);
                  if(var15 >= 0) {
                     if(var29 && var16 < 0) {
                        return "digit after \'#\' in number";
                     }

                     var14 = var2;
                     if(var2 < 0) {
                        var14 = var1 - 1;
                     }

                     var22 = (long)var4 * var22 + (long)var15;
                     var15 = var16;
                     var2 = var14;
                  } else {
                     switch(var10) {
                     case 46:
                        if(var16 >= 0) {
                           return "duplicate \'.\' in number";
                        }

                        if(var4 != 10) {
                           return "\'.\' in non-decimal number";
                        }

                        var15 = var1 - 1;
                        break;
                     case 47:
                        if(var12 != null) {
                           return "multiple fraction symbol \'/\'";
                        }

                        if(var2 < 0) {
                           return "no digits before fraction symbol \'/\'";
                        }

                        if(-1 >= 0 || var16 >= 0) {
                           return "fraction symbol \'/\' following exponent or \'.\'";
                        }

                        var12 = valueOf(var0, var2, var1 - var2, var4, var26, var22);
                        var2 = -1;
                        var22 = 0L;
                        var26 = false;
                        var29 = false;
                        var28 = false;
                        var15 = var16;
                        break;
                     case 68:
                     case 69:
                     case 70:
                     case 76:
                     case 83:
                     case 100:
                     case 101:
                     case 102:
                     case 108:
                     case 115:
                        if(var1 != var21 && var4 == 10) {
                           var3 = var0[var1];
                           if(var3 != 43 && var3 != 45) {
                              var14 = var1;
                              if(Character.digit(var3, 10) < 0) {
                                 --var1;
                                 var14 = var2;
                                 var32 = var26;
                                 var24 = var22;
                                 var15 = var16;
                                 var31 = var29;
                                 var13 = var12;
                                 var30 = var28;
                                 break label472;
                              }
                           } else {
                              label516: {
                                 var15 = var1 + 1;
                                 if(var15 < var21) {
                                    var14 = var15;
                                    if(Character.digit(var0[var15], 10) >= 0) {
                                       break label516;
                                    }
                                 }

                                 return "missing exponent digits";
                              }
                           }

                           if(-1 >= 0) {
                              return "duplicate exponent";
                           }

                           if(var4 != 10) {
                              return "exponent in non-decimal number";
                           }

                           if(var2 < 0) {
                              return "mantissa with no digits";
                           }

                           int var20 = var1 - 1;

                           int var19;
                           do {
                              var19 = var14 + 1;
                              var14 = var2;
                              var32 = var26;
                              var24 = var22;
                              var1 = var19;
                              var15 = var16;
                              var18 = var20;
                              var31 = var29;
                              var13 = var12;
                              var30 = var28;
                              if(var19 >= var21) {
                                 break label472;
                              }

                              var14 = var19;
                           } while(Character.digit(var0[var19], 10) >= 0);

                           var14 = var2;
                           var32 = var26;
                           var24 = var22;
                           var1 = var19;
                           var15 = var16;
                           var18 = var20;
                           var31 = var29;
                           var13 = var12;
                           var30 = var28;
                        } else {
                           --var1;
                           var14 = var2;
                           var32 = var26;
                           var24 = var22;
                           var15 = var16;
                           var31 = var29;
                           var13 = var12;
                           var30 = var28;
                        }
                        break label472;
                     default:
                        --var1;
                        var14 = var2;
                        var32 = var26;
                        var24 = var22;
                        var15 = var16;
                        var31 = var29;
                        var13 = var12;
                        var30 = var28;
                        break label472;
                     }
                  }

                  if(var1 == var21) {
                     var30 = var28;
                     var13 = var12;
                     var31 = var29;
                     var24 = var22;
                     var32 = var26;
                     var14 = var2;
                     break;
                  }

                  var10 = var0[var1];
                  ++var1;
                  var16 = var15;
               }

               byte var39 = 0;
               byte var47 = 0;
               if(var14 < 0) {
                  var39 = var47;
                  if(var17) {
                     var39 = var47;
                     if(var1 + 4 < var21) {
                        var39 = var47;
                        if(var0[var1 + 3] == 46) {
                           var39 = var47;
                           if(var0[var1 + 4] == 48) {
                              if(var0[var1] == 105 && var0[var1 + 1] == 110 && var0[var1 + 2] == 102) {
                                 var39 = 105;
                              } else {
                                 var39 = var47;
                                 if(var0[var1] == 110) {
                                    var39 = var47;
                                    if(var0[var1 + 1] == 97) {
                                       var39 = var47;
                                       if(var0[var1 + 2] == 110) {
                                          var39 = 110;
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }

                  if(var39 == 0) {
                     return "no digits";
                  }

                  var16 = var1 + 5;
               } else {
                  var16 = var1;
               }

               if(!var31 && var30) {
                  ;
               }

               boolean var37;
               if(var11 != 105 && var11 != 73 && (var11 != 32 || !var31)) {
                  var37 = false;
               } else {
                  var37 = true;
               }

               byte var46 = 0;
               byte var48 = 0;
               double var8;
               Object var41;
               if(var39 != 0) {
                  if(var39 == 105) {
                     var6 = Double.POSITIVE_INFINITY;
                  } else {
                     var6 = Double.NaN;
                  }

                  var8 = var6;
                  if(var32) {
                     var8 = -var6;
                  }

                  var41 = new DFloNum(var8);
                  var38 = (char)var48;
               } else if(var18 < 0 && var15 < 0) {
                  var41 = valueOf(var0, var14, var16 - var14, var4, var32, var24);
                  if(var13 != null) {
                     if(((IntNum)var41).isZero()) {
                        var26 = var13.isZero();
                        if(var37) {
                           if(var26) {
                              var6 = Double.NaN;
                           } else if(var27) {
                              var6 = Double.NEGATIVE_INFINITY;
                           } else {
                              var6 = Double.POSITIVE_INFINITY;
                           }

                           var41 = new DFloNum(var6);
                        } else {
                           if(var26) {
                              return "0/0 is undefined";
                           }

                           var41 = RatNum.make(var13, (IntNum)var41);
                        }
                     } else {
                        var41 = RatNum.make(var13, (IntNum)var41);
                     }
                  }

                  if(var37 && ((RealNum)var41).isExact()) {
                     if(var27 && ((RealNum)var41).isZero()) {
                        var6 = 0.0D;
                     } else {
                        var6 = ((RealNum)var41).doubleValue();
                     }

                     var41 = new DFloNum(var6);
                     var38 = (char)var48;
                  } else {
                     var38 = (char)var48;
                  }
               } else {
                  var2 = var14;
                  if(var14 > var15) {
                     var2 = var14;
                     if(var15 >= 0) {
                        var2 = var15;
                     }
                  }

                  if(var13 != null) {
                     return "floating-point number after fraction symbol \'/\'";
                  }

                  String var43 = new String(var0, var2, var16 - var2);
                  var38 = (char)var46;
                  String var42 = var43;
                  if(var18 >= 0) {
                     char var40 = Character.toLowerCase(var0[var18]);
                     var38 = var40;
                     var42 = var43;
                     if(var40 != 101) {
                        var1 = var18 - var2;
                        var42 = var43.substring(0, var1) + 'e' + var43.substring(var1 + 1);
                        var38 = var40;
                     }
                  }

                  var8 = Convert.parseDouble(var42);
                  var6 = var8;
                  if(var32) {
                     var6 = -var8;
                  }

                  var41 = new DFloNum(var6);
               }

               Object var45;
               label365: {
                  if(var11 != 101) {
                     var45 = var41;
                     if(var11 != 69) {
                        break label365;
                     }
                  }

                  var45 = ((RealNum)var41).toExact();
               }

               if(var16 >= var21) {
                  if(var45 instanceof DFloNum && var38 > 0 && var38 != 101) {
                     var6 = ((RealNum)var45).doubleValue();
                     switch(var38) {
                     case 100:
                        return Double.valueOf(var6);
                     case 102:
                     case 115:
                        return Float.valueOf((float)var6);
                     case 108:
                        return BigDecimal.valueOf(var6);
                     }
                  }

                  return var45;
               }

               var1 = var16 + 1;
               var3 = var0[var16];
               if(var3 != 64) {
                  if(var3 != 45 && var3 != 43) {
                     var2 = 0;

                     while(true) {
                        if(!Character.isLetter(var3)) {
                           --var1;
                           var4 = var2;
                           var2 = var1;
                           break;
                        }

                        var5 = var2 + 1;
                        var2 = var1;
                        var4 = var5;
                        if(var1 == var21) {
                           break;
                        }

                        var3 = var0[var1];
                        ++var1;
                        var2 = var5;
                     }

                     if(var4 == 1) {
                        var38 = var0[var2 - 1];
                        if(var38 == 105 || var38 == 73) {
                           if(var2 < var21) {
                              return "junk after imaginary suffix \'i\'";
                           }

                           return Complex.make(IntNum.zero(), (RealNum)var45);
                        }
                     }

                     return "excess junk after number";
                  }

                  --var1;
                  var33 = parseNumber(var0, var1, var21 - var1, var11, 10, var5);
                  if(var33 instanceof String) {
                     return var33;
                  }

                  if(!(var33 instanceof Complex)) {
                     return "invalid numeric constant (" + var33 + ")";
                  }

                  Complex var35 = (Complex)var33;
                  if(!var35.re().isZero()) {
                     return "invalid numeric constant";
                  }

                  return Complex.make((RealNum)var45, var35.im());
               }

               var41 = parseNumber(var0, var1, var21 - var1, var11, 10, var5);
               var33 = var41;
               if(!(var41 instanceof String)) {
                  if(!(var41 instanceof RealNum)) {
                     return "invalid complex polar constant";
                  }

                  RealNum var34 = (RealNum)var41;
                  if(((RealNum)var45).isZero() && !var34.isExact()) {
                     return new DFloNum(0.0D);
                  }

                  return Complex.polar((RealNum)var45, var34);
               }
               break;
            }

            if(var15 >= var21) {
               return "no digits";
            }

            var4 = var15 + 1;
            var10 = var0[var15];
            switch(var10) {
            case 66:
            case 98:
               if(var14 != 0) {
                  return "duplicate radix specifier";
               }

               var14 = 2;
               break;
            case 68:
            case 100:
               if(var14 != 0) {
                  return "duplicate radix specifier";
               }

               var14 = 10;
               break;
            case 69:
            case 73:
            case 101:
            case 105:
               if(var3 != 0) {
                  if(var3 == 32) {
                     return "non-prefix exactness specifier";
                  }

                  return "duplicate exactness specifier";
               }

               var3 = var10;
               break;
            case 79:
            case 111:
               if(var14 != 0) {
                  return "duplicate radix specifier";
               }

               var14 = 8;
               break;
            case 88:
            case 120:
               if(var14 != 0) {
                  return "duplicate radix specifier";
               }

               var14 = 16;
               break;
            default:
               var15 = 0;

               while(true) {
                  var16 = Character.digit(var10, 10);
                  if(var16 < 0) {
                     if(var10 != 82 && var10 != 114) {
                        return "unknown modifier \'#" + var10 + '\'';
                     }

                     if(var14 != 0) {
                        return "duplicate radix specifier";
                     }

                     if(var15 < 2 || var15 > 35) {
                        return "invalid radix specifier";
                     }

                     var14 = var15;
                     break;
                  }

                  var15 = var15 * 10 + var16;
                  if(var4 >= var21) {
                     return "missing letter after \'#\'";
                  }

                  var10 = var0[var4];
                  ++var4;
               }
            }

            if(var4 >= var21) {
               return "no digits";
            }

            var10 = var0[var4];
            var15 = var4 + 1;
         }
      }

      return var33;
   }

   public static Object readCharacter(LispReader var0) throws IOException, SyntaxException {
      int var3 = var0.read();
      if(var3 < 0) {
         var0.eofError("unexpected EOF in character literal");
      }

      int var5 = var0.tokenBufferLength;
      var0.tokenBufferAppend(var3);
      var0.readToken(var0.read(), 'D', ReadTable.getCurrent());
      char[] var1 = var0.tokenBuffer;
      int var6 = var0.tokenBufferLength - var5;
      if(var6 == 1) {
         return Char.make(var1[var5]);
      } else {
         String var2 = new String(var1, var5, var6);
         var3 = Char.nameToChar(var2);
         if(var3 >= 0) {
            return Char.make(var3);
         } else {
            char var7 = var1[var5];
            int var4;
            if(var7 == 120 || var7 == 88) {
               var4 = 0;
               var3 = 1;

               while(true) {
                  if(var3 == var6) {
                     return Char.make(var4);
                  }

                  int var8 = Character.digit(var1[var5 + var3], 16);
                  if(var8 < 0) {
                     break;
                  }

                  var4 = var4 * 16 + var8;
                  if(var4 > 1114111) {
                     break;
                  }

                  ++var3;
               }
            }

            var4 = Character.digit(var7, 8);
            if(var4 >= 0) {
               var3 = 1;

               while(true) {
                  if(var3 == var6) {
                     return Char.make(var4);
                  }

                  int var9 = Character.digit(var1[var5 + var3], 8);
                  if(var9 < 0) {
                     break;
                  }

                  var4 = var4 * 8 + var9;
                  ++var3;
               }
            }

            var0.error("unknown character name: " + var2);
            return Char.make(63);
         }
      }
   }

   public static Object readNumberWithRadix(int var0, LispReader var1, int var2) throws IOException, SyntaxException {
      var0 = var1.tokenBufferLength - var0;
      var1.readToken(var1.read(), 'P', ReadTable.getCurrent());
      int var5 = var1.tokenBufferLength;
      Object var3;
      if(var0 == var5) {
         var1.error("missing numeric token");
         var3 = IntNum.zero();
      } else {
         Object var4 = parseNumber(var1.tokenBuffer, var0, var5 - var0, '\u0000', var2, 0);
         if(var4 instanceof String) {
            var1.error((String)var4);
            return IntNum.zero();
         }

         var3 = var4;
         if(var4 == null) {
            var1.error("invalid numeric constant");
            return IntNum.zero();
         }
      }

      return var3;
   }

   public static SimpleVector readSimpleVector(LispReader var0, char var1) throws IOException, SyntaxException {
      int var3 = 0;

      while(true) {
         int var4 = var0.read();
         if(var4 < 0) {
            var0.eofError("unexpected EOF reading uniform vector");
         }

         int var5 = Character.digit((char)var4, 10);
         if(var5 < 0) {
            if((var3 == 8 || var3 == 16 || var3 == 32 || var3 == 64) && (var1 != 70 || var3 >= 32) && var4 == 40) {
               Object var2 = ReaderParens.readList(var0, 40, -1, 41);
               if(LList.listLength(var2, false) < 0) {
                  var0.error("invalid elements in uniform vector syntax");
                  return null;
               }

               Sequence var6 = (Sequence)var2;
               switch(var1) {
               case 70:
                  switch(var3) {
                  case 32:
                     return new F32Vector(var6);
                  case 64:
                     return new F64Vector(var6);
                  }
               case 83:
                  switch(var3) {
                  case 8:
                     return new S8Vector(var6);
                  case 16:
                     return new S16Vector(var6);
                  case 32:
                     return new S32Vector(var6);
                  case 64:
                     return new S64Vector(var6);
                  }
               case 85:
                  switch(var3) {
                  case 8:
                     return new U8Vector(var6);
                  case 16:
                     return new U16Vector(var6);
                  case 32:
                     return new U32Vector(var6);
                  case 64:
                     return new U64Vector(var6);
                  default:
                     return null;
                  }
               default:
                  return null;
               }
            }

            var0.error("invalid uniform vector syntax");
            return null;
         }

         var3 = var3 * 10 + var5;
      }
   }

   public static Object readSpecial(LispReader var0) throws IOException, SyntaxException {
      Values var1 = null;
      int var4 = var0.read();
      if(var4 < 0) {
         var0.eofError("unexpected EOF in #! special form");
      }

      if(var4 == 47 && var0.getLineNumber() == 0 && var0.getColumnNumber() == 3) {
         ReaderIgnoreRestOfLine.getInstance().read(var0, 35, 1);
         var1 = Values.empty;
      } else {
         int var3 = var0.tokenBufferLength;
         var0.tokenBufferAppend(var4);
         var0.readToken(var0.read(), 'D', ReadTable.getCurrent());
         var4 = var0.tokenBufferLength;
         String var2 = new String(var0.tokenBuffer, var3, var4 - var3);
         if(var2.equals("optional")) {
            return Special.optional;
         }

         if(var2.equals("rest")) {
            return Special.rest;
         }

         if(var2.equals("key")) {
            return Special.key;
         }

         if(var2.equals("eof")) {
            return Special.eof;
         }

         if(var2.equals("void")) {
            return QuoteExp.voidExp;
         }

         if(var2.equals("default")) {
            return Special.dfault;
         }

         if(var2.equals("undefined")) {
            return Special.undefined;
         }

         if(var2.equals("abstract")) {
            return Special.abstractSpecial;
         }

         if(!var2.equals("null")) {
            var0.error("unknown named constant #!" + var2);
            return null;
         }
      }

      return var1;
   }

   private static IntNum valueOf(char[] var0, int var1, int var2, int var3, boolean var4, long var5) {
      if(var2 + var3 <= 28) {
         long var7 = var5;
         if(var4) {
            var7 = -var5;
         }

         return IntNum.make(var7);
      } else {
         return IntNum.valueOf(var0, var1, var2, var3, var4);
      }
   }

   Object handlePostfix(Object var1, ReadTable var2, int var3, int var4) throws IOException, SyntaxException {
      Object var5 = var1;
      if(var1 == QuoteExp.voidExp) {
         var5 = Values.empty;
      }

      while(true) {
         int var6 = this.port.peek();
         if(var6 < 0 || var6 != var2.postfixLookupOperator) {
            return var5;
         }

         this.port.read();
         if(!this.validPostfixLookupStart(this.port.peek(), var2)) {
            this.unread();
            return var5;
         }

         var6 = this.port.read();
         var1 = this.readValues(var6, var2.lookup(var6), var2);
         Pair var7 = LList.list2(var5, LList.list2(var2.makeSymbol("quasiquote"), var1));
         var5 = PairWithPosition.make(LispLanguage.lookup_sym, var7, this.port.getName(), var3 + 1, var4 + 1);
      }
   }

   protected Object makeNil() {
      return LList.Empty;
   }

   protected Pair makePair(Object var1, int var2, int var3) {
      return this.makePair(var1, LList.Empty, var2, var3);
   }

   protected Pair makePair(Object var1, Object var2, int var3, int var4) {
      String var5 = this.port.getName();
      return (Pair)(var5 != null && var3 >= 0?PairWithPosition.make(var1, var2, var5, var3 + 1, var4 + 1):Pair.make(var1, var2));
   }

   protected Object readAndHandleToken(int var1, int var2, ReadTable var3) throws IOException, SyntaxException {
      this.readToken(var1, getReadCase(), var3);
      int var18 = this.tokenBufferLength;
      if(!this.seenEscapes) {
         Object var6 = parseNumber(this.tokenBuffer, var2, var18 - var2, '\u0000', 0, 1);
         if(var6 != null && !(var6 instanceof String)) {
            return var6;
         }
      }

      char var19 = getReadCase();
      char var8 = var19;
      char var4;
      int var9;
      int var10;
      int var11;
      int var12;
      int var23;
      if(var19 == 73) {
         var23 = 0;
         var9 = 0;

         for(var1 = var2; var1 < var18; var23 = var12) {
            var4 = this.tokenBuffer[var1];
            if(var4 == '\uffff') {
               var11 = var1 + 1;
               var12 = var23;
               var10 = var9;
            } else if(Character.isLowerCase(var4)) {
               var10 = var9 + 1;
               var11 = var1;
               var12 = var23;
            } else {
               var11 = var1;
               var10 = var9;
               var12 = var23;
               if(Character.isUpperCase(var4)) {
                  var12 = var23 + 1;
                  var11 = var1;
                  var10 = var9;
               }
            }

            var1 = var11 + 1;
            var9 = var10;
         }

         if(var9 == 0) {
            var8 = 68;
         } else if(var23 == 0) {
            var8 = 85;
         } else {
            var8 = 80;
         }
      }

      boolean var17;
      if(var18 >= var2 + 2 && this.tokenBuffer[var18 - 1] == 125 && this.tokenBuffer[var18 - 2] != '\uffff' && this.peek() == 58) {
         var17 = true;
      } else {
         var17 = false;
      }

      var11 = -1;
      var9 = -1;
      var12 = -1;
      int var15 = 0;
      int var13 = var2;

      int var16;
      for(var1 = var2; var13 < var18; var9 = var16) {
         char var5 = this.tokenBuffer[var13];
         char[] var21;
         if(var5 == '\uffff') {
            ++var13;
            if(var13 < var18) {
               var21 = this.tokenBuffer;
               var10 = var1 + 1;
               var21[var1] = this.tokenBuffer[var13];
               var16 = var9;
               var1 = var10;
               var10 = var15;
            } else {
               var10 = var15;
               var16 = var9;
            }
         } else {
            var10 = var15;
            var16 = var9;
            int var14 = var12;
            if(var17) {
               if(var5 == 123) {
                  if(var9 < 0) {
                     var16 = var1;
                  } else {
                     var16 = var9;
                     if(var15 == 0) {
                        var16 = var9;
                     }
                  }

                  var10 = var15 + 1;
                  var14 = var12;
               } else {
                  var10 = var15;
                  var16 = var9;
                  var14 = var12;
                  if(var5 == 125) {
                     --var15;
                     if(var15 < 0) {
                        var10 = var15;
                        var16 = var9;
                        var14 = var12;
                     } else {
                        var10 = var15;
                        var16 = var9;
                        var14 = var12;
                        if(var15 == 0) {
                           if(var12 < 0) {
                              var14 = var1;
                              var10 = var15;
                              var16 = var9;
                           } else {
                              var10 = var15;
                              var16 = var9;
                              var14 = var12;
                           }
                        }
                     }
                  }
               }
            }

            if(var10 > 0) {
               var9 = var11;
               var4 = var5;
            } else if(var5 == 58) {
               if(var11 >= 0) {
                  var9 = -1;
               } else {
                  var9 = var1;
               }

               var4 = var5;
            } else if(var8 == 85) {
               var4 = Character.toUpperCase(var5);
               var9 = var11;
            } else {
               var4 = var5;
               var9 = var11;
               if(var8 == 68) {
                  var4 = Character.toLowerCase(var5);
                  var9 = var11;
               }
            }

            var21 = this.tokenBuffer;
            var11 = var1 + 1;
            var21[var1] = var4;
            var1 = var11;
            var11 = var9;
            var12 = var14;
         }

         ++var13;
         var15 = var10;
      }

      var23 = var1 - var2;
      if(var9 >= 0 && var12 > var9) {
         String var22;
         if(var9 > 0) {
            var22 = new String(this.tokenBuffer, var2, var9 - var2);
         } else {
            var22 = null;
         }

         var1 = var9 + 1;
         String var7 = new String(this.tokenBuffer, var1, var12 - var1);
         this.read();
         var1 = this.read();
         Object var20 = this.readValues(var1, var3.lookup(var1), var3);
         if(!(var20 instanceof SimpleSymbol)) {
            this.error("expected identifier in symbol after \'{URI}:\'");
         }

         return Symbol.valueOf(var20.toString(), var7, var22);
      } else if(var3.initialColonIsKeyword && var11 == var2 && var23 > 1) {
         ++var2;
         return Keyword.make((new String(this.tokenBuffer, var2, var1 - var2)).intern());
      } else {
         return var3.finalColonIsKeyword && var11 == var1 - 1 && (var23 > 1 || this.seenEscapes)?Keyword.make((new String(this.tokenBuffer, var2, var23 - 1)).intern()):var3.makeSymbol(new String(this.tokenBuffer, var2, var23));
      }
   }

   public Object readCommand() throws IOException, SyntaxException {
      return this.readObject();
   }

   public int readEscape() throws IOException, SyntaxException {
      int var1 = this.read();
      if(var1 < 0) {
         this.eofError("unexpected EOF in character literal");
         return -1;
      } else {
         return this.readEscape(var1);
      }
   }

   public final int readEscape(int var1) throws IOException, SyntaxException {
      int var2 = var1;
      int var3;
      switch((char)var1) {
      case 9:
      case 10:
      case 13:
      case 32:
         while(true) {
            if(var2 < 0) {
               this.eofError("unexpected EOF in literal");
               return -1;
            }

            if(var2 == 10) {
               break;
            }

            if(var2 == 13) {
               if(this.peek() == 10) {
                  this.skip();
               }

               var2 = 10;
               break;
            }

            if(var2 != 32 && var2 != 9) {
               this.unread(var2);
               break;
            }

            var2 = this.read();
         }

         var1 = var2;
         if(var2 == 10) {
            do {
               var1 = this.read();
               if(var1 < 0) {
                  this.eofError("unexpected EOF in literal");
                  return -1;
               }
            } while(var1 == 32 || var1 == 9);

            this.unread(var1);
            return -2;
         }
         break;
      case 34:
         var1 = 34;
         break;
      case 48:
      case 49:
      case 50:
      case 51:
      case 52:
      case 53:
      case 54:
      case 55:
         var2 = var1 - 48;
         var1 = 0;

         while(true) {
            var3 = var1 + 1;
            var1 = var2;
            if(var3 >= 3) {
               return var1;
            }

            int var4 = this.read();
            var1 = Character.digit((char)var4, 8);
            if(var1 < 0) {
               var1 = var2;
               if(var4 >= 0) {
                  this.unread(var4);
                  var1 = var2;
               }

               return var1;
            }

            var2 = (var2 << 3) + var1;
            var1 = var3;
         }
      case 67:
         if(this.read() != 45) {
            this.error("Invalid escape character syntax");
            return 63;
         }
      case 77:
         if(this.read() != 45) {
            this.error("Invalid escape character syntax");
            return 63;
         }

         var2 = this.read();
         var1 = var2;
         if(var2 == 92) {
            var1 = this.readEscape();
         }

         return var1 | 128;
      case 88:
      case 120:
         return this.readHexEscape();
      case 92:
         var1 = 92;
         break;
      case 94:
         var2 = this.read();
         var1 = var2;
         if(var2 == 92) {
            var1 = this.readEscape();
         }

         if(var1 == 63) {
            return 127;
         }

         return var1 & 159;
      case 97:
         var1 = 7;
         break;
      case 98:
         var1 = 8;
         break;
      case 101:
         var1 = 27;
         break;
      case 102:
         var1 = 12;
         break;
      case 110:
         var1 = 10;
         break;
      case 114:
         var1 = 13;
         break;
      case 116:
         var1 = 9;
         break;
      case 117:
         var2 = 0;
         var1 = 4;

         while(true) {
            var3 = var1 - 1;
            var1 = var2;
            if(var3 < 0) {
               return var1;
            }

            var1 = this.read();
            if(var1 < 0) {
               this.eofError("premature EOF in \\u escape");
            }

            var1 = Character.digit((char)var1, 16);
            if(var1 < 0) {
               this.error("non-hex character following \\u");
            }

            var2 = var2 * 16 + var1;
            var1 = var3;
         }
      case 118:
         var1 = 11;
      }

      return var1;
   }

   public int readHexEscape() throws IOException, SyntaxException {
      int var1 = 0;

      while(true) {
         int var2 = this.read();
         int var3 = Character.digit((char)var2, 16);
         if(var3 < 0) {
            if(var2 != 59 && var2 >= 0) {
               this.unread(var2);
            }

            return var1;
         }

         var1 = (var1 << 4) + var3;
      }
   }

   public final void readNestedComment(char var1, char var2) throws IOException, SyntaxException {
      int var5 = 1;
      int var7 = this.port.getLineNumber();
      int var8 = this.port.getColumnNumber();

      int var4;
      do {
         int var6 = this.read();
         int var3;
         if(var6 == 124) {
            var6 = this.read();
            var3 = var6;
            var4 = var5;
            if(var6 == var1) {
               var4 = var5 - 1;
               var3 = var6;
            }
         } else {
            var3 = var6;
            var4 = var5;
            if(var6 == var1) {
               var6 = this.read();
               var3 = var6;
               var4 = var5;
               if(var6 == var2) {
                  var4 = var5 + 1;
                  var3 = var6;
               }
            }
         }

         if(var3 < 0) {
            this.eofError("unexpected end-of-file in " + var1 + var2 + " comment starting here", var7 + 1, var8 - 1);
            return;
         }

         var5 = var4;
      } while(var4 > 0);

   }

   public Object readObject() throws IOException, SyntaxException {
      // $FF: Couldn't be decompiled
   }

   public final Object readObject(int var1) throws IOException, SyntaxException {
      this.unread(var1);
      return this.readObject();
   }

   void readToken(int var1, char var2, ReadTable var3) throws IOException, SyntaxException {
      boolean var5 = false;
      byte var9 = 0;
      int var6 = var1;
      var1 = var9;

      while(true) {
         if(var6 < 0) {
            if(!var5) {
               break;
            }

            this.eofError("unexpected EOF between escapes");
         }

         ReadTableEntry var4 = var3.lookup(var6);
         int var10 = var4.getKind();
         boolean var11;
         if(var10 == 0) {
            if(var5) {
               this.tokenBufferAppend('\uffff');
               this.tokenBufferAppend(var6);
               var11 = var5;
            } else {
               label77: {
                  if(var6 == 125) {
                     --var1;
                     if(var1 >= 0) {
                        this.tokenBufferAppend(var6);
                        var11 = var5;
                        break label77;
                     }
                  }

                  this.unread(var6);
                  break;
               }
            }
         } else {
            int var7 = var10;
            if(var6 == var3.postfixLookupOperator) {
               var7 = var10;
               if(!var5) {
                  int var8 = this.port.peek();
                  if(var8 == var3.postfixLookupOperator) {
                     this.unread(var6);
                     return;
                  }

                  var7 = var10;
                  if(this.validPostfixLookupStart(var8, var3)) {
                     var7 = 5;
                  }
               }
            }

            if(var7 == 3) {
               var6 = this.read();
               if(var6 < 0) {
                  this.eofError("unexpected EOF after single escape");
               }

               var10 = var6;
               if(var3.hexEscapeAfterBackslash) {
                  label67: {
                     if(var6 != 120) {
                        var10 = var6;
                        if(var6 != 88) {
                           break label67;
                        }
                     }

                     var10 = this.readHexEscape();
                  }
               }

               this.tokenBufferAppend('\uffff');
               this.tokenBufferAppend(var10);
               this.seenEscapes = true;
               var11 = var5;
            } else if(var7 == 4) {
               if(!var5) {
                  var11 = true;
               } else {
                  var11 = false;
               }

               this.seenEscapes = true;
            } else if(var5) {
               this.tokenBufferAppend('\uffff');
               this.tokenBufferAppend(var6);
               var11 = var5;
            } else {
               var10 = var1;
               switch(var7) {
               case 1:
                  this.unread(var6);
                  return;
               case 2:
                  var10 = var1;
                  if(var6 == 123) {
                     var10 = var1;
                     if(var4 == ReadTableEntry.brace) {
                        var10 = var1 + 1;
                     }
                  }
               case 3:
               default:
                  var11 = var5;
                  break;
               case 4:
                  var11 = true;
                  this.seenEscapes = true;
                  break;
               case 5:
                  this.unread(var6);
                  return;
               case 6:
                  this.tokenBufferAppend(var6);
                  var1 = var10;
                  var11 = var5;
               }
            }
         }

         var6 = this.read();
         var5 = var11;
      }

   }

   public Object readValues(int var1, ReadTable var2) throws IOException, SyntaxException {
      return this.readValues(var1, var2.lookup(var1), var2);
   }

   public Object readValues(int var1, ReadTableEntry var2, ReadTable var3) throws IOException, SyntaxException {
      int var4 = this.tokenBufferLength;
      this.seenEscapes = false;
      switch(var2.getKind()) {
      case 0:
         String var5 = "invalid character #\\" + (char)var1;
         if(this.interactive) {
            this.fatal(var5);
         } else {
            this.error(var5);
         }

         return Values.empty;
      case 1:
         return Values.empty;
      case 2:
      case 3:
      case 4:
      default:
         return this.readAndHandleToken(var1, var4, var3);
      case 5:
      case 6:
         return var2.read(this, var1, -1);
      }
   }

   protected void setCdr(Object var1, Object var2) {
      ((Pair)var1).setCdrBackdoor(var2);
   }

   protected boolean validPostfixLookupStart(int var1, ReadTable var2) throws IOException {
      if(var1 >= 0 && var1 != 58 && var1 != var2.postfixLookupOperator) {
         if(var1 == 44) {
            return true;
         }

         var1 = var2.lookup(var1).getKind();
         if(var1 == 2 || var1 == 6 || var1 == 4 || var1 == 3) {
            return true;
         }
      }

      return false;
   }
}
