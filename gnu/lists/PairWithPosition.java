package gnu.lists;

import gnu.lists.ImmutablePair;
import gnu.text.SourceLocator;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class PairWithPosition extends ImmutablePair implements SourceLocator {

   String filename;
   int position;


   public PairWithPosition() {
   }

   public PairWithPosition(SourceLocator var1, Object var2, Object var3) {
      super(var2, var3);
      this.filename = var1.getFileName();
      this.setLine(var1.getLineNumber(), var1.getColumnNumber());
   }

   public PairWithPosition(Object var1, Object var2) {
      super(var1, var2);
   }

   public static PairWithPosition make(Object var0, Object var1, String var2, int var3) {
      PairWithPosition var4 = new PairWithPosition(var0, var1);
      var4.filename = var2;
      var4.position = var3;
      return var4;
   }

   public static PairWithPosition make(Object var0, Object var1, String var2, int var3, int var4) {
      PairWithPosition var5 = new PairWithPosition(var0, var1);
      var5.filename = var2;
      var5.setLine(var3, var4);
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

   public boolean isStableSourceLocation() {
      return true;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.car = var1.readObject();
      this.cdr = var1.readObject();
      this.filename = (String)var1.readObject();
      this.position = var1.readInt();
   }

   public final void setFile(String var1) {
      this.filename = var1;
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

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.car);
      var1.writeObject(this.cdr);
      var1.writeObject(this.filename);
      var1.writeInt(this.position);
   }
}
