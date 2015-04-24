package kawa.standard;

import gnu.expr.Expression;
import kawa.lang.ListPat;
import kawa.lang.Pattern;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class prim_method extends Syntax {

   public static final prim_method interface_method;
   public static final prim_method op1;
   private static Pattern pattern3;
   private static Pattern pattern4;
   public static final prim_method static_method;
   public static final prim_method virtual_method = new prim_method(182);
   int op_code;


   static {
      virtual_method.setName("primitive-virtual-method");
      static_method = new prim_method(184);
      static_method.setName("primitive-static-method");
      interface_method = new prim_method(185);
      interface_method.setName("primitive-interface-method");
      op1 = new prim_method();
      op1.setName("primitive-op1");
      pattern3 = new ListPat(3);
      pattern4 = new ListPat(4);
   }

   public prim_method() {
   }

   public prim_method(int var1) {
      this.op_code = var1;
   }

   int opcode() {
      return this.op_code;
   }

   public Expression rewrite(Object param1, Translator param2) {
      // $FF: Couldn't be decompiled
   }
}
