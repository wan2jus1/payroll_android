package gnu.mapping;

import gnu.mapping.Environment;
import gnu.mapping.HasNamedParts;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.SymbolRef;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.util.Hashtable;

public class Namespace implements Externalizable, HasNamedParts {

   public static final Namespace EmptyNamespace = valueOf("");
   protected static final Hashtable nsTable = new Hashtable(50);
   int log2Size;
   private int mask;
   String name;
   int num_bindings;
   protected String prefix;
   protected SymbolRef[] table;


   protected Namespace() {
      this(64);
   }

   protected Namespace(int var1) {
      this.prefix = "";

      for(this.log2Size = 4; var1 > 1 << this.log2Size; ++this.log2Size) {
         ;
      }

      var1 = 1 << this.log2Size;
      this.table = new SymbolRef[var1];
      this.mask = var1 - 1;
   }

   public static Namespace create() {
      return new Namespace(64);
   }

   public static Namespace create(int var0) {
      return new Namespace(var0);
   }

   public static Namespace getDefault() {
      return EmptyNamespace;
   }

   public static Symbol getDefaultSymbol(String var0) {
      return EmptyNamespace.getSymbol(var0);
   }

   public static Namespace makeUnknownNamespace(String var0) {
      String var1;
      if(var0 != null && var0 != "") {
         var1 = "http://kawa.gnu.org/unknown-namespace/" + var0;
      } else {
         var1 = "";
      }

      return valueOf(var1, (String)var0);
   }

   public static Namespace valueOf() {
      return EmptyNamespace;
   }

   public static Namespace valueOf(String param0) {
      // $FF: Couldn't be decompiled
   }

   public static Namespace valueOf(String var0, SimpleSymbol var1) {
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.getName();
      }

      return valueOf(var0, (String)var2);
   }

   public static Namespace valueOf(String param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   public Symbol add(Symbol var1, int var2) {
      var2 &= this.mask;
      SymbolRef var3 = new SymbolRef(var1, this);
      var1.namespace = this;
      var3.next = this.table[var2];
      this.table[var2] = var3;
      ++this.num_bindings;
      if(this.num_bindings >= this.table.length) {
         this.rehash();
      }

      return var1;
   }

   public Object get(String var1) {
      return Environment.getCurrent().get((Symbol)this.getSymbol(var1));
   }

   public final String getName() {
      return this.name;
   }

   public final String getPrefix() {
      return this.prefix;
   }

   public Symbol getSymbol(String var1) {
      return this.lookup(var1, var1.hashCode(), true);
   }

   public boolean isConstant(String var1) {
      return false;
   }

   public Symbol lookup(String var1) {
      return this.lookup(var1, var1.hashCode(), false);
   }

   public Symbol lookup(String param1, int param2, boolean param3) {
      // $FF: Couldn't be decompiled
   }

   protected final Symbol lookupInternal(String var1, int var2) {
      var2 &= this.mask;
      SymbolRef var4 = null;

      SymbolRef var5;
      for(SymbolRef var3 = this.table[var2]; var3 != null; var3 = var5) {
         var5 = var3.next;
         Symbol var6 = var3.getSymbol();
         if(var6 == null) {
            if(var4 == null) {
               this.table[var2] = var5;
            } else {
               var4.next = var5;
            }

            --this.num_bindings;
         } else {
            if(var6.getLocalPart().equals(var1)) {
               return var6;
            }

            var4 = var3;
         }
      }

      return null;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.name = ((String)var1.readObject()).intern();
      this.prefix = (String)var1.readObject();
   }

   public Object readResolve() throws ObjectStreamException {
      String var1 = this.getName();
      if(var1 != null) {
         if(this.prefix != null && this.prefix.length() != 0) {
            var1 = this.prefix + " -> " + var1;
         }

         Namespace var2 = (Namespace)nsTable.get(var1);
         if(var2 != null) {
            return var2;
         }

         nsTable.put(var1, this);
      }

      return this;
   }

   protected void rehash() {
      int var7 = this.table.length;
      int var6 = var7 * 2;
      int var10 = var6 - 1;
      int var8 = 0;
      SymbolRef[] var3 = this.table;
      SymbolRef[] var4 = new SymbolRef[var6];

      while(true) {
         int var9 = var7 - 1;
         if(var9 < 0) {
            this.table = var4;
            ++this.log2Size;
            this.mask = var10;
            this.num_bindings = var8;
            return;
         }

         SymbolRef var1 = var3[var9];
         var6 = var8;

         while(true) {
            var8 = var6;
            var7 = var9;
            if(var1 == null) {
               break;
            }

            SymbolRef var2 = var1.next;
            Symbol var5 = var1.getSymbol();
            var7 = var6;
            if(var5 != null) {
               var8 = var5.getName().hashCode() & var10;
               var7 = var6 + 1;
               var1.next = var4[var8];
               var4[var8] = var1;
            }

            var1 = var2;
            var6 = var7;
         }
      }
   }

   public boolean remove(Symbol param1) {
      // $FF: Couldn't be decompiled
   }

   public final void setName(String var1) {
      this.name = var1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder("#,(namespace \"");
      var1.append(this.name);
      var1.append('\"');
      if(this.prefix != null && this.prefix != "") {
         var1.append(' ');
         var1.append(this.prefix);
      }

      var1.append(')');
      return var1.toString();
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.getName());
      var1.writeObject(this.prefix);
   }
}
