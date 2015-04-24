package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.kawa.reflect.ClassMemberLocation;
import gnu.mapping.Location;
import gnu.mapping.Procedure;
import gnu.mapping.WrappedException;
import java.lang.reflect.Field;
import kawa.lang.Syntax;

public class FieldLocation extends ClassMemberLocation {

   static final int CONSTANT = 4;
   static final int INDIRECT_LOCATION = 2;
   public static final int KIND_FLAGS_SET = 64;
   public static final int PROCEDURE = 16;
   static final int SETUP_DONE = 1;
   public static final int SYNTAX = 32;
   static final int VALUE_SET = 8;
   Declaration decl;
   private int flags;
   Object value;


   public FieldLocation(Object var1, ClassType var2, String var3) {
      super(var1, (ClassType)var2, var3);
   }

   public FieldLocation(Object var1, String var2, String var3) {
      super(var1, (ClassType)ClassType.make(var2), var3);
   }

   public FieldLocation(Object var1, Field var2) {
      super(var1, var2);
      this.type = (ClassType)Type.make(var2.getDeclaringClass());
   }

   private Object getFieldValue() {
      super.setup();

      try {
         Object var1 = this.rfield.get(this.instance);
         return var1;
      } catch (Throwable var2) {
         throw WrappedException.wrapIfNeeded(var2);
      }
   }

   public static FieldLocation make(Object var0, Declaration var1) {
      gnu.bytecode.Field var2 = var1.field;
      FieldLocation var3 = new FieldLocation(var0, var2.getDeclaringClass(), var2.getName());
      var3.setDeclaration(var1);
      return var3;
   }

   public static FieldLocation make(Object var0, String var1, String var2) {
      return new FieldLocation(var0, ClassType.make(var1), var2);
   }

   public Object get(Object var1) {
      try {
         this.setup();
      } catch (Throwable var5) {
         return var1;
      }

      Object var2;
      Object var3;
      if((this.flags & 8) != 0) {
         var3 = this.value;
         var2 = var3;
         if((this.flags & 4) != 0) {
            return var3;
         }
      } else {
         var3 = this.getFieldValue();
         var2 = var3;
         if((this.type.getDeclaredField(this.mname).getModifiers() & 16) != 0) {
            this.flags |= 8;
            if((this.flags & 2) == 0) {
               this.flags |= 4;
            }

            this.value = var3;
            var2 = var3;
         }
      }

      var3 = var2;
      if((this.flags & 2) != 0) {
         String var6 = Location.UNBOUND;
         Location var4 = (Location)var2;
         var2 = var4.get(var6);
         if(var2 == var6) {
            return var1;
         }

         var3 = var2;
         if(var4.isConstant()) {
            this.flags |= 4;
            this.value = var2;
            return var2;
         }
      }

      return var3;
   }

   public Declaration getDeclaration() {
      // $FF: Couldn't be decompiled
   }

   public Type getFType() {
      return this.type.getDeclaredField(this.mname).getType();
   }

   public gnu.bytecode.Field getField() {
      return this.type.getDeclaredField(this.mname);
   }

   public boolean isBound() {
      if((this.flags & 64) == 0) {
         this.setKindFlags();
      }

      if((this.flags & 4) == 0 && (this.flags & 2) != 0) {
         Object var1;
         if((this.flags & 8) != 0) {
            var1 = this.value;
         } else {
            try {
               this.setup();
            } catch (Throwable var2) {
               return false;
            }

            var1 = this.getFieldValue();
            this.flags |= 8;
            this.value = var1;
         }

         return ((Location)var1).isBound();
      } else {
         return true;
      }
   }

   public boolean isConstant() {
      boolean var2 = false;
      if((this.flags & 64) == 0) {
         this.setKindFlags();
      }

      if((this.flags & 4) != 0) {
         var2 = true;
      } else if(this.isIndirectLocation()) {
         Object var1;
         if((this.flags & 8) != 0) {
            var1 = this.value;
         } else {
            try {
               this.setup();
            } catch (Throwable var3) {
               return false;
            }

            var1 = this.getFieldValue();
            this.flags |= 8;
            this.value = var1;
         }

         return ((Location)var1).isConstant();
      }

      return var2;
   }

   public boolean isIndirectLocation() {
      return (this.flags & 2) != 0;
   }

   public boolean isProcedureOrSyntax() {
      if((this.flags & 64) == 0) {
         this.setKindFlags();
      }

      return (this.flags & 48) != 0;
   }

   public void set(Object var1) {
      this.setup();
      if((this.flags & 2) == 0) {
         try {
            this.rfield.set(this.instance, var1);
         } catch (Throwable var3) {
            throw WrappedException.wrapIfNeeded(var3);
         }
      } else {
         Object var2;
         if((this.flags & 8) != 0) {
            var2 = this.value;
         } else {
            this.flags |= 8;
            var2 = this.getFieldValue();
            this.value = var2;
         }

         ((Location)var2).set(var1);
      }
   }

   public void setDeclaration(Declaration var1) {
      this.decl = var1;
   }

   void setKindFlags() {
      String var1 = this.getMemberName();
      gnu.bytecode.Field var3 = this.getDeclaringClass().getDeclaredField(var1);
      int var2 = var3.getModifiers();
      Type var4 = var3.getType();
      if(var4.isSubtype(Compilation.typeLocation)) {
         this.flags |= 2;
      }

      if((var2 & 16) != 0) {
         if((this.flags & 2) == 0) {
            this.flags |= 4;
            if(var4.isSubtype(Compilation.typeProcedure)) {
               this.flags |= 16;
            }

            if(var4 instanceof ClassType && ((ClassType)var4).isSubclass((String)"kawa.lang.Syntax")) {
               this.flags |= 32;
            }
         } else {
            Location var5 = (Location)this.getFieldValue();
            if(var5 instanceof FieldLocation) {
               FieldLocation var6 = (FieldLocation)var5;
               if((var6.flags & 64) == 0) {
                  var6.setKindFlags();
               }

               this.flags |= var6.flags & 52;
               if((var6.flags & 4) != 0) {
                  if((var6.flags & 8) != 0) {
                     this.value = var6.value;
                     this.flags |= 8;
                  }
               } else {
                  this.value = var6;
                  this.flags |= 8;
               }
            } else if(var5.isConstant()) {
               Object var7 = var5.get((Object)null);
               if(var7 instanceof Procedure) {
                  this.flags |= 16;
               }

               if(var7 instanceof Syntax) {
                  this.flags |= 32;
               }

               this.flags |= 12;
               this.value = var7;
            }
         }
      }

      this.flags |= 64;
   }

   public void setProcedure() {
      this.flags |= 84;
   }

   public void setRestore(Object var1) {
      if((this.flags & 2) == 0) {
         super.setRestore(var1);
      } else {
         ((Location)this.value).setRestore(var1);
      }
   }

   public void setSyntax() {
      this.flags |= 100;
   }

   public Object setWithSave(Object var1) {
      if((this.flags & 64) == 0) {
         this.setKindFlags();
      }

      if((this.flags & 2) == 0) {
         return super.setWithSave(var1);
      } else {
         Object var2;
         if((this.flags & 8) != 0) {
            var2 = this.value;
         } else {
            this.flags |= 8;
            var2 = this.getFieldValue();
            this.value = var2;
         }

         return ((Location)var2).setWithSave(var1);
      }
   }

   void setup() {
      // $FF: Couldn't be decompiled
   }

   public String toString() {
      StringBuffer var2 = new StringBuffer();
      var2.append("FieldLocation[");
      if(this.instance != null) {
         var2.append(this.instance);
         var2.append(' ');
      }

      String var1;
      if(this.type == null) {
         var1 = "(null)";
      } else {
         var1 = this.type.getName();
      }

      var2.append(var1);
      var2.append('.');
      var2.append(this.mname);
      var2.append(']');
      return var2.toString();
   }
}
