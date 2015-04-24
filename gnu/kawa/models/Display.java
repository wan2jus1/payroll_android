package gnu.kawa.models;

import gnu.kawa.models.Box;
import gnu.kawa.models.Button;
import gnu.kawa.models.DrawImage;
import gnu.kawa.models.Label;
import gnu.kawa.models.Model;
import gnu.kawa.models.Spacer;
import gnu.kawa.models.Text;
import gnu.kawa.models.Window;
import gnu.lists.FString;
import gnu.mapping.ThreadLocation;
import gnu.mapping.WrappedException;
import java.awt.Dimension;
import java.awt.geom.Dimension2D;

public abstract class Display {

   public static ThreadLocation myDisplay = new ThreadLocation("my-display");


   public static Dimension asDimension(Dimension2D var0) {
      return !(var0 instanceof Dimension) && var0 != null?new Dimension((int)(var0.getWidth() + 0.5D), (int)(var0.getHeight() + 0.5D)):(Dimension)var0;
   }

   public static Display getInstance() {
      Object var3 = myDisplay.get((Object)null);
      if(var3 instanceof Display) {
         return (Display)var3;
      } else {
         String var0;
         if(var3 == null) {
            var0 = "swing";
         } else {
            var0 = var3.toString();
         }

         while(true) {
            int var4 = var0.indexOf(44);
            String var1 = null;
            String var2 = var0;
            if(var4 >= 0) {
               var1 = var0.substring(var4 + 1);
               var2 = var0.substring(0, var4);
            }

            if(var2.equals("swing")) {
               var0 = "gnu.kawa.swingviews.SwingDisplay";
            } else if(var2.equals("swt")) {
               var0 = "gnu.kawa.swtviews.SwtDisplay";
            } else {
               var0 = var2;
               if(var2.equals("echo2")) {
                  var0 = "gnu.kawa.echo2.Echo2Display";
               }
            }

            try {
               Display var7 = (Display)Class.forName(var0).getDeclaredMethod("getInstance", new Class[0]).invoke((Object)null, new Object[0]);
               return var7;
            } catch (ClassNotFoundException var5) {
               if(var1 == null) {
                  throw new RuntimeException("no display toolkit: " + var3);
               }

               var0 = var1;
            } catch (Throwable var6) {
               throw WrappedException.wrapIfNeeded(var6);
            }
         }
      }
   }

   public abstract void addBox(Box var1, Object var2);

   public abstract void addButton(Button var1, Object var2);

   public abstract void addImage(DrawImage var1, Object var2);

   public abstract void addLabel(Label var1, Object var2);

   public void addSpacer(Spacer var1, Object var2) {
      throw new Error("makeView called on Spacer");
   }

   public void addText(Text var1, Object var2) {
      throw new Error("makeView called on Text");
   }

   public abstract void addView(Object var1, Object var2);

   public Model coerceToModel(Object var1) {
      return (Model)(!(var1 instanceof FString) && !(var1 instanceof String)?(Model)var1:new Label(var1.toString()));
   }

   public abstract Window makeWindow();
}
