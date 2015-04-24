package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.view.View;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.LinearLayout;
import com.google.appinventor.components.runtime.util.AlignmentUtil;
import com.google.appinventor.components.runtime.util.ViewUtil;

@SimpleObject
public class HVArrangement extends AndroidViewComponent implements Component, ComponentContainer {

   private final AlignmentUtil alignmentSetter;
   private final Activity context;
   private int horizontalAlignment;
   private final int orientation;
   private int verticalAlignment;
   private final LinearLayout viewLayout;


   public HVArrangement(ComponentContainer var1, int var2) {
      super(var1);
      this.context = var1.$context();
      this.orientation = var2;
      this.viewLayout = new LinearLayout(this.context, var2, Integer.valueOf(100), Integer.valueOf(100));
      this.alignmentSetter = new AlignmentUtil(this.viewLayout);
      this.horizontalAlignment = 1;
      this.verticalAlignment = 1;
      this.alignmentSetter.setHorizontalAlignment(this.horizontalAlignment);
      this.alignmentSetter.setVerticalAlignment(this.verticalAlignment);
      var1.$add(this);
   }

   public void $add(AndroidViewComponent var1) {
      this.viewLayout.add(var1);
   }

   public Activity $context() {
      return this.context;
   }

   public Form $form() {
      return this.container.$form();
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "A number that encodes how contents of the arrangement are aligned  horizontally. The choices are: 1 = left aligned, 2 = horizontally centered,  3 = right aligned.  Alignment has no effect if the arrangement\'s width is automatic."
   )
   public int AlignHorizontal() {
      return this.horizontalAlignment;
   }

   @DesignerProperty(
      defaultValue = "1",
      editorType = "horizontal_alignment"
   )
   @SimpleProperty
   public void AlignHorizontal(int var1) {
      try {
         this.alignmentSetter.setHorizontalAlignment(var1);
         this.horizontalAlignment = var1;
      } catch (IllegalArgumentException var3) {
         this.container.$form().dispatchErrorOccurredEvent(this, "HorizontalAlignment", 1401, new Object[]{Integer.valueOf(var1)});
      }
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "A number that encodes how the contents of the arrangement are aligned  vertically. The choices are: 1 = aligned at the top, 2 = vertically centered, 3 = aligned at the bottom.  Alignment has no effect if the arrangement\'s height is automatic."
   )
   public int AlignVertical() {
      return this.verticalAlignment;
   }

   @DesignerProperty(
      defaultValue = "1",
      editorType = "vertical_alignment"
   )
   @SimpleProperty
   public void AlignVertical(int var1) {
      try {
         this.alignmentSetter.setVerticalAlignment(var1);
         this.verticalAlignment = var1;
      } catch (IllegalArgumentException var3) {
         this.container.$form().dispatchErrorOccurredEvent(this, "VerticalAlignment", 1402, new Object[]{Integer.valueOf(var1)});
      }
   }

   public View getView() {
      return this.viewLayout.getLayoutManager();
   }

   public void setChildHeight(AndroidViewComponent var1, int var2) {
      if(this.orientation == 0) {
         ViewUtil.setChildHeightForHorizontalLayout(var1.getView(), var2);
      } else {
         ViewUtil.setChildHeightForVerticalLayout(var1.getView(), var2);
      }
   }

   public void setChildWidth(AndroidViewComponent var1, int var2) {
      if(this.orientation == 0) {
         ViewUtil.setChildWidthForHorizontalLayout(var1.getView(), var2);
      } else {
         ViewUtil.setChildWidthForVerticalLayout(var1.getView(), var2);
      }
   }
}
