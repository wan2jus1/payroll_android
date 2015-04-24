package gnu.kawa.xml;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.Compilation;
import gnu.expr.TypeValue;
import gnu.kawa.xml.KElement;
import gnu.kawa.xml.MakeElement;
import gnu.kawa.xml.NodeType;
import gnu.lists.AbstractSequence;
import gnu.lists.ElementPredicate;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.xml.NamespaceBinding;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.xml.namespace.QName;

public class ElementType extends NodeType implements TypeValue, Externalizable, ElementPredicate {

   public static final String MATCH_ANY_LOCALNAME = "";
   public static final Symbol MATCH_ANY_QNAME = new Symbol((Namespace)null, "");
   public static final ElementType anyElement = make((String)null, (String)null);
   static final Method coerceMethod = typeElementType.getDeclaredMethod("coerce", 3);
   static final Method coerceOrNullMethod = typeElementType.getDeclaredMethod("coerceOrNull", 3);
   public static final ClassType typeElementType = ClassType.make("gnu.kawa.xml.ElementType");
   NamespaceBinding namespaceNodes;
   Symbol qname;


   public ElementType(Symbol var1) {
      this((String)null, var1);
   }

   public ElementType(String var1, Symbol var2) {
      if(var1 == null || var1.length() <= 0) {
         var1 = "ELEMENT " + var2 + " (*)";
      }

      super(var1);
      this.qname = var2;
   }

   public static KElement coerce(Object var0, String var1, String var2) {
      KElement var3 = coerceOrNull(var0, var1, var2);
      if(var3 == null) {
         throw new ClassCastException();
      } else {
         return var3;
      }
   }

   public static KElement coerceOrNull(Object var0, String var1, String var2) {
      KElement var4 = (KElement)NodeType.coerceOrNull(var0, 2);
      KElement var5;
      if(var4 == null) {
         var5 = null;
         return var5;
      } else {
         String var3 = var2;
         if(var2 != null) {
            var3 = var2;
            if(var2.length() == 0) {
               var3 = null;
            }
         }

         var0 = var4.getNextTypeObject();
         String var7;
         if(var0 instanceof Symbol) {
            Symbol var6 = (Symbol)var0;
            var2 = var6.getNamespaceURI();
            var7 = var6.getLocalName();
         } else if(var0 instanceof QName) {
            QName var8 = (QName)var0;
            var2 = var8.getNamespaceURI();
            var7 = var8.getLocalPart();
         } else {
            var2 = "";
            var7 = var0.toString().intern();
         }

         if(var3 == var7 || var3 == null) {
            var5 = var4;
            if(var1 == var2) {
               return var5;
            }

            var5 = var4;
            if(var1 == null) {
               return var5;
            }
         }

         return null;
      }
   }

   public static ElementType make(Symbol var0) {
      return new ElementType(var0);
   }

   public static ElementType make(String var0, String var1) {
      Symbol var2;
      if(var0 != null) {
         var2 = Symbol.make(var0, var1);
      } else if(var1 == "") {
         var2 = MATCH_ANY_QNAME;
      } else {
         var2 = new Symbol((Namespace)null, var1);
      }

      return new ElementType(var2);
   }

   public Object coerceFromObject(Object var1) {
      return coerce(var1, this.qname.getNamespaceURI(), this.qname.getLocalName());
   }

   public void emitCoerceFromObject(CodeAttr var1) {
      var1.emitPushString(this.qname.getNamespaceURI());
      var1.emitPushString(this.qname.getLocalName());
      var1.emitInvokeStatic(coerceMethod);
   }

   protected void emitCoerceOrNullMethod(Variable var1, Compilation var2) {
      CodeAttr var3 = var2.getCode();
      if(var1 != null) {
         var3.emitLoad(var1);
      }

      var3.emitPushString(this.qname.getNamespaceURI());
      var3.emitPushString(this.qname.getLocalName());
      var3.emitInvokeStatic(coerceOrNullMethod);
   }

   public Procedure getConstructor() {
      MakeElement var1 = new MakeElement();
      var1.tag = this.qname;
      var1.setHandlingKeywordParameters(true);
      if(this.namespaceNodes != null) {
         var1.setNamespaceNodes(this.namespaceNodes);
      }

      return var1;
   }

   public Type getImplementationType() {
      return ClassType.make("gnu.kawa.xml.KElement");
   }

   public final String getLocalName() {
      return this.qname.getLocalName();
   }

   public NamespaceBinding getNamespaceNodes() {
      return this.namespaceNodes;
   }

   public final String getNamespaceURI() {
      return this.qname.getNamespaceURI();
   }

   public boolean isInstance(AbstractSequence var1, int var2, Object var3) {
      String var6 = this.qname.getNamespaceURI();
      String var5 = this.qname.getLocalName();
      String var4;
      String var8;
      String var10;
      if(var3 instanceof Symbol) {
         Symbol var7 = (Symbol)var3;
         var10 = var7.getNamespaceURI();
         var8 = var7.getLocalName();
      } else if(var3 instanceof QName) {
         QName var9 = (QName)var3;
         var10 = var9.getNamespaceURI();
         var8 = var9.getLocalPart();
      } else {
         var4 = "";
         var8 = var3.toString().intern();
         var10 = var4;
      }

      var4 = var5;
      if(var5 != null) {
         var4 = var5;
         if(var5.length() == 0) {
            var4 = null;
         }
      }

      return (var4 == var8 || var4 == null) && (var6 == var10 || var6 == null);
   }

   public boolean isInstance(Object var1) {
      return coerceOrNull(var1, this.qname.getNamespaceURI(), this.qname.getLocalName()) != null;
   }

   public boolean isInstancePos(AbstractSequence var1, int var2) {
      int var3 = var1.getNextKind(var2);
      return var3 == 33?this.isInstance(var1, var2, var1.getNextTypeObject(var2)):(var3 == 32?this.isInstance(var1.getPosNext(var2)):false);
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      String var2 = var1.readUTF();
      if(var2.length() > 0) {
         this.setName(var2);
      }

      this.qname = (Symbol)var1.readObject();
   }

   public void setNamespaceNodes(NamespaceBinding var1) {
      this.namespaceNodes = var1;
   }

   public String toString() {
      return "ElementType " + this.qname;
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      String var3 = this.getName();
      String var2 = var3;
      if(var3 == null) {
         var2 = "";
      }

      var1.writeUTF(var2);
      var1.writeObject(this.qname);
   }
}
