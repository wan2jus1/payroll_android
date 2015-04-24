package com.google.appinventor.components.runtime;

import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.TextViewUtil;
import com.google.appinventor.components.runtime.util.ViewUtil;
import java.io.IOException;

@SimpleObject
@UsesPermissions(
   permissionNames = "android.permission.INTERNET"
)
public abstract class ButtonBase extends AndroidViewComponent implements OnClickListener, OnFocusChangeListener, OnLongClickListener, OnTouchListener {

   private static final String LOG_TAG = "ButtonBase";
   private static final float[] ROUNDED_CORNERS_ARRAY = new float[]{10.0F, 10.0F, 10.0F, 10.0F, 10.0F, 10.0F, 10.0F, 10.0F};
   private static final float ROUNDED_CORNERS_RADIUS = 10.0F;
   private static final int SHAPED_DEFAULT_BACKGROUND_COLOR = -3355444;
   private int backgroundColor;
   private Drawable backgroundImageDrawable;
   private boolean bold;
   private Drawable defaultButtonDrawable;
   private ColorStateList defaultColorStateList;
   private int fontTypeface;
   private String imagePath = "";
   private boolean italic;
   private int shape;
   private boolean showFeedback = true;
   private int textAlignment;
   private int textColor;
   private final android.widget.Button view;


   public ButtonBase(ComponentContainer var1) {
      super(var1);
      this.view = new android.widget.Button(var1.$context());
      this.defaultButtonDrawable = this.view.getBackground();
      this.defaultColorStateList = this.view.getTextColors();
      var1.$add(this);
      this.view.setOnClickListener(this);
      this.view.setOnFocusChangeListener(this);
      this.view.setOnLongClickListener(this);
      this.view.setOnTouchListener(this);
      this.TextAlignment(1);
      this.BackgroundColor(0);
      this.Image("");
      this.Enabled(true);
      this.fontTypeface = 0;
      TextViewUtil.setFontTypeface(this.view, this.fontTypeface, this.bold, this.italic);
      this.FontSize(14.0F);
      this.Text("");
      this.TextColor(0);
      this.Shape(0);
   }

   private void setShape() {
      ShapeDrawable var1 = new ShapeDrawable();
      Paint var2 = var1.getPaint();
      int var3;
      if(this.backgroundColor == 0) {
         var3 = -3355444;
      } else {
         var3 = this.backgroundColor;
      }

      var2.setColor(var3);
      switch(this.shape) {
      case 1:
         var1.setShape(new RoundRectShape(ROUNDED_CORNERS_ARRAY, (RectF)null, (float[])null));
         break;
      case 2:
         var1.setShape(new RectShape());
         break;
      case 3:
         var1.setShape(new OvalShape());
         break;
      default:
         throw new IllegalArgumentException();
      }

      this.view.setBackgroundDrawable(var1);
      this.view.invalidate();
   }

   private void updateAppearance() {
      if(this.backgroundImageDrawable == null) {
         if(this.shape == 0) {
            if(this.backgroundColor == 0) {
               ViewUtil.setBackgroundDrawable(this.view, this.defaultButtonDrawable);
            } else {
               ViewUtil.setBackgroundDrawable(this.view, (Drawable)null);
               TextViewUtil.setBackgroundColor(this.view, this.backgroundColor);
            }
         } else {
            this.setShape();
         }
      } else {
         ViewUtil.setBackgroundImage(this.view, this.backgroundImageDrawable);
      }
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Returns the button\'s background color"
   )
   public int BackgroundColor() {
      return this.backgroundColor;
   }

   @DesignerProperty(
      defaultValue = "&H00000000",
      editorType = "color"
   )
   @SimpleProperty(
      description = "Specifies the button\'s background color. The background color will not be visible if an Image is being displayed."
   )
   public void BackgroundColor(int var1) {
      this.backgroundColor = var1;
      this.updateAppearance();
   }

   @DesignerProperty(
      defaultValue = "True",
      editorType = "boolean"
   )
   @SimpleProperty
   public void Enabled(boolean var1) {
      TextViewUtil.setEnabled(this.view, var1);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "If set, user can tap check box to cause action."
   )
   public boolean Enabled() {
      return TextViewUtil.isEnabled(this.view);
   }

   @DesignerProperty(
      defaultValue = "False",
      editorType = "boolean"
   )
   @SimpleProperty(
      category = PropertyCategory.APPEARANCE
   )
   public void FontBold(boolean var1) {
      this.bold = var1;
      TextViewUtil.setFontTypeface(this.view, this.fontTypeface, var1, this.italic);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "If set, button text is displayed in bold."
   )
   public boolean FontBold() {
      return this.bold;
   }

   @DesignerProperty(
      defaultValue = "False",
      editorType = "boolean"
   )
   @SimpleProperty(
      category = PropertyCategory.APPEARANCE
   )
   public void FontItalic(boolean var1) {
      this.italic = var1;
      TextViewUtil.setFontTypeface(this.view, this.fontTypeface, this.bold, var1);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "If set, button text is displayed in italics."
   )
   public boolean FontItalic() {
      return this.italic;
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Point size for button text."
   )
   public float FontSize() {
      return TextViewUtil.getFontSize(this.view);
   }

   @DesignerProperty(
      defaultValue = "14.0",
      editorType = "non_negative_float"
   )
   @SimpleProperty(
      category = PropertyCategory.APPEARANCE
   )
   public void FontSize(float var1) {
      TextViewUtil.setFontSize(this.view, var1);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Font family for button text.",
      userVisible = false
   )
   public int FontTypeface() {
      return this.fontTypeface;
   }

   @DesignerProperty(
      defaultValue = "0",
      editorType = "typeface"
   )
   @SimpleProperty(
      userVisible = false
   )
   public void FontTypeface(int var1) {
      this.fontTypeface = var1;
      TextViewUtil.setFontTypeface(this.view, this.fontTypeface, this.bold, this.italic);
   }

   @SimpleEvent(
      description = "Indicates the cursor moved over the button so it is now possible to click it."
   )
   public void GotFocus() {
      EventDispatcher.dispatchEvent(this, "GotFocus", new Object[0]);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Image to display on button."
   )
   public String Image() {
      return this.imagePath;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "asset"
   )
   @SimpleProperty(
      description = "Specifies the path of the button\'s image.  If there is both an Image and a BackgroundColor, only the Image will be visible."
   )
   public void Image(String var1) {
      if(!var1.equals(this.imagePath) || this.backgroundImageDrawable == null) {
         String var2 = var1;
         if(var1 == null) {
            var2 = "";
         }

         this.imagePath = var2;
         this.backgroundImageDrawable = null;
         if(this.imagePath.length() > 0) {
            try {
               this.backgroundImageDrawable = MediaUtil.getBitmapDrawable(this.container.$form(), this.imagePath);
            } catch (IOException var3) {
               Log.e("ButtonBase", "Unable to load " + this.imagePath);
            }
         }

         this.updateAppearance();
      }
   }

   @SimpleEvent(
      description = "Indicates the cursor moved away from the button so it is now no longer possible to click it."
   )
   public void LostFocus() {
      EventDispatcher.dispatchEvent(this, "LostFocus", new Object[0]);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      userVisible = false
   )
   public int Shape() {
      return this.shape;
   }

   @DesignerProperty(
      defaultValue = "0",
      editorType = "button_shape"
   )
   @SimpleProperty(
      description = "Specifies the button\'s shape (default, rounded, rectangular, oval). The shape will not be visible if an Image is being displayed.",
      userVisible = false
   )
   public void Shape(int var1) {
      this.shape = var1;
      this.updateAppearance();
   }

   @DesignerProperty(
      defaultValue = "True",
      editorType = "boolean"
   )
   @SimpleProperty(
      description = "Specifies if a visual feedback should be shown  for a button that as an image as background."
   )
   public void ShowFeedback(boolean var1) {
      this.showFeedback = var1;
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Returns the button\'s visual feedback state"
   )
   public boolean ShowFeedback() {
      return this.showFeedback;
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Text to display on button."
   )
   public String Text() {
      return TextViewUtil.getText(this.view);
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty
   public void Text(String var1) {
      TextViewUtil.setText(this.view, var1);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Left, center, or right.",
      userVisible = false
   )
   public int TextAlignment() {
      return this.textAlignment;
   }

   @DesignerProperty(
      defaultValue = "1",
      editorType = "textalignment"
   )
   @SimpleProperty(
      userVisible = false
   )
   public void TextAlignment(int var1) {
      this.textAlignment = var1;
      TextViewUtil.setAlignment(this.view, var1, true);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Color for button text."
   )
   public int TextColor() {
      return this.textColor;
   }

   @DesignerProperty(
      defaultValue = "&H00000000",
      editorType = "color"
   )
   @SimpleProperty
   public void TextColor(int var1) {
      this.textColor = var1;
      if(var1 != 0) {
         TextViewUtil.setTextColor(this.view, var1);
      } else {
         TextViewUtil.setTextColors(this.view, this.defaultColorStateList);
      }
   }

   @SimpleEvent(
      description = "Indicates that the button was pressed down."
   )
   public void TouchDown() {
      EventDispatcher.dispatchEvent(this, "TouchDown", new Object[0]);
   }

   @SimpleEvent(
      description = "Indicates that a button has been released."
   )
   public void TouchUp() {
      EventDispatcher.dispatchEvent(this, "TouchUp", new Object[0]);
   }

   public abstract void click();

   public View getView() {
      return this.view;
   }

   public boolean longClick() {
      return false;
   }

   public void onClick(View var1) {
      this.click();
   }

   public void onFocusChange(View var1, boolean var2) {
      if(var2) {
         this.GotFocus();
      } else {
         this.LostFocus();
      }
   }

   public boolean onLongClick(View var1) {
      return this.longClick();
   }

   public boolean onTouch(View var1, MotionEvent var2) {
      if(var2.getAction() == 0) {
         if(this.ShowFeedback()) {
            var1.getBackground().setAlpha(70);
            var1.invalidate();
         }

         this.TouchDown();
      } else if(var2.getAction() == 1 || var2.getAction() == 3) {
         if(this.ShowFeedback()) {
            var1.getBackground().setAlpha(255);
            var1.invalidate();
         }

         this.TouchUp();
      }

      return false;
   }
}
