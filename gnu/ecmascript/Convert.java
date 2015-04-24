package gnu.ecmascript;


public class Convert {

   public static double toInteger(double var0) {
      return Double.isNaN(var0)?0.0D:(var0 < 0.0D?Math.ceil(var0):Math.floor(var0));
   }

   public static double toInteger(Object var0) {
      return toInteger(toNumber(var0));
   }

   public static double toNumber(Object var0) {
      double var1 = Double.NaN;
      if(var0 instanceof Number) {
         var1 = ((Number)var0).doubleValue();
      } else {
         if(var0 instanceof Boolean) {
            if(((Boolean)var0).booleanValue()) {
               return 1.0D;
            }

            return 0.0D;
         }

         if(var0 instanceof String) {
            try {
               var1 = Double.valueOf((String)var0).doubleValue();
               return var1;
            } catch (NumberFormatException var3) {
               return Double.NaN;
            }
         }
      }

      return var1;
   }

   public int toInt32(double var1) {
      return !Double.isNaN(var1) && !Double.isInfinite(var1)?(int)var1:0;
   }

   public int toInt32(Object var1) {
      return this.toInt32(toNumber(var1));
   }
}
