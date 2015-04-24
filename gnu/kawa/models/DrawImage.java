package gnu.kawa.models;

import gnu.kawa.models.Display;
import gnu.kawa.models.Model;
import gnu.kawa.models.Paintable;
import gnu.kawa.models.WithTransform;
import gnu.mapping.WrappedException;
import gnu.text.Path;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.Serializable;
import javax.imageio.ImageIO;

public class DrawImage extends Model implements Paintable, Serializable {

   String description;
   BufferedImage image;
   Path src;


   public DrawImage() {
   }

   public DrawImage(BufferedImage var1) {
      this.image = var1;
   }

   public Rectangle2D getBounds2D() {
      this.loadImage();
      int var1 = this.image.getWidth();
      int var2 = this.image.getHeight();
      return new Float(0.0F, 0.0F, (float)var1, (float)var2);
   }

   public Image getImage() {
      this.loadImage();
      return this.image;
   }

   public Path getSrc() {
      return this.src;
   }

   void loadImage() {
      if(this.image == null) {
         try {
            this.image = ImageIO.read(this.src.openInputStream());
         } catch (Throwable var2) {
            throw WrappedException.wrapIfNeeded(var2);
         }
      }

   }

   public void makeView(Display var1, Object var2) {
      var1.addImage(this, var2);
   }

   public void paint(Graphics2D var1) {
      this.loadImage();
      var1.drawImage(this.image, (AffineTransform)null, (ImageObserver)null);
   }

   public void setSrc(Path var1) {
      this.src = var1;
   }

   public Paintable transform(AffineTransform var1) {
      return new WithTransform(this, var1);
   }
}
