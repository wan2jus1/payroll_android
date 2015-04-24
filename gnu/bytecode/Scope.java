package gnu.bytecode;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Label;
import gnu.bytecode.Type;
import gnu.bytecode.VarEnumerator;
import gnu.bytecode.Variable;

public class Scope {

   Label end;
   Scope firstChild;
   Scope lastChild;
   Variable last_var;
   Scope nextSibling;
   Scope parent;
   boolean preserved;
   Label start;
   Variable vars;


   public Scope() {
   }

   public Scope(Label var1, Label var2) {
      this.start = var1;
      this.end = var2;
   }

   static boolean equals(byte[] var0, byte[] var1) {
      if(var0.length != var1.length) {
         return false;
      } else if(var0 == var1) {
         return true;
      } else {
         int var2 = var0.length;

         int var3;
         do {
            var3 = var2 - 1;
            if(var3 < 0) {
               return true;
            }

            var2 = var3;
         } while(var0[var3] == var1[var3]);

         return false;
      }
   }

   public Variable addVariable(CodeAttr var1, Type var2, String var3) {
      Variable var4 = new Variable(var3, var2);
      this.addVariable(var1, var4);
      return var4;
   }

   public void addVariable(CodeAttr var1, Variable var2) {
      this.addVariable(var2);
      if(var2.isSimple() && var1 != null) {
         var2.allocateLocal(var1);
      }

   }

   public void addVariable(Variable var1) {
      if(this.last_var == null) {
         this.vars = var1;
      } else {
         this.last_var.next = var1;
      }

      this.last_var = var1;
      var1.scope = this;
   }

   public void addVariableAfter(Variable var1, Variable var2) {
      if(var1 == null) {
         var2.next = this.vars;
         this.vars = var2;
      } else {
         var2.next = var1.next;
         var1.next = var2;
      }

      if(this.last_var == var1) {
         this.last_var = var2;
      }

      if(var2.next == var2) {
         throw new Error("cycle");
      } else {
         var2.scope = this;
      }
   }

   public VarEnumerator allVars() {
      return new VarEnumerator(this);
   }

   public final Variable firstVar() {
      return this.vars;
   }

   void freeLocals(CodeAttr var1) {
      if(!this.preserved) {
         for(Variable var2 = this.vars; var2 != null; var2 = var2.next) {
            if(var2.isSimple() && !var2.dead()) {
               var2.freeLocal(var1);
            }
         }

         for(Scope var3 = this.firstChild; var3 != null; var3 = var3.nextSibling) {
            if(var3.preserved) {
               var3.preserved = false;
               var3.freeLocals(var1);
            }
         }
      }

   }

   public Variable getVariable(int var1) {
      Variable var2 = this.vars;

      while(true) {
         --var1;
         if(var1 < 0) {
            return var2;
         }

         var2 = var2.next;
      }
   }

   public void linkChild(Scope var1) {
      this.parent = var1;
      if(var1 != null) {
         if(var1.lastChild == null) {
            var1.firstChild = this;
         } else {
            var1.lastChild.nextSibling = this;
         }

         var1.lastChild = this;
      }
   }

   public Variable lookup(String var1) {
      for(Variable var2 = this.vars; var2 != null; var2 = var2.next) {
         if(var1.equals(var2.name)) {
            return var2;
         }
      }

      return null;
   }

   public void noteStartFunction(CodeAttr var1) {
      this.setStartPC(var1);
      this.start.setTypes((CodeAttr)var1);
   }

   public void setStartPC(CodeAttr var1) {
      if(this.start == null) {
         this.start = new Label();
      }

      boolean var2 = var1.reachableHere();
      this.start.define(var1);
      var1.setReachable(var2);
   }
}
