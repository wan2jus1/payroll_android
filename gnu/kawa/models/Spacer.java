package gnu.kawa.models;

import gnu.kawa.models.Display;
import gnu.kawa.models.Model;
import gnu.kawa.models.Viewable;
import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.io.Serializable;

public class Spacer extends Model implements Viewable, Serializable {

   Dimension2D maxSize;
   Dimension2D minSize;
   Dimension2D preferredSize;


   public static Spacer rigidArea(int var0, int var1) {
      return rigidArea(new Dimension(var0, var1));
   }

   public static Spacer rigidArea(Dimension2D var0) {
      Spacer var1 = new Spacer();
      var1.minSize = var0;
      var1.maxSize = var0;
      var1.preferredSize = var0;
      return var1;
   }

   public Dimension getMaximumSize() {
      return Display.asDimension(this.maxSize);
   }

   public Dimension2D getMaximumSize2D() {
      return this.maxSize;
   }

   public Dimension getMinimumSize() {
      return Display.asDimension(this.minSize);
   }

   public Dimension2D getMinimumSize2D() {
      return this.minSize;
   }

   public Dimension getPreferredSize() {
      return Display.asDimension(this.preferredSize);
   }

   public Dimension2D getPreferredSize2D() {
      return this.preferredSize;
   }

   public boolean isRigid() {
      return this.minSize == this.maxSize || this.minSize != null && this.maxSize != null && this.minSize.getWidth() == this.maxSize.getWidth() && this.minSize.getHeight() == this.maxSize.getHeight();
   }

   public void makeView(Display var1, Object var2) {
      var1.addSpacer(this, var2);
   }
}
