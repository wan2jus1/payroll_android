package gnu.kawa.slib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.lispexpr.ReadTable;
import gnu.kawa.lispexpr.ReadTableEntry;
import gnu.kawa.lispexpr.ReaderDispatch;
import gnu.kawa.lispexpr.ReaderDispatchMacro;
import gnu.kawa.lispexpr.ReaderMacro;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.text.Char;

public class readtable extends ModuleBody {

   public static final readtable $instance = new readtable();
   static final SimpleSymbol Lit0 = (SimpleSymbol)(new SimpleSymbol("current-readtable")).readResolve();
   static final SimpleSymbol Lit1 = (SimpleSymbol)(new SimpleSymbol("readtable?")).readResolve();
   static final SimpleSymbol Lit2 = (SimpleSymbol)(new SimpleSymbol("set-macro-character")).readResolve();
   static final SimpleSymbol Lit3 = (SimpleSymbol)(new SimpleSymbol("make-dispatch-macro-character")).readResolve();
   static final SimpleSymbol Lit4 = (SimpleSymbol)(new SimpleSymbol("set-dispatch-macro-character")).readResolve();
   static final SimpleSymbol Lit5 = (SimpleSymbol)(new SimpleSymbol("get-dispatch-macro-table")).readResolve();
   static final SimpleSymbol Lit6 = (SimpleSymbol)(new SimpleSymbol("define-reader-ctor")).readResolve();
   public static final ModuleMethod current$Mnreadtable;
   public static final ModuleMethod define$Mnreader$Mnctor;
   public static final ModuleMethod get$Mndispatch$Mnmacro$Mntable;
   public static final ModuleMethod make$Mndispatch$Mnmacro$Mncharacter;
   public static final ModuleMethod readtable$Qu;
   public static final ModuleMethod set$Mndispatch$Mnmacro$Mncharacter;
   public static final ModuleMethod set$Mnmacro$Mncharacter;


   static {
      readtable var0 = $instance;
      current$Mnreadtable = new ModuleMethod(var0, 1, Lit0, 0);
      readtable$Qu = new ModuleMethod(var0, 2, Lit1, 4097);
      set$Mnmacro$Mncharacter = new ModuleMethod(var0, 3, Lit2, 16386);
      make$Mndispatch$Mnmacro$Mncharacter = new ModuleMethod(var0, 6, Lit3, 12289);
      set$Mndispatch$Mnmacro$Mncharacter = new ModuleMethod(var0, 9, Lit4, 16387);
      get$Mndispatch$Mnmacro$Mntable = new ModuleMethod(var0, 11, Lit5, 12290);
      define$Mnreader$Mnctor = new ModuleMethod(var0, 13, Lit6, 12290);
      $instance.run();
   }

   public readtable() {
      ModuleInfo.register(this);
   }

   public static ReadTable currentReadtable() {
      return ReadTable.getCurrent();
   }

   public static void defineReaderCtor(Symbol var0, Procedure var1) {
      defineReaderCtor(var0, var1, currentReadtable());
   }

   public static void defineReaderCtor(Symbol var0, Procedure var1, ReadTable var2) {
      String var3;
      if(var0 == null) {
         var3 = null;
      } else {
         var3 = var0.toString();
      }

      var2.putReaderCtor(var3, (Procedure)var1);
   }

   public static Object getDispatchMacroTable(char var0, char var1) {
      return getDispatchMacroTable(var0, var1, currentReadtable());
   }

   public static Object getDispatchMacroTable(char var0, char var1, ReadTable var2) {
      ReadTableEntry var5 = var2.lookup(var0);

      ReaderDispatch var3;
      try {
         var3 = (ReaderDispatch)var5;
      } catch (ClassCastException var4) {
         throw new WrongType(var4, "disp-entry", -2, var5);
      }

      ReadTableEntry var7 = var3.lookup(var1);
      Object var6 = var7;
      if(var7 == null) {
         var6 = Boolean.FALSE;
      }

      return var6;
   }

   public static boolean isReadtable(Object var0) {
      return var0 instanceof ReadTable;
   }

   public static void makeDispatchMacroCharacter(char var0) {
      makeDispatchMacroCharacter(var0, false);
   }

   public static void makeDispatchMacroCharacter(char var0, boolean var1) {
      makeDispatchMacroCharacter(var0, var1, currentReadtable());
   }

   public static void makeDispatchMacroCharacter(char var0, boolean var1, ReadTable var2) {
      var2.set(var0, new ReaderDispatch(var1));
   }

   public static void setDispatchMacroCharacter(char var0, char var1, Object var2) {
      setDispatchMacroCharacter(var0, var1, var2, currentReadtable());
   }

   public static void setDispatchMacroCharacter(char var0, char var1, Object var2, ReadTable var3) {
      ReadTableEntry var7 = var3.lookup(var0);

      ReaderDispatch var4;
      try {
         var4 = (ReaderDispatch)var7;
      } catch (ClassCastException var6) {
         throw new WrongType(var6, "entry", -2, var7);
      }

      Procedure var8;
      try {
         var8 = (Procedure)var2;
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "gnu.kawa.lispexpr.ReaderDispatchMacro.<init>(gnu.mapping.Procedure)", 1, var2);
      }

      var4.set(var1, new ReaderDispatchMacro(var8));
   }

   public static void setMacroCharacter(char var0, Object var1) {
      setMacroCharacter(var0, var1, false);
   }

   public static void setMacroCharacter(char var0, Object var1, boolean var2) {
      setMacroCharacter(var0, var1, var2, currentReadtable());
   }

   public static void setMacroCharacter(char var0, Object var1, boolean var2, ReadTable var3) {
      Procedure var4;
      try {
         var4 = (Procedure)var1;
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "gnu.kawa.lispexpr.ReaderMacro.<init>(gnu.mapping.Procedure,boolean)", 1, var1);
      }

      var3.set(var0, new ReaderMacro(var4, var2));
   }

   public Object apply0(ModuleMethod var1) {
      return var1.selector == 1?currentReadtable():super.apply0(var1);
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      switch(var1.selector) {
      case 2:
         if(isReadtable(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 6:
         char var3;
         try {
            var3 = ((Char)var2).charValue();
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "make-dispatch-macro-character", 1, var2);
         }

         makeDispatchMacroCharacter(var3);
         return Values.empty;
      default:
         return super.apply1(var1, var2);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      char var4;
      switch(var1.selector) {
      case 3:
         try {
            var4 = ((Char)var2).charValue();
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "set-macro-character", 1, var2);
         }

         setMacroCharacter(var4, var3);
         return Values.empty;
      case 6:
         try {
            var4 = ((Char)var2).charValue();
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "make-dispatch-macro-character", 1, var2);
         }

         Boolean var15;
         try {
            var15 = Boolean.FALSE;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "make-dispatch-macro-character", 2, var3);
         }

         boolean var6;
         if(var3 != var15) {
            var6 = true;
         } else {
            var6 = false;
         }

         makeDispatchMacroCharacter(var4, var6);
         return Values.empty;
      case 11:
         try {
            var4 = ((Char)var2).charValue();
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "get-dispatch-macro-table", 1, var2);
         }

         char var5;
         try {
            var5 = ((Char)var3).charValue();
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "get-dispatch-macro-table", 2, var3);
         }

         return getDispatchMacroTable(var4, var5);
      case 13:
         Symbol var14;
         try {
            var14 = (Symbol)var2;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "define-reader-ctor", 1, var2);
         }

         Procedure var16;
         try {
            var16 = (Procedure)var3;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "define-reader-ctor", 2, var3);
         }

         defineReaderCtor(var14, var16);
         return Values.empty;
      default:
         return super.apply2(var1, var2, var3);
      }
   }

   public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
      boolean var7 = true;
      char var5;
      char var6;
      Boolean var23;
      ReadTable var22;
      switch(var1.selector) {
      case 3:
         try {
            var5 = ((Char)var2).charValue();
         } catch (ClassCastException var20) {
            throw new WrongType(var20, "set-macro-character", 1, var2);
         }

         try {
            var23 = Boolean.FALSE;
         } catch (ClassCastException var19) {
            throw new WrongType(var19, "set-macro-character", 3, var4);
         }

         if(var4 != var23) {
            var7 = true;
         } else {
            var7 = false;
         }

         setMacroCharacter(var5, var3, var7);
         return Values.empty;
      case 4:
      case 5:
      case 7:
      case 8:
      case 10:
      case 12:
      default:
         return super.apply3(var1, var2, var3, var4);
      case 6:
         try {
            var5 = ((Char)var2).charValue();
         } catch (ClassCastException var18) {
            throw new WrongType(var18, "make-dispatch-macro-character", 1, var2);
         }

         try {
            var23 = Boolean.FALSE;
         } catch (ClassCastException var17) {
            throw new WrongType(var17, "make-dispatch-macro-character", 2, var3);
         }

         if(var3 == var23) {
            var7 = false;
         }

         try {
            var22 = (ReadTable)var4;
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "make-dispatch-macro-character", 3, var4);
         }

         makeDispatchMacroCharacter(var5, var7, var22);
         return Values.empty;
      case 9:
         try {
            var5 = ((Char)var2).charValue();
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "set-dispatch-macro-character", 1, var2);
         }

         try {
            var6 = ((Char)var3).charValue();
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "set-dispatch-macro-character", 2, var3);
         }

         setDispatchMacroCharacter(var5, var6, var4);
         return Values.empty;
      case 11:
         try {
            var5 = ((Char)var2).charValue();
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "get-dispatch-macro-table", 1, var2);
         }

         try {
            var6 = ((Char)var3).charValue();
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "get-dispatch-macro-table", 2, var3);
         }

         try {
            var22 = (ReadTable)var4;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "get-dispatch-macro-table", 3, var4);
         }

         return getDispatchMacroTable(var5, var6, var22);
      case 13:
         Symbol var21;
         try {
            var21 = (Symbol)var2;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "define-reader-ctor", 1, var2);
         }

         Procedure var24;
         try {
            var24 = (Procedure)var3;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "define-reader-ctor", 2, var3);
         }

         ReadTable var25;
         try {
            var25 = (ReadTable)var4;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "define-reader-ctor", 3, var4);
         }

         defineReaderCtor(var21, var24, var25);
         return Values.empty;
      }
   }

   public Object apply4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5) {
      char var6;
      ReadTable var15;
      switch(var1.selector) {
      case 3:
         try {
            var6 = ((Char)var2).charValue();
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "set-macro-character", 1, var2);
         }

         Boolean var16;
         try {
            var16 = Boolean.FALSE;
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "set-macro-character", 3, var4);
         }

         boolean var8;
         if(var4 != var16) {
            var8 = true;
         } else {
            var8 = false;
         }

         try {
            var15 = (ReadTable)var5;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "set-macro-character", 4, var5);
         }

         setMacroCharacter(var6, var3, var8, var15);
         return Values.empty;
      case 9:
         try {
            var6 = ((Char)var2).charValue();
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "set-dispatch-macro-character", 1, var2);
         }

         char var7;
         try {
            var7 = ((Char)var3).charValue();
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "set-dispatch-macro-character", 2, var3);
         }

         try {
            var15 = (ReadTable)var5;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "set-dispatch-macro-character", 4, var5);
         }

         setDispatchMacroCharacter(var6, var7, var4, var15);
         return Values.empty;
      default:
         return super.apply4(var1, var2, var3, var4, var5);
      }
   }

   public int match0(ModuleMethod var1, CallContext var2) {
      if(var1.selector == 1) {
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      } else {
         return super.match0(var1, var2);
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      switch(var1.selector) {
      case 2:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 6:
         if(var2 instanceof Char) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      default:
         return super.match1(var1, var2, var3);
      }
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      int var5 = -786431;
      switch(var1.selector) {
      case 3:
         if(var2 instanceof Char) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 6:
         if(var2 instanceof Char) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 11:
         if(var2 instanceof Char) {
            var4.value1 = var2;
            if(var3 instanceof Char) {
               var4.value2 = var3;
               var4.proc = var1;
               var4.pc = 2;
               return 0;
            }

            return -786430;
         }
         break;
      case 13:
         if(var2 instanceof Symbol) {
            var4.value1 = var2;
            if(!(var3 instanceof Procedure)) {
               return -786430;
            }

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
      case 3:
         if(var2 instanceof Char) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }
         break;
      case 4:
      case 5:
      case 7:
      case 8:
      case 10:
      case 12:
      default:
         var6 = super.match3(var1, var2, var3, var4, var5);
         break;
      case 6:
         if(var2 instanceof Char) {
            var5.value1 = var2;
            var5.value2 = var3;
            if(!(var4 instanceof ReadTable)) {
               return -786429;
            }

            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }
         break;
      case 9:
         if(var2 instanceof Char) {
            var5.value1 = var2;
            if(var3 instanceof Char) {
               var5.value2 = var3;
               var5.value3 = var4;
               var5.proc = var1;
               var5.pc = 3;
               return 0;
            }

            return -786430;
         }
         break;
      case 11:
         if(var2 instanceof Char) {
            var5.value1 = var2;
            if(var3 instanceof Char) {
               var5.value2 = var3;
               if(!(var4 instanceof ReadTable)) {
                  return -786429;
               }

               var5.value3 = var4;
               var5.proc = var1;
               var5.pc = 3;
               return 0;
            }

            return -786430;
         }
         break;
      case 13:
         if(var2 instanceof Symbol) {
            var5.value1 = var2;
            if(!(var3 instanceof Procedure)) {
               return -786430;
            }

            var5.value2 = var3;
            if(!(var4 instanceof ReadTable)) {
               return -786429;
            }

            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }
      }

      return var6;
   }

   public int match4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5, CallContext var6) {
      int var7 = -786428;
      switch(var1.selector) {
      case 3:
         if(!(var2 instanceof Char)) {
            return -786431;
         }

         var6.value1 = var2;
         var6.value2 = var3;
         var6.value3 = var4;
         if(var5 instanceof ReadTable) {
            var6.value4 = var5;
            var6.proc = var1;
            var6.pc = 4;
            return 0;
         }
         break;
      case 9:
         if(!(var2 instanceof Char)) {
            return -786431;
         }

         var6.value1 = var2;
         if(!(var3 instanceof Char)) {
            return -786430;
         }

         var6.value2 = var3;
         var6.value3 = var4;
         if(var5 instanceof ReadTable) {
            var6.value4 = var5;
            var6.proc = var1;
            var6.pc = 4;
            return 0;
         }
         break;
      default:
         var7 = super.match4(var1, var2, var3, var4, var5, var6);
      }

      return var7;
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }
}
