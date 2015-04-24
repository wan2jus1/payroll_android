package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.expr.Language;
import gnu.kawa.reflect.FieldLocation;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.mapping.Environment;
import gnu.mapping.Location;
import gnu.mapping.Named;
import gnu.mapping.Symbol;
import gnu.mapping.UnboundLocationException;
import gnu.mapping.WrappedException;
import java.lang.reflect.Field;

public abstract class ClassMemberLocation extends Location {

   Object instance;
   String mname;
   Field rfield;
   ClassType type;


   public ClassMemberLocation(Object var1, ClassType var2, String var3) {
      this.instance = var1;
      this.type = var2;
      this.mname = var3;
   }

   public ClassMemberLocation(Object var1, Class var2, String var3) {
      this.instance = var1;
      this.type = (ClassType)Type.make(var2);
      this.mname = var3;
   }

   public ClassMemberLocation(Object var1, Field var2) {
      this.instance = var1;
      this.rfield = var2;
      this.mname = var2.getName();
   }

   public static void define(Object var0, Field var1, String var2, Language var3, Environment var4) throws IllegalAccessException {
      Object var7 = var1.get(var0);
      Type var5 = Type.make(var1.getType());
      boolean var10 = var5.isSubtype(Compilation.typeLocation);
      var5.isSubtype(Compilation.typeProcedure);
      int var9 = var1.getModifiers();
      boolean var8;
      if((var9 & 16) != 0) {
         var8 = true;
      } else {
         var8 = false;
      }

      Object var12;
      if(var8 && var7 instanceof Named && !var10) {
         var12 = ((Named)var7).getSymbol();
      } else {
         var12 = Compilation.demangleName(var1.getName(), true);
      }

      String var6;
      Symbol var13;
      if(var12 instanceof Symbol) {
         var13 = (Symbol)var12;
      } else {
         var6 = var2;
         if(var2 == null) {
            var6 = "";
         }

         var13 = Symbol.make(var6, var12.toString().intern());
      }

      var6 = null;
      Object var11 = null;
      if(var10 && var8) {
         var0 = (Location)var7;
      } else {
         var11 = var6;
         if(var8) {
            var11 = var3.getEnvPropertyFor(var1, var7);
         }

         if((var9 & 8) != 0) {
            var8 = true;
         } else {
            var8 = false;
         }

         if(var8) {
            var0 = new StaticFieldLocation(var1);
         } else {
            var0 = new FieldLocation(var0, var1);
         }
      }

      var4.addLocation(var13, var11, (Location)var0);
   }

   public static void defineAll(Object var0, Language var1, Environment var2) throws IllegalAccessException {
      Field[] var3 = var0.getClass().getFields();
      int var6 = var3.length;

      while(true) {
         int var7 = var6 - 1;
         if(var7 < 0) {
            return;
         }

         Field var4 = var3[var7];
         String var5 = var4.getName();
         var6 = var7;
         if(!var5.startsWith("$Prvt$")) {
            var6 = var7;
            if(!var5.endsWith("$instance")) {
               define(var0, var4, (String)null, var1, var2);
               var6 = var7;
            }
         }
      }
   }

   public Object get(Object var1) {
      Field var2 = this.getRField();
      if(var2 == null) {
         return var1;
      } else {
         try {
            var1 = var2.get(this.instance);
            return var1;
         } catch (IllegalAccessException var3) {
            throw WrappedException.wrapIfNeeded(var3);
         }
      }
   }

   public ClassType getDeclaringClass() {
      return this.type;
   }

   public final Object getInstance() {
      return this.instance;
   }

   public String getMemberName() {
      return this.mname;
   }

   public Class getRClass() {
      Field var1 = this.rfield;
      if(var1 != null) {
         return var1.getDeclaringClass();
      } else {
         try {
            Class var3 = this.type.getReflectClass();
            return var3;
         } catch (Exception var2) {
            return null;
         }
      }
   }

   public Field getRField() {
      Field var2 = this.rfield;
      Field var1 = var2;
      if(var2 == null) {
         try {
            var1 = this.type.getReflectClass().getField(this.mname);
            this.rfield = var1;
         } catch (Exception var3) {
            return null;
         }
      }

      return var1;
   }

   public boolean isBound() {
      return this.getRField() != null;
   }

   public boolean isConstant() {
      return this.getRField() != null && (this.rfield.getModifiers() & 16) != 0;
   }

   public void set(Object var1) {
      this.setup();

      try {
         this.rfield.set(this.instance, var1);
      } catch (IllegalAccessException var2) {
         throw WrappedException.wrapIfNeeded(var2);
      }
   }

   public final void setInstance(Object var1) {
      this.instance = var1;
   }

   void setup() {
      if(this.rfield == null) {
         Class var1;
         UnboundLocationException var2;
         try {
            var1 = this.type.getReflectClass();
         } catch (RuntimeException var4) {
            var2 = new UnboundLocationException((Object)null, "Unbound location - " + var4.toString());
            var2.initCause(var4);
            throw var2;
         }

         try {
            this.rfield = var1.getField(this.mname);
         } catch (NoSuchFieldException var3) {
            var2 = new UnboundLocationException((Object)null, "Unbound location  - no field " + this.mname + " in " + this.type.getName());
            var2.initCause(var3);
            throw var2;
         }
      }

   }
}
