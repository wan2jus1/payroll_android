package gnu.kawa.models;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public interface Paintable {

   Rectangle2D getBounds2D();

   void paint(Graphics2D var1);

   Paintable transform(AffineTransform var1);
}
