package gnu.kawa.util;

import java.util.Map.Entry;

public class HashNode implements Entry {

   int hash;
   Object key;
   public HashNode next;
   Object value;


   public HashNode(Object var1, Object var2) {
      this.key = var1;
      this.value = var2;
   }

   public boolean equals(Object var1) {
      if(var1 instanceof HashNode) {
         HashNode var2 = (HashNode)var1;
         if(this.key == null) {
            if(var2.key != null) {
               return false;
            }
         } else if(!this.key.equals(var2.key)) {
            return false;
         }

         if(this.value == null) {
            if(var2.value != null) {
               return false;
            }
         } else if(!this.value.equals(var2.value)) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   public Object get(Object var1) {
      return this.getValue();
   }

   public Object getKey() {
      return this.key;
   }

   public Object getValue() {
      return this.value;
   }

   public int hashCode() {
      int var2 = 0;
      int var1;
      if(this.key == null) {
         var1 = 0;
      } else {
         var1 = this.key.hashCode();
      }

      if(this.value != null) {
         var2 = this.value.hashCode();
      }

      return var1 ^ var2;
   }

   public Object setValue(Object var1) {
      Object var2 = this.value;
      this.value = var1;
      return var2;
   }
}
