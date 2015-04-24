package gnu.kawa.models;

import gnu.kawa.models.Display;
import gnu.kawa.models.Model;
import gnu.kawa.models.Viewable;
import java.io.Serializable;

public class Label extends Model implements Viewable, Serializable {

   String text;


   public Label() {
   }

   public Label(String var1) {
      this.text = var1;
   }

   public String getText() {
      return this.text;
   }

   public void makeView(Display var1, Object var2) {
      var1.addLabel(this, var2);
   }

   public void setText(String var1) {
      this.text = var1;
      this.notifyListeners("text");
   }
}
