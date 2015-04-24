package com.google.appinventor.components.runtime;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.util.TextViewUtil;

@DesignerComponent(
   category = ComponentCategory.USERINTERFACE,
   description = "A Label displays a piece of text, which is specified through the <code>Text</code> property.  Other properties, all of which can be set in the Designer or Blocks Editor, control the appearance and placement of the text.",
   version = 3
)
@SimpleObject
public final class Label extends AndroidViewComponent {

   private static final int DEFAULT_LABEL_MARGIN = 2;
   private int backgroundColor;
   private boolean bold;
   private int defaultLabelMarginInDp = 0;
   private int fontTypeface;
   private boolean hasMargins;
   private boolean italic;
   private final android.widget.LinearLayout.LayoutParams linearLayoutParams;
   private int textAlignment;
   private int textColor;
   private final TextView view;


   public Label(ComponentContainer var1) {
      super(var1);
      this.view = new TextView(var1.$context());
      var1.$add(this);
      LayoutParams var2 = this.view.getLayoutParams();
      if(var2 instanceof android.widget.LinearLayout.LayoutParams) {
         this.linearLayoutParams = (android.widget.LinearLayout.LayoutParams)var2;
         this.defaultLabelMarginInDp = dpToPx(this.view, 2);
      } else {
         this.defaultLabelMarginInDp = 0;
         this.linearLayoutParams = null;
         Log.e("Label", "Error: The label\'s view does not have linear layout parameters");
         (new RuntimeException()).printStackTrace();
      }

      this.TextAlignment(0);
      this.BackgroundColor(16777215);
      this.fontTypeface = 0;
      TextViewUtil.setFontTypeface(this.view, this.fontTypeface, this.bold, this.italic);
      this.FontSize(14.0F);
      this.Text("");
      this.TextColor(-16777216);
      this.HasMargins(true);
   }

   private static int dpToPx(View var0, int var1) {
      float var2 = var0.getContext().getResources().getDisplayMetrics().density;
      return Math.round((float)var1 * var2);
   }

   private void setLabelMargins(boolean var1) {
      int var2;
      if(var1) {
         var2 = this.defaultLabelMarginInDp;
      } else {
         var2 = 0;
      }

      this.linearLayoutParams.setMargins(var2, var2, var2, var2);
      this.view.invalidate();
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE
   )
   public int BackgroundColor() {
      return this.backgroundColor;
   }

   @DesignerProperty(
      defaultValue = "&H00FFFFFF",
      editorType = "color"
   )
   @SimpleProperty
   public void BackgroundColor(int var1) {
      this.backgroundColor = var1;
      if(var1 != 0) {
         TextViewUtil.setBackgroundColor(this.view, var1);
      } else {
         TextViewUtil.setBackgroundColor(this.view, 16777215);
      }
   }

   @DesignerProperty(
      defaultValue = "False",
      editorType = "boolean"
   )
   @SimpleProperty(
      userVisible = false
   )
   public void FontBold(boolean var1) {
      this.bold = var1;
      TextViewUtil.setFontTypeface(this.view, this.fontTypeface, var1, this.italic);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      userVisible = false
   )
   public boolean FontBold() {
      return this.bold;
   }

   @DesignerProperty(
      defaultValue = "False",
      editorType = "boolean"
   )
   @SimpleProperty(
      userVisible = false
   )
   public void FontItalic(boolean var1) {
      this.italic = var1;
      TextViewUtil.setFontTypeface(this.view, this.fontTypeface, this.bold, var1);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      userVisible = false
   )
   public boolean FontItalic() {
      return this.italic;
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE
   )
   public float FontSize() {
      return TextViewUtil.getFontSize(this.view);
   }

   @DesignerProperty(
      defaultValue = "14.0",
      editorType = "non_negative_float"
   )
   @SimpleProperty
   public void FontSize(float var1) {
      TextViewUtil.setFontSize(this.view, var1);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
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

   @DesignerProperty(
      defaultValue = "True",
      editorType = "boolean"
   )
   @SimpleProperty(
      userVisible = true
   )
   public void HasMargins(boolean var1) {
      this.hasMargins = var1;
      this.setLabelMargins(var1);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Reports whether or not the label appears with margins.  All four margins (left, right, top, bottom) are the same.  This property has no effect in the designer, where labels are always shown with margins.",
      userVisible = true
   )
   public boolean HasMargins() {
      return this.hasMargins;
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE
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
      userVisible = false
   )
   public int TextAlignment() {
      return this.textAlignment;
   }

   @DesignerProperty(
      defaultValue = "0",
      editorType = "textalignment"
   )
   @SimpleProperty(
      userVisible = false
   )
   public void TextAlignment(int var1) {
      this.textAlignment = var1;
      TextViewUtil.setAlignment(this.view, var1, false);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE
   )
   public int TextColor() {
      return this.textColor;
   }

   @DesignerProperty(
      defaultValue = "&HFF000000",
      editorType = "color"
   )
   @SimpleProperty
   public void TextColor(int var1) {
      this.textColor = var1;
      if(var1 != 0) {
         TextViewUtil.setTextColor(this.view, var1);
      } else {
         TextViewUtil.setTextColor(this.view, -16777216);
      }
   }

   public View getView() {
      return this.view;
   }
}
