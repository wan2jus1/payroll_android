package gnu.bytecode;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Label;
import gnu.bytecode.PrimType;
import gnu.bytecode.TryState;
import gnu.bytecode.Type;

public class SwitchState {

   Label after_label;
   Label cases_label;
   Label defaultLabel;
   Label[] labels;
   int maxValue;
   int minValue;
   int numCases;
   TryState outerTry;
   Label switch_label;
   int[] values;


   public SwitchState(CodeAttr var1) {
      this.switch_label = new Label(var1);
      this.cases_label = new Label(var1);
      this.after_label = new Label(var1);
      this.outerTry = var1.try_stack;
      this.numCases = 0;
   }

   public boolean addCase(int var1, CodeAttr var2) {
      Label var3 = new Label(var2);
      var3.setTypes((Label)this.cases_label);
      var3.define(var2);
      return this.insertCase(var1, var3, var2);
   }

   public boolean addCaseGoto(int var1, CodeAttr var2, Label var3) {
      boolean var4 = this.insertCase(var1, var3, var2);
      var3.setTypes((Label)this.cases_label);
      var2.setUnreachable();
      return var4;
   }

   public void addDefault(CodeAttr var1) {
      Label var2 = new Label(var1);
      var2.setTypes((Label)this.cases_label);
      var2.define(var1);
      if(this.defaultLabel != null) {
         throw new Error();
      } else {
         this.defaultLabel = var2;
      }
   }

   public void exitSwitch(CodeAttr var1) {
      if(this.outerTry != var1.try_stack) {
         throw new Error("exitSwitch cannot exit through a try");
      } else {
         var1.emitGoto(this.after_label);
      }
   }

   public void finish(CodeAttr var1) {
      if(this.defaultLabel == null) {
         this.defaultLabel = new Label(var1);
         this.defaultLabel.define(var1);
         ClassType var2 = ClassType.make("java.lang.RuntimeException");
         var1.emitNew(var2);
         var1.emitDup(var2);
         var1.emitPushString("bad case value!");
         ClassType var3 = Type.string_type;
         PrimType var4 = Type.voidType;
         var1.emitInvokeSpecial(var2.addMethod("<init>", 1, new Type[]{var3}, var4));
         var1.emitThrow();
      }

      var1.fixupChain(this.switch_label, this.after_label);
      if(this.numCases <= 1) {
         var1.pushType(Type.intType);
         if(this.numCases == 1) {
            if(this.minValue == 0) {
               var1.emitIfIntEqZero();
            } else {
               var1.emitPushInt(this.minValue);
               var1.emitIfEq();
            }

            var1.emitGoto(this.labels[0]);
            var1.emitElse();
            var1.emitGoto(this.defaultLabel);
            var1.emitFi();
         } else {
            var1.emitPop(1);
            var1.emitGoto(this.defaultLabel);
         }
      } else {
         int var5;
         if(this.numCases * 2 >= this.maxValue - this.minValue) {
            var1.reserve((this.maxValue - this.minValue + 1) * 4 + 13);
            var1.fixupAdd(2, (Label)null);
            var1.put1(170);
            var1.fixupAdd(3, this.defaultLabel);
            var1.PC += 4;
            var1.put4(this.minValue);
            var1.put4(this.maxValue);
            int var6 = 0;

            for(var5 = this.minValue; var5 <= this.maxValue; ++var5) {
               Label var7;
               if(this.values[var6] == var5) {
                  var7 = this.labels[var6];
                  ++var6;
               } else {
                  var7 = this.defaultLabel;
               }

               var1.fixupAdd(3, var7);
               var1.PC += 4;
            }
         } else {
            var1.reserve(this.numCases * 8 + 9);
            var1.fixupAdd(2, (Label)null);
            var1.put1(171);
            var1.fixupAdd(3, this.defaultLabel);
            var1.PC += 4;
            var1.put4(this.numCases);

            for(var5 = 0; var5 < this.numCases; ++var5) {
               var1.put4(this.values[var5]);
               var1.fixupAdd(3, this.labels[var5]);
               var1.PC += 4;
            }
         }
      }

      var1.fixupChain(this.after_label, this.cases_label);
   }

   public int getMaxValue() {
      return this.maxValue;
   }

   public int getNumCases() {
      return this.numCases;
   }

   public boolean insertCase(int var1, Label var2, CodeAttr var3) {
      if(this.values == null) {
         this.values = new int[10];
         this.labels = new Label[10];
         this.numCases = 1;
         this.maxValue = var1;
         this.minValue = var1;
         this.values[0] = var1;
         this.labels[0] = var2;
         return true;
      } else {
         int[] var8 = this.values;
         Label[] var4 = this.labels;
         if(this.numCases >= this.values.length) {
            this.values = new int[this.numCases * 2];
            this.labels = new Label[this.numCases * 2];
         }

         int var5;
         int var6;
         if(var1 < this.minValue) {
            var6 = 0;
            this.minValue = var1;
         } else if(var1 > this.maxValue) {
            var6 = this.numCases;
            this.maxValue = var1;
         } else {
            int var7 = 0;
            var6 = this.numCases - 1;
            var5 = 0;

            while(var7 <= var6) {
               var5 = var7 + var6 >>> 1;
               if(var8[var5] >= var1) {
                  var6 = var5 - 1;
               } else {
                  ++var5;
                  var7 = var5;
               }
            }

            var6 = var5;
            if(var1 == var8[var5]) {
               return false;
            }
         }

         var5 = this.numCases - var6;
         System.arraycopy(var8, var6, this.values, var6 + 1, var5);
         System.arraycopy(var8, 0, this.values, 0, var6);
         this.values[var6] = var1;
         System.arraycopy(var4, var6, this.labels, var6 + 1, var5);
         System.arraycopy(var4, 0, this.labels, 0, var6);
         this.labels[var6] = var2;
         ++this.numCases;
         return true;
      }
   }

   public void switchValuePushed(CodeAttr var1) {
      var1.popType();
      this.cases_label.setTypes((CodeAttr)var1);
      var1.fixupChain(this.cases_label, this.switch_label);
   }
}
