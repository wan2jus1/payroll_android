package gnu.mapping;

import gnu.mapping.EnvironmentKey;
import gnu.mapping.Namespace;
import gnu.mapping.SimpleSymbol;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

public class Symbol implements EnvironmentKey, Comparable, Externalizable {

   public static final Symbol FUNCTION = makeUninterned("(function)");
   public static final Symbol PLIST = makeUninterned("(property-list)");
   protected String name;
   Namespace namespace;


   public Symbol() {
   }

   public Symbol(Namespace var1, String var2) {
      this.name = var2;
      this.namespace = var1;
   }

   public static boolean equals(Symbol var0, Symbol var1) {
      if(var0 != var1) {
         if(var0 == null || var1 == null) {
            return false;
         } else {
            if(var0.name == var1.name) {
               Namespace var2 = var0.namespace;
               Namespace var3 = var1.namespace;
               if(var2 != null && var3 != null) {
                  if(var2.name != var3.name) {
                     return false;
                  }

                  return true;
               }
            }

            return false;
         }
      } else {
         return true;
      }
   }

   public static Symbol make(Object var0, String var1) {
      Namespace var2;
      if(var0 instanceof String) {
         var2 = Namespace.valueOf((String)var0);
      } else {
         var2 = (Namespace)var0;
      }

      return var2 != null && var1 != null?var2.getSymbol(var1.intern()):makeUninterned(var1);
   }

   public static Symbol make(String var0, String var1, String var2) {
      return Namespace.valueOf(var0, (String)var2).getSymbol(var1.intern());
   }

   public static Symbol makeUninterned(String var0) {
      return new Symbol((Namespace)null, var0);
   }

   public static Symbol makeWithUnknownNamespace(String var0, String var1) {
      return Namespace.makeUnknownNamespace(var1).getSymbol(var0.intern());
   }

   public static Symbol parse(String var0) {
      int var13 = var0.length();
      int var5 = -1;
      byte var11 = -1;
      int var8 = 0;
      byte var12 = 0;
      int var6 = 0;
      int var3 = 0;

      int var4;
      int var7;
      int var9;
      int var10;
      while(true) {
         var9 = var5;
         var7 = var12;
         var4 = var6;
         var10 = var11;
         if(var3 >= var13) {
            break;
         }

         char var14 = var0.charAt(var3);
         if(var14 == 58 && var8 == 0) {
            var4 = var3;
            var7 = var3 + 1;
            var10 = var11;
            var9 = var5;
            break;
         }

         var9 = var8;
         var7 = var5;
         var4 = var6;
         if(var14 == 123) {
            var7 = var5;
            var4 = var6;
            if(var5 < 0) {
               var4 = var3;
               var7 = var3;
            }

            var9 = var8 + 1;
         }

         var8 = var9;
         if(var14 == 125) {
            var5 = var9 - 1;
            if(var5 == 0) {
               var10 = var3;
               if(var3 < var13 && var0.charAt(var3 + 1) == 58) {
                  var3 += 2;
               } else {
                  ++var3;
               }

               var9 = var7;
               var7 = var3;
               break;
            }

            var8 = var5;
            if(var5 < 0) {
               var9 = var7;
               var7 = var4;
               var10 = var11;
               break;
            }
         }

         ++var3;
         var5 = var7;
         var6 = var4;
      }

      if(var9 >= 0 && var10 > 0) {
         String var2 = var0.substring(var9 + 1, var10);
         String var1;
         if(var4 > 0) {
            var1 = var0.substring(0, var4);
         } else {
            var1 = null;
         }

         return valueOf(var0.substring(var7), var2, var1);
      } else {
         return (Symbol)(var4 > 0?makeWithUnknownNamespace(var0.substring(var7), var0.substring(0, var4)):valueOf(var0));
      }
   }

   public static SimpleSymbol valueOf(String var0) {
      return (SimpleSymbol)Namespace.EmptyNamespace.getSymbol(var0.intern());
   }

   public static Symbol valueOf(String var0, Object var1) {
      if(var1 != null && var1 != Boolean.FALSE) {
         Namespace var2;
         if(var1 instanceof Namespace) {
            var2 = (Namespace)var1;
         } else if(var1 == Boolean.TRUE) {
            var2 = Namespace.EmptyNamespace;
         } else {
            var2 = Namespace.valueOf(((CharSequence)var1).toString());
         }

         return var2.getSymbol(var0.intern());
      } else {
         return makeUninterned(var0);
      }
   }

   public static Symbol valueOf(String var0, String var1, String var2) {
      return Namespace.valueOf(var1, (String)var2).getSymbol(var0.intern());
   }

   public int compareTo(Object var1) {
      Symbol var2 = (Symbol)var1;
      if(this.getNamespaceURI() != var2.getNamespaceURI()) {
         throw new IllegalArgumentException("comparing Symbols in different namespaces");
      } else {
         return this.getLocalName().compareTo(var2.getLocalName());
      }
   }

   public final boolean equals(Object var1) {
      return var1 instanceof Symbol && equals(this, (Symbol)var1);
   }

   public final Object getKeyProperty() {
      return null;
   }

   public final Symbol getKeySymbol() {
      return this;
   }

   public final String getLocalName() {
      return this.name;
   }

   public final String getLocalPart() {
      return this.name;
   }

   public final String getName() {
      return this.name;
   }

   public final Namespace getNamespace() {
      return this.namespace;
   }

   public final String getNamespaceURI() {
      Namespace var1 = this.getNamespace();
      return var1 == null?null:var1.getName();
   }

   public final String getPrefix() {
      Namespace var1 = this.namespace;
      return var1 == null?"":var1.prefix;
   }

   public final boolean hasEmptyNamespace() {
      Namespace var1 = this.getNamespace();
      if(var1 != null) {
         String var2 = var1.getName();
         if(var2 != null && var2.length() != 0) {
            return false;
         }
      }

      return true;
   }

   public int hashCode() {
      return this.name == null?0:this.name.hashCode();
   }

   public boolean matches(EnvironmentKey var1) {
      return equals(var1.getKeySymbol(), this) && var1.getKeyProperty() == null;
   }

   public boolean matches(Symbol var1, Object var2) {
      return equals(var1, this) && var2 == null;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.namespace = (Namespace)var1.readObject();
      this.name = (String)var1.readObject();
   }

   public Object readResolve() throws ObjectStreamException {
      return this.namespace == null?this:make(this.namespace, this.getName());
   }

   public final void setNamespace(Namespace var1) {
      this.namespace = var1;
   }

   public String toString() {
      return this.toString('P');
   }

   public String toString(char var1) {
      boolean var6 = true;
      String var2 = this.getNamespaceURI();
      String var4 = this.getPrefix();
      boolean var5;
      if(var2 != null && var2.length() > 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      if(var4 == null || var4.length() <= 0) {
         var6 = false;
      }

      String var3 = this.getName();
      if(!var5) {
         var2 = var3;
         if(!var6) {
            return var2;
         }
      }

      StringBuilder var7 = new StringBuilder();
      if(var6 && (var1 != 85 || !var5)) {
         var7.append(var4);
      }

      if(var5 && (var1 != 80 || !var6)) {
         var7.append('{');
         var7.append(this.getNamespaceURI());
         var7.append('}');
      }

      var7.append(':');
      var7.append(var3);
      var2 = var7.toString();
      return var2;
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.getNamespace());
      var1.writeObject(this.getName());
   }
}
