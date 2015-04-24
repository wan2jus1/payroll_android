package com.google.appinventor.components.runtime;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.util.TextViewUtil;
import com.google.appinventor.components.runtime.util.ViewUtil;

@SimpleObject
public abstract class TextBoxBase extends AndroidViewComponent implements OnFocusChangeListener {

   private int backgroundColor;
   private boolean bold;
   private Drawable defaultTextBoxDrawable;
   private int fontTypeface;
   private String hint;
   private boolean italic;
   private int textAlignment;
   private int textColor;
   protected final EditText view;


   public TextBoxBase(ComponentContainer var1, EditText var2) {
      super(var1);
      this.view = var2;
      this.view.setOnFocusChangeListener(this);
      this.defaultTextBoxDrawable = this.view.getBackground();
      var1.$add(this);
      var1.setChildWidth(this, 160);
      this.TextAlignment(0);
      this.Enabled(true);
      this.fontTypeface = 0;
      TextViewUtil.setFontTypeface(this.view, this.fontTypeface, this.bold, this.italic);
      this.FontSize(14.0F);
      this.Hint("");
      this.Text("");
      this.TextColor(-16777216);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "The background color of the input box.  You can choose a color by name in the Designer or in the Blocks Editor.  The default background color is \'default\' (shaded 3-D look)."
   )
   public int BackgroundColor() {
      return this.backgroundColor;
   }

   @DesignerProperty(
      defaultValue = "&H00000000",
      editorType = "color"
   )
   @SimpleProperty
   public void BackgroundColor(int var1) {
      this.backgroundColor = var1;
      if(var1 != 0) {
         TextViewUtil.setBackgroundColor(this.view, var1);
      } else {
         ViewUtil.setBackgroundDrawable(this.view, this.defaultTextBoxDrawable);
      }
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
      description = "Whether the user can enter text into this input box.  By default, this is true."
   )
   public boolean Enabled() {
      return TextViewUtil.isEnabled(this.view);
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
      description = "Whether the font for the text should be bold.  By default, it is not.",
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
      description = "Whether the text should appear in italics.  By default, it does not.",
      userVisible = false
   )
   public boolean FontItalic() {
      return this.italic;
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "The font size for the text.  By default, it is 14.0 points."
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
      description = "The font for the text.  The value can be changed in the Designer.",
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

   @SimpleEvent
   public void GotFocus() {
      EventDispatcher.dispatchEvent(this, "GotFocus", new Object[0]);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Text that should appear faintly in the input box to provide a hint as to what the user should enter.  This can only be seen if the <code>Text</code> property is empty."
   )
   public String Hint() {
      return this.hint;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty
   public void Hint(String var1) {
      this.hint = var1;
      this.view.setHint(var1);
      this.view.invalidate();
   }

   @SimpleEvent
   public void LostFocus() {
      EventDispatcher.dispatchEvent(this, "LostFocus", new Object[0]);
   }

   @SimpleFunction(
      description = "Sets the textbox active."
   )
   public void RequestFocus() {
      this.view.requestFocus();
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public String Text() {
      return TextViewUtil.getText(this.view);
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The text in the input box, which can be set by the programmer in the Designer or Blocks Editor, or it can be entered by the user (unless the <code>Enabled</code> property is false)."
   )
   public void Text(String var1) {
      TextViewUtil.setText(this.view, var1);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Whether the text should be left justified, centered, or right justified.  By default, text is left justified.",
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
      category = PropertyCategory.APPEARANCE,
      description = "The color for the text.  You can choose a color by name in the Designer or in the Blocks Editor.  The default text color is black."
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

   public void onFocusChange(View var1, boolean var2) {
      if(var2) {
         this.GotFocus();
      } else {
         this.LostFocus();
      }
   }
}
