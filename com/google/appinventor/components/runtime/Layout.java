package com.google.appinventor.components.runtime;

import android.view.ViewGroup;
import com.google.appinventor.components.runtime.AndroidViewComponent;

public interface Layout {

   void add(AndroidViewComponent var1);

   ViewGroup getLayoutManager();
}
