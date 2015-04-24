package gnu.kawa.lispexpr;

import gnu.kawa.lispexpr.NamespaceUse;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;

public class LispPackage extends Namespace {

   Namespace exported;
   NamespaceUse imported;
   NamespaceUse importing;
   LList shadowingSymbols;


   public LispPackage() {
      this.shadowingSymbols = LList.Empty;
   }

   private void addToShadowingSymbols(Symbol var1) {
      Pair var3;
      for(Object var2 = this.shadowingSymbols; var2 != LList.Empty; var2 = var3.getCdr()) {
         var3 = (Pair)var2;
         if(var3.getCar() == var1) {
            return;
         }
      }

      this.shadowingSymbols = new Pair(var1, this.shadowingSymbols);
   }

   private boolean removeFromShadowingSymbols(Symbol var1) {
      Pair var2 = null;

      Pair var4;
      for(Object var3 = this.shadowingSymbols; var3 != LList.Empty; var2 = var4) {
         var4 = (Pair)var3;
         var3 = var4.getCdr();
         if(var4.getCar() == var1) {
            if(var2 == null) {
               this.shadowingSymbols = (LList)var3;
            } else {
               var2.setCdr(var3);
            }

            return true;
         }
      }

      return false;
   }

   public boolean isPresent(String var1) {
      boolean var2 = false;
      if(this.lookupPresent(var1, var1.hashCode(), false) != null) {
         var2 = true;
      }

      return var2;
   }

   public Symbol lookup(String var1, int var2, boolean var3) {
      Symbol var4 = this.exported.lookup(var1, var2, false);
      if(var4 != null) {
         return var4;
      } else {
         var4 = this.lookupInternal(var1, var2);
         if(var4 != null) {
            return var4;
         } else {
            for(NamespaceUse var6 = this.imported; var6 != null; var6 = var6.nextImported) {
               Symbol var5 = this.lookup(var1, var2, false);
               if(var5 != null) {
                  return var5;
               }
            }

            if(var3) {
               return this.add(new Symbol(this, var1), var2);
            } else {
               return null;
            }
         }
      }
   }

   public Symbol lookupPresent(String var1, int var2, boolean var3) {
      Symbol var5 = this.exported.lookup(var1, var2, false);
      Symbol var4 = var5;
      if(var5 == null) {
         var4 = super.lookup(var1, var2, var3);
      }

      return var4;
   }

   public void shadow(String var1) {
      this.addToShadowingSymbols(this.lookupPresent(var1, var1.hashCode(), true));
   }

   public void shadowingImport(Symbol var1) {
      String var2 = var1.getName();
      var2.hashCode();
      Symbol var3 = this.lookupPresent(var2, var2.hashCode(), false);
      if(var3 != null && var3 != var1) {
         this.unintern(var3);
      }

      this.addToShadowingSymbols(var1);
   }

   public boolean unintern(Symbol var1) {
      boolean var4 = false;
      String var2 = var1.getName();
      int var3 = var2.hashCode();
      if(this.exported.lookup(var2, var3, false) == var1) {
         this.exported.remove(var1);
      } else {
         if(super.lookup(var2, var3, false) != var1) {
            return var4;
         }

         super.remove(var1);
      }

      var1.setNamespace((Namespace)null);
      if(this.removeFromShadowingSymbols(var1)) {
         ;
      }

      var4 = true;
      return var4;
   }
}
