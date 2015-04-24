package kawa.lib.rnrs;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.ApplyToArgs;
import gnu.kawa.util.AbstractHashTable;
import gnu.kawa.util.HashNode;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.Pair;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.kawa.hashtable;
import kawa.standard.Scheme;

public class hashtables extends ModuleBody {

   public static final hashtables $instance = new hashtables();
   static final SimpleSymbol Lit0 = (SimpleSymbol)(new SimpleSymbol("hash-by-identity")).readResolve();
   static final SimpleSymbol Lit1 = (SimpleSymbol)(new SimpleSymbol("hash-for-eqv")).readResolve();
   static final SimpleSymbol Lit10 = (SimpleSymbol)(new SimpleSymbol("hashtable-contains?")).readResolve();
   static final SimpleSymbol Lit11 = (SimpleSymbol)(new SimpleSymbol("hashtable-update!")).readResolve();
   static final SimpleSymbol Lit12 = (SimpleSymbol)(new SimpleSymbol("hashtable-copy")).readResolve();
   static final SimpleSymbol Lit13 = (SimpleSymbol)(new SimpleSymbol("hashtable-clear!")).readResolve();
   static final SimpleSymbol Lit14 = (SimpleSymbol)(new SimpleSymbol("hashtable-keys")).readResolve();
   static final SimpleSymbol Lit15 = (SimpleSymbol)(new SimpleSymbol("hashtable-entries")).readResolve();
   static final SimpleSymbol Lit16 = (SimpleSymbol)(new SimpleSymbol("hashtable-equivalence-function")).readResolve();
   static final SimpleSymbol Lit17 = (SimpleSymbol)(new SimpleSymbol("hashtable-hash-function")).readResolve();
   static final SimpleSymbol Lit18 = (SimpleSymbol)(new SimpleSymbol("hashtable-mutable?")).readResolve();
   static final SimpleSymbol Lit19 = (SimpleSymbol)(new SimpleSymbol("equal-hash")).readResolve();
   static final SimpleSymbol Lit2 = (SimpleSymbol)(new SimpleSymbol("make-eq-hashtable")).readResolve();
   static final SimpleSymbol Lit20 = (SimpleSymbol)(new SimpleSymbol("string-hash")).readResolve();
   static final SimpleSymbol Lit21 = (SimpleSymbol)(new SimpleSymbol("string-ci-hash")).readResolve();
   static final SimpleSymbol Lit22 = (SimpleSymbol)(new SimpleSymbol("symbol-hash")).readResolve();
   static final SimpleSymbol Lit3 = (SimpleSymbol)(new SimpleSymbol("make-eqv-hashtable")).readResolve();
   static final SimpleSymbol Lit4 = (SimpleSymbol)(new SimpleSymbol("make-hashtable")).readResolve();
   static final SimpleSymbol Lit5 = (SimpleSymbol)(new SimpleSymbol("hashtable?")).readResolve();
   static final SimpleSymbol Lit6 = (SimpleSymbol)(new SimpleSymbol("hashtable-size")).readResolve();
   static final SimpleSymbol Lit7 = (SimpleSymbol)(new SimpleSymbol("hashtable-ref")).readResolve();
   static final SimpleSymbol Lit8 = (SimpleSymbol)(new SimpleSymbol("hashtable-set!")).readResolve();
   static final SimpleSymbol Lit9 = (SimpleSymbol)(new SimpleSymbol("hashtable-delete!")).readResolve();
   public static final ModuleMethod equal$Mnhash;
   static final ModuleMethod hash$Mnby$Mnidentity;
   static final ModuleMethod hash$Mnfor$Mneqv;
   public static final ModuleMethod hashtable$Mnclear$Ex;
   public static final ModuleMethod hashtable$Mncontains$Qu;
   public static final ModuleMethod hashtable$Mncopy;
   public static final ModuleMethod hashtable$Mndelete$Ex;
   public static final ModuleMethod hashtable$Mnentries;
   public static final ModuleMethod hashtable$Mnequivalence$Mnfunction;
   public static final ModuleMethod hashtable$Mnhash$Mnfunction;
   public static final ModuleMethod hashtable$Mnkeys;
   public static final ModuleMethod hashtable$Mnmutable$Qu;
   public static final ModuleMethod hashtable$Mnref;
   public static final ModuleMethod hashtable$Mnset$Ex;
   public static final ModuleMethod hashtable$Mnsize;
   public static final ModuleMethod hashtable$Mnupdate$Ex;
   public static final ModuleMethod hashtable$Qu;
   public static final ModuleMethod make$Mneq$Mnhashtable;
   public static final ModuleMethod make$Mneqv$Mnhashtable;
   public static final ModuleMethod make$Mnhashtable;
   public static final ModuleMethod string$Mnci$Mnhash;
   public static final ModuleMethod string$Mnhash;
   public static final ModuleMethod symbol$Mnhash;


   static {
      hashtables var0 = $instance;
      hash$Mnby$Mnidentity = new ModuleMethod(var0, 1, Lit0, 4097);
      hash$Mnfor$Mneqv = new ModuleMethod(var0, 2, Lit1, 4097);
      make$Mneq$Mnhashtable = new ModuleMethod(var0, 3, Lit2, 4096);
      make$Mneqv$Mnhashtable = new ModuleMethod(var0, 5, Lit3, 4096);
      make$Mnhashtable = new ModuleMethod(var0, 7, Lit4, 12290);
      hashtable$Qu = new ModuleMethod(var0, 9, Lit5, 4097);
      hashtable$Mnsize = new ModuleMethod(var0, 10, Lit6, 4097);
      hashtable$Mnref = new ModuleMethod(var0, 11, Lit7, 12291);
      hashtable$Mnset$Ex = new ModuleMethod(var0, 12, Lit8, 12291);
      hashtable$Mndelete$Ex = new ModuleMethod(var0, 13, Lit9, 8194);
      hashtable$Mncontains$Qu = new ModuleMethod(var0, 14, Lit10, 8194);
      hashtable$Mnupdate$Ex = new ModuleMethod(var0, 15, Lit11, 16388);
      hashtable$Mncopy = new ModuleMethod(var0, 16, Lit12, 8193);
      hashtable$Mnclear$Ex = new ModuleMethod(var0, 18, Lit13, 8193);
      hashtable$Mnkeys = new ModuleMethod(var0, 20, Lit14, 4097);
      hashtable$Mnentries = new ModuleMethod(var0, 21, Lit15, 4097);
      hashtable$Mnequivalence$Mnfunction = new ModuleMethod(var0, 22, Lit16, 4097);
      hashtable$Mnhash$Mnfunction = new ModuleMethod(var0, 23, Lit17, 4097);
      hashtable$Mnmutable$Qu = new ModuleMethod(var0, 24, Lit18, 4097);
      equal$Mnhash = new ModuleMethod(var0, 25, Lit19, 4097);
      string$Mnhash = new ModuleMethod(var0, 26, Lit20, 4097);
      string$Mnci$Mnhash = new ModuleMethod(var0, 27, Lit21, 4097);
      symbol$Mnhash = new ModuleMethod(var0, 28, Lit22, 4097);
      $instance.run();
   }

   public hashtables() {
      ModuleInfo.register(this);
   }

   public static int equalHash(Object var0) {
      return var0.hashCode();
   }

   static int hashByIdentity(Object var0) {
      return System.identityHashCode(var0);
   }

   static int hashForEqv(Object var0) {
      return var0.hashCode();
   }

   public static void hashtableClear$Ex(hashtable.HashTable var0) {
      hashtableClear$Ex(var0, 64);
   }

   public static void hashtableClear$Ex(hashtable.HashTable var0, int var1) {
      hashtable.hashtableCheckMutable(var0);
      var0.clear();
   }

   public static hashtable.HashTable hashtableCopy(hashtable.HashTable var0) {
      return hashtableCopy(var0, false);
   }

   public static hashtable.HashTable hashtableCopy(hashtable.HashTable var0, boolean var1) {
      return var0.new HashTable(var1);
   }

   public static void hashtableDelete$Ex(hashtable.HashTable var0, Object var1) {
      hashtable.hashtableCheckMutable(var0);
      var0.remove(var1);
   }

   public static Object hashtableEntries(hashtable.HashTable var0) {
      Pair var1 = var0.entriesVectorPair();
      return misc.values(new Object[]{lists.car.apply1(var1), lists.cdr.apply1(var1)});
   }

   public static Procedure hashtableEquivalenceFunction(hashtable.HashTable var0) {
      return (Procedure)var0.equivalenceFunction.apply1(var0);
   }

   public static Object hashtableHashFunction(hashtable.HashTable var0) {
      Object var1 = var0.hashFunction.apply1(var0);
      Object var2 = Scheme.isEqv.apply2(var1, hash$Mnby$Mnidentity);
      Object var3;
      if(var2 != Boolean.FALSE) {
         var3 = var1;
         if(var2 == Boolean.FALSE) {
            return var3;
         }
      } else {
         var3 = var1;
         if(Scheme.isEqv.apply2(var1, hash$Mnfor$Mneqv) == Boolean.FALSE) {
            return var3;
         }
      }

      var3 = Boolean.FALSE;
      return var3;
   }

   public static FVector hashtableKeys(hashtable.HashTable var0) {
      return var0.keysVector();
   }

   public static Object hashtableRef(hashtable.HashTable var0, Object var1, Object var2) {
      HashNode var3 = var0.getNode(var1);
      return var3 == null?var2:var3.getValue();
   }

   public static void hashtableSet$Ex(hashtable.HashTable var0, Object var1, Object var2) {
      hashtable.hashtableCheckMutable(var0);
      var0.put(var1, var2);
   }

   public static int hashtableSize(hashtable.HashTable var0) {
      return var0.size();
   }

   public static Object hashtableUpdate$Ex(hashtable.HashTable var0, Object var1, Procedure var2, Object var3) {
      hashtable.hashtableCheckMutable(var0);
      HashNode var4 = var0.getNode(var1);
      if(var4 == null) {
         hashtableSet$Ex(var0, var1, var2.apply1(var3));
         return Values.empty;
      } else {
         return var4.setValue(var2.apply1(var4.getValue()));
      }
   }

   public static boolean isHashtable(Object var0) {
      return var0 instanceof hashtable.HashTable;
   }

   public static boolean isHashtableContains(hashtable.HashTable var0, Object var1) {
      byte var2;
      if(var0.getNode(var1) == null) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      return (boolean)(var2 + 1 & 1);
   }

   public static Object isHashtableMutable(hashtable.HashTable var0) {
      ApplyToArgs var1 = Scheme.applyToArgs;
      Boolean var2;
      if(var0.mutable) {
         var2 = Boolean.TRUE;
      } else {
         var2 = Boolean.FALSE;
      }

      return var1.apply1(var2);
   }

   public static hashtable.HashTable makeEqHashtable() {
      return makeEqHashtable(AbstractHashTable.DEFAULT_INITIAL_SIZE);
   }

   public static hashtable.HashTable makeEqHashtable(int var0) {
      return Scheme.isEq.new HashTable(hash$Mnby$Mnidentity, AbstractHashTable.DEFAULT_INITIAL_SIZE);
   }

   public static hashtable.HashTable makeEqvHashtable() {
      return makeEqvHashtable(AbstractHashTable.DEFAULT_INITIAL_SIZE);
   }

   public static hashtable.HashTable makeEqvHashtable(int var0) {
      return Scheme.isEqv.new HashTable(hash$Mnfor$Mneqv, AbstractHashTable.DEFAULT_INITIAL_SIZE);
   }

   public static hashtable.HashTable makeHashtable(Procedure var0, Procedure var1) {
      return makeHashtable(var0, var1, AbstractHashTable.DEFAULT_INITIAL_SIZE);
   }

   public static hashtable.HashTable makeHashtable(Procedure var0, Procedure var1, int var2) {
      return var0.new HashTable(var1, var2);
   }

   public static int stringCiHash(CharSequence var0) {
      return var0.toString().toLowerCase().hashCode();
   }

   public static int stringHash(CharSequence var0) {
      return var0.hashCode();
   }

   public static int symbolHash(Symbol var0) {
      return var0.hashCode();
   }

   public Object apply0(ModuleMethod var1) {
      switch(var1.selector) {
      case 3:
         return makeEqHashtable();
      case 4:
      default:
         return super.apply0(var1);
      case 5:
         return makeEqvHashtable();
      }
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      int var3;
      hashtable.HashTable var19;
      CharSequence var18;
      switch(var1.selector) {
      case 1:
         return Integer.valueOf(hashByIdentity(var2));
      case 2:
         return Integer.valueOf(hashForEqv(var2));
      case 3:
         try {
            var3 = ((Number)var2).intValue();
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "make-eq-hashtable", 1, var2);
         }

         return makeEqHashtable(var3);
      case 4:
      case 6:
      case 7:
      case 8:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 17:
      case 19:
      default:
         return super.apply1(var1, var2);
      case 5:
         try {
            var3 = ((Number)var2).intValue();
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "make-eqv-hashtable", 1, var2);
         }

         return makeEqvHashtable(var3);
      case 9:
         if(isHashtable(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 10:
         try {
            var19 = (hashtable.HashTable)var2;
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "hashtable-size", 1, var2);
         }

         return Integer.valueOf(hashtableSize(var19));
      case 16:
         try {
            var19 = (hashtable.HashTable)var2;
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "hashtable-copy", 1, var2);
         }

         return hashtableCopy(var19);
      case 18:
         try {
            var19 = (hashtable.HashTable)var2;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "hashtable-clear!", 1, var2);
         }

         hashtableClear$Ex(var19);
         return Values.empty;
      case 20:
         try {
            var19 = (hashtable.HashTable)var2;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "hashtable-keys", 1, var2);
         }

         return hashtableKeys(var19);
      case 21:
         try {
            var19 = (hashtable.HashTable)var2;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "hashtable-entries", 1, var2);
         }

         return hashtableEntries(var19);
      case 22:
         try {
            var19 = (hashtable.HashTable)var2;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "hashtable-equivalence-function", 1, var2);
         }

         return hashtableEquivalenceFunction(var19);
      case 23:
         try {
            var19 = (hashtable.HashTable)var2;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "hashtable-hash-function", 1, var2);
         }

         return hashtableHashFunction(var19);
      case 24:
         try {
            var19 = (hashtable.HashTable)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "hashtable-mutable?", 1, var2);
         }

         return isHashtableMutable(var19);
      case 25:
         return Integer.valueOf(equalHash(var2));
      case 26:
         try {
            var18 = (CharSequence)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "string-hash", 1, var2);
         }

         return Integer.valueOf(stringHash(var18));
      case 27:
         try {
            var18 = (CharSequence)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "string-ci-hash", 1, var2);
         }

         return Integer.valueOf(stringCiHash(var18));
      case 28:
         Symbol var17;
         try {
            var17 = (Symbol)var2;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "symbol-hash", 1, var2);
         }

         return Integer.valueOf(symbolHash(var17));
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      boolean var5 = true;
      hashtable.HashTable var14;
      switch(var1.selector) {
      case 7:
         Procedure var16;
         try {
            var16 = (Procedure)var2;
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "make-hashtable", 1, var2);
         }

         Procedure var17;
         try {
            var17 = (Procedure)var3;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "make-hashtable", 2, var3);
         }

         return makeHashtable(var16, var17);
      case 13:
         try {
            var14 = (hashtable.HashTable)var2;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "hashtable-delete!", 1, var2);
         }

         hashtableDelete$Ex(var14, var3);
         return Values.empty;
      case 14:
         try {
            var14 = (hashtable.HashTable)var2;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "hashtable-contains?", 1, var2);
         }

         if(isHashtableContains(var14, var3)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 16:
         try {
            var14 = (hashtable.HashTable)var2;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "hashtable-copy", 1, var2);
         }

         Boolean var15;
         try {
            var15 = Boolean.FALSE;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "hashtable-copy", 2, var3);
         }

         if(var3 == var15) {
            var5 = false;
         }

         return hashtableCopy(var14, var5);
      case 18:
         try {
            var14 = (hashtable.HashTable)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "hashtable-clear!", 1, var2);
         }

         int var4;
         try {
            var4 = ((Number)var3).intValue();
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "hashtable-clear!", 2, var3);
         }

         hashtableClear$Ex(var14, var4);
         return Values.empty;
      default:
         return super.apply2(var1, var2, var3);
      }
   }

   public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
      hashtable.HashTable var11;
      switch(var1.selector) {
      case 7:
         Procedure var12;
         try {
            var12 = (Procedure)var2;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "make-hashtable", 1, var2);
         }

         Procedure var13;
         try {
            var13 = (Procedure)var3;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "make-hashtable", 2, var3);
         }

         int var5;
         try {
            var5 = ((Number)var4).intValue();
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "make-hashtable", 3, var4);
         }

         return makeHashtable(var12, var13, var5);
      case 8:
      case 9:
      case 10:
      default:
         return super.apply3(var1, var2, var3, var4);
      case 11:
         try {
            var11 = (hashtable.HashTable)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "hashtable-ref", 1, var2);
         }

         return hashtableRef(var11, var3, var4);
      case 12:
         try {
            var11 = (hashtable.HashTable)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "hashtable-set!", 1, var2);
         }

         hashtableSet$Ex(var11, var3, var4);
         return Values.empty;
      }
   }

   public Object apply4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5) {
      if(var1.selector == 15) {
         hashtable.HashTable var8;
         try {
            var8 = (hashtable.HashTable)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "hashtable-update!", 1, var2);
         }

         Procedure var9;
         try {
            var9 = (Procedure)var4;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "hashtable-update!", 3, var4);
         }

         return hashtableUpdate$Ex(var8, var3, var9, var5);
      } else {
         return super.apply4(var1, var2, var3, var4, var5);
      }
   }

   public int match0(ModuleMethod var1, CallContext var2) {
      switch(var1.selector) {
      case 3:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 4:
      default:
         return super.match0(var1, var2);
      case 5:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      int var4 = -786431;
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
      case 3:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 4:
      case 6:
      case 7:
      case 8:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 17:
      case 19:
      default:
         var4 = super.match1(var1, var2, var3);
         break;
      case 5:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 9:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 10:
         if(var2 instanceof hashtable.HashTable) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 16:
         if(var2 instanceof hashtable.HashTable) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 18:
         if(var2 instanceof hashtable.HashTable) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 20:
         if(var2 instanceof hashtable.HashTable) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 21:
         if(var2 instanceof hashtable.HashTable) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 22:
         if(var2 instanceof hashtable.HashTable) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 23:
         if(var2 instanceof hashtable.HashTable) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 24:
         if(var2 instanceof hashtable.HashTable) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 25:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 26:
         if(var2 instanceof CharSequence) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 27:
         if(var2 instanceof CharSequence) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 28:
         if(var2 instanceof Symbol) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
      }

      return var4;
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      int var5 = -786431;
      switch(var1.selector) {
      case 7:
         if(var2 instanceof Procedure) {
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
      case 13:
         if(var2 instanceof hashtable.HashTable) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 14:
         if(var2 instanceof hashtable.HashTable) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 16:
         if(var2 instanceof hashtable.HashTable) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 18:
         if(var2 instanceof hashtable.HashTable) {
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
      case 7:
         if(var2 instanceof Procedure) {
            var5.value1 = var2;
            if(!(var3 instanceof Procedure)) {
               return -786430;
            }

            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }
         break;
      case 8:
      case 9:
      case 10:
      default:
         var6 = super.match3(var1, var2, var3, var4, var5);
         break;
      case 11:
         if(var2 instanceof hashtable.HashTable) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }
         break;
      case 12:
         if(var2 instanceof hashtable.HashTable) {
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
      if(var1.selector == 15) {
         if(!(var2 instanceof hashtable.HashTable)) {
            return -786431;
         } else {
            var6.value1 = var2;
            var6.value2 = var3;
            if(!(var4 instanceof Procedure)) {
               return -786429;
            } else {
               var6.value3 = var4;
               var6.value4 = var5;
               var6.proc = var1;
               var6.pc = 4;
               return 0;
            }
         }
      } else {
         return super.match4(var1, var2, var3, var4, var5, var6);
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }
}
