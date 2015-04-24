package gnu.kawa.slib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.Special;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.ApplyToArgs;
import gnu.kawa.functions.DivideOp;
import gnu.kawa.functions.IsEqual;
import gnu.kawa.functions.IsEqv;
import gnu.kawa.functions.MultiplyOp;
import gnu.kawa.functions.NumberCompare;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.slib.genwrite;
import gnu.lists.CharSeq;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.Complex;
import gnu.math.IntNum;
import gnu.math.RealNum;
import gnu.text.Char;
import kawa.lib.characters;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.lib.ports;
import kawa.lib.strings;
import kawa.lib.vectors;
import kawa.lib.rnrs.unicode;
import kawa.standard.Scheme;
import kawa.standard.append;

public class printf extends ModuleBody {

   public static final printf $instance;
   static final IntNum Lit0;
   static final IntNum Lit1;
   static final PairWithPosition Lit10;
   static final Char Lit11;
   static final Char Lit12;
   static final Char Lit13;
   static final IntNum Lit14;
   static final IntNum Lit15;
   static final IntNum Lit16;
   static final IntNum Lit17;
   static final Char Lit18;
   static final Char Lit19;
   static final PairWithPosition Lit2;
   static final Char Lit20;
   static final Char Lit21;
   static final Char Lit22;
   static final Char Lit23;
   static final Char Lit24;
   static final Char Lit25;
   static final Char Lit26;
   static final Char Lit27;
   static final Char Lit28;
   static final Char Lit29;
   static final Char Lit3;
   static final Char Lit30;
   static final Char Lit31;
   static final Char Lit32;
   static final PairWithPosition Lit33;
   static final SimpleSymbol Lit34 = (SimpleSymbol)(new SimpleSymbol("printf")).readResolve();
   static final Char Lit35 = Char.make(99);
   static final Char Lit36 = Char.make(67);
   static final Char Lit37 = Char.make(115);
   static final Char Lit38 = Char.make(83);
   static final Char Lit39 = Char.make(97);
   static final Char Lit4;
   static final Char Lit40 = Char.make(65);
   static final Char Lit41 = Char.make(68);
   static final Char Lit42 = Char.make(73);
   static final Char Lit43 = Char.make(117);
   static final Char Lit44 = Char.make(85);
   static final IntNum Lit45 = IntNum.make(10);
   static final Char Lit46 = Char.make(111);
   static final Char Lit47 = Char.make(79);
   static final IntNum Lit48 = IntNum.make(8);
   static final Char Lit49 = Char.make(120);
   static final Char Lit5;
   static final IntNum Lit50 = IntNum.make(16);
   static final Char Lit51 = Char.make(88);
   static final Char Lit52 = Char.make(98);
   static final Char Lit53 = Char.make(66);
   static final Char Lit54 = Char.make(69);
   static final Char Lit55 = Char.make(103);
   static final Char Lit56 = Char.make(71);
   static final Char Lit57 = Char.make(107);
   static final Char Lit58 = Char.make(75);
   static final IntNum Lit59 = IntNum.make(6);
   static final Char Lit6;
   static final IntNum Lit60 = IntNum.make(-10);
   static final IntNum Lit61 = IntNum.make(3);
   static final FVector Lit62 = FVector.make(new Object[]{"y", "z", "a", "f", "p", "n", "u", "m", "", "k", "M", "G", "T", "P", "E", "Z", "Y"});
   static final PairWithPosition Lit63 = PairWithPosition.make("i", LList.Empty, "printf.scm", 1634315);
   static final SimpleSymbol Lit64 = (SimpleSymbol)(new SimpleSymbol("format-real")).readResolve();
   static final Char Lit65 = Char.make(63);
   static final Char Lit66 = Char.make(42);
   static final SimpleSymbol Lit67 = (SimpleSymbol)(new SimpleSymbol("pad")).readResolve();
   static final SimpleSymbol Lit68 = (SimpleSymbol)(new SimpleSymbol("sprintf")).readResolve();
   static final SimpleSymbol Lit69 = (SimpleSymbol)(new SimpleSymbol("stdio:parse-float")).readResolve();
   static final IntNum Lit7;
   static final SimpleSymbol Lit70 = (SimpleSymbol)(new SimpleSymbol("stdio:round-string")).readResolve();
   static final SimpleSymbol Lit71 = (SimpleSymbol)(new SimpleSymbol("stdio:iprintf")).readResolve();
   static final SimpleSymbol Lit72 = (SimpleSymbol)(new SimpleSymbol("fprintf")).readResolve();
   static final Char Lit8;
   static final Char Lit9;
   public static final ModuleMethod fprintf;
   public static final ModuleMethod printf;
   public static final ModuleMethod sprintf;
   public static final boolean stdio$Clhex$Mnupper$Mncase$Qu = false;
   public static final ModuleMethod stdio$Cliprintf;
   public static final ModuleMethod stdio$Clparse$Mnfloat;
   public static final ModuleMethod stdio$Clround$Mnstring;


   static {
      Char var0 = Lit35;
      Char var1 = Lit37;
      Char var2 = Lit39;
      Char var3 = Char.make(100);
      Lit12 = var3;
      Char var4 = Char.make(105);
      Lit3 = var4;
      Char var5 = Lit43;
      Char var6 = Lit46;
      Char var7 = Lit49;
      Char var8 = Lit52;
      Char var9 = Char.make(102);
      Lit25 = var9;
      Char var10 = Char.make(101);
      Lit13 = var10;
      Lit33 = PairWithPosition.make(var0, PairWithPosition.make(var1, PairWithPosition.make(var2, PairWithPosition.make(var3, PairWithPosition.make(var4, PairWithPosition.make(var5, PairWithPosition.make(var6, PairWithPosition.make(var7, PairWithPosition.make(var8, PairWithPosition.make(var9, PairWithPosition.make(var10, PairWithPosition.make(Lit55, PairWithPosition.make(Lit57, LList.Empty, "printf.scm", 1781780), "printf.scm", 1781776), "printf.scm", 1781772), "printf.scm", 1781768), "printf.scm", 1777704), "printf.scm", 1777700), "printf.scm", 1777696), "printf.scm", 1777692), "printf.scm", 1777688), "printf.scm", 1777684), "printf.scm", 1777680), "printf.scm", 1777676), "printf.scm", 1777671);
      Lit32 = Char.make(104);
      Lit31 = Char.make(76);
      Lit30 = Char.make(108);
      Lit29 = Char.make(32);
      Lit28 = Char.make(37);
      Lit27 = Char.make(12);
      Lit26 = Char.make(70);
      Lit24 = Char.make(9);
      Lit23 = Char.make(84);
      Lit22 = Char.make(116);
      Lit21 = Char.make(10);
      Lit20 = Char.make(78);
      Lit19 = Char.make(110);
      Lit18 = Char.make(92);
      Lit17 = IntNum.make(-1);
      Lit16 = IntNum.make(9);
      Lit15 = IntNum.make(5);
      Lit14 = IntNum.make(2);
      Lit11 = Char.make(46);
      Lit10 = PairWithPosition.make(Lit13, PairWithPosition.make(Lit37, PairWithPosition.make(Lit25, PairWithPosition.make(Lit12, PairWithPosition.make(Lit30, PairWithPosition.make(Lit54, PairWithPosition.make(Lit38, PairWithPosition.make(Lit26, PairWithPosition.make(Lit41, PairWithPosition.make(Lit31, LList.Empty, "printf.scm", 266284), "printf.scm", 266280), "printf.scm", 266276), "printf.scm", 266272), "printf.scm", 266268), "printf.scm", 266264), "printf.scm", 266260), "printf.scm", 266256), "printf.scm", 266252), "printf.scm", 266247);
      Lit9 = Char.make(48);
      Lit8 = Char.make(35);
      Lit7 = IntNum.make(1);
      Lit6 = Char.make(43);
      Lit5 = Char.make(45);
      Lit4 = Char.make(64);
      Lit2 = PairWithPosition.make(Lit6, PairWithPosition.make(Lit5, LList.Empty, "printf.scm", 446503), "printf.scm", 446498);
      Lit1 = IntNum.make(0);
      Lit0 = IntNum.make(-15);
      $instance = new printf();
      printf var11 = $instance;
      stdio$Clparse$Mnfloat = new ModuleMethod(var11, 22, Lit69, 8194);
      stdio$Clround$Mnstring = new ModuleMethod(var11, 23, Lit70, 12291);
      stdio$Cliprintf = new ModuleMethod(var11, 24, Lit71, -4094);
      fprintf = new ModuleMethod(var11, 25, Lit72, -4094);
      printf = new ModuleMethod(var11, 26, Lit34, -4095);
      sprintf = new ModuleMethod(var11, 27, Lit68, -4094);
      $instance.run();
   }

   public printf() {
      ModuleInfo.register(this);
   }

   public static Object fprintf$V(Object var0, Object var1, Object[] var2) {
      printf.frame12 var3 = new printf.frame12();
      var3.port = var0;
      LList var4 = LList.makeList(var2, 0);
      var3.cnt = Lit1;
      Scheme.apply.apply4(stdio$Cliprintf, var3.lambda$Fn18, var1, var4);
      return var3.cnt;
   }

   public static Object printf$V(Object var0, Object[] var1) {
      LList var2 = LList.makeList(var1, 0);
      return Scheme.apply.apply4(fprintf, ports.current$Mnoutput$Mnport.apply0(), var0, var2);
   }

   public static Object sprintf$V(Object var0, Object var1, Object[] var2) {
      printf.frame13 var3 = new printf.frame13();
      var3.str = var0;
      LList var11 = LList.makeList(var2, 0);
      var3.cnt = Lit1;
      int var5;
      if(strings.isString(var3.str)) {
         var0 = var3.str;
      } else if(numbers.isNumber(var3.str)) {
         var0 = var3.str;

         try {
            var5 = ((Number)var0).intValue();
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "make-string", 1, var0);
         }

         var0 = strings.makeString(var5);
      } else if(var3.str == Boolean.FALSE) {
         var0 = strings.makeString(100);
      } else {
         var0 = misc.error$V(Lit68, new Object[]{"first argument not understood", var3.str});
      }

      var3.s = var0;
      var0 = var3.s;

      CharSequence var4;
      try {
         var4 = (CharSequence)var0;
      } catch (ClassCastException var8) {
         throw new WrongType(var8, "string-length", 1, var0);
      }

      var3.end = Integer.valueOf(strings.stringLength(var4));
      Scheme.apply.apply4(stdio$Cliprintf, var3.lambda$Fn19, var1, var11);
      if(strings.isString(var3.str)) {
         return var3.cnt;
      } else if(Scheme.isEqv.apply2(var3.end, var3.cnt) != Boolean.FALSE) {
         return var3.s;
      } else {
         var0 = var3.s;

         CharSequence var10;
         try {
            var10 = (CharSequence)var0;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "substring", 1, var0);
         }

         var0 = var3.cnt;

         try {
            var5 = ((Number)var0).intValue();
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "substring", 3, var0);
         }

         return strings.substring(var10, 0, var5);
      }
   }

   public static Object stdio$ClIprintf$V(Object var0, Object var1, Object[] var2) {
      printf.frame9 var3 = new printf.frame9();
      var3.out = var0;
      var3.format$Mnstring = var1;
      var3.args = LList.makeList(var2, 0);
      if(IsEqual.apply("", var3.format$Mnstring)) {
         return Values.empty;
      } else {
         IntNum var40 = Lit17;
         var1 = var3.format$Mnstring;

         CharSequence var42;
         try {
            var42 = (CharSequence)var1;
         } catch (ClassCastException var39) {
            throw new WrongType(var39, "string-length", 1, var1);
         }

         int var8 = strings.stringLength(var42);
         var1 = var3.format$Mnstring;

         try {
            var42 = (CharSequence)var1;
         } catch (ClassCastException var38) {
            throw new WrongType(var38, "string-ref", 1, var1);
         }

         var3.fc = Char.make(strings.stringRef(var42, 0));
         var3.fl = var8;
         var3.pos = var40;
         var0 = var3.args;

         while(true) {
            printf.frame10 var45 = new printf.frame10();
            var45.staticLink = var3;
            var45.args = var0;
            var3.pos = AddOp.$Pl.apply2(Lit7, var3.pos);
            CharSequence var41;
            if(Scheme.numGEq.apply2(var3.pos, Integer.valueOf(var3.fl)) != Boolean.FALSE) {
               var3.fc = Boolean.FALSE;
            } else {
               var0 = var3.format$Mnstring;

               try {
                  var41 = (CharSequence)var0;
               } catch (ClassCastException var13) {
                  throw new WrongType(var13, "string-ref", 1, var0);
               }

               var0 = var3.pos;

               try {
                  var8 = ((Number)var0).intValue();
               } catch (ClassCastException var12) {
                  throw new WrongType(var12, "string-ref", 2, var0);
               }

               var3.fc = Char.make(strings.stringRef(var41, var8));
            }

            boolean var9 = var3.lambda19isEndOfFormat();
            if(var9) {
               if(var9) {
                  var0 = Boolean.TRUE;
               } else {
                  var0 = Boolean.FALSE;
               }
               break;
            }

            if(Scheme.isEqv.apply2(Lit18, var3.fc) != Boolean.FALSE) {
               label664: {
                  label538: {
                     var3.lambda18mustAdvance();
                     var0 = var3.fc;
                     var1 = Scheme.isEqv.apply2(var0, Lit19);
                     if(var1 != Boolean.FALSE) {
                        if(var1 == Boolean.FALSE) {
                           break label538;
                        }
                     } else if(Scheme.isEqv.apply2(var0, Lit20) == Boolean.FALSE) {
                        break label538;
                     }

                     var0 = Scheme.applyToArgs.apply2(var3.out, Lit21);
                     break label664;
                  }

                  label531: {
                     var1 = Scheme.isEqv.apply2(var0, Lit22);
                     if(var1 != Boolean.FALSE) {
                        if(var1 == Boolean.FALSE) {
                           break label531;
                        }
                     } else if(Scheme.isEqv.apply2(var0, Lit23) == Boolean.FALSE) {
                        break label531;
                     }

                     var0 = Scheme.applyToArgs.apply2(var3.out, Lit24);
                     break label664;
                  }

                  label524: {
                     var1 = Scheme.isEqv.apply2(var0, Lit25);
                     if(var1 != Boolean.FALSE) {
                        if(var1 != Boolean.FALSE) {
                           break label524;
                        }
                     } else if(Scheme.isEqv.apply2(var0, Lit26) != Boolean.FALSE) {
                        break label524;
                     }

                     if(Scheme.isEqv.apply2(var0, Lit21) != Boolean.FALSE) {
                        var0 = Boolean.TRUE;
                     } else {
                        var0 = Scheme.applyToArgs.apply2(var3.out, var3.fc);
                     }
                     break label664;
                  }

                  var0 = Scheme.applyToArgs.apply2(var3.out, Lit27);
               }

               if(var0 == Boolean.FALSE) {
                  return var0;
               }

               var0 = var45.args;
            } else if(Scheme.isEqv.apply2(Lit28, var3.fc) != Boolean.FALSE) {
               var3.lambda18mustAdvance();
               Boolean var43 = Boolean.FALSE;
               Boolean var44 = Boolean.FALSE;
               Boolean var4 = Boolean.FALSE;
               Boolean var5 = Boolean.FALSE;
               Boolean var6 = Boolean.FALSE;
               IntNum var7 = Lit1;
               var45.precision = Lit17;
               var45.width = var7;
               var45.leading$Mn0s = var6;
               var45.alternate$Mnform = var5;
               var45.blank = var4;
               var45.signed = var44;
               var45.left$Mnadjust = var43;
               var45.pad = var45.pad;

               while(true) {
                  var0 = var3.fc;
                  if(Scheme.isEqv.apply2(var0, Lit5) != Boolean.FALSE) {
                     var45.left$Mnadjust = Boolean.TRUE;
                  } else if(Scheme.isEqv.apply2(var0, Lit6) != Boolean.FALSE) {
                     var45.signed = Boolean.TRUE;
                  } else if(Scheme.isEqv.apply2(var0, Lit29) != Boolean.FALSE) {
                     var45.blank = Boolean.TRUE;
                  } else if(Scheme.isEqv.apply2(var0, Lit8) != Boolean.FALSE) {
                     var45.alternate$Mnform = Boolean.TRUE;
                  } else {
                     if(Scheme.isEqv.apply2(var0, Lit9) == Boolean.FALSE) {
                        if(var45.left$Mnadjust != Boolean.FALSE) {
                           var45.leading$Mn0s = Boolean.FALSE;
                        }

                        if(var45.signed != Boolean.FALSE) {
                           var45.blank = Boolean.FALSE;
                        }

                        var45.width = var45.lambda22readFormatNumber();
                        var0 = var45.width;

                        RealNum var46;
                        try {
                           var46 = LangObjType.coerceRealNum(var0);
                        } catch (ClassCastException var11) {
                           throw new WrongType(var11, "negative?", 1, var0);
                        }

                        if(numbers.isNegative(var46)) {
                           var45.left$Mnadjust = Boolean.TRUE;
                           var45.width = AddOp.$Mn.apply1(var45.width);
                        }

                        if(Scheme.isEqv.apply2(Lit11, var3.fc) != Boolean.FALSE) {
                           var3.lambda18mustAdvance();
                           var45.precision = var45.lambda22readFormatNumber();
                        }

                        label458: {
                           var0 = var3.fc;
                           var1 = Scheme.isEqv.apply2(var0, Lit30);
                           if(var1 != Boolean.FALSE) {
                              if(var1 == Boolean.FALSE) {
                                 break label458;
                              }
                           } else {
                              var1 = Scheme.isEqv.apply2(var0, Lit31);
                              if(var1 != Boolean.FALSE) {
                                 if(var1 == Boolean.FALSE) {
                                    break label458;
                                 }
                              } else if(Scheme.isEqv.apply2(var0, Lit32) == Boolean.FALSE) {
                                 break label458;
                              }
                           }

                           var3.lambda18mustAdvance();
                        }

                        if(lists.isNull(var45.args)) {
                           var0 = var3.fc;

                           Char var47;
                           try {
                              var47 = (Char)var0;
                           } catch (ClassCastException var10) {
                              throw new WrongType(var10, "char-downcase", 1, var0);
                           }

                           if(lists.memv(unicode.charDowncase(var47), Lit33) != Boolean.FALSE) {
                              misc.error$V(Lit34, new Object[]{"wrong number of arguments", Integer.valueOf(lists.length(var3.args)), var3.format$Mnstring});
                           }
                        }

                        Object var51;
                        ApplyToArgs var71;
                        label667: {
                           var0 = var3.fc;
                           var1 = Scheme.isEqv.apply2(var0, Lit35);
                           if(var1 != Boolean.FALSE) {
                              if(var1 != Boolean.FALSE) {
                                 break label667;
                              }
                           } else if(Scheme.isEqv.apply2(var0, Lit36) != Boolean.FALSE) {
                              break label667;
                           }

                           CharSequence var53;
                           label668: {
                              var1 = Scheme.isEqv.apply2(var0, Lit37);
                              if(var1 != Boolean.FALSE) {
                                 if(var1 == Boolean.FALSE) {
                                    break label668;
                                 }
                              } else if(Scheme.isEqv.apply2(var0, Lit38) == Boolean.FALSE) {
                                 break label668;
                              }

                              if(misc.isSymbol(lists.car.apply1(var45.args))) {
                                 var0 = lists.car.apply1(var45.args);

                                 Symbol var52;
                                 try {
                                    var52 = (Symbol)var0;
                                 } catch (ClassCastException var37) {
                                    throw new WrongType(var37, "symbol->string", 1, var0);
                                 }

                                 var0 = misc.symbol$To$String(var52);
                              } else if(lists.car.apply1(var45.args) == Boolean.FALSE) {
                                 var0 = "(NULL)";
                              } else {
                                 var0 = lists.car.apply1(var45.args);
                              }

                              var1 = var45.precision;

                              RealNum var48;
                              try {
                                 var48 = LangObjType.coerceRealNum(var1);
                              } catch (ClassCastException var36) {
                                 throw new WrongType(var36, "negative?", 1, var1);
                              }

                              label670: {
                                 var9 = numbers.isNegative(var48);
                                 if(var9) {
                                    var1 = var0;
                                    if(var9) {
                                       break label670;
                                    }
                                 } else {
                                    NumberCompare var49 = Scheme.numGEq;
                                    Object var50 = var45.precision;

                                    CharSequence var56;
                                    try {
                                       var56 = (CharSequence)var0;
                                    } catch (ClassCastException var35) {
                                       throw new WrongType(var35, "string-length", 1, var0);
                                    }

                                    var1 = var0;
                                    if(var49.apply2(var50, Integer.valueOf(strings.stringLength(var56))) != Boolean.FALSE) {
                                       break label670;
                                    }
                                 }

                                 try {
                                    var41 = (CharSequence)var0;
                                 } catch (ClassCastException var34) {
                                    throw new WrongType(var34, "substring", 1, var0);
                                 }

                                 var0 = var45.precision;

                                 try {
                                    var8 = ((Number)var0).intValue();
                                 } catch (ClassCastException var33) {
                                    throw new WrongType(var33, "substring", 3, var0);
                                 }

                                 var1 = strings.substring(var41, 0, var8);
                              }

                              NumberCompare var64 = Scheme.numLEq;
                              var51 = var45.width;

                              try {
                                 var53 = (CharSequence)var1;
                              } catch (ClassCastException var32) {
                                 throw new WrongType(var32, "string-length", 1, var1);
                              }

                              if(var64.apply2(var51, Integer.valueOf(strings.stringLength(var53))) == Boolean.FALSE) {
                                 AddOp var62;
                                 if(var45.left$Mnadjust != Boolean.FALSE) {
                                    var62 = AddOp.$Mn;
                                    var51 = var45.width;

                                    try {
                                       var53 = (CharSequence)var1;
                                    } catch (ClassCastException var31) {
                                       throw new WrongType(var31, "string-length", 1, var1);
                                    }

                                    var0 = var62.apply2(var51, Integer.valueOf(strings.stringLength(var53)));

                                    try {
                                       var8 = ((Number)var0).intValue();
                                    } catch (ClassCastException var30) {
                                       throw new WrongType(var30, "make-string", 1, var0);
                                    }

                                    var1 = LList.list2(var1, strings.makeString(var8, Lit29));
                                 } else {
                                    var62 = AddOp.$Mn;
                                    var51 = var45.width;

                                    try {
                                       var53 = (CharSequence)var1;
                                    } catch (ClassCastException var29) {
                                       throw new WrongType(var29, "string-length", 1, var1);
                                    }

                                    var0 = var62.apply2(var51, Integer.valueOf(strings.stringLength(var53)));

                                    try {
                                       var8 = ((Number)var0).intValue();
                                    } catch (ClassCastException var28) {
                                       throw new WrongType(var28, "make-string", 1, var0);
                                    }

                                    Char var67;
                                    if(var45.leading$Mn0s != Boolean.FALSE) {
                                       var67 = Lit9;
                                    } else {
                                       var67 = Lit29;
                                    }

                                    var1 = LList.list2(strings.makeString(var8, var67), var1);
                                 }
                              }

                              var1 = var3.lambda21out$St(var1);
                              var0 = var1;
                              if(var1 == Boolean.FALSE) {
                                 return var0;
                              }

                              var0 = lists.cdr.apply1(var45.args);
                              break;
                           }

                           label674: {
                              var1 = Scheme.isEqv.apply2(var0, Lit39);
                              if(var1 != Boolean.FALSE) {
                                 if(var1 != Boolean.FALSE) {
                                    break label674;
                                 }
                              } else if(Scheme.isEqv.apply2(var0, Lit40) != Boolean.FALSE) {
                                 break label674;
                              }

                              label675: {
                                 var1 = Scheme.isEqv.apply2(var0, Lit12);
                                 if(var1 != Boolean.FALSE) {
                                    if(var1 != Boolean.FALSE) {
                                       break label675;
                                    }
                                 } else {
                                    var1 = Scheme.isEqv.apply2(var0, Lit41);
                                    if(var1 != Boolean.FALSE) {
                                       if(var1 != Boolean.FALSE) {
                                          break label675;
                                       }
                                    } else {
                                       var1 = Scheme.isEqv.apply2(var0, Lit3);
                                       if(var1 != Boolean.FALSE) {
                                          if(var1 != Boolean.FALSE) {
                                             break label675;
                                          }
                                       } else {
                                          var1 = Scheme.isEqv.apply2(var0, Lit42);
                                          if(var1 != Boolean.FALSE) {
                                             if(var1 != Boolean.FALSE) {
                                                break label675;
                                             }
                                          } else {
                                             var1 = Scheme.isEqv.apply2(var0, Lit43);
                                             if(var1 != Boolean.FALSE) {
                                                if(var1 != Boolean.FALSE) {
                                                   break label675;
                                                }
                                             } else if(Scheme.isEqv.apply2(var0, Lit44) != Boolean.FALSE) {
                                                break label675;
                                             }
                                          }
                                       }
                                    }
                                 }

                                 label676: {
                                    var1 = Scheme.isEqv.apply2(var0, Lit46);
                                    if(var1 != Boolean.FALSE) {
                                       if(var1 != Boolean.FALSE) {
                                          break label676;
                                       }
                                    } else if(Scheme.isEqv.apply2(var0, Lit47) != Boolean.FALSE) {
                                       break label676;
                                    }

                                    IntNum var63;
                                    if(Scheme.isEqv.apply2(var0, Lit49) != Boolean.FALSE) {
                                       var1 = lists.car.apply1(var45.args);
                                       var63 = Lit50;
                                       if(stdio$Clhex$Mnupper$Mncase$Qu) {
                                          var0 = unicode.string$Mndowncase;
                                       } else {
                                          var0 = Boolean.FALSE;
                                       }

                                       var1 = var3.lambda21out$St(var45.lambda24integerConvert(var1, var63, var0));
                                       var0 = var1;
                                       if(var1 == Boolean.FALSE) {
                                          return var0;
                                       }

                                       var0 = lists.cdr.apply1(var45.args);
                                       break;
                                    }

                                    if(Scheme.isEqv.apply2(var0, Lit51) != Boolean.FALSE) {
                                       var1 = lists.car.apply1(var45.args);
                                       var63 = Lit50;
                                       if(stdio$Clhex$Mnupper$Mncase$Qu) {
                                          var0 = Boolean.FALSE;
                                       } else {
                                          var0 = unicode.string$Mnupcase;
                                       }

                                       var1 = var3.lambda21out$St(var45.lambda24integerConvert(var1, var63, var0));
                                       var0 = var1;
                                       if(var1 == Boolean.FALSE) {
                                          return var0;
                                       }

                                       var0 = lists.cdr.apply1(var45.args);
                                       break;
                                    }

                                    label679: {
                                       var1 = Scheme.isEqv.apply2(var0, Lit52);
                                       if(var1 != Boolean.FALSE) {
                                          if(var1 != Boolean.FALSE) {
                                             break label679;
                                          }
                                       } else if(Scheme.isEqv.apply2(var0, Lit53) != Boolean.FALSE) {
                                          break label679;
                                       }

                                       if(Scheme.isEqv.apply2(var0, Lit28) != Boolean.FALSE) {
                                          var1 = Scheme.applyToArgs.apply2(var3.out, Lit28);
                                          var0 = var1;
                                          if(var1 == Boolean.FALSE) {
                                             return var0;
                                          }

                                          var0 = var45.args;
                                          break;
                                       }

                                       label680: {
                                          var1 = Scheme.isEqv.apply2(var0, Lit25);
                                          if(var1 != Boolean.FALSE) {
                                             if(var1 == Boolean.FALSE) {
                                                break label680;
                                             }
                                          } else {
                                             var1 = Scheme.isEqv.apply2(var0, Lit26);
                                             if(var1 != Boolean.FALSE) {
                                                if(var1 == Boolean.FALSE) {
                                                   break label680;
                                                }
                                             } else {
                                                var1 = Scheme.isEqv.apply2(var0, Lit13);
                                                if(var1 != Boolean.FALSE) {
                                                   if(var1 == Boolean.FALSE) {
                                                      break label680;
                                                   }
                                                } else {
                                                   var1 = Scheme.isEqv.apply2(var0, Lit54);
                                                   if(var1 != Boolean.FALSE) {
                                                      if(var1 == Boolean.FALSE) {
                                                         break label680;
                                                      }
                                                   } else {
                                                      var1 = Scheme.isEqv.apply2(var0, Lit55);
                                                      if(var1 != Boolean.FALSE) {
                                                         if(var1 == Boolean.FALSE) {
                                                            break label680;
                                                         }
                                                      } else {
                                                         var1 = Scheme.isEqv.apply2(var0, Lit56);
                                                         if(var1 != Boolean.FALSE) {
                                                            if(var1 == Boolean.FALSE) {
                                                               break label680;
                                                            }
                                                         } else {
                                                            var1 = Scheme.isEqv.apply2(var0, Lit57);
                                                            if(var1 != Boolean.FALSE) {
                                                               if(var1 == Boolean.FALSE) {
                                                                  break label680;
                                                               }
                                                            } else if(Scheme.isEqv.apply2(var0, Lit58) == Boolean.FALSE) {
                                                               break label680;
                                                            }
                                                         }
                                                      }
                                                   }
                                                }
                                             }
                                          }

                                          var0 = lists.car.apply1(var45.args);
                                          var51 = var3.fc;
                                          printf.frame11 var69 = new printf.frame11();
                                          var69.staticLink = var45;
                                          var69.fc = var51;
                                          var51 = var45.precision;

                                          RealNum var55;
                                          try {
                                             var55 = LangObjType.coerceRealNum(var51);
                                          } catch (ClassCastException var18) {
                                             throw new WrongType(var18, "negative?", 1, var51);
                                          }

                                          if(numbers.isNegative(var55)) {
                                             var45.precision = Lit59;
                                          } else {
                                             label681: {
                                                var51 = var45.precision;

                                                Number var61;
                                                try {
                                                   var61 = (Number)var51;
                                                } catch (ClassCastException var17) {
                                                   throw new WrongType(var17, "zero?", 1, var51);
                                                }

                                                var9 = numbers.isZero(var61);
                                                if(var9) {
                                                   var51 = var69.fc;

                                                   Char var60;
                                                   try {
                                                      var60 = (Char)var51;
                                                   } catch (ClassCastException var16) {
                                                      throw new WrongType(var16, "char-ci=?", 1, var51);
                                                   }

                                                   if(!unicode.isCharCi$Eq(var60, Lit55)) {
                                                      break label681;
                                                   }
                                                } else if(!var9) {
                                                   break label681;
                                                }

                                                var45.precision = Lit7;
                                             }
                                          }

                                          if(numbers.isNumber(var0)) {
                                             Number var59;
                                             try {
                                                var59 = (Number)var0;
                                             } catch (ClassCastException var15) {
                                                throw new WrongType(var15, "exact->inexact", 1, var0);
                                             }

                                             var0 = numbers.number$To$String(numbers.exact$To$Inexact(var59));
                                          } else if(!strings.isString(var0)) {
                                             if(misc.isSymbol(var0)) {
                                                Symbol var65;
                                                try {
                                                   var65 = (Symbol)var0;
                                                } catch (ClassCastException var14) {
                                                   throw new WrongType(var14, "symbol->string", 1, var0);
                                                }

                                                var0 = misc.symbol$To$String(var65);
                                             } else {
                                                var0 = "???";
                                             }
                                          }

                                          var69.format$Mnreal = var69.format$Mnreal;
                                          var0 = stdio$ClParseFloat(var0, var69.lambda$Fn17);
                                          if(var0 == Boolean.FALSE) {
                                             var0 = var45.lambda23pad$V("???", new Object[0]);
                                          }

                                          var1 = var3.lambda21out$St(var0);
                                          var0 = var1;
                                          if(var1 == Boolean.FALSE) {
                                             return var0;
                                          }

                                          var0 = lists.cdr.apply1(var45.args);
                                          break;
                                       }

                                       if(var3.lambda19isEndOfFormat()) {
                                          return var3.lambda20incomplete();
                                       }

                                       var1 = Scheme.applyToArgs.apply2(var3.out, Lit28);
                                       var0 = var1;
                                       if(var1 == Boolean.FALSE) {
                                          return var0;
                                       }

                                       var1 = Scheme.applyToArgs.apply2(var3.out, var3.fc);
                                       var0 = var1;
                                       if(var1 == Boolean.FALSE) {
                                          return var0;
                                       }

                                       var1 = Scheme.applyToArgs.apply2(var3.out, Lit65);
                                       var0 = var1;
                                       if(var1 == Boolean.FALSE) {
                                          return var0;
                                       }

                                       var0 = var45.args;
                                       break;
                                    }

                                    var1 = var3.lambda21out$St(var45.lambda24integerConvert(lists.car.apply1(var45.args), Lit14, Boolean.FALSE));
                                    var0 = var1;
                                    if(var1 == Boolean.FALSE) {
                                       return var0;
                                    }

                                    var0 = lists.cdr.apply1(var45.args);
                                    break;
                                 }

                                 var1 = var3.lambda21out$St(var45.lambda24integerConvert(lists.car.apply1(var45.args), Lit48, Boolean.FALSE));
                                 var0 = var1;
                                 if(var1 == Boolean.FALSE) {
                                    return var0;
                                 }

                                 var0 = lists.cdr.apply1(var45.args);
                                 break;
                              }

                              var1 = var3.lambda21out$St(var45.lambda24integerConvert(lists.car.apply1(var45.args), Lit45, Boolean.FALSE));
                              var0 = var1;
                              if(var1 == Boolean.FALSE) {
                                 return var0;
                              }

                              var0 = lists.cdr.apply1(var45.args);
                              break;
                           }

                           var45.pr = var45.precision;
                           var45.os = "";
                           var51 = lists.car.apply1(var45.args);
                           if(var45.alternate$Mnform != Boolean.FALSE) {
                              var44 = Boolean.FALSE;
                           } else {
                              var44 = Boolean.TRUE;
                           }

                           ModuleMethod var72;
                           label621: {
                              label620: {
                                 var5 = Boolean.FALSE;
                                 var0 = var45.left$Mnadjust;
                                 RealNum var54;
                                 if(var0 != Boolean.FALSE) {
                                    var0 = var45.pr;

                                    try {
                                       var54 = LangObjType.coerceRealNum(var0);
                                    } catch (ClassCastException var27) {
                                       throw new WrongType(var27, "negative?", 1, var0);
                                    }

                                    if(numbers.isNegative(var54)) {
                                       break label620;
                                    }
                                 } else if(var0 != Boolean.FALSE) {
                                    break label620;
                                 }

                                 if(var45.left$Mnadjust != Boolean.FALSE) {
                                    var72 = var45.lambda$Fn14;
                                 } else {
                                    var0 = var45.pr;

                                    try {
                                       var54 = LangObjType.coerceRealNum(var0);
                                    } catch (ClassCastException var26) {
                                       throw new WrongType(var26, "negative?", 1, var0);
                                    }

                                    if(numbers.isNegative(var54)) {
                                       var45.pr = var45.width;
                                       var72 = var45.lambda$Fn15;
                                    } else {
                                       var72 = var45.lambda$Fn16;
                                    }
                                 }
                                 break label621;
                              }

                              var45.pr = Lit1;
                              var72 = var45.lambda$Fn13;
                           }

                           label612: {
                              label611: {
                                 genwrite.genericWrite(var51, var44, var5, var72);
                                 var0 = var45.left$Mnadjust;
                                 if(var0 != Boolean.FALSE) {
                                    var0 = var45.precision;

                                    try {
                                       var46 = LangObjType.coerceRealNum(var0);
                                    } catch (ClassCastException var25) {
                                       throw new WrongType(var25, "negative?", 1, var0);
                                    }

                                    if(!numbers.isNegative(var46)) {
                                       break label611;
                                    }
                                 } else if(var0 == Boolean.FALSE) {
                                    break label611;
                                 }

                                 if(Scheme.numGrt.apply2(var45.width, var45.pr) != Boolean.FALSE) {
                                    var71 = Scheme.applyToArgs;
                                    var51 = var3.out;
                                    var0 = AddOp.$Mn.apply2(var45.width, var45.pr);

                                    try {
                                       var8 = ((Number)var0).intValue();
                                    } catch (ClassCastException var24) {
                                       throw new WrongType(var24, "make-string", 1, var0);
                                    }

                                    var71.apply2(var51, strings.makeString(var8, Lit29));
                                 }
                                 break label612;
                              }

                              if(var45.left$Mnadjust != Boolean.FALSE) {
                                 if(Scheme.numGrt.apply2(var45.width, AddOp.$Mn.apply2(var45.precision, var45.pr)) != Boolean.FALSE) {
                                    var71 = Scheme.applyToArgs;
                                    var51 = var3.out;
                                    var0 = AddOp.$Mn.apply2(var45.width, AddOp.$Mn.apply2(var45.precision, var45.pr));

                                    try {
                                       var8 = ((Number)var0).intValue();
                                    } catch (ClassCastException var23) {
                                       throw new WrongType(var23, "make-string", 1, var0);
                                    }

                                    var71.apply2(var51, strings.makeString(var8, Lit29));
                                 }
                              } else {
                                 var0 = var45.os;

                                 try {
                                    var44 = Boolean.FALSE;
                                 } catch (ClassCastException var22) {
                                    throw new WrongType(var22, "x", -2, var0);
                                 }

                                 byte var68;
                                 if(var0 != var44) {
                                    var68 = 1;
                                 } else {
                                    var68 = 0;
                                 }

                                 if((var68 + 1 & 1) == 0) {
                                    NumberCompare var70 = Scheme.numLEq;
                                    var51 = var45.width;
                                    var0 = var45.os;

                                    try {
                                       var53 = (CharSequence)var0;
                                    } catch (ClassCastException var21) {
                                       throw new WrongType(var21, "string-length", 1, var0);
                                    }

                                    if(var70.apply2(var51, Integer.valueOf(strings.stringLength(var53))) != Boolean.FALSE) {
                                       Scheme.applyToArgs.apply2(var3.out, var45.os);
                                    } else {
                                       ApplyToArgs var73 = Scheme.applyToArgs;
                                       var1 = var3.out;
                                       AddOp var66 = AddOp.$Mn;
                                       Object var57 = var45.width;
                                       var51 = var45.os;

                                       CharSequence var58;
                                       try {
                                          var58 = (CharSequence)var51;
                                       } catch (ClassCastException var20) {
                                          throw new WrongType(var20, "string-length", 1, var51);
                                       }

                                       var51 = var66.apply2(var57, Integer.valueOf(strings.stringLength(var58)));

                                       try {
                                          var8 = ((Number)var51).intValue();
                                       } catch (ClassCastException var19) {
                                          throw new WrongType(var19, "make-string", 1, var51);
                                       }

                                       if(var73.apply2(var1, strings.makeString(var8, Lit29)) != Boolean.FALSE) {
                                          Scheme.applyToArgs.apply2(var3.out, var45.os);
                                       }
                                    }
                                 }
                              }
                           }

                           var0 = lists.cdr.apply1(var45.args);
                           break;
                        }

                        var71 = Scheme.applyToArgs;
                        var51 = var3.out;
                        var0 = lists.car.apply1(var45.args);
                        Object[] var74;
                        if(var0 instanceof Object[]) {
                           var74 = (Object[])var0;
                        } else {
                           var74 = new Object[]{var0};
                        }

                        var1 = var71.apply2(var51, strings.$make$string$(var74));
                        var0 = var1;
                        if(var1 == Boolean.FALSE) {
                           return var0;
                        }

                        var0 = lists.cdr.apply1(var45.args);
                        break;
                     }

                     var45.leading$Mn0s = Boolean.TRUE;
                  }

                  var3.lambda18mustAdvance();
               }
            } else {
               var1 = Scheme.applyToArgs.apply2(var3.out, var3.fc);
               var0 = var1;
               if(var1 == Boolean.FALSE) {
                  break;
               }

               var0 = var45.args;
            }
         }

         return var0;
      }
   }

   public static Object stdio$ClParseFloat(Object var0, Object var1) {
      printf.frame var2 = new printf.frame();
      var2.str = var0;
      var2.proc = var1;
      var0 = var2.str;

      CharSequence var4;
      try {
         var4 = (CharSequence)var0;
      } catch (ClassCastException var3) {
         throw new WrongType(var3, "string-length", 1, var0);
      }

      var2.n = strings.stringLength(var4);
      return var2.lambda4real(Lit1, var2.lambda$Fn1);
   }

   public static Object stdio$ClRoundString(CharSequence var0, Object var1, Object var2) {
      printf.frame8 var4 = new printf.frame8();
      var4.str = var0;
      int var7 = strings.stringLength(var4.str) - 1;
      boolean var9;
      Object var27;
      CharSequence var29;
      if(Scheme.numLss.apply2(var1, Lit1) != Boolean.FALSE) {
         var27 = "";
      } else if(Scheme.numEqu.apply2(Integer.valueOf(var7), var1) != Boolean.FALSE) {
         var27 = var4.str;
      } else {
         int var8;
         if(Scheme.numLss.apply2(Integer.valueOf(var7), var1) != Boolean.FALSE) {
            IntNum var3 = Lit1;
            AddOp var5 = AddOp.$Mn;
            var27 = var1;
            if(var2 != Boolean.FALSE) {
               var27 = var2;
            }

            var27 = numbers.max(new Object[]{var3, var5.apply2(var27, Integer.valueOf(var7))});

            Number var28;
            try {
               var28 = (Number)var27;
            } catch (ClassCastException var26) {
               throw new WrongType(var26, "zero?", 1, var27);
            }

            if(numbers.isZero(var28)) {
               var27 = var4.str;
            } else {
               var29 = var4.str;

               try {
                  var8 = ((Number)var27).intValue();
               } catch (ClassCastException var25) {
                  throw new WrongType(var25, "make-string", 1, var27);
               }

               Char var32;
               if(unicode.isCharNumeric(Char.make(strings.stringRef(var4.str, var7)))) {
                  var32 = Lit9;
               } else {
                  var32 = Lit8;
               }

               var27 = strings.stringAppend(new Object[]{var29, strings.makeString(var8, var32)});
            }
         } else {
            label189: {
               CharSequence var33 = var4.str;
               var27 = AddOp.$Pl.apply2(var1, Lit7);

               try {
                  var8 = ((Number)var27).intValue();
               } catch (ClassCastException var24) {
                  throw new WrongType(var24, "substring", 3, var27);
               }

               var33 = strings.substring(var33, 0, var8);
               Object var38 = var4.lambda17dig(AddOp.$Pl.apply2(Lit7, var1));
               var27 = Scheme.numGrt.apply2(var38, Lit15);

               try {
                  var9 = ((Boolean)var27).booleanValue();
               } catch (ClassCastException var23) {
                  throw new WrongType(var23, "x", -2, var27);
               }

               if(var9) {
                  var27 = var33;
                  if(!var9) {
                     break label189;
                  }
               } else {
                  var27 = Scheme.numEqu.apply2(var38, Lit15);

                  try {
                     var9 = ((Boolean)var27).booleanValue();
                  } catch (ClassCastException var22) {
                     throw new WrongType(var22, "x", -2, var27);
                  }

                  if(var9) {
                     var27 = AddOp.$Pl.apply2(Lit14, var1);

                     while(true) {
                        if(Scheme.numGrt.apply2(var27, Integer.valueOf(var7)) != Boolean.FALSE) {
                           var27 = var33;
                           if((((Number)var4.lambda17dig(var1)).intValue() & 1) == 0) {
                              break label189;
                           }
                           break;
                        }

                        var38 = var4.lambda17dig(var27);

                        Number var6;
                        try {
                           var6 = (Number)var38;
                        } catch (ClassCastException var21) {
                           throw new WrongType(var21, "zero?", 1, var38);
                        }

                        if(!numbers.isZero(var6)) {
                           break;
                        }

                        var27 = AddOp.$Pl.apply2(var27, Lit7);
                     }
                  } else {
                     var27 = var33;
                     if(!var9) {
                        break label189;
                     }
                  }
               }

               var27 = var1;

               while(true) {
                  var38 = var4.lambda17dig(var27);
                  CharSeq var30;
                  if(Scheme.numLss.apply2(var38, Lit16) != Boolean.FALSE) {
                     try {
                        var30 = (CharSeq)var33;
                     } catch (ClassCastException var18) {
                        throw new WrongType(var18, "string-set!", 1, var33);
                     }

                     try {
                        var7 = ((Number)var27).intValue();
                     } catch (ClassCastException var17) {
                        throw new WrongType(var17, "string-set!", 2, var27);
                     }

                     var27 = AddOp.$Pl.apply2(var38, Lit7);

                     Number var35;
                     try {
                        var35 = (Number)var27;
                     } catch (ClassCastException var16) {
                        throw new WrongType(var16, "number->string", 1, var27);
                     }

                     strings.stringSet$Ex(var30, var7, strings.stringRef(numbers.number$To$String(var35), 0));
                     var27 = var33;
                     break;
                  }

                  try {
                     var30 = (CharSeq)var33;
                  } catch (ClassCastException var20) {
                     throw new WrongType(var20, "string-set!", 1, var33);
                  }

                  try {
                     var7 = ((Number)var27).intValue();
                  } catch (ClassCastException var19) {
                     throw new WrongType(var19, "string-set!", 2, var27);
                  }

                  strings.stringSet$Ex(var30, var7, '0');
                  var27 = AddOp.$Mn.apply2(var27, Lit7);
               }
            }
         }
      }

      var1 = var27;
      if(var2 != Boolean.FALSE) {
         try {
            var29 = (CharSequence)var27;
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "string-length", 1, var27);
         }

         var1 = Integer.valueOf(strings.stringLength(var29) - 1);

         while(true) {
            Object var34 = Scheme.numLEq.apply2(var1, var2);

            try {
               var9 = ((Boolean)var34).booleanValue();
            } catch (ClassCastException var12) {
               throw new WrongType(var12, "x", -2, var34);
            }

            if(var9) {
               if(var9) {
                  break;
               }
            } else {
               Char var36 = Lit9;

               CharSequence var37;
               try {
                  var37 = (CharSequence)var27;
               } catch (ClassCastException var14) {
                  throw new WrongType(var14, "string-ref", 1, var27);
               }

               try {
                  var7 = ((Number)var1).intValue();
               } catch (ClassCastException var13) {
                  throw new WrongType(var13, "string-ref", 2, var1);
               }

               if(!characters.isChar$Eq(var36, Char.make(strings.stringRef(var37, var7)))) {
                  break;
               }
            }

            var1 = AddOp.$Mn.apply2(var1, Lit7);
         }

         CharSequence var31;
         try {
            var31 = (CharSequence)var27;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "substring", 1, var27);
         }

         var27 = AddOp.$Pl.apply2(var1, Lit7);

         try {
            var7 = ((Number)var27).intValue();
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "substring", 3, var27);
         }

         var1 = strings.substring(var31, 0, var7);
      }

      return var1;
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      return var1.selector == 22?stdio$ClParseFloat(var2, var3):super.apply2(var1, var2, var3);
   }

   public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
      if(var1.selector == 23) {
         CharSequence var6;
         try {
            var6 = (CharSequence)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "stdio:round-string", 1, var2);
         }

         return stdio$ClRoundString(var6, var3, var4);
      } else {
         return super.apply3(var1, var2, var3, var4);
      }
   }

   public Object applyN(ModuleMethod var1, Object[] var2) {
      Object var3;
      Object[] var4;
      int var5;
      Object var6;
      switch(var1.selector) {
      case 24:
         var6 = var2[0];
         var3 = var2[1];
         var5 = var2.length - 2;
         var4 = new Object[var5];

         while(true) {
            --var5;
            if(var5 < 0) {
               return stdio$ClIprintf$V(var6, var3, var4);
            }

            var4[var5] = var2[var5 + 2];
         }
      case 25:
         var6 = var2[0];
         var3 = var2[1];
         var5 = var2.length - 2;
         var4 = new Object[var5];

         while(true) {
            --var5;
            if(var5 < 0) {
               return fprintf$V(var6, var3, var4);
            }

            var4[var5] = var2[var5 + 2];
         }
      case 26:
         var6 = var2[0];
         var5 = var2.length - 1;
         Object[] var7 = new Object[var5];

         while(true) {
            --var5;
            if(var5 < 0) {
               return printf$V(var6, var7);
            }

            var7[var5] = var2[var5 + 1];
         }
      case 27:
         var6 = var2[0];
         var3 = var2[1];
         var5 = var2.length - 2;
         var4 = new Object[var5];

         while(true) {
            --var5;
            if(var5 < 0) {
               return sprintf$V(var6, var3, var4);
            }

            var4[var5] = var2[var5 + 2];
         }
      default:
         return super.applyN(var1, var2);
      }
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      if(var1.selector == 22) {
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      } else {
         return super.match2(var1, var2, var3, var4);
      }
   }

   public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
      if(var1.selector == 23) {
         if(var2 instanceof CharSequence) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return -786431;
         }
      } else {
         return super.match3(var1, var2, var3, var4, var5);
      }
   }

   public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
      switch(var1.selector) {
      case 24:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 25:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 26:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 27:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      default:
         return super.matchN(var1, var2, var3);
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
      stdio$Clhex$Mnupper$Mncase$Qu = strings.isString$Eq("-F", numbers.number$To$String(Lit0, 16));
   }

   public class frame extends ModuleBody {

      final ModuleMethod lambda$Fn1;
      int n;
      Object proc;
      Object str;


      public frame() {
         ModuleMethod var1 = new ModuleMethod(this, 12, (Object)null, 16388);
         var1.setProperty("source-location", "printf.scm:106");
         this.lambda$Fn1 = var1;
      }

      public static Boolean lambda1parseError() {
         return Boolean.FALSE;
      }

      public Object apply4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5) {
         return var1.selector == 12?this.lambda5(var2, var3, var4, var5):super.apply4(var1, var2, var3, var4, var5);
      }

      public Object lambda2sign(Object var1, Object var2) {
         if(Scheme.numLss.apply2(var1, Integer.valueOf(this.n)) != Boolean.FALSE) {
            Object var3 = this.str;

            CharSequence var4;
            try {
               var4 = (CharSequence)var3;
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "string-ref", 1, var3);
            }

            int var5;
            try {
               var5 = ((Number)var1).intValue();
            } catch (ClassCastException var6) {
               throw new WrongType(var6, "string-ref", 2, var1);
            }

            char var8 = strings.stringRef(var4, var5);
            var3 = Scheme.isEqv.apply2(Char.make(var8), printf.Lit5);
            if(var3 != Boolean.FALSE) {
               if(var3 != Boolean.FALSE) {
                  return Scheme.applyToArgs.apply3(var2, AddOp.$Pl.apply2(var1, printf.Lit7), Char.make(var8));
               }
            } else if(Scheme.isEqv.apply2(Char.make(var8), printf.Lit6) != Boolean.FALSE) {
               return Scheme.applyToArgs.apply3(var2, AddOp.$Pl.apply2(var1, printf.Lit7), Char.make(var8));
            }

            return Scheme.applyToArgs.apply3(var2, var1, printf.Lit6);
         } else {
            return Values.empty;
         }
      }

      public Object lambda3digits(Object var1, Object var2) {
         Object var3 = var1;

         CharSequence var6;
         int var7;
         while(true) {
            Object var4 = Scheme.numGEq.apply2(var3, Integer.valueOf(this.n));

            boolean var9;
            try {
               var9 = ((Boolean)var4).booleanValue();
            } catch (ClassCastException var13) {
               throw new WrongType(var13, "x", -2, var4);
            }

            if(var9) {
               if(var9) {
                  break;
               }
            } else {
               var4 = this.str;

               CharSequence var5;
               try {
                  var5 = (CharSequence)var4;
               } catch (ClassCastException var17) {
                  throw new WrongType(var17, "string-ref", 1, var4);
               }

               try {
                  var7 = ((Number)var3).intValue();
               } catch (ClassCastException var16) {
                  throw new WrongType(var16, "string-ref", 2, var3);
               }

               var9 = unicode.isCharNumeric(Char.make(strings.stringRef(var5, var7)));
               if(var9) {
                  if(!var9) {
                     break;
                  }
               } else {
                  Char var18 = printf.Lit8;
                  var4 = this.str;

                  try {
                     var6 = (CharSequence)var4;
                  } catch (ClassCastException var15) {
                     throw new WrongType(var15, "string-ref", 1, var4);
                  }

                  try {
                     var7 = ((Number)var3).intValue();
                  } catch (ClassCastException var14) {
                     throw new WrongType(var14, "string-ref", 2, var3);
                  }

                  if(!characters.isChar$Eq(var18, Char.make(strings.stringRef(var6, var7)))) {
                     break;
                  }
               }
            }

            var3 = AddOp.$Pl.apply2(var3, printf.Lit7);
         }

         ApplyToArgs var19 = Scheme.applyToArgs;
         if(Scheme.numEqu.apply2(var1, var3) != Boolean.FALSE) {
            var1 = "0";
         } else {
            Object var20 = this.str;

            try {
               var6 = (CharSequence)var20;
            } catch (ClassCastException var12) {
               throw new WrongType(var12, "substring", 1, var20);
            }

            try {
               var7 = ((Number)var1).intValue();
            } catch (ClassCastException var11) {
               throw new WrongType(var11, "substring", 2, var1);
            }

            int var8;
            try {
               var8 = ((Number)var3).intValue();
            } catch (ClassCastException var10) {
               throw new WrongType(var10, "substring", 3, var3);
            }

            var1 = strings.substring(var6, var7, var8);
         }

         return var19.apply3(var2, var3, var1);
      }

      public Object lambda4real(Object var1, Object var2) {
         printf.frame2 var3 = new printf.frame2();
         var3.staticLink = this;
         var3.cont = var2;
         ModuleMethod var13 = var3.lambda$Fn5;

         while(true) {
            Object var14 = Scheme.numLss.apply2(var1, Integer.valueOf(this.n - 1));

            boolean var7;
            try {
               var7 = ((Boolean)var14).booleanValue();
            } catch (ClassCastException var12) {
               throw new WrongType(var12, "x", -2, var14);
            }

            int var6;
            if(var7) {
               Char var4 = printf.Lit8;
               var14 = this.str;

               CharSequence var5;
               try {
                  var5 = (CharSequence)var14;
               } catch (ClassCastException var11) {
                  throw new WrongType(var11, "string-ref", 1, var14);
               }

               try {
                  var6 = ((Number)var1).intValue();
               } catch (ClassCastException var10) {
                  throw new WrongType(var10, "string-ref", 2, var1);
               }

               if(!characters.isChar$Eq(var4, Char.make(strings.stringRef(var5, var6)))) {
                  return Scheme.applyToArgs.apply2(var13, var1);
               }
            } else if(!var7) {
               return Scheme.applyToArgs.apply2(var13, var1);
            }

            var14 = this.str;

            CharSequence var15;
            try {
               var15 = (CharSequence)var14;
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "string-ref", 1, var14);
            }

            var14 = AddOp.$Pl.apply2(var1, printf.Lit7);

            try {
               var6 = ((Number)var14).intValue();
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "string-ref", 2, var14);
            }

            char var16;
            label60: {
               var16 = strings.stringRef(var15, var6);
               var14 = Scheme.isEqv.apply2(Char.make(var16), printf.Lit12);
               if(var14 != Boolean.FALSE) {
                  if(var14 == Boolean.FALSE) {
                     break label60;
                  }
               } else {
                  var14 = Scheme.isEqv.apply2(Char.make(var16), printf.Lit3);
                  if(var14 != Boolean.FALSE) {
                     if(var14 == Boolean.FALSE) {
                        break label60;
                     }
                  } else if(Scheme.isEqv.apply2(Char.make(var16), printf.Lit13) == Boolean.FALSE) {
                     break label60;
                  }
               }

               var1 = AddOp.$Pl.apply2(var1, printf.Lit14);
               continue;
            }

            if(Scheme.isEqv.apply2(Char.make(var16), printf.Lit11) != Boolean.FALSE) {
               return Scheme.applyToArgs.apply2(var13, var1);
            }

            return lambda1parseError();
         }
      }

      Object lambda5(Object var1, Object var2, Object var3, Object var4) {
         printf.frame0 var5 = new printf.frame0();
         var5.staticLink = this;
         var5.sgn = var2;
         var5.digs = var3;
         var5.ex = var4;
         if(Scheme.numEqu.apply2(var1, Integer.valueOf(this.n)) != Boolean.FALSE) {
            return Scheme.applyToArgs.apply4(this.proc, var5.sgn, var5.digs, var5.ex);
         } else {
            var2 = this.str;

            CharSequence var14;
            try {
               var14 = (CharSequence)var2;
            } catch (ClassCastException var12) {
               throw new WrongType(var12, "string-ref", 1, var2);
            }

            int var6;
            try {
               var6 = ((Number)var1).intValue();
            } catch (ClassCastException var11) {
               throw new WrongType(var11, "string-ref", 2, var1);
            }

            if(lists.memv(Char.make(strings.stringRef(var14, var6)), printf.Lit2) != Boolean.FALSE) {
               return this.lambda4real(var1, var5.lambda$Fn2);
            } else {
               IsEqv var16 = Scheme.isEqv;
               var2 = this.str;

               CharSequence var17;
               try {
                  var17 = (CharSequence)var2;
               } catch (ClassCastException var10) {
                  throw new WrongType(var10, "string-ref", 1, var2);
               }

               try {
                  var6 = ((Number)var1).intValue();
               } catch (ClassCastException var9) {
                  throw new WrongType(var9, "string-ref", 2, var1);
               }

               if(var16.apply2(Char.make(strings.stringRef(var17, var6)), printf.Lit4) != Boolean.FALSE) {
                  var1 = this.str;

                  CharSequence var13;
                  try {
                     var13 = (CharSequence)var1;
                  } catch (ClassCastException var8) {
                     throw new WrongType(var8, "string->number", 1, var1);
                  }

                  var5.num = numbers.string$To$Number(var13);
                  if(var5.num != Boolean.FALSE) {
                     var1 = var5.num;

                     Complex var15;
                     try {
                        var15 = (Complex)var1;
                     } catch (ClassCastException var7) {
                        throw new WrongType(var7, "real-part", 1, var1);
                     }

                     return printf.stdio$ClParseFloat(numbers.number$To$String(numbers.realPart(var15)), var5.lambda$Fn3);
                  } else {
                     return lambda1parseError();
                  }
               } else {
                  return Boolean.FALSE;
               }
            }
         }
      }

      public int match4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5, CallContext var6) {
         if(var1.selector == 12) {
            var6.value1 = var2;
            var6.value2 = var3;
            var6.value3 = var4;
            var6.value4 = var5;
            var6.proc = var1;
            var6.pc = 4;
            return 0;
         } else {
            return super.match4(var1, var2, var3, var4, var5, var6);
         }
      }
   }

   public class frame0 extends ModuleBody {

      Object digs;
      Object ex;
      final ModuleMethod lambda$Fn2;
      final ModuleMethod lambda$Fn3;
      Object num;
      Object sgn;
      printf.frame staticLink;


      public frame0() {
         ModuleMethod var1 = new ModuleMethod(this, 2, (Object)null, 16388);
         var1.setProperty("source-location", "printf.scm:111");
         this.lambda$Fn2 = var1;
         var1 = new ModuleMethod(this, 3, (Object)null, 12291);
         var1.setProperty("source-location", "printf.scm:123");
         this.lambda$Fn3 = var1;
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 3?this.lambda7(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      public Object apply4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5) {
         return var1.selector == 2?this.lambda6(var2, var3, var4, var5):super.apply4(var1, var2, var3, var4, var5);
      }

      Object lambda6(Object var1, Object var2, Object var3, Object var4) {
         Object var5 = Scheme.numEqu.apply2(var1, Integer.valueOf(this.staticLink.n - 1));

         boolean var9;
         try {
            var9 = ((Boolean)var5).booleanValue();
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "x", -2, var5);
         }

         if(var9) {
            Char var6 = printf.Lit3;
            var5 = this.staticLink.str;

            CharSequence var7;
            try {
               var7 = (CharSequence)var5;
            } catch (ClassCastException var11) {
               throw new WrongType(var11, "string-ref", 1, var5);
            }

            int var8;
            try {
               var8 = ((Number)var1).intValue();
            } catch (ClassCastException var10) {
               throw new WrongType(var10, "string-ref", 2, var1);
            }

            if(unicode.isCharCi$Eq(var6, Char.make(strings.stringRef(var7, var8)))) {
               return Scheme.applyToArgs.applyN(new Object[]{this.staticLink.proc, this.sgn, this.digs, this.ex, var2, var3, var4});
            }
         } else if(var9) {
            return Scheme.applyToArgs.applyN(new Object[]{this.staticLink.proc, this.sgn, this.digs, this.ex, var2, var3, var4});
         }

         return printf.frame.lambda1parseError();
      }

      Object lambda7(Object var1, Object var2, Object var3) {
         printf.frame1 var4 = new printf.frame1();
         var4.staticLink = this;
         var4.sgn = var1;
         var4.digs = var2;
         var4.ex = var3;
         var1 = this.num;

         Complex var6;
         try {
            var6 = (Complex)var1;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "imag-part", 1, var1);
         }

         return printf.stdio$ClParseFloat(numbers.number$To$String(numbers.imagPart(var6)), var4.lambda$Fn4);
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 3) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }

      public int match4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5, CallContext var6) {
         if(var1.selector == 2) {
            var6.value1 = var2;
            var6.value2 = var3;
            var6.value3 = var4;
            var6.value4 = var5;
            var6.proc = var1;
            var6.pc = 4;
            return 0;
         } else {
            return super.match4(var1, var2, var3, var4, var5, var6);
         }
      }
   }

   public class frame1 extends ModuleBody {

      Object digs;
      Object ex;
      final ModuleMethod lambda$Fn4;
      Object sgn;
      printf.frame0 staticLink;


      public frame1() {
         ModuleMethod var1 = new ModuleMethod(this, 1, (Object)null, 12291);
         var1.setProperty("source-location", "printf.scm:126");
         this.lambda$Fn4 = var1;
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 1?this.lambda8(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda8(Object var1, Object var2, Object var3) {
         return Scheme.applyToArgs.applyN(new Object[]{this.staticLink.staticLink.proc, this.sgn, this.digs, this.ex, var1, var2, var3});
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 1) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame10 extends ModuleBody {

      Object alternate$Mnform;
      Object args;
      Object blank;
      final ModuleMethod lambda$Fn13;
      final ModuleMethod lambda$Fn14;
      final ModuleMethod lambda$Fn15;
      final ModuleMethod lambda$Fn16;
      Object leading$Mn0s;
      Object left$Mnadjust;
      Object os;
      Procedure pad;
      Object pr;
      Object precision;
      Object signed;
      printf.frame9 staticLink;
      Object width;


      public frame10() {
         this.pad = new ModuleMethod(this, 15, printf.Lit67, -4095);
         ModuleMethod var1 = new ModuleMethod(this, 16, (Object)null, 4097);
         var1.setProperty("source-location", "printf.scm:472");
         this.lambda$Fn13 = var1;
         var1 = new ModuleMethod(this, 17, (Object)null, 4097);
         var1.setProperty("source-location", "printf.scm:476");
         this.lambda$Fn14 = var1;
         var1 = new ModuleMethod(this, 18, (Object)null, 4097);
         var1.setProperty("source-location", "printf.scm:484");
         this.lambda$Fn15 = var1;
         var1 = new ModuleMethod(this, 19, (Object)null, 4097);
         var1.setProperty("source-location", "printf.scm:494");
         this.lambda$Fn16 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         switch(var1.selector) {
         case 16:
            return this.lambda25(var2);
         case 17:
            if(this.lambda26(var2)) {
               return Boolean.TRUE;
            }

            return Boolean.FALSE;
         case 18:
            return this.lambda27(var2);
         case 19:
            if(this.lambda28(var2)) {
               return Boolean.TRUE;
            }

            return Boolean.FALSE;
         default:
            return super.apply1(var1, var2);
         }
      }

      public Object applyN(ModuleMethod var1, Object[] var2) {
         if(var1.selector != 15) {
            return super.applyN(var1, var2);
         } else {
            Object var5 = var2[0];
            int var4 = var2.length - 1;
            Object[] var3 = new Object[var4];

            while(true) {
               --var4;
               if(var4 < 0) {
                  return this.lambda23pad$V(var5, var3);
               }

               var3[var4] = var2[var4 + 1];
            }
         }
      }

      public Object lambda22readFormatNumber() {
         Object var1;
         if(Scheme.isEqv.apply2(printf.Lit66, this.staticLink.fc) != Boolean.FALSE) {
            this.staticLink.lambda18mustAdvance();
            var1 = lists.car.apply1(this.args);
            this.args = lists.cdr.apply1(this.args);
            return var1;
         } else {
            var1 = this.staticLink.fc;
            Object var2 = printf.Lit1;

            while(true) {
               Object var3 = this.staticLink.fc;

               Char var4;
               try {
                  var4 = (Char)var3;
               } catch (ClassCastException var5) {
                  throw new WrongType(var5, "char-numeric?", 1, var3);
               }

               if(!unicode.isCharNumeric(var4)) {
                  return var2;
               }

               this.staticLink.lambda18mustAdvance();
               var3 = this.staticLink.fc;
               AddOp var7 = AddOp.$Pl;
               var2 = MultiplyOp.$St.apply2(var2, printf.Lit45);
               Object[] var6;
               if(var1 instanceof Object[]) {
                  var6 = (Object[])var1;
               } else {
                  var6 = new Object[]{var1};
               }

               var2 = var7.apply2(var2, numbers.string$To$Number(strings.$make$string$(var6)));
               var1 = var3;
            }
         }
      }

      public Object lambda23pad$V(Object var1, Object[] var2) {
         LList var4 = LList.makeList(var2, 0);

         CharSequence var14;
         try {
            var14 = (CharSequence)var1;
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "string-length", 1, var1);
         }

         Object var3 = Integer.valueOf(strings.stringLength(var14));

         for(Object var15 = var4; Scheme.numGEq.apply2(var3, this.width) == Boolean.FALSE; var15 = lists.cdr.apply1(var15)) {
            if(lists.isNull(var15)) {
               int var8;
               if(this.left$Mnadjust != Boolean.FALSE) {
                  var15 = AddOp.$Mn.apply2(this.width, var3);

                  try {
                     var8 = ((Number)var15).intValue();
                  } catch (ClassCastException var9) {
                     throw new WrongType(var9, "make-string", 1, var15);
                  }

                  return lists.cons(var1, append.append$V(new Object[]{var4, LList.list1(strings.makeString(var8, printf.Lit29))}));
               }

               if(this.leading$Mn0s != Boolean.FALSE) {
                  var15 = AddOp.$Mn.apply2(this.width, var3);

                  try {
                     var8 = ((Number)var15).intValue();
                  } catch (ClassCastException var10) {
                     throw new WrongType(var10, "make-string", 1, var15);
                  }

                  return lists.cons(var1, lists.cons(strings.makeString(var8, printf.Lit9), var4));
               }

               var15 = AddOp.$Mn.apply2(this.width, var3);

               try {
                  var8 = ((Number)var15).intValue();
               } catch (ClassCastException var11) {
                  throw new WrongType(var11, "make-string", 1, var15);
               }

               return lists.cons(strings.makeString(var8, printf.Lit29), lists.cons(var1, var4));
            }

            AddOp var6 = AddOp.$Pl;
            Object var5 = lists.car.apply1(var15);

            CharSequence var7;
            try {
               var7 = (CharSequence)var5;
            } catch (ClassCastException var12) {
               throw new WrongType(var12, "string-length", 1, var5);
            }

            var3 = var6.apply2(var3, Integer.valueOf(strings.stringLength(var7)));
         }

         return lists.cons(var1, var4);
      }

      public Object lambda24integerConvert(Object var1, Object var2, Object var3) {
         Object var4 = this.precision;

         RealNum var5;
         try {
            var5 = LangObjType.coerceRealNum(var4);
         } catch (ClassCastException var19) {
            throw new WrongType(var19, "negative?", 1, var4);
         }

         if(!numbers.isNegative(var5)) {
            this.leading$Mn0s = Boolean.FALSE;
            var4 = this.precision;

            Number var28;
            try {
               var28 = (Number)var4;
            } catch (ClassCastException var18) {
               throw new WrongType(var18, "zero?", 1, var4);
            }

            label148: {
               boolean var7 = numbers.isZero(var28);
               if(var7) {
                  var4 = var1;
                  if(Scheme.isEqv.apply2(printf.Lit1, var1) == Boolean.FALSE) {
                     break label148;
                  }
               } else {
                  var4 = var1;
                  if(!var7) {
                     break label148;
                  }
               }

               var4 = "";
            }

            var1 = var4;
         }

         int var6;
         if(misc.isSymbol(var1)) {
            Symbol var29;
            try {
               var29 = (Symbol)var1;
            } catch (ClassCastException var17) {
               throw new WrongType(var17, "symbol->string", 1, var1);
            }

            var1 = misc.symbol$To$String(var29);
         } else if(numbers.isNumber(var1)) {
            Number var33;
            try {
               var33 = (Number)var1;
            } catch (ClassCastException var16) {
               throw new WrongType(var16, "number->string", 1, var1);
            }

            try {
               var6 = ((Number)var2).intValue();
            } catch (ClassCastException var15) {
               throw new WrongType(var15, "number->string", 2, var2);
            }

            var1 = numbers.number$To$String(var33, var6);
         } else {
            label157: {
               Boolean var31;
               try {
                  var31 = Boolean.FALSE;
               } catch (ClassCastException var14) {
                  throw new WrongType(var14, "x", -2, var1);
               }

               byte var32;
               if(var1 != var31) {
                  var32 = 1;
               } else {
                  var32 = 0;
               }

               label138: {
                  var6 = var32 + 1 & 1;
                  if(var6 != 0) {
                     if(var6 == 0) {
                        break label138;
                     }
                  } else if(!lists.isNull(var1)) {
                     break label138;
                  }

                  var1 = "0";
                  break label157;
               }

               if(!strings.isString(var1)) {
                  var1 = "1";
               }
            }
         }

         var4 = var1;
         if(var3 != Boolean.FALSE) {
            var4 = Scheme.applyToArgs.apply2(var3, var1);
         }

         String var24;
         CharSequence var30;
         if(IsEqual.apply("", var4)) {
            var24 = "";
         } else {
            IsEqv var26 = Scheme.isEqv;
            Char var23 = printf.Lit5;

            try {
               var30 = (CharSequence)var4;
            } catch (ClassCastException var13) {
               throw new WrongType(var13, "string-ref", 1, var4);
            }

            if(var26.apply2(var23, Char.make(strings.stringRef(var30, 0))) != Boolean.FALSE) {
               CharSequence var27;
               try {
                  var27 = (CharSequence)var4;
               } catch (ClassCastException var12) {
                  throw new WrongType(var12, "substring", 1, var4);
               }

               CharSequence var20;
               try {
                  var20 = (CharSequence)var4;
               } catch (ClassCastException var11) {
                  throw new WrongType(var11, "string-length", 1, var4);
               }

               var4 = strings.substring(var27, 1, strings.stringLength(var20));
               var24 = "-";
            } else if(this.signed != Boolean.FALSE) {
               var24 = "+";
            } else if(this.blank != Boolean.FALSE) {
               var24 = " ";
            } else if(this.alternate$Mnform != Boolean.FALSE) {
               if(Scheme.isEqv.apply2(var2, printf.Lit48) != Boolean.FALSE) {
                  var24 = "0";
               } else if(Scheme.isEqv.apply2(var2, printf.Lit50) != Boolean.FALSE) {
                  var24 = "0x";
               } else {
                  var24 = "";
               }
            } else {
               var24 = "";
            }
         }

         NumberCompare var21 = Scheme.numLss;

         CharSequence var25;
         try {
            var25 = (CharSequence)var4;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "string-length", 1, var4);
         }

         if(var21.apply2(Integer.valueOf(strings.stringLength(var25)), this.precision) != Boolean.FALSE) {
            AddOp var22 = AddOp.$Mn;
            var3 = this.precision;

            try {
               var30 = (CharSequence)var4;
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "string-length", 1, var4);
            }

            var2 = var22.apply2(var3, Integer.valueOf(strings.stringLength(var30)));

            try {
               var6 = ((Number)var2).intValue();
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "make-string", 1, var2);
            }

            var2 = strings.makeString(var6, printf.Lit9);
         } else {
            var2 = "";
         }

         return this.lambda23pad$V(var24, new Object[]{var2, var4});
      }

      Object lambda25(Object var1) {
         AddOp var2 = AddOp.$Pl;
         Object var3 = this.pr;

         CharSequence var4;
         try {
            var4 = (CharSequence)var1;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "string-length", 1, var1);
         }

         this.pr = var2.apply2(var3, Integer.valueOf(strings.stringLength(var4)));
         return Scheme.applyToArgs.apply2(this.staticLink.out, var1);
      }

      boolean lambda26(Object var1) {
         Special var2 = Special.undefined;
         AddOp var12 = AddOp.$Mn;
         Object var3 = this.pr;

         CharSequence var4;
         try {
            var4 = (CharSequence)var1;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "string-length", 1, var1);
         }

         Object var14 = var12.apply2(var3, Integer.valueOf(strings.stringLength(var4)));

         RealNum var15;
         try {
            var15 = LangObjType.coerceRealNum(var14);
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "negative?", 1, var14);
         }

         if(numbers.isNegative(var15)) {
            ApplyToArgs var16 = Scheme.applyToArgs;
            Object var17 = this.staticLink.out;

            CharSequence var5;
            try {
               var5 = (CharSequence)var1;
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "substring", 1, var1);
            }

            var1 = this.pr;

            int var6;
            try {
               var6 = ((Number)var1).intValue();
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "substring", 3, var1);
            }

            var16.apply2(var17, strings.substring(var5, 0, var6));
            var1 = printf.Lit1;
         } else {
            Scheme.applyToArgs.apply2(this.staticLink.out, var1);
            var1 = var14;
         }

         this.pr = var1;

         RealNum var13;
         try {
            var13 = LangObjType.coerceRealNum(var14);
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "positive?", 1, var14);
         }

         return numbers.isPositive(var13);
      }

      Boolean lambda27(Object var1) {
         AddOp var2 = AddOp.$Mn;
         Object var3 = this.pr;

         CharSequence var4;
         try {
            var4 = (CharSequence)var1;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "string-length", 1, var1);
         }

         this.pr = var2.apply2(var3, Integer.valueOf(strings.stringLength(var4)));
         if(this.os == Boolean.FALSE) {
            Scheme.applyToArgs.apply2(this.staticLink.out, var1);
         } else {
            Object var7 = this.pr;

            RealNum var8;
            try {
               var8 = LangObjType.coerceRealNum(var7);
            } catch (ClassCastException var5) {
               throw new WrongType(var5, "negative?", 1, var7);
            }

            if(numbers.isNegative(var8)) {
               Scheme.applyToArgs.apply2(this.staticLink.out, this.os);
               this.os = Boolean.FALSE;
               Scheme.applyToArgs.apply2(this.staticLink.out, var1);
            } else {
               this.os = strings.stringAppend(new Object[]{this.os, var1});
            }
         }

         return Boolean.TRUE;
      }

      boolean lambda28(Object var1) {
         Special var2 = Special.undefined;
         AddOp var12 = AddOp.$Mn;
         Object var3 = this.pr;

         CharSequence var4;
         try {
            var4 = (CharSequence)var1;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "string-length", 1, var1);
         }

         Object var13 = var12.apply2(var3, Integer.valueOf(strings.stringLength(var4)));

         RealNum var14;
         try {
            var14 = LangObjType.coerceRealNum(var13);
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "negative?", 1, var13);
         }

         if(numbers.isNegative(var14)) {
            var3 = this.os;

            try {
               var4 = (CharSequence)var1;
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "substring", 1, var1);
            }

            var1 = this.pr;

            int var5;
            try {
               var5 = ((Number)var1).intValue();
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "substring", 3, var1);
            }

            this.os = strings.stringAppend(new Object[]{var3, strings.substring(var4, 0, var5)});
         } else {
            this.os = strings.stringAppend(new Object[]{this.os, var1});
         }

         this.pr = var13;

         RealNum var11;
         try {
            var11 = LangObjType.coerceRealNum(var13);
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "positive?", 1, var13);
         }

         return numbers.isPositive(var11);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         switch(var1.selector) {
         case 16:
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         case 17:
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         case 18:
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         case 19:
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         default:
            return super.match1(var1, var2, var3);
         }
      }

      public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
         if(var1.selector == 15) {
            var3.values = var2;
            var3.proc = var1;
            var3.pc = 5;
            return 0;
         } else {
            return super.matchN(var1, var2, var3);
         }
      }
   }

   public class frame11 extends ModuleBody {

      Object fc;
      Procedure format$Mnreal;
      final ModuleMethod lambda$Fn17;
      printf.frame10 staticLink;


      public frame11() {
         this.format$Mnreal = new ModuleMethod(this, 13, printf.Lit64, -4092);
         ModuleMethod var1 = new ModuleMethod(this, 14, (Object)null, -4093);
         var1.setProperty("source-location", "printf.scm:401");
         this.lambda$Fn17 = var1;
      }

      public Object applyN(ModuleMethod var1, Object[] var2) {
         Object var3;
         Object var4;
         int var7;
         Object var8;
         switch(var1.selector) {
         case 13:
            var8 = var2[0];
            var3 = var2[1];
            var4 = var2[2];
            Object var9 = var2[3];
            var7 = var2.length - 4;
            Object[] var6 = new Object[var7];

            while(true) {
               --var7;
               if(var7 < 0) {
                  return this.lambda30formatReal$V(var8, var3, var4, var9, var6);
               }

               var6[var7] = var2[var7 + 4];
            }
         case 14:
            var8 = var2[0];
            var3 = var2[1];
            var4 = var2[2];
            var7 = var2.length - 3;
            Object[] var5 = new Object[var7];

            while(true) {
               --var7;
               if(var7 < 0) {
                  return this.lambda31$V(var8, var3, var4, var5);
               }

               var5[var7] = var2[var7 + 3];
            }
         default:
            return super.applyN(var1, var2);
         }
      }

      public Object lambda29f(Object var1, Object var2, Object var3) {
         CharSequence var4;
         try {
            var4 = (CharSequence)var1;
         } catch (ClassCastException var19) {
            throw new WrongType(var19, "stdio:round-string", 0, var1);
         }

         Object var5 = AddOp.$Pl.apply2(var2, this.staticLink.precision);
         if(var3 != Boolean.FALSE) {
            var1 = var2;
         } else {
            var1 = var3;
         }

         Object var25 = printf.stdio$ClRoundString(var4, var5, var1);
         int var6;
         boolean var8;
         if(Scheme.numGEq.apply2(var2, printf.Lit1) != Boolean.FALSE) {
            Number var21;
            try {
               var21 = (Number)var2;
            } catch (ClassCastException var18) {
               throw new WrongType(var18, "zero?", 1, var2);
            }

            IntNum var22;
            CharSequence var24;
            if(numbers.isZero(var21)) {
               var22 = printf.Lit1;
            } else {
               Char var23 = printf.Lit9;

               try {
                  var24 = (CharSequence)var25;
               } catch (ClassCastException var17) {
                  throw new WrongType(var17, "string-ref", 1, var25);
               }

               if(characters.isChar$Eq(var23, Char.make(strings.stringRef(var24, 0)))) {
                  var22 = printf.Lit7;
               } else {
                  var22 = printf.Lit1;
               }
            }

            var3 = numbers.max(new Object[]{printf.Lit7, AddOp.$Pl.apply2(printf.Lit7, var2)});

            CharSequence var20;
            try {
               var20 = (CharSequence)var25;
            } catch (ClassCastException var16) {
               throw new WrongType(var16, "substring", 1, var25);
            }

            try {
               var6 = var22.intValue();
            } catch (ClassCastException var15) {
               throw new WrongType(var15, "substring", 2, var22);
            }

            int var7;
            try {
               var7 = ((Number)var3).intValue();
            } catch (ClassCastException var14) {
               throw new WrongType(var14, "substring", 3, var3);
            }

            var20 = strings.substring(var20, var6, var7);

            CharSequence var26;
            try {
               var26 = (CharSequence)var25;
            } catch (ClassCastException var13) {
               throw new WrongType(var13, "substring", 1, var25);
            }

            try {
               var6 = ((Number)var3).intValue();
            } catch (ClassCastException var12) {
               throw new WrongType(var12, "substring", 2, var3);
            }

            try {
               var24 = (CharSequence)var25;
            } catch (ClassCastException var11) {
               throw new WrongType(var11, "string-length", 1, var25);
            }

            label118: {
               label117: {
                  var26 = strings.substring(var26, var6, strings.stringLength(var24));
                  var8 = strings.isString$Eq(var26, "");
                  if(var8) {
                     if(this.staticLink.alternate$Mnform == Boolean.FALSE) {
                        break label117;
                     }
                  } else if(var8) {
                     break label117;
                  }

                  var1 = LList.list2(".", var26);
                  break label118;
               }

               var1 = LList.Empty;
            }

            var3 = lists.cons(var20, var1);
         } else {
            var1 = this.staticLink.precision;

            Number var27;
            try {
               var27 = (Number)var1;
            } catch (ClassCastException var10) {
               throw new WrongType(var10, "zero?", 1, var1);
            }

            if(numbers.isZero(var27)) {
               String var28;
               if(this.staticLink.alternate$Mnform != Boolean.FALSE) {
                  var28 = "0.";
               } else {
                  var28 = "0";
               }

               return LList.list1(var28);
            }

            if(var3 != Boolean.FALSE) {
               var8 = strings.isString$Eq(var25, "");
               if(var8) {
                  var1 = LList.list1("0");
               } else if(var8) {
                  var1 = Boolean.TRUE;
               } else {
                  var1 = Boolean.FALSE;
               }
            } else {
               var1 = var3;
            }

            var3 = var1;
            if(var1 == Boolean.FALSE) {
               var1 = numbers.min(new Object[]{this.staticLink.precision, AddOp.$Mn.apply2(printf.Lit17, var2)});

               try {
                  var6 = ((Number)var1).intValue();
               } catch (ClassCastException var9) {
                  throw new WrongType(var9, "make-string", 1, var1);
               }

               return LList.list3("0.", strings.makeString(var6, printf.Lit9), var25);
            }
         }

         return var3;
      }

      public Object lambda30formatReal$V(Object var1, Object var2, Object var3, Object var4, Object[] var5) {
         LList var27 = LList.makeList(var5, 0);
         if(lists.isNull(var27)) {
            Char var30 = printf.Lit5;

            Char var6;
            try {
               var6 = (Char)var2;
            } catch (ClassCastException var23) {
               throw new WrongType(var23, "char=?", 2, var2);
            }

            String var24;
            if(characters.isChar$Eq(var30, var6)) {
               var24 = "-";
            } else if(var1 != Boolean.FALSE) {
               var24 = "+";
            } else if(this.staticLink.blank != Boolean.FALSE) {
               var24 = " ";
            } else {
               var24 = "";
            }

            int var8;
            boolean var9;
            String var29;
            Object var32;
            Boolean var33;
            label220: {
               label228: {
                  var1 = Scheme.isEqv.apply2(this.fc, printf.Lit13);
                  if(var1 != Boolean.FALSE) {
                     if(var1 != Boolean.FALSE) {
                        break label228;
                     }
                  } else if(Scheme.isEqv.apply2(this.fc, printf.Lit54) != Boolean.FALSE) {
                     break label228;
                  }

                  label244: {
                     var1 = Scheme.isEqv.apply2(this.fc, printf.Lit25);
                     if(var1 != Boolean.FALSE) {
                        if(var1 == Boolean.FALSE) {
                           break label244;
                        }
                     } else if(Scheme.isEqv.apply2(this.fc, printf.Lit26) == Boolean.FALSE) {
                        break label244;
                     }

                     var1 = this.lambda29f(var3, var4, Boolean.FALSE);
                     return lists.cons(var24, var1);
                  }

                  label229: {
                     var1 = Scheme.isEqv.apply2(this.fc, printf.Lit55);
                     if(var1 != Boolean.FALSE) {
                        if(var1 != Boolean.FALSE) {
                           break label229;
                        }
                     } else if(Scheme.isEqv.apply2(this.fc, printf.Lit56) != Boolean.FALSE) {
                        break label229;
                     }

                     if(Scheme.isEqv.apply2(this.fc, printf.Lit57) != Boolean.FALSE) {
                        var29 = "";
                     } else {
                        if(Scheme.isEqv.apply2(this.fc, printf.Lit58) == Boolean.FALSE) {
                           var1 = Values.empty;
                           return lists.cons(var24, var1);
                        }

                        var29 = " ";
                     }

                     RealNum var25;
                     try {
                        var25 = LangObjType.coerceRealNum(var4);
                     } catch (ClassCastException var22) {
                        throw new WrongType(var22, "negative?", 1, var4);
                     }

                     if(numbers.isNegative(var25)) {
                        var1 = DivideOp.quotient.apply2(AddOp.$Mn.apply2(var4, printf.Lit61), printf.Lit61);
                     } else {
                        var1 = DivideOp.quotient.apply2(AddOp.$Mn.apply2(var4, printf.Lit7), printf.Lit61);
                     }

                     var32 = Scheme.numLss.apply3(printf.Lit17, AddOp.$Pl.apply2(var1, printf.Lit48), Integer.valueOf(vectors.vectorLength(printf.Lit62)));

                     try {
                        var9 = ((Boolean)var32).booleanValue();
                     } catch (ClassCastException var21) {
                        throw new WrongType(var21, "x", -2, var32);
                     }

                     if(!var9) {
                        if(var9) {
                           var1 = Boolean.TRUE;
                        } else {
                           var1 = Boolean.FALSE;
                        }
                     }

                     if(var1 != Boolean.FALSE) {
                        var4 = AddOp.$Mn.apply2(var4, MultiplyOp.$St.apply2(printf.Lit61, var1));
                        this.staticLink.precision = numbers.max(new Object[]{printf.Lit1, AddOp.$Mn.apply2(this.staticLink.precision, var4)});
                        var3 = this.lambda29f(var3, var4, Boolean.FALSE);
                        FVector var26 = printf.Lit62;
                        var1 = AddOp.$Pl.apply2(var1, printf.Lit48);

                        try {
                           var8 = ((Number)var1).intValue();
                        } catch (ClassCastException var10) {
                           throw new WrongType(var10, "vector-ref", 2, var1);
                        }

                        var1 = append.append$V(new Object[]{var3, LList.list2(var29, vectors.vectorRef(var26, var8))});
                        return lists.cons(var24, var1);
                     }
                  }

                  var1 = this.staticLink.alternate$Mnform;

                  Boolean var38;
                  try {
                     var38 = Boolean.FALSE;
                  } catch (ClassCastException var20) {
                     throw new WrongType(var20, "strip-0s", -2, var1);
                  }

                  byte var39;
                  if(var1 != var38) {
                     var39 = 1;
                  } else {
                     var39 = 0;
                  }

                  var8 = var39 + 1 & 1;
                  this.staticLink.alternate$Mnform = Boolean.FALSE;
                  if(Scheme.numLEq.apply3(AddOp.$Mn.apply2(printf.Lit7, this.staticLink.precision), var4, this.staticLink.precision) != Boolean.FALSE) {
                     this.staticLink.precision = AddOp.$Mn.apply2(this.staticLink.precision, var4);
                     if(var8 != 0) {
                        var33 = Boolean.TRUE;
                     } else {
                        var33 = Boolean.FALSE;
                     }

                     var1 = this.lambda29f(var3, var4, var33);
                     return lists.cons(var24, var1);
                  }

                  this.staticLink.precision = AddOp.$Mn.apply2(this.staticLink.precision, printf.Lit7);
                  if(var8 != 0) {
                     var33 = Boolean.TRUE;
                  } else {
                     var33 = Boolean.FALSE;
                  }
                  break label220;
               }

               var33 = Boolean.FALSE;
            }

            CharSequence var36;
            try {
               var36 = (CharSequence)var3;
            } catch (ClassCastException var19) {
               throw new WrongType(var19, "stdio:round-string", 0, var3);
            }

            var32 = AddOp.$Pl.apply2(printf.Lit7, this.staticLink.precision);
            var3 = var33;
            if(var33 != Boolean.FALSE) {
               var3 = printf.Lit1;
            }

            var3 = printf.stdio$ClRoundString(var36, var32, var3);
            Char var41 = printf.Lit9;

            try {
               var36 = (CharSequence)var3;
            } catch (ClassCastException var18) {
               throw new WrongType(var18, "string-ref", 1, var3);
            }

            IntNum var40;
            if(characters.isChar$Eq(var41, Char.make(strings.stringRef(var36, 0)))) {
               var40 = printf.Lit7;
            } else {
               var40 = printf.Lit1;
            }

            try {
               var36 = (CharSequence)var3;
            } catch (ClassCastException var17) {
               throw new WrongType(var17, "substring", 1, var3);
            }

            var8 = var40.intValue();

            CharSequence var37;
            try {
               var37 = (CharSequence)var3;
            } catch (ClassCastException var16) {
               throw new WrongType(var16, "string-length", 1, var3);
            }

            CharSequence var7 = strings.substring(var36, var8 + 1, strings.stringLength(var37));
            if(!numbers.isZero(var40)) {
               var4 = AddOp.$Mn.apply2(var4, printf.Lit7);
            }

            try {
               var36 = (CharSequence)var3;
            } catch (ClassCastException var15) {
               throw new WrongType(var15, "substring", 1, var3);
            }

            try {
               var8 = var40.intValue();
            } catch (ClassCastException var14) {
               throw new WrongType(var14, "substring", 2, var40);
            }

            Pair var35;
            String var43;
            label183: {
               label182: {
                  var35 = LList.list1(strings.substring(var36, var8, var40.intValue() + 1));
                  var9 = strings.isString$Eq(var7, "");
                  if(var9) {
                     if(this.staticLink.alternate$Mnform != Boolean.FALSE) {
                        break label182;
                     }
                  } else if(!var9) {
                     break label182;
                  }

                  var43 = "";
                  break label183;
               }

               var43 = ".";
            }

            var3 = this.fc;

            try {
               var30 = (Char)var3;
            } catch (ClassCastException var13) {
               throw new WrongType(var13, "char-upper-case?", 1, var3);
            }

            String var28;
            if(unicode.isCharUpperCase(var30)) {
               var28 = "E";
            } else {
               var28 = "e";
            }

            RealNum var42;
            try {
               var42 = LangObjType.coerceRealNum(var4);
            } catch (ClassCastException var12) {
               throw new WrongType(var12, "negative?", 1, var4);
            }

            if(numbers.isNegative(var42)) {
               var29 = "-";
            } else {
               var29 = "+";
            }

            Pair var34 = LList.chain4(var35, var43, var7, var28, var29);
            if(Scheme.numLss.apply3(printf.Lit60, var4, printf.Lit45) != Boolean.FALSE) {
               var43 = "0";
            } else {
               var43 = "";
            }

            Pair var44 = LList.chain1(var34, var43);

            Number var31;
            try {
               var31 = (Number)var4;
            } catch (ClassCastException var11) {
               throw new WrongType(var11, "abs", 1, var4);
            }

            LList.chain1(var44, numbers.number$To$String(numbers.abs(var31)));
            var1 = var35;
            return lists.cons(var24, var1);
         } else {
            return append.append$V(new Object[]{this.lambda30formatReal$V(var1, var2, var3, var4, new Object[0]), Scheme.apply.apply3(this.format$Mnreal, Boolean.TRUE, var27), printf.Lit63});
         }
      }

      Object lambda31$V(Object var1, Object var2, Object var3, Object[] var4) {
         LList var5 = LList.makeList(var4, 0);
         return Scheme.apply.apply2(this.staticLink.pad, Scheme.apply.applyN(new Object[]{this.format$Mnreal, this.staticLink.signed, var1, var2, var3, var5}));
      }

      public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
         switch(var1.selector) {
         case 13:
            var3.values = var2;
            var3.proc = var1;
            var3.pc = 5;
            return 0;
         case 14:
            var3.values = var2;
            var3.proc = var1;
            var3.pc = 5;
            return 0;
         default:
            return super.matchN(var1, var2, var3);
         }
      }
   }

   public class frame12 extends ModuleBody {

      Object cnt;
      final ModuleMethod lambda$Fn18;
      Object port;


      public frame12() {
         ModuleMethod var1 = new ModuleMethod(this, 20, (Object)null, 4097);
         var1.setProperty("source-location", "printf.scm:546");
         this.lambda$Fn18 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 20?this.lambda32(var2):super.apply1(var1, var2);
      }

      Boolean lambda32(Object var1) {
         if(strings.isString(var1)) {
            AddOp var2 = AddOp.$Pl;

            CharSequence var3;
            try {
               var3 = (CharSequence)var1;
            } catch (ClassCastException var4) {
               throw new WrongType(var4, "string-length", 1, var1);
            }

            this.cnt = var2.apply2(Integer.valueOf(strings.stringLength(var3)), this.cnt);
            ports.display(var1, this.port);
            return Boolean.TRUE;
         } else {
            this.cnt = AddOp.$Pl.apply2(printf.Lit7, this.cnt);
            ports.display(var1, this.port);
            return Boolean.TRUE;
         }
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 20) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame13 extends ModuleBody {

      Object cnt;
      Object end;
      final ModuleMethod lambda$Fn19;
      Object s;
      Object str;


      public frame13() {
         ModuleMethod var1 = new ModuleMethod(this, 21, (Object)null, 4097);
         var1.setProperty("source-location", "printf.scm:564");
         this.lambda$Fn19 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 21?(this.lambda33(var2)?Boolean.TRUE:Boolean.FALSE):super.apply1(var1, var2);
      }

      boolean lambda33(Object var1) {
         int var7;
         CharSequence var25;
         Object var24;
         byte var30;
         if(strings.isString(var1)) {
            label153: {
               Object var4;
               CharSequence var23;
               if(this.str == Boolean.FALSE) {
                  NumberCompare var3 = Scheme.numGEq;
                  var4 = AddOp.$Mn.apply2(this.end, this.cnt);

                  CharSequence var5;
                  try {
                     var5 = (CharSequence)var1;
                  } catch (ClassCastException var22) {
                     throw new WrongType(var22, "string-length", 1, var1);
                  }

                  if(var3.apply2(var4, Integer.valueOf(strings.stringLength(var5))) == Boolean.FALSE) {
                     var24 = this.s;

                     try {
                        var25 = (CharSequence)var24;
                     } catch (ClassCastException var16) {
                        throw new WrongType(var16, "substring", 1, var24);
                     }

                     var24 = this.cnt;

                     try {
                        var7 = ((Number)var24).intValue();
                     } catch (ClassCastException var15) {
                        throw new WrongType(var15, "substring", 3, var24);
                     }

                     this.s = strings.stringAppend(new Object[]{strings.substring(var25, 0, var7), var1});
                     var1 = this.s;

                     try {
                        var23 = (CharSequence)var1;
                     } catch (ClassCastException var14) {
                        throw new WrongType(var14, "string-length", 1, var1);
                     }

                     this.cnt = Integer.valueOf(strings.stringLength(var23));
                     this.end = this.cnt;
                     break label153;
                  }
               }

               try {
                  var23 = (CharSequence)var1;
               } catch (ClassCastException var21) {
                  throw new WrongType(var21, "string-length", 1, var1);
               }

               var4 = numbers.min(new Object[]{Integer.valueOf(strings.stringLength(var23)), AddOp.$Mn.apply2(this.end, this.cnt)});

               for(var24 = printf.Lit1; Scheme.numGEq.apply2(var24, var4) == Boolean.FALSE; var24 = AddOp.$Pl.apply2(var24, printf.Lit7)) {
                  Object var6 = this.s;

                  CharSeq var26;
                  try {
                     var26 = (CharSeq)var6;
                  } catch (ClassCastException var20) {
                     throw new WrongType(var20, "string-set!", 1, var6);
                  }

                  var6 = this.cnt;

                  try {
                     var7 = ((Number)var6).intValue();
                  } catch (ClassCastException var19) {
                     throw new WrongType(var19, "string-set!", 2, var6);
                  }

                  CharSequence var29;
                  try {
                     var29 = (CharSequence)var1;
                  } catch (ClassCastException var18) {
                     throw new WrongType(var18, "string-ref", 1, var1);
                  }

                  int var8;
                  try {
                     var8 = ((Number)var24).intValue();
                  } catch (ClassCastException var17) {
                     throw new WrongType(var17, "string-ref", 2, var24);
                  }

                  strings.stringSet$Ex(var26, var7, strings.stringRef(var29, var8));
                  this.cnt = AddOp.$Pl.apply2(this.cnt, printf.Lit7);
               }
            }
         } else {
            if(this.str != Boolean.FALSE) {
               var24 = Scheme.numGEq.apply2(this.cnt, this.end);
            } else {
               var24 = this.str;
            }

            if(var24 == Boolean.FALSE) {
               var24 = this.str;

               Boolean var27;
               try {
                  var27 = Boolean.FALSE;
               } catch (ClassCastException var13) {
                  throw new WrongType(var13, "x", -2, var24);
               }

               if(var24 != var27) {
                  var30 = 1;
               } else {
                  var30 = 0;
               }

               label158: {
                  var7 = var30 + 1 & 1;
                  if(var7 != 0) {
                     if(Scheme.numGEq.apply2(this.cnt, this.end) == Boolean.FALSE) {
                        break label158;
                     }
                  } else if(var7 == 0) {
                     break label158;
                  }

                  this.s = strings.stringAppend(new Object[]{this.s, strings.makeString(100)});
                  var24 = this.s;

                  try {
                     var25 = (CharSequence)var24;
                  } catch (ClassCastException var12) {
                     throw new WrongType(var12, "string-length", 1, var24);
                  }

                  this.end = Integer.valueOf(strings.stringLength(var25));
               }

               var24 = this.s;

               CharSeq var28;
               try {
                  var28 = (CharSeq)var24;
               } catch (ClassCastException var11) {
                  throw new WrongType(var11, "string-set!", 1, var24);
               }

               var24 = this.cnt;

               try {
                  var7 = ((Number)var24).intValue();
               } catch (ClassCastException var10) {
                  throw new WrongType(var10, "string-set!", 2, var24);
               }

               char var2;
               if(characters.isChar(var1)) {
                  try {
                     var2 = ((Char)var1).charValue();
                  } catch (ClassCastException var9) {
                     throw new WrongType(var9, "string-set!", 3, var1);
                  }
               } else {
                  var2 = 63;
               }

               strings.stringSet$Ex(var28, var7, var2);
               this.cnt = AddOp.$Pl.apply2(this.cnt, printf.Lit7);
            }
         }

         if(this.str != Boolean.FALSE) {
            if(Scheme.numGEq.apply2(this.cnt, this.end) != Boolean.FALSE) {
               var30 = 1;
            } else {
               var30 = 0;
            }
         } else if(this.str != Boolean.FALSE) {
            var30 = 1;
         } else {
            var30 = 0;
         }

         return (boolean)(var30 + 1 & 1);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 21) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame2 extends ModuleBody {

      Object cont;
      final ModuleMethod lambda$Fn5;
      final ModuleMethod lambda$Fn6;
      printf.frame staticLink;


      public frame2() {
         ModuleMethod var1 = new ModuleMethod(this, 10, (Object)null, 8194);
         var1.setProperty("source-location", "printf.scm:81");
         this.lambda$Fn6 = var1;
         var1 = new ModuleMethod(this, 11, (Object)null, 4097);
         var1.setProperty("source-location", "printf.scm:78");
         this.lambda$Fn5 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 11?this.lambda9(var2):super.apply1(var1, var2);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 10?this.lambda10(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda10(Object var1, Object var2) {
         printf.frame3 var3 = new printf.frame3();
         var3.staticLink = this;
         var3.sgn = var2;
         return this.staticLink.lambda3digits(var1, var3.lambda$Fn7);
      }

      Object lambda9(Object var1) {
         return this.staticLink.lambda2sign(var1, this.lambda$Fn6);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 11) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 10) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame3 extends ModuleBody {

      final ModuleMethod lambda$Fn7;
      Object sgn;
      printf.frame2 staticLink;


      public frame3() {
         ModuleMethod var1 = new ModuleMethod(this, 9, (Object)null, 8194);
         var1.setProperty("source-location", "printf.scm:84");
         this.lambda$Fn7 = var1;
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 9?this.lambda11(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda11(Object var1, Object var2) {
         printf.frame4 var3 = new printf.frame4();
         var3.staticLink = this;
         var3.idigs = var2;
         ModuleMethod var11 = var3.lambda$Fn8;
         Object var12 = Scheme.numLss.apply2(var1, Integer.valueOf(this.staticLink.staticLink.n));

         boolean var7;
         try {
            var7 = ((Boolean)var12).booleanValue();
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "x", -2, var12);
         }

         if(var7) {
            Char var4 = printf.Lit11;
            var12 = this.staticLink.staticLink.str;

            CharSequence var5;
            try {
               var5 = (CharSequence)var12;
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "string-ref", 1, var12);
            }

            int var6;
            try {
               var6 = ((Number)var1).intValue();
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "string-ref", 2, var1);
            }

            if(characters.isChar$Eq(var4, Char.make(strings.stringRef(var5, var6)))) {
               return Scheme.applyToArgs.apply2(var11, AddOp.$Pl.apply2(var1, printf.Lit7));
            }
         } else if(var7) {
            return Scheme.applyToArgs.apply2(var11, AddOp.$Pl.apply2(var1, printf.Lit7));
         }

         return Scheme.applyToArgs.apply2(var11, var1);
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 9) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame4 extends ModuleBody {

      Object idigs;
      final ModuleMethod lambda$Fn8;
      final ModuleMethod lambda$Fn9;
      printf.frame3 staticLink;


      public frame4() {
         ModuleMethod var1 = new ModuleMethod(this, 7, (Object)null, 8194);
         var1.setProperty("source-location", "printf.scm:90");
         this.lambda$Fn9 = var1;
         var1 = new ModuleMethod(this, 8, (Object)null, 4097);
         var1.setProperty("source-location", "printf.scm:87");
         this.lambda$Fn8 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 8?this.lambda12(var2):super.apply1(var1, var2);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 7?this.lambda13(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda12(Object var1) {
         return this.staticLink.staticLink.staticLink.lambda3digits(var1, this.lambda$Fn9);
      }

      Object lambda13(Object var1, Object var2) {
         printf.frame5 var3 = new printf.frame5();
         var3.staticLink = this;
         var3.fdigs = var2;
         ModuleMethod var9 = var3.lambda$Fn10;
         printf.frame var4 = this.staticLink.staticLink.staticLink;
         printf.frame6 var8 = new printf.frame6();
         var8.staticLink = var4;
         var8.cont = var9;
         if(Scheme.numGEq.apply2(var1, Integer.valueOf(this.staticLink.staticLink.staticLink.n)) != Boolean.FALSE) {
            return Scheme.applyToArgs.apply3(var8.cont, var1, printf.Lit1);
         } else {
            Object var10 = this.staticLink.staticLink.staticLink.str;

            CharSequence var11;
            try {
               var11 = (CharSequence)var10;
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "string-ref", 1, var10);
            }

            int var5;
            try {
               var5 = ((Number)var1).intValue();
            } catch (ClassCastException var6) {
               throw new WrongType(var6, "string-ref", 2, var1);
            }

            return lists.memv(Char.make(strings.stringRef(var11, var5)), printf.Lit10) != Boolean.FALSE?this.staticLink.staticLink.staticLink.lambda2sign(AddOp.$Pl.apply2(var1, printf.Lit7), var8.lambda$Fn11):Scheme.applyToArgs.apply3(var8.cont, var1, printf.Lit1);
         }
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 8) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 7) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame5 extends ModuleBody {

      Object fdigs;
      final ModuleMethod lambda$Fn10;
      printf.frame4 staticLink;


      public frame5() {
         ModuleMethod var1 = new ModuleMethod(this, 6, (Object)null, 8194);
         var1.setProperty("source-location", "printf.scm:92");
         this.lambda$Fn10 = var1;
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 6?this.lambda14(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda14(Object var1, Object var2) {
         FString var4 = strings.stringAppend(new Object[]{"0", this.staticLink.idigs, this.fdigs});
         int var8 = strings.stringLength(var4);
         Object var3 = printf.Lit7;
         AddOp var6 = AddOp.$Pl;
         Object var5 = this.staticLink.idigs;

         CharSequence var7;
         try {
            var7 = (CharSequence)var5;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "string-length", 1, var5);
         }

         for(var2 = var6.apply2(var2, Integer.valueOf(strings.stringLength(var7))); Scheme.numGEq.apply2(var3, Integer.valueOf(var8)) == Boolean.FALSE; var2 = AddOp.$Mn.apply2(var2, printf.Lit7)) {
            Char var13 = printf.Lit9;

            int var9;
            try {
               var9 = ((Number)var3).intValue();
            } catch (ClassCastException var11) {
               throw new WrongType(var11, "string-ref", 2, var3);
            }

            if(!characters.isChar$Eq(var13, Char.make(strings.stringRef(var4, var9)))) {
               ApplyToArgs var14 = Scheme.applyToArgs;
               Object var15 = this.staticLink.staticLink.staticLink.cont;
               Object var16 = this.staticLink.staticLink.sgn;
               var3 = AddOp.$Mn.apply2(var3, printf.Lit7);

               try {
                  var9 = ((Number)var3).intValue();
               } catch (ClassCastException var10) {
                  throw new WrongType(var10, "substring", 2, var3);
               }

               return var14.applyN(new Object[]{var15, var1, var16, strings.substring(var4, var9, var8), var2});
            }

            var3 = AddOp.$Pl.apply2(var3, printf.Lit7);
         }

         return Scheme.applyToArgs.applyN(new Object[]{this.staticLink.staticLink.staticLink.cont, var1, this.staticLink.staticLink.sgn, "0", printf.Lit7});
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 6) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame6 extends ModuleBody {

      Object cont;
      final ModuleMethod lambda$Fn11;
      printf.frame staticLink;


      public frame6() {
         ModuleMethod var1 = new ModuleMethod(this, 5, (Object)null, 8194);
         var1.setProperty("source-location", "printf.scm:67");
         this.lambda$Fn11 = var1;
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 5?this.lambda15(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda15(Object var1, Object var2) {
         printf.frame7 var3 = new printf.frame7();
         var3.staticLink = this;
         var3.sgn = var2;
         return this.staticLink.lambda3digits(var1, var3.lambda$Fn12);
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 5) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame7 extends ModuleBody {

      final ModuleMethod lambda$Fn12;
      Object sgn;
      printf.frame6 staticLink;


      public frame7() {
         ModuleMethod var1 = new ModuleMethod(this, 4, (Object)null, 8194);
         var1.setProperty("source-location", "printf.scm:69");
         this.lambda$Fn12 = var1;
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 4?this.lambda16(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda16(Object var1, Object var2) {
         ApplyToArgs var3 = Scheme.applyToArgs;
         Object var4 = this.staticLink.cont;
         Char var6 = printf.Lit5;
         Object var5 = this.sgn;

         Char var7;
         try {
            var7 = (Char)var5;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "char=?", 2, var5);
         }

         if(characters.isChar$Eq(var6, var7)) {
            AddOp var11 = AddOp.$Mn;

            CharSequence var13;
            try {
               var13 = (CharSequence)var2;
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "string->number", 1, var2);
            }

            var2 = var11.apply1(numbers.string$To$Number(var13));
         } else {
            CharSequence var12;
            try {
               var12 = (CharSequence)var2;
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "string->number", 1, var2);
            }

            var2 = numbers.string$To$Number(var12);
         }

         return var3.apply3(var4, var1, var2);
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 4) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame8 extends ModuleBody {

      CharSequence str;


      public Object lambda17dig(Object var1) {
         CharSequence var2 = this.str;

         int var3;
         try {
            var3 = ((Number)var1).intValue();
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "string-ref", 2, var1);
         }

         char var5 = strings.stringRef(var2, var3);
         return unicode.isCharNumeric(Char.make(var5))?numbers.string$To$Number(strings.$make$string$(new Object[]{Char.make(var5)})):printf.Lit1;
      }
   }

   public class frame9 extends ModuleBody {

      LList args;
      Object fc;
      int fl;
      Object format$Mnstring;
      Object out;
      Object pos;


      public Object lambda18mustAdvance() {
         this.pos = AddOp.$Pl.apply2(printf.Lit7, this.pos);
         if(Scheme.numGEq.apply2(this.pos, Integer.valueOf(this.fl)) != Boolean.FALSE) {
            return this.lambda20incomplete();
         } else {
            Object var1 = this.format$Mnstring;

            CharSequence var2;
            try {
               var2 = (CharSequence)var1;
            } catch (ClassCastException var5) {
               throw new WrongType(var5, "string-ref", 1, var1);
            }

            var1 = this.pos;

            int var3;
            try {
               var3 = ((Number)var1).intValue();
            } catch (ClassCastException var4) {
               throw new WrongType(var4, "string-ref", 2, var1);
            }

            this.fc = Char.make(strings.stringRef(var2, var3));
            return Values.empty;
         }
      }

      public boolean lambda19isEndOfFormat() {
         return ((Boolean)Scheme.numGEq.apply2(this.pos, Integer.valueOf(this.fl))).booleanValue();
      }

      public Object lambda20incomplete() {
         return misc.error$V(printf.Lit34, new Object[]{"conversion specification incomplete", this.format$Mnstring});
      }

      public Object lambda21out$St(Object var1) {
         Object var2 = var1;
         if(strings.isString(var1)) {
            return Scheme.applyToArgs.apply2(this.out, var1);
         } else {
            while(true) {
               boolean var3 = lists.isNull(var2);
               if(var3) {
                  return var3?Boolean.TRUE:Boolean.FALSE;
               }

               var1 = Scheme.applyToArgs.apply2(this.out, lists.car.apply1(var2));
               if(var1 == Boolean.FALSE) {
                  return var1;
               }

               var2 = lists.cdr.apply1(var2);
            }
         }
      }
   }
}
