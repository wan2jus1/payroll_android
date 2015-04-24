package gnu.kawa.slib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.kawa.util.HashNode;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.lib.strings;
import kawa.lib.kawa.hashtable;
import kawa.lib.rnrs.hashtables;
import kawa.lib.rnrs.unicode;
import kawa.standard.Scheme;

public class srfi69 extends ModuleBody {

   public static final srfi69 $instance = new srfi69();
   static final IntNum Lit0 = IntNum.make(64);
   static final SimpleSymbol Lit1 = (SimpleSymbol)(new SimpleSymbol("string-hash")).readResolve();
   static final SimpleSymbol Lit10 = (SimpleSymbol)(new SimpleSymbol("hash-table-update!")).readResolve();
   static final SimpleSymbol Lit11 = (SimpleSymbol)(new SimpleSymbol("hash-table-update!/default")).readResolve();
   static final SimpleSymbol Lit12 = (SimpleSymbol)(new SimpleSymbol("hash-table-walk")).readResolve();
   static final SimpleSymbol Lit13 = (SimpleSymbol)(new SimpleSymbol("hash-table-fold")).readResolve();
   static final SimpleSymbol Lit14 = (SimpleSymbol)(new SimpleSymbol("alist->hash-table")).readResolve();
   static final SimpleSymbol Lit15 = (SimpleSymbol)(new SimpleSymbol("hash-table->alist")).readResolve();
   static final SimpleSymbol Lit16 = (SimpleSymbol)(new SimpleSymbol("hash-table-copy")).readResolve();
   static final SimpleSymbol Lit17 = (SimpleSymbol)(new SimpleSymbol("hash-table-merge!")).readResolve();
   static final SimpleSymbol Lit18 = (SimpleSymbol)(new SimpleSymbol("hash-table-keys")).readResolve();
   static final SimpleSymbol Lit19 = (SimpleSymbol)(new SimpleSymbol("hash-table-values")).readResolve();
   static final SimpleSymbol Lit2 = (SimpleSymbol)(new SimpleSymbol("string-ci-hash")).readResolve();
   static final SimpleSymbol Lit3 = (SimpleSymbol)(new SimpleSymbol("hash")).readResolve();
   static final SimpleSymbol Lit4 = (SimpleSymbol)(new SimpleSymbol("hash-by-identity")).readResolve();
   static final SimpleSymbol Lit5 = (SimpleSymbol)(new SimpleSymbol("hash-table-equivalence-function")).readResolve();
   static final SimpleSymbol Lit6 = (SimpleSymbol)(new SimpleSymbol("hash-table-hash-function")).readResolve();
   static final SimpleSymbol Lit7 = (SimpleSymbol)(new SimpleSymbol("make-hash-table")).readResolve();
   static final SimpleSymbol Lit8 = (SimpleSymbol)(new SimpleSymbol("hash-table-ref")).readResolve();
   static final SimpleSymbol Lit9 = (SimpleSymbol)(new SimpleSymbol("hash-table-ref/default")).readResolve();
   public static final ModuleMethod alist$Mn$Grhash$Mntable;
   public static final ModuleMethod hash;
   public static final ModuleMethod hash$Mnby$Mnidentity;
   public static final ModuleMethod hash$Mntable$Mn$Gralist;
   public static final ModuleMethod hash$Mntable$Mncopy;
   public static final Location hash$Mntable$Mndelete$Ex = StaticFieldLocation.make("kawa.lib.rnrs.hashtables", "hashtable$Mndelete$Ex");
   public static final ModuleMethod hash$Mntable$Mnequivalence$Mnfunction;
   public static final Location hash$Mntable$Mnexists$Qu = StaticFieldLocation.make("kawa.lib.rnrs.hashtables", "hashtable$Mncontains$Qu");
   public static final ModuleMethod hash$Mntable$Mnfold;
   public static final ModuleMethod hash$Mntable$Mnhash$Mnfunction;
   public static final ModuleMethod hash$Mntable$Mnkeys;
   public static final ModuleMethod hash$Mntable$Mnmerge$Ex;
   public static final ModuleMethod hash$Mntable$Mnref;
   public static final ModuleMethod hash$Mntable$Mnref$Sldefault;
   public static final Location hash$Mntable$Mnset$Ex = StaticFieldLocation.make("kawa.lib.rnrs.hashtables", "hashtable$Mnset$Ex");
   public static final Location hash$Mntable$Mnsize = StaticFieldLocation.make("kawa.lib.rnrs.hashtables", "hashtable$Mnsize");
   public static final ModuleMethod hash$Mntable$Mnupdate$Ex;
   public static final ModuleMethod hash$Mntable$Mnupdate$Ex$Sldefault;
   public static final ModuleMethod hash$Mntable$Mnvalues;
   public static final ModuleMethod hash$Mntable$Mnwalk;
   public static final Location hash$Mntable$Qu = StaticFieldLocation.make("kawa.lib.rnrs.hashtables", "hashtable$Qu");
   static final ModuleMethod lambda$Fn1;
   static final ModuleMethod lambda$Fn2;
   static final ModuleMethod lambda$Fn3;
   public static final ModuleMethod make$Mnhash$Mntable;
   public static final ModuleMethod string$Mnci$Mnhash;
   public static final ModuleMethod string$Mnhash;


   static {
      srfi69 var0 = $instance;
      string$Mnhash = new ModuleMethod(var0, 1, Lit1, 8193);
      string$Mnci$Mnhash = new ModuleMethod(var0, 3, Lit2, 8193);
      hash = new ModuleMethod(var0, 5, Lit3, 8193);
      hash$Mnby$Mnidentity = new ModuleMethod(var0, 7, Lit4, 8193);
      hash$Mntable$Mnequivalence$Mnfunction = new ModuleMethod(var0, 9, Lit5, 4097);
      hash$Mntable$Mnhash$Mnfunction = new ModuleMethod(var0, 10, Lit6, 4097);
      make$Mnhash$Mntable = new ModuleMethod(var0, 11, Lit7, 12288);
      hash$Mntable$Mnref = new ModuleMethod(var0, 15, Lit8, 12290);
      hash$Mntable$Mnref$Sldefault = new ModuleMethod(var0, 17, Lit9, 12291);
      hash$Mntable$Mnupdate$Ex = new ModuleMethod(var0, 18, Lit10, 16387);
      hash$Mntable$Mnupdate$Ex$Sldefault = new ModuleMethod(var0, 20, Lit11, 16388);
      hash$Mntable$Mnwalk = new ModuleMethod(var0, 21, Lit12, 8194);
      hash$Mntable$Mnfold = new ModuleMethod(var0, 22, Lit13, 12291);
      ModuleMethod var1 = new ModuleMethod(var0, 23, (Object)null, 4097);
      var1.setProperty("source-location", "srfi69.scm:166");
      lambda$Fn1 = var1;
      alist$Mn$Grhash$Mntable = new ModuleMethod(var0, 24, Lit14, 16385);
      hash$Mntable$Mn$Gralist = new ModuleMethod(var0, 28, Lit15, 4097);
      hash$Mntable$Mncopy = new ModuleMethod(var0, 29, Lit16, 4097);
      hash$Mntable$Mnmerge$Ex = new ModuleMethod(var0, 30, Lit17, 8194);
      var1 = new ModuleMethod(var0, 31, (Object)null, 12291);
      var1.setProperty("source-location", "srfi69.scm:183");
      lambda$Fn2 = var1;
      hash$Mntable$Mnkeys = new ModuleMethod(var0, 32, Lit18, 4097);
      var1 = new ModuleMethod(var0, 33, (Object)null, 12291);
      var1.setProperty("source-location", "srfi69.scm:186");
      lambda$Fn3 = var1;
      hash$Mntable$Mnvalues = new ModuleMethod(var0, 34, Lit19, 4097);
      $instance.run();
   }

   public srfi69() {
      ModuleInfo.register(this);
   }

   public static hashtable.HashTable alist$To$HashTable(Object var0) {
      return alist$To$HashTable(var0, Scheme.isEqual);
   }

   public static hashtable.HashTable alist$To$HashTable(Object var0, Object var1) {
      return alist$To$HashTable(var0, var1, appropriateHashFunctionFor(var1));
   }

   public static hashtable.HashTable alist$To$HashTable(Object var0, Object var1, Object var2) {
      IntNum var3 = Lit0;

      LList var4;
      try {
         var4 = (LList)var0;
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "length", 1, var0);
      }

      return alist$To$HashTable(var0, var1, var2, numbers.max(new Object[]{var3, Integer.valueOf(lists.length(var4) * 2)}));
   }

   public static hashtable.HashTable alist$To$HashTable(Object var0, Object var1, Object var2, Object var3) {
      Procedure var4;
      try {
         var4 = (Procedure)var1;
      } catch (ClassCastException var9) {
         throw new WrongType(var9, "make-hash-table", 0, var1);
      }

      Procedure var10;
      try {
         var10 = (Procedure)var2;
      } catch (ClassCastException var8) {
         throw new WrongType(var8, "make-hash-table", 1, var2);
      }

      int var5;
      try {
         var5 = ((Number)var3).intValue();
      } catch (ClassCastException var7) {
         throw new WrongType(var7, "make-hash-table", 2, var3);
      }

      hashtable.HashTable var11;
      Pair var12;
      for(var11 = makeHashTable(var4, var10, var5); var0 != LList.Empty; var0 = var12.getCdr()) {
         try {
            var12 = (Pair)var0;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "arg0", -2, var0);
         }

         var0 = var12.getCar();
         hashTableUpdate$Ex$SlDefault(var11, lists.car.apply1(var0), lambda$Fn1, lists.cdr.apply1(var0));
      }

      return var11;
   }

   static Procedure appropriateHashFunctionFor(Object var0) {
      boolean var2;
      if(var0 == Scheme.isEq) {
         var2 = true;
      } else {
         var2 = false;
      }

      Object var1;
      if(var2) {
         var1 = hash$Mnby$Mnidentity;
      } else if(var2) {
         var1 = Boolean.TRUE;
      } else {
         var1 = Boolean.FALSE;
      }

      if(var1 != Boolean.FALSE) {
         return (Procedure)var1;
      } else {
         if(var0 == strings.string$Eq$Qu) {
            var2 = true;
         } else {
            var2 = false;
         }

         if(var2) {
            var1 = string$Mnhash;
         } else if(var2) {
            var1 = Boolean.TRUE;
         } else {
            var1 = Boolean.FALSE;
         }

         if(var1 != Boolean.FALSE) {
            return (Procedure)var1;
         } else {
            if(var0 == unicode.string$Mnci$Eq$Qu) {
               var2 = true;
            } else {
               var2 = false;
            }

            if(var2) {
               var0 = string$Mnci$Mnhash;
            } else if(var2) {
               var0 = Boolean.TRUE;
            } else {
               var0 = Boolean.FALSE;
            }

            return (Procedure)(var0 != Boolean.FALSE?(Procedure)var0:hash);
         }
      }
   }

   public static Object hash(Object var0) {
      return hash(var0, (IntNum)null);
   }

   public static Object hash(Object var0, IntNum var1) {
      int var2;
      if(var0 == null) {
         var2 = 0;
      } else {
         var2 = var0.hashCode();
      }

      return var1 == null?Integer.valueOf(var2):IntNum.modulo(IntNum.make(var2), var1);
   }

   public static Object hashByIdentity(Object var0) {
      return hashByIdentity(var0, (IntNum)null);
   }

   public static Object hashByIdentity(Object var0, IntNum var1) {
      int var2 = System.identityHashCode(var0);
      return var1 == null?Integer.valueOf(var2):IntNum.modulo(IntNum.make(var2), var1);
   }

   public static Object hashTable$To$Alist(hashtable.HashTable var0) {
      return var0.toAlist();
   }

   public static hashtable.HashTable hashTableCopy(hashtable.HashTable var0) {
      return var0.new HashTable(true);
   }

   public static Procedure hashTableEquivalenceFunction(hashtable.HashTable var0) {
      return var0.equivalenceFunction;
   }

   public static Object hashTableFold(hashtable.HashTable var0, Procedure var1, Object var2) {
      return var0.fold(var1, var2);
   }

   public static Procedure hashTableHashFunction(hashtable.HashTable var0) {
      return var0.hashFunction;
   }

   public static Object hashTableKeys(hashtable.HashTable var0) {
      return hashTableFold(var0, lambda$Fn2, LList.Empty);
   }

   public static void hashTableMerge$Ex(hashtable.HashTable var0, hashtable.HashTable var1) {
      var0.putAll(var1);
   }

   public static Object hashTableRef(hashtable.HashTable var0, Object var1) {
      return hashTableRef(var0, var1, Boolean.FALSE);
   }

   public static Object hashTableRef(hashtable.HashTable var0, Object var1, Object var2) {
      HashNode var3 = var0.getNode(var1);
      return var3 == null?(var2 != Boolean.FALSE?Scheme.applyToArgs.apply1(var2):misc.error$V("hash-table-ref: no value associated with", new Object[]{var1})):var3.getValue();
   }

   public static Object hashTableRef$SlDefault(hashtable.HashTable var0, Object var1, Object var2) {
      return var0.get(var1, var2);
   }

   public static void hashTableUpdate$Ex(hashtable.HashTable var0, Object var1, Object var2) {
      hashTableUpdate$Ex(var0, var1, var2, Boolean.FALSE);
   }

   public static void hashTableUpdate$Ex(hashtable.HashTable var0, Object var1, Object var2, Object var3) {
      hashtable.hashtableCheckMutable(var0);
      HashNode var4 = var0.getNode(var1);
      if(var4 == null) {
         if(var3 != Boolean.FALSE) {
            hashtables.hashtableSet$Ex(var0, var1, Scheme.applyToArgs.apply2(var2, Scheme.applyToArgs.apply1(var3)));
         } else {
            misc.error$V("hash-table-update!: no value exists for key", new Object[]{var1});
         }
      } else {
         var4.setValue(Scheme.applyToArgs.apply2(var2, var4.getValue()));
      }
   }

   public static void hashTableUpdate$Ex$SlDefault(hashtable.HashTable var0, Object var1, Object var2, Object var3) {
      hashtable.hashtableCheckMutable(var0);
      HashNode var4 = var0.getNode(var1);
      if(var4 == null) {
         hashtables.hashtableSet$Ex(var0, var1, Scheme.applyToArgs.apply2(var2, var3));
      } else {
         var4.setValue(Scheme.applyToArgs.apply2(var2, var4.getValue()));
      }
   }

   public static Object hashTableValues(hashtable.HashTable var0) {
      return hashTableFold(var0, lambda$Fn3, LList.Empty);
   }

   public static void hashTableWalk(hashtable.HashTable var0, Procedure var1) {
      var0.walk(var1);
   }

   static Object lambda1(Object var0) {
      return var0;
   }

   static Pair lambda2(Object var0, Object var1, Object var2) {
      return lists.cons(var0, var2);
   }

   static Pair lambda3(Object var0, Object var1, Object var2) {
      return lists.cons(var1, var2);
   }

   public static hashtable.HashTable makeHashTable() {
      return makeHashTable(Scheme.isEqual);
   }

   public static hashtable.HashTable makeHashTable(Procedure var0) {
      return makeHashTable(var0, appropriateHashFunctionFor(var0), 64);
   }

   public static hashtable.HashTable makeHashTable(Procedure var0, Procedure var1) {
      return makeHashTable(var0, var1, 64);
   }

   public static hashtable.HashTable makeHashTable(Procedure var0, Procedure var1, int var2) {
      return var0.new HashTable(var1, var2);
   }

   public static Object stringCiHash(Object var0) {
      return stringCiHash(var0, (IntNum)null);
   }

   public static Object stringCiHash(Object var0, IntNum var1) {
      int var2 = var0.toString().toLowerCase().hashCode();
      return var1 == null?Integer.valueOf(var2):IntNum.modulo(IntNum.make(var2), var1);
   }

   public static Object stringHash(CharSequence var0) {
      return stringHash(var0, (IntNum)null);
   }

   public static Object stringHash(CharSequence var0, IntNum var1) {
      int var2 = var0.hashCode();
      return var1 == null?Integer.valueOf(var2):IntNum.modulo(IntNum.make(var2), var1);
   }

   static Object symbolHash(Symbol var0) {
      return symbolHash(var0, (IntNum)null);
   }

   static Object symbolHash(Symbol var0, IntNum var1) {
      int var2 = var0.hashCode();
      return var1 == null?Integer.valueOf(var2):IntNum.modulo(IntNum.make(var2), var1);
   }

   static Object vectorHash(Object var0) {
      return vectorHash(var0, (IntNum)null);
   }

   static Object vectorHash(Object var0, IntNum var1) {
      int var2 = var0.hashCode();
      return var1 == null?Integer.valueOf(var2):IntNum.modulo(IntNum.make(var2), var1);
   }

   public Object apply0(ModuleMethod var1) {
      return var1.selector == 11?makeHashTable():super.apply0(var1);
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      hashtable.HashTable var11;
      switch(var1.selector) {
      case 1:
         CharSequence var13;
         try {
            var13 = (CharSequence)var2;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "string-hash", 1, var2);
         }

         return stringHash(var13);
      case 3:
         return stringCiHash(var2);
      case 5:
         return hash(var2);
      case 7:
         return hashByIdentity(var2);
      case 9:
         try {
            var11 = (hashtable.HashTable)var2;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "hash-table-equivalence-function", 1, var2);
         }

         return hashTableEquivalenceFunction(var11);
      case 10:
         try {
            var11 = (hashtable.HashTable)var2;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "hash-table-hash-function", 1, var2);
         }

         return hashTableHashFunction(var11);
      case 11:
         Procedure var12;
         try {
            var12 = (Procedure)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "make-hash-table", 1, var2);
         }

         return makeHashTable(var12);
      case 23:
         return lambda1(var2);
      case 24:
         return alist$To$HashTable(var2);
      case 28:
         try {
            var11 = (hashtable.HashTable)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "hash-table->alist", 1, var2);
         }

         return hashTable$To$Alist(var11);
      case 29:
         try {
            var11 = (hashtable.HashTable)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "hash-table-copy", 1, var2);
         }

         return hashTableCopy(var11);
      case 32:
         try {
            var11 = (hashtable.HashTable)var2;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "hash-table-keys", 1, var2);
         }

         return hashTableKeys(var11);
      case 34:
         try {
            var11 = (hashtable.HashTable)var2;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "hash-table-values", 1, var2);
         }

         return hashTableValues(var11);
      default:
         return super.apply1(var1, var2);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      hashtable.HashTable var16;
      IntNum var19;
      Procedure var20;
      switch(var1.selector) {
      case 1:
         CharSequence var22;
         try {
            var22 = (CharSequence)var2;
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "string-hash", 1, var2);
         }

         IntNum var21;
         try {
            var21 = LangObjType.coerceIntNum(var3);
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "string-hash", 2, var3);
         }

         return stringHash(var22, var21);
      case 3:
         try {
            var19 = LangObjType.coerceIntNum(var3);
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "string-ci-hash", 2, var3);
         }

         return stringCiHash(var2, var19);
      case 5:
         try {
            var19 = LangObjType.coerceIntNum(var3);
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "hash", 2, var3);
         }

         return hash(var2, var19);
      case 7:
         try {
            var19 = LangObjType.coerceIntNum(var3);
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "hash-by-identity", 2, var3);
         }

         return hashByIdentity(var2, var19);
      case 11:
         Procedure var17;
         try {
            var17 = (Procedure)var2;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "make-hash-table", 1, var2);
         }

         try {
            var20 = (Procedure)var3;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "make-hash-table", 2, var3);
         }

         return makeHashTable(var17, var20);
      case 15:
         try {
            var16 = (hashtable.HashTable)var2;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "hash-table-ref", 1, var2);
         }

         return hashTableRef(var16, var3);
      case 21:
         try {
            var16 = (hashtable.HashTable)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "hash-table-walk", 1, var2);
         }

         try {
            var20 = (Procedure)var3;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "hash-table-walk", 2, var3);
         }

         hashTableWalk(var16, var20);
         return Values.empty;
      case 24:
         return alist$To$HashTable(var2, var3);
      case 30:
         try {
            var16 = (hashtable.HashTable)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "hash-table-merge!", 1, var2);
         }

         hashtable.HashTable var18;
         try {
            var18 = (hashtable.HashTable)var3;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "hash-table-merge!", 2, var3);
         }

         hashTableMerge$Ex(var16, var18);
         return Values.empty;
      default:
         return super.apply2(var1, var2, var3);
      }
   }

   public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
      hashtable.HashTable var14;
      Procedure var15;
      switch(var1.selector) {
      case 11:
         Procedure var16;
         try {
            var16 = (Procedure)var2;
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "make-hash-table", 1, var2);
         }

         try {
            var15 = (Procedure)var3;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "make-hash-table", 2, var3);
         }

         int var5;
         try {
            var5 = ((Number)var4).intValue();
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "make-hash-table", 3, var4);
         }

         return makeHashTable(var16, var15, var5);
      case 15:
         try {
            var14 = (hashtable.HashTable)var2;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "hash-table-ref", 1, var2);
         }

         return hashTableRef(var14, var3, var4);
      case 17:
         try {
            var14 = (hashtable.HashTable)var2;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "hash-table-ref/default", 1, var2);
         }

         return hashTableRef$SlDefault(var14, var3, var4);
      case 18:
         try {
            var14 = (hashtable.HashTable)var2;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "hash-table-update!", 1, var2);
         }

         hashTableUpdate$Ex(var14, var3, var4);
         return Values.empty;
      case 22:
         try {
            var14 = (hashtable.HashTable)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "hash-table-fold", 1, var2);
         }

         try {
            var15 = (Procedure)var3;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "hash-table-fold", 2, var3);
         }

         return hashTableFold(var14, var15, var4);
      case 24:
         return alist$To$HashTable(var2, var3, var4);
      case 31:
         return lambda2(var2, var3, var4);
      case 33:
         return lambda3(var2, var3, var4);
      default:
         return super.apply3(var1, var2, var3, var4);
      }
   }

   public Object apply4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5) {
      hashtable.HashTable var8;
      switch(var1.selector) {
      case 18:
         try {
            var8 = (hashtable.HashTable)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "hash-table-update!", 1, var2);
         }

         hashTableUpdate$Ex(var8, var3, var4, var5);
         return Values.empty;
      case 20:
         try {
            var8 = (hashtable.HashTable)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "hash-table-update!/default", 1, var2);
         }

         hashTableUpdate$Ex$SlDefault(var8, var3, var4, var5);
         return Values.empty;
      case 24:
         return alist$To$HashTable(var2, var3, var4, var5);
      default:
         return super.apply4(var1, var2, var3, var4, var5);
      }
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

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      int var4 = -786431;
      switch(var1.selector) {
      case 1:
         if(var2 instanceof CharSequence) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 3:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 5:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 7:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 9:
         if(var2 instanceof hashtable.HashTable) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 10:
         if(var2 instanceof hashtable.HashTable) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 11:
         if(var2 instanceof Procedure) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 23:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 24:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 28:
         if(var2 instanceof hashtable.HashTable) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 29:
         if(var2 instanceof hashtable.HashTable) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 32:
         if(var2 instanceof hashtable.HashTable) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 34:
         if(var2 instanceof hashtable.HashTable) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      default:
         var4 = super.match1(var1, var2, var3);
      }

      return var4;
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      int var5 = -786431;
      switch(var1.selector) {
      case 1:
         if(var2 instanceof CharSequence) {
            var4.value1 = var2;
            if(IntNum.asIntNumOrNull(var3) != null) {
               var4.value2 = var3;
               var4.proc = var1;
               var4.pc = 2;
               return 0;
            }

            return -786430;
         }
         break;
      case 3:
         var4.value1 = var2;
         if(IntNum.asIntNumOrNull(var3) != null) {
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786430;
      case 5:
         var4.value1 = var2;
         if(IntNum.asIntNumOrNull(var3) != null) {
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786430;
      case 7:
         var4.value1 = var2;
         if(IntNum.asIntNumOrNull(var3) != null) {
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786430;
      case 11:
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
      case 15:
         if(var2 instanceof hashtable.HashTable) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 21:
         if(var2 instanceof hashtable.HashTable) {
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
      case 24:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 30:
         if(var2 instanceof hashtable.HashTable) {
            var4.value1 = var2;
            if(!(var3 instanceof hashtable.HashTable)) {
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
      switch(var1.selector) {
      case 11:
         if(!(var2 instanceof Procedure)) {
            return -786431;
         } else {
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
      case 15:
         if(!(var2 instanceof hashtable.HashTable)) {
            return -786431;
         }

         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 17:
         if(!(var2 instanceof hashtable.HashTable)) {
            return -786431;
         }

         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 18:
         if(!(var2 instanceof hashtable.HashTable)) {
            return -786431;
         }

         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 22:
         if(!(var2 instanceof hashtable.HashTable)) {
            return -786431;
         } else {
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
      case 24:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 31:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 33:
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

   public int match4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5, CallContext var6) {
      switch(var1.selector) {
      case 18:
         if(!(var2 instanceof hashtable.HashTable)) {
            return -786431;
         }

         var6.value1 = var2;
         var6.value2 = var3;
         var6.value3 = var4;
         var6.value4 = var5;
         var6.proc = var1;
         var6.pc = 4;
         return 0;
      case 20:
         if(!(var2 instanceof hashtable.HashTable)) {
            return -786431;
         }

         var6.value1 = var2;
         var6.value2 = var3;
         var6.value3 = var4;
         var6.value4 = var5;
         var6.proc = var1;
         var6.pc = 4;
         return 0;
      case 24:
         var6.value1 = var2;
         var6.value2 = var3;
         var6.value3 = var4;
         var6.value4 = var5;
         var6.proc = var1;
         var6.pc = 4;
         return 0;
      default:
         return super.match4(var1, var2, var3, var4, var5, var6);
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }
}
