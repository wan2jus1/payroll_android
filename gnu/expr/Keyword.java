package gnu.expr;

import gnu.expr.Special;
import gnu.expr.Symbols;
import gnu.lists.Consumer;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import gnu.text.Printable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

public class Keyword extends Symbol implements Printable, Externalizable {

   public static final Namespace keywordNamespace = Namespace.create();


   static {
      keywordNamespace.setName("(keywords)");
   }

   public Keyword() {
   }

   public Keyword(Namespace var1, String var2) {
      super(var1, var2);
   }

   private Keyword(String var1) {
      super(keywordNamespace, var1);
   }

   public static boolean isKeyword(Object var0) {
      return var0 instanceof Keyword;
   }

   public static Keyword make(String var0) {
      int var3 = var0.hashCode();
      Keyword var2 = (Keyword)keywordNamespace.lookup(var0, var3, false);
      Keyword var1 = var2;
      if(var2 == null) {
         var1 = new Keyword(var0);
         keywordNamespace.add(var1, var3);
      }

      return var1;
   }

   public static Object searchForKeyword(Object[] var0, int var1, Object var2) {
      while(var1 < var0.length) {
         if(var0[var1] == var2) {
            return var0[var1 + 1];
         }

         var1 += 2;
      }

      return Special.dfault;
   }

   public static Object searchForKeyword(Object[] var0, int var1, Object var2, Object var3) {
      while(true) {
         Object var4 = var3;
         if(var1 < var0.length) {
            if(var0[var1] != var2) {
               var1 += 2;
               continue;
            }

            var4 = var0[var1 + 1];
         }

         return var4;
      }
   }

   public Symbol asSymbol() {
      return Namespace.EmptyNamespace.getSymbol(this.getName());
   }

   public void print(Consumer var1) {
      Symbols.print(this.getName(), var1);
      var1.write(58);
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.name = (String)var1.readObject();
   }

   public Object readResolve() throws ObjectStreamException {
      return make(keywordNamespace, this.getName());
   }

   public final String toString() {
      return this.getName() + ':';
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.getName());
   }
}
