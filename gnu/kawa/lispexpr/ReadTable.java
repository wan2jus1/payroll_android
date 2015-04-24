package gnu.kawa.lispexpr;

import gnu.bytecode.Type;
import gnu.expr.Language;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.lispexpr.ReadTableEntry;
import gnu.kawa.lispexpr.ReaderColon;
import gnu.kawa.lispexpr.ReaderDispatch;
import gnu.kawa.lispexpr.ReaderIgnoreRestOfLine;
import gnu.kawa.lispexpr.ReaderParens;
import gnu.kawa.lispexpr.ReaderQuote;
import gnu.kawa.lispexpr.ReaderString;
import gnu.kawa.lispexpr.ReaderTypespec;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.kawa.util.RangeTable;
import gnu.mapping.Environment;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.mapping.ThreadLocation;

public class ReadTable extends RangeTable {

   public static final int CONSTITUENT = 2;
   public static final int ILLEGAL = 0;
   public static final int MULTIPLE_ESCAPE = 4;
   public static final int NON_TERMINATING_MACRO = 6;
   public static final int SINGLE_ESCAPE = 3;
   public static final int TERMINATING_MACRO = 5;
   public static final int WHITESPACE = 1;
   static final ThreadLocation current = new ThreadLocation("read-table");
   public static int defaultBracketMode = -1;
   Environment ctorTable = null;
   protected boolean finalColonIsKeyword;
   protected boolean hexEscapeAfterBackslash = true;
   protected boolean initialColonIsKeyword;
   public char postfixLookupOperator = '\uffff';


   public static ReadTable createInitial() {
      ReadTable var0 = new ReadTable();
      var0.initialize();
      return var0;
   }

   public static ReadTable getCurrent() {
      ReadTable var1 = (ReadTable)current.get((Object)null);
      ReadTable var0 = var1;
      if(var1 == null) {
         Language var2 = Language.getDefaultLanguage();
         if(var2 instanceof LispLanguage) {
            var0 = ((LispLanguage)var2).defaultReadTable;
         } else {
            var0 = createInitial();
         }

         current.set(var0);
      }

      return var0;
   }

   public static void setCurrent(ReadTable var0) {
      current.set(var0);
   }

   public Object getReaderCtor(String var1) {
      synchronized(this){}

      Object var4;
      try {
         this.initCtorTable();
         var4 = this.ctorTable.get((String)var1, (Object)null);
      } finally {
         ;
      }

      return var4;
   }

   void initCtorTable() {
      if(this.ctorTable == null) {
         this.ctorTable = Environment.make();
      }

   }

   public void initialize() {
      ReadTableEntry var1 = ReadTableEntry.getWhitespaceInstance();
      this.set(32, var1);
      this.set(9, var1);
      this.set(10, var1);
      this.set(13, var1);
      this.set(12, var1);
      this.set(124, ReadTableEntry.getMultipleEscapeInstance());
      this.set(92, ReadTableEntry.getSingleEscapeInstance());
      this.set(48, 57, ReadTableEntry.getDigitInstance());
      var1 = ReadTableEntry.getConstituentInstance();
      this.set(97, 122, var1);
      this.set(65, 90, var1);
      this.set(33, var1);
      this.set(36, var1);
      this.set(37, var1);
      this.set(38, var1);
      this.set(42, var1);
      this.set(43, var1);
      this.set(45, var1);
      this.set(46, var1);
      this.set(47, var1);
      this.set(61, var1);
      this.set(62, var1);
      this.set(63, var1);
      this.set(64, var1);
      this.set(94, var1);
      this.set(95, var1);
      this.set(123, ReadTableEntry.brace);
      this.set(126, var1);
      this.set(127, var1);
      this.set(8, var1);
      this.set(58, new ReaderColon());
      this.set(34, new ReaderString());
      this.set(35, ReaderDispatch.create(this));
      this.set(59, ReaderIgnoreRestOfLine.getInstance());
      this.set(40, ReaderParens.getInstance('(', ')'));
      this.set(39, new ReaderQuote(this.makeSymbol("quote")));
      this.set(96, new ReaderQuote(this.makeSymbol("quasiquote")));
      this.set(44, new ReaderQuote(this.makeSymbol("unquote"), '@', this.makeSymbol("unquote-splicing")));
      this.setBracketMode();
   }

   public ReadTableEntry lookup(int var1) {
      ReadTableEntry var3 = (ReadTableEntry)this.lookup(var1, (Object)null);
      ReadTableEntry var2 = var3;
      if(var3 == null) {
         var2 = var3;
         if(var1 >= 0) {
            var2 = var3;
            if(var1 < 65536) {
               if(Character.isDigit((char)var1)) {
                  var2 = (ReadTableEntry)this.lookup(48, (Object)null);
               } else if(Character.isLowerCase((char)var1)) {
                  var2 = (ReadTableEntry)this.lookup(97, (Object)null);
               } else if(Character.isLetter((char)var1)) {
                  var2 = (ReadTableEntry)this.lookup(65, (Object)null);
               } else {
                  var2 = var3;
                  if(Character.isWhitespace((char)var1)) {
                     var2 = (ReadTableEntry)this.lookup(32, (Object)null);
                  }
               }

               var3 = var2;
               if(var2 == null) {
                  var3 = var2;
                  if(var1 >= 128) {
                     var3 = ReadTableEntry.getConstituentInstance();
                  }
               }

               var2 = var3;
               if(var3 == null) {
                  var2 = ReadTableEntry.getIllegalInstance();
               }
            }
         }
      }

      return var2;
   }

   protected Object makeSymbol(String var1) {
      return Namespace.EmptyNamespace.getSymbol(var1.intern());
   }

   public void putReaderCtor(String var1, Type var2) {
      synchronized(this){}

      try {
         this.initCtorTable();
         this.ctorTable.put((String)var1, var2);
      } finally {
         ;
      }

   }

   public void putReaderCtor(String var1, Procedure var2) {
      synchronized(this){}

      try {
         this.initCtorTable();
         this.ctorTable.put((String)var1, var2);
      } finally {
         ;
      }

   }

   public void putReaderCtorFld(String var1, String var2, String var3) {
      synchronized(this){}

      try {
         this.initCtorTable();
         Symbol var6 = this.ctorTable.getSymbol(var1);
         StaticFieldLocation.define(this.ctorTable, var6, (Object)null, var2, var3);
      } finally {
         ;
      }

   }

   public void setBracketMode() {
      this.setBracketMode(defaultBracketMode);
   }

   public void setBracketMode(int var1) {
      if(var1 <= 0) {
         ReadTableEntry var2 = ReadTableEntry.getConstituentInstance();
         this.set(60, var2);
         if(var1 < 0) {
            this.set(91, var2);
            this.set(93, var2);
         }
      } else {
         this.set(60, new ReaderTypespec());
      }

      if(var1 >= 0) {
         this.set(91, ReaderParens.getInstance('[', ']'));
         this.remove(93);
      }

   }

   public void setFinalColonIsKeyword(boolean var1) {
      this.finalColonIsKeyword = var1;
   }

   public void setInitialColonIsKeyword(boolean var1) {
      this.initialColonIsKeyword = var1;
   }
}
