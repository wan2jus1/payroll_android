package com.google.appinventor.components.runtime;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.util.TextViewUtil;

@DesignerComponent(
   category = ComponentCategory.USERINTERFACE,
   description = "Checkbox that raises an event when the user clicks on it. There are many properties affecting its appearance that can be set in the Designer or Blocks Editor.",
   version = 2
)
@SimpleObject
public final class CheckBox extends AndroidViewComponent implements OnCheckedChangeListener, OnFocusChangeListener {

   private int backgroundColor;
   private boolean bold;
   private int fontTypeface;
   private boolean italic;
   private int textColor;
   private final android.widget.CheckBox view;


   public CheckBox(ComponentContainer var1) {
      super(var1);
      this.view = new android.widget.CheckBox(var1.$context());
      this.view.setOnFocusChangeListener(this);
      this.view.setOnCheckedChangeListener(this);
      var1.$add(this);
      this.BackgroundColor(16777215);
      this.Enabled(true);
      this.fontTypeface = 0;
      TextViewUtil.setFontTypeface(this.view, this.fontTypeface, this.bold, this.italic);
      this.FontSize(14.0F);
      this.Text("");
      this.TextColor(-16777216);
      this.Checked(false);
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

   @SimpleEvent
   public void Changed() {
      EventDispatcher.dispatchEvent(this, "Changed", new Object[0]);
   }

   @DesignerProperty(
      defaultValue = "False",
      editorType = "boolean"
   )
   @SimpleProperty
   public void Checked(boolean var1) {
      this.view.setChecked(var1);
      this.view.invalidate();
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public boolean Checked() {
      return this.view.isChecked();
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
      category = PropertyCategory.BEHAVIOR
   )
   public boolean Enabled() {
      return this.view.isEnabled();
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

   @SimpleEvent
   public void GotFocus() {
      EventDispatcher.dispatchEvent(this, "GotFocus", new Object[0]);
   }

   @SimpleEvent
   public void LostFocus() {
      EventDispatcher.dispatchEvent(this, "LostFocus", new Object[0]);
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

   public void onCheckedChanged(CompoundButton var1, boolean var2) {
      this.Changed();
   }

   public void onFocusChange(View var1, boolean var2) {
      if(var2) {
         this.GotFocus();
      } else {
         this.LostFocus();
      }
   }
}
