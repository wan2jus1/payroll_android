package gnu.kawa.slib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.Format;
import gnu.lists.CharSeq;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Char;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.lib.strings;
import kawa.lib.vectors;
import kawa.standard.Scheme;

public class genwrite extends ModuleBody {

   public static final genwrite $instance = new genwrite();
   static final Char Lit0 = Char.make(10);
   static final IntNum Lit1 = IntNum.make(0);
   static final SimpleSymbol Lit10 = (SimpleSymbol)(new SimpleSymbol("and")).readResolve();
   static final SimpleSymbol Lit11 = (SimpleSymbol)(new SimpleSymbol("or")).readResolve();
   static final SimpleSymbol Lit12 = (SimpleSymbol)(new SimpleSymbol("let")).readResolve();
   static final SimpleSymbol Lit13 = (SimpleSymbol)(new SimpleSymbol("begin")).readResolve();
   static final SimpleSymbol Lit14 = (SimpleSymbol)(new SimpleSymbol("do")).readResolve();
   static final IntNum Lit15 = IntNum.make(7);
   static final IntNum Lit16 = IntNum.make(8);
   static final IntNum Lit17 = IntNum.make(1);
   static final IntNum Lit18 = IntNum.make(50);
   static final IntNum Lit19 = IntNum.make(2);
   static final SimpleSymbol Lit2 = (SimpleSymbol)(new SimpleSymbol("lambda")).readResolve();
   static final SimpleSymbol Lit20 = (SimpleSymbol)(new SimpleSymbol("pp-expr")).readResolve();
   static final SimpleSymbol Lit21 = (SimpleSymbol)(new SimpleSymbol("pp-expr-list")).readResolve();
   static final SimpleSymbol Lit22 = (SimpleSymbol)(new SimpleSymbol("pp-LAMBDA")).readResolve();
   static final SimpleSymbol Lit23 = (SimpleSymbol)(new SimpleSymbol("pp-IF")).readResolve();
   static final SimpleSymbol Lit24 = (SimpleSymbol)(new SimpleSymbol("pp-COND")).readResolve();
   static final SimpleSymbol Lit25 = (SimpleSymbol)(new SimpleSymbol("pp-CASE")).readResolve();
   static final SimpleSymbol Lit26 = (SimpleSymbol)(new SimpleSymbol("pp-AND")).readResolve();
   static final SimpleSymbol Lit27 = (SimpleSymbol)(new SimpleSymbol("pp-LET")).readResolve();
   static final SimpleSymbol Lit28 = (SimpleSymbol)(new SimpleSymbol("pp-BEGIN")).readResolve();
   static final SimpleSymbol Lit29 = (SimpleSymbol)(new SimpleSymbol("pp-DO")).readResolve();
   static final SimpleSymbol Lit3 = (SimpleSymbol)(new SimpleSymbol("let*")).readResolve();
   static final SimpleSymbol Lit30 = (SimpleSymbol)(new SimpleSymbol("quote")).readResolve();
   static final SimpleSymbol Lit31 = (SimpleSymbol)(new SimpleSymbol("quasiquote")).readResolve();
   static final SimpleSymbol Lit32 = (SimpleSymbol)(new SimpleSymbol("unquote")).readResolve();
   static final SimpleSymbol Lit33 = (SimpleSymbol)(new SimpleSymbol("unquote-splicing")).readResolve();
   static final SimpleSymbol Lit34 = (SimpleSymbol)(new SimpleSymbol("generic-write")).readResolve();
   static final SimpleSymbol Lit35 = (SimpleSymbol)(new SimpleSymbol("reverse-string-append")).readResolve();
   static final SimpleSymbol Lit4 = (SimpleSymbol)(new SimpleSymbol("letrec")).readResolve();
   static final SimpleSymbol Lit5 = (SimpleSymbol)(new SimpleSymbol("define")).readResolve();
   static final SimpleSymbol Lit6 = (SimpleSymbol)(new SimpleSymbol("if")).readResolve();
   static final SimpleSymbol Lit7 = (SimpleSymbol)(new SimpleSymbol("set!")).readResolve();
   static final SimpleSymbol Lit8 = (SimpleSymbol)(new SimpleSymbol("cond")).readResolve();
   static final SimpleSymbol Lit9 = (SimpleSymbol)(new SimpleSymbol("case")).readResolve();
   public static final ModuleMethod generic$Mnwrite;
   public static final ModuleMethod reverse$Mnstring$Mnappend;


   static {
      genwrite var0 = $instance;
      generic$Mnwrite = new ModuleMethod(var0, 12, Lit34, 16388);
      reverse$Mnstring$Mnappend = new ModuleMethod(var0, 13, Lit35, 4097);
      $instance.run();
   }

   public genwrite() {
      ModuleInfo.register(this);
   }

   public static Object genericWrite(Object var0, Object var1, Object var2, Object var3) {
      genwrite.frame var4 = new genwrite.frame();
      var4.display$Qu = var1;
      var4.width = var2;
      var4.output = var3;
      if(var4.width != Boolean.FALSE) {
         CharSequence var14 = strings.makeString(1, Lit0);
         IntNum var15 = Lit1;
         genwrite.frame0 var16 = new genwrite.frame0();
         var16.staticLink = var4;
         Procedure var5 = var16.pp$Mnexpr;
         Procedure var6 = var16.pp$Mnexpr$Mnlist;
         Procedure var7 = var16.pp$MnLAMBDA;
         Procedure var8 = var16.pp$MnIF;
         Procedure var9 = var16.pp$MnCOND;
         Procedure var10 = var16.pp$MnCASE;
         Procedure var11 = var16.pp$MnAND;
         Procedure var12 = var16.pp$MnLET;
         Procedure var13 = var16.pp$MnBEGIN;
         var16.pp$MnDO = var16.pp$MnDO;
         var16.pp$MnBEGIN = var13;
         var16.pp$MnLET = var12;
         var16.pp$MnAND = var11;
         var16.pp$MnCASE = var10;
         var16.pp$MnCOND = var9;
         var16.pp$MnIF = var8;
         var16.pp$MnLAMBDA = var7;
         var16.pp$Mnexpr$Mnlist = var6;
         var16.pp$Mnexpr = var5;
         return var4.lambda4out(var14, var16.lambda7pr(var0, var15, Lit1, var16.pp$Mnexpr));
      } else {
         return var4.lambda5wr(var0, Lit1);
      }
   }

   public static Object lambda23revStringAppend(Object var0, Object var1) {
      int var7;
      Object var19;
      if(lists.isPair(var0)) {
         Object var4 = lists.car.apply1(var0);

         CharSequence var2;
         try {
            var2 = (CharSequence)var4;
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "string-length", 1, var4);
         }

         var7 = strings.stringLength(var2);
         Object var3 = lambda23revStringAppend(lists.cdr.apply1(var0), AddOp.$Pl.apply2(var1, Integer.valueOf(var7)));
         IntNum var18 = Lit1;
         AddOp var17 = AddOp.$Mn;
         AddOp var5 = AddOp.$Mn;

         CharSequence var6;
         try {
            var6 = (CharSequence)var3;
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "string-length", 1, var3);
         }

         var0 = var17.apply2(var5.apply2(Integer.valueOf(strings.stringLength(var6)), var1), Integer.valueOf(var7));
         var1 = var18;

         while(true) {
            var19 = var3;
            if(Scheme.numLss.apply2(var1, Integer.valueOf(var7)) == Boolean.FALSE) {
               break;
            }

            CharSeq var20;
            try {
               var20 = (CharSeq)var3;
            } catch (ClassCastException var14) {
               throw new WrongType(var14, "string-set!", 1, var3);
            }

            int var8;
            try {
               var8 = ((Number)var0).intValue();
            } catch (ClassCastException var13) {
               throw new WrongType(var13, "string-set!", 2, var0);
            }

            CharSequence var21;
            try {
               var21 = (CharSequence)var4;
            } catch (ClassCastException var12) {
               throw new WrongType(var12, "string-ref", 1, var4);
            }

            int var9;
            try {
               var9 = ((Number)var1).intValue();
            } catch (ClassCastException var11) {
               throw new WrongType(var11, "string-ref", 2, var1);
            }

            strings.stringSet$Ex(var20, var8, strings.stringRef(var21, var9));
            var1 = AddOp.$Pl.apply2(var1, Lit17);
            var0 = AddOp.$Pl.apply2(var0, Lit17);
         }
      } else {
         try {
            var7 = ((Number)var1).intValue();
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "make-string", 1, var1);
         }

         var19 = strings.makeString(var7);
      }

      return var19;
   }

   public static Object reverseStringAppend(Object var0) {
      return lambda23revStringAppend(var0, Lit1);
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      return var1.selector == 13?reverseStringAppend(var2):super.apply1(var1, var2);
   }

   public Object apply4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5) {
      return var1.selector == 12?genericWrite(var2, var3, var4, var5):super.apply4(var1, var2, var3, var4, var5);
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      if(var1.selector == 13) {
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      } else {
         return super.match1(var1, var2, var3);
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

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }

   public class frame extends ModuleBody {

      Object display$Qu;
      Object output;
      Object width;


      public static Object lambda1isReadMacro(Object var0) {
         Object var1 = lists.car.apply1(var0);
         var0 = lists.cdr.apply1(var0);
         Object var2 = Scheme.isEqv.apply2(var1, genwrite.Lit30);
         if(var2 != Boolean.FALSE) {
            if(var2 == Boolean.FALSE) {
               return Boolean.FALSE;
            }
         } else {
            var2 = Scheme.isEqv.apply2(var1, genwrite.Lit31);
            if(var2 != Boolean.FALSE) {
               if(var2 == Boolean.FALSE) {
                  return Boolean.FALSE;
               }
            } else {
               var2 = Scheme.isEqv.apply2(var1, genwrite.Lit32);
               if(var2 != Boolean.FALSE) {
                  if(var2 == Boolean.FALSE) {
                     return Boolean.FALSE;
                  }
               } else if(Scheme.isEqv.apply2(var1, genwrite.Lit33) == Boolean.FALSE) {
                  return Boolean.FALSE;
               }
            }
         }

         boolean var3 = lists.isPair(var0);
         if(var3) {
            if(lists.isNull(lists.cdr.apply1(var0))) {
               return Boolean.TRUE;
            } else {
               return Boolean.FALSE;
            }
         } else if(var3) {
            return Boolean.TRUE;
         } else {
            return Boolean.FALSE;
         }
      }

      public static Object lambda2readMacroBody(Object var0) {
         return lists.cadr.apply1(var0);
      }

      public static Object lambda3readMacroPrefix(Object var0) {
         Object var1 = lists.car.apply1(var0);
         lists.cdr.apply1(var0);
         return Scheme.isEqv.apply2(var1, genwrite.Lit30) != Boolean.FALSE?"\'":(Scheme.isEqv.apply2(var1, genwrite.Lit31) != Boolean.FALSE?"`":(Scheme.isEqv.apply2(var1, genwrite.Lit32) != Boolean.FALSE?",":(Scheme.isEqv.apply2(var1, genwrite.Lit33) != Boolean.FALSE?",@":Values.empty)));
      }

      public Object lambda4out(Object var1, Object var2) {
         if(var2 != Boolean.FALSE) {
            Object var4 = Scheme.applyToArgs.apply2(this.output, var1);
            Object var3 = var4;
            if(var4 != Boolean.FALSE) {
               AddOp var6 = AddOp.$Pl;

               CharSequence var7;
               try {
                  var7 = (CharSequence)var1;
               } catch (ClassCastException var5) {
                  throw new WrongType(var5, "string-length", 1, var1);
               }

               var3 = var6.apply2(var2, Integer.valueOf(strings.stringLength(var7)));
            }

            return var3;
         } else {
            return var2;
         }
      }

      public Object lambda5wr(Object var1, Object var2) {
         Object var3;
         Object var4;
         if(lists.isPair(var1)) {
            if(lambda1isReadMacro(var1) != Boolean.FALSE) {
               var3 = this.lambda5wr(lambda2readMacroBody(var1), this.lambda4out(lambda3readMacroPrefix(var1), var2));
               return var3;
            }

            var4 = var2;
            var3 = var1;
         } else {
            var3 = var1;
            var4 = var2;
            if(!lists.isNull(var1)) {
               if(!vectors.isVector(var1)) {
                  String var8;
                  if(this.display$Qu != Boolean.FALSE) {
                     var8 = "~a";
                  } else {
                     var8 = "~s";
                  }

                  return this.lambda4out(Format.formatToString(0, new Object[]{var8, var1}), var2);
               }

               FVector var7;
               try {
                  var7 = (FVector)var1;
               } catch (ClassCastException var6) {
                  throw new WrongType(var6, "vector->list", 1, var1);
               }

               var3 = vectors.vector$To$List(var7);
               var4 = this.lambda4out("#", var2);
            }
         }

         if(!lists.isPair(var3)) {
            return this.lambda4out("()", var4);
         } else {
            Object var5 = lists.cdr.apply1(var3);
            var2 = var5;
            var1 = var4;
            if(var4 != Boolean.FALSE) {
               var1 = this.lambda5wr(lists.car.apply1(var3), this.lambda4out("(", var4));
               var2 = var5;
            }

            while(true) {
               var3 = var1;
               if(var1 == Boolean.FALSE) {
                  return var3;
               }

               if(!lists.isPair(var2)) {
                  if(lists.isNull(var2)) {
                     return this.lambda4out(")", var1);
                  }

                  return this.lambda4out(")", this.lambda5wr(var2, this.lambda4out(" . ", var1)));
               }

               var3 = lists.cdr.apply1(var2);
               var1 = this.lambda5wr(lists.car.apply1(var2), this.lambda4out(" ", var1));
               var2 = var3;
            }
         }
      }
   }

   public class frame0 extends ModuleBody {

      Procedure pp$MnAND;
      Procedure pp$MnBEGIN;
      Procedure pp$MnCASE;
      Procedure pp$MnCOND;
      Procedure pp$MnDO;
      Procedure pp$MnIF;
      Procedure pp$MnLAMBDA;
      Procedure pp$MnLET;
      Procedure pp$Mnexpr;
      Procedure pp$Mnexpr$Mnlist;
      genwrite.frame staticLink;


      public frame0() {
         this.pp$Mnexpr = new ModuleMethod(this, 2, genwrite.Lit20, 12291);
         this.pp$Mnexpr$Mnlist = new ModuleMethod(this, 3, genwrite.Lit21, 12291);
         this.pp$MnLAMBDA = new ModuleMethod(this, 4, genwrite.Lit22, 12291);
         this.pp$MnIF = new ModuleMethod(this, 5, genwrite.Lit23, 12291);
         this.pp$MnCOND = new ModuleMethod(this, 6, genwrite.Lit24, 12291);
         this.pp$MnCASE = new ModuleMethod(this, 7, genwrite.Lit25, 12291);
         this.pp$MnAND = new ModuleMethod(this, 8, genwrite.Lit26, 12291);
         this.pp$MnLET = new ModuleMethod(this, 9, genwrite.Lit27, 12291);
         this.pp$MnBEGIN = new ModuleMethod(this, 10, genwrite.Lit28, 12291);
         this.pp$MnDO = new ModuleMethod(this, 11, genwrite.Lit29, 12291);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         switch(var1.selector) {
         case 2:
            return this.lambda8ppExpr(var2, var3, var4);
         case 3:
            return this.lambda13ppExprList(var2, var3, var4);
         case 4:
            return this.lambda14pp$MnLAMBDA(var2, var3, var4);
         case 5:
            return this.lambda15pp$MnIF(var2, var3, var4);
         case 6:
            return this.lambda16pp$MnCOND(var2, var3, var4);
         case 7:
            return this.lambda17pp$MnCASE(var2, var3, var4);
         case 8:
            return this.lambda18pp$MnAND(var2, var3, var4);
         case 9:
            return this.lambda19pp$MnLET(var2, var3, var4);
         case 10:
            return this.lambda20pp$MnBEGIN(var2, var3, var4);
         case 11:
            return this.lambda21pp$MnDO(var2, var3, var4);
         default:
            return super.apply3(var1, var2, var3, var4);
         }
      }

      public Object lambda10ppList(Object var1, Object var2, Object var3, Object var4) {
         var2 = this.staticLink.lambda4out("(", var2);
         return this.lambda11ppDown(var1, var2, var2, var3, var4);
      }

      public Object lambda11ppDown(Object var1, Object var2, Object var3, Object var4, Object var5) {
         while(true) {
            if(var2 != Boolean.FALSE) {
               if(lists.isPair(var1)) {
                  Object var7 = lists.cdr.apply1(var1);
                  Object var6;
                  if(lists.isNull(var7)) {
                     var6 = AddOp.$Pl.apply2(var4, genwrite.Lit17);
                  } else {
                     var6 = genwrite.Lit1;
                  }

                  var2 = this.lambda7pr(lists.car.apply1(var1), this.lambda6indent(var3, var2), var6, var5);
                  var1 = var7;
                  continue;
               }

               if(lists.isNull(var1)) {
                  return this.staticLink.lambda4out(")", var2);
               }

               return this.staticLink.lambda4out(")", this.lambda7pr(var1, this.lambda6indent(var3, this.staticLink.lambda4out(".", this.lambda6indent(var3, var2))), AddOp.$Pl.apply2(var4, genwrite.Lit17), var5));
            }

            return var2;
         }
      }

      public Object lambda12ppGeneral(Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7) {
         Object var8;
         Object var9;
         Object var10;
         label50: {
            label49: {
               var9 = lists.car.apply1(var1);
               var8 = lists.cdr.apply1(var1);
               var1 = this.staticLink.lambda5wr(var9, this.staticLink.lambda4out("(", var2));
               if(var4 != Boolean.FALSE) {
                  if(!lists.isPair(var8)) {
                     break label49;
                  }
               } else if(var4 == Boolean.FALSE) {
                  break label49;
               }

               var9 = lists.car.apply1(var8);
               var4 = lists.cdr.apply1(var8);
               var1 = this.staticLink.lambda5wr(var9, this.staticLink.lambda4out(" ", var1));
               var9 = AddOp.$Pl.apply2(var2, genwrite.Lit19);
               var10 = AddOp.$Pl.apply2(var1, genwrite.Lit17);
               break label50;
            }

            var9 = AddOp.$Pl.apply2(var2, genwrite.Lit19);
            var10 = AddOp.$Pl.apply2(var1, genwrite.Lit17);
            var4 = var8;
         }

         label53: {
            if(var5 != Boolean.FALSE) {
               var2 = var1;
               var8 = var4;
               if(!lists.isPair(var4)) {
                  break label53;
               }
            } else {
               var2 = var1;
               var8 = var4;
               if(var5 == Boolean.FALSE) {
                  break label53;
               }
            }

            Object var11 = lists.car.apply1(var4);
            var8 = lists.cdr.apply1(var4);
            if(lists.isNull(var8)) {
               var2 = AddOp.$Pl.apply2(var3, genwrite.Lit17);
            } else {
               var2 = genwrite.Lit1;
            }

            var2 = this.lambda7pr(var11, this.lambda6indent(var10, var1), var2, var5);
         }

         label59: {
            if(var6 != Boolean.FALSE) {
               if(!lists.isPair(var8)) {
                  break label59;
               }
            } else if(var6 == Boolean.FALSE) {
               break label59;
            }

            var5 = lists.car.apply1(var8);
            var4 = lists.cdr.apply1(var8);
            if(lists.isNull(var4)) {
               var1 = AddOp.$Pl.apply2(var3, genwrite.Lit17);
            } else {
               var1 = genwrite.Lit1;
            }

            var2 = this.lambda7pr(var5, this.lambda6indent(var10, var2), var1, var6);
            var1 = var4;
            return this.lambda11ppDown(var1, var2, var9, var3, var7);
         }

         var1 = var8;
         return this.lambda11ppDown(var1, var2, var9, var3, var7);
      }

      public Object lambda13ppExprList(Object var1, Object var2, Object var3) {
         return this.lambda10ppList(var1, var2, var3, this.pp$Mnexpr);
      }

      public Object lambda14pp$MnLAMBDA(Object var1, Object var2, Object var3) {
         return this.lambda12ppGeneral(var1, var2, var3, Boolean.FALSE, this.pp$Mnexpr$Mnlist, Boolean.FALSE, this.pp$Mnexpr);
      }

      public Object lambda15pp$MnIF(Object var1, Object var2, Object var3) {
         return this.lambda12ppGeneral(var1, var2, var3, Boolean.FALSE, this.pp$Mnexpr, Boolean.FALSE, this.pp$Mnexpr);
      }

      public Object lambda16pp$MnCOND(Object var1, Object var2, Object var3) {
         return this.lambda9ppCall(var1, var2, var3, this.pp$Mnexpr$Mnlist);
      }

      public Object lambda17pp$MnCASE(Object var1, Object var2, Object var3) {
         return this.lambda12ppGeneral(var1, var2, var3, Boolean.FALSE, this.pp$Mnexpr, Boolean.FALSE, this.pp$Mnexpr$Mnlist);
      }

      public Object lambda18pp$MnAND(Object var1, Object var2, Object var3) {
         return this.lambda9ppCall(var1, var2, var3, this.pp$Mnexpr);
      }

      public Object lambda19pp$MnLET(Object var1, Object var2, Object var3) {
         Object var4 = lists.cdr.apply1(var1);
         boolean var5 = lists.isPair(var4);
         if(var5) {
            var5 = misc.isSymbol(lists.car.apply1(var4));
         }

         Boolean var6;
         if(var5) {
            var6 = Boolean.TRUE;
         } else {
            var6 = Boolean.FALSE;
         }

         return this.lambda12ppGeneral(var1, var2, var3, var6, this.pp$Mnexpr$Mnlist, Boolean.FALSE, this.pp$Mnexpr);
      }

      public Object lambda20pp$MnBEGIN(Object var1, Object var2, Object var3) {
         return this.lambda12ppGeneral(var1, var2, var3, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, this.pp$Mnexpr);
      }

      public Object lambda21pp$MnDO(Object var1, Object var2, Object var3) {
         return this.lambda12ppGeneral(var1, var2, var3, Boolean.FALSE, this.pp$Mnexpr$Mnlist, this.pp$Mnexpr$Mnlist, this.pp$Mnexpr);
      }

      public Object lambda6indent(Object var1, Object var2) {
         if(var2 == Boolean.FALSE) {
            return var2;
         } else {
            Object var3;
            if(Scheme.numLss.apply2(var1, var2) != Boolean.FALSE) {
               var3 = this.staticLink.lambda4out(strings.makeString(1, genwrite.Lit0), var2);
               var2 = var3;
               if(var3 == Boolean.FALSE) {
                  return var2;
               }

               var2 = genwrite.Lit1;
            } else {
               var1 = AddOp.$Mn.apply2(var1, var2);
            }

            while(true) {
               var3 = var2;
               if(Scheme.numGrt.apply2(var1, genwrite.Lit1) == Boolean.FALSE) {
                  break;
               }

               if(Scheme.numGrt.apply2(var1, genwrite.Lit15) == Boolean.FALSE) {
                  genwrite.frame var6 = this.staticLink;

                  int var4;
                  try {
                     var4 = ((Number)var1).intValue();
                  } catch (ClassCastException var5) {
                     throw new WrongType(var5, "substring", 3, var1);
                  }

                  var3 = var6.lambda4out(strings.substring("        ", 0, var4), var2);
                  break;
               }

               var1 = AddOp.$Mn.apply2(var1, genwrite.Lit16);
               var2 = this.staticLink.lambda4out("        ", var2);
            }

            var2 = var3;
            return var2;
         }
      }

      public Object lambda7pr(Object var1, Object var2, Object var3, Object var4) {
         genwrite.frame1 var5 = new genwrite.frame1();
         var5.staticLink = this;
         boolean var7 = lists.isPair(var1);
         if(var7) {
            if(!var7) {
               return this.staticLink.lambda5wr(var1, var2);
            }
         } else if(!vectors.isVector(var1)) {
            return this.staticLink.lambda5wr(var1, var2);
         }

         LList var6 = LList.Empty;
         var5.left = numbers.min(new Object[]{AddOp.$Pl.apply2(AddOp.$Mn.apply2(AddOp.$Mn.apply2(this.staticLink.width, var2), var3), genwrite.Lit17), genwrite.Lit18});
         var5.result = var6;
         genwrite.genericWrite(var1, this.staticLink.display$Qu, Boolean.FALSE, var5.lambda$Fn1);
         if(Scheme.numGrt.apply2(var5.left, genwrite.Lit1) != Boolean.FALSE) {
            return this.staticLink.lambda4out(genwrite.reverseStringAppend(var5.result), var2);
         } else if(lists.isPair(var1)) {
            return Scheme.applyToArgs.apply4(var4, var1, var2, var3);
         } else {
            FVector var9;
            try {
               var9 = (FVector)var1;
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "vector->list", 1, var1);
            }

            return this.lambda10ppList(vectors.vector$To$List(var9), this.staticLink.lambda4out("#", var2), var3, this.pp$Mnexpr);
         }
      }

      public Object lambda8ppExpr(Object var1, Object var2, Object var3) {
         if(genwrite.frame.lambda1isReadMacro(var1) != Boolean.FALSE) {
            return this.lambda7pr(genwrite.frame.lambda2readMacroBody(var1), this.staticLink.lambda4out(genwrite.frame.lambda3readMacroPrefix(var1), var2), var3, this.pp$Mnexpr);
         } else {
            Object var5 = lists.car.apply1(var1);
            if(!misc.isSymbol(var5)) {
               return this.lambda10ppList(var1, var2, var3, this.pp$Mnexpr);
            } else {
               Object var4;
               label88: {
                  label81: {
                     var4 = Scheme.isEqv.apply2(var5, genwrite.Lit2);
                     if(var4 != Boolean.FALSE) {
                        if(var4 == Boolean.FALSE) {
                           break label81;
                        }
                     } else {
                        var4 = Scheme.isEqv.apply2(var5, genwrite.Lit3);
                        if(var4 != Boolean.FALSE) {
                           if(var4 == Boolean.FALSE) {
                              break label81;
                           }
                        } else {
                           var4 = Scheme.isEqv.apply2(var5, genwrite.Lit4);
                           if(var4 != Boolean.FALSE) {
                              if(var4 == Boolean.FALSE) {
                                 break label81;
                              }
                           } else if(Scheme.isEqv.apply2(var5, genwrite.Lit5) == Boolean.FALSE) {
                              break label81;
                           }
                        }
                     }

                     var4 = this.pp$MnLAMBDA;
                     break label88;
                  }

                  label70: {
                     var4 = Scheme.isEqv.apply2(var5, genwrite.Lit6);
                     if(var4 != Boolean.FALSE) {
                        if(var4 == Boolean.FALSE) {
                           break label70;
                        }
                     } else if(Scheme.isEqv.apply2(var5, genwrite.Lit7) == Boolean.FALSE) {
                        break label70;
                     }

                     var4 = this.pp$MnIF;
                     break label88;
                  }

                  if(Scheme.isEqv.apply2(var5, genwrite.Lit8) != Boolean.FALSE) {
                     var4 = this.pp$MnCOND;
                  } else if(Scheme.isEqv.apply2(var5, genwrite.Lit9) != Boolean.FALSE) {
                     var4 = this.pp$MnCASE;
                  } else {
                     label62: {
                        label61: {
                           var4 = Scheme.isEqv.apply2(var5, genwrite.Lit10);
                           if(var4 != Boolean.FALSE) {
                              if(var4 == Boolean.FALSE) {
                                 break label61;
                              }
                           } else if(Scheme.isEqv.apply2(var5, genwrite.Lit11) == Boolean.FALSE) {
                              break label61;
                           }

                           var4 = this.pp$MnAND;
                           break label62;
                        }

                        if(Scheme.isEqv.apply2(var5, genwrite.Lit12) != Boolean.FALSE) {
                           var4 = this.pp$MnLET;
                        } else if(Scheme.isEqv.apply2(var5, genwrite.Lit13) != Boolean.FALSE) {
                           var4 = this.pp$MnBEGIN;
                        } else if(Scheme.isEqv.apply2(var5, genwrite.Lit14) != Boolean.FALSE) {
                           var4 = this.pp$MnDO;
                        } else {
                           var4 = Boolean.FALSE;
                        }
                     }
                  }
               }

               if(var4 != Boolean.FALSE) {
                  return Scheme.applyToArgs.apply4(var4, var1, var2, var3);
               } else {
                  Symbol var7;
                  try {
                     var7 = (Symbol)var5;
                  } catch (ClassCastException var6) {
                     throw new WrongType(var6, "symbol->string", 1, var5);
                  }

                  return strings.stringLength(misc.symbol$To$String(var7)) > 5?this.lambda12ppGeneral(var1, var2, var3, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, this.pp$Mnexpr):this.lambda9ppCall(var1, var2, var3, this.pp$Mnexpr);
               }
            }
         }
      }

      public Object lambda9ppCall(Object var1, Object var2, Object var3, Object var4) {
         Object var6 = this.staticLink.lambda5wr(lists.car.apply1(var1), this.staticLink.lambda4out("(", var2));
         Object var5 = var2;
         if(var2 != Boolean.FALSE) {
            var5 = this.lambda11ppDown(lists.cdr.apply1(var1), var6, AddOp.$Pl.apply2(var6, genwrite.Lit17), var3, var4);
         }

         return var5;
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         switch(var1.selector) {
         case 2:
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         case 3:
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         case 4:
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         case 5:
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         case 6:
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         case 7:
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         case 8:
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         case 9:
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         case 10:
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         case 11:
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         default:
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame1 extends ModuleBody {

      final ModuleMethod lambda$Fn1;
      Object left;
      Object result;
      genwrite.frame0 staticLink;


      public frame1() {
         ModuleMethod var1 = new ModuleMethod(this, 1, (Object)null, 4097);
         var1.setProperty("source-location", "genwrite.scm:72");
         this.lambda$Fn1 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 1?(this.lambda22(var2)?Boolean.TRUE:Boolean.FALSE):super.apply1(var1, var2);
      }

      boolean lambda22(Object var1) {
         this.result = lists.cons(var1, this.result);
         AddOp var2 = AddOp.$Mn;
         Object var3 = this.left;

         CharSequence var4;
         try {
            var4 = (CharSequence)var1;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "string-length", 1, var1);
         }

         this.left = var2.apply2(var3, Integer.valueOf(strings.stringLength(var4)));
         return ((Boolean)Scheme.numGrt.apply2(this.left, genwrite.Lit1)).booleanValue();
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 1) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }
}
