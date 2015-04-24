package gnu.kawa.models;

import gnu.kawa.models.Paintable;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class WithTransform implements Paintable {

   Paintable paintable;
   AffineTransform transform;


   public WithTransform(Paintable var1, AffineTransform var2) {
      this.paintable = var1;
      this.transform = var2;
   }

   public Rectangle2D getBounds2D() {
      return this.transform.createTransformedShape(this.paintable.getBounds2D()).getBounds2D();
   }

   public void paint(Graphics2D var1) {
      AffineTransform var2 = var1.getTransform();

      try {
         var1.transform(this.transform);
         this.paintable.paint(var1);
      } finally {
         var1.setTransform(var2);
      }

   }

   public Paintable transform(AffineTransform var1) {
      AffineTransform var2 = new AffineTransform(this.transform);
      var2.concatenate(var1);
      return new WithTransform(this.paintable, var2);
   }
}
