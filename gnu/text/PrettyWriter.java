package gnu.text;

import gnu.lists.LList;
import gnu.mapping.ThreadLocation;
import java.io.IOException;
import java.io.Writer;

public class PrettyWriter extends Writer {

   private static final int BLOCK_PER_LINE_PREFIX_END = -3;
   private static final int BLOCK_PREFIX_LENGTH = -4;
   private static final int BLOCK_SECTION_COLUMN = -2;
   private static final int BLOCK_SECTION_START_LINE = -6;
   private static final int BLOCK_START_COLUMN = -1;
   private static final int BLOCK_SUFFIX_LENGTH = -5;
   private static final int LOGICAL_BLOCK_LENGTH = 6;
   public static final int NEWLINE_FILL = 70;
   public static final int NEWLINE_LINEAR = 78;
   public static final int NEWLINE_LITERAL = 76;
   public static final int NEWLINE_MANDATORY = 82;
   public static final int NEWLINE_MISER = 77;
   public static final int NEWLINE_SPACE = 83;
   static final int QITEM_BASE_SIZE = 2;
   static final int QITEM_BLOCK_END_SIZE = 2;
   static final int QITEM_BLOCK_END_TYPE = 5;
   static final int QITEM_BLOCK_START_BLOCK_END = 4;
   static final int QITEM_BLOCK_START_PREFIX = 5;
   static final int QITEM_BLOCK_START_SIZE = 7;
   static final int QITEM_BLOCK_START_SUFFIX = 6;
   static final int QITEM_BLOCK_START_TYPE = 4;
   static final int QITEM_INDENTATION_AMOUNT = 3;
   static final char QITEM_INDENTATION_BLOCK = 'B';
   static final char QITEM_INDENTATION_CURRENT = 'C';
   static final int QITEM_INDENTATION_KIND = 2;
   static final int QITEM_INDENTATION_SIZE = 4;
   static final int QITEM_INDENTATION_TYPE = 3;
   static final int QITEM_NEWLINE_KIND = 4;
   static final int QITEM_NEWLINE_SIZE = 5;
   static final int QITEM_NEWLINE_TYPE = 2;
   static final int QITEM_NOP_TYPE = 0;
   static final int QITEM_POSN = 1;
   static final int QITEM_SECTION_START_DEPTH = 2;
   static final int QITEM_SECTION_START_SECTION_END = 3;
   static final int QITEM_SECTION_START_SIZE = 4;
   static final int QITEM_TAB_COLINC = 4;
   static final int QITEM_TAB_COLNUM = 3;
   static final int QITEM_TAB_FLAGS = 2;
   static final int QITEM_TAB_IS_RELATIVE = 2;
   static final int QITEM_TAB_IS_SECTION = 1;
   static final int QITEM_TAB_SIZE = 5;
   static final int QITEM_TAB_TYPE = 6;
   static final int QITEM_TYPE_AND_SIZE = 0;
   static final int QUEUE_INIT_ALLOC_SIZE = 300;
   public static ThreadLocation indentLoc = new ThreadLocation("indent");
   public static int initialBufferSize = 126;
   public static ThreadLocation lineLengthLoc = new ThreadLocation("line-length");
   public static ThreadLocation miserWidthLoc = new ThreadLocation("miser-width");
   int blockDepth;
   int[] blocks;
   public char[] buffer;
   public int bufferFillPointer;
   int bufferOffset;
   int bufferStartColumn;
   int currentBlock;
   int lineLength;
   int lineNumber;
   int miserWidth;
   protected Writer out;
   public int pendingBlocksCount;
   char[] prefix;
   int prettyPrintingMode;
   int[] queueInts;
   int queueSize;
   String[] queueStrings;
   int queueTail;
   char[] suffix;
   boolean wordEndSeen;


   public PrettyWriter(Writer var1) {
      this.lineLength = 80;
      this.miserWidth = 40;
      this.buffer = new char[initialBufferSize];
      this.blocks = new int[60];
      this.blockDepth = 6;
      this.prefix = new char[initialBufferSize];
      this.suffix = new char[initialBufferSize];
      this.queueInts = new int[300];
      this.queueStrings = new String[300];
      this.currentBlock = -1;
      this.out = var1;
      this.prettyPrintingMode = 1;
   }

   public PrettyWriter(Writer var1, int var2) {
      byte var3 = 1;
      super();
      this.lineLength = 80;
      this.miserWidth = 40;
      this.buffer = new char[initialBufferSize];
      this.blocks = new int[60];
      this.blockDepth = 6;
      this.prefix = new char[initialBufferSize];
      this.suffix = new char[initialBufferSize];
      this.queueInts = new int[300];
      this.queueStrings = new String[300];
      this.currentBlock = -1;
      this.out = var1;
      this.lineLength = var2;
      byte var4;
      if(var2 > 1) {
         var4 = var3;
      } else {
         var4 = 0;
      }

      this.prettyPrintingMode = var4;
   }

   public PrettyWriter(Writer var1, boolean var2) {
      this.lineLength = 80;
      this.miserWidth = 40;
      this.buffer = new char[initialBufferSize];
      this.blocks = new int[60];
      this.blockDepth = 6;
      this.prefix = new char[initialBufferSize];
      this.suffix = new char[initialBufferSize];
      this.queueInts = new int[300];
      this.queueStrings = new String[300];
      this.currentBlock = -1;
      this.out = var1;
      byte var3;
      if(var2) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      this.prettyPrintingMode = var3;
   }

   private static int enoughSpace(int var0, int var1) {
      int var2 = var0 * 2;
      var0 += var1 * 5 >> 2;
      return var2 > var0?var2:var0;
   }

   private int getPerLinePrefixEnd() {
      return this.blocks[this.blockDepth - 3];
   }

   private int getPrefixLength() {
      return this.blocks[this.blockDepth - 4];
   }

   private int getQueueSize(int var1) {
      return this.queueInts[var1] >> 16;
   }

   private int getQueueType(int var1) {
      return this.queueInts[var1] & 255;
   }

   private int getSectionColumn() {
      return this.blocks[this.blockDepth - 2];
   }

   private int getSectionStartLine() {
      return this.blocks[this.blockDepth - 6];
   }

   private int getStartColumn() {
      return this.blocks[this.blockDepth - 1];
   }

   private int getSuffixLength() {
      return this.blocks[this.blockDepth - 5];
   }

   private int indexPosn(int var1) {
      return this.bufferOffset + var1;
   }

   private int posnColumn(int var1) {
      return this.indexColumn(this.posnIndex(var1));
   }

   private int posnIndex(int var1) {
      return var1 - this.bufferOffset;
   }

   private void pushLogicalBlock(int var1, int var2, int var3, int var4, int var5) {
      int var7 = this.blockDepth + 6;
      if(var7 >= this.blocks.length) {
         int[] var6 = new int[this.blocks.length * 2];
         System.arraycopy(this.blocks, 0, var6, 0, this.blockDepth);
         this.blocks = var6;
      }

      this.blockDepth = var7;
      this.blocks[this.blockDepth - 1] = var1;
      this.blocks[this.blockDepth - 2] = var1;
      this.blocks[this.blockDepth - 3] = var2;
      this.blocks[this.blockDepth - 4] = var3;
      this.blocks[this.blockDepth - 5] = var4;
      this.blocks[this.blockDepth - 6] = var5;
   }

   public void addIndentation(int var1, boolean var2) {
      if(this.prettyPrintingMode > 0) {
         char var3;
         if(var2) {
            var3 = 67;
         } else {
            var3 = 66;
         }

         this.enqueueIndent(var3, var1);
      }

   }

   public void clearBuffer() {
      this.bufferStartColumn = 0;
      this.bufferFillPointer = 0;
      this.lineNumber = 0;
      this.bufferOffset = 0;
      this.blockDepth = 6;
      this.queueTail = 0;
      this.queueSize = 0;
      this.pendingBlocksCount = 0;
   }

   public void clearWordEnd() {
      this.wordEndSeen = false;
   }

   public void close() throws IOException {
      if(this.out != null) {
         this.forcePrettyOutput();
         this.out.close();
         this.out = null;
      }

      this.buffer = null;
   }

   public void closeThis() throws IOException {
      if(this.out != null) {
         this.forcePrettyOutput();
         this.out = null;
      }

      this.buffer = null;
   }

   int computeTabSize(int var1, int var2, int var3) {
      int var6 = 0;
      int var5 = this.queueInts[var1 + 2];
      boolean var4;
      if((var5 & 1) != 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      boolean var7;
      if((var5 & 2) != 0) {
         var7 = true;
      } else {
         var7 = false;
      }

      if(var4) {
         var6 = var2;
      }

      var2 = this.queueInts[var1 + 3];
      int var8 = this.queueInts[var1 + 4];
      if(var7) {
         var1 = var2;
         if(var8 > 1) {
            var3 = (var3 + var2) % var8;
            var1 = var2;
            if(var3 != 0) {
               var1 = var2 + var3;
            }
         }

         return var1;
      } else {
         return var3 <= var2 + var6?var3 + var6 - var3:var8 - (var3 - var6) % var8;
      }
   }

   public void endLogicalBlock() {
      int var5 = this.enqueue(5, 2);
      --this.pendingBlocksCount;
      int var2;
      int var3;
      if(this.currentBlock < 0) {
         var2 = this.blocks[this.blockDepth - 5];
         var3 = this.blocks[this.blockDepth - 6 - 5];
         if(var2 > var3) {
            this.write((char[])this.suffix, this.suffix.length - var2, var2 - var3);
         }

         this.currentBlock = -1;
      } else {
         int var4 = this.currentBlock;
         int var6 = this.queueInts[var4 + 4];
         if(var6 == 0) {
            this.currentBlock = -1;
         } else {
            var3 = this.queueTail - var4;
            var2 = var3;
            if(var3 > 0) {
               var2 = var3 - this.queueInts.length;
            }

            if(var6 < var2) {
               this.currentBlock = -1;
            } else {
               var3 = var6 + var4;
               var2 = var3;
               if(var3 < 0) {
                  var2 = var3 + this.queueInts.length;
               }

               this.currentBlock = var2;
            }
         }

         String var1 = this.queueStrings[var4 + 6];
         if(var1 != null) {
            this.write((String)var1);
         }

         var3 = var5 - var4;
         var2 = var3;
         if(var3 < 0) {
            var2 = var3 + this.queueInts.length;
         }

         this.queueInts[var4 + 4] = var2;
      }
   }

   public void endLogicalBlock(String var1) {
      if(this.prettyPrintingMode > 0) {
         this.endLogicalBlock();
      } else if(var1 != null) {
         this.write((String)var1);
         return;
      }

   }

   public int enqueue(int var1, int var2) {
      int var5 = this.queueInts.length;
      int var6 = var5 - this.queueTail - this.queueSize;
      if(var6 > 0 && var2 > var6) {
         this.enqueue(0, var6);
      }

      if(this.queueSize + var2 > var5) {
         var6 = enoughSpace(var5, var2);
         int[] var3 = new int[var6];
         String[] var4 = new String[var6];
         int var7 = this.queueTail + this.queueSize - var5;
         if(var7 > 0) {
            System.arraycopy(this.queueInts, 0, var3, 0, var7);
            System.arraycopy(this.queueStrings, 0, var4, 0, var7);
         }

         var7 = var5 - this.queueTail;
         var5 = var6 - var5;
         System.arraycopy(this.queueInts, this.queueTail, var3, this.queueTail + var5, var7);
         System.arraycopy(this.queueStrings, this.queueTail, var4, this.queueTail + var5, var7);
         this.queueInts = var3;
         this.queueStrings = var4;
         if(this.currentBlock >= this.queueTail) {
            this.currentBlock += var5;
         }

         this.queueTail += var5;
      }

      var6 = this.queueTail + this.queueSize;
      var5 = var6;
      if(var6 >= this.queueInts.length) {
         var5 = var6 - this.queueInts.length;
      }

      this.queueInts[var5 + 0] = var2 << 16 | var1;
      if(var2 > 1) {
         this.queueInts[var5 + 1] = this.indexPosn(this.bufferFillPointer);
      }

      this.queueSize += var2;
      return var5;
   }

   public int enqueueIndent(char var1, int var2) {
      int var3 = this.enqueue(3, 4);
      this.queueInts[var3 + 2] = var1;
      this.queueInts[var3 + 3] = var2;
      return var3;
   }

   public void enqueueNewline(int var1) {
      this.wordEndSeen = false;
      int var6 = this.pendingBlocksCount;
      int var7 = this.enqueue(2, 5);
      this.queueInts[var7 + 4] = var1;
      this.queueInts[var7 + 2] = this.pendingBlocksCount;
      this.queueInts[var7 + 3] = 0;
      int var2 = this.queueTail;

      int var4;
      for(int var3 = this.queueSize; var3 > 0; var2 += var4) {
         var4 = var2;
         if(var2 == this.queueInts.length) {
            var4 = 0;
         }

         if(var4 == var7) {
            break;
         }

         var2 = this.getQueueType(var4);
         if((var2 == 2 || var2 == 4) && this.queueInts[var4 + 3] == 0 && var6 <= this.queueInts[var4 + 2]) {
            int var5 = var7 - var4;
            var2 = var5;
            if(var5 < 0) {
               var2 = var5 + this.queueInts.length;
            }

            this.queueInts[var4 + 3] = var2;
         }

         var2 = this.getQueueSize(var4);
         var3 -= var2;
      }

      boolean var8;
      if(var1 != 76 && var1 != 82) {
         var8 = false;
      } else {
         var8 = true;
      }

      this.maybeOutput(var8, false);
   }

   int enqueueTab(int var1, int var2, int var3) {
      int var4 = this.enqueue(6, 5);
      this.queueInts[var4 + 2] = var1;
      this.queueInts[var4 + 3] = var2;
      this.queueInts[var4 + 4] = var3;
      return var4;
   }

   int ensureSpaceInBuffer(int var1) {
      char[] var2 = this.buffer;
      int var5 = var2.length;
      int var4 = this.bufferFillPointer;
      int var6 = var5 - var4;
      if(var6 > 0) {
         return var6;
      } else if(this.prettyPrintingMode > 0 && var4 > this.lineLength) {
         if(!this.maybeOutput(false, false)) {
            this.outputPartialLine();
         }

         return this.ensureSpaceInBuffer(var1);
      } else {
         var5 = enoughSpace(var5, var1);
         char[] var3 = new char[var5];
         this.buffer = var3;
         var1 = var4;

         while(true) {
            --var1;
            if(var1 < 0) {
               return var5 - var4;
            }

            var3[var1] = var2[var1];
         }
      }
   }

   void expandTabs(int var1) {
      int var5 = 0;
      int var4 = 0;
      int var9 = this.bufferStartColumn;
      int var8 = this.getSectionColumn();
      int var6 = this.queueTail;
      int var7 = this.queueSize;

      int var11;
      int var14;
      for(var14 = this.pendingBlocksCount * 6; var7 > 0; var8 = var11) {
         int var10 = var6;
         if(var6 == this.queueInts.length) {
            var10 = 0;
         }

         if(var10 == var1) {
            break;
         }

         int var12;
         int var13;
         if(this.getQueueType(var10) == 6) {
            int var16 = this.posnIndex(this.queueInts[var10 + 1]);
            int var15 = this.computeTabSize(var10, var8, var9 + var16);
            var13 = var4;
            var12 = var9;
            var6 = var5;
            var11 = var8;
            if(var15 != 0) {
               if(var5 * 2 + var14 + 1 >= this.blocks.length) {
                  int[] var2 = new int[this.blocks.length * 2];
                  System.arraycopy(this.blocks, 0, var2, 0, this.blocks.length);
                  this.blocks = var2;
               }

               this.blocks[var5 * 2 + var14] = var16;
               this.blocks[var5 * 2 + var14 + 1] = var15;
               var6 = var5 + 1;
               var13 = var4 + var15;
               var12 = var9 + var15;
               var11 = var8;
            }
         } else {
            label59: {
               if(var10 != 2) {
                  var13 = var4;
                  var12 = var9;
                  var6 = var5;
                  var11 = var8;
                  if(var10 != 4) {
                     break label59;
                  }
               }

               var11 = var9 + this.posnIndex(this.queueInts[var10 + 1]);
               var13 = var4;
               var12 = var9;
               var6 = var5;
            }
         }

         var4 = this.getQueueSize(var10);
         var7 -= var4;
         var8 = var10 + var4;
         var4 = var13;
         var9 = var12;
         var5 = var6;
         var6 = var8;
      }

      if(var5 > 0) {
         var6 = this.bufferFillPointer;
         var7 = var6 + var4;
         char[] var3 = this.buffer;
         char[] var17 = var3;
         var8 = var3.length;
         var1 = var6;
         if(var7 > var8) {
            var17 = new char[enoughSpace(var6, var4)];
            this.buffer = var17;
         }

         this.bufferFillPointer = var7;
         this.bufferOffset -= var4;

         while(true) {
            --var5;
            if(var5 < 0) {
               if(var17 != var3) {
                  System.arraycopy(var3, 0, var17, 0, var1);
               }
               break;
            }

            var6 = this.blocks[var5 * 2 + var14];
            var7 = this.blocks[var5 * 2 + var14 + 1];
            var8 = var6 + var4;
            System.arraycopy(var3, var6, var17, var8, var1 - var6);

            for(var1 = var8 - var7; var1 < var8; ++var1) {
               var17[var1] = 32;
            }

            var4 -= var7;
            var1 = var6;
         }
      }

   }

   int fitsOnLine(int var1, boolean var2) {
      byte var5 = -1;
      int var4 = this.lineLength;
      int var3 = var4;
      if(!this.printReadably()) {
         var3 = var4;
         if(this.getMaxLines() == this.lineNumber) {
            var3 = var4 - 3 - this.getSuffixLength();
         }
      }

      byte var6;
      if(var1 >= 0) {
         var6 = var5;
         if(this.posnColumn(this.queueInts[var1 + 1]) <= var3) {
            var6 = 1;
         }
      } else {
         var6 = var5;
         if(!var2) {
            var6 = var5;
            if(this.indexColumn(this.bufferFillPointer) <= var3) {
               return 0;
            }
         }
      }

      return var6;
   }

   public void flush() {
      if(this.out != null) {
         try {
            this.forcePrettyOutput();
            this.out.flush();
         } catch (IOException var2) {
            throw new RuntimeException(var2.toString());
         }
      }
   }

   public void forcePrettyOutput() throws IOException {
      this.maybeOutput(false, true);
      if(this.bufferFillPointer > 0) {
         this.outputPartialLine();
      }

      this.expandTabs(-1);
      this.bufferStartColumn = this.getColumnNumber();
      this.out.write(this.buffer, 0, this.bufferFillPointer);
      this.bufferFillPointer = 0;
      this.queueSize = 0;
   }

   public int getColumnNumber() {
      int var1 = this.bufferFillPointer;

      int var2;
      char var3;
      do {
         var2 = var1 - 1;
         if(var2 < 0) {
            return this.bufferStartColumn + this.bufferFillPointer;
         }

         var3 = this.buffer[var2];
         if(var3 == 10) {
            break;
         }

         var1 = var2;
      } while(var3 != 13);

      return this.bufferFillPointer - (var2 + 1);
   }

   int getMaxLines() {
      return -1;
   }

   protected int getMiserWidth() {
      return this.miserWidth;
   }

   public int getPrettyPrintingMode() {
      return this.prettyPrintingMode;
   }

   int indexColumn(int var1) {
      int var5 = this.bufferStartColumn;
      int var4 = this.getSectionColumn();
      int var8 = this.indexPosn(var1);
      int var2 = this.queueTail;

      int var7;
      for(int var3 = this.queueSize; var3 > 0; var4 = var7) {
         int var6 = var2;
         if(var2 >= this.queueInts.length) {
            var6 = 0;
         }

         int var9 = this.getQueueType(var6);
         var2 = var5;
         var7 = var4;
         if(var9 != 0) {
            var2 = this.queueInts[var6 + 1];
            if(var2 >= var8) {
               break;
            }

            if(var9 == 6) {
               var2 = var5 + this.computeTabSize(var6, var4, this.posnIndex(var2) + var5);
               var7 = var4;
            } else {
               label25: {
                  if(var9 != 2) {
                     var2 = var5;
                     var7 = var4;
                     if(var9 != 4) {
                        break label25;
                     }
                  }

                  var7 = var5 + this.posnIndex(this.queueInts[var6 + 1]);
                  var2 = var5;
               }
            }
         }

         var4 = this.getQueueSize(var6);
         var3 -= var4;
         var4 += var6;
         var5 = var2;
         var2 = var4;
      }

      return var5 + var1;
   }

   boolean isMisering() {
      int var1 = this.getMiserWidth();
      return var1 > 0 && this.lineLength - this.getStartColumn() <= var1;
   }

   public boolean isPrettyPrinting() {
      return this.prettyPrintingMode > 0;
   }

   public void lineAbbreviationHappened() {
   }

   boolean maybeOutput(boolean param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   void outputLine(int var1) throws IOException {
      char[] var3 = this.buffer;
      boolean var4;
      if(this.queueInts[var1 + 4] == 76) {
         var4 = true;
      } else {
         var4 = false;
      }

      int var5 = this.posnIndex(this.queueInts[var1 + 1]);
      int var6;
      if(var4) {
         var1 = var5;
      } else {
         var1 = var5;

         while(true) {
            var6 = var1 - 1;
            if(var6 < 0) {
               var1 = 0;
               break;
            }

            var1 = var6;
            if(var3[var6] != 32) {
               var1 = var6 + 1;
               break;
            }
         }
      }

      this.out.write(var3, 0, var1);
      var6 = this.lineNumber + 1;
      char[] var2;
      int var7;
      if(!this.printReadably()) {
         var1 = this.getMaxLines();
         if(var1 > 0 && var6 >= var1) {
            this.out.write(" ..");
            var1 = this.getSuffixLength();
            if(var1 != 0) {
               var2 = this.suffix;
               var7 = var2.length;
               this.out.write(var2, var7 - var1, var1);
            }

            this.lineAbbreviationHappened();
         }
      }

      this.lineNumber = var6;
      this.out.write(10);
      this.bufferStartColumn = 0;
      var7 = this.bufferFillPointer;
      if(var4) {
         var1 = this.getPerLinePrefixEnd();
      } else {
         var1 = this.getPrefixLength();
      }

      int var8 = var5 - var1;
      int var9 = var7 - var8;
      var2 = var3;
      int var10 = var3.length;
      if(var9 > var10) {
         var2 = new char[enoughSpace(var10, var9 - var10)];
         this.buffer = var2;
      }

      System.arraycopy(var3, var5, var2, var1, var7 - var5);
      System.arraycopy(this.prefix, 0, var2, 0, var1);
      this.bufferFillPointer = var9;
      this.bufferOffset += var8;
      if(!var4) {
         this.blocks[this.blockDepth - 2] = var1;
         this.blocks[this.blockDepth - 6] = var6;
      }

   }

   void outputPartialLine() {
      int var2;
      int var3;
      for(var2 = this.queueTail; this.queueSize > 0 && this.getQueueType(var2) == 0; this.queueTail = var2) {
         var3 = this.getQueueSize(var2);
         this.queueSize -= var3;
         var3 += var2;
         var2 = var3;
         if(var3 == this.queueInts.length) {
            var2 = 0;
         }
      }

      var3 = this.bufferFillPointer;
      if(this.queueSize > 0) {
         var2 = this.posnIndex(this.queueInts[var2 + 1]);
      } else {
         var2 = var3;
      }

      var3 -= var2;
      if(var2 <= 0) {
         throw new Error("outputPartialLine called when nothing can be output.");
      } else {
         try {
            this.out.write(this.buffer, 0, var2);
         } catch (IOException var4) {
            throw new RuntimeException(var4);
         }

         this.bufferFillPointer = var2;
         this.bufferStartColumn = this.getColumnNumber();
         System.arraycopy(this.buffer, var2, this.buffer, 0, var3);
         this.bufferFillPointer = var3;
         this.bufferOffset += var2;
      }
   }

   boolean printReadably() {
      return true;
   }

   void reallyEndLogicalBlock() {
      int var1 = this.getPrefixLength();
      this.blockDepth -= 6;
      int var2 = this.getPrefixLength();
      if(var2 > var1) {
         while(var1 < var2) {
            this.prefix[var1] = 32;
            ++var1;
         }
      }

   }

   void reallyStartLogicalBlock(int var1, String var2, String var3) {
      int var4 = this.getPerLinePrefixEnd();
      int var6 = this.getPrefixLength();
      int var5 = this.getSuffixLength();
      this.pushLogicalBlock(var1, var4, var6, var5, this.lineNumber);
      this.setIndentation(var1);
      if(var2 != null) {
         this.blocks[this.blockDepth - 3] = var1;
         var4 = var2.length();
         var2.getChars(0, var4, this.suffix, var1 - var4);
      }

      if(var3 != null) {
         char[] var8 = this.suffix;
         var4 = var8.length;
         var6 = var3.length();
         int var7 = var5 + var6;
         var1 = var4;
         if(var7 > var4) {
            var1 = enoughSpace(var4, var6);
            this.suffix = new char[var1];
            System.arraycopy(var8, var4 - var5, this.suffix, var1 - var5, var5);
         }

         var3.getChars(0, var6, var8, var1 - var7);
         this.blocks[this.blockDepth - 5] = var7;
      }

   }

   public void setColumnNumber(int var1) {
      this.bufferStartColumn += var1 - this.getColumnNumber();
   }

   public void setIndentation(int var1) {
      char[] var2 = this.prefix;
      int var6 = var2.length;
      int var4 = this.getPrefixLength();
      int var5 = this.getPerLinePrefixEnd();
      int var3 = var1;
      if(var5 > var1) {
         var3 = var5;
      }

      if(var3 > var6) {
         var2 = new char[enoughSpace(var6, var3 - var6)];
         System.arraycopy(this.prefix, 0, var2, 0, var4);
         this.prefix = var2;
      }

      if(var3 > var4) {
         for(var1 = var4; var1 < var3; ++var1) {
            var2[var1] = 32;
         }
      }

      this.blocks[this.blockDepth - 4] = var3;
   }

   public void setPrettyPrinting(boolean var1) {
      byte var2;
      if(var1) {
         var2 = 0;
      } else {
         var2 = 1;
      }

      this.prettyPrintingMode = var2;
   }

   public void setPrettyPrintingMode(int var1) {
      this.prettyPrintingMode = var1;
   }

   public void startLogicalBlock(String var1, boolean var2, String var3) {
      if(this.queueSize == 0 && this.bufferFillPointer == 0) {
         Object var4 = lineLengthLoc.get((Object)null);
         if(var4 == null) {
            this.lineLength = 80;
         } else {
            this.lineLength = Integer.parseInt(var4.toString());
         }

         var4 = miserWidthLoc.get((Object)null);
         if(var4 != null && var4 != Boolean.FALSE && var4 != LList.Empty) {
            this.miserWidth = Integer.parseInt(var4.toString());
         } else {
            this.miserWidth = -1;
         }

         indentLoc.get((Object)null);
      }

      if(var1 != null) {
         this.write((String)var1);
      }

      if(this.prettyPrintingMode != 0) {
         int var7 = this.enqueue(4, 7);
         this.queueInts[var7 + 2] = this.pendingBlocksCount;
         String[] var8 = this.queueStrings;
         if(!var2) {
            var1 = null;
         }

         var8[var7 + 5] = var1;
         this.queueStrings[var7 + 6] = var3;
         ++this.pendingBlocksCount;
         int var5 = this.currentBlock;
         if(var5 < 0) {
            var5 = 0;
         } else {
            int var6 = var5 - var7;
            var5 = var6;
            if(var6 > 0) {
               var5 = var6 - this.queueInts.length;
            }
         }

         this.queueInts[var7 + 4] = var5;
         this.queueInts[var7 + 3] = 0;
         this.currentBlock = var7;
      }
   }

   public void write(int var1) {
      this.wordEndSeen = false;
      if(var1 == 10 && this.prettyPrintingMode > 0) {
         this.enqueueNewline(76);
      } else {
         this.ensureSpaceInBuffer(1);
         int var2 = this.bufferFillPointer;
         this.buffer[var2] = (char)var1;
         this.bufferFillPointer = var2 + 1;
         if(var1 == 32 && this.prettyPrintingMode > 1 && this.currentBlock < 0) {
            this.enqueueNewline(83);
            return;
         }
      }

   }

   public void write(String var1) {
      this.write((String)var1, 0, var1.length());
   }

   public void write(String var1, int var2, int var3) {
      this.wordEndSeen = false;
      int var6 = var3;
      var3 = var2;

      while(var6 > 0) {
         int var8 = this.ensureSpaceInBuffer(var6);
         var2 = var6;
         if(var6 > var8) {
            var2 = var8;
         }

         var8 = this.bufferFillPointer;
         int var7 = var6 - var2;
         var6 = var8;

         while(true) {
            var8 = var2 - 1;
            if(var8 < 0) {
               this.bufferFillPointer = var6;
               var6 = var7;
               break;
            }

            char var4 = var1.charAt(var3);
            if(var4 == 10 && this.prettyPrintingMode > 0) {
               this.bufferFillPointer = var6;
               this.enqueueNewline(76);
               var2 = this.bufferFillPointer;
            } else {
               char[] var5 = this.buffer;
               int var9 = var6 + 1;
               var5[var6] = var4;
               var2 = var9;
               if(var4 == 32) {
                  var2 = var9;
                  if(this.prettyPrintingMode > 1) {
                     var2 = var9;
                     if(this.currentBlock < 0) {
                        this.bufferFillPointer = var9;
                        this.enqueueNewline(83);
                        var2 = this.bufferFillPointer;
                     }
                  }
               }
            }

            var6 = var2;
            ++var3;
            var2 = var8;
         }
      }

   }

   public void write(char[] var1) {
      this.write((char[])var1, 0, var1.length);
   }

   public void write(char[] var1, int var2, int var3) {
      this.wordEndSeen = false;
      int var7 = var2 + var3;

      label49:
      while(var3 > 0) {
         int var6 = var2;

         while(true) {
            int var5 = var2;
            int var4 = var3;
            if(var6 >= var7) {
               while(true) {
                  var3 = this.ensureSpaceInBuffer(var4);
                  if(var3 >= var4) {
                     var3 = var4;
                  }

                  var6 = this.bufferFillPointer;
                  int var8 = var6 + var3;

                  for(var2 = var5; var6 < var8; ++var2) {
                     this.buffer[var6] = var1[var2];
                     ++var6;
                  }

                  this.bufferFillPointer = var8;
                  var3 = var4 - var3;
                  if(var3 == 0) {
                     continue label49;
                  }

                  var4 = var3;
                  var5 = var2;
               }
            }

            if(this.prettyPrintingMode > 0) {
               char var9 = var1[var6];
               if(var9 == 10 || var9 == 32 && this.currentBlock < 0) {
                  this.write((char[])var1, var2, var6 - var2);
                  this.write(var9);
                  var2 = var6 + 1;
                  var3 = var7 - var2;
                  break;
               }
            }

            ++var6;
         }
      }

   }

   public final void writeBreak(int var1) {
      if(this.prettyPrintingMode > 0) {
         this.enqueueNewline(var1);
      }

   }

   public void writeWordEnd() {
      this.wordEndSeen = true;
   }

   public void writeWordStart() {
      if(this.wordEndSeen) {
         this.write(32);
      }

      this.wordEndSeen = false;
   }
}
