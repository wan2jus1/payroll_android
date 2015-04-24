package gnu.kawa.models;

import gnu.kawa.models.Display;
import gnu.kawa.models.Model;
import gnu.kawa.models.Viewable;
import gnu.lists.CharBuffer;
import java.io.Serializable;

public class Text extends Model implements Viewable, Serializable {

   public final CharBuffer buffer;


   public Text() {
      this("");
   }

   public Text(String var1) {
      this.buffer = new CharBuffer(100);
      this.buffer.gapEnd = 99;
      this.buffer.getArray()[this.buffer.gapEnd] = 10;
      this.setText(var1);
   }

   public CharBuffer getBuffer() {
      return this.buffer;
   }

   public String getText() {
      int var1 = this.buffer.size() - 1;
      int var2 = this.buffer.getSegment(0, var1);
      return new String(this.buffer.getArray(), var2, var1);
   }

   public void makeView(Display var1, Object var2) {
      var1.addText(this, var2);
   }

   public void setText(String var1) {
      int var2 = this.buffer.size() - 1;
      if(var2 > 0) {
         this.buffer.delete(0, var2);
      }

      this.buffer.insert(0, var1, false);
      this.notifyListeners("text");
   }
}
