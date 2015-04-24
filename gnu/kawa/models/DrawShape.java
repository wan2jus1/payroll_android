package gnu.kawa.models;

import gnu.kawa.models.Paintable;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class DrawShape implements Paintable {

   Shape shape;


   public DrawShape(Shape var1) {
      this.shape = var1;
   }

   public Rectangle2D getBounds2D() {
      return this.shape.getBounds2D();
   }

   public void paint(Graphics2D var1) {
      var1.draw(this.shape);
   }

   public Paintable transform(AffineTransform var1) {
      return new DrawShape(var1.createTransformedShape(this.shape));
   }
}
