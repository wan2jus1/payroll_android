package gnu.kawa.models;

import gnu.kawa.models.Display;

public interface Window {

   Display getDisplay();

   String getTitle();

   void open();

   void setContent(Object var1);

   void setMenuBar(Object var1);

   void setTitle(String var1);
}
