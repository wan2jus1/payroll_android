package gnu.kawa.slib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.slib.pp;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.InPort;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;
import gnu.text.Char;
import gnu.text.Path;
import kawa.lib.characters;
import kawa.lib.lists;
import kawa.lib.ports;
import kawa.lib.rnrs.unicode;
import kawa.standard.Scheme;
import kawa.standard.readchar;

public class ppfile extends ModuleBody {

   public static final ppfile $instance = new ppfile();
   static final Char Lit0 = Char.make(59);
   static final Char Lit1 = Char.make(10);
   static final SimpleSymbol Lit2 = (SimpleSymbol)(new SimpleSymbol("pprint-filter-file")).readResolve();
   static final SimpleSymbol Lit3 = (SimpleSymbol)(new SimpleSymbol("pprint-file")).readResolve();
   static final ModuleMethod lambda$Fn3;
   public static final ModuleMethod pprint$Mnfile;
   public static final ModuleMethod pprint$Mnfilter$Mnfile;


   static {
      ppfile var0 = $instance;
      pprint$Mnfilter$Mnfile = new ModuleMethod(var0, 3, Lit2, -4094);
      ModuleMethod var1 = new ModuleMethod(var0, 4, (Object)null, 4097);
      var1.setProperty("source-location", "ppfile.scm:70");
      lambda$Fn3 = var1;
      pprint$Mnfile = new ModuleMethod(var0, 5, Lit3, 8193);
      $instance.run();
   }

   public ppfile() {
      ModuleInfo.register(this);
   }

   static Object lambda3(Object var0) {
      return var0;
   }

   public static Object pprintFile(Object var0) {
      return pprintFile(var0, ports.current$Mnoutput$Mnport.apply0());
   }

   public static Object pprintFile(Object var0, Object var1) {
      return pprintFilterFile$V(var0, lambda$Fn3, new Object[]{var1});
   }

   public static Object pprintFilterFile$V(Object var0, Object var1, Object[] var2) {
      ppfile.frame var3 = new ppfile.frame();
      var3.filter = var1;
      var3.optarg = LList.makeList(var2, 0);
      ModuleMethod var5 = var3.lambda$Fn1;
      if(ports.isInputPort(var0)) {
         return var3.lambda1(var0);
      } else {
         Path var6;
         try {
            var6 = Path.valueOf(var0);
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "call-with-input-file", 1, var0);
         }

         return ports.callWithInputFile(var6, var5);
      }
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      switch(var1.selector) {
      case 4:
         return lambda3(var2);
      case 5:
         return pprintFile(var2);
      default:
         return super.apply1(var1, var2);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      return var1.selector == 5?pprintFile(var2, var3):super.apply2(var1, var2, var3);
   }

   public Object applyN(ModuleMethod var1, Object[] var2) {
      if(var1.selector != 3) {
         return super.applyN(var1, var2);
      } else {
         Object var6 = var2[0];
         Object var3 = var2[1];
         int var5 = var2.length - 2;
         Object[] var4 = new Object[var5];

         while(true) {
            --var5;
            if(var5 < 0) {
               return pprintFilterFile$V(var6, var3, var4);
            }

            var4[var5] = var2[var5 + 2];
         }
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      switch(var1.selector) {
      case 4:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 5:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      default:
         return super.match1(var1, var2, var3);
      }
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

   public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
      if(var1.selector == 3) {
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

   public class frame extends ModuleBody {

      Object filter;
      final ModuleMethod lambda$Fn1;
      LList optarg;


      public frame() {
         ModuleMethod var1 = new ModuleMethod(this, 2, (Object)null, 4097);
         var1.setProperty("source-location", "ppfile.scm:27");
         this.lambda$Fn1 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 2?this.lambda1(var2):super.apply1(var1, var2);
      }

      Object lambda1(Object var1) {
         ppfile.frame0 var2 = new ppfile.frame0();
         var2.staticLink = this;
         var2.port = var1;
         ModuleMethod var3 = var2.lambda$Fn2;
         if(lists.isNull(this.optarg)) {
            var1 = ports.current$Mnoutput$Mnport.apply0();
         } else {
            var1 = lists.car.apply1(this.optarg);
         }

         if(ports.isOutputPort(var1)) {
            return var2.lambda2(var1);
         } else {
            Path var5;
            try {
               var5 = Path.valueOf(var1);
            } catch (ClassCastException var4) {
               throw new WrongType(var4, "call-with-output-file", 1, var1);
            }

            return ports.callWithOutputFile(var5, var3);
         }
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 2) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame0 extends ModuleBody {

      final ModuleMethod lambda$Fn2;
      Object port;
      ppfile.frame staticLink;


      public frame0() {
         ModuleMethod var1 = new ModuleMethod(this, 1, (Object)null, 4097);
         var1.setProperty("source-location", "ppfile.scm:34");
         this.lambda$Fn2 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 1?this.lambda2(var2):super.apply1(var1, var2);
      }

      Object lambda2(Object var1) {
         Object var2 = readchar.peekChar.apply1(this.port);

         while(true) {
            boolean var5 = ports.isEofObject(var2);
            if(var5) {
               if(var5) {
                  return Boolean.TRUE;
               }

               return Boolean.FALSE;
            }

            Char var3;
            try {
               var3 = (Char)var2;
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "char-whitespace?", 1, var2);
            }

            if(unicode.isCharWhitespace(var3)) {
               ports.display(readchar.readChar.apply1(this.port), var1);
               var2 = readchar.peekChar.apply1(this.port);
            } else {
               var3 = ppfile.Lit0;

               Char var4;
               try {
                  var4 = (Char)var2;
               } catch (ClassCastException var6) {
                  throw new WrongType(var6, "char=?", 2, var2);
               }

               if(!characters.isChar$Eq(var3, var4)) {
                  var2 = this.port;

                  InPort var10;
                  try {
                     var10 = (InPort)var2;
                  } catch (ClassCastException var8) {
                     throw new WrongType(var8, "read", 1, var2);
                  }

                  var2 = ports.read(var10);
                  var5 = ports.isEofObject(var2);
                  if(var5) {
                     if(var5) {
                        return Boolean.TRUE;
                     }

                     return Boolean.FALSE;
                  }

                  pp.prettyPrint(Scheme.applyToArgs.apply2(this.staticLink.filter, var2), var1);
                  Object var11 = readchar.peekChar.apply1(this.port);
                  var2 = var11;
                  if(Scheme.isEqv.apply2(ppfile.Lit1, var11) != Boolean.FALSE) {
                     readchar.readChar.apply1(this.port);
                     var2 = readchar.peekChar.apply1(this.port);
                  }
               } else {
                  while(true) {
                     var5 = ports.isEofObject(var2);
                     if(var5) {
                        if(var5) {
                           return Boolean.TRUE;
                        }

                        return Boolean.FALSE;
                     }

                     var3 = ppfile.Lit1;

                     try {
                        var4 = (Char)var2;
                     } catch (ClassCastException var9) {
                        throw new WrongType(var9, "char=?", 2, var2);
                     }

                     if(characters.isChar$Eq(var3, var4)) {
                        ports.display(readchar.readChar.apply1(this.port), var1);
                        var2 = readchar.peekChar.apply1(this.port);
                        break;
                     }

                     ports.display(readchar.readChar.apply1(this.port), var1);
                     var2 = readchar.peekChar.apply1(this.port);
                  }
               }
            }
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
   }
}
