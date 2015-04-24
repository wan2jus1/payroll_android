package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.kawa.reflect.Invoke;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.math.BaseUnit;
import gnu.math.Quantity;
import gnu.math.Unit;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;
import kawa.standard.Scheme;

public class define_unit extends Syntax {

   public static final define_unit define_base_unit;
   public static final define_unit define_unit = new define_unit(false);
   boolean base;


   static {
      define_unit.setName("define-unit");
      define_base_unit = new define_unit(true);
      define_base_unit.setName("define-base-unit");
   }

   public define_unit(boolean var1) {
      this.base = var1;
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      Object var7 = var1.getCdr();
      if(var7 instanceof Pair) {
         Pair var5 = (Pair)var7;
         if(var5.getCar() instanceof Declaration) {
            Declaration var3 = (Declaration)var5.getCar();
            String var4 = ((Symbol)var3.getSymbol()).getLocalPart();
            ClassType var6 = ClassType.make("gnu.math.Unit");
            var3.setType(var6);
            var7 = var3.getValue();
            if(!(var7 instanceof QuoteExp) || !(((QuoteExp)var7).getValue() instanceof Unit)) {
               if(this.base) {
                  String var8 = null;
                  if(var5.getCdr() != LList.Empty) {
                     var8 = ((Pair)var5.getCdr()).getCar().toString();
                  }

                  var7 = new QuoteExp(BaseUnit.make(var4, var8));
               } else {
                  label37: {
                     if(!(var5.getCdr() instanceof Pair)) {
                        return var2.syntaxError("missing value for define-unit");
                     }

                     Expression var11 = var2.rewrite(((Pair)var5.getCdr()).getCar());
                     if(var11 instanceof QuoteExp) {
                        Object var9 = ((QuoteExp)var11).getValue();
                        if(var9 instanceof Quantity) {
                           var7 = new QuoteExp(Unit.make(var4, (Quantity)var9));
                           break label37;
                        }
                     }

                     var7 = Invoke.makeInvokeStatic(var6, "make", new Expression[]{new QuoteExp(var4), var11});
                  }
               }
            }

            SetExp var10 = new SetExp(var3, (Expression)var7);
            var10.setDefining(true);
            var3.noteValue((Expression)var7);
            return var10;
         }
      }

      return var2.syntaxError("invalid syntax for " + this.getName());
   }

   public boolean scanForDefinitions(Pair var1, Vector var2, ScopeExp var3, Translator var4) {
      if(var1.getCdr() instanceof Pair) {
         Pair var5 = (Pair)var1.getCdr();
         Object var6 = var5.getCar();
         if(var6 instanceof SimpleSymbol) {
            String var7 = var6.toString();
            Declaration var10 = var3.getDefine(Scheme.unitNamespace.getSymbol(var7), 'w', var4);
            var4.push(var10);
            Translator.setLine((Declaration)var10, var5);
            var10.setFlag(16384L);
            if(var3 instanceof ModuleExp) {
               var10.setCanRead(true);
            }

            var4 = null;
            Object var9;
            if(this.base && var5.getCdr() == LList.Empty) {
               var9 = BaseUnit.make(var7, (String)null);
            } else {
               var9 = var4;
               if(var5.getCdr() instanceof Pair) {
                  Object var8 = ((Pair)var5.getCdr()).getCar();
                  if(this.base && var8 instanceof CharSequence) {
                     var9 = BaseUnit.make(var7, var8.toString());
                  } else {
                     var9 = var4;
                     if(!this.base) {
                        var9 = var4;
                        if(var8 instanceof Quantity) {
                           var9 = Unit.make(var7, (Quantity)var8);
                        }
                     }
                  }
               }
            }

            if(var9 != null) {
               var10.noteValue(new QuoteExp(var9));
            }

            var2.addElement(Translator.makePair(var1, this, Translator.makePair(var5, var10, var5.getCdr())));
            return true;
         }
      }

      var4.error('e', "missing name in define-unit");
      return false;
   }
}
