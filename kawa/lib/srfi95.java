package kawa.lib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.Special;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.ApplyToArgs;
import gnu.kawa.functions.DivideOp;
import gnu.kawa.lispexpr.LangObjType;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.Sequence;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.math.RealNum;
import kawa.lib.lists;
import kawa.lib.numbers;
import kawa.lib.vectors;
import kawa.standard.Scheme;
import kawa.standard.append;

public class srfi95 extends ModuleBody {

   public static final ModuleMethod $Pcsort$Mnlist;
   public static final ModuleMethod $Pcsort$Mnvector;
   public static final ModuleMethod $Pcvector$Mnsort$Ex;
   public static final srfi95 $instance = new srfi95();
   static final IntNum Lit0 = IntNum.make(-1);
   static final IntNum Lit1 = IntNum.make(2);
   static final SimpleSymbol Lit10 = (SimpleSymbol)(new SimpleSymbol("%vector-sort!")).readResolve();
   static final SimpleSymbol Lit11 = (SimpleSymbol)(new SimpleSymbol("%sort-vector")).readResolve();
   static final SimpleSymbol Lit12 = (SimpleSymbol)(new SimpleSymbol("sort")).readResolve();
   static final IntNum Lit2 = IntNum.make(1);
   static final IntNum Lit3 = IntNum.make(0);
   static final SimpleSymbol Lit4 = (SimpleSymbol)(new SimpleSymbol("identity")).readResolve();
   static final SimpleSymbol Lit5 = (SimpleSymbol)(new SimpleSymbol("sorted?")).readResolve();
   static final SimpleSymbol Lit6 = (SimpleSymbol)(new SimpleSymbol("merge")).readResolve();
   static final SimpleSymbol Lit7 = (SimpleSymbol)(new SimpleSymbol("merge!")).readResolve();
   static final SimpleSymbol Lit8 = (SimpleSymbol)(new SimpleSymbol("%sort-list")).readResolve();
   static final SimpleSymbol Lit9 = (SimpleSymbol)(new SimpleSymbol("sort!")).readResolve();
   static final ModuleMethod identity;
   public static final ModuleMethod merge;
   public static final ModuleMethod merge$Ex;
   public static final ModuleMethod sort;
   public static final ModuleMethod sort$Ex;
   public static final ModuleMethod sorted$Qu;


   public static Object $PcSortList(Object var0, Object var1, Object var2) {
      srfi95.frame0 var3 = new srfi95.frame0();
      var3.seq = var0;
      var3.less$Qu = var1;
      var3.keyer = Special.undefined;
      if(var2 != Boolean.FALSE) {
         var0 = lists.car;
      } else {
         var0 = identity;
      }

      var3.keyer = var0;
      LList var9;
      if(var2 == Boolean.FALSE) {
         var0 = var3.seq;

         try {
            var9 = (LList)var0;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "length", 1, var0);
         }

         return var3.lambda2step(Integer.valueOf(lists.length(var9)));
      } else {
         Pair var8;
         for(var0 = var3.seq; !lists.isNull(var0); var0 = lists.cdr.apply1(var0)) {
            try {
               var8 = (Pair)var0;
            } catch (ClassCastException var6) {
               throw new WrongType(var6, "set-car!", 1, var0);
            }

            lists.setCar$Ex(var8, lists.cons(Scheme.applyToArgs.apply2(var2, lists.car.apply1(var0)), lists.car.apply1(var0)));
         }

         var0 = var3.seq;

         try {
            var9 = (LList)var0;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "length", 1, var0);
         }

         var3.seq = var3.lambda2step(Integer.valueOf(lists.length(var9)));

         for(var0 = var3.seq; !lists.isNull(var0); var0 = lists.cdr.apply1(var0)) {
            try {
               var8 = (Pair)var0;
            } catch (ClassCastException var4) {
               throw new WrongType(var4, "set-car!", 1, var0);
            }

            lists.setCar$Ex(var8, lists.cdar.apply1(var0));
         }

         return var3.seq;
      }
   }

   public static void $PcSortVector(Sequence var0, Object var1) {
      $PcSortVector(var0, var1, Boolean.FALSE);
   }

   public static void $PcSortVector(Sequence var0, Object var1, Object var2) {
      FVector var3 = vectors.makeVector(var0.size());
      var1 = $PcSortList(rank$Mn1Array$To$List(var0), var1, var2);

      for(Object var6 = Lit3; !lists.isNull(var1); var6 = AddOp.$Pl.apply2(var6, Lit2)) {
         int var4;
         try {
            var4 = ((Number)var6).intValue();
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "vector-set!", 2, var6);
         }

         vectors.vectorSet$Ex(var3, var4, lists.car.apply1(var1));
         var1 = lists.cdr.apply1(var1);
      }

   }

   public static Object $PcVectorSort$Ex(Sequence var0, Object var1, Object var2) {
      var2 = $PcSortList(rank$Mn1Array$To$List(var0), var1, var2);

      for(var1 = Lit3; !lists.isNull(var2); var1 = AddOp.$Pl.apply2(var1, Lit2)) {
         var0.set(((Number)var1).intValue(), lists.car.apply1(var2));
         var2 = lists.cdr.apply1(var2);
      }

      return var0;
   }

   static {
      srfi95 var0 = $instance;
      identity = new ModuleMethod(var0, 1, Lit4, 4097);
      sorted$Qu = new ModuleMethod(var0, 2, Lit5, 12290);
      merge = new ModuleMethod(var0, 4, Lit6, 16387);
      merge$Ex = new ModuleMethod(var0, 6, Lit7, 16387);
      $Pcsort$Mnlist = new ModuleMethod(var0, 8, Lit8, 12291);
      sort$Ex = new ModuleMethod(var0, 9, Lit9, 12290);
      $Pcvector$Mnsort$Ex = new ModuleMethod(var0, 11, Lit10, 12291);
      $Pcsort$Mnvector = new ModuleMethod(var0, 12, Lit11, 12290);
      sort = new ModuleMethod(var0, 14, Lit12, 12290);
      $instance.run();
   }

   public srfi95() {
      ModuleInfo.register(this);
   }

   static Object identity(Object var0) {
      return var0;
   }

   public static Object isSorted(Object var0, Object var1) {
      return isSorted(var0, var1, identity);
   }

   public static Object isSorted(Object var0, Object var1, Object var2) {
      if(lists.isNull(var0)) {
         return Boolean.TRUE;
      } else {
         Object var3;
         Object var4;
         boolean var8;
         int var17;
         if(var0 instanceof Sequence) {
            Sequence var15;
            try {
               var15 = (Sequence)var0;
            } catch (ClassCastException var11) {
               throw new WrongType(var11, "arr", -2, var0);
            }

            int var7 = var15.size() - 1;
            boolean var16;
            if(var7 <= 1) {
               var16 = true;
            } else {
               var16 = false;
            }

            if(var16) {
               return var16?Boolean.TRUE:Boolean.FALSE;
            } else {
               var0 = Integer.valueOf(var7 - 1);
               var3 = Scheme.applyToArgs.apply2(var2, var15.get(var7));

               while(true) {
                  RealNum var13;
                  try {
                     var13 = LangObjType.coerceRealNum(var0);
                  } catch (ClassCastException var9) {
                     throw new WrongType(var9, "negative?", 1, var0);
                  }

                  var8 = numbers.isNegative(var13);
                  if(var8) {
                     if(var8) {
                        return Boolean.TRUE;
                     }

                     return Boolean.FALSE;
                  }

                  ApplyToArgs var14 = Scheme.applyToArgs;

                  try {
                     var17 = ((Number)var0).intValue();
                  } catch (ClassCastException var10) {
                     throw new WrongType(var10, "gnu.lists.Sequence.get(int)", 2, var0);
                  }

                  var4 = var14.apply2(var2, var15.get(var17));
                  var3 = Scheme.applyToArgs.apply3(var1, var4, var3);
                  if(var3 == Boolean.FALSE) {
                     return var3;
                  }

                  var0 = AddOp.$Pl.apply2(Lit0, var0);
                  var3 = var4;
               }
            }
         } else if(lists.isNull(lists.cdr.apply1(var0))) {
            return Boolean.TRUE;
         } else {
            var3 = Scheme.applyToArgs.apply2(var2, lists.car.apply1(var0));
            var0 = lists.cdr.apply1(var0);

            while(true) {
               var8 = lists.isNull(var0);
               if(var8) {
                  return var8?Boolean.TRUE:Boolean.FALSE;
               }

               var4 = Scheme.applyToArgs.apply2(var2, lists.car.apply1(var0));
               var3 = Scheme.applyToArgs.apply3(var1, var4, var3);

               Boolean var5;
               try {
                  var5 = Boolean.FALSE;
               } catch (ClassCastException var12) {
                  throw new WrongType(var12, "x", -2, var3);
               }

               byte var6;
               if(var3 != var5) {
                  var6 = 1;
               } else {
                  var6 = 0;
               }

               var17 = var6 + 1 & 1;
               if(var17 == 0) {
                  if(var17 != 0) {
                     return Boolean.TRUE;
                  }

                  return Boolean.FALSE;
               }

               var0 = lists.cdr.apply1(var0);
               var3 = var4;
            }
         }
      }
   }

   public static Object merge(Object var0, Object var1, Object var2) {
      return merge(var0, var1, var2, identity);
   }

   public static Object merge(Object var0, Object var1, Object var2, Object var3) {
      srfi95.frame var4 = new srfi95.frame();
      var4.less$Qu = var2;
      var4.key = var3;
      return lists.isNull(var0)?var1:(lists.isNull(var1)?var0:var4.lambda1loop(lists.car.apply1(var0), Scheme.applyToArgs.apply2(var4.key, lists.car.apply1(var0)), lists.cdr.apply1(var0), lists.car.apply1(var1), Scheme.applyToArgs.apply2(var4.key, lists.car.apply1(var1)), lists.cdr.apply1(var1)));
   }

   public static Object merge$Ex(Object var0, Object var1, Object var2) {
      return merge$Ex(var0, var1, var2, identity);
   }

   public static Object merge$Ex(Object var0, Object var1, Object var2, Object var3) {
      return sort$ClMerge$Ex(var0, var1, var2, var3);
   }

   static Object rank$Mn1Array$To$List(Sequence var0) {
      int var2 = var0.size() - 1;

      Object var1;
      for(var1 = LList.Empty; var2 >= 0; --var2) {
         var1 = lists.cons(var0.get(var2), var1);
      }

      return var1;
   }

   public static Object sort(Sequence var0, Object var1) {
      return sort(var0, var1, Boolean.FALSE);
   }

   public static Object sort(Sequence var0, Object var1, Object var2) {
      if(lists.isList(var0)) {
         return $PcSortList(append.append$V(new Object[]{var0, LList.Empty}), var1, var2);
      } else {
         $PcSortVector(var0, var1, var2);
         return Values.empty;
      }
   }

   static Object sort$ClMerge$Ex(Object var0, Object var1, Object var2, Object var3) {
      srfi95.frame1 var4 = new srfi95.frame1();
      var4.less$Qu = var2;
      var4.key = var3;
      if(lists.isNull(var0)) {
         return var1;
      } else if(lists.isNull(var1)) {
         return var0;
      } else {
         var2 = Scheme.applyToArgs.apply2(var4.key, lists.car.apply1(var0));
         var3 = Scheme.applyToArgs.apply2(var4.key, lists.car.apply1(var1));
         Pair var7;
         if(Scheme.applyToArgs.apply3(var4.less$Qu, var3, var2) != Boolean.FALSE) {
            if(lists.isNull(lists.cdr.apply1(var1))) {
               try {
                  var7 = (Pair)var1;
               } catch (ClassCastException var5) {
                  throw new WrongType(var5, "set-cdr!", 1, var1);
               }

               lists.setCdr$Ex(var7, var0);
               return var1;
            } else {
               var4.lambda3loop(var1, var0, var2, lists.cdr.apply1(var1), Scheme.applyToArgs.apply2(var4.key, lists.cadr.apply1(var1)));
               return var1;
            }
         } else {
            if(lists.isNull(lists.cdr.apply1(var0))) {
               try {
                  var7 = (Pair)var0;
               } catch (ClassCastException var6) {
                  throw new WrongType(var6, "set-cdr!", 1, var0);
               }

               lists.setCdr$Ex(var7, var1);
            } else {
               var4.lambda3loop(var0, lists.cdr.apply1(var0), Scheme.applyToArgs.apply2(var4.key, lists.cadr.apply1(var0)), var1, var3);
            }

            return var0;
         }
      }
   }

   public static Object sort$Ex(Sequence var0, Object var1) {
      return sort$Ex(var0, var1, Boolean.FALSE);
   }

   public static Object sort$Ex(Sequence var0, Object var1, Object var2) {
      if(!lists.isList(var0)) {
         return $PcVectorSort$Ex(var0, var1, var2);
      } else {
         var2 = $PcSortList(var0, var1, var2);
         if(var2 != var0) {
            for(var1 = var2; lists.cdr.apply1(var1) != var0; var1 = lists.cdr.apply1(var1)) {
               ;
            }

            Pair var3;
            try {
               var3 = (Pair)var1;
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "set-cdr!", 1, var1);
            }

            lists.setCdr$Ex(var3, var2);
            Object var10 = lists.car.apply1(var0);
            var1 = lists.cdr.apply1(var0);

            Pair var4;
            try {
               var4 = (Pair)var0;
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "set-car!", 1, var0);
            }

            lists.setCar$Ex(var4, lists.car.apply1(var2));

            try {
               var4 = (Pair)var0;
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "set-cdr!", 1, var0);
            }

            lists.setCdr$Ex(var4, lists.cdr.apply1(var2));

            try {
               var4 = (Pair)var2;
            } catch (ClassCastException var6) {
               throw new WrongType(var6, "set-car!", 1, var2);
            }

            lists.setCar$Ex(var4, var10);

            try {
               var3 = (Pair)var2;
            } catch (ClassCastException var5) {
               throw new WrongType(var5, "set-cdr!", 1, var2);
            }

            lists.setCdr$Ex(var3, var1);
         }

         return var0;
      }
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      return var1.selector == 1?identity(var2):super.apply1(var1, var2);
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      Sequence var7;
      switch(var1.selector) {
      case 2:
         return isSorted(var2, var3);
      case 9:
         try {
            var7 = (Sequence)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "sort!", 1, var2);
         }

         return sort$Ex(var7, var3);
      case 12:
         try {
            var7 = (Sequence)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "%sort-vector", 1, var2);
         }

         $PcSortVector(var7, var3);
         return Values.empty;
      case 14:
         try {
            var7 = (Sequence)var2;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "sort", 1, var2);
         }

         return sort(var7, var3);
      default:
         return super.apply2(var1, var2, var3);
      }
   }

   public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
      Sequence var9;
      switch(var1.selector) {
      case 2:
         return isSorted(var2, var3, var4);
      case 3:
      case 5:
      case 7:
      case 10:
      case 13:
      default:
         return super.apply3(var1, var2, var3, var4);
      case 4:
         return merge(var2, var3, var4);
      case 6:
         return merge$Ex(var2, var3, var4);
      case 8:
         return $PcSortList(var2, var3, var4);
      case 9:
         try {
            var9 = (Sequence)var2;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "sort!", 1, var2);
         }

         return sort$Ex(var9, var3, var4);
      case 11:
         try {
            var9 = (Sequence)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "%vector-sort!", 1, var2);
         }

         return $PcVectorSort$Ex(var9, var3, var4);
      case 12:
         try {
            var9 = (Sequence)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "%sort-vector", 1, var2);
         }

         $PcSortVector(var9, var3, var4);
         return Values.empty;
      case 14:
         try {
            var9 = (Sequence)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "sort", 1, var2);
         }

         return sort(var9, var3, var4);
      }
   }

   public Object apply4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5) {
      switch(var1.selector) {
      case 4:
         return merge(var2, var3, var4, var5);
      case 5:
      default:
         return super.apply4(var1, var2, var3, var4, var5);
      case 6:
         return merge$Ex(var2, var3, var4, var5);
      }
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

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      int var5 = -786431;
      switch(var1.selector) {
      case 2:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 9:
         if(var2 instanceof Sequence) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 12:
         if(var2 instanceof Sequence) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 14:
         if(var2 instanceof Sequence) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      default:
         var5 = super.match2(var1, var2, var3, var4);
      }

      return var5;
   }

   public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
      int var6 = -786431;
      switch(var1.selector) {
      case 2:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 3:
      case 5:
      case 7:
      case 10:
      case 13:
      default:
         var6 = super.match3(var1, var2, var3, var4, var5);
         break;
      case 4:
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
      case 8:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 9:
         if(var2 instanceof Sequence) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }
         break;
      case 11:
         if(var2 instanceof Sequence) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }
         break;
      case 12:
         if(var2 instanceof Sequence) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }
         break;
      case 14:
         if(var2 instanceof Sequence) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }
      }

      return var6;
   }

   public int match4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5, CallContext var6) {
      switch(var1.selector) {
      case 4:
         var6.value1 = var2;
         var6.value2 = var3;
         var6.value3 = var4;
         var6.value4 = var5;
         var6.proc = var1;
         var6.pc = 4;
         return 0;
      case 5:
      default:
         return super.match4(var1, var2, var3, var4, var5, var6);
      case 6:
         var6.value1 = var2;
         var6.value2 = var3;
         var6.value3 = var4;
         var6.value4 = var5;
         var6.proc = var1;
         var6.pc = 4;
         return 0;
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }

   public class frame extends ModuleBody {

      Object key;
      Object less$Qu;


      public Object lambda1loop(Object var1, Object var2, Object var3, Object var4, Object var5, Object var6) {
         return Scheme.applyToArgs.apply3(this.less$Qu, var5, var2) != Boolean.FALSE?(lists.isNull(var6)?lists.cons(var4, lists.cons(var1, var3)):lists.cons(var4, this.lambda1loop(var1, var2, var3, lists.car.apply1(var6), Scheme.applyToArgs.apply2(this.key, lists.car.apply1(var6)), lists.cdr.apply1(var6)))):(lists.isNull(var3)?lists.cons(var1, lists.cons(var4, var6)):lists.cons(var1, this.lambda1loop(lists.car.apply1(var3), Scheme.applyToArgs.apply2(this.key, lists.car.apply1(var3)), lists.cdr.apply1(var3), var4, var5, var6)));
      }
   }

   public class frame0 extends ModuleBody {

      Object keyer;
      Object less$Qu;
      Object seq;


      public Object lambda2step(Object var1) {
         Object var9;
         if(Scheme.numGrt.apply2(var1, srfi95.Lit1) != Boolean.FALSE) {
            var9 = DivideOp.quotient.apply2(var1, srfi95.Lit1);
            return srfi95.sort$ClMerge$Ex(this.lambda2step(var9), this.lambda2step(AddOp.$Mn.apply2(var1, var9)), this.less$Qu, this.keyer);
         } else if(Scheme.numEqu.apply2(var1, srfi95.Lit1) != Boolean.FALSE) {
            var9 = lists.car.apply1(this.seq);
            Object var3 = lists.cadr.apply1(this.seq);
            var1 = this.seq;
            this.seq = lists.cddr.apply1(this.seq);
            if(Scheme.applyToArgs.apply3(this.less$Qu, Scheme.applyToArgs.apply2(this.keyer, var3), Scheme.applyToArgs.apply2(this.keyer, var9)) != Boolean.FALSE) {
               Pair var4;
               try {
                  var4 = (Pair)var1;
               } catch (ClassCastException var7) {
                  throw new WrongType(var7, "set-car!", 1, var1);
               }

               lists.setCar$Ex(var4, var3);
               var3 = lists.cdr.apply1(var1);

               try {
                  var4 = (Pair)var3;
               } catch (ClassCastException var6) {
                  throw new WrongType(var6, "set-car!", 1, var3);
               }

               lists.setCar$Ex(var4, var9);
            }

            var9 = lists.cdr.apply1(var1);

            Pair var10;
            try {
               var10 = (Pair)var9;
            } catch (ClassCastException var5) {
               throw new WrongType(var5, "set-cdr!", 1, var9);
            }

            lists.setCdr$Ex(var10, LList.Empty);
            return var1;
         } else if(Scheme.numEqu.apply2(var1, srfi95.Lit2) != Boolean.FALSE) {
            var1 = this.seq;
            this.seq = lists.cdr.apply1(this.seq);

            Pair var2;
            try {
               var2 = (Pair)var1;
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "set-cdr!", 1, var1);
            }

            lists.setCdr$Ex(var2, LList.Empty);
            return var1;
         } else {
            return LList.Empty;
         }
      }
   }

   public class frame1 extends ModuleBody {

      Object key;
      Object less$Qu;


      public Object lambda3loop(Object var1, Object var2, Object var3, Object var4, Object var5) {
         Pair var10;
         if(Scheme.applyToArgs.apply3(this.less$Qu, var5, var3) != Boolean.FALSE) {
            Pair var12;
            try {
               var12 = (Pair)var1;
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "set-cdr!", 1, var1);
            }

            lists.setCdr$Ex(var12, var4);
            if(lists.isNull(lists.cdr.apply1(var4))) {
               try {
                  var10 = (Pair)var4;
               } catch (ClassCastException var6) {
                  throw new WrongType(var6, "set-cdr!", 1, var4);
               }

               lists.setCdr$Ex(var10, var2);
               return Values.empty;
            } else {
               return this.lambda3loop(var4, var2, var3, lists.cdr.apply1(var4), Scheme.applyToArgs.apply2(this.key, lists.cadr.apply1(var4)));
            }
         } else {
            Pair var11;
            try {
               var11 = (Pair)var1;
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "set-cdr!", 1, var1);
            }

            lists.setCdr$Ex(var11, var2);
            if(lists.isNull(lists.cdr.apply1(var2))) {
               try {
                  var10 = (Pair)var2;
               } catch (ClassCastException var8) {
                  throw new WrongType(var8, "set-cdr!", 1, var2);
               }

               lists.setCdr$Ex(var10, var4);
               return Values.empty;
            } else {
               return this.lambda3loop(var2, lists.cdr.apply1(var2), Scheme.applyToArgs.apply2(this.key, lists.cadr.apply1(var2)), var4, var5);
            }
         }
      }
   }
}
