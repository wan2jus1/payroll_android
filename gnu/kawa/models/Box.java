package gnu.kawa.models;

import gnu.kawa.models.Display;
import gnu.kawa.models.Model;
import gnu.kawa.models.Spacer;
import gnu.kawa.models.Viewable;
import gnu.math.IntNum;
import java.awt.Dimension;
import java.io.Serializable;

public abstract class Box extends Model implements Viewable, Serializable {

   Viewable cellSpacing;
   Viewable[] components;
   int numComponents;


   public void add(Viewable var1) {
      Viewable[] var2 = this.components;
      int var3 = this.numComponents;
      if(var3 == 0) {
         this.components = new Viewable[4];
      } else if(var2.length <= var3) {
         this.components = new Viewable[var3 * 2];
         System.arraycopy(var2, 0, this.components, 0, var3);
         var2 = this.components;
      }

      this.components[var3] = var1;
      this.numComponents = var3 + 1;
   }

   public abstract int getAxis();

   public Viewable getCellSpacing() {
      return this.cellSpacing;
   }

   public final Viewable getComponent(int var1) {
      return this.components[var1];
   }

   public final int getComponentCount() {
      return this.numComponents;
   }

   public void makeView(Display var1, Object var2) {
      var1.addBox(this, var2);
   }

   public void setCellSpacing(Object var1) {
      if(!(var1 instanceof IntNum) && !(var1 instanceof Integer)) {
         this.cellSpacing = (Viewable)var1;
      } else {
         int var2 = ((Number)var1).intValue();
         Dimension var3;
         if(this.getAxis() == 0) {
            var3 = new Dimension(var2, 0);
         } else {
            var3 = new Dimension(0, var2);
         }

         this.cellSpacing = Spacer.rigidArea(var3);
      }
   }
}
