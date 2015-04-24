package gnu.text;

import gnu.text.SourceError;
import gnu.text.SourceLocator;
import java.io.PrintStream;
import java.io.PrintWriter;

public class SourceMessages implements SourceLocator {

   public static boolean debugStackTraceOnError = false;
   public static boolean debugStackTraceOnWarning = false;
   int current_column;
   String current_filename;
   int current_line;
   private int errorCount = 0;
   SourceError firstError;
   SourceError lastError;
   SourceError lastPrevFilename = null;
   SourceLocator locator;
   public boolean sortMessages;


   public boolean checkErrors(PrintStream var1, int var2) {
      boolean var4 = false;
      boolean var3 = var4;
      if(this.firstError != null) {
         this.printAll((PrintStream)var1, var2);
         this.lastError = null;
         this.firstError = null;
         var2 = this.errorCount;
         this.errorCount = 0;
         var3 = var4;
         if(var2 > 0) {
            var3 = true;
         }
      }

      return var3;
   }

   public boolean checkErrors(PrintWriter var1, int var2) {
      boolean var4 = false;
      boolean var3 = var4;
      if(this.firstError != null) {
         this.printAll((PrintWriter)var1, var2);
         this.lastError = null;
         this.firstError = null;
         var2 = this.errorCount;
         this.errorCount = 0;
         var3 = var4;
         if(var2 > 0) {
            var3 = true;
         }
      }

      return var3;
   }

   public void clear() {
      this.lastError = null;
      this.firstError = null;
      this.errorCount = 0;
   }

   public void clearErrors() {
      this.errorCount = 0;
   }

   public void error(char var1, SourceLocator var2, String var3) {
      this.error(new SourceError(var1, var2, var3));
   }

   public void error(char var1, SourceLocator var2, String var3, String var4) {
      SourceError var5 = new SourceError(var1, var2, var3);
      var5.code = var4;
      this.error(var5);
   }

   public void error(char var1, String var2) {
      this.error(new SourceError(var1, this.current_filename, this.current_line, this.current_column, var2));
   }

   public void error(char var1, String var2, int var3, int var4, String var5) {
      this.error(new SourceError(var1, var2, var3, var4, var5));
   }

   public void error(char var1, String var2, int var3, int var4, String var5, String var6) {
      SourceError var7 = new SourceError(var1, var2, var3, var4, var5);
      var7.code = var6;
      this.error(var7);
   }

   public void error(char var1, String var2, String var3) {
      SourceError var4 = new SourceError(var1, this.current_filename, this.current_line, this.current_column, var2);
      var4.code = var3;
      this.error(var4);
   }

   public void error(char var1, String var2, Throwable var3) {
      SourceError var4 = new SourceError(var1, this.current_filename, this.current_line, this.current_column, var2);
      var4.fakeException = var3;
      this.error(var4);
   }

   public void error(SourceError var1) {
      if(var1.severity == 102) {
         this.errorCount = 1000;
      } else if(var1.severity != 119) {
         ++this.errorCount;
      }

      if(debugStackTraceOnError && (var1.severity == 101 || var1.severity == 102) || debugStackTraceOnWarning && var1.severity == 119) {
         var1.fakeException = new Throwable();
      }

      if(this.lastError != null && this.lastError.filename != null && !this.lastError.filename.equals(var1.filename)) {
         this.lastPrevFilename = this.lastError;
      }

      SourceError var2 = this.lastPrevFilename;
      SourceError var4;
      if(this.sortMessages && var1.severity != 102) {
         while(true) {
            SourceError var3;
            if(var2 == null) {
               var3 = this.firstError;
            } else {
               var3 = var2.next;
            }

            var4 = var2;
            if(var3 == null) {
               break;
            }

            if(var1.line != 0 && var3.line != 0) {
               var4 = var2;
               if(var1.line < var3.line) {
                  break;
               }

               if(var1.line == var3.line && var1.column != 0 && var3.column != 0 && var1.column < var3.column) {
                  var4 = var2;
                  break;
               }
            }

            var2 = var3;
         }
      } else {
         var4 = this.lastError;
      }

      if(var4 == null) {
         var1.next = this.firstError;
         this.firstError = var1;
      } else {
         var1.next = var4.next;
         var4.next = var1;
      }

      if(var4 == this.lastError) {
         this.lastError = var1;
      }

   }

   public final int getColumnNumber() {
      return this.locator == null?this.current_column:this.locator.getColumnNumber();
   }

   public int getErrorCount() {
      return this.errorCount;
   }

   public SourceError getErrors() {
      return this.firstError;
   }

   public final String getFileName() {
      return this.current_filename;
   }

   public final int getLineNumber() {
      return this.locator == null?this.current_line:this.locator.getLineNumber();
   }

   public String getPublicId() {
      return this.locator == null?null:this.locator.getPublicId();
   }

   public String getSystemId() {
      return this.locator == null?this.current_filename:this.locator.getSystemId();
   }

   public boolean isStableSourceLocation() {
      return false;
   }

   public void printAll(PrintStream var1, int var2) {
      for(SourceError var3 = this.firstError; var3 != null; var3 = var3.next) {
         --var2;
         if(var2 < 0) {
            break;
         }

         var3.println((PrintStream)var1);
      }

   }

   public void printAll(PrintWriter var1, int var2) {
      for(SourceError var3 = this.firstError; var3 != null; var3 = var3.next) {
         --var2;
         if(var2 < 0) {
            break;
         }

         var3.println((PrintWriter)var1);
      }

   }

   public boolean seenErrors() {
      return this.errorCount > 0;
   }

   public boolean seenErrorsOrWarnings() {
      return this.firstError != null;
   }

   public void setColumn(int var1) {
      this.current_column = var1;
   }

   public void setFile(String var1) {
      this.current_filename = var1;
   }

   public void setLine(int var1) {
      this.current_line = var1;
   }

   public void setLine(String var1, int var2, int var3) {
      this.current_filename = var1;
      this.current_line = var2;
      this.current_column = var3;
   }

   public final void setLocation(SourceLocator var1) {
      this.locator = null;
      this.current_line = var1.getLineNumber();
      this.current_column = var1.getColumnNumber();
      this.current_filename = var1.getFileName();
   }

   public final void setSourceLocator(SourceLocator var1) {
      SourceLocator var2 = var1;
      if(var1 == this) {
         var2 = null;
      }

      this.locator = var2;
   }

   public final SourceLocator swapSourceLocator(SourceLocator var1) {
      SourceLocator var2 = this.locator;
      this.locator = var1;
      return var2;
   }

   public String toString(int var1) {
      if(this.firstError == null) {
         return null;
      } else {
         StringBuffer var3 = new StringBuffer();

         for(SourceError var2 = this.firstError; var2 != null; var2 = var2.next) {
            --var1;
            if(var1 < 0) {
               break;
            }

            var3.append(var2);
            var3.append('\n');
         }

         return var3.toString();
      }
   }
}
