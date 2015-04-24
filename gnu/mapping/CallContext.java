package gnu.mapping;

import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Procedure;
import gnu.mapping.ValueStack;
import gnu.mapping.Values;
import gnu.mapping.WrongArguments;
import gnu.math.IntNum;

public class CallContext {

   public static final int ARG_IN_IVALUE1 = 5;
   public static final int ARG_IN_IVALUE2 = 6;
   public static final int ARG_IN_VALUE1 = 1;
   public static final int ARG_IN_VALUE2 = 2;
   public static final int ARG_IN_VALUE3 = 3;
   public static final int ARG_IN_VALUE4 = 4;
   public static final int ARG_IN_VALUES_ARRAY = 0;
   static ThreadLocal currentContext = new ThreadLocal();
   public Consumer consumer;
   public int count;
   public Object[][] evalFrames;
   public int ivalue1;
   public int ivalue2;
   public int next;
   public int pc;
   public Procedure proc;
   public Object value1;
   public Object value2;
   public Object value3;
   public Object value4;
   public Object[] values;
   public ValueStack vstack = new ValueStack();
   public int where;


   public CallContext() {
      this.consumer = this.vstack;
   }

   public static CallContext getInstance() {
      CallContext var1 = getOnlyInstance();
      CallContext var0 = var1;
      if(var1 == null) {
         var0 = new CallContext();
         setInstance(var0);
      }

      return var0;
   }

   public static CallContext getOnlyInstance() {
      return (CallContext)currentContext.get();
   }

   public static void setInstance(CallContext var0) {
      Thread.currentThread();
      currentContext.set(var0);
   }

   public final void cleanupFromContext(int var1) {
      ValueStack var2 = this.vstack;
      char[] var3 = var2.data;
      int var4 = var3[var1 - 2] << 16 | var3[var1 - 1] & '\uffff';
      this.consumer = (Consumer)var2.objects[var4];
      var2.objects[var4] = null;
      var2.oindex = var4;
      var2.gapStart = var1 - 3;
   }

   Object getArgAsObject(int var1) {
      if(var1 < 8) {
         switch(this.where >> var1 * 4 & 15) {
         case 1:
            return this.value1;
         case 2:
            return this.value2;
         case 3:
            return this.value3;
         case 4:
            return this.value4;
         case 5:
            return IntNum.make(this.ivalue1);
         case 6:
            return IntNum.make(this.ivalue2);
         }
      }

      return this.values[var1];
   }

   public int getArgCount() {
      return this.count;
   }

   public Object[] getArgs() {
      Object[] var1;
      if(this.where == 0) {
         var1 = this.values;
      } else {
         int var4 = this.count;
         this.next = 0;
         Object[] var2 = new Object[var4];
         int var3 = 0;

         while(true) {
            var1 = var2;
            if(var3 >= var4) {
               break;
            }

            var2[var3] = this.getNextArg();
            ++var3;
         }
      }

      return var1;
   }

   public final Object getFromContext(int var1) throws Throwable {
      this.runUntilDone();
      ValueStack var2 = this.vstack;
      Object var3 = Values.make(var2, var1, var2.gapStart);
      this.cleanupFromContext(var1);
      return var3;
   }

   public Object getNextArg() {
      if(this.next >= this.count) {
         throw new WrongArguments((Procedure)null, this.count);
      } else {
         int var1 = this.next;
         this.next = var1 + 1;
         return this.getArgAsObject(var1);
      }
   }

   public Object getNextArg(Object var1) {
      if(this.next >= this.count) {
         return var1;
      } else {
         int var2 = this.next;
         this.next = var2 + 1;
         return this.getArgAsObject(var2);
      }
   }

   public int getNextIntArg() {
      if(this.next >= this.count) {
         throw new WrongArguments((Procedure)null, this.count);
      } else {
         int var1 = this.next;
         this.next = var1 + 1;
         return ((Number)this.getArgAsObject(var1)).intValue();
      }
   }

   public int getNextIntArg(int var1) {
      if(this.next >= this.count) {
         return var1;
      } else {
         var1 = this.next;
         this.next = var1 + 1;
         return ((Number)this.getArgAsObject(var1)).intValue();
      }
   }

   public final Object[] getRestArgsArray(int var1) {
      Object[] var2 = new Object[this.count - var1];

      for(int var3 = 0; var1 < this.count; ++var1) {
         var2[var3] = this.getArgAsObject(var1);
         ++var3;
      }

      return var2;
   }

   public final LList getRestArgsList(int var1) {
      LList var5 = LList.Empty;
      Object var4 = var5;

      Pair var2;
      for(Pair var3 = null; var1 < this.count; var3 = var2) {
         var2 = new Pair(this.getArgAsObject(var1), var5);
         if(var3 == null) {
            var4 = var2;
         } else {
            var3.setCdr(var2);
         }

         ++var1;
      }

      return (LList)var4;
   }

   public void lastArg() {
      if(this.next < this.count) {
         throw new WrongArguments((Procedure)null, this.count);
      } else {
         this.values = null;
      }
   }

   public void runUntilDone() throws Throwable {
      while(true) {
         Procedure var1 = this.proc;
         if(var1 == null) {
            return;
         }

         this.proc = null;
         var1.apply(this);
      }
   }

   public final Object runUntilValue() throws Throwable {
      Consumer var1 = this.consumer;
      ValueStack var2 = this.vstack;
      this.consumer = var2;
      int var4 = var2.gapStart;
      int var5 = var2.oindex;

      Object var3;
      try {
         this.runUntilDone();
         var3 = Values.make(var2, var4, var2.gapStart);
      } finally {
         this.consumer = var1;
         var2.gapStart = var4;
         var2.oindex = var5;
      }

      return var3;
   }

   public final void runUntilValue(Consumer var1) throws Throwable {
      Consumer var2 = this.consumer;
      this.consumer = var1;

      try {
         this.runUntilDone();
      } finally {
         this.consumer = var2;
      }

   }

   public final int startFromContext() {
      ValueStack var1 = this.vstack;
      int var3 = var1.find(this.consumer);
      var1.ensureSpace(3);
      int var4 = var1.gapStart;
      char[] var2 = var1.data;
      int var5 = var4 + 1;
      var2[var4] = '\uf102';
      var1.setIntN(var5, var3);
      var3 = var5 + 2;
      this.consumer = var1;
      var1.gapStart = var3;
      return var3;
   }

   public void writeValue(Object var1) {
      Values.writeValues(var1, this.consumer);
   }
}
