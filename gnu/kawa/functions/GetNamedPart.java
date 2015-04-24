package gnu.kawa.functions;

import gnu.bytecode.ClassType;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.expr.Language;
import gnu.kawa.functions.NamedPart;
import gnu.kawa.functions.SetNamedPart;
import gnu.kawa.reflect.ClassMethods;
import gnu.kawa.reflect.SlotGet;
import gnu.mapping.HasNamedParts;
import gnu.mapping.HasSetter;
import gnu.mapping.MethodProc;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure2;
import gnu.mapping.Symbol;
import gnu.mapping.Values;

public class GetNamedPart extends Procedure2 implements HasSetter {

   public static final String CAST_METHOD_NAME = "@";
   public static final String CLASSTYPE_FOR = "<>";
   public static final String INSTANCEOF_METHOD_NAME = "instance?";
   public static final GetNamedPart getNamedPart = new GetNamedPart();


   static {
      getNamedPart.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileNamedPart:validateGetNamedPart");
   }

   public static Object getMemberPart(Object var0, String var1) throws Throwable {
      try {
         Object var4 = SlotGet.field(var0, var1);
         return var4;
      } catch (Throwable var3) {
         MethodProc var2 = ClassMethods.apply((ClassType)ClassType.make(var0.getClass()), Compilation.mangleName(var1), '\u0000', Language.getDefaultLanguage());
         if(var2 != null) {
            return new NamedPart(var0, var1, 'M', var2);
         } else {
            throw new RuntimeException("no part \'" + var1 + "\' in " + var0);
         }
      }
   }

   public static Object getNamedPart(Object var0, Symbol var1) throws Throwable {
      String var3 = var1.getName();
      if(var0 instanceof HasNamedParts) {
         return ((HasNamedParts)var0).get(var3);
      } else {
         Object var2 = var0;
         if(var0 instanceof Class) {
            var2 = Type.make((Class)var0);
         }

         if(var2 instanceof Package) {
            try {
               String var5 = ((Package)var2).getName();
               Class var6 = ClassType.getContextClass(var5 + '.' + var3);
               return var6;
            } catch (Throwable var4) {
               ;
            }
         }

         return var2 instanceof Type?getTypePart((Type)var2, var3):getMemberPart(var2, var1.toString());
      }
   }

   public static Object getTypePart(Type var0, String var1) throws Throwable {
      if(var1.equals("<>")) {
         return var0;
      } else {
         if(var0 instanceof ObjectType) {
            if(var1.equals("instance?")) {
               return new NamedPart(var0, var1, 'I');
            }

            if(var1.equals("@")) {
               return new NamedPart(var0, var1, 'C');
            }

            if(var1.equals("new")) {
               return new NamedPart(var0, var1, 'N');
            }

            if(var1.equals(".length") || var1.length() > 1 && var1.charAt(0) == 46 && var0 instanceof ClassType) {
               return new NamedPart(var0, var1, 'D');
            }
         }

         if(var0 instanceof ClassType) {
            try {
               Object var2 = SlotGet.staticField(var0, var1);
               return var2;
            } catch (Throwable var3) {
               return ClassMethods.apply(ClassMethods.classMethods, var0, var1);
            }
         } else {
            return getMemberPart(var0, var1);
         }
      }
   }

   public Object apply2(Object var1, Object var2) throws Throwable {
      if(!(var1 instanceof Values)) {
         Symbol var6;
         if(var2 instanceof Symbol) {
            var6 = (Symbol)var2;
         } else {
            var6 = Namespace.EmptyNamespace.getSymbol(var2.toString().intern());
         }

         return getNamedPart(var1, var6);
      } else {
         Object[] var5 = ((Values)var1).getValues();
         Values var3 = new Values();

         for(int var4 = 0; var4 < var5.length; ++var4) {
            Values.writeValues(this.apply2(var5[var4], var2), var3);
         }

         return var3.canonicalize();
      }
   }

   public Procedure getSetter() {
      return SetNamedPart.setNamedPart;
   }
}
