package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.Filter;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;

class MethodFilter implements Filter {

   ClassType caller;
   int modifiers;
   int modmask;
   String name;
   int nlen;
   ObjectType receiver;


   public MethodFilter(String var1, int var2, int var3, ClassType var4, ObjectType var5) {
      this.name = var1;
      this.nlen = var1.length();
      this.modifiers = var2;
      this.modmask = var3;
      this.caller = var4;
      this.receiver = var5;
   }

   public boolean select(Object var1) {
      Method var2 = (Method)var1;
      String var6 = var2.getName();
      int var3 = var2.getModifiers();
      if((this.modmask & var3) == this.modifiers && (var3 & 4096) == 0 && var6.startsWith(this.name)) {
         var3 = var6.length();
         if(var3 != this.nlen) {
            label48: {
               if(var3 == this.nlen + 2 && var6.charAt(this.nlen) == 36) {
                  char var4 = var6.charAt(this.nlen + 1);
                  if(var4 == 86 || var4 == 88) {
                     break label48;
                  }
               }

               if(var3 != this.nlen + 4 || !var6.endsWith("$V$X")) {
                  return false;
               }
            }
         }

         ClassType var7;
         if(this.receiver instanceof ClassType) {
            var7 = (ClassType)this.receiver;
         } else {
            var7 = var2.getDeclaringClass();
         }

         boolean var5;
         if(this.caller != null && !this.caller.isAccessible(var7, this.receiver, var2.getModifiers())) {
            var5 = false;
         } else {
            var5 = true;
         }

         return var5;
      } else {
         return false;
      }
   }
}
