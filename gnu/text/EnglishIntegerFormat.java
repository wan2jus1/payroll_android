package gnu.text;

import gnu.lists.Consumer;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class EnglishIntegerFormat extends NumberFormat {

   private static EnglishIntegerFormat cardinalEnglish;
   public static final String[] ones = new String[]{null, "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
   public static final String[] onesth = new String[]{null, "first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth", "tenth", "eleventh", "twelveth", "thirteenth", "fourteenth", "fifteenth", "sixteenth", "seventeenth", "eighteenth", "nineteenth"};
   private static EnglishIntegerFormat ordinalEnglish;
   public static final String[] power1000s = new String[]{null, " thousand", " million", " billion", " trillion", " quadrillion", " quintillion", " sextillion", " septillion", " octillion", " nonillion", " decillion", " undecillion", " duodecillion", " tredecillion", " quattuordecillion", " quindecillion", " sexdecillion", " septendecillion", " octodecillion", " novemdecillion", " vigintillion"};
   public static final String[] tens = new String[]{null, null, "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
   public static final String[] tensth = new String[]{null, null, "twentieth", "thirtieth", "fortieth", "fiftieth", "sixtieth", "seventieth", "eightieth", "ninetieth"};
   public boolean ordinal;


   public EnglishIntegerFormat(boolean var1) {
      this.ordinal = var1;
   }

   public static EnglishIntegerFormat getInstance(boolean var0) {
      if(var0) {
         if(ordinalEnglish == null) {
            ordinalEnglish = new EnglishIntegerFormat(true);
         }

         return ordinalEnglish;
      } else {
         if(cardinalEnglish == null) {
            cardinalEnglish = new EnglishIntegerFormat(false);
         }

         return cardinalEnglish;
      }
   }

   public StringBuffer format(double var1, StringBuffer var3, FieldPosition var4) {
      long var5 = (long)var1;
      if((double)var5 == var1) {
         return this.format(var5, var3, var4);
      } else {
         var3.append(Double.toString(var1));
         return var3;
      }
   }

   public StringBuffer format(long var1, StringBuffer var3, FieldPosition var4) {
      if(var1 == 0L) {
         String var5;
         if(this.ordinal) {
            var5 = "zeroth";
         } else {
            var5 = "zero";
         }

         var3.append(var5);
      } else {
         long var6 = var1;
         if(var1 < 0L) {
            var3.append("minus ");
            var6 = -var1;
         }

         this.format(var3, var6, 0, this.ordinal);
      }

      if(var4 != null) {
         ;
      }

      return var3;
   }

   void format(StringBuffer var1, long var2, int var4, boolean var5) {
      long var7 = var2;
      if(var2 >= 1000L) {
         this.format(var1, var2 / 1000L, var4 + 1, false);
         var2 %= 1000L;
         if(var2 > 0L) {
            var1.append(", ");
            var7 = var2;
         } else {
            var7 = var2;
            if(var5) {
               var1.append("th");
               var7 = var2;
            }
         }
      }

      if(var7 > 0L) {
         int var6 = (int)var7;
         if(var5 && var4 == 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         this.format999(var1, var6, var5);
         if(var4 >= power1000s.length) {
            var1.append(" times ten to the ");
            this.format(var1, (long)(var4 * 3), 0, true);
            var1.append(" power");
         } else if(var4 > 0) {
            var1.append(power1000s[var4]);
            return;
         }
      }

   }

   void format999(StringBuffer var1, int var2, boolean var3) {
      int var5 = var2;
      if(var2 >= 100) {
         var5 = var2 / 100;
         var2 %= 100;
         if(var5 > 1) {
            var1.append(ones[var5]);
            var1.append(' ');
         }

         var1.append("hundred");
         if(var2 > 0) {
            var1.append(' ');
            var5 = var2;
         } else {
            var5 = var2;
            if(var3) {
               var1.append("th");
               var5 = var2;
            }
         }
      }

      var2 = var5;
      String[] var4;
      if(var5 >= 20) {
         var2 = var5 / 10;
         var5 %= 10;
         if(var3 && var5 == 0) {
            var4 = tensth;
         } else {
            var4 = tens;
         }

         var1.append(var4[var2]);
         var2 = var5;
         if(var5 > 0) {
            var1.append('-');
            var2 = var5;
         }
      }

      if(var2 > 0) {
         if(var3) {
            var4 = onesth;
         } else {
            var4 = ones;
         }

         var1.append(var4[var2]);
      }

   }

   public Number parse(String var1, ParsePosition var2) {
      throw new Error("EnglishIntegerFormat.parseObject - not implemented");
   }

   public void writeBoolean(boolean var1, Consumer var2) {
      long var3;
      if(var1) {
         var3 = 1L;
      } else {
         var3 = 0L;
      }

      this.writeLong(var3, var2);
   }

   public void writeInt(int var1, Consumer var2) {
      this.writeLong((long)var1, var2);
   }

   public void writeLong(long var1, Consumer var3) {
      StringBuffer var4 = new StringBuffer();
      this.format(var1, var4, (FieldPosition)null);
      var3.write((CharSequence)var4, 0, var4.length());
   }

   public void writeObject(Object var1, Consumer var2) {
      this.writeLong(((Number)var1).longValue(), var2);
   }
}
