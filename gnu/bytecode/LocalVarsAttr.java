package gnu.bytecode;

import gnu.bytecode.Attribute;
import gnu.bytecode.ClassType;
import gnu.bytecode.ClassTypeWriter;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Scope;
import gnu.bytecode.Type;
import gnu.bytecode.VarEnumerator;
import gnu.bytecode.Variable;
import java.io.DataOutputStream;
import java.io.IOException;

public class LocalVarsAttr extends Attribute {

   public Scope current_scope;
   private Method method;
   Scope parameter_scope;
   Variable[] used;


   public LocalVarsAttr(CodeAttr var1) {
      super("LocalVariableTable");
      this.addToFrontOf(var1);
      this.method = (Method)var1.getContainer();
      var1.locals = this;
   }

   public LocalVarsAttr(Method var1) {
      super("LocalVariableTable");
      CodeAttr var2 = var1.code;
      this.method = var1;
      var2.locals = this;
   }

   public VarEnumerator allVars() {
      return new VarEnumerator(this.parameter_scope);
   }

   public void assignConstants(ClassType var1) {
      super.assignConstants(var1);
      VarEnumerator var2 = this.allVars();

      while(true) {
         Variable var3 = var2.nextVar();
         if(var3 == null) {
            return;
         }

         if(var3.isSimple() && var3.name != null) {
            if(var3.name_index == 0) {
               var3.name_index = var1.getConstants().addUtf8(var3.getName()).index;
            }

            if(var3.signature_index == 0) {
               var3.signature_index = var1.getConstants().addUtf8(var3.getType().getSignature()).index;
            }
         }
      }
   }

   public void enterScope(Scope var1) {
      var1.linkChild(this.current_scope);
      this.current_scope = var1;
      CodeAttr var2 = this.method.getCode();

      for(Variable var3 = var1.firstVar(); var3 != null; var3 = var3.nextVar()) {
         if(var3.isSimple()) {
            if(!var3.isAssigned()) {
               var3.allocateLocal(var2);
            } else if(this.used[var3.offset] == null) {
               this.used[var3.offset] = var3;
            } else if(this.used[var3.offset] != var3) {
               throw new Error("inconsistent local variable assignments for " + var3 + " != " + this.used[var3.offset]);
            }
         }
      }

   }

   public final int getCount() {
      int var3 = 0;
      VarEnumerator var1 = this.allVars();

      while(true) {
         Variable var2 = var1.nextVar();
         if(var2 == null) {
            return var3;
         }

         if(var2.shouldEmit()) {
            ++var3;
         }
      }
   }

   public final int getLength() {
      return this.getCount() * 10 + 2;
   }

   public final Method getMethod() {
      return this.method;
   }

   public final boolean isEmpty() {
      VarEnumerator var1 = this.allVars();

      Variable var2;
      do {
         var2 = var1.nextVar();
         if(var2 == null) {
            return true;
         }
      } while(!var2.isSimple() || var2.name == null);

      return false;
   }

   public void preserveVariablesUpto(Scope var1) {
      for(Scope var2 = this.current_scope; var2 != var1; var2 = var2.parent) {
         var2.preserved = true;
      }

   }

   public void print(ClassTypeWriter var1) {
      VarEnumerator var2 = this.allVars();
      var1.print("Attribute \"");
      var1.print(this.getName());
      var1.print("\", length:");
      var1.print(this.getLength());
      var1.print(", count: ");
      var1.println(this.getCount());
      var2.reset();

      while(true) {
         Variable var3 = var2.nextVar();
         if(var3 == null) {
            return;
         }

         if(var3.isSimple() && var3.name != null) {
            label25: {
               var1.print("  slot#");
               var1.print(var3.offset);
               var1.print(": name: ");
               var1.printOptionalIndex(var3.name_index);
               var1.print(var3.getName());
               var1.print(", type: ");
               var1.printOptionalIndex(var3.signature_index);
               var1.printSignature((Type)var3.getType());
               var1.print(" (pc: ");
               Scope var6 = var3.scope;
               if(var6 != null && var6.start != null && var6.end != null) {
                  int var4 = var6.start.position;
                  if(var4 >= 0) {
                     int var5 = var6.end.position;
                     if(var5 >= 0) {
                        var1.print(var4);
                        var1.print(" length: ");
                        var1.print(var5 - var4);
                        break label25;
                     }
                  }
               }

               var1.print("unknown");
            }

            var1.println(')');
         }
      }
   }

   public void write(DataOutputStream var1) throws IOException {
      VarEnumerator var2 = this.allVars();
      var1.writeShort(this.getCount());
      var2.reset();

      while(true) {
         Variable var3 = var2.nextVar();
         if(var3 == null) {
            return;
         }

         if(var3.shouldEmit()) {
            Scope var4 = var3.scope;
            int var5 = var4.start.position;
            int var6 = var4.end.position;
            var1.writeShort(var5);
            var1.writeShort(var6 - var5);
            var1.writeShort(var3.name_index);
            var1.writeShort(var3.signature_index);
            var1.writeShort(var3.offset);
         }
      }
   }
}
