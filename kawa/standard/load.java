package kawa.standard;

import gnu.mapping.Environment;
import gnu.mapping.Procedure1;

public class load extends Procedure1 {

   public static final load load = new load("load", false);
   public static final load loadRelative = new load("load-relative", true);
   boolean relative;


   public load(String var1, boolean var2) {
      super(var1);
      this.relative = var2;
   }

   public final Object apply1(Object var1) throws Throwable {
      return this.apply2(var1, Environment.getCurrent());
   }

   public final Object apply2(Object param1, Object param2) throws Throwable {
      // $FF: Couldn't be decompiled
   }
}
