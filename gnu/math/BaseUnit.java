package gnu.math;

import gnu.math.Dimensions;
import gnu.math.NamedUnit;
import gnu.math.Unit;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

public class BaseUnit extends NamedUnit implements Externalizable {

   static int base_count = 0;
   private static final String unitName = "(name)";
   String dimension;
   int index;


   public BaseUnit() {
      this.name = "(name)";
      this.index = Integer.MAX_VALUE;
      this.dims = Dimensions.Empty;
   }

   public BaseUnit(String var1) {
      this.name = var1;
      this.init();
   }

   public BaseUnit(String var1, String var2) {
      this.name = var1;
      this.dimension = var2;
      this.init();
   }

   public static int compare(BaseUnit var0, BaseUnit var1) {
      int var2 = var0.name.compareTo(var1.name);
      if(var2 != 0) {
         return var2;
      } else {
         String var3 = var0.dimension;
         String var4 = var1.dimension;
         return var3 == var4?0:(var3 == null?-1:(var4 == null?1:var3.compareTo(var4)));
      }
   }

   public static BaseUnit lookup(String var0, String var1) {
      String var4 = var0.intern();
      BaseUnit var7;
      if(var4 == "(name)" && var1 == null) {
         var7 = Unit.Empty;
      } else {
         int var5 = var4.hashCode();
         int var6 = table.length;
         NamedUnit var2 = table[(Integer.MAX_VALUE & var5) % var6];

         while(true) {
            if(var2 == null) {
               return null;
            }

            if(var2.name == var4 && var2 instanceof BaseUnit) {
               BaseUnit var3 = (BaseUnit)var2;
               var7 = var3;
               if(var3.dimension == var1) {
                  break;
               }
            }

            var2 = var2.chain;
         }
      }

      return var7;
   }

   public static BaseUnit make(String var0, String var1) {
      BaseUnit var3 = lookup(var0, var1);
      BaseUnit var2 = var3;
      if(var3 == null) {
         var2 = new BaseUnit(var0, var1);
      }

      return var2;
   }

   public String getDimension() {
      return this.dimension;
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   protected void init() {
      this.base = this;
      this.scale = 1.0D;
      this.dims = new Dimensions(this);
      super.init();
      int var1 = base_count;
      base_count = var1 + 1;
      this.index = var1;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.name = var1.readUTF();
      this.dimension = (String)var1.readObject();
   }

   public Object readResolve() throws ObjectStreamException {
      BaseUnit var1 = lookup(this.name, this.dimension);
      if(var1 != null) {
         return var1;
      } else {
         this.init();
         return this;
      }
   }

   public Unit unit() {
      return this;
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeUTF(this.name);
      var1.writeObject(this.dimension);
   }
}
