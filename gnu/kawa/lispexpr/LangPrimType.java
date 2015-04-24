package gnu.kawa.lispexpr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Language;
import gnu.expr.Target;
import gnu.expr.TypeValue;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.reflect.InstanceOf;
import gnu.mapping.Procedure;
import gnu.mapping.Values;
import gnu.text.Char;

public class LangPrimType extends PrimType implements TypeValue {

   public static final PrimType byteType = Type.byteType;
   public static final LangPrimType charType = new LangPrimType(Type.charType);
   public static final PrimType doubleType = Type.doubleType;
   public static final PrimType floatType = Type.floatType;
   public static final PrimType intType = Type.intType;
   public static final PrimType longType = Type.longType;
   public static final PrimType shortType = Type.shortType;
   public static final LangPrimType voidType = new LangPrimType(Type.voidType);
   PrimType implementationType;
   Language language;


   public LangPrimType(PrimType var1) {
      super(var1);
      this.implementationType = var1;
   }

   public LangPrimType(PrimType var1, Language var2) {
      super(var1);
      this.language = var2;
      this.implementationType = var1;
   }

   public LangPrimType(String var1, String var2, int var3, Class var4) {
      super(var1, var2, var3, var4);
   }

   public LangPrimType(String var1, String var2, int var3, Class var4, Language var5) {
      this(var1, var2, var3, var4);
      this.implementationType = Type.signatureToPrimitive(var2.charAt(0));
      this.language = var5;
   }

   public char charValue(Object var1) {
      return var1 instanceof Character?((Character)var1).charValue():((Char)var1).charValue();
   }

   public Object coerceFromObject(Object var1) {
      if(var1.getClass() == this.reflectClass) {
         return var1;
      } else {
         switch(this.getSignature().charAt(0)) {
         case 67:
            return new Character(((Char)var1).charValue());
         case 86:
            return Values.empty;
         case 90:
            Boolean var2;
            if(this.language.isTrue(var1)) {
               var2 = Boolean.TRUE;
            } else {
               var2 = Boolean.FALSE;
            }

            return var2;
         default:
            return super.coerceFromObject(var1);
         }
      }
   }

   public Object coerceToObject(Object var1) {
      Object var2;
      switch(this.getSignature().charAt(0)) {
      case 67:
         var2 = var1;
         if(!(var1 instanceof Char)) {
            return Char.make(((Character)var1).charValue());
         }
         break;
      case 86:
         return Values.empty;
      case 90:
         return this.language.booleanObject(((Boolean)var1).booleanValue());
      default:
         var2 = super.coerceToObject(var1);
      }

      return var2;
   }

   public int compare(Type var1) {
      char var2 = this.getSignature().charAt(0);
      if(var1 instanceof PrimType) {
         char var3 = var1.getSignature().charAt(0);
         if(var2 == var3) {
            return 0;
         }

         if(var2 == 86) {
            return 1;
         }

         if(var3 == 86 || var3 == 90) {
            return -1;
         }
      }

      return var2 != 86 && var2 != 90?(var2 == 67 && var1.getName().equals("gnu.text.Char")?-1:(var1 instanceof LangObjType?swappedCompareResult(var1.compare(this)):super.compare(var1))):1;
   }

   public Expression convertValue(Expression var1) {
      return null;
   }

   public void emitCoerceFromObject(CodeAttr var1) {
      switch(this.getSignature().charAt(0)) {
      case 67:
         ClassType var2 = ClassType.make("gnu.text.Char");
         Method var3 = var2.getDeclaredMethod("charValue", 0);
         var1.emitCheckcast(var2);
         var1.emitInvokeVirtual(var3);
         return;
      case 90:
         this.language.emitCoerceToBoolean(var1);
         return;
      default:
         super.emitCoerceFromObject(var1);
      }
   }

   public void emitCoerceToObject(CodeAttr var1) {
      switch(this.getSignature().charAt(0)) {
      case 67:
         var1.emitInvokeStatic(ClassType.make("gnu.text.Char").getDeclaredMethod("make", 1));
         break;
      case 90:
         var1.emitIfIntNotZero();
         this.language.emitPushBoolean(true, var1);
         var1.emitElse();
         this.language.emitPushBoolean(false, var1);
         var1.emitFi();
         break;
      default:
         super.emitCoerceToObject(var1);
      }

      if(false) {
         var1.emitInvokeStatic(ClassType.make((String)null).getDeclaredMethod("make", new Type[]{null}));
      }

   }

   public void emitIsInstance(CodeAttr var1) {
      switch(this.getSignature().charAt(0)) {
      case 67:
         var1.emitInstanceof(ClassType.make("gnu.text.Char"));
         return;
      case 90:
         var1.emitPop(1);
         var1.emitPushInt(1);
         return;
      default:
         super.emitIsInstance(var1);
      }
   }

   public void emitIsInstance(Variable var1, Compilation var2, Target var3) {
      InstanceOf.emitIsInstance(this, var1, var2, var3);
   }

   public void emitTestIf(Variable var1, Declaration var2, Compilation var3) {
      this.getSignature().charAt(0);
      CodeAttr var4 = var3.getCode();
      if(var1 != null) {
         var4.emitLoad(var1);
      }

      if(var2 != null) {
         var4.emitDup();
         var2.compileStore(var3);
      }

      this.emitIsInstance(var4);
      var4.emitIfIntNotZero();
   }

   public Procedure getConstructor() {
      return null;
   }

   public Type getImplementationType() {
      return this.implementationType;
   }
}
