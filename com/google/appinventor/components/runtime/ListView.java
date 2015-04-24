package com.google.appinventor.components.runtime;

import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
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
import com.google.appinventor.components.runtime.util.ElementsUtil;
import com.google.appinventor.components.runtime.util.YailList;

@DesignerComponent(
   category = ComponentCategory.USERINTERFACE,
   description = "<p>This is a visible component that displays a list of text elements. <br> The list can be set using the ElementsFromString property or using the Elements block in the blocks editor. <br> Warning: This component will not work correctly on Screens that are scrollable.</p>",
   iconName = "images/listView.png",
   nonVisible = false,
   version = 4
)
@SimpleObject
public final class ListView extends AndroidViewComponent implements OnItemClickListener {

   private static final int DEFAULT_BACKGROUND_COLOR = -16777216;
   private static final boolean DEFAULT_ENABLED = false;
   private static final int DEFAULT_TEXT_COLOR = -1;
   private static final int DEFAULT_TEXT_SIZE = 22;
   private static final String LOG_TAG = "ListView";
   private ArrayAdapter adapter;
   private int backgroundColor;
   protected final ComponentContainer container;
   private YailList items;
   private final android.widget.LinearLayout listViewLayout;
   private String selection;
   private int selectionIndex;
   private boolean showFilter = false;
   private int textColor;
   private int textSize;
   private EditText txtSearchBox;
   private final android.widget.ListView view;


   public ListView(ComponentContainer var1) {
      super(var1);
      this.container = var1;
      this.items = YailList.makeEmptyList();
      this.view = new android.widget.ListView(var1.$context());
      this.view.setOnItemClickListener(this);
      this.listViewLayout = new android.widget.LinearLayout(var1.$context());
      this.listViewLayout.setOrientation(1);
      this.txtSearchBox = new EditText(var1.$context());
      this.txtSearchBox.setSingleLine(true);
      this.txtSearchBox.setWidth(-2);
      this.txtSearchBox.setPadding(10, 10, 10, 10);
      this.txtSearchBox.setHint("Search list...");
      this.txtSearchBox.addTextChangedListener(new TextWatcher() {
         public void afterTextChanged(Editable var1) {
         }
         public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {
         }
         public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
            ListView.this.adapter.getFilter().filter(var1);
         }
      });
      if(this.showFilter) {
         this.txtSearchBox.setVisibility(0);
      } else {
         this.txtSearchBox.setVisibility(8);
      }

      this.Width(-2);
      this.BackgroundColor(-16777216);
      this.textColor = -1;
      this.TextColor(this.textColor);
      this.textSize = 22;
      this.TextSize(this.textSize);
      this.ElementsFromString("");
      this.listViewLayout.addView(this.txtSearchBox);
      this.listViewLayout.addView(this.view);
      this.listViewLayout.requestLayout();
      var1.$add(this);
   }

   @SimpleEvent(
      description = "Simple event to be raised after the an element has been chosen in the list. The selected element is available in the Selection property."
   )
   public void AfterPicking() {
      EventDispatcher.dispatchEvent(this, "AfterPicking", new Object[0]);
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "The color of the listview background."
   )
   public int BackgroundColor() {
      return this.backgroundColor;
   }

   @DesignerProperty(
      defaultValue = "&HFF000000",
      editorType = "color"
   )
   @SimpleProperty
   public void BackgroundColor(int var1) {
      this.backgroundColor = var1;
      this.setBackgroundColor(this.backgroundColor);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR
   )
   public YailList Elements() {
      return this.items;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "List of text elements to show in the ListView.  This willsignal an error if the elements are not text strings."
   )
   public void Elements(YailList var1) {
      this.items = ElementsUtil.elements(var1, "Listview");
      this.setAdapterData();
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The TextView elements specified as a string with the items separated by commas such as: Cheese,Fruit,Bacon,Radish. Each word before the comma will be an element in the list."
   )
   public void ElementsFromString(String var1) {
      this.items = ElementsUtil.elementsFromString(var1);
      this.setAdapterData();
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Determines the height of the list on the view."
   )
   public void Height(int var1) {
      int var2 = var1;
      if(var1 == -1) {
         var2 = -2;
      }

      super.Height(var2);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Returns the text last selected in the ListView."
   )
   public String Selection() {
      return this.selection;
   }

   @DesignerProperty(
      defaultValue = "",
      editorType = "string"
   )
   @SimpleProperty
   public void Selection(String var1) {
      this.selection = var1;
      this.selectionIndex = ElementsUtil.setSelectedIndexFromValue(var1, this.items);
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "The index of the currently selected item, starting at 1.  If no item is selected, the value will be 0.  If an attempt is made to set this to a number less than 1 or greater than the number of items in the ListView, SelectionIndex will be set to 0, and Selection will be set to the empty text."
   )
   public int SelectionIndex() {
      return this.selectionIndex;
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Specifies the position of the selected item in the ListView. This could be used to retrievethe text at the chosen position. If an attempt is made to set this to a number less than 1 or greater than the number of items in the ListView, SelectionIndex will be set to 0, and Selection will be set to the empty text."
   )
   public void SelectionIndex(int var1) {
      this.selectionIndex = ElementsUtil.selectionIndex(var1, this.items);
      this.selection = ElementsUtil.setSelectionFromIndex(var1, this.items);
   }

   @DesignerProperty(
      defaultValue = "False",
      editorType = "boolean"
   )
   @SimpleProperty(
      description = "Sets visibility of ShowFilterBar. True will show the bar, False will hide it."
   )
   public void ShowFilterBar(boolean var1) {
      this.showFilter = var1;
      if(var1) {
         this.txtSearchBox.setVisibility(0);
      } else {
         this.txtSearchBox.setVisibility(8);
      }
   }

   @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "Returns current state of ShowFilterBar for visibility."
   )
   public boolean ShowFilterBar() {
      return this.showFilter;
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "The text color of the listview items."
   )
   public int TextColor() {
      return this.textColor;
   }

   @DesignerProperty(
      defaultValue = "&HFFFFFFFF",
      editorType = "color"
   )
   @SimpleProperty
   public void TextColor(int var1) {
      this.textColor = var1;
      this.setAdapterData();
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "The text size of the listview items."
   )
   public int TextSize() {
      return this.textSize;
   }

   @DesignerProperty(
      defaultValue = "22",
      editorType = "non_negative_integer"
   )
   @SimpleProperty
   public void TextSize(int var1) {
      if(var1 > 1000) {
         this.textSize = 999;
      } else {
         this.textSize = var1;
      }

      this.setAdapterData();
   }

   @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Determines the width of the list on the view."
   )
   public void Width(int var1) {
      int var2 = var1;
      if(var1 == -1) {
         var2 = -2;
      }

      super.Width(var2);
   }

   public View getView() {
      return this.listViewLayout;
   }

   public Spannable[] itemsToColoredText() {
      int var4 = this.items.size();
      Spannable[] var1 = new Spannable[var4];

      for(int var3 = 1; var3 <= var4; ++var3) {
         SpannableString var2 = new SpannableString(YailList.YailListElementToString(this.items.get(var3)));
         var2.setSpan(new ForegroundColorSpan(this.textColor), 0, var2.length(), 0);
         var2.setSpan(new AbsoluteSizeSpan(this.textSize), 0, var2.length(), 0);
         var1[var3 - 1] = var2;
      }

      return var1;
   }

   public void onItemClick(AdapterView var1, View var2, int var3, long var4) {
      this.selection = var1.getAdapter().getItem(var3).toString();
      this.selectionIndex = var3 + 1;
      this.AfterPicking();
   }

   public void setAdapterData() {
      this.adapter = new ArrayAdapter(this.container.$context(), 17367043, this.itemsToColoredText());
      this.view.setAdapter(this.adapter);
   }

   public void setBackgroundColor(int var1) {
      this.backgroundColor = var1;
      this.view.setBackgroundColor(this.backgroundColor);
      this.listViewLayout.setBackgroundColor(this.backgroundColor);
      this.view.setCacheColorHint(this.backgroundColor);
   }
}
