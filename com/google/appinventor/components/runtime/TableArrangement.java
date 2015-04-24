package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.view.View;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.TableLayout;
import com.google.appinventor.components.runtime.util.ViewUtil;

@DesignerComponent(
   category = ComponentCategory.LAYOUT,
   description = "<p>A formatting element in which to place components that should be displayed in tabular form.</p>",
   version = 1
)
@SimpleObject
public class TableArrangement extends AndroidViewComponent implements Component, ComponentContainer {

   private final Activity context;
   private final TableLayout viewLayout;


   public TableArrangement(ComponentContainer var1) {
      super(var1);
      this.context = var1.$context();
      this.viewLayout = new TableLayout(this.context, 2, 2);
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
      userVisible = false
   )
   public int Columns() {
      return this.viewLayout.getNumColumns();
   }

   @DesignerProperty(
      defaultValue = "2",
      editorType = "non_negative_integer"
   )
   @SimpleProperty(
      userVisible = false
   )
   public void Columns(int var1) {
      this.viewLayout.setNumColumns(var1);
   }

   @SimpleProperty(
      userVisible = false
   )
   public int Rows() {
      return this.viewLayout.getNumRows();
   }

   @DesignerProperty(
      defaultValue = "2",
      editorType = "non_negative_integer"
   )
   @SimpleProperty(
      userVisible = false
   )
   public void Rows(int var1) {
      this.viewLayout.setNumRows(var1);
   }

   public View getView() {
      return this.viewLayout.getLayoutManager();
   }

   public void setChildHeight(AndroidViewComponent var1, int var2) {
      ViewUtil.setChildHeightForTableLayout(var1.getView(), var2);
   }

   public void setChildWidth(AndroidViewComponent var1, int var2) {
      ViewUtil.setChildWidthForTableLayout(var1.getView(), var2);
   }
}
