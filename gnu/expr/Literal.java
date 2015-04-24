package gnu.expr;

import gnu.bytecode.Field;
import gnu.bytecode.Type;
import gnu.expr.LitTable;

public class Literal {

   static final int CYCLIC = 4;
   static final int EMITTED = 8;
   static final int WRITING = 1;
   static final int WRITTEN = 2;
   public static final Literal nullLiteral = new Literal((Object)null, Type.nullType);
   Type[] argTypes;
   Object[] argValues;
   public Field field;
   public int flags;
   int index;
   Literal next;
   public Type type;
   Object value;


   public Literal(Object var1, Field var2, LitTable var3) {
      this.value = var1;
      var3.literalTable.put(var1, this);
      this.field = var2;
      this.type = var2.getType();
      this.flags = 10;
   }

   Literal(Object var1, Type var2) {
      this.value = var1;
      this.type = var2;
   }

   public Literal(Object var1, Type var2, LitTable var3) {
      this.value = var1;
      var3.literalTable.put(var1, this);
      this.type = var2;
   }

   public Literal(Object var1, LitTable var2) {
      this(var1, (String)((String)null), var2);
   }

   public Literal(Object var1, String var2, LitTable var3) {
      this.value = var1;
      var3.literalTable.put(var1, this);
      this.type = Type.make(var1.getClass());
      this.assign((String)var2, var3);
   }

   void assign(Field var1, LitTable var2) {
      this.next = var2.literalsChain;
      var2.literalsChain = this;
      this.field = var1;
   }

   void assign(LitTable var1) {
      this.assign((String)((String)null), var1);
   }

   void assign(String var1, LitTable var2) {
      int var3;
      if(var2.comp.immediate) {
         var3 = 9;
      } else {
         var3 = 24;
      }

      if(var1 == null) {
         int var4 = var2.literalsCount;
         var2.literalsCount = var4 + 1;
         this.index = var4;
         var1 = "Lit" + this.index;
      } else {
         var3 |= 1;
      }

      this.assign((Field)var2.mainClass.addField(var1, this.type, var3), var2);
   }

   public final Object getValue() {
      return this.value;
   }
}
