package gnu.bytecode;

import gnu.bytecode.ClassTypeWriter;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Label;
import gnu.bytecode.Method;
import gnu.bytecode.MiscAttr;
import gnu.bytecode.ObjectType;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.bytecode.UninitializedType;
import java.io.DataOutputStream;
import java.io.IOException;

public class StackMapTableAttr extends MiscAttr {

   public static boolean compressStackMapTable = true;
   int countLocals;
   int countStack;
   int[] encodedLocals;
   int[] encodedStack;
   int numEntries;
   int prevPosition = -1;


   public StackMapTableAttr() {
      super("StackMapTable", (byte[])null, 0, 0);
      this.put2(0);
   }

   public StackMapTableAttr(byte[] var1, CodeAttr var2) {
      super("StackMapTable", var1, 0, var1.length);
      this.addToFrontOf(var2);
      this.numEntries = this.u2(0);
   }

   static int[] reallocBuffer(int[] var0, int var1) {
      int[] var2;
      if(var0 == null) {
         var2 = new int[var1 + 10];
      } else {
         var2 = var0;
         if(var1 > var0.length) {
            var2 = new int[var1 + 10];
            System.arraycopy(var0, 0, var2, 0, var0.length);
            return var2;
         }
      }

      return var2;
   }

   public void emitStackMapEntry(Label var1, CodeAttr var2) {
      int var10 = var1.position - this.prevPosition - 1;
      int var9 = var1.localTypes.length;
      int[] var3;
      if(var9 > this.encodedLocals.length) {
         var3 = new int[this.encodedLocals.length + var9];
         System.arraycopy(this.encodedLocals, 0, var3, 0, this.countLocals);
         this.encodedLocals = var3;
      }

      int var11 = var1.stackTypes.length;
      if(var11 > this.encodedStack.length) {
         var3 = new int[this.encodedStack.length + var11];
         System.arraycopy(this.encodedStack, 0, var3, 0, this.countStack);
         this.encodedStack = var3;
      }

      int var7 = 0;
      int var6 = 0;

      int var5;
      int var8;
      for(var5 = 0; var6 < var9; var7 = var8) {
         int var13 = this.encodedLocals[var5];
         int var12 = this.encodeVerificationType(var1.localTypes[var6], var2);
         var8 = var7;
         if(var13 == var12) {
            var8 = var7;
            if(var7 == var5) {
               var8 = var5 + 1;
            }
         }

         label126: {
            this.encodedLocals[var5] = var12;
            if(var12 != 3) {
               var7 = var6;
               if(var12 != 4) {
                  break label126;
               }
            }

            var7 = var6 + 1;
         }

         var6 = var7 + 1;
         ++var5;
      }

      for(var6 = var5; var6 > 0 && this.encodedLocals[var6 - 1] == 0; --var6) {
         ;
      }

      var5 = 0;

      for(var8 = 0; var5 < var11; ++var8) {
         int var10000 = this.encodedStack[var8];
         Type var4 = var1.stackTypes[var5];
         var9 = var5;
         Type var14 = var4;
         if(var4 == Type.voidType) {
            Type[] var15 = var1.stackTypes;
            var9 = var5 + 1;
            var14 = var15[var9];
         }

         var5 = this.encodeVerificationType(var14, var2);
         this.encodedStack[var8] = var5;
         var5 = var9 + 1;
      }

      var9 = var6 - this.countLocals;
      if(compressStackMapTable && var9 == 0 && var6 == var7 && var8 <= 1) {
         if(var8 == 0) {
            if(var10 <= 63) {
               this.put1(var10);
            } else {
               this.put1(251);
               this.put2(var10);
            }
         } else {
            if(var10 <= 63) {
               this.put1(var10 + 64);
            } else {
               this.put1(247);
               this.put2(var10);
            }

            this.emitVerificationType(this.encodedStack[0]);
         }
      } else if(compressStackMapTable && var8 == 0 && var6 < this.countLocals && var7 == var6 && var9 >= -3) {
         this.put1(var9 + 251);
         this.put2(var10);
      } else if(compressStackMapTable && var8 == 0 && this.countLocals == var7 && var9 <= 3) {
         this.put1(var9 + 251);
         this.put2(var10);

         for(var5 = 0; var5 < var9; ++var5) {
            this.emitVerificationType(this.encodedLocals[var7 + var5]);
         }
      } else {
         this.put1(255);
         this.put2(var10);
         this.put2(var6);

         for(var5 = 0; var5 < var6; ++var5) {
            this.emitVerificationType(this.encodedLocals[var5]);
         }

         this.put2(var8);

         for(var5 = 0; var5 < var8; ++var5) {
            this.emitVerificationType(this.encodedStack[var5]);
         }
      }

      this.countLocals = var6;
      this.countStack = var8;
      this.prevPosition = var1.position;
      ++this.numEntries;
   }

   void emitVerificationType(int var1) {
      int var2 = var1 & 255;
      this.put1(var2);
      if(var2 >= 7) {
         this.put2(var1 >> 8);
      }

   }

   int encodeVerificationType(Type var1, CodeAttr var2) {
      if(var1 == null) {
         return 0;
      } else if(var1 instanceof UninitializedType) {
         Label var3 = ((UninitializedType)var1).label;
         return var3 == null?6:var3.position << 8 | 8;
      } else {
         var1 = var1.getImplementationType();
         if(var1 instanceof PrimType) {
            switch(var1.signature.charAt(0)) {
            case 66:
            case 67:
            case 73:
            case 83:
            case 90:
               return 1;
            case 68:
               return 3;
            case 70:
               return 2;
            case 74:
               return 4;
            default:
               return 0;
            }
         } else {
            return var1 == Type.nullType?5:var2.getConstants().addClass((ObjectType)((ObjectType)var1)).index << 8 | 7;
         }
      }
   }

   int extractVerificationType(int var1, int var2) {
      int var3;
      if(var2 != 7) {
         var3 = var2;
         if(var2 != 8) {
            return var3;
         }
      }

      var3 = var2 | this.u2(var1 + 1) << 8;
      return var3;
   }

   int extractVerificationTypes(int var1, int var2, int var3, int[] var4) {
      int var5 = var1;
      var1 = var3;
      var3 = var2;
      var2 = var5;

      while(true) {
         var5 = var3 - 1;
         if(var5 < 0) {
            return var2;
         }

         if(var2 >= this.dataLength) {
            var3 = -1;
         } else {
            byte var7 = this.data[var2];
            int var6 = this.extractVerificationType(var2, var7);
            byte var8;
            if(var7 != 7 && var7 != 8) {
               var8 = 1;
            } else {
               var8 = 3;
            }

            var2 += var8;
            var3 = var6;
         }

         var4[var1] = var3;
         ++var1;
         var3 = var5;
      }
   }

   public Method getMethod() {
      return ((CodeAttr)this.container).getMethod();
   }

   public void print(ClassTypeWriter var1) {
      var1.print("Attribute \"");
      var1.print(this.getName());
      var1.print("\", length:");
      var1.print(this.getLength());
      var1.print(", number of entries: ");
      var1.println(this.numEntries);
      byte var5 = 2;
      int var6 = -1;
      Method var3 = this.getMethod();
      int[] var2 = null;
      byte var4;
      if(var3.getStaticFlag()) {
         var4 = 0;
      } else {
         var4 = 1;
      }

      int var8 = var4 + var3.arg_types.length;
      int var7 = 0;
      int var12 = var5;

      for(int var11 = var8; var7 < this.numEntries && var12 < this.dataLength; ++var7) {
         var8 = var12 + 1;
         int var9 = this.u1(var12);
         var12 = var6 + 1;
         if(var9 <= 127) {
            var6 = var12 + (var9 & 63);
            var12 = var8;
         } else {
            if(var8 + 1 >= this.dataLength) {
               return;
            }

            var6 = var12 + this.u2(var8);
            var12 = var8 + 2;
         }

         var1.print("  offset: ");
         var1.print(var6);
         if(var9 <= 63) {
            var1.println(" - same_frame");
         } else if(var9 > 127 && var9 != 247) {
            if(var9 <= 246) {
               var1.print(" - tag reserved for future use - ");
               var1.println(var9);
               return;
            }

            if(var9 <= 250) {
               var8 = 251 - var9;
               var1.print(" - chop_frame - undefine ");
               var1.print(var8);
               var1.println(" locals");
               var11 -= var8;
            } else if(var9 == 251) {
               var1.println(" - same_frame_extended");
            } else if(var9 <= 254) {
               var8 = var9 - 251;
               var1.print(" - append_frame - define ");
               var1.print(var8);
               var1.println(" more locals");
               var2 = reallocBuffer(var2, var11 + var8);
               var12 = this.extractVerificationTypes(var12, var8, var11, var2);
               this.printVerificationTypes(var2, var11, var8, var1);
               var11 += var8;
            } else {
               if(var12 + 1 >= this.dataLength) {
                  return;
               }

               var11 = this.u2(var12);
               var1.print(" - full_frame.  Locals count: ");
               var1.println(var11);
               var2 = reallocBuffer(var2, var11);
               var8 = this.extractVerificationTypes(var12 + 2, var11, 0, var2);
               this.printVerificationTypes(var2, 0, var11, var1);
               if(var8 + 1 >= this.dataLength) {
                  return;
               }

               var9 = this.u2(var8);
               var1.print("    (end of locals)");
               var12 = Integer.toString(var6).length();

               while(true) {
                  --var12;
                  if(var12 < 0) {
                     var1.print("       Stack count: ");
                     var1.println(var9);
                     var2 = reallocBuffer(var2, var9);
                     var12 = this.extractVerificationTypes(var8 + 2, var9, 0, var2);
                     this.printVerificationTypes(var2, 0, var9, var1);
                     break;
                  }

                  var1.print(' ');
               }
            }
         } else {
            String var10;
            if(var9 <= 127) {
               var10 = " - same_locals_1_stack_item_frame";
            } else {
               var10 = " - same_locals_1_stack_item_frame_extended";
            }

            var1.println(var10);
            var2 = reallocBuffer(var2, 1);
            var12 = this.extractVerificationTypes(var12, 1, 0, var2);
            this.printVerificationTypes(var2, 0, 1, var1);
         }

         if(var12 < 0) {
            var1.println("<ERROR - missing data>");
            return;
         }
      }

   }

   void printVerificationType(int var1, ClassTypeWriter var2) {
      int var3 = var1 & 255;
      switch(var3) {
      case 0:
         var2.print("top/unavailable");
         return;
      case 1:
         var2.print("integer");
         return;
      case 2:
         var2.print("float");
         return;
      case 3:
         var2.print("double");
         return;
      case 4:
         var2.print("long");
         return;
      case 5:
         var2.print("null");
         return;
      case 6:
         var2.print("uninitialized this");
         return;
      case 7:
         var1 >>= 8;
         var2.printOptionalIndex(var1);
         var2.printConstantTersely(var1, 7);
         return;
      case 8:
         var2.print("uninitialized object created at ");
         var2.print(var1 >> 8);
         return;
      default:
         var2.print("<bad verification type tag " + var3 + '>');
      }
   }

   void printVerificationTypes(int[] var1, int var2, int var3, ClassTypeWriter var4) {
      int var5 = 0;

      for(int var6 = 0; var6 < var2 + var3; ++var6) {
         int var7 = var1[var6];
         int var8 = var7 & 255;
         if(var6 >= var2) {
            var4.print("  ");
            if(var5 < 100) {
               if(var5 < 10) {
                  var4.print(' ');
               }

               var4.print(' ');
            }

            var4.print(var5);
            var4.print(": ");
            this.printVerificationType(var7, var4);
            var4.println();
         }

         var7 = var5 + 1;
         if(var8 != 3) {
            var5 = var7;
            if(var8 != 4) {
               continue;
            }
         }

         var5 = var7 + 1;
      }

   }

   public void write(DataOutputStream var1) throws IOException {
      this.put2(0, this.numEntries);
      super.write(var1);
   }
}
