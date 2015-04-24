package gnu.mapping;

import gnu.bytecode.Type;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.WrappedException;

public class WrongType extends WrappedException {

   public static final int ARG_CAST = -4;
   public static final int ARG_DESCRIPTION = -3;
   public static final int ARG_UNKNOWN = -1;
   public static final int ARG_VARNAME = -2;
   public Object argValue;
   public Object expectedType;
   public int number;
   public Procedure proc;
   public String procname;


   public WrongType(int var1, Object var2, Type var3) {
      this.number = var1;
      this.argValue = var2;
      this.expectedType = var3;
   }

   public WrongType(Procedure var1, int var2, ClassCastException var3) {
      super((Throwable)var3);
      this.proc = var1;
      this.procname = var1.getName();
      this.number = var2;
   }

   public WrongType(Procedure var1, int var2, Object var3) {
      this.proc = var1;
      this.procname = var1.getName();
      this.number = var2;
      this.argValue = var3;
   }

   public WrongType(Procedure var1, int var2, Object var3, Type var4) {
      this.proc = var1;
      this.procname = var1.getName();
      this.number = var2;
      this.argValue = var3;
      this.expectedType = var4;
   }

   public WrongType(Procedure var1, int var2, Object var3, String var4) {
      this((String)var1.getName(), var2, var3, (String)var4);
      this.proc = var1;
   }

   public WrongType(ClassCastException var1, Procedure var2, int var3, Object var4) {
      this((Procedure)var2, var3, (ClassCastException)var1);
      this.argValue = var4;
   }

   public WrongType(ClassCastException var1, String var2, int var3, Object var4) {
      this((String)var2, var3, (ClassCastException)var1);
      this.argValue = var4;
   }

   public WrongType(String var1, int var2, ClassCastException var3) {
      super((Throwable)var3);
      this.procname = var1;
      this.number = var2;
   }

   public WrongType(String var1, int var2, Object var3, String var4) {
      this.procname = var1;
      this.number = var2;
      this.argValue = var3;
      this.expectedType = var4;
   }

   public WrongType(String var1, int var2, String var3) {
      super((String)null, (Throwable)null);
      this.procname = var1;
      this.number = var2;
      this.expectedType = var3;
   }

   public static WrongType make(ClassCastException var0, Procedure var1, int var2) {
      return new WrongType(var1, var2, var0);
   }

   public static WrongType make(ClassCastException var0, Procedure var1, int var2, Object var3) {
      WrongType var4 = new WrongType(var1, var2, var0);
      var4.argValue = var3;
      return var4;
   }

   public static WrongType make(ClassCastException var0, String var1, int var2) {
      return new WrongType(var1, var2, var0);
   }

   public static WrongType make(ClassCastException var0, String var1, int var2, Object var3) {
      WrongType var4 = new WrongType(var1, var2, var0);
      var4.argValue = var3;
      return var4;
   }

   public String getMessage() {
      StringBuffer var3 = new StringBuffer(100);
      if(this.number == -3) {
         var3.append(this.procname);
      } else if(this.number != -4 && this.number != -2) {
         var3.append("Argument ");
         if(this.number > 0) {
            var3.append('#');
            var3.append(this.number);
         }
      } else {
         var3.append("Value");
      }

      String var1;
      if(this.argValue != null) {
         var3.append(" (");
         var1 = this.argValue.toString();
         if(var1.length() > 50) {
            var3.append(var1.substring(0, 47));
            var3.append("...");
         } else {
            var3.append(var1);
         }

         var3.append(")");
      }

      if(this.procname != null && this.number != -3) {
         if(this.number == -2) {
            var1 = " for variable \'";
         } else {
            var1 = " to \'";
         }

         var3.append(var1);
         var3.append(this.procname);
         var3.append("\'");
      }

      var3.append(" has wrong type");
      if(this.argValue != null) {
         var3.append(" (");
         var3.append(this.argValue.getClass().getName());
         var3.append(")");
      }

      Object var2 = this.expectedType;
      Object var4 = var2;
      if(var2 == null) {
         var4 = var2;
         if(this.number > 0) {
            var4 = var2;
            if(this.proc instanceof MethodProc) {
               var4 = ((MethodProc)this.proc).getParameterType(this.number - 1);
            }
         }
      }

      if(var4 != null && var4 != Type.pointer_type) {
         var3.append(" (expected: ");
         if(var4 instanceof Type) {
            var2 = ((Type)var4).getName();
         } else {
            var2 = var4;
            if(var4 instanceof Class) {
               var2 = ((Class)var4).getName();
            }
         }

         var3.append(var2);
         var3.append(")");
      }

      Throwable var5 = this.getCause();
      if(var5 != null) {
         var1 = var5.getMessage();
         if(var1 != null) {
            var3.append(" (");
            var3.append(var1);
            var3.append(')');
         }
      }

      return var3.toString();
   }
}
