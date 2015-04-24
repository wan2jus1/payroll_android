package org.acra.collector;


public class ThreadCollector {

   public static String collect(Thread var0) {
      StringBuilder var1 = new StringBuilder();
      if(var0 != null) {
         var1.append("id=").append(var0.getId()).append("\n");
         var1.append("name=").append(var0.getName()).append("\n");
         var1.append("priority=").append(var0.getPriority()).append("\n");
         if(var0.getThreadGroup() != null) {
            var1.append("groupName=").append(var0.getThreadGroup().getName()).append("\n");
         }
      } else {
         var1.append("No broken thread, this might be a silent exception.");
      }

      return var1.toString();
   }
}
