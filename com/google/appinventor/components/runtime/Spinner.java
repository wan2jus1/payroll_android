package com.google.appinventor.components.runtime;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.util.ElementsUtil;
import com.google.appinventor.components.runtime.util.YailList;

@DesignerComponent(
   category = ComponentCategory.USERINTERFACE,
   description = "<p>A spinner component that displays a pop-up with a list of elements. These elements can be set in the Designer or Blocks Editor by setting the<code>ElementsFromString</code> property to a string-separated concatenation (for example, <em>choice 1, choice 2, choice 3</em>) or by setting the <code>Elements</code> property to a List in the Blocks editor. Spinners are created with the first item already selected. So selecting  it does not generate an After Picking event. Consequently it\'s useful to make the  first Spinner item be a non-choice like \"Select from below...\". </p>",
   iconName = "images/spinner.png",
   nonVisible = false,
   version = 1
)
@SimpleObject
public final class Spinner extends AndroidViewComponent implements OnItemSelectedListener {

   private ArrayAdapter adapter;
   private boolean isInitialized = false;
   private YailList items = new YailList();
   private String selection;
   private int selectionIndex;
   private final android.widget.Spinner view;


   public Spinner(ComponentContainer var1) {
      super(var1);
      this.view = new android.widget.Spinner(var1.$context());
      this.adapter = new ArrayAdapter(var1.$context(), 17367048);
      this.adapter.setDropDownViewResource(17367049);
      this.view.setAdapter(this.adapter);
      this.view.setOnItemSelectedListener(this);
      var1.$add(this);
   }

   private void setAdapterData(String[] var1) {
      this.adapter.clear();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         this.adapter.add(var1[var2]);
      }

   }

   @SimpleEvent(
      description = "Event called after the user selects an item from the dropdown list."
   )
   public void AfterSelecting(String var1) {
      EventDispatcher.dispatchEvent(this, "AfterSelecting", new Object[]{var1});
   }

   @SimpleFunction(
      description = "displays the dropdown list for selection, same action as when the user clicks on the spinner."
   )
   public void DisplayDropdown() {
      this.view.performClick();
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "returns a list of text elements to be picked from."
   )
   public YailList Elements() {
      return this.items;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "adds the passed text element to the Spinner list"
   )
   public void Elements(YailList var1) {
      this.items = ElementsUtil.elements(var1, "Spinner");
      this.isInitialized = false;
      this.setAdapterData(var1.toStringArray());
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "sets the Spinner list to the elements passed in the comma-separated string"
   )
   public void ElementsFromString(String var1) {
      this.items = ElementsUtil.elementsFromString(var1);
      this.setAdapterData(var1.split(" *, *"));
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Text with the current title for the Spinner window"
   )
   public String Prompt() {
      return this.view.getPrompt().toString();
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "sets the Spinner window prompt to the given tittle"
   )
   public void Prompt(String var1) {
      this.view.setPrompt(var1);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Returns the current selected item in the spinner "
   )
   public String Selection() {
      return this.selection;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Set the selected item in the spinner"
   )
   public void Selection(String var1) {
      this.selection = var1;
      this.view.setSelection(this.adapter.getPosition(var1));
      this.selectionIndex = ElementsUtil.setSelectedIndexFromValue(var1, this.items);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The index of the currently selected item, starting at 1. If no item is selected, the value will be 0."
   )
   public int SelectionIndex() {
      return this.selectionIndex;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Set the spinner selection to the element at the given index.If an attempt is made to set this to a number less than 1 or greater than the number of items in the Spinner, SelectionIndex will be set to 0, and Selection will be set to empty."
   )
   public void SelectionIndex(int var1) {
      this.selectionIndex = ElementsUtil.selectionIndex(var1, this.items);
      this.view.setSelection(this.selectionIndex - 1);
      this.selection = ElementsUtil.setSelectionFromIndex(var1, this.items);
   }

   public View getView() {
      return this.view;
   }

   public void onItemSelected(AdapterView var1, View var2, int var3, long var4) {
      if(!this.isInitialized) {
         this.isInitialized = true;
      } else {
         this.SelectionIndex(var3 + 1);
         this.AfterSelecting(this.selection);
      }
   }

   public void onNothingSelected(AdapterView var1) {
      this.view.setSelection(0);
   }
}
