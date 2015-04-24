package gnu.kawa.models;

import gnu.kawa.models.Display;
import gnu.kawa.models.Model;
import java.awt.Color;

public class Button extends Model {

   Object action;
   Color background;
   boolean disabled;
   Color foreground;
   String text;
   Object width;


   public Object getAction() {
      return this.action;
   }

   public Color getBackground() {
      return this.background;
   }

   public Color getForeground() {
      return this.foreground;
   }

   public String getText() {
      return this.text;
   }

   public boolean isDisabled() {
      return this.disabled;
   }

   public void makeView(Display var1, Object var2) {
      var1.addButton(this, var2);
   }

   public void setAction(Object var1) {
      this.action = var1;
   }

   public void setBackground(Color var1) {
      this.background = var1;
      this.notifyListeners("background");
   }

   public void setDisabled(boolean var1) {
      this.disabled = var1;
   }

   public void setForeground(Color var1) {
      this.foreground = var1;
      this.notifyListeners("foreground");
   }

   public void setText(String var1) {
      this.text = var1;
      this.notifyListeners("text");
   }
}
