package appinventor.ai_greatbattlewar.new_thinker;

import appinventor.ai_greatbattlewar.new_thinker.Screen1;
import com.google.appinventor.components.runtime.Component;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleMethod;
import gnu.mapping.CallContext;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;

public class Screen1$frame extends ModuleBody {

   Screen1 $main;


   public Object apply0(ModuleMethod var1) {
      switch(var1.selector) {
      case 15:
         return Screen1.lambda2();
      case 16:
         this.$main.$define();
         return Values.empty;
      case 17:
         return Screen1.lambda3();
      case 18:
         return Screen1.lambda4();
      case 19:
         return Screen1.lambda5();
      case 20:
         return Screen1.lambda6();
      case 21:
         return Screen1.lambda8();
      case 22:
         return Screen1.lambda7();
      case 23:
         return Screen1.lambda9();
      case 24:
         return Screen1.lambda10();
      case 25:
         return Screen1.lambda11();
      case 26:
         return Screen1.lambda12();
      case 27:
         return Screen1.lambda13();
      case 28:
         return Screen1.lambda14();
      case 29:
         return Screen1.lambda15();
      case 30:
         return Screen1.lambda16();
      case 31:
         return Screen1.lambda17();
      case 32:
         return Screen1.lambda18();
      case 33:
         return Screen1.lambda19();
      case 34:
         return Screen1.lambda20();
      case 35:
         return Screen1.lambda21();
      case 36:
         return Screen1.lambda22();
      case 37:
         return Screen1.lambda23();
      case 38:
         return Screen1.lambda24();
      case 39:
         return Screen1.lambda25();
      case 40:
         return Screen1.lambda26();
      case 41:
         return Screen1.lambda27();
      case 42:
         return Screen1.lambda28();
      case 43:
         return Screen1.lambda29();
      case 44:
         return Screen1.lambda30();
      case 45:
         return Screen1.lambda31();
      case 46:
         return Screen1.lambda32();
      case 47:
         return Screen1.lambda33();
      case 48:
         return Screen1.lambda34();
      case 49:
         return Screen1.lambda35();
      case 50:
         return Screen1.lambda36();
      case 51:
         return Screen1.lambda37();
      case 52:
         return Screen1.lambda38();
      case 53:
         return Screen1.lambda39();
      case 54:
         return Screen1.lambda40();
      case 55:
         return Screen1.lambda41();
      case 56:
         return Screen1.lambda42();
      case 57:
         return Screen1.lambda43();
      case 58:
         return Screen1.lambda44();
      case 59:
         return Screen1.lambda45();
      case 60:
         return Screen1.lambda46();
      case 61:
         return Screen1.lambda47();
      case 62:
         return Screen1.lambda48();
      case 63:
         return Screen1.lambda49();
      case 64:
         return Screen1.lambda50();
      case 65:
         return this.$main.add_emp$Click();
      case 66:
         return Screen1.lambda51();
      case 67:
         return Screen1.lambda52();
      case 68:
         return this.$main.update_emp$Click();
      case 69:
         return Screen1.lambda53();
      case 70:
         return Screen1.lambda54();
      case 71:
         return this.$main.View_Customers$Click();
      case 72:
         return Screen1.lambda55();
      case 73:
         return Screen1.lambda56();
      case 74:
         return this.$main.ListPicker1$AfterPicking();
      default:
         return super.apply0(var1);
      }
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      Symbol var3;
      Screen1 var6;
      switch(var1.selector) {
      case 1:
         this.$main.androidLogForm(var2);
         return Values.empty;
      case 3:
         var6 = this.$main;

         try {
            var3 = (Symbol)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "lookup-in-form-environment", 1, var2);
         }

         return var6.lookupInFormEnvironment(var3);
      case 5:
         var6 = this.$main;

         try {
            var3 = (Symbol)var2;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "is-bound-in-form-environment", 1, var2);
         }

         if(var6.isBoundInFormEnvironment(var3)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 10:
         this.$main.addToFormDoAfterCreation(var2);
         return Values.empty;
      case 11:
         this.$main.sendError(var2);
         return Values.empty;
      case 12:
         this.$main.processException(var2);
         return Values.empty;
      case 75:
         return Screen1.lambda57(var2);
      default:
         return super.apply1(var1, var2);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      Symbol var4;
      Screen1 var8;
      switch(var1.selector) {
      case 2:
         var8 = this.$main;

         try {
            var4 = (Symbol)var2;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "add-to-form-environment", 1, var2);
         }

         var8.addToFormEnvironment(var4, var3);
         return Values.empty;
      case 3:
         var8 = this.$main;

         try {
            var4 = (Symbol)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "lookup-in-form-environment", 1, var2);
         }

         return var8.lookupInFormEnvironment(var4, var3);
      case 4:
      case 5:
      case 8:
      case 10:
      case 11:
      case 12:
      case 13:
      default:
         return super.apply2(var1, var2, var3);
      case 6:
         var8 = this.$main;

         try {
            var4 = (Symbol)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "add-to-global-var-environment", 1, var2);
         }

         var8.addToGlobalVarEnvironment(var4, var3);
         return Values.empty;
      case 7:
         this.$main.addToEvents(var2, var3);
         return Values.empty;
      case 9:
         this.$main.addToGlobalVars(var2, var3);
         return Values.empty;
      case 14:
         return this.$main.lookupHandler(var2, var3);
      }
   }

   public Object apply4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5) {
      switch(var1.selector) {
      case 8:
         this.$main.addToComponents(var2, var3, var4, var5);
         return Values.empty;
      case 13:
         Screen1 var11 = this.$main;

         Component var6;
         try {
            var6 = (Component)var2;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "dispatchEvent", 1, var2);
         }

         String var12;
         try {
            var12 = (String)var3;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "dispatchEvent", 2, var3);
         }

         String var13;
         try {
            var13 = (String)var4;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "dispatchEvent", 3, var4);
         }

         Object[] var14;
         try {
            var14 = (Object[])var5;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "dispatchEvent", 4, var5);
         }

         if(var11.dispatchEvent(var6, var12, var13, var14)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 76:
         return this.$main.Web1$GotText(var2, var3, var4, var5);
      default:
         return super.apply4(var1, var2, var3, var4, var5);
      }
   }

   public int match0(ModuleMethod var1, CallContext var2) {
      switch(var1.selector) {
      case 15:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 16:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 17:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 18:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 19:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 20:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 21:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 22:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 23:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 24:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 25:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 26:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 27:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 28:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 29:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 30:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 31:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 32:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 33:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 34:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 35:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 36:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 37:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 38:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 39:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 40:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 41:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 42:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 43:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 44:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 45:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 46:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 47:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 48:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 49:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 50:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 51:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 52:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 53:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 54:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 55:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 56:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 57:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 58:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 59:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 60:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 61:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 62:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 63:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 64:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 65:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 66:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 67:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 68:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 69:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 70:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 71:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 72:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 73:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 74:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      default:
         return super.match0(var1, var2);
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      switch(var1.selector) {
      case 1:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 3:
         if(!(var2 instanceof Symbol)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 5:
         if(!(var2 instanceof Symbol)) {
            return -786431;
         }

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
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 12:
         if(!(var2 instanceof Screen1)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 75:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      default:
         return super.match1(var1, var2, var3);
      }
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      switch(var1.selector) {
      case 2:
         if(!(var2 instanceof Symbol)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 3:
         if(!(var2 instanceof Symbol)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 4:
      case 5:
      case 8:
      case 10:
      case 11:
      case 12:
      case 13:
      default:
         return super.match2(var1, var2, var3, var4);
      case 6:
         if(!(var2 instanceof Symbol)) {
            return -786431;
         }

         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 7:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 9:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 14:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      }
   }

   public int match4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5, CallContext var6) {
      switch(var1.selector) {
      case 8:
         var6.value1 = var2;
         var6.value2 = var3;
         var6.value3 = var4;
         var6.value4 = var5;
         var6.proc = var1;
         var6.pc = 4;
         return 0;
      case 13:
         if(!(var2 instanceof Screen1)) {
            return -786431;
         } else {
            var6.value1 = var2;
            if(!(var3 instanceof Component)) {
               return -786430;
            } else {
               var6.value2 = var3;
               if(!(var4 instanceof String)) {
                  return -786429;
               } else {
                  var6.value3 = var4;
                  if(!(var5 instanceof String)) {
                     return -786428;
                  }

                  var6.value4 = var5;
                  var6.proc = var1;
                  var6.pc = 4;
                  return 0;
               }
            }
         }
      case 76:
         var6.value1 = var2;
         var6.value2 = var3;
         var6.value3 = var4;
         var6.value4 = var5;
         var6.proc = var1;
         var6.pc = 4;
         return 0;
      default:
         return super.match4(var1, var2, var3, var4, var5, var6);
      }
   }
}
