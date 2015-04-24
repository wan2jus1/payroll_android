package gnu.bytecode;

import gnu.bytecode.Attribute;
import gnu.bytecode.ClassType;
import gnu.bytecode.ClassTypeWriter;
import gnu.bytecode.SourceFileAttr;
import java.io.DataOutputStream;
import java.io.IOException;

public class SourceDebugExtAttr extends Attribute {

   int curFileIndex = -1;
   String curFileName;
   int curLineIndex = -1;
   byte[] data;
   private String defaultStratumId;
   int dlength;
   int fileCount;
   int[] fileIDs;
   String[] fileNames;
   int lineCount;
   int[] lines;
   int maxFileID;
   private String outputFileName;


   public SourceDebugExtAttr(ClassType var1) {
      super("SourceDebugExtension");
      this.addToFrontOf(var1);
   }

   private int fixLine(int var1, int var2) {
      int var4 = this.lines[var2];
      int var5 = this.lines[var2 + 2];
      int var3 = var4;
      if(var1 < var4) {
         if(var2 > 0) {
            return -1;
         }

         this.lines[var2] = var1;
         this.lines[var2 + 2] = var4 + var5 - 1 - var1 + 1;
         this.lines[var2 + 3] = var1;
         var3 = var1;
      }

      var4 = this.lines[var2 + 3] - var3;
      if(var1 < var3 + var5) {
         return var1 + var4;
      } else if(var2 == (this.lineCount - 1) * 5 || var2 == 0 && var1 < this.lines[8]) {
         this.lines[var2 + 2] = var1 - var3 + 1;
         return var1 + var4;
      } else {
         return -1;
      }
   }

   void addFile(String var1) {
      if(this.curFileName != var1 && (var1 == null || !var1.equals(this.curFileName))) {
         this.curFileName = var1;
         String var2 = SourceFileAttr.fixSourceFile(var1);
         int var8 = var2.lastIndexOf(47);
         if(var8 >= 0) {
            var1 = var2.substring(var8 + 1);
            String var3 = var1 + '\n' + var2;
            var2 = var1;
            var1 = var3;
         } else {
            var1 = var2;
         }

         if(this.curFileIndex < 0 || !var1.equals(this.fileNames[this.curFileIndex])) {
            int var7 = this.fileCount;

            int var5;
            for(var5 = 0; var5 < var7; ++var5) {
               if(var5 != this.curFileIndex && var1.equals(this.fileNames[var5])) {
                  this.curFileIndex = var5;
                  this.curLineIndex = -1;
                  return;
               }
            }

            if(this.fileIDs == null) {
               this.fileIDs = new int[5];
               this.fileNames = new String[5];
            } else if(var7 >= this.fileIDs.length) {
               int[] var9 = new int[var7 * 2];
               String[] var4 = new String[var7 * 2];
               System.arraycopy(this.fileIDs, 0, var9, 0, var7);
               System.arraycopy(this.fileNames, 0, var4, 0, var7);
               this.fileIDs = var9;
               this.fileNames = var4;
            }

            ++this.fileCount;
            var5 = this.maxFileID + 1;
            this.maxFileID = var5;
            int var6 = var5 << 1;
            var5 = var6;
            if(var8 >= 0) {
               var5 = var6 + 1;
            }

            this.fileNames[var7] = var1;
            if(this.outputFileName == null) {
               this.outputFileName = var2;
            }

            this.fileIDs[var7] = var5;
            this.curFileIndex = var7;
            this.curLineIndex = -1;
            return;
         }
      }

   }

   public void addStratum(String var1) {
      this.defaultStratumId = var1;
   }

   public void assignConstants(ClassType var1) {
      super.assignConstants(var1);
      StringBuffer var2 = new StringBuffer();
      var2.append("SMAP\n");
      this.nonAsteriskString(this.outputFileName, var2);
      var2.append('\n');
      String var12;
      if(this.defaultStratumId == null) {
         var12 = "Java";
      } else {
         var12 = this.defaultStratumId;
      }

      this.nonAsteriskString(var12, var2);
      var2.append('\n');
      var2.append("*S ");
      var2.append(var12);
      var2.append('\n');
      var2.append("*F\n");

      int var3;
      int var5;
      for(var3 = 0; var3 < this.fileCount; ++var3) {
         var5 = this.fileIDs[var3];
         boolean var4;
         if((var5 & 1) != 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         if(var4) {
            var2.append("+ ");
         }

         var2.append(var5 >> 1);
         var2.append(' ');
         var2.append(this.fileNames[var3]);
         var2.append('\n');
      }

      if(this.lineCount > 0) {
         var5 = 0;
         var2.append("*L\n");
         var3 = 0;
         int var13 = 0;

         int var7;
         do {
            int var6 = this.lines[var13];
            var7 = this.fileIDs[this.lines[var13 + 1]] >> 1;
            int var8 = this.lines[var13 + 2];
            int var9 = this.lines[var13 + 3];
            int var10 = this.lines[var13 + 4];
            var2.append(var6);
            var6 = var5;
            if(var7 != var5) {
               var2.append('#');
               var2.append(var7);
               var6 = var7;
            }

            if(var8 != 1) {
               var2.append(',');
               var2.append(var8);
            }

            var2.append(':');
            var2.append(var9);
            if(var10 != 1) {
               var2.append(',');
               var2.append(var10);
            }

            var2.append('\n');
            var13 += 5;
            var7 = var3 + 1;
            var3 = var7;
            var5 = var6;
         } while(var7 < this.lineCount);
      }

      var2.append("*E\n");

      try {
         this.data = var2.toString().getBytes("UTF-8");
      } catch (Exception var11) {
         throw new RuntimeException(var11.toString());
      }

      this.dlength = this.data.length;
   }

   int fixLine(int var1) {
      int var3;
      if(this.curLineIndex >= 0) {
         var3 = this.fixLine(var1, this.curLineIndex);
         if(var3 >= 0) {
            return var3;
         }
      }

      int var4 = 0;
      int var7 = this.curFileIndex;

      int var5;
      for(var3 = 0; var3 < this.lineCount; ++var3) {
         if(var4 != this.curLineIndex && var7 == this.lines[var4 + 1]) {
            var5 = this.fixLine(var1, var4);
            if(var5 >= 0) {
               this.curLineIndex = var4;
               return var5;
            }
         }

         var4 += 5;
      }

      if(this.lines == null) {
         this.lines = new int[20];
      } else if(var4 >= this.lines.length) {
         int[] var2 = new int[var4 * 2];
         System.arraycopy(this.lines, 0, var2, 0, var4);
         this.lines = var2;
      }

      if(var4 == 0) {
         var5 = var1;
         var3 = var1;
      } else {
         var5 = this.lines[var4 - 5 + 3] + this.lines[var4 - 5 + 2];
         var3 = var5;
         if(var4 == 5) {
            var3 = var5;
            if(var5 < 10000) {
               var3 = 10000;
            }
         }

         var5 = var3;
         var3 = var3;
      }

      this.lines[var4] = var1;
      this.lines[var4 + 1] = var7;
      this.lines[var4 + 2] = 1;
      this.lines[var4 + 3] = var5;
      this.lines[var4 + 4] = 1;
      this.curLineIndex = var4;
      ++this.lineCount;
      return var3;
   }

   public int getLength() {
      return this.dlength;
   }

   void nonAsteriskString(String var1, StringBuffer var2) {
      if(var1 == null || var1.length() == 0 || var1.charAt(0) == 42) {
         var2.append(' ');
      }

      var2.append(var1);
   }

   public void print(ClassTypeWriter var1) {
      var1.print("Attribute \"");
      var1.print(this.getName());
      var1.print("\", length:");
      var1.println(this.dlength);

      try {
         var1.print(new String(this.data, 0, this.dlength, "UTF-8"));
      } catch (Exception var3) {
         var1.print("(Caught ");
         var1.print(var3);
         var1.println(')');
      }

      if(this.dlength > 0 && this.data[this.dlength - 1] != 13 && this.data[this.dlength - 1] != 10) {
         var1.println();
      }

   }

   public void write(DataOutputStream var1) throws IOException {
      var1.write(this.data, 0, this.dlength);
   }
}
