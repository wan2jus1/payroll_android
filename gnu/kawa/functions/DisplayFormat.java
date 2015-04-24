package gnu.kawa.functions;

import gnu.expr.Keyword;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.UntypedAtomic;
import gnu.kawa.xml.XmlNamespace;
import gnu.lists.AbstractFormat;
import gnu.lists.Array;
import gnu.lists.CharSeq;
import gnu.lists.Consumable;
import gnu.lists.Consumer;
import gnu.lists.ConsumerWriter;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.SimpleVector;
import gnu.lists.Strings;
import gnu.mapping.Namespace;
import gnu.mapping.OutPort;
import gnu.mapping.Symbol;
import gnu.mapping.ThreadLocation;
import gnu.mapping.Values;
import gnu.math.IntNum;
import gnu.math.RatNum;
import gnu.text.Char;
import gnu.text.Printable;
import gnu.xml.XMLPrinter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URI;
import java.util.regex.Pattern;

public class DisplayFormat extends AbstractFormat {

   public static final ThreadLocation outBase = new ThreadLocation("out-base");
   public static final ThreadLocation outRadix;
   static Pattern r5rsIdentifierMinusInteriorColons;
   char language;
   boolean readable;


   static {
      outBase.setGlobal(IntNum.ten());
      outRadix = new ThreadLocation("out-radix");
      r5rsIdentifierMinusInteriorColons = Pattern.compile("(([a-zA-Z]|[!$%&*/:<=>?^_~])([a-zA-Z]|[!$%&*/<=>?^_~]|[0-9]|([-+.@]))*[:]?)|([-+]|[.][.][.])");
   }

   public DisplayFormat(boolean var1, char var2) {
      this.readable = var1;
      this.language = var2;
   }

   public static DisplayFormat getCommonLispFormat(boolean var0) {
      return new DisplayFormat(var0, 'C');
   }

   public static DisplayFormat getEmacsLispFormat(boolean var0) {
      return new DisplayFormat(var0, 'E');
   }

   public static DisplayFormat getSchemeFormat(boolean var0) {
      return new DisplayFormat(var0, 'S');
   }

   public boolean getReadableOutput() {
      return this.readable;
   }

   int write(Array var1, int var2, int var3, Consumer var4) {
      int var9 = var1.rank();
      int var7 = 0;
      byte var8 = 0;
      String var5;
      if(var3 > 0) {
         var5 = "(";
      } else if(var9 == 1) {
         var5 = "#(";
      } else {
         var5 = "#" + var9 + "a(";
      }

      if(var4 instanceof OutPort) {
         ((OutPort)var4).startLogicalBlock(var5, false, ")");
      } else {
         this.write(var5, var4);
      }

      if(var9 > 0) {
         int var10 = var1.getSize(var3);
         int var11 = var3 + 1;
         var3 = 0;
         int var6 = var2;
         var2 = var8;

         while(true) {
            var7 = var2;
            if(var3 >= var10) {
               break;
            }

            if(var3 > 0) {
               this.write(" ", var4);
               if(var4 instanceof OutPort) {
                  ((OutPort)var4).writeBreakFill();
               }
            }

            if(var11 == var9) {
               this.writeObject(var1.getRowMajor(var6), var4);
               var7 = 1;
            } else {
               var7 = this.write(var1, var6, var11, var4);
            }

            var6 += var7;
            var2 += var7;
            ++var3;
         }
      }

      if(var4 instanceof OutPort) {
         ((OutPort)var4).endLogicalBlock(")");
         return var7;
      } else {
         this.write(")", var4);
         return var7;
      }
   }

   public void write(int var1, Consumer var2) {
      if(!this.getReadableOutput()) {
         Char.print(var1, var2);
      } else if(this.language == 69 && var1 > 32) {
         var2.write(63);
         Char.print(var1, var2);
      } else {
         this.write(Char.toScmReadableString(var1), var2);
      }
   }

   public void writeBoolean(boolean var1, Consumer var2) {
      String var3;
      if(this.language == 83) {
         if(var1) {
            var3 = "#t";
         } else {
            var3 = "#f";
         }
      } else if(var1) {
         var3 = "t";
      } else {
         var3 = "nil";
      }

      this.write(var3, var2);
   }

   public void writeList(LList var1, OutPort var2) {
      Object var3 = var1;
      var2.startLogicalBlock("(", false, ")");

      while(var3 instanceof Pair) {
         if(var3 != var1) {
            var2.writeSpaceFill();
         }

         Pair var4 = (Pair)var3;
         this.writeObject(var4.getCar(), var2);
         var3 = var4.getCdr();
      }

      if(var3 != LList.Empty) {
         var2.writeSpaceFill();
         var2.write(". ");
         this.writeObject(LList.checkNonList(var3), var2);
      }

      var2.endLogicalBlock(")");
   }

   public void writeObject(Object var1, Consumer var2) {
      boolean var4 = false;
      boolean var3 = var4;
      if(var2 instanceof OutPort) {
         var3 = var4;
         if(!(var1 instanceof UntypedAtomic)) {
            var3 = var4;
            if(!(var1 instanceof Values)) {
               label24: {
                  if(!this.getReadableOutput()) {
                     var3 = var4;
                     if(var1 instanceof Char) {
                        break label24;
                     }

                     var3 = var4;
                     if(var1 instanceof CharSequence) {
                        break label24;
                     }

                     var3 = var4;
                     if(var1 instanceof Character) {
                        break label24;
                     }
                  }

                  ((OutPort)var2).writeWordStart();
                  var3 = true;
               }
            }
         }
      }

      this.writeObjectRaw(var1, var2);
      if(var3) {
         ((OutPort)var2).writeWordEnd();
      }

   }

   public void writeObjectRaw(Object var1, Consumer var2) {
      if(var1 instanceof Boolean) {
         this.writeBoolean(((Boolean)var1).booleanValue(), var2);
      } else {
         if(var1 instanceof Char) {
            this.write(((Char)var1).intValue(), var2);
            return;
         }

         if(var1 instanceof Character) {
            this.write(((Character)var1).charValue(), var2);
            return;
         }

         if(var1 instanceof Symbol) {
            Symbol var15 = (Symbol)var1;
            if(var15.getNamespace() == XmlNamespace.HTML) {
               this.write("html:", var2);
               this.write(var15.getLocalPart(), var2);
               return;
            }

            this.writeSymbol((Symbol)var15, var2, this.readable);
            return;
         }

         if(var1 instanceof URI && this.getReadableOutput() && var2 instanceof PrintWriter) {
            this.write("#,(URI ", var2);
            Strings.printQuoted(var1.toString(), (PrintWriter)var2, 1);
            var2.write(41);
            return;
         }

         int var5;
         int var6;
         if(var1 instanceof CharSequence) {
            CharSequence var3 = (CharSequence)var1;
            if(this.getReadableOutput() && var2 instanceof PrintWriter) {
               Strings.printQuoted(var3, (PrintWriter)var2, 1);
               return;
            }

            if(var1 instanceof String) {
               var2.write((String)var1);
               return;
            }

            if(var1 instanceof CharSeq) {
               CharSeq var8 = (CharSeq)var1;
               var8.consume(0, var8.size(), var2);
               return;
            }

            var6 = var3.length();

            for(var5 = 0; var5 < var6; ++var5) {
               var2.write(var3.charAt(var5));
            }
         } else {
            if(var1 instanceof LList && var2 instanceof OutPort) {
               this.writeList((LList)var1, (OutPort)var2);
               return;
            }

            String var9;
            if(var1 instanceof SimpleVector) {
               SimpleVector var16 = (SimpleVector)var1;
               var9 = var16.getTag();
               String var13;
               if(this.language == 69) {
                  var9 = "[";
                  var13 = "]";
               } else {
                  if(var9 == null) {
                     var9 = "#(";
                  } else {
                     var9 = "#" + var9 + "(";
                  }

                  var13 = ")";
               }

               if(var2 instanceof OutPort) {
                  ((OutPort)var2).startLogicalBlock(var9, false, var13);
               } else {
                  this.write(var9, var2);
               }

               var6 = var16.size();

               for(var5 = 0; var5 < var6 << 1; var5 += 2) {
                  if(var5 > 0 && var2 instanceof OutPort) {
                     ((OutPort)var2).writeSpaceFill();
                  }

                  if(!var16.consumeNext(var5, var2)) {
                     break;
                  }
               }

               if(var2 instanceof OutPort) {
                  ((OutPort)var2).endLogicalBlock(var13);
                  return;
               }

               this.write(var13, var2);
               return;
            }

            if(var1 instanceof Array) {
               this.write((Array)var1, 0, 0, var2);
               return;
            }

            if(var1 instanceof KNode) {
               if(this.getReadableOutput()) {
                  this.write("#", var2);
               }

               Object var10;
               if(var2 instanceof Writer) {
                  var10 = (Writer)var2;
               } else {
                  var10 = new ConsumerWriter(var2);
               }

               XMLPrinter var11 = new XMLPrinter((Writer)var10);
               var11.writeObject(var1);
               var11.closeThis();
               return;
            }

            if(var1 == Values.empty && this.getReadableOutput()) {
               this.write("#!void", var2);
               return;
            }

            if(var1 instanceof Consumable) {
               ((Consumable)var1).consume(var2);
               return;
            }

            if(var1 instanceof Printable) {
               ((Printable)var1).print(var2);
               return;
            }

            if(!(var1 instanceof RatNum)) {
               if(var1 instanceof Enum && this.getReadableOutput()) {
                  this.write(var1.getClass().getName(), var2);
                  this.write(":", var2);
                  this.write(((Enum)var1).name(), var2);
                  return;
               }

               if(var1 == null) {
                  var9 = null;
               } else {
                  if(var1.getClass().isArray()) {
                     var6 = java.lang.reflect.Array.getLength(var1);
                     if(var2 instanceof OutPort) {
                        ((OutPort)var2).startLogicalBlock("[", false, "]");
                     } else {
                        this.write("[", var2);
                     }

                     for(var5 = 0; var5 < var6; ++var5) {
                        if(var5 > 0) {
                           this.write(" ", var2);
                           if(var2 instanceof OutPort) {
                              ((OutPort)var2).writeBreakFill();
                           }
                        }

                        this.writeObject(java.lang.reflect.Array.get(var1, var5), var2);
                     }

                     if(var2 instanceof OutPort) {
                        ((OutPort)var2).endLogicalBlock("]");
                        return;
                     }

                     this.write("]", var2);
                     return;
                  }

                  var9 = var1.toString();
               }

               if(var9 == null) {
                  this.write("#!null", var2);
                  return;
               }

               this.write(var9, var2);
               return;
            }

            var5 = 10;
            boolean var7 = false;
            Object var12 = outBase.get((Object)null);
            Object var4 = outRadix.get((Object)null);
            boolean var17 = var7;
            if(var4 != null) {
               label196: {
                  if(var4 != Boolean.TRUE) {
                     var17 = var7;
                     if(!"yes".equals(var4.toString())) {
                        break label196;
                     }
                  }

                  var17 = true;
               }
            }

            if(var12 instanceof Number) {
               var5 = ((IntNum)var12).intValue();
            } else if(var12 != null) {
               var5 = Integer.parseInt(var12.toString());
            }

            String var14 = ((RatNum)var1).toString(var5);
            if(var17) {
               if(var5 == 16) {
                  this.write("#x", var2);
               } else if(var5 == 8) {
                  this.write("#o", var2);
               } else if(var5 == 2) {
                  this.write("#b", var2);
               } else if(var5 != 10 || !(var1 instanceof IntNum)) {
                  this.write("#" + var12 + "r", var2);
               }
            }

            this.write(var14, var2);
            if(var17 && var5 == 10 && var1 instanceof IntNum) {
               this.write(".", var2);
               return;
            }
         }
      }

   }

   void writeSymbol(Symbol var1, Consumer var2, boolean var3) {
      boolean var8 = true;
      String var5 = var1.getPrefix();
      Namespace var6 = var1.getNamespace();
      String var4;
      if(var6 == null) {
         var4 = null;
      } else {
         var4 = var6.getName();
      }

      boolean var7;
      if(var4 != null && var4.length() > 0) {
         var7 = true;
      } else {
         var7 = false;
      }

      if(var5 == null || var5.length() <= 0) {
         var8 = false;
      }

      boolean var10 = false;
      boolean var9;
      if(var6 == Keyword.keywordNamespace) {
         if(this.language != 67 && this.language != 69) {
            var9 = true;
         } else {
            var2.write(58);
            var9 = var10;
         }
      } else {
         label64: {
            if(!var8) {
               var9 = var10;
               if(!var7) {
                  break label64;
               }
            }

            if(var8) {
               this.writeSymbol((String)var5, var2, var3);
            }

            if(var7 && (var3 || !var8)) {
               var2.write(123);
               var2.write(var4);
               var2.write(125);
            }

            var2.write(58);
            var9 = var10;
         }
      }

      this.writeSymbol((String)var1.getName(), var2, var3);
      if(var9) {
         var2.write(58);
      }

   }

   void writeSymbol(String var1, Consumer var2, boolean var3) {
      if(var3 && !r5rsIdentifierMinusInteriorColons.matcher(var1).matches()) {
         int var8 = var1.length();
         if(var8 == 0) {
            this.write("||", var2);
         } else {
            boolean var7 = false;

            boolean var5;
            for(int var6 = 0; var6 < var8; var7 = var5) {
               char var9 = var1.charAt(var6);
               if(var9 == 124) {
                  String var4;
                  if(var7) {
                     var4 = "|\\";
                  } else {
                     var4 = "\\";
                  }

                  this.write(var4, var2);
                  var5 = false;
               } else {
                  var5 = var7;
                  if(!var7) {
                     var2.write(124);
                     var5 = true;
                  }
               }

               var2.write(var9);
               ++var6;
            }

            if(var7) {
               var2.write(124);
               return;
            }
         }

      } else {
         this.write(var1, var2);
      }
   }
}
