package gnu.kawa.functions;

import gnu.kawa.functions.IntegerFormat;
import gnu.kawa.functions.LispCharacterFormat;
import gnu.kawa.functions.LispChoiceFormat;
import gnu.kawa.functions.LispEscapeFormat;
import gnu.kawa.functions.LispFreshlineFormat;
import gnu.kawa.functions.LispIndentFormat;
import gnu.kawa.functions.LispIterationFormat;
import gnu.kawa.functions.LispNewlineFormat;
import gnu.kawa.functions.LispObjectFormat;
import gnu.kawa.functions.LispPluralFormat;
import gnu.kawa.functions.LispPrettyFormat;
import gnu.kawa.functions.LispRealFormat;
import gnu.kawa.functions.LispRepositionFormat;
import gnu.kawa.functions.LispTabulateFormat;
import gnu.kawa.functions.ObjectFormat;
import gnu.lists.Pair;
import gnu.lists.Sequence;
import gnu.math.IntNum;
import gnu.text.CaseConvertFormat;
import gnu.text.Char;
import gnu.text.CompoundFormat;
import gnu.text.FlushFormat;
import gnu.text.LiteralFormat;
import gnu.text.ReportFormat;
import java.text.ParseException;
import java.util.Stack;
import java.util.Vector;

public class LispFormat extends CompoundFormat {

   public static final String paramFromCount = "<from count>";
   public static final String paramFromList = "<from list>";
   public static final String paramUnspecified = "<unspecified>";


   public LispFormat(String var1) throws ParseException {
      this((char[])var1.toCharArray());
   }

   public LispFormat(char[] var1) throws ParseException {
      this(var1, 0, var1.length);
   }

   public LispFormat(char[] var1, int var2, int var3) throws ParseException {
      super((java.text.Format[])null, 0);
      byte var10 = -1;
      byte var9 = 0;
      StringBuffer var6 = new StringBuffer(100);
      Stack var7 = new Stack();
      int var13 = var2 + var3;
      int var8 = var2;
      var2 = var10;
      var3 = var9;

      while(true) {
         label305:
         while(true) {
            if((var8 >= var13 || var1[var8] == 126) && var6.length() > 0) {
               var7.push(new LiteralFormat(var6));
               var6.setLength(0);
            }

            if(var8 >= var13) {
               if(var8 > var13) {
                  throw new IndexOutOfBoundsException();
               }

               if(var2 >= 0) {
                  throw new ParseException("missing ~] or ~}", var8);
               }

               this.length = var7.size();
               this.formats = new java.text.Format[this.length];
               var7.copyInto(this.formats);
               return;
            }

            int var27 = var8 + 1;
            char var4 = var1[var8];
            if(var4 == 126) {
               int var24 = var7.size();
               var8 = var27 + 1;
               var4 = var1[var27];

               int var11;
               int var12;
               boolean var18;
               while(true) {
                  if(var4 == 35) {
                     var7.push("<from count>");
                     var4 = var1[var8];
                     ++var8;
                  } else if(var4 != 118 && var4 != 86) {
                     if(var4 != 45 && Character.digit(var4, 10) < 0) {
                        if(var4 == 39) {
                           var27 = var8 + 1;
                           var7.push(Char.make(var1[var8]));
                           var8 = var27 + 1;
                           var4 = var1[var27];
                        } else {
                           if(var4 != 44) {
                              break;
                           }

                           var7.push("<unspecified>");
                        }
                     } else {
                        if(var4 == 45) {
                           var18 = true;
                        } else {
                           var18 = false;
                        }

                        if(var18) {
                           var27 = var8 + 1;
                           var4 = var1[var8];
                           var8 = var27;
                        }

                        var27 = 0;
                        var11 = var8;

                        while(true) {
                           var12 = Character.digit(var4, 10);
                           if(var12 < 0) {
                              IntNum var5;
                              if(var11 - var8 < 8) {
                                 var8 = var27;
                                 if(var18) {
                                    var8 = -var27;
                                 }

                                 var5 = IntNum.make(var8);
                              } else {
                                 var5 = IntNum.valueOf(var1, var8, var11 - var8, 10, var18);
                              }

                              var7.push(var5);
                              var8 = var11;
                              break;
                           }

                           var27 = var27 * 10 + var12;
                           var4 = var1[var11];
                           ++var11;
                        }
                     }
                  } else {
                     var7.push("<from list>");
                     var4 = var1[var8];
                     ++var8;
                  }

                  if(var4 != 44) {
                     break;
                  }

                  var4 = var1[var8];
                  ++var8;
               }

               boolean var19 = false;
               var18 = false;
               var27 = var8;

               while(true) {
                  if(var4 == 58) {
                     var19 = true;
                  } else {
                     if(var4 != 64) {
                        var4 = Character.toUpperCase(var4);
                        var8 = var7.size() - var24;
                        int var14;
                        int var15;
                        Object var21;
                        LispIterationFormat var20;
                        LispChoiceFormat var22;
                        LispPrettyFormat var26;
                        byte var29;
                        switch(var4) {
                        case 10:
                           if(var18) {
                              var6.append(var4);
                           }

                           var8 = var27;
                           if(var19) {
                              continue label305;
                           }

                           while(true) {
                              var8 = var27;
                              if(var27 >= var13) {
                                 continue label305;
                              }

                              var8 = var27 + 1;
                              if(!Character.isWhitespace(var1[var27])) {
                                 --var8;
                                 continue label305;
                              }

                              var27 = var8;
                           }
                        case 33:
                           var21 = FlushFormat.getInstance();
                           break;
                        case 36:
                        case 69:
                        case 70:
                        case 71:
                           var21 = new LispRealFormat();
                           ((LispRealFormat)var21).op = var4;
                           ((LispRealFormat)var21).arg1 = getParam(var7, var24);
                           ((LispRealFormat)var21).arg2 = getParam(var7, var24 + 1);
                           ((LispRealFormat)var21).arg3 = getParam(var7, var24 + 2);
                           ((LispRealFormat)var21).arg4 = getParam(var7, var24 + 3);
                           if(var4 != 36) {
                              ((LispRealFormat)var21).arg5 = getParam(var7, var24 + 4);
                              if(var4 == 69 || var4 == 71) {
                                 ((LispRealFormat)var21).arg6 = getParam(var7, var24 + 5);
                                 ((LispRealFormat)var21).arg7 = getParam(var7, var24 + 6);
                              }
                           }

                           ((LispRealFormat)var21).showPlus = var18;
                           ((LispRealFormat)var21).internalPad = var19;
                           if(((LispRealFormat)var21).argsUsed == 0) {
                              var21 = ((LispRealFormat)var21).resolve((Object[])null, 0);
                           }
                           break;
                        case 37:
                           var11 = getParam(var7, var24);
                           var8 = var11;
                           if(var11 == -1073741824) {
                              var8 = 1;
                           }

                           var21 = LispNewlineFormat.getInstance(var8, 76);
                           break;
                        case 38:
                           var21 = new LispFreshlineFormat(getParam(var7, var24));
                           break;
                        case 40:
                           char var23;
                           if(var19) {
                              if(var18) {
                                 var23 = 85;
                              } else {
                                 var23 = 67;
                              }
                           } else if(var18) {
                              var23 = 84;
                           } else {
                              var23 = 76;
                           }

                           CaseConvertFormat var28 = new CaseConvertFormat((java.text.Format)null, var23);
                           var7.setSize(var24);
                           var7.push(var28);
                           var7.push(IntNum.make(var2));
                           var2 = var24;
                           var8 = var27;
                           continue label305;
                        case 41:
                           if(var2 >= 0 && var7.elementAt(var2) instanceof CaseConvertFormat) {
                              ((CaseConvertFormat)var7.elementAt(var2)).setBaseFormat(popFormats(var7, var2 + 2, var24));
                              var2 = ((IntNum)var7.pop()).intValue();
                              var8 = var27;
                              continue label305;
                           }

                           throw new ParseException("saw ~) without matching ~(", var27);
                        case 42:
                           var21 = new LispRepositionFormat(getParam(var7, var24), var19, var18);
                           break;
                        case 59:
                           if(var2 >= 0) {
                              if(var7.elementAt(var2) instanceof LispChoiceFormat) {
                                 var22 = (LispChoiceFormat)var7.elementAt(var2);
                                 if(var19) {
                                    var22.lastIsDefault = true;
                                 }

                                 var7.push(popFormats(var7, var2 + 3 + var3, var24));
                                 ++var3;
                                 var8 = var27;
                                 continue label305;
                              }

                              if(var7.elementAt(var2) instanceof LispPrettyFormat) {
                                 var26 = (LispPrettyFormat)var7.elementAt(var2);
                                 if(var18) {
                                    var26.perLine = true;
                                 }

                                 var7.push(popFormats(var7, var2 + 3 + var3, var24));
                                 ++var3;
                                 var8 = var27;
                                 continue label305;
                              }
                           }

                           throw new ParseException("saw ~; without matching ~[ or ~<", var27);
                        case 60:
                           var26 = new LispPrettyFormat();
                           var26.seenAt = var18;
                           if(var19) {
                              var26.prefix = "(";
                              var26.suffix = ")";
                           } else {
                              var26.prefix = "";
                              var26.suffix = "";
                           }

                           var7.setSize(var24);
                           var7.push(var26);
                           var7.push(IntNum.make(var2));
                           var7.push(IntNum.make(var3));
                           var2 = var24;
                           var3 = 0;
                           var8 = var27;
                           continue label305;
                        case 62:
                           if(var2 >= 0 && var7.elementAt(var2) instanceof LispPrettyFormat) {
                              var7.push(popFormats(var7, var2 + 3 + var3, var24));
                              var26 = (LispPrettyFormat)var7.elementAt(var2);
                              var26.segments = getFormats(var7, var2 + 3, var7.size());
                              var7.setSize(var2 + 3);
                              ((IntNum)var7.pop()).intValue();
                              var24 = ((IntNum)var7.pop()).intValue();
                              if(!var19) {
                                 throw new ParseException("not implemented: justfication i.e. ~<...~>", var27);
                              }

                              var11 = var26.segments.length;
                              if(var11 > 3) {
                                 throw new ParseException("too many segments in Logical Block format", var27);
                              }

                              if(var11 >= 2) {
                                 if(!(var26.segments[0] instanceof LiteralFormat)) {
                                    throw new ParseException("prefix segment is not literal", var27);
                                 }

                                 var26.prefix = ((LiteralFormat)var26.segments[0]).content();
                                 var26.body = var26.segments[1];
                              } else {
                                 var26.body = var26.segments[0];
                              }

                              var8 = var27;
                              var2 = var24;
                              if(var11 >= 3) {
                                 if(!(var26.segments[2] instanceof LiteralFormat)) {
                                    throw new ParseException("suffix segment is not literal", var27);
                                 }

                                 var26.suffix = ((LiteralFormat)var26.segments[2]).content();
                                 var8 = var27;
                                 var2 = var24;
                              }
                              continue label305;
                           }

                           throw new ParseException("saw ~> without matching ~<", var27);
                        case 63:
                           var21 = new LispIterationFormat();
                           ((LispIterationFormat)var21).seenAt = var18;
                           ((LispIterationFormat)var21).maxIterations = 1;
                           ((LispIterationFormat)var21).atLeastOnce = true;
                           break;
                        case 65:
                        case 83:
                        case 87:
                        case 89:
                           if(var4 != 65) {
                              var19 = true;
                           } else {
                              var19 = false;
                           }

                           var21 = ObjectFormat.getInstance(var19);
                           if(var8 > 0) {
                              var11 = getParam(var7, var24);
                              var12 = getParam(var7, var24 + 1);
                              var14 = getParam(var7, var24 + 2);
                              var15 = getParam(var7, var24 + 3);
                              ReportFormat var25 = (ReportFormat)var21;
                              if(var18) {
                                 var29 = 0;
                              } else {
                                 var29 = 100;
                              }

                              var21 = new LispObjectFormat(var25, var11, var12, var14, var15, var29);
                           }
                           break;
                        case 66:
                        case 68:
                        case 79:
                        case 82:
                        case 88:
                           var11 = var24;
                           if(var4 == 82) {
                              var8 = getParam(var7, var24);
                              var11 = var24 + 1;
                           } else if(var4 == 68) {
                              var8 = 10;
                           } else if(var4 == 79) {
                              var8 = 8;
                           } else if(var4 == 88) {
                              var8 = 16;
                           } else {
                              var8 = 2;
                           }

                           var14 = getParam(var7, var11);
                           var15 = getParam(var7, var11 + 1);
                           int var16 = getParam(var7, var11 + 2);
                           int var17 = getParam(var7, var11 + 3);
                           var11 = 0;
                           if(var19) {
                              var11 = 0 | 1;
                           }

                           var12 = var11;
                           if(var18) {
                              var12 = var11 | 2;
                           }

                           var21 = IntegerFormat.getInstance(var8, var14, var15, var16, var17, var12);
                           break;
                        case 67:
                           if(var8 > 0) {
                              var8 = getParam(var7, var24);
                           } else {
                              var8 = -1610612736;
                           }

                           var21 = LispCharacterFormat.getInstance(var8, 1, var18, var19);
                           break;
                        case 73:
                           var11 = getParam(var7, var24);
                           var8 = var11;
                           if(var11 == -1073741824) {
                              var8 = 0;
                           }

                           var21 = LispIndentFormat.getInstance(var8, var19);
                           break;
                        case 80:
                           var21 = LispPluralFormat.getInstance(var19, var18);
                           break;
                        case 84:
                           var21 = new LispTabulateFormat(getParam(var7, var24), getParam(var7, var24 + 1), getParam(var7, var24 + 2), var18);
                           break;
                        case 91:
                           var22 = new LispChoiceFormat();
                           var22.param = getParam(var7, var24);
                           if(var22.param == -1073741824) {
                              var22.param = -1610612736;
                           }

                           if(var19) {
                              var22.testBoolean = true;
                           }

                           if(var18) {
                              var22.skipIfFalse = true;
                           }

                           var7.setSize(var24);
                           var7.push(var22);
                           var7.push(IntNum.make(var2));
                           var7.push(IntNum.make(var3));
                           var2 = var24;
                           var3 = 0;
                           var8 = var27;
                           continue label305;
                        case 93:
                           if(var2 >= 0 && var7.elementAt(var2) instanceof LispChoiceFormat) {
                              var7.push(popFormats(var7, var2 + 3 + var3, var24));
                              ((LispChoiceFormat)var7.elementAt(var2)).choices = getFormats(var7, var2 + 3, var7.size());
                              var7.setSize(var2 + 3);
                              var3 = ((IntNum)var7.pop()).intValue();
                              var2 = ((IntNum)var7.pop()).intValue();
                              var8 = var27;
                              continue label305;
                           }

                           throw new ParseException("saw ~] without matching ~[", var27);
                        case 94:
                           var21 = new LispEscapeFormat(getParam(var7, var24), getParam(var7, var24 + 1), getParam(var7, var24 + 2));
                           break;
                        case 95:
                           var8 = getParam(var7, var24);
                           var11 = var8;
                           if(var8 == -1073741824) {
                              var11 = 1;
                           }

                           if(var19 && var18) {
                              ;
                           }

                           if(var18 && var19) {
                              var29 = 82;
                           } else if(var18) {
                              var29 = 77;
                           } else if(var19) {
                              var29 = 70;
                           } else {
                              var29 = 78;
                           }

                           var21 = LispNewlineFormat.getInstance(var11, var29);
                           break;
                        case 123:
                           var20 = new LispIterationFormat();
                           var20.seenAt = var18;
                           var20.seenColon = var19;
                           var20.maxIterations = getParam(var7, var24);
                           var7.setSize(var24);
                           var7.push(var20);
                           var7.push(IntNum.make(var2));
                           var2 = var24;
                           var8 = var27;
                           continue label305;
                        case 125:
                           if(var2 >= 0 && var7.elementAt(var2) instanceof LispIterationFormat) {
                              var20 = (LispIterationFormat)var7.elementAt(var2);
                              var20.atLeastOnce = var19;
                              if(var24 > var2 + 2) {
                                 var20.body = popFormats(var7, var2 + 2, var24);
                              }

                              var2 = ((IntNum)var7.pop()).intValue();
                              var8 = var27;
                              continue label305;
                           }

                           throw new ParseException("saw ~} without matching ~{", var27);
                        case 126:
                           if(var8 == 0) {
                              var6.append(var4);
                              var8 = var27;
                              continue label305;
                           }
                        case 124:
                           var8 = getParam(var7, var24);
                           var11 = var8;
                           if(var8 == -1073741824) {
                              var11 = 1;
                           }

                           var12 = getParam(var7, var24 + 1);
                           var8 = var12;
                           if(var12 == -1073741824) {
                              if(var4 == 124) {
                                 var8 = 12;
                              } else {
                                 var8 = 126;
                              }
                           }

                           var21 = LispCharacterFormat.getInstance(var8, var11, false, false);
                           break;
                        default:
                           throw new ParseException("unrecognized format specifier ~" + var4, var27);
                        }

                        var7.setSize(var24);
                        var7.push(var21);
                        var8 = var27;
                        break;
                     }

                     var18 = true;
                  }

                  var4 = var1[var27];
                  ++var27;
               }
            } else {
               var6.append(var4);
               var8 = var27;
            }
         }
      }
   }

   public static Object[] asArray(Object var0) {
      if(var0 instanceof Object[]) {
         return (Object[])((Object[])var0);
      } else if(!(var0 instanceof Sequence)) {
         return null;
      } else {
         int var4 = ((Sequence)var0).size();
         Object[] var1 = new Object[var4];

         int var2;
         for(var2 = 0; var0 instanceof Pair; ++var2) {
            Pair var5 = (Pair)var0;
            var1[var2] = var5.getCar();
            var0 = var5.getCdr();
         }

         if(var2 < var4) {
            if(!(var0 instanceof Sequence)) {
               return null;
            }

            Sequence var6 = (Sequence)var0;

            for(int var3 = var2; var3 < var4; ++var3) {
               var1[var3] = var6.get(var2 + var3);
            }
         }

         return var1;
      }
   }

   static java.text.Format[] getFormats(Vector var0, int var1, int var2) {
      java.text.Format[] var3 = new java.text.Format[var2 - var1];

      for(int var4 = var1; var4 < var2; ++var4) {
         var3[var4 - var1] = (java.text.Format)var0.elementAt(var4);
      }

      return var3;
   }

   public static int getParam(Vector var0, int var1) {
      if(var1 < var0.size()) {
         Object var2 = var0.elementAt(var1);
         if(var2 == "<from list>") {
            return -1610612736;
         }

         if(var2 == "<from count>") {
            return -1342177280;
         }

         if(var2 != "<unspecified>") {
            return getParam(var2, -1073741824);
         }
      }

      return -1073741824;
   }

   static java.text.Format popFormats(Vector var0, int var1, int var2) {
      Object var3;
      if(var2 == var1 + 1) {
         var3 = (java.text.Format)var0.elementAt(var1);
      } else {
         var3 = new CompoundFormat(getFormats(var0, var1, var2));
      }

      var0.setSize(var1);
      return (java.text.Format)var3;
   }
}
