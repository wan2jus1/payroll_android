package gnu.kawa.functions;

import gnu.bytecode.ClassType;
import gnu.bytecode.Type;
import gnu.kawa.functions.Convert;
import gnu.kawa.functions.NamedPartSetter;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.kawa.reflect.SlotSet;
import gnu.mapping.CallContext;
import gnu.mapping.HasSetter;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import kawa.standard.Scheme;

class NamedPart extends ProcedureN implements HasSetter, Externalizable {

   Object container;
   char kind;
   Object member;
   MethodProc methods;


   public NamedPart(Object var1, Object var2, char var3) {
      this.container = var1;
      this.member = var2;
      this.kind = var3;
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileNamedPart:validateNamedPart");
   }

   public NamedPart(Object var1, String var2, char var3, MethodProc var4) {
      this(var1, var2, var3);
      this.methods = var4;
   }

   public void apply(CallContext var1) throws Throwable {
      this.apply(var1.getArgs(), var1);
   }

   public void apply(Object[] var1, CallContext var2) throws Throwable {
      if(this.kind == 83) {
         this.methods.checkN(var1, var2);
      } else if(this.kind == 77) {
         int var4 = var1.length;
         Object[] var3 = new Object[var4 + 1];
         var3[0] = this.container;
         System.arraycopy(var1, 0, var3, 1, var4);
         this.methods.checkN(var3, var2);
      } else {
         var2.writeValue(this.applyN(var1));
      }
   }

   public Object applyN(Object[] var1) throws Throwable {
      Object[] var2;
      switch(this.kind) {
      case 67:
         return Convert.as.apply2(this.container, var1[0]);
      case 68:
         String var3 = this.member.toString().substring(1);
         if(var1.length == 0) {
            return SlotGet.staticField((ClassType)this.container, var3);
         }

         return SlotGet.field(((Type)this.container).coerceFromObject(var1[0]), var3);
      case 73:
         return Scheme.instanceOf.apply2(var1[0], this.container);
      case 77:
         var2 = new Object[var1.length + 1];
         var2[0] = this.container;
         System.arraycopy(var1, 0, var2, 1, var1.length);
         return this.methods.applyN(var2);
      case 78:
         var2 = new Object[var1.length + 1];
         var2[0] = this.container;
         System.arraycopy(var1, 0, var2, 1, var1.length);
         return Invoke.make.applyN(var2);
      case 83:
         return this.methods.applyN(var1);
      default:
         throw new Error("unknown part " + this.member + " in " + this.container);
      }
   }

   public Procedure getSetter() {
      if(this.kind == 68) {
         return new NamedPartSetter(this);
      } else {
         throw new RuntimeException("procedure \'" + this.getName() + "\' has no setter");
      }
   }

   public int numArgs() {
      return this.kind != 73 && this.kind != 67?(this.kind == 68?4096:-4096):4097;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.kind = var1.readChar();
      this.container = (Procedure)var1.readObject();
      this.member = (Procedure)var1.readObject();
   }

   public void set0(Object var1) throws Throwable {
      switch(this.kind) {
      case 68:
         String var2 = this.member.toString().substring(1);
         SlotSet.setStaticField((ClassType)this.container, var2, var1);
         return;
      default:
         throw new Error("invalid setter for " + this);
      }
   }

   public void set1(Object var1, Object var2) throws Throwable {
      switch(this.kind) {
      case 68:
         String var3 = this.member.toString().substring(1);
         SlotSet.setField(((Type)this.container).coerceFromObject(var1), var3, var2);
         return;
      default:
         throw new Error("invalid setter for " + this);
      }
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.container);
      var1.writeObject(this.member);
      var1.writeChar(this.kind);
   }
}
