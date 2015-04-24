package gnu.bytecode;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Label;
import gnu.bytecode.Type;

public class IfState {

   boolean doing_else;
   Label end_label;
   IfState previous;
   int stack_growth;
   int start_stack_size;
   Type[] then_stacked_types;


   public IfState(CodeAttr var1) {
      this(var1, new Label(var1));
   }

   public IfState(CodeAttr var1, Label var2) {
      this.previous = var1.if_stack;
      var1.if_stack = this;
      this.end_label = var2;
      this.start_stack_size = var1.SP;
   }
}
