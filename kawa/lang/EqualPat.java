package kawa.lang;

import gnu.lists.Consumer;
import gnu.mapping.Symbol;
import gnu.text.Printable;
import gnu.text.ReportFormat;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import kawa.lang.Pattern;

public class EqualPat extends Pattern implements Printable, Externalizable {

   Object value;


   public EqualPat() {
   }

   public EqualPat(Object var1) {
      this.value = var1;
   }

   public static EqualPat make(Object var0) {
      return new EqualPat(var0);
   }

   public boolean match(Object var1, Object[] var2, int var3) {
      Object var4 = var1;
      if(this.value instanceof String) {
         var4 = var1;
         if(var1 instanceof Symbol) {
            var4 = ((Symbol)var1).getName();
         }
      }

      return this.value.equals(var4);
   }

   public void print(Consumer var1) {
      var1.write("#<equals: ");
      ReportFormat.print((Object)this.value, (Consumer)var1);
      var1.write(62);
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.value = var1.readObject();
   }

   public int varCount() {
      return 0;
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.value);
   }
}
