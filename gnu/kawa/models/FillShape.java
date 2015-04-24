package gnu.kawa.models;

import gnu.kawa.models.Paintable;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class FillShape implements Paintable {

   Shape shape;


   public FillShape(Shape var1) {
      this.shape = var1;
   }

   public Rectangle2D getBounds2D() {
      return this.shape.getBounds2D();
   }

   public void paint(Graphics2D var1) {
      var1.fill(this.shape);
   }

   public Paintable transform(AffineTransform var1) {
      return new FillShape(var1.createTransformedShape(this.shape));
   }
}
