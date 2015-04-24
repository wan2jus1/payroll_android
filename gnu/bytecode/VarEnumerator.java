package gnu.bytecode;

import gnu.bytecode.Scope;
import gnu.bytecode.Variable;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class VarEnumerator implements Enumeration {

   Scope currentScope;
   Variable next;
   Scope topScope;


   public VarEnumerator(Scope var1) {
      this.topScope = var1;
      this.reset();
   }

   private void fixup() {
      for(; this.next == null; this.next = this.currentScope.firstVar()) {
         if(this.currentScope.firstChild != null) {
            this.currentScope = this.currentScope.firstChild;
         } else {
            while(this.currentScope.nextSibling == null) {
               if(this.currentScope == this.topScope) {
                  return;
               }

               this.currentScope = this.currentScope.parent;
            }

            this.currentScope = this.currentScope.nextSibling;
         }
      }

   }

   public final boolean hasMoreElements() {
      return this.next != null;
   }

   public Object nextElement() {
      Variable var1 = this.nextVar();
      if(var1 == null) {
         throw new NoSuchElementException("VarEnumerator");
      } else {
         return var1;
      }
   }

   public final Variable nextVar() {
      Variable var1 = this.next;
      if(var1 != null) {
         this.next = var1.nextVar();
         if(this.next == null) {
            this.fixup();
         }
      }

      return var1;
   }

   public final void reset() {
      this.currentScope = this.topScope;
      if(this.topScope != null) {
         this.next = this.currentScope.firstVar();
         if(this.next == null) {
            this.fixup();
         }
      }

   }
}
