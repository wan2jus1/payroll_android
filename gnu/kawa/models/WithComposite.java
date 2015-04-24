package gnu.kawa.models;

import gnu.kawa.models.Paintable;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class WithComposite implements Paintable {

   Composite[] composite;
   Paintable[] paintable;


   public static WithComposite make(Paintable var0, Composite var1) {
      WithComposite var2 = new WithComposite();
      var2.paintable = new Paintable[]{var0};
      var2.composite = new Composite[]{var1};
      return var2;
   }

   public static WithComposite make(Paintable[] var0, Composite[] var1) {
      WithComposite var2 = new WithComposite();
      var2.paintable = var0;
      var2.composite = var1;
      return var2;
   }

   public static WithComposite make(Object[] var0) {
      int var6 = 0;
      int var5 = var0.length;

      while(true) {
         int var7 = var5 - 1;
         if(var7 < 0) {
            Paintable[] var2 = new Paintable[var6];
            Composite[] var3 = new Composite[var6];
            Composite var1 = null;
            var6 = 0;

            for(var5 = 0; var5 < var0.length; ++var5) {
               Object var4 = var0[var5];
               if(var4 instanceof Paintable) {
                  var2[var6] = (Paintable)var0[var5];
                  var3[var6] = var1;
                  ++var6;
               } else {
                  var1 = (Composite)var4;
               }
            }

            return make((Paintable[])var2, (Composite[])var3);
         }

         var5 = var7;
         if(var0[var7] instanceof Paintable) {
            ++var6;
            var5 = var7;
         }
      }
   }

   public Rectangle2D getBounds2D() {
      int var4 = this.paintable.length;
      Rectangle2D var2;
      if(var4 == 0) {
         var2 = null;
      } else {
         Rectangle2D var1 = this.paintable[0].getBounds2D();
         int var3 = 1;

         while(true) {
            var2 = var1;
            if(var3 >= var4) {
               break;
            }

            var1 = var1.createUnion(this.paintable[var3].getBounds2D());
            ++var3;
         }
      }

      return var2;
   }

   public void paint(Graphics2D param1) {
      // $FF: Couldn't be decompiled
   }

   public Paintable transform(AffineTransform var1) {
      int var4 = this.paintable.length;
      Paintable[] var2 = new Paintable[var4];

      for(int var3 = 0; var3 < var4; ++var3) {
         var2[var3] = this.paintable[var3].transform(var1);
      }

      return make((Paintable[])var2, (Composite[])this.composite);
   }
}
