package gnu.mapping;

import gnu.mapping.Location;
import gnu.mapping.Procedure;
import gnu.mapping.WrappedException;

public class ProcLocation extends Location {

   Object[] args;
   Procedure proc;


   public ProcLocation(Procedure var1, Object[] var2) {
      this.proc = var1;
      this.args = var2;
   }

   public Object get(Object var1) {
      try {
         var1 = this.proc.applyN(this.args);
         return var1;
      } catch (RuntimeException var2) {
         throw var2;
      } catch (Error var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new WrappedException(var4);
      }
   }

   public boolean isBound() {
      return true;
   }

   public void set(Object var1) {
      int var3 = this.args.length;
      Object[] var2 = new Object[var3 + 1];
      var2[var3] = var1;
      System.arraycopy(this.args, 0, var2, 0, var3);

      try {
         this.proc.setN(var2);
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Error var5) {
         throw var5;
      } catch (Throwable var6) {
         throw new WrappedException(var6);
      }
   }
}
