package gnu.kawa.xml;

import gnu.bytecode.Type;
import gnu.kawa.reflect.OccurrenceType;

public class NodeSetType extends OccurrenceType {

   public NodeSetType(Type var1) {
      super(var1, 0, -1);
   }

   public static Type getInstance(Type var0) {
      return new NodeSetType(var0);
   }

   public String toString() {
      return super.toString() + "node-set";
   }
}
