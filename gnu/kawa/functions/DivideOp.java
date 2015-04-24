package gnu.kawa.functions;

import gnu.kawa.functions.ArithOp;
import gnu.kawa.functions.Arithmetic;
import gnu.mapping.Procedure;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.math.RatNum;
import gnu.math.RealNum;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class DivideOp extends ArithOp {

   public static final DivideOp $Sl = new DivideOp("/", 4);
   public static final DivideOp div = new DivideOp("div", 6);
   public static final DivideOp div0 = new DivideOp("div0", 6);
   public static final DivideOp idiv = new DivideOp("idiv", 7);
   public static final DivideOp mod = new DivideOp("mod", 8);
   public static final DivideOp mod0 = new DivideOp("mod0", 8);
   public static final DivideOp modulo = new DivideOp("modulo", 8);
   public static final DivideOp quotient = new DivideOp("quotient", 6);
   public static final DivideOp remainder = new DivideOp("remainder", 8);
   int rounding_mode;


   static {
      idiv.rounding_mode = 3;
      quotient.rounding_mode = 3;
      remainder.rounding_mode = 3;
      modulo.rounding_mode = 1;
      div.rounding_mode = 5;
      mod.rounding_mode = 5;
      div0.rounding_mode = 4;
      mod0.rounding_mode = 4;
   }

   public DivideOp(String var1, int var2) {
      super(var1, var2);
      this.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileArith:validateApplyArithOp");
      Procedure.compilerKey.set(this, "*gnu.kawa.functions.CompileArith:forDiv");
   }

   public Object applyN(Object[] var1) throws Throwable {
      int var17 = var1.length;
      Object var22;
      if(var17 == 0) {
         var22 = IntNum.one();
         return var22;
      } else {
         Object var9 = (Number)var1[0];
         if(var17 == 1) {
            return this.apply2(IntNum.one(), var9);
         } else {
            int var13 = Arithmetic.classifyValue(var9);

            int var12;
            for(int var16 = 1; var16 < var17; var13 = var12) {
               Object var8 = var1[var16];
               int var14 = Arithmetic.classifyValue(var8);
               var12 = var13;
               if(var13 < var14) {
                  var12 = var14;
               }

               int var15 = var12;
               var14 = var12;
               if(var12 < 4) {
                  switch(this.op) {
                  case 4:
                  case 5:
                     var15 = 4;
                     var14 = 4;
                     break;
                  default:
                     label137: {
                        if(this.rounding_mode == 3) {
                           var15 = var12;
                           var14 = var12;
                           if(var12 == 1) {
                              break label137;
                           }

                           if(var12 == 2) {
                              var14 = var12;
                              var15 = var12;
                              break label137;
                           }
                        }

                        var14 = 4;
                        var15 = var12;
                     }
                  }
               }

               byte var28;
               if(this.op == 5 && var15 <= 10) {
                  var28 = 10;
                  var12 = var15;
                  var13 = var28;
                  if(var15 != 8) {
                     var12 = var15;
                     var13 = var28;
                     if(var15 != 7) {
                        var12 = 9;
                        var13 = var28;
                     }
                  }
               } else {
                  label161: {
                     if(var14 != 8) {
                        var12 = var15;
                        var13 = var14;
                        if(var14 != 7) {
                           break label161;
                        }
                     }

                     var28 = 9;
                     var12 = var15;
                     var13 = var28;
                     if(this.op == 7) {
                        var12 = 9;
                        var13 = var28;
                     }
                  }
               }

               label148:
               switch(var13) {
               case 1:
                  var14 = Arithmetic.asInt(var9);
                  var15 = Arithmetic.asInt(var8);
                  switch(this.op) {
                  case 8:
                     var14 %= var15;
                     break;
                  default:
                     var14 /= var15;
                  }

                  var8 = Integer.valueOf(var14);
                  break;
               case 2:
                  long var18 = Arithmetic.asLong(var9);
                  long var20 = Arithmetic.asLong(var8);
                  switch(this.op) {
                  case 8:
                     var18 %= var20;
                     break;
                  default:
                     var18 /= var20;
                  }

                  var8 = Long.valueOf(var18);
                  break;
               case 3:
               case 6:
               case 7:
               case 8:
               default:
                  Numeric var23 = Arithmetic.asNumeric(var9);
                  Numeric var25 = Arithmetic.asNumeric(var8);
                  if(this.op == 8 && var25.isZero()) {
                     var22 = var23;
                     if(!var25.isExact()) {
                        return var23.toInexact();
                     }

                     return var22;
                  }

                  Numeric var26 = var23.div(var25);
                  var8 = var26;
                  if(this.op == 8) {
                     var8 = var23.sub(((RealNum)var26).toInt(this.getRoundingMode()).mul(var25));
                  }

                  switch(this.op) {
                  case 5:
                     var8 = ((Numeric)var8).toInexact();
                     break label148;
                  case 6:
                     var8 = ((RealNum)var8).toInt(this.rounding_mode);
                     break label148;
                  case 7:
                     var8 = ((RealNum)var8).toExactInt(this.rounding_mode);
                     var12 = 4;
                     var13 = 4;
                  default:
                     break label148;
                  }
               case 4:
                  switch(this.op) {
                  case 4:
                     var8 = RatNum.make(Arithmetic.asIntNum((Object)var9), Arithmetic.asIntNum((Object)var8));
                     if(var8 instanceof IntNum) {
                        var12 = 4;
                     } else {
                        var12 = 6;
                     }

                     var13 = var12;
                     break label148;
                  case 5:
                  default:
                     var8 = var9;
                     break label148;
                  case 6:
                  case 7:
                     var8 = IntNum.quotient(Arithmetic.asIntNum((Object)var9), Arithmetic.asIntNum((Object)var8), this.getRoundingMode());
                     break label148;
                  case 8:
                     var8 = IntNum.remainder(Arithmetic.asIntNum((Object)var9), Arithmetic.asIntNum((Object)var8), this.getRoundingMode());
                     break label148;
                  }
               case 5:
                  BigDecimal var10 = Arithmetic.asBigDecimal(var9);
                  BigDecimal var11 = Arithmetic.asBigDecimal(var8);
                  RoundingMode var24;
                  switch(this.getRoundingMode()) {
                  case 1:
                     var24 = RoundingMode.FLOOR;
                     break;
                  case 2:
                     var24 = RoundingMode.CEILING;
                     break;
                  case 3:
                     var24 = RoundingMode.DOWN;
                     break;
                  case 5:
                     if(var11.signum() < 0) {
                        var24 = RoundingMode.CEILING;
                     } else {
                        var24 = RoundingMode.FLOOR;
                     }
                  case 4:
                  default:
                     var24 = RoundingMode.HALF_EVEN;
                  }

                  MathContext var27 = new MathContext(0, var24);
                  switch(this.op) {
                  case 4:
                     var8 = var10.divide(var11);
                     break label148;
                  case 5:
                  default:
                     var8 = var9;
                     break label148;
                  case 6:
                     var8 = var10.divideToIntegralValue(var11, var27);
                     break label148;
                  case 7:
                     var8 = var10.divideToIntegralValue(var11, var27).toBigInteger();
                     var13 = 3;
                     var12 = 3;
                     break label148;
                  case 8:
                     var8 = var10.remainder(var11, var27);
                     break label148;
                  }
               case 9:
                  double var4 = Arithmetic.asDouble(var9);
                  double var6 = Arithmetic.asDouble(var8);
                  switch(this.op) {
                  case 4:
                  case 5:
                     var8 = DFloNum.make(var4 / var6);
                     break;
                  case 6:
                     var8 = Double.valueOf(RealNum.toInt(var4 / var6, this.getRoundingMode()));
                     break;
                  case 7:
                     var8 = RealNum.toExactInt(var4 / var6, this.getRoundingMode());
                     var13 = 4;
                     var12 = 4;
                     break;
                  case 8:
                     double var2 = var4;
                     if(var6 != 0.0D) {
                        var2 = var4 - RealNum.toInt(var4 / var6, this.getRoundingMode()) * var6;
                     }

                     var8 = DFloNum.make(var2);
                     break;
                  default:
                     var8 = var9;
                  }
               }

               var9 = var8;
               if(var12 != var13) {
                  var9 = var8;
                  switch(var12) {
                  case 1:
                     var9 = Integer.valueOf(((Number)var8).intValue());
                     break;
                  case 2:
                     var9 = Long.valueOf(((Number)var8).longValue());
                     break;
                  case 3:
                     var9 = Arithmetic.asBigInteger(var8);
                  case 4:
                  case 5:
                  case 6:
                     break;
                  case 7:
                     var9 = Float.valueOf(((Number)var8).floatValue());
                     break;
                  case 8:
                     var9 = Double.valueOf(((Number)var8).doubleValue());
                     break;
                  default:
                     var9 = var8;
                  }
               }

               ++var16;
            }

            return var9;
         }
      }
   }

   public int getRoundingMode() {
      return this.rounding_mode;
   }

   public int numArgs() {
      return this.op == 4?-4095:8194;
   }
}
