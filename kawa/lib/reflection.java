package kawa.lib;

import gnu.bytecode.ClassType;
import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.lispexpr.LangObjType;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import kawa.lang.GetFieldProc;
import kawa.lang.Macro;
import kawa.lang.Record;
import kawa.lang.RecordConstructor;
import kawa.lang.SetFieldProc;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lang.SyntaxTemplate;
import kawa.lang.TemplateScope;
import kawa.lib.std_syntax;
import kawa.standard.syntax_case;

public class reflection extends ModuleBody {

   public static final reflection $instance;
   static final SimpleSymbol Lit0;
   static final SyntaxPattern Lit1;
   static final SimpleSymbol Lit10;
   static final SimpleSymbol Lit11;
   static final SimpleSymbol Lit12;
   static final SimpleSymbol Lit13;
   static final SimpleSymbol Lit14;
   static final SyntaxRules Lit15;
   static final SimpleSymbol Lit16;
   static final SyntaxRules Lit17;
   static final SimpleSymbol Lit18;
   static final SyntaxRules Lit19;
   static final SyntaxTemplate Lit2;
   static final SimpleSymbol Lit20;
   static final SyntaxRules Lit21;
   static final SimpleSymbol Lit22;
   static final SyntaxRules Lit23;
   static final SimpleSymbol Lit24;
   static final SyntaxRules Lit25;
   static final SimpleSymbol Lit26;
   static final SyntaxRules Lit27;
   static final SimpleSymbol Lit28;
   static final SyntaxRules Lit29;
   static final SyntaxPattern Lit3;
   static final SimpleSymbol Lit30 = (SimpleSymbol)(new SimpleSymbol("subtype?")).readResolve();
   static final SimpleSymbol Lit31 = (SimpleSymbol)(new SimpleSymbol("constant-fold")).readResolve();
   static final SimpleSymbol Lit32 = (SimpleSymbol)(new SimpleSymbol("make")).readResolve();
   static final IntNum Lit33 = IntNum.make(9);
   static final IntNum Lit34 = IntNum.make(1);
   static final SyntaxTemplate Lit4;
   static final SimpleSymbol Lit5;
   static final SimpleSymbol Lit6;
   static final SimpleSymbol Lit7;
   static final SimpleSymbol Lit8;
   static final SimpleSymbol Lit9;
   public static final ModuleMethod make$Mnrecord$Mntype;
   public static final Macro primitive$Mnarray$Mnget;
   public static final Macro primitive$Mnarray$Mnlength;
   public static final Macro primitive$Mnarray$Mnnew;
   public static final Macro primitive$Mnarray$Mnset;
   public static final Macro primitive$Mnconstructor;
   public static final Macro primitive$Mnget$Mnfield;
   public static final Macro primitive$Mnget$Mnstatic;
   public static final Macro primitive$Mnset$Mnfield;
   public static final Macro primitive$Mnset$Mnstatic;
   public static final ModuleMethod record$Mnaccessor;
   public static final ModuleMethod record$Mnconstructor;
   public static final ModuleMethod record$Mnmodifier;
   public static final ModuleMethod record$Mnpredicate;
   public static final ModuleMethod record$Mntype$Mndescriptor;
   public static final ModuleMethod record$Mntype$Mnfield$Mnnames;
   public static final ModuleMethod record$Mntype$Mnname;
   public static final ModuleMethod record$Qu;
   public static final ModuleMethod subtype$Qu;


   static {
      SimpleSymbol var0 = (SimpleSymbol)(new SimpleSymbol("primitive-set-static")).readResolve();
      Lit28 = var0;
      SyntaxRule var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3), "\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\f\u0011\u0018\u0014\t\u0003\t\u000b\t\u0013\u0018\u001c", new Object[]{Lit31, Lit32, (SimpleSymbol)(new SimpleSymbol("<gnu.kawa.reflect.StaticSet>")).readResolve(), PairWithPosition.make(Lit33, LList.Empty, "reflection.scm", 454679)}, 0);
      Lit29 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 3);
      var0 = (SimpleSymbol)(new SimpleSymbol("primitive-get-static")).readResolve();
      Lit26 = var0;
      var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3), "\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\f\u0011\u0018\u0014\t\u0003\t\u000b\t\u0013\u0018\u001c", new Object[]{Lit31, Lit32, (SimpleSymbol)(new SimpleSymbol("<gnu.kawa.reflect.StaticGet>")).readResolve(), PairWithPosition.make(Lit33, LList.Empty, "reflection.scm", 430103)}, 0);
      Lit27 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 3);
      var0 = (SimpleSymbol)(new SimpleSymbol("primitive-set-field")).readResolve();
      Lit24 = var0;
      var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3), "\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\f\u0011\u0018\u0014\t\u0003\t\u000b\t\u0013\u0018\u001c", new Object[]{Lit31, Lit32, (SimpleSymbol)(new SimpleSymbol("<kawa.lang.SetFieldProc>")).readResolve(), PairWithPosition.make(Lit34, LList.Empty, "reflection.scm", 401431)}, 0);
      Lit25 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 3);
      var0 = (SimpleSymbol)(new SimpleSymbol("primitive-get-field")).readResolve();
      Lit22 = var0;
      var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3), "\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\f\u0011\u0018\u0014\t\u0003\t\u000b\t\u0013\u0018\u001c", new Object[]{Lit31, Lit32, (SimpleSymbol)(new SimpleSymbol("<kawa.lang.GetFieldProc>")).readResolve(), PairWithPosition.make(Lit34, LList.Empty, "reflection.scm", 376855)}, 0);
      Lit23 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 3);
      var0 = (SimpleSymbol)(new SimpleSymbol("primitive-array-length")).readResolve();
      Lit20 = var0;
      var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\u0011\u0018\f\u0011\u0018\u0014\b\u0003", new Object[]{Lit31, Lit32, (SimpleSymbol)(new SimpleSymbol("<gnu.kawa.reflect.ArrayLength>")).readResolve()}, 0);
      Lit21 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 1);
      var0 = (SimpleSymbol)(new SimpleSymbol("primitive-array-get")).readResolve();
      Lit18 = var0;
      var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\u0011\u0018\f\u0011\u0018\u0014\b\u0003", new Object[]{Lit31, Lit32, (SimpleSymbol)(new SimpleSymbol("<gnu.kawa.reflect.ArrayGet>")).readResolve()}, 0);
      Lit19 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 1);
      var0 = (SimpleSymbol)(new SimpleSymbol("primitive-array-set")).readResolve();
      Lit16 = var0;
      var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\u0011\u0018\f\u0011\u0018\u0014\b\u0003", new Object[]{Lit31, Lit32, (SimpleSymbol)(new SimpleSymbol("<gnu.kawa.reflect.ArraySet>")).readResolve()}, 0);
      Lit17 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 1);
      var0 = (SimpleSymbol)(new SimpleSymbol("primitive-array-new")).readResolve();
      Lit14 = var0;
      var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\u0011\u0018\f\u0011\u0018\u0014\b\u0003", new Object[]{Lit31, Lit32, (SimpleSymbol)(new SimpleSymbol("<gnu.kawa.reflect.ArrayNew>")).readResolve()}, 0);
      Lit15 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 1);
      Lit13 = (SimpleSymbol)(new SimpleSymbol("record-type-field-names")).readResolve();
      Lit12 = (SimpleSymbol)(new SimpleSymbol("record-type-name")).readResolve();
      Lit11 = (SimpleSymbol)(new SimpleSymbol("record-type-descriptor")).readResolve();
      Lit10 = (SimpleSymbol)(new SimpleSymbol("record-predicate")).readResolve();
      Lit9 = (SimpleSymbol)(new SimpleSymbol("record?")).readResolve();
      Lit8 = (SimpleSymbol)(new SimpleSymbol("record-modifier")).readResolve();
      Lit7 = (SimpleSymbol)(new SimpleSymbol("record-accessor")).readResolve();
      Lit6 = (SimpleSymbol)(new SimpleSymbol("record-constructor")).readResolve();
      Lit5 = (SimpleSymbol)(new SimpleSymbol("make-record-type")).readResolve();
      Lit4 = new SyntaxTemplate("\u0001\u0001\u0003\u0003", "\u0011\u0018\u0004\u0019\b\u001d\u001b\u0011\u0018\f\t\u000b\b\u0011\u0018\u0014\t\u000b\b\u0015\u0011\u0018\u001c\t\u0013\b\u001b", new Object[]{(SimpleSymbol)(new SimpleSymbol("lambda")).readResolve(), (SimpleSymbol)(new SimpleSymbol("::")).readResolve(), Lit32, (SimpleSymbol)(new SimpleSymbol("as")).readResolve()}, 1);
      Lit3 = new SyntaxPattern("\r\u001f\u0018\b\b", new Object[0], 4);
      Lit2 = new SyntaxTemplate("\u0001\u0001\u0003", "\b\u0015\u0013", new Object[0], 1);
      Lit1 = new SyntaxPattern("\f\u0007\f\u000f,\r\u0017\u0010\b\b\b", new Object[0], 3);
      Lit0 = (SimpleSymbol)(new SimpleSymbol("primitive-constructor")).readResolve();
      $instance = new reflection();
      var0 = Lit0;
      reflection var2 = $instance;
      primitive$Mnconstructor = Macro.make(var0, new ModuleMethod(var2, 2, (Object)null, 4097), $instance);
      make$Mnrecord$Mntype = new ModuleMethod(var2, 3, Lit5, 8194);
      record$Mnconstructor = new ModuleMethod(var2, 4, Lit6, 8193);
      record$Mnaccessor = new ModuleMethod(var2, 6, Lit7, 8194);
      record$Mnmodifier = new ModuleMethod(var2, 7, Lit8, 8194);
      record$Qu = new ModuleMethod(var2, 8, Lit9, 4097);
      record$Mnpredicate = new ModuleMethod(var2, 9, Lit10, 4097);
      record$Mntype$Mndescriptor = new ModuleMethod(var2, 10, Lit11, 4097);
      record$Mntype$Mnname = new ModuleMethod(var2, 11, Lit12, 4097);
      record$Mntype$Mnfield$Mnnames = new ModuleMethod(var2, 12, Lit13, 4097);
      primitive$Mnarray$Mnnew = Macro.make(Lit14, Lit15, $instance);
      primitive$Mnarray$Mnset = Macro.make(Lit16, Lit17, $instance);
      primitive$Mnarray$Mnget = Macro.make(Lit18, Lit19, $instance);
      primitive$Mnarray$Mnlength = Macro.make(Lit20, Lit21, $instance);
      primitive$Mnget$Mnfield = Macro.make(Lit22, Lit23, $instance);
      primitive$Mnset$Mnfield = Macro.make(Lit24, Lit25, $instance);
      primitive$Mnget$Mnstatic = Macro.make(Lit26, Lit27, $instance);
      primitive$Mnset$Mnstatic = Macro.make(Lit28, Lit29, $instance);
      subtype$Qu = new ModuleMethod(var2, 13, Lit30, 8194);
      $instance.run();
   }

   public reflection() {
      ModuleInfo.register(this);
   }

   public static boolean isRecord(Object var0) {
      return var0 instanceof Record;
   }

   public static boolean isSubtype(Type var0, Type var1) {
      return var0.isSubtype(var1);
   }

   static Object lambda2(Object var0) {
      Object[] var1 = SyntaxPattern.allocVars(3, (Object[])null);
      if(Lit1.match(var0, var1, 0)) {
         TemplateScope var2 = TemplateScope.make();
         var0 = std_syntax.generateTemporaries(Lit2.execute(var1, var2));
         var1 = SyntaxPattern.allocVars(4, var1);
         if(Lit3.match(var0, var1, 0)) {
            var2 = TemplateScope.make();
            return Lit4.execute(var1, var2);
         } else {
            return syntax_case.error("syntax-case", var0);
         }
      } else {
         return syntax_case.error("syntax-case", var0);
      }
   }

   public static ClassType makeRecordType(String var0, LList var1) {
      return Record.makeRecordType(var0, var1);
   }

   public static GetFieldProc recordAccessor(ClassType var0, String var1) {
      return new GetFieldProc(var0, var1);
   }

   public static RecordConstructor recordConstructor(ClassType var0) {
      return recordConstructor(var0, (Object)null);
   }

   public static RecordConstructor recordConstructor(ClassType var0, Object var1) {
      return new RecordConstructor(var0, var1);
   }

   public static SetFieldProc recordModifier(ClassType var0, String var1) {
      return new SetFieldProc(var0, var1);
   }

   public static Procedure recordPredicate(Object var0) {
      reflection.frame var1 = new reflection.frame();
      var1.rtype = var0;
      return var1.lambda$Fn1;
   }

   public static Type recordTypeDescriptor(Object var0) {
      return Type.make(var0.getClass());
   }

   public static LList recordTypeFieldNames(Object var0) {
      ClassType var1;
      try {
         var1 = LangObjType.coerceToClassType(var0);
      } catch (ClassCastException var2) {
         throw new WrongType(var2, "kawa.lang.Record.typeFieldNames(class-type)", 1, var0);
      }

      return Record.typeFieldNames((ClassType)var1);
   }

   public static String recordTypeName(ClassType var0) {
      return Compilation.demangleName(var0.getName(), true);
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      ClassType var5;
      switch(var1.selector) {
      case 2:
         return lambda2(var2);
      case 3:
      case 5:
      case 6:
      case 7:
      default:
         return super.apply1(var1, var2);
      case 4:
         try {
            var5 = LangObjType.coerceToClassType(var2);
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "record-constructor", 1, var2);
         }

         return recordConstructor(var5);
      case 8:
         if(isRecord(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 9:
         return recordPredicate(var2);
      case 10:
         return recordTypeDescriptor(var2);
      case 11:
         try {
            var5 = LangObjType.coerceToClassType(var2);
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "record-type-name", 1, var2);
         }

         return recordTypeName(var5);
      case 12:
         return recordTypeFieldNames(var2);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      ClassType var4 = null;
      ClassType var5 = null;
      Object var6 = null;
      String var14;
      switch(var1.selector) {
      case 3:
         if(var2 == null) {
            var14 = (String)var6;
         } else {
            var14 = var2.toString();
         }

         LList var16;
         try {
            var16 = (LList)var3;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "make-record-type", 2, var3);
         }

         return makeRecordType(var14, var16);
      case 4:
         ClassType var17;
         try {
            var17 = LangObjType.coerceToClassType(var2);
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "record-constructor", 1, var2);
         }

         return recordConstructor(var17, var3);
      case 5:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      default:
         return super.apply2(var1, var2, var3);
      case 6:
         try {
            var5 = LangObjType.coerceToClassType(var2);
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "record-accessor", 1, var2);
         }

         if(var3 == null) {
            var14 = var4;
         } else {
            var14 = var3.toString();
         }

         return recordAccessor(var5, var14);
      case 7:
         try {
            var4 = LangObjType.coerceToClassType(var2);
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "record-modifier", 1, var2);
         }

         if(var3 == null) {
            var14 = var5;
         } else {
            var14 = var3.toString();
         }

         return recordModifier(var4, var14);
      case 13:
         Type var13;
         try {
            var13 = LangObjType.coerceToType(var2);
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "subtype?", 1, var2);
         }

         Type var15;
         try {
            var15 = LangObjType.coerceToType(var3);
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "subtype?", 2, var3);
         }

         return isSubtype(var13, var15)?Boolean.TRUE:Boolean.FALSE;
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      switch(var1.selector) {
      case 2:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 3:
      case 5:
      case 6:
      case 7:
      default:
         return super.match1(var1, var2, var3);
      case 4:
         if(LangObjType.coerceToClassTypeOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 8:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 9:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 10:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 11:
         if(LangObjType.coerceToClassTypeOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 12:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      }
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      switch(var1.selector) {
      case 3:
         var4.value1 = var2;
         if(var3 instanceof LList) {
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786430;
      case 4:
         if(LangObjType.coerceToClassTypeOrNull(var2) != null) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786431;
      case 5:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      default:
         return super.match2(var1, var2, var3, var4);
      case 6:
         if(LangObjType.coerceToClassTypeOrNull(var2) != null) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786431;
      case 7:
         if(LangObjType.coerceToClassTypeOrNull(var2) != null) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786431;
      case 13:
         if(LangObjType.coerceToTypeOrNull(var2) != null) {
            var4.value1 = var2;
            if(LangObjType.coerceToTypeOrNull(var3) != null) {
               var4.value2 = var3;
               var4.proc = var1;
               var4.pc = 2;
               return 0;
            } else {
               return -786430;
            }
         } else {
            return -786431;
         }
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }

   public class frame extends ModuleBody {

      final ModuleMethod lambda$Fn1;
      Object rtype;


      public frame() {
         ModuleMethod var1 = new ModuleMethod(this, 1, (Object)null, 4097);
         var1.setProperty("source-location", "reflection.scm:30");
         this.lambda$Fn1 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 1?(this.lambda1(var2)?Boolean.TRUE:Boolean.FALSE):super.apply1(var1, var2);
      }

      boolean lambda1(Object var1) {
         Object var2 = this.rtype;

         Type var3;
         try {
            var3 = (Type)var2;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "gnu.bytecode.Type.isInstance(java.lang.Object)", 1, var2);
         }

         return var3.isInstance(var1);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 1) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }
}
