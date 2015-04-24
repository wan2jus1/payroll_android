package gnu.lists;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class UnescapedData implements CharSequence, Externalizable {

   String data;


   public UnescapedData() {
   }

   public UnescapedData(String var1) {
      this.data = var1;
   }

   public char charAt(int var1) {
      return this.data.charAt(var1);
   }

   public final boolean equals(Object var1) {
      return var1 instanceof UnescapedData && this.data.equals(var1.toString());
   }

   public final String getData() {
      return this.data;
   }

   public final int hashCode() {
      return this.data == null?0:this.data.hashCode();
   }

   public int length() {
      return this.data.length();
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.data = (String)var1.readObject();
   }

   public CharSequence subSequence(int var1, int var2) {
      return new UnescapedData(this.data.substring(var1, var2));
   }

   public final String toString() {
      return this.data;
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.data);
   }
}
