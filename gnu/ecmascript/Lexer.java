package gnu.ecmascript;

import gnu.ecmascript.Reserved;
import gnu.expr.QuoteExp;
import gnu.lists.Sequence;
import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.math.IntNum;
import gnu.text.Char;
import gnu.text.SyntaxException;
import java.io.IOException;
import java.util.Hashtable;

public class Lexer extends gnu.text.Lexer {

   public static final Char colonToken = Char.make(58);
   public static final Char commaToken = Char.make(44);
   public static final Char condToken = Char.make(63);
   public static final Char dotToken = Char.make(46);
   public static final Reserved elseToken = new Reserved("else", 38);
   public static final Object eofToken = Sequence.eofValue;
   public static final Object eolToken = Char.make(10);
   public static final Char equalToken = Char.make(61);
   public static final Char lbraceToken = Char.make(123);
   public static final Char lbracketToken = Char.make(91);
   public static final Char lparenToken = Char.make(40);
   public static final Reserved newToken = new Reserved("new", 39);
   public static final Char notToken = Char.make(33);
   public static final Char rbraceToken = Char.make(125);
   public static final Char rbracketToken = Char.make(93);
   static Hashtable reserved;
   public static final Char rparenToken = Char.make(41);
   public static final Char semicolonToken = Char.make(59);
   public static final Char tildeToken = Char.make(126);
   private boolean prevWasCR = false;


   public Lexer(InPort var1) {
      super(var1);
   }

   public static Object checkReserved(String var0) {
      if(reserved == null) {
         initReserved();
      }

      return reserved.get(var0);
   }

   public static Object getToken(InPort var0) throws IOException, SyntaxException {
      return (new Lexer(var0)).getToken();
   }

   static void initReserved() {
      synchronized(Lexer.class){}

      try {
         if(reserved == null) {
            reserved = new Hashtable(20);
            reserved.put("null", new QuoteExp((Object)null));
            reserved.put("true", new QuoteExp(Boolean.TRUE));
            reserved.put("false", new QuoteExp(Boolean.FALSE));
            reserved.put("var", new Reserved("var", 30));
            reserved.put("if", new Reserved("if", 31));
            reserved.put("while", new Reserved("while", 32));
            reserved.put("for", new Reserved("for", 33));
            reserved.put("continue", new Reserved("continue", 34));
            reserved.put("break", new Reserved("break", 35));
            reserved.put("return", new Reserved("return", 36));
            reserved.put("with", new Reserved("with", 37));
            reserved.put("function", new Reserved("function", 41));
            reserved.put("this", new Reserved("this", 40));
            reserved.put("else", elseToken);
            reserved.put("new", newToken);
         }
      } finally {
         ;
      }

   }

   public static void main(String[] var0) {
      Lexer var4 = new Lexer(InPort.inDefault());

      Object var1;
      Object var5;
      do {
         try {
            var1 = var4.getToken();
            OutPort var2 = OutPort.outDefault();
            var2.print((String)"token:");
            var2.print((Object)var1);
            var2.println(" [class:" + var1.getClass() + "]");
            var5 = Sequence.eofValue;
         } catch (Exception var3) {
            System.err.println("caught exception:" + var3);
            return;
         }
      } while(var1 != var5);

   }

   public String getIdentifier(int var1) throws IOException {
      var1 = this.port.pos;
      int var4 = var1 - 1;
      int var5 = this.port.limit;

      char[] var2;
      for(var2 = this.port.buffer; var1 < var5 && Character.isJavaIdentifierPart(var2[var1]); ++var1) {
         ;
      }

      this.port.pos = var1;
      if(var1 < var5) {
         return new String(var2, var4, var1 - var4);
      } else {
         StringBuffer var3 = new StringBuffer();
         var3.append(var2, var4, var1 - var4);

         while(true) {
            var1 = this.port.read();
            if(var1 < 0) {
               break;
            }

            if(!Character.isJavaIdentifierPart((char)var1)) {
               this.port.unread_quick();
               break;
            }

            var3.append((char)var1);
         }

         return var3.toString();
      }
   }

   public Double getNumericLiteral(int var1) throws IOException {
      byte var7 = 10;
      byte var5 = var7;
      int var6 = var1;
      if(var1 == 48) {
         var1 = this.read();
         if(var1 != 120 && var1 != 88) {
            var5 = var7;
            var6 = var1;
            if(var1 != 46) {
               var5 = var7;
               var6 = var1;
               if(var1 != 101) {
                  var5 = var7;
                  var6 = var1;
                  if(var1 != 69) {
                     var5 = 8;
                     var6 = var1;
                  }
               }
            }
         } else {
            var5 = 16;
            var6 = this.read();
         }
      }

      var1 = this.port.pos;
      int var14 = var1;
      if(var6 >= 0) {
         var14 = var1 - 1;
      }

      this.port.pos = var14;
      long var10 = readDigitsInBuffer(this.port, var5);
      boolean var12;
      if(this.port.pos > var14) {
         var12 = true;
      } else {
         var12 = false;
      }

      if(var12 && this.port.pos < this.port.limit) {
         char var15 = this.port.buffer[this.port.pos];
         if(!Character.isLetterOrDigit((char)var15) && var15 != 46) {
            double var2;
            if(var10 >= 0L) {
               var2 = (double)var10;
            } else {
               var2 = IntNum.valueOf(this.port.buffer, var14, this.port.pos - var14, var5, false).doubleValue();
            }

            return new Double(var2);
         }
      }

      if(var5 != 10) {
         this.error("invalid character in non-decimal number");
      }

      StringBuffer var4 = new StringBuffer(20);
      if(var12) {
         var4.append(this.port.buffer, var14, this.port.pos - var14);
      }

      var6 = -1;
      byte var8 = 0;

      label87:
      while(true) {
         while(true) {
            int var9 = this.port.read();
            if(Character.digit((char)var9, var5) < 0) {
               switch(var9) {
               case 46:
                  if(var6 >= 0) {
                     this.error("duplicate \'.\' in number");
                  } else {
                     var6 = var4.length();
                     var4.append('.');
                  }
                  break;
               case 69:
               case 101:
                  var6 = var8;
                  var14 = var9;
                  if(var5 == 10) {
                     int var13 = this.port.peek();
                     if(var13 != 43 && var13 != 45) {
                        var6 = var8;
                        var14 = var9;
                        if(Character.digit((char)var13, 10) < 0) {
                           break label87;
                        }
                     }

                     if(!var12) {
                        this.error("mantissa with no digits");
                     }

                     var6 = this.readOptionalExponent();
                     var14 = this.read();
                  }
                  break label87;
               default:
                  var14 = var9;
                  var6 = var8;
                  break label87;
               }
            } else {
               var12 = true;
               var4.append((char)var9);
            }
         }
      }

      if(var14 >= 0) {
         this.port.unread();
      }

      if(var6 != 0) {
         var4.append('e');
         var4.append(var6);
      }

      return new Double(var4.toString());
   }

   public String getStringLiteral(char var1) throws IOException, SyntaxException {
      int var5 = this.port.pos;
      int var6 = this.port.limit;
      char[] var2 = this.port.buffer;

      int var4;
      for(var4 = var5; var4 < var6; ++var4) {
         char var7 = var2[var4];
         if(var7 == var1) {
            this.port.pos = var4 + 1;
            return new String(var2, var5, var4 - var5);
         }

         if(var7 == 92 || var7 == 10 || var7 == 13) {
            break;
         }
      }

      this.port.pos = var4;
      StringBuffer var3 = new StringBuffer();
      var3.append(var2, var5, var4 - var5);

      while(true) {
         var5 = this.port.read();
         if(var5 == var1) {
            return var3.toString();
         }

         if(var5 < 0) {
            this.eofError("unterminated string literal");
         }

         if(var5 == 10 || var5 == 13) {
            this.fatal("string literal not terminated before end of line");
         }

         var4 = var5;
         if(var5 == 92) {
            var6 = this.port.read();
            var4 = var6;
            switch(var6) {
            case -1:
               this.eofError("eof following \'\\\' in string");
            case 10:
            case 13:
               this.fatal("line terminator following \'\\\' in string");
               var4 = var6;
            case 34:
            case 39:
            case 92:
               break;
            case 98:
               var4 = 8;
               break;
            case 102:
               var4 = 12;
               break;
            case 110:
               var4 = 10;
               break;
            case 114:
               var4 = 13;
               break;
            case 116:
               var4 = 9;
               break;
            case 117:
            case 120:
               var4 = 0;
               if(var6 == 120) {
                  var5 = 2;
               } else {
                  var5 = 4;
               }

               while(true) {
                  int var9 = var5 - 1;
                  var5 = var4;
                  if(var9 < 0) {
                     break;
                  }

                  var5 = this.port.read();
                  if(var5 < 0) {
                     this.eofError("eof following \'\\" + (char)var6 + "\' in string");
                  }

                  char var8 = Character.forDigit((char)var5, 16);
                  if(var8 < 0) {
                     this.error("invalid char following \'\\" + (char)var6 + "\' in string");
                     var5 = 63;
                     break;
                  }

                  var4 = var4 * 16 + var8;
                  var5 = var9;
               }

               var4 = var5;
               break;
            default:
               var4 = var6;
               if(var6 >= 48) {
                  if(var6 > 55) {
                     var4 = var6;
                  } else {
                     var4 = 0;
                     var5 = 3;

                     while(true) {
                        --var5;
                        if(var5 < 0) {
                           break;
                        }

                        var6 = this.port.read();
                        if(var6 < 0) {
                           this.eofError("eof in octal escape in string literal");
                        }

                        char var10 = Character.forDigit((char)var6, 8);
                        if(var10 < 0) {
                           this.port.unread_quick();
                           break;
                        }

                        var4 = var4 * 8 + var10;
                     }
                  }
               }
            }
         }

         var3.append((char)var4);
      }
   }

   public Object getToken() throws IOException, SyntaxException {
      Object var1;
      for(int var4 = this.read(); var4 >= 0; var4 = this.read()) {
         if(!Character.isWhitespace((char)var4)) {
            switch(var4) {
            case 33:
               if(this.port.peek() == 61) {
                  this.port.skip_quick();
                  return Reserved.opNotEqual;
               }

               return notToken;
            case 34:
            case 39:
               return new QuoteExp(this.getStringLiteral((char)var4));
            case 37:
               return this.maybeAssignment(Reserved.opRemainder);
            case 38:
               if(this.port.peek() == 38) {
                  this.port.skip_quick();
                  return this.maybeAssignment(Reserved.opBoolAnd);
               }

               return this.maybeAssignment(Reserved.opBitAnd);
            case 40:
               return lparenToken;
            case 41:
               return rparenToken;
            case 42:
               return this.maybeAssignment(Reserved.opTimes);
            case 43:
               if(this.port.peek() == 43) {
                  this.port.skip_quick();
                  return this.maybeAssignment(Reserved.opPlusPlus);
               }

               return this.maybeAssignment(Reserved.opPlus);
            case 44:
               return commaToken;
            case 45:
               if(this.port.peek() == 45) {
                  this.port.skip_quick();
                  return this.maybeAssignment(Reserved.opMinusMinus);
               }

               return this.maybeAssignment(Reserved.opMinus);
            case 46:
               var4 = this.port.peek();
               if(var4 >= 48 && var4 <= 57) {
                  return new QuoteExp(this.getNumericLiteral(46));
               }

               return dotToken;
            case 47:
               return this.maybeAssignment(Reserved.opDivide);
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
               return new QuoteExp(this.getNumericLiteral(var4));
            case 58:
               return colonToken;
            case 59:
               return semicolonToken;
            case 60:
               switch(this.port.peek()) {
               case 60:
                  this.port.skip_quick();
                  return this.maybeAssignment(Reserved.opLshift);
               case 61:
                  this.port.skip_quick();
                  return Reserved.opLessEqual;
               default:
                  return Reserved.opLess;
               }
            case 61:
               if(this.port.peek() == 61) {
                  this.port.skip_quick();
                  return Reserved.opEqual;
               }

               return equalToken;
            case 62:
               switch(this.port.peek()) {
               case 61:
                  this.port.skip_quick();
                  return Reserved.opGreaterEqual;
               case 62:
                  this.port.skip_quick();
                  if(this.port.peek() == 62) {
                     this.port.skip_quick();
                     return this.maybeAssignment(Reserved.opRshiftUnsigned);
                  }

                  return this.maybeAssignment(Reserved.opRshiftSigned);
               default:
                  return Reserved.opGreater;
               }
            case 63:
               return condToken;
            case 91:
               return lbracketToken;
            case 93:
               return rbracketToken;
            case 94:
               return this.maybeAssignment(Reserved.opBitXor);
            case 123:
               return lbraceToken;
            case 124:
               if(this.port.peek() == 124) {
                  this.port.skip_quick();
                  return this.maybeAssignment(Reserved.opBoolOr);
               }

               return this.maybeAssignment(Reserved.opBitOr);
            case 125:
               return rbraceToken;
            case 126:
               return tildeToken;
            default:
               if(!Character.isJavaIdentifierStart((char)var4)) {
                  return Char.make((char)var4);
               }

               String var3 = this.getIdentifier(var4).intern();
               Object var2 = checkReserved(var3);
               var1 = var2;
               if(var2 == null) {
                  return var3;
               }

               return var1;
            }
         }

         if(var4 == 13) {
            this.prevWasCR = true;
            return eolToken;
         }

         if(var4 == 10 && !this.prevWasCR) {
            return eolToken;
         }

         this.prevWasCR = false;
      }

      var1 = eofToken;
      return var1;
   }

   public Object maybeAssignment(Object var1) throws IOException, SyntaxException {
      int var2 = this.read();
      if(var2 == 61) {
         this.error("assignment operation not implemented");
      }

      if(var2 >= 0) {
         this.port.unread_quick();
      }

      return var1;
   }
}
