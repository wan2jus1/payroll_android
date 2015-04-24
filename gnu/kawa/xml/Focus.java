package gnu.kawa.xml;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.expr.Compilation;
import gnu.lists.TreePosition;
import gnu.math.IntNum;

public final class Focus extends TreePosition {

   public static final ClassType TYPE = ClassType.make("gnu.kawa.xml.Focus");
   static ThreadLocal current = new ThreadLocal();
   static final Method getCurrentFocusMethod = TYPE.getDeclaredMethod("getCurrent", 0);
   IntNum contextPosition;
   public long position;


   public static void compileGetCurrent(Compilation var0) {
      var0.getCode().emitInvoke(getCurrentFocusMethod);
   }

   public static Focus getCurrent() {
      Object var1 = current.get();
      Object var0 = var1;
      if(var1 == null) {
         var0 = new Focus();
         current.set(var0);
      }

      return (Focus)var0;
   }
}
