package gnu.expr;

import gnu.expr.Declaration;
import gnu.expr.Language;
import gnu.expr.ScopeExp;
import gnu.kawa.util.GeneralHashTable;
import gnu.kawa.util.HashNode;
import gnu.mapping.Environment;
import gnu.mapping.Location;
import gnu.mapping.Symbol;

public class NameLookup extends GeneralHashTable {

   static final Symbol KEY = Symbol.makeUninterned("<current-NameLookup>");
   Language language;


   public NameLookup(Language var1) {
      this.language = var1;
   }

   public static NameLookup getInstance(Environment var0, Language var1) {
      Location var3 = var0.getLocation(KEY);
      NameLookup var2 = (NameLookup)var3.get((Object)null);
      if(var2 == null) {
         NameLookup var4 = new NameLookup(var1);
         var3.set(var4);
         return var4;
      } else {
         var2.setLanguage(var1);
         return var2;
      }
   }

   public static void setInstance(Environment var0, NameLookup var1) {
      if(var1 == null) {
         var0.remove((Symbol)KEY);
      } else {
         var0.put(KEY, (Object)null, var1);
      }
   }

   public Language getLanguage() {
      return this.language;
   }

   public Declaration lookup(Object var1, int var2) {
      int var5 = this.hashToIndex(this.hash(var1));

      for(HashNode var3 = ((HashNode[])this.table)[var5]; var3 != null; var3 = var3.next) {
         Declaration var4 = (Declaration)var3.getValue();
         if(var1.equals(var4.getSymbol()) && this.language.hasNamespace(var4, var2)) {
            return var4;
         }
      }

      return null;
   }

   public Declaration lookup(Object var1, boolean var2) {
      byte var3;
      if(var2) {
         var3 = 2;
      } else {
         var3 = 1;
      }

      return this.lookup(var1, var3);
   }

   public void pop(ScopeExp var1) {
      for(Declaration var2 = var1.firstDecl(); var2 != null; var2 = var2.nextDecl()) {
         this.pop((Declaration)var2);
      }

   }

   public boolean pop(Declaration var1) {
      Object var2 = var1.getSymbol();
      if(var2 == null) {
         return false;
      } else {
         int var5 = this.hash(var2);
         HashNode var3 = null;
         var5 = this.hashToIndex(var5);

         HashNode var4;
         for(HashNode var6 = ((HashNode[])this.table)[var5]; var6 != null; var6 = var4) {
            var4 = var6.next;
            if(var6.getValue() == var1) {
               if(var3 == null) {
                  ((HashNode[])this.table)[var5] = var4;
               } else {
                  var3.next = var4;
               }

               --this.num_bindings;
               return true;
            }

            var3 = var6;
         }

         return false;
      }
   }

   public void push(Declaration var1) {
      Object var2 = var1.getSymbol();
      if(var2 != null) {
         int var3 = this.num_bindings + 1;
         this.num_bindings = var3;
         if(var3 >= ((HashNode[])this.table).length) {
            this.rehash();
         }

         var3 = this.hash(var2);
         HashNode var4 = this.makeEntry(var2, var3, var1);
         var3 = this.hashToIndex(var3);
         var4.next = ((HashNode[])this.table)[var3];
         ((HashNode[])this.table)[var3] = var4;
      }
   }

   public void push(ScopeExp var1) {
      for(Declaration var2 = var1.firstDecl(); var2 != null; var2 = var2.nextDecl()) {
         this.push((Declaration)var2);
      }

   }

   public void removeSubsumed(Declaration var1) {
      int var6 = this.hashToIndex(this.hash(var1.getSymbol()));
      HashNode var3 = null;

      HashNode var4;
      for(HashNode var2 = ((HashNode[])this.table)[var6]; var2 != null; var2 = var4) {
         var4 = var2.next;
         Declaration var5 = (Declaration)var2.getValue();
         if(var5 != var1 && this.subsumedBy(var1, var5)) {
            if(var3 == null) {
               ((HashNode[])this.table)[var6] = var4;
            } else {
               var3.next = var4;
            }
         } else {
            var3 = var2;
         }
      }

   }

   public void setLanguage(Language var1) {
      this.language = var1;
   }

   protected boolean subsumedBy(Declaration var1, Declaration var2) {
      return var1.getSymbol() == var2.getSymbol() && (this.language.getNamespaceOf(var1) & this.language.getNamespaceOf(var2)) != 0;
   }
}
