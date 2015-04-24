package gnu.bytecode;

import gnu.bytecode.ArrayType;
import gnu.bytecode.AttrContainer;
import gnu.bytecode.Attribute;
import gnu.bytecode.ClassType;
import gnu.bytecode.ClassTypeWriter;
import gnu.bytecode.ConstantPool;
import gnu.bytecode.CpoolEntry;
import gnu.bytecode.CpoolValue2;
import gnu.bytecode.ExitableBlock;
import gnu.bytecode.Field;
import gnu.bytecode.IfState;
import gnu.bytecode.Label;
import gnu.bytecode.LineNumbersAttr;
import gnu.bytecode.LocalVarsAttr;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.PrimType;
import gnu.bytecode.Scope;
import gnu.bytecode.SourceDebugExtAttr;
import gnu.bytecode.StackMapTableAttr;
import gnu.bytecode.SwitchState;
import gnu.bytecode.TryState;
import gnu.bytecode.Type;
import gnu.bytecode.UninitializedType;
import gnu.bytecode.Variable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;

public class CodeAttr extends Attribute implements AttrContainer {

   public static final int DONT_USE_JSR = 2;
   static final int FIXUP_CASE = 3;
   static final int FIXUP_DEFINE = 1;
   static final int FIXUP_DELETE3 = 8;
   static final int FIXUP_GOTO = 4;
   static final int FIXUP_JSR = 5;
   static final int FIXUP_LINE_NUMBER = 15;
   static final int FIXUP_LINE_PC = 14;
   static final int FIXUP_MOVE = 9;
   static final int FIXUP_MOVE_TO_END = 10;
   static final int FIXUP_NONE = 0;
   static final int FIXUP_SWITCH = 2;
   static final int FIXUP_TRANSFER = 6;
   static final int FIXUP_TRANSFER2 = 7;
   static final int FIXUP_TRY = 11;
   static final int FIXUP_TRY_END = 12;
   static final int FIXUP_TRY_HANDLER = 13;
   public static final int GENERATE_STACK_MAP_TABLE = 1;
   public static boolean instructionLineMode = false;
   int PC;
   int SP;
   Attribute attributes;
   byte[] code;
   ExitableBlock currentExitableBlock;
   short[] exception_table;
   int exception_table_length;
   int exitableBlockLevel;
   int fixup_count;
   Label[] fixup_labels;
   int[] fixup_offsets;
   int flags;
   IfState if_stack;
   LineNumbersAttr lines;
   Type[] local_types;
   public LocalVarsAttr locals;
   private int max_locals;
   private int max_stack;
   Label previousLabel;
   SourceDebugExtAttr sourceDbgExt;
   public StackMapTableAttr stackMap;
   public Type[] stack_types;
   TryState try_stack;
   private boolean unreachable_here;
   boolean[] varsSetInCurrentBlock;


   public CodeAttr(Method var1) {
      super("Code");
      this.addToFrontOf(var1);
      var1.code = this;
      if(var1.getDeclaringClass().getClassfileMajorVersion() >= 50) {
         this.flags |= 3;
      }

   }

   private int adjustTypedOp(char var1) {
      switch(var1) {
      case 66:
      case 90:
         return 5;
      case 67:
         return 6;
      case 68:
         return 3;
      case 70:
         return 2;
      case 73:
         return 0;
      case 74:
         return 1;
      case 83:
         return 7;
      default:
         return 4;
      }
   }

   private int adjustTypedOp(Type var1) {
      return this.adjustTypedOp(var1.getSignature().charAt(0));
   }

   public static final String calculateSplit(String var0) {
      int var8 = var0.length();
      StringBuffer var1 = new StringBuffer(20);
      int var5 = 0;
      int var3 = 0;

      int var6;
      for(int var2 = 0; var2 < var8; var5 = var6) {
         char var4 = var0.charAt(var2);
         byte var9;
         if(var4 >= 2048) {
            var9 = 3;
         } else if(var4 < 128 && var4 != 0) {
            var9 = 1;
         } else {
            var9 = 2;
         }

         int var7 = var3;
         var6 = var5;
         if(var3 + var9 > '\uffff') {
            var1.append((char)(var2 - var5));
            var6 = var2;
            var7 = 0;
         }

         var3 = var7 + var9;
         ++var2;
      }

      var1.append((char)(var8 - var5));
      return var1.toString();
   }

   public static boolean castNeeded(Type var0, Type var1) {
      Type var2 = var0;
      Type var3 = var1;
      if(var0 instanceof UninitializedType) {
         var2 = ((UninitializedType)var0).getImplementationType();
         var3 = var1;
      }

      while(var2 != var3) {
         if(var3 instanceof ClassType && var2 instanceof ClassType && ((ClassType)var2).isSubclass((ClassType)((ClassType)var3))) {
            return false;
         }

         if(!(var3 instanceof ArrayType) || !(var2 instanceof ArrayType)) {
            return true;
         }

         var3 = ((ArrayType)var3).getComponentType();
         var2 = ((ArrayType)var2).getComponentType();
      }

      return false;
   }

   private void emitBinop(int var1) {
      Type var2 = this.popType().promote();
      Type var3 = this.popType();
      Type var4 = var3.promote();
      if(var4 == var2 && var4 instanceof PrimType) {
         this.emitTypedOp(var1, var4);
         this.pushType(var3);
      } else {
         throw new Error("non-matching or bad types in binary operation");
      }
   }

   private void emitBinop(int var1, char var2) {
      this.popType();
      this.popType();
      this.emitTypedOp(var1, var2);
      this.pushType(Type.signatureToPrimitive(var2));
   }

   private void emitCheckcast(Type var1, int var2) {
      this.reserve(3);
      this.popType();
      this.put1(var2);
      if(var1 instanceof ObjectType) {
         this.putIndex2(this.getConstants().addClass((ObjectType)((ObjectType)var1)));
      } else {
         throw new Error("unimplemented type " + var1 + " in emitCheckcast/emitInstanceof");
      }
   }

   private final void emitFieldop(Field var1, int var2) {
      this.reserve(3);
      this.put1(var2);
      this.putIndex2(this.getConstants().addFieldRef(var1));
   }

   private void emitShift(int var1) {
      Type var2 = this.popType().promote();
      Type var3 = this.popType();
      Type var4 = var3.promote();
      if(var4 != Type.intType && var4 != Type.longType) {
         throw new Error("the value shifted must be an int or a long");
      } else if(var2 != Type.intType) {
         throw new Error("the amount of shift must be an int");
      } else {
         this.emitTypedOp(var1, var4);
         this.pushType(var3);
      }
   }

   private void emitTryEnd(boolean var1) {
      if(!this.try_stack.tryClauseDone) {
         this.try_stack.tryClauseDone = true;
         if(this.try_stack.finally_subr != null) {
            this.try_stack.exception = this.addLocal(Type.javalangThrowableType);
         }

         this.gotoFinallyOrEnd(var1);
         this.try_stack.end_try = this.getLabel();
      }
   }

   private void emitTypedOp(int var1, char var2) {
      this.reserve(1);
      this.put1(this.adjustTypedOp(var2) + var1);
   }

   private void emitTypedOp(int var1, Type var2) {
      this.reserve(1);
      this.put1(this.adjustTypedOp(var2) + var1);
   }

   private final int fixupKind(int var1) {
      return this.fixup_offsets[var1] & 15;
   }

   private final int fixupOffset(int var1) {
      return this.fixup_offsets[var1] >> 4;
   }

   private void gotoFinallyOrEnd(boolean var1) {
      if(this.reachableHere()) {
         if(this.try_stack.saved_result != null) {
            this.emitStore(this.try_stack.saved_result);
         }

         if(this.try_stack.end_label == null) {
            this.try_stack.end_label = new Label();
         }

         if(this.try_stack.finally_subr != null && !this.useJsr()) {
            if(this.try_stack.exitCases != null) {
               this.emitPushInt(0);
            }

            this.emitPushNull();
            if(!var1) {
               this.emitGoto(this.try_stack.finally_subr);
               return;
            }
         } else {
            if(this.try_stack.finally_subr != null) {
               this.emitJsr(this.try_stack.finally_subr);
            }

            this.emitGoto(this.try_stack.end_label);
         }
      }

   }

   private Label mergeLabels(Label var1, Label var2) {
      if(var1 != null) {
         var2.setTypes((Label)var1);
      }

      return var2;
   }

   private void print(String var1, int var2, PrintWriter var3) {
      int var4 = 0;

      int var5;
      for(var5 = -1; var2 >= 0; --var2) {
         var4 = var5 + 1;
         var5 = var1.indexOf(59, var4);
      }

      var3.write(var1, var4, var5 - var4);
   }

   private int readInt(int var1) {
      return this.readUnsignedShort(var1) << 16 | this.readUnsignedShort(var1 + 2);
   }

   private int readUnsignedShort(int var1) {
      return (this.code[var1] & 255) << 8 | this.code[var1 + 1] & 255;
   }

   private int words(Type[] var1) {
      int var2 = 0;
      int var3 = var1.length;

      while(true) {
         --var3;
         if(var3 < 0) {
            return var2;
         }

         if(var1[var3].size > 4) {
            var2 += 2;
         } else {
            ++var2;
         }
      }
   }

   public void addHandler(int var1, int var2, int var3, int var4) {
      int var7 = this.exception_table_length * 4;
      short[] var5;
      if(this.exception_table == null) {
         this.exception_table = new short[20];
      } else if(this.exception_table.length <= var7) {
         var5 = new short[this.exception_table.length * 2];
         System.arraycopy(this.exception_table, 0, var5, 0, var7);
         this.exception_table = var5;
      }

      var5 = this.exception_table;
      int var6 = var7 + 1;
      var5[var7] = (short)var1;
      var5 = this.exception_table;
      var1 = var6 + 1;
      var5[var6] = (short)var2;
      var5 = this.exception_table;
      var2 = var1 + 1;
      var5[var1] = (short)var3;
      this.exception_table[var2] = (short)var4;
      ++this.exception_table_length;
   }

   public void addHandler(Label var1, Label var2, ClassType var3) {
      ConstantPool var4 = this.getConstants();
      int var5;
      if(var3 == null) {
         var5 = 0;
      } else {
         var5 = var4.addClass((ObjectType)var3).index;
      }

      this.fixupAdd(11, var1);
      this.fixupAdd(12, var5, var2);
      var2 = new Label();
      var2.localTypes = var1.localTypes;
      var2.stackTypes = new Type[1];
      ClassType var6;
      if(var3 == null) {
         var6 = Type.javalangThrowableType;
      } else {
         var6 = var3;
      }

      var2.stackTypes[0] = var6;
      this.setTypes(var2);
      this.fixupAdd(13, 0, var2);
   }

   public Variable addLocal(Type var1) {
      return this.locals.current_scope.addVariable(this, var1, (String)null);
   }

   public Variable addLocal(Type var1, String var2) {
      return this.locals.current_scope.addVariable(this, var1, var2);
   }

   public void addParamLocals() {
      Method var1 = this.getMethod();
      if((var1.access_flags & 8) == 0) {
         this.addLocal(var1.classfile).setParameter(true);
      }

      int var3 = var1.arg_types.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         this.addLocal(var1.arg_types[var2]).setParameter(true);
      }

   }

   public void assignConstants(ClassType var1) {
      if(this.locals != null && this.locals.container == null && !this.locals.isEmpty()) {
         this.locals.addToFrontOf(this);
      }

      this.processFixups();
      if(this.stackMap != null && this.stackMap.numEntries > 0) {
         this.stackMap.addToFrontOf(this);
      }

      if(instructionLineMode) {
         if(this.lines == null) {
            this.lines = new LineNumbersAttr(this);
         }

         this.lines.linenumber_count = 0;
         int var3 = this.getCodeLength();

         for(int var2 = 0; var2 < var3; ++var2) {
            this.lines.put(var2, var2);
         }
      }

      super.assignConstants(var1);
      Attribute.assignConstants(this, var1);
   }

   public int beginFragment(Label var1) {
      return this.beginFragment(new Label(), var1);
   }

   public int beginFragment(Label var1, Label var2) {
      int var3 = this.fixup_count;
      this.fixupAdd(10, var2);
      var1.define(this);
      return var3;
   }

   public void disAssemble(ClassTypeWriter var1, int var2, int var3) {
      boolean var6 = false;
      int var8 = var2;

      while(var8 < var3) {
         var2 = var8 + 1;
         int var9 = this.code[var8] & 255;
         String var5 = Integer.toString(var8);
         byte var10 = 0;
         int var7 = var5.length();

         while(true) {
            ++var7;
            if(var7 > 3) {
               var1.print(var5);
               var1.print(": ");
               char var4;
               byte[] var13;
               int var14;
               byte var15;
               boolean var17;
               short var16;
               if(var9 < 120) {
                  if(var9 < 87) {
                     if(var9 < 3) {
                        this.print("nop;aconst_null;iconst_m1;", var9, var1);
                        var15 = var10;
                     } else if(var9 < 9) {
                        var1.print("iconst_");
                        var1.print(var9 - 3);
                        var15 = var10;
                     } else if(var9 < 16) {
                        if(var9 < 11) {
                           var4 = 108;
                           var7 = var9 - 9;
                        } else if(var9 < 14) {
                           var4 = 102;
                           var7 = var9 - 11;
                        } else {
                           var4 = 100;
                           var7 = var9 - 14;
                        }

                        var1.print(var4);
                        var1.print("const_");
                        var1.print(var7);
                        var15 = var10;
                     } else if(var9 < 21) {
                        if(var9 < 18) {
                           this.print("bipush ;sipush ;", var9 - 16, var1);
                           if(var9 == 16) {
                              var16 = this.code[var2];
                              ++var2;
                           } else {
                              var16 = (short)this.readUnsignedShort(var2);
                              var2 += 2;
                           }

                           var1.print(var16);
                           var15 = var10;
                        } else {
                           if(var9 == 18) {
                              var15 = 1;
                           } else {
                              var15 = 2;
                           }

                           this.print("ldc;ldc_w;ldc2_w;", var9 - 18, var1);
                        }
                     } else {
                        if(var9 < 54) {
                           var5 = "load";
                        } else {
                           var5 = "store";
                           var9 -= 33;
                        }

                        if(var9 < 26) {
                           var7 = -1;
                           var8 = var9 - 21;
                        } else if(var9 < 46) {
                           var8 = var9 - 26;
                           var7 = var8 % 4;
                           var8 >>= 2;
                        } else {
                           var7 = -2;
                           var8 = var9 - 46;
                        }

                        var1.print("ilfdabcs".charAt(var8));
                        if(var7 == -2) {
                           var1.write(97);
                        }

                        var1.print(var5);
                        if(var7 >= 0) {
                           var1.write(95);
                           var1.print(var7);
                           var17 = var6;
                           var9 = var2;
                        } else {
                           var9 = var2;
                           var17 = var6;
                           if(var7 == -1) {
                              if(var6) {
                                 var14 = this.readUnsignedShort(var2);
                                 var9 = var2 + 2;
                                 var2 = var14;
                              } else {
                                 var14 = this.code[var2] & 255;
                                 var9 = var2 + 1;
                                 var2 = var14;
                              }

                              var17 = false;
                              var1.print(' ');
                              var1.print(var2);
                           }
                        }

                        var2 = var9;
                        var15 = var10;
                        var6 = var17;
                     }
                  } else if(var9 < 96) {
                     this.print("pop;pop2;dup;dup_x1;dup_x2;dup2;dup2_x1;dup2_x2;swap;", var9 - 87, var1);
                     var15 = var10;
                  } else {
                     var1.print("ilfda".charAt((var9 - 96) % 4));
                     this.print("add;sub;mul;div;rem;neg;", var9 - 96 >> 2, var1);
                     var15 = var10;
                  }
               } else if(var9 < 170) {
                  if(var9 < 132) {
                     if((var9 & 1) == 0) {
                        var4 = 105;
                     } else {
                        var4 = 108;
                     }

                     var1.print(var4);
                     this.print("shl;shr;ushr;and;or;xor;", var9 - 120 >> 1, var1);
                     var15 = var10;
                  } else if(var9 == 132) {
                     var1.print("iinc");
                     if(!var6) {
                        var13 = this.code;
                        var7 = var2 + 1;
                        var8 = var13[var2] & 255;
                        var13 = this.code;
                        var2 = var7 + 1;
                        var16 = var13[var7];
                     } else {
                        var8 = this.readUnsignedShort(var2);
                        var2 += 2;
                        var16 = (short)this.readUnsignedShort(var2);
                        var2 += 2;
                        var6 = false;
                     }

                     var1.print(' ');
                     var1.print(var8);
                     var1.print(' ');
                     var1.print(var16);
                     var15 = var10;
                  } else if(var9 < 148) {
                     var1.print("ilfdi".charAt((var9 - 133) / 3));
                     var1.print('2');
                     var1.print("lfdifdildilfbcs".charAt(var9 - 133));
                     var15 = var10;
                  } else if(var9 < 153) {
                     this.print("lcmp;fcmpl;fcmpg;dcmpl;dcmpg;", var9 - 148, var1);
                     var15 = var10;
                  } else if(var9 < 169) {
                     if(var9 < 159) {
                        var1.print("if");
                        this.print("eq;ne;lt;ge;gt;le;", var9 - 153, var1);
                     } else if(var9 < 167) {
                        if(var9 < 165) {
                           var1.print("if_icmp");
                        } else {
                           var1.print("if_acmp");
                           var9 -= 6;
                        }

                        this.print("eq;ne;lt;ge;gt;le;", var9 - 159, var1);
                     } else {
                        this.print("goto;jsr;", var9 - 167, var1);
                     }

                     var16 = (short)this.readUnsignedShort(var2);
                     var1.print(' ');
                     var1.print(var8 + var16);
                     var2 += 2;
                     var15 = var10;
                  } else {
                     var1.print("ret ");
                     if(var6) {
                        var14 = this.readUnsignedShort(var2) + 2;
                     } else {
                        var14 = this.code[var2] & 255;
                        ++var2;
                     }

                     var17 = false;
                     var1.print(var14);
                     var15 = var10;
                     var6 = var17;
                  }
               } else if(var9 < 172) {
                  var7 = var2;
                  if(this.fixup_count <= 0) {
                     var7 = var2 + 3 & -4;
                  }

                  int var12 = this.readInt(var7);
                  var2 = var7 + 4;
                  int var11;
                  if(var9 == 170) {
                     var1.print("tableswitch");
                     var9 = this.readInt(var2);
                     var2 += 4;
                     var11 = this.readInt(var2);
                     var2 += 4;
                     var1.print(" low: ");
                     var1.print(var9);
                     var1.print(" high: ");
                     var1.print(var11);
                     var1.print(" default: ");
                     var1.print(var8 + var12);

                     while(true) {
                        var7 = var2;
                        if(var9 > var11) {
                           break;
                        }

                        var7 = this.readInt(var2);
                        var2 += 4;
                        var1.println();
                        var1.print("  ");
                        var1.print(var9);
                        var1.print(": ");
                        var1.print(var8 + var7);
                        ++var9;
                     }
                  } else {
                     var1.print("lookupswitch");
                     var7 = this.readInt(var2);
                     var2 += 4;
                     var1.print(" npairs: ");
                     var1.print(var7);
                     var1.print(" default: ");
                     var1.print(var8 + var12);

                     while(true) {
                        var9 = var7 - 1;
                        var7 = var2;
                        if(var9 < 0) {
                           break;
                        }

                        var7 = this.readInt(var2);
                        var2 += 4;
                        var11 = this.readInt(var2);
                        var2 += 4;
                        var1.println();
                        var1.print("  ");
                        var1.print(var7);
                        var1.print(": ");
                        var1.print(var8 + var11);
                        var7 = var9;
                     }
                  }

                  var2 = var7;
                  var15 = var10;
               } else if(var9 < 178) {
                  if(var9 < 177) {
                     var1.print("ilfda".charAt(var9 - 172));
                  }

                  var1.print("return");
                  var15 = var10;
               } else if(var9 < 182) {
                  this.print("getstatic;putstatic;getfield;putfield;", var9 - 178, var1);
                  var15 = 2;
               } else if(var9 < 185) {
                  var1.print("invoke");
                  this.print("virtual;special;static;", var9 - 182, var1);
                  var15 = 2;
               } else if(var9 == 185) {
                  var1.print("invokeinterface (");
                  var7 = this.readUnsignedShort(var2);
                  var2 += 2;
                  byte var19 = this.code[var2];
                  var1.print((var19 & 255) + " args)");
                  var1.printConstantOperand(var7);
                  var2 += 2;
                  var15 = var10;
               } else {
                  label206: {
                     if(var9 < 196) {
                        this.print("186;new;newarray;anewarray;arraylength;athrow;checkcast;instanceof;monitorenter;monitorexit;", var9 - 186, var1);
                        if(var9 == 187 || var9 == 189 || var9 == 192 || var9 == 193) {
                           var15 = 2;
                           break label206;
                        }

                        if(var9 == 188) {
                           var13 = this.code;
                           var7 = var2 + 1;
                           byte var18 = var13[var2];
                           var1.print(' ');
                           if(var18 >= 4 && var18 <= 11) {
                              this.print("boolean;char;float;double;byte;short;int;long;", var18 - 4, var1);
                              var2 = var7;
                              var15 = var10;
                           } else {
                              var1.print(var18);
                              var2 = var7;
                              var15 = var10;
                           }
                           break label206;
                        }
                     } else {
                        if(var9 == 196) {
                           var1.print("wide");
                           var6 = true;
                           var15 = var10;
                           break label206;
                        }

                        if(var9 == 197) {
                           var1.print("multianewarray");
                           var7 = this.readUnsignedShort(var2);
                           var8 = var2 + 2;
                           var1.printConstantOperand(var7);
                           var13 = this.code;
                           var2 = var8 + 1;
                           byte var20 = var13[var8];
                           var1.print(' ');
                           var1.print(var20 & 255);
                           var15 = var10;
                           break label206;
                        }

                        if(var9 < 200) {
                           this.print("ifnull;ifnonnull;", var9 - 198, var1);
                           var16 = (short)this.readUnsignedShort(var2);
                           var1.print(' ');
                           var1.print(var8 + var16);
                           var2 += 2;
                           var15 = var10;
                           break label206;
                        }

                        if(var9 < 202) {
                           this.print("goto_w;jsr_w;", var9 - 200, var1);
                           var7 = this.readInt(var2);
                           var1.print(' ');
                           var1.print(var8 + var7);
                           var2 += 4;
                           var15 = var10;
                           break label206;
                        }

                        var1.print(var9);
                     }

                     var15 = var10;
                  }
               }

               if(var15 > 0) {
                  if(var15 == 1) {
                     var13 = this.code;
                     var8 = var2 + 1;
                     var7 = var13[var2] & 255;
                     var2 = var8;
                  } else {
                     var7 = this.readUnsignedShort(var2);
                     var2 += 2;
                  }

                  var1.printConstantOperand(var7);
               }

               var1.println();
               var8 = var2;
               break;
            }

            var1.print(' ');
         }
      }

   }

   public final void emitAdd() {
      this.emitBinop(96);
   }

   public final void emitAdd(char var1) {
      this.emitBinop(96, var1);
   }

   public final void emitAdd(PrimType var1) {
      this.emitBinop(96, var1);
   }

   public final void emitAnd() {
      this.emitBinop(126);
   }

   public final void emitArrayLength() {
      if(!(this.popType() instanceof ArrayType)) {
         throw new Error("non-array type in emitArrayLength");
      } else {
         this.reserve(1);
         this.put1(190);
         this.pushType(Type.intType);
      }
   }

   public void emitArrayLoad() {
      this.popType();
      Type var1 = ((ArrayType)this.popType().getImplementationType()).getComponentType();
      this.emitTypedOp(46, var1);
      this.pushType(var1);
   }

   public void emitArrayLoad(Type var1) {
      this.popType();
      this.popType();
      this.emitTypedOp(46, var1);
      this.pushType(var1);
   }

   public void emitArrayStore() {
      this.popType();
      this.popType();
      this.emitTypedOp(79, ((ArrayType)this.popType().getImplementationType()).getComponentType());
   }

   public void emitArrayStore(Type var1) {
      this.popType();
      this.popType();
      this.popType();
      this.emitTypedOp(79, var1);
   }

   public void emitBinop(int var1, Type var2) {
      this.popType();
      this.popType();
      this.emitTypedOp(var1, var2);
      this.pushType(var2);
   }

   public void emitCatchEnd() {
      this.gotoFinallyOrEnd(false);
      this.try_stack.try_type = null;
   }

   public void emitCatchStart(Variable var1) {
      this.emitTryEnd(false);
      this.setTypes(this.try_stack.start_try.localTypes, Type.typeArray0);
      if(this.try_stack.try_type != null) {
         this.emitCatchEnd();
      }

      ClassType var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = (ClassType)var1.getType();
      }

      this.try_stack.try_type = var2;
      this.addHandler(this.try_stack.start_try, this.try_stack.end_try, var2);
      if(var1 != null) {
         this.emitStore(var1);
      }

   }

   public void emitCheckcast(Type var1) {
      if(castNeeded(this.topType(), var1)) {
         this.emitCheckcast(var1, 192);
         this.pushType(var1);
      }

   }

   public final void emitConvert(Type var1, Type var2) {
      short var5;
      label66: {
         String var3 = var2.getSignature();
         String var4 = var1.getSignature();
         byte var7 = -1;
         if(var3.length() != 1) {
            var5 = var7;
            if(var4.length() != 1) {
               break label66;
            }
         }

         char var8 = var3.charAt(0);
         char var9 = var4.charAt(0);
         if(var9 != var8) {
            if(var1.size < 4) {
               var9 = 73;
            }

            char var6 = var9;
            if(var2.size < 4) {
               this.emitConvert(var1, Type.intType);
               var6 = 73;
            }

            if(var6 != var8) {
               var5 = var7;
               switch(var6) {
               case 68:
                  switch(var8) {
                  case 70:
                     var5 = 144;
                     break label66;
                  case 71:
                  case 72:
                  default:
                     var5 = var7;
                     break label66;
                  case 73:
                     var5 = 142;
                     break label66;
                  case 74:
                     var5 = 143;
                  }
               case 69:
               case 71:
               case 72:
                  break label66;
               case 70:
                  switch(var8) {
                  case 68:
                     var5 = 141;
                     break label66;
                  case 73:
                     var5 = 139;
                     break label66;
                  case 74:
                     var5 = 140;
                     break label66;
                  default:
                     var5 = var7;
                     break label66;
                  }
               case 73:
                  switch(var8) {
                  case 66:
                     var5 = 145;
                     break label66;
                  case 67:
                     var5 = 146;
                     break label66;
                  case 68:
                     var5 = 135;
                     break label66;
                  case 70:
                     var5 = 134;
                     break label66;
                  case 74:
                     var5 = 133;
                     break label66;
                  case 83:
                     var5 = 147;
                     break label66;
                  default:
                     var5 = var7;
                     break label66;
                  }
               case 74:
                  switch(var8) {
                  case 68:
                     var5 = 138;
                     break label66;
                  case 69:
                  case 71:
                  case 72:
                  default:
                     var5 = var7;
                     break label66;
                  case 70:
                     var5 = 137;
                     break label66;
                  case 73:
                     var5 = 136;
                     break label66;
                  }
               default:
                  var5 = var7;
                  break label66;
               }
            }
         }

         return;
      }

      if(var5 < 0) {
         throw new Error("unsupported CodeAttr.emitConvert");
      } else {
         this.reserve(1);
         this.popType();
         this.put1(var5);
         this.pushType(var2);
      }
   }

   public final void emitDiv() {
      this.emitBinop(108);
   }

   public void emitDup() {
      this.reserve(1);
      Type var1 = this.topType();
      byte var2;
      if(var1.size <= 4) {
         var2 = 89;
      } else {
         var2 = 92;
      }

      this.put1(var2);
      this.pushType(var1);
   }

   public void emitDup(int var1) {
      this.emitDup(var1, 0);
   }

   public void emitDup(int var1, int var2) {
      if(var1 != 0) {
         this.reserve(1);
         Type var8 = this.popType();
         Type var4 = null;
         Type var3;
         if(var1 == 1) {
            if(var8.size > 4) {
               throw new Error("using dup for 2-word type");
            }
         } else {
            if(var1 != 2) {
               throw new Error("invalid size to emitDup");
            }

            if(var8.size <= 4) {
               var3 = this.popType();
               var4 = var3;
               if(var3.size > 4) {
                  throw new Error("dup will cause invalid types on stack");
               }
            }
         }

         var3 = null;
         Type var5 = null;
         byte var9;
         if(var2 == 0) {
            if(var1 == 1) {
               var9 = 89;
            } else {
               var9 = 92;
            }
         } else {
            Type var6;
            if(var2 == 1) {
               if(var1 == 1) {
                  var9 = 90;
               } else {
                  var9 = 93;
               }

               var6 = this.popType();
               var3 = var6;
               if(var6.size > 4) {
                  throw new Error("dup will cause invalid types on stack");
               }
            } else {
               if(var2 != 2) {
                  throw new Error("emitDup:  invalid offset");
               }

               byte var10;
               if(var1 == 1) {
                  var10 = 91;
               } else {
                  var10 = 94;
               }

               var6 = this.popType();
               var9 = var10;
               var3 = var6;
               if(var6.size <= 4) {
                  Type var7 = this.popType();
                  var9 = var10;
                  var3 = var6;
                  var5 = var7;
                  if(var7.size > 4) {
                     throw new Error("dup will cause invalid types on stack");
                  }
               }
            }
         }

         this.put1(var9);
         if(var4 != null) {
            this.pushType(var4);
         }

         this.pushType(var8);
         if(var5 != null) {
            this.pushType(var5);
         }

         if(var3 != null) {
            this.pushType(var3);
         }

         if(var4 != null) {
            this.pushType(var4);
         }

         this.pushType(var8);
      }
   }

   public void emitDup(Type var1) {
      byte var2;
      if(var1.size > 4) {
         var2 = 2;
      } else {
         var2 = 1;
      }

      this.emitDup(var2, 0);
   }

   public void emitDupX() {
      this.reserve(1);
      Type var1 = this.popType();
      Type var2 = this.popType();
      byte var3;
      if(var2.size <= 4) {
         if(var1.size <= 4) {
            var3 = 90;
         } else {
            var3 = 93;
         }

         this.put1(var3);
      } else {
         if(var1.size <= 4) {
            var3 = 91;
         } else {
            var3 = 94;
         }

         this.put1(var3);
      }

      this.pushType(var1);
      this.pushType(var2);
      this.pushType(var1);
   }

   public final void emitElse() {
      Label var1 = this.if_stack.end_label;
      if(this.reachableHere()) {
         Label var2 = new Label(this);
         this.if_stack.end_label = var2;
         int var3 = this.SP - this.if_stack.start_stack_size;
         this.if_stack.stack_growth = var3;
         if(var3 > 0) {
            this.if_stack.then_stacked_types = new Type[var3];
            System.arraycopy(this.stack_types, this.if_stack.start_stack_size, this.if_stack.then_stacked_types, 0, var3);
         } else {
            this.if_stack.then_stacked_types = new Type[0];
         }

         this.emitGoto(var2);
      } else {
         this.if_stack.end_label = null;
      }

      while(this.SP > this.if_stack.start_stack_size) {
         this.popType();
      }

      this.SP = this.if_stack.start_stack_size;
      if(var1 != null) {
         var1.define(this);
      }

      this.if_stack.doing_else = true;
   }

   public final void emitFi() {
      boolean var2 = false;
      boolean var1;
      if(!this.if_stack.doing_else) {
         var1 = var2;
         if(this.reachableHere()) {
            var1 = var2;
            if(this.SP != this.if_stack.start_stack_size) {
               throw new Error("at PC " + this.PC + " then clause grows stack with no else clause");
            }
         }
      } else if(this.if_stack.then_stacked_types != null) {
         int var3 = this.if_stack.start_stack_size + this.if_stack.stack_growth;
         if(!this.reachableHere()) {
            if(this.if_stack.stack_growth > 0) {
               System.arraycopy(this.if_stack.then_stacked_types, 0, this.stack_types, this.if_stack.start_stack_size, this.if_stack.stack_growth);
            }

            this.SP = var3;
            var1 = var2;
         } else {
            var1 = var2;
            if(this.SP != var3) {
               throw new Error("at PC " + this.PC + ": SP at end of \'then\' was " + var3 + " while SP at end of \'else\' was " + this.SP);
            }
         }
      } else {
         var1 = var2;
         if(this.unreachable_here) {
            var1 = true;
         }
      }

      if(this.if_stack.end_label != null) {
         this.if_stack.end_label.define(this);
      }

      if(var1) {
         this.setUnreachable();
      }

      this.if_stack = this.if_stack.previous;
   }

   public void emitFinallyEnd() {
      if(this.useJsr()) {
         this.emitRet(this.try_stack.finally_ret_addr);
      } else if(this.try_stack.end_label == null && this.try_stack.exitCases == null) {
         this.emitThrow();
      } else {
         this.emitStore(this.try_stack.exception);
         this.emitLoad(this.try_stack.exception);
         this.emitIfNotNull();
         this.emitLoad(this.try_stack.exception);
         this.emitThrow();
         this.emitElse();
         ExitableBlock var1 = this.try_stack.exitCases;
         if(var1 != null) {
            ExitableBlock var2;
            SwitchState var3;
            for(var3 = this.startSwitch(); var1 != null; var1 = var2) {
               var2 = var1.nextCase;
               var1.nextCase = null;
               var1.currentTryState = null;
               TryState var4 = TryState.outerHandler(this.try_stack.previous, var1.initialTryState);
               if(var4 == var1.initialTryState) {
                  var3.addCaseGoto(var1.switchCase, this, var1.endLabel);
               } else {
                  var3.addCase(var1.switchCase, this);
                  var1.exit(var4);
               }
            }

            this.try_stack.exitCases = null;
            var3.addDefault(this);
            var3.finish(this);
         }

         this.emitFi();
         this.setUnreachable();
      }

      this.popScope();
      this.try_stack.finally_subr = null;
   }

   public void emitFinallyStart() {
      this.emitTryEnd(true);
      if(this.try_stack.try_type != null) {
         this.emitCatchEnd();
      }

      this.try_stack.end_try = this.getLabel();
      this.pushScope();
      if(this.useJsr()) {
         this.SP = 0;
         this.emitCatchStart((Variable)null);
         this.emitStore(this.try_stack.exception);
         this.emitJsr(this.try_stack.finally_subr);
         this.emitLoad(this.try_stack.exception);
         this.emitThrow();
      } else {
         this.setUnreachable();
         int var2 = this.beginFragment(new Label(this));
         this.addHandler(this.try_stack.start_try, this.try_stack.end_try, Type.javalangThrowableType);
         if(this.try_stack.saved_result != null) {
            this.emitStoreDefaultValue(this.try_stack.saved_result);
         }

         if(this.try_stack.exitCases != null) {
            this.emitPushInt(-1);
            this.emitSwap();
         }

         this.emitGoto(this.try_stack.finally_subr);
         this.endFragment(var2);
      }

      this.try_stack.finally_subr.define(this);
      if(this.useJsr()) {
         ClassType var1 = Type.objectType;
         this.try_stack.finally_ret_addr = this.addLocal(var1);
         this.pushType(var1);
         this.emitStore(this.try_stack.finally_ret_addr);
      }

   }

   public final void emitGetField(Field var1) {
      this.popType();
      this.pushType(var1.type);
      this.emitFieldop(var1, 180);
   }

   public final void emitGetStatic(Field var1) {
      this.pushType(var1.type);
      this.emitFieldop(var1, 178);
   }

   public final void emitGoto(Label var1) {
      var1.setTypes((CodeAttr)this);
      this.fixupAdd(4, var1);
      this.reserve(3);
      this.put1(167);
      this.PC += 2;
      this.setUnreachable();
   }

   public final void emitGotoIfCompare1(Label var1, int var2) {
      this.popType();
      this.reserve(3);
      this.emitTransfer(var1, var2);
   }

   public final void emitGotoIfCompare2(Label var1, int var2) {
      boolean var5 = false;
      if(var2 >= 153 && var2 <= 158) {
         Type var3 = this.popType().promote();
         Type var4 = this.popType().promote();
         this.reserve(4);
         char var6 = var4.getSignature().charAt(0);
         char var7 = var3.getSignature().charAt(0);
         if(var2 == 155 || var2 == 158) {
            var5 = true;
         }

         if(var6 == 73 && var7 == 73) {
            var2 += 6;
         } else if(var6 == 74 && var7 == 74) {
            this.put1(148);
         } else {
            short var8;
            if(var6 == 70 && var7 == 70) {
               if(var5) {
                  var8 = 149;
               } else {
                  var8 = 150;
               }

               this.put1(var8);
            } else if(var6 == 68 && var7 == 68) {
               if(var5) {
                  var8 = 151;
               } else {
                  var8 = 152;
               }

               this.put1(var8);
            } else {
               if(var6 != 76 && var6 != 91 || var7 != 76 && var7 != 91 || var2 > 154) {
                  throw new Error("invalid types to emitGotoIfCompare2");
               }

               var2 += 12;
            }
         }

         this.emitTransfer(var1, var2);
      } else {
         throw new Error("emitGotoIfCompare2: logop must be one of ifeq...ifle");
      }
   }

   public final void emitGotoIfEq(Label var1) {
      this.emitGotoIfCompare2(var1, 153);
   }

   public final void emitGotoIfEq(Label var1, boolean var2) {
      short var3;
      if(var2) {
         var3 = 154;
      } else {
         var3 = 153;
      }

      this.emitGotoIfCompare2(var1, var3);
   }

   public final void emitGotoIfGe(Label var1) {
      this.emitGotoIfCompare2(var1, 156);
   }

   public final void emitGotoIfGt(Label var1) {
      this.emitGotoIfCompare2(var1, 157);
   }

   public final void emitGotoIfIntEqZero(Label var1) {
      this.emitGotoIfCompare1(var1, 153);
   }

   public final void emitGotoIfIntGeZero(Label var1) {
      this.emitGotoIfCompare1(var1, 156);
   }

   public final void emitGotoIfIntGtZero(Label var1) {
      this.emitGotoIfCompare1(var1, 157);
   }

   public final void emitGotoIfIntLeZero(Label var1) {
      this.emitGotoIfCompare1(var1, 158);
   }

   public final void emitGotoIfIntLtZero(Label var1) {
      this.emitGotoIfCompare1(var1, 155);
   }

   public final void emitGotoIfIntNeZero(Label var1) {
      this.emitGotoIfCompare1(var1, 154);
   }

   public final void emitGotoIfLe(Label var1) {
      this.emitGotoIfCompare2(var1, 158);
   }

   public final void emitGotoIfLt(Label var1) {
      this.emitGotoIfCompare2(var1, 155);
   }

   public final void emitGotoIfNE(Label var1) {
      this.emitGotoIfCompare2(var1, 154);
   }

   public final void emitIOr() {
      this.emitBinop(128);
   }

   public final void emitIfCompare1(int var1) {
      IfState var2 = new IfState(this);
      if(this.popType().promote() != Type.intType) {
         throw new Error("non-int type to emitIfCompare1");
      } else {
         this.reserve(3);
         this.emitTransfer(var2.end_label, var1);
         var2.start_stack_size = this.SP;
      }
   }

   public final void emitIfEq() {
      IfState var1 = new IfState(this);
      this.emitGotoIfNE(var1.end_label);
      var1.start_stack_size = this.SP;
   }

   public final void emitIfGe() {
      IfState var1 = new IfState(this);
      this.emitGotoIfLt(var1.end_label);
      var1.start_stack_size = this.SP;
   }

   public final void emitIfGt() {
      IfState var1 = new IfState(this);
      this.emitGotoIfLe(var1.end_label);
      var1.start_stack_size = this.SP;
   }

   public final void emitIfIntCompare(int var1) {
      IfState var2 = new IfState(this);
      this.popType();
      this.popType();
      this.reserve(3);
      this.emitTransfer(var2.end_label, var1);
      var2.start_stack_size = this.SP;
   }

   public final void emitIfIntEqZero() {
      this.emitIfCompare1(154);
   }

   public final void emitIfIntLEqZero() {
      this.emitIfCompare1(157);
   }

   public final void emitIfIntLt() {
      this.emitIfIntCompare(162);
   }

   public final void emitIfIntNotZero() {
      this.emitIfCompare1(153);
   }

   public final void emitIfLe() {
      IfState var1 = new IfState(this);
      this.emitGotoIfGt(var1.end_label);
      var1.start_stack_size = this.SP;
   }

   public final void emitIfLt() {
      IfState var1 = new IfState(this);
      this.emitGotoIfGe(var1.end_label);
      var1.start_stack_size = this.SP;
   }

   public final void emitIfNEq() {
      IfState var1 = new IfState(this);
      this.emitGotoIfEq(var1.end_label);
      var1.start_stack_size = this.SP;
   }

   public final void emitIfNotNull() {
      this.emitIfRefCompare1(198);
   }

   public final void emitIfNull() {
      this.emitIfRefCompare1(199);
   }

   public final void emitIfRefCompare1(int var1) {
      IfState var2 = new IfState(this);
      if(!(this.popType() instanceof ObjectType)) {
         throw new Error("non-ref type to emitIfRefCompare1");
      } else {
         this.reserve(3);
         this.emitTransfer(var2.end_label, var1);
         var2.start_stack_size = this.SP;
      }
   }

   public final void emitIfThen() {
      new IfState(this, (Label)null);
   }

   public void emitInc(Variable var1, short var2) {
      if(var1.dead()) {
         throw new Error("attempting to increment dead variable");
      } else {
         int var4 = var1.offset;
         if(var4 >= 0 && var1.isSimple()) {
            this.reserve(6);
            if(var1.getType().getImplementationType().promote() != Type.intType) {
               throw new Error("attempting to increment non-int variable");
            } else {
               boolean var3;
               if(var4 <= 255 && var2 <= 255 && var2 >= -256) {
                  var3 = false;
               } else {
                  var3 = true;
               }

               if(var3) {
                  this.put1(196);
                  this.put1(132);
                  this.put2(var4);
                  this.put2(var2);
               } else {
                  this.put1(132);
                  this.put1(var4);
                  this.put1(var2);
               }
            }
         } else {
            throw new Error("attempting to increment unassigned variable" + var1.getName() + " simple:" + var1.isSimple() + ", offset: " + var4);
         }
      }
   }

   public void emitInstanceof(Type var1) {
      this.emitCheckcast(var1, 193);
      this.pushType(Type.booleanType);
   }

   public void emitInvoke(Method var1) {
      short var2;
      if((var1.access_flags & 8) != 0) {
         var2 = 184;
      } else if(var1.classfile.isInterface()) {
         var2 = 185;
      } else if("<init>".equals(var1.getName())) {
         var2 = 183;
      } else {
         var2 = 182;
      }

      this.emitInvokeMethod(var1, var2);
   }

   public void emitInvokeInterface(Method var1) {
      this.emitInvokeMethod(var1, 185);
   }

   public void emitInvokeMethod(Method var1, int var2) {
      boolean var10 = true;
      byte var7;
      if(var2 == 185) {
         var7 = 5;
      } else {
         var7 = 3;
      }

      this.reserve(var7);
      int var11 = var1.arg_types.length;
      boolean var9;
      if(var2 == 184) {
         var9 = true;
      } else {
         var9 = false;
      }

      boolean var8;
      if(var2 == 183 && "<init>".equals(var1.getName())) {
         var8 = true;
      } else {
         var8 = false;
      }

      boolean var12;
      if((var1.access_flags & 8) != 0) {
         var12 = var10;
      } else {
         var12 = false;
      }

      if(var9 != var12) {
         throw new Error("emitInvokeXxx static flag mis-match method.flags=" + var1.access_flags);
      } else {
         int var13 = var11;
         if(!var9) {
            var13 = var11;
            if(!var8) {
               var13 = var11 + 1;
            }
         }

         this.put1(var2);
         this.putIndex2(this.getConstants().addMethodRef(var1));
         int var14 = var13;
         if(var2 == 185) {
            this.put1(this.words(var1.arg_types) + 1);
            this.put1(0);
            var14 = var13;
         }

         Type var3;
         do {
            --var14;
            if(var14 < 0) {
               if(var8) {
                  var3 = this.popType();
                  if(!(var3 instanceof UninitializedType)) {
                     throw new Error("calling <init> on already-initialized object");
                  }

                  ClassType var4 = ((UninitializedType)var3).ctype;

                  for(var2 = 0; var2 < this.SP; ++var2) {
                     if(this.stack_types[var2] == var3) {
                        this.stack_types[var2] = var4;
                     }
                  }

                  Variable[] var5 = this.locals.used;
                  if(var5 == null) {
                     var2 = 0;
                  } else {
                     var2 = var5.length;
                  }

                  label82:
                  while(true) {
                     var13 = var2 - 1;
                     if(var13 < 0) {
                        if(this.local_types == null) {
                           var2 = 0;
                        } else {
                           var2 = this.local_types.length;
                        }

                        while(true) {
                           var13 = var2 - 1;
                           if(var13 < 0) {
                              break label82;
                           }

                           var2 = var13;
                           if(this.local_types[var13] == var3) {
                              this.local_types[var13] = var4;
                              var2 = var13;
                           }
                        }
                     }

                     Variable var6 = var5[var13];
                     var2 = var13;
                     if(var6 != null) {
                        var2 = var13;
                        if(var6.type == var3) {
                           var6.type = var4;
                           var2 = var13;
                        }
                     }
                  }
               }

               if(var1.return_type.size != 0) {
                  this.pushType(var1.return_type);
               }

               return;
            }

            var3 = this.popType();
         } while(!(var3 instanceof UninitializedType));

         throw new Error("passing " + var3 + " as parameter");
      }
   }

   public void emitInvokeSpecial(Method var1) {
      this.emitInvokeMethod(var1, 183);
   }

   public void emitInvokeStatic(Method var1) {
      this.emitInvokeMethod(var1, 184);
   }

   public void emitInvokeVirtual(Method var1) {
      this.emitInvokeMethod(var1, 182);
   }

   public final void emitJsr(Label var1) {
      this.fixupAdd(5, var1);
      this.reserve(3);
      this.put1(168);
      this.PC += 2;
   }

   public final void emitLoad(Variable var1) {
      if(var1.dead()) {
         throw new Error("attempting to push dead variable");
      } else {
         int var3 = var1.offset;
         if(var3 >= 0 && var1.isSimple()) {
            Type var2 = var1.getType().promote();
            this.reserve(4);
            int var4 = this.adjustTypedOp(var2);
            if(var3 <= 3) {
               this.put1(var4 * 4 + 26 + var3);
            } else {
               this.emitMaybeWide(var4 + 21, var3);
            }

            this.pushType(var1.getType());
         } else {
            throw new Error("attempting to load from unassigned variable " + var1 + " simple:" + var1.isSimple() + ", offset: " + var3);
         }
      }
   }

   void emitMaybeWide(int var1, int var2) {
      if(var2 >= 256) {
         this.put1(196);
         this.put1(var1);
         this.put2(var2);
      } else {
         this.put1(var1);
         this.put1(var2);
      }
   }

   public final void emitMonitorEnter() {
      this.popType();
      this.reserve(1);
      this.put1(194);
   }

   public final void emitMonitorExit() {
      this.popType();
      this.reserve(1);
      this.put1(195);
   }

   public final void emitMul() {
      this.emitBinop(104);
   }

   public void emitNew(ClassType var1) {
      this.reserve(3);
      Label var2 = new Label(this);
      var2.defineRaw(this);
      this.put1(187);
      this.putIndex2(this.getConstants().addClass((ObjectType)var1));
      this.pushType(new UninitializedType(var1, var2));
   }

   void emitNewArray(int var1) {
      this.reserve(2);
      this.put1(188);
      this.put1(var1);
   }

   public void emitNewArray(Type var1) {
      this.emitNewArray(var1, 1);
   }

   public void emitNewArray(Type var1, int var2) {
      if(this.popType().promote() != Type.intType) {
         throw new Error("non-int dim. spec. in emitNewArray");
      } else {
         if(var1 instanceof PrimType) {
            byte var3;
            switch(var1.getSignature().charAt(0)) {
            case 66:
               var3 = 8;
               break;
            case 67:
               var3 = 5;
               break;
            case 68:
               var3 = 7;
               break;
            case 70:
               var3 = 6;
               break;
            case 73:
               var3 = 10;
               break;
            case 74:
               var3 = 11;
               break;
            case 83:
               var3 = 9;
               break;
            case 90:
               var3 = 4;
               break;
            default:
               throw new Error("bad PrimType in emitNewArray");
            }

            this.emitNewArray(var3);
         } else if(var1 instanceof ObjectType) {
            this.reserve(3);
            this.put1(189);
            this.putIndex2(this.getConstants().addClass((ObjectType)((ObjectType)var1)));
         } else {
            if(!(var1 instanceof ArrayType)) {
               throw new Error("unimplemented type in emitNewArray");
            }

            this.reserve(4);
            this.put1(197);
            this.putIndex2(this.getConstants().addClass((ObjectType)(new ArrayType(var1))));
            if(var2 < 1 || var2 > 255) {
               throw new Error("dims out of range in emitNewArray");
            }

            this.put1(var2);

            while(true) {
               --var2;
               if(var2 <= 0) {
                  break;
               }

               if(this.popType().promote() != Type.intType) {
                  throw new Error("non-int dim. spec. in emitNewArray");
               }
            }
         }

         this.pushType(new ArrayType(var1));
      }
   }

   public final void emitNot(Type var1) {
      this.emitPushConstant(1, var1);
      this.emitAdd();
      this.emitPushConstant(1, var1);
      this.emitAnd();
   }

   public void emitPop(int var1) {
      for(; var1 > 0; --var1) {
         this.reserve(1);
         if(this.popType().size > 4) {
            this.put1(88);
         } else if(var1 > 1) {
            if(this.popType().size > 4) {
               this.put1(87);
               this.reserve(1);
            }

            this.put1(88);
            --var1;
         } else {
            this.put1(87);
         }
      }

   }

   public void emitPrimop(int var1, int var2, Type var3) {
      this.reserve(1);

      while(true) {
         --var2;
         if(var2 < 0) {
            this.put1(var1);
            this.pushType(var3);
            return;
         }

         this.popType();
      }
   }

   public final void emitPushClass(ObjectType var1) {
      this.emitPushConstant(this.getConstants().addClass((ObjectType)var1));
      this.pushType(Type.javalangClassType);
   }

   public final void emitPushConstant(int var1, Type var2) {
      switch(var2.getSignature().charAt(0)) {
      case 66:
      case 67:
      case 73:
      case 83:
      case 90:
         this.emitPushInt(var1);
         return;
      case 68:
         this.emitPushDouble((double)var1);
         return;
      case 70:
         this.emitPushFloat((float)var1);
         return;
      case 74:
         this.emitPushLong((long)var1);
         return;
      default:
         throw new Error("bad type to emitPushConstant");
      }
   }

   public final void emitPushConstant(CpoolEntry var1) {
      this.reserve(3);
      int var2 = var1.index;
      if(var1 instanceof CpoolValue2) {
         this.put1(20);
         this.put2(var2);
      } else if(var2 < 256) {
         this.put1(18);
         this.put1(var2);
      } else {
         this.put1(19);
         this.put2(var2);
      }
   }

   public void emitPushDefaultValue(Type var1) {
      var1 = var1.getImplementationType();
      if(var1 instanceof PrimType) {
         this.emitPushConstant(0, var1);
      } else {
         this.emitPushNull();
      }
   }

   public void emitPushDouble(double var1) {
      int var3 = (int)var1;
      if((double)var3 == var1 && var3 >= -128 && var3 < 128) {
         if(var3 != 0 && var3 != 1) {
            this.emitPushInt(var3);
            this.reserve(1);
            this.popType();
            this.put1(135);
         } else {
            this.reserve(1);
            this.put1(var3 + 14);
            if(var3 == 0 && Double.doubleToLongBits(var1) != 0L) {
               this.reserve(1);
               this.put1(119);
            }
         }
      } else {
         this.emitPushConstant(this.getConstants().addDouble(var1));
      }

      this.pushType(Type.doubleType);
   }

   public void emitPushFloat(float var1) {
      int var2 = (int)var1;
      if((float)var2 == var1 && var2 >= -128 && var2 < 128) {
         if(var2 >= 0 && var2 <= 2) {
            this.reserve(1);
            this.put1(var2 + 11);
            if(var2 == 0 && Float.floatToIntBits(var1) != 0) {
               this.reserve(1);
               this.put1(118);
            }
         } else {
            this.emitPushInt(var2);
            this.reserve(1);
            this.popType();
            this.put1(134);
         }
      } else {
         this.emitPushConstant(this.getConstants().addFloat(var1));
      }

      this.pushType(Type.floatType);
   }

   public final void emitPushInt(int var1) {
      this.reserve(3);
      if(var1 >= -1 && var1 <= 5) {
         this.put1(var1 + 3);
      } else if(var1 >= -128 && var1 < 128) {
         this.put1(16);
         this.put1(var1);
      } else if(var1 >= -32768 && var1 < '耀') {
         this.put1(17);
         this.put2(var1);
      } else {
         this.emitPushConstant(this.getConstants().addInt(var1));
      }

      this.pushType(Type.intType);
   }

   public void emitPushLong(long var1) {
      if(var1 != 0L && var1 != 1L) {
         if((long)((int)var1) == var1) {
            this.emitPushInt((int)var1);
            this.reserve(1);
            this.popType();
            this.put1(133);
         } else {
            this.emitPushConstant(this.getConstants().addLong(var1));
         }
      } else {
         this.reserve(1);
         this.put1((int)var1 + 9);
      }

      this.pushType(Type.longType);
   }

   public void emitPushNull() {
      this.reserve(1);
      this.put1(1);
      this.pushType(Type.nullType);
   }

   public final void emitPushPrimArray(Object var1, ArrayType var2) {
      Type var9 = var2.getComponentType();
      int var11 = Array.getLength(var1);
      this.emitPushInt(var11);
      this.emitNewArray(var9);
      char var12 = var9.getSignature().charAt(0);

      for(int var10 = 0; var10 < var11; ++var10) {
         long var13 = 0L;
         float var7 = 0.0F;
         double var3 = 0.0D;
         long var15;
         switch(var12) {
         case 66:
            var15 = (long)((byte[])((byte[])var1))[var10];
            var13 = var15;
            if(var15 == 0L) {
               continue;
            }
            break;
         case 67:
            var15 = (long)((char[])((char[])var1))[var10];
            var13 = var15;
            if(var15 == 0L) {
               continue;
            }
            break;
         case 68:
            double var5 = ((double[])((double[])var1))[var10];
            var3 = var5;
            if(var5 == 0.0D) {
               continue;
            }
            break;
         case 70:
            float var8 = ((float[])((float[])var1))[var10];
            var7 = var8;
            if((double)var8 == 0.0D) {
               continue;
            }
            break;
         case 73:
            var15 = (long)((int[])((int[])var1))[var10];
            var13 = var15;
            if(var15 == 0L) {
               continue;
            }
            break;
         case 74:
            var15 = ((long[])((long[])var1))[var10];
            var13 = var15;
            if(var15 == 0L) {
               continue;
            }
            break;
         case 83:
            var15 = (long)((short[])((short[])var1))[var10];
            var13 = var15;
            if(var15 == 0L) {
               continue;
            }
            break;
         case 90:
            if(((boolean[])((boolean[])var1))[var10]) {
               var15 = 1L;
            } else {
               var15 = 0L;
            }

            var13 = var15;
            if(var15 == 0L) {
               continue;
            }
         }

         this.emitDup(var2);
         this.emitPushInt(var10);
         switch(var12) {
         case 66:
         case 67:
         case 73:
         case 83:
         case 90:
            this.emitPushInt((int)var13);
            break;
         case 68:
            this.emitPushDouble(var3);
            break;
         case 70:
            this.emitPushFloat(var7);
            break;
         case 74:
            this.emitPushLong(var13);
         }

         this.emitArrayStore(var9);
      }

   }

   public final void emitPushString(String var1) {
      if(var1 == null) {
         this.emitPushNull();
      } else {
         int var5 = var1.length();
         String var2 = calculateSplit(var1);
         int var8 = var2.length();
         if(var8 <= 1) {
            this.emitPushConstant(this.getConstants().addString((String)var1));
            this.pushType(Type.javalangStringType);
            return;
         }

         if(var8 == 2) {
            char var9 = var2.charAt(0);
            this.emitPushString(var1.substring(0, var9));
            this.emitPushString(var1.substring(var9));
            this.emitInvokeVirtual(Type.javalangStringType.getDeclaredMethod("concat", 1));
         } else {
            ClassType var3 = ClassType.make("java.lang.StringBuffer");
            this.emitNew(var3);
            this.emitDup(var3);
            this.emitPushInt(var5);
            this.emitInvokeSpecial(var3.getDeclaredMethod("<init>", new Type[]{Type.intType}));
            Method var4 = var3.getDeclaredMethod("append", new Type[]{Type.javalangStringType});
            int var6 = 0;

            for(var5 = 0; var5 < var8; ++var5) {
               this.emitDup(var3);
               int var7 = var6 + var2.charAt(var5);
               this.emitPushString(var1.substring(var6, var7));
               this.emitInvokeVirtual(var4);
               var6 = var7;
            }

            this.emitInvokeVirtual(Type.toString_method);
         }

         if(var1 == var1.intern()) {
            this.emitInvokeVirtual(Type.javalangStringType.getDeclaredMethod("intern", 0));
            return;
         }
      }

   }

   public final void emitPushThis() {
      this.emitLoad(this.locals.used[0]);
   }

   public final void emitPutField(Field var1) {
      this.popType();
      this.popType();
      this.emitFieldop(var1, 181);
   }

   public final void emitPutStatic(Field var1) {
      this.popType();
      this.emitFieldop(var1, 179);
   }

   final void emitRawReturn() {
      if(this.getMethod().getReturnType().size == 0) {
         this.reserve(1);
         this.put1(177);
      } else {
         this.emitTypedOp(172, this.popType().promote());
      }

      this.setUnreachable();
   }

   public final void emitRem() {
      this.emitBinop(112);
   }

   public void emitRet(Variable var1) {
      int var2 = var1.offset;
      if(var2 < 256) {
         this.reserve(2);
         this.put1(169);
         this.put1(var2);
      } else {
         this.reserve(4);
         this.put1(196);
         this.put1(169);
         this.put2(var2);
      }
   }

   public final void emitReturn() {
      if(this.try_stack != null) {
         new Error();
      }

      this.emitRawReturn();
   }

   public final void emitShl() {
      this.emitShift(120);
   }

   public final void emitShr() {
      this.emitShift(122);
   }

   public void emitStore(Variable var1) {
      int var2 = var1.offset;
      if(var2 >= 0 && var1.isSimple()) {
         Type var4 = var1.getType().promote();
         this.noteVarType(var2, var4);
         this.reserve(4);
         this.popType();
         int var3 = this.adjustTypedOp(var4);
         if(var2 <= 3) {
            this.put1(var3 * 4 + 59 + var2);
         } else {
            this.emitMaybeWide(var3 + 54, var2);
         }
      } else {
         throw new Error("attempting to store in unassigned " + var1 + " simple:" + var1.isSimple() + ", offset: " + var2);
      }
   }

   public void emitStoreDefaultValue(Variable var1) {
      this.emitPushDefaultValue(var1.getType());
      this.emitStore(var1);
   }

   public final void emitSub() {
      this.emitBinop(100);
   }

   public final void emitSub(char var1) {
      this.emitBinop(100, var1);
   }

   public final void emitSub(PrimType var1) {
      this.emitBinop(100, var1);
   }

   public void emitSwap() {
      this.reserve(1);
      Type var1 = this.popType();
      Type var2 = this.popType();
      if(var1.size <= 4 && var2.size <= 4) {
         this.pushType(var1);
         this.put1(95);
         this.pushType(var2);
      } else {
         this.pushType(var2);
         this.pushType(var1);
         this.emitDupX();
         this.emitPop(1);
      }
   }

   public void emitTailCall(boolean var1, Scope var2) {
      if(var1) {
         Method var3 = this.getMethod();
         int var4;
         if((var3.access_flags & 8) != 0) {
            var4 = 0;
         } else {
            var4 = 1;
         }

         int var5 = var3.arg_types.length;

         label35:
         while(true) {
            int var6 = var5 - 1;
            byte var7;
            if(var6 < 0) {
               var5 = var3.arg_types.length;

               while(true) {
                  var6 = var5 - 1;
                  if(var6 < 0) {
                     break label35;
                  }

                  if(var3.arg_types[var6].size > 4) {
                     var7 = 2;
                  } else {
                     var7 = 1;
                  }

                  var4 -= var7;
                  this.emitStore(this.locals.used[var4]);
                  var5 = var6;
               }
            }

            if(var3.arg_types[var6].size > 4) {
               var7 = 2;
            } else {
               var7 = 1;
            }

            var4 += var7;
            var5 = var6;
         }
      }

      this.emitGoto(var2.start);
   }

   public final void emitThen() {
      this.if_stack.start_stack_size = this.SP;
   }

   public final void emitThrow() {
      this.popType();
      this.reserve(1);
      this.put1(191);
      this.setUnreachable();
   }

   final void emitTransfer(Label var1, int var2) {
      var1.setTypes((CodeAttr)this);
      this.fixupAdd(6, var1);
      this.put1(var2);
      this.PC += 2;
   }

   public void emitTryCatchEnd() {
      if(this.try_stack.finally_subr != null) {
         this.emitFinallyEnd();
      }

      Variable[] var1 = this.try_stack.savedStack;
      if(this.try_stack.end_label == null) {
         this.setUnreachable();
      } else {
         this.setTypes(this.try_stack.start_try.localTypes, Type.typeArray0);
         this.try_stack.end_label.define(this);
         if(var1 != null) {
            int var3 = var1.length;

            while(true) {
               int var4 = var3 - 1;
               if(var4 < 0) {
                  break;
               }

               Variable var2 = var1[var4];
               var3 = var4;
               if(var2 != null) {
                  this.emitLoad(var2);
                  var3 = var4;
               }
            }
         }

         if(this.try_stack.saved_result != null) {
            this.emitLoad(this.try_stack.saved_result);
         }
      }

      if(this.try_stack.saved_result != null || var1 != null) {
         this.popScope();
      }

      this.try_stack = this.try_stack.previous;
   }

   public void emitTryEnd() {
      this.emitTryEnd(false);
   }

   public void emitTryStart(boolean var1, Type var2) {
      Type var3 = var2;
      if(var2 != null) {
         var3 = var2;
         if(var2.isVoid()) {
            var3 = null;
         }
      }

      Variable[] var6 = null;
      if(var3 != null || this.SP > 0) {
         this.pushScope();
      }

      int var5;
      if(this.SP > 0) {
         Variable[] var4 = new Variable[this.SP];
         var5 = 0;

         while(true) {
            var6 = var4;
            if(this.SP <= 0) {
               break;
            }

            Variable var7 = this.addLocal(this.topType());
            this.emitStore(var7);
            var4[var5] = var7;
            ++var5;
         }
      }

      TryState var9 = new TryState(this);
      var9.savedStack = var6;
      if(this.local_types == null) {
         var5 = 0;
      } else {
         var5 = this.local_types.length;
      }

      while(var5 > 0 && this.local_types[var5 - 1] == null) {
         --var5;
      }

      Type[] var8;
      if(var5 == 0) {
         var8 = Type.typeArray0;
      } else {
         var8 = new Type[var5];
         System.arraycopy(this.local_types, 0, var8, 0, var5);
      }

      var9.start_try.localTypes = var8;
      if(var3 != null) {
         var9.saved_result = this.addLocal(var3);
      }

      if(var1) {
         var9.finally_subr = new Label();
      }

   }

   public final void emitUshr() {
      this.emitShift(124);
   }

   public void emitWithCleanupCatch(Variable var1) {
      this.emitTryEnd();
      Type[] var2;
      if(this.SP > 0) {
         var2 = new Type[this.SP];
         System.arraycopy(this.stack_types, 0, var2, 0, this.SP);
         this.SP = 0;
      } else {
         var2 = null;
      }

      this.try_stack.savedTypes = var2;
      this.try_stack.saved_result = var1;
      int var3 = this.SP;
      this.emitCatchStart(var1);
   }

   public void emitWithCleanupDone() {
      Variable var1 = this.try_stack.saved_result;
      this.try_stack.saved_result = null;
      if(var1 != null) {
         this.emitLoad(var1);
      }

      this.emitThrow();
      this.emitCatchEnd();
      Type[] var2 = this.try_stack.savedTypes;
      this.emitTryCatchEnd();
      if(var2 != null) {
         this.SP = var2.length;
         if(this.SP >= this.stack_types.length) {
            this.stack_types = var2;
         } else {
            System.arraycopy(var2, 0, this.stack_types, 0, this.SP);
         }
      } else {
         this.SP = 0;
      }
   }

   public void emitWithCleanupStart() {
      int var1 = this.SP;
      this.SP = 0;
      this.emitTryStart(false, (Type)null);
      this.SP = var1;
   }

   public final void emitXOr() {
      this.emitBinop(130);
   }

   public void endExitableBlock() {
      ExitableBlock var1 = this.currentExitableBlock;
      var1.finish();
      this.currentExitableBlock = var1.outer;
   }

   public void endFragment(int var1) {
      this.fixup_offsets[var1] = this.fixup_count << 4 | 10;
      Label var2 = this.fixup_labels[var1];
      this.fixupAdd(9, 0, (Label)null);
      var2.define(this);
   }

   public void enterScope(Scope var1) {
      var1.setStartPC(this);
      this.locals.enterScope(var1);
   }

   final void fixupAdd(int var1, int var2, Label var3) {
      if(var3 != null && var1 != 1 && var1 != 0 && var1 != 2 && var1 != 11) {
         var3.needsStackMapEntry = true;
      }

      int var5 = this.fixup_count;
      if(var5 == 0) {
         this.fixup_offsets = new int[30];
         this.fixup_labels = new Label[30];
      } else if(this.fixup_count == this.fixup_offsets.length) {
         int var6 = var5 * 2;
         Label[] var4 = new Label[var6];
         System.arraycopy(this.fixup_labels, 0, var4, 0, var5);
         this.fixup_labels = var4;
         int[] var7 = new int[var6];
         System.arraycopy(this.fixup_offsets, 0, var7, 0, var5);
         this.fixup_offsets = var7;
      }

      this.fixup_offsets[var5] = var2 << 4 | var1;
      this.fixup_labels[var5] = var3;
      this.fixup_count = var5 + 1;
   }

   public final void fixupAdd(int var1, Label var2) {
      this.fixupAdd(var1, this.PC, var2);
   }

   public final void fixupChain(Label var1, Label var2) {
      this.fixupAdd(9, 0, var2);
      var1.defineRaw(this);
   }

   public Variable getArg(int var1) {
      return this.locals.parameter_scope.getVariable(var1);
   }

   public final Attribute getAttributes() {
      return this.attributes;
   }

   public byte[] getCode() {
      return this.code;
   }

   public int getCodeLength() {
      return this.PC;
   }

   public final ConstantPool getConstants() {
      return this.getMethod().classfile.constants;
   }

   public Scope getCurrentScope() {
      return this.locals.current_scope;
   }

   public final TryState getCurrentTry() {
      return this.try_stack;
   }

   public Label getLabel() {
      Label var1 = new Label();
      var1.defineRaw(this);
      return var1;
   }

   public final int getLength() {
      return this.getCodeLength() + 12 + this.exception_table_length * 8 + Attribute.getLengthAll(this);
   }

   public int getMaxLocals() {
      return this.max_locals;
   }

   public int getMaxStack() {
      return this.max_stack;
   }

   public final Method getMethod() {
      return (Method)this.getContainer();
   }

   public final int getPC() {
      return this.PC;
   }

   public final int getSP() {
      return this.SP;
   }

   byte invert_opcode(byte var1) {
      int var2 = var1 & 255;
      if((var2 < 153 || var2 > 166) && (var2 < 198 || var2 > 199)) {
         throw new Error("unknown opcode to invert_opcode");
      } else {
         return (byte)(var2 ^ 1);
      }
   }

   public final boolean isInTry() {
      return this.try_stack != null;
   }

   public Variable lookup(String var1) {
      for(Scope var2 = this.locals.current_scope; var2 != null; var2 = var2.parent) {
         Variable var3 = var2.lookup(var1);
         if(var3 != null) {
            return var3;
         }
      }

      return null;
   }

   void noteParamTypes() {
      Method var3 = this.getMethod();
      int var4 = 0;
      if((var3.access_flags & 8) == 0) {
         ClassType var2 = var3.classfile;
         Object var1 = var2;
         if("<init>".equals(var3.getName())) {
            var1 = var2;
            if(!"java.lang.Object".equals(var2.getName())) {
               var1 = UninitializedType.uninitializedThis((ClassType)var2);
            }
         }

         this.noteVarType(0, (Type)var1);
         var4 = 0 + 1;
      }

      int var7 = var3.arg_types.length;
      int var6 = 0;
      int var5 = var4;

      while(var6 < var7) {
         Type var9 = var3.arg_types[var6];
         var4 = var5 + 1;
         this.noteVarType(var5, var9);
         var5 = var9.getSizeInWords();

         while(true) {
            --var5;
            if(var5 <= 0) {
               ++var6;
               var5 = var4;
               break;
            }

            ++var4;
         }
      }

      if((this.flags & 1) != 0) {
         this.stackMap = new StackMapTableAttr();
         int[] var10 = new int[var5 + 20];
         var4 = 0;

         for(var6 = 0; var4 < var5; ++var6) {
            label34: {
               var7 = this.stackMap.encodeVerificationType(this.local_types[var4], this);
               var10[var6] = var7;
               int var8 = var7 & 255;
               if(var8 != 3) {
                  var7 = var4;
                  if(var8 != 4) {
                     break label34;
                  }
               }

               var7 = var4 + 1;
            }

            var4 = var7 + 1;
         }

         this.stackMap.encodedLocals = var10;
         this.stackMap.countLocals = var6;
         this.stackMap.encodedStack = new int[10];
         this.stackMap.countStack = 0;
      }

   }

   public void noteVarType(int var1, Type var2) {
      int var6 = var2.getSizeInWords();
      if(this.local_types == null) {
         this.local_types = new Type[var1 + var6 + 20];
      } else if(var1 + var6 > this.local_types.length) {
         Type[] var3 = new Type[(var1 + var6) * 2];
         System.arraycopy(this.local_types, 0, var3, 0, this.local_types.length);
         this.local_types = var3;
      }

      this.local_types[var1] = var2;
      if(this.varsSetInCurrentBlock == null) {
         this.varsSetInCurrentBlock = new boolean[this.local_types.length];
      } else if(this.varsSetInCurrentBlock.length <= var1) {
         boolean[] var7 = new boolean[this.local_types.length];
         System.arraycopy(this.varsSetInCurrentBlock, 0, var7, 0, this.varsSetInCurrentBlock.length);
         this.varsSetInCurrentBlock = var7;
      }

      this.varsSetInCurrentBlock[var1] = true;
      int var4 = var6;
      int var5 = var1;
      if(var1 > 0) {
         var2 = this.local_types[var1 - 1];
         var4 = var6;
         var5 = var1;
         if(var2 != null) {
            var4 = var6;
            var5 = var1;
            if(var2.getSizeInWords() == 2) {
               this.local_types[var1 - 1] = null;
               var5 = var1;
               var4 = var6;
            }
         }
      }

      while(true) {
         --var4;
         if(var4 <= 0) {
            return;
         }

         Type[] var8 = this.local_types;
         ++var5;
         var8[var5] = null;
      }
   }

   public Scope popScope() {
      Scope var1 = this.locals.current_scope;
      this.locals.current_scope = var1.parent;
      var1.freeLocals(this);
      var1.end = this.getLabel();
      return var1;
   }

   public final Type popType() {
      if(this.SP <= 0) {
         throw new Error("popType called with empty stack " + this.getMethod());
      } else {
         Type[] var1 = this.stack_types;
         int var2 = this.SP - 1;
         this.SP = var2;
         Type var3 = var1[var2];
         if(var3.size == 8 && !this.popType().isVoid()) {
            throw new Error("missing void type on stack");
         } else {
            return var3;
         }
      }
   }

   public void print(ClassTypeWriter var1) {
      var1.print("Attribute \"");
      var1.print(this.getName());
      var1.print("\", length:");
      var1.print(this.getLength());
      var1.print(", max_stack:");
      var1.print(this.max_stack);
      var1.print(", max_locals:");
      var1.print(this.max_locals);
      var1.print(", code_length:");
      int var2 = this.getCodeLength();
      var1.println(var2);
      this.disAssemble(var1, 0, var2);
      if(this.exception_table_length > 0) {
         var1.print("Exceptions (count: ");
         var1.print(this.exception_table_length);
         var1.println("):");
         int var3 = this.exception_table_length;
         var2 = 0;

         while(true) {
            --var3;
            if(var3 < 0) {
               break;
            }

            var1.print("  start: ");
            var1.print(this.exception_table[var2] & '\uffff');
            var1.print(", end: ");
            var1.print(this.exception_table[var2 + 1] & '\uffff');
            var1.print(", handler: ");
            var1.print(this.exception_table[var2 + 2] & '\uffff');
            var1.print(", type: ");
            int var4 = this.exception_table[var2 + 3] & '\uffff';
            if(var4 == 0) {
               var1.print("0 /* finally */");
            } else {
               var1.printOptionalIndex(var4);
               var1.printConstantTersely(var4, 7);
            }

            var1.println();
            var2 += 4;
         }
      }

      var1.printAttributes(this);
   }

   public void processFixups() {
      if(this.fixup_count > 0) {
         int var8 = 0;
         int var10 = this.fixup_count;
         this.fixupAdd(9, 0, (Label)null);
         int var7 = 0;

         while(true) {
            Label var2;
            int var6;
            int var9;
            int var11;
            int var12;
            int var13;
            label220:
            while(true) {
               var13 = this.fixup_offsets[var7];
               var12 = var13 >> 4;
               var2 = this.fixup_labels[var7];
               var6 = var8;
               var9 = var7;
               var11 = var10;
               switch(var13 & 15) {
               case 0:
               case 3:
               case 8:
                  break;
               case 1:
                  var2.position += var8;
                  var6 = var8;
                  var9 = var7;
                  break;
               case 2:
                  var6 = var8 + 3;
                  var9 = var7;
                  break;
               case 4:
                  if(var2.first_fixup == var7 + 1 && this.fixupOffset(var7 + 1) == var12 + 3) {
                     this.fixup_offsets[var7] = var12 << 4 | 8;
                     this.fixup_labels[var7] = null;
                     var6 = var8 - 3;
                     var9 = var7;
                     break;
                  }
               case 5:
                  var6 = var8;
                  var9 = var7;
                  if(this.PC >= '耀') {
                     var6 = var8 + 2;
                     var9 = var7;
                  }
                  break;
               case 6:
                  var6 = var8;
                  var9 = var7;
                  if(this.PC >= '耀') {
                     var6 = var8 + 5;
                     var9 = var7;
                  }
                  break;
               case 7:
               case 12:
               case 13:
               default:
                  throw new Error("unexpected fixup");
               case 9:
                  break label220;
               case 10:
                  this.fixup_labels[var10] = this.fixup_labels[var7 + 1];
                  var11 = var12;
                  break label220;
               case 11:
                  var9 = var7 + 2;
                  var6 = var8;
                  break;
               case 14:
                  var9 = var7 + 1;
                  var6 = var8;
               }

               var7 = var9 + 1;
               var8 = var6;
            }

            if(var7 + 1 >= this.fixup_count) {
               var6 = this.PC;
            } else {
               var6 = this.fixupOffset(this.fixup_labels[var7 + 1].first_fixup);
            }

            this.fixup_offsets[var7] = var6 << 4 | 9;
            if(var2 == null) {
               var13 = this.PC;
               var9 = 0;
               var6 = 0;

               while(true) {
                  Label var3;
                  if(var6 < this.fixup_count) {
                     label228: {
                        var7 = this.fixup_offsets[var6];
                        var11 = var7 & 15;
                        var3 = this.fixup_labels[var6];
                        var2 = var3;
                        if(var3 != null) {
                           var2 = var3;
                           if(var3.position < 0) {
                              throw new Error("undefined label " + var3);
                           }
                        }

                        while(var2 != null && var11 >= 4 && var11 <= 7 && var2.first_fixup + 1 < this.fixup_count && this.fixup_offsets[var2.first_fixup + 1] == (this.fixup_offsets[var2.first_fixup] & 15 | 4)) {
                           var2 = this.fixup_labels[var2.first_fixup + 1];
                           this.fixup_labels[var6] = var2;
                        }

                        var12 = var7 >> 4;
                        var7 = var9;
                        var10 = var6;
                        var8 = var13;
                        switch(var11) {
                        case 0:
                        case 3:
                           break;
                        case 1:
                           var2.position = var12 + var9;
                           var7 = var9;
                           var10 = var6;
                           var8 = var13;
                           break;
                        case 2:
                           var8 = 3 - (var12 + var9) & 3;
                           var7 = var9 + var8;
                           var8 += var13;
                           var10 = var6;
                           break;
                        case 4:
                        case 5:
                        case 6:
                           var7 = var2.position - (var12 + var9);
                           if((short)var7 == var7) {
                              this.fixup_offsets[var6] = var12 << 4 | 7;
                              var7 = var9;
                              var10 = var6;
                              var8 = var13;
                           } else {
                              byte var17;
                              if(var11 == 6) {
                                 var17 = 5;
                              } else {
                                 var17 = 2;
                              }

                              var8 = var9 + var17;
                              if(var11 == 6) {
                                 var17 = 5;
                              } else {
                                 var17 = 2;
                              }

                              var9 = var13 + var17;
                              var7 = var8;
                              var10 = var6;
                              var8 = var9;
                           }
                           break;
                        case 7:
                        case 10:
                        case 12:
                        case 13:
                        default:
                           throw new Error("unexpected fixup");
                        case 8:
                           var7 = var9 - 3;
                           var8 = var13 - 3;
                           var10 = var6;
                           break;
                        case 9:
                           if(var2 != null) {
                              var6 = var2.first_fixup;
                              var9 = var12 + var9 - this.fixupOffset(var6);
                              continue;
                           }
                           break label228;
                        case 11:
                           var10 = var6 + 2;
                           this.fixup_labels[var10].position = var12 + var9;
                           var8 = var13;
                           var7 = var9;
                           break;
                        case 14:
                           var10 = var6 + 1;
                           var7 = var9;
                           var8 = var13;
                        }

                        var6 = var10 + 1;
                        var9 = var7;
                        var13 = var8;
                        continue;
                     }
                  }

                  byte[] var4 = new byte[var13];
                  int var14 = -1;
                  var9 = 0;
                  var8 = this.fixupOffset(0);
                  var3 = null;
                  var7 = 0;
                  var6 = 0;

                  while(true) {
                     while(var7 < var8) {
                        var4[var6] = this.code[var7];
                        ++var7;
                        ++var6;
                     }

                     var10 = this.fixup_offsets[var9] & 15;
                     Label var5 = this.fixup_labels[var9];
                     var2 = var3;
                     if(var3 != null) {
                        var2 = var3;
                        if(var3.position < var6) {
                           this.stackMap.emitStackMapEntry(var3, this);
                           var2 = null;
                        }
                     }

                     if(var2 != null && var2.position > var6) {
                        throw new Error("labels out of order");
                     }

                     int var15;
                     label171:
                     switch(var10) {
                     case 0:
                        var15 = var14;
                        var3 = var2;
                        var8 = var7;
                        var7 = var6;
                        break;
                     case 1:
                        if(this.stackMap != null && var5 != null && var5.stackTypes != null && var5.needsStackMapEntry) {
                           var3 = this.mergeLabels(var2, var5);
                           var8 = var7;
                           var7 = var6;
                           var15 = var14;
                        } else {
                           var8 = var7;
                           var7 = var6;
                           var3 = var2;
                           var15 = var14;
                        }
                        break;
                     case 2:
                        var8 = 3 - var6 & 3;
                        byte[] var16 = this.code;
                        var11 = var7 + 1;
                        var4[var6] = var16[var7];
                        var7 = var6 + 1;

                        while(true) {
                           --var8;
                           if(var8 < 0) {
                              var10 = var7;
                              var12 = var9;

                              while(true) {
                                 var7 = var10;
                                 var9 = var12;
                                 var8 = var11;
                                 var3 = var2;
                                 var15 = var14;
                                 if(var12 >= this.fixup_count) {
                                    break label171;
                                 }

                                 var7 = var10;
                                 var9 = var12;
                                 var8 = var11;
                                 var3 = var2;
                                 var15 = var14;
                                 if(this.fixupKind(var12 + 1) != 3) {
                                    break label171;
                                 }

                                 ++var12;
                                 var9 = this.fixupOffset(var12);
                                 var7 = var11;

                                 for(var8 = var10; var7 < var9; ++var8) {
                                    var4[var8] = this.code[var7];
                                    ++var7;
                                 }

                                 var9 = this.fixup_labels[var12].position - var6;
                                 var10 = var8 + 1;
                                 var4[var8] = (byte)(var9 >> 24);
                                 var8 = var10 + 1;
                                 var4[var10] = (byte)(var9 >> 16);
                                 var10 = var8 + 1;
                                 var4[var8] = (byte)(var9 >> 8);
                                 var4[var10] = (byte)(var9 & 255);
                                 var11 = var7 + 4;
                                 ++var10;
                              }
                           }

                           var4[var7] = 0;
                           ++var7;
                        }
                     case 3:
                     case 10:
                     case 12:
                     case 13:
                     default:
                        throw new Error("unexpected fixup");
                     case 4:
                     case 5:
                     case 6:
                        var8 = var5.position - var6;
                        byte var1 = this.code[var7];
                        if(var10 == 6) {
                           var1 = this.invert_opcode(var1);
                           var10 = var6 + 1;
                           var4[var6] = var1;
                           var11 = var10 + 1;
                           var4[var10] = 0;
                           var6 = var11 + 1;
                           var4[var11] = 8;
                           var1 = -56;
                        } else {
                           var1 = (byte)(var1 + 33);
                        }

                        var10 = var6 + 1;
                        var4[var6] = var1;
                        var6 = var10 + 1;
                        var4[var10] = (byte)(var8 >> 24);
                        var10 = var6 + 1;
                        var4[var6] = (byte)(var8 >> 16);
                        var6 = var10 + 1;
                        var4[var10] = (byte)(var8 >> 8);
                        var4[var6] = (byte)(var8 & 255);
                        var8 = var7 + 3;
                        var7 = var6 + 1;
                        var3 = var2;
                        var15 = var14;
                        break;
                     case 7:
                        var8 = var5.position - var6;
                        var10 = var6 + 1;
                        var4[var6] = this.code[var7];
                        var11 = var10 + 1;
                        var4[var10] = (byte)(var8 >> 8);
                        var6 = var11 + 1;
                        var4[var11] = (byte)(var8 & 255);
                        var8 = var7 + 3;
                        var7 = var6;
                        var3 = var2;
                        var15 = var14;
                        break;
                     case 8:
                        var8 = var7 + 3;
                        var7 = var6;
                        var3 = var2;
                        var15 = var14;
                        break;
                     case 9:
                        if(var5 == null) {
                           if(var13 != var6) {
                              throw new Error("PC confusion new_pc:" + var6 + " new_size:" + var13);
                           }

                           this.PC = var13;
                           this.code = var4;
                           this.fixup_count = 0;
                           this.fixup_labels = null;
                           this.fixup_offsets = null;
                           return;
                        }

                        var9 = var5.first_fixup;
                        var7 = this.fixupOffset(var9);
                        var8 = var7;
                        if(var5.position != var6) {
                           throw new Error("bad pc");
                        }

                        var3 = var2;
                        continue;
                     case 11:
                        var5 = this.fixup_labels[var9 + 2];
                        var8 = this.fixupOffset(var9 + 1);
                        var3 = var2;
                        if(this.stackMap != null) {
                           var3 = this.mergeLabels(var2, var5);
                        }

                        this.addHandler(this.fixup_labels[var9].position, this.fixup_labels[var9 + 1].position, var6, var8);
                        var9 += 2;
                        var8 = var7;
                        var7 = var6;
                        var15 = var14;
                        break;
                     case 14:
                        if(this.lines == null) {
                           this.lines = new LineNumbersAttr(this);
                        }

                        ++var9;
                        var15 = this.fixupOffset(var9);
                        if(var15 != var14) {
                           this.lines.put(var15, var6);
                        }

                        var8 = var7;
                        var7 = var6;
                        var3 = var2;
                     }

                     ++var9;
                     var11 = this.fixupOffset(var9);
                     var10 = var8;
                     var6 = var7;
                     var8 = var11;
                     var7 = var10;
                     var14 = var15;
                  }
               }
            }

            var7 = var2.first_fixup;
            var8 = var6 + var8 - this.fixupOffset(var7);
            var10 = var11;
         }
      }
   }

   public Scope pushScope() {
      Scope var1 = new Scope();
      if(this.locals == null) {
         this.locals = new LocalVarsAttr(this.getMethod());
      }

      this.enterScope(var1);
      if(this.locals.parameter_scope == null) {
         this.locals.parameter_scope = var1;
      }

      return var1;
   }

   public final void pushType(Type var1) {
      if(var1.size == 0) {
         throw new Error("pushing void type onto stack");
      } else {
         Type[] var2;
         if(this.stack_types != null && this.stack_types.length != 0) {
            if(this.SP + 1 >= this.stack_types.length) {
               var2 = new Type[this.stack_types.length * 2];
               System.arraycopy(this.stack_types, 0, var2, 0, this.SP);
               this.stack_types = var2;
            }
         } else {
            this.stack_types = new Type[20];
         }

         int var3;
         if(var1.size == 8) {
            var2 = this.stack_types;
            var3 = this.SP;
            this.SP = var3 + 1;
            var2[var3] = Type.voidType;
         }

         var2 = this.stack_types;
         var3 = this.SP;
         this.SP = var3 + 1;
         var2[var3] = var1;
         if(this.SP > this.max_stack) {
            this.max_stack = this.SP;
         }

      }
   }

   public final void put1(int var1) {
      byte[] var2 = this.code;
      int var3 = this.PC;
      this.PC = var3 + 1;
      var2[var3] = (byte)var1;
      this.unreachable_here = false;
   }

   public final void put2(int var1) {
      byte[] var2 = this.code;
      int var3 = this.PC;
      this.PC = var3 + 1;
      var2[var3] = (byte)(var1 >> 8);
      var2 = this.code;
      var3 = this.PC;
      this.PC = var3 + 1;
      var2[var3] = (byte)var1;
      this.unreachable_here = false;
   }

   public final void put4(int var1) {
      byte[] var2 = this.code;
      int var3 = this.PC;
      this.PC = var3 + 1;
      var2[var3] = (byte)(var1 >> 24);
      var2 = this.code;
      var3 = this.PC;
      this.PC = var3 + 1;
      var2[var3] = (byte)(var1 >> 16);
      var2 = this.code;
      var3 = this.PC;
      this.PC = var3 + 1;
      var2[var3] = (byte)(var1 >> 8);
      var2 = this.code;
      var3 = this.PC;
      this.PC = var3 + 1;
      var2[var3] = (byte)var1;
      this.unreachable_here = false;
   }

   public final void putIndex2(CpoolEntry var1) {
      this.put2(var1.index);
   }

   public final void putLineNumber(int var1) {
      int var2 = var1;
      if(this.sourceDbgExt != null) {
         var2 = this.sourceDbgExt.fixLine(var1);
      }

      this.fixupAdd(14, (Label)null);
      this.fixupAdd(15, var2, (Label)null);
   }

   public final void putLineNumber(String var1, int var2) {
      if(var1 != null) {
         this.getMethod().classfile.setSourceFile(var1);
      }

      this.putLineNumber(var2);
   }

   public final boolean reachableHere() {
      return !this.unreachable_here;
   }

   public final void reserve(int var1) {
      if(this.code == null) {
         this.code = new byte[var1 + 100];
      } else if(this.PC + var1 > this.code.length) {
         byte[] var2 = new byte[this.code.length * 2 + var1];
         System.arraycopy(this.code, 0, var2, 0, this.PC);
         this.code = var2;
         return;
      }

   }

   public final void setAttributes(Attribute var1) {
      this.attributes = var1;
   }

   public void setCode(byte[] var1) {
      this.code = var1;
      this.PC = var1.length;
   }

   public void setCodeLength(int var1) {
      this.PC = var1;
   }

   public void setMaxLocals(int var1) {
      this.max_locals = var1;
   }

   public void setMaxStack(int var1) {
      this.max_stack = var1;
   }

   public final void setReachable(boolean var1) {
      if(!var1) {
         var1 = true;
      } else {
         var1 = false;
      }

      this.unreachable_here = var1;
   }

   public final void setTypes(Label var1) {
      this.setTypes(var1.localTypes, var1.stackTypes);
   }

   public final void setTypes(Type[] var1, Type[] var2) {
      int var4 = var2.length;
      int var3 = var1.length;
      if(this.local_types != null) {
         if(var3 > 0) {
            System.arraycopy(var1, 0, this.local_types, 0, var3);
         }

         while(var3 < this.local_types.length) {
            this.local_types[var3] = null;
            ++var3;
         }
      }

      if(this.stack_types != null && var4 <= this.stack_types.length) {
         for(var3 = var4; var3 < this.stack_types.length; ++var3) {
            this.stack_types[var3] = null;
         }
      } else {
         this.stack_types = new Type[var4];
      }

      System.arraycopy(var2, 0, this.stack_types, 0, var4);
      this.SP = var4;
   }

   public final void setUnreachable() {
      this.unreachable_here = true;
   }

   public ExitableBlock startExitableBlock(Type var1, boolean var2) {
      ExitableBlock var3 = new ExitableBlock(var1, this, var2);
      var3.outer = this.currentExitableBlock;
      this.currentExitableBlock = var3;
      return var3;
   }

   public SwitchState startSwitch() {
      SwitchState var1 = new SwitchState(this);
      var1.switchValuePushed(this);
      return var1;
   }

   public final Type topType() {
      return this.stack_types[this.SP - 1];
   }

   boolean useJsr() {
      return (this.flags & 2) == 0;
   }

   public void write(DataOutputStream var1) throws IOException {
      var1.writeShort(this.max_stack);
      var1.writeShort(this.max_locals);
      var1.writeInt(this.PC);
      var1.write(this.code, 0, this.PC);
      var1.writeShort(this.exception_table_length);
      int var3 = this.exception_table_length;
      int var2 = 0;

      while(true) {
         --var3;
         if(var3 < 0) {
            Attribute.writeAll(this, var1);
            return;
         }

         var1.writeShort(this.exception_table[var2]);
         var1.writeShort(this.exception_table[var2 + 1]);
         var1.writeShort(this.exception_table[var2 + 2]);
         var1.writeShort(this.exception_table[var2 + 3]);
         var2 += 4;
      }
   }
}
