package gnu.kawa.xml;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConditionalTarget;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Target;
import gnu.expr.TypeValue;
import gnu.kawa.reflect.InstanceOf;
import gnu.kawa.xml.KNode;
import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.mapping.Procedure;
import gnu.xml.NodeTree;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class NodeType extends ObjectType implements TypeValue, NodePredicate, Externalizable {

   public static final int ATTRIBUTE_OK = 4;
   public static final int COMMENT_OK = 16;
   public static final int DOCUMENT_OK = 8;
   public static final int ELEMENT_OK = 2;
   public static final int PI_OK = 32;
   public static final int TEXT_OK = 1;
   public static final NodeType anyNodeTest = new NodeType("node");
   static final Method coerceMethod = typeNodeType.getDeclaredMethod("coerceForce", 2);
   static final Method coerceOrNullMethod = typeNodeType.getDeclaredMethod("coerceOrNull", 2);
   public static final NodeType commentNodeTest = new NodeType("comment", 16);
   public static final NodeType documentNodeTest = new NodeType("document-node", 8);
   public static final NodeType nodeType = new NodeType("gnu.kawa.xml.KNode");
   public static final NodeType textNodeTest = new NodeType("text", 1);
   public static final ClassType typeKNode = ClassType.make("gnu.kawa.xml.KNode");
   public static final ClassType typeNodeType = ClassType.make("gnu.kawa.xml.NodeType");
   int kinds;


   public NodeType(String var1) {
      this(var1, -1);
   }

   public NodeType(String var1, int var2) {
      super(var1);
      this.kinds = -1;
      this.kinds = var2;
   }

   public static KNode coerceForce(Object var0, int var1) {
      KNode var2 = coerceOrNull(var0, var1);
      if(var2 == null) {
         throw new ClassCastException("coerce from " + var0.getClass());
      } else {
         return var2;
      }
   }

   public static KNode coerceOrNull(Object var0, int var1) {
      KNode var2 = null;
      KNode var3;
      if(var0 instanceof NodeTree) {
         var3 = KNode.make((NodeTree)var0);
      } else {
         if(!(var0 instanceof KNode)) {
            return var2;
         }

         var3 = (KNode)var0;
      }

      if(!isInstance(var3.sequence, var3.ipos, var1)) {
         var3 = null;
      }

      var2 = var3;
      return var2;
   }

   public static boolean isInstance(AbstractSequence var0, int var1, int var2) {
      var1 = var0.getNextKind(var1);
      if(var2 < 0) {
         if(var1 == 0) {
            return false;
         }
      } else {
         switch(var1) {
         case 0:
            return false;
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 30:
         case 31:
         default:
            return true;
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
         case 29:
         case 32:
            if((var2 & 1) == 0) {
               return false;
            }
            break;
         case 33:
            if((var2 & 2) == 0) {
               return false;
            }
            break;
         case 34:
            if((var2 & 8) == 0) {
               return false;
            }
            break;
         case 35:
            if((var2 & 4) == 0) {
               return false;
            }
            break;
         case 36:
            if((var2 & 16) == 0) {
               return false;
            }
            break;
         case 37:
            if((var2 & 32) == 0) {
               return false;
            }
         }
      }

      return true;
   }

   public Object coerceFromObject(Object var1) {
      return coerceForce(var1, this.kinds);
   }

   public int compare(Type var1) {
      return this.getImplementationType().compare(var1);
   }

   public Expression convertValue(Expression var1) {
      ApplyExp var2 = new ApplyExp(coerceMethod, new Expression[]{var1});
      var2.setType(this);
      return var2;
   }

   public void emitCoerceFromObject(CodeAttr var1) {
      var1.emitPushInt(this.kinds);
      var1.emitInvokeStatic(coerceMethod);
   }

   protected void emitCoerceOrNullMethod(Variable var1, Compilation var2) {
      CodeAttr var3 = var2.getCode();
      if(var1 != null) {
         var3.emitLoad(var1);
      }

      var3.emitPushInt(this.kinds);
      var3.emitInvokeStatic(coerceOrNullMethod);
   }

   public void emitIsInstance(Variable var1, Compilation var2, Target var3) {
      if(var3 instanceof ConditionalTarget) {
         ConditionalTarget var5 = (ConditionalTarget)var3;
         this.emitCoerceOrNullMethod(var1, var2);
         CodeAttr var4 = var2.getCode();
         if(var5.trueBranchComesFirst) {
            var4.emitGotoIfCompare1(var5.ifFalse, 198);
         } else {
            var4.emitGotoIfCompare1(var5.ifTrue, 199);
         }

         var5.emitGotoFirstBranch(var4);
      } else {
         InstanceOf.emitIsInstance(this, var1, var2, var3);
      }
   }

   public void emitTestIf(Variable var1, Declaration var2, Compilation var3) {
      CodeAttr var4 = var3.getCode();
      this.emitCoerceOrNullMethod(var1, var3);
      if(var2 != null) {
         var4.emitDup();
         var2.compileStore(var3);
      }

      var4.emitIfNotNull();
   }

   public Procedure getConstructor() {
      return null;
   }

   public Type getImplementationType() {
      return typeKNode;
   }

   public boolean isInstance(Object var1) {
      if(var1 instanceof KNode) {
         KNode var2 = (KNode)var1;
         return this.isInstancePos(var2.sequence, var2.getPos());
      } else {
         return false;
      }
   }

   public boolean isInstancePos(AbstractSequence var1, int var2) {
      return isInstance(var1, var2, this.kinds);
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      String var2 = var1.readUTF();
      if(var2.length() > 0) {
         this.setName(var2);
      }

      this.kinds = var1.readInt();
   }

   public String toString() {
      return "NodeType " + this.getName();
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      String var3 = this.getName();
      String var2 = var3;
      if(var3 == null) {
         var2 = "";
      }

      var1.writeUTF(var2);
      var1.writeInt(this.kinds);
   }
}
