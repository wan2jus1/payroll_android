package com.google.appinventor.components.runtime;

import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
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

@DesignerComponent(
   category = ComponentCategory.USERINTERFACE,
   description = "A Slider is a progress bar that adds a draggable thumb. You can touch the thumb and drag left or right to set the slider thumb position. As the Slider thumb is dragged, it will trigger the PositionChanged event, reporting the position of the Slider thumb. The reported position of the Slider thumb can be used to dynamically update another component attribute, such as the font size of a TextBox or the radius of a Ball.",
   version = 2
)
@SimpleObject
public class Slider extends AndroidViewComponent implements OnSeekBarChangeListener {

   private static final boolean DEBUG = false;
   private static final String LOG_TAG = "Slider";
   private static final int initialLeftColor = -14336;
   private static final String initialLeftColorString = "&HFFFFC800";
   private static final int initialRightColor = -7829368;
   private static final String initialRightColorString = "&HFF888888";
   private ClipDrawable beforeThumb;
   private LayerDrawable fullBar;
   private int leftColor;
   private float maxValue;
   private float minValue;
   private int rightColor;
   private final SeekBar seekbar;
   private boolean thumbEnabled;
   private float thumbPosition;


   public Slider(ComponentContainer var1) {
      super(var1);
      this.seekbar = new SeekBar(var1.$context());
      this.fullBar = (LayerDrawable)this.seekbar.getProgressDrawable();
      this.beforeThumb = (ClipDrawable)this.fullBar.findDrawableByLayerId(16908301);
      this.leftColor = -14336;
      this.rightColor = -7829368;
      this.setSliderColors();
      var1.$add(this);
      this.minValue = 10.0F;
      this.maxValue = 50.0F;
      this.thumbPosition = 30.0F;
      this.thumbEnabled = true;
      this.seekbar.setOnSeekBarChangeListener(this);
      this.seekbar.setMax(100);
      this.setSeekbarPosition();
   }

   private void setSeekbarPosition() {
      float var1 = (this.thumbPosition - this.minValue) / (this.maxValue - this.minValue);
      this.seekbar.setProgress((int)(var1 * 100.0F));
   }

   private void setSliderColors() {
      this.fullBar.setColorFilter(this.rightColor, Mode.SRC);
      this.beforeThumb.setColorFilter(this.leftColor, Mode.SRC);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "The color of slider to the left of the thumb."
   )
   public int ColorLeft() {
      return this.leftColor;
   }

   @DesignerProperty(
      defaultValue = "&HFFFFC800",
      editorType = "color"
   )
   @SimpleProperty
   public void ColorLeft(int var1) {
      this.leftColor = var1;
      this.setSliderColors();
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "The color of slider to the left of the thumb."
   )
   public int ColorRight() {
      return this.rightColor;
   }

   @DesignerProperty(
      defaultValue = "&HFF888888",
      editorType = "color"
   )
   @SimpleProperty
   public void ColorRight(int var1) {
      this.rightColor = var1;
      this.setSliderColors();
   }

   public int Height() {
      return this.getView().getHeight();
   }

   public void Height(int var1) {
      this.container.setChildHeight(this, var1);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Returns the slider max value.",
      userVisible = true
   )
   public float MaxValue() {
      return this.maxValue;
   }

   @DesignerProperty(
      defaultValue = "50.0",
      editorType = "float"
   )
   @SimpleProperty(
      description = "Sets the maximum value of slider.  Changing the maximum value also resets Thumbposition to be halfway between the minimum and the (new) maximum. If the new maximum is less than the current minimum, then minimum and maximum will both be set to this value.  Setting MaxValue resets the thumb position to halfway between MinValue and MaxValue and signals the PositionChanged event.",
      userVisible = true
   )
   public void MaxValue(float var1) {
      this.maxValue = var1;
      this.minValue = Math.min(var1, this.minValue);
      this.ThumbPosition((this.minValue + this.maxValue) / 2.0F);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Returns the value of slider min value.",
      userVisible = true
   )
   public float MinValue() {
      return this.minValue;
   }

   @DesignerProperty(
      defaultValue = "10.0",
      editorType = "float"
   )
   @SimpleProperty(
      description = "Sets the minimum value of slider.  Changing the minimum value also resets Thumbposition to be halfway between the (new) minimum and the maximum. If the new minimum is greater than the current maximum, then minimum and maximum will both be set to this value.  Setting MinValue resets the thumb position to halfway between MinValue and MaxValue and signals the PositionChanged event.",
      userVisible = true
   )
   public void MinValue(float var1) {
      this.minValue = var1;
      this.maxValue = Math.max(var1, this.maxValue);
      this.ThumbPosition((this.minValue + this.maxValue) / 2.0F);
   }

   @SimpleEvent
   public void PositionChanged(float var1) {
      EventDispatcher.dispatchEvent(this, "PositionChanged", new Object[]{Float.valueOf(var1)});
   }

   @DesignerProperty(
      defaultValue = "True",
      editorType = "boolean"
   )
   @SimpleProperty(
      description = "Sets whether or not to display the slider thumb.",
      userVisible = true
   )
   public void ThumbEnabled(boolean var1) {
      this.thumbEnabled = var1;
      short var2;
      if(this.thumbEnabled) {
         var2 = 255;
      } else {
         var2 = 0;
      }

      this.seekbar.getThumb().mutate().setAlpha(var2);
      this.seekbar.setEnabled(this.thumbEnabled);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Returns whether or not the slider thumb is being be shown",
      userVisible = true
   )
   public boolean ThumbEnabled() {
      return this.thumbEnabled;
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Returns the position of slider thumb",
      userVisible = true
   )
   public float ThumbPosition() {
      return this.thumbPosition;
   }

   @DesignerProperty(
      defaultValue = "30.0",
      editorType = "float"
   )
   @SimpleProperty(
      description = "Sets the position of the slider thumb. If this value is greater than MaxValue, then it will be set to same value as MaxValue. If this value is less than MinValue, then it will be set to same value as MinValue.",
      userVisible = true
   )
   public void ThumbPosition(float var1) {
      this.thumbPosition = Math.max(Math.min(var1, this.maxValue), this.minValue);
      this.setSeekbarPosition();
      this.PositionChanged(this.thumbPosition);
   }

   public View getView() {
      return this.seekbar;
   }

   public void onProgressChanged(SeekBar var1, int var2, boolean var3) {
      this.thumbPosition = (this.maxValue - this.minValue) * (float)var2 / 100.0F + this.minValue;
      this.PositionChanged(this.thumbPosition);
   }

   public void onStartTrackingTouch(SeekBar var1) {
   }

   public void onStopTrackingTouch(SeekBar var1) {
   }
}
