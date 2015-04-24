package gnu.bytecode;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Label;
import gnu.bytecode.Location;
import gnu.bytecode.Scope;
import gnu.bytecode.Type;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class Variable extends Location implements Enumeration {

   private static final int LIVE_FLAG = 4;
   private static final int PARAMETER_FLAG = 2;
   private static final int SIMPLE_FLAG = 1;
   static final int UNASSIGNED = -1;
   private int flags = 1;
   Variable next;
   int offset = -1;
   Scope scope;


   public Variable() {
   }

   public Variable(String var1) {
      this.setName(var1);
   }

   public Variable(String var1, Type var2) {
      this.setName(var1);
      this.setType(var2);
   }

   private void setFlag(boolean var1, int var2) {
      if(var1) {
         this.flags |= var2;
      } else {
         this.flags &= ~var2;
      }
   }

   public void allocateLocal(CodeAttr var1) {
      if(this.offset == -1) {
         for(int var2 = 0; !this.reserveLocal(var2, var1); ++var2) {
            ;
         }
      }

   }

   public final boolean dead() {
      return (this.flags & 4) == 0;
   }

   public void freeLocal(CodeAttr var1) {
      this.flags &= -5;
      byte var3;
      if(this.getType().size > 4) {
         var3 = 2;
      } else {
         var3 = 1;
      }

      int var5 = this.offset + var3;

      while(true) {
         int var4 = var5 - 1;
         if(var4 < this.offset) {
            return;
         }

         var1.locals.used[var4] = null;
         Type[] var2 = var1.local_types;
         var5 = var4;
         if(var2 != null) {
            var2[var4] = null;
            var5 = var4;
         }
      }
   }

   public final boolean hasMoreElements() {
      return this.next != null;
   }

   public final boolean isAssigned() {
      return this.offset != -1;
   }

   public final boolean isParameter() {
      return (this.flags & 2) != 0;
   }

   public final boolean isSimple() {
      return (this.flags & 1) != 0;
   }

   public Object nextElement() {
      if(this.next == null) {
         throw new NoSuchElementException("Variable enumeration");
      } else {
         return this.next;
      }
   }

   public final Variable nextVar() {
      return this.next;
   }

   public boolean reserveLocal(int var1, CodeAttr var2) {
      int var5 = this.getType().getSizeInWords();
      if(var2.locals.used == null) {
         var2.locals.used = new Variable[var5 + 20];
      } else if(var2.getMaxLocals() + var5 >= var2.locals.used.length) {
         Variable[] var3 = new Variable[var2.locals.used.length * 2 + var5];
         System.arraycopy(var2.locals.used, 0, var3, 0, var2.getMaxLocals());
         var2.locals.used = var3;
      }

      int var4;
      for(var4 = 0; var4 < var5; ++var4) {
         if(var2.locals.used[var1 + var4] != null) {
            return false;
         }
      }

      for(var4 = 0; var4 < var5; ++var4) {
         var2.locals.used[var1 + var4] = this;
      }

      if(var1 + var5 > var2.getMaxLocals()) {
         var2.setMaxLocals(var1 + var5);
      }

      this.offset = var1;
      this.flags |= 4;
      if(this.offset == 0 && "<init>".equals(var2.getMethod().getName())) {
         this.setType(var2.local_types[0]);
      }

      return true;
   }

   public final void setParameter(boolean var1) {
      this.setFlag(var1, 2);
   }

   public final void setSimple(boolean var1) {
      this.setFlag(var1, 1);
   }

   boolean shouldEmit() {
      Scope var1 = this.scope;
      if(this.isSimple() && this.name != null && var1 != null) {
         Label var2 = var1.start;
         if(var2 != null) {
            int var3 = var2.position;
            if(var3 >= 0) {
               Label var4 = var1.end;
               if(var4 != null && var4.position > var3) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public String toString() {
      return "Variable[" + this.getName() + " offset:" + this.offset + ']';
   }
}
