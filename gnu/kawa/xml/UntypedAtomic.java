package gnu.kawa.xml;


public class UntypedAtomic {

   String text;


   public UntypedAtomic(String var1) {
      this.text = var1;
   }

   public boolean equals(Object var1) {
      return var1 instanceof UntypedAtomic && this.text.equals(((UntypedAtomic)var1).text);
   }

   public int hashCode() {
      return this.text.hashCode();
   }

   public String toString() {
      return this.text;
   }
}
