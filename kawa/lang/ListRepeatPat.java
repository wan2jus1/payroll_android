package kawa.lang;

import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.text.Printable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import kawa.lang.Pattern;

public class ListRepeatPat extends Pattern implements Printable, Externalizable {

   Pattern element_pattern;


   public ListRepeatPat() {
   }

   public ListRepeatPat(Pattern var1) {
      this.element_pattern = var1;
   }

   public static ListRepeatPat make(Pattern var0) {
      return new ListRepeatPat(var0);
   }

   public boolean match(Object var1, Object[] var2, int var3) {
      int var8 = LList.listLength(var1, false);
      if(var8 < 0) {
         return false;
      } else {
         int var7 = this.element_pattern.varCount();
         int var5 = var7;

         while(true) {
            --var5;
            if(var5 < 0) {
               Object[] var4 = new Object[var7];

               for(var5 = 0; var5 < var8; ++var5) {
                  Pair var9 = (Pair)var1;
                  if(!this.element_pattern.match(var9.getCar(), var4, 0)) {
                     return false;
                  }

                  for(int var6 = 0; var6 < var7; ++var6) {
                     ((Object[])((Object[])var2[var3 + var6]))[var5] = var4[var6];
                  }

                  var1 = var9.getCdr();
               }

               return true;
            }

            var2[var3 + var5] = new Object[var8];
         }
      }
   }

   public void print(Consumer var1) {
      var1.write("#<list-repeat-pattern ");
      this.element_pattern.print(var1);
      var1.write(62);
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.element_pattern = (Pattern)var1.readObject();
   }

   public int varCount() {
      return this.element_pattern.varCount();
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.element_pattern);
   }
}
