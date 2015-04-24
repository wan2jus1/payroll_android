package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.google.appinventor.components.runtime.ListPicker;
import com.google.appinventor.components.runtime.util.AnimationUtil;

public class ListPickerActivity extends Activity implements OnItemClickListener {

   static int backgroundColor;
   static int itemColor;
   ListPickerActivity.MyAdapter adapter;
   private String closeAnim = "";
   private android.widget.ListView listView;
   EditText txtSearchBox;


   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      android.widget.LinearLayout var4 = new android.widget.LinearLayout(this);
      var4.setOrientation(1);
      Intent var2 = this.getIntent();
      if(var2.hasExtra(ListPicker.LIST_ACTIVITY_ANIM_TYPE)) {
         this.closeAnim = var2.getStringExtra(ListPicker.LIST_ACTIVITY_ANIM_TYPE);
      }

      if(var2.hasExtra(ListPicker.LIST_ACTIVITY_ORIENTATION_TYPE)) {
         String var3 = var2.getStringExtra(ListPicker.LIST_ACTIVITY_ORIENTATION_TYPE).toLowerCase();
         if(var3.equals("portrait")) {
            this.setRequestedOrientation(1);
         } else if(var3.equals("landscape")) {
            this.setRequestedOrientation(0);
         }
      }

      if(var2.hasExtra(ListPicker.LIST_ACTIVITY_TITLE)) {
         this.setTitle(var2.getStringExtra(ListPicker.LIST_ACTIVITY_TITLE));
      }

      if(var2.hasExtra(ListPicker.LIST_ACTIVITY_ARG_NAME)) {
         String[] var6 = this.getIntent().getStringArrayExtra(ListPicker.LIST_ACTIVITY_ARG_NAME);
         this.listView = new android.widget.ListView(this);
         this.listView.setOnItemClickListener(this);
         itemColor = var2.getIntExtra(ListPicker.LIST_ACTIVITY_ITEM_TEXT_COLOR, -1);
         backgroundColor = var2.getIntExtra(ListPicker.LIST_ACTIVITY_BACKGROUND_COLOR, -16777216);
         var4.setBackgroundColor(backgroundColor);
         this.adapter = new ListPickerActivity.MyAdapter(this, var6);
         this.listView.setAdapter(this.adapter);
         String var5 = var2.getStringExtra(ListPicker.LIST_ACTIVITY_SHOW_SEARCH_BAR);
         this.txtSearchBox = new EditText(this);
         this.txtSearchBox.setSingleLine(true);
         this.txtSearchBox.setWidth(-2);
         this.txtSearchBox.setPadding(10, 10, 10, 10);
         this.txtSearchBox.setHint("Search list...");
         if(var5 == null || !var5.equalsIgnoreCase("true")) {
            this.txtSearchBox.setVisibility(8);
         }

         this.txtSearchBox.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable var1) {
            }
            public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {
            }
            public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
               ListPickerActivity.this.adapter.getFilter().filter(var1);
            }
         });
      } else {
         this.setResult(0);
         this.finish();
         AnimationUtil.ApplyCloseScreenAnimation(this, this.closeAnim);
      }

      var4.addView(this.txtSearchBox);
      var4.addView(this.listView);
      this.setContentView(var4);
      var4.requestLayout();
      ((InputMethodManager)this.getSystemService("input_method")).hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0);
      this.getWindow().setSoftInputMode(3);
   }

   public void onItemClick(AdapterView var1, View var2, int var3, long var4) {
      String var6 = (String)var1.getAdapter().getItem(var3);
      Intent var7 = new Intent();
      var7.putExtra(ListPicker.LIST_ACTIVITY_RESULT_NAME, var6);
      var7.putExtra(ListPicker.LIST_ACTIVITY_RESULT_INDEX, var3 + 1);
      this.closeAnim = var6;
      this.setResult(-1, var7);
      this.finish();
      AnimationUtil.ApplyCloseScreenAnimation(this, this.closeAnim);
   }

   public boolean onKeyDown(int var1, KeyEvent var2) {
      if(var1 == 4) {
         boolean var3 = super.onKeyDown(var1, var2);
         AnimationUtil.ApplyCloseScreenAnimation(this, this.closeAnim);
         return var3;
      } else {
         return super.onKeyDown(var1, var2);
      }
   }

   private static class MyAdapter extends ArrayAdapter {

      private final Context mContext;


      public MyAdapter(Context var1, String[] var2) {
         super(var1, 17367040, var2);
         this.mContext = var1;
      }

      public long getItemId(int var1) {
         return (long)((String)this.getItem(var1)).hashCode();
      }

      public View getView(int var1, View var2, ViewGroup var3) {
         TextView var4 = (TextView)var2;
         TextView var5 = var4;
         if(var4 == null) {
            var5 = (TextView)LayoutInflater.from(this.mContext).inflate(17367043, var3, false);
         }

         var5.setText((CharSequence)this.getItem(var1));
         var5.setTextColor(ListPickerActivity.itemColor);
         return var5;
      }
   }
}
