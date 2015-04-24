package com.google.appinventor.components.runtime;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableLayout.LayoutParams;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.Layout;

@SimpleObject
public class TableLayout implements Layout {

   private final Handler handler;
   private final android.widget.TableLayout layoutManager;
   private int numColumns;
   private int numRows;


   TableLayout(Context var1, int var2, int var3) {
      this.layoutManager = new android.widget.TableLayout(var1);
      this.numColumns = var2;
      this.numRows = var3;
      this.handler = new Handler();

      for(int var5 = 0; var5 < var3; ++var5) {
         TableRow var4 = new TableRow(var1);

         for(int var6 = 0; var6 < var2; ++var6) {
            var4.addView(this.newEmptyCellView(), var6, newEmptyCellLayoutParams());
         }

         this.layoutManager.addView(var4, var5, new LayoutParams());
      }

   }

   private void addChild(AndroidViewComponent var1) {
      int var3 = var1.Row();
      int var4 = var1.Column();
      if(var3 != -1 && var4 != -1) {
         if(var3 >= 0 && var3 < this.numRows) {
            if(var4 >= 0 && var4 < this.numColumns) {
               TableRow var2 = (TableRow)this.layoutManager.getChildAt(var3);
               var2.removeViewAt(var4);
               View var5 = var1.getView();
               var2.addView(var5, var4, var5.getLayoutParams());
            } else {
               Log.e("TableLayout", "Child has illegal Column property: " + var1);
            }
         } else {
            Log.e("TableLayout", "Child has illegal Row property: " + var1);
         }
      } else {
         this.addChildLater(var1);
      }
   }

   private void addChildLater(final AndroidViewComponent var1) {
      this.handler.post(new Runnable() {
         public void run() {
            TableLayout.this.addChild(var1);
         }
      });
   }

   private static android.widget.TableRow.LayoutParams newCellLayoutParams() {
      return new android.widget.TableRow.LayoutParams();
   }

   private static android.widget.TableRow.LayoutParams newEmptyCellLayoutParams() {
      return new android.widget.TableRow.LayoutParams(0, 0);
   }

   private View newEmptyCellView() {
      return new TextView(this.layoutManager.getContext());
   }

   public void add(AndroidViewComponent var1) {
      var1.getView().setLayoutParams(newCellLayoutParams());
      this.addChildLater(var1);
   }

   public ViewGroup getLayoutManager() {
      return this.layoutManager;
   }

   int getNumColumns() {
      return this.numColumns;
   }

   int getNumRows() {
      return this.numRows;
   }

   void setNumColumns(int var1) {
      int var3;
      if(var1 > this.numColumns) {
         this.layoutManager.getContext();

         for(var3 = 0; var3 < this.numRows; ++var3) {
            TableRow var2 = (TableRow)this.layoutManager.getChildAt(var3);

            for(int var4 = this.numColumns; var4 < var1; ++var4) {
               var2.addView(this.newEmptyCellView(), var4, newEmptyCellLayoutParams());
            }
         }

         this.numColumns = var1;
      } else if(var1 < this.numColumns) {
         for(var3 = 0; var3 < this.numRows; ++var3) {
            ((TableRow)this.layoutManager.getChildAt(var3)).removeViews(var1, this.numColumns - var1);
         }

         this.numColumns = var1;
         return;
      }

   }

   void setNumRows(int var1) {
      if(var1 > this.numRows) {
         Context var2 = this.layoutManager.getContext();

         for(int var4 = this.numRows; var4 < var1; ++var4) {
            TableRow var3 = new TableRow(var2);

            for(int var5 = 0; var5 < this.numColumns; ++var5) {
               var3.addView(this.newEmptyCellView(), var5, newEmptyCellLayoutParams());
            }

            this.layoutManager.addView(var3, var4, new LayoutParams());
         }

         this.numRows = var1;
      } else if(var1 < this.numRows) {
         this.layoutManager.removeViews(var1, this.numRows - var1);
         this.numRows = var1;
         return;
      }

   }
}
