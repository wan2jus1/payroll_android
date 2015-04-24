package gnu.mapping;

import gnu.lists.Consumer;
import gnu.lists.TreeList;
import gnu.mapping.Procedure;
import gnu.text.Printable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.util.Iterator;
import java.util.List;

public class Values extends TreeList implements Printable, Externalizable {

   public static final Values empty = new Values(noArgs);
   public static final Object[] noArgs = new Object[0];


   public Values() {
   }

   public Values(Object[] var1) {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         this.writeObject(var1[var2]);
      }

   }

   public static int countValues(Object var0) {
      return var0 instanceof Values?((Values)var0).size():1;
   }

   public static Values make() {
      return new Values();
   }

   public static Object make(TreeList var0) {
      return make(var0, 0, var0.data.length);
   }

   public static Object make(TreeList var0, int var1, int var2) {
      if(var1 != var2) {
         int var4 = var0.nextDataIndex(var1);
         if(var4 > 0) {
            if(var4 != var2 && var0.nextDataIndex(var4) >= 0) {
               Values var3 = new Values();
               var0.consumeIRange(var1, var2, var3);
               return var3;
            }

            return var0.getPosNext(var1 << 1);
         }
      }

      return empty;
   }

   public static Object make(List var0) {
      int var3;
      if(var0 == null) {
         var3 = 0;
      } else {
         var3 = var0.size();
      }

      Values var4;
      if(var3 == 0) {
         var4 = empty;
      } else {
         if(var3 == 1) {
            return var0.get(0);
         }

         Values var1 = new Values();
         Iterator var2 = var0.iterator();

         while(true) {
            var4 = var1;
            if(!var2.hasNext()) {
               break;
            }

            var1.writeObject(var2.next());
         }
      }

      return var4;
   }

   public static Object make(Object[] var0) {
      return var0.length == 1?var0[0]:(var0.length == 0?empty:new Values(var0));
   }

   public static int nextIndex(Object var0, int var1) {
      return var0 instanceof Values?((Values)var0).nextDataIndex(var1):(var1 == 0?1:-1);
   }

   public static Object nextValue(Object var0, int var1) {
      Object var2 = var0;
      if(var0 instanceof Values) {
         Values var4 = (Values)var0;
         int var3 = var1;
         if(var1 >= var4.gapEnd) {
            var3 = var1 - (var4.gapEnd - var4.gapStart);
         }

         var2 = ((Values)var0).getPosNext(var3 << 1);
      }

      return var2;
   }

   public static Object values(Object ... var0) {
      return make((Object[])var0);
   }

   public static void writeValues(Object var0, Consumer var1) {
      if(var0 instanceof Values) {
         ((Values)var0).consume(var1);
      } else {
         var1.writeObject(var0);
      }
   }

   public Object call_with(Procedure var1) throws Throwable {
      return var1.applyN(this.toArray());
   }

   public final Object canonicalize() {
      Values var1 = this;
      if(this.gapEnd == this.data.length) {
         if(this.gapStart == 0) {
            var1 = empty;
         } else {
            var1 = this;
            if(this.nextDataIndex(0) == this.gapStart) {
               return this.getPosNext(0);
            }
         }
      }

      return var1;
   }

   public Object[] getValues() {
      return this.isEmpty()?noArgs:this.toArray();
   }

   public void print(Consumer var1) {
      if(this == empty) {
         var1.write("#!void");
      } else {
         int var3 = this.toArray().length;
         if(true) {
            var1.write("#<values");
         }

         var3 = 0;

         while(true) {
            int var5 = this.nextDataIndex(var3);
            if(var5 < 0) {
               if(true) {
                  var1.write(62);
                  return;
               }
               break;
            }

            var1.write(32);
            int var4 = var3;
            if(var3 >= this.gapEnd) {
               var4 = var3 - (this.gapEnd - this.gapStart);
            }

            Object var2 = this.getPosNext(var4 << 1);
            if(var2 instanceof Printable) {
               ((Printable)var2).print(var1);
            } else {
               var1.writeObject(var2);
            }

            var3 = var5;
         }
      }

   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      int var3 = var1.readInt();

      for(int var2 = 0; var2 < var3; ++var2) {
         this.writeObject(var1.readObject());
      }

   }

   public Object readResolve() throws ObjectStreamException {
      Values var1 = this;
      if(this.isEmpty()) {
         var1 = empty;
      }

      return var1;
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      Object[] var2 = this.toArray();
      int var4 = var2.length;
      var1.writeInt(var4);

      for(int var3 = 0; var3 < var4; ++var3) {
         var1.writeObject(var2[var3]);
      }

   }
}
