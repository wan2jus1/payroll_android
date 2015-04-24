package gnu.expr;

import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.CheckedTarget;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ExpVisitor;
import gnu.expr.IfExp;
import gnu.expr.InlineCalls;
import gnu.expr.LambdaExp;
import gnu.expr.LetExp;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.kawa.reflect.OccurrenceType;
import gnu.kawa.util.IdentityHashTable;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.CharArrayOutPort;
import gnu.mapping.Environment;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure0;
import gnu.text.Printable;
import gnu.text.SourceLocator;
import java.io.PrintWriter;

public abstract class Expression extends Procedure0 implements Printable, SourceLocator {

   protected static final int NEXT_AVAIL_FLAG = 2;
   public static final int VALIDATED = 1;
   public static final Expression[] noExpressions = new Expression[0];
   String filename;
   protected int flags;
   int position;


   public static void compileButFirst(Expression var0, Compilation var1) {
      if(var0 instanceof BeginExp) {
         BeginExp var4 = (BeginExp)var0;
         int var3 = var4.length;
         if(var3 != 0) {
            Expression[] var5 = var4.exps;
            compileButFirst(var5[0], var1);

            for(int var2 = 1; var2 < var3; ++var2) {
               var5[var2].compileWithPosition(var1, Target.Ignore);
            }
         }
      }

   }

   protected static Expression deepCopy(Expression var0) {
      return deepCopy((Expression)var0, new IdentityHashTable());
   }

   public static Expression deepCopy(Expression var0, IdentityHashTable var1) {
      if(var0 == null) {
         return null;
      } else {
         Object var2 = var1.get(var0);
         if(var2 != null) {
            return (Expression)var2;
         } else {
            Expression var3 = var0.deepCopy((IdentityHashTable)var1);
            var1.put(var0, var3);
            return var3;
         }
      }
   }

   public static Expression[] deepCopy(Expression[] var0, IdentityHashTable var1) {
      Expression[] var2;
      if(var0 == null) {
         var2 = null;
      } else {
         int var6 = var0.length;
         Expression[] var3 = new Expression[var6];
         int var5 = 0;

         while(true) {
            var2 = var3;
            if(var5 >= var6) {
               break;
            }

            Expression var7 = var0[var5];
            Expression var4 = deepCopy((Expression)var7, var1);
            if(var4 == null && var7 != null) {
               return null;
            }

            var3[var5] = var4;
            ++var5;
         }
      }

      return var2;
   }

   public static Expression makeWhile(Object var0, Object var1, Compilation var2) {
      Expression[] var3 = new Expression[1];
      LetExp var4 = new LetExp(var3);
      Declaration var5 = var4.addDeclaration("%do%loop");
      ApplyExp var6 = new ApplyExp(new ReferenceExp(var5), noExpressions);
      LambdaExp var7 = new LambdaExp();
      var2.push((ScopeExp)var7);
      var7.body = new IfExp(var2.parse(var0), new BeginExp(var2.parse(var1), var6), QuoteExp.voidExp);
      var7.setName("%do%loop");
      var2.pop(var7);
      var3[0] = var7;
      var5.noteValue(var7);
      var4.setBody(new ApplyExp(new ReferenceExp(var5), noExpressions));
      return var4;
   }

   public void apply(CallContext var1) throws Throwable {
      throw new RuntimeException("internal error - " + this.getClass() + ".eval called");
   }

   public final Object apply0() throws Throwable {
      CallContext var1 = CallContext.getInstance();
      this.check0(var1);
      return var1.runUntilValue();
   }

   public final void compile(Compilation var1, Type var2) {
      this.compile(var1, (Target)StackTarget.getInstance(var2));
   }

   public final void compile(Compilation var1, Declaration var2) {
      this.compile(var1, (Target)CheckedTarget.getInstance((Declaration)var2));
   }

   public abstract void compile(Compilation var1, Target var2);

   public final void compileNotePosition(Compilation var1, Target var2, Expression var3) {
      String var4 = var1.getFileName();
      int var5 = var1.getLineNumber();
      int var6 = var1.getColumnNumber();
      var1.setLine((Expression)var3);
      this.compile(var1, (Target)var2);
      var1.setLine(var4, var5, var6);
   }

   public final void compileWithPosition(Compilation var1, Target var2) {
      int var3 = this.getLineNumber();
      if(var3 > 0) {
         var1.getCode().putLineNumber(this.getFileName(), var3);
         this.compileNotePosition(var1, var2, this);
      } else {
         this.compile(var1, (Target)var2);
      }
   }

   public final void compileWithPosition(Compilation var1, Target var2, Expression var3) {
      int var4 = var3.getLineNumber();
      if(var4 > 0) {
         var1.getCode().putLineNumber(var3.getFileName(), var4);
         this.compileNotePosition(var1, var2, var3);
      } else {
         this.compile(var1, (Target)var2);
      }
   }

   protected Expression deepCopy(IdentityHashTable var1) {
      return null;
   }

   public final Object eval(CallContext var1) throws Throwable {
      int var3 = var1.startFromContext();

      try {
         this.match0(var1);
         Object var2 = var1.getFromContext(var3);
         return var2;
      } catch (Throwable var4) {
         var1.cleanupFromContext(var3);
         throw var4;
      }
   }

   public final Object eval(Environment var1) throws Throwable {
      CallContext var2 = CallContext.getInstance();
      var1 = Environment.setSaveCurrent(var1);

      Object var5;
      try {
         var5 = this.eval((CallContext)var2);
      } finally {
         Environment.restoreCurrent(var1);
      }

      return var5;
   }

   public final int getColumnNumber() {
      int var2 = this.position & 4095;
      int var1 = var2;
      if(var2 == 0) {
         var1 = -1;
      }

      return var1;
   }

   public final String getFileName() {
      return this.filename;
   }

   public boolean getFlag(int var1) {
      return (this.flags & var1) != 0;
   }

   public int getFlags() {
      return this.flags;
   }

   public final int getLineNumber() {
      int var2 = this.position >> 12;
      int var1 = var2;
      if(var2 == 0) {
         var1 = -1;
      }

      return var1;
   }

   public String getPublicId() {
      return null;
   }

   public String getSystemId() {
      return this.filename;
   }

   public Type getType() {
      return Type.pointer_type;
   }

   public boolean isSingleValue() {
      return OccurrenceType.itemCountIsOne(this.getType());
   }

   public boolean isStableSourceLocation() {
      return true;
   }

   public final int match0(CallContext var1) {
      var1.proc = this;
      var1.pc = 0;
      return 0;
   }

   protected abstract boolean mustCompile();

   public final void print(Consumer var1) {
      if(var1 instanceof OutPort) {
         this.print((OutPort)((OutPort)var1));
      } else if(var1 instanceof PrintWriter) {
         OutPort var3 = new OutPort((PrintWriter)var1);
         this.print((OutPort)var3);
         var3.close();
      } else {
         CharArrayOutPort var2 = new CharArrayOutPort();
         this.print((OutPort)var2);
         var2.close();
         var2.writeTo(var1);
      }
   }

   public abstract void print(OutPort var1);

   public void printLineColumn(OutPort var1) {
      int var2 = this.getLineNumber();
      if(var2 > 0) {
         var1.print((String)"line:");
         var1.print(var2);
         var2 = this.getColumnNumber();
         if(var2 > 0) {
            var1.print(':');
            var1.print(var2);
         }

         var1.writeSpaceFill();
      }

   }

   public final void setFile(String var1) {
      this.filename = var1;
   }

   public void setFlag(int var1) {
      this.flags |= var1;
   }

   public void setFlag(boolean var1, int var2) {
      if(var1) {
         this.flags |= var2;
      } else {
         this.flags &= ~var2;
      }
   }

   public final Expression setLine(Expression var1) {
      this.setLocation(var1);
      return this;
   }

   public final void setLine(int var1) {
      this.setLine(var1, 0);
   }

   public final void setLine(int var1, int var2) {
      int var3 = var1;
      if(var1 < 0) {
         var3 = 0;
      }

      var1 = var2;
      if(var2 < 0) {
         var1 = 0;
      }

      this.position = (var3 << 12) + var1;
   }

   public void setLine(Compilation var1) {
      int var2 = var1.getLineNumber();
      if(var2 > 0) {
         this.setFile(var1.getFileName());
         this.setLine(var2, var1.getColumnNumber());
      }

   }

   public final void setLocation(SourceLocator var1) {
      this.filename = var1.getFileName();
      this.setLine(var1.getLineNumber(), var1.getColumnNumber());
   }

   public boolean side_effects() {
      return true;
   }

   public String toString() {
      String var2 = this.getClass().getName();
      String var1 = var2;
      if(var2.startsWith("gnu.expr.")) {
         var1 = var2.substring(9);
      }

      return var1 + "@" + Integer.toHexString(this.hashCode());
   }

   public Expression validateApply(ApplyExp var1, InlineCalls var2, Type var3, Declaration var4) {
      var1.args = var2.visitExps(var1.args, (Object)null);
      return var1;
   }

   public Object valueIfConstant() {
      return null;
   }

   protected Object visit(ExpVisitor var1, Object var2) {
      return var1.visitExpression(this, var2);
   }

   protected void visitChildren(ExpVisitor var1, Object var2) {
   }
}
