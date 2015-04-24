package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.runtime.collect.Lists;
import com.google.appinventor.components.runtime.util.YailList;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public final class CsvUtil {

   public static YailList fromCsvRow(String var0) throws Exception {
      CsvUtil.CsvParser var2 = new CsvUtil.CsvParser(new StringReader(var0));
      if(var2.hasNext()) {
         YailList var1 = YailList.makeList((List)var2.next());
         if(var2.hasNext()) {
            throw new IllegalArgumentException("CSV text has multiple rows. Expected just one row.");
         } else {
            var2.throwAnyProblem();
            return var1;
         }
      } else {
         throw new IllegalArgumentException("CSV text cannot be parsed as a row.");
      }
   }

   public static YailList fromCsvTable(String var0) throws Exception {
      CsvUtil.CsvParser var2 = new CsvUtil.CsvParser(new StringReader(var0));
      ArrayList var1 = new ArrayList();

      while(var2.hasNext()) {
         var1.add(YailList.makeList((List)var2.next()));
      }

      var2.throwAnyProblem();
      return YailList.makeList((List)var1);
   }

   private static void makeCsvRow(YailList var0, StringBuilder var1) {
      String var2 = "";
      Object[] var3 = var0.toArray();
      int var5 = var3.length;
      int var4 = 0;

      for(String var6 = var2; var4 < var5; ++var4) {
         var2 = var3[var4].toString().replaceAll("\"", "\"\"");
         var1.append(var6).append("\"").append(var2).append("\"");
         var6 = ",";
      }

   }

   public static String toCsvRow(YailList var0) {
      StringBuilder var1 = new StringBuilder();
      makeCsvRow(var0, var1);
      return var1.toString();
   }

   public static String toCsvTable(YailList var0) {
      StringBuilder var1 = new StringBuilder();
      Object[] var4 = var0.toArray();
      int var3 = var4.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         makeCsvRow((YailList)var4[var2], var1);
         var1.append("\r\n");
      }

      return var1.toString();
   }

   private static class CsvParser implements Iterator {

      private final Pattern ESCAPED_QUOTE_PATTERN = Pattern.compile("\"\"");
      private final char[] buf = new char[10240];
      private int cellLength = -1;
      private int delimitedCellLength = -1;
      private final Reader in;
      private Exception lastException;
      private int limit;
      private boolean opened = true;
      private int pos;
      private long previouslyRead;


      public CsvParser(Reader var1) {
         this.in = var1;
      }

      private int checkedIndex(int var1) {
         return var1 < this.limit?var1:this.indexAfterCompactionAndFilling(var1);
      }

      private int compact(int var1) {
         int var2 = this.pos;
         this.pos = 0;
         int var3 = this.limit - var2;
         if(var3 > 0) {
            System.arraycopy(this.buf, var2, this.buf, 0, var3);
         }

         this.limit -= var2;
         this.previouslyRead += (long)var2;
         return var1 - var2;
      }

      private void fill() {
         // $FF: Couldn't be decompiled
      }

      private boolean findDelimOrEnd(int var1) {
         while(true) {
            int var2 = var1;
            if(var1 >= this.limit) {
               var2 = this.indexAfterCompactionAndFilling(var1);
               if(var2 >= this.limit) {
                  this.delimitedCellLength = this.limit - this.pos;
                  return true;
               }
            }

            switch(this.buf[var2]) {
            case 9:
            case 32:
               var1 = var2 + 1;
               break;
            case 10:
            case 44:
               this.delimitedCellLength = this.checkedIndex(var2 + 1) - this.pos;
               return true;
            case 13:
               var2 = this.checkedIndex(var2 + 1);
               var1 = var2;
               if(this.buf[var2] == 10) {
                  var1 = this.checkedIndex(var2 + 1);
               }

               this.delimitedCellLength = var1 - this.pos;
               return true;
            default:
               this.lastException = new IOException("Syntax Error: non-whitespace between closing quote and delimiter or end");
               return false;
            }
         }
      }

      private boolean findUnescapedEndQuote(int var1) {
         int var2 = var1;

         while(true) {
            var1 = var2;
            if(var2 >= this.limit) {
               var1 = this.indexAfterCompactionAndFilling(var2);
               if(var1 >= this.limit) {
                  this.lastException = new IllegalArgumentException("Syntax Error. unclosed quoted cell");
                  return false;
               }
            }

            var2 = var1;
            if(this.buf[var1] == 34) {
               var1 = this.checkedIndex(var1 + 1);
               if(var1 == this.limit) {
                  break;
               }

               var2 = var1;
               if(this.buf[var1] != 34) {
                  break;
               }
            }

            ++var2;
         }

         this.cellLength = var1 - this.pos;
         return this.findDelimOrEnd(var1);
      }

      private boolean findUnquotedCellEnd(int var1) {
         while(true) {
            int var2 = var1;
            if(var1 >= this.limit) {
               var2 = this.indexAfterCompactionAndFilling(var1);
               if(var2 >= this.limit) {
                  var1 = this.limit - this.pos;
                  this.cellLength = var1;
                  this.delimitedCellLength = var1;
                  return true;
               }
            }

            switch(this.buf[var2]) {
            case 10:
            case 44:
               this.cellLength = var2 - this.pos;
               this.delimitedCellLength = this.cellLength + 1;
               return true;
            case 13:
               this.cellLength = var2 - this.pos;
               var2 = this.checkedIndex(var2 + 1);
               var1 = var2;
               if(this.buf[var2] == 10) {
                  var1 = this.checkedIndex(var2 + 1);
               }

               this.delimitedCellLength = var1 - this.pos;
               return true;
            case 34:
               this.lastException = new IllegalArgumentException("Syntax Error: quote in unquoted cell");
               return false;
            default:
               var1 = var2 + 1;
            }
         }
      }

      private int indexAfterCompactionAndFilling(int var1) {
         int var2 = var1;
         if(this.pos > 0) {
            var2 = this.compact(var1);
         }

         this.fill();
         return var2;
      }

      private boolean lookingAtCell() {
         return this.buf[this.pos] == 34?this.findUnescapedEndQuote(this.pos + 1):this.findUnquotedCellEnd(this.pos);
      }

      public long getCharPosition() {
         return this.previouslyRead + (long)this.pos;
      }

      public boolean hasNext() {
         if(this.limit == 0) {
            this.fill();
         }

         return (this.pos < this.limit || this.indexAfterCompactionAndFilling(this.pos) < this.limit) && this.lookingAtCell();
      }

      public ArrayList next() {
         ArrayList var1 = Lists.newArrayList();

         boolean var3;
         boolean var4;
         do {
            if(this.buf[this.pos] != 34) {
               var1.add((new String(this.buf, this.pos, this.cellLength)).trim());
            } else {
               String var2 = new String(this.buf, this.pos + 1, this.cellLength - 2);
               var1.add(this.ESCAPED_QUOTE_PATTERN.matcher(var2).replaceAll("\"").trim());
            }

            if(this.delimitedCellLength > 0 && this.buf[this.pos + this.delimitedCellLength - 1] == 44) {
               var3 = true;
            } else {
               var3 = false;
            }

            this.pos += this.delimitedCellLength;
            this.cellLength = -1;
            this.delimitedCellLength = -1;
            if(this.pos >= this.limit && this.indexAfterCompactionAndFilling(this.pos) >= this.limit) {
               var4 = false;
            } else {
               var4 = true;
            }
         } while(var3 && var4 && this.lookingAtCell());

         return var1;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      public void skip(long var1) throws IOException {
         while(true) {
            if(var1 > 0L) {
               int var3 = this.in.read(this.buf, 0, Math.min((int)var1, this.buf.length));
               if(var3 >= 0) {
                  this.previouslyRead += (long)var3;
                  var1 -= (long)var3;
                  continue;
               }
            }

            return;
         }
      }

      public void throwAnyProblem() throws Exception {
         if(this.lastException != null) {
            throw this.lastException;
         }
      }
   }
}
