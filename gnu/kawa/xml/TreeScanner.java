package gnu.kawa.xml;

import gnu.kawa.xml.KNode;
import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.lists.PositionConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.WrongType;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public abstract class TreeScanner extends MethodProc implements Externalizable {

   public NodePredicate type;


   TreeScanner() {
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.xml.CompileXmlFunctions:validateApplyTreeScanner");
   }

   public void apply(CallContext var1) throws Throwable {
      PositionConsumer var3 = (PositionConsumer)var1.consumer;
      Object var2 = var1.getNextArg();
      var1.lastArg();

      KNode var5;
      try {
         var5 = (KNode)var2;
      } catch (ClassCastException var4) {
         throw new WrongType(this.getDesc(), -4, var2, "node()");
      }

      this.scan(var5.sequence, var5.getPos(), var3);
   }

   public String getDesc() {
      String var2 = this.getClass().getName();
      int var3 = var2.lastIndexOf(46);
      String var1 = var2;
      if(var3 > 0) {
         var1 = var2.substring(var3 + 1);
      }

      return var1 + "::" + this.type;
   }

   public NodePredicate getNodePredicate() {
      return this.type;
   }

   public int numArgs() {
      return 4097;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.type = (NodePredicate)var1.readObject();
   }

   public abstract void scan(AbstractSequence var1, int var2, PositionConsumer var3);

   public String toString() {
      return "#<" + this.getClass().getName() + ' ' + this.type + '>';
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.type);
   }
}
