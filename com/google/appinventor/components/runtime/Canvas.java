package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.MeasureSpec;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.Sprite;
import com.google.appinventor.components.runtime.util.BoundingBox;
import com.google.appinventor.components.runtime.util.FileUtil;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.PaintUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@DesignerComponent(
   category = ComponentCategory.ANIMATION,
   description = "<p>A two-dimensional touch-sensitive rectangular panel on which drawing can be done and sprites can be moved.</p> <p>The <code>BackgroundColor</code>, <code>PaintColor</code>, <code>BackgroundImage</code>, <code>Width</code>, and <code>Height</code> of the Canvas can be set in either the Designer or in the Blocks Editor.  The <code>Width</code> and <code>Height</code> are measured in pixels and must be positive.</p><p>Any location on the Canvas can be specified as a pair of (X, Y) values, where <ul> <li>X is the number of pixels away from the left edge of the Canvas</li><li>Y is the number of pixels away from the top edge of the Canvas</li></ul>.</p> <p>There are events to tell when and where a Canvas has been touched or a <code>Sprite</code> (<code>ImageSprite</code> or <code>Ball</code>) has been dragged.  There are also methods for drawing points, lines, and circles.</p>",
   version = 10
)
@SimpleObject
@UsesPermissions(
   permissionNames = "android.permission.INTERNET,android.permission.WRITE_EXTERNAL_STORAGE"
)
public final class Canvas extends AndroidViewComponent implements ComponentContainer {

   private static final int DEFAULT_BACKGROUND_COLOR = -1;
   private static final float DEFAULT_LINE_WIDTH = 2.0F;
   private static final int DEFAULT_PAINT_COLOR = -16777216;
   private static final int DEFAULT_TEXTALIGNMENT = 1;
   private static final int FLING_INTERVAL = 1000;
   private static final String LOG_TAG = "Canvas";
   private static final int MIN_WIDTH_HEIGHT = 1;
   private int backgroundColor;
   private String backgroundImagePath = "";
   private final Activity context;
   private boolean drawn;
   private final GestureDetector mGestureDetector;
   private final Canvas.MotionEventParser motionEventParser;
   private final Paint paint;
   private int paintColor;
   private final List sprites;
   private int textAlignment;
   private final Canvas.CanvasView view;


   public Canvas(ComponentContainer var1) {
      super(var1);
      this.context = var1.$context();
      this.view = new Canvas.CanvasView(this.context);
      var1.$add(this);
      this.paint = new Paint();
      this.paint.setStrokeWidth(2.0F);
      this.PaintColor(-16777216);
      this.BackgroundColor(-1);
      this.TextAlignment(1);
      this.FontSize(14.0F);
      this.sprites = new LinkedList();
      this.motionEventParser = new Canvas.MotionEventParser();
      this.mGestureDetector = new GestureDetector(this.context, new Canvas.FlingGestureListener());
   }

   private void changePaint(Paint var1, int var2) {
      if(var2 == 0) {
         PaintUtil.changePaint(var1, -16777216);
      } else if(var2 == 16777215) {
         PaintUtil.changePaintTransparent(var1);
      } else {
         PaintUtil.changePaint(var1, var2);
      }
   }

   private String saveFile(java.io.File param1, CompressFormat param2, String param3) {
      // $FF: Couldn't be decompiled
   }

   public void $add(AndroidViewComponent var1) {
      throw new UnsupportedOperationException("Canvas.$add() called");
   }

   public Activity $context() {
      return this.context;
   }

   public Form $form() {
      return this.container.$form();
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "The color of the canvas background."
   )
   public int BackgroundColor() {
      return this.backgroundColor;
   }

   @DesignerProperty(
      defaultValue = "&HFFFFFFFF",
      editorType = "color"
   )
   @SimpleProperty
   public void BackgroundColor(int var1) {
      this.view.setBackgroundColor(var1);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "The name of a file containing the background image for the canvas"
   )
   public String BackgroundImage() {
      return this.backgroundImagePath;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "asset"
   )
   @SimpleProperty
   public void BackgroundImage(String var1) {
      this.view.setBackgroundImage(var1);
   }

   @SimpleFunction(
      description = "Clears anything drawn on this Canvas but not any background color or image."
   )
   public void Clear() {
      this.view.clearDrawingLayer();
   }

   @SimpleEvent
   public void Dragged(float var1, float var2, float var3, float var4, float var5, float var6, boolean var7) {
      EventDispatcher.dispatchEvent(this, "Dragged", new Object[]{Float.valueOf(var1), Float.valueOf(var2), Float.valueOf(var3), Float.valueOf(var4), Float.valueOf(var5), Float.valueOf(var6), Boolean.valueOf(var7)});
   }

   @SimpleFunction
   public void DrawCircle(int var1, int var2, float var3, boolean var4) {
      Paint var6 = new Paint(this.paint);
      Style var5;
      if(var4) {
         var5 = Style.FILL;
      } else {
         var5 = Style.STROKE;
      }

      var6.setStyle(var5);
      this.view.canvas.drawCircle((float)var1, (float)var2, var3, var6);
      this.view.invalidate();
   }

   @SimpleFunction
   public void DrawLine(int var1, int var2, int var3, int var4) {
      this.view.canvas.drawLine((float)var1, (float)var2, (float)var3, (float)var4, this.paint);
      this.view.invalidate();
   }

   @SimpleFunction
   public void DrawPoint(int var1, int var2) {
      this.view.canvas.drawPoint((float)var1, (float)var2, this.paint);
      this.view.invalidate();
   }

   @SimpleFunction(
      description = "Draws the specified text relative to the specified coordinates using the values of the FontSize and TextAlignment properties."
   )
   public void DrawText(String var1, int var2, int var3) {
      this.view.canvas.drawText(var1, (float)var2, (float)var3, this.paint);
      this.view.invalidate();
   }

   @SimpleFunction(
      description = "Draws the specified text starting at the specified coordinates at the specified angle using the values of the FontSize and TextAlignment properties."
   )
   public void DrawTextAtAngle(String var1, int var2, int var3, float var4) {
      this.view.drawTextAtAngle(var1, var2, var3, var4);
   }

   @SimpleEvent
   public void Flung(float var1, float var2, float var3, float var4, float var5, float var6, boolean var7) {
      EventDispatcher.dispatchEvent(this, "Flung", new Object[]{Float.valueOf(var1), Float.valueOf(var2), Float.valueOf(var3), Float.valueOf(var4), Float.valueOf(var5), Float.valueOf(var6), Boolean.valueOf(var7)});
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "The font size of text drawn on the canvas."
   )
   public float FontSize() {
      return this.paint.getTextSize();
   }

   @DesignerProperty(
      defaultValue = "14.0",
      editorType = "non_negative_float"
   )
   @SimpleProperty
   public void FontSize(float var1) {
      this.paint.setTextSize(var1);
   }

   @SimpleFunction(
      description = "Gets the color of the specified point. This includes the background and any drawn points, lines, or circles but not sprites."
   )
   public int GetBackgroundPixelColor(int var1, int var2) {
      return this.view.getBackgroundPixelColor(var1, var2);
   }

   @SimpleFunction(
      description = "Gets the color of the specified point."
   )
   public int GetPixelColor(int var1, int var2) {
      return this.view.getPixelColor(var1, var2);
   }

   @SimpleProperty
   public void Height(int var1) {
      if(var1 <= 0 && var1 != -2 && var1 != -1) {
         this.container.$form().dispatchErrorOccurredEvent(this, "Height", 1003, new Object[0]);
      } else {
         super.Height(var1);
      }
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "The width of lines drawn on the canvas."
   )
   public float LineWidth() {
      return this.paint.getStrokeWidth();
   }

   @DesignerProperty(
      defaultValue = "2.0",
      editorType = "non_negative_float"
   )
   @SimpleProperty
   public void LineWidth(float var1) {
      this.paint.setStrokeWidth(var1);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "The color in which lines are drawn"
   )
   public int PaintColor() {
      return this.paintColor;
   }

   @DesignerProperty(
      defaultValue = "&HFF000000",
      editorType = "color"
   )
   @SimpleProperty
   public void PaintColor(int var1) {
      this.paintColor = var1;
      this.changePaint(this.paint, var1);
   }

   @SimpleFunction(
      description = "Saves a picture of this Canvas to the device\'s external storage. If an error occurs, the Screen\'s ErrorOccurred event will be called."
   )
   public String Save() {
      try {
         String var1 = this.saveFile(FileUtil.getPictureFile("png"), CompressFormat.PNG, "Save");
         return var1;
      } catch (IOException var2) {
         this.container.$form().dispatchErrorOccurredEvent(this, "Save", 708, new Object[]{var2.getMessage()});
      } catch (FileUtil.FileException var3) {
         this.container.$form().dispatchErrorOccurredEvent(this, "Save", var3.getErrorMessageNumber(), new Object[0]);
      }

      return "";
   }

   @SimpleFunction(
      description = "Saves a picture of this Canvas to the device\'s external storage in the file named fileName. fileName must end with one of .jpg, .jpeg, or .png, which determines the file type."
   )
   public String SaveAs(String var1) {
      CompressFormat var2;
      if(!var1.endsWith(".jpg") && !var1.endsWith(".jpeg")) {
         if(var1.endsWith(".png")) {
            var2 = CompressFormat.PNG;
         } else {
            if(var1.contains(".")) {
               this.container.$form().dispatchErrorOccurredEvent(this, "SaveAs", 706, new Object[0]);
               return "";
            }

            var1 = var1 + ".png";
            var2 = CompressFormat.PNG;
         }
      } else {
         var2 = CompressFormat.JPEG;
      }

      try {
         var1 = this.saveFile(FileUtil.getExternalFile(var1), var2, "SaveAs");
         return var1;
      } catch (IOException var3) {
         this.container.$form().dispatchErrorOccurredEvent(this, "SaveAs", 708, new Object[]{var3.getMessage()});
      } catch (FileUtil.FileException var4) {
         this.container.$form().dispatchErrorOccurredEvent(this, "SaveAs", var4.getErrorMessageNumber(), new Object[0]);
      }

      return "";
   }

   @SimpleFunction(
      description = "Sets the color of the specified point. This differs from DrawPoint by having an argument for color."
   )
   public void SetBackgroundPixelColor(int var1, int var2, int var3) {
      Paint var4 = new Paint();
      PaintUtil.changePaint(var4, var3);
      this.view.canvas.drawPoint((float)var1, (float)var2, var4);
      this.view.invalidate();
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Determines the alignment of the text drawn by DrawText() or DrawAngle() with respect to the point specified by that command.",
      userVisible = true
   )
   public int TextAlignment() {
      return this.textAlignment;
   }

   @DesignerProperty(
      defaultValue = "1",
      editorType = "textalignment"
   )
   @SimpleProperty(
      userVisible = true
   )
   public void TextAlignment(int var1) {
      this.textAlignment = var1;
      switch(var1) {
      case 0:
         this.paint.setTextAlign(Align.LEFT);
         return;
      case 1:
         this.paint.setTextAlign(Align.CENTER);
         return;
      case 2:
         this.paint.setTextAlign(Align.RIGHT);
         return;
      default:
      }
   }

   @SimpleEvent
   public void TouchDown(float var1, float var2) {
      EventDispatcher.dispatchEvent(this, "TouchDown", new Object[]{Float.valueOf(var1), Float.valueOf(var2)});
   }

   @SimpleEvent
   public void TouchUp(float var1, float var2) {
      EventDispatcher.dispatchEvent(this, "TouchUp", new Object[]{Float.valueOf(var1), Float.valueOf(var2)});
   }

   @SimpleEvent
   public void Touched(float var1, float var2, boolean var3) {
      EventDispatcher.dispatchEvent(this, "Touched", new Object[]{Float.valueOf(var1), Float.valueOf(var2), Boolean.valueOf(var3)});
   }

   @SimpleProperty
   public void Width(int var1) {
      if(var1 <= 0 && var1 != -2 && var1 != -1) {
         this.container.$form().dispatchErrorOccurredEvent(this, "Width", 1002, new Object[0]);
      } else {
         super.Width(var1);
      }
   }

   void addSprite(Sprite var1) {
      for(int var2 = 0; var2 < this.sprites.size(); ++var2) {
         if(((Sprite)this.sprites.get(var2)).Z() > var1.Z()) {
            this.sprites.add(var2, var1);
            return;
         }
      }

      this.sprites.add(var1);
   }

   void changeSpriteLayer(Sprite var1) {
      this.removeSprite(var1);
      this.addSprite(var1);
      this.view.invalidate();
   }

   protected void findSpriteCollisions(Sprite var1) {
      Iterator var2 = this.sprites.iterator();

      while(var2.hasNext()) {
         Sprite var3 = (Sprite)var2.next();
         if(var3 != var1) {
            if(var1.CollidingWith(var3)) {
               if(!var1.Visible() || !var1.Enabled() || !var3.Visible() || !var3.Enabled() || !Sprite.colliding(var3, var1)) {
                  var1.NoLongerCollidingWith(var3);
                  var3.NoLongerCollidingWith(var1);
               }
            } else if(var1.Visible() && var1.Enabled() && var3.Visible() && var3.Enabled() && Sprite.colliding(var3, var1)) {
               var1.CollidedWith(var3);
               var3.CollidedWith(var1);
            }
         }
      }

   }

   public View getView() {
      return this.view;
   }

   public boolean ready() {
      return this.drawn;
   }

   void registerChange(Sprite var1) {
      this.view.invalidate();
      this.findSpriteCollisions(var1);
   }

   void removeSprite(Sprite var1) {
      this.sprites.remove(var1);
   }

   public void setChildHeight(AndroidViewComponent var1, int var2) {
      throw new UnsupportedOperationException("Canvas.setChildHeight() called");
   }

   public void setChildWidth(AndroidViewComponent var1, int var2) {
      throw new UnsupportedOperationException("Canvas.setChildWidth() called");
   }

   private final class CanvasView extends View {

      private BitmapDrawable backgroundDrawable;
      private Bitmap bitmap;
      private android.graphics.Canvas canvas;
      private Bitmap completeCache;
      private Bitmap scaledBackgroundBitmap;


      public CanvasView(Context var2) {
         super(var2);
         this.bitmap = Bitmap.createBitmap(32, 48, Config.ARGB_8888);
         this.canvas = new android.graphics.Canvas(this.bitmap);
      }

      // $FF: synthetic method
      static Bitmap access$1200(Canvas.CanvasView var0) {
         return var0.completeCache;
      }

      // $FF: synthetic method
      static Bitmap access$1300(Canvas.CanvasView var0) {
         return var0.buildCache();
      }

      private Bitmap buildCache() {
         this.setDrawingCacheEnabled(true);
         this.destroyDrawingCache();
         Bitmap var2 = this.getDrawingCache();
         Bitmap var1 = var2;
         if(var2 == null) {
            int var3 = this.getWidth();
            int var4 = this.getHeight();
            var1 = Bitmap.createBitmap(var3, var4, Config.ARGB_8888);
            android.graphics.Canvas var5 = new android.graphics.Canvas(var1);
            this.layout(0, 0, var3, var4);
            this.draw(var5);
         }

         return var1;
      }

      private void clearDrawingLayer() {
         this.canvas.drawColor(0, Mode.CLEAR);
         this.invalidate();
      }

      private void drawTextAtAngle(String var1, int var2, int var3, float var4) {
         this.canvas.save();
         this.canvas.rotate(-var4, (float)var2, (float)var3);
         this.canvas.drawText(var1, (float)var2, (float)var3, Canvas.this.paint);
         this.canvas.restore();
         this.invalidate();
      }

      private int getBackgroundPixelColor(int param1, int param2) {
         // $FF: Couldn't be decompiled
      }

      private int getPixelColor(int var1, int var2) {
         if(var1 >= 0 && var1 < this.bitmap.getWidth() && var2 >= 0 && var2 < this.bitmap.getHeight()) {
            if(this.completeCache == null) {
               boolean var5 = false;
               Iterator var3 = Canvas.this.sprites.iterator();

               boolean var4;
               while(true) {
                  var4 = var5;
                  if(!var3.hasNext()) {
                     break;
                  }

                  if(((Sprite)var3.next()).Visible()) {
                     var4 = true;
                     break;
                  }
               }

               if(!var4) {
                  return this.getBackgroundPixelColor(var1, var2);
               }

               this.completeCache = this.buildCache();
            }

            try {
               var1 = this.completeCache.getPixel(var1, var2);
               return var1;
            } catch (IllegalArgumentException var6) {
               Log.e("Canvas", String.format("Returning COLOR_NONE (exception) from getPixelColor.", new Object[0]));
               return 16777215;
            }
         } else {
            return 16777215;
         }
      }

      private int getSize(int var1, int var2) {
         int var4 = MeasureSpec.getMode(var1);
         int var3 = MeasureSpec.getSize(var1);
         if(var4 == 1073741824) {
            var1 = var3;
         } else {
            var1 = var2;
            if(var4 == Integer.MIN_VALUE) {
               return Math.min(var2, var3);
            }
         }

         return var1;
      }

      public void onDraw(android.graphics.Canvas var1) {
         this.completeCache = null;
         super.onDraw(var1);
         var1.drawBitmap(this.bitmap, 0.0F, 0.0F, (Paint)null);
         Iterator var2 = Canvas.this.sprites.iterator();

         while(var2.hasNext()) {
            ((Sprite)var2.next()).onDraw(var1);
         }

         Canvas.this.drawn = true;
      }

      protected void onMeasure(int var1, int var2) {
         int var4;
         int var5;
         if(this.backgroundDrawable != null) {
            Bitmap var3 = this.backgroundDrawable.getBitmap();
            var5 = var3.getWidth();
            var4 = var3.getHeight();
         } else {
            var5 = 32;
            var4 = 48;
         }

         this.setMeasuredDimension(this.getSize(var1, var5), this.getSize(var2, var4));
      }

      protected void onSizeChanged(int var1, int var2, int var3, int var4) {
         var3 = this.bitmap.getWidth();
         var4 = this.bitmap.getHeight();
         if(var1 != var3 || var2 != var4) {
            Bitmap var5 = this.bitmap;

            try {
               Bitmap var6 = Bitmap.createScaledBitmap(var5, var1, var2, false);
               if(var6.isMutable()) {
                  this.bitmap = var6;
                  this.canvas = new android.graphics.Canvas(this.bitmap);
               } else {
                  this.bitmap = Bitmap.createBitmap(var1, var2, Config.ARGB_8888);
                  this.canvas = new android.graphics.Canvas(this.bitmap);
                  Rect var9 = new Rect(0, 0, var3, var4);
                  RectF var7 = new RectF(0.0F, 0.0F, (float)var1, (float)var2);
                  this.canvas.drawBitmap(var5, var9, var7, (Paint)null);
               }
            } catch (IllegalArgumentException var8) {
               Log.e("Canvas", "Bad values to createScaledBimap w = " + var1 + ", h = " + var2);
            }

            this.scaledBackgroundBitmap = null;
         }

      }

      public boolean onTouchEvent(MotionEvent var1) {
         Canvas.this.container.$form().dontGrabTouchEventsForComponent();
         Canvas.this.motionEventParser.parse(var1);
         Canvas.this.mGestureDetector.onTouchEvent(var1);
         return true;
      }

      public void setBackgroundColor(int var1) {
         Canvas.this.backgroundColor = var1;
         if(this.backgroundDrawable == null) {
            super.setBackgroundColor(var1);
         }

         this.clearDrawingLayer();
      }

      void setBackgroundImage(String var1) {
         Canvas var3 = Canvas.this;
         String var2 = var1;
         if(var1 == null) {
            var2 = "";
         }

         var3.backgroundImagePath = var2;
         this.backgroundDrawable = null;
         this.scaledBackgroundBitmap = null;
         if(!TextUtils.isEmpty(Canvas.this.backgroundImagePath)) {
            try {
               this.backgroundDrawable = MediaUtil.getBitmapDrawable(Canvas.this.container.$form(), Canvas.this.backgroundImagePath);
            } catch (IOException var4) {
               Log.e("Canvas", "Unable to load " + Canvas.this.backgroundImagePath);
            }
         }

         this.setBackgroundDrawable(this.backgroundDrawable);
         if(this.backgroundDrawable == null) {
            super.setBackgroundColor(Canvas.this.backgroundColor);
         }

         this.clearDrawingLayer();
      }
   }

   class FlingGestureListener extends SimpleOnGestureListener {

      public boolean onFling(MotionEvent var1, MotionEvent var2, float var3, float var4) {
         float var5 = (float)Math.max(0, (int)var1.getX());
         float var6 = (float)Math.max(0, (int)var1.getY());
         var3 /= 1000.0F;
         var4 /= 1000.0F;
         float var7 = (float)Math.sqrt((double)(var3 * var3 + var4 * var4));
         float var8 = (float)(-Math.toDegrees(Math.atan2((double)var4, (double)var3)));
         int var10 = Canvas.this.Width();
         int var11 = Canvas.this.Height();
         BoundingBox var13 = new BoundingBox((double)Math.max(0, (int)var5 - 12), (double)Math.max(0, (int)var6 - 12), (double)Math.min(var10 - 1, (int)var5 + 12), (double)Math.min(var11 - 1, (int)var6 + 12));
         boolean var12 = false;
         Iterator var14 = Canvas.this.sprites.iterator();

         while(var14.hasNext()) {
            Sprite var9 = (Sprite)var14.next();
            if(var9.Enabled() && var9.Visible() && var9.intersectsWith(var13)) {
               var9.Flung(var5, var6, var7, var8, var3, var4);
               var12 = true;
            }
         }

         Canvas.this.Flung(var5, var6, var7, var8, var3, var4, var12);
         return true;
      }
   }

   class MotionEventParser {

      public static final int FINGER_HEIGHT = 24;
      public static final int FINGER_WIDTH = 24;
      private static final int HALF_FINGER_HEIGHT = 12;
      private static final int HALF_FINGER_WIDTH = 12;
      public static final int TAP_THRESHOLD = 30;
      private static final int UNSET = -1;
      private boolean drag = false;
      private final List draggedSprites = new ArrayList();
      private boolean isDrag = false;
      private float lastX = -1.0F;
      private float lastY = -1.0F;
      private float startX = -1.0F;
      private float startY = -1.0F;


      void parse(MotionEvent var1) {
         int var6 = Canvas.this.Width();
         int var7 = Canvas.this.Height();
         float var2 = (float)Math.max(0, (int)var1.getX());
         float var3 = (float)Math.max(0, (int)var1.getY());
         BoundingBox var4 = new BoundingBox((double)Math.max(0, (int)var2 - 12), (double)Math.max(0, (int)var3 - 12), (double)Math.min(var6 - 1, (int)var2 + 12), (double)Math.min(var7 - 1, (int)var3 + 12));
         Sprite var5;
         boolean var8;
         Iterator var9;
         Sprite var10;
         switch(var1.getAction()) {
         case 0:
            this.draggedSprites.clear();
            this.startX = var2;
            this.startY = var3;
            this.lastX = var2;
            this.lastY = var3;
            this.drag = false;
            this.isDrag = false;
            var9 = Canvas.this.sprites.iterator();

            while(var9.hasNext()) {
               var5 = (Sprite)var9.next();
               if(var5.Enabled() && var5.Visible() && var5.intersectsWith(var4)) {
                  this.draggedSprites.add(var5);
                  var5.TouchDown(this.startX, this.startY);
               }
            }

            Canvas.this.TouchDown(this.startX, this.startY);
            return;
         case 1:
            if(!this.drag) {
               var8 = false;
               var9 = this.draggedSprites.iterator();

               while(var9.hasNext()) {
                  var10 = (Sprite)var9.next();
                  if(var10.Enabled() && var10.Visible()) {
                     var10.Touched(var2, var3);
                     var10.TouchUp(var2, var3);
                     var8 = true;
                  }
               }

               Canvas.this.Touched(var2, var3, var8);
            } else {
               var9 = this.draggedSprites.iterator();

               while(var9.hasNext()) {
                  var10 = (Sprite)var9.next();
                  if(var10.Enabled() && var10.Visible()) {
                     var10.Touched(var2, var3);
                     var10.TouchUp(var2, var3);
                  }
               }
            }

            Canvas.this.TouchUp(var2, var3);
            this.drag = false;
            this.startX = -1.0F;
            this.startY = -1.0F;
            this.lastX = -1.0F;
            this.lastY = -1.0F;
            return;
         case 2:
            if(this.startX == -1.0F || this.startY == -1.0F || this.lastX == -1.0F || this.lastY == -1.0F) {
               Log.w("Canvas", "In Canvas.MotionEventParser.parse(), an ACTION_MOVE was passed without a preceding ACTION_DOWN: " + var1);
            }

            if(this.isDrag || Math.abs(var2 - this.startX) >= 30.0F || Math.abs(var3 - this.startY) >= 30.0F) {
               this.isDrag = true;
               this.drag = true;
               var9 = Canvas.this.sprites.iterator();

               while(var9.hasNext()) {
                  var5 = (Sprite)var9.next();
                  if(!this.draggedSprites.contains(var5) && var5.Enabled() && var5.Visible() && var5.intersectsWith(var4)) {
                     this.draggedSprites.add(var5);
                  }
               }

               var8 = false;
               var9 = this.draggedSprites.iterator();

               while(var9.hasNext()) {
                  var10 = (Sprite)var9.next();
                  if(var10.Enabled() && var10.Visible()) {
                     var10.Dragged(this.startX, this.startY, this.lastX, this.lastY, var2, var3);
                     var8 = true;
                  }
               }

               Canvas.this.Dragged(this.startX, this.startY, this.lastX, this.lastY, var2, var3, var8);
               this.lastX = var2;
               this.lastY = var3;
               return;
            }
         default:
         }
      }
   }
}
