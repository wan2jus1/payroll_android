package com.google.appinventor.components.runtime;

import android.os.Handler;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.runtime.AlarmHandler;
import com.google.appinventor.components.runtime.Canvas;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Deleteable;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.OnDestroyListener;
import com.google.appinventor.components.runtime.VisibleComponent;
import com.google.appinventor.components.runtime.errors.AssertionFailure;
import com.google.appinventor.components.runtime.errors.IllegalArgumentError;
import com.google.appinventor.components.runtime.util.BoundingBox;
import com.google.appinventor.components.runtime.util.TimerInternal;
import java.util.HashSet;
import java.util.Set;

@SimpleObject
public abstract class Sprite extends VisibleComponent implements AlarmHandler, OnDestroyListener, Deleteable {

   private static final boolean DEFAULT_ENABLED = true;
   private static final int DEFAULT_HEADING = 0;
   private static final int DEFAULT_INTERVAL = 100;
   private static final float DEFAULT_SPEED = 0.0F;
   private static final boolean DEFAULT_VISIBLE = true;
   private static final double DEFAULT_Z = 1.0D;
   private static final String LOG_TAG = "Sprite";
   private final Handler androidUIHandler;
   protected final Canvas canvas;
   protected double heading;
   protected double headingCos;
   protected double headingRadians;
   protected double headingSin;
   protected boolean initialized;
   protected int interval;
   private final Set registeredCollisions;
   protected float speed;
   private final TimerInternal timerInternal;
   protected double userHeading;
   protected boolean visible;
   protected double xLeft;
   protected double yTop;
   protected double zLayer;


   protected Sprite(ComponentContainer var1) {
      this(var1, new Handler());
   }

   protected Sprite(ComponentContainer var1, Handler var2) {
      this.initialized = false;
      this.visible = true;
      this.androidUIHandler = var2;
      if(!(var1 instanceof Canvas)) {
         throw new IllegalArgumentError("Sprite constructor called with container " + var1);
      } else {
         this.canvas = (Canvas)var1;
         this.canvas.addSprite(this);
         this.registeredCollisions = new HashSet();
         this.timerInternal = new TimerInternal(this, true, 100, var2);
         this.Heading(0.0D);
         this.Enabled(true);
         this.Interval(100);
         this.Speed(0.0F);
         this.Visible(true);
         this.Z(1.0D);
         var1.$form().registerForOnDestroy(this);
      }
   }

   public static boolean colliding(Sprite var0, Sprite var1) {
      BoundingBox var6 = var0.getBoundingBox(1);
      if(var6.intersectDestructively(var1.getBoundingBox(1))) {
         for(double var2 = var6.getLeft(); var2 <= var6.getRight(); ++var2) {
            for(double var4 = var6.getTop(); var4 <= var6.getBottom(); ++var4) {
               if(var0.containsPoint(var2, var4) && var1.containsPoint(var2, var4)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   private final boolean overEastEdge(int var1) {
      return this.xLeft + (double)this.Width() > (double)var1;
   }

   private final boolean overNorthEdge() {
      return this.yTop < 0.0D;
   }

   private final boolean overSouthEdge(int var1) {
      return this.yTop + (double)this.Height() > (double)var1;
   }

   private final boolean overWestEdge() {
      return this.xLeft < 0.0D;
   }

   @SimpleFunction(
      description = "Makes this sprite bounce, as if off a wall.  For normal bouncing, the edge argument should be the one returned by EdgeReached."
   )
   public void Bounce(int var1) {
      this.MoveIntoBounds();
      double var4 = this.userHeading % 360.0D;
      double var2 = var4;
      if(var4 < 0.0D) {
         var2 = var4 + 360.0D;
      }

      if((var1 != 3 || var2 >= 90.0D && var2 <= 270.0D) && (var1 != -3 || var2 <= 90.0D || var2 >= 270.0D)) {
         if(var1 == 1 && var2 > 0.0D && var2 < 180.0D || var1 == -1 && var2 > 180.0D) {
            this.Heading(360.0D - var2);
            return;
         }

         if(var1 == 2 && var2 > 0.0D && var2 < 90.0D || var1 == -4 && var2 > 90.0D && var2 < 180.0D || var1 == -2 && var2 > 180.0D && var2 < 270.0D || var1 == 4 && var2 > 270.0D) {
            this.Heading(180.0D + var2);
            return;
         }
      } else {
         this.Heading(180.0D - var2);
      }

   }

   @SimpleEvent
   public void CollidedWith(Sprite var1) {
      if(this.registeredCollisions.contains(var1)) {
         Log.e("Sprite", "Collision between sprites " + this + " and " + var1 + " re-registered");
      } else {
         this.registeredCollisions.add(var1);
         this.postEvent(this, "CollidedWith", new Object[]{var1});
      }
   }

   @SimpleFunction
   public boolean CollidingWith(Sprite var1) {
      return this.registeredCollisions.contains(var1);
   }

   @SimpleEvent
   public void Dragged(float var1, float var2, float var3, float var4, float var5, float var6) {
      this.postEvent(this, "Dragged", new Object[]{Float.valueOf(var1), Float.valueOf(var2), Float.valueOf(var3), Float.valueOf(var4), Float.valueOf(var5), Float.valueOf(var6)});
   }

   @SimpleEvent(
      description = "Event handler called when the sprite reaches an edge of the screen. If Bounce is then called with that edge, the sprite will appear to bounce off of the edge it reached.  Edge here is represented as an integer that indicates one of eight directions north(1), northeast(2), east(3), southeast(4), south (-1), southwest(-2), west(-3), and northwest(-4)."
   )
   public void EdgeReached(int var1) {
      if(var1 != 0 && var1 >= -4 && var1 <= 4) {
         this.postEvent(this, "EdgeReached", new Object[]{Integer.valueOf(var1)});
      } else {
         throw new IllegalArgumentException("Illegal argument " + var1 + " to Sprite.EdgeReached()");
      }
   }

   @DesignerProperty(
      defaultValue = "True",
      editorType = "boolean"
   )
   @SimpleProperty
   public void Enabled(boolean var1) {
      this.timerInternal.Enabled(var1);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Controls whether the sprite moves when its speed is non-zero."
   )
   public boolean Enabled() {
      return this.timerInternal.Enabled();
   }

   @SimpleEvent
   public void Flung(float var1, float var2, float var3, float var4, float var5, float var6) {
      this.postEvent(this, "Flung", new Object[]{Float.valueOf(var1), Float.valueOf(var2), Float.valueOf(var3), Float.valueOf(var4), Float.valueOf(var5), Float.valueOf(var6)});
   }

   @SimpleProperty(
      description = "Returns the sprite\'s heading in degrees above the positive x-axis.  Zero degrees is toward the right of the screen; 90 degrees is toward the top of the screen."
   )
   public double Heading() {
      return this.userHeading;
   }

   @DesignerProperty(
      defaultValue = "0",
      editorType = "float"
   )
   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public void Heading(double var1) {
      this.userHeading = var1;
      this.heading = -var1;
      this.headingRadians = Math.toRadians(this.heading);
      this.headingCos = Math.cos(this.headingRadians);
      this.headingSin = Math.sin(this.headingRadians);
      this.registerChange();
   }

   public void Initialize() {
      this.initialized = true;
      this.canvas.registerChange(this);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The interval in milliseconds at which the sprite\'s position is updated.  For example, if the interval is 50 and the speed is 10, then the sprite will move 10 pixels every 50 milliseconds."
   )
   public int Interval() {
      return this.timerInternal.Interval();
   }

   @DesignerProperty(
      defaultValue = "100",
      editorType = "non_negative_integer"
   )
   @SimpleProperty
   public void Interval(int var1) {
      this.timerInternal.Interval(var1);
   }

   @SimpleFunction
   public void MoveIntoBounds() {
      this.moveIntoBounds(this.canvas.Width(), this.canvas.Height());
   }

   @SimpleFunction(
      description = "Moves the sprite so that its left top corner is at the specfied x and y coordinates."
   )
   public void MoveTo(double var1, double var3) {
      this.xLeft = var1;
      this.yTop = var3;
      this.registerChange();
   }

   @SimpleEvent(
      description = "Event indicating that a pair of sprites are no longer colliding."
   )
   public void NoLongerCollidingWith(Sprite var1) {
      if(!this.registeredCollisions.contains(var1)) {
         Log.e("Sprite", "Collision between sprites " + this + " and " + var1 + " removed but not present");
      }

      this.registeredCollisions.remove(var1);
      this.postEvent(this, "NoLongerCollidingWith", new Object[]{var1});
   }

   @SimpleFunction(
      description = "Turns the sprite to point towards the point with coordinates as (x, y)."
   )
   public void PointInDirection(double var1, double var3) {
      this.Heading(-Math.toDegrees(Math.atan2(var3 - this.Y() - (double)(this.Height() / 2), var1 - this.X() - (double)(this.Width() / 2))));
   }

   @SimpleFunction(
      description = "Turns the sprite to point towards a designated target sprite. The new heading will be parallel to the line joining the centerpoints of the two sprites."
   )
   public void PointTowards(Sprite var1) {
      this.Heading(-Math.toDegrees(Math.atan2(var1.Y() - this.Y() + (double)((var1.Height() - this.Height()) / 2), var1.X() - this.X() + (double)((var1.Width() - this.Width()) / 2))));
   }

   @SimpleProperty(
      description = "he speed at which the sprite moves.  The sprite moves this many pixels every interval."
   )
   public float Speed() {
      return this.speed;
   }

   @DesignerProperty(
      defaultValue = "0.0",
      editorType = "float"
   )
   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public void Speed(float var1) {
      this.speed = var1;
   }

   @SimpleEvent
   public void TouchDown(float var1, float var2) {
      this.postEvent(this, "TouchDown", new Object[]{Float.valueOf(var1), Float.valueOf(var2)});
   }

   @SimpleEvent
   public void TouchUp(float var1, float var2) {
      this.postEvent(this, "TouchUp", new Object[]{Float.valueOf(var1), Float.valueOf(var2)});
   }

   @SimpleEvent
   public void Touched(float var1, float var2) {
      this.postEvent(this, "Touched", new Object[]{Float.valueOf(var1), Float.valueOf(var2)});
   }

   @DesignerProperty(
      defaultValue = "True",
      editorType = "boolean"
   )
   @SimpleProperty
   public void Visible(boolean var1) {
      this.visible = var1;
      this.registerChange();
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "True if the sprite is visible."
   )
   public boolean Visible() {
      return this.visible;
   }

   @SimpleProperty(
      description = "The horizontal coordinate of the left edge of the sprite, increasing as the sprite moves to the right."
   )
   public double X() {
      return this.xLeft;
   }

   @DesignerProperty(
      defaultValue = "0.0",
      editorType = "float"
   )
   @SimpleProperty(
      category = PropertyCategory.APPEARANCE
   )
   public void X(double var1) {
      this.xLeft = var1;
      this.registerChange();
   }

   @SimpleProperty(
      description = "The vertical coordinate of the top of the sprite, increasing as the sprite moves down."
   )
   public double Y() {
      return this.yTop;
   }

   @DesignerProperty(
      defaultValue = "0.0",
      editorType = "float"
   )
   @SimpleProperty(
      category = PropertyCategory.APPEARANCE
   )
   public void Y(double var1) {
      this.yTop = var1;
      this.registerChange();
   }

   @SimpleProperty(
      description = "How the sprite should be layered relative to other sprits, with higher-numbered layers in front of lower-numbered layers."
   )
   public double Z() {
      return this.zLayer;
   }

   @DesignerProperty(
      defaultValue = "1.0",
      editorType = "float"
   )
   @SimpleProperty(
      category = PropertyCategory.APPEARANCE
   )
   public void Z(double var1) {
      this.zLayer = var1;
      this.canvas.changeSpriteLayer(this);
   }

   public void alarm() {
      if(this.initialized && this.speed != 0.0F) {
         this.updateCoordinates();
         this.registerChange();
      }

   }

   public boolean containsPoint(double var1, double var3) {
      return var1 >= this.xLeft && var1 < this.xLeft + (double)this.Width() && var3 >= this.yTop && var3 < this.yTop + (double)this.Height();
   }

   public BoundingBox getBoundingBox(int var1) {
      return new BoundingBox(this.X() - (double)var1, this.Y() - (double)var1, this.X() + (double)this.Width() - 1.0D + (double)var1, this.Y() + (double)this.Height() - 1.0D + (double)var1);
   }

   public HandlesEventDispatching getDispatchDelegate() {
      return this.canvas.$form();
   }

   protected int hitEdge() {
      return !this.canvas.ready()?0:this.hitEdge(this.canvas.Width(), this.canvas.Height());
   }

   protected int hitEdge(int var1, int var2) {
      boolean var3 = this.overWestEdge();
      boolean var4 = this.overNorthEdge();
      boolean var5 = this.overEastEdge(var1);
      boolean var6 = this.overSouthEdge(var2);
      if(!var4 && !var6 && !var5 && !var3) {
         return 0;
      } else {
         this.MoveIntoBounds();
         if(var3) {
            return var4?-4:(var6?-2:-3);
         } else if(var5) {
            return var4?2:(var6?4:3);
         } else if(var4) {
            return 1;
         } else if(var6) {
            return -1;
         } else {
            throw new AssertionFailure("Unreachable code hit in Sprite.hitEdge()");
         }
      }
   }

   public boolean intersectsWith(BoundingBox var1) {
      BoundingBox var6 = this.getBoundingBox(0);
      if(var6.intersectDestructively(var1)) {
         for(double var2 = var6.getLeft(); var2 < var6.getRight(); ++var2) {
            for(double var4 = var6.getTop(); var4 < var6.getBottom(); ++var4) {
               if(this.containsPoint(var2, var4)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   @SimpleFunction
   protected final void moveIntoBounds(int var1, int var2) {
      boolean var3 = false;
      if(this.Width() > var1) {
         if(this.xLeft != 0.0D) {
            this.xLeft = 0.0D;
            var3 = true;
         }
      } else if(this.overWestEdge()) {
         this.xLeft = 0.0D;
         var3 = true;
      } else if(this.overEastEdge(var1)) {
         this.xLeft = (double)(var1 - this.Width());
         var3 = true;
      }

      if(this.Height() > var2) {
         if(this.yTop != 0.0D) {
            this.yTop = 0.0D;
            var3 = true;
         }
      } else if(this.overNorthEdge()) {
         this.yTop = 0.0D;
         var3 = true;
      } else if(this.overSouthEdge(var2)) {
         this.yTop = (double)(var2 - this.Height());
         var3 = true;
      }

      if(var3) {
         this.registerChange();
      }

   }

   public void onDelete() {
      this.timerInternal.Enabled(false);
      this.canvas.removeSprite(this);
   }

   public void onDestroy() {
      this.timerInternal.Enabled(false);
   }

   protected abstract void onDraw(android.graphics.Canvas var1);

   protected void postEvent(final Sprite var1, final String var2, final Object ... var3) {
      this.androidUIHandler.post(new Runnable() {
         public void run() {
            EventDispatcher.dispatchEvent(var1, var2, var3);
         }
      });
   }

   protected void registerChange() {
      if(!this.initialized) {
         this.canvas.getView().invalidate();
      } else {
         int var1 = this.hitEdge();
         if(var1 != 0) {
            this.EdgeReached(var1);
         }

         this.canvas.registerChange(this);
      }
   }

   protected void updateCoordinates() {
      this.xLeft += (double)this.speed * this.headingCos;
      this.yTop += (double)this.speed * this.headingSin;
   }
}
