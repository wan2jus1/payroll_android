package gnu.kawa.slib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.Apply;
import gnu.kawa.functions.IsEqual;
import gnu.kawa.functions.NumberCompare;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Char;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.strings;
import kawa.standard.Scheme;
import kawa.standard.call_with_values;

public class srfi37 extends ModuleBody {

   public static final srfi37 $instance = new srfi37();
   static final IntNum Lit0 = IntNum.make(1);
   static final Char Lit1 = Char.make(45);
   static final SimpleSymbol Lit10 = (SimpleSymbol)(new SimpleSymbol("option-processor")).readResolve();
   static final SimpleSymbol Lit11 = (SimpleSymbol)(new SimpleSymbol("args-fold")).readResolve();
   static final Char Lit2 = Char.make(61);
   static final IntNum Lit3 = IntNum.make(3);
   static final IntNum Lit4 = IntNum.make(0);
   static final SimpleSymbol Lit5 = (SimpleSymbol)(new SimpleSymbol("option?")).readResolve();
   static final SimpleSymbol Lit6 = (SimpleSymbol)(new SimpleSymbol("option")).readResolve();
   static final SimpleSymbol Lit7 = (SimpleSymbol)(new SimpleSymbol("option-names")).readResolve();
   static final SimpleSymbol Lit8 = (SimpleSymbol)(new SimpleSymbol("option-required-arg?")).readResolve();
   static final SimpleSymbol Lit9 = (SimpleSymbol)(new SimpleSymbol("option-optional-arg?")).readResolve();
   public static final ModuleMethod args$Mnfold;
   public static final ModuleMethod option;
   public static final ModuleMethod option$Mnnames;
   public static final ModuleMethod option$Mnoptional$Mnarg$Qu;
   public static final ModuleMethod option$Mnprocessor;
   public static final ModuleMethod option$Mnrequired$Mnarg$Qu;
   static final Class option$Mntype = srfi37.Mntype.class;
   public static final ModuleMethod option$Qu;


   static {
      srfi37 var0 = $instance;
      option$Qu = new ModuleMethod(var0, 25, Lit5, 4097);
      option = new ModuleMethod(var0, 26, Lit6, 16388);
      option$Mnnames = new ModuleMethod(var0, 27, Lit7, 4097);
      option$Mnrequired$Mnarg$Qu = new ModuleMethod(var0, 28, Lit8, 4097);
      option$Mnoptional$Mnarg$Qu = new ModuleMethod(var0, 29, Lit9, 4097);
      option$Mnprocessor = new ModuleMethod(var0, 30, Lit10, 4097);
      args$Mnfold = new ModuleMethod(var0, 31, Lit11, -4092);
      $instance.run();
   }

   public srfi37() {
      ModuleInfo.register(this);
   }

   public static Object argsFold$V(Object var0, Object var1, Object var2, Object var3, Object[] var4) {
      srfi37.frame var5 = new srfi37.frame();
      var5.options = var1;
      var5.unrecognized$Mnoption$Mnproc = var2;
      var5.operand$Mnproc = var3;
      return var5.lambda5scanArgs(var0, LList.makeList(var4, 0));
   }

   public static boolean isOption(Object var0) {
      return var0 instanceof srfi37.Mntype;
   }

   public static Object isOptionOptionalArg(srfi37.Mntype var0) {
      return var0.optional$Mnarg$Qu;
   }

   public static Object isOptionRequiredArg(srfi37.Mntype var0) {
      return var0.required$Mnarg$Qu;
   }

   public static srfi37.Mntype option(Object var0, Object var1, Object var2, Object var3) {
      srfi37.Mntype var4 = new srfi37.Mntype();
      var4.names = var0;
      var4.required$Mnarg$Qu = var1;
      var4.optional$Mnarg$Qu = var2;
      var4.processor = var3;
      return var4;
   }

   public static Object optionNames(srfi37.Mntype var0) {
      return var0.names;
   }

   public static Object optionProcessor(srfi37.Mntype var0) {
      return var0.processor;
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      srfi37.Mntype var7;
      switch(var1.selector) {
      case 25:
         if(isOption(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 26:
      default:
         return super.apply1(var1, var2);
      case 27:
         try {
            var7 = (srfi37.Mntype)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "option-names", 1, var2);
         }

         return optionNames(var7);
      case 28:
         try {
            var7 = (srfi37.Mntype)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "option-required-arg?", 1, var2);
         }

         return isOptionRequiredArg(var7);
      case 29:
         try {
            var7 = (srfi37.Mntype)var2;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "option-optional-arg?", 1, var2);
         }

         return isOptionOptionalArg(var7);
      case 30:
         try {
            var7 = (srfi37.Mntype)var2;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "option-processor", 1, var2);
         }

         return optionProcessor(var7);
      }
   }

   public Object apply4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5) {
      return var1.selector == 26?option(var2, var3, var4, var5):super.apply4(var1, var2, var3, var4, var5);
   }

   public Object applyN(ModuleMethod var1, Object[] var2) {
      if(var1.selector != 31) {
         return super.applyN(var1, var2);
      } else {
         Object var8 = var2[0];
         Object var3 = var2[1];
         Object var4 = var2[2];
         Object var5 = var2[3];
         int var7 = var2.length - 4;
         Object[] var6 = new Object[var7];

         while(true) {
            --var7;
            if(var7 < 0) {
               return argsFold$V(var8, var3, var4, var5, var6);
            }

            var6[var7] = var2[var7 + 4];
         }
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      int var4 = -786431;
      switch(var1.selector) {
      case 25:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 26:
      default:
         var4 = super.match1(var1, var2, var3);
         break;
      case 27:
         if(var2 instanceof srfi37.Mntype) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 28:
         if(var2 instanceof srfi37.Mntype) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 29:
         if(var2 instanceof srfi37.Mntype) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 30:
         if(var2 instanceof srfi37.Mntype) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
      }

      return var4;
   }

   public int match4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5, CallContext var6) {
      if(var1.selector == 26) {
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

   public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
      if(var1.selector == 31) {
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      } else {
         return super.matchN(var1, var2, var3);
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }

   public class Mntype {

      public Object names;
      public Object optional$Mnarg$Qu;
      public Object processor;
      public Object required$Mnarg$Qu;


   }

   public class frame extends ModuleBody {

      Object operand$Mnproc;
      Object options;
      Object unrecognized$Mnoption$Mnproc;


      public static Object lambda1find(Object var0, Object var1) {
         return lists.isNull(var0)?Boolean.FALSE:(Scheme.applyToArgs.apply2(var1, lists.car.apply1(var0)) != Boolean.FALSE?lists.car.apply1(var0):lambda1find(lists.cdr.apply1(var0), var1));
      }

      public Object lambda2findOption(Object var1) {
         srfi37.frame0 var2 = new srfi37.frame0();
         var2.staticLink = this;
         var2.name = var1;
         return lambda1find(this.options, var2.lambda$Fn1);
      }

      public Object lambda3scanShortOptions(Object var1, Object var2, Object var3, Object var4) {
         srfi37.frame1 var5 = new srfi37.frame1();
         var5.staticLink = this;
         var5.index = var1;
         var5.shorts = var2;
         var5.args = var3;
         var5.seeds = var4;
         NumberCompare var16 = Scheme.numEqu;
         var3 = var5.index;
         var1 = var5.shorts;

         CharSequence var19;
         try {
            var19 = (CharSequence)var1;
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "string-length", 1, var1);
         }

         if(var16.apply2(var3, Integer.valueOf(strings.stringLength(var19))) != Boolean.FALSE) {
            return this.lambda5scanArgs(var5.args, var5.seeds);
         } else {
            var1 = var5.shorts;

            CharSequence var17;
            try {
               var17 = (CharSequence)var1;
            } catch (ClassCastException var14) {
               throw new WrongType(var14, "string-ref", 1, var1);
            }

            var1 = var5.index;

            int var6;
            try {
               var6 = ((Number)var1).intValue();
            } catch (ClassCastException var13) {
               throw new WrongType(var13, "string-ref", 2, var1);
            }

            var5.name = strings.stringRef(var17, var6);
            var1 = this.lambda2findOption(Char.make(var5.name));
            if(var1 == Boolean.FALSE) {
               var1 = srfi37.option(LList.list1(Char.make(var5.name)), Boolean.FALSE, Boolean.FALSE, this.unrecognized$Mnoption$Mnproc);
            }

            var5.option = var1;
            var16 = Scheme.numLss;
            var3 = AddOp.$Pl.apply2(var5.index, srfi37.Lit0);
            var1 = var5.shorts;

            try {
               var19 = (CharSequence)var1;
            } catch (ClassCastException var12) {
               throw new WrongType(var12, "string-length", 1, var1);
            }

            var1 = var16.apply2(var3, Integer.valueOf(strings.stringLength(var19)));

            boolean var7;
            try {
               var7 = ((Boolean)var1).booleanValue();
            } catch (ClassCastException var11) {
               throw new WrongType(var11, "x", -2, var1);
            }

            srfi37.Mntype var18;
            if(var7) {
               var1 = var5.option;

               try {
                  var18 = (srfi37.Mntype)var1;
               } catch (ClassCastException var10) {
                  throw new WrongType(var10, "option-required-arg?", 0, var1);
               }

               var1 = srfi37.isOptionRequiredArg(var18);
               if(var1 != Boolean.FALSE) {
                  if(var1 != Boolean.FALSE) {
                     return call_with_values.callWithValues(var5.lambda$Fn3, var5.lambda$Fn4);
                  }
               } else {
                  var1 = var5.option;

                  try {
                     var18 = (srfi37.Mntype)var1;
                  } catch (ClassCastException var9) {
                     throw new WrongType(var9, "option-optional-arg?", 0, var1);
                  }

                  if(srfi37.isOptionOptionalArg(var18) != Boolean.FALSE) {
                     return call_with_values.callWithValues(var5.lambda$Fn3, var5.lambda$Fn4);
                  }
               }
            } else if(var7) {
               return call_with_values.callWithValues(var5.lambda$Fn3, var5.lambda$Fn4);
            }

            var1 = var5.option;

            try {
               var18 = (srfi37.Mntype)var1;
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "option-required-arg?", 0, var1);
            }

            var1 = srfi37.isOptionRequiredArg(var18);
            if(var1 != Boolean.FALSE) {
               if(!lists.isPair(var5.args)) {
                  return call_with_values.callWithValues(var5.lambda$Fn7, var5.lambda$Fn8);
               }
            } else if(var1 == Boolean.FALSE) {
               return call_with_values.callWithValues(var5.lambda$Fn7, var5.lambda$Fn8);
            }

            return call_with_values.callWithValues(var5.lambda$Fn5, var5.lambda$Fn6);
         }
      }

      public Object lambda4scanOperands(Object var1, Object var2) {
         srfi37.frame2 var3 = new srfi37.frame2();
         var3.staticLink = this;
         var3.operands = var1;
         var3.seeds = var2;
         return lists.isNull(var3.operands)?Scheme.apply.apply2(misc.values, var3.seeds):call_with_values.callWithValues(var3.lambda$Fn9, var3.lambda$Fn10);
      }

      public Object lambda5scanArgs(Object var1, Object var2) {
         throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:783)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:662)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:722)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s2stmt(TypeTransformer.java:823)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:846)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)\r\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)\r\n\tat java.lang.reflect.Method.invoke(Unknown Source)\r\n\tat com.exe4j.runtime.LauncherEngine.launch(Unknown Source)\r\n\tat com.exe4j.runtime.WinLauncher.main(Unknown Source)\r\n");
      }
   }

   public class frame0 extends ModuleBody {

      final ModuleMethod lambda$Fn1;
      final ModuleMethod lambda$Fn2;
      Object name;
      srfi37.frame staticLink;


      public frame0() {
         ModuleMethod var1 = new ModuleMethod(this, 1, (Object)null, 4097);
         var1.setProperty("source-location", "srfi37.scm:75");
         this.lambda$Fn2 = var1;
         var1 = new ModuleMethod(this, 2, (Object)null, 4097);
         var1.setProperty("source-location", "srfi37.scm:72");
         this.lambda$Fn1 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         switch(var1.selector) {
         case 1:
            if(this.lambda7(var2)) {
               return Boolean.TRUE;
            }

            return Boolean.FALSE;
         case 2:
            return this.lambda6(var2);
         default:
            return super.apply1(var1, var2);
         }
      }

      Object lambda6(Object var1) {
         srfi37.Mntype var2;
         try {
            var2 = (srfi37.Mntype)var1;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "option-names", 0, var1);
         }

         return srfi37.frame.lambda1find(srfi37.optionNames(var2), this.lambda$Fn2);
      }

      boolean lambda7(Object var1) {
         return IsEqual.apply(this.name, var1);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         switch(var1.selector) {
         case 1:
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         case 2:
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         default:
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame1 extends ModuleBody {

      Object args;
      Object index;
      final ModuleMethod lambda$Fn3 = new ModuleMethod(this, 3, (Object)null, 0);
      final ModuleMethod lambda$Fn4 = new ModuleMethod(this, 4, (Object)null, -4096);
      final ModuleMethod lambda$Fn5 = new ModuleMethod(this, 5, (Object)null, 0);
      final ModuleMethod lambda$Fn6 = new ModuleMethod(this, 6, (Object)null, -4096);
      final ModuleMethod lambda$Fn7 = new ModuleMethod(this, 7, (Object)null, 0);
      final ModuleMethod lambda$Fn8 = new ModuleMethod(this, 8, (Object)null, -4096);
      char name;
      Object option;
      Object seeds;
      Object shorts;
      srfi37.frame staticLink;


      public Object apply0(ModuleMethod var1) {
         switch(var1.selector) {
         case 3:
            return this.lambda8();
         case 4:
         case 6:
         default:
            return super.apply0(var1);
         case 5:
            return this.lambda10();
         case 7:
            return this.lambda12();
         }
      }

      public Object applyN(ModuleMethod var1, Object[] var2) {
         switch(var1.selector) {
         case 4:
            return this.lambda9$V(var2);
         case 5:
         case 7:
         default:
            return super.applyN(var1, var2);
         case 6:
            return this.lambda11$V(var2);
         case 8:
            return this.lambda13$V(var2);
         }
      }

      Object lambda10() {
         Apply var2 = Scheme.apply;
         Object var1 = this.option;

         srfi37.Mntype var3;
         try {
            var3 = (srfi37.Mntype)var1;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "option-processor", 0, var1);
         }

         return var2.applyN(new Object[]{srfi37.optionProcessor(var3), this.option, Char.make(this.name), lists.car.apply1(this.args), this.seeds});
      }

      Object lambda11$V(Object[] var1) {
         LList var2 = LList.makeList(var1, 0);
         return this.staticLink.lambda5scanArgs(lists.cdr.apply1(this.args), var2);
      }

      Object lambda12() {
         Apply var2 = Scheme.apply;
         Object var1 = this.option;

         srfi37.Mntype var3;
         try {
            var3 = (srfi37.Mntype)var1;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "option-processor", 0, var1);
         }

         return var2.applyN(new Object[]{srfi37.optionProcessor(var3), this.option, Char.make(this.name), Boolean.FALSE, this.seeds});
      }

      Object lambda13$V(Object[] var1) {
         LList var2 = LList.makeList(var1, 0);
         return this.staticLink.lambda3scanShortOptions(AddOp.$Pl.apply2(this.index, srfi37.Lit0), this.shorts, this.args, var2);
      }

      Object lambda8() {
         Apply var1 = Scheme.apply;
         Object var2 = this.option;

         srfi37.Mntype var3;
         try {
            var3 = (srfi37.Mntype)var2;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "option-processor", 0, var2);
         }

         var2 = srfi37.optionProcessor(var3);
         Object var13 = this.option;
         Char var4 = Char.make(this.name);
         Object var6 = this.shorts;

         CharSequence var5;
         try {
            var5 = (CharSequence)var6;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "substring", 1, var6);
         }

         var6 = AddOp.$Pl.apply2(this.index, srfi37.Lit0);

         int var8;
         try {
            var8 = ((Number)var6).intValue();
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "substring", 2, var6);
         }

         var6 = this.shorts;

         CharSequence var7;
         try {
            var7 = (CharSequence)var6;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "string-length", 1, var6);
         }

         return var1.applyN(new Object[]{var2, var13, var4, strings.substring(var5, var8, strings.stringLength(var7)), this.seeds});
      }

      Object lambda9$V(Object[] var1) {
         LList var2 = LList.makeList(var1, 0);
         return this.staticLink.lambda5scanArgs(this.args, var2);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         switch(var1.selector) {
         case 3:
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         case 4:
         case 6:
         default:
            return super.match0(var1, var2);
         case 5:
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         case 7:
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         }
      }

      public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
         switch(var1.selector) {
         case 4:
            var3.values = var2;
            var3.proc = var1;
            var3.pc = 5;
            return 0;
         case 5:
         case 7:
         default:
            return super.matchN(var1, var2, var3);
         case 6:
            var3.values = var2;
            var3.proc = var1;
            var3.pc = 5;
            return 0;
         case 8:
            var3.values = var2;
            var3.proc = var1;
            var3.pc = 5;
            return 0;
         }
      }
   }

   public class frame2 extends ModuleBody {

      final ModuleMethod lambda$Fn10 = new ModuleMethod(this, 10, (Object)null, -4096);
      final ModuleMethod lambda$Fn9 = new ModuleMethod(this, 9, (Object)null, 0);
      Object operands;
      Object seeds;
      srfi37.frame staticLink;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 9?this.lambda14():super.apply0(var1);
      }

      public Object applyN(ModuleMethod var1, Object[] var2) {
         return var1.selector == 10?this.lambda15$V(var2):super.applyN(var1, var2);
      }

      Object lambda14() {
         return Scheme.apply.apply3(this.staticLink.operand$Mnproc, lists.car.apply1(this.operands), this.seeds);
      }

      Object lambda15$V(Object[] var1) {
         LList var2 = LList.makeList(var1, 0);
         return this.staticLink.lambda4scanOperands(lists.cdr.apply1(this.operands), var2);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 9) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
         if(var1.selector == 10) {
            var3.values = var2;
            var3.proc = var1;
            var3.pc = 5;
            return 0;
         } else {
            return super.matchN(var1, var2, var3);
         }
      }
   }

   public class frame3 extends ModuleBody {

      Object arg;
      Object args;
      final ModuleMethod lambda$Fn11 = new ModuleMethod(this, 17, (Object)null, 0);
      final ModuleMethod lambda$Fn12 = new ModuleMethod(this, 18, (Object)null, 4097);
      final ModuleMethod lambda$Fn19 = new ModuleMethod(this, 19, (Object)null, 0);
      final ModuleMethod lambda$Fn20 = new ModuleMethod(this, 20, (Object)null, -4096);
      final ModuleMethod lambda$Fn21 = new ModuleMethod(this, 21, (Object)null, 0);
      final ModuleMethod lambda$Fn22 = new ModuleMethod(this, 22, (Object)null, -4096);
      final ModuleMethod lambda$Fn23 = new ModuleMethod(this, 23, (Object)null, 0);
      final ModuleMethod lambda$Fn24 = new ModuleMethod(this, 24, (Object)null, -4096);
      CharSequence name;
      Object option;
      Object seeds;
      srfi37.frame staticLink;
      Object temp;


      public Object apply0(ModuleMethod var1) {
         switch(var1.selector) {
         case 17:
            return this.lambda16();
         case 18:
         case 20:
         case 22:
         default:
            return super.apply0(var1);
         case 19:
            return this.lambda24();
         case 21:
            return this.lambda26();
         case 23:
            return this.lambda28();
         }
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 18?this.lambda17(var2):super.apply1(var1, var2);
      }

      public Object applyN(ModuleMethod var1, Object[] var2) {
         switch(var1.selector) {
         case 20:
            return this.lambda25$V(var2);
         case 21:
         case 23:
         default:
            return super.applyN(var1, var2);
         case 22:
            return this.lambda27$V(var2);
         case 24:
            return this.lambda29$V(var2);
         }
      }

      CharSequence lambda16() {
         Object var1 = this.arg;

         CharSequence var2;
         try {
            var2 = (CharSequence)var1;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "substring", 1, var1);
         }

         var1 = this.temp;

         int var3;
         try {
            var3 = ((Number)var1).intValue();
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "substring", 3, var1);
         }

         return strings.substring(var2, 2, var3);
      }

      Object lambda17(Object var1) {
         srfi37.frame4 var2 = new srfi37.frame4();
         var2.staticLink = this;
         var2.x = var1;
         return call_with_values.callWithValues(var2.lambda$Fn13, var2.lambda$Fn14);
      }

      Object lambda24() {
         Apply var2 = Scheme.apply;
         Object var1 = this.option;

         srfi37.Mntype var3;
         try {
            var3 = (srfi37.Mntype)var1;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "option-processor", 0, var1);
         }

         return var2.applyN(new Object[]{srfi37.optionProcessor(var3), this.option, this.name, lists.car.apply1(this.args), this.seeds});
      }

      Object lambda25$V(Object[] var1) {
         LList var2 = LList.makeList(var1, 0);
         return this.staticLink.lambda5scanArgs(lists.cdr.apply1(this.args), var2);
      }

      Object lambda26() {
         Apply var2 = Scheme.apply;
         Object var1 = this.option;

         srfi37.Mntype var3;
         try {
            var3 = (srfi37.Mntype)var1;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "option-processor", 0, var1);
         }

         return var2.applyN(new Object[]{srfi37.optionProcessor(var3), this.option, this.name, Boolean.FALSE, this.seeds});
      }

      Object lambda27$V(Object[] var1) {
         LList var2 = LList.makeList(var1, 0);
         return this.staticLink.lambda5scanArgs(this.args, var2);
      }

      Object lambda28() {
         return Scheme.apply.apply3(this.staticLink.operand$Mnproc, this.arg, this.seeds);
      }

      Object lambda29$V(Object[] var1) {
         LList var2 = LList.makeList(var1, 0);
         return this.staticLink.lambda5scanArgs(this.args, var2);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         switch(var1.selector) {
         case 17:
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         case 18:
         case 20:
         case 22:
         default:
            return super.match0(var1, var2);
         case 19:
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         case 21:
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         case 23:
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         }
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 18) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }

      public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
         switch(var1.selector) {
         case 20:
            var3.values = var2;
            var3.proc = var1;
            var3.pc = 5;
            return 0;
         case 21:
         case 23:
         default:
            return super.matchN(var1, var2, var3);
         case 22:
            var3.values = var2;
            var3.proc = var1;
            var3.pc = 5;
            return 0;
         case 24:
            var3.values = var2;
            var3.proc = var1;
            var3.pc = 5;
            return 0;
         }
      }
   }

   public class frame4 extends ModuleBody {

      final ModuleMethod lambda$Fn13 = new ModuleMethod(this, 15, (Object)null, 0);
      final ModuleMethod lambda$Fn14 = new ModuleMethod(this, 16, (Object)null, 4097);
      srfi37.frame3 staticLink;
      Object x;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 15?this.lambda18():super.apply0(var1);
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 16?this.lambda19(var2):super.apply1(var1, var2);
      }

      CharSequence lambda18() {
         Object var2 = this.staticLink.arg;

         CharSequence var1;
         try {
            var1 = (CharSequence)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "substring", 1, var2);
         }

         var2 = AddOp.$Pl.apply2(this.staticLink.temp, srfi37.Lit0);

         int var4;
         try {
            var4 = ((Number)var2).intValue();
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "substring", 2, var2);
         }

         var2 = this.staticLink.arg;

         CharSequence var3;
         try {
            var3 = (CharSequence)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "string-length", 1, var2);
         }

         return strings.substring(var1, var4, strings.stringLength(var3));
      }

      Object lambda19(Object var1) {
         srfi37.frame5 var2 = new srfi37.frame5();
         var2.staticLink = this;
         var2.x = var1;
         return call_with_values.callWithValues(var2.lambda$Fn15, var2.lambda$Fn16);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 15) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 16) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame5 extends ModuleBody {

      final ModuleMethod lambda$Fn15 = new ModuleMethod(this, 13, (Object)null, 0);
      final ModuleMethod lambda$Fn16 = new ModuleMethod(this, 14, (Object)null, 4097);
      srfi37.frame4 staticLink;
      Object x;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 13?this.lambda20():super.apply0(var1);
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 14?this.lambda21(var2):super.apply1(var1, var2);
      }

      Object lambda20() {
         Object var1 = this.staticLink.staticLink.staticLink.lambda2findOption(this.staticLink.x);
         return var1 != Boolean.FALSE?var1:srfi37.option(LList.list1(this.staticLink.x), Boolean.TRUE, Boolean.FALSE, this.staticLink.staticLink.staticLink.unrecognized$Mnoption$Mnproc);
      }

      Object lambda21(Object var1) {
         srfi37.frame6 var2 = new srfi37.frame6();
         var2.staticLink = this;
         var2.x = var1;
         return call_with_values.callWithValues(var2.lambda$Fn17, var2.lambda$Fn18);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 13) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 14) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame6 extends ModuleBody {

      final ModuleMethod lambda$Fn17 = new ModuleMethod(this, 11, (Object)null, 0);
      final ModuleMethod lambda$Fn18 = new ModuleMethod(this, 12, (Object)null, -4096);
      srfi37.frame5 staticLink;
      Object x;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 11?this.lambda22():super.apply0(var1);
      }

      public Object applyN(ModuleMethod var1, Object[] var2) {
         return var1.selector == 12?this.lambda23$V(var2):super.applyN(var1, var2);
      }

      Object lambda22() {
         Apply var2 = Scheme.apply;
         Object var1 = this.x;

         srfi37.Mntype var3;
         try {
            var3 = (srfi37.Mntype)var1;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "option-processor", 0, var1);
         }

         return var2.applyN(new Object[]{srfi37.optionProcessor(var3), this.x, this.staticLink.staticLink.x, this.staticLink.x, this.staticLink.staticLink.staticLink.seeds});
      }

      Object lambda23$V(Object[] var1) {
         LList var2 = LList.makeList(var1, 0);
         return this.staticLink.staticLink.staticLink.staticLink.lambda5scanArgs(this.staticLink.staticLink.staticLink.args, var2);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 11) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
         if(var1.selector == 12) {
            var3.values = var2;
            var3.proc = var1;
            var3.pc = 5;
            return 0;
         } else {
            return super.matchN(var1, var2, var3);
         }
      }
   }
}
