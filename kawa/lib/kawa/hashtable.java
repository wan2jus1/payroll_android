package kawa.lib.kawa;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.SetNamedPart;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.kawa.util.GeneralHashTable;
import gnu.kawa.util.HashNode;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import java.util.Map.Entry;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.standard.thisRef;

public class hashtable extends ModuleBody {

   public static final Location $Prvt$do = StaticFieldLocation.make("kawa.lib.std_syntax", "do");
   public static final Class $Prvt$hashnode = HashNode.class;
   public static final Location $Prvt$let$St = StaticFieldLocation.make("kawa.lib.std_syntax", "let$St");
   public static final hashtable $instance = new hashtable();
   static final SimpleSymbol Lit0 = (SimpleSymbol)(new SimpleSymbol("mutable")).readResolve();
   static final SimpleSymbol Lit1 = (SimpleSymbol)(new SimpleSymbol("hashtable-check-mutable")).readResolve();
   public static final Class hashtable = hashtable.HashTable.class;
   public static final ModuleMethod hashtable$Mncheck$Mnmutable = new ModuleMethod($instance, 1, Lit1, 4097);


   static {
      $instance.run();
   }

   public hashtable() {
      ModuleInfo.register(this);
   }

   public static void hashtableCheckMutable(hashtable.HashTable var0) {
      if(!var0.mutable) {
         misc.error$V("cannot modify non-mutable hashtable", new Object[0]);
      }

   }

   public Object apply1(ModuleMethod var1, Object var2) {
      if(var1.selector == 1) {
         hashtable.HashTable var4;
         try {
            var4 = (hashtable.HashTable)var2;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "hashtable-check-mutable", 1, var2);
         }

         hashtableCheckMutable(var4);
         return Values.empty;
      } else {
         return super.apply1(var1, var2);
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      if(var1.selector == 1) {
         if(!(var2 instanceof hashtable.HashTable)) {
            return -786431;
         } else {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
      } else {
         return super.match1(var1, var2, var3);
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }

   public class HashTable extends GeneralHashTable {

      public Procedure equivalenceFunction;
      public Procedure hashFunction;
      public boolean mutable;


      private void $finit$() {
         this.mutable = true;
      }

      public HashTable(Procedure var2) {
         this.$finit$();
         this.equivalenceFunction = hashtable.this;
         this.hashFunction = var2;
      }

      public HashTable(Procedure var2, int var3) {
         super(var3);
         this.$finit$();
         this.equivalenceFunction = hashtable.this;
         this.hashFunction = var2;
      }

      public HashTable(boolean var2) {
         this.$finit$();
         Invoke.invokeSpecial.applyN(new Object[]{hashtable.hashtable, this, hashtable.super.equivalenceFunction.apply0(), hashtable.super.hashFunction.apply0(), Integer.valueOf(hashtable.this.size() + 100)});
         this.putAll(hashtable.this);
         SetNamedPart var3 = SetNamedPart.setNamedPart;
         thisRef var4 = thisRef.thisSyntax;
         SimpleSymbol var5 = hashtable.Lit0;
         Boolean var6;
         if(var2) {
            var6 = Boolean.TRUE;
         } else {
            var6 = Boolean.FALSE;
         }

         var3.apply3(var4, var5, var6);
      }

      public Object clone() {
         return new hashtable.HashTable(true);
      }

      public Pair entriesVectorPair() {
         FVector var2 = new FVector();
         FVector var3 = new FVector();
         Entry[] var1 = super.table;

         HashNode[] var4;
         try {
            var4 = (HashNode[])var1;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "table", -2, var1);
         }

         for(int var5 = var4.length - 1; var5 >= 0; --var5) {
            for(HashNode var7 = var4[var5]; var7 != null; var7 = this.getEntryNext(var7)) {
               var2.add(var7.getKey());
               var3.add(var7.getValue());
            }
         }

         return lists.cons(var2, var3);
      }

      public Object fold(Procedure var1, Object var2) {
         Entry[] var3 = super.table;

         HashNode[] var5;
         try {
            var5 = (HashNode[])var3;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "table", -2, var3);
         }

         Object var9;
         for(int var6 = var5.length - 1; var6 >= 0; var2 = var9) {
            HashNode var4 = var5[var6];
            var9 = var2;

            for(HashNode var8 = var4; var8 != null; var8 = this.getEntryNext(var8)) {
               var9 = var1.apply3(var8.getKey(), var8.getValue(), var9);
            }

            --var6;
         }

         return var2;
      }

      public int hash(Object var1) {
         return ((Number)this.hashFunction.apply1(var1)).intValue();
      }

      public FVector keysVector() {
         FVector var2 = new FVector();
         Entry[] var1 = super.table;

         HashNode[] var3;
         try {
            var3 = (HashNode[])var1;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "table", -2, var1);
         }

         for(int var4 = var3.length - 1; var4 >= 0; --var4) {
            for(HashNode var6 = var3[var4]; var6 != null; var6 = this.getEntryNext(var6)) {
               var2.add(var6.getKey());
            }
         }

         return var2;
      }

      public boolean matches(Object var1, Object var2) {
         return this.equivalenceFunction.apply2(var1, var2) != Boolean.FALSE;
      }

      public void putAll(hashtable.HashTable var1) {
         Entry[] var2 = var1.table;

         HashNode[] var3;
         try {
            var3 = (HashNode[])var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "table", -2, var2);
         }

         for(int var4 = var3.length - 1; var4 >= 0; --var4) {
            for(HashNode var6 = var3[var4]; var6 != null; var6 = var1.getEntryNext(var6)) {
               this.put(var6.getKey(), var6.getValue());
            }
         }

      }

      public Object toAlist() {
         Object var2 = LList.Empty;
         Entry[] var1 = super.table;

         HashNode[] var3;
         try {
            var3 = (HashNode[])var1;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "table", -2, var1);
         }

         for(int var4 = var3.length - 1; var4 >= 0; --var4) {
            for(HashNode var6 = var3[var4]; var6 != null; var6 = this.getEntryNext(var6)) {
               var2 = lists.cons(lists.cons(var6.getKey(), var6.getValue()), var2);
            }
         }

         return var2;
      }

      public HashNode[] toNodeArray() {
         HashNode[] var2 = new HashNode[this.size()];
         int var4 = 0;
         Entry[] var1 = super.table;

         HashNode[] var3;
         try {
            var3 = (HashNode[])var1;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "table", -2, var1);
         }

         for(int var5 = var3.length - 1; var5 >= 0; --var5) {
            for(HashNode var7 = var3[var5]; var7 != null; ++var4) {
               var2[var4] = var7;
               var7 = this.getEntryNext(var7);
            }
         }

         return var2;
      }

      public LList toNodeList() {
         Object var2 = LList.Empty;
         Entry[] var1 = super.table;

         HashNode[] var3;
         try {
            var3 = (HashNode[])var1;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "table", -2, var1);
         }

         for(int var4 = var3.length - 1; var4 >= 0; --var4) {
            for(HashNode var6 = var3[var4]; var6 != null; var6 = this.getEntryNext(var6)) {
               var2 = lists.cons(var6, var2);
            }
         }

         return (LList)var2;
      }

      public void walk(Procedure var1) {
         Entry[] var2 = super.table;

         HashNode[] var3;
         try {
            var3 = (HashNode[])var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "table", -2, var2);
         }

         for(int var4 = var3.length - 1; var4 >= 0; --var4) {
            for(HashNode var6 = var3[var4]; var6 != null; var6 = this.getEntryNext(var6)) {
               var1.apply2(var6.getKey(), var6.getValue());
            }
         }

      }
   }
}
