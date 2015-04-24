package gnu.kawa.functions;

import gnu.bytecode.ClassType;
import gnu.bytecode.Type;
import gnu.kawa.reflect.SlotSet;
import gnu.mapping.Environment;
import gnu.mapping.HasSetter;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure3;
import gnu.mapping.Symbol;
import gnu.mapping.Values;

public class SetNamedPart extends Procedure3 implements HasSetter {

   public static final SetNamedPart setNamedPart = new SetNamedPart();


   static {
      setNamedPart.setName("setNamedPart");
      setNamedPart.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileNamedPart:validateSetNamedPart");
   }

   public Object apply3(Object var1, Object var2, Object var3) {
      Object var4 = var1;
      if(var1 instanceof Namespace) {
         Namespace var6 = (Namespace)var1;
         String var8 = var6.getName();
         if(!var8.startsWith("class:")) {
            Symbol var7 = var6.getSymbol(var2.toString());
            Environment.getCurrent();
            Environment.getCurrent().put((Symbol)var7, var3);
            return Values.empty;
         }

         var4 = ClassType.make(var8.substring(6));
      }

      var1 = var4;
      if(var4 instanceof Class) {
         var1 = (ClassType)Type.make((Class)var4);
      }

      if(var1 instanceof ClassType) {
         try {
            SlotSet.setStaticField(var1, var2.toString(), var3);
            Values var9 = Values.empty;
            return var9;
         } catch (Throwable var5) {
            ;
         }
      }

      SlotSet.setField(var1, var2.toString(), var3);
      return Values.empty;
   }
}
