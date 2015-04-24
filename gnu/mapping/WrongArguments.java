package gnu.mapping;

import gnu.mapping.Procedure;

public class WrongArguments extends IllegalArgumentException {

   public int number;
   Procedure proc;
   public String procname;
   public String usage;


   public WrongArguments(Procedure var1, int var2) {
      this.proc = var1;
      this.number = var2;
   }

   public WrongArguments(String var1, int var2, String var3) {
      this.procname = var1;
      this.number = var2;
      this.usage = var3;
   }

   public static String checkArgCount(Procedure var0, int var1) {
      int var4 = var0.numArgs();
      String var3 = var0.getName();
      String var2 = var3;
      if(var3 == null) {
         var2 = var0.getClass().getName();
      }

      return checkArgCount(var2, var4 & 4095, var4 >> 12, var1);
   }

   public static String checkArgCount(String var0, int var1, int var2, int var3) {
      boolean var5;
      if(var3 < var1) {
         var5 = false;
      } else {
         if(var2 < 0 || var3 <= var2) {
            return null;
         }

         var5 = true;
      }

      StringBuffer var4 = new StringBuffer(100);
      var4.append("call to ");
      if(var0 == null) {
         var4.append("unnamed procedure");
      } else {
         var4.append('\'');
         var4.append(var0);
         var4.append('\'');
      }

      if(var5) {
         var0 = " has too many";
      } else {
         var0 = " has too few";
      }

      var4.append(var0);
      var4.append(" arguments (");
      var4.append(var3);
      if(var1 == var2) {
         var4.append("; must be ");
         var4.append(var1);
      } else {
         var4.append("; min=");
         var4.append(var1);
         if(var2 >= 0) {
            var4.append(", max=");
            var4.append(var2);
         }
      }

      var4.append(')');
      return var4.toString();
   }

   public String getMessage() {
      if(this.proc != null) {
         String var1 = checkArgCount(this.proc, this.number);
         if(var1 != null) {
            return var1;
         }
      }

      return super.getMessage();
   }
}
