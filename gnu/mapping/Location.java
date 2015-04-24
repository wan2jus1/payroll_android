package gnu.mapping;

import gnu.mapping.IndirectableLocation;
import gnu.mapping.Namespace;
import gnu.mapping.PlainLocation;
import gnu.mapping.Symbol;
import gnu.mapping.ThreadLocation;
import gnu.mapping.UnboundLocationException;
import java.io.PrintWriter;

public abstract class Location {

   public static final String UNBOUND = new String("(unbound)");


   public static IndirectableLocation make(Symbol var0) {
      PlainLocation var1 = new PlainLocation(var0, (Object)null);
      var1.base = null;
      var1.value = UNBOUND;
      return var1;
   }

   public static IndirectableLocation make(String var0) {
      PlainLocation var1 = new PlainLocation(Namespace.EmptyNamespace.getSymbol(var0.intern()), (Object)null);
      var1.base = null;
      var1.value = UNBOUND;
      return var1;
   }

   public static Location make(Object var0, String var1) {
      ThreadLocation var2 = new ThreadLocation(var1);
      var2.setGlobal(var0);
      return var2;
   }

   public boolean entered() {
      return false;
   }

   public final Object get() {
      String var1 = UNBOUND;
      Object var2 = this.get(var1);
      if(var2 == var1) {
         throw new UnboundLocationException(this);
      } else {
         return var2;
      }
   }

   public abstract Object get(Object var1);

   public Location getBase() {
      return this;
   }

   public Object getKeyProperty() {
      return null;
   }

   public Symbol getKeySymbol() {
      return null;
   }

   public final Object getValue() {
      return this.get((Object)null);
   }

   public boolean isBound() {
      String var1 = UNBOUND;
      return this.get(var1) != var1;
   }

   public boolean isConstant() {
      return false;
   }

   public void print(PrintWriter var1) {
      var1.print("#<location ");
      Symbol var2 = this.getKeySymbol();
      if(var2 != null) {
         var1.print(var2);
      }

      String var4 = UNBOUND;
      Object var3 = this.get(var4);
      if(var3 != var4) {
         var1.print(" -> ");
         var1.print(var3);
      } else {
         var1.print("(unbound)");
      }

      var1.print('>');
   }

   public abstract void set(Object var1);

   public void setRestore(Object var1) {
      this.set(var1);
   }

   public final Object setValue(Object var1) {
      Object var2 = this.get((Object)null);
      this.set(var1);
      return var2;
   }

   public Object setWithSave(Object var1) {
      Object var2 = this.get(UNBOUND);
      this.set(var1);
      return var2;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(this.getClass().getName());
      Symbol var2 = this.getKeySymbol();
      var1.append('[');
      if(var2 != null) {
         var1.append(var2);
         Object var3 = this.getKeyProperty();
         if(var3 != null && var3 != this) {
            var1.append('/');
            var1.append(var3);
         }
      }

      var1.append("]");
      return var1.toString();
   }

   public void undefine() {
      this.set(UNBOUND);
   }
}
