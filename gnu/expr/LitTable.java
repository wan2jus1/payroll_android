package gnu.expr;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.expr.Literal;
import gnu.expr.StackTarget;
import gnu.lists.FString;
import gnu.mapping.Symbol;
import gnu.mapping.Table2D;
import gnu.mapping.Values;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectOutput;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.IdentityHashMap;
import java.util.regex.Pattern;

public class LitTable implements ObjectOutput {

   static Table2D staticTable = new Table2D(100);
   Compilation comp;
   IdentityHashMap literalTable = new IdentityHashMap(100);
   Literal literalsChain;
   int literalsCount;
   ClassType mainClass;
   int stackPointer;
   Type[] typeStack = new Type[20];
   Object[] valueStack = new Object[20];


   public LitTable(Compilation var1) {
      this.comp = var1;
      this.mainClass = var1.mainClass;
   }

   private void store(Literal var1, boolean var2, CodeAttr var3) {
      if(var1.field != null) {
         if(!var2) {
            var3.emitDup(var1.type);
         }

         var3.emitPutStatic(var1.field);
      }

      var1.flags |= 8;
   }

   public void close() {
   }

   public void emit() throws IOException {
      Literal var1;
      for(var1 = this.literalsChain; var1 != null; var1 = var1.next) {
         this.writeObject(var1.value);
      }

      for(var1 = this.literalsChain; var1 != null; var1 = var1.next) {
         this.emit(var1, true);
      }

      this.literalTable = null;
      this.literalsCount = 0;
   }

   void emit(Literal var1, boolean var2) {
      CodeAttr var5 = this.comp.getCode();
      if(var1.value == null) {
         if(!var2) {
            var5.emitPushNull();
         }
      } else if(var1.value instanceof String) {
         if(!var2) {
            var5.emitPushString(var1.value.toString());
            return;
         }
      } else if((var1.flags & 8) != 0) {
         if(!var2) {
            var5.emitGetStatic(var1.field);
            return;
         }
      } else {
         Literal var4;
         if(var1.value instanceof Object[]) {
            int var8 = var1.argValues.length;
            Type var3 = ((ArrayType)var1.type).getComponentType();
            var5.emitPushInt(var8);
            var5.emitNewArray(var3);
            this.store(var1, var2, var5);

            for(int var7 = 0; var7 < var8; ++var7) {
               var4 = (Literal)var1.argValues[var7];
               if(var4.value != null) {
                  var5.emitDup(var3);
                  var5.emitPushInt(var7);
                  this.emit(var4, false);
                  var5.emitArrayStore(var3);
               }
            }
         } else {
            if(var1.type instanceof ArrayType) {
               var5.emitPushPrimArray(var1.value, (ArrayType)var1.type);
               this.store(var1, var2, var5);
               return;
            }

            String var15;
            if(var1.value instanceof Class) {
               Class var17 = (Class)var1.value;
               if(var17.isPrimitive()) {
                  String var16 = var17.getName();
                  var15 = var16;
                  if(var16.equals("int")) {
                     var15 = "integer";
                  }

                  var5.emitGetStatic(ClassType.make("java.lang." + Character.toUpperCase(var15.charAt(0)) + var15.substring(1)).getDeclaredField("TYPE"));
               } else {
                  this.comp.loadClassRef((ObjectType)Type.make(var17));
               }

               this.store(var1, var2, var5);
               return;
            }

            Method var13;
            Method var14;
            if(var1.value instanceof ClassType && !((ClassType)var1.value).isExisting()) {
               this.comp.loadClassRef((ClassType)var1.value);
               var14 = Compilation.typeType.getDeclaredMethod("valueOf", 1);
               var13 = var14;
               if(var14 == null) {
                  var13 = Compilation.typeType.getDeclaredMethod("make", 1);
               }

               var5.emitInvokeStatic(var13);
               var5.emitCheckcast(Compilation.typeClassType);
               this.store(var1, var2, var5);
               return;
            }

            ClassType var6 = (ClassType)var1.type;
            boolean var18;
            if((var1.flags & 4) != 0) {
               var18 = true;
            } else {
               var18 = false;
            }

            var13 = null;
            var4 = null;
            boolean var9 = false;
            boolean var11 = false;
            boolean var10 = var18;
            if(!var18) {
               var13 = var4;
               if(!(var1.value instanceof Symbol)) {
                  var13 = this.getMethod(var6, "valueOf", var1, true);
               }

               var14 = var13;
               if(var13 == null) {
                  var14 = var13;
                  if(!(var1.value instanceof Values)) {
                     var15 = "make";
                     if(var1.value instanceof Pattern) {
                        var15 = "compile";
                     }

                     var14 = this.getMethod(var6, var15, var1, true);
                  }
               }

               boolean var19;
               if(var14 != null) {
                  var19 = true;
               } else {
                  var19 = var11;
                  if(var1.argTypes.length > 0) {
                     var14 = this.getMethod(var6, "<init>", var1, false);
                     var19 = var11;
                  }
               }

               var9 = var19;
               var13 = var14;
               var10 = var18;
               if(var14 == null) {
                  var10 = true;
                  var13 = var14;
                  var9 = var19;
               }
            }

            if(var10) {
               var13 = this.getMethod(var6, "set", var1, false);
            }

            if(var13 == null && var1.argTypes.length > 0) {
               this.error("no method to construct " + var1.type);
            }

            if(var9) {
               this.putArgs(var1, var5);
               var5.emitInvokeStatic(var13);
            } else if(var10) {
               var5.emitNew(var6);
               var5.emitDup(var6);
               var5.emitInvokeSpecial(var6.getDeclaredMethod("<init>", 0));
            } else {
               var5.emitNew(var6);
               var5.emitDup(var6);
               this.putArgs(var1, var5);
               var5.emitInvokeSpecial(var13);
            }

            if(!var9 && !(var1.value instanceof Values)) {
               var14 = var6.getDeclaredMethod("readResolve", 0);
            } else {
               var14 = null;
            }

            if(var14 != null) {
               var5.emitInvokeVirtual(var14);
               var6.emitCoerceFromObject(var5);
            }

            boolean var12;
            if(var2 && (!var10 || var13 == null)) {
               var12 = true;
            } else {
               var12 = false;
            }

            this.store(var1, var12, var5);
            if(var10 && var13 != null) {
               if(!var2) {
                  var5.emitDup(var6);
               }

               this.putArgs(var1, var5);
               var5.emitInvokeVirtual(var13);
               return;
            }
         }
      }

   }

   void error(String var1) {
      throw new Error(var1);
   }

   public Literal findLiteral(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public void flush() {
   }

   Method getMethod(ClassType var1, String var2, Literal var3, boolean var4) {
      Type[] var11 = var3.argTypes;
      Method var6 = var1.getDeclaredMethods();
      int var19 = var11.length;
      Method var5 = null;
      long var23 = 0L;
      boolean var16 = false;

      Type[] var7;
      int var14;
      int var15;
      int var17;
      long var25;
      Type var30;
      for(var7 = null; var6 != null; var23 = var25) {
         Type[] var8;
         Method var9;
         boolean var18;
         if(!var2.equals(var6.getName())) {
            var25 = var23;
            var9 = var5;
            var8 = var7;
            var18 = var16;
         } else {
            var18 = var16;
            var8 = var7;
            var9 = var5;
            var25 = var23;
            if(var4 == var6.getStaticFlag()) {
               long var21 = 0L;
               Type[] var10 = var6.getParameterTypes();
               var14 = 0;
               var17 = 0;

               label165:
               while(true) {
                  int var39;
                  if(var14 == var19 && var17 == var10.length) {
                     if(var5 != null && (var23 == 0L || var21 != 0L)) {
                        var18 = var16;
                        var8 = var7;
                        var9 = var5;
                        var25 = var23;
                        if(var21 == 0L) {
                           boolean var34 = false;
                           boolean var36 = false;
                           var17 = var19;

                           boolean var37;
                           while(true) {
                              var39 = var17 - 1;
                              var37 = var34;
                              var16 = var36;
                              if(var39 < 0) {
                                 break;
                              }

                              int var20 = var7[var39].compare(var10[var39]);
                              var16 = var36;
                              if(var20 != 1) {
                                 var16 = true;
                                 var36 = true;
                                 if(var34) {
                                    var16 = var36;
                                    var37 = var34;
                                    break;
                                 }
                              }

                              var17 = var39;
                              var36 = var16;
                              if(var20 != -1) {
                                 boolean var40 = true;
                                 var34 = true;
                                 var17 = var39;
                                 var36 = var16;
                                 if(var16) {
                                    var37 = var40;
                                    break;
                                 }
                              }
                           }

                           if(var37) {
                              var5 = var6;
                              var7 = var10;
                           }

                           if(var37 && var16) {
                              var34 = true;
                           } else {
                              var34 = false;
                           }

                           var18 = var34;
                           var8 = var7;
                           var9 = var5;
                           var25 = var23;
                        }
                     } else {
                        var9 = var6;
                        var8 = var10;
                        var18 = var16;
                        var25 = var21;
                     }
                     break;
                  }

                  var18 = var16;
                  var8 = var7;
                  var9 = var5;
                  var25 = var23;
                  if(var14 == var19) {
                     break;
                  }

                  var18 = var16;
                  var8 = var7;
                  var9 = var5;
                  var25 = var23;
                  if(var17 == var10.length) {
                     break;
                  }

                  Type var12 = var11[var14];
                  Type var13 = var10[var17];
                  if(!var12.isSubtype(var13)) {
                     var18 = var16;
                     var8 = var7;
                     var9 = var5;
                     var25 = var23;
                     if(!(var13 instanceof ArrayType)) {
                        break;
                     }

                     var18 = var16;
                     var8 = var7;
                     var9 = var5;
                     var25 = var23;
                     if(var17 >= 64) {
                        break;
                     }

                     if(var12 != Type.intType) {
                        var18 = var16;
                        var8 = var7;
                        var9 = var5;
                        var25 = var23;
                        if(var12 != Type.shortType) {
                           break;
                        }
                     }

                     var39 = ((Number)var3.argValues[var14]).intValue();
                     var15 = var39;
                     if(var39 < 0) {
                        var15 = var39;
                        if(var1.getName().equals("gnu.math.IntNum")) {
                           var15 = var39 + Integer.MIN_VALUE;
                        }
                     }

                     var12 = ((ArrayType)var13).getComponentType();
                     var18 = var16;
                     var8 = var7;
                     var9 = var5;
                     var25 = var23;
                     if(var15 < 0) {
                        break;
                     }

                     var18 = var16;
                     var8 = var7;
                     var9 = var5;
                     var25 = var23;
                     if(var14 + var15 >= var19) {
                        break;
                     }

                     var39 = var15;

                     while(true) {
                        --var39;
                        if(var39 < 0) {
                           var14 += var15;
                           var21 |= (long)(1 << var17);
                           break;
                        }

                        var30 = var11[var14 + var39 + 1];
                        if(var12 instanceof PrimType) {
                           if(var12.getSignature() != var30.getSignature()) {
                              var18 = var16;
                              var8 = var7;
                              var9 = var5;
                              var25 = var23;
                              break label165;
                           }
                        } else if(!var30.isSubtype(var12)) {
                           var18 = var16;
                           var8 = var7;
                           var9 = var5;
                           var25 = var23;
                           break label165;
                        }
                     }
                  }

                  ++var14;
                  ++var17;
               }
            }
         }

         var6 = var6.getNext();
         var16 = var18;
         var7 = var8;
         var5 = var9;
      }

      Method var27;
      if(var16) {
         var27 = null;
      } else {
         var27 = var5;
         if(var23 != 0L) {
            Object[] var28 = new Object[var7.length];
            Type[] var29 = new Type[var7.length];
            var15 = 0;

            for(int var38 = 0; var15 != var19; ++var38) {
               Object var33 = var7[var38];
               if(((long)(1 << var38) & var23) == 0L) {
                  var28[var38] = var3.argValues[var15];
                  var29[var38] = var3.argTypes[var15];
               } else {
                  var17 = ((Number)var3.argValues[var15]).intValue();
                  var4 = var1.getName().equals("gnu.math.IntNum");
                  var14 = var17;
                  if(var4) {
                     var14 = var17 + Integer.MIN_VALUE;
                  }

                  var30 = ((ArrayType)var33).getComponentType();
                  var29[var38] = (Type)var33;
                  var28[var38] = Array.newInstance(var30.getReflectClass(), var14);
                  Object[] var32 = var3.argValues;
                  if(var4) {
                     int[] var31 = (int[])((int[])var28[var38]);

                     for(var17 = var14; var17 > 0; --var17) {
                        var31[var14 - var17] = ((Integer)var32[var15 + var17]).intValue();
                     }
                  } else {
                     var17 = var14;

                     while(true) {
                        --var17;
                        if(var17 < 0) {
                           break;
                        }

                        Array.set(var28[var38], var17, var32[var15 + 1 + var17]);
                     }
                  }

                  Literal var35 = new Literal(var28[var38], (Type)var33);
                  if(var30 instanceof ObjectType) {
                     var35.argValues = (Object[])((Object[])var28[var38]);
                  }

                  var28[var38] = var35;
                  var15 += var14;
               }

               ++var15;
            }

            var3.argValues = var28;
            var3.argTypes = var29;
            return var5;
         }
      }

      return var27;
   }

   void push(Object var1, Type var2) {
      if(this.stackPointer >= this.valueStack.length) {
         Object[] var3 = new Object[this.valueStack.length * 2];
         Type[] var4 = new Type[this.typeStack.length * 2];
         System.arraycopy(this.valueStack, 0, var3, 0, this.stackPointer);
         System.arraycopy(this.typeStack, 0, var4, 0, this.stackPointer);
         this.valueStack = var3;
         this.typeStack = var4;
      }

      this.valueStack[this.stackPointer] = var1;
      this.typeStack[this.stackPointer] = var2;
      ++this.stackPointer;
   }

   void putArgs(Literal var1, CodeAttr var2) {
      Type[] var6 = var1.argTypes;
      int var5 = var6.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         Object var3 = var1.argValues[var4];
         if(var3 instanceof Literal) {
            this.emit((Literal)var3, false);
         } else {
            this.comp.compileConstant(var3, new StackTarget(var6[var4]));
         }
      }

   }

   public void write(int var1) throws IOException {
      this.error("cannot handle call to write(int) when externalizing literal");
   }

   public void write(byte[] var1) throws IOException {
      this.error("cannot handle call to write(byte[]) when externalizing literal");
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      this.error("cannot handle call to write(byte[],int,int) when externalizing literal");
   }

   public void writeBoolean(boolean var1) {
      this.push(new Boolean(var1), Type.booleanType);
   }

   public void writeByte(int var1) {
      this.push(new Byte((byte)var1), Type.byteType);
   }

   public void writeBytes(String var1) throws IOException {
      this.error("cannot handle call to writeBytes(String) when externalizing literal");
   }

   public void writeChar(int var1) {
      this.push(new Character((char)var1), Type.charType);
   }

   public void writeChars(String var1) {
      this.push(var1, Type.string_type);
   }

   public void writeDouble(double var1) {
      this.push(new Double(var1), Type.doubleType);
   }

   public void writeFloat(float var1) {
      this.push(new Float(var1), Type.floatType);
   }

   public void writeInt(int var1) {
      this.push(new Integer(var1), Type.intType);
   }

   public void writeLong(long var1) {
      this.push(new Long(var1), Type.longType);
   }

   public void writeObject(Object var1) throws IOException {
      Literal var2 = this.findLiteral(var1);
      if((var2.flags & 3) != 0) {
         if(var2.field == null && var1 != null && !(var1 instanceof String)) {
            var2.assign(this);
         }

         if((var2.flags & 2) == 0) {
            var2.flags |= 4;
         }
      } else {
         var2.flags |= 1;
         int var4 = this.stackPointer;
         int var3;
         if(var1 instanceof FString && ((FString)var1).size() < '\uffff') {
            this.push(var1.toString(), Type.string_type);
         } else if(var1 instanceof Externalizable) {
            ((Externalizable)var1).writeExternal(this);
         } else if(var1 instanceof Object[]) {
            Object[] var5 = (Object[])((Object[])var1);

            for(var3 = 0; var3 < var5.length; ++var3) {
               this.writeObject(var5[var3]);
            }
         } else if(var1 != null && !(var1 instanceof String) && !(var2.type instanceof ArrayType)) {
            if(var1 instanceof BigInteger) {
               this.writeChars(var1.toString());
            } else if(var1 instanceof BigDecimal) {
               BigDecimal var6 = (BigDecimal)var1;
               this.writeObject(var6.unscaledValue());
               this.writeInt(var6.scale());
            } else if(var1 instanceof Integer) {
               this.push(var1, Type.intType);
            } else if(var1 instanceof Short) {
               this.push(var1, Type.shortType);
            } else if(var1 instanceof Byte) {
               this.push(var1, Type.byteType);
            } else if(var1 instanceof Long) {
               this.push(var1, Type.longType);
            } else if(var1 instanceof Double) {
               this.push(var1, Type.doubleType);
            } else if(var1 instanceof Float) {
               this.push(var1, Type.floatType);
            } else if(var1 instanceof Character) {
               this.push(var1, Type.charType);
            } else if(var1 instanceof Class) {
               this.push(var1, Type.java_lang_Class_type);
            } else if(var1 instanceof Pattern) {
               Pattern var7 = (Pattern)var1;
               this.push(var7.pattern(), Type.string_type);
               this.push(Integer.valueOf(var7.flags()), Type.intType);
            } else {
               this.error(var1.getClass().getName() + " does not implement Externalizable");
            }
         }

         var3 = this.stackPointer - var4;
         if(var3 == 0) {
            var2.argValues = Values.noArgs;
            var2.argTypes = Type.typeArray0;
         } else {
            var2.argValues = new Object[var3];
            var2.argTypes = new Type[var3];
            System.arraycopy(this.valueStack, var4, var2.argValues, 0, var3);
            System.arraycopy(this.typeStack, var4, var2.argTypes, 0, var3);
            this.stackPointer = var4;
         }

         var2.flags |= 2;
      }

      this.push(var2, var2.type);
   }

   public void writeShort(int var1) {
      this.push(new Short((short)var1), Type.shortType);
   }

   public void writeUTF(String var1) {
      this.push(var1, Type.string_type);
   }
}
