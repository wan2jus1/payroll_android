package gnu.kawa.xml;

import gnu.bytecode.ClassType;
import gnu.kawa.xml.XDataType;
import gnu.math.DateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class XTimeType extends XDataType {

   public static final XTimeType dateTimeType = new XTimeType("dateTime", 20);
   public static final XTimeType dateType = new XTimeType("date", 21);
   private static TimeZone fixedTimeZone;
   public static final XTimeType gDayType = new XTimeType("gDay", 26);
   public static final XTimeType gMonthDayType = new XTimeType("gMonthDay", 25);
   public static final XTimeType gMonthType = new XTimeType("gMonth", 27);
   public static final XTimeType gYearMonthType = new XTimeType("gYearMonth", 23);
   public static final XTimeType gYearType = new XTimeType("gYear", 24);
   public static final XTimeType timeType = new XTimeType("time", 22);
   static ClassType typeDateTime = ClassType.make("gnu.math.DateTime");


   XTimeType(String var1, int var2) {
      super(var1, typeDateTime, var2);
   }

   static int components(int var0) {
      byte var1 = 126;
      switch(var0) {
      case 21:
         return 14;
      case 22:
         return 112;
      case 23:
         return 6;
      case 24:
         return 2;
      case 25:
         return 12;
      case 26:
         return 8;
      case 27:
         return 4;
      case 29:
         return 6;
      case 30:
         return 120;
      default:
         var1 = 0;
      case 20:
      case 28:
         return var1;
      }
   }

   private static TimeZone fixedTimeZone() {
      synchronized(XTimeType.class){}

      TimeZone var0;
      try {
         if(fixedTimeZone == null) {
            fixedTimeZone = DateTime.minutesToTimeZone(TimeZone.getDefault().getRawOffset() / '\uea60');
         }

         var0 = fixedTimeZone;
      } finally {
         ;
      }

      return var0;
   }

   public static DateTime parseDateTime(String var0, int var1) {
      DateTime var2 = DateTime.parse(var0, var1);
      if(var2.isZoneUnspecified()) {
         var2.setTimeZone(fixedTimeZone());
      }

      return var2;
   }

   public boolean isInstance(Object var1) {
      return var1 instanceof DateTime && components(this.typeCode) == ((DateTime)var1).components();
   }

   public DateTime now() {
      return new DateTime(components(this.typeCode) | 128, (GregorianCalendar)Calendar.getInstance(fixedTimeZone()));
   }

   public Object valueOf(String var1) {
      return parseDateTime(var1, components(this.typeCode));
   }
}
