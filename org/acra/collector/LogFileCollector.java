package org.acra.collector;

import android.content.Context;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.acra.util.BoundedLinkedList;

class LogFileCollector {

   public static String collectLogFile(Context var0, String var1, int var2) throws IOException {
      BoundedLinkedList var3 = new BoundedLinkedList(var2);
      BufferedReader var4;
      if(var1.contains("/")) {
         var4 = new BufferedReader(new InputStreamReader(new FileInputStream(var1)), 1024);
      } else {
         var4 = new BufferedReader(new InputStreamReader(var0.openFileInput(var1)), 1024);
      }

      for(var1 = var4.readLine(); var1 != null; var1 = var4.readLine()) {
         var3.add(var1 + "\n");
      }

      return var3.toString();
   }
}
