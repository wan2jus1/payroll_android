package gnu.q2.lang;

import gnu.expr.Keyword;
import gnu.expr.QuoteExp;
import gnu.kawa.lispexpr.LispReader;
import gnu.kawa.xml.MakeAttribute;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.lists.Sequence;
import gnu.mapping.InPort;
import gnu.q2.lang.Q2;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.IOException;
import kawa.standard.begin;

public class Q2Read extends LispReader {

   int curIndentation;
   int expressionStartColumn;
   String expressionStartFile;
   int expressionStartLine;


   public Q2Read(InPort var1) {
      super(var1);
      this.init();
   }

   public Q2Read(InPort var1, SourceMessages var2) {
      super(var1, var2);
      this.init();
   }

   public static Object readObject(InPort var0) throws IOException, SyntaxException {
      return (new Q2Read(var0)).readObject();
   }

   void init() {
      ((InPort)this.port).readState = 32;
   }

   public Object readCommand() throws IOException, SyntaxException {
      int var3 = this.skipIndentation();
      Object var1;
      if(var3 < 0) {
         var1 = Sequence.eofValue;
      } else {
         this.curIndentation = var3;
         Object var2 = this.readIndentCommand();
         var1 = var2;
         if(!this.interactive) {
            this.port.reset();
            return var2;
         }
      }

      return var1;
   }

   public Object readCommand(boolean var1) throws IOException, SyntaxException {
      this.port.getLineNumber();
      int var13 = this.port.getColumnNumber();
      int var10 = var13;
      Object var4 = LList.Empty;
      PairWithPosition var3 = null;
      PairWithPosition var2 = null;

      Object var5;
      while(true) {
         int var11 = this.read();
         PairWithPosition var6;
         if(var11 < 0) {
            var6 = var2;
         } else {
            if(var11 == 32 || var11 == 9) {
               continue;
            }

            this.unread();
            var6 = var2;
            if(var11 != 41) {
               label115: {
                  int var14 = this.port.getLineNumber();
                  int var9 = this.port.getColumnNumber();

                  while(var11 == 13 || var11 == 10) {
                     var5 = var4;
                     if(this.singleLine()) {
                        return var5;
                     }

                     this.read();
                     this.skipIndentation();
                     int var12 = this.port.getColumnNumber();
                     var11 = this.peek();
                     var9 = var12;
                     if(var12 <= var13) {
                        var9 = var12;
                        break;
                     }
                  }

                  if(var9 <= var13) {
                     var6 = var2;
                     if(var2 != null) {
                        break label115;
                     }
                  }

                  PairWithPosition var15;
                  if(var9 == var10 && var2 != null) {
                     var5 = this.readCommand();
                  } else if(var9 < var10 && var2 != null) {
                     var15 = var3;

                     while(true) {
                        Object var16 = var15.getCdr();
                        if(var16 == LList.Empty) {
                           var6 = var2;
                           break;
                        }

                        PairWithPosition var7 = (PairWithPosition)var16;
                        var10 = var7.getColumnNumber() - 1;
                        if(var10 >= var9) {
                           if(var10 > var9) {
                              this.error('e', "some tokens on previous line indented more than current line");
                           }

                           Object var8 = var7.getCdr();
                           var6 = var2;
                           if(var8 == LList.Empty) {
                              break;
                           }

                           if(((PairWithPosition)var8).getColumnNumber() - 1 != var9) {
                              var6 = (PairWithPosition)this.makePair(var7, this.port.getLineNumber(), var9);
                              var15.setCdrBackdoor(var6);
                              break;
                           }

                           var15 = (PairWithPosition)var8;
                        } else {
                           var15 = var7;
                        }
                     }

                     var5 = this.readCommand();
                     var2 = var6;
                  } else {
                     var5 = this.readObject();
                  }

                  var6 = var2;
                  if(var5 != Sequence.eofValue) {
                     var10 = var9;
                     String var18 = this.port.getName();
                     var15 = PairWithPosition.make(var5, LList.Empty, var18, var14 + 1, var9 + 1);
                     if(var2 == null) {
                        var3 = var15;
                        var4 = var15;
                     } else {
                        if(var2.getCar() instanceof Keyword) {
                           QuoteExp var17 = new QuoteExp(((Keyword)var2.getCar()).getName());
                           var2.setCar(new PairWithPosition(var2, MakeAttribute.makeAttribute, new PairWithPosition(var2, var17, var15)));
                           continue;
                        }

                        var2.setCdrBackdoor(var15);
                     }

                     var2 = var15;
                     continue;
                  }
               }
            }
         }

         var5 = var4;
         if(!var1) {
            if(var4 == var6) {
               var5 = var6.getCar();
            } else {
               var5 = var4;
               if(var6 == null) {
                  return QuoteExp.voidExp;
               }
            }
         }
         break;
      }

      return var5;
   }

   Object readIndentCommand() throws IOException, SyntaxException {
      int var4 = this.curIndentation;
      Object var1 = LList.Empty;
      LList var2 = LList.Empty;

      Object var8;
      while(true) {
         int var5 = this.read();
         if(var5 < 0) {
            var8 = var1;
            break;
         }

         if(var5 != 32 && var5 != 9) {
            this.unread();
            var8 = var1;
            if(var5 == 41) {
               break;
            }

            int var6;
            if(var5 == 13 || var5 == 10) {
               var8 = var1;
               if(!this.singleLine()) {
                  this.read();
                  this.port.mark(Integer.MAX_VALUE);
                  var5 = this.skipIndentation();
                  Object var3 = LList.Empty;

                  int var7;
                  for(this.curIndentation = var5; this.curIndentation != -1 && var5 == this.curIndentation; var3 = this.makePair(this.readIndentCommand(), var3, var6, var7)) {
                     var6 = Q2.compareIndentation(var5, var4);
                     if(var6 == Integer.MIN_VALUE) {
                        this.error('e', "cannot compare indentation - mix of tabs and spaces");
                        break;
                     }

                     if(var6 == -1 || var6 == 1) {
                        this.error('e', "indentation must differ by 2 or more");
                        break;
                     }

                     if(var6 <= 0) {
                        break;
                     }

                     var6 = this.port.getLineNumber();
                     var7 = this.port.getColumnNumber();
                  }

                  var8 = var1;
                  if(var3 != LList.Empty) {
                     var8 = new Pair(new Pair(begin.begin, LList.reverseInPlace(var3)), var1);
                  }
               }
               break;
            }

            var5 = this.port.getLineNumber();
            var6 = this.port.getColumnNumber();
            var1 = this.makePair(this.readObject(), var1, var5, var6);
         }
      }

      return LList.reverseInPlace(var8);
   }

   void saveExpressionStartPosition() {
      this.expressionStartFile = this.port.getName();
      this.expressionStartLine = this.port.getLineNumber();
      this.expressionStartColumn = this.port.getColumnNumber();
   }

   boolean singleLine() {
      return this.interactive && this.nesting == 0;
   }

   int skipIndentation() throws IOException, SyntaxException {
      int var2 = 0;
      byte var5 = 0;
      int var1 = this.port.read();

      while(true) {
         int var3 = var1;
         int var4 = var5;
         if(var1 != 9) {
            while(var3 == 32) {
               ++var4;
               var3 = this.port.read();
            }

            if(var3 < 0) {
               return -1;
            } else {
               this.port.unread();
               return (var2 << 16) + var4;
            }
         }

         ++var2;
         var1 = this.port.read();
      }
   }
}
