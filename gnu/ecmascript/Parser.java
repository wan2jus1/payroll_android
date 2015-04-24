package gnu.ecmascript;

import gnu.ecmascript.Lexer;
import gnu.ecmascript.Prompter;
import gnu.ecmascript.Reserved;
import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.LambdaExp;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.SetExp;
import gnu.lists.Sequence;
import gnu.mapping.Environment;
import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.TtyInPort;
import gnu.mapping.Values;
import gnu.text.SyntaxException;
import java.io.IOException;
import java.util.Vector;
import kawa.standard.Scheme;

public class Parser {

   public static final Expression[] emptyArgs = new Expression[0];
   static Expression emptyStatement = new QuoteExp(Values.empty);
   public static Expression eofExpr = new QuoteExp(Sequence.eofValue);
   public int errors;
   Lexer lexer;
   InPort port;
   Object previous_token;
   Object token;


   public Parser(InPort var1) {
      this.port = var1;
      this.lexer = new Lexer(var1);
   }

   public static void main(String[] var0) {
      new Scheme();
      InPort var4 = InPort.inDefault();
      if(var4 instanceof TtyInPort) {
         Prompter var1 = new Prompter();
         ((TtyInPort)var4).setPrompter((Procedure)var1);
      }

      Parser var5 = new Parser(var4);
      OutPort var6 = OutPort.outDefault();

      while(true) {
         try {
            Expression var2 = var5.parseStatement();
            if(var2 == eofExpr) {
               return;
            }

            var6.print((String)"[Expression: ");
            var2.print((OutPort)var6);
            var6.println("]");
            Object var7 = var2.eval((Environment)Environment.user());
            var6.print((String)"result: ");
            var6.print((Object)var7);
            var6.println();
         } catch (Throwable var3) {
            System.err.println("caught exception:" + var3);
            var3.printStackTrace(System.err);
            return;
         }
      }
   }

   public Expression buildLoop(Expression var1, Expression var2, Expression var3, Expression var4) {
      if(var1 != null) {
         return new BeginExp(new Expression[]{var1, this.buildLoop((Expression)null, var2, var3, var4)});
      } else {
         throw new Error("not implemented - buildLoop");
      }
   }

   public String getIdentifier() throws IOException, SyntaxException {
      Object var1 = this.getToken();
      if(var1 instanceof String) {
         return (String)var1;
      } else {
         this.syntaxError("missing identifier");
         return "??";
      }
   }

   public void getSemicolon() throws IOException, SyntaxException {
      this.token = this.peekToken();
      if(this.token == Lexer.semicolonToken) {
         this.skipToken();
      } else if(this.token != Lexer.rbraceToken && this.token != Lexer.eofToken && this.previous_token != Lexer.eolToken) {
         this.syntaxError("missing \';\' after expression");
         return;
      }

   }

   public Object getToken() throws IOException, SyntaxException {
      Object var1 = this.peekToken();
      this.skipToken();
      return var1;
   }

   public Expression makeCallExpression(Expression var1, Expression[] var2) {
      return new ApplyExp(var1, var2);
   }

   public Expression makeNewExpression(Expression var1, Expression[] var2) {
      Expression[] var3 = var2;
      if(var2 == null) {
         var3 = emptyArgs;
      }

      return new ApplyExp((Expression)null, var3);
   }

   public Expression makePropertyAccessor(Expression var1, Expression var2) {
      return null;
   }

   public Expression[] parseArguments() throws IOException, SyntaxException {
      this.skipToken();
      if(this.peekToken() == Lexer.rparenToken) {
         this.skipToken();
         return emptyArgs;
      } else {
         Vector var1 = new Vector(10);

         while(true) {
            var1.addElement(this.parseAssignmentExpression());
            Object var2 = this.getToken();
            if(var2 == Lexer.rparenToken) {
               Expression[] var3 = new Expression[var1.size()];
               var1.copyInto(var3);
               return var3;
            }

            if(var2 != Lexer.commaToken) {
               this.syntaxError("invalid token \'" + var2 + "\' in argument list");
            }
         }
      }
   }

   public Expression parseAssignmentExpression() throws IOException, SyntaxException {
      Expression var2 = this.parseConditionalExpression();
      Object var3 = this.peekToken();
      Expression var1;
      Object var4;
      if(var3 == Lexer.equalToken) {
         this.skipToken();
         var1 = this.parseAssignmentExpression();
         if(!(var2 instanceof ReferenceExp)) {
            return this.syntaxError("unmplemented non-symbol ihs in assignment");
         }

         var4 = new SetExp(((ReferenceExp)var2).getName(), var1);
         ((SetExp)var4).setDefining(true);
      } else {
         var4 = var2;
         if(var3 instanceof Reserved) {
            Reserved var5 = (Reserved)var3;
            var4 = var2;
            if(var5.isAssignmentOp()) {
               this.skipToken();
               var1 = this.parseAssignmentExpression();
               return new ApplyExp(new QuoteExp(var5.proc), new Expression[]{var2, var1});
            }
         }
      }

      return (Expression)var4;
   }

   public Expression parseBinaryExpression(int var1) throws IOException, SyntaxException {
      Object var2 = this.parseUnaryExpression();

      while(true) {
         this.token = this.peekToken();
         if(!(this.token instanceof Reserved)) {
            break;
         }

         Reserved var3 = (Reserved)this.token;
         if(var3.prio < var1) {
            break;
         }

         this.getToken();
         Expression var4 = this.parseBinaryExpression(var3.prio + 1);
         var2 = new ApplyExp(new QuoteExp(var3.proc), new Expression[]{(Expression)var2, var4});
      }

      return (Expression)var2;
   }

   public Expression parseBlock() throws IOException, SyntaxException {
      Expression[] var2 = null;
      if(this.getToken() != Lexer.lbraceToken) {
         return this.syntaxError("extened \'{\'");
      } else {
         int var3 = 0;

         while(true) {
            this.token = this.peekToken();
            boolean var4;
            if(this.token == Lexer.rbraceToken) {
               this.skipToken();
               if(var2 == null) {
                  return emptyStatement;
               }

               var4 = true;
            } else {
               var4 = false;
            }

            Expression[] var1;
            if(var2 == null) {
               var1 = new Expression[2];
            } else {
               label47: {
                  if(var4) {
                     var1 = var2;
                     if(var2.length == var3) {
                        break label47;
                     }
                  } else {
                     var1 = var2;
                     if(var2.length > var3) {
                        break label47;
                     }
                  }

                  int var5;
                  if(var4) {
                     var5 = var3;
                  } else {
                     var5 = var2.length * 2;
                  }

                  var1 = new Expression[var5];
                  System.arraycopy(var2, 0, var1, 0, var3);
               }
            }

            if(var4) {
               return new BeginExp(var1);
            }

            var1[var3] = this.parseStatement();
            ++var3;
            var2 = var1;
         }
      }
   }

   public Expression parseConditionalExpression() throws IOException, SyntaxException {
      Expression var1 = this.parseBinaryExpression(1);
      if(this.peekToken() != Lexer.condToken) {
         return var1;
      } else {
         this.skipToken();
         Expression var2 = this.parseAssignmentExpression();
         return (Expression)(this.getToken() != Lexer.colonToken?this.syntaxError("expected \':\' in conditional expression"):new IfExp(var1, var2, this.parseAssignmentExpression()));
      }
   }

   public Expression parseExpression() throws IOException, SyntaxException {
      Expression[] var2 = null;
      int var4 = 0;

      while(true) {
         Expression var3 = this.parseAssignmentExpression();
         boolean var5;
         if(this.peekToken() != Lexer.commaToken) {
            var5 = true;
         } else {
            var5 = false;
         }

         Expression[] var1;
         if(var2 == null) {
            if(var5) {
               return var3;
            }

            var1 = new Expression[2];
         } else {
            label42: {
               if(var5) {
                  var1 = var2;
                  if(var2.length == var4 + 1) {
                     break label42;
                  }
               } else {
                  var1 = var2;
                  if(var2.length > var4) {
                     break label42;
                  }
               }

               int var6;
               if(var5) {
                  var6 = var4 + 1;
               } else {
                  var6 = var2.length * 2;
               }

               var1 = new Expression[var6];
               System.arraycopy(var2, 0, var1, 0, var4);
            }
         }

         var1[var4] = var3;
         if(var5) {
            return new BeginExp(var1);
         }

         this.skipToken();
         ++var4;
         var2 = var1;
      }
   }

   public Expression parseFunctionDefinition() throws IOException, SyntaxException {
      this.skipToken();
      String var1 = this.getIdentifier();
      Object var2 = this.getToken();
      if(var2 != Lexer.lparenToken) {
         return this.syntaxError("expected \'(\' - got:" + var2);
      } else {
         Vector var5 = new Vector(10);
         if(this.peekToken() == Lexer.rparenToken) {
            this.skipToken();
         } else {
            while(true) {
               var5.addElement(this.getIdentifier());
               Object var3 = this.getToken();
               if(var3 == Lexer.rparenToken) {
                  break;
               }

               if(var3 != Lexer.commaToken) {
                  this.syntaxError("invalid token \'" + var3 + "\' in argument list");
               }
            }
         }

         LambdaExp var6 = new LambdaExp(this.parseBlock());
         var6.setName(var1);
         SetExp var4 = new SetExp(var1, var6);
         var4.setDefining(true);
         return var4;
      }
   }

   public Expression parseIfStatement() throws IOException, SyntaxException {
      this.skipToken();
      Object var1 = this.getToken();
      if(var1 != Lexer.lparenToken) {
         return this.syntaxError("expected \'(\' - got:" + var1);
      } else {
         Expression var2 = this.parseExpression();
         var1 = this.getToken();
         if(var1 != Lexer.rparenToken) {
            return this.syntaxError("expected \')\' - got:" + var1);
         } else {
            Expression var3 = this.parseStatement();
            Expression var4;
            if(this.peekToken() == Lexer.elseToken) {
               this.skipToken();
               var4 = this.parseStatement();
            } else {
               var4 = null;
            }

            return new IfExp(var2, var3, var4);
         }
      }
   }

   public Expression parseLeftHandSideExpression() throws IOException, SyntaxException {
      int var4 = 0;

      while(this.peekToken() == Lexer.newToken) {
         ++var4;
         this.skipToken();
      }

      Expression var1 = this.parsePrimaryExpression();

      while(true) {
         while(true) {
            Object var3 = this.peekToken();
            if(var3 == Lexer.dotToken) {
               this.skipToken();
               var1 = this.makePropertyAccessor(var1, new QuoteExp(this.getIdentifier()));
            } else {
               Expression var2;
               if(var3 == Lexer.lbracketToken) {
                  this.skipToken();
                  var2 = this.parseExpression();
                  var3 = this.getToken();
                  if(var3 != Lexer.rbracketToken) {
                     return this.syntaxError("expected \']\' - got:" + var3);
                  }

                  var1 = this.makePropertyAccessor(var1, var2);
               } else {
                  var2 = var1;
                  int var5 = var4;
                  if(var3 != Lexer.lparenToken) {
                     while(var5 > 0) {
                        var2 = this.makeNewExpression(var2, (Expression[])null);
                        --var5;
                     }

                     return var2;
                  }

                  Expression[] var6 = this.parseArguments();
                  System.err.println("after parseArgs:" + this.peekToken());
                  if(var4 > 0) {
                     var1 = this.makeNewExpression(var1, var6);
                     --var4;
                  } else {
                     var1 = this.makeCallExpression(var1, var6);
                  }
               }
            }
         }
      }
   }

   public Expression parsePostfixExpression() throws IOException, SyntaxException {
      Expression var1 = this.parseLeftHandSideExpression();
      Object var2 = this.peekTokenOrLine();
      if(var2 != Reserved.opPlusPlus && var2 != Reserved.opMinusMinus) {
         return var1;
      } else {
         this.skipToken();
         return new ApplyExp(new QuoteExp(((Reserved)var2).proc), new Expression[]{var1});
      }
   }

   public Expression parsePrimaryExpression() throws IOException, SyntaxException {
      Object var1 = this.getToken();
      if(var1 instanceof QuoteExp) {
         return (QuoteExp)var1;
      } else if(var1 instanceof String) {
         return new ReferenceExp((String)var1);
      } else if(var1 == Lexer.lparenToken) {
         Expression var3 = this.parseExpression();
         Object var2 = this.getToken();
         return var2 != Lexer.rparenToken?this.syntaxError("expected \')\' - got:" + var2):var3;
      } else {
         return this.syntaxError("unexpected token: " + var1);
      }
   }

   public Expression parseStatement() throws IOException, SyntaxException {
      Object var1 = this.peekToken();
      if(var1 instanceof Reserved) {
         switch(((Reserved)var1).prio) {
         case 31:
            return this.parseIfStatement();
         case 32:
            return this.parseWhileStatement();
         case 41:
            return this.parseFunctionDefinition();
         }
      }

      if(var1 == Lexer.eofToken) {
         return eofExpr;
      } else if(var1 == Lexer.semicolonToken) {
         this.skipToken();
         return emptyStatement;
      } else if(var1 == Lexer.lbraceToken) {
         return this.parseBlock();
      } else {
         Expression var2 = this.parseExpression();
         this.getSemicolon();
         return var2;
      }
   }

   public Expression parseUnaryExpression() throws IOException, SyntaxException {
      return this.parsePostfixExpression();
   }

   public Expression parseWhileStatement() throws IOException, SyntaxException {
      this.skipToken();
      Object var1 = this.getToken();
      if(var1 != Lexer.lparenToken) {
         return this.syntaxError("expected \'(\' - got:" + var1);
      } else {
         Expression var3 = this.parseExpression();
         Object var2 = this.getToken();
         return var2 != Lexer.rparenToken?this.syntaxError("expected \')\' - got:" + var2):this.buildLoop((Expression)null, var3, (Expression)null, this.parseStatement());
      }
   }

   public Object peekToken() throws IOException, SyntaxException {
      if(this.token == null) {
         this.token = this.lexer.getToken();
      }

      while(this.token == Lexer.eolToken) {
         this.skipToken();
         this.token = this.lexer.getToken();
      }

      return this.token;
   }

   public Object peekTokenOrLine() throws IOException, SyntaxException {
      if(this.token == null) {
         this.token = this.lexer.getToken();
      }

      return this.token;
   }

   public final void skipToken() {
      if(this.token != Lexer.eofToken) {
         this.previous_token = this.token;
         this.token = null;
      }

   }

   public Expression syntaxError(String var1) {
      ++this.errors;
      OutPort var2 = OutPort.errDefault();
      String var3 = this.port.getName();
      int var4 = this.port.getLineNumber() + 1;
      int var5 = this.port.getColumnNumber() + 1;
      if(var4 > 0) {
         if(var3 != null) {
            var2.print((String)var3);
         }

         var2.print(':');
         var2.print(var4);
         if(var5 > 1) {
            var2.print(':');
            var2.print(var5);
         }

         var2.print((String)": ");
      }

      var2.println(var1);
      return new ErrorExp(var1);
   }
}
