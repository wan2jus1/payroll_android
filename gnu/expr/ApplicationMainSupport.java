package gnu.expr;

import gnu.expr.Language;
import gnu.lists.FString;
import gnu.lists.FVector;
import gnu.mapping.Environment;
import gnu.mapping.Symbol;
import gnu.mapping.ThreadLocation;

public class ApplicationMainSupport {

   public static String[] commandLineArgArray;
   public static FVector commandLineArguments;
   public static boolean processCommandLinePropertyAssignments;
   static String[][] propertyFields;


   static {
      String[] var0 = new String[]{"out:doctype-system", "gnu.xml.XMLPrinter", "doctypeSystem"};
      String[] var1 = new String[]{"out:doctype-public", "gnu.xml.XMLPrinter", "doctypePublic"};
      String[] var2 = new String[]{"out:base", "gnu.kawa.functions.DisplayFormat", "outBase"};
      String[] var3 = new String[]{"out:radix", "gnu.kawa.functions.DisplayFormat", "outRadix"};
      String[] var4 = new String[]{"out:right-margin", "gnu.text.PrettyWriter", "lineLengthLoc"};
      String[] var5 = new String[]{"out:miser-width", "gnu.text.PrettyWriter", "miserWidthLoc"};
      String[] var6 = new String[]{"out:xml-indent", "gnu.xml.XMLPrinter", "indentLoc"};
      String[] var7 = new String[]{"display:toolkit", "gnu.kawa.models.Display", "myDisplay"};
      propertyFields = new String[][]{var0, var1, var2, var3, {"out:line-length", "gnu.text.PrettyWriter", "lineLengthLoc"}, var4, var5, var6, var7, null};
   }

   public static void processArgs(String[] var0) {
      int var2 = 0;
      int var1 = 0;
      if(processCommandLinePropertyAssignments) {
         while(true) {
            var2 = var1;
            if(var1 >= var0.length) {
               break;
            }

            var2 = var1;
            if(!processSetProperty(var0[var1])) {
               break;
            }

            ++var1;
         }
      }

      setArgs(var0, var2);
   }

   public static void processSetProperties() {
      String[] var0 = commandLineArgArray;
      if(var0 == null) {
         processCommandLinePropertyAssignments = true;
      } else {
         int var1;
         for(var1 = 0; var1 < var0.length && processSetProperty(var0[var1]); ++var1) {
            ;
         }

         if(var1 != 0) {
            setArgs(var0, var1);
            return;
         }
      }

   }

   public static boolean processSetProperty(String var0) {
      int var5 = var0.indexOf(61);
      if(var5 <= 0) {
         return false;
      } else {
         String var1 = var0.substring(0, var5);
         var0 = var0.substring(var5 + 1);
         var5 = 0;

         while(true) {
            String[] var3 = propertyFields[var5];
            if(var3 == null) {
               break;
            }

            if(var1.equals(var3[0])) {
               String var2 = var3[1];
               String var8 = var3[2];

               try {
                  ((ThreadLocation)Class.forName(var2).getDeclaredField(var8).get((Object)null)).setGlobal(var0);
                  break;
               } catch (Throwable var6) {
                  System.err.println("error setting property " + var1 + " field " + var2 + '.' + var8 + ": " + var6);
                  System.exit(-1);
               }
            }

            ++var5;
         }

         Symbol var7 = Symbol.parse(var1);
         Language.getDefaultLanguage();
         Environment.getCurrent().define(var7, (Object)null, var0);
         return true;
      }
   }

   public static void setArgs(String[] var0, int var1) {
      int var4 = var0.length - var1;
      Object[] var2 = new Object[var4];
      if(var1 == 0) {
         commandLineArgArray = var0;
      } else {
         String[] var3 = new String[var4];
         int var5 = var4;

         while(true) {
            --var5;
            if(var5 < 0) {
               commandLineArgArray = var3;
               break;
            }

            var3[var5] = var0[var5 + var1];
         }
      }

      while(true) {
         --var4;
         if(var4 < 0) {
            commandLineArguments = new FVector(var2);
            Environment.getCurrent().put((String)"command-line-arguments", commandLineArguments);
            return;
         }

         var2[var4] = new FString(var0[var4 + var1]);
      }
   }
}
