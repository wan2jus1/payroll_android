package gnu.kawa.models;

import gnu.kawa.models.Paintable;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class WithPaint implements Paintable {

   Paint paint;
   Paintable paintable;


   public WithPaint(Paintable var1, Paint var2) {
      this.paintable = var1;
      this.paint = var2;
   }

   public Rectangle2D getBounds2D() {
      return this.paintable.getBounds2D();
   }

   public void paint(Graphics2D var1) {
      Paint var2 = var1.getPaint();

      try {
         var1.setPaint(this.paint);
         this.paintable.paint(var1);
      } finally {
         var1.setPaint(var2);
      }

   }

   public Paintable transform(AffineTransform var1) {
      return new WithPaint(this.paintable.transform(var1), this.paint);
   }
}
